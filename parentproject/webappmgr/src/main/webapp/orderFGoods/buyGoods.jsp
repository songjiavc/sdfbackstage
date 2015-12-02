<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>购买商品</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link href="<%=request.getContextPath() %>/css/ztree/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <jsp:include page="../common/top.jsp" flush="true" /> 
    <script src="<%=request.getContextPath() %>/orderFGoods/js/buyGoods.js" type="text/javascript"></script>
    <script type="text/javascript">
		
	</script>
		<style type="text/css">
			.ztree li button.switch {visibility:hidden; width:1px;}
			.ztree li button.switch.roots_docu {visibility:visible; width:16px;}
			.ztree li button.switch.center_docu {visibility:visible; width:16px;}
			.ztree li button.switch.bottom_docu {visibility:visible; width:16px;}
			
			  .ftitle{
	  			width:50%;
	  			float : left;
	  			margin-bottom: 20px;
	  			font-family:'微软雅黑';
	  		}
	  		
	  		.ftable{
	  			width:100%;
	  			float : left;
	  			margin-bottom: 20px;
	  			font-family:'微软雅黑';
	  		}
	  		.ftitle label{
	  			float : left;
	  			margin-left: 30px;
	  		}
	  		
	  		.ftable label{
	  			float : left;
	  			margin-left: 30px;
	  		}
	  		.ftitle .commonInput{
	  			float : left;
	  			width: 200px;
	  			margin-left: 30px;
	  			border-radius : 5px;
	  		}
	  		
	  		.td_font{
	  			font-weight:bold;
	  		}
	  		
	  		.input_border{
	  			width:150px;
	  			border-radius : 5px;
	  		}
	  		
	  		#main-layout{     min-width:1050px;     min-height:240px;     overflow:hidden; }
		</style>
		
	 
</head>
<body style="background-color:white;width:100%;" class="easyui-layout">
	<div  region="center" title="订单详情" style="margin-top:10px;">
		<form id="ff" method="get" novalidate>
	        <div class="ftitle">
	            <label for="idA">订单编码:</label>
	            <input type="hidden" name="id" id="idA"/>
	            <div style="float:left;margin-left: 30px;">
	            	<input name="code" id="codehidden" type="hidden" >
	           	 	<input class="easyui-textbox" readonly="readonly" type="text" id="codeA" style="width:200px"/> 
	           	</div>
	        </div>
	         <div class="ftitle">
	            <label for="creatorU">订单创建人:</label><!-- 读取创建订单的代理人姓名 -->
	             <div style="float:left;margin-left: 30px;">
	           		 <input class="easyui-textbox" id="creatorA" type="text" name="creator" readonly="readonly" style="width:200px"/> 
	           	 </div>
	        </div>
	        <div class="ftitle">
	            <label for="nameA">订单名称:</label>
	            <input class="easyui-validatebox commonInput" type="text" id="nameA" name="name" data-options="required:true"
	             validType="length[1,50]"  missingMessage="订单名称不可以为空" ></input>
	        </div>
	       <div class="ftitle">
	            <label for="payModeA">支付方式:</label>
	             <div style="float:left;margin-left: 40px;">
		            <select class="easyui-combobox " id="payModeA" name="payMode"  
			          	 		 data-options="editable:false" style="width:200px;" >
			          	 		<option value="0" checked="checked">现金支付</option>
			          	 		<option value="1">转账支付</option>
					</select>
				</div>
	        </div>
	        <div class="ftitle">
	            <label for="priceA">收货人地址:</label>
	            <input class="easyui-validatebox commonInput" type="text" id="receiveAddrA" name="receiveAddr" data-options="required:true"
	             validType="length[1,50]" ></input>
	        </div>
	        <div class="ftitle">
	            <label for="receiveTeleA">联系电话:</label>
	            
	            <input class="easyui-validatebox commonInput" type="text" id="receiveTeleA" name="receiveTele" data-options="required:true"
	             validType="mobileAndTel"  ></input>
	        </div>
	       <!--  <div class="ftitle">
	            <label for="subject">配送方式:</label>
	            <input class="easyui-validatebox commonInput" type="text" id="priceU" name="price" data-options="required:true"
	             validType="money" missingMessage="商品价格不可以为空" ></input>
	        </div> -->
	        <!-- <div class="ftitle">
	            <label for="subject">运费:</label>
	            <input class="easyui-validatebox commonInput" type="text" id="priceU" name="price" data-options="required:true"
	             validType="money" missingMessage="商品价格不可以为空" ></input>
	        </div> -->
	        <div class="ftitle">
	            <label for="priceA">商品总价(元):</label>
	            <div style="float:left;margin-left: 30px;">
	            	<input name="price" id="pricehidden" type="hidden" >
	           	 	<input class="easyui-textbox" readonly="readonly" type="text" id="priceA" style="width:200px"/> 
	           	</div>
	        </div>
	        <div class="ftitle">
	            <label for="stationA">选中站点:</label>
	             <div style="float:left;margin-left: 40px;">
		            <select class="easyui-combobox " id="stationA" name="station"  
			          	 		 data-options="editable:false" style="width:200px;" >
			          	 		
					</select>
				</div>
	        </div>
	       <div class="ftable">
	           <label for="goodsDatagridU">选择商品:</label>
	           <div style="float:left;margin-left:30px;width:800px;">
	            	<table id="goodsDatagridU" class="easyui-datagrid" style="width:700px;"  title="商品列表" >
					</table>
	            </div>
	        </div>
	      </form>
	  </div>
	  
	  <div	style="float:left;width:100%;" region="south">
	  		<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="submitAddgoods('0')">保存</a>
	  		<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="submitAddgoods('1')">保存并提交</a>
	  </div>
	  
	  <div id="selectOtherStation" class="easyui-dialog" title="选择其他彩种站点" style="width:400px;height:200px;padding:10px;top:50px;"
            data-options="
                iconCls: 'icon-save',
                buttons: [{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                    	getSelectedOtherStation();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#selectOtherStation').dialog('close');
                        
                    }
                }]
            ">
            <div style="float:left;margin-left: 40px;">
		            <select class="easyui-combobox " id="otherStation" 
			          	 		 data-options="editable:false" style="width:200px;" >
			          	 		
					</select>
			</div>
		
    </div>
</body>
	
	
</html>