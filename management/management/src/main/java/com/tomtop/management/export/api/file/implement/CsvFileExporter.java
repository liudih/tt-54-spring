package com.tomtop.management.export.api.file.implement;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.tomtop.management.export.annotation.ExportFileColumn;
import com.tomtop.management.export.api.IData;
import com.tomtop.management.export.api.IFileExport;

public class CsvFileExporter implements IFileExport {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(this
			.getClass());

	@Override
	public byte[] exportFile(Class<?> dataClass, List<IData> datas)
			throws IOException {
		List<Field> fields = FluentIterable.from(
				Lists.newArrayList(dataClass.getDeclaredFields()))
				.toSortedList(
						(s1, s2) -> {
							ExportFileColumn exc1 = s1
									.getAnnotation(ExportFileColumn.class);
							ExportFileColumn exc2 = s2
									.getAnnotation(ExportFileColumn.class);
							return Integer.compare(exc1.priority(),
									exc2.priority());
						});
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Writer writer = new OutputStreamWriter(outputStream);
		ICsvBeanWriter csvWriter = new CsvBeanWriter(writer,
				CsvPreference.STANDARD_PREFERENCE);
		List<String> titleString = new ArrayList<String>();
		List<CellProcessor> cList = new ArrayList<>();
		try {
			datas.forEach(p -> {
				fields.forEach(f -> {
					f.setAccessible(true);
					String columntitle = f
							.getAnnotation(ExportFileColumn.class).name();
					if (!titleString.contains(columntitle)) {
						cList.add(new NotNull());
						titleString.add(columntitle);
					}
				});
			});

			String[] title = new String[titleString.size()];
			titleString.toArray(title);
			if (null != title && title.length > 0) {
				csvWriter.writeHeader(title);
			}

			for (IData object : datas) {
				// csvWriter.write(object, title, cList.toArray(new
				// CellProcessor[cList.size()]));
				csvWriter.write(object, title);
			}

		} catch (Exception e) {
			logger.error("error,msg：", e);
		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}
		byte[] f = outputStream.toByteArray();
		return f;
	}

}
