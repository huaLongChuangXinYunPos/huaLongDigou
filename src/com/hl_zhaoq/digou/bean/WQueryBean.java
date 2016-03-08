package com.hl_zhaoq.digou.bean;

public class WQueryBean {
	private String CharSet;// ;//(字符集)
	private String APPDevID;// :支付设备ID,（手机为手机号，别的为CPUID）
	private String APPSystem;// : 设备系统版本（ANDROID4.2.1, IOS1.1）
	private String APPVersion;// :HL-Ver2.1
	private String SignType;// (签名类型RSA)
	private String Sign;// :签名:以下蓝色部分的RSA签名字符串
	private String OwnerNo;// :String (户主账户编码)，
	private String OwnerType;// :String (店铺Store还是顾客User)
	private String WPassword;// :密码
	private String QueryDatetime;// ：查询时间

	public String getCharSet() {
		return CharSet;
	}

	public void setCharSet(String charSet) {
		CharSet = charSet;
	}

	public String getAPPDevID() {
		return APPDevID;
	}

	public void setAPPDevID(String aPPDevID) {
		APPDevID = aPPDevID;
	}

	public String getAPPSystem() {
		return APPSystem;
	}

	public void setAPPSystem(String aPPSystem) {
		APPSystem = aPPSystem;
	}

	public String getAPPVersion() {
		return APPVersion;
	}

	public void setAPPVersion(String aPPVersion) {
		APPVersion = aPPVersion;
	}

	public String getSignType() {
		return SignType;
	}

	public void setSignType(String signType) {
		SignType = signType;
	}

	public String getSign() {
		return Sign;
	}

	public void setSign(String sign) {
		Sign = sign;
	}

	public String getOwnerNo() {
		return OwnerNo;
	}

	public void setOwnerNo(String ownerNo) {
		OwnerNo = ownerNo;
	}

	public String getOwnerType() {
		return OwnerType;
	}

	public void setOwnerType(String ownerType) {
		OwnerType = ownerType;
	}

	public String getWPassword() {
		return WPassword;
	}

	public void setWPassword(String wPassword) {
		WPassword = wPassword;
	}

	public String getQueryDatetime() {
		return QueryDatetime;
	}

	public void setQueryDatetime(String queryDatetime) {
		QueryDatetime = queryDatetime;
	}

}
