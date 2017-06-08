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
  	/* var toolbar = [{
  	    text:'添加群',
  	    iconCls:'icon-add',
  	    handler:function(){
  	    	
  	    	addLotteryGroup();
  	    	
  	    }
  	} ]; */
  	  
  	
		
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
		    		<td width="7%" class="td_font">审批状态：</td>
		    		<td width="15%">
		    			<select class="easyui-combobox" id="statusC" name="statusC" style="width:100px;">
								<option value="">全部</option>
								<option value="0">审批未通过</option>
								<option value="1">审批通过</option>
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
    	 <table id="datagrid" class="easyui-datagrid"  title="群公告数据列表" >
			</table>
 	</div>  
  
    <!-- 查看详情 -->
  <div id="detailLotteryGroupNotice" class="easyui-dialog"  title="查看群公告详情" style="width:700px;height:500px;padding:0px;border:0;top:1px;"
            data-options="
            modal:true,
                iconCls: 'icon-save',
                buttons: [{
                    text:'通过',
                    iconCls:'icon-ok',
                    handler:function(){
                        applyNotice('1','');
                    }
                },{
                    text:'不通过',
                    iconCls:'icon-cancel',
                    handler:function(){
                        fillInReason('0');
                        
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#detailLotteryGroupNotice').dialog('close');
                    }
                }]
            ">
		
			<div class="easyui-layout" style="height:100%;padding:0;width:100%;" >
	    	 		<form id="ffDetail" method="get" novalidate style="margin-top:5px;">
		    	 		<div class="ftitle">
				            <label for="codeA">发布对象群:</label>
				            <input type="hidden" name="id" id="idA"/>
				             <input class="easyui-validatebox commonInput" type="text" id="groupNameA" name="groupName" style="width:200px"  
				             readonly="readonly"  />
				        </div>
				        <div class="ftitle">
				            <label for="priceA">群公告内容:</label>
				            <div class="textareaDiv">
				             <textarea id="noticeA" name="notice" class="easyui-validatebox"
								readonly="readonly"" 
								style="resize: none; width: 200px; height: 110px;top:0px;"></textarea>
							</div>
				        </div>
				        
				       </form>
    		</div>
			
    		
	     
    </div>
    
    <div id="notAllow" class="easyui-dialog"  title="选择不通过原因" style="width:400px;height:300px;padding:0px;border:0;top:10px;"
            data-options="
            modal:true,
                iconCls: 'icon-save',
                buttons: [{
                    text:'确定',
                    iconCls:'icon-save',
                    handler:function(){
                       submitNotAllow();
                    }
                },{
                    text:'关闭',
                    iconCls:'icon-cancel',
                    handler:function(){
                       closeNotAllow();
                    }
                }]
            ">
		
			<div class="easyui-layout" style="height:100%;padding:0;width:100%;" >
	    	 		<form id="noAllowForm" method="get" novalidate style="margin-top:5px;">
		    	 		<input type="hidden" name="id" id="idn"/>
		    	 		<input type="hidden" name="status" id="statusn"/>
		    	 		  <div class="ftitle">
		    	 		   <label for="priceA">不通过原因:</label>
				      <select class="easyui-combobox" id="notPassReasonA" name="notPassReason" style="width:200px;">
									<option value="群公告内容不符合">群公告内容不符合</option>
									<option value="不可以发布群公告">不可以发布群公告</option>
							</select>
							</div>
				      </form>
	        </div>
	        </div>
    
  <div id="viewLotteryGroupNotice" class="easyui-dialog"  title="查看群公告详情" style="width:700px;height:500px;padding:0px;border:0;top:1px;"
            data-options="
            modal:true,
                iconCls: 'icon-save',
                buttons: [{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#viewLotteryGroupNotice').dialog('close');
                    }
                }]
            ">
		
			<div class="easyui-layout" style="height:100%;padding:0;width:100%;" >
	    	 		<form id="ffviewDetail" method="get" novalidate style="margin-top:5px;">
		    	 		<div class="ftitle">
				            <label for="codeA">发布对象群:</label>
				            <input type="hidden" name="id" id="idV"/>
				             <input class="easyui-validatebox commonInput" type="text" id="groupNameV" name="groupName" style="width:200px"  
				             readonly="readonly"  />
				        </div>
				        <div class="ftitle">
				            <label for="priceA">群公告内容:</label>
				            <div class="textareaDiv">
					             <textarea id="noticeV" name="notice" class="easyui-validatebox"
								readonly="readonly"
								style="resize: none; width: 200px; height: 110px;top:0px;"></textarea>
							</div>
				        </div>
				        
				       </form>
    		</div>
			
    		
	     
    </div>
    
</body>
	
	
</html>