/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.xl.modules.cms.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xl.common.persistence.CrudDao;
import com.xl.modules.cms.entity.Comment;


@Mapper
public interface CommentDao extends CrudDao<Comment> {

}
