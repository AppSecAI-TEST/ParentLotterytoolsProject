var lpbuId = '';//基础预测类型id
var lplayId = '';//区域彩种id

$(function()
		{
			
			closeDialog();//页面加载时关闭弹框
			
			initDatagrid();//初始化数据列表
			
			
		});





//关闭弹框
function closeDialog()
{
	$("#addPredictionType").dialog('close');//初始化添加角色弹框关闭
	$("#updatePredictionType").dialog('close');
}


function reset()
{
	
	$("#predictionNameC").val("");
	
}


/**
 * 初始化区域预测类型列表
 */
function initDatagrid()
{
	
	var params = new Object();
	params.predictionName = $("#predictionNameC").val().trim();//获取模糊查询条件“应用名称”
	
	$('#datagrid').datagrid({
		singleSelect:false,
		rownumbers:false,
		queryParams: params,
		url:contextPath + '/ptype/getPredictionTypeList.action',//'datagrid_data1.json',
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
				{field:'predictionName',title:'预测类型名称',width:'15%',align:'center'},
				{field:'predictionTable',title:'预测方案表',width:'15%',align:'center'},
				{field:'lotteryPlayName',title:'区域彩种名称',width:'15%',align:'center'},
				{field:'basePredictionTypeName',title:'基础预测类型名称',width:'15%',align:'center'},
				{field:'createTime',title:'创建时间',width:'15%',align:'center'},
				{field:'opt',title:'操作',width:'15%',align:'center',  
			            formatter:function(value,row,index){  
			                var btn = '<a class="editcls" onclick="updatePredictionType(&quot;'+row.id+'&quot;)" href="javascript:void(0)">编辑</a>'
			                	+'<a class="deleterole" onclick="deletePredictionType(&quot;'+row.id+'&quot;)" href="javascript:void(0)">删除</a>';
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

function addPredictionType()
{
	$("#addPredictionType").dialog('open');
	$("#basePTypeU").datagrid('loadData', { total: 0, rows: [] });  
	initBasePtypeDatagrid('basePTypeA','');
	$("#lotteryPlayU").datagrid('loadData', { total: 0, rows: [] });  
	initLotteryPlayDatagrid('lotteryPlayA', '');
}

/**
 * 修改预测类型
 * @param id
 */
function updatePredictionType(id)
{
	var url = contextPath + '/ptype/getPredictionType.action';
	var data1 = new Object();
	data1.id=id;//应用的id
	
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: url,
        data:data1,
        dataType: "json",
        success: function (data) {
        	
				$('#ffUpdate').form('load',{
					id:data.id,
					predictionName:data.predictionName,
					predictionCode:data.predictionCode,
					predictionTable:data.predictionTable,
					liangmaTableName:data.liangmaTableName,
					sanmaTableName:data.sanmaTableName,
					simaTableName:data.simaTableName,
					liumaTableName:data.liumaTableName,
					orderRule:data.orderRule
					
				});
				//在加载修改的数据时要对添加的表格中数据情况，因为使用的是自定义的radio，所以获取时会获取到添加功能的选择列表中的数据
				$("#basePTypeA").datagrid('loadData', { total: 0, rows: [] });
				initBasePtypeDatagrid('basePTypeU', data.id);
				$("#lotteryPlayA").datagrid('loadData', { total: 0, rows: [] });
				initLotteryPlayDatagrid('lotteryPlayU', data.id);
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/menu/error.action";
        }
	});
	
	
	$("#updatePredictionType").dialog('open');
	
	
}
/**
 * 删除预测类型
 * @param id
 */
function deleteBasePredictionType(id)
{
	var url = contextPath + '/ptype/deletePredictionType.action';
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
 * 初始化补录数据列表
 * buluDatagridId:补录table的id
 * lpId：补录信息id
 */
function initBasePtypeDatagrid(oruleDatagridId,lpId)
{
	var params = new Object();
	
	lpbuId = getCheckIpbuId(lpId);
	
	
	$('#'+oruleDatagridId).datagrid({
		singleSelect:true,
		rownumbers:false,
		queryParams: params,
		url:contextPath + '/ptype/getBasePredictionTypeList.action',//'datagrid_data1.json',
		method:'get',
		border:false,
		singleSelect:false,
		fit:true,//datagrid自适应
		fitColumns:true,
		pagination:true,
		collapsible:false,
		pageSize:5,//初始化页面显示条数的值是根据pageList的数组中的值来设置的，否则无法正确设置
		pageList:[5,10],
		columns:[[
				{  
                field: 'oid', title: '', width: '5%', align: 'center',  
                //调用formater函数对列进行格式化，使其显示单选按钮（所有单选按钮name属性设为统一，这样就只能有一个处于选中状态）  
                formatter: function (value, row, index) {  
                     var s = '<input name="basetypeselectRadio"  type="radio" id="selectRadio"'+index+'  /> ';  
  
                    return s;  
                }  
  
            }  ,
				{field:'id',hidden:true},
				{field:'basePredictionName',title:'基本预测类型名称',width:'30%',align:'center'},
				{field:'methodName',width:'25%',title:'预测方法名称',align:'center' },
				{field:'createTime',title:'创建时间',width:'30%',align:'center'}
		    ]],  
	    onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'}); 
	        $('.deleterole').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});  
	        
	        if(data.rows.length==0){
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="4">没有数据</td></tr>');
			}
	        
	        if('' != lpbuId)
	        	{
		        	 var selectedRows = $('#'+oruleDatagridId).datagrid('getRows');
		 			$.each(selectedRows,function(j,selectedRow){
		 				
		 				if(selectedRow.id == lpbuId){
		 					$("input[name='basetypeselectRadio']")[j].checked = true;
		 				}
		 			});
	        	}
	       
	        
	        
	    },
        onClickRow: function(rowIndex, rowData){
            //加载完毕后获取所有的checkbox遍历
            var radio = $("input[name='basetypeselectRadio']")[rowIndex].disabled;
            //如果当前的单选框不可选，则不让其选中
            if (radio!= true) {
                //让点击的行单选按钮选中
            	$("input[name='basetypeselectRadio']")[rowIndex].checked = true;
                lpbuId = rowData.id;//返回该行的id 
            }
            else {
            	$("input[name='basetypeselectRadio']")[rowIndex].checked = false;
                lpbuId = '';//返回该行的id 
            }
        }
	});
}

/**
 * 初始化区域彩种数据
 * @param lpDatagridId
 * @param lpId
 */
function initLotteryPlayDatagrid(lpDatagridId,lpId)
{
	var params = new Object();
	
	lplayId = getCheckLotteryPlayid(lpId);
	
	
	$('#'+lpDatagridId).datagrid({
		singleSelect:true,
		rownumbers:false,
		queryParams: params,
		url:contextPath + '/lotteryManage/getLotteryPlayList.action',//'datagrid_data1.json',
		method:'get',
		border:false,
		singleSelect:false,
		fit:true,//datagrid自适应
		fitColumns:true,
		pagination:true,
		collapsible:false,
		pageSize:5,//初始化页面显示条数的值是根据pageList的数组中的值来设置的，否则无法正确设置
		pageList:[5,10],
		columns:[[
				{  
                field: 'oid', title: '', width: '5%', align: 'center',  
                //调用formater函数对列进行格式化，使其显示单选按钮（所有单选按钮name属性设为统一，这样就只能有一个处于选中状态）  
                formatter: function (value, row, index) {  
                     var s = '<input name="lplayIdselectRadio"  type="radio" id="selectRadio"'+index+'  /> ';  
  
                    return s;  
                }  
  
            }  ,
				{field:'id',hidden:true},
				{field:'name',title:'彩种名称',width:'40%',align:'center'},
				{field:'createTime',title:'创建时间',width:'40%',align:'center'}
		    ]],  
	    onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'}); 
	        $('.deleterole').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});  
	        
	        if(data.rows.length==0){
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="4">没有数据</td></tr>');
			}
	        
	        if('' != lplayId)
	        	{
		        	 var selectedRows = $('#'+lpDatagridId).datagrid('getRows');
		 			$.each(selectedRows,function(j,selectedRow){
		 				
		 				if(selectedRow.id == lplayId){
		 					$("input[name='lplayIdselectRadio']")[j].checked = true;
		 				}
		 			});
	        	}
	       
	        
	        
	    },
        onClickRow: function(rowIndex, rowData){
            //加载完毕后获取所有的checkbox遍历
            var radio = $("input[name='lplayIdselectRadio']")[rowIndex].disabled;
            //如果当前的单选框不可选，则不让其选中
            if (radio!= true) {
                //让点击的行单选按钮选中
            	$("input[name='lplayIdselectRadio']")[rowIndex].checked = true;
                lplayId = rowData.id;//返回该行的id 
            }
            else {
            	$("input[name='lplayIdselectRadio']")[rowIndex].checked = false;
                lplayId = '';//返回该行的id 
            }
        }
	});
}


/**
 * 获取当前区域预测类型对应的基本预测类型id
 * @param lpId，补录id
 */
function getCheckIpbuId(lpId)
{
	var ipbuId = '';
	var data = new Object();
	
	data.id = lpId;
	
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: contextPath+'/ptype/getBaseptypeId.action',
        data:data,
        dataType: "json",
        success: function (data) {
        	ipbuId = data.lpBuluId;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/menu/error.action";
        }
   });
	
	return ipbuId;
}

/**
 * 获取区域预测类型对应的区域彩种id
 * @param lpId
 * @returns
 */
function getCheckLotteryPlayid(lpId)
{
	var lplayId = '';
	var data = new Object();
	
	data.id = lpId;
	
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: contextPath+'/ptype/getLotteryPlayIdOfPtype.action',
        data:data,
        dataType: "json",
        success: function (data) {
        	lplayId = data.lpidOfptype;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/menu/error.action";
        }
   });
	
	return lplayId;
}


/**
 * 提交保存
 */
function submitAddPredictionType()
{
	$('#ff').form('submit',{
		url:contextPath+'/ptype/saveOrUpdatePredictionType.action',
		onSubmit:function(param){
			var flag = false;
			
			param.lpbuId=lpbuId;
			if($('#ff').form('enableValidation').form('validate') )
				{
					if(null != lpbuId && '' != lpbuId)
						{
							param.basePredictionTypeId = lpbuId;
							
							if(null != lplayId && '' != lplayId)
								{
									param.lotteryPlayId = lplayId;
									flag = true;
								}
							else
								{
									$.messager.alert('提示', "请选择区域彩种!");
								}
							
							
						}
					else
						{
							$.messager.alert('提示', "请选择基础预测类型!");
						}
				}
			return flag;
		},
	    success:function(data){
	    	//提交表单后，从后台返回的data类型为String，要获取信息需要将其转换为json类型，使用eval("(" + data + ")")方法转换
	    	$.messager.alert('提示', eval("(" + data + ")").message);
	    	$("#addPredictionType").dialog('close');//初始化添加应用弹框关闭
	    	
	    	//添加角色后刷新数据列表
	    	$('#ff').form('clear');//清空表单内容
	    	initDatagrid();
	    	lpbuId = '';	  
	    	lplayId = '';	    	
	    }
	});
}



/**
 * 提交修改
 */
function submitUpdatePredictionType()
{
	$('#ffUpdate').form('submit',{
		url:contextPath+'/ptype/saveOrUpdatePredictionType.action',
		onSubmit:function(param){
			var flag = false;
			param.lpbuId=lpbuId;
			if($('#ffUpdate').form('enableValidation').form('validate') )
				{
					if(null != lpbuId && '' != lpbuId)
					{
						param.basePredictionTypeId = lpbuId;
						
						if(null != lplayId && '' != lplayId)
							{
								param.lotteryPlayId = lplayId;
								flag = true;
							}
						else
							{
								$.messager.alert('提示', "请选择区域彩种!");
							}
						
						
					}
					else
						{
							$.messager.alert('提示', "请选择基础预测类型!");
						}
				}
			return flag;
		},
	    success:function(data){
	    	//提交表单后，从后台返回的data类型为String，要获取信息需要将其转换为json类型，使用eval("(" + data + ")")方法转换
	    	$.messager.alert('提示', eval("(" + data + ")").message);
	    	$("#updatePredictionType").dialog('close');//初始化添加应用弹框关闭
	    	
	    	//添加角色后刷新数据列表
	    	$('#ffUpdate').form('clear');//清空表单内容
	    	initDatagrid();
	    	lpbuId = '';	
	    	lplayId = '';	
	    	
	    }
	});
}