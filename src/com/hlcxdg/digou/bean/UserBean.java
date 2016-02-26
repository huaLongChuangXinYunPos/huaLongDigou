package com.hlcxdg.digou.bean;

import java.math.BigDecimal;

public class UserBean {
	private double jingdu;
	private double weidu;
	private String UserNo;
	private String UserPwd;
	private String CVipNo;
	private String UserName;
	

	private String Tel;
	private String Credit_rating;
	private BigDecimal OCurValue;
	private BigDecimal PosCurValue;

	public double getJingdu() {
		return jingdu;
	}

	public void setJingdu(double jingdu) {
		this.jingdu = jingdu;
	}

	public double getWeidu() {
		return weidu;
	}

	public void setWeidu(double weidu) {
		this.weidu = weidu;
	}

	public String getUserNo() {
		return UserNo;
	}

	public void setUserNo(String userNo) {
		UserNo = userNo;
	}

	public String getUserPwd() {
		return UserPwd;
	}

	public void setUserPwd(String userPwd) {
		UserPwd = userPwd;
	}

	public String getCVipNo() {
		return CVipNo;
	}

	public void setCVipNo(String cVipNo) {
		CVipNo = cVipNo;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String getCredit_rating() {
		return Credit_rating;
	}

	public void setCredit_rating(String credit_rating) {
		Credit_rating = credit_rating;
	}

	public BigDecimal getOCurValue() {
		return OCurValue;
	}

	public void setOCurValue(BigDecimal oCurValue) {
		OCurValue = oCurValue;
	}

	public BigDecimal getPosCurValue() {
		return PosCurValue;
	}

	public void setPosCurValue(BigDecimal posCurValue) {
		PosCurValue = posCurValue;
	}

}
