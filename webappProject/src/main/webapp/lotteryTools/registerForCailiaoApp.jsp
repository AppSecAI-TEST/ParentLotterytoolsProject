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
<script type="text/javascript" src="<%=request.getContextPath() %>/lotteryTools/js/registerForCailiaoApp.js"></script>
<title>彩聊账号注册</title>


</head>
<body>
	<div class="box" style="padding: 10px;">
		<div class="weui_cells_title">注册彩聊账号</div>
		<div class="weui_cells weui_cells_form">
			<div class="weui_cell">
					<div class="weui_cell_hd">
						<label class="weui_label">昵称</label>
					</div>
					<div class="weui_cell_bd weui_cell_primary">
						<input class="weui_input" type="text" name="name" id="name" onblur="checkName()" placeholder="请输入昵称" />
					</div>
			</div>
			<div class="weui_cell">
					<div class="weui_cell_hd">
						<label class="weui_label">手机号</label>
					</div>
					<div class="weui_cell_bd weui_cell_primary">
						<input class="weui_input" type="text" name="telephone" id="telephone" placeholder="请输入手机号" />
					</div>
			</div>
			<div class="weui_cell weui_vcode weui_cell_primary">
					<div class="weui_cell_hd">
						<label class="weui_label">验证码</label>
					</div>
					<div class="weui_cell_bd weui_cell_primary">
						<input class="weui_input"  type="text" id="yanzhengma"  placeholder="请输入验证码" />
					</div>
					<div class="weui_cell_ft">
						<i class="weui_icon"></i>
						<input id="getYzm" style="border-radius:5px;margin-top: 5px;margin-bottom: 5px;" onclick="sendCode(this)" type="button" value="获取验证码"/>
						<!-- <a href="javascript:;"  class="weui_btn weui_btn_mini weui_btn_default">获取验证码</a> -->
					</div>
			</div>	
			<div class="weui_cell">
					<div class="weui_cell_hd">
						<label class="weui_label">密码</label>
					</div>
					<div class="weui_cell_bd weui_cell_primary">
						<input class="weui_input" type="password"  name="password" id="password" onblur="validatePwd()" placeholder="请输入密码" />
					</div>
			</div>
			<div class="weui_cell">
					<div class="weui_cell_hd">
						<label class="weui_label" id="cLabel">确认密码</label>
					</div>
					<div class="weui_cell_bd weui_cell_primary">
						<input class="weui_input" type="password" onChange="checkPwd()"  name="cpassword" id="cpassword" placeholder="确认密码" />
					</div>
			</div>
			
		</div>
		<div class="weui_btn_area">
				<a class="weui_btn weui_btn_plain_primary" href="javascript:" id="showTooltips" onclick="register()">注册</a>
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
</body>
</html>