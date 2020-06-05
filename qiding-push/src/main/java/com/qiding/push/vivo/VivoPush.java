package com.qiding.push.vivo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.vivo.push.sdk.notofication.Message;
import com.vivo.push.sdk.notofication.Result;
import com.vivo.push.sdk.server.Sender;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.UUID;

public class VivoPush {
	public static void main(String[] args) {
		Properties properties = new Properties();
		String vivoConfigPath = System.getProperty("vivo-config");
		String title = System.getProperty("vivo-title");
		String content = System.getProperty("vivo-content");

		try (FileInputStream inputStream = new FileInputStream(vivoConfigPath)) {
			properties.load(inputStream);
			VivoConfig config = JSON.parseObject(JSON.toJSONString(properties), VivoConfig.class, Feature.SafeMode);
			//获取一次性token
			Sender sender = new Sender(config.getAppSecret());
			Result result = sender.getToken(Integer.parseInt(config.getAppId()), config.getAppKey());
			String authToken = result.getAuthToken();
			//消息内容
			Message messagePayload = buildMessage(config, title, content);
			//消息发送
			sender = new Sender(config.getAppSecret(), authToken);
			sender.initPool(config.getConnectCount(), config.getRouter());
			//发送结果
			result = sender.sendSingle(messagePayload);
			System.out.println(JSON.toJSONString(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Message buildMessage(VivoConfig vivoConfig,  String title, String content) {
		return new Message.Builder()
			.networkType(-1)
			.title(title)
			.content(content)
			.notifyType(3)
			.skipType(1)
			.skipContent("http://www.vivo.com")
			.timeToLive(86400)
			.regId(vivoConfig.getRegId())
			.requestId(UUID.randomUUID().toString())
			.build();
	}
}
