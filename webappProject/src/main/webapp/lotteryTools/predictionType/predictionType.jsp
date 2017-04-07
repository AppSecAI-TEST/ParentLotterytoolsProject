<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>区域预测类型管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link href="<%=request.getContextPath() %>/css/ztree/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <jsp:include page="../../common/top.jsp" flush="true" /> 
    <script src="<%=request.getContextPath() %>/lotteryTools/predictionType/js/predictionType.js" type="text/javascript"></script>
    
    <script type="text/javascript">
  	var toolbar = [{
  	    text:'添加预测类型',
  	    iconCls:'icon-add',
  	    handler:function(){
  	    	
  	    	addPredictionType();
  	    	
  	    }
  	} ];
  	  
  	
		
	</script>
		<style type="text/css">
			.ztree li button.switch {visibility:hidden; width:1px;}
			.ztree li button.switch.roots_docu {visibility:visible; width:16px;}
			.ztree li button.switch.center_docu {visibility:visible; width:16px;}
			.ztree li button.switch.bottom_docu {visibility:visible; width:16px;}
			
			 .ftitle{
	  			width:50%;
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
		    		<td width="7%" class="td_font">预测类型名称：</td>
		    		<td width="15%">
		    			<input id="predictionNameC" class="input_border"  type="text" name="predictionNameC"  />  
		    		</td>
		    		
		    		<td class="td_font" width="12%">
		    			<input style="cursor:pointer;background-color: #e0ecff;border-radius:5px;float:left" type="button" value="查询" onclick="initDatagrid()">
		    			<input style="cursor:pointer;background-color: #e0ecff;border-radius:5px;float:left;margin-left:5px;" type="button" value="重置" onclick="reset()">
		    		</td>
		    	</tr>
	    	</table>	
	</div>

    <div  data-options="region:'center'" data-options="border:false" >
    	 <table id="datagrid" class="easyui-datagrid"  title="预测类型列表" >
			</table>
 	</div>  
  
  
    <!-- 添加预测类型弹框 -->
  <div id="addPredictionType" class="easyui-dialog" fit="true"  title="添加预测类型" style="width:800px;height:600px;padding:0px;border:0;top:1px;"
            data-options="
            modal:true,
                iconCls: 'icon-save',
                buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitAddPredictionType();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#addPredictionType').dialog('close');
                        $('#ff').form('clear');//清空表单内容
                    }
                }]
            ">
		
			<div class="easyui-layout" style="height:100%;padding:0;" >
	    	 	<div region="north" style="height:28%;" title="预测类型内容" hide="false">
	    	 		<form id="ff" method="get" novalidate style="margin-top:5px;">
		    	 		<div class="ftitle">
				            <label for="codeA">预测类型名称:</label>
				            <input type="hidden" name="id" id="idA"/>
				            <input class="easyui-validatebox commonInput" type="text" id="predictionNameA" name="predictionName" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				        <div class="ftitle">
				            <label for="priceA">预测类型编码:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="predictionCodeA" name="predictionCode" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				         <div class="ftitle">
				            <label for="priceA">预测方案表:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="predictionTableA" name="predictionTable" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				        <div class="ftitle">
				            <label for="priceA">准确率计算数据基数:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="orderRuleA" name="orderRule" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				        <div class="ftitle">
				            <label for="priceA">两码基础表:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="liangmaTableNameA" name="liangmaTableName" style="width:200px"  
				              ></input>
				        </div>
				         <div class="ftitle">
				            <label for="priceA">三码基础表:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="sanmaTableNameA" name="sanmaTableName" style="width:200px"  
				               ></input>
				        </div>
				         <div class="ftitle">
				            <label for="priceA">四码基础表:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="simaTableNameA" name="simaTableName" style="width:200px"  
				              ></input>
				        </div>
				         <div class="ftitle">
				            <label for="priceA">六码基础表:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="liumaTableNameA" name="liumaTableName" style="width:200px"  
				               ></input>
				        </div>
				       </form>
	    	 	</div>
	    	 	<div region="center" style="height:58%;padding:0;" >
		    	 	<div  style="height:50%;width:100%;" >
		    	 		<table id="basePTypeA" class="easyui-datagrid" style="width:100%;height:100%;"   title="选择基础预测类型(必选)"></table>
		    	 	</div>
		    	 	<div style="height:50%;width:100%;">
		    	 		<table id="lotteryPlayA" class="easyui-datagrid" style="width:100%;height:100%;"   title="选择区域彩种(必选)" ></table>
		    	 	</div>
	    	 	</div>
    		</div>
			
    		
	     
    </div>
     <!-- 修改预测类型名称 -->
    <div id="updatePredictionType" class="easyui-dialog" fit="true"  title="修改预测类型" style="width:800px;height:600px;padding:0px;border:0;top:1px;"
            data-options="
            modal:true,
                iconCls: 'icon-save',
                buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitUpdatePredictionType();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#updatePredictionType').dialog('close');
                        $('#ffUpdate').form('clear');//清空表单内容
                    }
                }]
            ">
		
			<div class="easyui-layout" style="height:100%;padding:0;" >
	    	 	<div region="north" style="height:28%;" title="预测类型内容" hide="false">
	    	 		<form id="ffUpdate" method="get" novalidate style="margin-top:5px;">
		    	 		<div class="ftitle">
				            <label for="codeA">预测类型名称:</label>
				            <input type="hidden" name="id" id="idU"/>
				            <input class="easyui-validatebox commonInput" type="text" id="predictionNameU" name="predictionName" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				        <div class="ftitle">
				            <label for="priceA">预测类型编码:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="predictionCodeU" name="predictionCode" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				         <div class="ftitle">
				            <label for="priceA">预测方案表:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="predictionTableU" name="predictionTable" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				        <div class="ftitle">
				            <label for="priceA">准确率计算数据基数:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="orderRuleU" name="orderRule" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				        <div class="ftitle">
				            <label for="priceA">两码基础表:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="liangmaTableNameU" name="liangmaTableName" style="width:200px"  
				             ></input>
				        </div>
				         <div class="ftitle">
				            <label for="priceA">三码基础表:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="sanmaTableNameU" name="sanmaTableName" style="width:200px"  
				              ></input>
				        </div>
				         <div class="ftitle">
				            <label for="priceA">四码基础表:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="simaTableNameU" name="simaTableName" style="width:200px"  
				              ></input>
				        </div>
				         <div class="ftitle">
				            <label for="priceA">六码基础表:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="liumaTableNameU" name="liumaTableName" style="width:200px"  
				              ></input>
				        </div>
				       </form>
	    	 	</div>
	    	 	<div region="center" style="height:58%;padding:0;" >
		    	 	<div  style="height:48%;width:100%;" >
		    	 		<table id="basePTypeU" class="easyui-datagrid"  title="选择基础预测类型(必选)" style="width:100%;height:100%;"   ></table>
		    	 	</div>
		    	 	<div style="height:48%;width:100%;">
		    	 		<table id="lotteryPlayU" class="easyui-datagrid"  title="选择区域彩种(必选)" style="width:100%;height:100%;"   ></table>
		    	 	</div>
	    	 	</div>
    		</div>
			
    		
	     
    </div>
   
</body>
	
	
</html>