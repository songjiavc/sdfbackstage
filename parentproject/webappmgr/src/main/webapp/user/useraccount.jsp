<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>测试页面</title>
	  
	<jsp:include page="../common/top.jsp" flush="true" /> 
    <script type="text/javascript" src="<%=request.getContextPath() %>/user/js/test.js"></script>  
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
</head>
<body>
	
	<table class="easyui-datagrid" title="站点列表" style="width:100%;height:250px"
			data-options="singleSelect:true,collapsible:true,url:'datagrid_data1.json',method:'get',toolbar:toolbar,fit:true,fitColumns:true">
		<thead>
			<tr>
				<th data-options="field:'itemid',width:80">Item ID</th>
				<th data-options="field:'productid',width:100">Product</th>
				<th data-options="field:'listprice',width:80,align:'right'">List Price</th>
				<th data-options="field:'unitcost',width:80,align:'right'">Unit Cost</th>
				<th data-options="field:'attr1',width:250">Attribute</th>
				<th data-options="field:'status',align:'center'">Status</th>
			</tr>
		</thead>
	</table>

</body>
</html>