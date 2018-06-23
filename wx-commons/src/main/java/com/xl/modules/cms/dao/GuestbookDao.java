/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.xl.modules.cms.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xl.common.persistence.CrudDao;
import com.xl.modules.cms.entity.Guestbook;

/**
 * 留言DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@Mapper
public interface GuestbookDao extends CrudDao<Guestbook> {

}
