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
	private String air_date;
	private String air_time;
	private String network;
	private String overview;
	private String nb_comments;
	private String is_new;
	
	private String seen;
	private String previous_episode;
	private String next_episode;
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
	public String getAir_date() {
		return air_date;
	}
	public void setAir_date(String air_date) {
		this.air_date = air_date;
	}
	public String getAir_time() {
		return air_time;
	}
	public void setAir_time(String air_time) {
		this.air_time = air_time;
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
	public String getNb_comments() {
		return nb_comments;
	}
	public void setNb_comments(String nb_comments) {
		this.nb_comments = nb_comments;
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
	
	public String getPrevious_episode() {
		return previous_episode;
	}
	public void setPrevious_episode(String previous_episode) {
		this.previous_episode = previous_episode;
	}
	public String getNext_episode() {
		return next_episode;
	}
	public void setNext_episode(String next_episode) {
		this.next_episode = next_episode;
	}
	public ShowTST getShow() {
		return show;
	}
	public void setShow(ShowTST show) {
		this.show = show;
	}	
	
	
	

}
