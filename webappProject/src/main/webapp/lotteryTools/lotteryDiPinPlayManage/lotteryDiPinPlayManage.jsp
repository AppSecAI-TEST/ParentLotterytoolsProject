<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>低频数据玩法方案管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link href="<%=request.getContextPath() %>/css/ztree/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <jsp:include page="../../common/top.jsp" flush="true" /> 
    <script src="<%=request.getContextPath() %>/lotteryTools/lotteryDiPinPlayManage/js/lotteryDiPinPlayManage.js" type="text/javascript"></script>
    
    <script type="text/javascript">
  	var toolbar = [{
  	    text:'添加低频玩法',
  	    iconCls:'icon-add',
  	    handler:function(){
  	    	
  	    	addLotteryPlayBuluPlan();
  	    	
  	    }
  	} ];
  	  
  	
		
	</script>
		<style type="text/css">
			.ztree li button.switch {visibility:hidden; width:1px;}
			.ztree li button.switch.roots_docu {visibility:visible; width:16px;}
			.ztree li button.switch.center_docu {visibility:visible; width:16px;}
			.ztree li button.switch.bottom_docu {visibility:visible; width:16px;}
			
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
	  		
	  		
	  		/*非validatebox的输入框样式*/
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
		    		<td width="7%" class="td_font">数字或其他：</td>
		    		<td width="15%">
		    			<select class="easyui-combobox" id="numOrCharC" name="numOrCharC" style="width:100px;">
								<option value="">全部</option>
								<option value="0">数字</option>
								<option value="1">其他</option>
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
    	 <table id="datagrid" class="easyui-datagrid"  title="补录方案数据列表" >
			</table>
 	</div>  
  
  
    <!-- 添加补录方案弹框 -->
  <div id="addBuluPlan" class="easyui-dialog"  title="添加低频方案" style="width:600px;height:600px;padding:0px;border:0;top:1px;"
            data-options="
            modal:true,
                iconCls: 'icon-save',
                buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitAddBuluPlan();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#addBuluPlan').dialog('close');
                        $('#ff').form('clear');//清空表单内容
                    }
                }]
            ">
		
			<div class="easyui-layout" style="height:100%;padding:0;width:100%;" >
				<div region="north" style="height:100%;" hide="false">
	    	 		<form id="ff" method="post" novalidate style="margin-top:5px;">
		    	 		<div class="ftitle" >
				            <label for="planNameA">方案名称:</label>
				            <input type="hidden" name="id" id="idA"/>
				            <input class="easyui-validatebox commonInput"  type="text" id="planNameA" name="planName" data-options="required:true"
				              validType="checkAname['#planNameA','idA']"  missingMessage="方案名称不可以为空" >
				        </div>
				        <div class="ftitle" >
				            <label for="planCodeA">方案编码:</label>
				            <input class="easyui-validatebox commonInput"  type="text" id="planCodeA" name="planCode" data-options="required:true"
				              validType="checkCode['#planCodeA','idA']"  missingMessage="方案编码不可以为空" >
				        </div>
				         <div class="ftitle">
				            <label for="lotteryTypeA">彩种:</label>
				            
				            	   <select class="easyui-combobox" id="lotteryTypeA" name="lotteryType" style="width:200px;">
									<option value="1">体彩</option>
									<option value="2">福彩</option>
									</select>
					        
				        </div>
				        
		    	 		<div class="ftitle">
				            <label for="numOrCharA">数字或其他方案:</label>
				            
				            	   <select class="easyui-combobox" id="numOrCharA" name="numOrChar" style="width:200px;">
									<option value="0">数字</option>
									<option value="1">其他</option>
									</select>
					        
				        </div>
				        <div class="ftitle">
				            <label for="morePartKjA">红蓝号开奖:</label>
				            
				            	   <select class="easyui-combobox" id="morePartKjA" name="morePartKj" style="width:200px;">
									<option value="1">否</option>
									<option value="2">是</option>
									</select>
					        
				        </div>
				        <div class="ftitle">
				            <label for="repeatNumA">开奖号码是否重复:</label>
				            
				            	   <select class="easyui-combobox" id="repeatNumA" name="repeatNum" style="width:200px;">
									<option value="0">不重复</option>
									<option value="1">重复</option>
									</select>
					        
				        </div>
				        <div class="ftitle" id="snDivA">
				            <label for="startNumberA">开始号码:</label>
				            <input class="easyui-numberbox numberInput" precision="0"  type="text" id="startNumberA" name="startNumber" style="width:200px"  
				               >
				        </div>
				        <div class="ftitle" id="enDivA">
				            <label for="endNumberA">结束号码:</label>
				            <input class="easyui-numberbox numberInput" precision="0" type="text" id="endNumberA" name="endNumber" style="width:200px"  
				              ></input>
				        </div>
				        <div class="ftitle" id="snDivA">
				            <label for="moreStartNumberA">蓝号开始号码:</label>
				            <input class="easyui-numberbox numberInput" precision="0"  type="text" id="moreStartNumberA" name="moreStartNumber" style="width:200px"  
				               >
				        </div>
				        <div class="ftitle" id="enDivA">
				            <label for="moreEndNumberA">蓝号结束号码:</label>
				            <input class="easyui-numberbox numberInput" precision="0" type="text" id="moreEndNumberA" name="moreEndNumber" style="width:200px"  
				              ></input>
				        </div>
				        <div class="ftitle" >
				            <label for="issueNumLenA">期号长度:</label>
				            <input class="easyui-numberbox numberInput" precision="0" type="text" id="issueNumLenA" name="issueNumLen" style="width:200px"  
				              ></input>
				        </div>
				         <div class="ftitle" >
				            <label for="lotteryNumberA">开奖号码个数:</label>
				            <input class="easyui-numberbox numberInput" precision="0" type="text" id="lotteryNumberA" name="lotteryNumber" style="width:200px"  
				              ></input>
				        </div>
				         <div class="ftitle" >
				            <label for="blueLotteryNumberA">蓝号开奖号码个数:</label>
				            <input class="easyui-numberbox numberInput" precision="0" type="text" id="blueLotteryNumberA" name="blueLotteryNumber" style="width:200px"  
				              ></input>
				        </div>
				        <div class="ftitle" >
				            <label for="correspondingTableA">存储表:</label>
				            <input class="easyui-validatebox commonInput"  type="text" id="correspondingTableA" name="correspondingTable" data-options="required:true"
				                missingMessage="存储表不可以为空" >
				        </div>
				         <div class="ftitle" id="opDivA">
				            <label for="otherPlanA">其他方案:</label>
				            <textarea id="otherPlanA" name="otherPlan" class="easyui-validatebox" 
					         	 validType="length[0,500]" style="resize:none;width:350px;height:100px;border-radius:5px;margin-left: 30px;"></textarea>
					        </div>
					       <div class="ftitle" >
				            <label for="otherNumA">其他需要计算的字段:</label>
				            <textarea id="otherNumA" name="otherNum" class="easyui-validatebox" placeholder="请输入调用方法名和字段名，以‘,’分隔"
					         	 validType="length[0,500]" style="resize:none;width:350px;height:100px;border-radius:5px;margin-left: 30px;"></textarea>
					        </div>
				       </form>
				       
				     </div>
    		</div>
    </div>
    
    
    
    
     <!-- 修改应用弹框 -->
     <div id="updateBuluPlan" class="easyui-dialog"   title="修改补录方案" style="width:600px;height:600px;padding:0px;border:0;top:1px;"
            data-options="
            modal:true,
                iconCls: 'icon-save',
                buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitUpdateBuluPlan();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#updateBuluPlan').dialog('close');
                    }
                }]
            ">
	      <div class="easyui-layout" style="height:100%;padding:0;width:100%;" >
	     	 <div region="north" style="height:100%;"  hide="false">
	    	 	<form id="ffUpdate" method="post" novalidate style="margin-top:5px;">
	    	 			<div class="ftitle" >
				            <label for="planNameU">方案名称:</label>
				            <input type="hidden" name="id" id="idU"/>
				            <input class="easyui-validatebox commonInput"  type="text" id="planNameU" name="planName" data-options="required:true"
				              validType="checkAname['#planNameU','idU']"  missingMessage="方案名称不可以为空" >
				        </div>
				         <div class="ftitle" >
				            <label for="planCodeU">方案编码:</label>
				            <input class="easyui-validatebox commonInput"  type="text" id="planCodeU" name="planCode" data-options="required:true"
				              validType="checkCode['#planCodeU','idU']"  missingMessage="方案编码不可以为空" >
				        </div>
				         <div class="ftitle">
				            <label for="lotteryTypeA">彩种:</label>
				            
				            	   <select class="easyui-combobox" id="lotteryTypeU" name="lotteryType" style="width:200px;">
									<option value="1">体彩</option>
									<option value="2">福彩</option>
									</select>
					        
				        </div>
				        
		    	 		<div class="ftitle">
				            <label for="numOrCharU">数字或其他方案:</label>
				            
					           <select class="easyui-combobox" id="numOrCharU" name="numOrChar" style="width:200px;">
									<option value="0">数字</option>
									<option value="1">其他</option>
								</select>
				        </div>
				         <div class="ftitle">
				            <label for="morePartKjA">红蓝号开奖:</label>
				            
				            	   <select class="easyui-combobox" id="morePartKjU" name="morePartKj" style="width:200px;">
									<option value="1">否</option>
									<option value="2">是</option>
									</select>
					        
				        </div>
				        <div class="ftitle">
				            <label for="repeatNumU">开奖号码是否重复:</label>
				            
				            	   <select class="easyui-combobox" id="repeatNumU" name="repeatNum" style="width:200px;">
									<option value="0">不重复</option>
									<option value="1">重复</option>
									</select>
					        
				        </div>
				        <div class="ftitle" id="snDivU">
				            <label for="startNumberU">开始号码:</label>
				            <input class="easyui-numberbox numberInput" precision="0"  type="text" id="startNumberU" name="startNumber" style="width:200px"  
				               ></input>
				        </div>
				        <div class="ftitle" id="enDivU"><!-- precision="0" 的含义是：小数点后保留0位数字 -->
				            <label for="endNumberU" >结束号码:</label>
				            <input class="easyui-numberbox numberInput" precision="0"  type="text" id="endNumberU" name="endNumber" style="width:200px"  
				              ></input>
				        </div>
				         <div class="ftitle" id="snDivA">
				            <label for="moreStartNumberA">蓝号开始号码:</label>
				            <input class="easyui-numberbox numberInput" precision="0"  type="text" id="moreStartNumberU" name="moreStartNumber" style="width:200px"  
				               >
				        </div>
				        <div class="ftitle" id="enDivA">
				            <label for="moreEndNumberA">蓝号结束号码:</label>
				            <input class="easyui-numberbox numberInput" precision="0" type="text" id="moreEndNumberU" name="moreEndNumber" style="width:200px"  
				              ></input>
				        </div>
				         <div class="ftitle" >
				            <label for="issueNumLenA">期号长度:</label>
				            <input class="easyui-numberbox numberInput" precision="0" type="text" id="issueNumLenU" name="issueNumLen" style="width:200px"  
				              ></input>
				        </div>
				         <div class="ftitle" >
				            <label for="lotteryNumberA">开奖号码个数:</label>
				            <input class="easyui-numberbox numberInput" precision="0" type="text" id="lotteryNumberU" name="lotteryNumber" style="width:200px"  
				              ></input>
				        </div>
				         <div class="ftitle" >
				            <label for="blueLotteryNumberA">蓝号开奖号码个数:</label>
				            <input class="easyui-numberbox numberInput" precision="0" type="text" id="blueLotteryNumberU" name="blueLotteryNumber" style="width:200px"  
				              ></input>
				        </div>
				        <div class="ftitle" >
				            <label for="correspondingTableA">存储表:</label>
				            <input class="easyui-validatebox commonInput"  type="text" id="correspondingTableU" name="correspondingTable" data-options="required:true"
				                missingMessage="存储表不可以为空" >
				        </div>
				         <div class="ftitle" id="opDivU">
				            <label for="otherPlanU">其他方案:</label>
				            <textarea id="otherPlanU" name="otherPlan" class="easyui-validatebox" 
					         	 validType="length[0,500]" style="resize:none;width:350px;height:100px;border-radius:5px;margin-left: 30px;"></textarea>
					     </div>
					     <div class="ftitle" >
				            <label for="otherNumU">其他需要计算的字段:</label>
				            <textarea id="otherNumU" name="otherNum" class="easyui-validatebox" placeholder="请输入调用方法名和字段名，以‘,’分隔"
					         	 validType="length[0,500]" style="resize:none;width:350px;height:100px;border-radius:5px;margin-left: 30px;"></textarea>
				        </div>
				 </form>
				</div>
    		</div>
    </div>
    
   
</body>
	
	
</html>