/**
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feilong.taglib.display.pager;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.commons.core.configure.ResourceBundleUtil;
import com.feilong.commons.core.entity.Pager;
import com.feilong.commons.core.net.URIUtil;
import com.feilong.commons.core.util.Validator;
import com.feilong.servlet.http.ParamUtil;
import com.feilong.taglib.display.pager.command.PagerConstants;
import com.feilong.taglib.display.pager.command.PagerParams;
import com.feilong.taglib.display.pager.command.PagerUrlTemplate;
import com.feilong.taglib.display.pager.command.PagerVMParam;
import com.feilong.tools.json.JsonUtil;
import com.feilong.tools.velocity.VelocityUtil;

/**
 * 分页工具类<br>
 * 该类主要是将url相关数据转成vm需要的数据,解析成字符串返回.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0.0 2010-5-26 下午11:50:10
 * @version 1.0.5 2014-5-4 19:36
 */
public final class PagerUtil{

	/** The Constant log. */
	private static final Logger	log					= LoggerFactory.getLogger(PagerUtil.class);

	/**
	 * vm里面 pagerVMParam变量名称 <code>{@value}</code>,在vm里面,你可以使用 ${pagerVMParam.xxx} 来获取数据
	 */
	private static final String	VM_KEY_PAGERVMPARAM	= "pagerVMParam";

	/**
	 * vm里面i18n变量名称 <code>{@value}</code>,在vm里面,你可以使用 ${i18nMap.xxx} 来获取数据
	 */
	private static final String	VM_KEY_I18NMAP		= "i18nMap";

	/** 国际化配置文件<code>{@value}</code>. */
	private static final String	I18N_FEILONG_PAGER	= "messages/feilong-pager";

	/**
	 * 解析vm模板 生成分页html代码.
	 * 
	 * @param pagerParams
	 *            带有一系列分页需要的参数
	 * @return 生成分页html代码
	 */
	public final static String getPagerContent(PagerParams pagerParams){

		if (Validator.isNullOrEmpty(pagerParams)){
			throw new IllegalArgumentException("pagerParams can't be null/empty!");
		}

		int totalCount = pagerParams.getTotalCount();

		// 有数据,不是空
		// 如果=0 应该显示其他内容
		if (totalCount > 0){

			PagerVMParam pagerVMParam = buildPagerVMParam(pagerParams);
			Map<String, String> i18nMap = buildI18nMap(pagerParams);

			boolean debugIsNotParseVM = pagerParams.getDebugIsNotParseVM();
			if (!debugIsNotParseVM){

				// ****************设置变量参数************************************************************
				Map<String, Object> vmParamMap = new HashMap<String, Object>();
				vmParamMap.put(VM_KEY_PAGERVMPARAM, pagerVMParam);
				vmParamMap.put(VM_KEY_I18NMAP, i18nMap);

				if (log.isDebugEnabled()){
					log.debug("vmParamMap:{}", JsonUtil.format(vmParamMap));
					log.debug("debugNotParseVM:{}", debugIsNotParseVM);
				}
				String vmPath = pagerParams.getVmPath();
				String content = VelocityUtil.parseTemplateWithClasspathResourceLoader(vmPath, vmParamMap);
				return content;
			}
		}else{
			if (log.isDebugEnabled()){
				log.debug("the param totalCount:{} not >0", totalCount);
			}
		}

		// 如果总数不>0 则直接返回 empty,页面分页地方显示空白
		return "";
	}

	/**
	 * 获得当前分页数字,不带这个参数 或者转换异常 返回1.
	 * 
	 * @param request
	 *            当前请求
	 * @param pageParamName
	 *            分页参数名称
	 * @return <ul>
	 *         <li>请求参数中,分页参数值 Integer 类型</li>
	 *         <li>如果参数中不带这个分页参数,或者转换异常 返回1</li>
	 *         </ul>
	 */
	public final static Integer getCurrentPageNo(HttpServletRequest request,String pageParamName){
		// /s/s-t-b-f-a-cBlack-s-f-p-gHeat+Gear-e-i-o.htm?keyword=&pageNo=%uFF1B
		Integer currentPageNo = null;
		try{
			currentPageNo = ParamUtil.getParameterToInteger(request, pageParamName);
		}catch (Exception e){
			// 抛出异常, 但是不给 currentPageNo 赋值
			e.printStackTrace();
		}

		// 不是空 直接返回
		if (null != currentPageNo){
			return currentPageNo;
		}

		// 不带这个参数 或者转换异常 返回1
		return 1;
	}

	// *****************************private************************************************************

	/**
	 * Builds the i18n map.
	 * 
	 * @param pagerParams
	 *            the pager params
	 * @return the map
	 * @since 1.0.5
	 */
	private static Map<String, String> buildI18nMap(PagerParams pagerParams){
		// ****************国际化*****************************************
		Locale locale = pagerParams.getLocale();
		Map<String, String> i18nMap = ResourceBundleUtil.readAllPropertiesToMap(I18N_FEILONG_PAGER, locale);
		return i18nMap;
	}

	/**
	 * Builds the pager vm param.
	 * 
	 * @param pagerParams
	 *            the pager params
	 * @return the pager vm param
	 * @since 1.0.5
	 */
	private static PagerVMParam buildPagerVMParam(PagerParams pagerParams){

		int totalCount = pagerParams.getTotalCount();

		int currentPageNo = getCurrentPageNo(pagerParams);
		int pageSize = pagerParams.getPageSize();

		// *************************************************************
		Integer maxShowPageNo = pagerParams.getMaxShowPageNo();
		Pager pager = new Pager(currentPageNo, pageSize, totalCount);
		pager.setMaxShowPageNo(maxShowPageNo);

		int allPageNo = pager.getAllPageNo();

		// *************************************************************
		// 获得开始和结束的索引
		int[] startAndEndIteratorIndexs = getStartAndEndIteratorIndexs(allPageNo, currentPageNo, pagerParams);
		// 开始迭代索引编号
		int startIteratorIndex = startAndEndIteratorIndexs[0];
		// 结束迭代索引编号
		int endIteratorIndex = startAndEndIteratorIndexs[1];

		Set<Integer> indexSet = getAllIndexSet(pager, startAndEndIteratorIndexs);
		// ****************************************************************************************
		// 获得所有页码的连接.
		Map<Integer, String> indexAndHrefMap = getAllIndexAndHrefMap(pagerParams, indexSet);

		// ****************************************************************************************
		int prePageNo = pager.getPrePageNo();
		int nextPageNo = pager.getNextPageNo();
		int firstPageNo = 1;
		int lastPageNo = allPageNo;

		String pageParamName = pagerParams.getPageParamName();

		// ********************************************************************************************
		String skin = pagerParams.getSkin();

		PagerVMParam pagerVMParam = new PagerVMParam();
		pagerVMParam.setSkin(skin);// 皮肤

		pagerVMParam.setTotalCount(totalCount);// 总行数，总结果数
		pagerVMParam.setCurrentPageNo(currentPageNo);// 当前页
		pagerVMParam.setAllPageNo(allPageNo);// 总页数

		pagerVMParam.setStartIteratorIndex(startIteratorIndex);// 导航开始页码
		pagerVMParam.setEndIteratorIndex(endIteratorIndex);// 导航结束页码

		// ****************************************************************************************
		pagerVMParam.setPreUrl(indexAndHrefMap.get(prePageNo)); // 上一页链接
		pagerVMParam.setNextUrl(indexAndHrefMap.get(nextPageNo));// 下一页链接
		pagerVMParam.setFirstUrl(indexAndHrefMap.get(firstPageNo));// 第一页的链接
		pagerVMParam.setLastUrl(indexAndHrefMap.get(lastPageNo));// 最后一页的链接

		Integer defaultTemplatePageNo = PagerConstants.DEFAULT_TEMPLATE_PAGE_NO;

		PagerUrlTemplate pagerUrlTemplate = new PagerUrlTemplate();
		pagerUrlTemplate.setHref(indexAndHrefMap.get(defaultTemplatePageNo));// 模板链接
		pagerUrlTemplate.setTemplateValue(defaultTemplatePageNo);
		pagerVMParam.setPagerUrlTemplate(pagerUrlTemplate);

		pagerVMParam.setPageParamName(pageParamName);

		// *********************************************************
		LinkedHashMap<Integer, String> iteratorIndexAndHrefMap = getIteratorIndexAndHrefMap(
				startIteratorIndex,
				endIteratorIndex,
				indexAndHrefMap);
		pagerVMParam.setIteratorIndexMap(iteratorIndexAndHrefMap);
		return pagerVMParam;
	}

	/**
	 * 要循环的 index和 end 索引 =href map.
	 * 
	 * @param startIteratorIndex
	 *            the start iterator index
	 * @param endIteratorIndex
	 *            the end iterator index
	 * @param indexAndHrefMap
	 *            the index and href map
	 * @return the iterator index and href map
	 * @since 1.0.5
	 */
	private static LinkedHashMap<Integer, String> getIteratorIndexAndHrefMap(
			int startIteratorIndex,
			int endIteratorIndex,
			Map<Integer, String> indexAndHrefMap){
		LinkedHashMap<Integer, String> iteratorIndexAndHrefMap = new LinkedHashMap<Integer, String>();
		for (int i = startIteratorIndex; i <= endIteratorIndex; ++i){
			iteratorIndexAndHrefMap.put(i, indexAndHrefMap.get(i));
		}
		return iteratorIndexAndHrefMap;
	}

	/**
	 * 需要生成链接的 索引号.
	 * 
	 * @param pager
	 *            the pager
	 * @param startIteratorIndexAndEndIteratorIndexs
	 *            the start iterator index and end iterator indexs
	 * @return the index set
	 * @since 1.0.5
	 */
	private final static Set<Integer> getAllIndexSet(Pager pager,int[] startIteratorIndexAndEndIteratorIndexs){
		int prePageNo = pager.getPrePageNo();
		int nextPageNo = pager.getNextPageNo();
		int allPageNo = pager.getAllPageNo();
		int lastPageNo = allPageNo;
		int firstPageNo = 1;

		// 开始迭代索引编号
		int startIteratorIndex = startIteratorIndexAndEndIteratorIndexs[0];
		// 结束迭代索引编号
		int endIteratorIndex = startIteratorIndexAndEndIteratorIndexs[1];

		// 所有需要生成url 的 index值
		Set<Integer> indexSet = new HashSet<Integer>();
		indexSet.add(PagerConstants.DEFAULT_TEMPLATE_PAGE_NO);// 模板链接 用于前端操作
		indexSet.add(prePageNo);
		indexSet.add(nextPageNo);
		indexSet.add(firstPageNo);
		indexSet.add(lastPageNo);
		for (int i = startIteratorIndex; i <= endIteratorIndex; ++i){
			indexSet.add(i);
		}
		return indexSet;
	}

	/**
	 * 如果pagerParams.getCurrentPageNo()<1返回1 否则返回 pagerParams.getCurrentPageNo()
	 * 
	 * @param pagerParams
	 *            the pager params
	 * @return 如果pagerParams.getCurrentPageNo()<1返回1 否则返回 pagerParams.getCurrentPageNo()
	 * @since 1.0.5
	 */
	private final static int getCurrentPageNo(PagerParams pagerParams){
		int currentPageNo = pagerParams.getCurrentPageNo();

		// 解决可能出现界面上 负数的情况
		if (currentPageNo < 1){
			currentPageNo = 1;
		}
		return currentPageNo;
	}

	/**
	 * 获得所有页码的连接<br>
	 * 注:(key={@link #DEFAULT_TEMPLATE_PAGE_NO} 为模板链接,可用户前端解析 {@link PagerVMParam#getHrefUrlTemplate()}.
	 * 
	 * @param pagerParams
	 *            the pager params
	 * @param indexSet
	 *            索引set
	 * @return key是分页页码，value是解析之后的链接
	 */
	private final static Map<Integer, String> getAllIndexAndHrefMap(PagerParams pagerParams,Set<Integer> indexSet){
		String pageUrl = pagerParams.getPageUrl();
		String charsetType = pagerParams.getCharsetType();
		String pageParamName = pagerParams.getPageParamName();

		URI uri = URIUtil.create(pageUrl, charsetType);

		if (null == uri){
			log.error("pageUrl:{} can not create to uri,charsetType:{}", pageUrl, charsetType);
			return null;
		}

		String url = uri.toString();
		String before = URIUtil.getBeforePath(url);

		// ***********************************************************************
		// getQuery() 返回此 URI 的已解码的查询组成部分。
		// getRawQuery() 返回此 URI 的原始查询组成部分。 URI 的查询组成部分（如果定义了）只包含合法的 URI 字符。
		String query = uri.getRawQuery();

		// ***********************************************************************
		Map<String, String[]> map = new LinkedHashMap<String, String[]>();
		// 传入的url不带参数的情况
		if (Validator.isNullOrEmpty(query)){
			// nothing to do
		}else{

			// 先返回 安全的 参数 ,避免循环里面 浪费性能
			Map<String, String[]> originalMap = URIUtil.parseQueryToArrayMap(query, charsetType);
			map.putAll(originalMap);
		}

		// ***********************************************************************F
		Map<Integer, String> returnMap = new HashMap<Integer, String>();

		// XXX 可以优化 优化成先出 模板链接,然后每个替换, 这样性能要比 循环解析url要快
		for (Integer index : indexSet){

			// 构建一个数组，完全覆盖pageParamName
			map.put(pageParamName, new String[] { index + "" });

			// 循环里面不再加码,避免 浪费性能
			String encodedUrl = URIUtil.getEncodedUrlByArrayMap(before, map, null);
			returnMap.put(index, encodedUrl);
		}
		return returnMap;
	}

	/**
	 * 获得开始和结束的索引.
	 * 
	 * @param allPageNo
	 *            总页数
	 * @param currentPageNo
	 *            当前页数
	 * @param pagerParams
	 *            the pager params
	 * @return 获得开始和结束的索引
	 * @since 1.0.0
	 */
	private final static int[] getStartAndEndIteratorIndexs(int allPageNo,int currentPageNo,PagerParams pagerParams){
		int maxIndexPages = pagerParams.getMaxIndexPages();

		// 最多显示多少个导航页码
		maxIndexPages = getAutoMaxIndexPages(allPageNo, maxIndexPages);
		// 开始迭代索引编号
		int startIteratorIndex = 1;
		// 结束迭代索引编号
		int endIteratorIndex = allPageNo;

		// 总页数大于最大导航页数
		if (allPageNo > maxIndexPages){
			// 当前页导航两边总数和
			int fenTwo = (maxIndexPages - 1);
			// 当前页左侧导航数
			int leftCount = fenTwo / 2;
			// 当前页右侧导航数
			int rightCount = (fenTwo % 2 == 0) ? leftCount : (leftCount + 1);
			// 当前页<=(左边页数+1)
			if (currentPageNo <= (leftCount + 1)){
				startIteratorIndex = 1;
				// 此时迭代结束为maxIndexPages
				endIteratorIndex = maxIndexPages;
			}else{
				// 如果当前页+右边页>=总页数
				if (currentPageNo + rightCount >= allPageNo){
					startIteratorIndex = allPageNo - maxIndexPages + 1;
					// 此时迭代结束为allPageNo
					endIteratorIndex = allPageNo;
				}else{
					startIteratorIndex = currentPageNo - leftCount;
					endIteratorIndex = currentPageNo + rightCount;
				}
			}
		}
		return new int[] { startIteratorIndex, endIteratorIndex };
	}

	/**
	 * 获得分页最大显示的分页码<br>
	 * 如果maxIndexPages 是0或者null,那么根据allpageNo,采用自动调节长度功能<br>
	 * 因为,如果页码大于1000的时候, 如果还是10页码的显示(1001,1002,1003,1004,1005,1006,1007,1008,1009,1010) 显示上面会很长 ,会打乱页面布局 <br>
	 * <ul>
	 * <li>当大于1000的页码 显示6个 即 1001,1002,1003,1004,1005,1006 类似于这样的</li>
	 * <li>当大于100的页码 显示8个 即 101,102,103,104,105,106,107,108 类似于这样的</li>
	 * <li>其余,默认显示10条</li>
	 * </ul>
	 * .
	 * 
	 * @param allPageNo
	 *            分页总总页数
	 * @param maxIndexPages
	 *            表示手动指定一个固定的显示码<br>
	 *            如果不指定,或者是0 那么就采用自动调节的显示码
	 * @return 最大显示码
	 * @deprecated 需要重构
	 */
	private final static int getAutoMaxIndexPages(int allPageNo,Integer maxIndexPages){
		if (Validator.isNullOrEmpty(maxIndexPages) || 0 == maxIndexPages){
			// 总页数超过1000的时候,自动调节导航数量的作用
			if (allPageNo > 1000){
				return 6;
			}else if (allPageNo > 100){
				return 8;
			}else{
				return 10;// 默认为10
			}
		}
		// 不是 0 或者null,直接返回指定的
		return maxIndexPages;
	}
}