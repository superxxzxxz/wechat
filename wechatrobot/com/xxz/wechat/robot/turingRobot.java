package com.xxz.wechat.robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class turingRobot {
	/**
	 * 图灵机器人调用
	 * @param input 问
	 * @param output 答
	 * @return
	 */
	public static JSONObject getTuringRobot(String input,String output){
		 //申请图灵机器人后获取  
        String APIKEY = "88f803e2bebc47c9add4a59298b98c14";  
        try {
	        String INFO = URLEncoder.encode(input, "utf-8");  
	        String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY+ "&info=" + INFO;  
	        URL getUrl = new URL(getURL);  
	        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();  
	        connection.connect();  
	        // 取得输入流，并使用Reader读取  
	        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));  
	        StringBuffer sb = new StringBuffer();  
	        String line = "";  
	        while ((line = reader.readLine()) != null) {  
	            sb.append(line);  
	        }  
	        reader.close();  
	        // 断开连接  
	        connection.disconnect();  
	        JSONObject jsonobject= JSONObject.fromObject(sb.toString());
	        System.out.println("问："+input+"-------答："+jsonobject.get("text"));
	        return jsonobject;
        } catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	/**
	 * 验证图灵机器人
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {  
		 //getTuringRobot("你是哪儿人", "");//测试接口
		
		//冒泡排序测试
		int [] array ={-10,-1,10,2,4,5,43,54,12,23,1,33,0,66,77,89,91,100,101,300};
		for(int i=0;i<array.length;i++){
			for(int j=0;j<array.length-1-i;j++){//for(int j=0;j<array.length;j++)//第二种写法
				if(array[j]>array[j+1]){			//if(array[i]>array[j])
					int one=array[j];				//int one=array[j];
					array[j]=array[j+1];			//array[j]=array[i];
					array[j+1]=one;					//array[i]=one;
				}
			}
		}
		for(int i=0;i<array.length;i++){
			System.out.print(array[i] + " ");
		}
		System.out.println("");
		//list排序
		List list=new ArrayList();
		list.add("-11");
		list.add("3");
		list.add("2");
		list.add("4");
		list.add("6");
		list.add("5");
		Collections.sort(list);
		System.out.println(list);
    }  
}
