/**
 * Copyright &copy; 2017 <a href="#">xf</a> All rights reserved.
 */
package com.xl.modules.sys.dao;

import com.xl.common.persistence.TreeDao;
import com.xl.modules.sys.entity.Office;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 机构DAO接口
 *
 * @author dingrenxin
 * @version 2014-05-16
 */
@Mapper
public interface OfficeDao extends TreeDao<Office> {
	 List<Office> findByAreaId(Office entity);
	
}
