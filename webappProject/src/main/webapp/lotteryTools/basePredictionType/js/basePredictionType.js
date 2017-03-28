var lpbuId = '';//源码规则id

$(function()
		{
			
			closeDialog();//页面加载时关闭弹框
			
			initDatagrid();//初始化数据列表
			
			
		});





//关闭弹框
function closeDialog()
{
	$("#addBasePredictionType").dialog('close');//初始化添加角色弹框关闭
	$("#updateBasePredictionType").dialog('close');
}


function reset()
{
	
	$("#basePredictionNameC").val("");
	
}


/**
 * 初始化补录信息数据列表
 */
function initDatagrid()
{
	
	var params = new Object();
	params.basePredictionName = $("#basePredictionNameC").val().trim();//获取模糊查询条件“应用名称”
	
	$('#datagrid').datagrid({
		singleSelect:false,
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
		toolbar:toolbar,
		columns:[[
				{field:'ck',checkbox:true},
				{field:'id',hidden:true},
				{field:'basePredictionName',title:'基本预测类型名称',width:'15%',align:'center'},
				{field:'originDataSize',title:'源码数据量',width:'15%',align:'center'},
				{field:'nPlan',title:'N期计划',width:'10%',align:'center'},
				{field:'methodName',title:'对应预测方法名称',width:'15%',align:'center'},
				{field:'createTime',title:'创建时间',width:'15%',align:'center'},
				{field:'opt',title:'操作',width:'15%',align:'center',  
			            formatter:function(value,row,index){  
			                var btn = '<a class="editcls" onclick="updateBasePredictionType(&quot;'+row.id+'&quot;)" href="javascript:void(0)">编辑</a>'
			                	+'<a class="deleterole" onclick="deleteBasePredictionType(&quot;'+row.id+'&quot;)" href="javascript:void(0)">删除</a>';
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

function addBasePredictionType()
{
	$("#addBasePredictionType").dialog('open');
	initOrderRuleDatagrid('orderRuleA','');
}

/**
 * 修改基本预测类型
 * @param id
 */
function updateBasePredictionType(id)
{
	var url = contextPath + '/ptype/getBasePredictionType.action';
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
					originDataSize:data.originDataSize,
					nPlan:data.nPlan,
					basePredictionName:data.basePredictionName,
					methodName:data.methodName
					
				});
				
				initOrderRuleDatagrid('orderRuleU',id);
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/menu/error.action";
        }
	});
	
	
	$("#updateBasePredictionType").dialog('open');
	
	
}
/**
 * 删除基本预测类型
 * @param id
 */
function deleteBasePredictionType(id)
{
	var url = contextPath + '/ptype/deleteBasePredictionType.action';
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
function initOrderRuleDatagrid(oruleDatagridId,lpId)
{
	var params = new Object();
	
	lpbuId = getCheckIpbuId(lpId);
	
	
	$('#'+oruleDatagridId).datagrid({
		singleSelect:true,
		rownumbers:false,
		queryParams: params,
		url:contextPath + '/origin/getOriginDataRuleList.action',//'datagrid_data1.json',
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
                     var s = '<input name="selectRadio"  type="radio" id="selectRadio"'+index+'  /> ';  
  
                    return s;  
                }  
  
            }  ,
				{field:'id',hidden:true},
				{field:'ruleName',title:'源码规则名称',width:'30%',align:'center'},
				{field:'type',width:'30%',title:'源码筛选类型',align:'center',  
		            formatter:function(value,row,index){  
		            	var numOrCharName ='';
		            	switch(value)
		            	{
		            		case '0':numOrCharName='当前开奖号码';break;
		            		case '1':numOrCharName='关联期号方式';break;
		            		case '2':numOrCharName='期号方式';break;
		            	}
		            	return numOrCharName;  
		            }  },
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
		 					$("input[type='radio']")[j].checked = true;
		 				}
		 			});
	        	}
	       
	        
	        
	    },
        onClickRow: function(rowIndex, rowData){
            //加载完毕后获取所有的checkbox遍历
            var radio = $("input[type='radio']")[rowIndex].disabled;
            //如果当前的单选框不可选，则不让其选中
            if (radio!= true) {
                //让点击的行单选按钮选中
                $("input[type='radio']")[rowIndex].checked = true;
                lpbuId = rowData.id;//返回该行的id 
            }
            else {
                $("input[type='radio']")[rowIndex].checked = false;
                lpbuId = '';//返回该行的id 
            }
        }
	});
}


/**
 * 获取当前补录信息的补录方案id
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
        url: contextPath+'/ptype/getOrderRuleId.action',
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
 * 提交保存补录信息数据
 */
function submitAddLotteryPlay()
{
	$('#ff').form('submit',{
		url:contextPath+'/ptype/saveOrUpdateBasePType.action',
		onSubmit:function(param){
			var flag = false;
			
			param.lpbuId=lpbuId;
			if($('#ff').form('enableValidation').form('validate') )
				{
					if(null != lpbuId && '' != lpbuId)
						{
							param.originDataRuleId = lpbuId;
							flag = true;
						}
					else
						{
							$.messager.alert('提示', "请选择源码规则!");
						}
				}
			return flag;
		},
	    success:function(data){
	    	//提交表单后，从后台返回的data类型为String，要获取信息需要将其转换为json类型，使用eval("(" + data + ")")方法转换
	    	$.messager.alert('提示', eval("(" + data + ")").message);
	    	$("#addBasePredictionType").dialog('close');//初始化添加应用弹框关闭
	    	
	    	//添加角色后刷新数据列表
	    	$('#ff').form('clear');//清空表单内容
	    	initDatagrid();
	    	lpbuId = '';	   
	    	
	    }
	});
}



/**
 * 提交修改补录信息数据
 */
function submitUpdateLotteryPlay()
{
	$('#ffUpdate').form('submit',{
		url:contextPath+'/ptype/saveOrUpdateBasePType.action',
		onSubmit:function(param){
			var flag = false;
			param.lpbuId=lpbuId;
			if($('#ffUpdate').form('enableValidation').form('validate') )
				{
					if(null != lpbuId && '' != lpbuId)
					{
						param.originDataRuleId = lpbuId;
						flag = true;
					}
					else
					{
						$.messager.alert('提示', "请选择源码规则!");
					}
				}
			return flag;
		},
	    success:function(data){
	    	//提交表单后，从后台返回的data类型为String，要获取信息需要将其转换为json类型，使用eval("(" + data + ")")方法转换
	    	$.messager.alert('提示', eval("(" + data + ")").message);
	    	$("#updateBasePredictionType").dialog('close');//初始化添加应用弹框关闭
	    	
	    	//添加角色后刷新数据列表
	    	$('#ffUpdate').form('clear');//清空表单内容
	    	initDatagrid();
	    	lpbuId = '';	    	
	    	
	    }
	});
}