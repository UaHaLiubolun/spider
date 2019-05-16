package com.chinamcloud.api.controller;

import com.chinamcloud.api.CodeResult;
import com.chinamcloud.api.dao.NewsListDao;
import com.chinamcloud.api.dao.mysql.SiteTreeDao;
import com.chinamcloud.api.model.SiteTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class TreeController {

    @Autowired
    private SiteTreeDao siteTreeDao;

    @Resource(name = "newsListDao")
    private NewsListDao newsListDao;


    @GetMapping(value = "/site/tree/{parentId}")
    public CodeResult getByParentId(@PathVariable(value = "parentId") Long parentId) {
        List<SiteTree> siteTreeList = siteTreeDao.getByParentId(parentId);
        return CodeResult.successResult("", siteTreeList);
    }

    @PostMapping(value = "/site/tree")
    public CodeResult add(@RequestBody SiteTree siteTree) {
        siteTree.setLeaf(false);
        boolean isSuccess = siteTreeDao.add(siteTree);
        return isSuccess ? CodeResult.successResult() : CodeResult.failedResult("failed");
    }

    @DeleteMapping(value = "/site/tree")
    public CodeResult delete(@RequestParam(value = "id") Long id,
                             @RequestParam(value = "parentId") Long parentId){
        siteTreeDao.delete(id, parentId);
        deleteNotLeaf(id);
        return CodeResult.successResult();
    }


    @Transactional(rollbackFor = Exception.class)
    protected void deleteNotLeaf(Long id) {
        List<SiteTree> siteTrees = siteTreeDao.getByParentId(id);
        for (SiteTree s:
                siteTrees) {
            if (!s.isLeaf()) {
                deleteNotLeaf(s.getId());
            }
            siteTreeDao.delete(s.getId(), s.getParentId());
        }
    }

    @PostMapping(value = "/site/tree/{parentId}")
    public CodeResult addAll(@PathVariable(value = "parentId") Long parentId,
                             @RequestBody List<Long> urlMd5s) {
        for (Long urlMd5:
             urlMd5s) {
            SiteTree siteTree = new SiteTree();
            siteTree.setId(urlMd5);
            siteTree.setParentId(parentId);
            Map<String, String> map = newsListDao.getByUrl(String.valueOf(urlMd5));
            if (map == null) continue;
            siteTree.setName(map.get("name"));
            siteTree.setSourceId(Math.abs(siteTree.getName().hashCode()));
            siteTree.setLeaf(true);
            try {
                siteTreeDao.add(siteTree);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return CodeResult.successResult();
    }

    @GetMapping(value = "/site/tree")
    public CodeResult getAll() {
        List<SiteTree> all = siteTreeDao.getAll();
        List<SiteTree> newTree = split(all, 0);
        return CodeResult.successResult("", newTree);
    }

    private List<SiteTree> split(List<SiteTree> origin, long parentId) {
        List<SiteTree> siteTrees = new LinkedList<>();
        for (SiteTree siteTree : origin) {
            if (siteTree.getParentId() == parentId) {
                siteTrees.add(siteTree);
                siteTree.setChildren(split(origin, siteTree.getId()));
            }
        }
        return siteTrees;
    }

}
