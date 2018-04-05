package com.sachintha.dis.webapp.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class AuthorizeController {

	@RequestMapping(value="/authorize", method=RequestMethod.POST)
	public String authorize(
			@RequestParam("code") String code, 
			@RequestParam("id_token") String idToken,
			@RequestParam("state") UUID state,
			HttpServletRequest request) {
		// Get the expected state value from the session
		HttpSession session = request.getSession();
		UUID expectedState = (UUID) session.getAttribute("expected_state");
		UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");
		session.removeAttribute("expected_state");
		session.removeAttribute("expected_nonce");
		
		// Make sure that the state query parameter returned matches
		// the expected state
		if (state.equals(expectedState)) {
			com.sachintha.dis.webapp.auth.IdToken idTokenObj = com.sachintha.dis.webapp.auth.IdToken.parseEncodedToken(idToken, expectedNonce.toString());
			if (idTokenObj != null) {
				com.sachintha.dis.webapp.auth.TokenResponse tokenResponse = com.sachintha.dis.webapp.auth.AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());
				session.setAttribute("tokens", tokenResponse);
				session.setAttribute("userConnected", true);
				session.setAttribute("userName", idTokenObj.getName());
				session.setAttribute("userTenantId", idTokenObj.getTenantId());
				// Get user info
				com.sachintha.dis.webapp.service.OutlookService outlookService = com.sachintha.dis.webapp.service.OutlookServiceBuilder.getOutlookService(tokenResponse.getAccessToken(), null);
				com.sachintha.dis.webapp.service.OutlookUser user;
				try {
					user = outlookService.getCurrentUser().execute().body();
					session.setAttribute("userEmail", user.getMail());
				} catch (IOException e) {
					session.setAttribute("error", e.getMessage());
				}
			} else {
				session.setAttribute("error", "ID token failed validation.");
			}
		} else {
			session.setAttribute("error", "Unexpected state returned from authority.");
		}
		return "redirect:/mail.html";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/index.html";
	}
}
