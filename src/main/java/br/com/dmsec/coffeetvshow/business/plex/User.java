/*
 * coffeetvshow
 * Copyright (C) 2018  DMSec - @douglasmsi
 * 
 */


package br.com.dmsec.coffeetvshow.business.plex;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "User")
public class User {
    private Long id;
    private String name;

    @XmlAttribute(name="id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlAttribute(name="title")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
