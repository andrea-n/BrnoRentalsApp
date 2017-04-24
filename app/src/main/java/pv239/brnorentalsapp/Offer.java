package pv239.brnorentalsapp;

import java.io.Serializable;

/**
 * Created by Andrea Navratilova on 5/13/2017.
 */
public class Offer implements Serializable {
	public String source_url;
	public String title;
	public String price;
	public String description;
	public String preview_image;
	public String[] images;
	public String phone;
	public String email;
	public String fb_user;
	public String area;
	public String type;
	public String street;

	/*public Offer (String title) {
		this.title = title;
	}*/
}
