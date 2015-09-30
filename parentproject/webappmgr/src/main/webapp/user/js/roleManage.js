$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
//			setCheck();
			
			closeDialog();
			initDatagrid();
			
            var pager = $('#datagrid').datagrid().datagrid('getPager');    // get the pager of datagrid
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
		});

//关闭弹框
function closeDialog()
{
	$('#w').dialog('close');//初始化权限设置弹框关闭
	$("#addRole").dialog('close');//初始化添加角色弹框关闭
}


function initDatagrid()
{
	$('#datagrid').datagrid({
		singleSelect:false,
//		queryParams: params,
		url:'datagrid_data1.json',
		method:'get',
		border:false,
//		fit:true,
		fitColumns:true,
//		pagination:true,
//		pageSize:10,
//		striped:true,
		columns:[[
				{field:'ck',checkbox:true},
		        {field:'itemid',width:120,title:'角色名称'},
				{field:'productid',title:'角色编码',width:120,align:'left'},
				{field:'listprice',title:'角色上级角色',width:120,align:'left'},
				{field:'unitcost',title:'是否启用',width:80,align:'left'},
				{field:'opt',title:'操作',width:160,align:'center',  
		            formatter:function(value,rec){  
		                var btn = '<a class="editcls" onclick="updateRole()" href="javascript:void(0)">编辑</a>'
		                	+'<a class="auth" onclick="authManage()" href="javascript:void(0)">权限设置</a>';
		                return btn;  
		            }  
		        }  
		    ]],  
	    onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});  
	        $('.auth').linkbutton({text:'权限设置',plain:true,iconCls:'icon-auth'});  
	    }  
	});
}

/**
 * 角色修改
 */
function updateRole()
{
	alert("角色修改");
}


/**
 * 权限设置
 */
function authManage()
{
	$('#w').dialog('open');//打开弹框
}

/**
 * 提交role表单
 */
function submitAddrole()
{
	alert("submit");
}


/*******树方法********/
var setting = {
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};

	var zNodes =[
		{ id:1, pId:0, name:"随意勾选 1", open:true},
		{ id:11, pId:1, name:"随意勾选 1-1", open:true},
		{ id:111, pId:11, name:"随意勾选 1-1-1"},
		{ id:112, pId:11, name:"随意勾选 1-1-2"},
		{ id:12, pId:1, name:"随意勾选 1-2", open:true},
		{ id:121, pId:12, name:"随意勾选 1-2-1"},
		{ id:122, pId:12, name:"随意勾选 1-2-2"},
		{ id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
		{ id:21, pId:2, name:"随意勾选 2-1"},
		{ id:22, pId:2, name:"随意勾选 2-2", open:true},
		{ id:221, pId:22, name:"随意勾选 2-2-1", checked:true},
		{ id:222, pId:22, name:"随意勾选 2-2-2"},
		{ id:23, pId:2, name:"随意勾选 2-3"}
	];
	
	


/***************/