package com.sdf.manager.user.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdf.manager.common.bean.ResultBean;
import com.sdf.manager.common.exception.BizException;
import com.sdf.manager.user.bean.AccountBean;
import com.sdf.manager.user.entity.User;
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
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody ResultBean saveOrUpdate(
			AccountBean accountBean,
			ModelMap model)   {
			ResultBean resultBean = new ResultBean();
			try{
				userService.saveOrUpdate(accountBean);
				resultBean.setMessage("操作成功!");
				resultBean.setStatus("success");
			}catch(BizException bizEx){
				resultBean.setMessage(bizEx.getMessage());
				resultBean.setStatus("failure");
			}
			catch (Exception e) {
				resultBean.setMessage("操作异常!");
				resultBean.setStatus("failure");
			}
			return resultBean;
	}
	
	@RequestMapping(value = "/getUserList", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getUserList(
			@RequestParam(value="page",required=false) int page,
			@RequestParam(value="rows",required=false) int rows,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		Map<String,Object> rtMap = new HashMap<String, Object>(); 
		Pageable pageable = new PageRequest(page-1, rows);
		Object[] params = new Object[]{};
		rtMap = userService.getScrollDataByJpql(User.class,null, params,null , pageable);
		return rtMap;
	}
	@RequestMapping(value = "/getDetailAccount", method = RequestMethod.GET)
	public @ResponseBody AccountBean getDetailAccount(
			@RequestParam(value="id",required=true) String id,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		AccountBean accountBean = new AccountBean();
		User user = userService.getUserById(id);
		accountBean.setId(user.getId());
		accountBean.setCode(user.getCode());
		accountBean.setName(user.getName());
		accountBean.setPassword(user.getPassword());
		accountBean.setTelephone(user.getTelephone());
		accountBean.setStatus(user.getStatus());
		return accountBean;
	}
}
