/*
 * coffeetvshow
 * Copyright (C) 2018  DMSec - @douglasmsi
 * 
 */




package br.com.dmsec.coffeetvshow.business.coffeetvshow;

import br.com.dmsec.coffeetvshow.business.eztv.TorrentEZTV;
import br.com.dmsec.coffeetvshow.business.tvshowtime.EpisodeTST;

public class EpisodeCoffee {
	
	
	private EpisodeTST episodeTST;
	
	private TorrentEZTV torrent;
	
	public EpisodeTST getEpisodeTST() {
		return episodeTST;
	}
	public void setEpisodeTST(EpisodeTST episodeTST) {
		this.episodeTST = episodeTST;
	}
	public TorrentEZTV getTorrent() {
		return torrent;
	}
	public void setTorrent(TorrentEZTV torrent) {
		this.torrent = torrent;
	}
	
	
}
