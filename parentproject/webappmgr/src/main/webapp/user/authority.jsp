<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>权限管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link href="<%=request.getContextPath() %>/css/ztree/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <jsp:include page="../common/top.jsp" flush="true" /> 
    <script src="<%=request.getContextPath() %>/js/ztree/jquery.ztree.core-3.5.js" type="text/javascript"></script>
    
    <script src="<%=request.getContextPath() %>/user/js/authority.js" type="text/javascript"></script>
    
    <script type="text/javascript">
  	 
  	  
  	var toolbar = [{
  	    text:'添加',
  	    iconCls:'icon-add',
  	    handler:function(){
  	    	$("#addAuth").dialog('open');
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
    <div class="zTreeDemoBackground left" style="float:left;width: 200px;">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
    <div style="float:left;margin-left:10px;" >
    	<div style="margin-top:0px;">
    	 <table id="datagrid"  title="权限列表" style="width:800px;height:400px;"
			data-options="rownumbers:true,singleSelect:false,pagination:true,
			collapsible:false,toolbar:toolbar">
		 </table>
    	</div>
     </div>
     
       <!-- 添加权限弹框 -->
  <div id="addAuth" class="easyui-dialog" title="添加权限" style="width:400px;height:300px;padding:10px"
            data-options="
                iconCls: 'icon-save',
                buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitAddauth();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-ok',
                    handler:function(){
                        $('#addAuth').dialog('close');
                    }
                }]
            ">
		<form id="ff" method="get" novalidate>
	        <div class="formbox">
	            <label for="name">权限编码:</label>
	            <input class="easyui-validatebox" type="text" name="name" data-options="required:true"
	             validType="length[1,20]" missingMessage="权限编码不可以为空" invalidMessage="权限编码长度不可以超过20个字符"></input>
	        </div>
	        <div class="formbox">
	            <label for="email">权限名称:</label>
	            <input class="easyui-validatebox" type="text" name="email" data-options="required:true"
	             validType="length[1,20]" missingMessage="权限名称不可以为空" invalidMessage="权限名称长度不可以超过20个字符"></input>
	        </div>
	        <div class="formbox">
	            <label for="authurl">权限 url:</label>
	            <input class="easyui-validatebox" type="text" name="authurl" data-options="required:true"></input>
	        </div>
	        <div class="formbox">
	            <label for="authimg">权限图片:</label>
	            <input class="easyui-validatebox" type="text" name="authimg" data-options="required:true"></input>
	        </div>
	         <div class="formbox">
	            <label for="isVisible">是否启用:</label>
	            <input class="easyui-validatebox" type="radio" name="isVisible" checked >是</input>
	            <input class="easyui-validatebox" type="radio" name="isVisible">否</input>
	        </div>
	      </form>
    </div>  
	</body>
</html>