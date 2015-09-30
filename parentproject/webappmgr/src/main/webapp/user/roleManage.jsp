<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>角色管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link href="<%=request.getContextPath() %>/css/ztree/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <jsp:include page="../common/top.jsp" flush="true" /> 
    <script src="<%=request.getContextPath() %>/user/js/roleManage.js" type="text/javascript"></script>
    
    <script type="text/javascript">
  	var toolbar = [{
  	    text:'添加',
  	    iconCls:'icon-add',
  	    handler:function(){
  	    	$("#addRole").dialog('open');
  	    	
  	    }
  	},{
  	    text:'删除',
  	    iconCls:'icon-remove',
  	    handler:function(){alert('cut')}
  	}];
  	  
  	
		
	</script>
		<style type="text/css">
			.ztree li button.switch {visibility:hidden; width:1px;}
			.ztree li button.switch.roots_docu {visibility:visible; width:16px;}
			.ztree li button.switch.center_docu {visibility:visible; width:16px;}
			.ztree li button.switch.bottom_docu {visibility:visible; width:16px;}
			
			.formbox{
				margin-top:10px;
				text-align:center;
			}
		</style>
		
	 
</head>
<body>
  <!-- 带模糊查询的布局 
   <div id="cc" class="easyui-layout" style="width:800px;height:auto;" style="float:left;margin-left:10px;">
        <div region="north" title="查询"  style="height:100px;">
        	
        </div>
        <div style="margin-top:110px;">
	    	 <table id="datagrid"  title="角色列表"   style="width:800px;height:auto;"
				data-options="rownumbers:true,singleSelect:false,pagination:true,
				collapsible:false,toolbar:toolbar">
				</table>
        </div>
	</div> -->
    <div style="float:left;margin-left:10px;" >
    	<div style="margin-top:0px;">
    	 <table id="datagrid"  title="角色列表" style="width:800px;height:400px;"
			data-options="rownumbers:true,singleSelect:false,pagination:true,
			collapsible:false,toolbar:toolbar">
			</table>
    	</div>
 	</div>  
  
  
  <!-- 权限设置弹框 -->
  <div id="w" class="easyui-dialog" title="权限设置" style="width:400px;height:300px;padding:10px"
            data-options="
                iconCls: 'icon-save',
                buttons: [{
                    text:'确认',
                    iconCls:'icon-ok',
                    handler:function(){
                        alert('ok');
                    }
                },{
                    text:'取消',
                    iconCls:'icon-ok',
                    handler:function(){
                        $('#w').dialog('close');
                    }
                }]
            ">
        <div class="zTreeDemoBackground left" style="float:left;width: 200px;">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
    </div>
    
    <!-- 添加角色弹框 -->
  <div id="addRole" class="easyui-dialog" title="添加角色" style="width:400px;height:300px;padding:10px"
            data-options="
                iconCls: 'icon-save',
                buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitAddrole();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-ok',
                    handler:function(){
                        $('#addRole').dialog('close');
                    }
                }]
            ">
		<form id="ff" method="get" novalidate>
	        <div class="formbox">
	            <label for="name">角色编码:</label>
	            <input class="easyui-validatebox" type="text" name="name" data-options="required:true"
	             validType="length[1,20]" missingMessage="角色编码不可以为空" invalidMessage="角色编码长度不可以超过20个字符"></input>
	        </div>
	        <div class="formbox">
	            <label for="email">角色名称:</label>
	            <input class="easyui-validatebox" type="text" name="email" data-options="required:true"
	             validType="length[1,20]" missingMessage="角色名称不可以为空" invalidMessage="角色名称长度不可以超过20个字符"></input>
	        </div>
	        <div class="formbox">
	            <label for="subject">角色上级:</label>
	            <input class="easyui-validatebox" type="text" name="subject" data-options="required:true"></input>
	        </div>
	         <div class="formbox">
	            <label for="isVisible">是否启用:</label>
	            <input class="easyui-validatebox" type="radio" name="isVisible" checked >是</input>
	            <input class="easyui-validatebox" type="radio" name="isVisible">否</input>
	        </div>
	      </form>
    </div>
     
</body>
	
 <%-- <script src="<%=request.getContextPath() %>/js/ztree/jquery-1.4.4.min.js" type="text/javascript"></script> --%>
    <script src="<%=request.getContextPath() %>/js/ztree/jquery.ztree.core-3.5.js" type="text/javascript"></script>	
    <script src="<%=request.getContextPath() %>/js/ztree/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>	
	
</html>