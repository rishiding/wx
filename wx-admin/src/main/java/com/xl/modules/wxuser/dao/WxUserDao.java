/**
 * Copyright &copy; 2017 <a href="#">xf</a> All rights reserved.
 */
package com.xl.modules.wxuser.dao;

import com.xl.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import com.xl.modules.wxuser.entity.WxUser;

/**
 * 微信用户管理DAO接口
 * @author dingrenxin
 * @version 2018-07-05
 */
@Mapper
public interface WxUserDao extends CrudDao<WxUser> {
	
}