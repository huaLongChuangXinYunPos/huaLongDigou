package com.hl_zhaoq.digou.activity.wallet.bean;

import java.math.BigDecimal;

public class BuyerR {

	public String WServerID = "1";
	public String Buyer_id;
	public String BuyerName = "222";
	public String BuyerPwd = "";
	public String BuyerPwdOld; // 旧密码
	public String PayPwdOld; // 旧密码
	public String TelOld; //旧手机号码
	public String TelNew; //新手机号码
	public String PayPwdNew; // 新密码
	public String BuyerPwdNew; // 新密码
	public String PayPwd = "999";
	public String Email = "";
	public String Tel = "";
	public String cRealName = "";
	public String Cid = "";
	public BigDecimal  WMoney =new BigDecimal(0);
	public BigDecimal getMyWMoney() {
		return WMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getWMoney() {
		return WMoney;
	}

	public void setWMoney(BigDecimal wMoney) {
		WMoney = wMoney;
	}

	public String getBuyer_id() {
		return Buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		Buyer_id = buyer_id;
	}

	public String getTelOld() {
		return TelOld;
	}

	public void setTelOld(String telOld) {
		TelOld = telOld;
	}

	public String getTelNew() {
		return TelNew;
	}

	public void setTelNew(String telNew) {
		TelNew = telNew;
	}

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

	public String getBuyerPwdOld() {
		return BuyerPwdOld;
	}

	public void setBuyerPwdOld(String buyerPwdOld) {
		BuyerPwdOld = buyerPwdOld;
	}

	public String getBuyerPwdNew() {
		return BuyerPwdNew;
	}

	public void setBuyerPwdNew(String buyerPwdNew) {
		BuyerPwdNew = buyerPwdNew;
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

	public String getBuyerPwd() {
		return BuyerPwd;
	}

	public void setBuyerPwd(String buyerPwd) {
		BuyerPwd = buyerPwd;
	}

	public String getPayPwd() {
		return PayPwd;
	}

	public void setPayPwd(String payPwd) {
		PayPwd = payPwd;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String getcRealName() {
		return cRealName;
	}

	public void setcRealName(String cRealName) {
		this.cRealName = cRealName;
	}

	public String getCid() {
		return Cid;
	}

	public void setCid(String cid) {
		Cid = cid;
	}

}
