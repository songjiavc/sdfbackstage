var goodsList = new Array();//选中的商品数据
var countPrice = 0;//选中商品总价
var proOfgoods  = new map();

$(document).ready(function(){
	
			clearGoodsArray();
			closeDialog();//页面加载时关闭弹框
			initDatagrid();//初始化数据列表
			bindStationCombobox();
			getLoginuserRole();
			
			goodsList = new Array();
		});


/**
 * 初始化站点下拉框（加载站点的约束条件：1.上级代理下属的所有站点）
 */
function initStationList(stationinput,stationId)
{
	var data = new Object();
	
	$('#'+stationinput).combobox({
		queryParams:data,
		url:contextPath+'/order/getStationList.action',
		valueField:'id',
		textField:'stationNumber',
		 onLoadSuccess: function (data1) { //数据加载完毕事件
			 $('#'+stationinput).combobox('setValue',stationId);//初始化选中的stationlist时用setvalue，避免触发级联事件
				
         }
	}); 
}

/**
 * 获取当前登录用户的角色
 */
function getLoginuserRole()
{
	var isProxy = false;//是否拥有代理角色
	var isFinancialManager = false;//是否拥有财务管理员角色
	var returnArr = new Array();
	
	var data1 = new Object();
	var url = contextPath + '/order/getLoginuserRole.action';
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "post",
        url: url,
        data:data1,
        dataType: "json",
        success: function (data) {
        	isProxy = data.proxy;
        	isFinancialManager = data.financialManager;
        	
        	returnArr.push(isProxy);
        	returnArr.push(isFinancialManager);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
   });
	
	
	return returnArr;
	
}

/**
 * 清空全局变量
 */
function clearGoodsArray()
{
	goodsList = new Array();
	proOfgoods  = new map();
	countPrice = 0;//清零商品总价
	$('#goodsDatagridU').datagrid('loadData', { total: 0, rows: [] });//清空商品datagrid内容，避免异常，在再次打开弹框时，可以重新加载内容
	$('#goodsPTDatagridD').datagrid('loadData', { total: 0, rows: [] });//清空商品datagrid内容，避免异常，在再次打开弹框时，可以重新加载内容
	$('#goodsDatagridD').datagrid('loadData', { total: 0, rows: [] });//清空商品datagrid内容，避免异常，在再次打开弹框时，可以重新加载内容
}

/**
 * 为站点下拉框绑定级联事件
 */
function bindStationCombobox()
{
	$("#stationA").combobox({

		onSelect: function (rec) {
			if(undefined != rec &&null != rec.id && '' != rec.id)
			{
				clearGoodsArray();
				proOfgoods  = new map();
				$("#pricehidden").val('');
				$("#priceA").textbox('setText','');
				var returnArr = new Array();
				returnArr = getDetailStation(rec.id);//rec.id is stationId
				$('#goodsDatagridU').datagrid('loadData', { total: 0, rows: [] });//清空商品datagrid内容，避免异常，在再次打开弹框时，可以重新加载内容
				//根据站点的区域和彩种加载商品信息列表
				initGoodsDatagrid(returnArr[0], returnArr[1], 'goodsDatagridU', returnArr[2],'1');		
			}
			
		}

		}); 
}




/**
 * 重置
 */
function reset()
{
	//重置订单名称
	$("#orderNameC").val("");
}




//关闭弹框
function closeDialog()
{
	$("#updateOrders").dialog('close');//修改订单详情dialog
	$("#detailOrders").dialog('close');//财务管理员查看订单详情dialog
	$("#detailPTOrders").dialog('close');//普通用户查看订单详情dialog
}

/**
 * 初始化订单列表数据
 */
function initDatagrid()
{
	var params = new Object();
	params.orderName = $("#orderNameC").val().trim();
	
	$('#datagrid').datagrid({
		singleSelect:false,
		rownumbers:false,
		queryParams: params,
		url:contextPath + '/order/getOrdersList.action',//'datagrid_data1.json',
		method:'get',
		border:false,
		singleSelect:false,
		fit:true,//datagrid自适应
		fitColumns:true,
		pagination:true,
		collapsible:false,
		toolbar:toolbar,
		columns:[[
				{field:'ck',checkbox:true},
				{field:'id',hidden:true},
				{field:'status',hidden:true},
				{field:'code',title:'订单编码',width:120,align:'center'},
		        {field:'name',width:120,title:'订单名称',align:'center'},
				{field:'price',title:'订单金额(元)',width:60,align:'center'},
				{field:'payMode',title:'支付方式',width:100,align:'center',  
		            formatter:function(value,row,index){  
		                var showPaymode = "";
		                if("0"==value)
		                	{
		                		showPaymode = "现金支付";
		                	}
		                else if("1"==value)
		                	{
		                		showPaymode = "转账支付";
		                	}
		                return showPaymode;  
		            }  },
				{field:'creator',title:'创建人',width:70,align:'center'},
				{field:'createTime',title:'创建时间',width:130,align:'center'},
				{field:'operator',title:'操作人',width:70,align:'center'},
				{field:'statusName',title:'状态',width:100,align:'center'},
				{field:'opt',title:'操作',width:200,align:'center',  
		            formatter:function(value,row,index){  
		            	
		            	var roleArr = getLoginuserRole();
		            	var isProxy = roleArr[0];//是否拥有代理角色
		            	var isFinancialManager = roleArr[1];//是否拥有财务管理员角色
		            	var status = row.status;
		                var btn = ''
		                	if(isProxy && ('01'==status||'02'==status))//当前角色为“代理”且订单状态为“代理保存订单”或“财务管理员驳回”时，显示以下按钮
		                		{
		                			btn=btn+'<a class="editcls" onclick="updateOrders(&quot;'+row.id+'&quot;)" href="javascript:void(0)" title="编辑">编辑</a>'//代理编辑
				                	+'<a class="deleterole" onclick="deleteOrders(&quot;'+row.id+'&quot;)" href="javascript:void(0)" title="删除">删除</a>'//代理删除
				                	+'<a class="submitOrder" onclick="approveOrders(&quot;'+row.id+'&quot;,1)" href="javascript:void(0)" title="提交">提交</a>'//代理提交
		                		}
		                	else if(isFinancialManager && '11'==status)//当前角色为“财务管理员”且订单状态为“提交财务管理员审批”时，显示以下按钮
	                			{
	                				btn=btn+'<a class="detailcls" onclick="viewOrdersDetail(&quot;'+row.id+'&quot;)" href="javascript:void(0)" title="查看详情">详情</a>'//财务管理员可以查看订单详情但是不可以修改内容，可以审批
				                	+'<a class="rejectOrder" onclick="approveOrders(&quot;'+row.id+'&quot;,3)" href="javascript:void(0)" title="审批驳回">驳回</a>'//财务管理员驳回
				                	+'<a class="stopOrder" onclick="approveOrders(&quot;'+row.id+'&quot;,4)" href="javascript:void(0)" title="不通过">不通过</a>'//财务管理员不通过，终止订单的审批流程且流程不可恢复
				                	+'<a class="throughOrder" onclick="approveOrders(&quot;'+row.id+'&quot;,2)" href="javascript:void(0)" title="审批通过">通过</a>';//财务管理员审批通过
	                			}
		                		else
	                			{
	                				btn=btn+'<a class="detailcls" onclick="viewPTOrdersDetail(&quot;'+row.id+'&quot;)" href="javascript:void(0)" title="查看详情">详情</a>';//财务管理员可以查看订单详情但是不可以修改内容，可以审批
	                			}
		                	
		                return btn;  
		            }  
		        }  
		    ]],  
	    onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'}); 
	        $('.detailcls').linkbutton({text:'详情',plain:true,iconCls:'icon-edit'}); 
	        $('.submitOrder').linkbutton({text:'提交',plain:true,iconCls:'icon-edit'});  
	        $('.deleterole').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});  
	        $('.rejectOrder').linkbutton({text:'驳回',plain:true,iconCls:'icon-remove'});  
	        $('.throughOrder').linkbutton({text:'通过',plain:true,iconCls:'icon-remove'});
	        $('.stopOrder').linkbutton({text:'不通过',plain:true,iconCls:'icon-remove'});  
	        
	        if(data.rows.length==0){
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="8">没有数据</td></tr>');
			}
	        
	        
	    },
	    rowStyler:function(index,row){//设置行样式
	    		
	    	}
	});
}

/**
 * 处理订单状态
 * @param orderId
 * @param operortype（1：代理提交  2：财管审批通过 3：财管审批驳回 4：财管不通过）
 */
function approveOrders(orderId,operortype)
{
	var data1 = new Object();
	
	data1.operortype = operortype;
	data1.orderId = orderId;
	
	var url = contextPath + '/order/approveOrders.action';
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "post",
        url: url,
        data:data1,
        dataType: "json",
        success: function (data) {
        	initDatagrid();
        	$.messager.alert('提示', data.message);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
   });
}

/**
 * 财管在弹框中审批订单
 * @param operortype
 */
function approveOrdersInDialog(operortype)
{
	var orderId = $("#idD").val();
	approveOrders(orderId, operortype);
	$('#detailOrders').dialog('close');//关闭查看订单详情的dialog
	initDatagrid();
	clearGoodsArray();
}

/**
 * 查看订单详情（财务管理员权限）
 * @param orderId
 */
function viewOrdersDetail(orderId)
{
	clearGoodsArray();
	
	var url = contextPath + '/order/getDetailOrders.action';
	var data1 = new Object();
	data1.id=orderId;//订单id
	
		$.ajax({
			async: false,   //设置为同步获取数据形式
	        type: "get",
	        url: url,
	        data:data1,
	        dataType: "json",
	        success: function (data) {
	        	
					$('#ffDetail').form('load',{
						id:data.id,
						code:data.code,
						name:data.name,
						creator:data.creator,
						price:data.price,
						payMode:data.payMode=='0'?'现金支付':'转账支付',
						receiveAddr:data.receiveAddr,
						receiveTele:data.receiveTele,
//						transCost:data.transCost
						stationId:data.stationId//填充选中订单中站点
					});
					
					//选中当前商品对应产品,并在商品列表加载完成后选中商品
					var returnArr = new Array();
					returnArr = getDetailStation(data.stationId);//rec.id is stationId
					$("#stationD").val(returnArr[3]);//显示选中的站点号
					//根据站点的区域和彩种加载商品信息列表
					initGoodsDetailDatagrid(returnArr[0], returnArr[1], 'goodsDatagridD', returnArr[2],'idD');
			
					
	        },
	        error: function (XMLHttpRequest, textStatus, errorThrown) {
	            alert(errorThrown);
	        }
		});
		
		
	
	$('#detailOrders').dialog('open');//打开查看订单详情的dialog
}

/**
 * 查看普通用户订单详情（普通用户权限）
 * @param orderId
 */
function viewPTOrdersDetail(orderId)
{
	clearGoodsArray();
	
	var url = contextPath + '/order/getDetailOrders.action';
	var data1 = new Object();
	data1.id=orderId;//订单id
	
		$.ajax({
			async: false,   //设置为同步获取数据形式
	        type: "get",
	        url: url,
	        data:data1,
	        dataType: "json",
	        success: function (data) {
	        	
					$('#ffPTDetail').form('load',{
						id:data.id,
						code:data.code,
						name:data.name,
						creator:data.creator,
						price:data.price,
						payMode:data.payMode=='0'?'现金支付':'转账支付',
						receiveAddr:data.receiveAddr,
						receiveTele:data.receiveTele,
//						transCost:data.transCost
						stationId:data.stationId//填充选中订单中站点
					});
					
					//选中当前商品对应产品,并在商品列表加载完成后选中商品
					var returnArr = new Array();
					returnArr = getDetailStation(data.stationId);//rec.id is stationId
					$("#stationPTD").val(returnArr[3]);//显示选中的站点号
					//根据站点的区域和彩种加载商品信息列表
					initGoodsDetailDatagrid(returnArr[0], returnArr[1], 'goodsPTDatagridD', returnArr[2],'idPTD');
			
					
	        },
	        error: function (XMLHttpRequest, textStatus, errorThrown) {
	            alert(errorThrown);
	        }
		});
		
		
	
	$('#detailPTOrders').dialog('open');//打开查看订单详情的dialog
}




/**
 *商品修改
 */
function updateOrders(id)
{
	clearGoodsArray();
	var url = contextPath + '/order/getDetailOrders.action';
	var data1 = new Object();
	data1.id=id;//权限的id
	
		$.ajax({
			async: false,   //设置为同步获取数据形式
	        type: "get",
	        url: url,
	        data:data1,
	        dataType: "json",
	        success: function (data) {
	        	
					$('#ffUpdate').form('load',{
						id:data.id,
						code:data.code,
						name:data.name,
						creator:data.creator,
						price:data.price,
						payMode:data.payMode,
						receiveAddr:data.receiveAddr,
						receiveTele:data.receiveTele,
//						transCost:data.transCost
						station:data.stationId//填充选中订单中站点
					});
					
					initStationList('stationA',data.stationId);
					//选中当前商品对应产品,并在商品列表加载完成后选中商品
					var returnArr = new Array();
					returnArr = getDetailStation(data.stationId);//rec.id is stationId
					//根据站点的区域和彩种加载商品信息列表
					initGoodsDatagrid(returnArr[0], returnArr[1], 'goodsDatagridU', returnArr[2],'0');
			
					
	        },
	        error: function (XMLHttpRequest, textStatus, errorThrown) {
	            alert(errorThrown);
	        }
		});
		
		$("#updateOrders").dialog('open');
		
	
}


/**
 * 获取站点详情
 * @param stationId：站点id
 */
function getDetailStation(stationId)
{
	var returnArr = new Array();
	var url = contextPath + '/station/getStationDetail.action';
	var paramData = new Object();
	paramData.id=stationId;
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        cache:false,
        url: url,
        data:paramData,
        dataType: "json",
        success: function (data) {
			
        	var province = data.addFormProvince;
        	var city = data.addFormCity;
        	var stationType = data.addFormStationStyle;//站点类型：1：体彩 2：福彩
        	var stationNumber = data.addFormStationNumber;//站点号
        	
        	returnArr.push(province);//站点所属省
        	returnArr.push(city);//站点所属市
        	returnArr.push(stationType);//站点类型：1：体彩 2：福彩
        	returnArr.push(stationNumber);//站点类型：1：体彩 2：福彩
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
//            alert(errorThrown);
        }
	});
	
	return returnArr;
}


/**
 * 选中当前订单配置的商品
 * @param id
 */
function checkedGoodsOfOrder(id,productDatagrid)
{
	var data = new Object();
	data.id = id;
	
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: contextPath+'/order/getGoodsOfOrder.action',
        data:data,
        dataType: "json",
        success: function (data) {
        	if(data.length>0)
        		{
        			var selectedRows = $('#'+productDatagrid).datagrid('getRows');
        			for(var i=0;i<data.length;i++)
        				{
        					var goods = data[i];
	        				$.each(selectedRows,function(j,selectedRow){
	        					var	goodId = goods.id;
	        					var price = parseInt(goods.price);
								if(selectedRow.id == goodId)
								{
//									 $('#'+productDatagrid).datagrid('selectRow',j);
									var price = 0;
									var pushFlag = goodListExist(goodId);
									if(pushFlag)
										{
											goodsList.push(goodId);//※每次在操作goodlist的内容时就要同时操作商品总价
											addGoodList(goodId);//添加商品下的产品数据
											price = parseInt(selectedRow.price);//js中将字符串转换为数字
											countPrice = countPrice+price;
											addCountPrice(countPrice);//更新商品总价
										}
									
								}
				        	});
        				}
        			
        		}
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
   });
}


/**
 * 选中当前订单详情配置的商品
 * @param id
 */
function checkedGoodsOfDetailOrder(id,productDatagrid)
{
	var data = new Object();
	data.id = id;
	
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: contextPath+'/order/getGoodsOfOrder.action',
        data:data,
        dataType: "json",
        success: function (data) {
        	if(data.length>0)
        		{
        			var selectedRows = $('#'+productDatagrid).datagrid('getRows');
        			for(var i=0;i<data.length;i++)
        				{
        					var goods = data[i];
	        				$.each(selectedRows,function(j,selectedRow){
	        					var	goodId = goods.id;
	        					var price = parseInt(goods.price);
								if(selectedRow.id == goodId)
								{
//									 $('#'+productDatagrid).datagrid('selectRow',j);
									var price = 0;
									var pushFlag = goodListExist(goodId);
									if(pushFlag)
										{
											goodsList.push(goodId);//※每次在操作goodlist的内容时就要同时操作商品总价
										}
									
								}
				        	});
        				}
        			
        		}
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
   });
}

//填充商品总价显示值
function addCountPrice(cprice)
{
	$("#pricehidden").val(cprice);
	$("#priceA").textbox('setText',cprice);
}

/**
 * 初始化商品列表
 * @param provinceId
 * @param cityId
 * @param productDatagrid
 * @param ischange:是否为站点更新加载（1：是，0：否）
 */
function initGoodsDatagrid(provinceId,cityId,productDatagrid,stationType,ischange)
{
	var params = new Object();
	params.province = provinceId;
	params.city = cityId;
	params.status = '1';//上架商品
	params.stationType = stationType;//站点类型：1：体彩 2：福彩
	
	var idU = $("#idU").val();
	
	var prostaList = new Object() ;
	 if('0'==ischange)
 	{//只有在非更新站点加载数据时重新选中数据
		prostaList = getSproductsOfOrderId(idU);
 	}
	
	
	$('#'+productDatagrid).datagrid({
		singleSelect:false,
		rownumbers:false,
		queryParams: params,
		url:contextPath + '/goods/getGoodsList.action',//'datagrid_data1.json',
		method:'get',
		border:false,
		singleSelect:false,
		fitColumns:true,
		pagination:true,
		collapsible:false,
		pageSize:2,//初始化页面显示条数的值是根据pageList的数组中的值来设置的，否则无法正确设置
		pageList:[2,5,10],
		columns:[[
				{field:'ck',checkbox:true},
				{field:'id',hidden:true},
				{field:'code',title:'商品编码',width:120,align:'center'},
		        {field:'name',width:120,title:'商品名称',align:'center'},
		        {field:'goodType',width:50,title:'彩种',align:'center', 
		            formatter:function(value,row,index){  
		            	var goodTypeName ='';
		            	switch(value)
		            	{
		            		case '1':goodTypeName='体彩';break;
		            		case '2':goodTypeName='福彩';break;
		            		case '0':goodTypeName='双机';break;
		            	}
		            	return goodTypeName;  
		            }  },
				{field:'price',title:'价格(元)',width:80,align:'center'},
				{field:'provinceName',title:'省级区域',width:80,align:'center'},
				{field:'cityName',title:'市级区域',width:80,align:'center'},
				{field:'createTime',title:'创建时间',width:150,align:'center'},
				{field:'goodsDesprition',title:'商品描述',width:120,align:'center'}
		    ]],  
	    onLoadSuccess:function(data){  
	    	//没有返回数据时的填充内容
	        if(data.rows.length==0){
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="8">没有数据</td></tr>');
			}
	        
	       
	        var selectedRows = $('#'+productDatagrid).datagrid('getRows');
	        
	        //获取选中的商品下的产品数据
	        $.each(data.rows,function(j,selectRow){
	        	$('#'+productDatagrid).datagrid('expandRow',j);//将商品的子网格展开
	        	
        	});
        	
	        if('0'==ischange)
	        	{//只有在非更新站点加载数据时重新选中数据
	        	  	//选中（写入后台读取的产品数据进行选中）
	        		checkedGoodsOfOrder(idU,productDatagrid);
	        	}
	      
	        //写入和选中当前数据选中的产品（写入productlist选中的）
	        if(goodsList.length>0)
	        	{
	        		for(var i=0;i<goodsList.length;i++)
	        			{
	        				var	goodId = goodsList[i];
	        				$.each(selectedRows,function(j,selectedRow){
	        					
								if(selectedRow.id == goodId){
									
									 $('#'+productDatagrid).datagrid('selectRow',j);
//									 var price = parseInt(selectedRow.price);//js中将字符串转换为数字
//									 countPrice = countPrice+price;
									
								}
				        	});
	        				
	        			}
//	        		 addCountPrice(countPrice);//更新商品总价
	        	}
	        
	        
	        
	    },
	    onSelect:function(index,row){
			
			var pushFlag = goodListExist(row.id);
			
			var goodType = row.goodType;//获取选中行的彩种，若为"双机"，则要选另一个站点
			if('0' == goodType)
				{
					var selectedFlag = true;//是否可选中
					 var selectedRows = $('#ddv-'+row.id).datagrid('getRows');
		                $.each(selectedRows,function(editIndex,selectedRow){
		                	if(selectedFlag)
		                		{
			                		var stationcombobox = $('#ddv-'+row.id).datagrid('getEditor', {index:editIndex,field:'stationOfPro'});
				                	var productname = $(stationcombobox.target).combobox('getText');
				                	var value = $(stationcombobox.target).combobox('getValue');
				                	if(value==""&&productname==""){
				                		selectedFlag = false;
				                	}
		                		}
		                	
		                });
		                
		               if(!selectedFlag)
		            	   {
			            	   $.messager.alert('提示', "当前选中的商品为双机商品且当前选中站点的站主没有其他可选彩种站点!");
								pushFlag = false;
								$('#'+productDatagrid).datagrid('unselectRow', index);
								$('#'+productDatagrid).datagrid('onUncheck', index);
		            	   }
		               /*else
		            	   {
		            	   		$.messager.alert('提示', "当前选中的商品为双机商品，请配置其他彩种站点!");
		            	   }*/
		               	
		                
					
				}
			
			var price = 0;
			if(pushFlag)
				{
					goodsList.push(row.id);
					addGoodList(row.id);//添加商品下的产品数据
					price = parseInt(row.price);//js中将字符串转换为数字
					countPrice = countPrice+price;
					addCountPrice(countPrice);//更新商品总价
				}
			
		},
		onUnselect:function(index,row){
			var pushFlag = goodListExist(row.id);
			if(!pushFlag)//false为不允许放入list，则表示当前值做过价格处理
				{
					var price = 0;
					price = parseInt(row.price);
					removeGoodList(row.id);
					countPrice = countPrice-price;
					addCountPrice(countPrice);//更新商品总价
				}
			
			
		},
		onUnselectAll:function(rows){
			var price = 0;
			
			for(var i=0;i<rows.length;i++)
			{
				var pushFlag = goodListExist(row.id);
				if(!pushFlag)//false为不允许放入list，则表示当前值做过价格处理
					{
						price = parseInt(rows[i].price);
						removeGoodList(rows[i].id);
						countPrice = countPrice-price;
					}
				
			}
			addCountPrice(countPrice);//更新商品总价
			
		},
		onSelectAll:function(rows){
			var price = 0;
			for(var i=0;i<rows.length;i++)
			{
				price = parseInt(rows[i].price);
				var pushFlag = goodListExist(rows[i].id);
				if(pushFlag)
				{
					goodsList.push(rows[i].id);
					addGoodList(rows[i].id);//添加商品下的产品数据
					countPrice = countPrice+price;
				}
			}
			addCountPrice(countPrice);//更新商品总价
		},
		//创建子网格
		view: detailview,
	    detailFormatter:function(index,row){//注意2  
         return '<div style="padding:2px"><table id="ddv-'+row.id+'"></table></div>';  
        }, 
	    onExpandRow: function(index,row){
	    	var rowIndex = index;
	    	var tableId = row.id;//商品id
	    	 $('#ddv-'+tableId).datagrid({
	    		method:'get',
	            url:contextPath+'/goods/getProductsOfGoods.action?id='+tableId,
	            fitColumns:true,
	            loadMsg:'加载中...',
	            height:'auto',
	            columns:[[
	                {field:'id',hidden:true},//商品和产品关联id
					{field:'goodId',hidden:true},
					{field:'productId',hidden:true},
					{field:'name',width:120,title:'产品名称',align:'center'},
					{field:'lotteryType',width:50,title:'彩种',align:'center',  
			            formatter:function(value,row,index){  
			            	var lotteryTypeName ='';
			            	switch(value)
			            	{
			            		case '1':lotteryTypeName='体彩';break;
			            		case '2':lotteryTypeName='福彩';break;
			            	}
			            	return lotteryTypeName;  
			            }  },
			        {field:'probation',width:50,title:'试用期最大值',align:'center'},
		            {field:'orderprobation',title:'试用期(天)',width:50,align:'center',editor: {  
						type: 'text',  
	                    options: {  
	                        required: true
	                    }  
	                }},
	                {field:'stationOfPro',width:50,title:'站点',align:'center',
	                    editor : {  
		                        type : 'combobox',  
		                        options : {  
			                        valueField:'id',
									textField:'stationNumber',   
		                            panelHeight: 'auto',  
		                            required: false ,  
		                            editable:false  
	                        }  
	                    }  
	                }
	            ]],
	            onResize:function(){
	            	$('#'+productDatagrid).datagrid('fixDetailRowHeight',index);
	            },
	            onLoadSuccess:function(prodata){
	                
	            	//设置商品下产品的试用期和站点下拉框可用，并且设置试用期默认值为'0'
	            	for(var i=0;i<prodata.rows.length;i++)
        			{
	                	 $('#ddv-'+tableId).datagrid('beginEdit', i);
	                	 var editors = $('#ddv-'+tableId).datagrid('getEditors', i);
	                	 editors[0].target.val('0');
	                	 
        			}
	                
	                //绑定editor校验
	                var selectedproRows = $('#ddv-'+tableId).datagrid('getRows');
	                $.each(selectedproRows,function(indexp,row){
	                	var editors = $('#ddv-'+tableId).datagrid('getEditors', indexp);//获取当前行可编辑的值
	                	//校验
	                	var oldValue = row.probation;//当前产品可以设定的最大试用期
	                	
	                	editors[0].target.bind('change',function () {//sellprice校验
	                		
	                		if(editors[0].target.val()>oldValue)
	                			{
	                				$.messager.alert('提示', "产品中第"+(indexp+1)+"行数据的试用期天数大于试用期最大值");
	                				editors[0].target.val('0');
	                			}
			 					
		                		//更新goodlist内容
		                		var pushflag = goodListExist(tableId);
		                		if(!pushflag)
		                			{//当前更改的所属商品id已存在
		                				removeGoodList(tableId);
		                				goodsList.push(tableId);//※每次在操作goodlist的内容时就要同时操作商品总价
										addGoodList(tableId);//添加商品下的产品数据
		                			}
	                		
	                		
			 				});
	                	
	                		//给站点更改绑定级联事件
	                		editors[1].target.combobox({

	                			onChange : function (n,o) {
		                			//更新goodlist内容
			                		var pushflag = goodListExist(tableId);
			                		if(!pushflag)
			                			{//当前更改的所属商品id已存在
			                				removeGoodList(tableId);
			                				goodsList.push(tableId);//※每次在操作goodlist的内容时就要同时操作商品总价
											addGoodList(tableId);//添加商品下的产品数据
			                			}
		                			
		                			}

	                		}); 
	                	
			        	
	                	});
	                
	              //处理站点combobox的默认值选中，这个处理只能放在"站点更改绑定级联事件"的后面，否则无法设定默认值
	                for(var i=0;i<prodata.rows.length;i++)
        			{
	                	//处理站点combobox
						 var stationcombobox = $('#ddv-'+tableId).datagrid('getEditor', {index:i,field:'stationOfPro'});
						 var lotteryType = prodata.rows[i].lotteryType;
						 var sdata = getSelectedOtherStation('stationA',lotteryType);
						 $(stationcombobox.target).combobox( 'loadData' , sdata); 
						 if(sdata.length>0)
							 {
							 	$(stationcombobox.target).combobox( 'setValue' ,sdata[0].id); //默认选中第一项
							 }
        			}
	                
	                //收起子网格再展开时的处理
	                var selectedproRows = $('#ddv-'+tableId).datagrid('getRows');
    				
    				$.each(selectedproRows,function(index,selectedproRow){
    					
     	                var editors = $('#ddv-'+tableId).datagrid('getEditors', index);
    					
    					var rowid = selectedproRow.id;//产品id
    					
    					for(var m=0;m<prostaList.length;m++)
    						{
    							var proAndGId = prostaList[m].productId;
    							var orderProbation = prostaList[m].probation;
    							var stationOfpro = prostaList[m].stationId;
    							if(rowid==proAndGId)
    								{
    									editors[0].target.val(orderProbation);
    									$(editors[1].target).combobox('select',stationOfpro);//使用select也可以给combobox赋值，但是用setValue不会触发级联事件
    								}
    						}
    					
    					
    				});
	                
 	                $('#'+productDatagrid).datagrid('fixRowHeight',index);
		       
	            }
	        });
	        $('#'+productDatagrid).datagrid('fixDetailRowHeight',index);
	        
	    }
	});
}

/**
 * 获取选中的其他彩种的站点的id
 * lotteryType:产品彩种
 */
function getSelectedOtherStation(stationInput,lotteryType)
{
	var otherStation ;
	var returnArr = new Array();
	var currentStation = $("#"+stationInput).combobox('getValue');//当前选中站点
	returnArr = getDetailStation(currentStation);//rec.id is stationId
	var currentStationType = returnArr[2];
	
	if(currentStationType == lotteryType)
		{
			var curMap = new map();
			var arr = new Array();
			var currentStation = $("#"+stationInput).combobox('getValue');//当前选中站点
			var currentStationText = $("#"+stationInput).combobox('getText');//当前选中站点站点号
			curMap.put('id',currentStation);
			curMap.put('stationNumber',currentStationText);
			arr.push(curMap);
			//替换加载editor的值为当前选中站点值
			otherStation = JSON.parse('[{ \"id\": \"'+currentStation+'\", \"stationNumber\": \"'+currentStationText+'\"}]') ;
		}
	else
		{
			otherStation = getOtherStation();//当前选中的station的站主的其他站点
		}
	
	return otherStation;
}

/**
 * 根据当前选中站点的信息获取其另一类别站点
 * @return
 */
function getOtherStation()
{
	var data = new Object();
	
	var selectStation = $("#stationA").combobox('getValue');//获取选中的站点的id
	
	data.id = selectStation;
	
	var returnData ;
	
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "post",
        url: contextPath+'/order/getOtherStations.action',
        data:data,
        dataType: "json",
        success: function (data1) {
        	returnData = data1;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
   });
	return returnData;
}

/**
 * 初始化订单详情中的商品列表
 * @param provinceId
 * @param cityId
 * @param productDatagrid
 */
function initGoodsDetailDatagrid(provinceId,cityId,productDatagrid,stationType,orderId)
{
	var params = new Object();
	params.province = provinceId;
	params.city = cityId;
	params.status = '1';//上架商品
	params.stationType = stationType;//站点类型：1：体彩 2：福彩
	
	 var idU = $("#"+orderId).val();
	var prostaList = getSproductsOfOrderId(idU);
	
	$('#'+productDatagrid).datagrid({
//		singleSelect:false,
		rownumbers:false,
		queryParams: params,
		url:contextPath + '/goods/getGoodsList.action',//'datagrid_data1.json',
		method:'get',
		border:false,
		singleSelect:false,
		fitColumns:true,
		pagination:true,
		collapsible:false,
		pageSize:5,//初始化页面显示条数的值是根据pageList的数组中的值来设置的，否则无法正确设置
		pageList:[5,10],
		columns:[[
//				{field:'ck',checkbox:true},
				{field:'id',hidden:true},
				{field:'code',title:'商品编码',width:120,align:'center'},
		        {field:'name',width:120,title:'商品名称',align:'center'},
				{field:'price',title:'价格(元)',width:80,align:'center'},
				{field:'provinceName',title:'省级区域',width:80,align:'center'},
				{field:'cityName',title:'市级区域',width:80,align:'center'},
				{field:'createTime',title:'创建时间',width:150,align:'center'},
				{field:'goodsDesprition',title:'商品描述',width:120,align:'center'}
		    ]],  
	    onLoadSuccess:function(data){  
	        if(data.rows.length==0){
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="8">没有数据</td></tr>');
			}
	        
	        checkedGoodsOfDetailOrder(idU,productDatagrid);
	        //写入和选中当前数据选中的产品（写入productlist选中的）
	        if(goodsList.length>0)
	        	{
	        		var selectedRows = $('#'+productDatagrid).datagrid('getRows');
	        		for(var i=0;i<goodsList.length;i++)
	        			{
	        				var	goodId = goodsList[i];
	        				$.each(selectedRows,function(j,selectedRow){
	        					
								if(selectedRow.id == goodId){
									
									 $('#'+productDatagrid).datagrid('selectRow',j);
								}
				        	});
	        				
	        				
	        			}
	        	}
	    },
		//创建子网格
		view: detailview,
	    detailFormatter:function(index,row){//注意2  
         return '<div style="padding:2px"><table id="ddv-'+row.id+'"></table></div>';  
        }, 
	    onExpandRow: function(index,row){
	    	var rowIndex = index;
	    	var tableId = row.id;//商品id
	    	 $('#ddv-'+tableId).datagrid({
	    		method:'get',
	            url:contextPath+'/goods/getProductsOfGoods.action?id='+tableId,
	            fitColumns:true,
	            loadMsg:'加载中...',
	            height:'auto',
	            columns:[[
	                {field:'id',hidden:true},//商品和产品关联id
					{field:'goodId',hidden:true},
					{field:'productId',hidden:true},
					{field:'name',width:120,title:'产品名称',align:'center'},
					{field:'lotteryType',width:50,title:'彩种',align:'center',  
			            formatter:function(value,row,index){  
			            	var lotteryTypeName ='';
			            	switch(value)
			            	{
			            		case '1':lotteryTypeName='体彩';break;
			            		case '2':lotteryTypeName='福彩';break;
			            	}
			            	return lotteryTypeName;  
			            }  },
			        {field:'probation',width:50,title:'试用期最大值',align:'center'},
		            {field:'orderprobation',title:'试用期(天)',width:50,align:'center',editor: {  
						type: 'text',  
	                    options: {  
	                        required: true
	                    }  
	                }},
	                {field:'stationOfPro',width:50,title:'站点号',align:'center',
	                    editor : {  
	                    	type: 'text',  
		                    options: {  
		                        required: true
		                    } 
	                    }  
	                }
	            ]],
	            onResize:function(){
	            	$('#'+productDatagrid).datagrid('fixDetailRowHeight',index);
	            },
	            onLoadSuccess:function(prodata){
	                
	                
	                //收起子网格再展开时的处理
	                var selectedproRows = $('#ddv-'+tableId).datagrid('getRows');
	                
    				
    				$.each(selectedproRows,function(index,selectedproRow){
    					var rowid = selectedproRow.id;//产品id
    					
    					for(var m=0;m<prostaList.length;m++)
    						{
    							var proAndGId = prostaList[m].productId;
    							var orderProbation = prostaList[m].probation;
    							var stationOfpro = prostaList[m].stationId;
    							if(rowid==proAndGId)
    								{
	    								$('#ddv-'+tableId).datagrid('beginEdit', index);//先设置为可编辑，用来放置代理设置的试用期
	    		     	                var editors = $('#ddv-'+tableId).datagrid('getEditors', index);
	    		     	                var returnarr = getDetailStation(stationOfpro)
    		    					
    									editors[0].target.val(orderProbation);
    									editors[1].target.val(returnarr[3]);
    									$('#ddv-'+tableId).datagrid('endEdit', index);//关闭编辑
    									
    									
    								}
    						}
    					
    					
    				});
	                
 	                $('#'+productDatagrid).datagrid('fixRowHeight',index);
		       
	            }
	        });
	        $('#'+productDatagrid).datagrid('fixDetailRowHeight',index);
	        
	    }
	        
	});
}



/**
 * goodList中是否有这个值
 * @param value
 */
function goodListExist(value)
{
	var pushFlag = true;
	for(var i=0;i<goodsList.length;i++)
		{
			if(value == goodsList[i])
				{
					pushFlag= false;
				}
		}
	return pushFlag;
}

/**
 * 
 * @param value:放入到goodList的商品id
 */
function addGoodList(value)
{
	var goodArr = new Array();//放入的对象是key是和value的组合
	var selectedproRows = $('#ddv-'+value).datagrid('getRows');
	$.each(selectedproRows,function(j,selectedRow){
			var valueArr =  new Array();
			var productId = selectedRow.productId;
			var goodId = selectedRow.goodId;
			var proAndgoodId = selectedRow.id;
			var lotteryType = selectedRow.lotteryType;
			var editors = $('#ddv-'+value).datagrid('getEditors', j);
			var orderProbation = editors[0].target.val();//形成订单填写的试用期
        	var stationvalue = $(editors[1].target).combobox('getValue');
			
			valueArr.push(productId);//0
			valueArr.push(goodId);//1
			valueArr.push(orderProbation);//2
			valueArr.push(stationvalue);//放入站点号//3
			valueArr.push(lotteryType);//4
			valueArr.push(proAndgoodId);//5//商品和产品关联表id
			
			var proOfgoods = new map();
			proOfgoods.put(productId, valueArr);
			
			goodArr.push(proOfgoods);
		}
	);

	//判断是否存在
	if(!proOfgoods.contain(value))
		{//不包括当前值则可以放入
			proOfgoods.put(value, goodArr);
		}
	
}

/**
 * 移除goodList的内容
 * @param value
 */
function removeGoodList(value)
{
	for(var i=0;i<goodsList.length;i++)
	{
		if(value == goodsList[i])
			{
				goodsList.splice(i,1);
				proOfgoods.remove(value);
			}
	}
}

/**
 * 选中当前商品对应产品
 * @param id
 */
function checkProducts(id,productDatagrid)
{
	var data = new Object();
	data.id = id;
	
	var proList ;
	
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: contextPath+'/goods/getProductsOfGoods.action',
        data:data,
        dataType: "json",
        success: function (returndata) {
        	
        	proList = returndata;
        	
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
   });
	
	return proList;
}

/**
 * 根据订单id获取站点和产品关联数据
 * @param orderId
 */
function getSproductsOfOrderId(orderId)
{
	var data = new Object();
	data.orderId = orderId;
	
	var proStaList ;
	
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "post",
        url: contextPath+'/order/getStationAndProducts.action',
        data:data,
        dataType: "json",
        success: function (returndata) {
        	
        	proStaList = returndata;
        	
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
   });
	
	return proStaList;
}


/**
 * 提交修改商品表单
 */
function submitUpdateOrders(operatype)
{
	$('#ffUpdate').form('submit',{
		url:contextPath+'/order/saveOrUpdate.action',
		onSubmit:function(param){
			var flag = false;
			if($('#ffUpdate').form('enableValidation').form('validate')&& goodsList.length>0)
				{
					flag = true;
					//存入“站点--产品”关联数据
//					var proOfgoods  = new map();
					/*for(var i=0;i<goodsList.length;i++)
						{
							var goodArr = new Array();//放入的对象是key是和value的组合
//							try
//							{
								var selectedproRows = $('#ddv-'+goodsList[i]).datagrid('getRows');
								$.each(selectedproRows,function(j,selectedRow){
										var valueArr =  new Array();
										var productId = selectedRow.productId;
										var goodId = selectedRow.goodId;
										var proAndgoodId = selectedRow.id;
										var lotteryType = selectedRow.lotteryType;
										var editors = $('#ddv-'+goodsList[i]).datagrid('getEditors', j);
										var orderProbation = editors[0].target.val();//形成订单填写的试用期
					                	var stationvalue = $(editors[1].target).combobox('getValue');
										
										valueArr.push(productId);//0
										valueArr.push(goodId);//1
										valueArr.push(orderProbation);//2
										valueArr.push(stationvalue);//放入站点号//3
										valueArr.push(lotteryType);//4
										valueArr.push(proAndgoodId);//5//商品和产品关联表id
										
										var proOfgoods = new map();
										proOfgoods.put(productId, valueArr);
										
										goodArr.push(proOfgoods);
									}
					        	);
//							}
//							catch(e)
//							{
//								//商品没有点击展开设置试用期
//								console.log('商品没有点击展开设置试用期');
//							}
							
							
							proOfgoods.put(goodsList[i], goodArr);
						}*/
				}
			else if(goodsList.length==0)
			{
				$.messager.alert('提示', "请为当前订单选择商品!");
			}
			
			param.goodsList = JSON.stringify(goodsList);
			param.operatype = operatype;//0:保存 1：保存并提交
			param.proList = JSON.stringify(proOfgoods);
			
			return flag;
		},
	    success:function(data){
	    	//提交表单后，从后台返回的data类型为String，要获取信息需要将其转换为json类型，使用eval("(" + data + ")")方法转换
	    	$.messager.alert('提示', eval("(" + data + ")").message);
	    	
	    	$("#updateOrders").dialog('close');
	    	//修改角色后刷新数据列表
	    	clearGoodsArray();
	    	initDatagrid();
	    	
	    }
	});
}

/**
 * 删除商品数据
 * @param id
 */
function deleteOrders(id)
{
	var url = contextPath + '/order/deleteOrders.action';
	var data1 = new Object();
	
	var codearr = [];
	codearr.push(id);
	
	data1.ids=codearr.toString();
		
	if(codearr.length>0)
	{
		var finishFlag = checkOrderFinish(id);
		if(!finishFlag)
			{
				$.messager.confirm("提示", "您确认删除选中数据？", function (r) {  
			        if (r) {  
				        	$.ajax({
				        		async: false,   //设置为同步获取数据形式
				                type: "post",
				                url: url,
				                data:data1,
				                dataType: "json",
				                success: function (data) {
				                	initDatagrid();
				                	$.messager.alert('提示', data.message);
				                },
				                error: function (XMLHttpRequest, textStatus, errorThrown) {
				                    alert(errorThrown);
				                }
				           });
				        	
			        }  
			    });  
			}
		else
			{
				$.messager.alert('提示', "待删除订单已完成审批，不可进行删除操作!");
			}
		
	}
	else
	{
		$.messager.alert('提示',"请选择数据后操作!");
	}
	
}


/**
 * 批量删除商品数据
 */
function deleteOrdersList()
{
	var url = contextPath + '/order/deleteOrders.action';
	var data1 = new Object();
	
	var codearr = new Array();
	var rows = $('#datagrid').datagrid('getSelections');
	
	var deleteFlag = true;
	
	
	for(var i=0; i<rows.length; i++)
	{
		var finishFlag = checkOrderFinish(rows[i].id);
		if(finishFlag)
			{
				deleteFlag = false;
				$.messager.alert('提示', "待删除订单中订单编码为："+rows[i].code+"已完成审批，不可以被删除");
				break;
			}
		codearr.push(rows[i].id);//code
	}
	
	if(deleteFlag)//选中的待删除权限中没有拥有子级权限的权限时可以进行删除操作
		{
			if(codearr.length>0)
			{
				data1.ids=codearr.toString();//将id数组转换为String传递到后台
				
				$.messager.confirm("提示", "您确认删除选中数据？", function (r) {  
			        if (r) {  
			        	
				        	$.ajax({
				        		async: false,   //设置为同步获取数据形式
				                type: "post",
				                url: url,
				                data:data1,
				                dataType: "json",
				                success: function (data) {
				                	initDatagrid();
				                	
				                	$.messager.alert('提示', data.message);
				                	
				                },
				                error: function (XMLHttpRequest, textStatus, errorThrown) {
				                    alert(errorThrown);
				                }
				           });
				        	
			        }  
			    });  
				
			}
			else
			{
				$.messager.alert('提示', "请选择数据后操作!");
			}
		}
}

/*********校验************/

/**
 * 自定义校验商品名称？？？（暂定商品名称校验规则：全局唯一）
 */
$.extend($.fn.validatebox.defaults.rules, {
    checkAname: {//自定义校验name
        validator: function(value,param){
        	var rules = $.fn.validatebox.defaults.rules;  
        	if(value.length==0||value.length>10){  
        		rules.checkAname.message = "当前商品名称不可为空且长度不可以超过10个字符";  
                return false;  
            }
        	else
    		{
        		rules.checkAname.message = "当前商品名称已存在"; 
        		
                return !checkProName($("#"+param[1]).val(),value);
    		}
        	
        }
    }
});

/**
 * 自定义校验商品编码
 */
$.extend($.fn.validatebox.defaults.rules, {
    checkCodes: {//自定义校验code
        validator: function(value,param){
        	var rules = $.fn.validatebox.defaults.rules;  
        	if(value.length==0||value.length>15){  
        		rules.checkCodes.message = "当前商品编码不可为空且长度不可以超过15个字符";  
                return false;  
            }
        	else
    		{
        		rules.checkCodes.message = "当前商品编码已存在"; 
                return !checkCode($("#"+param[1]).val(),value);
    		}
        	
        	
        }
    }
});

/**
 * 校验当前订单是否审批完成
 * @param id
 * @returns {Boolean}
 */
function checkOrderFinish(id)
{
	var flag = false;//当前值可用，不存在
	var data = new Object();
	
	data.id = id;
	
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "post",
        url: contextPath+'/order/checkOrderStatus.action',
        data:data,
        dataType: "json",
        success: function (data) {
        	
        	flag = data.exist;//true:订单审批已完成
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
   });
	
	return flag;
}




/**
 * 校验code,name唯一性
 */
function checkCode(id,code)
{
	var flag = false;//当前值可用，不存在
	var data = new Object();
	
	data.id = id;
	data.code = code;
	
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "post",
        url: contextPath+'/goods/checkCode.action',
        data:data,
        dataType: "json",
        success: function (data) {
        	if(data.exist)//若data.isExist==true,则当前校验值已存在，则不可用使用
        		{
        			flag = true;
        		}
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
   });
	
	return flag;
}

//校验产品名称（暂定规则：全局唯一）
function checkProName(id,name)
{
	var flag = false;//当前值可用，不存在
	var data = new Object();
	
	data.id = id;
	data.name = name;
	
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "post",
        url: contextPath+'/goods/checkGoodsName.action',
        data:data,
        dataType: "json",
        success: function (data) {
        	if(data.exist)//若data.isExist==true,则当前校验值已存在，则不可用使用
        		{
        			flag = true;
        		}
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
   });
	
	return flag;
}


/**
 * 自定义校验商品销售价格
 */
$.extend($.fn.validatebox.defaults.rules, {
    checkSellprice: {//自定义校验sellprice
        validator: function(value,param){
        	var rules = $.fn.validatebox.defaults.rules;  
        	if(value.length==0||value.length>10){  
        		rules.checkSellprice.message = "当前商品销售价格不可为空且长度不可以超过10个字符";  
        		productList.remove(param[0]);//row.id
				$('#'+param[2]).datagrid('unselectRow', param[1]);//productDatagrid,index
                return false;  
            }
        	else
    		{
        		rules.checkSellprice.message = "当前商品销售价格不符合金额的输入规则"; 
        		var flag = checkEditorSellprice(value);
        		if(!flag)
        			{
	        			productList.remove(param[0]);//row.id
	    				$('#'+param[2]).datagrid('unselectRow', param[1]);//productDatagrid,index
        			}
        		
                return flag;
    		}
        	
        	
        }
    }
});

/**
 * 自定义校验商品使用天数
 */
$.extend($.fn.validatebox.defaults.rules, {
    checkProbation: {//自定义校验probation
        validator: function(value,param){
        	var rules = $.fn.validatebox.defaults.rules;  
        	if(value.length==0||value.length>10){  
        		rules.checkProbation.message = "当前商品试用天数不可为空且长度不可以超过10个字符";  
        		productList.remove(param[0]);//row.id
				$('#'+param[2]).datagrid('unselectRow', param[1]);//productDatagrid,index
                return false;  
            }
        	else
    		{
        		rules.checkProbation.message = "当前商品试用天数只可以输入0或正整数"; 
                var flag = checkEditorProbation(value);
        		if(!flag)
        			{
	        			productList.remove(param[0]);//row.id
	    				$('#'+param[2]).datagrid('unselectRow', param[1]);//productDatagrid,index
        			}
        		
                return flag;
    		}
        	
        	
        }
    }
});

/****封装map****/
function map()
{
	this.keys = [];
    this.data = {};
 
    /**
     * 放入一个键值对
     * @param {String} key
     * @param {Object} value
     */
    this.put = function(key, value) {
        if (this.data[key] == null) {
            this.keys.push(key);
        }
        this.data[key] = value;
    };
 
    /**
     * 获取某键对应的值
     * @param {String}  key
     * @return {Object} value
     */
    this.get = function(key) {
        return this.data[key];
    };
 
    /**
     * 是否包含该键
     */
    this.contain = function(key) {
        
        var value = this.data[key];
        if (value)
            return true;
        else
            return false;
    };
    
    this.getKeys = function()
    {
    	return keys;
    };
 
    /**
     * 删除一个键值对
     * @param {String} key
     */
    this.remove = function(key) {
        for(var index=0;index<this.keys.length;index++){
            if(this.keys[index]==key){
                this.keys.splice(index,1);
                break;
            }
        }
        //this.keys.remove(key);
        this.data[key] = null;
    };
}

