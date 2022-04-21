package com.qaqnus.news.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="t_news")
public class News {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String title;
	
	@NotBlank
	@Max(500)
	private String context;
	
	@Temporal(TemporalType.TIMESTAMP)
	private final Date  created_at = new Date(System.currentTimeMillis());
	private Date updated_at = created_at;
	private Date approved_time = created_at;
	private boolean is_approved = false;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	public News() {
		this.updated_at = new Date(System.currentTimeMillis());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public Date getApproved_time() {
		return approved_time;
	}

	public void setApproved_time(Date approved_time) {
		this.approved_time = approved_time;
	}

	public boolean isIs_approved() {
		return is_approved;
	}

	public void setIs_approved(boolean is_approved) {
		setApproved_time(new Date(System.currentTimeMillis()));
		this.is_approved = is_approved;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
