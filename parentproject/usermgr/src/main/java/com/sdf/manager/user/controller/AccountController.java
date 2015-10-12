package com.sdf.manager.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sdf.manager.user.bean.AccountBean;
import com.sdf.manager.user.service.UserService;


/** 
  * @ClassName: AccountController 
  * @Description: 帐号controller
  * @author songj@sdfcp.com
  * @date 2015年10月12日 上午11:19:38 
  *  
  */
@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private UserService userService;
	
    /**
	 * demo登录提交后跳转方法
	 * @param userName
	 * @param password
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewAccount")
	public String addNewAccount(
			AccountBean accountBean,
			ModelMap model) throws Exception {

		
		model.addAttribute("menuId", "1");
		return "user/test";
	}
}
