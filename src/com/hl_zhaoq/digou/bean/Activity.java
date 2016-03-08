package com.hl_zhaoq.digou.bean;

import java.math.BigDecimal;

/*
 [cPloyNo] [varchar](16) NOT NULL,  --促销专场编码
 [cPloyTypeNo] [varchar](32) NOT NULL,  ----策略类别代码
 [cPloyTypeName] [varchar](64) NOT NULL, --策略类别名称
 [fQuantity_Ploy] [money] NOT NULL, --数量限制
 [dDateStart] [datetime] NULL,       --开始日期
 [cTimeStart] [char](10) NULL,       --开始时间
 [dDateEnd] [datetime] NULL,         --结束日期
 [cTimeEnd] [char](10) NULL,         --结束时间
 */
public class Activity {
	public String cPloyNo;
	public String cPloyTypeNo;
	public String cPloyTypeName;
	public String dDateStart;
	public String cTimeStart;
	public String dDateEnd;
	public String cTimeEnd;
	public BigDecimal fQuantity_Ploy=new BigDecimal(0);
	public Integer Days=0;
	public Integer Hours=0;
	public Integer minutes=0;


	public Integer getDays() {
		return Days;
	}

	public void setDays(Integer days) {
		Days = days;
	}

	public Integer getHours() {
		return Hours;
	}

	public void setHours(Integer hours) {
		Hours = hours;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

	public String getcPloyNo() {
		return cPloyNo;
	}

	public void setcPloyNo(String cPloyNo) {
		this.cPloyNo = cPloyNo;
	}

	public String getcPloyTypeNo() {
		return cPloyTypeNo;
	}

	public void setcPloyTypeNo(String cPloyTypeNo) {
		this.cPloyTypeNo = cPloyTypeNo;
	}

	public String getcPloyTypeName() {
		return cPloyTypeName;
	}

	public void setcPloyTypeName(String cPloyTypeName) {
		this.cPloyTypeName = cPloyTypeName;
	}

	public String getdDateStart() {
		return dDateStart;
	}

	public void setdDateStart(String dDateStart) {
		this.dDateStart = dDateStart;
	}

	public String getcTimeStart() {
		return cTimeStart;
	}

	public void setcTimeStart(String cTimeStart) {
		this.cTimeStart = cTimeStart;
	}

	public String getdDateEnd() {
		return dDateEnd;
	}

	public void setdDateEnd(String dDateEnd) {
		this.dDateEnd = dDateEnd;
	}

	public String getcTimeEnd() {
		return cTimeEnd;
	}

	public void setcTimeEnd(String cTimeEnd) {
		this.cTimeEnd = cTimeEnd;
	}

	public BigDecimal getfQuantity_Ploy() {
		return fQuantity_Ploy;
	}

	public void setfQuantity_Ploy(BigDecimal fQuantity_Ploy) {
		this.fQuantity_Ploy = fQuantity_Ploy;
	}

}
