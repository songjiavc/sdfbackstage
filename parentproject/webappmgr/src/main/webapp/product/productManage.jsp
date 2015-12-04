<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>产品管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link href="<%=request.getContextPath() %>/css/ztree/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <jsp:include page="../common/top.jsp" flush="true" /> 
    <script src="<%=request.getContextPath() %>/product/js/productManage.js" type="text/javascript"></script>
    
    <script type="text/javascript">
  	var toolbar = [{
  	    text:'添加产品',
  	    iconCls:'icon-add',
  	    handler:function(){
  	    	 //初始化上级权限下拉框值
  	    	initProvince('add','privinceA','');//默认选中全部，则全部下是没有市数据的
  	    	initProductDL('add','cpdlA','');
  	    	generateCode();//生成产品编码
  	    	$("#addProduct").dialog('open');
  	    	
  	    }
  	},{
  	    text:'批量删除产品',
  	    iconCls:'icon-remove',
  	    handler:function(){
  	    	deleteProductList();
  	    	}
  	}];
  	  
  	
		
	</script>
		<style type="text/css">
			.ztree li button.switch {visibility:hidden; width:1px;}
			.ztree li button.switch.roots_docu {visibility:visible; width:16px;}
			.ztree li button.switch.center_docu {visibility:visible; width:16px;}
			.ztree li button.switch.bottom_docu {visibility:visible; width:16px;}
			
			 .ftitle{
	  			width:100%;
	  			float : left;
	  			margin-bottom: 20px;
	  			font-family:'微软雅黑',
	  		}
	  		.ftitle label{
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
<body >
	<!-- 模糊查询 -->
	<div   region="north" style="height:80px;border:1px solid #95b8e7; margin-bottom: 10px;border-radius:5px;background-color:white;">
	    	<table style="border: none;height: 80px;">
		    	<tr>
		    		<td width="7%" class="td_font">产品名称：</td>
		    		<td width="15%">
		    			<input id="productNameC" class="input_border"  type="text" name="productNameC"  />  
		    		</td>
		    		<td width="7%" class="td_font">产品编码：</td>
		    		<td width="15%">
		    			<input type="text" class="input_border"  name="procodeC" id="procodeC" >
		    		</td>
		    		<td width="7%"  class="td_font">产品地域：</td>
		    		<td width="35%">
		    			 <label for="privinceC">省:</label>
		    			 <select class="easyui-combobox " id="privinceC" name="privinceC"  
		          	 		 data-options="editable:false" style="width:150px;" >
						</select>
						 <label for="cityC">市:</label>
						<select class="easyui-combobox " id="cityC" name="cityC"  
			          	  data-options="editable:false" style="width:150px;" >
						</select>
		    		</td>
		    		<td class="td_font" width="12%">
		    			<input style="cursor:pointer;background-color: #e0ecff;border-radius:5px;float:left" type="button" value="查询" onclick="initDatagrid()">
		    			<input style="cursor:pointer;background-color: #e0ecff;border-radius:5px;float:left;margin-left:5px;" type="button" value="重置" onclick="reset()">
		    		</td>
		    	</tr>
		    	<!-- <tr >
		    		<td class="td_font" width="7%">产品类别：</td>
		    		<td colspan="4">
			            <div style="float:left;margin-left:30px;">
			          		 <label for="cpdlC">大类:</label>
				            <select class="easyui-combobox " id="cpdlC" name="cpdlC"  
				          	  data-options="editable:false" style="width:100px;" >
				          	  	<option value="0">软件</option>
							</select>
			            </div>
			            <div style="float:left;margin-left:20px;">
			         	   <label for="cpzlC">中类:</label>
				            <select class="easyui-combobox " id="cpzlC" name="cpzlC"  
				          	  data-options="editable:false" style="width:100px;" >
				          	  	<option value="0">电子走势图</option>
							</select>
			            </div>
			            <div style="float:left;margin-left:20px;">
			           		 <label for="cpxlC">小类:</label>
				            <select class="easyui-combobox " id="cpxlC" name="cpxlC"  
				          	  data-options="editable:false" style="width:100px;" >
				          	  	<option value="0">体彩</option>
							</select>
			            </div> 			
		    		</td>
		    		<td class="td_font" width="7%">
		    			<input style="cursor:pointer;background-color: #e0ecff;border-radius:5px;" type="button" value="查询" onclick="initDatagrid()">
		    		</td>
		    	</tr> --> 
	    	</table>	
	</div>

    <div id="main-layout" class="easyui-layout" data-options="border:false" >
    	 <table id="datagrid" class="easyui-datagrid"  title="产品列表" >
			</table>
 	</div>  
  
  
    <!-- 添加产品弹框 -->
  <div id="addProduct" class="easyui-dialog" title="添加产品" style="width:600px;height:450px;padding:10px;top:40px;"
            data-options="
                iconCls: 'icon-save',
                buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitAddproduct();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#addProduct').dialog('close');
                    }
                }]
            ">
		<form id="ff" method="get" novalidate>
	        <div class="ftitle">
	            <label for="codeA">产品编码:</label>
	            <input type="hidden" name="id" id="idA"/>
	             <div style="float:left;margin-left: 30px;">
		            <input name="code" id="codehidden" type="hidden" >
		            <input class="easyui-textbox" readonly="readonly" type="text" id="codeA"  data-options="required:true"
		              style="width:200px" ></input>
		         </div>
	        </div>
	        <div class="ftitle">
	            <label for="nameA">产品名称:</label>
	            <input class="easyui-validatebox commonInput" type="text" id="nameA" name="name" data-options="required:true"
	             validType="checkAname['#nameA','idA']" missingMessage="产品名称不可以为空" ></input>
	        </div>
	        <div class="ftitle">
	            <label for="lotteryTypeA">产品彩种分类:</label>
	            <select class="easyui-combobox " id="lotteryTypeA" name="lotteryType"  
		          	  data-options="editable:false" style="width:150px;" >
		          	  <option value="1" checked>体彩</option>
		          	  <option value="2" >福彩</option>
				</select>
	        </div>
	        <div class="ftitle">
	            <label for="priceA">参考价格:</label>
	            <input class="easyui-validatebox commonInput" type="text" id="priceA" name="price" data-options="required:true"
	             validType="money" missingMessage="参考价格不可以为空" ></input>(元)
	        </div>
	        <div class="ftitle">
	            <label for="subject">产品地域:</label>
	            <div style="float:left;margin-left:30px;">
	           		<!-- <label for="privinceA">省:</label> -->
		            <select class="easyui-combobox " id="privinceA" name="privince"  
		          	  data-options="editable:false" style="width:150px;" >
					</select>
					<!-- <label for="cityA">市:</label> -->
					<select class="easyui-combobox " id="cityA" name="city"  
		          	  data-options="editable:false" style="width:150px;" >
					</select>
	            </div>
	        </div>
	        <div class="ftitle">
	            <label for="cpdlA">大类:</label>
	            <div style="float:left;margin-left:30px;">
		            <select class="easyui-combobox " id="cpdlA" name="cpdl"  
		          	  data-options="editable:false" style="width:100px;" >
					</select>
	            </div>
	             <label for="cpzlA">中类:</label>
	            <div style="float:left;margin-left:20px;">
		            <select class="easyui-combobox " id="cpzlA" name="cpzl"  
		          	  data-options="editable:false" style="width:100px;" >
					</select>
	            </div>
	            <label for="cpxlA">小类:</label>
	            <div style="float:left;margin-left:20px;">
		            <select class="easyui-combobox " id="cpxlA" name="cpxl"  
		          	  data-options="editable:false" style="width:100px;" >
					</select>
	            </div>
	        </div>
	         <div class="ftitle">
	         	 <label for="productDespritionA">产品描述:</label>
	         	 <!-- ※textarea两个标签间有空格时焦点会不落在首字符上※ -->
	         	 <textarea id="productDespritionA" name="productDesprition" class="easyui-validatebox" 
	         	 validType="length[0,100]" style="resize:none;width:400px;height:100px;margin-left:30px;border-radius:5px;"></textarea>
	         </div>
	      </form>
    </div>
     <!-- 修改产品弹框 -->
     <div id="updateProduct" class="easyui-dialog" title="修改产品信息" style="width:600px;height:450px;padding:10px;top:40px;"
            data-options="
                iconCls: 'icon-save',
                buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitUpdateproduct();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#updateProduct').dialog('close');
                    }
                }]
            ">
		<form id="ffUpdate" method="get" novalidate>
	        <div class="ftitle">
	            <label for="codeU">产品编码:</label>
	            <input type="hidden" name="id" id="idU"/>
	            <input class="easyui-validatebox commonInput" readonly="readonly" type="text" id="codeU" name="code" data-options="required:true"
	              ></input>
	        </div>
	        <div class="ftitle">
	            <label for="nameU">产品名称:</label>
	            <input class="easyui-validatebox commonInput" type="text" id="nameU" name="name" data-options="required:true"
	             validType="checkAname['#nameU','idU']" missingMessage="产品名称不可以为空" ></input>
	        </div>
	        <div class="ftitle">
	            <label for="lotteryTypeU">产品彩种分类:</label>
	            <select class="easyui-combobox " id="lotteryTypeU" name="lotteryType"  
		          	  data-options="editable:false" style="width:150px;" >
		          	  <option value="1" checked>体彩</option>
		          	  <option value="2" >福彩</option>
				</select>
	        </div>
	       <div class="ftitle">
	            <label for="priceU">参考价格:</label>
	            <input class="easyui-validatebox commonInput" type="text" id="priceU" name="price" data-options="required:true"
	             validType="money" missingMessage="产品价格不可以为空" ></input>(元)
	        </div>
	        <div class="ftitle">
	            <label for="subject">产品地域:</label>
	            <div style="float:left;margin-left:30px;">
	           		<!-- <label for="privinceA">省:</label> -->
		            <select class="easyui-combobox " id="privinceU" name="privince"  
		          	  data-options="editable:false" style="width:150px;" >
					</select>
					<!-- <label for="cityA">市:</label> -->
					<select class="easyui-combobox " id="cityU" name="city"  
		          	  data-options="editable:false" style="width:150px;" >
					</select>
	            </div>
	        </div>
	        <div class="ftitle">
	            <label for="cpdlU">大类:</label>
	            <div style="float:left;margin-left:30px;">
		            <select class="easyui-combobox " id="cpdlU" name="cpdl"  
		          	  data-options="editable:false" style="width:100px;" >
					</select>
	            </div>
	             <label for="cpzlU">中类:</label>
	            <div style="float:left;margin-left:20px;">
		            <select class="easyui-combobox " id="cpzlU" name="cpzl"  
		          	  data-options="editable:false" style="width:100px;" >
					</select>
	            </div>
	            <label for="cpxlU">小类:</label>
	            <div style="float:left;margin-left:20px;">
		            <select class="easyui-combobox " id="cpxlU" name="cpxl"  
		          	  data-options="editable:false" style="width:100px;" >
					</select>
	            </div>
	        </div>
	         <div class="ftitle">
	         	 <label for="productDespritionU">产品描述:</label>
	         	 <textarea id="productDespritionU" name="productDesprition" class="easyui-validatebox" 
	         	 validType="length[0,100]" style="resize:none;width:400px;height:100px;margin-left:30px;border-radius:5px;"></textarea>
	         </div>
	      </form>
    </div>
</body>
	
	
</html>