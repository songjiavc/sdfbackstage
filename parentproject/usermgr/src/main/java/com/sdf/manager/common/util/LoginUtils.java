package com.sdf.manager.common.util;

import javax.servlet.http.HttpSession;

import com.sdf.manager.user.bean.AccountBean;

public class LoginUtils {

	 /**
	  * 
	 * @Description: TODO(获取当前登录人员的usercode) 
	 * @author bann@sdfcp.com
	 * @date 2015年10月27日 上午9:01:52
	  */
	 public static String getAuthenticatedUserCode(HttpSession session){
		 String userId = null; 
		
		 AccountBean userBean = (AccountBean)session.getAttribute("userBean");
		 
		 userId = userBean.getCode();
		 
		 return userId;
	 }
	 
	 /**
	  * 
	 * @Description: TODO(将登录信息放置到session中) 
	 * @author bann@sdfcp.com
	 * @date 2015年10月27日 上午9:16:03
	  */
	 public static void setLoginUserMessage(HttpSession session,
			 String userCode,String password,String name,String userId)
	 {
		AccountBean userBean = new AccountBean();
		userBean.setCode(userCode);
		userBean.setPassword(password);
		userBean.setName(name);
		userBean.setId(userId);
		
		//将session登录信息放置到session 
		session.setAttribute("userBean", userBean);
		
		
	 }
	 
	 /**
	  * 
	 * @Description: TODO(获取session登录的姓名信息) 
	 * @author bann@sdfcp.com
	 * @date 2015年10月27日 上午9:18:34
	  */
	 public static String getAuthenticatedUserName(HttpSession session){
		 String name = null; 
		
		 AccountBean userBean = (AccountBean)session.getAttribute("userBean");
		 
		 name = userBean.getName();
		 
		 return name;
	 }
	 
	 /**
	  * 
	 * @Description: TODO(获取session登录的姓名id) 
	 * @author bann@sdfcp.com
	 * @date 2015年10月27日 上午9:18:34
	  */
	 public static String getAuthenticatedUserId(HttpSession session){
		 String userId = null; 
		
		 AccountBean userBean = (AccountBean)session.getAttribute("userBean");
		 
		 userId = userBean.getId();
		 
		 return userId;
	 }
	 
}
