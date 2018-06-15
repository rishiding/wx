/**
 * Copyright &copy; 2017 <a href="#">xf</a> All rights reserved.
 */
package com.xl.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.xl.common.persistence.DataEntity;

/**
 * 建筑楼层Entity
 * @author rishi
 * @version 2018-03-19
 */
public class BuildingFloor extends DataEntity<BuildingFloor> {
	
	private static final long serialVersionUID = 1L;
	private Building building;		// 所属建筑
	private String floorNo;		// 楼层号
	private String planeImg;		// 楼层平面图
	private String videoUrl;		// 视频监控地址
	
	public BuildingFloor() {
		super();
	}

	public BuildingFloor(String id){
		super(id);
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}
	
	@Length(min=0, max=20, message="楼层号长度必须介于 0 和 20 之间")
	public String getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}
	
	@Length(min=0, max=400, message="楼层平面图长度必须介于 0 和 400 之间")
	public String getPlaneImg() {
		return planeImg;
	}

	public void setPlaneImg(String planeImg) {
		this.planeImg = planeImg;
	}
	
	@Length(min=0, max=5000, message="视频监控地址长度必须介于 0 和 5000 之间")
	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	
}