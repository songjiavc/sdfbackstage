package com.sdf.manager.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdf.manager.user.MenuBean;
import com.sdf.manager.user.service.AuthService;

/** 
  * @ClassName: MenuController 
  * @Description: 目录相关控制层
  * @author songj@sdfcp.com
  * @date 2015年9月23日 下午5:21:54 
  *  
  */
@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
	private AuthService authService;
	
    /**
	 * demo登录提交后跳转方法
	 * @param userName
	 * @param password
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getNewPage", method = RequestMethod.POST)
	public String getNewPage(
			@RequestParam(value="userName",required=false) String userName,
			@RequestParam(value="password",required=false) String password,
			ModelMap model) throws Exception {


		model.addAttribute("menuId", "1");
		return "user/test";
	}
    
    
	/**
	 * 
	* @Title: save 
	* @author banna    
	* @date 2015年9月22日 上午10:11:56  
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getMenuData", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getMenuData(ModelMap model,HttpSession httpSession) throws Exception {
		Map<String,Object> child = new HashMap<String,Object> ();
		List<MenuBean> menubeans = new ArrayList<MenuBean> ();
		List<MenuBean> menus = new ArrayList<MenuBean> ();
		List<MenuBean> menus2 = new ArrayList<MenuBean> ();
		child.put("basic", menubeans);
		
		MenuBean mb = new MenuBean();
		mb.setMenuid("10");
		mb.setIcon("icon-sys");
		mb.setMenuname("用户");
		mb.setMenus(menus);
		menubeans.add(mb);
		
		MenuBean mb5 = new MenuBean();
		mb5.setMenuid("112");
		mb5.setIcon("icon-nav");
		mb5.setMenuname("帐号管理");
		mb5.setUrl("/user/useraccount.jsp");
		menus.add(mb5);
		
		MenuBean mb1 = new MenuBean();
		mb1.setMenuid("111");
		mb1.setIcon("icon-nav");
		mb1.setMenuname("站点管理");
		mb1.setUrl("/user/basic.jsp");
		menus.add(mb1);
		
		MenuBean mb2 = new MenuBean();
		mb2.setMenuid("113");
		mb2.setIcon("icon-nav");
		mb2.setMenuname("添加站点");
		mb2.setUrl("#");
		menus.add(mb2);
		
		MenuBean mb3 = new MenuBean();
		mb3.setMenuid("114");
		mb3.setIcon("icon-nav");
		mb3.setMenuname("权限管理");
		mb3.setUrl("/user/authority.jsp");
		menus.add(mb3);
		
		MenuBean mb4 = new MenuBean();
		mb4.setMenuid("115");
		mb4.setIcon("icon-nav");
		mb4.setMenuname("角色管理");
		mb4.setUrl("/user/roleManage.jsp");
		menus.add(mb4);
		
		MenuBean m2b = new MenuBean();
		m2b.setMenuid("20");
		m2b.setIcon("icon-sys");
		m2b.setMenuname("商品");
		m2b.setMenus(menus2);
		menubeans.add(m2b);
		
		MenuBean mb21 = new MenuBean();
		mb21.setMenuid("211");
		mb21.setIcon("icon-nav");
		mb21.setMenuname("商品管理");
		mb21.setUrl("/user/basic.jsp");
		menus2.add(mb21);
		
		MenuBean mb22 = new MenuBean();
		mb22.setMenuid("113");
		mb22.setIcon("icon-nav");
		mb22.setMenuname("购买商品");
		mb22.setUrl("#");
		menus2.add(mb22);
		
		return child;
	}
	
	
	
	
	
	

	
}
