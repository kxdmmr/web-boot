package com.kxdmmr.blog.pojo;

import org.springframework.beans.factory.annotation.Value;

public class Article {
	
	int id;
	int parent_id;
	String title;
	String en_name;
	String content;
	int author;
	String create_date;
	String change_date;
	String status;
	String tags;
	int like_count;
	int unlike_count;
	int viewer_count;
	String desc;
	@Value("${ui_desc_max_long}") int desc_long;

	public String getDesc() {
		if (null == this.desc)
			setDesc(this.content);
		return desc;
	}

	public void setDesc(final String text) {
		int term = text.length();
		if(term > this.desc_long)
			term = this.desc_long;

		String desc = text.substring(0,term);

		if((term=desc.indexOf("- - -")) != -1){
			desc = text.substring(0,term);
		}

		if((term=desc.indexOf("---")) != -1){
			desc = text.substring(0,term);
		}

		if((term=desc.indexOf("* * *")) != -1){
			desc = text.substring(0,term);
		}

		if((term=desc.indexOf("***")) != -1){
			desc = text.substring(0,term);
		}

		desc = desc.replaceAll("#{1,5} .+\r\n","");
		desc = desc.replaceAll("#{1,5} .+\n","");

		if ((term = desc.lastIndexOf("\n")) != -1 ||
				(term = desc.lastIndexOf("。")) != -1 ||
				(term = desc.lastIndexOf("！")) != -1 ||
				(term = desc.lastIndexOf("，")) != -1)
			desc = desc.substring(0, term);

		this.desc = desc + "。";
	}

	public int getDesc_long() {
		return desc_long;
	}

	public void setDesc_long(int desc_long) {
		this.desc_long = desc_long;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEn_name() {
		return en_name;
	}

	public void setEn_name(String en_name) {
		this.en_name = en_name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getAuthor() {
		return author;
	}

	public void setAuthor(int author) {
		this.author = author;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getChange_date() {
		return change_date;
	}

	public void setChange_date(String change_date) {
		this.change_date = change_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public int getLike_count() {
		return like_count;
	}

	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}

	public int getUnlike_count() {
		return unlike_count;
	}

	public void setUnlike_count(int unlike_count) {
		this.unlike_count = unlike_count;
	}

	public int getViewer_count() {
		return viewer_count;
	}

	public void setViewer_count(int viewer_count) {
		this.viewer_count = viewer_count;
	}
}
