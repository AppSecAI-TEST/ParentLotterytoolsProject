$(function(){
	bindComboboxChange();//绑定级联事件
	
	initQueryProvince();
	
	closeDialog();//页面加载时关闭弹框
	
	initDatagrid();//初始化数据列表
	
	
});

function closeDialog()
{
	$("#addLotteryUserDiv").dialog('close');
	$("#updateLotteryUserDiv").dialog('close');
	$("#uploadShowAimgPreview").dialog('close');
}


function initDatagrid()
{
	var params = new Object();
	params.lotteryType = $("#lotteryTypeC").combobox('getValue');
	params.province = $("#privinceC").combobox('getValue');
	params.name = $("#nameC").val();
	params.isRobot="0";
	$('#datagrid').datagrid({
		rownumbers:false,
		queryParams: params,
		url:contextPath +'/lbuyerOrexpert/getLborExpertList.action',
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
				{field:'name',title:'昵称',width:'12%',align:'center'},
		        {field:'provinceName',width:'10%',title:'省',align:'center'},
		        {field:'cityName',width:'10%',title:'市',align:'center'},
				{field:'isPhone',width:'6%',title:'手机用户',align:'center',  
		            formatter:function(value,row,index){  
		            	var isPhoneName ='';
		            	switch(value)
		            	{
		            		case '1':isPhoneName='是';break;
		            		case '0':isPhoneName='否';break;
		            	}
		            	return isPhoneName;  
		            }  },
	            {field:'fromApp',width:'6%',title:'来自App',align:'center',  
		            formatter:function(value,row,index){  
		            	var isPhoneName ='';
		            	switch(value)
		            	{
		            		case '1':isPhoneName='是';break;
		            		case '0':isPhoneName='否';break;
		            	}
		            	return isPhoneName;  
		            }  },
	            {field:'isVirtual',width:'6%',title:'虚拟用户',align:'center',  
		            formatter:function(value,row,index){  
		            	var name ='';
		            	switch(value)
		            	{
		            		case '1':name='是';break;
		            		case '0':name='否';break;
		            	}
		            	return name;  
		            }  },
	            {field:'isExpert',width:'6%',title:'专家',align:'center',  
		            formatter:function(value,row,index){  
		            	var name ='';
		            	switch(value)
		            	{
		            		case '1':name='是';break;
		            		case '0':name='否';break;
		            	}
		            	return name;  
		            }  },
				{field:'createTimeStr',title:'创建时间',width:'15%',align:'center'},
				{field:'opt',title:'操作',width:'20%',align:'center',  
			            formatter:function(value,row,index){  
			            	 var btn = '<a class="editcls" onclick="updateUser(&quot;'+row.id+'&quot;)" href="javascript:void(0)">修改</a>'
					                	+'<a class="delete" onclick="deleteLotteryUser(&quot;'+row.id+'&quot;)" href="javascript:void(0)">删除</a>';
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

//删除彩聊用户
function deleteLotteryUser(id)
{
	var url = contextPath + '/lbuyerOrexpert/deleteLborExpert.action';
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

//添加彩聊用户
function addLotteryUser()
{
	$("#addLotteryUserDiv").dialog('open');
	//初始化值
	initProvince('add', 'provinceA', '');
}

function updateUser(id)
{
	var url = contextPath + '/lbuyerOrexpert/getDetailLborExpert.action';
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
					password:data.password,
					telephone:data.telephone,
					isPhone:data.isPhone,
					isVirtual:data.isVirtual,
					cailiaoName:data.cailiaoName,
					signature:data.signature,
					touXiang:data.touXiang
					
				});
				
				//初始化省份combobox
				initProvince("update", "provinceU", data.provinceCode);
				//初始化市级区域combobox
				initCities('update','cityU',data.cityCode,data.provinceCode);
				
				initRegion('regionCodeU', 'update', data.cityCode, data.regionCode);
				
				
				$("#touxiangImgU").attr("src",contextPath+data.touXiangUrl);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            window.parent.location.href = contextPath + "/menu/error.action";
        }
	});
	
	
	$("#updateLotteryUserDiv").dialog('open');
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
            	 	$("#"+provinceId).combobox('select', pcode);
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
	$("#provinceA").combobox({

		onSelect: function (rec) {
			 //加载市级数据
			initCities('add','cityA','',rec.pcode);
		}

		}); 
	//修改表单中的省份级联
	$("#provinceU").combobox({

		onSelect: function (rec) {
			//加载市级数据
			initCities('update','cityU','',rec.pcode);
		}

		}); 
	
	$("#cityA").combobox({

		onSelect: function (rec) {
			 //加载市级数据
			initRegion('regionCodeA','add',rec.ccode,'');
		}

		}); 
	$("#cityU").combobox({

		onSelect: function (rec) {
			//加载市级数据
			initRegion('regionCodeU','update',rec.ccode,'');
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
                	 $("#"+cityId).combobox('select',data1[data1.length-1].ccode);
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
//加载区
function initRegion(regionDivId,addOrUpdate,ccode,oldacode){
	
	//初始化乡镇区combo
	$('#'+regionDivId).combobox({
		url :  contextPath+'/menu/getRegionList.action',
		queryParams:{
			isHasall : false,    //不包含"全部",
			ccode : ccode
		},
		valueField:'acode',
		textField:'aname',
		 onLoadSuccess: function (data) { //数据加载完毕事件
             if (data.length > 0 && "add" == addOrUpdate) 
             {
            	 $('#'+regionDivId).combobox('select',data[0].acode);
             }
             else
        	 {
            	 if(data.length > 0 &&"update" == addOrUpdate&&"" == oldacode)
            		 {
            		 	$('#'+regionDivId).combobox('select',data[0].acode);
            		 }
            	 else
            		 {
            		 	$('#'+regionDivId).combobox('select', oldacode);
            		 }
        	 }
         }
	}); 
}


function submitAddLotteryUser()
{
	$('#ff').form('submit',{
		url:contextPath+'/lbuyerOrexpert/saveOrupdateFromBackStage.action',
		onSubmit:function(param){
			var flag = false;
			if($('#ff').form('enableValidation').form('validate') )
				{
					if(null != ownerId && '' != ownerId)
						{
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
	    	$("#addLotteryUserDiv").dialog('close');//初始化添加应用弹框关闭
	    	
	    	//添加角色后刷新数据列表
	    	$('#ff').form('clear');//清空表单内容
	    	initDatagrid();
	    	
	    }
	});
}

function submitUpdateLotteryUser()
{
	$('#ffUpdate').form('submit',{
		url:contextPath+'/lbuyerOrexpert/saveOrupdateFromBackStage.action',
		onSubmit:function(param){
			var flag = false;
			if($('#ffUpdate').form('enableValidation').form('validate') )
				{
				
				}
			return flag;
		},
	    success:function(data){
	    	//提交表单后，从后台返回的data类型为String，要获取信息需要将其转换为json类型，使用eval("(" + data + ")")方法转换
	    	$.messager.alert('提示', eval("(" + data + ")").message);
	    	$("#updateLotteryUserDiv").dialog('close');//初始化添加应用弹框关闭
	    	
	    	//添加角色后刷新数据列表
	    	$('#ffUpdate').form('clear');//清空表单内容
	    	initDatagrid();
	    	
	    }
	});
}