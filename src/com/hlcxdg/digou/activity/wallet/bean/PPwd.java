package com.hlcxdg.digou.activity.wallet.bean;

import java.math.BigDecimal;

public class PPwd {
	public String WServerID;
	public String BuyerName;
	public String PayPwdOld;
	public String PayPwdNew;
	
	public String getPayPwdOld() {
		return PayPwdOld;
	}
	public void setPayPwdOld(String payPwdOld) {
		PayPwdOld = payPwdOld;
	}
	public String getPayPwdNew() {
		return PayPwdNew;
	}
	public void setPayPwdNew(String payPwdNew) {
		PayPwdNew = payPwdNew;
	}
	public String getWServerID() {
		return WServerID;
	}
	public void setWServerID(String wServerID) {
		WServerID = wServerID;
	}
	public String getBuyerName() {
		return BuyerName;
	}
	public void setBuyerName(String buyerName) {
		BuyerName = buyerName;
	}


}
