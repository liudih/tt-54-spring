package com.tomtop.management.export.api;

import java.io.IOException;
import java.util.List;

/**
 * 
 * @ClassName: IFileExport
 * @Description: 不同文件格式的导出接口
 * @author liuxin
 * @date 2016年3月4日
 *
 */
public interface IFileExport {
	public byte[] exportFile(Class<?> dataClass, List<IData> datas) throws IOException;
}
