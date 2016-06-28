package com.tomtop.management.export.api.file.implement;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.tomtop.management.export.annotation.ExportFileColumn;
import com.tomtop.management.export.api.IData;
import com.tomtop.management.export.api.IFileExport;

public class XmlFileExporter implements IFileExport {
	// private final Logger logger =
	// org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	public byte[] exportFile(Class<?> dataClass, List<IData> datas) throws IOException {
		List<Field> fields = FluentIterable.from(Lists.newArrayList(dataClass.getDeclaredFields()))
				.toSortedList((s1, s2) -> {
					ExportFileColumn exc1 = s1.getAnnotation(ExportFileColumn.class);
					ExportFileColumn exc2 = s2.getAnnotation(ExportFileColumn.class);
					return Integer.compare(exc1.priority(), exc2.priority());
				});
		String root = dataClass.getAnnotation(ExportFileColumn.class).name();
		Document document = DocumentHelper.createDocument();
		Element rootNode = document.addElement(root);// 创建根节点
		List<String> titleString = new ArrayList<String>();
		datas.forEach(p -> {
			fields.forEach(f -> {
				f.setAccessible(true);
				// 获取非list的值
				if (!f.getType().isAssignableFrom(List.class)) {
					String columntitle = f.getAnnotation(ExportFileColumn.class).name();
					try {
						if (!titleString.contains(columntitle)) {
							titleString.add(columntitle);
							Object columnvalue = f.get(p);
							Element childNode = rootNode.addElement(columntitle);
							if (null != columnvalue) {
								childNode.setText(columnvalue.toString());
							} else {
								childNode.setText("");
							}
						}

					} catch (Exception e) {
						// logger.error("error,msg：" + e.getMessage());
						e.printStackTrace();
					}
				} else {
					try {
						Element childElement = rootNode.addElement(f.getAnnotation(ExportFileColumn.class).name());
						generationList(f, p, childElement);
					} catch (Exception e) {
						// logger.error("error,msg：" + e.getMessage());
						e.printStackTrace();
					}
				}

			});
		});
		return document.asXML().getBytes();

	}

	private static void generationList(Field f, Object obj, Element childElement)
			throws IllegalArgumentException, IllegalAccessException {
		if (f.getType().isAssignableFrom(List.class) == false) {
			return;
		}
		f.setAccessible(true);
		Type fc = f.getGenericType();
		if (fc instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) fc;
			Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];

			List alist = (List) f.get(obj);// 取list的值
			if (null == alist || alist.size() == 0) {
				return;
			}
			for (Object o : alist) {
				String item = genericClazz.getAnnotation(ExportFileColumn.class).item();
				if (item.equals("t")) {
					getAttrElement(genericClazz, o, childElement);
				} else {
					Element telement = childElement
							.addElement(genericClazz.getAnnotation(ExportFileColumn.class).name());
					for (Field ff : genericClazz.getDeclaredFields()) {
						ff.setAccessible(true);
						Element tcelement = telement.addElement(ff.getAnnotation(ExportFileColumn.class).name());
						String attr = ff.getAnnotation(ExportFileColumn.class).type();
						String value = "";
						if (null != ff.get(o)) {
							value = ff.get(o).toString();
						}
						/*if (attr.equals("attr")) {
							tcelement.addAttribute(ff.getAnnotation(ExportFileColumn.class).name(), value);
						}*/
						tcelement.addText(value);
					}
				}
			}
		}
	}

	private static void getAttrElement(Class<?> genericClazz, Object o, Element childElement)
			throws IllegalArgumentException, IllegalAccessException {
		//Element telement = childElement.addElement(genericClazz.getAnnotation(ExportFileColumn.class).name());
		Element tcelement = null;
		for (Field ff : genericClazz.getDeclaredFields()) {
			ff.setAccessible(true);
			String attr = ff.getAnnotation(ExportFileColumn.class).type();
			String value = "";
			if (null != ff.get(o)) {
				value = ff.get(o).toString();
			}
			if (attr.equals("label")) {
				tcelement = childElement.addElement(ff.getAnnotation(ExportFileColumn.class).name());
				tcelement.addText(value);
			}else{
				tcelement.addAttribute(ff.getAnnotation(ExportFileColumn.class).name(), value);
			}
		}

	}

}
