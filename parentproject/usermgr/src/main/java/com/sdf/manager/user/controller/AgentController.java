package com.sdf.manager.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
import com.sdf.manager.common.util.Constants;
import com.sdf.manager.common.util.DateUtil;
import com.sdf.manager.common.util.LoginUtils;
import com.sdf.manager.common.util.QueryResult;
import com.sdf.manager.product.entity.City;
import com.sdf.manager.product.entity.Province;
import com.sdf.manager.product.entity.Region;
import com.sdf.manager.product.service.CityService;
import com.sdf.manager.product.service.ProvinceService;
import com.sdf.manager.product.service.RegionService;
import com.sdf.manager.user.bean.AccountBean;
import com.sdf.manager.user.dto.AddAgentForm;
import com.sdf.manager.user.dto.AgentListDto;
import com.sdf.manager.user.entity.User;
import com.sdf.manager.user.service.UserService;


/** 
  * @ClassName: AgentController 
  * @Description: 帐号controller
  * @author songj@sdfcp.com
  * @date 2015年10月12日 上午11:19:38 
  *  
  */
@Controller
@RequestMapping("/agent")
public class AgentController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private RegionService regionService;
	
	
    /**
	 * demo登录提交后跳转方法
	 * @param userName
	 * @param password
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody ResultBean saveOrUpdate(
			AddAgentForm addAgentForm,
			ModelMap model,HttpSession httpSession)   {
			ResultBean resultBean = new ResultBean();
			try{
				String userId = LoginUtils.getAuthenticatedUserCode(httpSession);
				userService.saveOrUpdate(addAgentForm,userId);
				resultBean.setMessage("操作成功!");
				resultBean.setStatus("success");
			}catch(BizException bizEx){
				resultBean.setMessage(bizEx.getMessage());
				resultBean.setStatus("failure");
			}
			catch (Exception e) {
				resultBean.setMessage("操作异常!");
				resultBean.setStatus("failure");
				e.printStackTrace();
			}finally{
				return resultBean;
			}
	}
	
	
	@RequestMapping(value = "/getAgentList", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getAgentList(
			@RequestParam(value="page",required=false) int page,
			@RequestParam(value="rows",required=false) int rows,
			@RequestParam(value="searchFormNumber",required=false) String searchFormNumber,
			@RequestParam(value="searchFormName",required=false) String searchFormName,
			@RequestParam(value="searchFormTelephone",required=false) String searchFormTelephone,
			@RequestParam(value="searchFormProvince",required=false) String searchFormProvince,
			@RequestParam(value="searchFormCity",required=false) String searchFormCity,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		Map<String,Object> returnData = new HashMap<String, Object>();
		Pageable pageable = new PageRequest(page-1, rows);
		//参数
		StringBuffer buffer = new StringBuffer();
		
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("id", "desc");
		List<Object> params = new ArrayList<Object>();
		//只查询未删除数据
		params.add(Constants.IS_NOT_DELETED);//只查询有效的数据
		buffer.append(" a.IS_DELETED = ?").append(params.size());
		if(null != searchFormNumber && !"".equals(searchFormNumber))
		{
			params.add(searchFormNumber);//根据站点号
			buffer.append(" and a.CODE = ?").append(params.size());
		}
		if(null != searchFormProvince && !"".equals(searchFormProvince)&& !Constants.PROVINCE_ALL.equals(searchFormProvince))
		{
			params.add(searchFormProvince);//根据省份查询产品数据
			buffer.append(" and a.province_Code = ?").append(params.size());
		}
		if(null != searchFormCity && !"".equals(searchFormCity)&& !Constants.CITY_ALL.equals(searchFormCity))
		{
			params.add(searchFormCity);//根据省份查询产品数据
			buffer.append(" and a.city_Code = ?").append(params.size());
		}
		if(null != searchFormName && !"".equals(searchFormName))
		{
			params.add("%"+searchFormName+"%");//根据产品描述模糊查询产品数据
			buffer.append(" and a.NAME  like ?").append(params.size());
		}
		
		if(null != searchFormTelephone && !"".equals(searchFormTelephone))
		{
			params.add(searchFormTelephone);//根据产品描述模糊查询产品数据
			buffer.append(" and a.TELEPHONE = ?").append(params.size());
		}
		QueryResult<User> stationList = userService.getAgentList(User.class, buffer.toString(), params.toArray(),
				orderBy, pageable);
		List<User> users = stationList.getResultList();
		List<AgentListDto> stationDtos = this.toDtos(users);
		int totalrow = stationList.getTotalCount();
		returnData.put("rows", stationDtos);
		returnData.put("total", totalrow);
		return returnData;
	}
	@RequestMapping(value = "/getAgentDetail", method = RequestMethod.GET)
	public @ResponseBody AddAgentForm getAgentDetail(
			@RequestParam(value="id",required=true) String id,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		AddAgentForm addAgentForm = new AddAgentForm();
		User user = userService.getUserById(id);
		addAgentForm.setId(user.getId());
		addAgentForm.setAddFormAgentCode(user.getCode());
		addAgentForm.setAddFormName(user.getName());
		addAgentForm.setAddFormTelephone(user.getTelephone());
		addAgentForm.setAddFormParentId(user.getParentUid());
		addAgentForm.setAddFormProvince(user.getProvinceCode());
		addAgentForm.setAddFormCity(user.getCityCode());
		addAgentForm.setAddFormRegion(user.getRegionCode());
		addAgentForm.setAddFormAddress(user.getAddress());
		addAgentForm.setPassword(user.getPassword());
		return addAgentForm;
	}
	
	/** 
	  * @Description: 转换返回bean类型
	  * @author songj@sdfcp.com
	  * @date 2015年12月2日 下午2:43:05 
	  * @param stations
	  * @return 
	  */
	private List<AgentListDto> toDtos(List<User> users){
		List<AgentListDto> dtoList = new ArrayList<AgentListDto>();
		for(User user : users){
			AgentListDto agentListDto = new AgentListDto();
			agentListDto = this.toDto(user);
			dtoList.add(agentListDto);
		}
		return dtoList;
	}
	
	
	/** 
	  * @Description: 宁静
	  * @author songj@sdfcp.com
	  * @date 2015年12月2日 下午2:43:38 
	  * @param station
	  * @return 
	  */
	private AgentListDto toDto(	User user){
		AgentListDto agentListDto = new AgentListDto();
		agentListDto.setId(user.getId());
		agentListDto.setName(user.getName());
		agentListDto.setAgentCode(user.getCode());
		agentListDto.setCreater(user.getCreater());
		agentListDto.setAddress(user.getAddress());
		agentListDto.setTelephone(user.getTelephone());
		if(!StringUtils.isEmpty(user.getParentUid())){
			try {
				String parentName = userService.getUserById(user.getParentUid()).getName();
				agentListDto.setParentName(parentName);
			} catch (BizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//处理实体中的特殊转换值
		if(null != user.getCreaterTime())//创建时间
		{
			agentListDto.setCreaterTime(DateUtil.formatDate(user.getCreaterTime(), DateUtil.FULL_DATE_FORMAT));
		}
		if(null != user.getProvinceCode())//省级区域
		{
			Province province = new Province();
			province = provinceService.getProvinceByPcode(user.getProvinceCode());
			agentListDto.setProvince(null != province?province.getPname():"");
		}
		if(null != user.getCityCode())//市级区域
		{
			if(Constants.CITY_ALL.equals(user.getCityCode()))
			{
				agentListDto.setCity(Constants.CITY_ALL_NAME);
			}
			else
			{
				City city = new City();
				city = cityService.getCityByCcode(user.getCityCode());
				agentListDto.setCity(null != city?city.getCname():"");
			}
		}
		if(null != user.getRegionCode())//市级区域
		{
			if(Constants.REGION_ALL.equals(user.getRegionCode()))
			{
				agentListDto.setCity(Constants.REGION_ALL_NAME);
			}
			else
			{
				Region region = new Region();
				region = regionService.getRegionByAcode(user.getRegionCode());
				agentListDto.setRegion(null != region?region.getAname():"");
			}
		}
		return agentListDto;
	}
	/**
	 * 
	 * @Description: TODO(权限输入值校验，用来校验code唯一性和authname唯一性) 
	 * @author bann@sdfcp.com
	 * @date 2015年10月15日 上午10:55:07
	 */
	@RequestMapping(value = "/checkValue", method = RequestMethod.GET)
	public @ResponseBody boolean  checkValue(
			@RequestParam(value="code",required=false) String code,
			ModelMap model,HttpSession httpSession) throws Exception {
		User user = userService.getUserByCode(code);
		if(null == user){
			return false;
		}else{
			return true;
		}
	}
	
	

	/** 
	  * @Description: 帐号删除
	  * @author songj@sdfcp.com
	  * @date 2015年10月22日 下午1:35:59 
	  * @param ids
	  * @param model
	  * @param httpSession
	  * @return
	  * @throws Exception 
	  */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/deleteAgentByIds", method = RequestMethod.POST)
	public @ResponseBody ResultBean deleteAgent(
			@RequestParam(value="ids",required=false) String[] ids,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		ResultBean resultBean = new ResultBean();
		try {
			String userId = LoginUtils.getAuthenticatedUserCode(httpSession);
			userService.deleteAccountByIds(ids,userId);
			resultBean.setStatus("success");
			resultBean.setMessage("删除成功!");
		}catch(BizException bizEx){
			resultBean.setStatus("failure");
			resultBean.setMessage(bizEx.getMessage());
		}catch (Exception e) {
			resultBean.setStatus("failure");
			resultBean.setMessage(e.getMessage());
		}finally{
			return resultBean;
		}
	}
	 	/** 
	 	  * @Description: 根据角色code获取角色下面对应所有人
	 	  * @author songj@sdfcp.com
	 	  * @date 2015年11月27日 上午11:00:04 
	 	  * @param model
	 	  * @param httpSession
	 	  * @return
	 	  * @throws Exception 
	 	  */
	 	@RequestMapping(value = "/getSczyList", method = RequestMethod.POST)
		public @ResponseBody List<AccountBean> getSczyList(
				ModelMap model,HttpSession httpSession) throws Exception
		{
	 			List<AccountBean> sczyList = userService.findAccountsByRoleCode(Constants.ROLE_SCZY_CODE);
	 			return sczyList;
		}
}