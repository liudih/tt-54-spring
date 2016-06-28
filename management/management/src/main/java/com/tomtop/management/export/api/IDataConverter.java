package com.tomtop.management.export.api;

import java.util.List;

import com.tomtop.management.config.GlobalParameter;
import com.tomtop.management.export.data.model.BaseData;

/**
 * 
    * @ClassName: IDataConverter
    * @Description: 不同平台导出数据接口
    * @author liuxin
    * @date 2016年3月4日
    *
 */
public interface IDataConverter {
	/**
	    * @Title: getDataType
	    * @Description: 获取数据的类型
	    * @param @return   
	    * @return Class<?>   
	    * @throws
	 * @author liuxin
	    * @date 2016年3月5日
	 */
	public Class<?> getDataType();
	/**
	    * @param globalParameter 
	 * @Title: getDatas
	    * @Description: 获取数据
	    * @param @return    
	    * @return List<IData>
	    * @throws
	 * @author liuxin
	    * @date 2016年3月5日
	 */
	public List<IData> getDatas(List<BaseData> datas, GlobalParameter globalParameter);
}
