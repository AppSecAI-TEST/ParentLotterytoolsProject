<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>源码规则管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <jsp:include page="../../common/top.jsp" flush="true" /> 
    <script src="<%=request.getContextPath() %>/lotteryTools/orderRule/js/orderRuleManage.js" type="text/javascript"></script>
    
    <script type="text/javascript">
  	var toolbar = [{
  	    text:'添加源码规则',
  	    iconCls:'icon-add',
  	    handler:function(){
  	    	
  	    	addOrderRule();
  	    	
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
		    		<td width="7%" class="td_font">源码规则名称：</td>
		    		<td width="15%">
		    			<input id="ruleNameC" class="input_border"  type="text" name="ruleName"  />  
		    		</td>
		    		
		    		<td class="td_font" width="12%">
		    			<input style="cursor:pointer;background-color: #e0ecff;border-radius:5px;float:left" type="button" value="查询" onclick="initDatagrid()">
		    			<input style="cursor:pointer;background-color: #e0ecff;border-radius:5px;float:left;margin-left:5px;" type="button" value="重置" onclick="reset()">
		    		</td>
		    	</tr>
	    	</table>	
	</div>

    <div  data-options="region:'center'" data-options="border:false" >
    	 <table id="datagrid" class="easyui-datagrid"  title="源码规则数据列表" >
			</table>
 	</div>  
  
  
    <!-- 添加源码规则弹框 -->
  <div id="addOrderRule" class="easyui-dialog"  title="添加源码规则" style="width:600px;height:800px;padding:0px;border:0;top:1px;"
            data-options="
            modal:true,
                iconCls: 'icon-save',
                buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitAddOrderRule();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#addOrderRule').dialog('close');
                        $('#ff').form('clear');//清空表单内容
                    }
                }]
            ">
		
			<div class="easyui-layout" style="height:100%;padding:0;width:100%;" >
	    	 		<form id="ff" method="get" novalidate style="margin-top:5px;">
		    	 		<div class="ftitle">
				            <label for="codeA">规则名称:</label>
				            <input type="hidden" name="id" id="idA"/>
				            <input class="easyui-validatebox commonInput" type="text" id="ruleNameA" name="ruleName" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				         <div class="ftitle">
				            <label for="priceA">源码筛选方式:</label>
				            <select class="easyui-combobox" id="typeA" name="type" style="width:200px;">
								<option value="0" selected>当期开奖号码</option>
								<option value="1">关联期号方式</option>
								<option value="2">期号方式</option>
							</select>
				        </div>
				        <div class="ftitle">
				            <label for="locationOrContainA">定位查找或包含查找:</label>
				            <select class="easyui-combobox" id="locationOrContainA" name="locationOrContain" style="width:200px;">
								<option value="0" selected>定位</option>
								<option value="1">包含</option>
							</select>
				        </div>
				        <div class="ftitle" >
				            <label for="ciLocationNumberA">当期定位位置:</label>
				            <textarea id="ciLocationNumberA" name="ciLocationNumber" class="easyui-validatebox" placeholder="请输入当期需要定位的位置，以‘,’分隔"
					         	 validType="length[0,500]" style="resize:none;width:350px;height:100px;border-radius:5px;margin-left: 30px;"></textarea>
					     </div>
				         <div class="ftitle" >
				            <label for="ciRuleFiledA">当期定位字段:</label>
				            <textarea id="ciRuleFiledA" name="ciRuleFiled" class="easyui-validatebox" placeholder="请输入当期需要定位的字段，以‘,’分隔"
					         	 validType="length[0,500]" style="resize:none;width:350px;height:100px;border-radius:5px;margin-left: 30px;"></textarea>
					     </div>
				       <div class="ftitle" >
				            <label for="liLocationNumberA">上期定位位置:</label>
				            <textarea id="liLocationNumberA" name="liLocationNumber" class="easyui-validatebox" placeholder="请输入上期需要定位的位置，以‘,’分隔"
					         	 validType="length[0,500]" style="resize:none;width:350px;height:100px;border-radius:5px;margin-left: 30px;"></textarea>
					     </div>
				         <div class="ftitle" >
				            <label for="liRuleFiledA">上期定位字段:</label>
				            <textarea id="liRuleFiledA" name="liRuleFiled" class="easyui-validatebox" placeholder="请输入上期需要定位的字段，以‘,’分隔"
					         	 validType="length[0,500]" style="resize:none;width:350px;height:100px;border-radius:5px;margin-left: 30px;"></textarea>
					     </div>
					     <div class="ftitle">
				            <label for="cycleA">周期:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="cycleA" name="cycle" style="width:200px"  
				             data-options="required:true"  ></input>
				       </form>
	    	 	</div>
    		</div>
			
    		
	     
    <div id="updateOrderRule" class="easyui-dialog"  title="修改源码规则" style="width:600px;height:800px;padding:0px;border:0;top:1px;"
            data-options="
            modal:true,
                iconCls: 'icon-save',
                buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitUpdateOrderRule();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#updateOrderRule').dialog('close');
                        $('#ffUpdate').form('clear');//清空表单内容
                    }
                }]
            ">
		
			<div class="easyui-layout" style="height:100%;padding:0;width:100%;" >
	    	 		<form id="ffUpdate" method="get" novalidate style="margin-top:5px;">
		    	 		<div class="ftitle">
				            <label for="codeA">规则名称:</label>
				            <input type="hidden" name="id" id="idU"/>
				            <input class="easyui-validatebox commonInput" type="text" id="ruleNameU" name="ruleName" style="width:200px"  
				             data-options="required:true"   ></input>
				        </div>
				         <div class="ftitle">
				            <label for="priceA">源码筛选方式:</label>
				            <select class="easyui-combobox" id="typeU" name="type" style="width:200px;">
								<option value="0">当期开奖号码</option>
								<option value="1">关联期号方式</option>
								<option value="2">期号方式</option>
							</select>
				        </div>
				        <div class="ftitle">
				            <label for="locationOrContainA">定位查找或包含查找:</label>
				            <select class="easyui-combobox" id="locationOrContainU" name="locationOrContain" style="width:200px;">
								<option value="0">定位</option>
								<option value="1">包含</option>
							</select>
				        </div>
				        <div class="ftitle" >
				            <label for="ciLocationNumberA">当期定位位置:</label>
				            <textarea id="ciLocationNumberU" name="ciLocationNumber" class="easyui-validatebox" placeholder="请输入当期需要定位的位置，以‘,’分隔"
					         	 validType="length[0,500]" style="resize:none;width:350px;height:100px;border-radius:5px;margin-left: 30px;"></textarea>
					     </div>
				         <div class="ftitle" >
				            <label for="ciRuleFiledA">当期定位字段:</label>
				            <textarea id="ciRuleFiledU" name="ciRuleFiled" class="easyui-validatebox" placeholder="请输入当期需要定位的字段，以‘,’分隔"
					         	 validType="length[0,500]" style="resize:none;width:350px;height:100px;border-radius:5px;margin-left: 30px;"></textarea>
					     </div>
				       <div class="ftitle" >
				            <label for="liLocationNumberA">上期定位位置:</label>
				            <textarea id="liLocationNumberU" name="liLocationNumber" class="easyui-validatebox" placeholder="请输入上期需要定位的位置，以‘,’分隔"
					         	 validType="length[0,500]" style="resize:none;width:350px;height:100px;border-radius:5px;margin-left: 30px;"></textarea>
					     </div>
				         <div class="ftitle" >
				            <label for="liRuleFiledA">上期定位字段:</label>
				            <textarea id="liRuleFiledU" name="liRuleFiled" class="easyui-validatebox" placeholder="请输入上期需要定位的字段，以‘,’分隔"
					         	 validType="length[0,500]" style="resize:none;width:350px;height:100px;border-radius:5px;margin-left: 30px;"></textarea>
					     </div>
					     <div class="ftitle">
				            <label for="cycleA">周期:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="cycleU" name="cycle" style="width:200px"  
				             data-options="required:true"  ></input>
				       </form>
	    	 	</div>
    		</div>
			
    		
    
   
</body>
	
	
</html>