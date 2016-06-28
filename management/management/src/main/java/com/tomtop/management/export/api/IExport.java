package com.tomtop.management.export.api;

import java.util.List;

import com.tomtop.management.export.data.model.BaseData;

/**
 * 
 * @ClassName: IExport
 * @Description: 导出操作
 * @author liuxin
 * @date 2016年3月4日
 *
 */
public abstract class IExport {

	public IDataConverter iDataConverter;
	public IFileExport iFileExport;

	public IDataConverter getiDataConverter() {
		return iDataConverter;
	}

	public void setiDataConverter(IDataConverter iDataConverter) {
		this.iDataConverter = iDataConverter;
	}

	public IFileExport getiFileExport() {
		return iFileExport;
	}

	public void setiFileExport(IFileExport iFileExport) {
		this.iFileExport = iFileExport;
	}

	public abstract byte[] getExporter(List<BaseData> datas) throws Exception;
}
