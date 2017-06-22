$(function()
		{
			initProvince();
			
//			checkCity();
		});

function initProvince()
{
//	var data = new Object();
//	data.isHasall = false;//包含"全部"
	
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: contextPath+'/outer/generate.action',
//        data:data,
        dataType: "json",
        success: function (data) {
        	
        	/*for(var i=0;i<data.length;i++)
        		{
        			var pcode = data[i].pcode;
        			initCity(pcode);
        			checkCity(pcode);
        		}*/
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
   });
}

function initCity(pcode)
{
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: './province.json',
        dataType: "json",
        success: function (data) {
        	
        	initCityData(pcode, data['area1'][pcode]);
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
   });
}


function initCityData(pcode,codeArr)
{
	var data = new Object();
	data.province = pcode;
	if(null != codeArr && undefined != codeArr)
		{
			data.cities = codeArr.toString();
			$.ajax({
				async: false,   //设置为同步获取数据形式
		        type: "get",
		        url: contextPath+'/area/initCity.action',
		        data:data,
		        dataType: "json",
		        success: function (data) {
		        	
		        	
		        },
		        error: function (XMLHttpRequest, textStatus, errorThrown) {
		        }
		   });
		}
	
}

function checkCity(pcode)
{
	var data = new Object();
	data.isHasall = false;//包含"全部"
	data.pcode = pcode;
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "post",
        url: contextPath+'/menu/getCityList.action',
        data:data,
        dataType: "json",
        success: function (data) {
        	
        	for(var i=0;i<data.length;i++)
        		{
        			var ccode = data[i].ccode;
        			initRegion(ccode);
        		}
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
   });
}


function initRegion(ccode)
{
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: './province.json',
        dataType: "json",
        success: function (data) {
        	initRegionData(ccode, data['area2'][ccode]);
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
   });
}

function initRegionData(ccode,codeArr)
{
	var data = new Object();
	data.city = ccode;
	data.regions = codeArr.toString();
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "get",
        url: contextPath+'/area/initRegion.action',
        data:data,
        dataType: "json",
        success: function (data) {
        	
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
   });
}