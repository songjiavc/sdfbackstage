var goodsList = new Array();//选中的商品数据
var countPrice = 0;//选中商品总价
$(document).ready(function(){
	
			initGoodsDatagrid('210000', 'all', 'goodsDatagridU');
			clearGoodsArray();
			
			generateCode();
			
		});

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
 * 初始化选择产品列表
 * @param provinceId:选中站点的省id
 * @param cityId:选中站点的市id
 */
function initGoodsDatagrid(provinceId,cityId,productDatagrid)
{
	var params = new Object();
	params.province = provinceId;
	params.city = cityId;
	params.status = '1';//上架商品
	
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
				{field:'id',hidden:true},
				{field:'code',title:'商品编码',width:120,align:'left'},
		        {field:'name',width:120,title:'商品名称'},
				{field:'price',title:'价格(元)',width:80,align:'left'},
				{field:'provinceName',title:'省级区域',width:100,align:'left'},
				{field:'cityName',title:'市级区域',width:100,align:'left'},
				{field:'createTime',title:'创建时间',width:130,align:'left'},
				{field:'goodsDesprition',title:'商品描述',width:120,align:'left'}
		    ]],  
	    onLoadSuccess:function(data){  
	        if(data.rows.length==0){
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="8">没有数据</td></tr>');
			}
	       /* else
	        	{
	        		for(var i=0;i<data.rows.length;i++)
	        			{
	        				$('#'+productDatagrid).datagrid('beginEdit', i);
	        			}
	        	}*/
	        
	        //选中（写入后台读取的产品数据进行选中）
	        var selectedRows = $('#'+productDatagrid).datagrid('getRows');
	       /* if("productDatagridU" == productDatagrid)//修改商品
	        	{
		            var  pdList = checkProducts(params.goodsId,productDatagrid);
			        for(var i=0;i<pdList.length;i++)
					{
			        	var	proId = pdList[i].productId;
			        	var	probation = pdList[i].probation; 
			        	var	price  = pdList[i].price;
			        	
			        	$.each(selectedRows,function(j,selectedRow){
			        			
			        		
							if(selectedRow.id == proId){
								 var selled = $('#'+productDatagrid).datagrid('getEditor', {index:j,field:'sellPrice'});
								 var probationed = $('#'+productDatagrid).datagrid('getEditor', {index:j,field:'probation'});
								 selled.target.val(price);//※只有当editor的type是text的时候才可以设置值成功
								 probationed.target.val(probation);
								 $('#'+productDatagrid).datagrid('selectRow',j);
							}
			        	});
					}
	        	}*/
	       
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
			param.goodsList = JSON.stringify(goodsList);
			param.operatype = operatype;//0:保存 1：保存并提交
			var flag = false;
			if($('#ff').form('enableValidation').form('validate')&& goodsList.length>0 )
				{
					flag = true;
				}
			else if(goodsList.length==0)
				{
					$.messager.alert('提示', "请为当前订单选择商品!");
				}
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

