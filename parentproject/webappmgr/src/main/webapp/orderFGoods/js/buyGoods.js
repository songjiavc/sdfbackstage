var goodsList = new Array();//选中的商品数据
var countPrice = 0;//选中商品总价
$(document).ready(function(){
	
			initGoodsDatagrid('210000', 'all', 'goodsDatagridU','1');
			initStationList();//加载站点列表（TODO:从后台获取当前登录用户的信息，获取其下属的站点列表数据）
			clearGoodsArray();
			
			generateCode();
			
			bindStationCombobox();//为站点下拉框绑定级联事件
			
		});

/**
 * 为站点下拉框绑定级联事件
 */
function bindStationCombobox()
{
	$("#stationA").combobox({

		onSelect: function (rec) {
			if(null != rec.id && '' != rec.id)
			{
				var returnArr = new Array();
				returnArr = getDetailStation(rec.id);//rec.id is stationId
				//根据站点的区域和彩种加载商品信息列表
				initGoodsDatagrid(returnArr[0], returnArr[1], 'goodsDatagridU', returnArr[2]);		
			}
			
		}

		}); 
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
        	
        	returnArr.push(province);//站点所属省
        	returnArr.push(city);//站点所属市
        	returnArr.push(stationType);//站点类型：1：体彩 2：福彩
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
	});
	
	return returnArr;
}

/**
 * 初始化站点下拉框（加载站点的约束条件：1.上级代理下属的所有站点）
 */
function initStationList()
{
	var data = new Object();
	
	$('#stationA').combobox({
		queryParams:data,
		url:contextPath+'/order/getStationList.action',
		valueField:'id',
		textField:'stationNumber',
		 onLoadSuccess: function (data1) { //数据加载完毕事件
             if (data1.length > 0 ) 
             {//默认选中第一个站点
            	 $('#stationA').combobox('select',data1[data1.length-1].ccode);
             }
            
				
         }
	}); 
}

/**
 * 清空全局变量
 */
function clearGoodsArray()
{
	goodsList = new Array();
	countPrice = 0;//清零商品总价
}

/**
 * 生成订单编码
 */
function generateCode()
{
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "post",
        url: contextPath+'/order/generateOrdercode.action',
        dataType: "json",
        success: function (data) {
        	
        	var orderCode = data.code;
        	$("#codeA").textbox('setText',orderCode);
        	$("#codehidden").val(orderCode);
        	var creator = data.operator;
        	$("#creatorA").textbox('setText',creator);
        	
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
   });
}


/**
 * 初始化选择商品列表（加载条件：1.区域（根据选中的站点的区域值）2.彩种（根据选中的站点的彩种--》加载当前彩种和双机商品））
 * @param provinceId:选中站点的省id
 * @param cityId:选中站点的市id
 */
function initGoodsDatagrid(provinceId,cityId,productDatagrid,stationType)
{
	var params = new Object();
	params.province = provinceId;
	params.city = cityId;
	params.status = '1';//上架商品
	params.stationType = stationType;//站点类型：1：体彩 2：福彩
	
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
		pageSize:5,//初始化页面显示条数的值是根据pageList的数组中的值来设置的，否则无法正确设置
		pageList:[5,10],
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
	        if(data.rows.length==0){
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="8">没有数据</td></tr>');
			}
	        
	        //选中（写入后台读取的产品数据进行选中）
	        var selectedRows = $('#'+productDatagrid).datagrid('getRows');
	        
	        $.each(selectedRows,function(j,selectRow){
	        	$('#'+productDatagrid).datagrid('expandRow',j);//将子网格展开
	        	
        	});
	       
	        //写入和选中当前数据选中的产品（写入productlist选中的）
	        if(goodsList.length>0)
	        	{
	        		for(var i=0;i<goodsList.length;i++)
	        			{
	        				$.each(selectedRows,function(j,selectedRow){
	        					var	goodId = goodsList[i];
								if(selectedRow.id == goodId){
									
									 $('#'+productDatagrid).datagrid('selectRow',j);
								}
				        	});
	        			}
	        	}
	        
	       
	        
	        
	    },
	    onSelect:function(index,row){
			
			var pushFlag = goodListExist(row.id);
			var price = 0;
			if(pushFlag)
				{
					goodsList.push(row.id);
					price = parseInt(row.price);//js中将字符串转换为数字
					countPrice = countPrice+price;
				}
			addCountPrice(countPrice);//更新商品总价
		},
		onUnselect:function(index,row){
			var price = 0;
			price = parseInt(row.price);
			removeGoodList(row.id);
			countPrice = countPrice-price;
			addCountPrice(countPrice);//更新商品总价
			
		},
		onUnselectAll:function(rows){
			var price = 0;
			
			for(var i=0;i<rows.length;i++)
			{
				price = parseInt(rows[i].price);
				removeGoodList(rows[i].id);
				countPrice = countPrice-price;
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
					countPrice = countPrice+price;
				}
			}
			addCountPrice(countPrice);//更新商品总价
		},
		//创建子网格
		view: detailview,
	    detailFormatter:function(index,row){//注意2  
         return '<div style="padding:2px"><table id="ddv-' + row.id + '"></table></div>';  
        }, 
	    onExpandRow: function(index,row){
	    	
	    	var tableId = row.id;
	    	 $('#ddv-'+tableId).datagrid({
	    		method:'get',
	            url:contextPath+'/goods/getProductsOfGoods.action?id='+row.id,
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
		            {field:'orderProbation',title:'试用期(天)',width:50,align:'center',editor: {  
						type: 'text',  
	                    options: {  
	                        required: true
	                    }  
	                }}
	            ]],
	            onResize:function(){
	            	$('#'+productDatagrid).datagrid('fixDetailRowHeight',index);
	            },
	            onLoadSuccess:function(prodata){
	                
	                for(var i=0;i<prodata.rows.length;i++)
        			{
	                	 $('#ddv-'+tableId).datagrid('beginEdit', i);
	                	 var probationed = $('#ddv-'+tableId).datagrid('getEditor', {index:i,field:'orderProbation'});
						 probationed.target.val('0');
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
			 				
			 				});
			        	
	                	});
	                $('#'+productDatagrid).datagrid('fixRowHeight',index);
		       
	            }
	        });
	        $('#'+productDatagrid).datagrid('fixDetailRowHeight',index);
	        
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
			}
	}
}




/**
 * 提交保存商品表单
 */
function submitAddgoods(operatype)
{
	$('#ff').form('submit',{
		url:contextPath+'/order/saveOrUpdate.action',
		onSubmit:function(param){
			var flag = false;
			if($('#ff').form('enableValidation').form('validate')&& goodsList.length>0 )
				{
					flag = true;
					//存入“站点--产品”关联数据
					
					var proOfgoods  = new map();
					for(var i=0;i<goodsList.length;i++)
						{
							var goodArr = new Array();//放入的对象是key是和value的组合
							try
							{
								var selectedproRows = $('#ddv-'+goodsList[i]).datagrid('getRows');
								
								$.each(selectedproRows,function(j,selectedRow){
										var valueArr =  new Array();
										var productId = selectedRow.productId;
										var goodId = selectedRow.goodId;
										var proAndgoodId = selectedRow.id;
										var lotteryType = selectedRow.lotteryType;
										var orderProbationRow = $('#ddv-'+goodsList[i]).datagrid('getEditor', {index:j,field:'orderProbation'});
										var orderProbation = orderProbationRow.target.val();//形成订单填写的试用期
										
										valueArr.push(productId);//0
										valueArr.push(goodId);//1
										valueArr.push(orderProbation);//2
										valueArr.push('010102');//放入站点号//3
										valueArr.push(lotteryType);//4
										valueArr.push(proAndgoodId);//5//商品和产品关联表id
										
										var proOfgoods = new map();
										proOfgoods.put(productId, valueArr);
										
										goodArr.push(proOfgoods);
									}
					        	);
							}
							catch(e)
							{
								//商品没有点击展开设置试用期
								console.log('商品没有点击展开设置试用期');
							}
							
							
							proOfgoods.put(goodsList[i], goodArr);
						}
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
	    	
	    	//添加角色后刷新数据列表
	    	$('#ff').form('clear');//清空表单内容
	    	clearGoodsArray();
	    	initDatagrid();
	    	$('#payModeA').combobox("setValue",'0');
	    	
	    	
	    }
	});
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

//暂定：订单名称不需唯一
function checkOrderName(id,name)
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
        		goodsList.remove(param[0]);//row.id
				$('#'+param[2]).datagrid('unselectRow', param[1]);//productDatagrid,index
                return false;  
            }
        	else
    		{
        		rules.checkSellprice.message = "当前商品销售价格不符合金额的输入规则"; 
        		var flag = checkEditorSellprice(value);
        		if(!flag)
        			{
        				goodsList.remove(param[0]);//row.id
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
        		goodsList.remove(param[0]);//row.id
				$('#'+param[2]).datagrid('unselectRow', param[1]);//productDatagrid,index
                return false;  
            }
        	else
    		{
        		rules.checkProbation.message = "当前商品试用天数只可以输入0或正整数"; 
                var flag = checkEditorProbation(value);
        		if(!flag)
        			{
        			goodsList.remove(param[0]);//row.id
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

