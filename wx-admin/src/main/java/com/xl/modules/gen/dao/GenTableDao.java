/**
 * Copyright &copy; 2017 <a href="#">xf</a> All rights reserved.
 */
package com.xl.modules.gen.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xl.common.persistence.CrudDao;
import com.xl.modules.gen.entity.GenTable;

/**
 * 业务表DAO接口
 * @author dingrenxin
 * @version 2013-10-15
 */
@Mapper
public interface GenTableDao extends CrudDao<GenTable> {
	
}
