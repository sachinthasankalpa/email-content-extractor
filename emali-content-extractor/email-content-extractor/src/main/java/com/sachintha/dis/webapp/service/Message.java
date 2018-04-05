package com.sachintha.dis.webapp.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
	private String id;
	private Date receivedDateTime;
	private Recipient from;
	private Boolean isRead;
	private String subject;
	private ItemBody body;
	public static Date datetime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getReceivedDateTime() {
		return receivedDateTime;
	}
	public void setReceivedDateTime(Date receivedDateTime) {
		this.receivedDateTime = receivedDateTime;
		datetime=receivedDateTime;
	}
	public Recipient getFrom() {
		return from;
	}
	public void setFrom(Recipient from) {
		this.from = from;
	}
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public ItemBody getBody() {
		return body;
	}
	public void setBody(ItemBody body) {
		this.body = body;
	}
}
