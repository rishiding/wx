/**
 * Copyright &copy; 2017 <a href="#">xf</a> All rights reserved.
 */
package com.xl.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xl.common.persistence.Page;
import com.xl.common.service.CrudService;
import com.xl.modules.sys.entity.BuildingFloor;
import com.xl.modules.sys.dao.BuildingFloorDao;

/**
 * 建筑楼层Service
 * @author rishi
 * @version 2018-03-19
 */
@Service
@Transactional(readOnly = true)
public class BuildingFloorService extends CrudService<BuildingFloorDao, BuildingFloor> {

	public BuildingFloor get(String id) {
		return super.get(id);
	}
	
	public List<BuildingFloor> findList(BuildingFloor buildingFloor) {
		return super.findList(buildingFloor);
	}
	
	public Page<BuildingFloor> findPage(Page<BuildingFloor> page, BuildingFloor buildingFloor) {
		return super.findPage(page, buildingFloor);
	}
	
	@Transactional(readOnly = false)
	public void save(BuildingFloor buildingFloor) {
		super.save(buildingFloor);
	}
	
	@Transactional(readOnly = false)
	public void delete(BuildingFloor buildingFloor) {
		super.delete(buildingFloor);
	}
	
}