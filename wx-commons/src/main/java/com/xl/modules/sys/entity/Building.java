/**
 * Copyright &copy; 2017 <a href="#">xf</a> All rights reserved.
 */
package com.xl.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.xl.common.persistence.DataEntity;

/**
 * 消防建筑Entity
 * @author rishi
 * @version 2018-03-19
 */
public class Building extends DataEntity<Building> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 建筑名
	private Unit unit;		// 编号
	private String buildingNo;		// 楼栋
	private String latitude;		// 纬度
	private String longitude;		// 经度
	private String point;		// 经纬度
	private String floorNum;		// 楼层数量
	private String planeImg;		// 平面图
	private String elevatorsNum;		// 电梯数量
	private String stairNum;		// 楼梯数量
	private String videoUrl;		// 视频监控地址
	
	public Building() {
		super();
	}

	public Building(String id){
		super(id);
	}

	@Length(min=0, max=255, message="建筑名长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	@Length(min=0, max=64, message="楼栋长度必须介于 0 和 64 之间")
	public String getBuildingNo() {
		return buildingNo;
	}

	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	@Length(min=0, max=100, message="经纬度长度必须介于 0 和 100 之间")
	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}
	
	@Length(min=0, max=11, message="楼层数量长度必须介于 0 和 11 之间")
	public String getFloorNum() {
		return floorNum;
	}

	public void setFloorNum(String floorNum) {
		this.floorNum = floorNum;
	}
	
	@Length(min=0, max=400, message="平面图长度必须介于 0 和 400 之间")
	public String getPlaneImg() {
		return planeImg;
	}

	public void setPlaneImg(String planeImg) {
		this.planeImg = planeImg;
	}
	
	@Length(min=0, max=11, message="电梯数量长度必须介于 0 和 11 之间")
	public String getElevatorsNum() {
		return elevatorsNum;
	}

	public void setElevatorsNum(String elevatorsNum) {
		this.elevatorsNum = elevatorsNum;
	}
	
	@Length(min=0, max=11, message="楼梯数量长度必须介于 0 和 11 之间")
	public String getStairNum() {
		return stairNum;
	}

	public void setStairNum(String stairNum) {
		this.stairNum = stairNum;
	}
	
	@Length(min=0, max=5000, message="视频监控地址长度必须介于 0 和 5000 之间")
	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	
}