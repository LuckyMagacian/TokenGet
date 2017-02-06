package com.lanxi.token;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.util.AppException;
import com.lanxi.util.SignUtil;

/**
 * 自包含令牌
 * @author 1
 *步骤
 *	对象->json->负载串->签名->token
 */
public class SecretToken extends Token {
	/**哈希码*/
	private Integer hash;
	/**负载串*/
	private String  payStr;
	/**私钥*/
	private static final String priKey;
	/**公钥*/
	public  static final String pubKey;
	/**生成密钥对*/
	static{
		Map<String, Object> map=SignUtil.getKeyPair();
		priKey=SignUtil.getPrivateKey(map);
		pubKey=SignUtil.getPublicKey(map);
	}
	/**自定义的信息*/
	private String otherInfo;
//	/**签名*/
//	private String sign;
	
	public String getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
	
//	private void setSign(String sign){
//		this.sign=sign;
//	}
	
	public String sign() {
		return SignUtil.rsaMd5Sign(priKey, toPayload().getBytes());
	}
	@Override
	public String toString() {
		return toJson();
	}
	/**
	 * 获取json串
	 * @return
	 */
	public String toJson(){
		return JSONObject.toJSONString(this);
	}
	/**
	 * 获取负载串
	 * @return
	 */
	public String toPayload(){
		if(hash==null||hash!=this.hashCode()){
			hash=this.hashCode();
			payStr=new String(SignUtil.base64En(SignUtil.rsaEn(pubKey, toJson().getBytes())));
		}
		return payStr;
	}
	/**
	 * 获取token串
	 */
	public String toToken(){
		String str=toPayload()+"."+sign();
		byte[] bytes=SignUtil.desEn(priKey, str.getBytes());
		return new String(SignUtil.base64En(bytes));
	}
	/**
	 * 将token串解析成tokenStr对象
	 * @param tokenstr
	 * @return
	 */
	@SuppressWarnings("finally")
	public static SecretToken flipToken(String tokenstr){
		SecretToken token=null;
		try{
			byte[] bytes=SignUtil.base64De(tokenstr.getBytes("utf-8"));
			byte[] bytes2=SignUtil.desDe(priKey,bytes);
			String str=new String(bytes2);
			String[] strs=str.split("\\.");
			String sign=SignUtil.rsaMd5Sign(priKey,strs[0].getBytes());
			if(!sign.equals(strs[1]))
				return null;
			String jStr=new String(SignUtil.rsaDe(priKey,SignUtil.base64De(strs[0].getBytes())));
			token=JSONObject.parseObject(jStr, SecretToken.class);
			return token;
		}catch (Exception e) {
			throw new AppException("token串解析异常",e);
		}finally {
			return token;
		}
	}
	/**
	 * 校验令牌
	 * 
	 * @param token
	 * @return
	 */
	public static boolean verifyToken(SecretToken token) {
		Long now = System.currentTimeMillis();
		if (now<(token.getValidTo()))
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
		SecretToken token = flipToken(tokenStr);
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
		SecretToken token = flipToken(tokenStr);
		if (token == null)
			return false;
		if (!token.getOtherInfo().equals(otherInfo))
			return false;
		return verifyToken(token);
	}
	/**
	 * 校验token
	 * 若token剩余时间小于20%  重置token有效期(续费)
	 * @param tokenStr
	 * @return
	 */
	public static SecretToken verifyTokenRenew(String tokenStr){
		SecretToken token = flipToken(tokenStr);
		if(token!=null){
			Long iat=token.getValidFrom();
			Long exp=token.getValidTo();
			Long now=System.currentTimeMillis();
			if((exp-now)/(double)(exp-iat)<0.2){
				token.setValidFrom(now);
				token.setValidTo(now+(exp-iat));
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
	public static SecretToken verifyTokenRenew(String tokenStr,String otherInfo){
		SecretToken token=verifyTokenRenew(tokenStr);
		return token==null?null:token.getOtherInfo().equals(otherInfo)?token:null;
	}
}
