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
  	    text:'批量删除',
  	    iconCls:'icon-remove',
  	    handler:function(){
  	    	deleteAuthList();
  	    }
  	}];
  	  
  		
	</script>
		<style type="text/css">
			.ztree li button.switch {visibility:hidden; width:1px;}
			.ztree li button.switch.roots_docu {visibility:visible; width:16px;}
			.ztree li button.switch.center_docu {visibility:visible; width:16px;}
			.ztree li button.switch.bottom_docu {visibility:visible; width:16px;}
			
			<style type="text/css"> 
        #fm 
        { 
            margin: 0; 
            padding: 10px 30px; 
        } 
        .ftitle 
        { 
            font-size: 14px; 
            font-weight: bold; 
            padding: 5px 0; 
            margin-bottom: 10px; 
            border-bottom: 1px solid #ccc; 
        } 
        .fitem 
        { 
            margin-bottom: 5px; 
        } 
        .fitem label 
        { 
            display: inline-block; 
            width: 80px; 
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
	            <label for="code">权限编码:</label>
	            <input class="easyui-validatebox" type="text" name="code"  data-options="required:true"
	             validType="length[1,20]" missingMessage="权限编码不可以为空" invalidMessage="权限编码长度不可以超过20个字符"></input>
	        </div>
	        <div class="formbox">
	            <label for="authName">权限名称:</label>
	            <input class="easyui-validatebox" type="text" name="authName" data-options="required:true"
	             validType="length[1,20]" missingMessage="权限名称不可以为空" invalidMessage="权限名称长度不可以超过20个字符"></input>
	        </div>
	        <div class="formbox">
	            <label for="parentAuth">上级权限:</label>
	            <select class="easyui-combobox" id="parentAuth" name="parentAuth"  data-options="editable:false,required:true" 
	            style="width:140px;" missingMessage="请选择上级权限">
						<option value="0">无上级权限</option>
				</select>
	           
	        </div>
	        <div class="formbox">
	            <label for="url">权限 url:</label>
	            <input class="easyui-validatebox" type="text" name="url" data-options="required:true"></input>
	        </div>
	        <div class="formbox">
	            <label for="authImg">权限图片:</label>
	            <input class="easyui-validatebox" type="text" name="authImg" data-options="required:true"></input>
	        </div>
	         <div class="formbox">
	            <label for="status">是否启用:</label>
	            <input class="easyui-validatebox" type="radio" name="status" checked value="1">是</input>
	            <input class="easyui-validatebox" type="radio" name="status" value="0">否</input>
	        </div>
	      </form>
    </div> 
    
    <!-- 修改权限弹框 -->
    <div id="updateAuth" class="easyui-dialog" title="修改权限" style="width:400px;height:300px;padding:10px"
            data-options="
                iconCls: 'icon-save',
                buttons: [{
                    text:'修改',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitUpdateauth();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-ok',
                    handler:function(){
                        $('#updateAuth').dialog('close');
                    }
                }]
            ">
		<form id="ffupdate" method="get" novalidate>
	        <div class="formbox">
	            <label for="code">权限编码:</label>
	            <input class="easyui-validatebox" type="text" name="code" data-options="required:true" readonly="readonly"
	             validType="length[1,20]" missingMessage="权限编码不可以为空" invalidMessage="权限编码长度不可以超过20个字符"></input>
	        </div>
	        <div class="formbox">
	            <label for="authName">权限名称:</label>
	            <input class="easyui-validatebox" type="text" name="authName" data-options="required:true"
	             validType="length[1,20]" missingMessage="权限名称不可以为空" invalidMessage="权限名称长度不可以超过20个字符"></input>
	        </div>
	        <div class="formbox">
	            <label for="parentAuth">上级权限:</label>
	            <select class="easyui-combobox" id="parentAuth" name="parentAuth"  data-options="editable:false,required:true" 
	            style="width:140px;" missingMessage="请选择上级权限">
						<option value="0">无上级权限</option>
				</select>
	           
	        </div>
	        <div class="formbox">
	            <label for="url">权限 url:</label>
	            <input class="easyui-validatebox" type="text" name="url" data-options="required:true"></input>
	        </div>
	        <div class="formbox">
	            <label for="authImg">权限图片:</label>
	            <input class="easyui-validatebox" type="text" name="authImg" data-options="required:true"></input>
	        </div>
	         <div class="formbox">
	            <label for="status">是否启用:</label>
	            <input class="easyui-validatebox" type="radio" name="status" checked value="1">是</input>
	            <input class="easyui-validatebox" type="radio" name="status" value="0">否</input>
	        </div>
	      </form>
    </div> 
     
	</body>
</html>