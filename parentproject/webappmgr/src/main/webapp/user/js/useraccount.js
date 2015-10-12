$(document).ready(
		function()
		{
			
			initDatagrid();
			closeDialog();
			
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
	$('#accountDataGrid').datagrid({
		singleSelect:false,
//		queryParams: params,
		url:'datagrid_data1.json',
		method:'get',
		border:false,
		fit:true,
		fitColumns:true,
		pagination:true,
		pageSize:10,
		striped:true,
		columns:[[
				{field:'ck',checkbox:true},
		        {field:'uid',title:'权限名称'},
				{field:'code',title:'登录帐号',align:'left'},
				{field:'name',title:'用户名称',align:'left'},
				{field:'status',title:'状态',align:'left'},
				{field:'creater',title:'创建人',align:'left'},
				{field:'createrTime',title:'创建时间',align:'left'},
				{field:'opt',title:'操作',align:'center',  
		            formatter:function(value,rec){  
		                var btn = '<a class="editcls" onclick="updateAuth()" href="javascript:void(0)">编辑</a>'
		                	+'<a class="auth" onclick="deleteAuth()" href="javascript:void(0)">删除</a>';
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
	$("#addAccount").dialog('close');//初始化添加角色弹框关闭
}

/**
 * 权限修改
 */
function updateAccount()
{
	alert("权限修改");
}

/**
 * 删除权限
 */
function deleteAccount()
{
	alert("权限删除");
}


//提交添加权限form表单
function submitAddAccount()
{
	$('#accountForm').form('submit',{ 
		url: contextPath + '/account/addNewAccount.action',
        success:function(data){  
            alert(data);  
        }  
    });
}
