package com.tomtop.management.export.api.file.implement;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tomtop.management.config.GlobalParameter;
import com.tomtop.management.export.api.IData;
import com.tomtop.management.export.api.IExport;
import com.tomtop.management.export.data.model.BaseData;
@Component
public class ExportImplement extends IExport {
	
	@Autowired
	GlobalParameter globalParameter;
	
	private final Logger logger = org.slf4j.LoggerFactory.getLogger(this
			.getClass());

	@Override
	public byte[] getExporter(List<BaseData> datas) throws Exception {
		if (this.getiDataConverter() == null) {
			throw new Exception(" IDataConverter is null! ");
		}
		if (this.getiFileExport() == null) {
			throw new Exception(" IFileExport is null! ");
		}
		logger.debug("export Type --{}", this.getiDataConverter().getDataType());
		List<IData> lDatas = this.getiDataConverter().getDatas(datas, globalParameter);
		return this.getiFileExport().exportFile(
				this.getiDataConverter().getDataType(), lDatas);
	}

}
