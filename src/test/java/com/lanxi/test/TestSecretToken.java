package com.lanxi.test;

import java.util.Map;

import org.junit.Test;

import com.lanxi.token.SecretToken;
import com.lanxi.util.SignUtil;

public class TestSecretToken {

	@Test
	public void testToken(){
		SecretToken token=new SecretToken();
		token.setOtherInfo("testToken");
		token.setValidTo(token.getValidFrom()+7200000);
		System.out.println(token.toPayload().length());
		System.out.println(token.sign().length());
		System.out.println(token.toToken().length());
	}
	@Test
	public void testDes(){
		Map<String, Object> map=SignUtil.getKeyPair();
		String priKey=SignUtil.getPrivateKey(map);
		String pubKey=SignUtil.getPublicKey(map);
		String str="utf-8";
		String enStr=new String(SignUtil.base64En(SignUtil.desEn(priKey, str.getBytes())));
		System.out.println(enStr);
		String deStr=new String(SignUtil.desDe(priKey,SignUtil.base64De(enStr.getBytes())));
		System.out.println(deStr);
	}
	@Test
	public void testRsaMd5(){
		Map<String, Object> map=SignUtil.getKeyPair();
		String priKey=SignUtil.getPrivateKey(map);
		String pubKey=SignUtil.getPublicKey(map);
		String str="utf-8";
		System.out.println(SignUtil.rsaMd5Sign(priKey, str.getBytes()));
		System.out.println(SignUtil.rsaMd5Sign(priKey, str.getBytes()));
		System.out.println(SignUtil.rsaMd5Sign(priKey, str.getBytes()));
		System.out.println(SignUtil.rsaMd5Sign(priKey, str.getBytes()));
		System.out.println(SignUtil.rsaMd5Sign(priKey, str.getBytes()));
	}
	@Test
	public void testChar(){
		Map<String, Object> map=SignUtil.getKeyPair();
		String priKey=SignUtil.getPrivateKey(map);
		String pubKey=SignUtil.getPublicKey(map);
		String str="utf-8";
		byte[] bytes=SignUtil.rsaEn(pubKey, str.getBytes());
		System.out.println(bytes.length);
		String strEn=new String(bytes); 
		System.out.println(strEn);
		System.out.println(strEn.getBytes().length);
		System.out.println(SignUtil.base64En(bytes).length);
		System.out.println(SignUtil.md5LowerCase(str,"utf-8").length());
	}
}
