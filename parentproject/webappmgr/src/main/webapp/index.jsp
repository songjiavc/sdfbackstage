<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
Boolean useMaskCode = (Boolean)request.getAttribute("useMaskCode");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>时代枫企业管理平台</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	
	<jsp:include page="common/top.jsp" flush="true" />

  </head>
  
    <script language="javascript">
		function setFocus(){
			document.login.username.focus();
		}
		function submitForm(){
			   document.login.submit();
		}
		function resetForm(){
			document.login.reset();
			document.login.username.focus();
		}
		function changeimage(obj,path){
		    obj.src=path;
		}
		function submitLoginForm(){
		 if(event.keyCode==13)
		  document.login.submit();
		}
</script>
</head>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<center>
  <form method="post" name="login" style="margin:0px" action="<%=request.getContextPath() %>/menu/getNewPage.action">
    <table id="__01" width="1000" height="600" border="0" cellpadding="0" cellspacing="0" background="/images/login/lz_dl.jpg">
      <tr>
        <td colspan="3" height="100">&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td width="578"><div align="center">
            <TABLE align="right" cellSpacing=0 cellPadding=0 width="40%" border="0">
              <TBODY>
                <tr><td colspan="2" height="250">&nbsp;</td></tr> 
                <TR>
                  <TD class="px12-1" align="right" width="28%" height="25">
                  <div align="center"><font color="#0066CC">类&nbsp;&nbsp;型：</font></div></TD>
                  <TD width="57%">
                  
                  </TD>
                </TR>
                <TR>
                  <TD width="28%" height=25 align=right class=px12-1><div align="center"><font color="#0066CC">用户名：</font></div></TD>
                  <TD><INPUT name="userName" style="width:130px"></TD>
                </TR>
                <TR>
                  <TD class=px12-1 align=right width="28%"
                        height=25><div align="center"><font color="#0066CC">密&nbsp;&nbsp;码：</font></div></TD>
                  <TD width="57%"><INPUT type=password name="password" style="width:130px"></TD>
                </TR>
                
               <!--  <TR>
                  <TD class=px12-1 align=right width="28%"
                        height=25><div align="center"><font color="#0066CC">验证码：</font></div></TD>
                  <TD width="57%"><INPUT type=text  class=box6 name="code" style="width:63px" onkeydown="submitLoginForm();">
&nbsp;<img id="licenceImg" src="/zyProcurement/authImage" border="0" name="" width="60" height="20" align="absmiddle" alt='验证码,看不清楚? 请点击刷新验证码' onClick="this.src='/zyProcurement/authImage'" style="cursor : pointer;"></TD>
                </TR> -->
        
                <TR>
                  <TD height=40 colspan="2" align=center><br>
                	<button onclick="submitForm()">提交</button>
                 
                  </TD>
                </TR>
                
              </TBODY>
            </TABLE>
          </div></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td colspan="3">&nbsp;</td>
      </tr>
    </table>
  </form>
</center>
</body>
  </body>
</html>
