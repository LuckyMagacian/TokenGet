package com.lanxi.token;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.util.SignUtil;

public class Payload {
	private Integer hash;
//	/** 发放者 */
//	private String iss;
	/** 生成时间 */
	private Long iat;
	/** 过期时间 */
	private Long exp;
//	/** 接收方 */
//	private String aud;
//	/** 用户 */
//	private String sub;
	/** 其他信息 */
	private String content;
	/**负载串*/
	private String payStr;
	
	public Payload(){
		iat=System.currentTimeMillis();
//		iss="杭州蓝喜信息技术有限公司";
	}
	
//	public String getIss() {
//		return iss;
//	}
//
//	public void setIss(String iss) {
//		this.iss = iss;
//	}
	
	public Long getIat() {
		return iat;
	}

	public void setIat(Long iat) {
		this.iat = iat;
	}

	public Long getExp() {
		return exp;
	}

	public void setExp(Long exp) {
		this.exp = exp;
	}

//	public String getAud() {
//		return aud;
//	}
//
//	public void setAud(String aud) {
//		this.aud = aud;
//	}
//
//	public String getSub() {
//		return sub;
//	}
//
//	public void setSub(String sub) {
//		this.sub = sub;
//	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String toJson() {
		return JSONObject.toJSONString(this);
	}

	@Override
	public String toString() {
		return toJson();
	}

	public String toPayload() {
		if(hash==null||hash.intValue()!=this.hash){
			hash=this.hashCode();
			payStr=new String(SignUtil.base64En(SignUtil.rsaEn(JWTToken.pubKey, toJson().getBytes())));
		}
		return payStr;
	}
}