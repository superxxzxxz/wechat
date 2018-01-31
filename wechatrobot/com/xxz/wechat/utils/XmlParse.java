package com.xxz.wechat.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.xxz.wechat.entity.inputMessageEntity;
/**
 * 
 * @author xxz
 *	微信XML消息解析工具类
 */
public class XmlParse {
	static String token = "XXZ654962327ZXCVBNM";//令牌(自定义，必须与微信配置一致)
	static String encodingAesKey = "hAxZa1WAuNe1kiInweyRcLPTdX3pmGNjCiydawbz7qO";//消息加解密密钥
	static String appId = "wx96270b15dc18360d";//开发者ID
	//static String xmlFormat = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
	//官方加解密方法
	private static XMLParses xmlparses=new XMLParses();
	
	/**
	 * 信息流转换字符串
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
	/**
	 * 微信服务器将POST消息的XML数据包到开发者填写的URL上
	 * 这里将解析xml包
	 * @param inputis
	 * @return
	 * @throws IOException 
	 */
	public static Object obtainXMLPackageAndXmlParse(HttpServletRequest request) throws IOException{
		String encryption="";//接收的加密数据
		String decryptData="";//解密后数据
		String encrypt_type= request.getParameter("encrypt_type");//（加密类型，为aes）
	    String msgSignature = request.getParameter("msg_signature"); //msg_signature（消息体签名，用于验证消息体的正确性）        
        String timestamp = request.getParameter("timestamp");//时间戳
        String nonce = request.getParameter("nonce");//随机数    
		try {
		//解密
		encryption=convertStreamToString(request.getInputStream());//将信息流转换为字符串
		encryption=new String(encryption.getBytes(),"UTF-8");//设置编码，防止乱码
		System.out.println("接收微信POSTxml包："+encryption+"加密方式为"+encrypt_type);
		if(encrypt_type.equalsIgnoreCase("aes")){
		WXBizMsgCrypt wxmsgcrypt=new WXBizMsgCrypt(token, encodingAesKey, appId);//实例化对象
		decryptData=wxmsgcrypt.decryptMsg(msgSignature, timestamp, nonce, encryption);//解密
		}
		System.out.println("解密后："+decryptData.toString());
		XStream xstream = new XStream(new DomDriver());
		//将POST流转换为XStream对象  
		xstream.processAnnotations(inputMessageEntity.class);
		// 将指定节点下的xml节点数据映射为对象
		xstream.alias("inputMessageEntity", inputMessageEntity.class);  
		// 将xml内容转换为InputMessage对象  
		inputMessageEntity inputMsg = (inputMessageEntity) xstream.fromXML(decryptData.toString());  
		return inputMsg;
		} catch (AesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 调用生成回复消息xml
	 * 本方法加密
	 * @param request
	 * @return
	 * @throws IOException
	 * 实例化对象
	 * 使用构造函数，实例化一个对象，传入公众号第三方平台的token（申请公众号第三方平台时填写的接收消息的校验token）,
	 * 公众号第三方平台的appid, 公众号第三方平台的 EncodingAESKey（申请公众号第三方平台时填写的接收消息的加密symmetric_key）
	 */
	public static String returnEncryptedMessage(HttpServletRequest request,inputMessageEntity replyMessage) throws IOException{
		String encryptedXML="";//返回的加密xml包 
	    String timestamp = request.getParameter("timestamp");//时间戳
	    String nonce = request.getParameter("nonce");//随机数    
		try {
		WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);//实例化对象
		String generateReplyXML=xmlparses.generateReplyXML(replyMessage);//生成回复xml
		System.out.println("回复生成xml包："+generateReplyXML);
		encryptedXML = pc.encryptMsg(generateReplyXML, timestamp, nonce);//加密
		System.out.println("加密后: " + encryptedXML);
		return encryptedXML;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	} 
}
