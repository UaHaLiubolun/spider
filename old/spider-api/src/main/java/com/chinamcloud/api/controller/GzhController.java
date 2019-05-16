package com.chinamcloud.api.controller;

import com.chinamcloud.api.CodeResult;
import com.chinamcloud.api.dao.GzhContentDao;
import com.chinamcloud.spider.dao.WeChatDao;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 公众号接口
 */
@RestController
@CrossOrigin
public class GzhController {

    private WeChatDao weChatDao;

    private GzhContentDao gzhContentDao;

    {
        weChatDao = new WeChatDao();
        gzhContentDao = new GzhContentDao();
    }

    @GetMapping("/gzh")
    public CodeResult getAll(@RequestParam(value = "isCount", defaultValue = "0", required = false) boolean isCount) {
        List<Map<String, String>> newsList = weChatDao.getAll();
        for (Map<String, String> m : newsList) {
            String sourceId = String.valueOf(Math.abs(m.get("gzhId").hashCode()));
            m.put("sourceId", sourceId);
        }
        if (isCount) {
            Map<String, Integer> integerMap = gzhContentDao.getGzhCount();
            for (Map<String, String> m : newsList) {
                Integer i = integerMap.get(m.get("sourceId"));
                i = i == null ? 0 : i;
                m.put("count", i.toString());
            }
        }
        return CodeResult.successResult(null, newsList);
    }

    @GetMapping("/gzh/getHotNews")
    public CodeResult getHotNews(@RequestParam(value = "keyword") String keyword) {
        List<Map<String, String>> list = gzhContentDao.getContentBySourceId(Math.abs(("公众号关键字_"+ keyword).hashCode()));
        return CodeResult.successResult("success", list);
    }

    @PostMapping("/gzh")
    public CodeResult addListInfo(@RequestBody Map<String, String> listInfo) {
        listInfo.put("gzhId", listInfo.get("gzhId").replace(" ", ""));
        boolean isSuccess = weChatDao.add(listInfo);
        return isSuccess ? CodeResult.successResult() : CodeResult.failedResult("failed");
    }

    @PutMapping("/gzh")
    public CodeResult modifyListInfo(@RequestBody Map<String, String> listInfo) {
        boolean isSuccess = weChatDao.modify(listInfo);
        return isSuccess ? CodeResult.successResult() : CodeResult.failedResult("failed");
    }

    @DeleteMapping("/gzh/{gzhId}")
    public CodeResult addListInfo(@PathVariable(value = "gzhId") String gzhId) {
        boolean isSuccess = weChatDao.deleteByUrl(gzhId);
        return isSuccess ? CodeResult.successResult() : CodeResult.failedResult("failed");
    }
}
