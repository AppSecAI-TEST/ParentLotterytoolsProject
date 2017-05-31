package com.BYL.lotteryTools.backstage.outer.controller;

import io.netty.handler.codec.http.HttpMethod;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.NettyHttpClient;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;


/**
 * 推送控制层
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年4月14日 上午8:41:29
 */
@Controller
@RequestMapping("/push")
public class PushController 
{
	private  static final Logger LOG = LoggerFactory.getLogger(PushController.class);
	
	 protected static final String APP_KEY ="1ac163a5a1cdd796a873513a";
	 protected static final String MASTER_SECRET = "a73cf9a375c57af5f3f9538e";
	 
//	 private static final String TITLE = "title";
//     private static final String MSG_CONTENT = "msg_content";
//     private static final String CONTENT_TYPE = "content_type";
//     private static final String EXTRAS = "extras";
     
     // 使用 NettyHttpClient 异步接口发送请求
     @RequestMapping("/sendPush")
     public static void sendPushWithCallback(@RequestParam(value="tagsand",required=false) String[] tagsand,
    		 @RequestParam(value="alias",required=false) String[] alias,
     		@RequestParam(value="msgContent",required=false) String msgContent,
     		@RequestParam(value="province",required=false) String province) 
     {
         ClientConfig clientConfig = ClientConfig.getInstance();
         String host = (String) clientConfig.get(ClientConfig.PUSH_HOST_NAME);
         final NettyHttpClient client = new NettyHttpClient(ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET),
                 null, clientConfig);
         try {
             URI uri = new URI(host + clientConfig.get(ClientConfig.PUSH_PATH));
             PushPayload payload = buildPushObjectWithExtras(tagsand, alias, msgContent, province);
             client.sendRequest(HttpMethod.POST, payload.toString(), uri, new NettyHttpClient.BaseCallback() 
             {
                 public void onSucceed(ResponseWrapper responseWrapper) 
                 {
                	 LOG.info("Got result: " + responseWrapper.responseContent);
                 }
             });
         }
         catch (URISyntaxException e)
         {
             e.printStackTrace();
         }
     }
	 
	//推送自定义消息
    public static PushPayload buildPushObjectWithExtras(
    		@RequestParam String[] tagsand,@RequestParam String[] alias,
    		@RequestParam String msgContent,@RequestParam String province) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(
//                		Audience.all()
                		Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.tag_and(tagsand))//标签，and关系
//                        .addAudienceTarget(AudienceTarget.alias(alias))//别名，or关系
                        .build()
                        )
                .setMessage(Message.newBuilder()
                        .setMsgContent(msgContent)
                        .addExtra("type", province)
                        .build())
                .build();
    }
}
