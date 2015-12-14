$(document).ready(function(){
	
			closeDialog();//页面加载时关闭弹框
			initDatagrid();//初始化数据列表
			initQueryProvince();//初始化模糊查询省数据
			bindComboboxChange();//给下拉框绑定选中事件
		});

/**
 * 绑定上级角色下拉框改变事件
 */
function bindComboboxChange()
{
	/*产品区域级联绑定*/
	//添加表单中的省份级联
	$("#privinceA").combobox({

		onSelect: function (rec) {
			 //加载市级数据
			 initCities('add','cityA','',rec.pcode);
		}

		}); 
	//修改表单中的省份级联
	$("#privinceU").combobox({

		onSelect: function (rec) {
			//加载市级数据
			initCities('update','cityU','',rec.pcode);
		}

		}); 
	
	//模糊查询“省”级联
	$("#privinceC").combobox({

		onSelect: function (rec) {
			//加载市级数据
			initQueryCities(rec.pcode);
		}

		}); 
	
	/*产品三个类别的级联绑定*/
	$("#cpdlA").combobox({
		onSelect: function (rec) {
				//加载产品中类数据
				initProductZL("add", "cpzlA", '', rec.code);
				//加载产品小类数据
				initProductXL("add", "cpxlA", '', rec.code, '');
			}
		});
	$("#cpzlA").combobox({
		onSelect: function (rec) {
			//加载产品小类数据
			var checkProDlcode = $("#cpdlA").combobox('getValue');//当前选中的产品大类值
			initProductXL("add", "cpxlA", '', checkProDlcode, rec.code);
			}
		});
	
	$("#cpdlU").combobox({
		onSelect: function (rec) {
				//加载产品中类数据
				initProductZL("update", "cpzlU", '', rec.code);
				//加载产品小类数据
				initProductXL("update", "cpxlU", '', rec.code, '');
			}
		});
	$("#cpzlU").combobox({
		onSelect: function (rec) {
			//加载产品小类数据
			var checkProDlcode = $("#cpdlU").combobox('getValue');//当前选中的产品大类值
			initProductXL("update", "cpxlU", '', checkProDlcode, rec.code);
			}
		});
	
	
}

/**
 * 初始化模糊查询“省”下拉框数据
 */
function initQueryProvince()
{
	$('#privinceC').combobox('clear');//清空combobox值
	
	var data = new Object();
	data.isHasall = true;//包含"全部"
	
	$('#privinceC').combobox({
			queryParams:data,
			url:contextPath+'/product/getProvinceList.action',
			valueField:'pcode',
			textField:'pname',
			 onLoadSuccess: function (data1) { //数据加载完毕事件
				 $('#privinceC').combobox('select',data1[data1.length-1].pcode);
					
             }
		}); 
}

/**
 * 根据选中的“省”初始化模糊查询“市”数据
 * @param proId
 */
function initQueryCities(proId)
{
	$('#cityC').combobox('clear');//清空combobox值
	var data = new Object();
	data.isHasall = true;//包含"全部"
	
	data.pcode = proId;
	
	$('#cityC').combobox({
			queryParams:data,
			url:contextPath+'/product/getCityList.action',
			valueField:'ccode',
			textField:'cname',
			 onLoadSuccess: function (data1) { //数据加载完毕事件
				 $('#cityC').combobox('select',data1[data1.length-1].ccode);
					
             }
		}); 
}
/**
 * 初始化省数据
 * @param addOrUpdate:标记当前是添加表单操作还是修改表单的操作,值为"add" 和"update"
 * @param provinceId:当前操作的省份combobox标签的id
 * @param pcode:应该选中的省份的code
 */
function initProvince(addOrUpdate,provinceId,pcode)
{
	$('#'+provinceId).combobox('clear');//清空combobox值
	
	var data = new Object();
	data.isHasall = false;//不包含"全部"
	
	$('#'+provinceId).combobox({
			queryParams:data,
			url:contextPath+'/product/getProvinceList.action',
			valueField:'pcode',
			textField:'pname',
			 onLoadSuccess: function (data1) { //数据加载完毕事件
                 if (data1.length > 0 && "add" == addOrUpdate) 
                 {
                	 $("#"+provinceId).combobox('select',data1[0].pcode);
                 }
                 else
            	 {
                	//使用“setValue”设置选中值不会触发绑定事件导致多次加载市级数据，否则会多次触发产生错误
            	 	$("#"+provinceId).combobox('setValue', pcode);
            	 }
					
             }
		}); 
}

/**
 * 初始化市数据
 * @param addOrUpdate:标记当前是添加表单操作还是修改表单的操作,值为"add" 和"update"
 * @param cityId:当前操作的form表单的id
 * @param ccode:应该选中的市数据code
 * @param pcode:级联的上级省code
 */
function initCities(addOrUpdate,cityId,oldccode,pcode)
{
	$('#'+cityId).combobox('clear');//清空combobox值
	
	var data = new Object();
	data.isHasall = true;
	data.pcode = pcode;
	
	$('#'+cityId).combobox({
			queryParams:data,
			url:contextPath+'/product/getCityList.action',
			valueField:'ccode',
			textField:'cname',
			 onLoadSuccess: function (data1) { //数据加载完毕事件
                 if (data1.length > 0 && "add" == addOrUpdate) 
                 {
                	 $("#"+cityId).combobox('select',data1[data1.length-1].ccode);
                 }
                 else
            	 {
                	 
                	 if(data1.length > 0 &&"update" == addOrUpdate&&"" == oldccode)
                		 {//在修改表单中级联加载市级数据时也要默认选中全部
                		 	$("#"+cityId).combobox('select',data1[data1.length-1].ccode);
                		 }
                	 else
                		 {//当修改产品初始化市级数据时设置选中当前数据值
                		 	$("#"+cityId).combobox('select', oldccode);
                		 }
            	 }
					
             }
		}); 
}

/**
 * 
 * @param addOrUpdate:添加/修改
 * @param productDLId:当前操作的标签id
 * @param oldcode:当前选中值
 */
function initProductDL(addOrUpdate,productDLId,oldcode)
{
	$('#'+productDLId).combobox('clear');//清空combobox值
	
	var data = new Object();
	data.isHasall = false;//不包含"全部"
	
	$('#'+productDLId).combobox({
			queryParams:data,
			url:contextPath+'/product/getProductDlList.action',
			valueField:'code',
			textField:'name',
			 onLoadSuccess: function (data1) { //数据加载完毕事件
                 if (data1.length > 0 && "add" == addOrUpdate) 
                 {
                	 $("#"+productDLId).combobox('select',data1[0].code);
                 }
                 else
            	 {
                	//使用“setValue”设置选中值不会触发绑定事件导致多次加载产品大类数据，否则会多次触发产生错误
            	 	$("#"+productDLId).combobox('setValue', oldcode);
            	 }
					
             }
		}); 
}

/**
 * 
* @param addOrUpdate:添加/修改
 * @param productZLId:当前操作的标签id
 * @param oldcode:之前选中的值
 * @param dlcode:上级大类code
 */
function initProductZL(addOrUpdate,productZLId,oldcode,dlcode)
{
	$('#'+productZLId).combobox('clear');//清空combobox值
	
	var data = new Object();
	
	data.dlCode = dlcode;
	
	$('#'+productZLId).combobox({
			queryParams:data,
			url:contextPath+'/product/getProductZList.action',
			valueField:'code',
			textField:'name',
			 onLoadSuccess: function (data1) { //数据加载完毕事件
                 if (data1.length > 0 && "add" == addOrUpdate) 
                 {
                	 $("#"+productZLId).combobox('select',data1[data1.length-1].code);
                 }
                 else
            	 {
                	 
                	 if(data1.length > 0 &&"update" == addOrUpdate&&"" == oldcode)
                		 {//在修改表单中级联加载产品中类数据时也要默认选中全部
                		 	$("#"+productZLId).combobox('select',data1[data1.length-1].code);
                		 }
                	 else
                		 {//当修改产品初始化产品中类数据时设置选中当前数据值
                		 	$("#"+productZLId).combobox('setValue', oldcode);
                		 }
            	 }
					
             }
		}); 
}

/**
 * 
 * @param addOrUpdate:添加/修改
 * @param productZLId:当前操作的标签id
 * @param oldcode:之前选中的值
 * @param dlcode:上级大类code
 * @param zlcode:上级中类code
 */
function initProductXL(addOrUpdate,productXLId,oldcode,dlcode,zlcode)
{
	$('#'+productXLId).combobox('clear');//清空combobox值
	
	var data = new Object();
	
	data.dlCode = dlcode;
	data.zlCode = zlcode;
	
	$('#'+productXLId).combobox({
			queryParams:data,
			url:contextPath+'/product/getProductXList.action',
			valueField:'code',
			textField:'name',
			 onLoadSuccess: function (data1) { //数据加载完毕事件
                 if (data1.length > 0 && "add" == addOrUpdate) 
                 {
                	 $("#"+productXLId).combobox('select',data1[data1.length-1].code);
                 }
                 else
            	 {
                	 
                	 if(data1.length > 0 &&"update" == addOrUpdate&&"" == oldcode)
                		 {//在修改表单中级联加载产品中类数据时也要默认选中全部
                		 	$("#"+productXLId).combobox('select',data1[data1.length-1].code);
                		 }
                	 else
                		 {//当修改产品初始化产品中类数据时设置选中当前数据值
                		 	$("#"+productXLId).combobox('setValue', oldcode);
                		 }
            	 }
					
             }
		}); 
}

/**
 * 重置
 */
function reset()
{
	//重置产品名称
	$("#productNameC").val("");
	//重置产品编码
	$("#procodeC").val("");
	//重置省份选中“全部”
	var proAllId = getProvinceAllId();
	$("#privinceC").combobox("select",proAllId);
}

/**
 * 获取省份的“全部”选项code
 * @returns {String}
 */
function getProvinceAllId()
{
	var url = contextPath + '/menu/getConstant.action';
	var data1 = new Object();
	data1.constantName='PROVINCE_ALL';//省“全部”编码
	
	var proAllId = '';
	
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: url,
        data:data1,
        dataType: "json",
        success: function (data) {
        	
        	proAllId = data.message;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/error.jsp";
        }
   });
	
	return proAllId;
}



//关闭弹框
function closeDialog()
{
	$("#addProduct").dialog('close');//初始化添加角色弹框关闭
	$("#updateProduct").dialog('close');
}


function initDatagrid()
{
	var params = new Object();
	params.productName = $("#productNameC").val().trim();
	params.procode = $("#procodeC").val().trim();
	params.province = $("#privinceC").combobox('getValue');
	params.city = $("#cityC").combobox('getValue');
	
	$('#datagrid').datagrid({
		singleSelect:false,
		rownumbers:false,
		queryParams: params,
		url:contextPath + '/product/getProductList.action',//'datagrid_data1.json',
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
		        {field:'name',width:120,title:'产品名称',align:'center'},
				{field:'code',title:'产品编码',width:150,align:'center'},
				{field:'price',title:'参考价格(元)',width:80,align:'center'},
				{field:'provinceName',title:'省级区域',width:70,align:'center'},
				{field:'cityName',title:'市级区域',width:70,align:'center'},
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
				{field:'cpdlName',title:'大类别',width:80,align:'center'},
				{field:'cpzlName',title:'中类别',width:80,align:'center'},
				{field:'cpxlName',title:'小类别',width:80,align:'center'},
				{field:'durationOfUsername',title:'使用期',width:80,align:'center'},
				{field:'createTime',title:'创建时间',width:140,align:'center'},
					{field:'opt',title:'操作',width:160,align:'center',  
			            formatter:function(value,row,index){  
			                var btn = '<a class="editcls" onclick="updateProduct(&quot;'+row.id+'&quot;)" href="javascript:void(0)">编辑</a>'
			                	+'<a class="deleterole" onclick="deleteProduct(&quot;'+row.id+'&quot;)" href="javascript:void(0)">删除</a>';
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
	        
	    }  
	});
}

/**
 *产品修改
 */
function updateProduct(id)
{
	var url = contextPath + '/product/getDetailProduct.action';
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
						lotteryType:data.lotteryType,
						proUrl:data.prourl,//软件产品的访问url
						price:data.price,
						privince:data.privince,
						cpdl:data.cpdlDm,
						cpzl:data.cpzlDm,
						cpxl:data.cpxlDm,
						durationOfuser:data.durationOfuser,//产品使用期
						productDesprition:data.productDesprition,
						city:data.city
					});
					
					//初始化省份combobox
					initProvince("update", "privinceU", data.provinceDm);
					//初始化市级区域combobox
					initCities('update','cityU',data.cityDm,data.provinceDm);
					//初始化产品大类
					initProductDL("update", "cpdlU", data.cpdlDm);
					//初始化产品中类
					initProductZL("update", "cpzlU", data.cpzlDm, data.cpdlDm);
					//初始化产品大类
					initProductXL("update", "cpxlU", data.cpxlDm, data.cpdlDm, data.cpzlDm);
					//初始化产品使用期选择框
					initProDuration("durationOfuserU",data.durationOfuser);
			
	        	
	        },
	        error: function (XMLHttpRequest, textStatus, errorThrown) {
	            window.parent.location.href = contextPath + "/error.jsp";
	        }
		});
		
		$("#updateProduct").dialog('open');
	
}

/**
 * 初始化产品使用期
 * @param durationId
 * @param durationOfuser
 */
function initProDuration(durationId,durationOfuser)
{
	var data = new Object();
	
	$('#'+durationId).combobox({
		queryParams:data,
		url:contextPath+'/product/getCodedurationlist.action',
		valueField:'id',
		textField:'name',
		 onLoadSuccess: function (data1) { //数据加载完毕事件
			
			 if('durationOfuserA' == durationId)
				 {//添加产品
				 	$('#'+durationId).combobox('setValue',data1[data1.length-1].id);//默认选中第一个值
				 }
			 else
				 {
				 	$('#'+durationId).combobox('setValue',durationOfuser);//初始化选中的stationlist时用setvalue，避免触发级联事件
				 }
				
         }
	}); 
}

/**
 * 提交保存产品表单
 */
function submitAddproduct()
{
	$('#ff').form('submit',{
		url:contextPath+'/product/saveOrUpdate.action',
		onSubmit:function(param){
				
			return $('#ff').form('enableValidation').form('validate');
		},
	    success:function(data){
	    	//提交表单后，从后台返回的data类型为String，要获取信息需要将其转换为json类型，使用eval("(" + data + ")")方法转换
	    	$.messager.alert('提示', eval("(" + data + ")").message);
	    	$("#addProduct").dialog('close');//初始化添加角色弹框关闭
	    	
	    	//添加角色后刷新数据列表
	    	initDatagrid();
	    	
	    }
	});
}

/**
 * 提交修改产品表单
 */
function submitUpdateproduct()
{
	$('#ffUpdate').form('submit',{
		url:contextPath+'/product/saveOrUpdate.action',
		onSubmit:function(param){
				
			return $('#ffUpdate').form('enableValidation').form('validate');
		},
	    success:function(data){
	    	//提交表单后，从后台返回的data类型为String，要获取信息需要将其转换为json类型，使用eval("(" + data + ")")方法转换
	    	$.messager.alert('提示', eval("(" + data + ")").message);
	    	
	    	$("#updateProduct").dialog('close');
	    	
	    	//修改角色后刷新数据列表
	    	initDatagrid();
	    	
	    }
	});
}

/**
 * 生成产品编码
 */
function generateCode()
{
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "post",
        url: contextPath+'/product/generateProductcode.action',
        dataType: "json",
        success: function (data) {
        	
        	var proCode = data.code;
        	$("#codeA").textbox('setText',proCode);
        	$("#codehidden").val(proCode);
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/error.jsp";
        }
   });
}

/**
 * 删除产品数据
 * @param id
 */
function deleteProduct(id)
{
	var url = contextPath + '/product/deleteProduct.action';
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
			                    window.parent.location.href = contextPath + "/error.jsp";
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
 * 批量删除角色数据
 */
function deleteProductList()
{
	var url = contextPath + '/product/deleteProduct.action';
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
				                    window.parent.location.href = contextPath + "/error.jsp";
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
 * 自定义校验产品名称？？？（暂定产品名称校验规则：全局唯一）
 */
$.extend($.fn.validatebox.defaults.rules, {
    checkAname: {//自定义校验name
        validator: function(value,param){
        	var rules = $.fn.validatebox.defaults.rules;  
        	if(value.length==0||value.length>10){  
        		rules.checkAname.message = "当前产品名称不可为空且长度不可以超过10个字符";  
                return false;  
            }
        	else
    		{
        		rules.checkAname.message = "当前产品名称已存在"; 
        		
                return !checkProName($("#"+param[1]).val(),value);
    		}
        	
        }
    }
});

/**
 * 自定义校验产品编码
 */
$.extend($.fn.validatebox.defaults.rules, {
    checkCodes: {//自定义校验code
        validator: function(value,param){
        	var rules = $.fn.validatebox.defaults.rules;  
        	if(value.length==0||value.length>15){  
        		rules.checkCodes.message = "当前产品编码不可为空且长度不可以超过15个字符";  
                return false;  
            }
        	else
    		{
        		rules.checkCodes.message = "当前产品编码已存在"; 
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
        url: contextPath+'/product/checkCode.action',
        data:data,
        dataType: "json",
        success: function (data) {
        	if(data.exist)//若data.isExist==true,则当前校验值已存在，则不可用使用
        		{
        			flag = true;
        		}
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/error.jsp";
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
        url: contextPath+'/product/checkProName.action',
        data:data,
        dataType: "json",
        success: function (data) {
        	if(data.exist)//若data.isExist==true,则当前校验值已存在，则不可用使用
        		{
        			flag = true;
        		}
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/error.jsp";
        }
   });
	
	return flag;
}

