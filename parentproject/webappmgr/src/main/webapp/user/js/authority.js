$(document).ready(
		function()
		{
			
			initDatagrid();
			closeDialog();
			
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			
            var pager = $('#dg').datagrid().datagrid('getPager');    // get the pager of datagrid
           /*  pager.pagination({
                buttons:[{
                    iconCls:'icon-search',
                    handler:function(){
                        alert('search');
                    }
                },{
                    iconCls:'icon-add',
                    handler:function(){
                        alert('add');
                    }
                },{
                    iconCls:'icon-edit',
                    handler:function(){
                        alert('edit');
                    }
                }]
            });        */   
            
            
		}
);


function initDatagrid()
{
	$('#datagrid').datagrid({
		singleSelect:false,
//		queryParams: params,
		url:contextPath + '/menu/getAuthList.action',//'datagrid_data1.json',
		method:'get',
		border:false,
//		fit:true,
		fitColumns:true,
//		pagination:true,
		pageSize:10,
//		striped:true,
		columns:[[
				{field:'ck',checkbox:true},
				{field:'id',hidden:true},
		        {field:'authName',width:120,title:'权限名称'},
				{field:'code',title:'权限编码',width:120,align:'left'},
				{field:'url',title:'权限url',width:120,align:'left'},
				{field:'authImg',title:'权限图片',width:80,align:'left'},
				{field:'status',title:'是否启用',width:80,align:'left',
					formatter:function(value,row,index){
							var showStatus = "";
							if("1" == row.status)
								{
									showStatus = "是";
								}
							else
								{
									showStatus = "否";
								}
							return showStatus;
						}
					},
				{field:'opt',title:'操作',width:160,align:'center',  
		            formatter:function(value,row,index){  
		                var btn = '<a class="editcls" onclick="updateAuth(&quot;'+row.code+'&quot;)" href="javascript:void(0)">编辑</a>'
		                	+'<a class="auth" onclick="deleteAuth(&quot;'+row.code+'&quot;)" href="javascript:void(0)">删除</a>';
		                return btn;  
		            }  
		        }  
		    ]],  
	    onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});  
	        $('.auth').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});  
	    }  
	});
}


//关闭弹框
function closeDialog()
{
	$("#addAuth").dialog('close');//初始化添加权限弹框关闭
	$("#updateAuth").dialog('close');//初始化修改权限弹框关闭
}

/**
 * 权限修改
 */
function updateAuth(code)
{
	var url = contextPath + '/menu/getDetailAuth.action';
	var data1 = new Object();
	data1.code=code;
	
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: url,
        data:data1,
        dataType: "json",
        success: function (data) {
        	
				$('#ffupdate').form('load',{
					code:data.code,
					authName:data.authName,
					parentAuth:data.parentAuth,
					url:data.url,
					status:data.status,
					authImg:data.authImg
				});
		
        	
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
   });
	
	
	$("#updateAuth").dialog("open");//打开修改权限弹框
}

/**
 * 批量删除权限数据
 * @param code
 */
function deleteAuthList()
{
	var url = contextPath + '/menu/deleteAuth.action';
	var data1 = new Object();
	
	var codearr = new Array();
	var rows = $('#datagrid').datagrid('getSelections');
	for(var i=0; i<rows.length; i++)
	{
		codearr.push(rows[i].code);//code
	}
	
	
	if(codearr.length>0)
		{
			data1.codes=codearr.toString();//将id数组转换为String传递到后台
			
			$.messager.confirm(" ", "您确认删除选中数据？", function (r) {  
		        if (r) {  
		        	
			        	$.ajax({
			        		async: false,   //设置为同步获取数据形式
			                type: "post",
			                url: url,
			                data:data1,
			                dataType: "json",
			                success: function (data) {
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

/**
 * 删除权限
 */
function deleteAuth(code)
{
	var url = contextPath + '/menu/deleteAuth.action';
	var data1 = new Object();
	
	var codearr = [];
	codearr.push(code);
	
	data1.codes=codearr.toString();
	$.messager.confirm(" ", "您确认删除选中数据？", function (r) {  
        if (r) {  
        	
	        	$.ajax({
	        		async: false,   //设置为同步获取数据形式
	                type: "post",
	                url: url,
	                data:data1,
	                dataType: "json",
	                success: function (data) {
	                	$.messager.alert('提示', data.message);
	                },
	                error: function (XMLHttpRequest, textStatus, errorThrown) {
	                    alert(errorThrown);
	                }
	           });
	        	
        }  
    });  
	
	
}


//提交添加权限form表单
function submitAddauth()
{
	$('#ff').form('submit',{
		url:contextPath+'/menu/saveOrUpdate.action',
		onSubmit:function(param){
			var flag = true;
//			$('#ff').form('enableValidation').form('validate');
		},
	    success:function(data){
	    	//提交表单后，从后台返回的data类型为String，要获取信息需要将其转换为json类型，使用eval("(" + data + ")")方法转换
	    	$.messager.alert('提示', eval("(" + data + ")").message);
	    	$("#addAuth").dialog('close');//初始化修改权限弹框关闭
	    	//在添加权限后刷新权限数据列表
	    	initDatagrid();
	    }
	});
}

//提交修改权限form表单
function submitUpdateauth()
{
	$('#ffupdate').form('submit',{
		url:contextPath+'/menu/saveOrUpdate.action',
		onSubmit:function(param){
			var flag = true;
//			$('#ffupdate').form('enableValidation').form('validate');
		},
	    success:function(data){
	    	//data从后台返回后的类型为String，要获取信息需要将其转换为json类型，使用eval("(" + data + ")")方法转换
	    	$.messager.alert('提示', eval("(" + data + ")").message);
	    	$("#updateAuth").dialog('close');//初始化修改权限弹框关闭
	    	//在修改权限后刷新权限数据列表
	    	initDatagrid();
	    }
	});
}



/***********树配置************/
var setting = {
		view: {
			showLine: false
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};

var zNodes =[
				{ id:1, pId:0, name:"权限树", open:true},
				{ id:11, pId:1, name:"用户"},
				{ id:111, pId:11, name:"添加站点"},
				{ id:112, pId:11, name:"添加管理员"},
				{ id:113, pId:11, name:"添加中心"},
				{ id:114, pId:11, name:"站点管理"},
				{ id:115, pId:11, name:"代理管理"},
				{ id:12, pId:1, name:"商品"},
				{ id:121, pId:12, name:"商品管理"},
				{ id:122, pId:12, name:"购买商品"},
				{ id:13, pId:1, name:"产品", isParent:true},
				{ id:2, pId:0, name:"信息", isParent:true},
				{ id:3, pId:0, name:"日志", isParent:true},
				{ id:4, pId:0, name:"报表", isParent:true}
			];
