package com.tomtop.management.shipping.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.avaje.ebean.PagedList;
import com.tomtop.management.common.DateUtil;
import com.tomtop.management.common.Page;
import com.tomtop.management.common.StringUtil;
import com.tomtop.management.ebean.shipping.model.ShippingDescription;
import com.tomtop.management.ebean.shipping.model.ShippingMethod;
import com.tomtop.management.ebean.shipping.model.ShippingType;
import com.tomtop.management.shipping.values.ShippingDesValue;
import com.tomtop.management.shipping.values.ShippingTypeValue;
import com.tomtop.management.shipping.values.ValidCheckInfo;

@Service
public class ShippingTypeService {

	/**
	 * 
	 * @Title: getShipPage
	 * @Description: 物流管理类型方法
	 * @param @param
	 *            pageNo
	 * @param @param
	 *            pageLimit
	 * @param @param
	 *            shippingType
	 * @param @return
	 *            参数
	 * @return Page<ShippingTypeValue> 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2016年2月23日
	 */
	public Page<ShippingTypeValue> getShipPage(int pageNo, int pageLimit, ShippingType shippingType) {
		String q = "find Site where is_deleted = 0 ";
		if (null != shippingType.getType_name() && !shippingType.getType_name().equals("")) {
			q += "and type_name like '%" + shippingType.getType_name() + "%'";
		}
		Page<ShippingTypeValue> sitePage = new Page<ShippingTypeValue>();

		PagedList<ShippingType> sList = ShippingType.db().createQuery(ShippingType.class, q).order()
				.desc("whenModified").findPagedList(pageNo - 1, pageLimit);
		sList.loadRowCount();
		sitePage.setCount(sList.getTotalRowCount());
		sitePage.setPageNo(pageNo);
		sitePage.setLimit(pageLimit);
		List<ShippingTypeValue> siList = new ArrayList<ShippingTypeValue>();
		for (ShippingType t : sList.getList()) {
			ShippingDescription sd = ShippingDescription.db().find(ShippingDescription.class).where()
					.eq("shipping_type_id", Integer.valueOf(t.getId().intValue())).eq("language_id", 1).findUnique();
			ShippingTypeValue shipping = new ShippingTypeValue();
			try {
				BeanUtils.copyProperties(shipping, t);
				if (null != sd) {
					List<ShippingDesValue> shippingDesValues = new ArrayList<ShippingDesValue>();
					ShippingDesValue shippingDesValue = new ShippingDesValue();
					shippingDesValue.setDescription(sd.getDescription());
					shippingDesValue.setDisplay_name(sd.getDisplay_name());
					shippingDesValue.setLanguage_id(sd.getLanguage_id());
					shippingDesValues.add(shippingDesValue);
					shipping.setSdList(shippingDesValues);
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			siList.add(shipping);
		}
		for (ShippingTypeValue typeValue : siList) {
			typeValue.setCreateTime(DateUtil.dateFormat(typeValue.getWhenCreated()));
			typeValue.setUpdateTime(DateUtil.dateFormat(typeValue.getWhenModified()));
		}
		sitePage.setList(siList);
		return sitePage;
	}

	public ValidCheckInfo addType(ShippingTypeValue shipValue) {
		ValidCheckInfo vci = new ValidCheckInfo();
		vci = validateShippingCode(shipValue);
		if(vci.getStatus() == 1){
			return vci;
		}
		ShippingType shippingType = new ShippingType();
		shippingType.setIs_deleted(0);
		shippingType.setIs_enabled(shipValue.getIs_enabled());
		shippingType.setType_name(shipValue.getType_name());
		shippingType.setShipping_code(shipValue.getShipping_code().toUpperCase());
		shippingType.setShipping_sequence(shipValue.getShipping_sequence());
		ShippingType.db().insert(shippingType);
		shipValue.getSdList().forEach(p -> {
			ShippingType sType = ShippingType.db().find(ShippingType.class).where()
					.eq("type_name", shipValue.getType_name()).findUnique();
			ShippingDescription sDescription = new ShippingDescription();
			sDescription.setDescription(p.getDescription());
			sDescription.setDisplay_name(p.getDisplay_name());
			sDescription.setShipping_type_id(sType.getId().intValue());
			sDescription.setLanguage_id(p.getLanguage_id());
			ShippingDescription.db().insert(sDescription);
		});
		vci.setStatus(0);
		return vci;
	}

	public ValidCheckInfo updateType(ShippingTypeValue shipValue) {
		ValidCheckInfo vci = new ValidCheckInfo();
		vci = validateShippingCode(shipValue);
		if(vci.getStatus() == 1){
			return vci;
		}
		ShippingType shippingType = ShippingType.db().find(ShippingType.class, shipValue.getId());
		shippingType.setIs_enabled(shipValue.getIs_enabled());
		shippingType.setType_name(shipValue.getType_name());
		shippingType.setShipping_code(shipValue.getShipping_code().toUpperCase());
		shippingType.setShipping_sequence(shipValue.getShipping_sequence());
		ShippingType.db().update(shippingType);
		shipValue.getSdList().forEach(p -> {
			ShippingDescription s = ShippingDescription.db().find(ShippingDescription.class).where()
					.eq("shipping_type_id", shipValue.getId()).eq("language_id", p.getLanguage_id()).findUnique();
			s.setDescription(p.getDescription());
			s.setDisplay_name(p.getDisplay_name());
			s.setShipping_type_id(shipValue.getId().intValue());
			s.setLanguage_id(p.getLanguage_id());
			ShippingDescription.db().save(s);
		});
		vci.setStatus(0);
		return vci;
	}

	public int checkTypeName(Long id, String type_name) {
		int result = 0;
		if (id == 0) {
			result = ShippingType.db().find(ShippingType.class).where().eq("type_name", type_name).findRowCount();
		} else {
			List<ShippingType> sType = ShippingType.db().find(ShippingType.class).where().eq("type_name", type_name)
					.findList();
			for (ShippingType s : sType) {
				if (s.getId() != id) {
					result++;
				}
			}
		}

		return result;
	}

	private ValidCheckInfo validateShippingCode(ShippingTypeValue shipValue){
		int count = 0;
		ValidCheckInfo vci = new ValidCheckInfo();
		String shippingCode = shipValue.getShipping_code();
		if (StringUtil.isNotEmpty(shippingCode)) {
			if (shippingCode.indexOf(",") != -1) {
				String[] shippingCodes = shippingCode.split(",");
				for (String sc : shippingCodes) {
					count = ShippingMethod.db().find(ShippingMethod.class).where().eq("ccode", sc.toUpperCase())
							.findRowCount();
					if (count == 0) {
						vci.setStatus(1);
						vci.setType("shippingCode");
						vci.setDesc(sc);
						return vci;
					}
				}
			} else {
				count = ShippingMethod.db().find(ShippingMethod.class).where().eq("ccode", shippingCode.toUpperCase())
						.findRowCount();
				if (count == 0) {
					vci.setStatus(1);
					vci.setType("shippingCode");
					vci.setDesc(shippingCode);
					return vci;
				}
			}
		}
		vci.setStatus(0);
		return vci;
	}
}
