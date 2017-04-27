$(function()
		{
			initQueryProvince();
			
			closeDialog();//页面加载时关闭弹框
			
			
			initDatagrid();//初始化数据列表
			
		});


function closeDialog()
{
	$("#detailLotteryStation").dialog('close');//初始化添加角色弹框关闭
	$("#uploadShowAimgPreview").dialog('close');
	$("#viewDetailLotteryStation").dialog('close');
	$("#notAllow").dialog('close');
}

function initDatagrid()
{
	
	var params = new Object();
	params.approvalStatus = $("#approvalStatusC").combobox('getValue');//获取模糊查询条件“应用名称”
	params.status = $("#statusC").combobox('getValue');//获取模糊查询条件“应用编码”
	params.provinceCode = $("#privinceC").combobox('getValue');
	
	$('#datagrid').datagrid({
		singleSelect:false,
		rownumbers:false,
		queryParams: params,
		url:contextPath + '/lotteryStation/getLotteryStationList.action',//'datagrid_data1.json',
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
				{field:'stationName',title:'彩票站名称',width:'12%',align:'center'},
		        {field:'provinceName',width:'6%',title:'省',align:'center'},
		        {field:'cityName',width:'6%',title:'市',align:'center'},
				{field:'lotteryType',width:'6%',title:'彩种',align:'center',  
		            formatter:function(value,row,index){  
		            	var lotteryTypeName ='';
		            	switch(value)
		            	{
		            		case '1':lotteryTypeName='体彩';break;
		            		case '2':lotteryTypeName='福彩';break;
		            	}
		            	return lotteryTypeName;  
		            }  },
				{field:'isBylStation',title:'佰艺霖用户',width:'8%',align:'center',
			            formatter:function(value,row,index){  
			            	var isbyl ='';
			            	switch(value)
			            	{
			            		case '0':isbyl='否';break;
			            		case '1':isbyl='是';break;
			            	}
			            	return isbyl;  
			            }  },
				{field:'approvalStatusName',title:'审批状态',width:'8%',align:'center'},
				{field:'statusName',title:'彩票站状态',width:'8%',align:'center'},
				{field:'stationOwner',title:'站主',width:'8%',align:'center'},
				{field:'createTimeStr',title:'创建时间',width:'15%',align:'center'},
				{field:'opt',title:'操作',width:'15%',align:'center',  
			            formatter:function(value,row,index){  
			            	 var btn = '';
			            	if('0'== row.approvalStatus)
			            		{//审批中
			            		   btn = '<a class="editcls" onclick="detailStation(&quot;'+row.id+'&quot;)" href="javascript:void(0)">查看详情</a>'
			                		+'<a class="pass" onclick="applyStationInRow(&quot;'+row.id+'&quot;,1)" href="javascript:void(0)">通过</a>'
					                	+'<a class="nopass" onclick="fillInReasonInRow(&quot;'+row.id+'&quot;,0)" href="javascript:void(0)">不通过</a>'
					                	+'<a class="delete" onclick="deleteLotteryStation(&quot;'+row.id+'&quot;)" href="javascript:void(0)">删除</a>';
					                
			            		}
			            	else
			            		{//审批完成
			            			btn = '<a class="editcls" onclick="viewDetailStation(&quot;'+row.id+'&quot;)" href="javascript:void(0)">查看详情</a>'
					                	+'<a class="delete" onclick="deleteLotteryStation(&quot;'+row.id+'&quot;)" href="javascript:void(0)">删除</a>';
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
				body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="11">没有数据</td></tr>');
			}
	        
	        
	    },
	    rowStyler:function(index,row){//设置行样式
				
			},
	});
}

//查看彩票站详情
function detailStation(id)
{
	var url = contextPath + '/lotteryStation/getDetailStation.action';
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
					stationName:data.stationName,
					provinceName:data.provinceName,
					cityName:data.cityName,
					stationOwner:data.stationOwner
					
				});
				
				if('1' == data.lotteryType)
					{
						$("#lotteryTypeA").val("体彩");
					}
				else if('2' == data.lotteryType)
						{
							$("#lotteryTypeA").val("福彩");
						}
				
				if('1' == data.isBylStation)
				{
					$("#isBylStationA").val("是,佰艺霖账号："+data.bylStationCode);
				}
				else 
					{
						$("#isBylStationA").val("否");
					}
				
				$("#idNumberFrontImgA").attr("src",contextPath+data.idNumberFrontImgUrl);
				$("#idNumberBackImgA").attr("src",contextPath+data.idNumberBackImgUrl);
				$("#daixiaoImgA").attr("src",contextPath+data.daixiaoImgUrl);
				
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/menu/error.action";
        }
	});
	
	
	$("#detailLotteryStation").dialog('open');
}

//查看彩票站详情
function viewDetailStation(id)
{
	var url = contextPath + '/lotteryStation/getDetailStation.action';
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
					stationName:data.stationName,
					provinceName:data.provinceName,
					cityName:data.cityName,
					stationOwner:data.stationOwner
					
				});
				
				if('1' == data.lotteryType)
					{
						$("#lotteryTypeV").val("体彩");
					}
				else if('2' == data.lotteryType)
						{
							$("#lotteryTypeV").val("福彩");
						}
				
				if('1' == data.isBylStation)
				{
					$("#isBylStationV").val("是,佰艺霖账号："+data.bylStationCode);
				}
				else 
					{
						$("#isBylStationV").val("否");
					}
				
				$("#idNumberFrontImgV").attr("src",contextPath+data.idNumberFrontImgUrl);
				$("#idNumberBackImgV").attr("src",contextPath+data.idNumberBackImgUrl);
				$("#daixiaoImgV").attr("src",contextPath+data.daixiaoImgUrl);
				
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/menu/error.action";
        }
	});
	
	
	$("#viewDetailLotteryStation").dialog('open');
}

/**
 * 图片预览
 * @param imgId：图片div的id
 * @param realName：图片真实路径
 */
function previewImage(id)
{
	$("#uploadShowAimgPreview").dialog('open');//打开鼠标预览弹框
	var path = $("#"+id).prop("src");
	$("#uploadShowAimgPreview").html("<img style='width:600px;height:500px;' src='"+path+"'/>");
}

function closeNotAllow()
{
	 $('#notAllow').dialog('close');
	 var content = $("#notAllowReasonA").val().trim();
	 if('' == content)
		 {
		 	$.messager.alert('提示', "请输入不通过原因！");
		 }
}

function reset()
{
	initQueryProvince();
	$("#statusC").combobox('setValue','');
	$("#approvalStatusC").combobox('setValue','');
}

//在列表中审核彩票站
function applyStationInRow(id,status)
{
	var url = contextPath + '/lotteryStation/updateRenzhengStatusLotteryStaion.action';
	var data1 = new Object();
	var flag = true;
	data1.status=status;
	data1.approvalStatus="1";
	
	data1.id = id;//彩票站id
	
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: url,
        data:data1,
        dataType: "json",
        success: function (data) {
        	
        	$.messager.alert('提示', data.message);
        	$("#notAllowReasonA").val()
        	initDatagrid();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/menu/error.action";
        }
	});
}

function fillInReasonInRow(id,status)
{
	$("#notAllow").dialog('open');
	
	$("#idn").val(id);
	$("#statusn").val(status);
}

function fillInReason(status)
{
	$("#notAllow").dialog('open');
	
	var id = $("#idA").val();
	
	$("#idn").val(id);
	$("#statusn").val(status);
}

function submitNotAllow()
{
	var url = contextPath + '/lotteryStation/updateRenzhengStatusLotteryStaion.action';
	var data1 = new Object();
	data1.status=$("#statusn").val();
	data1.approvalStatus="1";
	data1.id =$("#idn").val();//彩票站id
	data1.notAllowReason=$("#notAllowReasonA").val().trim();
	
	if(data1.notAllowReason == '')
		{
			$.messager.alert('提示', "请输入不通过原因");
		}
	else
		{
			$.ajax({
				async: false,   //设置为同步获取数据形式
		        type: "get",
		        url: url,
		        data:data1,
		        dataType: "json",
		        success: function (data) {
		        	
		        	$.messager.alert('提示', data.message);
		        	$("#idn").val("");
		        	$("#statusn").val("");
		        	closeDialog();
		        	$("#notAllowReasonA").val("");
		        	$("#notAllow").dialog('close');
		        	
		        	initDatagrid();
		        	
		        },
		        error: function (XMLHttpRequest, textStatus, errorThrown) {
		            window.parent.location.href = contextPath + "/menu/error.action";
		        }
			});
		}
	
	
}

/**
 * 审核彩票站
 * @param status：审核状态
 */
function applyStation(status)
{
	var url = contextPath + '/lotteryStation/updateRenzhengStatusLotteryStaion.action';
	var data1 = new Object();
	data1.status=status;
	data1.approvalStatus="1";
	data1.id = $("#idA").val();//彩票站id
	
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: url,
        data:data1,
        dataType: "json",
        success: function (data) {
        	
        	$.messager.alert('提示', data.message);
        	
        	closeDialog();
        	$("#notAllowReasonA").val("");
        	
        	initDatagrid();
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/menu/error.action";
        }
	});
}

//删除彩票站
function deleteLotteryStation(id)
{
	var url = contextPath + '/lotteryStation/deleteLotteryStations.action';
	var data1 = new Object();
	
	var codearr = [];
	codearr.push(id);
	
	data1.ids=codearr.toString();
	
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
	                    window.parent.location.href = contextPath + "/menu/error.action";
	                }
	           });
	        	
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
			url:contextPath+'/menu/getProvinceList.action',
			valueField:'pcode',
			textField:'pname',
			 onLoadSuccess: function (data1) { //数据加载完毕事件
				 $('#privinceC').combobox('select',data1[data1.length-1].pcode);
					
             }
		}); 
}
		