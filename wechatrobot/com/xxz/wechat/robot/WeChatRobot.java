package com.xxz.wechat.robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.xxz.wechat.entity.inputMessageEntity;
import com.xxz.wechat.utils.SHA1orMD5;
import com.xxz.wechat.utils.XmlParse;
public class WeChatRobot extends HttpServlet {
	//xml解析类
	private static XmlParse xmlparse=new XmlParse();
	
	//SHA1或MD5加密类
	private static SHA1orMD5 sha1=new SHA1orMD5();
	
	//服务器验证标识
	//private static boolean serverId=false;
	
	//消息实体类
	private static inputMessageEntity msge=new inputMessageEntity();
	/**
	 * Constructor of the object.
	 */
	public WeChatRobot() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * 微信服务器验证
	 * 开发者提交信息后，微信服务器将发送GET请求到填写的服务器地址URL上，GET请求携带参数如下表所示：
	 * signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
	 * timestamp	时间戳
	 * nonce	           随机数
	 * echostr	           随机字符串
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String signature =  request.getParameter("signature");//微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
	    String timestamp =  request.getParameter("timestamp");//时间戳
	    String nonce =  request.getParameter("nonce");//随机数
	    String echostr =  request.getParameter("echostr");//随机字符串
	    String token = "XXZ654962327ZXCVBNM"; // 这里填写自己的 token(微信配置)
	    List<String> list = new ArrayList<String>();
	    list.add(nonce);
	    list.add(token);
	    list.add(timestamp);
	    //将token、timestamp、nonce三个参数进行字典序排序
	    Collections.sort(list);
	    //将三个参数字符串拼接成一个字符串进行sha1加密
	    String hash = sha1.encode(list.get(0)+list.get(1)+list.get(2), "SHA-1");
	    //开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
	    if(signature.equals(hash)){ // 验证下签名是否正确
	        response.getWriter().println(echostr);
	    }else{
	        response.getWriter().println("");
	    }
	    System.out.println("获得加密后的字符串可与signature对比成功");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			String responseMsg="";//回复
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		    String getOrPost=request.getMethod();//提交方式，微信信息类为POST
		    if(getOrPost.equalsIgnoreCase("POST")){
		        	inputMessageEntity inputMsg= (inputMessageEntity) this.xmlparse.obtainXMLPackageAndXmlParse(request);
		        	 // 文本消息  
		            responseMsg= xmlparse.returnEncryptedMessage(request, inputMsg);
		            response.getWriter().write(responseMsg.toString());
		    }else{
		    	response.getWriter().println("");
		    }
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	/** 
     * 提取出xml数据包中的加密消息 
     * @param xmltext 待提取的xml字符串 
     * @return 提取出的加密消息字符串 
     * @throws AesException  
     */  
    /*public static Object[] extract(String xmltext){  
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
            //throw new AesException(AesException.ParseXmlError);  
        }
		return null;  
    }  */
  
	/*public  String getHash(String source, String hashType) {
	    StringBuilder sb = new StringBuilder();
	    MessageDigest md5;
	    try {
	        md5 = MessageDigest.getInstance(hashType);
	        md5.update(source.getBytes());
	        for (byte b : md5.digest()) {
	            sb.append(String.format("%02x", b));
	        }
	        return sb.toString();
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return null;
	}*/
	
	/*public String formatResponseMsg(String content,Map map){
	    String responseMsg = "<xml>"
	            + "<ToUserName><![CDATA[%1$s]]></ToUserName>"
	            + "<FromUserName><![CDATA[%2$s]]></FromUserName>"
	            + "<CreateTime>%3$s</CreateTime>"
	            + "<MsgType><![CDATA[%4$s]]></MsgType>"
	            + "<Content><![CDATA[%5$s]]></Content>"
	            + "<MsgId>%6$s</MsgId>"
	            + "</xml>";
	    return String.format(responseMsg, map.get("FromUserName"),map.get("ToUserName"),map.get("CreateTime"),map.get("MsgType"),content,map.get("MsgId"));
	}*/
}
