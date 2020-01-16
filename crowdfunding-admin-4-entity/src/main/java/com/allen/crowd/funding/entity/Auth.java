package com.allen.crowd.funding.entity;

public class Auth {
    private Integer id;

    private String name;

    private String title;

    private String categoryId;
    
    public Auth() {
	}

	public Auth(Integer id, String name, String title, String categoryId) {
		this.id = id;
		this.name = name;
		this.title = title;
		this.categoryId = categoryId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }
}