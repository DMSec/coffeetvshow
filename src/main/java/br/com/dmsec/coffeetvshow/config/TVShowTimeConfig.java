/*
 * coffeetvshow
 * Copyright (C) 2018  DMSec - @douglasmsi
 * 
 */


package br.com.dmsec.coffeetvshow.config;

import org.springframework.boot.context.properties.ConfigurationProperties;



@ConfigurationProperties(prefix = "coffeetvshow.tvshowtime")
public class TVShowTimeConfig {
    private String tokenFile;

    public String getTokenFile() {
        return tokenFile;
    }

    public void setTokenFile(String tokenFile) {
        this.tokenFile = tokenFile;
    }
}
