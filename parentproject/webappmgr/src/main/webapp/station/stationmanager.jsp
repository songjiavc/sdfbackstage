<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  	<head>
    
    <title>测试页面</title>
	  
	<jsp:include page="../common/top.jsp" flush="true" /> 
    <script type="text/javascript" src="<%=request.getContextPath() %>/station/js/stationManager.js"></script>  
    <script type="text/javascript">
	    var toolbar = [{
	  	    text:'添加',
	  	    iconCls:'icon-add',
	  	    handler:function(){
	  	    	 //初始化上级权限下拉框值
	  	    	initProvince('add','addFormProvince','');//默认选中全部，则全部下是没有市数据的
	  	    	$('.panel-title.panel-with-icon').html('添加站点');
	  	    	$('#addOrUpdateStationForm').form('clear');
	  	    	$("#addOrUpdateStation").dialog('open');
	  	    }
	  	},{
	  	    text:'删除',
	  	    iconCls:'icon-remove',
	  	    handler:function(){
	  	    	deleteStationByIds();
	  	    }
	  	}];
	</script>
	 <style type="text/css" scoped="scoped">
	  		.ftitle{
	  			width:100%;
	  			margin-bottom: 20px;
	  			font-family:'微软雅黑',
	  		}
	  		.ftitle label{
	  			margin-left: 30px;
	  		}
	  		
	  		.ftitle .textbox{
	  			margin-right: 50px;
	  			float : right;
	  			width: 200px;
	  		}
	  		
	  	</style>
</head>
<body class="easyui-layout">
	<div  data-options="region:'north'" style="height:100px;">
		<form id="searchForm" method="post" >
	        <table style="border: none;height: 70px;margin:10 10 10 100">
		    	<tr style="margin:10;">
		    		<td colspan="1" class="td_font">站点号：</td>
		    		<td colspan="2">
		    			<input id="searchFormNumber" class="input_border"  type="text" name="searchFormNumber"  />  
		    		</td>
		    		<td colspan="1" class="td_font">站点类型：</td>
		    		<td colspan="2">
		    			<select class="easyui-combobox" id="searchFormStyle" name="searchFormStyle" style="width:200px;">
							<option value="">全部</option>
							<option value="1">体彩</option>
							<option value="2">福彩</option>
						</select>
		    		</td>
		    		<td colspan="1"  class="td_font">站点地域：</td>
		    		<td colspan="2">
		    			 <label for="searchFormProvince">省:</label>
		    			 <select class="easyui-combobox " id="searchFormProvince" name="searchFormProvince" style="width:150px;" >
						 </select>
						 <label for="searchFormCity">市:</label>
								<select class="easyui-combobox " id="searchFormCity" name="searchFormCity"  style="width:150px;" >
						</select>
		    		</td>
		    		</tr>
		    		<tr>
		    		<td colspan="1" class="td_font">站主姓名：</td>
		    		<td colspan="2">
		    			<input id="searchFormName" class="input_border"  type="text" name="searchFormName"  />  
		    		</td>
		    		<td colspan="1" class="td_font">站主电话：</td>
		    		<td colspan="2">
		    			<input id="searchFormTelephone" class="input_border"  type="text" name="searchFormTelephone"  />  
		    		</td>
		    		<td colspan="1" class="td_font">所属代理：</td>
		    		<td colspan="1">
		    			<input id="searchFormAgent" class="input_border"  type="text" name="searchFormAgent"  />  
		    		</td>
		    		<td class="td_font" colspan="2">
		    			<input style="cursor:pointer;background-color: #e0ecff;border-radius:5px;float:left;width:80;" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  type="button" value="查询" onclick="initDatagrid()">
		    			<input style="cursor:pointer;background-color: #e0ecff;border-radius:5px;float:left;margin-left:5px;width:80;" class="easyui-linkbutton" data-options="iconCls:'icon-search'" type="button" value="重置" onclick="reset()">
		    		</td>
		    	</tr>
	    	</table>	
	    	</form>
	</div>
	<div data-options="region:'center'" style="height:100px;">
		<table id="stationDataGrid" class="easyui-datagrid" title="站点列表"  data-options="toolbar:toolbar" >
		</table>
	</div>
	    <!-- 添加站点 -->
  	<div id="addOrUpdateStation" class="easyui-dialog" title="站点编辑" style="width:480px;height:530px;padding:20px;"
            data-options="
                iconCls: 'icon-save',
                buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitAddStation();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#addOrUpdateStation').dialog('close');
                    }
                }]
            ">
		<form id="addOrUpdateStationForm" method="post" >
	        <input type="hidden" name="id" />
	        <div class="ftitle">
	            <label for="addFormStationCode">站点编码:</label>
	            <input class="easyui-validatebox textbox" type="text" name="addFormStationCode"  data-options="required:true,validType:['englishOrNum','length[0,20]',]"" 
	              missingMessage="站点编码不可以为空" invalidMessage="站点编码长度不可以超过20个字符"></input>
	        </div>
	        <div class="ftitle">
	            <label for="addFormStationStyle">站点类型:</label>
	            <select class="easyui-combobox" id="addFormStationStyle" name="addFormStationStyle" style="width:200px;">
					<option value="1">体彩</option>
					<option value="2">福彩</option>
				</select>
	        </div>
	        <div class="ftitle">
	            <label for="addFormStationNumber">站点号:</label>
	            <input class="easyui-validatebox textbox" type="text" name="addFormStationNumber"  data-options="required:true,validType:['englishOrNum','length[0,20]']""
	              missingMessage="站点号不可以为空" invalidMessage="站点号长度不可以超过20个字符"></input>
	        </div>
	        
	        <div class="ftitle">
	            <label for="addFormProvince">省:</label>
	            <input id="addFormProvince" name="addFormProvince" class="easyui-combobox textbox"  />
	        </div>
	        <div class="ftitle">
	        	<label for="addFormCity">市:</label>
	        	<input id="addFormCity" name="addFormCity" class="easyui-combobox textbox"   />
	        </div>
	        <div class="ftitle">
	        	<label for="addFormRegion">区:</label>
	        	<input id="addFormRegion" name="addFormRegion" class="easyui-combobox textbox"   />
	        </div>
	        <div class="ftitle">
	            <label for="addFormAddress" >详细地址:</label>
	            <input class="easyui-validatebox textbox" type="text" name="addFormAddress"  data-options="multiline:true,required:true,validType:['length[0,40]']"  missingMessage="站点详细地址"></input>
	        </div>
	        <div class="ftitle">
	            <label for="addFormName" >站主姓名:</label>
	            <input class="easyui-validatebox textbox" type="text" name="addFormName"  data-options="required:true,validType:['CHS','length[0,20]']"  missingMessage="站主真实姓名"></input>
	        </div>
	        <div class="ftitle">
	        	<label for="addFormTelephone" >站主手机号:</label>
	            <input class="easyui-validatebox textbox" type="text" name="addFormTelephone"  data-options="required:true,validType:['mobile']" missingMessage="站主手机号"></input>
	        </div>
	        <div class="ftitle">
	            <label  for="password">密码:</label>
	            <input class="easyui-validatebox textbox" type="password" id="password" name="password" data-options="required:true"
	            validType="length[6,15]"   ></input>
	        </div>
	        <div class="ftitle">
	            <label for="confirmPassword" >确认密码:</label>
	            <input class="easyui-validatebox textbox" type="password" name ="confirmPassword"  data-options="required:true" validType="equalTo['#password']" invalidMessage="两次输入密码不匹配"></input>
	        </div>
          </form>
     </div> 
     <!-- 添加权限弹框 -->
  	 <div id="setOrder" class="easyui-dialog" title="购买商品" style="width:480px;height:450px;padding:20px;" ></div>
</body>
</html>