package com.hl_zhaoq.digou.activity.wallet.bean;

import java.math.BigDecimal;

public class Transfer {
	public String input_charset = "UTF-8";
	public String app_id = "HM1920";
	public String app_system = "android4.4.4";
	public String app_version = "HL-Ver2.1";
	public String signtype = "RSA";
	public String sign = "1234567890";

	public String WTransferNo="";
	public String WTransfer_Datetime="2015-11-30 11:50:22.00";
	public String In_Account="1";//tel
	public String In_ID = "201511261037037952042";//buyerID
	public String In_Name = "1";//用户名。realname。
	public String In_WServerID = "1";//
	public String In_Account_type = "1";
	public String Out_Account = "";
	public String Out_ID = "";//buyerID
	public String Out_Name = "";
	public String Out_WserverID = "1";
	public String Out_Account_type = "2";
	public BigDecimal WTransfer_Money = new BigDecimal(10);
	public String WBeizhu = "转账";
	public String PayPwd = "555";

	public String getInput_charset() {
		return input_charset;
	}

	public void setInput_charset(String input_charset) {
		this.input_charset = input_charset;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getApp_system() {
		return app_system;
	}

	public void setApp_system(String app_system) {
		this.app_system = app_system;
	}

	public String getApp_version() {
		return app_version;
	}

	public void setApp_version(String app_version) {
		this.app_version = app_version;
	}

	public String getSigntype() {
		return signtype;
	}

	public void setSigntype(String signtype) {
		this.signtype = signtype;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getWTransferNo() {
		return WTransferNo;
	}

	public void setWTransferNo(String wTransferNo) {
		WTransferNo = wTransferNo;
	}

	public String getWTransfer_Datetime() {
		return WTransfer_Datetime;
	}

	public void setWTransfer_Datetime(String wTransfer_Datetime) {
		WTransfer_Datetime = wTransfer_Datetime;
	}

	public String getIn_Account() {
		return In_Account;
	}

	public void setIn_Account(String in_Account) {
		In_Account = in_Account;
	}

	public String getIn_ID() {
		return In_ID;
	}

	public void setIn_ID(String in_ID) {
		In_ID = in_ID;
	}

	public String getIn_Name() {
		return In_Name;
	}

	public void setIn_Name(String in_Name) {
		In_Name = in_Name;
	}

	public String getIn_WServerID() {
		return In_WServerID;
	}

	public void setIn_WServerID(String in_WServerID) {
		In_WServerID = in_WServerID;
	}

	public String getIn_Account_type() {
		return In_Account_type;
	}

	public void setIn_Account_type(String in_Account_type) {
		In_Account_type = in_Account_type;
	}

	public String getOut_Account() {
		return Out_Account;
	}

	public void setOut_Account(String out_Account) {
		Out_Account = out_Account;
	}

	public String getOut_ID() {
		return Out_ID;
	}

	public void setOut_ID(String out_ID) {
		Out_ID = out_ID;
	}

	public String getOut_Name() {
		return Out_Name;
	}

	public void setOut_Name(String out_Name) {
		Out_Name = out_Name;
	}

	public String getOut_WserverID() {
		return Out_WserverID;
	}

	public void setOut_WserverID(String out_WserverID) {
		Out_WserverID = out_WserverID;
	}

	public String getOut_Account_type() {
		return Out_Account_type;
	}

	public void setOut_Account_type(String out_Account_type) {
		Out_Account_type = out_Account_type;
	}

	public BigDecimal getWTransfer_Money() {
		return WTransfer_Money;
	}

	public void setWTransfer_Money(BigDecimal wTransfer_Money) {
		WTransfer_Money = wTransfer_Money;
	}

	public String getWBeizhu() {
		return WBeizhu;
	}

	public void setWBeizhu(String wBeizhu) {
		WBeizhu = wBeizhu;
	}

	public String getPayPwd() {
		return PayPwd;
	}

	public void setPayPwd(String payPwd) {
		PayPwd = payPwd;
	}

}
