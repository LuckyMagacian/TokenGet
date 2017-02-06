package com.lanxi.test;

import org.junit.Test;

import com.lanxi.token.EasyToken;

public class TestEasyToken {

	@Test
	public void testToken(){
		EasyToken token=new EasyToken();
		token.setInfo("testToken9两只老虎料之老虎两只老虎999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
		token.setValidTo(token.getValidFrom()+7200000);
		System.out.println(token.getInfo().length());
		System.out.println(token.getInfo().getBytes().length);
		System.out.println(token.toJson().getBytes().length);
		System.out.println(token.toPayload().length());
		System.out.println(token.sign().length());
		System.out.println(token.toToken().length());
		System.out.println(EasyToken.flipToken(token.toToken()));
	}
}
