package com.chinamcloud.spider.handle;


import com.chinamcloud.spider.model.DataConversion;
import com.chinamcloud.spider.model.Extract;
import com.chinamcloud.spider.model.PageModel;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selector;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageModelExtractor {

    private PageModel pageModel;

    public PageModel getPageModel() {
        return pageModel;
    }

    private static Class<DataConversionUtil> dataConversionUtil;

    static {
        dataConversionUtil = DataConversionUtil.class;
    }


    public static PageModelExtractor create(PageModel pageModel) {
        PageModelExtractor pageModelExtractor = new PageModelExtractor(pageModel);
        return pageModelExtractor;
    }

    public PageModelExtractor(PageModel pageModel) {
        this.pageModel = pageModel;
    }

    public Object process(Page page) {
        // 判断是否为二级URL
        if (page.getRequest().isExtractLinks()) return null;
        if (pageModel.getMultiExtract() == null) {
            return processSingle(page, null, true);
        } else {
            // todo 一个页面抽取多条记录只支持单个value
            Selector selector = ExtractorsUtil.getSelector(pageModel.getMultiExtract()).get(0);
            if (pageModel.getMultiExtract().isMulti()) {
                List<Object> os = new ArrayList<Object>();
                List<String> list = selector.selectList(page.getRawText());
                for (String s : list) {
                    Object o = processSingle(page, s, false);
                    if (o != null) {
                        os.add(o);
                    }
                }
                return os;
            } else {
                String select = selector.select(page.getRawText());
                Object o = processSingle(page, select, false);
                return o;
            }
        }
    }


    private Map<String, Object> processSingle(Page page, String html, boolean isRaw) {
        Map<String, Object> map = new HashMap<>();
        for (Extract extract : pageModel.getExtracts()) {
            List<Selector> selectors = ExtractorsUtil.getSelector(extract);
            if (extract.isMulti()) {
                // 多选
                List<String> value = selectList(page, selectors, html, extract.getSource(), isRaw);
                if ((value == null || value.size() == 0) && extract.isNotNull()) {
                    return null;
                }
                map.put(extract.getFiled(), value);
            } else {
                // 单选
                String value = select(page, selectors, html, extract.getSource(), isRaw);
                if (value == null && extract.isNotNull()) {
                    return null;
                }
                if (extract.getDataConversion() != null && !extract.getDataConversion().getFunction().equals("")) {
                    DataConversion dataConversion = extract.getDataConversion();
                    try {
                        Method method = dataConversionUtil.
                                getMethod(dataConversion.getFunction(), Object.class, String.class);
                        Object valueC =
                                method.invoke(dataConversionUtil.newInstance(), value, dataConversion.getExpression());
                        value = String.valueOf(valueC);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                map.put(extract.getFiled(), value);
            }
        }
        // todo
        map.put("refererUrl", page.getRequest().getUrl());
        map.put("sourceUrl", page.getRequest().getSourceUrl());
        map.put("source", page.getRequest().getExtra("source"));
        map.put("classification", page.getRequest().getExtra("type"));
        map.put("tbNickName", page.getRequest().getExtra("sourceName"));
        map.put("isTest", page.getRequest().isTest());
        if ((map.get("description") == null || map.get("description").equals("")) && !page.getRequest().isTest()) {
            return null;
        }
        return map;
    }


    private List<String> selectList(Page page, List<Selector> selectors, String html, Extract.Source source, boolean isRaw) {
        List<String> value = null;
        for (Selector s : selectors) {
            value = selectList(page, s, html, source, isRaw);
            if (value != null && value.size() != 0) return value;
        }
        return value;
    }


    private List<String> selectList(Page page, Selector selector, String html, Extract.Source source, boolean isRaw) {
        List<String> value;
        switch (source) {
            case RawHtml:
                value = page.getHtml().selectDocumentForList(selector);
                break;
            case Html:
                if (isRaw) {
                    value = page.getHtml().selectDocumentForList(selector);
                } else {
                    value = selector.selectList(html);
                }
                break;
            case Url:
                value = selector.selectList(page.getUrl().toString());
                break;
            case RawText:
                value = selector.selectList(page.getRawText());
                break;
            default:
                value = selector.selectList(html);
        }
        return value;
    }

    private String select(Page page, List<Selector> selectors, String html, Extract.Source source, boolean isRaw) {
        String value = null;
        for (Selector s : selectors) {
            value = select(page, s, html, source, isRaw);
            if (value != null && !value.equals("") && !value.replaceAll(" ", "").equals("")) return value;
        }
        return value;
    }


    private String select(Page page, Selector selector, String html, Extract.Source source, boolean isRaw) {
        String value;
        switch (source) {
            case RawHtml:
                value = page.getHtml().selectDocument(selector);
                break;
            case Html:
                if (isRaw) {
                    value = page.getHtml().selectDocument(selector);
                } else {
                    value = selector.select(html);
                }
                break;
            case Url:
                value = selector.select(page.getUrl().toString());
                break;
            case RawText:
                value = selector.select(page.getRawText());
                break;
            default:
                value = selector.select(html);
        }
        return value;
    }
}
