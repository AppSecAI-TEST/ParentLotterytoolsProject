//var contextPath='36.7.190.20:1818/webappProject';
//var contextPath='192.168.1.124:8080/webappProject';
var inviteCode = '';
$(function()
		{
			inviteCode = getQueryString("inviteCode");
		});
function getTelephoneYzm()
{
	var tel = $("#telephone").val().trim();
	if(null != tel &&"" != tel)
		{
			var data = new Object();
			data.telephone = tel;
			$.ajax({
				async: false,   //设置为同步获取数据形式
		        type: "get",
		        url: contextPath+'/outerLbuyerOrexpert/getYanzhengmaForRegister.action',
		        data:data,
		        dataType: "json",
		        success: function (returndata) {
		        	if('200' == returndata.resultCode)
		        		{
		        			alert(returndata.message);
		        		}
		        	else
		        		if('500' == returndata.resultCode)
		        		{
		        			alert(returndata.message);
		        		}
		        	
		        },
		        error: function (XMLHttpRequest, textStatus, errorThrown) {
		        	alert("error");
		        }
		   });
		}
	else
		{
			alert("请填写手机号后获取验证码!");
		}
}

function alert(msg)
{
	
	$('#toast').show().delay(3000).hide(0);
	$("#toastContent").html(msg);
	
}

function checkTelephoneYzm()
{
	var data = new Object();
	data.telephone = $("#telephone").val();
	data.yanzhengma = $("#yanzhengma").val();
	var redata = new Object();
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: contextPath+'/outerLbuyerOrexpert/checkYanzhengma.action',
        data:data,
        dataType: "json",
        success: function (returndata) {
        	redata = returndata;
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
   });
	return redata;
}

function validatePwd()
{
	var pwd = $("#password").val().trim();
	if(pwd.length>20)
		{
			alert("密码最长20位");
			$("#password").val("");
		}
}

function checkName()
{
	var name = $("#name").val().trim();
	if(name.length>15)
		{
			alert("昵称最多15个字");
			$("#name").val("");
		}
	else
		if(name.length==0)
		{
			alert("昵称必须输入");
		}
}

function register()
{
	//注册前先验证手机验证码
	var name = $("#name").val().trim();
	var password = $("#password").val().trim();
	var telephone = $("#telephone").val().trim();
	if(name != "" &&password != ""&&telephone != ""  )
		{
		var pwdflag = checkPwd();
		if(pwdflag)
			{
				var redata =  checkTelephoneYzm();
				if('200' == redata.resultCode)
					{
						//验证成功后提交注册信息
						var data = new Object();
						data.telephone = $("#telephone").val().trim();
						data.password = $("#password").val().trim();
						data.inviteCode = inviteCode;
						data.name = $("#name").val().trim();
						$.ajax({
							async: false,   //设置为同步获取数据形式
					        type: "get",
					        url: contextPath+'/outerLbuyerOrexpert/saveFromApp.action',
					        data:data,
					        dataType: "json",
					        success: function (returndata) {
					        	
					        	if('408' == returndata.resultCode)
					        		{//手机号已注册
					        			alert(returndata.message);
					        		}
					        	else
					        		if('200' == returndata.resultCode)
					        		{//注册成功，提示下载app
					        			alert(returndata.message+"!下载应用!");
										//获取当前应用的最新版本
										getAppversion();
					        		}
					        	
						        },
						        error: function (XMLHttpRequest, textStatus, errorThrown) {
						        }
						   });
					}
				else
					{
						alert(redata.message);
					}
			
			}
		else
			{
				if(!pwdflag)
				{
						alert("请确认密码!");
				}
			}
		}
	else
		{
			alert("昵称/密码/手机号不可以为空!");
		}
		

}


//从一个页面获取另一个页面的url
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) 
		return unescape(r[2]); 
	return null;
}

function getAppversion()
{
	window.location.href = contextPath+'/uploadApkFile/4518df16-3c63-4a40-b45b-fd0f552fb558.apk' ;
}

function checkPwd()
{
	var flag = false;
	var pwd = $("#password").val();
	var cpwd = $("#cpassword").val();
	if(pwd != cpwd)
		{
			$("#cLabel").css({color:"red"});
		}
	else
		{
			flag = true;
			$("#cLabel").css({color:"black"});
		}
	return flag;
}


var clock = '';
var nums = 60;
var btn;
function sendCode(thisBtn)
{ 
	getTelephoneYzm();
	btn = thisBtn;
	btn.disabled = true; //将按钮置为不可点击
	btn.value = nums+'秒后可重新获取';
	clock = setInterval(doLoop, 1000); //一秒执行一次
	}
	function doLoop()
	{
	nums--;
	if(nums > 0){
	 btn.value = nums+'秒后可重新获取';
	}else{
	 clearInterval(clock); //清除js定时器
	 btn.disabled = false;
	 btn.value = '点击发送验证码';
	 nums = 10; //重置时间
	}
}
	
	
	