/*
 * coffeetvshow
 * Copyright (C) 2018  DMSec - @douglasmsi
 * 
 */


package br.com.dmsec.coffeetvshow.business.tvshowtime;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Message implements Serializable {
    
	private String result;
    private String message;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "result='" + result + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
