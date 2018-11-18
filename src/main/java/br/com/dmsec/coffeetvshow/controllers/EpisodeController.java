/*
 * coffeetvshow
 * Copyright (C) 2018  DMSec - @douglasmsi
 * 
 */


package br.com.dmsec.coffeetvshow.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dmsec.coffeetvshow.business.tvshowtime.EpisodeTST;
import br.com.dmsec.coffeetvshow.services.EpisodeService;

@RestController
@RequestMapping("/episode")
public class EpisodeController {
	
	private EpisodeService episodeService;
	
	public EpisodeController(EpisodeService episodeService) {
		this.episodeService = episodeService;
	}
	
	@GetMapping("/list")
	public Iterable<EpisodeTST> list(){
		return episodeService.list();
	}

}

