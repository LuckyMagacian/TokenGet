package com.lanxi.token;
/**
 * 证书
 * @author 1
 *
 */
public class Certificate extends Token {
	/** 生成时间 */
	private Long validFrom;
	/** 过期时间 */
	private Long validTo;
	/** 发布者 */
	private String issuer;
	/** 公钥 */
	private String publicKey;
	/** 请求者 */
	private String subject;
	/** 指纹 */
	private String thumbprint;
	/** 签名 */
	private String sign;
	/** 证书算法 */
	private String algorithm;
	/** 签名算法 */
	private String signAlgotithm;
	/** 指纹算法 */
	private String thumbprintAlgorithm;

	public Long getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Long validFrom) {
		this.validFrom = validFrom;
	}

	public Long getValidTo() {
		return validTo;
	}

	public void setValidTo(Long validTo) {
		this.validTo = validTo;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getThumbprint() {
		return thumbprint;
	}

	public void setThumbprint(String thumbprint) {
		this.thumbprint = thumbprint;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getSignAlgotithm() {
		return signAlgotithm;
	}

	public void setSignAlgotithm(String signAlgotithm) {
		this.signAlgotithm = signAlgotithm;
	}

	public String getThumbprintAlgorithm() {
		return thumbprintAlgorithm;
	}

	public void setThumbprintAlgorithm(String thumbprintAlgorithm) {
		this.thumbprintAlgorithm = thumbprintAlgorithm;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "Certificate [validFrom=" + validFrom + ", validTo=" + validTo + ", issuer=" + issuer + ", publicKey="
				+ publicKey + ", subject=" + subject + ", thumbprint=" + thumbprint + ", sign=" + sign + ", algorithm="
				+ algorithm + ", signAlgotithm=" + signAlgotithm + ", thumbprintAlgorithm=" + thumbprintAlgorithm + "]";
	}

	@Override
	public String toToken() {
		// TODO Auto-generated method stub
		return null;
	}
}
