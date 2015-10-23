<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    
    <title>时代枫企业管理平台</title>
	 <jsp:include page="../common/top.jsp" flush="true" /> 
	  <script type="text/javascript">
	  /* ########配置菜单的url时需要注意：要配置菜单url的全局路径，可以在数据库设置时设置为如"/user/basic.jsp"，
	  然后统一在前台进行与contextPath的拼接 ################*/
	  /* var _menus = {
				basic : [ {
					"menuid" : "10",
					"icon" : "icon-sys",
					"menuname" : "用户",
					"menus" : [ {
						"menuid" : "111",
						"menuname" : "站点管理",
						"icon" : "icon-nav",
						"url" : contextPath+"/user/basic.jsp"
					}, {
						"menuid" : "113",
						"menuname" : "添加站点",
						"icon" : "icon-nav",
						"url" : "#"
					}, {
						"menuid" : "115",
						"menuname" : "中心管理员管理",
						"icon" : "icon-nav",
						"url" : "#"
					}, {
						"menuid" : "117",
						"menuname" : "专员管理",
						"icon" : "icon-nav",
						"url" : "#"
					}, {
						"menuid" : "119",
						"menuname" : "市场管理员管理",
						"icon" : "icon-nav",
						"url" : "em/enterpriseChannelObtend.action"
					} ]
				}, {
					"menuid" : "20",
					"icon" : "icon-sys",
					"menuname" : "商品",
					"menus" : [ {
						"menuid" : "211",
						"menuname" : "商品管理",
						"icon" : "icon-nav",
						"url" : "#"
					}, {
						"menuid" : "213",
						"menuname" : "购买商品",
						"icon" : "icon-nav",
						"url" : "#"
					} ]
				} ],
				point : [{
					"menuid" : "20",
					"icon" : "icon-sys",
					"menuname" : "积分管理",
					"menus" : [ {
						"menuid" : "211",
						"menuname" : "积分用途",
						"icon" : "icon-nav",
						"url" : "#"
					}, {
						"menuid" : "213",
						"menuname" : "积分调整",
						"icon" : "icon-nav",
						"url" : "#"
					} ]

				}]
			}; */
        //设置登录窗口
        function openPwd() {
            $('#w').window({
                title: '修改密码',
                width: 300,
                modal: true,
                shadow: true,
                closed: true,
                height: 160,
                resizable:false
            });
        }
        //关闭登录窗口
        function closePwd() {
            $('#w').window('close');
        }

        

        //修改密码
        function serverLogin() {
            var $newpass = $('#txtNewPass');
            var $rePass = $('#txtRePass');

            if ($newpass.val() == '') {
                msgShow('系统提示', '请输入密码！', 'warning');
                return false;
            }
            if ($rePass.val() == '') {
                msgShow('系统提示', '请在一次输入密码！', 'warning');
                return false;
            }

            if ($newpass.val() != $rePass.val()) {
                msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
                return false;
            }

            $.post('/ajax/editpassword.ashx?newpass=' + $newpass.val(), function(msg) {
                msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + msg, 'info');
                $newpass.val('');
                $rePass.val('');
                close();
            })
            
        }

		
		

    </script>

<script type="text/javascript" src="<%=request.getContextPath() %>/user/js/test.js"></script>  
</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
<input type="hidden" name="menuId" id="menuId" value="${menuId}">

<noscript>
<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
    <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
</div></noscript>
    <div region="north" split="false" border="false" style="overflow: hidden; height: 50px;
        background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head"> 
       	 <a href="#" id="editpass">修改密码</a> <a href="#" id="loginOut" onclick="logout()">安全退出</a>
        </span>

        <span style="padding-left:10px; font-size: 26px; float:left;">时代枫企业管理平台</span>
    </div>
    <div region="south" split="false" style="height: 30px; background: #D2E0F2; ">
        <div class="footer"><center>时代枫传媒@2015</center></div>
    </div>
    <div region="west" hide="true" split="false" title="导航菜单" style="width:180px;" id="west">
<div id='wnav' class="easyui-accordion" fit="true" border="false">
		<!--  导航内容 -->
				
			</div>

    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			<div title="欢迎使用" style="padding:20px;overflow:hidden;" id="home">
				
			<h1>欢迎进入时代枫企业管理平台</h1>

			</div>
		</div>
    </div>
    
    
    <!--修改密码窗口-->
    <div id="w" class="easyui-window" title="修改密码" collapsible="false" minimizable="false"
        maximizable="false" icon="icon-save"  style="width: 300px; height: 150px; padding: 5px;
        background: #fafafa;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                <table cellpadding=3>
                    <tr>
                        <td>新密码：</td>
                        <td><input id="txtNewPass" type="Password" class="txt01" /></td>
                    </tr>
                    <tr>
                        <td>确认密码：</td>
                        <td><input id="txtRePass" type="Password" class="txt01" /></td>
                    </tr>
                </table>
            </div>
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >
                    确定</a> <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
            </div>
        </div>
    </div>

	<div id="mm" class="easyui-menu" style="width:150px;">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>


</body>
</html>