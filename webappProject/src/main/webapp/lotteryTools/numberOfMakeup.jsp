<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
<script>
	var contextPath = '<%=request.getContextPath() %>';
	
</script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/weui.min.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/lotteryTools/js/numberOfMakeup.js"></script>
<title>号码补录</title>


</head>
<body>
	<div id="box" class="box" style="padding: 10px;">
		<input type="hidden" name="sid" id="sid" value="${sid}">
		<input type="hidden" name="role" id="role" value="${role}">
		<div class="weui_cells_title">选择区域和彩种</div>
             <div class="weui_cell weui_cell_select weui_select_after">
                <div class="weui_cell_hd" style="font-weight:bold;font-size:15px;color: #000;width:4em;">
                  	类型
                </div>
                <div class="weui_cell_bd weui_cell_primary">
                    <select class="weui_select" name="lotteryType" id="lotteryType">
                        <option value="1">体彩</option>
                        <option value="2">福彩</option>
                    </select>
                </div>
            </div>
            <div class="weui_cell weui_cell_select weui_select_after">
                <div class="weui_cell_hd" style="font-weight:bold;font-size:15px;color: #000;width:4em;">
                  	彩种
                </div>
                <div class="weui_cell_bd weui_cell_primary">
                    <select class="weui_select" name="lotteryPlay" id="lotteryPlay">
                        <option value="1">辽宁快乐12</option>
                        <option value="2">快三</option>
                    </select>
                </div>
            </div>
            <div class="weui_cell weui_cell_select weui_select_after">
                <div class="weui_cell_hd" style="font-weight:bold;font-size:15px;color: #000;width:4em;">
                  <label class="weui_label">期号 </label>
                </div>
                <div class="weui_cell_bd weui_cell_primary">
                   <input class="weui_input" type="text" name="issueNum" id="issueNum" placeholder="请输入期号"  />
                </div>
            </div>
            <!-- 补录div内容 -->
            <div id="couldKjDiv">
           
				<div class="weui_cells_title">请按开奖顺序点击开奖号码</div>
				<div class="weui_cells weui_cells_form">
		           <div class="weui_cell">
							<div class="weui_cell_hd">
								<label class="weui_label">奖号</label>
							</div>
							<div class="weui_cell_bd weui_cell_primary">
									<input class="weui_input" type="text" name="numJ" id="numJ"  readonly="readonly" style="width:85%;"/>
									<i class="weui_icon_cancel" onclick="clearnumJ()" style="cursor:pointer;float:right;"></i>
							</div>
					</div>
				</div>
				<div id="lpBuluPlanDiv" style="backgroud-color:gray;width:80%;margin-right:auto;margin-lef:auto;">
					<a id="but1"  href="javascript:toogleFunction('but1');" class="weui_btn weui_btn_mini weui_btn_primary " style="width:20%;margin-right:5%;" >01</a>
					<a id="but2" href="javascript:toogleFunction('but2');" class="weui_btn weui_btn_mini weui_btn_primary" style="width:20%;margin-right:5%;">02</a>
					<a id="but3" href="javascript:toogleFunction('but3');" class="weui_btn weui_btn_mini weui_btn_primary" style="width:20%;margin-right:5%;">03</a>
					<a id="but4" href="javascript:toogleFunction('but4');" class="weui_btn weui_btn_mini weui_btn_primary" style="width:20%;">04</a>
					<a id="but5" href="javascript:toogleFunction('but5');" class="weui_btn weui_btn_mini weui_btn_primary" style="width:20%;margin-right:5%;">05</a>
					<a id="but6" href="javascript:toogleFunction('but6');" class="weui_btn weui_btn_mini weui_btn_primary" style="width:20%;margin-right:5%;">06</a>
					<a id="but7" href="javascript:toogleFunction('but7');" class="weui_btn weui_btn_mini weui_btn_primary" style="width:20%;margin-right:5%;">07</a>
					<a id="but8" href="javascript:toogleFunction('but8');" class="weui_btn weui_btn_mini weui_btn_primary" style="width:20%;">08</a>
				</div>
				
				<div class="weui_cells_tips"><input type="checkbox" id="agree" style="cursor:pointer"/>
			<label for="agree">我已阅读并同意<a  href="javascript:$('#dialog2').show();" id="showDialog2">《佰艺霖号码补录规则》</a></label></div>
			<div class="weui_btn_area" id="submitBut">
					<a class="weui_btn weui_btn_primary" href="javascript:" id="showTooltips" onclick="submitBulu()">确认提交</a>
		    </div>
	     </div>
	</div>
	
	
	
	<div class="weui_msg" id="sucMsg">
        <div class="weui_icon_area"><i class="weui_icon_success weui_icon_msg"></i></div>
        <div class="weui_text_area">
            <h2 class="weui_msg_title">操作成功</h2>
            <p class="weui_msg_desc"></p>
        </div>
        <div class="weui_opr_area">
            <p class="weui_btn_area">
                <a href="javascript:;" class="weui_btn weui_btn_primary" onclick="window.close();">确定</a>
            </p>
        </div>
    </div>
    
    
    
    <!--BEGIN toast-->
		<div id="toast" style="display:none;">
			<div class="weui_mask_transparent"></div>
			<div class="weui_toast">
				<i class="weui_icon_toast"></i>
				<p class="weui_toast_content" id="toastContent"></p>
			</div>
		</div>
		<!--end toast-->
	<!--BEGIN dialog2-->
		<div class="weui_dialog_alert" id="dialog2" style="display: none;">
			<div class="weui_mask"></div>
			<div class="weui_dialog">
				<div class="weui_dialog_hd"><strong class="weui_dialog_title">《佰艺霖发布广告规则》</strong></div>
				<div class="weui_dialog_bd">
					1.通行证发布应用广告时要将必填项全部填写后才可提交到市中心审批。<br/>
					2.请注意广告名称和广告内容的输入要求，按照要求进行填写。<br/>
					3.通行证发布的应用广告的最终解释权归应用广告的发布者即通行证所有者。<br/>
					4.通行证发布应用广告的行为是通行证所属人员的主观行为，与辽宁省佰艺霖科技有限公司无直接关系。<br/>
					5.通行证使用的一个应用上只可以发布一条应用广告，若多次发布且市中心审批通过，则会覆盖上一次的应用广告内容。<br/>
				</div>
				<div class="weui_dialog_ft">
					<a id="ok1" href="javascript:$('#dialog2').hide();" class="weui_btn_dialog primary" >确定</a>
				</div>
			</div>
		</div>
</body>
</html>