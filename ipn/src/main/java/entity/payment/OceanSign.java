package entity.payment;

/**
 * 钱海密匙
 * 
 * @author lijun
 *
 */
public class OceanSign {
	// 生成sign前的原始数据
	private final String original;
	// 生成的秘钥
	private final String sign;
	// 生成密匙时错误信息
	private final String error;

	public OceanSign(String original, String sign, String error) {
		this.original = original;
		this.sign = sign;
		this.error = error;
	}

	public String getOriginal() {
		return original;
	}

	public String getSign() {
		return sign;
	}

	public String getError() {
		return error;
	}

}
