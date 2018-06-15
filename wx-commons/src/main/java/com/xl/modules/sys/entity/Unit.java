/**
 * Copyright &copy; 2017 <a href="#">xf</a> All rights reserved.
 */
package com.xl.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;


import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import com.xl.modules.sys.entity.Area;
import com.xl.modules.sys.entity.Office;
import com.xl.modules.sys.utils.DictUtils;
import com.xl.common.persistence.TreeEntity;

/**
 * 单位管理Entity
 * @author rishi
 * @version 2017-11-16
 */
public class Unit extends TreeEntity<Unit> {
	
	private static final long serialVersionUID = 1L;
	
	private Area area;		// 归属区域
	private String code;		// 编码
	private String address;		// 联系地址
	private String longitude;		// 经度
	private String latitude;		// 纬度
	private String point;		// 经纬度
	private String zipCode;		// 邮政编码
	private String master;		// 负责人
	private String phone;		// 电话
	private String fax;		// 传真
	private String email;		// 邮箱
	private String usable;		// 是否启用
	private String industry;		// 所属行业
	private Office office;		// 消费机构
	private String securityType; //安防类型
	private String securityUser; //安保负责人
	private String securityPhone; //安保电话
	private Office streetOffice; //街道办
	public Unit() {
		super();
	}

	public Unit(String id){
		super(id);
	}

	

	@JsonBackReference
	@NotNull(message="父级编号不能为空")
	public Unit getParent() {
		return parent;
	}

	public void setParent(Unit parent) {
		this.parent = parent;
	}
	

	

	
	
	@NotNull(message="归属区域不能为空")
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	@Length(min=0, max=100, message="编码长度必须介于 0 和 100 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=255, message="联系地址长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	@Length(min=0, max=100, message="经纬度长度必须介于 0 和 100 之间")
	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}
	
	@Length(min=0, max=100, message="邮政编码长度必须介于 0 和 100 之间")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	@Length(min=0, max=100, message="负责人长度必须介于 0 和 100 之间")
	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}
	
	@Length(min=0, max=200, message="电话长度必须介于 0 和 200 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=200, message="传真长度必须介于 0 和 200 之间")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	@Length(min=0, max=200, message="邮箱长度必须介于 0 和 200 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSecurityUser() {
		return securityUser;
	}

	public void setSecurityUser(String securityUser) {
		this.securityUser = securityUser;
	}

	public String getSecurityPhone() {
		return securityPhone;
	}

	public void setSecurityPhone(String securityPhone) {
		this.securityPhone = securityPhone;
	}

	public Office getStreetOffice() {
		return streetOffice;
	}

	public void setStreetOffice(Office streetOffice) {
		this.streetOffice = streetOffice;
	}

	@Length(min=0, max=64, message="是否启用长度必须介于 0 和 64 之间")
	public String getUsable() {
		return usable;
	}

	public void setUsable(String usable) {
		this.usable = usable;
	}
	
	@Length(min=0, max=64, message="所属行业长度必须介于 0 和 64 之间")
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	public String getSecurityTypeName() {
		return DictUtils.getDictLabel(securityType, "sys_security_type", "") ;
	}
	public String getSecurityType() {
		return securityType;
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}
	
}