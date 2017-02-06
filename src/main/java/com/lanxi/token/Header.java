package com.lanxi.token;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSONObject;

/**
 * jwt令牌头
 * 
 * @author 1
 *
 */
public class Header {
	/** 类型 */
	private String typ;
	/** 算法 */
	private String alg;

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getAlg() {
		return alg;
	}

	public void setAlg(String alg) {
		this.alg = alg;
	}

	public String toJson() {
		return JSONObject.toJSONString(this);
	}

	@Override
	public String toString() {
		return toJson();
	}

	/**
	 * 将头部转换为jwt格式 base64处理json
	 * 
	 * @return
	 */
	public String toHeader() {
		try {
			return Base64.encodeBase64String(toJson().getBytes("utf-8"));
		} catch (Exception e) {
			throw new RuntimeException("令牌头编码异常", e);
		}
	}

}
