/**
 * Copyright &copy; 2017 <a href="#">xf</a> All rights reserved.
 */
package com.xl.modules.wxuser.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xl.common.persistence.Page;
import com.xl.common.service.CrudService;
import com.xl.modules.wxuser.entity.WxUser;
import com.xl.modules.wxuser.dao.WxUserDao;

/**
 * 微信用户管理Service
 * @author dingrenxin
 * @version 2018-07-05
 */
@Service
@Transactional(readOnly = true)
public class WxUserService extends CrudService<WxUserDao, WxUser> {

	public WxUser get(String id) {
		return super.get(id);
	}
	
	public List<WxUser> findList(WxUser wxUser) {
		return super.findList(wxUser);
	}
	
	public Page<WxUser> findPage(Page<WxUser> page, WxUser wxUser) {
		return super.findPage(page, wxUser);
	}
	
	@Transactional(readOnly = false)
	public void save(WxUser wxUser) {
		super.save(wxUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxUser wxUser) {
		super.delete(wxUser);
	}
	
}