package com.hl_zhaoq.digou.activity.wallet.bean;

import java.math.BigDecimal;

public class PaymentPara {
	public String input_charset="UTF-8";
	public String app_id="HM1920";
	public String app_system="android4.4.4";
	public String app_version="HL-Ver2.1";
	public String signtype="RSA";
	public String sign="1234567890";

	public String cStoreNo="10001";
	public String WServerID_cStore="1";
	public String notify_url="http://www.baidu.com";

	public String payment_type="1";
	public BigDecimal fLastMoney=new BigDecimal(12);

	public String Buyer_id="201511251550150691296";
	public String WServerID_buyer="1";
	public String cardno="0";
	public String T_createtime="";
	public String T_paytime="";
	public Integer valid_delay=9;
	public String PayPwd="";
	public String CertifyCode="14334324";

	public String cSaleSheetNo="";
	public String subject="sales";
	public String cSheetDetail="201594587384573222";

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

	public String getcStoreNo() {
		return cStoreNo;
	}

	public void setcStoreNo(String cStoreNo) {
		this.cStoreNo = cStoreNo;
	}

	public String getWServerID_cStore() {
		return WServerID_cStore;
	}

	public void setWServerID_cStore(String wServerID_cStore) {
		WServerID_cStore = wServerID_cStore;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public BigDecimal getfLastMoney() {
		return fLastMoney;
	}

	public void setfLastMoney(BigDecimal fLastMoney) {
		this.fLastMoney = fLastMoney;
	}

	public String getBuyer_id() {
		return Buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		Buyer_id = buyer_id;
	}

	public String getWServerID_buyer() {
		return WServerID_buyer;
	}

	public void setWServerID_buyer(String wServerID_buyer) {
		WServerID_buyer = wServerID_buyer;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getT_createtime() {
		return T_createtime;
	}

	public void setT_createtime(String t_createtime) {
		T_createtime = t_createtime;
	}

	public String getT_paytime() {
		return T_paytime;
	}

	public void setT_paytime(String t_paytime) {
		T_paytime = t_paytime;
	}

	public Integer getValid_delay() {
		return valid_delay;
	}

	public void setValid_delay(Integer valid_delay) {
		this.valid_delay = valid_delay;
	}

	public String getPayPwd() {
		return PayPwd;
	}

	public void setPayPwd(String PayPwd) {
		this.PayPwd = PayPwd;
	}

	public String getCertifyCode() {
		return CertifyCode;
	}

	public void setCertifyCode(String certifyCode) {
		CertifyCode = certifyCode;
	}

	public String getcSaleSheetNo() {
		return cSaleSheetNo;
	}

	public void setcSaleSheetNo(String cSaleSheetNo) {
		this.cSaleSheetNo = cSaleSheetNo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getcSheetDetail() {
		return cSheetDetail;
	}

	public void setcSheetDetail(String cSheetDetail) {
		this.cSheetDetail = cSheetDetail;
	}
}
