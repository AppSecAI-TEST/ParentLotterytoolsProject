$(function()
		{
			initQueryProvince();
			
			closeDialog();//页面加载时关闭弹框
			
			
			initDatagrid();//初始化数据列表
			
		});

function initDatagrid()
{
	var params = new Object();
	params.lotteryType = $("#lotteryTypeC").combobox('getValue');
	params.provinceCode = $("#privinceC").combobox('getValue');
	
	$('#datagrid').datagrid({
		rownumbers:false,
		queryParams: params,
		url:contextPath +'/lotteryStation/getLotteryStationList.action',
		method:'get',
		border:false,
		singleSelect:false,
		fit:true,//datagrid自适应
		fitColumns:true,
		pagination:true,
		collapsible:false,
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
				{field:'code',title:'站主',width:'8%',align:'center'},
				{field:'createTimeStr',title:'创建时间',width:'15%',align:'center'},
				{field:'opt',title:'操作',width:'15%',align:'center',  
			            formatter:function(value,row,index){  
			            	 var btn = '';
			            			btn = '<a class="editcls" onclick="detailStation(&quot;'+row.id+'&quot;)" href="javascript:void(0)">查看详情</a>'
					                	+'<a class="delete" onclick="deleteLotteryStation(&quot;'+row.id+'&quot;)" href="javascript:void(0)">删除</a>';
			            	return btn;  
			            }  
			        }  
		    ]],  
	    onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({text:'查看详情',plain:true,iconCls:'icon-edit'}); 
	        $('.delete').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});  
	        
	        if(data.rows.length==0){
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="11">没有数据</td></tr>');
			}
	        
	        
	    }
	});
}

function reset()
{
	initQueryProvince();
	$("#lotteryTypeC").combobox('setValue','');
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


function closeDialog()
{
	$("#detailLotteryStation").dialog('close');//初始化添加角色弹框关闭
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