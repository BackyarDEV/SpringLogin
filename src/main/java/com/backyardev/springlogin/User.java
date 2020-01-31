package com.backyardev.springlogin;

public class User {
	private static String name = null;
	private static String email = null;
	private static String image_url = null;
	private static String id = null;
	public static String getName() {
		return name;
	}
	public void setName(String name) {
		User.name = name;
	}
	public static String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		User.email = email;
	}
	public static String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		User.image_url = image_url;
	}
	public static String getId() {
		return id;
	}
	public void setId(String id) {
		User.id = id;
	}
}
