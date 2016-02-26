package com.hlcxdg.digou.activity.wallet.bean;

public class WTransferCertifyPara {
	public String WServerID="1";
	public String Tel="";
	public String getWServerID() {
		return WServerID;
	}
	public void setWServerID(String wServerID) {
		WServerID = wServerID;
	}
	public String getTel() {
		return Tel;
	}
	public void setTel(String tel) {
		Tel = tel;
	}
	public WTransferCertifyPara(String wServerID, String tel) {
		super();
		WServerID = wServerID;
		Tel = tel;
	}
	public WTransferCertifyPara() {
		super();
	}

}
