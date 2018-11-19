/*
 * coffeetvshow
 * Copyright (C) 2018  DMSec - @douglasmsi
 * 
 */


package br.com.dmsec.coffeetvshow.business.tvshowtime;



public class AuthorizationCode extends Message {
    
	private String deviceCode;
    private String userCode;
    private String verificationUrl;
    private Integer expiresIn;
    private Integer interval;
    
    

    public String getDeviceCode() {
		return deviceCode;
	}



	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}



	public String getUserCode() {
		return userCode;
	}



	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}



	public String getVerificationUrl() {
		return verificationUrl;
	}



	public void setVerificationUrl(String verificationUrl) {
		this.verificationUrl = verificationUrl;
	}



	public Integer getExpiresIn() {
		return expiresIn;
	}



	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}



	public Integer getInterval() {
		return interval;
	}



	public void setInterval(Integer interval) {
		this.interval = interval;
	}



	@Override
    public String toString() {
        return "AuthorizationCode{" +
                "device_code='" + deviceCode + '\'' +
                ", user_code='" + userCode + '\'' +
                ", verification_url='" + verificationUrl + '\'' +
                ", expires_in=" + expiresIn +
                ", interval=" + interval +
                '}';
    }
}
