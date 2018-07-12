package com.xl.modules.wx.web;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xl.common.config.Global;
import com.xl.common.utils.StringUtils;
import com.xl.common.web.BaseController;
import com.xl.modules.sys.entity.Office;
import com.xl.modules.sys.service.OfficeService;
import com.xl.modules.sys.utils.UserUtils;
import com.xl.modules.wx.service.QrCodeService;
@Controller
@RequestMapping(value = "${adminPath}/wx")
public class ErcodeController extends BaseController {
	@Autowired
	private OfficeService officeService;
	@Autowired
	private QrCodeService qrcodeService;
	/**
	 * 用户信息显示及保存
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "ercode")
	public String ercode(String flag,HttpServletResponse response, Model model) {

		if (Global.isDemoMode()) {
			model.addAttribute("message", "演示模式，不允许操作！");
		} else {
			Office hospital = officeService.get(UserUtils.getUser().getCompany());
			if (hospital == null) {
				model.addAttribute("message", "系统异常");
			}else if(StringUtils.isNotBlank(flag)&&flag.equals(Global.TRUE)){
				String code=qrcodeService.getminiqrQr(hospital.getId());
				officeService.updateErcode(code,hospital.getId());
				hospital.setErcode(code);
				model.addAttribute("message", "生成二维码成功");			
			}			

			model.addAttribute("office", hospital);
		}
		return "modules/sys/ercode";
	}

}
