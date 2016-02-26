package com.hlcxdg.digou.bean;

import java.util.List;

public class ResponseBean {
	private int respCode;
	private String resMsg;
	private List<UserBean> userList;
	private List<UserNeedsBean> userNeedsList;
	private List<StoreBaojiaBean> storeBaojiaList;
	private List<StoreInfoqdBean> storeInfoqdList;
	private StoreBaojiaBean storeBaojia;
	public StoreBaojiaBean getStoreBaojia() {
		return storeBaojia;
	}

	public void setStoreBaojia(StoreBaojiaBean storeBaojia) {
		this.storeBaojia = storeBaojia;
	}
	
	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public List<UserBean> getUserList() {
		return userList;
	}

	
	public void setUserList(List<UserBean> userList) {
		this.userList = userList;
	}

	public void setRespCode(int respCode) {
		this.respCode = respCode;
	}

	public int getRespCode() {
		return respCode;
	}

	public List<UserNeedsBean> getUserNeedsList() {
		return userNeedsList;
	}

	public void setUserNeedsList(List<UserNeedsBean> userNeedsList) {
		this.userNeedsList = userNeedsList;
	}

	public List<StoreBaojiaBean> getStoreBaojiaList() {
		return storeBaojiaList;
	}

	public void setStoreBaojiaList(List<StoreBaojiaBean> storeBaojiaList) {
		this.storeBaojiaList = storeBaojiaList;
	}

	public List<StoreInfoqdBean> getStoreInfoqdList() {
		return storeInfoqdList;
	}

	public void setStoreInfoqdList(List<StoreInfoqdBean> storeInfoqdList) {
		this.storeInfoqdList = storeInfoqdList;
	}

	

}
