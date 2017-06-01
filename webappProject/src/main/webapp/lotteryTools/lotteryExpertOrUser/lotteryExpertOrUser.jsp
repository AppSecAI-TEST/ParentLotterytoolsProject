<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>彩聊用户管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link href="<%=request.getContextPath() %>/css/ztree/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <jsp:include page="../../common/top.jsp" flush="true" /> 
    <script src="<%=request.getContextPath() %>/lotteryTools/lotteryExpertOrUser/js/lotteryExpertOrUser.js" type="text/javascript"></script>
    
    <script type="text/javascript">
  	var toolbar = [{
  	    text:'添加彩聊用户',
  	    iconCls:'icon-add',
  	    handler:function(){
  	    	
  	    	addLotteryUser();
  	    	
  	    }
  	} ];
  	  
  	
		
	</script>
		<style type="text/css">
			.ztree li button.switch {visibility:hidden; width:1px;}
			.ztree li button.switch.roots_docu {visibility:visible; width:16px;}
			.ztree li button.switch.center_docu {visibility:visible; width:16px;}
			.ztree li button.switch.bottom_docu {visibility:visible; width:16px;}
			
			.cardClass{
				width:100%;
	  			float : left;
	  			margin-top: 10px;
	  			margin-bottom: 20px;
	  			font-family:'微软雅黑',
			}
			
			.cardClass label{
	  			float : left;
	  			margin-left: 30px;
	  		}
			
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
		    		<td width="7%" class="td_font">昵称：</td>
		    		<td width="15%">
		    			<input id="nameC" class="input_border"  type="text" name="nameC"  />  
		    		</td>
		    		<td width="7%" class="td_font">彩种分类：</td>
		    		<td width="15%">
		    			<select class="easyui-combobox" id="lotteryTypeC" name="lotteryTypeC" style="width:100px;">
								<option value="">全部</option>
								<option value="1">体彩</option>
								<option value="2">福彩</option>
						</select>
		    		</td>
		    		<td width="7%" class="td_font">机器人：</td>
		    		<td width="15%">
		    			<select class="easyui-combobox" id="isRobotC" name="isRobotC" style="width:100px;">
								<option value="0" selected>非机器人</option>
								<option value="1">机器人</option>
						</select>
		    		</td>
		    		<td width="7%" class="td_font">省：</td>
		    		<td width="15%">
		    			<select class="easyui-combobox" id="privinceC" name="privinceC" style="width:100px;">
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
    	 <table id="datagrid" class="easyui-datagrid"  title="彩聊用户数据列表" >
			</table>
 	</div>  
  
  
    <!-- 添加彩聊用户弹框 -->
  <div id="addLotteryUserDiv" class="easyui-dialog" fit="true" title="添加补录信息数据" style="width:800px;height:600px;padding:0px;border:0;top:1px;"
            data-options="
            modal:true,
                iconCls: 'icon-save',
                buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitAddLotteryUser();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        closeAddUserDialog();
                    }
                }]
            ">
		
			<div class="easyui-layout" style="height:100%;padding:0;width:100%;" >
	    	 	<form id="ff" method="get" novalidate style="margin-top:5px;">
		    	 		<div class="ftitle">
				            <label for="codeA">昵称:</label>
				            <input type="hidden" name="id" id="idA"/>
				           <input class="easyui-validatebox commonInput" type="text" id="nameA" name="name" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				         <div class="ftitle">
				            <label for="lotteryTypeA" >密码:</label>
					    	<input class="easyui-validatebox commonInput" type="password" id="passwordA" name="password" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
						<div class="ftitle">
				            <label for="subject">手机号:</label>
				           <input class="easyui-validatebox commonInput" type="text" id="telephoneA" name="telephone" style="width:200px"  
				             data-options="validType:['mobile']"  ></input>
				        </div>
				        
					   <div class="ftitle">
				            <label for="priceA">是否为手机用户:</label>
				            <select class="easyui-combobox" id="isPhoneA" name="isPhone" style="width:200px;">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
				        </div>
				        
				       <div class="ftitle">
				            <label for="subject">用户地域:</label>
				            <div style="">
					            <select class="easyui-combobox " id="provinceA" name="provinceCode"  
					          	  data-options="editable:false" style="width:150px;" >
								</select>
								<select class="easyui-combobox " id="cityA" name="cityCode"  
					          	  data-options="editable:false" style="width:150px;" >
								</select>
								<select class="easyui-combobox " id="regionCodeA" name="regionCode"  
					          	  data-options="editable:false" style="width:150px;" >
								</select>
				            </div>
				        </div>
				        
				         <div class="ftitle">
				            <label for="priceA">虚拟用户:</label>
				            <select class="easyui-combobox" id="isVirtualA" name="isVirtual" style="width:200px;">
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
				            <label for="priceA">彩聊名:</label>
				           <input class="easyui-validatebox commonInput" type="text" id="cailiaoNameA" name="cailiaoName" style="width:200px"  
				             data-options="require:true"  ></input>
				        </div>
				         <div class="ftitle" >
				            <label for="introductionA">个性签名:</label>
				            <textarea id="signaturenA" name="signature" class="easyui-validatebox" placeholder="请输入个人签名"
					         	 validType="length[0,100]" style="resize:none;width:350px;height:100px;border-radius:5px;margin-left: 30px;"></textarea>
					    </div>
					    
					     <div class="ftitle">
				            <label for="priceA">是否为专家:</label>
				            <select class="easyui-combobox" id="isExpertA" name="isExpert" style="width:200px;">
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
				      
				        
				     </form>
    		</div>
			
    		
	     
    </div>
     <!-- 修改彩聊用户弹框 -->
     <div id="updateLotteryUserDiv" class="easyui-dialog"  fit="true" title="修改补录信息数据" style="width:800px;height:600px;padding:0px;border:0;top:1px;"
            data-options="
            modal:true,
                iconCls: 'icon-save',
                buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitUpdateLotteryUser();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#updateLotteryPlay').dialog('close');
                    }
                }]
            ">
	      <div class="easyui-layout" style="height:95%;padding:0;width:95%;" >
	    	 <form id="ffUpdate" method="get" novalidate style="margin-top:5px;">
		    	 		<div class="ftitle">
				            <label for="codeU">昵称:</label>
				            <input type="hidden" name="id" id="idU"/>
				           <input class="easyui-validatebox commonInput" type="text" id="nameU" name="name" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				         <div class="ftitle">
				            <label for="lotteryTypeA" >密码:</label>
					    	<input class="easyui-validatebox commonInput" type="password" id="passwordU" name="password" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
						<div class="ftitle">
				            <label for="subject">手机号:</label>
				           <input class="easyui-validatebox commonInput" type="text" id="telephoneU" name="telephone" style="width:200px"  
				             readonly="readonly"   ></input>
				        </div>
				        
					   <div class="ftitle">
				            <label for="priceA">是否为手机用户:</label>
				            <select class="easyui-combobox" id="isPhoneU" name="isPhone" style="width:200px;">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
				        </div>
				        
				       <div class="ftitle">
				            <label for="subject">用户地域:</label>
				            <div style="">
					            <select class="easyui-combobox " id="provinceU" name="provinceCode"  
					          	  data-options="editable:false" style="width:150px;" >
								</select>
								<select class="easyui-combobox " id="cityU" name="cityCode"  
					          	  data-options="editable:false" style="width:150px;" >
								</select>
								<select class="easyui-combobox " id="regionCodeU" name="regionCode"  
					          	  data-options="editable:false" style="width:150px;" >
								</select>
				            </div>
				        </div>
				        
				         <div class="ftitle">
				            <label for="priceA">虚拟用户:</label>
				            <select class="easyui-combobox" id="isVirtualU" name="isVirtual" style="width:100px;">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
				        </div>
				        
				         <div class="ftitle">
				            <label for="priceA">彩聊名:</label>
				           <input class="easyui-validatebox commonInput" type="text" id="cailiaoNameU" name="cailiaoName" style="width:200px"  
				             data-options="require:true"  ></input>
				        </div>
				         <div class="ftitle" >
				            <label for="introductionA">个性签名:</label>
				            <textarea id="signaturenU" name="signature" class="easyui-validatebox" placeholder="请输入个人签名"
					         	 validType="length[0,100]" style="resize:none;width:350px;height:100px;border-radius:5px;margin-left: 30px;"></textarea>
					    </div>
					    
					     <div class="ftitle">
				            <label for="priceA">是否为专家:</label>
				            <select class="easyui-combobox" id="isExpertU" name="isExpert" style="width:200px;">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
				        </div>
					    
				        <div class="ftitle">
				            <label for="subject">头像:</label>
				            <input type="hidden" id="touXiangU" name="touXiang">
				             <a href="#" id="uploadU" class="l-btn l-btn-small" plain="true" onclick="openDialog('ddA','add')" style="width:100px;">点击上传头像</a>
				       		 <img id="touxiangImgU" style="width:300px;height:300px;" alt="点击放大" src="" onclick="previewImage('touxiangImgU')">
				        </div>
				      
				        
				     </form>
    		</div>
    </div>
     <!-- 上传图片弹框 -->
     <div id="ddA">Dialog Content.</div>
    <div id="uploadShowAimgPreview" title="图片预览" class="easyui-dialog" data-options="modal:true"  style="width:700px; height:500px;"> </div>
   
    <!-- 设置用户的卡片 -->
       <div id="cardManageDiv" class="easyui-dialog" title="卡包管理" style="width:500px;height:600px;padding:0px;border:0;top:1px;"
            data-options="
            modal:true,
                iconCls: 'icon-save',
                buttons: [{
                    text:'关闭',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#cardManageDiv').dialog('close');
                    }
                }]
            ">
            <div class="cardClass">
	            <label for="subject">福彩建群卡:</label>
	             <input id="ss" class="easyui-numberspinner" style="width:80px;" value="0"
    						required="required" data-options="min:0,max:10,editable:false"><a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">确定</a>
	        </div>
    </div>
    
</body>
	
	
</html>