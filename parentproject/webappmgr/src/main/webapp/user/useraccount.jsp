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
			data-options="singleSelect:true,collapsible:true,url:'',method:'get',toolbar:toolbar,fit:true,fitColumns:true" />
	    <!-- 添加权限弹框 -->
  <div id="addAccount" class="easyui-dialog" title="添加用户" style="width:450px;height:300px;padding:10px;"
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
		<form id="accountForm" method="post" novalidate >
	        <div class="ftitle">
	            <label for="name" >用户帐号:</label>
	             <input class="easyui-validatebox commonInput" type="text" name="code" validType="" missingMessage="用户登录系统使用"></input>
	        </div>
	        <div class="ftitle">
	            <label  >密码:</label>
	            <input class="easyui-validatebox commonInput" type="password" name="password" data-options="required:true"
	             validType="" missingMessage="密码不可以为空" invalidMessage="权限名称长度不可以超过20个字符"></input>
	        </div>
	          <div class="ftitle">
	            <label for="authurl" >确认密码</label>
	            <input class="easyui-validatebox commonInput" type="text" name="confirmPassword"  missingMessage="密码不可以为空" data-options="required:true"></input>
	        </div>
          	<div class="ftitle">
	            <label for="isVisible">是否启用:</label>
	            <div style="float:right;margin-right: 50px; ">
	            <input class="easyui-validatebox" type="radio" name="status" checked >是</input>
	            <input class="easyui-validatebox" type="radio" name="status">否</input>
	        	</div>
           </div>
	       </div>
	       </form>
     </div>  
    
	 </div>
</body>
</html>