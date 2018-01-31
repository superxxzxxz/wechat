/**
 * 对公众平台发送给公众账号的消息加解密示例代码.
 * 
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */

// ------------------------------------------------------------------------

package com.xxz.wechat.utils;

import java.io.StringReader;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.json.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.xxz.wechat.entity.MsgType;
import com.xxz.wechat.entity.inputMessageEntity;
import com.xxz.wechat.robot.turingRobot;

/**
 * XMLParse class
 *
 * 提供提取消息格式中的密文及生成回复消息格式的接口.
 */
class XMLParses {
	private static turingRobot tr=new turingRobot();
	/**
	 * 提取出xml数据包中的加密消息
	 * @param xmltext 待提取的xml字符串
	 * @return 提取出的加密消息字符串
	 * @throws AesException 
	 */
	public static Object[] extract(String xmltext) throws AesException     {
		Object[] result = new Object[3];
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(xmltext);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);

			Element root = document.getDocumentElement();
			NodeList nodelist1 = root.getElementsByTagName("Encrypt");
			NodeList nodelist2 = root.getElementsByTagName("ToUserName");
			result[0] = 0;
			result[1] = nodelist1.item(0).getTextContent();
			result[2] = nodelist2.item(0).getTextContent();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.ParseXmlError);
		}
	}

	/**
	 * 生成xml消息
	 * @param encrypt 加密后的消息密文
	 * @param signature 安全签名
	 * @param timestamp 时间戳
	 * @param nonce 随机字符串
	 * @return 生成的xml字符串
	 */
	public static String generate(String encrypt, String signature, String timestamp, String nonce) {

		String format = "<xml>\n" + "<Encrypt><![CDATA[%1$s]]></Encrypt>\n"
				+ "<MsgSignature><![CDATA[%2$s]]></MsgSignature>\n"
				+ "<TimeStamp>%3$s</TimeStamp>\n" + "<Nonce><![CDATA[%4$s]]></Nonce>\n" + "</xml>";
		return String.format(format, encrypt, signature, timestamp, nonce);

	}
	/**
	 * 公众帐号处理消息，生成需要回复给微信公众平台的xml消息体：
	 * @return (生成加密前xml)
	 */
	public static String generateReplyXML(inputMessageEntity inputMsg){
		 Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间 
		 MsgType msgtype=MsgType.valueOf(inputMsg.getMsgType());//判断枚举，不同的消息类型生成不同的xml
		 String responseMsg="";//生成的xml
		 String outputContent="";//回复内容
		 //调用图灵机器人接口方法
		 JSONObject outinputobject = tr.getTuringRobot(inputMsg.getContent(), "");
		 outputContent=outinputobject.isNullObject()?"图灵机器人没有回复消息，请再试试吧！":outinputobject.get("text").toString();
		 switch (msgtype) {
			case text:
				 responseMsg="<xml>\n"+"<ToUserName><![CDATA[%1$s]]></ToUserName>\n"
				 + "<FromUserName><![CDATA[%2$s]]></FromUserName>\n"
				 + "<CreateTime>%3$s</CreateTime>\n"
				 + "<MsgType><![CDATA[%4$s]]></MsgType>\n"
				 + "<Content><![CDATA[%5$s]]></Content>\n"
				 + "</xml>"; 
				 return String.format(responseMsg, inputMsg.getFromUserName(),inputMsg.getToUserName(),returnTime,inputMsg.getMsgType(),outputContent);
			case image:
				System.out.println("图片消息");
				return "";
			case voice:
				System.out.println("语音消息");
				return "";
			case video:
				System.out.println("视频消息");
				return "";
			case shortvideo:
				System.out.println("小视频消息");
				return "";
			case location:
				System.out.println("地理位置消息");
				return "";
			case link:
				System.out.println("链接消息");
				return "";
			default:
				break;
			}
		 return "";
	}
}
