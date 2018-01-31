package com.xxz.wechat.entity;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml") 
public class inputMessageEntity implements Serializable  {
	 /** 
     *  
     */  
    private static final long serialVersionUID = 1L;  
    @XStreamAlias("ToUserName")  
    private String ToUserName;	//开发者微信号
    
    @XStreamAlias("FromUserName")  
    private String FromUserName;	//发送方帐号（一个OpenID）
    
    @XStreamAlias("CreateTime")  
    private Long CreateTime;	//消息创建时间 （整型）
    
    @XStreamAlias("MsgType")  
    private String MsgType = "text";	//消息类型
    
    @XStreamAlias("MsgId")  
    private Long MsgId;	//消息id，64位整型  
    // 1文本消息  
    @XStreamAlias("Content")  
    private String Content;	//文本消息内容
    // 2图片消息  
    @XStreamAlias("PicUrl")  
    private String PicUrl;  //图片链接（由系统生成）
    
    // 3语音信息  
    @XStreamAlias("MediaId")  
    private String MediaId;  //语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
    
    @XStreamAlias("Format")  //语音格式，如amr，speex等
    private String Format;  
    
   /*请注意，开通语音识别后，用户每次发送语音给公众号时，
             微信会在推送的语音消息XML数据包中，增加一个Recongnition字段
           （注：由于客户端缓存，开发者开启或者关闭语音识别功能，对新关注者立刻生效，
             对已关注用户需要24小时生效。开发者可以重新关注此帐号进行测试）。 */
    @XStreamAlias("Recognition")  
    private String Recognition;	//语音识别结果，UTF8编码
    
    //4视频消息
    @XStreamAlias("ThumbMediaId")  
    private String ThumbMediaId;//视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据
    
    
    // 5地理位置消息  
    @XStreamAlias("LocationX")  
    private String LocationX;  //地理位置维度
    
    @XStreamAlias("LocationY")  
    private String LocationY;  //地理位置经度
    
    @XStreamAlias("Scale")  
    private Long Scale;  //地图缩放大小
    
    @XStreamAlias("Label")  
    private String Label;  //地理位置信息
    // 6链接消息  
    @XStreamAlias("Title")  
    private String Title;  //消息标题
    
    @XStreamAlias("Description")  
    private String Description;  //消息描述
    
    @XStreamAlias("URL")  
    private String URL;  //消息链接
  
    // 7事件  
    @XStreamAlias("Event")  
    private String Event;  
    
    @XStreamAlias("EventKey")  
    private String EventKey;  
    
    @XStreamAlias("Ticket")  
    private String Ticket;
    
	@XStreamAlias("Encrypt")  
    private String Encrypt;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public Long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public Long getMsgId() {
		return MsgId;
	}

	public void setMsgId(Long msgId) {
		MsgId = msgId;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

	public String getRecognition() {
		return Recognition;
	}

	public void setRecognition(String recognition) {
		Recognition = recognition;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

	public String getLocationX() {
		return LocationX;
	}

	public void setLocationX(String locationX) {
		LocationX = locationX;
	}

	public String getLocationY() {
		return LocationY;
	}

	public void setLocationY(String locationY) {
		LocationY = locationY;
	}

	public Long getScale() {
		return Scale;
	}

	public void setScale(Long scale) {
		Scale = scale;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		Label = label;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String ticket) {
		Ticket = ticket;
	}  
	
	public String getEncrypt() {
		return Encrypt;
	}

	public void setEncrypt(String encrypt) {
		Encrypt = encrypt;
	}
}
