/**
 * Copyright &copy; 2017 <a href="#">xf</a> All rights reserved.
 */
package com.xl.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xl.common.service.TreeService;
import com.xl.modules.sys.entity.Unit;
import com.xl.modules.sys.utils.UserUtils;
import com.xl.modules.sys.dao.UnitDao;

/**
 * 单位管理Service
 * @author rishi
 * @version 2017-11-16
 */
@Service
@Transactional(readOnly = true)
public class UnitService extends TreeService<UnitDao, Unit> {
	public List<Unit> findAll(){
		return UserUtils.getUnitList();
	}

	
	public List<Unit> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getUnitAllList();
		}else{
			return UserUtils.getUnitList();
		}
	}
	@Override
	@Transactional(readOnly = true)
	public List<Unit> findList(Unit unit) {
		if(unit != null){
			unit.setParentIds(unit.getParentIds()+"%");
			return dao.findByParentIdsLike(unit);
		}
		return  new ArrayList<Unit>();
	}
	
	@Transactional(readOnly = true)
	public List<Unit> findByAreaId(Unit unit) {
		if(unit != null){
			return dao.findByAreaId(unit);
		}
		return  new ArrayList<Unit>();
	}
	
	
	@Transactional(readOnly = false)
	public void save(Unit unit) {
		super.save(unit);
		UserUtils.removeCache(UserUtils.CACHE_UNIT_LIST);
		UserUtils.removeCache(UserUtils.CACHE_UNIT_ALL_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Unit unit) {
		super.delete(unit);
		UserUtils.removeCache(UserUtils.CACHE_UNIT_LIST);
		UserUtils.removeCache(UserUtils.CACHE_UNIT_ALL_LIST);
	}
	
}