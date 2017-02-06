package com.lanxi.token;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.util.AppException;
import com.lanxi.util.SignUtil;

public class JWTToken {
	/** 私钥 */
	private static final String priKey;
	/** 公钥 */
	public  static final String pubKey;
	static {
		Map<String, Object> keyPair = SignUtil.getKeyPair();
		priKey = SignUtil.getPrivateKey(keyPair);
		pubKey = SignUtil.getPublicKey(keyPair);
	}
	// ----------------------------协议标准-----------------------------------------------
	/** 头部 */
	private Header head;
	/** 主体 */
	private Payload payload;
	/** 签名 */
	private String sign;
	// -----------------------------自定义-------------------------------------------------

	// ------------------------------method------------------------------------------------
	public JWTToken() {
		head = new Header();
		payload = new Payload();
		head.setAlg("RSA");
		head.setTyp("JWT");
	}
//	public String getIss() {
//		return payload.getIss();
//	}
//
//	public void setIss(String iss) {
//		payload.setIss(iss);
//	}

	public Long getIat() {
		return payload.getIat();
	}
	public void setIat(Long iat) {
		payload.setIat(iat);
	}
	public Long getExp() {
		return payload.getExp();
	}

	public void setExp(Long exp) {
		payload.setExp(exp);
	}

//	public String getAud() {
//		return getAud();
//	}

//	public void setAud(String aud) {
//		payload.setAud(aud);
//	}
//
//	public String getSub() {
//		return getSub();
//	}
//
//	public void setSub(String sub) {
//		payload.setSub(sub);
//	}

	public String getContent() {
		return payload.getContent();
	}

	public void setContent(String content) {
		payload.setContent(content);
	}
	
	public String getSign() {
		sign =new String(SignUtil.base64En(SignUtil.rsaMd5Sign(priKey, (head.toHeader() + "." + payload.toPayload()).getBytes()),"utf-8"));
		return sign;
	}
	
	@Override
	public String toString() {
		return "JWTToken [head=" + head + ", payload=" + payload + ", sign=" + getSign() + "]";
	}

	public String toToken() {
		return head.toHeader() + "." + payload.toPayload() + "." + getSign();
	}

	/**
	 * 从jwt字符串反转到jsttoken对象
	 * 
	 * @param tokenStr
	 * @return 会校验头部和签名 成功返回token 失败返回Null
	 */
	@SuppressWarnings("finally")
	public static JWTToken flipToken(String tokenStr) {
		JWTToken token = new JWTToken();
		try {
			String[] strs = tokenStr.split("\\.");
			String sign = SignUtil.base64En(SignUtil.rsaMd5Sign(priKey, (strs[0] + "." + strs[1]).getBytes()), "utf-8");
			if (!strs[2].equals(sign))
				return null;
			else
				token.sign = sign;
//			System.out.println("验签通过");
			Header head = JSONObject.parseObject(SignUtil.base64De(strs[0], "utf-8"), Header.class);
			if (head == null || !"RSA".equals(head.getAlg()) || !"JWT".equals(head.getTyp()))
				return null;
			else
				token.head = head;
//			System.out.println("头部校验通过");
			String payloadStr = new String(SignUtil.rsaDe(priKey, SignUtil.base64De(strs[1].getBytes("utf-8"))));
			Payload payload = JSONObject.parseObject(payloadStr, Payload.class);
			token.payload = payload;
//			System.out.println("转换完成");
			return token;
		} catch (Exception e) {
			throw new AppException("字符串转令牌时异常", e);
		}finally {
			return token.payload==null?null:token;
		}
	}

	/**
	 * 校验令牌
	 * 
	 * @param token
	 * @return
	 */
	public static boolean verifyToken(JWTToken token) {
		Long now = System.currentTimeMillis();
		if (now<(token.getExp()))
			return true;
		return false;
	}

	/**
	 * 校验令牌
	 * 
	 * @param tokenStr
	 * @return
	 */
	public static boolean verifyToken(String tokenStr) {
		JWTToken token = flipToken(tokenStr);
		if (token == null)
			return false;
		return verifyToken(token);
	}
	/**
	 * 校验令牌及其携带的信息
	 * 
	 * @param tokenStr
	 * @param otherInfo
	 * @return
	 */
	public static boolean verifyToken(String tokenStr,String otherInfo){
		JWTToken token = flipToken(tokenStr);
		if (token == null)
			return false;
		if (!token.getContent().equals(otherInfo))
			return false;
		return verifyToken(token);
	}
	/**
	 * 校验token
	 * 若token剩余时间小于20%  重置token有效期(续费)
	 * @param tokenStr
	 * @return
	 */
	public static JWTToken verifyTokenRenew(String tokenStr){
		JWTToken token = flipToken(tokenStr);
		if(token!=null){
			Long iat=token.getIat();
			Long exp=token.getExp();
			Long now=System.currentTimeMillis();
			if((exp-now)/(double)(exp-iat)<0.2){
				token.setIat(now);
				token.setExp(now+(exp-iat));
			}
		}
		return token;
	}
	/**
	 * 校验token及其内容
	 * 若token剩余时间小于20%  重置token有效期(续费)
	 * @param tokenStr
	 * @return
	 */
	public static JWTToken verifyTokenRenew(String tokenStr,String otherInfo){
		JWTToken token=verifyTokenRenew(tokenStr);
		return token==null?null:token.getContent().equals(otherInfo)?token:null;
	}
}
