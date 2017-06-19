<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>推送群通知管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link href="<%=request.getContextPath() %>/css/ztree/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <jsp:include page="../../common/top.jsp" flush="true" /> 
    <script src="<%=request.getContextPath() %>/lotteryTools/lotteryGroup/js/pushGroupNotice.js" type="text/javascript"></script>
    
    <script type="text/javascript">
  	 var toolbar = [{
  	    text:'发布群通知',
  	    iconCls:'icon-add',
  	    handler:function(){
  	    	
  	    	addGroupMsg();
  	    	
  	    }
  	} ]; 
  	  
  	
		
	</script>
		<style type="text/css">
			
			 .ftitle{
	  			width:100%;
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
	  		
	  		.textareaDiv{
	  			margin-left:30px;
	  			    float: left;
	  		}
	  		
	  		.textareaDiv textarea{
	  			border-radius:5px;
	  		}
	  		
		</style>
		
	 
</head>
<body class="easyui-layout">
	<!-- 模糊查询 -->
	<div   data-options="region:'north'" style="height:90px;border:1px solid #95b8e7; background-color:white;">
	    	<table style="border: none;height: 80px;">
		    	<tr>
		    		<td width="7%" class="td_font">推送类型：</td>
		    		<td width="15%">
		    			<select class="easyui-combobox" id="typeC" name="typeC" style="width:100px;">
		    					<option value="">全部</option>
								<option value="0">文字</option>
								<option value="1">图片</option>
								<option value="2">图文</option>
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
    	 <table id="datagrid" class="easyui-datagrid"  title="推送群通知数据列表" >
			</table>
 	</div>  
  
     <!-- 添加推送通知类型弹框 -->
  <div id="addGroupMsg" class="easyui-dialog" fit="true" title="添加群通知" style="width:800px;height:600px;padding:0px;border:0;top:1px;"
            data-options="
            modal:true,
                iconCls: 'icon-save',
                buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitAddGroupMsg();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                    	closeAddGroupMsgDialog();
                       
                    }
                }]
            ">
		
			<div class="easyui-layout" style="height:95%;padding:0;width:100%;" >
	    	 	<div region="north" style="height:60%;" title="群通知推送内容" hide="false" >
	    	 		<form id="ff" method="get"  style="margin-top:5px;" >
	    	 			
				        <!-- tabs -->
				        <div id="ttA" class="easyui-tabs" style="width:100%;height:150px;">
							<div title="文字类型"  style="padding:10px;"><!-- closable="true"设置当前tab可以被关闭 -->
								 <div class="ftitle">
						            <label for="priceA">内容:</label>
						            <textarea id="messageA" name="message" class="easyui-validatebox" validType="length[0,100]" 
						            	style="resize:none;width:400px;height:100px;border-radius:5px;margin-left: 30px;"></textarea>
						        </div>
							</div>
							<div title="图片类型" style="padding:10px;">
								  <div class="ftitle">
							            <label for="nameA">上传图片:</label>
							             <input type="hidden" name="id" id="idA"/>
							             <input type="hidden" name="type" id="typeA"/>
							             <a href="#" id="uploadA" class="l-btn l-btn-small" plain="true" 
							             	onclick="openDialog('ddA','add')" style="width:100px;">点击上传图片</a>
							       		 <img id="touxiangImgA" style="width:300px;height:300px;" alt="点击放大" src="" onclick="previewImage('touxiangImgA')">
							      </div>
							</div>
							<div title="图文类型"  style="padding:10px;"><!-- closable="true"设置当前tab可以被关闭 -->
								  <div class="ftitle">
							            <label for="nameA">上传图片:</label>
							            <input type="hidden" id="imgFileUuidA" name="imgFileUuid">
							             <a href="#" id="uploadA" class="l-btn l-btn-small" plain="true" 
							             	onclick="openDialog('ddA','add')" style="width:100px;">点击上传图片</a>
							       		 <img id="touxiangImgA" style="width:300px;height:300px;" alt="点击放大" src="" onclick="previewImage('touxiangImgA')">
							      </div>
								  <div class="ftitle">
						            <label for="priceA">内容:</label>
						            <textarea id="addWordA" name="addWord" class="easyui-validatebox" validType="length[0,100]" 
						            	style="resize:none;width:400px;height:100px;border-radius:5px;margin-left: 30px;"></textarea>
						         </div>
							</div>
						</div>
				        
				      </form>
	    	 	</div>
	    	 	<div region="center" style="height:33%;width:99%;" title="选择推送对象群(必选)">
	    	 		<table id="groupListA" class="easyui-datagrid" style="width:100%;height:auto;"   ></table>
	    	 	</div>
    		</div>
	     
    </div>
    
    <div id="updateGroupMsg" class="easyui-dialog" fit="true" title="修改群通知" style="width:800px;height:600px;padding:0px;border:0;top:1px;"
            data-options="
            modal:true,
                iconCls: 'icon-save',
                buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitUpdateGroupMsg();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                    	closeAddGroupMsgDialog();
                       
                    }
                }]
            ">
		
			<div class="easyui-layout" style="height:95%;padding:0;width:100%;" >
	    	 	<div region="north" style="height:60%;" title="群通知推送内容" hide="false" >
	    	 		<form id="ff" method="get"  style="margin-top:5px;" >
	    	 			
				        <!-- tabs -->
				        <div id="ttA" class="easyui-tabs" style="width:100%;height:150px;">
							<div title="图片类型" style="padding:10px;">
								  <div class="ftitle">
							            <label for="nameA">上传图片:</label>
							             <input type="hidden" name="id" id="idA"/>
							             <input type="hidden" name="type" id="typeA"/>
							             <a href="#" id="uploadA" class="l-btn l-btn-small" plain="true" 
							             	onclick="openDialog('ddA','add')" style="width:100px;">点击上传图片</a>
							       		 <img id="touxiangImgA" style="width:300px;height:300px;" alt="点击放大" src="" onclick="previewImage('touxiangImgA')">
							      </div>
							</div>
							<div title="文字类型"  style="padding:10px;"><!-- closable="true"设置当前tab可以被关闭 -->
								 <div class="ftitle">
						            <label for="priceA">内容:</label>
						            <textarea id="addWordA" name="addWord" class="easyui-validatebox" validType="length[0,100]" 
						            	style="resize:none;width:400px;height:100px;border-radius:5px;margin-left: 30px;"></textarea>
						        </div>
							</div>
							<div title="图文类型"  style="padding:10px;"><!-- closable="true"设置当前tab可以被关闭 -->
								  <div class="ftitle">
							            <label for="nameA">上传图片:</label>
							            <input type="hidden" id="imgFileUuidA" name="imgFileUuid">
							             <a href="#" id="uploadA" class="l-btn l-btn-small" plain="true" 
							             	onclick="openDialog('ddA','add')" style="width:100px;">点击上传图片</a>
							       		 <img id="touxiangImgA" style="width:300px;height:300px;" alt="点击放大" src="" onclick="previewImage('touxiangImgA')">
							      </div>
								  <div class="ftitle">
						            <label for="priceA">内容:</label>
						            <textarea id="addWordA" name="addWord" class="easyui-validatebox" validType="length[0,100]" 
						            	style="resize:none;width:400px;height:100px;border-radius:5px;margin-left: 30px;"></textarea>
						         </div>
							</div>
						</div>
				        
				      </form>
	    	 	</div>
	    	 	<div region="center" style="height:33%;width:99%;" title="选择推送对象群(必选)">
	    	 		<table id="groupListA" class="easyui-datagrid" style="width:100%;height:auto;"   ></table>
	    	 	</div>
    		</div>
	     
    </div>
    
     <!-- 上传图片弹框 -->
     <div id="ddA">Dialog Content.</div>
    <div id="uploadShowAimgPreview" title="图片预览" class="easyui-dialog" data-options="modal:true"  style="width:700px; height:500px;"> </div>
   
 
    
</body>
	
	
</html>