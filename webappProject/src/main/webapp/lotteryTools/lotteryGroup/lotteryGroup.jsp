<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>彩票站管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link href="<%=request.getContextPath() %>/css/ztree/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <jsp:include page="../../common/top.jsp" flush="true" /> 
    <script src="<%=request.getContextPath() %>/lotteryTools/lotteryGroup/js/lotteryGroup.js" type="text/javascript"></script>
    
    <script type="text/javascript">
  	var toolbar = [{
  	    text:'添加群',
  	    iconCls:'icon-add',
  	    handler:function(){
  	    	
  	    	addLotteryGroup();
  	    	
  	    }
  	} ];
  	
  	/* var addmemberToolbar = [{
  	    text:'批量添加用户到群',
  	    iconCls:'icon-add',
  	    handler:function(){
  	    	
  	    	batchAddMemberToGroup();
  	    	
  	    }
  	} ]; */
  	  
  	
		
	</script>
		<style type="text/css">
			
			 .ftitle{
	  			width:50%;
	  			float : left;
	  			margin-bottom: 20px;
	  			font-family:'微软雅黑',
	  		}
	  		.ftitle label{
	  			float : left;
	  			margin-left: 30px;
	  			width:100px;
	  		}
	  		.ftitle .commonInput{
	  			float : left;
	  			width: 200px;
	  			margin-left: 30px;
	  			border-radius : 5px;
	  		}
	  		
	  		/*非validatebox的样式*/
	  		.textbox{
	  			float : left;
	  			width: 200px;
	  			margin-left: 30px;
	  			border-radius : 5px;
	  		}
	  		
	  		.td_font{
	  			font-weight:bold;
	  		}
	  		
	  		.input_border{
	  			width:150px;
	  			border-radius : 5px;
	  		}
	  		
	  		#main-layout{     min-width:1050px;     min-height:240px;     overflow:hidden; }
	  		
	  		.toolbarTb div{
	  			float:left;
	  		}
	  		
	  		.showName
	  		{
	  			border:none;
	  			
	  		}
	  		
		</style>
		
	 
</head>
<body class="easyui-layout">
	<!-- 模糊查询 -->
	<div   data-options="region:'north'" style="height:90px;border:1px solid #95b8e7; background-color:white;">
	    	<table style="border: none;height: 80px;">
		    	<tr>
		    		<td width="7%" class="td_font">群名称：</td>
		    		<td width="15%">
		    			<input id="nameC" class="input_border"  type="text" name="nameC"  />  
		    		</td>
		    		<td width="7%" class="td_font">彩种分类：</td>
		    		<td width="15%">
		    			<select class="easyui-combobox" id="lotteryTypeC" name="lotteryTypeC" style="width:100px;">
								<option value="">全部</option>
								<option value="1">体彩</option>
								<option value="2">福彩</option>
								<option value="3">竞彩</option>
								<option value="4">中心群</option>
								<option value="5">公司群</option>
						</select>
		    		</td>
		    		<td width="7%" class="td_font">省：</td>
		    		<td width="15%">
		    			<select class="easyui-combobox" id="privinceC" name="privinceC" style="width:200px;">
						</select>
		    		</td>
		    		
		    		<td class="td_font" width="12%">
		    			<input style="cursor:pointer;background-color: #e0ecff;border-radius:5px;float:left" type="button" value="查询" onclick="initDatagrid()">
		    			<input style="cursor:pointer;background-color: #e0ecff;border-radius:5px;float:left;margin-left:5px;" type="button" value="重置" onclick="reset()">
		    		</td>
		    	</tr>
	    	</table>	
	</div>

    <div  data-options="region:'center'" data-options="border:false" >
    	 <table id="datagrid" class="easyui-datagrid"  title="彩聊群列表" >
			</table>
 	</div>  
  
  
    <!-- 添加群弹框 -->
  <div id="addLotteryGroup" class="easyui-dialog" fit="true" title="添加彩聊群" style="width:800px;height:600px;padding:0px;border:0;top:1px;"
            data-options="
            modal:true,
                iconCls: 'icon-save',
                buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitAddLotteryGroup();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                    	closeAddGroupDialog();
                       
                    }
                }]
            ">
		
			<div class="easyui-layout" style="height:95%;padding:0;width:100%;" >
	    	 	<div region="north" style="height:65%;" title="彩聊群内容" hide="false">
	    	 		<form id="ff" method="get" novalidate style="margin-top:5px;">
		    	 		<div class="ftitle">
				            <label for="codeA">群名:</label>
				            <input type="hidden" name="id" id="idA"/>
				           <input class="easyui-validatebox commonInput" type="text" id="nameA" name="name" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				         <div class="ftitle">
				            <label for="lotteryTypeA" >类型:</label>
					    			<select class="easyui-combobox" id="lotteryTypeA" name="lotteryType" style="width:200px;">
										<option value="1" >体彩群</option>
										<option value="2">福彩群</option>
										<option value="3">竞彩群</option>
										<option value="4">中心群</option>
										<option value="5">公司群</option>
									</select>
				        </div>
						<div class="ftitle">
				            <label for="subject">用户地域:</label>
				            <div style="">
				           		<!-- <label for="privinceA">省:</label> -->
					            <select class="easyui-combobox " id="privinceA" name="province"  
					          	  data-options="editable:false" style="width:150px;" >
								</select>
								<!-- <label for="cityA">市:</label> -->
								<select class="easyui-combobox " id="cityA" name="city"  
					          	  data-options="editable:false" style="width:150px;" >
								</select>
				            </div>
				        </div>
				        
					    
					   <div class="ftitle">
				            <label for="priceA">发布开奖画面:</label>
				            <select class="easyui-combobox" id="fabuKjA" name="fabuKj" style="width:200px;">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
				        </div>
				        
				         <div class="ftitle">
				            <label for="priceA">发布走势画面:</label>
				            <select class="easyui-combobox" id="fabuZsA" name="fabuZs" style="width:200px;">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
				        </div>
				        
				         <div class="ftitle">
				            <label for="priceA">实时遗漏查询:</label>
				            <select class="easyui-combobox" id="ssYlChaxunA" name="ssYlChaxun" style="width:200px;">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
				        </div>
				        
				         <div class="ftitle">
				            <label for="priceA">实时开奖查询:</label>
				            <select class="easyui-combobox" id="ssKjChaxunA" name="ssKjChaxun" style="width:200px;">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
				        </div>
				        
				         <div class="ftitle">
				            <label for="priceA">实时专家查询:</label>
				            <select class="easyui-combobox" id="ssZjChaxunA" name="ssZjChaxun" style="width:200px;">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
				        </div>
				        
				         <div class="ftitle">
				            <label for="priceA">群公告是否审核:</label>
				            <select class="easyui-combobox" id="noticeReviewA" name="noticeReview" style="width:200px;">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
				        </div>
				        
				        <div class="ftitle">
				            <label for="subject">头像:</label>
				            <input type="hidden" id="touXiangA" name="touXiang">
				             <a href="#" id="uploadA" class="l-btn l-btn-small" plain="true" onclick="openDialog('ddA','add')" style="width:100px;">点击上传头像</a>
				       		 <img id="touxiangImgA" style="width:300px;height:300px;" alt="点击放大" src="" onclick="previewImage('touxiangImgA')">
				        </div>
				      
				        
				        <div class="ftitle" >
				            <label for="introductionA">群描述:</label>
				            <textarea id="introductionA" name="introduction" class="easyui-validatebox" placeholder="请输入群简介"
					         	 validType="length[0,100]" style="resize:none;width:350px;height:100px;border-radius:5px;margin-left: 30px;"></textarea>
					    </div>
				        
				     </form>
	    	 	</div>
	    	 	<div region="center" style="height:33%;width:99%;" title="选择群主(必选)">
	    	 		<table id="ownerListA" class="easyui-datagrid" style="width:100%;height:auto;"   ></table>
	    	 	</div>
    		</div>
	     
    </div>
    
    
     <!-- 修改群弹框 -->
      <div id="updateLotteryGroup" class="easyui-dialog" fit="true" title="修改彩聊群" style="width:800px;height:600px;padding:0px;border:0;top:1px;"
            data-options="
            modal:true,
                iconCls: 'icon-save',
                buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitUpdateLotteryGroup();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#updateLotteryGroup').dialog('close');
                        $('#ffUpdate').form('clear');//清空表单内容
                    }
                }]
            ">
		
			<div class="easyui-layout" style="height:95%;padding:0;width:100%;" >
	    	 	<div region="north" style="height:63%;" title="彩聊群内容" hide="false">
	    	 		<form id="ffUpdate" method="get" novalidate style="margin-top:5px;">
		    	 		<div class="ftitle">
				            <label for="codeA">群名:</label>
				            <input type="hidden" name="id" id="idA"/>
				           <input class="easyui-validatebox commonInput" type="text" id="nameU" name="name" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				         <div class="ftitle">
				            <label for="lotteryTypeA" >类型:</label>
					    			<select class="easyui-combobox" id="lotteryTypeU" name="lotteryType" style="width:200px;">
										<option value="1" >体彩群</option>
										<option value="2">福彩群</option>
										<option value="3">竞彩群</option>
										<option value="4">中心群</option>
										<option value="5">公司群</option>
									</select>
				        </div>
						<div class="ftitle">
				            <label for="subject">用户地域:</label>
				            <div style="">
				           		<!-- <label for="privinceA">省:</label> -->
					            <select class="easyui-combobox " id="privinceU" name="province"  
					          	  data-options="editable:false" style="width:150px;" >
								</select>
								<!-- <label for="cityA">市:</label> -->
								<select class="easyui-combobox " id="cityU" name="city"  
					          	  data-options="editable:false" style="width:150px;" >
								</select>
				            </div>
				        </div>
				     
					   <div class="ftitle">
				            <label for="priceA">发布开奖画面:</label>
				            <select class="easyui-combobox" id="fabuKjU" name="fabuKj" style="width:200px;">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
				        </div>
				        
				         <div class="ftitle">
				            <label for="priceA">发布走势画面:</label>
				            <select class="easyui-combobox" id="fabuZsU" name="fabuZs" style="width:200px;">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
				        </div>
				        
				         <div class="ftitle">
				            <label for="priceA">实时遗漏查询:</label>
				            <select class="easyui-combobox" id="ssYlChaxunU" name="ssYlChaxun" style="width:200px;">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
				        </div>
				        
				         <div class="ftitle">
				            <label for="priceA">实时开奖查询:</label>
				            <select class="easyui-combobox" id="ssKjChaxunU" name="ssKjChaxun" style="width:200px;">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
				        </div>
				        
				         <div class="ftitle">
				            <label for="priceA">实时专家查询:</label>
				            <select class="easyui-combobox" id="ssZjChaxunU" name="ssZjChaxun" style="width:200px;">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
				        </div>
				        
				        
				         <div class="ftitle">
				            <label for="priceA">群公告是否审核:</label>
				            <select class="easyui-combobox" id="noticeReviewU" name="noticeReview" style="width:200px;">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
				        </div>
				        
				         <div class="ftitle">
				            <label for="priceA">头像:</label>
				            <input type="hidden" id="touXiangU" name="touXiang">
				             <a href="#" id="uploadU" class="l-btn l-btn-small" plain="true" onclick="openDialog('ddA','update')" style="width:100px;">点击上传头像</a>
				            <img id="touxiangImgU" style="width:300px;height:300px;" alt="点击放大" src="" onclick="previewImage('touxiangImgU')">
				        </div>
				        
				        <div class="ftitle" >
				            <label for="introductionA">群描述:</label>
				            <textarea id="introductionU" name="introduction" class="easyui-validatebox" placeholder="请输入群简介"
					         	 validType="length[0,100]" style="resize:none;width:350px;height:100px;border-radius:5px;margin-left: 30px;"></textarea>
					    </div>
					    
				        
				     </form>
	    	 	</div>
	    	 	<div region="center" style="height:35%;padding:0;width:99%;" title="选择群主(必选)">
	    	 		<table id="ownerListU" class="easyui-datagrid" style="width:100%;height:95%;"></table>
	    	 	</div>
    		</div>
	     
    </div>
    <!-- 上传图片弹框 -->
     <div id="ddA">Dialog Content.</div>
     <!-- 管理群成员dialog -->
     <div id="manageMemberDiv" class="easyui-dialog" fit="true" title="管理群成员" style="width:800px;height:600px;padding:0px;border:0;top:1px;"
            data-options="
            modal:true,
                iconCls: 'icon-save',
                buttons: [{
                    text:'关闭',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#manageMemberDiv').dialog('close');
                        $('#memberDatagrid').datagrid('loadData', { total: 0, rows: [] });  
                        ownerId = '';
                    }
                }]
            ">
     	 <table id="memberDatagrid" class="easyui-datagrid"  title="群成员列表" >
			</table>
     </div>
     
      <!-- 添加群成员dialog -->
     <div id="addMemberDiv" class="easyui-dialog" fit="true" title="添加群成员" style="width:800px;height:600px;padding:0px;border:0;top:1px;"
            data-options="
            modal:true,
                iconCls: 'icon-save',
                buttons: [{
                    text:'关闭',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#addMemberDiv').dialog('close');
                        $('#addMemberDatagrid').datagrid('loadData', { total: 0, rows: [] });  
                        ownerId = '';
                    }
                }]
            ">
     	 <table id="addMemberDatagrid" class="easyui-datagrid"  title="用户列表" >
			</table>
     </div>
    
     
     <div id="uploadShowAimgPreview" title="图片预览" class="easyui-dialog" data-options="modal:true"  style="width:700px; height:500px;"> </div>
</body>
	
	
</html>