package com.hl_zhaoq.digou.bean;

import java.math.BigDecimal;

/**
 * @author you
 */
public class PAZongdan {
	public String Beizhu = "";//
	public String cSaleSheetNo = "";//
	public BigDecimal fPayMoney = new BigDecimal(0.00);//
	public String cPayTime = "";//

	public String getBeizhu() {
		return Beizhu;
	}

	public void setBeizhu(String beizhu) {
		Beizhu = beizhu;
	}

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

	public String getcPayTime() {
		return cPayTime;
	}

	public void setcPayTime(String cPayTime) {
		this.cPayTime = cPayTime;
	}

}
