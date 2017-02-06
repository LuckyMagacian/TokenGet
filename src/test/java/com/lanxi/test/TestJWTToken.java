package com.lanxi.test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;

import org.junit.Test;

import com.lanxi.token.JWTToken;
import com.lanxi.util.SignUtil;

public class TestJWTToken {

	@Test
	public void testJWTToken() throws UnsupportedEncodingException{
		String charset="utf-8";
		
		JWTToken token=new JWTToken();
//		token.setIss("杭州蓝喜信息技术有限公司");	
		token.setExp(System.currentTimeMillis()+7200000);
//		token.setAud("testToken");
		token.setContent("yyj:19931123");
//		token.setSub("yyj");
//		System.err.println("1: "+token.payload.toPayload());
//		System.err.println("2: "+token.payload.toPayload());
//		System.err.println("3: "+token.payload.toPayload());
//		System.err.println("4: "+token.payload.toPayload());
		System.out.println(token.getSign());
		System.out.println(token.toToken());
		
//		
		String tokenStr=token.toToken();
		String[] strs=tokenStr.split("\\.");
		int i=0;
//		for(String each :strs){
//			System.out.println("str"+(i++)+": "+each);
//		}
//		
//		System.out.println(new String(SignUtil.rsaDe(token.priKey,SignUtil.base64De(strs[1].getBytes("utf-8")))));
		System.out.println(JWTToken.verifyToken(tokenStr));
	}
	@Test
	public void testBase() throws Exception{
		String str="utf-8";
//		outLength(str);
		String benStr=SignUtil.base64En(str, str);
//		outLength(benStr);
		String bdeStr=SignUtil.base64De(benStr, str);
//		outLength(bdeStr);
		
		byte[] bytes=SignUtil.rsaEn(JWTToken.pubKey, str.getBytes());
		String rsa=new String(bytes,"utf-8");
		System.out.println("rsa:"+rsa);
		String en=new String(SignUtil.base64En(bytes));

		System.out.println("base64en:"+en);
		String de=SignUtil.base64De(new String(SignUtil.base64En(bytes)),"utf-8");
		System.out.println("base64de:"+de);
		System.out.println(de.equals(rsa));
//		System.out.println(new String(SignUtil.rsaDe(JWTToken.priKey, SignUtil.base64De(en.getBytes()))));
		
		String rsaEnStr=new String(SignUtil.rsaEn(JWTToken.pubKey, str.getBytes()),str);
//		outLength(rsaEnStr);
//		String rsaDeStr=new String(SignUtil.rsaDe(JWTToken.priKey, str,str), str);
//		outLength(rsaDeStr);
	}
	public void outLength(String str) throws UnsupportedEncodingException{
		System.out.println(str.length());
		System.out.println(str.getBytes().length);
		System.out.println(str.getBytes("utf-8").length);
	}
	@Test
	public void testRsa(){
		
		Map<String, Object> keys=SignUtil.getKeyPair();			//密钥对
		String pubKey=SignUtil.getPublicKey(keys);				//公钥
		String priKey=SignUtil.getPrivateKey(keys);				//私钥
		
		String str="utf-8";										//原字符串
		byte[] bytes=str.getBytes();							//原字节
		byte[] enByte=SignUtil.rsaEn(pubKey, str.getBytes());			//rsa加密字节
		String enStr =new String(enByte);						//rsa加密字符串
		
		byte[] baseEnByte=SignUtil.base64En(enByte);			//rsa加密base64加密字节
		String baseEnStr=new String(baseEnByte);				//rsa加密base64字符串
		

		byte[] baseDeByte=SignUtil.base64De(baseEnByte);		//rsa加密字节
		String baseDeStr=new String(baseDeByte);				//rsa加密字符串
		System.out.println(baseDeByte.length);
		byte[] deByte=SignUtil.rsaDe(priKey,baseDeByte);		//原字节
		String deStr=new String(deByte);						//原字符串
		
		System.out.println("原字符串				:"+str);
		System.out.println("原字节				:"+Arrays.asList(bytes));
		System.out.println("rsa加密字节			:"+Arrays.asList(enByte));
		System.out.println("rsa加密字符串			:"+enStr);
		System.out.println("rsa加密base64加密字节	:"+Arrays.asList(baseEnByte));
		System.out.println("rsa加密base64字符串	:"+baseEnStr);
		System.out.println("rsa加密字符串			:"+baseDeStr);
		System.out.println("rsa加密字节			:"+Arrays.asList(baseDeByte));
		System.out.println("原字节(解密)			:"+Arrays.asList(deByte));
		System.out.println("原字符串(解密)			:"+deStr);
	}
	@Test
	public void testRep(){
		Map<String, Object> keys=SignUtil.getKeyPair();			//密钥对
		String pubKey=SignUtil.getPublicKey(keys);				//公钥
		String priKey=SignUtil.getPrivateKey(keys);				//私钥
		String str="utf-8";										//原字符串
		for(int i=0;i<5;i++){
			byte[] bytes=str.getBytes();							//原字节
			byte[] enByte=SignUtil.rsaEn(pubKey, str.getBytes());			//rsa加密字节
			String enStr =new String(enByte);						//rsa加密字符串
			System.out.println(i+":   "+enStr);
			byte[] baseEnByte=SignUtil.base64En(enByte);			//rsa加密base64加密字节
			String baseEnStr=new String(baseEnByte);				//rsa加密base64字符串
			

			byte[] baseDeByte=SignUtil.base64De(baseEnByte);		//rsa加密字节
			String baseDeStr=new String(baseDeByte);				//rsa加密字符串
			System.out.println(baseDeByte.length);
			byte[] deByte=SignUtil.rsaDe(priKey,baseDeByte);		//原字节
			String deStr=new String(deByte);						//原字符串
			System.out.println(i+":   "+deStr);
		}
	}

}
