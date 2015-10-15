<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  	<head>
    
    <title>测试页面</title>
	  
	<jsp:include page="../common/top.jsp" flush="true" /> 
    <script type="text/javascript" src="<%=request.getContextPath() %>/user/js/useraccount.js"></script>  
    <script type="text/javascript">
	    var toolbar = [{
	  	    text:'添加',
	  	    iconCls:'icon-add',
	  	    handler:function(){
	  	    	$("#addAccount").dialog('open');
	  	    }
	  	},{
	  	    text:'删除',
	  	    iconCls:'icon-remove',
	  	    handler:function(){alert('cut')}
	  	}];
	</script>
	 <style type="text/css">
	  		.ftitle{
	  			width:100%;
	  			margin-bottom: 20px;
	  			font-family:'微软雅黑',
	  		}
	  		.ftitle label{
	  			margin-left: 30px;
	  		}
	  		.ftitle .commonInput{
	  			margin-right: 50px;
	  			float : right;
	  			width: 200px;
	  			border-radius : 5px;
	  		}
	  	</style>
</head>
<body>
	
	<table id="accountDataGrid" class="easyui-datagrid" title="用户列表" 
			data-options="toolbar:toolbar" />
	    <!-- 添加权限弹框 -->
  	<div id="addAccount" class="easyui-dialog" title="添加用户" style="width:480px;height:350px;padding:10px;"
            data-options="
                iconCls: 'icon-save',
                buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitAddAccount();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#addAccount').dialog('close');
                    }
                }]
            ">
		<form id="ff" method="post" >
			<div>
				<label for="email">Email:</label>
				<input class="easyui-validatebox" type="text" name="email" required="true" validType="email"></input>
			</div>
			<div>
				<label for="subject">Subject:</label>
				<input class="easyui-validatebox" type="text" name="subject" required="true"></input>
			</div>
			<div>
				<label for="message">Message:</label>
				<textarea name="message" style="height:60px;"></textarea>
			</div>
	        <div class="ftitle">
	            <label for="code">权限编码:</label>
	            <input class="easyui-validatebox commonInput" type="text" name="code"  data-options="required:true"
	             validType="length[1,20]" missingMessage="权限编码不可以为空" invalidMessage="权限编码长度不可以超过20个字符"></input>
	        </div>
	        <div class="ftitle">
	            <label for="name" >用户姓名:</label>
	            <input class="easyui-validatebox commonInput" type="text" name="name"  data-options="required:true"  missingMessage="用户真实姓名"></input>
	        </div>
	        <div class="ftitle">
	        	<label for="telephone" >用户电话:</label>
	            <input class="easyui-validatebox commonInput" type="text" name="telephone" missingMessage="用户电话"></input>
	        </div>
	        <div class="ftitle">
	            <label  for="password">密码:</label>
	            <input class="easyui-validatebox commonInput" type="password" name="password" data-options="required:true"
	            validType="length[1,20]"   missingMessage="密码不可以为空" ></input>
	        </div>
	        <div class="ftitle">
	            <label for="confirmPassword" >确认密码:</label>
	            <input class="easyui-validatebox commonInput" type="password" name ="confirmPassword"  data-options="required:true"></input>
	        </div>
          	<div class="ftitle">
	            <label for="status">是否启用:</label>
	            <div style="float:right;margin-right: 50px; ">
		            <input class="easyui-validatebox" type="radio" name="status" checked >是</input>
		            <input class="easyui-validatebox" type="radio" name="status">否</input>
	        	</div>
           </div>
	       </div>
	       </form>
     </div>  
     <!-- 修改权限弹框 -->
    <div id="updateAccount" class="easyui-dialog" title="修改用户" style="width:480px;height:350px;padding:10px;"
            data-options=" 
                iconCls: 'icon-save',
                buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitUpdateAccount();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#updateAccount').dialog('close');
                    }
                }]
            ">
		<form id="updateAccountForm" method="post" novalidate>
	       	<input type="hidden" name="id" />
	      	<div class="ftitle">
	            <label for="code" >用户帐号:</label>
	          	<input class="easyui-validatebox commonInput" type="text" name="code"  readonly="readonly"
	             validType="length[1,20]" missingMessage="权限编码不可以为空" invalidMessage="权限编码长度不可以超过20个字符"></input>
	        </div>
	        <div class="ftitle">
	            <label for="name" >用户姓名:</label>
	            <input class="easyui-validatebox commonInput" type="text" name="name"  data-options="required:true"  missingMessage="用户真实姓名"></input>
	        </div>
	        <div class="ftitle">
	        	<label for="telephone" >用户电话:</label>
	            <input class="easyui-validatebox commonInput" type="text" name="telephone" missingMessage="用户电话"></input>
	        </div>
	        <div class="ftitle">
	            <label  for="password">密码:</label>
	            <input class="easyui-validatebox commonInput" type="password" name="password" data-options="required:true"
	            validType="length[1,20]"   missingMessage="密码不可以为空" ></input>
	        </div>
	        <div class="ftitle">
	            <label for="confirmPassword" >确认密码:</label>
	            <input class="easyui-validatebox commonInput" type="password" name ="confirmPassword"  data-options="required:true"></input>
	        </div>
          	<div class="ftitle">
	            <label for="status">是否启用:</label>
	            <div style="float:right;margin-right: 50px; ">
		            <input class="easyui-validatebox" type="radio" name="status" checked >是</input>
		            <input class="easyui-validatebox" type="radio" name="status">否</input>
	        	</div>
           </div>
	       </div>
	    </form>
	</div> 
</body>
</html>