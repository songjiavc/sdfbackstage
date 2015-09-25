<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>权限管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link href="<%=request.getContextPath() %>/css/ztree/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <jsp:include page="../common/top.jsp" flush="true" /> 
    <script src="<%=request.getContextPath() %>/js/ztree/jquery-1.4.4.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath() %>/js/ztree/jquery.ztree.core-3.5.js" type="text/javascript"></script>
    <script type="text/javascript">
  	  var setting = {
			view: {
				showLine: false
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
  	  
  	var toolbar = [{
  	    text:'添加',
  	    iconCls:'icon-add',
  	    handler:function(){alert('add')}
  	},{
  	    text:'删除',
  	    iconCls:'icon-remove',
  	    handler:function(){alert('cut')}
  	}];
  	  
  		var zNodes =[
  				{ id:1, pId:0, name:"父节点1 - 展开", open:true},
  				{ id:11, pId:1, name:"父节点11 - 折叠"},
  				{ id:111, pId:11, name:"叶子节点111"},
  				{ id:112, pId:11, name:"叶子节点112"},
  				{ id:113, pId:11, name:"叶子节点113"},
  				{ id:114, pId:11, name:"叶子节点114"},
  				{ id:12, pId:1, name:"父节点12 - 折叠"},
  				{ id:121, pId:12, name:"叶子节点121"},
  				{ id:122, pId:12, name:"叶子节点122"},
  				{ id:123, pId:12, name:"叶子节点123"},
  				{ id:124, pId:12, name:"叶子节点124"},
  				{ id:13, pId:1, name:"父节点13 - 没有子节点", isParent:true},
  				{ id:2, pId:0, name:"父节点2 - 折叠"},
  				{ id:21, pId:2, name:"父节点21 - 展开", open:true},
  				{ id:211, pId:21, name:"叶子节点211"},
  				{ id:212, pId:21, name:"叶子节点212"},
  				{ id:213, pId:21, name:"叶子节点213"},
  				{ id:214, pId:21, name:"叶子节点214"},
  				{ id:22, pId:2, name:"父节点22 - 折叠"},
  				{ id:221, pId:22, name:"叶子节点221"},
  				{ id:222, pId:22, name:"叶子节点222"},
  				{ id:223, pId:22, name:"叶子节点223"},
  				{ id:224, pId:22, name:"叶子节点224"},
  				{ id:23, pId:2, name:"父节点23 - 折叠"},
  				{ id:231, pId:23, name:"叶子节点231"},
  				{ id:232, pId:23, name:"叶子节点232"},
  				{ id:233, pId:23, name:"叶子节点233"},
  				{ id:234, pId:23, name:"叶子节点234"},
  				{ id:3, pId:0, name:"父节点3 - 没有子节点", isParent:true}
  			];

		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			
            var pager = $('#dg').datagrid().datagrid('getPager');    // get the pager of datagrid
           /*  pager.pagination({
                buttons:[{
                    iconCls:'icon-search',
                    handler:function(){
                        alert('search');
                    }
                },{
                    iconCls:'icon-add',
                    handler:function(){
                        alert('add');
                    }
                },{
                    iconCls:'icon-edit',
                    handler:function(){
                        alert('edit');
                    }
                }]
            });        */     
		});
	</script>
		<style type="text/css">
			.ztree li button.switch {visibility:hidden; width:1px;}
			.ztree li button.switch.roots_docu {visibility:visible; width:16px;}
			.ztree li button.switch.center_docu {visibility:visible; width:16px;}
			.ztree li button.switch.bottom_docu {visibility:visible; width:16px;}
		</style>
</head>
<body>
    <div class="zTreeDemoBackground left" style="float:left;width: 200px;">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
    <div style="float:left;margin-left:10px;" >
    	<div style="margin-top:0px;">
    	 <table class="easyui-datagrid" title="权限列表" style="width:800px;height:400px;"
			data-options="rownumbers:true,singleSelect:false,pagination:true,
			collapsible:false,url:'datagrid_data1.json',method:'get',toolbar:toolbar">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'itemid',width:100">权限编码</th>
					<th data-options="field:'productid',width:150">权限名称</th>
					<th data-options="field:'listprice',width:150,align:'center'">权限地址</th>
					<th data-options="field:'unitcost',width:80,align:'center'">是否启用</th>
					<th data-options="field:'attr1',width:250">操作</th>
				</tr>
				</thead>
			</table>
    	</div>
       
	</body>
</html>