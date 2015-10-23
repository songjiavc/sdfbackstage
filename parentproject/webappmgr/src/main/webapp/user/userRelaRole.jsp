<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>角色选择</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link href="<%=request.getContextPath() %>/css/ztree/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <jsp:include page="../common/top.jsp" flush="true" /> 
    <script src="<%=request.getContextPath() %>/user/js/userRelaRole.js" type="text/javascript"></script>
</head>
<body>

    <div style="float:left;margin-left:10px;" >
    	<div style="margin-top:0px;">
    	 <table id="datagrid"  title="角色列表" style="width:800px;height:400px;">
			</table>
    	</div>
 	</div>  
  
</body>
</html>