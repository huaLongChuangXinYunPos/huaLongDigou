package com.hl_zhaoq.digou.bean;

import java.math.BigDecimal;

/**
 * @author you
 */
public class PAXiangdan {
	public String cSaleSheetNo = "";
	public String ilineNo = "";// 
	public BigDecimal fPayMoney = new BigDecimal(0);// 
	public String cPayStyleNo = "";//zhifuleixing
	public String cPayStyleDetail = "";// zhifuzhanghao
	public String cPayTime = "";// 
	public String bAccount = "";// 

	public BigDecimal getfPayMoney() {
		return fPayMoney;
	}

	public void setfPayMoney(BigDecimal fPayMoney) {
		this.fPayMoney = fPayMoney;
	}

	public String getcSaleSheetNo() {
		return cSaleSheetNo;
	}

	public void setcSaleSheetNo(String cSaleSheetNo) {
		this.cSaleSheetNo = cSaleSheetNo;
	}

	public String getIlineNo() {
		return ilineNo;
	}

	public void setIlineNo(String ilineNo) {
		this.ilineNo = ilineNo;
	}

	public String getcPayStyleNo() {
		return cPayStyleNo;
	}

	public void setcPayStyleNo(String cPayStyleNo) {
		this.cPayStyleNo = cPayStyleNo;
	}

	public String getcPayStyleDetail() {
		return cPayStyleDetail;
	}

	public void setcPayStyleDetail(String cPayStyleDetail) {
		this.cPayStyleDetail = cPayStyleDetail;
	}

	public String getcPayTime() {
		return cPayTime;
	}

	public void setcPayTime(String cPayTime) {
		this.cPayTime = cPayTime;
	}

	public String getbAccount() {
		return bAccount;
	}

	public void setbAccount(String bAccount) {
		this.bAccount = bAccount;
	}
}
