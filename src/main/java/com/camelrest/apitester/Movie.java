package com.camelrest.apitester;


public class Movie {

	private long id;

	private String title;

	private String description;

	private boolean available;

	public Movie(String title, String description, boolean available) {
		this.title = title;
		this.description = description;
		this.available = available;
	}

	@Override
	public String toString() {
		return "Movie [id = " + id +" title = "+title+" description = "+description+" available = "+ available +"]";		
	}

	public long getId(){return id;}
	public String getTitle(){return title;}
	public String getDescription(){return description;}
	public boolean getAvailable(){return available;}

	public void setId(long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
}
