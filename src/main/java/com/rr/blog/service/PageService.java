package com.rr.blog.service;

import com.rr.blog.entity.Page;

import java.util.List;

public interface PageService {
    List<Page> listPage(Integer status);
    Page getPageByKey(Integer status,String key);
    Page getPageById(Integer id);
    /**
     * 添加页面
     *
     * @param page 页面
     */
    void insertPage(Page page);

    /**
     * 删除页面
     *
     * @param id 页面ID
     */
    void deletePage(Integer id);

    /**
     * 编辑页面
     *
     * @param page 分页
     */
    void updatePage(Page page);
}
