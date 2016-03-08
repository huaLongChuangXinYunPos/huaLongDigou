package com.hl_zhaoq.digou.bean;

import java.math.BigDecimal;

import android.widget.EditText;

public class UserAddress {
	public int Address_ID = 0;
	public String UserNo = "";
	public String AddUser_Name = "";
	public String Address_Name = "";
	public String Tel = "";
	public String Address_Status = "0";

	public int getAddress_id() {
		return Address_ID;
	}

	public void setAddress_id(int address_id) {
		Address_ID = address_id;
	}

	public String getUserNo() {
		return UserNo;
	}

	public void setUserNo(String userNo) {
		UserNo = userNo;
	}

	public String getAddUser_Name() {
		return AddUser_Name;
	}

	public void setAddUser_Name(String addUser_Name) {
		AddUser_Name = addUser_Name;
	}

	public String getAddress_Name() {
		return Address_Name;
	}

	public void setAddress_Name(String address_Name) {
		Address_Name = address_Name;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String getAddress_Status() {
		return Address_Status;
	}

	public void setAddress_Status(String address_Status) {
		Address_Status = address_Status;
	}

}
