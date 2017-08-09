var startNumber = 0;
var endNumber = 0;
var lotteryNumber = 0;//开奖号码个数
var repeatNum = '0';//开奖号码是否重复。0：不重复 1：重复
var issueNumLen = 0;//期号长度

var splitFlag = ' ';//奖号分隔符，若修改分隔符，在此处统一修改即可


$(function(){
	
	$("#couldKjDiv").hide();//隐藏补录区域内容
	$("#sucMsg").hide();
	$("#box").show();
	$("#submitBut").hide();
	
	$("#agree").click(function()
			{
				if($("#agree").is(':checked'))
					{
						$("#submitBut").show();
					}
				else
					{
						$("#submitBut").hide();
					}
			});
	
	bindChange();//绑定下拉框的change事件
	
	
	initLotteryPlay();//初始化彩种玩法数据
	
	
	initLotteryPlayBuluPlan();//查询补录信息的补录方案
	
	getLotteryPlayDetail();//初始化补录信息详情数据
	
	
});

/**
 * 绑定change事件
 */
function bindChange()
{
	$("#lotteryType").change(function(){
			initLotteryPlay();
			initLotteryPlayBuluPlan();
			getLotteryPlayDetail();//初始化补录信息详情数据
			$("#couldKjDiv").hide();//隐藏补录区域内容
			$("#issueNum").val("");//清空期号
			$("#numJ").val("");//清空奖号
		});
	$("#lotteryPlay").change(function(){
		initLotteryPlayBuluPlan();
		getLotteryPlayDetail();//初始化补录信息详情数据
		$("#couldKjDiv").hide();//隐藏补录区域内容
		$("#issueNum").val("");//清空期号
		$("#numJ").val("");//清空奖号
	});
	
	$("#issueNum").keyup(function(){
		//获取输入值的长度
		var len = $(this).val().length;
		if(issueNumLen == len)//判断期号长度
			{//若期号长度符合当前需要补录的彩种的期号要求时，则进行期号是否符合规则的判断
				var flag =  checkIssueNum($(this).val());
				if(flag)
					{
						$("#couldKjDiv").show();//可以进行补录时则显示补录区域内容
						//若可以进行补录操作，则要开始准备补录区域的内容
						initBuluDivContant();
					}
				else
					{
						$('#toast').show().delay(3000).hide(0);
						$("#toastContent").html('当前期号不可以进行补录!');
						$("#couldKjDiv").hide();
					}
			}
		else
			{
				$("#couldKjDiv").hide();
			}
	});
}

/**
 * 校验期号是否符合规则
 * @param issueNum
 */
function checkIssueNum(issueNum)
{
	var flag = true;
	var lotteryPlay = $("#lotteryPlay option:selected").val();//获取当前选中的待补录的补录信息id,目的是在后台要找其对应的表
	
	var data = new Object();
	data.id=lotteryPlay;//获取当前待补录的彩种
	data.issueNum = issueNum;//当前待补录的期号
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: contextPath+'/lDipinPlay/checkIssueNum.action',
        data:data,
        dataType: "json",
        success: function (result) {
        	
        	flag = result.flag;//false：不可以进行补录（期号不符合或已补录），true：可以进行补录
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
   });
	
	
	return flag;
}

/**
 * 初始化补录区域内容
 */
function initBuluDivContant()
{
	//1.按照补录方案，生成补录区域待选择内容
	/*if('0' == numOrChar)
		{*///数字方案，按照开始号码和结束号码，生成补录方案
			$("#lpBuluPlanDiv").html("");//清空方案内容
			
			var funHtml = '';
			if('0' == repeatNum)//开奖号码是否重复
			{//不重复
				funHtml = 'toogleFunction';
			}
			else
				{
					if('1' == repeatNum)//开奖号码是否重复
					{//重复
						funHtml = 'repeatNumFunction';
					}
				}
			
			var html = '';
			for(var i = startNumber;i <= endNumber ;i++)
				{//4个带一个换行
					if((i-startNumber)%4 == 3 && i != startNumber)//这么写是为了避免开始号码不仅是0和1
						{
							html += '<a id="but'+i+'" href="javascript:'+funHtml+'(&quot;but'+i+'&quot;);" '
									+'class="weui_btn weui_btn_mini weui_btn_primary" style="width:20%;">'
									+i+'</a>';
						}
					else
						{
							html += '<a id="but'+i+'" href="javascript:'+funHtml+'(&quot;but'+i+'&quot;);" '
							+'class="weui_btn weui_btn_mini weui_btn_primary" style="width:20%;margin-right:5%;">'
							+i+'</a>';
						}
					
				}
	/*	}
	else
		{
			if('1' == numOrChar)
			{//其他档案，按照其他方案内容，正则提取内容进行补录方案的生成
				var plan = otherPlan.split(",");//以“，”分隔非数字方案的内容
				
				var funHtml = '';
				if('0' == repeatNum)//开奖号码是否重复
				{//不重复
					funHtml = 'toogleFunction';
				}
				else
					{
						if('1' == repeatNum)//开奖号码是否重复
						{//重复
							funHtml = 'repeatNumFunction';
						}
					}
				
				var html = '';
				for(var i = 0;i < plan.length ;i++)
				{//4个带一个换行
					if((i-0)%4 == 3 && i != 0)//这么写是为了避免开始号码不仅是0和1
					{
						html += '<a id="but'+i+'" href="javascript:'+funHtml+'(&quot;but'+i+'&quot;);" '
								+'class="weui_btn weui_btn_mini weui_btn_primary" style="width:20%;">'
								+plan[i]+'</a>';
					}
					else
					{
						html += '<a id="but'+i+'" href="javascript:'+funHtml+'(&quot;but'+i+'&quot;);" '
						+'class="weui_btn weui_btn_mini weui_btn_primary" style="width:20%;margin-right:5%;">'
						+plan[i]+'</a>';
					}
				}
			}
		}*/
	
	//放置方案內容
	$("#lpBuluPlanDiv").html(html);
}

/**
 * 初始化彩种类型数据。根据省份和彩种类型
 */
function initLotteryPlay()
{
	$("#lotteryPlay").empty();
	
	var data = new Object();
	data.lotteryType = $("#lotteryType option:selected").val();
	data.rows=10000;
	data.page=1;
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: contextPath+'/lDipinPlay/getLotteryDiPinPlayList.action',
        data:data,
        dataType: "json",
        success: function (returndata) {
        	
        	var option;
        	var result = returndata.rows;
        	if(result.length>0)
        		{
	        		for(var i=0;i<result.length;i++)
	        		{
	        			var lotteryPlay = result[i];
	        			var id = lotteryPlay.id;
	        			var name = lotteryPlay.planName;
	        			option=$("<option>").val(id).text(name);
						
						$("#lotteryPlay").append(option);
	        		}
	        	
        		}
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
   });
}

/**
 *获取补录方案数据
 */
function initLotteryPlayBuluPlan()
{
	var data = new Object();
//	data.province = $("#province option:selected").val();
//	data.lotteryType = $("#lotteryType option:selected").val();
	data.id = $("#lotteryPlay option:selected").val();//获取补录信息id
	
	if(null == data.id || '' == data.id)
		{
			$('#toast').show().delay(3000).hide(0);
			$("#toastContent").html('当前没有可以进行补录的彩种!');
		}
	else
		{
			$.ajax({
				async: false,   //设置为同步获取数据形式
		        type: "get",
		        url: contextPath+'/lDipinPlay/getLotteryDiPinPlayById.action',
		        data:data,
		        dataType: "json",
		        success: function (returndata) {
		        	
		        	
		        	startNumber = returndata.startNumber;
		        	endNumber = returndata.endNumber;
		        	repeatNum = returndata.repeatNum;
		        	
		        },
		        error: function (XMLHttpRequest, textStatus, errorThrown) {
		        }
		   });
		}
	
}


//点击按钮切换class方法
function toogleFunction(id)
{
//   $( "#but1" ).removeClass( "weui_btn_primary", 1000 );
	 $( "#"+id ).toggleClass( "weui_btn_default weui_btn_primary", 1000 );//toggleClass中的第一个参数，若切换的对象有这个class就移除，没有就添加
//	     alert( $( this  ).html());
     appendNum( $( "#"+id ).html());
}

//点击可以重复开奖号码的按钮
function repeatNumFunction(id)
{
//	      $(this ).toggleClass( "weui_btn_default weui_btn_primary", 1000 );//toggleClass中的第一个参数，若切换的对象有这个class就移除，没有就添加
	     appendRepeatNum( $( "#"+id ).html());
}

//拼接不可重复奖号
function appendNum(num)
{
	var numj = $("#numJ").val();
	var numArr = numj.split(splitFlag);
	
	var finalNum = '';
	
	var addFlag = true;
	
	for(var i=0;i<numArr.length;i++)
	{
		if(num == numArr[i])
			{
				addFlag	 = false;
			}
	}
	
	
//	alert(addFlag);
	if(addFlag)//没有这个奖号，添加
		{
			//若当前选中的补录号码个数已经满足待补录彩种的开奖号的个数且新点击的号码未在补录的开奖号中，则不可以继续
			if( (numArr.length-1) == lotteryNumber )
				{
					finalNum = numj;
					$('#toast').show().delay(3000).hide(0);
					$("#toastContent").html('当前彩种的开奖号码个数已经满足补录要求!');
					//因为当前点击的按钮没有被记录到奖号里，所以要将奖号按钮切换会绿色未选中状态
					$('#but'+num).toggleClass( "weui_btn_default weui_btn_primary", 1000 );
				}
			else
				{
					finalNum += numj +  num + splitFlag;
				}
		
			
		}
	else
		{//有这个奖号，再次点击则触发移除操作
			for(var i=0;i<numArr.length-1;i++)
			{
				if(num != numArr[i])
					{
						finalNum = finalNum +  numArr[i] + splitFlag;
					}
			}
		}
	
	
	
	$("#numJ").val(finalNum);//去掉输入框的左空格
}

//拼接可重复奖号
function appendRepeatNum(num)
{
	var numj = $("#numJ").val();
	var numArr = numj.split(splitFlag);
	
	var finalNum = '';
	
	var addFlag = true;
	
	//①：一个号码可以在开奖号码中存在多次
	
	//若当前选中的补录号码个数已经满足待补录彩种的开奖号的个数且新点击的号码未在补录的开奖号中，则不可以继续
	if( (numArr.length-1) == lotteryNumber )
		{
			finalNum = numj;
			$('#toast').show().delay(3000).hide(0);
			$("#toastContent").html('当前彩种的开奖号码个数已经满足补录要求!');
		}
	else
		{
			finalNum += numj +  num + splitFlag;
		}
		
	$("#numJ").val(finalNum);//去掉输入框的左空格
}
//清空奖号
function clearnumJ()
{
	$("#numJ").val("");//清空奖号
	initBuluDivContant();//重新生成方案
}





/**
 * 根据id获取补录信息详情
 */
function getLotteryPlayDetail()
{
	var data = new Object();
	data.id=$("#lotteryPlay option:selected").val();
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: contextPath+'/lDipinPlay/getLotteryDiPinPlayById.action',
        data:data,
        dataType: "json",
        success: function (result) {
//        	startNumber = returndata.startNumber;
//        	endNumber = returndata.endNumber;
//        	repeatNum = returndata.repeatNum;
        	issueNumLen = result.issueNumLen;
        	lotteryNumber = result.lotteryNumber;
        	
        	$("#issueNum").attr('placeholder','请输入'+issueNumLen+'位期号');
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
   });
	
	
}

/**
 * 提交补录信息
 */
function submitBulu()
{
	var submitFlag = true;
	
	var data = new Object();
	data.lotteryPlay=$("#lotteryPlay option:selected").val();//补录彩种
	data.issueNum=$("#issueNum").val();//补录的期号
	data.numJ=$("#numJ").val();//补录的开奖号码
	
	var numArr = $("#numJ").val().split(splitFlag);
	if((numArr.length-1) < lotteryNumber)
		{//奖号长度没有达到要求
			submitFlag = false;
			$('#toast').show().delay(3000).hide(0);
			$("#toastContent").html('当前彩种的开奖号码个数不满足补录要求，请补录开奖号码'+lotteryNumber+'位!');
		}
	if(submitFlag)
		{
			$.ajax({
				async: false,   //设置为同步获取数据形式
		        type: "get",
		        url: contextPath+'/lDipinPlay/submitBuluMsg.action',
		        data:data,
		        dataType: "json",
		        success: function (result) {
		        	var showHtml = '';
		        	if(result.success)
		        		{
		        			showHtml = result.message;
		        			//补录成功后情况填写的内容
		        			$("#couldKjDiv").hide();//隐藏补录区域内容
		        			$("#issueNum").val("");//清空期号
		        			$("#numJ").val("");//清空奖号
		        		}
		        	else
		        		{
		        			showHtml = "补录失败!";
		        		}
		        	$('#toast').show().delay(3000).hide(0);
					$("#toastContent").html(showHtml);
					
					
					
		        },
		        error: function (XMLHttpRequest, textStatus, errorThrown) {
		        }
		   });
		}
	
	
}
