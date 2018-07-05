/**
 * Copyright &copy; 2017-2017<a href="#">rishi</a> All rights reserved.
 */
package com.xl.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xl.common.persistence.TreeDao;
import com.xl.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@Mapper
public interface OfficeDao extends TreeDao<Office> {
	public List<Office> findDeptList(Office entity);
 
	public List<Office> findInhospDept(Office office);
	
	public int updateCompanyInfo(Office office);
	
}
 