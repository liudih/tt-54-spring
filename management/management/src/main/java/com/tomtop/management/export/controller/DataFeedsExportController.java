package com.tomtop.management.export.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.base.model.Storage;
import com.tomtop.management.ebean.manage.model.Country;
import com.tomtop.management.ebean.manage.model.Currency;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.export.api.IExport;
import com.tomtop.management.export.api.data.implement.AdmitadDataConverter;
import com.tomtop.management.export.api.data.implement.ClixgaloreDataConverter;
import com.tomtop.management.export.api.data.implement.FaceBookDataConverter;
import com.tomtop.management.export.api.data.implement.GoogleDataConverter;
import com.tomtop.management.export.api.data.implement.LinkShareDataConverter;
import com.tomtop.management.export.api.data.implement.ShareSaleDataConverter;
import com.tomtop.management.export.api.data.implement.TradetrackerDataConverter;
import com.tomtop.management.export.api.data.implement.WebgainsDataConverter;
import com.tomtop.management.export.api.file.implement.CsvFileExporter;
import com.tomtop.management.export.api.file.implement.ExcelFileExporter;
import com.tomtop.management.export.api.file.implement.ExportImplement;
import com.tomtop.management.export.api.file.implement.XmlFileExporter;
import com.tomtop.management.export.data.model.BaseData;
import com.tomtop.management.export.service.DataFeedsExportService;
import com.tomtop.management.services.CategoryService;
import com.tomtop.management.services.CommonService;
import com.tomtop.management.services.ProductService;

@Controller
@RequestMapping("/market/datafeed")
public class DataFeedsExportController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	CommonService commonService;

	@Autowired
	DataFeedsExportService exportService;

	@Autowired
	ProductService pService;
	
	@Autowired
	ExportImplement export;

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/**
	 * 
	 * @Title: view
	 * @Description: 进入主界面
	 * @param @param
	 *            model
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2016年3月14日
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Map<String, Object> model) {
		List<Category> rootCategoryList = categoryService.getCategoryByLevel(1);
		List<Currency> currencies = commonService.getAllCurrency();
		List<Language> languageList = commonService.getAllLanguage();
		List<Country> countries = commonService.getAllCountry();
		List<Storage> storages=commonService.getAllStorage();
		model.put("rootCategoryList", rootCategoryList);
		model.put("currencies", currencies);
		model.put("languageList", languageList);
		model.put("section", "market/data_feeds_export.ftl");
		model.put("countries", countries);
		model.put("storages", storages);
		return "index";
	}

	/**
	 * 
	 * @Title: exportFile
	 * @Description: 数据导出
	 * @param @param
	 *            bData
	 * @param @param
	 *            res
	 * @param @throws
	 *            IOException 参数
	 * @return void 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2016年3月14日
	 */
	@RequestMapping("/export")
	public void exportFile(BaseData bData, HttpServletResponse res, HttpServletRequest request) throws IOException {
		String path = request.getSession().getServletContext().getRealPath("/");
		File f = new File(path + "datafeeds/");
		if (f.exists() == false) {
			f.mkdirs();
		}
		path = f.getPath() + File.separator;
		System.out.println(path);
		BufferedOutputStream bos = null;

		List<BaseData> bdatas = exportService.getData(bData.getCategoryId(), bData.getLanguage_id(),
				bData.getCurrency_code(), 20, bData.getIsExportFreight(), bData.getStorageId(), bData.getCountry());

		if (bData.getPlat_form().equals("admitad")) {
			export.setiDataConverter(new AdmitadDataConverter());
		} else if (bData.getPlat_form().equals("google")) {
			export.setiDataConverter(new GoogleDataConverter());
		} else if (bData.getPlat_form().equals("clixgalore")) {
			export.setiDataConverter(new ClixgaloreDataConverter());
		} else if (bData.getPlat_form().equals("facebook")) {
			export.setiDataConverter(new FaceBookDataConverter());
		} else if (bData.getPlat_form().equals("webgains")) {
			export.setiDataConverter(new WebgainsDataConverter());
		} else if (bData.getPlat_form().equals("shareasale")) {
			export.setiDataConverter(new ShareSaleDataConverter());
		} else if (bData.getPlat_form().equals("linkshare")) {
			export.setiDataConverter(new LinkShareDataConverter());
		} else if (bData.getPlat_form().equals("tradetracker")) {
			export.setiDataConverter(new TradetrackerDataConverter());
		}
		Category category = Category.db().find(Category.class).where().eq("iwebsiteid", pService.getCurrentSiteId())
				.eq("icategoryid", bData.getCategoryId()).findUnique();
		Language language = Language.db().find(Language.class, bData.getLanguage_id());
		String fileName = bData.getPlat_form() + "-" + category.getCpath() + "-" + language.getCode();
		if (bData.getFile_type().equals("xml")) {
			export.setiFileExport(new XmlFileExporter());
			fileName += ".xml";
		} else if (bData.getFile_type().equals("csv")) {
			export.setiFileExport(new CsvFileExporter());
			fileName += ".csv";
		} else if (bData.getFile_type().equals("excel")) {
			export.setiFileExport(new ExcelFileExporter());
			fileName += ".xlsx";
		}
		byte[] bytes = null;
		try {
			bytes = export.getExporter(bdatas);
		} catch (Exception e) {
			logger.error("export file error: ", e);
		}
		bos = new BufferedOutputStream(new FileOutputStream(path + fileName));
		bos.write(bytes);
		bos.flush();
		bos.close();
		File file = exportService.craeteZipPath(path + fileName);
		if (!file.exists()) {
			file.mkdirs();
		}
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] bt = new byte[1024];
		int n;
		while ((n = fis.read(bt)) != -1) {
			baos.write(bt, 0, n);
		}
		fis.close();
		baos.close();
		byte[] b = baos.toByteArray();
		fileName = bData.getPlat_form() + "-" + category.getCpath() + "-" + language.getCode() + ".zip";
		res.reset();
		res.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		res.setContentType("application/octet-stream; charset=utf-8");
		ServletOutputStream os = res.getOutputStream();
		os.write(b);
		os.flush();
		os.close();
		exportService.deleteExcelPath(new File(path + fileName));
	}

	@RequestMapping("/list")
	@ResponseBody
	public Page<BaseData> getCliPage(int pageNo, int pageLimit, int categoryId) throws IOException {
		Page<BaseData> pagel = exportService.getFilePage(pageNo, pageLimit, categoryId);
		return pagel;
	}
}
