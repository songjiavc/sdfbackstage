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
		        {field:'itemid',width:120,title:'权限名称'},
				{field:'productid',title:'权限编码',width:120,align:'left'},
				{field:'listprice',title:'权限url',width:120,align:'left'},
				{field:'unitcost',title:'权限图片',width:80,align:'left'},
				{field:'unitcost',title:'是否启用',width:80,align:'left'},
				{field:'opt',title:'操作',width:160,align:'center',  
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
	$("#addAuth").dialog('close');//初始化添加角色弹框关闭
}

/**
 * 权限修改
 */
function updateAuth()
{
	alert("权限修改");
}

/**
 * 删除权限
 */
function deleteAuth()
{
	alert("权限删除");
}


//提交添加权限form表单
function submitAddauth()
{
	alert("submitauth");
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
