/*
 * coffeetvshow
 * Copyright (C) 2018  DMSec - @douglasmsi
 * 
 */


package br.com.dmsec.coffeetvshow.business.tvshowtime;

import org.springframework.data.annotation.Id;


public class EpisodeTST {
	
	@Id
    private Long id;
	private String name;
	private int number;
	private int season_number;
	private String airDate;
	private String airTime;
	private String network;
	private String overview;
	private String nbComments;
	private String is_new;
	
	private String seen;
	private String previousEpisode;
	private String nextEpisode;
	private ShowTST show;
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
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getSeason_number() {
		return season_number;
	}
	public void setSeason_number(int season_number) {
		this.season_number = season_number;
	}
	public String getAirDate() {
		return airDate;
	}
	public void setAirDate(String airDate) {
		this.airDate = airDate;
	}
	public String getAirTime() {
		return airTime;
	}
	public void setAirTime(String airTime) {
		this.airTime = airTime;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public String getNbComments() {
		return nbComments;
	}
	public void setNbComments(String nbComments) {
		this.nbComments = nbComments;
	}
	public String getIs_new() {
		return is_new;
	}
	public void setIs_new(String is_new) {
		this.is_new = is_new;
	}
	public String getSeen() {
		return seen;
	}
	public void setSeen(String seen) {
		this.seen = seen;
	}
	public String getPreviousEpisode() {
		return previousEpisode;
	}
	public void setPreviousEpisode(String previousEpisode) {
		this.previousEpisode = previousEpisode;
	}
	public String getNextEpisode() {
		return nextEpisode;
	}
	public void setNextEpisode(String nextEpisode) {
		this.nextEpisode = nextEpisode;
	}
	public ShowTST getShow() {
		return show;
	}
	public void setShow(ShowTST show) {
		this.show = show;
	}
	
	
	
	
	

}
