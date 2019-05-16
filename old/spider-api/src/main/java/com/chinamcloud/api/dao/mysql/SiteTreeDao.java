package com.chinamcloud.api.dao.mysql;

import com.chinamcloud.api.model.SiteTree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SiteTreeDao {

    List<SiteTree> getByParentId(@Param("parentId") Long parentId);

    boolean add(SiteTree siteTree);

    boolean delete(@Param("id") Long id, @Param("parentId") Long parentId);

    List<SiteTree> getAll();


}
