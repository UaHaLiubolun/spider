package com.chinamcloud.api.controller;

import com.chinamcloud.api.CodeResult;
import com.chinamcloud.api.dao.NewsListDao;
import com.chinamcloud.api.model.ListInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@RestController
@CrossOrigin
public class NewsListController {

    @Resource(name = "newsListDao")
    private NewsListDao newsListDao;


    @GetMapping("/newsList")
    public CodeResult getAll(@RequestParam(value = "isSplit", required = false, defaultValue = "1" ) String isSplit) {
        List<Map<String, String>> newsList = newsListDao.getAll();
        if (isSplit.equals("0")) {
            return CodeResult.successResult(null, newsList);
        }
        return CodeResult.successResult(null, split(newsList));
    }

    @PostMapping("/newsList")
    public CodeResult addListInfo(@RequestBody Map<String, String> listInfo) {
        String isSuccess = newsListDao.add(listInfo);
        return isSuccess != null ? CodeResult.successResult() : CodeResult.failedResult("failed");
    }

    private List<ListInfo> split(List<Map<String, String>> newsList) {
        List<ListInfo> info = new ArrayList<>();
        for (Map<String, String> map:
             newsList) {
            test(info, map);
        }
        return info;
    }

    private void test(List<ListInfo> listInfo, Map<String, String> map) {
        String[] names = map.get("name").split("_");
        if (names.length == 1) {
            ListInfo l = new ListInfo();
            l.setUrl(map.get("url"));
            l.setUrlMd5(map.get("urlMd5"));
            l.setLeaf(true);
            l.setParentUrl(String.valueOf((long)(Math.random() * 100000000)));
            l.setName(names[0]);
            listInfo.add(l);
        } else {
            boolean match = false;
            for (ListInfo i:
                 listInfo) {
                if (i.getName().equals(names[0])) {
                    match = true;
                    if (i.getChildren() == null) {
                        i.setChildren(new ArrayList<>());
                    }
                    map.put("name", map.get("name").replace(names[0] + "_", ""));
                    test(i.getChildren(), map);
                    break;
                }
            }
            if (!match) {
                ListInfo l = new ListInfo();
                l.setUrl(map.get("url"));
                l.setUrlMd5(map.get("urlMd5"));
                l.setLeaf(false);
                l.setName(names[0]);
                l.setChildren(new ArrayList<>());
                listInfo.add(l);
                l.setParentUrl(String.valueOf((long)(Math.random() * 100000000)));
                map.put("name", map.get("name").replace(names[0] + "_", ""));
                test(l.getChildren(), map);
            }
        }
    }

}
