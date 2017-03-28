<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>基本预测类型管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link href="<%=request.getContextPath() %>/css/ztree/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <jsp:include page="../../common/top.jsp" flush="true" /> 
    <script src="<%=request.getContextPath() %>/lotteryTools/basePredictionType/js/basePredictionType.js" type="text/javascript"></script>
    
    <script type="text/javascript">
  	var toolbar = [{
  	    text:'添加基本预测类型',
  	    iconCls:'icon-add',
  	    handler:function(){
  	    	
  	    	addBasePredictionType();
  	    	
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
	  			width:150px;
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
		    		<td width="7%" class="td_font">基础预测类型名称：</td>
		    		<td width="15%">
		    			<input id="basePredictionNameC" class="input_border"  type="text" name="basePredictionNameC"  />  
		    		</td>
		    		
		    		<td class="td_font" width="12%">
		    			<input style="cursor:pointer;background-color: #e0ecff;border-radius:5px;float:left" type="button" value="查询" onclick="initDatagrid()">
		    			<input style="cursor:pointer;background-color: #e0ecff;border-radius:5px;float:left;margin-left:5px;" type="button" value="重置" onclick="reset()">
		    		</td>
		    	</tr>
	    	</table>	
	</div>

    <div  data-options="region:'center'" data-options="border:false" >
    	 <table id="datagrid" class="easyui-datagrid"  title="基本预测类型列表" >
			</table>
 	</div>  
  
  
    <!-- 添加基本预测类型弹框 -->
  <div id="addBasePredictionType" class="easyui-dialog"  title="添加基本预测类型" style="width:800px;height:600px;padding:0px;border:0;top:1px;"
            data-options="
            modal:true,
                iconCls: 'icon-save',
                buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitAddLotteryPlay();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#addBasePredictionType').dialog('close');
                        $('#ff').form('clear');//清空表单内容
                    }
                }]
            ">
		
			<div class="easyui-layout" style="height:100%;padding:0;width:100%;" >
	    	 	<div region="north" style="height:45%;" title="基本预测类型内容" hide="false">
	    	 		<form id="ff" method="get" novalidate style="margin-top:5px;">
		    	 		<div class="ftitle">
				            <label for="codeA">源码数据量要求:</label>
				            <input type="hidden" name="id" id="idA"/>
				            <input class="easyui-validatebox commonInput" type="text" id="originDataSizeA" name="originDataSize" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				        <div class="ftitle">
				            <label for="priceA">N期计划:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="nPlanA" name="nPlan" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				         <div class="ftitle">
				            <label for="priceA">基本预测类型名称:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="basePredictionNameA" name="basePredictionName" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				        <div class="ftitle">
				            <label for="priceA">对应预测方法名称:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="methodNameA" name="methodName" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				       </form>
	    	 	</div>
	    	 	<div region="center" style="height:55%;padding:0;width:99%;" title="选择源码规则(必选)">
	    	 		<table id="orderRuleA" class="easyui-datagrid" style="width:100%;height:95%;"   ></table>
	    	 	</div>
    		</div>
			
    		
	     
    </div>
     <!-- 修改基本预测类型名称 -->
     <div id="updateBasePredictionType" class="easyui-dialog"  title="修改基本预测类型" style="width:800px;height:600px;padding:0px;border:0;top:1px;"
            data-options="
            modal:true,
                iconCls: 'icon-save',
                buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitUpdateLotteryPlay();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#updateBasePredictionType').dialog('close');
                        $('#ffUpdate').form('clear');//清空表单内容
                    }
                }]
            ">
		
			<div class="easyui-layout" style="height:100%;padding:0;width:100%;" >
	    	 	<div region="north" style="height:45%;" title="基本预测类型内容" hide="false">
	    	 		<form id="ffUpdate" method="get" novalidate style="margin-top:5px;">
		    	 		<div class="ftitle">
				            <label for="originDataSizeU">源码数据量要求:</label>
				            <input type="hidden" name="id" id="idU"/>
				            <input class="easyui-validatebox commonInput" type="text" id="originDataSizeU" name="originDataSize" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				        <div class="ftitle">
				            <label for="nPlanU">N期计划:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="nPlanU" name="nPlan" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				         <div class="ftitle">
				            <label for="basePredictionNameU">基本预测类型名称:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="basePredictionNameU" name="basePredictionName" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				        <div class="ftitle">
				            <label for="methodNameU">对应预测方法名称:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="methodNameU" name="methodName" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				       </form>
	    	 	</div>
	    	 	<div region="center" style="height:55%;padding:0;width:99%;" title="选择源码规则(必选)">
	    	 		<table id="orderRuleU" class="easyui-datagrid" style="width:100%;height:95%;"   ></table>
	    	 	</div>
    		</div>
			
    		
	     
    </div>
   
</body>
	
	
</html>