<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>测试页面</title>
	  
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/easyUI/themes/default/easyui.css"/>    
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/easyUI/themes/icon.css"/>    
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/default.css"/>    
     <script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.10.1.min.js"></script>    
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/easyUI/jquery.easyui.min.js"></script>    
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/easyUI/locale/easyui-lang-zh_CN.js"></script>  
    <script type="text/javascript" src="<%=request.getContextPath() %>/user/js/test.js"></script>  
</head>
<body>
	
	<table class="easyui-datagrid" title="站点列表" style="width:700px;height:250px"
			data-options="singleSelect:true,collapsible:true,url:'datagrid_data1.json',method:'get'">
		<thead>
			<tr>
				<th data-options="field:'itemid',width:80">Item ID</th>
				<th data-options="field:'productid',width:100">Product</th>
				<th data-options="field:'listprice',width:80,align:'right'">List Price</th>
				<th data-options="field:'unitcost',width:80,align:'right'">Unit Cost</th>
				<th data-options="field:'attr1',width:250">Attribute</th>
				<th data-options="field:'status',width:60,align:'center'">Status</th>
			</tr>
		</thead>
	</table>

</body>
</html>