/**
 * Copyright &copy; 2017-2017<a href="#">rishi</a> All rights reserved.
 */
package com.xl.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xl.common.service.TreeService;
import com.xl.modules.sys.dao.OfficeDao;
import com.xl.modules.sys.entity.Office;
import com.xl.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {
	
	@Autowired
	private OfficeDao officeDao;

	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		if(office != null&&office.getParentIds()!=null&&!office.getParentIds().equals("")){			
			office.setParentIds(office.getParentIds()+"%");
			return dao.findByParentIdsLike(office);
		}
		return dao.findList(office);
	}
	@Transactional(readOnly = false)
	public List<Office> findDeptList(Office entity){
		return dao.findDeptList(entity);
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) { 
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}

	public List<Office> getAll(Office office) { 
		return  officeDao.findAllList(new Office());
	}
 
	public List<Office> findInhospDept(Office office) {
		return officeDao.findInhospDept(office);
	}
	
} 
