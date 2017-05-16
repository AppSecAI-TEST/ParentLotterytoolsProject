var ownerId ='';
$(function()
		{
			initQueryProvince();
			
			closeDialog();//页面加载时关闭弹框
			
			initDatagrid();//初始化数据列表
			
			bindComboboxChange();//绑定级联事件
			
		});

function initDatagrid()
{
	var params = new Object();
	params.lotteryType = $("#lotteryTypeC").combobox('getValue');
	params.province = $("#privinceC").combobox('getValue');
	params.name = $("#nameC").val();
	
	$('#datagrid').datagrid({
		rownumbers:false,
		queryParams: params,
		url:contextPath +'/lgroup/getGroupList.action',
		method:'get',
		border:false,
		singleSelect:false,
		fit:true,//datagrid自适应
		fitColumns:true,
		pagination:true,
		toolbar:toolbar,
		collapsible:false,
		columns:[[
				{field:'ck',checkbox:true},
				{field:'id',hidden:true},
				{field:'name',title:'群名称',width:'12%',align:'center'},
		        {field:'provinceName',width:'10%',title:'省',align:'center'},
		        {field:'cityName',width:'10%',title:'市',align:'center'},
				{field:'lotteryType',width:'10%',title:'彩种',align:'center',  
		            formatter:function(value,row,index){  
		            	var lotteryTypeName ='';
		            	switch(value)
		            	{
		            		case '1':lotteryTypeName='体彩';break;
		            		case '2':lotteryTypeName='福彩';break;
		            		case '3':lotteryTypeName='竞彩';break;
		            		case '4':lotteryTypeName='中心群';break;
		            		case '5':lotteryTypeName='公司群';break;
		            	}
		            	return lotteryTypeName;  
		            }  },
				{field:'ownerName',title:'群主昵称',width:'8%',align:'center'},
				{field:'groupNumber',title:'群号',width:'10%',align:'center'},
				{field:'createTimeStr',title:'创建时间',width:'15%',align:'center'},
				{field:'opt',title:'操作',width:'15%',align:'center',  
			            formatter:function(value,row,index){  
			            	 var btn = '<a class="editcls" onclick="updateLGroup(&quot;'+row.id+'&quot;)" href="javascript:void(0)">修改</a>'
					                	+'<a class="delete" onclick="deleteLotteryGroup(&quot;'+row.id+'&quot;)" href="javascript:void(0)">删除</a>';
			            	return btn;  
			            }  
			        }  
		    ]],  
	    onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({text:'修改',plain:true,iconCls:'icon-edit'}); 
	        $('.delete').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});  
	        
	        if(data.rows.length==0){
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="10">没有数据</td></tr>');
			}
	        
	        
	    }
	});
}

//获取群主列表
function initOwnerListDatagrid(ownerDatagridId)
{
	var params = new Object();
	
	params.isVirtual = "0";//获取非虚拟用户
	params.isRobot = "0";//获取非机器人用户
	
	$('#'+ownerDatagridId).datagrid({
		singleSelect:true,
		rownumbers:false,
		queryParams: params,
		url:contextPath + '/lbuyerOrexpert/getLborExpertList.action',//'datagrid_data1.json',
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
				{field:'name',title:'昵称',width:'15%',align:'center'},
				{field:'isPhone',width:'15%',title:'手机用户',align:'center',  
		            formatter:function(value,row,index){  
		            	var numOrCharName ='';
		            	switch(value)
		            	{
		            		case '0':numOrCharName='否';break;
		            		case '1':numOrCharName='是';break;
		            	}
		            	return numOrCharName;  
		            }  },
		        {field:'provinceName',title:'省',width:'15%',align:'center'},
		        {field:'cityName',title:'市',width:'15%',align:'center'},
		        {field:'cailiaoName',title:'彩聊名',width:'15%',align:'center'},
		        {field:'isGroupOwner',width:'15%',title:'站主',align:'center',  
		            formatter:function(value,row,index){  
		            	var numOrCharName ='';
		            	switch(value)
		            	{
		            		case '0':numOrCharName='否';break;
		            		case '1':numOrCharName='是';break;
		            	}
		            	return numOrCharName;  
		            }  }
		    ]],  
	    onLoadSuccess:function(data){  
	        if(data.rows.length==0){
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="8">没有数据</td></tr>');
			}
	        
	        if('' != ownerId)
	        	{
		        	 var selectedRows = $('#'+ownerDatagridId).datagrid('getRows');
		 			$.each(selectedRows,function(j,selectedRow){
		 				
		 				if(selectedRow.id == ownerId){
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
                ownerId = rowData.id;//返回该行的id 
            }
            else {
                $("input[type='radio']")[rowIndex].checked = false;
                ownerId = '';//返回该行的id 
            }
        }
	});
}

//添加群
function addLotteryGroup()
{
	//初始化
	//初始化添加弹框中的初始值
	$("#lotteryTypeA").combobox('setValue',"1");
	$("#fabuKjA").combobox('setValue',"0");
	$("#fabuZsA").combobox('setValue',"0");
	$("#ssYlChaxunA").combobox('setValue',"0");
	$("#ssKjChaxunA").combobox('setValue',"0");
	$("#ssZjChaxunA").combobox('setValue',"0");
	
	initProvince('add','privinceA','');//默认选中全部，则全部下是没有市数据的
	initOwnerListDatagrid('ownerListA');
	
	$("#addLotteryGroup").dialog('open');
}

function reset()
{
	initQueryProvince();
	$("#lotteryTypeC").combobox('setValue','');
	$("#nameC").val("");
}


//删除群
function deleteLotteryGroup(id)
{
	var url = contextPath + '/outerLGroup/deleteGroup.action';
	var data1 = new Object();
	
	
	data1.id=id;
	
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

//修改彩聊群
function updateLGroup(id)
{
	var url = contextPath + '/lgroup/getDetailLotteryGroup.action';
	var data1 = new Object();
	data1.id=id;//应用的id
	
	$.ajax({
		async: false, //设置为同步获取数据形式
        type: "get",
        url: url,
        data:data1,
        dataType: "json",
        success: function (data) {
        	
				$('#ffUpdate').form('load',{
					id:data.id,
					name:data.name,
					introduction:data.introduction,
					fabuKj:data.fabuKj,
					fabuZs:data.fabuZs,
					ssYlChaxun:data.ssYlChaxun,
					ssKjChaxun:data.ssKjChaxun,
					ssZjChaxun:data.ssZjChaxun
					
				});
				
				//初始化省份combobox
				initProvince("update", "privinceU", data.province);
				//初始化市级区域combobox
				initCities('update','cityU',data.city,data.province);
				
				ownerId = data.ownerId;
				initOwnerListDatagrid('ownerListU');
				
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/menu/error.action";
        }
	});
	
	
	$("#updateLotteryGroup").dialog('open');
}


function closeDialog()
{
	$("#addLotteryGroup").dialog('close');//初始化添加角色弹框关闭
	$("#updateLotteryGroup").dialog('close');//初始化添加角色弹框关闭
	$("#ddA").dialog('close');
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

/**
 * 初始化省下拉框
 * @param addOrUpdate
 * @param provinceId
 * @param pcode
 */
function initProvince(addOrUpdate,provinceId,pcode)
{
	$('#'+provinceId).combobox('clear');//清空combobox值
	
	var data = new Object();
	data.isHasall = false;//不包含"全部"
	
	$('#'+provinceId).combobox({
			queryParams:data,
			url:contextPath+'/menu/getProvinceList.action',
			valueField:'pcode',
			textField:'pname',
			 onLoadSuccess: function (data1) { //数据加载完毕事件
                 if (data1.length > 0 && "add" == addOrUpdate) 
                 {
                	 $("#"+provinceId).combobox('select',data1[0].pcode);
                 }
                 else
            	 {
                	//使用“setValue”设置选中值不会触发绑定事件导致多次加载市级数据，否则会多次触发产生错误
            	 	$("#"+provinceId).combobox('setValue', pcode);
            	 }
					
             }
		}); 
}

/**
 * 绑定上级角色下拉框改变事件
 */
function bindComboboxChange()
{
	//添加表单中的省份级联
	$("#privinceA").combobox({

		onSelect: function (rec) {
			 //加载市级数据
			 initCities('add','cityA','',rec.pcode);
		}

		}); 
	//修改表单中的省份级联
	$("#privinceU").combobox({

		onSelect: function (rec) {
			//加载市级数据
			initCities('update','cityU','',rec.pcode);
		}

		}); 
	
	
}

/**
 * 初始化市数据
 * @param addOrUpdate:标记当前是添加表单操作还是修改表单的操作,值为"add" 和"update"
 * @param cityId:当前操作的form表单的id
 * @param oldccode:应该选中的市数据code
 * @param pcode:级联的上级省code
 */
function initCities(addOrUpdate,cityId,oldccode,pcode)
{
	$('#'+cityId).combobox('clear');//清空combobox值
	var data = new Object();
	
	data.pcode = pcode;
	data.isHasall=false;
	$('#'+cityId).combobox({
			queryParams:data,
			url:contextPath+'/menu/getCityList.action',
			valueField:'ccode',
			textField:'cname',
			 onLoadSuccess: function (data1) { //数据加载完毕事件
                 if (data1.length > 0 && "add" == addOrUpdate) 
                 {
                	 $("#"+cityId).combobox('setValue',data1[data1.length-1].ccode);
                 }
                 else
            	 {
                	 
                	 if(data1.length > 0 &&"update" == addOrUpdate&&"" == oldccode)
                		 {//在修改表单中级联加载市级数据时也要默认选中全部
                		 	$("#"+cityId).combobox('select',data1[data1.length-1].ccode);
                		 }
                	 else
                		 {//当修改产品初始化市级数据时设置选中当前数据值
                		 	$("#"+cityId).combobox('select', oldccode);
                		 }
            	 }
					
             }
		}); 
}

function submitAddLotteryGroup()
{
	$('#ff').form('submit',{
		url:contextPath+'/lgroup/saveOrUpdateGroup.action',
		onSubmit:function(param){
			var flag = false;
			if($('#ff').form('enableValidation').form('validate') )
				{
					if(null != ownerId && '' != ownerId)
						{
							param.ownerId = ownerId;
							flag = true;
						}
					else
						{
							$.messager.alert('提示', "请选择群主!");
						}
				}
			return flag;
		},
	    success:function(data){
	    	//提交表单后，从后台返回的data类型为String，要获取信息需要将其转换为json类型，使用eval("(" + data + ")")方法转换
	    	$.messager.alert('提示', eval("(" + data + ")").message);
	    	$("#addLotteryGroup").dialog('close');//初始化添加应用弹框关闭
	    	
	    	//添加角色后刷新数据列表
	    	$('#ff').form('clear');//清空表单内容
	    	initDatagrid();
	    	ownerId = '';	   
	    	
	    }
	});
}

function submitUpdateLotteryGroup()
{
	$('#ffUpdate').form('submit',{
		url:contextPath+'/lgroup/saveOrUpdateGroup.action',
		onSubmit:function(param){
			var flag = false;
			if($('#ffUpdate').form('enableValidation').form('validate') )
				{
					if(null != ownerId && '' != ownerId)
					{
						param.ownerId = ownerId;
						flag = true;
					}
					else
					{
						$.messager.alert('提示', "请选择群主!");
					}
				}
			return flag;
		},
	    success:function(data){
	    	//提交表单后，从后台返回的data类型为String，要获取信息需要将其转换为json类型，使用eval("(" + data + ")")方法转换
	    	$.messager.alert('提示', eval("(" + data + ")").message);
	    	$("#updateLotteryGroup").dialog('close');//初始化添加应用弹框关闭
	    	
	    	//添加角色后刷新数据列表
	    	$('#ffUpdate').form('clear');//清空表单内容
	    	$("#lotteryTypeU").combobox('setValue',"1");
	    	initDatagrid();
	    	ownerId = '';	    	
	    	
	    }
	});
}





		