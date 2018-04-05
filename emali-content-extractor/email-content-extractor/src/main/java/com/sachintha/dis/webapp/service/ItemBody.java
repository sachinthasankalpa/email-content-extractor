package com.sachintha.dis.webapp.service;

import java.text.Format;
import java.text.SimpleDateFormat;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemBody {
	private String content;
	private String contentType;
	public static Session session;
	Transaction transaction;
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		try {
			Document doc = Jsoup.parse(content);
			session = NewHibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			Elements tables = doc.select("table.MsoNormalTable");
			com.sachintha.dis.webapp.pojo.DefectInfo defect = null;
			for (Element table : tables) {
				Elements trs = table.select("tr");
				for (int i = 1; i < trs.size(); i++) {
					Elements tds = trs.get(i).select("td");
					defect = new com.sachintha.dis.webapp.pojo.DefectInfo(tds);
					Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String date = formatter.format(Message.datetime);
					defect.setDatetimeD(date);
					try {
						session.saveOrUpdate(defect);
					} catch (Exception ex) {

					}
				}

			}
			try {
				transaction.commit();
			} catch (Exception ex) {
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {

		this.contentType = contentType;
	}
}
