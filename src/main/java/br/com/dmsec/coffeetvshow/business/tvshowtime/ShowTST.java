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
	private String seenEpisodes;
	private String airedEpisodes;
	private String genre;
	private String hashtag;

	private String imdbId;

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

	public String getSeenEpisodes() {
		return seenEpisodes;
	}

	public void setSeenEpisodes(String seenEpisodes) {
		this.seenEpisodes = seenEpisodes;
	}

	public String getAiredEpisodes() {
		return airedEpisodes;
	}

	public void setAiredEpisodes(String airedEpisodes) {
		this.airedEpisodes = airedEpisodes;
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

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}
	
	
	
	
}
