package com.tomtop.management.export.api.file.implement;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.tomtop.management.common.ExcelUtils;
import com.tomtop.management.export.annotation.ExportFileColumn;
import com.tomtop.management.export.api.IData;
import com.tomtop.management.export.api.IFileExport;

public class ExcelFileExporter implements IFileExport {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	public byte[] exportFile(Class<?> dataClass, List<IData> datas) {
		List<Field> fields = FluentIterable.from(Lists.newArrayList(dataClass.getDeclaredFields()))
				.toSortedList((s1, s2) -> {
					ExportFileColumn exc1 = s1.getAnnotation(ExportFileColumn.class);
					ExportFileColumn exc2 = s2.getAnnotation(ExportFileColumn.class);
					return Integer.compare(exc1.priority(), exc2.priority());
				});
		// 最终要转化为Excel的集合
		ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
		// Excel的标题
		List<String> titleString = new ArrayList<String>();
		ArrayList<Object> title = Lists.newArrayList();
		datas.forEach(p -> {
			fields.forEach(f -> {
				f.setAccessible(true);
				String columntitle = f.getAnnotation(ExportFileColumn.class).name();
				if (!titleString.contains(columntitle)) {
					titleString.add(columntitle);
				}
			});
		});
		title = Lists.newArrayList(titleString);
		data.add(title);
		datas.forEach(p -> {
			ArrayList<Object> row = new ArrayList<Object>();
			fields.forEach(f -> {
				f.setAccessible(true);
				try {
					Object columnvalue = f.get(p);
					if (null != columnvalue) {
						row.add(columnvalue);
					} else {
						row.add(null);
					}
				} catch (Exception e) {
					logger.error("error,msg：" + e.getMessage());
				}
			});
			data.add(row);
		});
		ExcelUtils excel = new ExcelUtils();
		return excel.arrayToXLSX(data);
	}

}
