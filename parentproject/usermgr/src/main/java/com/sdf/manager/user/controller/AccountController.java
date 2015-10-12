package com.sdf.manager.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** 
  * @ClassName: MenuController 
  * @Description: 目录相关控制层
  * @author songj@sdfcp.com
  * @date 2015年9月23日 下午5:21:54 
  *  
  */
@Controller
@RequestMapping("/account")
public class AccountController {
	
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
			@RequestParam(value="code",required=false) String userName,
			@RequestParam(value="password",required=false) String password,
			@RequestParam(value="status",required=false) String status,
			ModelMap model) throws Exception {


		model.addAttribute("menuId", "1");
		return "user/test";
	}
}
