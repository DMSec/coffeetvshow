/*
 * coffeetvshow
 * Copyright (C) 2018  DMSec - @douglasmsi
 * 
 */


package br.com.dmsec.coffeetvshow.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.dmsec.coffeetvshow.business.tvshowtime.EpisodeTST;
import br.com.dmsec.coffeetvshow.repository.EpisodeRepository;

@Service
public class EpisodeService {
	
	private EpisodeRepository episodeRepository;
	
	public EpisodeService(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    public Iterable<EpisodeTST> list() {
        return episodeRepository.findAll();
    }

    public Iterable<EpisodeTST> save(List<EpisodeTST> episodes) {
        return episodeRepository.save(episodes);
    }


}