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
		pageList:[10,20,30,50],
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
				{field:'opt',title:'操作',width:'20%',align:'center',  
			            formatter:function(value,row,index){  
			            	 var btn = '<a class="editcls" onclick="updateLGroup(&quot;'+row.id+'&quot;)" href="javascript:void(0)">修改</a>'
			            				+'<a class="memberManage" onclick="manageGroupMember(&quot;'+row.id+'&quot;)" href="javascript:void(0)">群成员管理</a>'
			            				+'<a class="addMember" onclick="addGroupMember(&quot;'+row.id+'&quot;)" href="javascript:void(0)">添加群成员</a>'
					                	+'<a class="delete" onclick="deleteLotteryGroup(&quot;'+row.id+'&quot;)" href="javascript:void(0)">删除</a>';
			            	return btn;  
			            }  
			        }  
		    ]],  
	    onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({text:'修改',plain:true,iconCls:'icon-edit'}); 
	        $('.delete').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});  
	        $('.memberManage').linkbutton({text:'群成员管理',plain:true,iconCls:'icon-reload'});
	        $('.addMember').linkbutton({text:'添加群成员',plain:true,iconCls:'icon-reload'});
	        if(data.rows.length==0){
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="10">没有数据</td></tr>');
			}
	        
	        
	    }
	});
}








//删除群成员
function removeMemberFromGroup(memberId,groupId,isGroupOwner)
{
	var url = contextPath + '/outerLGroup/quitUserFronGroup.action';
	var data1 = new Object();
	var flag = true;
	
	if(1 == isGroupOwner)
		{
			flag = false;
			$.messager.alert('提示', "群主不可以删除!");
		}
	
	if(flag)
		{
			var codearr = [];
			codearr.push(memberId);
			
			data1.quitUsers=codearr.toString();
			data1.groupId = groupId;
			
			$.messager.confirm("提示", "您确认移除当前群成员？", function (r) {  
		        if (r) {  
			        	$.ajax({
			        		async: false,   //设置为同步获取数据形式
			                type: "get",
			                url: url,
			                data:data1,
			                dataType: "json",
			                success: function (data) {
			                	manageGroupMember(groupId);//重新加载群成员列表
			                	$.messager.alert('提示', data.message);
			                },
			                error: function (XMLHttpRequest, textStatus, errorThrown) {
			                    window.parent.location.href = contextPath + "/menu/error.action";
			                }
			           });
			        	
		        }  
		    });  
		}
	
}

//获取群主列表
function initGroupListDatagrid(datagridId)
{
	var params = new Object();
	
	params.isVirtual = "0";//获取非虚拟用户
	params.isRobot = "0";//获取非机器人用户
	
	$('#'+datagridId).datagrid({
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
				{field:'ck',checkbox:true},
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
	        
	       
	        
	        
	    }
	});
}

//添加群
function addGroupMsg()
{
	//初始化
	$("#ttA").tabs('select',0);
	//初始化添加弹框中的初始值
	initProvince('add','privinceA','');//默认选中全部，则全部下是没有市数据的
	initGroupListDatagrid('groupListA');
	
	$("#addGroupMsg").dialog('open');
	
}

//关闭添加彩聊群弹框
function closeAddGroupDialog()
{
 //若已经上传了头像，则删除头像图片
  checkDeleteFujian();
  $('#addGroupMsg').dialog('close');
  $("#updateGroupMsg").dialog('close');
  $('#ff').form('clear');//清空表单内容
}

/**
 * 校验是否需要删除冗余附件数据
 */
function checkDeleteFujian()
{
	//若点击添加文章后已经上传了附件，则要将附件数据删除，若没有上传附件则可以正常退出
	var uploadId = '';//上传附件id
	if(''!=$("#touXiangA").val())
	{
		uploadId = $("#touXiangA").val();
		//调用删除方法
		deleteImgsByNewsuuid(uploadId);
	}
}



//删除图片
function deleteImgsByNewsuuid(newsUuid)
{
	var data = new Object();
	data.newsUuid = newsUuid;
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: contextPath+'/lbuyerOrexpert/deleteImgsByNewsuuid.action',
        data:data,
        dataType: "json",
        success: function (returndata) {
        	
      			console.log("删除成功");
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/menu/error.action";
        }
   });
	        	
	
	
}

function reset()
{
	initQueryProvince();
	$("#typeC").combobox('setValue','');
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
					ssZjChaxun:data.ssZjChaxun,
					touXiang:data.touXiang,
					noticeReview:data.noticeReview,
					lotteryType:data.lotteryType,
					detailLotteryType:data.detailLotteryType
					
				});
				
				//初始化省份combobox
				initProvince("update", "privinceU", data.province);
				//初始化市级区域combobox
				initCities('update','cityU',data.city,data.province);
				
				ownerId = data.ownerId;
				initOwnerListDatagrid('ownerListU');
				
				$("#touxiangImgU").attr("src",contextPath+data.touXiangImgUrl);
				
				if(4 == data.lotteryType)
					{
						$("#zxqTypeDivU").show();
					}
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/menu/error.action";
        }
	});
	
	
	$("#updateLotteryGroup").dialog('open');
}


function closeDialog()
{
	$("#addGroupMsg").dialog('close');
	$("#uploadShowAimgPreview").dialog('close');
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

function openDialog(dialogId,addorupdate){
	var createUUID = (function (uuidRegEx, uuidReplacer) { 
		 return function () { 
		 return"xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(uuidRegEx, uuidReplacer).toUpperCase(); 
		};
		 })(/[xy]/g, function (c) { 
		 var r = Math.random() * 16 | 0, 
		 v = c =="x"? r : (r & 3 | 8); 
		 return v.toString(16); 
		});
	
	var uploadId ;
	if('update'==addorupdate)
		{
			
			uploadId = $("#touXiangU").val();
			
			if(null == uploadId || '' == uploadId)//若附件id为空，则生成附件id并放入值中
				{
					uploadId = createUUID();
					$("#touXiangU").val(uploadId);
				}
			
		}
	else
		if('add'==addorupdate)
		{
			if(''==$("#touXiangA").val())
				{
					uploadId = createUUID();
					$("#touXiangA").val(uploadId);
				}
			else
				{
					uploadId = $("#touXiangA").val();
				}
			
		}
	
//	var url = 'uploadFile.jsp?uploadId='+uploadId;
	var url = contextPath+'/menu/uploadFile.action?uploadId='+uploadId;
	$('#'+dialogId).dialog({
	    title: '上传头像',
	    width: 500,
	    height: 300,
	    closed: false,
	    cache: false,
	    content: '<iframe id="'+uploadId+'"src="'+url+'" width="100%" height="100%" frameborder="0" scrolling="auto" ></iframe>',  
//	    href: 'uploadFile.jsp?uploadId='+uploadId,
	    modal: true,
	    onClose:function(){
	    		initImgShow(uploadId,addorupdate);
	    	}
	});
	
	
}

//初始化图片展示
function initImgShow(uploadId,addorupdate)
{
	var data1 = new Object();
	data1.newsUuid = uploadId;
	
	var url = contextPath+'/menu/getUploadFileData.action';
	$.ajax({
		async: false, //设置为同步获取数据形式
        type: "get",
        url: url,
        data:data1,
        dataType: "json",
        success: function (data) {
        	var touXiangImgUrl = contextPath+data.uploadfilepath+data.uploadRealName;
        	if('add' == addorupdate)
        		$("#touxiangImgA").attr("src",touXiangImgUrl);
        	else
        		$("#touxiangImgU").attr("src",touXiangImgUrl);
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/menu/error.action";
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
	
	$("#ttA").tabs({
		   onSelect:function(title,index){
		        $("#imgOrWordA").val(index);
		   }
		});
	
	$("#ttU").tabs({
		   onSelect:function(title,index){
		        $("#imgOrWordU").val(index);
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





		