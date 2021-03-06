<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>订单管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link href="<%=request.getContextPath() %>/css/ztree/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <jsp:include page="../common/top.jsp" flush="true" /> 
    <script src="<%=request.getContextPath() %>/orderFGoods/js/orderManage.js" type="text/javascript"></script>
    
    <script type="text/javascript">
  	var toolbar = [{
  	   /*  text:'批量删除订单',
  	    iconCls:'icon-remove',
  	    handler:function(){
  	    	deleteOrdersList();
  	    	} */
  	}];
  	  
  	
		
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
	  			width:75px;
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
<body  class="easyui-layout">
	<!-- 模糊查询 -->
	<div   data-options="region:'north'" style="height:90px;border:1px solid #95b8e7;background-color:white;">
	    	<table style="border: none;height: 80px;">
		    	<tr>
		    		<td width="7%" class="td_font">订单名称：</td>
		    		<td width="15%">
		    			<input id="orderNameC" class="input_border"  type="text" name="orderNameC"  />  
		    		</td>
		    		<!-- <td width="7%" class="td_font">商品编码：</td>
		    		<td width="15%">
		    			<input type="text" class="input_border"  name="goodscodeC" id="goodscodeC" >
		    		</td> -->
		    		
		    		<td class="td_font" width="12%">
		    			<input style="cursor:pointer;background-color: #e0ecff;border-radius:5px;float:left" type="button" value="查询" onclick="initDatagrid()">
		    			<input style="cursor:pointer;background-color: #e0ecff;border-radius:5px;float:left;margin-left:5px;" type="button" value="重置" onclick="reset()">
		    		</td>
		    	</tr>
	    	</table>	
	</div>

    <div id="main-layout" data-options="region:'center'" data-options="border:false" >
    	 <table id="datagrid" class="easyui-datagrid"  title="订单列表" >
			</table>
 	</div>  
  
  
  
     <!-- 修改订单弹框 -->
     <div id="updateOrders" class="easyui-dialog" title="修改订单信息" style="width:800px;height:500px;padding:10px;top:40px;"
            data-options="
                iconCls: 'icon-save',
                buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitUpdateOrders('0');
                    }
                },{
                    text:'保存并提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitUpdateOrders('1');
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#updateOrders').dialog('close');
                        clearGoodsArray();
                    }
                }]
            ">
		<form id="ffUpdate" method="get" novalidate>
	        <div class="ftitle">
	            <label for="codeU">订单编码:</label>
	            <input type="hidden" name="id" id="idU"/>
	            <input class="easyui-validatebox commonInput" readonly="readonly" type="text" id="codeU" name="code" data-options="required:true"
	              ></input>
	        </div>
	        <div class="ftitle">
	            <label for="creatorU">订单创建人:</label><!-- 读取创建订单的代理人姓名 -->
	            <input class="easyui-validatebox commonInput" type="text" id="creatorU" name="creator" readonly="readonly"
	               ></input>
	        </div>
	        <div class="ftitle">
	            <label for="nameU">订单名称:</label>
	            <input class="easyui-validatebox commonInput" type="text" id="nameU" name="name" data-options="required:true"
	            validType="length[1,50]"  missingMessage="订单名称不可以为空" ></input>
	        </div>
	       <div class="ftitle">
	            <label for="priceU">支付方式:</label>
	            <div style="float : left;margin-left: 30px;">
		            <select class="easyui-combobox" id="payModeC" name="payMode"  
			          	 		 data-options="editable:false" style="width:150px;" >
			          	 		<option value="0" checked="checked">现金支付</option>
			          	 		<option value="1">转账支付</option>
					</select>
				</div>
	        </div>
	        <div class="ftitle">
	            <label for="receiveAddrU">收货人地址:</label>
	            <input class="easyui-validatebox commonInput" type="text" id="receiveAddrU" name="receiveAddr" data-options="required:true"
	             validType="length[1,50]" ></input>
	        </div>
	         <div class="ftitle">
	            <label for="receiveTeleU">联系电话:</label>
	            <input class="easyui-validatebox commonInput" type="text" id="receiveTeleU" name="receiveTele" data-options="required:true"
	             validType="mobileAndTel"  ></input>
	        </div>
	       <!--  <div class="ftitle">
	            <label for="subject">配送方式:</label>
	             <input class="easyui-validatebox commonInput" type="text" id="priceU" name="price" data-options="required:true"
	             validType="money"  ></input>
	        </div> -->
	      <!--   <div class="ftitle">
	            <label for="priceU">运费:</label>
	            <input class="easyui-validatebox commonInput" type="text" id="priceU" name="price" data-options="required:true"
	             validType="money"  ></input>
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
	             <div style="float:left;margin-left: 30px;">
		            <select class="easyui-combobox " id="stationA" name="station"  
			          	 		 data-options="editable:false" style="width:200px;" >
			          	 		
					</select>
				</div>
	        </div>
	        <div class="ftable">
	            <label for="product">选择商品:<span style="color:red;font-size: 11px;">(*若选中的商品为双机商品,请配置其他彩种站点!)</span></label>
	           <div style="float:left;margin-left:30px;width:700px;">
	            	<table id="goodsDatagridU" class="easyui-datagrid" style="width:700px;"  title="商品列表" >
					</table>
	            </div>
	        </div>
	      </form>
    </div>
    
     <div id="detailOrders" class="easyui-dialog" title="订单详情" style="width:800px;height:500px;padding:10px;top:40px;"
            data-options="
                iconCls: 'icon-save',
                buttons: [{
                    text:'审批通过',
                    iconCls:'icon-ok',
                    handler:function(){
                        approveOrdersInDialog('2');
                    }
                },{
                    text:'审批驳回',
                    iconCls:'icon-ok',
                    handler:function(){
                        approveOrdersInDialog('3');
                    }
                },{
                    text:'不通过',
                    iconCls:'icon-ok',
                    handler:function(){
                       approveOrdersInDialog('4');
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#detailOrders').dialog('close');
                        clearGoodsArray();
                    }
                }]
            ">
		<form id="ffDetail" method="get" novalidate>
	        <div class="ftitle">
	            <label for="codeU">订单编码:</label>
	            <input type="hidden" name="id" id="idD"/>
	            <input class="easyui-validatebox commonInput" readonly="readonly" type="text" id="codeU" name="code" 
	              ></input>
	        </div>
	        <div class="ftitle">
	            <label for="creatorU">订单创建人:</label><!-- 读取创建订单的代理人姓名 -->
	            <input class="easyui-validatebox commonInput" type="text" id="creatorU" name="creator" readonly="readonly"
	               ></input>
	        </div>
	        <div class="ftitle">
	            <label for="nameU">订单名称:</label>
	            <input class="easyui-validatebox commonInput" type="text" id="nameU" name="name"  readonly="readonly"></input>
	        </div>
	       <div class="ftitle">
	            <label for="priceU">支付方式:</label>
	           		<input class="easyui-validatebox commonInput" type="text" id="payModeC" name="payMode"  readonly="readonly"></input>
		           <!--  <select class="easyui-combobox" id="payModeC" name="payMode"  
			          	 		 data-options="editable:false" style="width:150px;" >
			          	 		<option value="0" checked="checked">现金支付</option>
			          	 		<option value="1">转账支付</option>
					</select> -->
	        </div>
	        <div class="ftitle">
	            <label for="receiveAddrU">收货人地址:</label>
	            <input class="easyui-validatebox commonInput" type="text" id="receiveAddrU" name="receiveAddr" readonly="readonly"
	             ></input>
	        </div>
	         <div class="ftitle">
	            <label for="receiveTeleU">联系电话:</label>
	            <input class="easyui-validatebox commonInput" type="text" id="receiveTeleU" name="receiveTele"  readonly="readonly"
	            ></input>
	        </div>
	       <!--  <div class="ftitle">
	            <label for="subject">配送方式:</label>
	             <input class="easyui-validatebox commonInput" type="text" id="priceU" name="price" data-options="required:true"
	             validType="money"  ></input>
	        </div> -->
	      <!--   <div class="ftitle">
	            <label for="priceU">运费:</label>
	            <input class="easyui-validatebox commonInput" type="text" id="priceU" name="price" data-options="required:true"
	             validType="money"  ></input>
	        </div> -->
	         <div class="ftitle">
	            <label for="priceA">商品总价(元):</label>
	            <div style="float:left;margin-left: 30px;">
	           	 	<input class="easyui-textbox" readonly="readonly" type="text" id="priceD" name="price" style="width:200px" readonly="readonly"/> 
	           	</div>
	        </div>
	        <div class="ftitle">
	            <label for="stationA">选中站点号:</label>
		            <!-- <select class="easyui-combobox " id="stationD" name="station"  
			          	 		 data-options="editable:false" style="width:200px;" >
			          	 		
					</select> -->
					<input class="easyui-validatebox commonInput" readonly="readonly" type="text" id="stationD" name="station" 
	              ></input>
	        </div>
	        <div class="ftable">
	            <label for="product">选中的商品列表:</label>
	           <div style="float:left;margin-left:30px;width:700px;">
	            	<table id="goodsDatagridD" class="easyui-datagrid" style="width:700px;"  title="商品列表" >
					</table>
	            </div>
	        </div>
	      </form>
    </div>
    
     <!-- 普通用户订单详情弹框 -->
    <div id="detailPTOrders" class="easyui-dialog" title="订单详情" style="width:800px;height:500px;padding:10px;top:40px;"
            data-options="
                iconCls: 'icon-save',
                buttons: [{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#detailPTOrders').dialog('close');
                        clearGoodsArray();
                    }
                }]
            ">
		<form id="ffPTDetail" method="get" novalidate>
	        <div class="ftitle">
	            <label for="codePTU">订单编码:</label>
	            <input type="hidden" name="id" id="idPTD"/>
	            <input class="easyui-validatebox commonInput" readonly="readonly" type="text" id="codePTU" name="code" 
	              ></input>
	        </div>
	        <div class="ftitle">
	            <label for="creatorPTU">订单创建人:</label><!-- 读取创建订单的代理人姓名 -->
	            <input class="easyui-validatebox commonInput" type="text" id="creatorPTU" name="creator" readonly="readonly"
	               ></input>
	        </div>
	        <div class="ftitle">
	            <label for="namePTU">订单名称:</label>
	            <input class="easyui-validatebox commonInput" type="text" id="namePTU" name="name"  readonly="readonly"></input>
	        </div>
	       <div class="ftitle">
	            <label for="payModePTC">支付方式:</label>
	           		<input class="easyui-validatebox commonInput" type="text" id="payModePTC" name="payMode"  readonly="readonly"></input>
		           <!--  <select class="easyui-combobox" id="payModeC" name="payMode"  
			          	 		 data-options="editable:false" style="width:150px;" >
			          	 		<option value="0" checked="checked">现金支付</option>
			          	 		<option value="1">转账支付</option>
					</select> -->
	        </div>
	        <div class="ftitle">
	            <label for="receiveAddrPTU">收货人地址:</label>
	            <input class="easyui-validatebox commonInput" type="text" id="receiveAddrPTU" name="receiveAddr" readonly="readonly"
	             ></input>
	        </div>
	         <div class="ftitle">
	            <label for="receiveTelePTU">联系电话:</label>
	            <input class="easyui-validatebox commonInput" type="text" id="receiveTelePTU" name="receiveTele"  readonly="readonly"
	            ></input>
	        </div>
	       <!--  <div class="ftitle">
	            <label for="subject">配送方式:</label>
	             <input class="easyui-validatebox commonInput" type="text" id="priceU" name="price" data-options="required:true"
	             validType="money"  ></input>
	        </div> -->
	      <!--   <div class="ftitle">
	            <label for="priceU">运费:</label>
	            <input class="easyui-validatebox commonInput" type="text" id="priceU" name="price" data-options="required:true"
	             validType="money"  ></input>
	        </div> -->
	         <div class="ftitle">
	            <label for="pricePTD">商品总价(元):</label>
	            <div style="float:left;margin-left: 30px;">
	           	 	<input class="easyui-textbox" readonly="readonly" type="text" id="pricePTD" name="price" style="width:200px" readonly="readonly"/> 
	           	</div>
	        </div>
	        <div class="ftitle">
	            <label for="stationPTD">选中站点号:</label>
		            <!-- <select class="easyui-combobox " id="stationD" name="station"  
			          	 		 data-options="editable:false" style="width:200px;" >
			          	 		
					</select> -->
					<input class="easyui-validatebox commonInput" readonly="readonly" type="text" id="stationPTD" name="station" 
	              ></input>
	        </div>
	        <div class="ftable">
	            <label for="goodsPTDatagridD">选中的商品列表:</label>
	           <div style="float:left;margin-left:30px;width:700px;">
	            	<table id="goodsPTDatagridD" class="easyui-datagrid" style="width:700px;"  title="商品列表" >
					</table>
	            </div>
	        </div>
	      </form>
    </div>
    
</body>
	
	
</html>