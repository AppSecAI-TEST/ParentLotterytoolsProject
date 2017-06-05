$(function()
		{
			
			closeDialog();//页面加载时关闭弹框
			
			
			initDatagrid();//初始化数据列表
			
		});


function closeDialog()
{
	$("#detailLotteryGroupNotice").dialog('close');
	$("#viewLotteryGroupNotice").dialog('close');
	$("#notAllow").dialog('close');
}

function initDatagrid()
{
	
	var params = new Object();
	params.status = $("#statusC").combobox('getValue');//获取模糊查询条件“应用名称”
	
	$('#datagrid').datagrid({
		singleSelect:false,
		rownumbers:false,
		queryParams: params,
		url:contextPath + '/outerLGroup/getGroupNoticeList.action',//'datagrid_data1.json',
		method:'get',
		border:false,
		singleSelect:false,
		fit:true,//datagrid自适应
		fitColumns:true,
		pagination:true,
		pageList:[10,20,30,50],
		collapsible:false,
//		toolbar:toolbar,
		columns:[[
				{field:'ck',checkbox:true},
				{field:'id',hidden:true},
				{field:'groupName',title:'发布对象群',width:'15%',align:'center'},
				{field:'status',width:'20%',title:'群公告状态',align:'center',  
		            formatter:function(value,row,index){  
		            	var name ='';
		            	switch(value)
		            	{
		            		case '1':name='通过';break;
		            		case '0':name='未通过';break;
		            		default:name='未审核';break;
		            	}
		            	return name;  
		            }  },
				{field:'createTime',title:'创建时间',width:'15%',align:'center'},
				{field:'opt',title:'操作',width:'15%',align:'center',  
			            formatter:function(value,row,index){  
			            	 var btn = '';
			            	if('999'== row.status)
			            		{//审批中
			            		   btn = '<a class="editcls" onclick="detailLotteryGroupNotice(&quot;'+row.id+'&quot;)" href="javascript:void(0)">查看详情</a>'
			                		+'<a class="pass" onclick="applyNoticeInRow(&quot;'+row.id+'&quot;,1)" href="javascript:void(0)">通过</a>'
					                	+'<a class="nopass" onclick="fillInReasonInRow(&quot;'+row.id+'&quot;,0)" href="javascript:void(0)">不通过</a>'
					                	+'<a class="delete" onclick="deleteLotteryGroupNotice(&quot;'+row.id+'&quot;)" href="javascript:void(0)">删除</a>';
					                
			            		}
			            	else
			            		{//审批完成
			            			btn = '<a class="editcls" onclick="viewDetailNotice(&quot;'+row.id+'&quot;)" href="javascript:void(0)">查看详情</a>'
					                	+'<a class="delete" onclick="deleteLotteryGroupNotice(&quot;'+row.id+'&quot;)" href="javascript:void(0)">删除</a>';
			            		}
			            	return btn;  
			            }  
			        }  
		    ]],  
	    onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({text:'查看详情',plain:true,iconCls:'icon-edit'}); 
	        $('.pass').linkbutton({text:'通过',plain:true,iconCls:'icon-remove'});  
	        $('.nopass').linkbutton({text:'不通过',plain:true,iconCls:'icon-remove'});  
	        $('.delete').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});  
	        
	        if(data.rows.length==0){
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="4">没有数据</td></tr>');
			}
	        
	        
	    },
	    rowStyler:function(index,row){//设置行样式
				
			},
	});
}

//查看群公告详情
function detailLotteryGroupNotice(id)
{
	var url = contextPath + '/outerLGroup/getDetailLotteryGroupNotice.action';
	var data1 = new Object();
	data1.id=id;//应用的id
	
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: url,
        data:data1,
        dataType: "json",
        success: function (data) {
        	
				$('#ffDetail').form('load',{
					id:data.id,
					groupName:data.groupName,
					notice:data.notice
					
				});
				
				
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/menu/error.action";
        }
	});
	
	
	$("#detailLotteryGroupNotice").dialog('open');
}

//
function viewDetailNotice(id)
{
	var url = contextPath + '/outerLGroup/getDetailLotteryGroupNotice.action';
	var data1 = new Object();
	data1.id=id;//应用的id
	
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: url,
        data:data1,
        dataType: "json",
        success: function (data) {
        	
				$('#ffviewDetail').form('load',{
					id:data.id,
					groupName:data.groupName,
					notice:data.notice
					
				});
				
				
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/menu/error.action";
        }
	});
	
	
	$("#viewLotteryGroupNotice").dialog('open');
}


function reset()
{
	$("#statusC").combobox('setValue','');
}

//在列表中审核群公告
function applyNoticeInRow(id,status)
{
	var url = contextPath + '/outerLGroup/updateGroupNoticeOfGroup.action';
	var data1 = new Object();
	
	data1.noticeId=id;
	data1.userId="1";
	data1.status = status;//彩票站id
	
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: url,
        data:data1,
        dataType: "json",
        success: function (data) {
        	
        	$.messager.alert('提示', data.message);
        	initDatagrid();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/menu/error.action";
        }
	});
}

function closeNotAllow()
{
	 $('#notAllow').dialog('close');
	 var content = $("#notPassReasonA").combobox('getValue');
	 if('' == content)
		 {
		 	$.messager.alert('提示', "请选择不通过原因！");
		 }
}

function fillInReason(status)
{
	$("#notAllow").dialog('open');
	
	var id = $("#idA").val();
	
	$("#idn").val(id);
	$("#statusn").val(status);
	$("#detailLotteryGroupNotice").dialog('close');
}

function fillInReasonInRow(id,status)
{
	$("#notAllow").dialog('open');
	
	$("#idn").val(id);
	$("#statusn").val(status);
}

//提交不通过的审核
function submitNotAllow()
{
	applyNotice($("#statusn").val(), $("#notPassReasonA").combobox('getValue'));
}

/**
 * 审核彩票站
 * @param status：审核状态
 */
function applyNotice(status,notPassMessage)
{
	var url = contextPath + '/outerLGroup/updateGroupNoticeOfGroup.action';
	var data1 = new Object();
	data1.userId="1";
	data1.status = status;//彩票站id
	if('0' == status)
		{
			data1.notPassMessage = notPassMessage;
			data1.noticeId = $("#idn").val();//彩票站id(获取不通过原因的公告id)
		}
	else
		{
			data1.noticeId = $("#idA").val();//彩票站id
		}
	
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: url,
        data:data1,
        dataType: "json",
        success: function (data) {
        	
        	$.messager.alert('提示', data.message);
        	
        	closeDialog();
        	
        	initDatagrid();
        	
        	
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/menu/error.action";
        }
	});
}

//删除群公告
function deleteLotteryGroupNotice(id)
{
	var url = contextPath + '/outerLGroup/deleteGroupNotice.action';
	var data1 = new Object();
	
	data1.noticeId = id;
	data1.userId = "1";
	
	$.messager.confirm("提示", "您确认删除选中数据？", function (r) {  
        if (r) {  
	        	$.ajax({
	        		async: false,   //设置为同步获取数据形式
	                type: "get",
	                url: url,
	                data:data1,
	                dataType: "json",
	                success: function (data) {
	                	initDatagrid();
	                	$.messager.alert('提示', data.message);
	                },
	                error: function (XMLHttpRequest, textStatus, errorThrown) {
	                    window.parent.location.href = contextPath + "/menu/error.action";
	                }
	           });
	        	
        }  
    });  
}

		