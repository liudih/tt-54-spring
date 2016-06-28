package com.tomtop.product.controllers;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.tomtop.framework.core.utils.Result;
import com.tomtop.interaction.mappers.ProductCollectMapper;
import com.tomtop.interaction.mappers.ProductReviewsMapper;
import com.tomtop.product.common.enums.CommonEntity;
import com.tomtop.product.mappers.CategoryProductRecommendMapper;
import com.tomtop.product.mappers.PrdouctStorageMapper;
import com.tomtop.product.mappers.ProductBaseMapper;
import com.tomtop.product.mappers.ProductEntityMapMapper;
import com.tomtop.product.mappers.ProductImageMapper;
import com.tomtop.product.mappers.ProductLabelMapper;
import com.tomtop.product.mappers.ProductSalePriceMapper;
import com.tomtop.product.mappers.ProductUrlMapper;
import com.tomtop.product.mappers.ProductVideoMapper;
import com.tomtop.product.mappers.ProductViewCountMapper;
import com.tomtop.product.mappers.base.LanguageMapper;
import com.tomtop.product.mappers.base.StorageMapper;
import com.tomtop.product.mappers.category.CategoryMapper;
import com.tomtop.product.mappers.order.OrderMapper;
import com.tomtop.product.mappers.order.ShippingMethodMapper;
import com.tomtop.product.models.dto.ProductBase;
import com.tomtop.product.models.dto.ProductLabel;
import com.tomtop.product.models.dto.ProductSalePrice;
import com.tomtop.product.models.dto.ProductUrl;
import com.tomtop.product.models.dto.ProductViewCountDto;
import com.tomtop.product.models.dto.ReviewCountAndScoreDto;
import com.tomtop.product.models.dto.base.Language;
import com.tomtop.product.models.vo.AttributeItem;
import com.tomtop.product.models.vo.DepotEntity;
import com.tomtop.product.models.vo.MutilLanguage;
import com.tomtop.product.models.vo.ProductEntity;
import com.tomtop.product.models.vo.ProductImageEntity;
import com.tomtop.product.models.vo.ProductTypeEntity;
import com.tomtop.product.models.vo.PromotionPrice;
import com.tomtop.product.models.vo.ReviewStartNumBo;
import com.tomtop.product.models.vo.StartNum;
import com.tomtop.product.models.vo.TagEntity;

/**
 * 运行同步
 * 
 * @author liulj
 *
 */
@RestController
public class Run {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${es_url}")
	private String esUrl;

	@Resource(name = "restTemplate")
	private RestTemplate restTemplate;

	@Value("${page_size}")
	private Integer Page_SIZE;

	@Value("${thread_size}")
	private Integer THREAD_SIZE;

	@Autowired
	private ProductBaseMapper baseMapper;

	@Autowired
	private ProductImageMapper imageMapper;

	@Autowired
	private ProductSalePriceMapper salePriceMapper;

	@Autowired
	private ProductReviewsMapper reviewsMapper;

	@Autowired
	private ProductUrlMapper urlMapper;

	@Autowired
	private ProductLabelMapper lableMapper;

	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private ProductEntityMapMapper entiryMapper;

	@Autowired
	StorageMapper storageMapper;

	@Autowired
	PrdouctStorageMapper productStorageMapper;

	@Autowired
	ProductViewCountMapper viewCountMapper;

	@Autowired
	LanguageMapper languageMapper;

	@Autowired
	OrderMapper orderMapper;

	@Autowired
	ProductCollectMapper collectMapper;

	@Autowired
	ShippingMethodMapper shippingMethodMapper;

	@Autowired
	ProductVideoMapper videoMapper;

	@Autowired
	CategoryProductRecommendMapper categoryProductRecommendService;

	private ExecutorService exector;

	private volatile static long insertIndex = 1;

	private static RunStatus runStatus = RunStatus.NO_RUNIG;

	static Set<Integer> unFinishPage;

	private ObjectMapper jsonMapper = new ObjectMapper();

	HttpHeaders headers = new HttpHeaders();

	static List<String> errorListingIds;
	
	enum ErrorType {
		SENDT, DATA
	}

	private static String currentLisingId;
	private static String errorMsg;
	private static String errorJsonMsg;

	Map<Integer, String> langsMap = new HashMap<Integer, String>();

	@PostConstruct
	public void inint() {
		langsMap = languageMapper
				.getAllLanguage()
				.stream()
				.collect(Collectors.toMap(Language::getIid, Language::getCname));
		exector = Executors.newFixedThreadPool(THREAD_SIZE);
		headers.setContentType(new MediaType("text", "plain", Charset
				.forName("UTF-8")));
	}

	private enum RunStatus {
		NO_RUNIG, ERROR, SUCCESS, RUNING, STOP
	}

	@RequestMapping(value = "/stop", method = RequestMethod.GET)
	public String stop() {
		runStatus = RunStatus.STOP;
		exector.shutdownNow();
		return "已经请求停止，请等待一段时间，刷新es查看是不是还有无添加数据来确认已经停止";
	}

	@RequestMapping(value = "/unFinishPageInfo", method = RequestMethod.GET)
	public String getUnFinishPage() {
		if (unFinishPage == null || unFinishPage.size() <= 0) {
			return "没有未发送完成的页";
		}
		return "未完成页数共：" + unFinishPage.size() + ",分别是："
				+ unFinishPage.toString();
	}
	
	@RequestMapping(value = "/errorlistingIds", method = RequestMethod.GET)
	public String getErrorListingIds() {
		if (errorListingIds == null || errorListingIds.size() <= 0) {
			return "没有未发送完成的页";
		}
		return "listingId出错共：" + errorListingIds.size() + "个,分别是："
		+ errorListingIds.toString();
	}

	@RequestMapping(value = "/batchStatus", method = RequestMethod.GET)
	public String batchStatus() {
		return exector.isTerminated() ? "多线程已执行完成或关闭" : "正在执行任务,或没有开始";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "batchByCreadteDate 方法执行按产品仓颉时间段导入，"
				+ "参数有:start 开始日期,end 结束日期 ，日期格式为yyyy-MM-dd HH:mm:ss ,反回值 {ret:1,data:''},"
				+ "ret是状态 1成功，0失败，data是说明内容</br> batchStatus 查看多线程状态 </br>"
				+ " keepUnFinishPage 运行没有发送完的页 </br>"
				+ " unFinishPageInfo 查看还没有完成的页数 ,只针对batch执行时有效，"
				+ "在stop后查看还有则可继续 运行keepUnFinishPage继续运行没有执行完的页,</br>"
				+ "stop方法停止导入(只针对连续导入),</br> "
				+ "run 运行导入,单线程,</br> batch 批量导入，</br>"
				+ " singleBatch/:page 导入某一页的数据 page参数为页码，页码从1开始，开多个线程，可配置,</br> "
				+ "retry 重试(参数:ignore,是否忽略当前的错误，跳过执行,默认0),</br>"
				+ " keep/:index 继续运行，index是运行的索引,</br> "
				+ "reImport(batch参数标识是否以批量多线程导入默认0) 重新导入，</br>"
				+ " importByListingId/:listingId 导入多个listingId以,隔开.</br>"
				+ " errorlistingIds 出错的listingId集合.</br>"
				+ " reImport-errorlistingIds 重新导入出错的listingId集合.</br>"
				+ " 除了导入单个listing的方法外，都支持ignoreError参数，默认为0,为1时表示忽略所有错误继续运行.";
	}

	@RequestMapping(value = "/reImport-errorlistingIds", method = RequestMethod.GET)
	public String reImportErrorListingIds() throws UnsupportedEncodingException {
		if (errorListingIds == null || errorListingIds.size() <= 0) {
			return "没有出错的listingid";
		}
		if (runStatus == RunStatus.RUNING) {
			return "正在执行导入数据到es，请勿再试执行,或先执行stop,再运行此方法";
		} else if (runStatus == RunStatus.SUCCESS) {
			return "数据分发任务已成功，请先执行stop,再运行此方法";
		}
		runStatus = RunStatus.RUNING;
		this.importByListingId(errorListingIds);
		return "成功分发任务,总数：" + errorListingIds.size() + "条";
	}
	
	@RequestMapping(value = "/importByListingId/{listingId}", method = RequestMethod.GET)
	public String importByListingId(
			@PathVariable(value = "listingId") List<String> listingId)
			throws UnsupportedEncodingException {
		if (listingId != null && listingId.size() > 0) {
			try {
				List<ProductBase> bases = baseMapper
						.getProductByListingIds(listingId);
				if (bases != null && bases.size() > 0) {
					List<ProductEntity> entities = Lists.newArrayList();
					for (ProductBase base : bases) {
						entities.add(getObject(base));
					}
					sendToEs(entities);
					return "成功";
				} else {
					return "没有查到产品信息";
				}
			} catch (Exception e) {
				String error = "发生了错误，发生错误的listingId:" + listingId + ",错误信息:"
						+ e.getMessage() + ",错误的json:" + errorJsonMsg;
				logger.error(error);
				return error;
			}
		} else {
			return "listing id 为空";
		}
	}

	@RequestMapping(value = "/reImport", method = RequestMethod.GET)
	public String reImport(
			@RequestParam(value = "ignoreError", required = false, defaultValue = "0") boolean ignoreError,
			@RequestParam(value = "batch", required = false, defaultValue = "0") boolean batch) {
		if (runStatus == RunStatus.RUNING) {
			return "正在运行，请停止运行后重试";
		}
		runStatus = RunStatus.NO_RUNIG;
		insertIndex = 1;
		if (batch) {
			return batch();
		} else {
			return runImport(ignoreError);
		}
	}

	@RequestMapping(value = "/keep/{index}", method = RequestMethod.GET)
	public String keep(
			@PathVariable("index") Integer index,
			@RequestParam(value = "ignoreError", required = false, defaultValue = "0") boolean ignoreError) {
		if (runStatus == RunStatus.RUNING) {
			return "正在执行重试导入数据到es，请勿再试重试";
		} else if (runStatus == RunStatus.SUCCESS) {
			return "导入数据已成功，如要重新开始，请运行，/reImport";
		}
		runStatus = RunStatus.NO_RUNIG;
		insertIndex = index;
		return runImport(ignoreError);
	}

	@RequestMapping(value = "/retry", method = RequestMethod.GET)
	public String retry(
			@RequestParam(value = "ignore", required = false, defaultValue = "0") boolean ignore,
			@RequestParam(value = "ignoreError", required = false, defaultValue = "0") boolean ignoreError) {
		if (runStatus == RunStatus.RUNING) {
			return "正在执行重试导入数据到es，请勿再试";
		} else if (runStatus == RunStatus.SUCCESS) {
			return "导入数据已成功，如要重新开始，请运行，/reImport";
		}
		if (ignore) {
			logger.info("忽略了当前错误，错误信息======。。" + errorMsg);
			insertIndex++;
		}
		runStatus = RunStatus.NO_RUNIG;
		return runImport(ignoreError);
	}

	public String errorHandle(String msg, ErrorType type) {
		errorMsg = msg;
		runStatus = RunStatus.ERROR;
		String error = "发生了错误，执行数:"
				+ insertIndex
				+ (type == ErrorType.SENDT ? ""
						: (",发生错误的listingId:" + currentLisingId))
				+ ",如排除了错误，请重新继续执行(访问/retry),错误信息:" + errorMsg;
		// + (type == ErrorType.SENDT ? ",错误的json:" + errorJsonMsg : "");
		logger.error(error);
		return error;
	}

	@RequestMapping(value = "/run", method = RequestMethod.GET)
	public String runImport(
			@RequestParam(value = "ignoreError", required = false, defaultValue = "0") boolean ignore) {
		if (runStatus == RunStatus.RUNING) {
			return "正在执行导入数据到es，请勿再试执行";
		} else if (runStatus == RunStatus.SUCCESS) {
			return "导入数据已成功，如要重新开始，请运行，/reImport";
		} else if (runStatus == RunStatus.ERROR) {
			return errorHandle(errorMsg, ErrorType.DATA);
		}

		runStatus = RunStatus.RUNING;
		List<ProductBase> proList = Lists.newArrayList();
		int page = ((int) ((insertIndex - 1) / Page_SIZE)) + 1;
		long currentIndex = (page - 1) * Page_SIZE + 1;
		do {
			try {
				proList = baseMapper.getAllListingIds(Page_SIZE, (page - 1)
						* Page_SIZE);
				page++;
			} catch (Exception e) {
				
				String errmsg = errorHandle(
						"查分页的listingIds失败," + e.getMessage(), ErrorType.DATA);
				if (!ignore) {
					return errmsg;
				}
			}
			if (proList != null && proList.size() > 0) {
				List<ProductEntity> productEntities = Lists.newArrayList();
				for (ProductBase p : proList) {
					if (currentIndex != insertIndex) {
						currentIndex++;
						continue;
					}
					currentLisingId = p.getClistingid();

					try {
						productEntities.add(getObject(p));
						currentIndex = ++insertIndex;

					} catch (Exception e2) {
						
						String errmsg = errorHandle(e2.getMessage(),
								ErrorType.DATA);
						if (!ignore) {
							return errmsg;
						}
					}
				}
				try {
					long start = System.currentTimeMillis();
					Date startDate = new Date();
					sendToEs(productEntities);
					logger.info("当前已经发送到es的索引条数为:"
							+ insertIndex
							+ ",请求的开始时间："
							+ DateFormatUtils.format(startDate,
									"yyyy-MM-dd HH:mm:ss") + ",请求成功消耗的时间:"
							+ (System.currentTimeMillis() - start));
				} catch (Exception e) {
					
					String errmsg = errorHandle(e.getMessage(), ErrorType.DATA);
					if (!ignore) {
						return errmsg;
					}
				}
			}
		} while (proList != null && proList.size() > 0
				&& runStatus != RunStatus.STOP);
		runStatus = RunStatus.SUCCESS;
		return "成功,总条数:" + insertIndex;
	}

	@RequestMapping(value = "/singleBatch/{num}", method = RequestMethod.GET)
	public String singleBatch(@PathVariable("num") int num) {
		List<ProductBase> proList = Lists.newArrayList();
		try {
			proList = baseMapper.getAllListingIds(Page_SIZE, (num - 1)
					* Page_SIZE);
		} catch (Exception e) {
			
			errorHandle("查分页的listingIds失败," + e.getMessage(), ErrorType.DATA);
		}
		if (proList != null && proList.size() > 0) {
			dispatchTask(proList, num, null, null);
		}
		return "成功";
	}

	@RequestMapping(value = "/batch", method = RequestMethod.GET)
	public String batch() {
		if (runStatus == RunStatus.RUNING) {
			return "正在执行导入数据到es，请勿再试执行,如要重新开始，请先执行stop，";
		} else if (runStatus == RunStatus.SUCCESS) {
			return "数据分发任务已成功，如要重新开始，请先执行stop，请运行，keepUnFinishPage 或/reImport,或retry 并发导入";
		}
		runStatus = RunStatus.RUNING;
		int totCount = 0;
		List<ProductBase> proList = Lists.newArrayList();
		unFinishPage = Sets.newLinkedHashSet();
		errorListingIds = Lists.newArrayList();
		int page = 1;
		do {
			try {
				proList = baseMapper.getAllListingIds(Page_SIZE, (page - 1)
						* Page_SIZE);
				totCount += proList.size();
			} catch (Exception e) {
				
				errorHandle(
						"查分页的listingIds失败,出错的页码为:" + (page) + ","
								+ e.getMessage(), ErrorType.DATA);
			}
			if (proList != null && proList.size() > 0) {
				unFinishPage.add(page);
				page++;
				dispatchTask(proList, page - 1, null, null);
			}
		} while (proList != null && proList.size() > 0
				&& runStatus != RunStatus.STOP);
		runStatus = RunStatus.SUCCESS;
		return "成功分发任务,总数：" + totCount + "条";
	}

	@RequestMapping(value = "/batchByCreadteDate", method = RequestMethod.GET)
	public Result batchByCreadteDate(@RequestParam("start") String start,
			@RequestParam("end") String end) {
		if (runStatus == RunStatus.RUNING) {
			return new Result(Result.FAIL, "正在执行批量导入，请稍等再试，以防重复建索引");
		}
		int totCount = 0;
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = DateUtils.parseDate(start, "yyyy-MM-dd HH:mm:ss");
			endDate = DateUtils.parseDate(end, "yyyy-MM-dd HH:mm:ss");
		} catch (Exception e) {
			
			logger.error("日期转换失败,时期格式:yyyy-MM-dd HH:mm:ss,startDate:" + start
					+ ",endDate:" + end);
			return new Result(Result.FAIL,
					"日期转换失败,时期格式:yyyy-MM-dd HH:mm:ss,startDate:" + start
							+ ",endDate:" + end);
		}
		if (startDate == null || endDate == null) {
			return new Result(Result.FAIL, "开始或结束时间不能为空");
		}
		List<ProductBase> proList = Lists.newArrayList();
		unFinishPage = Sets.newLinkedHashSet();
		errorListingIds = Lists.newArrayList();
		int page = 1;
		do {
			try {
				proList = baseMapper.getPageListByCreateDate(Page_SIZE,
						(page - 1) * Page_SIZE, startDate, endDate);
				totCount += proList.size();
			} catch (Exception e) {
				
				errorHandle("根具创建时间查产品失败,startDate:" + start + ",endDate:"
						+ end + ",出错的页码为:" + (page) + "," + e.getMessage(),
						ErrorType.DATA);
			}
			if (proList != null && proList.size() > 0) {
				unFinishPage.add(page);
				page++;
				dispatchTask(proList, page - 1, start, end);
			}
		} while (proList != null && proList.size() > 0
				&& runStatus != RunStatus.STOP);
		return new Result(Result.SUCCESS, "成功分发任务,总数：" + totCount + "条");
	}

	@RequestMapping(value = "/keepUnFinishPage", method = RequestMethod.GET)
	public String keepUnFinishPage() {
		if (unFinishPage == null || unFinishPage.size() <= 0) {
			return "没有未发送完成的页";
		}
		if (runStatus == RunStatus.RUNING) {
			return "正在执行导入数据到es，请勿再试执行,或先执行stop,再运行此方法";
		} else if (runStatus == RunStatus.SUCCESS) {
			return "数据分发任务已成功，请先执行stop,再运行此方法";
		}
		runStatus = RunStatus.RUNING;
		int totCount = 0;
		for (Integer page : unFinishPage) {
			List<ProductBase> proList = Lists.newArrayList();
			try {
				proList = baseMapper.getAllListingIds(Page_SIZE, (page - 1)
						* Page_SIZE);
				totCount += proList.size();
			} catch (Exception e) {
				
				errorHandle(
						"查分页的listingIds失败,出错的页码为:" + (page) + ","
								+ e.getMessage(), ErrorType.DATA);
			}
			if (proList != null && proList.size() > 0) {
				dispatchTask(proList, page, null, null);
			}
		}
		runStatus = RunStatus.SUCCESS;
		return "成功分发任务,总数：" + totCount + "条";
	}

	public void dispatchTask(List<ProductBase> proList, int page, String start,
			String end) {
		if (runStatus != RunStatus.STOP && exector.isShutdown()) {
			exector = Executors.newFixedThreadPool(THREAD_SIZE);
		}
		exector.execute(new Runnable() {
			@Override
			public void run() {
				List<ProductEntity> productEntities = Lists.newArrayList();
				long startt = System.currentTimeMillis();
				for (ProductBase p : proList) {
					currentLisingId = p.getClistingid();
					try {
						productEntities.add(getObject(p));
						++insertIndex;

					} catch (Exception e2) {
						errorListingIds.add(p.getClistingid());
						
						String errormsg = ",读数据库发生错误,出错的listingId:"
								+ p.getClistingid() + ",错误的信息："
								+ e2.getMessage() + ",";
						if (start != null) {
							errormsg += ",执行方法是根具创建时间来导入，startDate:" + start
									+ ",endDate:" + end;
						}
						errorHandle(errormsg, ErrorType.SENDT);
					}
				}
				logger.info("读数据库时间：" + (System.currentTimeMillis() - startt));
				try {
					long start = System.currentTimeMillis();
					Date startDate = new Date();
					sendToEs(productEntities);
					logger.info("当前已经发送到es的索引条数为:"
							+ insertIndex
							+ "页码为:"
							+ page
							+ ",请求的开始时间："
							+ DateFormatUtils.format(startDate,
									"yyyy-MM-dd HH:mm:ss") + ",请求成功消耗的时间:"
							+ (System.currentTimeMillis() - start));
					if (unFinishPage != null && unFinishPage.size() > 0) {
						unFinishPage.remove(page);
					}
				} catch (Exception e) {
					
					String errormsg = ",发送到搜索引擎出错，出错的页码为:" + page + ","
							+ e.getMessage();
					if (start != null) {
						errormsg += ",执行方法是根具创建时间来导入，startDate:" + start
								+ ",endDate:" + end;
					}
					errorHandle(errormsg, ErrorType.SENDT);
				}
			}
		});
	}

	public ProductEntity getObject(ProductBase p)
			throws UnsupportedEncodingException, ParseException {
		ProductEntity entityVo = new ProductEntity();
		entityVo.setListingId(p.getClistingid());
		entityVo.setSku(p.getCsku());
		entityVo.setCostPrice(p.getFcostprice());
		entityVo.setYjPrice(p.getFprice());
		entityVo.setWeight(p.getFweight());
		entityVo.setSpu(p.getCparentsku());
		entityVo.setBactivity(p.getBactivity());
		if (p.getBmain() != null && p.getBmain()) {
			entityVo.setBmain(p.getBmain());
		} else {
			entityVo.setBmain(p.getBvisible());
		}
		entityVo.setStoreNum(p.getIqty());
		entityVo.setStatus(p.getIstatus());
		ProductViewCountDto viewCountDto = viewCountMapper
				.getViewCountByListingId(p.getClistingid(), p.getIwebsiteid());
		if (viewCountDto != null) {
			entityVo.setViewcount(viewCountDto.getIviewcount());
		}
		entityVo.setVideos(videoMapper.getVideoUrlByListingId(p.getClistingid()));
		if (p.getDcreatedate() != null) {
			entityVo.setReleaseTime(DateFormatUtils.format(p.getDcreatedate(),
					"yyyy-MM-dd HH:mm:ss"));
		}
		entityVo.setWebSites(Arrays.asList(p.getIwebsiteid()));
		entityVo.setSalesTotalCount(orderMapper.getSaleCountByListingId(p
				.getClistingid()));
		entityVo.setColltes(collectMapper.getCountByListingID(p.getClistingid()));
		List<ProductImageEntity> images = Lists.transform(
				imageMapper.getProductImgsByListingId(p.getClistingid(), true),
				i -> new ProductImageEntity(i.getCimageurl(), i.getIorder(), i
						.getBbaseimage(), i.getBsmallimage()));
		if (images != null && images.size() > 0) {
			String defaluimg = "";
			for (ProductImageEntity productImageEntity : images) {
				//遍历为主图的设置为defalue imgage
				if(productImageEntity.getIsBase()){
					defaluimg = productImageEntity.getUrl();
				}
			}
			if (!"".equals(defaluimg)) {
				entityVo.setDefaultImgUrl(defaluimg);
			}else{
				//如果images重没有主图,则拿第一张为主图.并设置主图
				defaluimg = images.get(0).getUrl();
				images.get(0).setIsBase(true);
				images.get(0).setIsSmall(true);
			}
			entityVo.setImgs(images);
		}
		List<ProductSalePrice> promos = salePriceMapper
				.getAllProductSalePriceByListingId(p.getClistingid());
		if (promos != null && promos.size() > 0) {
			Date currentDate = DateUtils.parseDate(DateFormatUtils.formatUTC(
					new Date(), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
			for (ProductSalePrice s : promos) {
				Date startDate = s.getDbegindate();
				Date endDate = s.getDenddate();
				if (startDate != null && endDate != null) {
					if (currentDate.before(endDate)
							&& currentDate.after(startDate)) {
						entityVo.setIsOnSale(true);
						break;
					}
				}
			}
			entityVo.setPromotionPrice(Lists.newArrayList(Lists.transform(
					promos,
					e -> new PromotionPrice(e.getCsku(), p.getIwebsiteid(), e
							.getFsaleprice(),
							e.getDbegindate() != null ? DateFormatUtils.format(
									e.getDbegindate(), "yyyy-MM-dd HH:mm:ss")
									: "",
							e.getDenddate() != null ? DateFormatUtils.format(
									e.getDenddate(), "yyyy-MM-dd HH:mm:ss")
									: ""))));
		}
		ReviewCountAndScoreDto andScoreDto = reviewsMapper
				.getScoreByListingIdAdnStatue(p.getClistingid(), 1);
		if (andScoreDto != null) {
			entityVo.setReview(new ReviewStartNumBo(andScoreDto
					.getReviewCount(), andScoreDto.getAvgScore(), Lists
					.newArrayList(Lists.transform(
							reviewsMapper.getFoverallratingNumByListingId(
									p.getClistingid(), 1),
							e -> new StartNum(e.getStartNum(),
									(int) ((double) e.getNum()
											/ (double) andScoreDto
													.getReviewCount() * 100))))));
		}
		List<ProductLabel> tages = lableMapper.getLabelListingId(
				p.getClistingid(), p.getIwebsiteid());
		entityVo.setIsFreeShipping(false);
		if (tages != null && tages.size() > 0) {
			tages.forEach(t -> {
				if ("freeShipping".equals(t.getCtype())
						|| "allfreeshipping".equals(t.getCtype())) {
					entityVo.setIsFreeShipping(true);
				}
			});
			entityVo.setTagsName(Lists.transform(
					tages,
					t -> new TagEntity(t.getIid(), t.getCtype(), t
							.getDcreatedate() != null ? DateFormatUtils.format(
							t.getDcreatedate(), "yyyy-MM-dd HH:mm:ss") : "")));
		}
		List<Integer> storages = productStorageMapper
				.getProductStorageByListingId(p.getClistingid());
		if (storages != null && storages.size() > 0) {
			entityVo.setDepots(Lists.newArrayList(Lists.transform(
					storageMapper.getStorage(storages),
					e -> new DepotEntity(e.getIid(), e.getCstoragename()))));
		}

		List<ProductTypeEntity> productTypeEntities = Lists.newArrayList(Lists
				.transform(
						categoryMapper.getCategoryLableInfoByListingIdClient(
								p.getClistingid(), p.getIwebsiteid()),
						l -> new ProductTypeEntity(l.getIlanguageid(), l
								.getIcategoryid(), l.getCname(), l.getIlevel(),
								null, l.getCpath(),
								categoryProductRecommendService.getObj(1,
										l.getIcategoryid(), p.getCsku(),
										p.getIwebsiteid()))));
		Map<Integer, List<ProductTypeEntity>> typeEntityMaps = productTypeEntities
				.stream()
				.collect(
						Collectors.groupingBy(ProductTypeEntity::getLanguageId));
		Map<Integer, List<AttributeItem>> attrItems = Lists
				.newArrayList(
						Lists.transform(
								entiryMapper.getListByListingId(p
										.getClistingid()),
								a -> {
									if (a.getCkeyname() != null
											&& "brand".equals(a.getCkeyname())) {
										entityVo.setBrand(a.getCvaluename());
									}
									return new AttributeItem(a.getIkey(), a
											.getCkeyname(), a.getIvalue(), a
											.getCvaluename(), a
											.getIlanguageid(), a.getBshowimg(),
											a.getBshow());
								})).stream()
				.collect(Collectors.groupingBy(AttributeItem::getLanguageId));
		Map<Integer, List<ProductUrl>> urlsMap = urlMapper
				.geUrlsByListingId(p.getClistingid(), p.getIwebsiteid())
				.stream()
				.collect(Collectors.groupingBy(ProductUrl::getIlanguageid));
		Map<Integer, List<ProductBase>> productInfoBases = baseMapper
				.getProductBaseInfoByListingId(p.getClistingid()).stream()
				.collect(Collectors.groupingBy(ProductBase::getIlanguageid));
		List<MutilLanguage> mutilLanguages = Lists.newArrayList();
		langsMap.forEach((langId, langName) -> {
			MutilLanguage mutil = new MutilLanguage();
			List<ProductTypeEntity> productTypes = Lists.newArrayList();
			Map<Integer, List<ProductTypeEntity>> levelMap = Maps.newHashMap();
			if (typeEntityMaps != null && typeEntityMaps.size() > 0) {
				productTypes = typeEntityMaps.get(langId);
				if (productTypes != null && productTypes.size() > 0) {
					productTypes = Lists.newArrayList(productTypes);
					levelMap = productTypes.stream().collect(
							Collectors.groupingBy(ProductTypeEntity::getLevel));
				}
			}
			List<String> url = Lists.newArrayList();
			List<ProductUrl> urls = urlsMap.get(langId);//根据语言获取url
			if (urls != null && urls.size() > 0) {
				url = Lists.newArrayList(Lists.transform(urls, u -> u.getCurl()));
			}else{
				if(langId > 1){
					urls = urlsMap.get(1);//其他语言无法获取url时根据英语获取
					url = Lists.newArrayList(Lists.transform(urls, u -> u.getCurl()));
				}
			}
			List<ProductBase> productBases = productInfoBases.get(langId);//根据语言获取商品基本信息
			mutil.setLanguageId(langId);
			mutil.setLanguageName(langName);
			if ((productBases != null && productBases.size() > 0)){
					ProductBase base = productBases.get(0);
					mutil.setDesc(base.getCdescription());
					mutil.setTitle(base.getCtitle());
					mutil.setKeywords(base.getCkeyword());
					mutil.setShortDescription(base.getCshortdescription());
					mutil.setMetaDescription(base.getCmetadescription());
					mutil.setMetaKeyword(base.getCmetakeyword());
					mutil.setMetaTitle(base.getCmetatitle());
			}else{
				if(langId > 1){
					 productBases = productInfoBases.get(1);//多语言无法获取基本信息时 根据英语设置
					 ProductBase base = productBases.get(0);
					 mutil.setDesc(base.getCdescription());
					 mutil.setTitle(base.getCtitle());
					 mutil.setKeywords(base.getCkeyword());
					 mutil.setShortDescription(base.getCshortdescription());
					 mutil.setMetaDescription(base.getCmetadescription());
					 mutil.setMetaKeyword(base.getCmetakeyword());
					 mutil.setMetaTitle(base.getCmetatitle());
				}
			}
			if(productTypes != null && productTypes.size() > 0){
				mutil.setProductTypes(productTypes);
				mutil.setProductLevel1(levelMap.get(1));
				mutil.setProductLevel2(levelMap.get(2));
				mutil.setProductLevel3(levelMap.get(3));
			}
			if (url != null && url.size() > 0){
				mutil.setUrl(url);//设置url
			}
			List<AttributeItem> items = attrItems.get(langId);//获取多属性
			if (items != null && items.size() > 0) {
				mutil.setItems(Lists.newArrayList(items));
			}else{
				if(langId > 1){
					items = attrItems.get(1);//找不到时获取英语多属性
					mutil.setItems(Lists.newArrayList(items));
				}
			}
			
			mutilLanguages.add(mutil);
		});
		entityVo.setMutil(mutilLanguages);
		return entityVo;
	}

	public void sendToEs(List<ProductEntity> proudcts)
			throws UnsupportedEncodingException, JsonProcessingException {
		
//		boolean result = HttpSendRequest.sendPostByJson(esUrl, json);
//		
////		ResponseEntity<String> rest = restTemplate.postForEntity(esUrl,
////				new HttpEntity<String>(json, headers), String.class);
//		if (result) {
//
//		} else {
//			errorJsonMsg = json;
//		}
		String json = jsonMapper.writeValueAsString(proudcts);
		errorJsonMsg = json;
		ResponseEntity<String> rest = restTemplate.postForEntity(esUrl,
				new HttpEntity<String>(json, headers), String.class);
		if (rest.getStatusCode().is2xxSuccessful()
				&& CommonEntity.returnStatus.SUCESS.getName().equals(
						JSON.parseObject(rest.getBody()).get("result"))) {

		} else {
			throw new RuntimeException("http返回状态码：" + rest.getStatusCode()
					+ ",返回的请求体:" + rest.getBody());
		}

	}
}
