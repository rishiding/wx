/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.xl.modules.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xl.common.persistence.CrudDao;
import com.xl.modules.cms.entity.Link;

/**
 * 链接DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@Mapper
public interface LinkDao extends CrudDao<Link> {
	
	public List<Link> findByIdIn(String[] ids);
//	{
//		return find("front Like where id in (:p1)", new Parameter(new Object[]{ids}));
//	}
	
	public int updateExpiredWeight(Link link);
//	{
//		return update("update Link set weight=0 where weight > 0 and weightDate < current_timestamp()");
//	}
//	public List<Link> fjindListByEntity();
}
