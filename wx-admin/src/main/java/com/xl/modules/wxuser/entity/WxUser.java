/**
 * Copyright &copy; 2017 <a href="#">xf</a> All rights reserved.
 */
package com.xl.modules.wxuser.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xl.modules.sys.entity.Office;

import com.xl.common.persistence.DataEntity;

/**
 * 微信用户管理Entity
 * @author dingrenxin
 * @version 2018-07-05
 */
public class WxUser extends DataEntity<WxUser> {
	
	private static final long serialVersionUID = 1L;
	private String WxUserId;		// 微信用户id
	private Date lastLoginDate;		// 最后一次登录时间
	private Office hospital;		// 最后一次访问的医院
	private String UserName;		// 微信用户名
	
	public WxUser() {
		super();
	}

	public WxUser(String id){
		super(id);
	}

	@Length(min=0, max=64, message="微信用户id长度必须介于 0 和 64 之间")
	public String getWxUserId() {
		return WxUserId;
	}

	public void setWxUserId(String WxUserId) {
		this.WxUserId = WxUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	public Office getHospital() {
		return hospital;
	}

	public void setHospital(Office hospital) {
		this.hospital = hospital;
	}
	
	@Length(min=0, max=50, message="微信用户名长度必须介于 0 和 50 之间")
	public String getUserName() {
		return UserName;
	}

	public void setUserName(String UserName) {
		this.UserName = UserName;
	}
	
}