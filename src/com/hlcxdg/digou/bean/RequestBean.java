package com.hlcxdg.digou.bean;

import java.util.List;

public class RequestBean {
	private int reqCode;
	private String reqMsg;
	private List<UserBean> userList;
	private List<UserNeedsBean> userneedsList;
	private List<StoreBaojiaBean> storeBaojiaList;
	private StoreInfoqdBean storeInfoqdBean;
	private String baojiaID;
	private String userNo;
	private String peisongTel;
	private String peisongName;
	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getPeisongTel() {
		return peisongTel;
	}

	public void setPeisongTel(String peisongTel) {
		this.peisongTel = peisongTel;
	}

	public List<UserBean> getUserList() {
		return userList;
	}

	public void setUserList(List<UserBean> userList) {
		this.userList = userList;
	}

	public void setReqMsg(String reqMsg) {
		this.reqMsg = reqMsg;
	}

	public String getReqMsg() {
		return reqMsg;
	}

	public void setReqCode(int reqCode) {
		this.reqCode = reqCode;
	}

	public int getReqCode() {
		return reqCode;
	}

	public void setUserneedsList(List<UserNeedsBean> userneedsList) {
		this.userneedsList = userneedsList;
	}

	public List<UserNeedsBean> getUserneedsList() {
		return userneedsList;
	}

	public List<StoreBaojiaBean> getStoreBaojiaList() {
		return storeBaojiaList;
	}

	public void setStoreBaojiaList(List<StoreBaojiaBean> storeBaojiaList) {
		this.storeBaojiaList = storeBaojiaList;
	}

	public String getBaojiaID() {
		return baojiaID;
	}

	public void setBaojiaID(String baojiaID) {
		this.baojiaID = baojiaID;
	}

	public String getPeisongName() {
		return peisongName;
	}

	public void setPeisongName(String peisongName) {
		this.peisongName = peisongName;
	}

	public StoreInfoqdBean getStoreInfoqdBean() {
		return storeInfoqdBean;
	}

	public void setStoreInfoqdBean(StoreInfoqdBean storeInfoqdBean) {
		this.storeInfoqdBean = storeInfoqdBean;
	}


}
