$(document).ready(
		function()
		{
			initDatagrid();
			closeDialog();
		}
);

/*
 * 	@desc	 
 */
function initDatagrid()
{
	$('#accountDataGrid').datagrid({
		singleSelect:false,
//		queryParams: params,
		url: contextPath + '/account/getUserList.action',
		method:'get',
		border:false,
		fit:true,
		//fitColumns:true,
		pagination:true,
		pageSize:10,
		striped:true,
		columns:[[
				{field:'id',checkbox:true	},
				{field:'code',title:'登录帐号',width:'10%',align:'center'},
				{field:'name',title:'名称',width:'10%',align:'center'},
				{field:'telephone',title:'电话',width:'15%',align:'center'},
				{field:'creater',title:'录入人',width:'10%',align:'center'},
				{field:'createrTime',title:'录入时间',width:'10%',align:'center'},
				{field:'status',title:'启用',align:'center',width:'5%',
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
				{field:'roles',title:'角色',align:'center',width:'15%',
					formatter:function(value,row,index){
						if(value!= undefined){
							var lis = '<ol 	style="padding:0 0 0 15;">';
							$.each(value,function(i,data){
								lis=lis+'<li style="align:left">'+data.roleName;
			                });
							return lis+'</ol>';
						}
					}
				},
				{field:'opt',title:'操作',width:'150',align:'center', 
		            formatter:function(value,row,index){
		                var btn = '<a class="editcls" onclick="updateAccount(&quot;'+row.id+'&quot;)" href="javascript:void(0)"></a>'
		                	+'<a class="setRoles" onclick="setRoles(&quot;'+row.id+'&quot;,&quot;'+row.roles+'&quot;)" href="javascript:void(0)"></a>';
		                return btn;
		            }
		        }
		    ]],
	    onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});
	        $('.setRoles').linkbutton({text:'设置角色',plain:true,iconCls:'icon-search'});
	    }  
	});
}


	//关闭弹框
	function closeDialog()
	{
		$("#addAccount").dialog('close'); 
		$("#updateAccount").dialog('close');
		$("#selectRoleDiv").dialog('close');
	}

	/**
	 * 权限修改
	 */
	function updateAccount(id)
	{
			/**
			 * 用户修改
			 */
			var url = contextPath + '/account/getDetailAccount.action';
			var paramData = new Object();
			paramData.id=id;
			$.ajax({
				async: false,   //设置为同步获取数据形式
		        type: "get",
		        cache:false,
		        url: url,
		        data:paramData,
		        dataType: "json",
		        success: function (data) {
					$('#updateAccountForm').form('load',{
						id:data.id,
						code:data.code,
						name:data.name,
						password:data.password,
						confirmPassword:data.password,
						telephone:data.telephone,
						status:data.status
					});
		        },
		        error: function (XMLHttpRequest, textStatus, errorThrown) {
		            alert(errorThrown);
		        }
			});
			$("#updateAccount").dialog("open");//打开修改用户弹框
	}


	//提交添加权限form表单
	function submitAddAccount()
	{
		$('#addAccountForm').form('submit',{
			url:contextPath+'/account/saveOrUpdate.action',
			onSubmit:function(param){
				return $('#addAccountForm').form('validate');
			},
			success:function(data){
				$.messager.alert('提示', eval("(" + data + ")").message);
	        	closeDialog();
	        	initDatagrid();
			}
		});
	}
	
	//修改帐号form表单
	function submitUpdateAccount()
	{
		$('#updateAccountForm').form('submit',{
			url:contextPath+'/account/saveOrUpdate.action',
			onSubmit:function(param){
				return $('#updateAccountForm').form('enableValidation').form('validate');
			},
		    success:function(data){
		    	//data从后台返回后的类型为String，要获取信息需要将其转换为json类型，使用eval("(" + data + ")")方法转换
		    	$.messager.alert('提示', eval("(" + data + ")").message);
		    	$("#updateAccount").dialog('close');//初始化修改权限弹框关闭
		    	//在修改权限后刷新权限数据列表
		    	initDatagrid();
		    	$('#updateAccountForm').form('clear');
		    }
		});
	}
	
	/**
	 * 校验code唯一性
	 */
	function checkCode(code)
	{
		var flag = false;//当前值可用，不存在
		$.ajax({
			async: false,   //设置为同步获取数据形式
	        type: "get",
	        url: contextPath+'/account/checkValue.action',
	        data:{
	        	code : code
	        },
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
	 * 批量删除
	 * @param code
	 */
	function deleteAccountByIds()
	{
		var url = contextPath + '/account/deleteAccountByIds.action';
		var paramObj = new Object();
		
		var idArr = new Array();
		var rows = $('#accountDataGrid').datagrid('getSelections');
		var deleteFlag = true;
		
		for(var i=0; i<rows.length; i++)
		{
			idArr.push(rows[i].id);//code
		}
		
		if(idArr.length>0)
		{
			paramObj.ids=idArr.toString();	//将id数组转换为String传递到后台
			
			$.messager.confirm(" ", "您确认删除选中数据？", function (r) {  
		        if (r) {  
			        	$.ajax({
			        		async: false,   //设置为同步获取数据形式
			                type: "post",
			                url: url,
			                data:paramObj,
			                dataType: "json",
			                success: function (data) {
			                	initDatagrid('');
			                	//批量删除权限后重新加载树
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
	
	
	function initRoleGrid(){
		$('#selectRoleGrid').datagrid({
			singleSelect:false,
			rownumbers:false,
			url:contextPath + '/role/getRoleList.action',//'datagrid_data1.json',
			method:'get',
			border:false,
			singleSelect:false,
			fitColumns:true,
			pagination:true,
			collapsible:false,
//				pageSize:10,
//				striped:true,
			columns:[[
					{field:'ck',checkbox:true},
					{field:'id',hidden:true},
			        {field:'name',width:120,title:'角色名称'},
					{field:'code',title:'角色编码',width:120,align:'left'},
					{field:'parentRolename',title:'角色上级角色',width:120,align:'left'}
			    ]],  
		    onLoadSuccess:function(data){  
		    	if(data.rows.length==0){
					var body = $(this).data().datagrid.dc.body2;
					body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="8">没有数据</td></tr>');
				}else{
					//获取已经选择的角色数据
					var selectedRows = $('#selectedRoleGrid').datagrid('getRows');
					//获取待选择的角色列表
					var selectRows = $('#selectRoleGrid').datagrid('getRows');
					$.each(selectedRows,function(i,selectedRow){
						$.each(selectRows,function(j,selectRow){
							if(selectedRow.id == selectRow.id){
								$('#selectRoleGrid').datagrid('checkRow',j);
							}
						});
					});
				}
		    },
		    onCheck : function(rowIndex,rowData){
		    	//var index = $('#selectedRoleGrid').datagrid('getRowIndex',rowData);//获取某行的行号
		    	var index = getRowIndex(rowData);
		    	if(index == -1){
			    	$('#selectedRoleGrid').datagrid('insertRow',{
			            row : rowData
			            });//这url是想动态获取一条数据，怎么弄。？
		    	}
		    },
		    onUncheck : function(rowIndex,rowData){
		    	var index = getRowIndex(rowData);
		    	 $('#selectedRoleGrid').datagrid('deleteRow',index);
		    },
		    onCheckAll : function(rows){
		    	$.each(rows,function(i,row){
		    		var index = getRowIndex(row);
		    		if(index == -1){
			    		$('#selectedRoleGrid').datagrid('insertRow',{
				            row: rowData
				        });//这url是想动态获取一条数据，怎么弄。？
		    		}
                });
		    	
		    },
		    onUncheckAll : function(rows){
		    	$.each(rows,function(i,row){
		    		var index = getRowIndex(row);
		    		if(index != -1){
			    		$('#selectedRoleGrid').datagrid('deleteRow',index);
		    		}
		    });
		}
	});
	}
	///扩展datagrid的方法  处理当翻页时getRowIndex方法失效的问题	
	function getRowIndex(row){
		var selectedRows = $('#selectedRoleGrid').datagrid('getRows');
    	var index = -1;
    	$.each(selectedRows,function(i,selectedRow){
    		if(row.id == selectedRow.id){
    			index = i;
    		}
    	});
    	return index;
	}
	
	
	function initSelectedRoleGrid(id){
		$('#selectedRoleGrid').datagrid({
			singleSelect:false,
			rownumbers:false,
			url:contextPath + '/account/getUserRelaRoleList.action',//'datagrid_data1.json',
			// url: contextPath + '/account/getUserRelaRoleList.action',     //初始化的时候查找已经存在的关联关系，并在该列表中显示。
			method:'get',
			queryParams : {
				id : id
			},
			border:false,
			fitColumns:true,
			collapsible:false,
			columns:[[
					{field:'id',hidden:true},    //userRelaRole 关联关系表主键
					{field:'userId',hidden:true},	//userId  用户主键
					{field:'roleId',hidden:true},	//角色主键
			        {field:'name',width:120,title:'角色名称'},
					{field:'code',title:'角色编码',width:120,align:'left'},
					{field:'parentRolename',title:'角色上级角色',width:120,align:'left'}
			    ]],  
		    onLoadSuccess:function(data){
		    	if(data.rows.length==0){
					var body = $(this).data().datagrid.dc.body2;
					body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="8">没有数据</td></tr>');
				}else{
					//获取已经选择的角色数据
					var selectedRows = $('#selectedRoleGrid').datagrid('getRows');
					//获取待选择的角色列表
					var selectRows = $('#selectRoleGrid').datagrid('getRows');
					$.each(selectedRows,function(i,selectedRow){
						$.each(selectRows,function(j,selectRow){
							if(selectedRow.id == selectRow.id){
								$('#selectRoleGrid').datagrid('checkRow',j);
							}
						});
					});
				}
		    }
		});
	}
	
	/**
	 * @desc 设置角色
	 * @param userId
	 * @param roles
	 */
	function setRoles(userId,roles){
		initRoleGrid();
		initSelectedRoleGrid(userId);
		$("#userId").val(userId);
		$("#selectRoleDiv").dialog("open");     //设定权限
	}

	function submitSelRoles(){
		//undo 保存选择角色列表
		var rows = $('#selectedRoleGrid').datagrid('getRows');
		var userId = 	$("#userId").val();
		$.ajax({
			async: false,   //设置为同步获取数据形式
	        type: "post",
	        url: contextPath+'/account/saveUserRleaRole.action',
	        data: {
	        	userId : userId,                  //传入将要设定角色的用户
	        	roleList : JSON.stringify(rows)	//传入已经选择好的用户
	        },
	        dataType: "json",
	        success: function (data) {
	        	$.messager.alert('提示', data.message);
	        	closeDialog();
	        	initDatagrid();
	        },
	        error: function (XMLHttpRequest, textStatus, errorThrown) {
	        	$.messager.alert('提示', errorThrown);
	        }
	   });
	}
	//  初始化角色选择dialog
	
	function selectRoleBeforeClose(){
		var selectedRows = $('#selectedRoleGrid').datagrid('getRows');
		$.each(selectedRows,function(i,selectedRow){
			$('#selectedRoleGrid').datagrid('deleteRow',0);
		});
	}
	
/**
 * 自定义校验code
 */
$.extend($.fn.validatebox.defaults.rules, {
    checkCodes: {//自定义校验code
        validator: function(value,param){
        	var rules = $.fn.validatebox.defaults.rules;  
    		rules.checkCodes.message = "当前权限编码已存在"; 
            return !checkCode($("#"+param[1]).val(),value,'');
        }
    },
    equalTo: { 
    	validator: function (value, param) { 
    		return $(param[0]).val() == value; 
    		}, message: '字段不匹配' }
});
