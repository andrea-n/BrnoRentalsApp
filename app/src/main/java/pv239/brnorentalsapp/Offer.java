package pv239.brnorentalsapp;

import java.io.Serializable;

/**
 * Created by Andrea Navratilova on 5/13/2017.
 */
public class Offer implements Serializable {
	private String source_url;
	private String title;
	private String price;
	private String description;
	private String preview_image;
	private String[] images;
	private String phone;
	private String email;
	private String fb_user;
	private String area;
	private String type;
	private String street;
	private Integer likes;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSource_url() {
		return source_url;
	}

	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPreview_image() {
		return preview_image;
	}

	public void setPreview_image(String preview_image) {
		this.preview_image = preview_image;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFb_user() {
		return fb_user;
	}

	public void setFb_user(String fb_user) {
		this.fb_user = fb_user;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}
}
