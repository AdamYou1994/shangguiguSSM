package com.allen.crowd.funding.entity;

public class Role {
    private Integer id;

    private String name;

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

	public Role(Integer id, String name) {
		//super();
		this.id = id;
		this.name = name;
	}

	public Role() {
		//super();
	}
    
    
}