package com.chinamcloud.api.controller;

import com.chinamcloud.api.CodeResult;
import com.chinamcloud.api.dao.PageModelModuleDao;
import com.chinamcloud.api.model.PageModelModule;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
public class PageModelController {

    @Resource(name = "pageModelModuleDao")
    private PageModelModuleDao pageModelModuleDao;

    @GetMapping("/pageModel/all")
    public CodeResult getAll() {
        return CodeResult.successResult("", pageModelModuleDao.getAll());
    }

    @PostMapping("/pageModel")
    public CodeResult add(@RequestBody PageModelModule pageModelModule) {
        boolean isSuccess = pageModelModuleDao.add(pageModelModule);
        if (!isSuccess) {
            pageModelModuleDao.deleteByName(pageModelModule.getName());
            isSuccess = pageModelModuleDao.add(pageModelModule);
        }
        return isSuccess ? CodeResult.successResult() : CodeResult.failedResult("failed");
    }

}
