package com.sdf.manager.station.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdf.manager.common.bean.ResultBean;
import com.sdf.manager.common.exception.BizException;
import com.sdf.manager.common.util.Constants;
import com.sdf.manager.common.util.QueryResult;
import com.sdf.manager.product.entity.Product;
import com.sdf.manager.station.application.dto.StationDto;
import com.sdf.manager.station.bean.StationBean;
import com.sdf.manager.station.entity.Station;
import com.sdf.manager.station.service.StationService;


/** 
  * @ClassName: AccountController 
  * @Description: 帐号controller
  * @author songj@sdfcp.com
  * @date 2015年10月12日 上午11:19:38 
  *  
  */
@Controller
@RequestMapping("/station")
public class StationController {
	
	@Autowired
	private StationService stationService;
	
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
			StationBean stationBean,
			ModelMap model)   {
			ResultBean resultBean = new ResultBean();
			try{
				stationService.saveOrUpdate(stationBean);
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
	
	@RequestMapping(value = "/getStationList", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getUserList(
			@RequestParam(value="page",required=false) int page,
			@RequestParam(value="rows",required=false) int rows,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		Map<String,Object> returnData = new HashMap<String, Object>(); 
		Pageable pageable = new PageRequest(page-1, rows);
		//参数
		StringBuffer buffer = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("id", "desc");
		//只查询未删除数据
		params.add(Constants.IS_NOT_DELETED);//只查询有效的数据
		buffer.append(" isDeleted = ?").append(params.size());
		QueryResult<Station> stationList = stationService.getStationList(Station.class, buffer.toString(), params.toArray(),
				orderBy, pageable);
		List<Station> stations = stationList.getResultList();
		List<StationDto> stationDtos = this.to
		Long totalrow = stationList.getTotalRecord();
		returnData.put("rows", productDtos);
		returnData.put("total", totalrow);
		return returnData;
	}
	@RequestMapping(value = "/getDetailAccount", method = RequestMethod.GET)
	public @ResponseBody StationBean getDetailAccount(
			@RequestParam(value="id",required=true) String id,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		StationBean accountBean = new StationBean();
		Station user = stationService.getSationById(id);
		accountBean.setId(user.getId());
		accountBean.setCode(user.getCode());
		accountBean.setName(user.getName());
		accountBean.setPassword(user.getPassword());
		accountBean.setTelephone(user.getTelephone());
		accountBean.setStatus(user.getStatus());
		return accountBean;
	}
	
	
	/**
	  * 
	 * @Description: 校验产品编码唯一性
	 * @author bann@sdfcp.com
	 * @date 2015年11月4日 下午2:58:17
	  */
	 @RequestMapping(value = "/checkCode", method = RequestMethod.POST)
	public @ResponseBody ResultBean  checkCode(
				@RequestParam(value="id",required=false) String id,
				@RequestParam(value="code",required=false) String code,
				ModelMap model,HttpSession httpSession) throws Exception {
		 
		 	ResultBean resultBean = new ResultBean ();
			
			//放置分页参数
			Pageable pageable = new PageRequest(0,10000);
			
			//参数
			StringBuffer buffer = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			
			//只查询未删除数据
			params.add("1");//只查询有效的数据
			buffer.append(" isDeleted = ?").append(params.size());
			
			if(null != code && !"".equals(code))
			{
				params.add(code);
				buffer.append(" and code = ?").append(params.size());
			}
			
			
			if(null != id && !"".equals(id))
			{//校验修改中的值的唯一性
				params.add(id);
				buffer.append(" and id != ?").append(params.size());
			}
			
			//排序
			LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
			
			QueryResult<Station> plist = stationService.getStationList(Product.class, buffer.toString(), params.toArray(),
					orderBy, pageable);
			
			if(plist.getResultList().size()>0)
			{
				resultBean.setExist(true);//若查询的数据条数大于0，则当前输入值已存在，不符合唯一性校验
			}
			else
			{
				resultBean.setExist(false);
			}
			
			return resultBean;
			
		}
	
	

	@SuppressWarnings("finally")
	@RequestMapping(value = "/deleteAccountByIds", method = RequestMethod.POST)
	public @ResponseBody ResultBean deleteAccount(
			@RequestParam(value="ids",required=false) String[] ids,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		ResultBean resultBean = new ResultBean();
		try {
			stationService.deleteStationByIds(ids);
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
	
	
	private List<StationDto> toDtos(List<Station> stations){
		
	}
	
	private StationDto toDto(Station station){
		StationDto stationDto = new StationDto();
		stationDto.setId(station.getId());
		stationDto.setStationCode(station.getCode());
		stationDto.setStationNumber(station.getStationCode());
		stationDto.setProvince(station.getProvinceCode());
		a
	}
}
