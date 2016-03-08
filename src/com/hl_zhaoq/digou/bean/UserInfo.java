package com.hl_zhaoq.digou.bean;

import java.math.BigDecimal;

public class UserInfo {

	private String UserNo="";
	private String UserPwd="";
	private String CVipNo="0";
	private String UserName="";

	private String Tel="0";
	private String Credit_rating="0";
	private BigDecimal OCurValue=BigDecimal.valueOf(0);
	private BigDecimal PosCurValue=BigDecimal.valueOf(0);

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
