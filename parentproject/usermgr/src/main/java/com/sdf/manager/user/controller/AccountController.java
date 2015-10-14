package com.sdf.manager.user.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.sdf.manager.common.util.QueryResult;
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
				User user;
				user = userService.getUserByCode(accountBean.getCode());//判断当前code所属的auth是否已存在，若存在则进行修改操作
				if(StringUtils.isEmpty(user)){
					user = new User();
					user.setCode(accountBean.getCode());
					user.setName(accountBean.getName());
					user.setPassword(accountBean.getPassword());
					userService.save(user);
					resultBean.setMessage("新增权限成功!");
					resultBean.setStatus("success");
				}else{
					user.setCode(accountBean.getCode());
					user.setName(accountBean.getName());
					user.setPassword(accountBean.getPassword());
					userService.save(user);
					resultBean.setMessage("修改权限成功!");
					resultBean.setStatus("success");
				}
			}catch (Exception e) {
				resultBean.setMessage("操作失败!");
				resultBean.setStatus("failure");
			}
			return resultBean;
	}
	
	@RequestMapping(value = "/getUserList", method = RequestMethod.GET)
	public @ResponseBody QueryResult<User> getUserList(
			@RequestParam(value="page",required=false) int page,
			@RequestParam(value="rows",required=false) int rows,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		Pageable pageable = new PageRequest(page, rows);
		Object[] params = new Object[]{};
		QueryResult<User> queryObj = userService.getScrollDataByJpql("1=1", params,null , pageable);
		List<AccountBean> accountList = new ArrayList<AccountBean>();
		List<User> userList = queryObj.getResultList();
		for(User user : userList){
			AccountBean accountBean = new AccountBean();
			accountBean.setCode(user.getCode());
			accountBean.setName(user.getName());
			accountBean.setPage(page);
			accountBean.setRows(rows);
		}
		return queryObj;
	}

}
