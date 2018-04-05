package com.sachintha.dis.webapp.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OutlookService {
	
	@GET("/v1.0/me")
	Call<OutlookUser> getCurrentUser();

	@GET("/v1.0/me/mailfolders/{folderid}/messages")
	Call<PagedResult<Message>> getMessages(
	  @Path("folderid") String folderId,
	  @Query("$search") String from,
	  @Query("$select") String select,
	  @Query("$top") Integer maxResults
	);
	
}
