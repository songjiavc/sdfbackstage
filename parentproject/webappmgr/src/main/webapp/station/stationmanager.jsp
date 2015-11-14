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
	  	    	$("#addStation").dialog('open');
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
		    			<input id="productNameC" class="input_border"  type="text" name="productNameC"  />  
		    		</td>
		    		<td colspan="1" class="td_font">站点类型：</td>
		    		<td colspan="2">
		    			<select class="easyui-combobox" name="searchFormStationStyle" style="width:200px;">
							<option value="1">体彩</option>
							<option value="2">福彩</option>
						</select>
		    		</td>
		    		<td colspan="1"  class="td_font">产品地域：</td>
		    		<td colspan="2">
		    			 <label for="privinceC">省:</label>
		    			 <select class="easyui-combobox " id="searchFormProvince" name="searchFormProvince" style="width:150px;" >
						</select>
						 <label for="cityC">市:</label>
						<select class="easyui-combobox " id="searchFormCity" name="searchFormCity"  style="width:150px;" >
						</select>
		    		</td>
		    		</tr>
		    		<tr>
		    		<td colspan="1" class="td_font">站长姓名：</td>
		    		<td colspan="2">
		    			<input id="productNameC" class="input_border"  type="text" name="searchStationName"  />  
		    		</td>
		    		<td colspan="1" class="td_font">站长电话：</td>
		    		<td colspan="2">
		    			<input id="productNameC" class="input_border"  type="text" name="searchStationTelephone"  />  
		    		</td>
		    		<td colspan="1" class="td_font">所属代理：</td>
		    		<td colspan="1">
		    			<input id="agent" class="input_border"  type="text" name="agent"  />  
		    		</td>
		    		<td class="td_font" colspan="2">
		    			<input style="cursor:pointer;background-color: #e0ecff;border-radius:5px;float:left;width:80;" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  type="button" value="查询" onclick="initDatagrid()">
		    			<input style="cursor:pointer;background-color: #e0ecff;border-radius:5px;float:left;margin-left:5px;width:80;" class="easyui-linkbutton" data-options="iconCls:'icon-search'" type="button" value="重置" onclick="reset()">
		    		</td>
		    	</tr>
	    	</table>	
	</div>
	<div data-options="region:'center'" style="height:100px;">
		<table id="stationDataGrid" class="easyui-datagrid" title="站点列表"  data-options="toolbar:toolbar" >
		</table>
	</div>
	    <!-- 添加权限弹框 -->
  	<div id="addStation" class="easyui-dialog" title="添加站点" style="width:480px;height:450px;padding:20px;"
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
                        $('#addStation').dialog('close');
                    }
                }]
            ">
		<form id="addOrUpdateStationForm" method="post" >
	        <div class="ftitle">
	            <label for="addFormStationCode">站点编码:</label>
	            <input class="easyui-validatebox textbox" type="text" name="addFormStationCode"  data-options="required:true,validType:['englishOrNum','length[0,20]',]"" 
	              missingMessage="站点编码不可以为空" invalidMessage="站点编码长度不可以超过20个字符"></input>
	        </div>
	        <div class="ftitle">d
	            <label for="addFormStationStyle">站点类型:</label>
	            <select class="easyui-combobox" name="addFormStationStyle" style="width:200px;">
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
	            <input id="addFormProvince" class="easyui-combobox textbox"  />
	        </div>
	        <div class="ftitle">
	        	<label for="addFormCity">市:</label>
	        	<input id="addFormCity" class="easyui-combobox textbox"   />
	        </div>
	        <div class="ftitle">
	            <label for="addFormName" >站主姓名:</label>
	            <input class="easyui-validatebox textbox" type="text" name="addFormName"  data-options="required:true,validType:['CHS','length[0,20]']"  missingMessage="站主真实姓名"></input>
	        </div>
	        <div class="ftitle">
	        	<label for="addFormTelephone" >站主手机号:</label>
	            <input class="easyui-validatebox textbox" type="text" name="addFormTelephone"  data-options="required:true,validType:['mobile']" validType="" missingMessage="站主手机号"></input>
	        </div>
	        <div class="ftitle">
	            <label  for="password">密码:</label>
	            <input class="easyui-validatebox textbox" type="password" id="password" name="password" data-options="required:true"
	            validType="length[1,20]"   missingMessage="密码不可以为空" ></input>
	        </div>
	        <div class="ftitle">
	            <label for="confirmPassword" >确认密码:</label>
	            <input class="easyui-validatebox textbox" type="password" name ="confirmPassword"  data-options="required:true" validType="equalTo['#password']" invalidMessage="两次输入密码不匹配"></input>
	        </div>
          </form>
     </div>  
</body>
</html>