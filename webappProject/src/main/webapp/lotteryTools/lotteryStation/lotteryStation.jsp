<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>彩票站管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <jsp:include page="../../common/top.jsp" flush="true" /> 
    <script src="<%=request.getContextPath() %>/lotteryTools/lotteryStation/js/lotteryStation.js" type="text/javascript"></script>
    
    <script type="text/javascript">
  	/* var toolbar = [{
  	    text:'添加补录信息',
  	    iconCls:'icon-add',
  	    handler:function(){
  	    	
  	    	addLotteryPlay();
  	    	
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
		    		<td width="7%" class="td_font">彩种分类：</td>
		    		<td width="15%">
		    			<select class="easyui-combobox" id="lotteryTypeC" name="lotteryTypeC" style="width:100px;">
								<option value="">全部</option>
								<option value="1">体彩</option>
								<option value="2">福彩</option>
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
    	 <table id="datagrid" class="easyui-datagrid"  title="彩票站数据列表" >
			</table>
 	</div>  
  
  
  
     <!-- 查看彩票站详情 -->
  <div id="detailLotteryStation" class="easyui-dialog" fit="true" title="查看彩票站详情" style="width:800px;height:600px;padding:0px;border:0;top:1px;"
            data-options="
            modal:true,
                iconCls: 'icon-save',
                buttons: [{
                    text:'关闭',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#detailLotteryStation').dialog('close');
                    }
                }]
            ">
		
			<div class="easyui-layout" style="height:100%;padding:0;width:100%;" >
	    	 		<form id="ffDetail" method="get" novalidate style="margin-top:5px;">
		    	 		<div class="ftitle">
				            <label for="codeA">彩票站名称:</label>
				            <input type="hidden" name="id" id="idA"/>
				            <input class="easyui-validatebox commonInput" type="text" id="stationNameA" name="stationName" style="width:200px"  
				             data-options="required:true"   />
				        </div>
				        <div class="ftitle">
				            <label for="priceA">省:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="provinceNameA" name="provinceName" style="width:200px"  
				             readonly="readonly"  />
				        </div>
				         <div class="ftitle">
				            <label for="priceA">市:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="cityNameA" name="cityName" style="width:200px"  
				             readonly="readonly" />
				        </div>
				         <div class="ftitle">
				            <label for="priceA">彩种:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="lotteryTypeA" name="lotteryType" style="width:200px"  
				             readonly="readonly"   />
				        </div>
				        <div class="ftitle">
				            <label for="priceA">佰艺霖用户:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="isBylStationA" name="isBylStation" style="width:200px"  
				             readonly="readonly"   />
				        </div>
				         <div class="ftitle">
				            <label for="priceA">站主:</label>
				            <input class="easyui-validatebox commonInput" type="text" id="stationOwnerA" name="stationOwner" style="width:200px"  
				             readonly="readonly"   />
				        </div>
				        <div class="ftitle">
				            <label for="priceA">身份证正面图片:</label>
				            <img id="idNumberFrontImgA" style="width:300px;height:300px;" alt="点击放大" src="" onclick="previewImage('idNumberFrontImgA')">
				        </div>
				         <div class="ftitle">
				            <label for="priceA">身份证反面图片:</label>
				            <img id="idNumberBackImgA" alt="点击放大" style="width:300px;height:300px;" src="" onclick="previewImage('idNumberBackImgA')">
				        </div>
				        <div class="ftitle">
				            <label for="priceA">代销证图片:</label>
				            <img id="daixiaoImgA" alt="点击放大" style="width:300px;height:300px;" src="" onclick="previewImage('daixiaoImgA')">
				        </div>
				        
				       </form>
    		</div>
			
    		
	     
    </div>
    
    
   
</body>
	
	
</html>