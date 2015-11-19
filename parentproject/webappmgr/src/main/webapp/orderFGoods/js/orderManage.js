var goodsList = new Array();//选中的产品数据

$(document).ready(function(){
	
			closeDialog();//页面加载时关闭弹框
			initDatagrid();//初始化数据列表
			bindComboboxChange();//给下拉框绑定选中事件
			
			goodsList = new Array();
		});

/**
 * 清空全局变量
 */
function clearGoodsArray()
{
	goodsList = new Array();
}

/**
 * 绑定上级角色下拉框改变事件
 */
function bindComboboxChange()
{
	/*产品区域级联绑定*/
	//添加表单中的省份级联
	/*$("#privinceA").combobox({

		onSelect: function (rec) {
			 //加载市级数据
			 initCities('add','cityA','',rec.pcode);
			 initProductDatagrid('privinceA','cityA','productDatagridA');//初始化待选择产品列表
		}

		}); 
	//修改表单中的省份级联
	$("#privinceU").combobox({

		onSelect: function (rec) {
			//加载市级数据
			initCities('update','cityU','',rec.pcode);
			initProductDatagrid('privinceU','cityU','productDatagridU');//初始化待选择产品列表
		}

		}); 
	
	//模糊查询“省”级联
	$("#privinceC").combobox({

		onSelect: function (rec) {
			//加载市级数据
			initQueryCities(rec.pcode);
		}

		}); */
	

	
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
	$("#updateOrders").dialog('close');
}

/**
 * 初始化商品列表数据
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
		fitColumns:true,
		pagination:true,
		collapsible:false,
		toolbar:toolbar,
		columns:[[
				{field:'ck',checkbox:true},
				{field:'id',hidden:true},
				{field:'code',title:'订单编码',width:120,align:'left'},
		        {field:'name',width:120,title:'订单名称'},
				{field:'price',title:'订单金额(元)',width:80,align:'left'},
				{field:'payMode',title:'支付方式',width:100,align:'left',  
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
				{field:'creator',title:'创建人',width:100,align:'left'},
				{field:'createTime',title:'创建时间',width:130,align:'left'},
				{field:'operator',title:'操作人',width:120,align:'left'},
				{field:'statusName',title:'状态',width:80,align:'left'},
				{field:'opt',title:'操作',width:160,align:'center',  
		            formatter:function(value,row,index){  
		                var btn = '<a class="editcls" onclick="updateOrders(&quot;'+row.id+'&quot;)" href="javascript:void(0)">编辑</a>'
		                	+'<a class="deleterole" onclick="deleteOrders(&quot;'+row.id+'&quot;)" href="javascript:void(0)">删除</a>';
		                //※(还没做)订单列表还要根据权限显示”提交“和”审批“按钮
		                return btn;  
		            }  
		        }  
		    ]],  
	    onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'}); 
	        $('.deleterole').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});  
	        
	        if(data.rows.length==0){
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="8">没有数据</td></tr>');
			}
	        
	        
	    },
	    rowStyler:function(index,row){//设置行样式
	    		
	    	},
	});
}

/**
 * 初始化选择产品列表
 * @param provinceId
 * @param cityId
 */
function initProductDatagrid(provinceId,cityId,productDatagrid)
{
	var params = new Object();
	params.province = $("#"+provinceId).combobox('getValue');
	params.orCity = $("#"+cityId).combobox('getValue');
	
	
	$('#'+productDatagrid).datagrid({
		singleSelect:false,
		rownumbers:false,
		queryParams: params,
		url:contextPath + '/goods/getProductList.action',//'datagrid_data1.json',
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
		        {field:'name',width:120,title:'产品名称'},
				{field:'code',title:'产品编码',width:120,align:'center'},
				{field:'price',title:'参考价格(元)',width:100,align:'center'},
				{field:'provinceName',title:'省级区域',width:120,align:'center'},
				{field:'cityName',title:'市级区域',width:120,align:'center'},
				{field:'cpdlName',title:'大类别',width:100,align:'center'},
				{field:'cpzlName',title:'中类别',width:100,align:'center'},
//				{field:'cpxlName',title:'小类别',width:100,align:'center'},
//				{field:'createTime',title:'创建时间',width:120,align:'center'},
				{field:'sellPrice',title:'销售价格(元)',width:100,align:'center',editor: {  
                    type: 'text',  
                    options: {  
                        required: true
//                        ,  
//                        validType: ['money']  
                    }  
                }}, 
				{field:'probation',title:'试用期(天)',width:100,align:'center',editor: {  
					type: 'text',  
                    options: {  
                        required: true
//                        ,  
//                        validType: ['number']  
                    }  
                }}
		    ]],  
	    onLoadSuccess:function(data){  
	        if(data.rows.length==0){
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="8">没有数据</td></tr>');
			}
	        else
	        	{
	        		for(var i=0;i<data.rows.length;i++)
	        			{
	        				$('#'+productDatagrid).datagrid('beginEdit', i);
	        			}
	        	}
	        
	        //选中（写入后台读取的产品数据进行选中）
	        var selectedRows = $('#'+productDatagrid).datagrid('getRows');
	        if("productDatagridU" == productDatagrid)//修改商品
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
	        	}
	       
	        //写入和选中当前数据选中的产品（写入productlist选中的）
	        if(productList.keys.length>0)
	        	{
	        		var editvalue = [];
	        		for(var i=0;i<productList.keys.length;i++)
	        			{
	        				editvalue = productList.get(productList.keys[i]);
	        				$.each(selectedRows,function(j,selectedRow){
	        					var	proId = productList.keys[i];
	    			        	var	probation = editvalue[1]; 
	    			        	var	price  = editvalue[0];
				        		
								if(selectedRow.id == proId){
									 var selled = $('#'+productDatagrid).datagrid('getEditor', {index:j,field:'sellPrice'});
									 var probationed = $('#'+productDatagrid).datagrid('getEditor', {index:j,field:'probation'});
									 selled.target.val(price);//※只有当editor的type是text的时候才可以设置值成功
									 probationed.target.val(probation);
									 $('#'+productDatagrid).datagrid('selectRow',j);
								}
				        	});
	        			}
	        	}
	        
	        //给每一行绑定校验事件
	        $.each(selectedRows,function(index,row){
		        	 var editors = $('#'+productDatagrid).datagrid('getEditors', index);//获取当前行可编辑的值
		 			//校验
		 			editors[0].target.bind('change',function () {//sellprice校验
		 				
		 				var oldvalue = [];
		 				if(checkEditorSellprice(editors[0].target.val()))
		 					{//符合输入的金额校验
		 					  oldvalue = productList.get(row.id);
		 					  if(null != oldvalue)
		 						  {
		 						  	oldvalue[0] = editors[0].target.val();
		 						  }
		 					  
		 					}
		 				else
		 					{
		 						$.messager.alert('提示', "产品列表中第"+(index+1)+"行数据的销售价格不符合金额的输入规则");
//		 						productList.remove(row.id);
		 						$('#'+productDatagrid).datagrid('unselectRow', index);
		 					}
		 				
		 			});
		 			editors[1].target.bind('change',function () {//probation校验
	
		 				var oldvalue = [];
		 				if(checkEditorProbation(editors[1].target.val()))
		 					{//符合输入的金额校验
		 					  oldvalue = productList.get(row.id);
		 					  if(null != oldvalue)
	 						  {
		 						 oldvalue[1] = editors[1].target.val();
	 						  }
		 					  
		 					}
		 				else
		 					{
		 						$.messager.alert('提示', "产品列表中第"+(index+1)+"行数据的试用天数应输入0或正整数");
//		 						productList.remove(row.id);
		 						$('#'+productDatagrid).datagrid('unselectRow', index);
		 					}
		 			});
		        	
	    	});
	       
	        
	        
	    },
	   /* onClickRow:function(index,row){
			var editors = $('#'+productDatagrid).datagrid('getEditors', index);//获取当前行可编辑的值
			
		},*/
		onCheck:function(index,row){
			//获取选中行的填写值
			var editors = $('#'+productDatagrid).datagrid('getEditors', index);//获取当前行可编辑的值
			var errRow = index;
			if(null != editors && ""!=editors)
				{
					if("" == editors[0].target.val() || "" == editors[1].target.val())
					{
						$.messager.alert('提示', "请填写选中产品的销售价格和试用期!");
						$('#'+productDatagrid).datagrid('unselectRow', index);
					}
					else
					{
						var pushFlag = false;
						if(!productList.contain(row.id))//新选中的产品数据
							{
								var editvalue = [];//当前选中行的编辑值
								var pushFlag = true;
								if(checkEditorSellprice(editors[0].target.val()))
								{//符合输入的金额校验
									editvalue.push(editors[0].target.val());//放置sellprice销售价格
									
									if(checkEditorProbation(editors[1].target.val()))
									{//符合输入的金额校验
										editvalue.push(editors[1].target.val());//放置probation试用期
									}
									else
									{
										pushFlag = false;
										$.messager.alert('提示', "1产品列表中第"+(errRow+1)+"行数据的试用天数应输入0或正整数");
									}
								}
								else
								{
									pushFlag = false;
									$.messager.alert('提示', "1产品列表中第"+(errRow+1)+"行数据的销售价格不符合金额的输入规则");
								}
								
								
								
								if(pushFlag)
									{
										productList.put(row.id, editvalue);
									}
								else
									{
										$('#'+productDatagrid).datagrid('unselectRow', index);
									}
								

								
							}
						else//已选择的产品数据
							{
								/*//校验
								editors[0].target.bind('blur',function () {//sellprice校验
									
									var oldvalue = [];
									if(checkEditorSellprice(editors[0].target.val()))
										{//符合输入的金额校验
										  oldvalue = productList.get(row.id);
										  oldvalue[0] = editors[0].target.val();
										}
									else
										{
											$.messager.alert('提示', "2产品列表中第"+(errRow+1)+"行数据的销售价格不符合金额的输入规则");
											productList.remove(row.id);
											$('#'+productDatagrid).datagrid('unselectRow', index);
										}
									
								});
								editors[1].target.bind('blur',function () {//probation校验
	
									var oldvalue = [];
									if(checkEditorProbation(editors[1].target.val()))
										{//符合输入的金额校验
										  oldvalue = productList.get(row.id);
										  oldvalue[1] = editors[1].target.val();
										}
									else
										{
											$.messager.alert('提示', "2产品列表中第"+(errRow+1)+"行数据的试用天数应输入0或正整数");
											productList.remove(row.id);
											$('#'+productDatagrid).datagrid('unselectRow', index);
										}
								});*/
							}
					}
				}
			
		},
		onUncheck:function(index,row){
			productList.remove(row.id);
		}
	});
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
						receiveTele:data.receiveTele
//						transCost:data.transCost
					});
					
					
					
			
	        	
	        },
	        error: function (XMLHttpRequest, textStatus, errorThrown) {
	            alert(errorThrown);
	        }
		});
		
		//选中当前商品对应产品
//		checkProducts(id,'productDatagridU');
		
		$("#updateOrders").dialog('open');
	
		
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
 * 提交修改商品表单
 */
function submitUpdateOrders(operatype)
{
	$('#ffUpdate').form('submit',{
		url:contextPath+'/order/saveOrUpdate.action',
		onSubmit:function(param){
//			param.goodsList = JSON.stringify(goodsList);
			param.operatype = operatype;//0:保存 1：保存并提交
			var flag = false;
			if($('#ffUpdate').form('enableValidation').form('validate'))// && goodsList.keys.length>0
				{
					flag = true;
				}
			else if(productList.keys.length==0)
			{
				$.messager.alert('提示', "请为当前订单选择商品!");
			}
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

