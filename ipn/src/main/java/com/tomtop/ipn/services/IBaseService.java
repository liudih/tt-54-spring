package com.tomtop.ipn.services;

import entity.payment.SystemParameter;

/**
 * base数据服务
 * 
 * @author lijun
 *
 */
public interface IBaseService {

	public double getRate(String ccy);

	/**
	 * 币种之间的转换
	 *
	 * @param money
	 *            面值
	 * @param originalCCY
	 *            原始币种
	 * @param targetCCY
	 *            目标币种
	 * @return 返回计算后原始数值
	 * @author lijun
	 */
	public double exchange(double money, String originalCCY, String targetCCY);

	/**
	 * 获取系统参数
	 * 
	 * @param key
	 * @return
	 */
	public SystemParameter getSysPara(String key, int siteId);
}
