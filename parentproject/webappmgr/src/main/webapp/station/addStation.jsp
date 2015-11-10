<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  	<head>
    
    <title>添加站点</title>
	  
	<jsp:include page="../common/top.jsp" flush="true" />
    <script type="text/javascript" src="<%=request.getContextPath() %>/station/js/addStation.js"></script>
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
	<div  data-options="region:'north',title:'North Title',split:true" style="height:100px;">
	</div>
	<div data-options="region:'center'" style="height:100px;">
		<form id="addStationForm" method="post" >
	        <div class="ftitle">
	            <label for="code">站点编码:</label>
	            <input class="easyui-validatebox textbox" type="text" name="code"  data-options="required:true,validType:['englishOrNum','length[0,20]']""
	              missingMessage="站点编码不可以为空" invalidMessage="站点编码长度不可以超过20个字符"></input>
	        </div>
	        <div class="ftitle ">
	            <label for="name" >站点号:</label>
	            <input class="easyui-validatebox textbox" type="text" name="name"  data-options="required:true,validType:['CHS','length[0,20]']"  missingMessage="站点号不能为空！"></input>
	        </div>
	        <div class="ftitle easyui-layout">
        		<div data-options="region:'east'">
        			<input id="province" class="easyui-combobox" data-options="
				        valueField: 'id',
				        textField: 'text',
				        url: 'get_data1.php',
				        onSelect: function(rec){
				            var url = 'get_data2.php?id='+rec.id;
				            $('#city').combobox('reload', url);
				        }"></input>
        		<div data-options="region:'center'">
        			<input id="city" class="easyui-combobox"  data-options="
				        valueField: 'id',
				        textField: 'text',
				        url: 'get_data1.php',
				        onSelect: function(rec){
				            var url = 'get_data2.php?id='+rec.id;
				            $('#region').combobox('reload', url);
				       	}" ></div>
        		</div>
        		<div data-options="region:'west'">
        			<input id="region" class="easyui-combobox" data-options="valueField:'id',textField:'text'"></input>
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
	       	<div class="ftitle">
	        	<label for="telephone" >站主姓名:</label>
	            <input class="easyui-validatebox textbox" type="text" name="name"  data-options="required:true,validType:['CHS','length[0,20]']"  missingMessage="站主真实姓名"></input>
	        </div>
	        <div class="ftitle">
	        	<label for="telephone" >站主电话:</label>
	            <input class="easyui-validatebox textbox" type="text" name="telephone" validType="" missingMessage="站主电话"></input>
	        </div>
          </form>
	</div>
</body>
</html>