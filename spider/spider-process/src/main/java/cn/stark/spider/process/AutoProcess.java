package cn.stark.spider.process;

import cn.stark.spider.common.Page;
import cn.stark.spider.common.spider.PageProcesser;

public class AutoProcess implements PageProcesser {

    @Override
    public void process(Page page) {
        try {
            String s = new String(page.getBytes(), page.getCharset() ==  null ? "utf-8" : page.getCharset());
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
