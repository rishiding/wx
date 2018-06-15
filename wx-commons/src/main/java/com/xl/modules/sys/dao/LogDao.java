/**
 * Copyright &copy; 2017 <a href="#">xf</a> All rights reserved.
 */
package com.xl.modules.sys.dao;

import com.xl.common.persistence.CrudDao;
import com.xl.modules.sys.entity.Log;
import org.apache.ibatis.annotations.Mapper;

/**
 * 日志DAO接口
 *
 * @author dingrenxin
 * @version 2014-05-16
 */
@Mapper
public interface LogDao extends CrudDao<Log> {

}
