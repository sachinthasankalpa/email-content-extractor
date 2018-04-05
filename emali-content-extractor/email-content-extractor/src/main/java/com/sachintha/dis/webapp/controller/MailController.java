package com.sachintha.dis.webapp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sachintha.dis.webapp.service.ItemBody;


@Controller
public class MailController {

	@RequestMapping("/mail")
	public String mail(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		HttpSession session = request.getSession();
		com.sachintha.dis.webapp.auth.TokenResponse tokens = (com.sachintha.dis.webapp.auth.TokenResponse) session.getAttribute("tokens");
		if (tokens == null) {
			// No tokens in session, user needs to sign in
			redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
			return "redirect:/index.html";
		}

		String tenantId = (String) session.getAttribute("userTenantId");

		tokens = com.sachintha.dis.webapp.auth.AuthHelper.ensureTokens(tokens, tenantId);

		String email = (String) session.getAttribute("userEmail");

		com.sachintha.dis.webapp.service.OutlookService outlookService = com.sachintha.dis.webapp.service.OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

		String folder = "inbox";
		String from = "\"from:IndrasiriP@esquel.com\"";
		String properties = "receivedDateTime,body";
		Integer maxResults = 15;
		try {
			com.sachintha.dis.webapp.service.PagedResult<com.sachintha.dis.webapp.service.Message> messages = outlookService.getMessages(folder, from, properties, maxResults).execute()
					.body();
			ItemBody.session = com.sachintha.dis.webapp.connection.NewHibernateUtil.getSessionFactory().openSession();
			Criteria criteria = ItemBody.session.createCriteria(com.sachintha.dis.webapp.pojo.DefectInfo.class);

			List<com.sachintha.dis.webapp.pojo.DefectInfo> defect_info_list = criteria.list();
			JSONArray displayInfo = new JSONArray(defect_info_list);
			model.addAttribute("defects", displayInfo);
			model.addAttribute("size", defect_info_list.size());
			
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/index.html";
		}

		return "mail";
	}
}
