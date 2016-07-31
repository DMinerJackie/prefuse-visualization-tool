package org.apache.demo;

public class User {

	private static User instance = new User();

	private String name;

	private User() {

	}

	public static User getInstance() {
		return instance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
