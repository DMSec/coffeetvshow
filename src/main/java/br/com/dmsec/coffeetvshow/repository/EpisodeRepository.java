/*
 * coffeetvshow
 * Copyright (C) 2018  DMSec - @douglasmsi
 * 
 */


package br.com.dmsec.coffeetvshow.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.dmsec.coffeetvshow.business.tvshowtime.EpisodeTST;

public interface EpisodeRepository extends MongoRepository<EpisodeTST, String>{

	Iterable<EpisodeTST> save(List<EpisodeTST> episodes);

}
