package com.hlcxdg.digou.bean;

import java.math.BigDecimal;

public class UserNeedsBean {

	private String userNeedsID = "xfd12323";
	private String userNo ;
	private String userName = "222";
	private String goodsNo = "12323";
	private String goodsName = "hfiuhfs";
	private BigDecimal qdnum = new BigDecimal(1);
	private float jingdu = 112.323f;
	private float weidu = 12.323f;
	private String zhuangtai = "已发布";
	private String beizhu = "12323";

	public String getUserNeedsID() {
		return userNeedsID;
	}

	public void setUserNeedsID(String userNeedsID) {
		this.userNeedsID = userNeedsID;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public BigDecimal getQdnum() {
		return qdnum;
	}

	public void setQdnum(BigDecimal qdnum) {
		this.qdnum = qdnum;
	}

	public float getJingdu() {
		return jingdu;
	}

	public void setJingdu(float jingdu) {
		this.jingdu = jingdu;
	}

	public float getWeidu() {
		return weidu;
	}

	public void setWeidu(float weidu) {
		this.weidu = weidu;
	}

	public String getZhuangtai() {
		return zhuangtai;
	}

	public void setZhuangtai(String zhuangtai) {
		this.zhuangtai = zhuangtai;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	public UserNeedsBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserNeedsBean(String userNeedsID, String userNo, String userName,
			String goodsNo, String goodsName, BigDecimal qdnum, float jingdu,
			float weidu, String zhuangtai, String beizhu) {
		super();
		this.userNeedsID = userNeedsID;
		this.userNo = userNo;
		this.userName = userName;
		this.goodsNo = goodsNo;
		this.goodsName = goodsName;
		this.qdnum = qdnum;
		this.jingdu = jingdu;
		this.weidu = weidu;
		this.zhuangtai = zhuangtai;
		this.beizhu = beizhu;
	}

	@Override
	public String toString() {
		return "UserNeedsBean [userNeedsID=" + userNeedsID + ", userNo="
				+ userNo + ", userName=" + userName + ", goodsNo=" + goodsNo
				+ ", goodsName=" + goodsName + ", qdnum=" + qdnum + ", jingdu="
				+ jingdu + ", weidu=" + weidu + ", zhuangtai=" + zhuangtai
				+ ", beizhu=" + beizhu + "]";
	}

}
