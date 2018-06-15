/**
 * Copyright &copy; 2017 <a href="#">xf</a> All rights reserved.
 */
package com.xl.modules.sys.dao;

import com.xl.common.persistence.TreeDao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.xl.modules.sys.entity.Unit;

/**
 * 单位管理DAO接口
 * @author rishi
 * @version 2017-11-16
 */
@Mapper
public interface UnitDao extends TreeDao<Unit> {
	  List<Unit> findByAreaId(Unit entity); 
}