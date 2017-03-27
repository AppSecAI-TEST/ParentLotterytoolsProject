$(function(){
	
	closeDialog();
	initDatagrid();
});

function addOrderRule()
{
	$('#addOrderRule').dialog('open');
}

function closeDialog()
{
	$('#addOrderRule').dialog('close');//初始化添加角色弹框关闭
	 $('#updateOrderRule').dialog('close');
}

function initDatagrid()
{
	var params = new Object();
	params.ruleName = $("#ruleNameC").val();
	
	$('#datagrid').datagrid({
		singleSelect:false,
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
		toolbar:toolbar,
		columns:[[
				{field:'ck',checkbox:true},
				{field:'id',hidden:true},
				{field:'ruleName',title:'规则名称',width:'25%',align:'center'},
				{field:'type',width:'15%',title:'源码筛选方式',align:'center',  
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
		        {field:'cycle',title:'周期',width:'5%',align:'center'},
				{field:'createTime',title:'创建时间',width:'25%',align:'center'},
				{field:'opt',title:'操作',width:'25%',align:'center',  
			            formatter:function(value,row,index){  
			                var btn = '<a class="editcls" onclick="updateOrderRule(&quot;'+row.id+'&quot;)" href="javascript:void(0)">编辑</a>'
			                	+'<a class="deleterole" onclick="deleteOrderRule(&quot;'+row.id+'&quot;)" href="javascript:void(0)">删除</a>';
			                return btn;  
			            }  
			        }  
		    ]],  
	    onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'}); 
	        $('.deleterole').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});  
	        
	        if(data.rows.length==0){
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="6">没有数据</td></tr>');
			}
	        
	        
	    }
	});
}


function updateOrderRule(id)
{
	var url = contextPath + '/origin/getDetailOriginDataRule.action';
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
					ruleName:data.ruleName,
					type:data.type,
					locationOrContain:data.locationOrContain,
					ciLocationNumber:data.ciLocationNumber,
					ciRuleFiled:data.ciRuleFiled,
					liLocationNumber:data.liLocationNumber,
					liRuleFiled:data.liRuleFiled,
					cycle:data.cycle
					
				});
		
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/menu/error.action";
        }
	});
	
	
	$("#updateOrderRule").dialog('open');
	
	
}


function deleteOrderRule(id)
{
	var url = contextPath + '/origin/deleteOriginRules.action';
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
 * 添加源码规则
 */
function submitAddOrderRule()
{
	$('#ff').form('submit',{
		url:contextPath+'/origin/saveOrUpdateOriginRule.action',
		onSubmit:function(param){
			var flag = false;
			
			if($('#ff').form('enableValidation').form('validate') )
				{
					//如果当前源码筛选方式是当期规则或包含规则或是关联期规则，则其当期期号及当期字段以及上期期号及上期字段不可以为空
					var type = $("#typeA").combobox('getValue');
					
					if(0 == type || 1 == type)
						{
							//判断当前期的定位是否为空
							var ciLocationNumber = $("#ciLocationNumberA").val().trim();//获取当前期的位置
							if(null != ciLocationNumber || '' == ciLocationNumber)
								{
									if(1 == type)
									{
										var liLocationNumber = $("#liLocationNumberA").val().trim();//获取当前期的位置
										var ciRuleFiledA = $("#ciRuleFiledA").val().trim();
										var liRuleFiledA = $("#liRuleFiledA").val().trim();
										
										if(null == ciLocationNumber && '' == ciRuleFiledA)
											{
												$.messager.alert('提示', "当期定位位置和定位字段不可以同时为空");
											}
										else
											if(null == liLocationNumber && '' == liRuleFiledA)
											{
												$.messager.alert('提示', "上期定位位置和定位字段不可以同时为空");
											}
											else
												{
													flag = true;
												}
											
									}
									else
										{
											flag = true;
										}
								}
							else
								{
									$.messager.alert('提示', "当期定位位置数据不可以为空");
								}
							
						}
					else
						{
							flag = true;
						}
				}
			
			return flag;
		},
	    success:function(data){
	    	//提交表单后，从后台返回的data类型为String，要获取信息需要将其转换为json类型，使用eval("(" + data + ")")方法转换
	    	$.messager.alert('提示', eval("(" + data + ")").message);
	    	$("#addOrderRule").dialog('close');//初始化添加应用弹框关闭
	    	
	    	//添加角色后刷新数据列表
	    	$('#ff').form('clear');//清空表单内容
	    	initDatagrid();
	    	
	    	
	    }
	});
}

function submitUpdateOrderRule()
{
	$('#ffUpdate').form('submit',{
		url:contextPath+'/origin/saveOrUpdateOriginRule.action',
		onSubmit:function(param){
			var flag = false;
			
			if($('#ffUpdate').form('enableValidation').form('validate') )
				{
					//如果当前源码筛选方式是当期规则或包含规则或是关联期规则，则其当期期号及当期字段以及上期期号及上期字段不可以为空
					var type = $("#typeU").combobox('getValue');
					
					if(0 == type || 1 == type)
						{
							//判断当前期的定位是否为空
							var ciLocationNumber = $("#ciLocationNumberU").val().trim();//获取当前期的位置
							if(null != ciLocationNumber || '' == ciLocationNumber)
								{
									if(1 == type)
									{
										var liLocationNumber = $("#liLocationNumberU").val().trim();//获取当前期的位置
										var ciRuleFiledA = $("#ciRuleFiledU").val().trim();
										var liRuleFiledA = $("#liRuleFiledU").val().trim();
										
										if(null == ciLocationNumber && '' == ciRuleFiledA)
											{
												$.messager.alert('提示', "当期定位位置和定位字段不可以同时为空");
											}
										else
											if(null == liLocationNumber && '' == liRuleFiledA)
											{
												$.messager.alert('提示', "上期定位位置和定位字段不可以同时为空");
											}
											else
												{
													flag = true;
												}
											
									}
									else
									{
										flag = true;
									}
								}
							else
								{
									$.messager.alert('提示', "当期定位位置数据不可以为空");
								}
							
						}
					else
						{
							flag = true;
						}
				}
			
			return flag;
		},
	    success:function(data){
	    	//提交表单后，从后台返回的data类型为String，要获取信息需要将其转换为json类型，使用eval("(" + data + ")")方法转换
	    	$.messager.alert('提示', eval("(" + data + ")").message);
	    	$("#updateOrderRule").dialog('close');//初始化添加应用弹框关闭
	    	
	    	$('#ffUpdate').form('clear');//清空表单内容
	    	initDatagrid();
	    	
	    	
	    }
	});
}