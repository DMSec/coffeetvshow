/*
 * coffeetvshow
 * Copyright (C) 2018  DMSec - @douglasmsi
 * 
 */



package br.com.dmsec.coffeetvshow.business.tvshowtime;

import org.springframework.data.annotation.Id;

public class ShowTST {

	@Id
	private Long id;
	private String name;
	private String seen_episodes;
	private String aired_episodes;
	private String genre;
	private String hashtag;

	private String imdb_id;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSeen_episodes() {
		return seen_episodes;
	}
	public void setSeen_episodes(String seen_episodes) {
		this.seen_episodes = seen_episodes;
	}
	public String getAired_episodes() {
		return aired_episodes;
	}
	public void setAired_episodes(String aired_episodes) {
		this.aired_episodes = aired_episodes;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getHashtag() {
		return hashtag;
	}
	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}
	public String getImdb_id() {
		return imdb_id;
	}
	public void setImdb_id(String imdb_id) {
		this.imdb_id = imdb_id;
	}
	
	
}
