package com.rr.blog.service.impl;

import com.rr.blog.entity.Page;
import com.rr.blog.enums.PageStatus;
import com.rr.blog.mapper.PageMapper;
import com.rr.blog.service.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@Slf4j
public class PageServiceImpl implements PageService {
    @Autowired
    private PageMapper pageMapper;
    @Override
    public List<Page> listPage(Integer status) {
        return pageMapper.listPage(status);
    }

    @Override
    public Page getPageByKey(Integer status, String key) {
        return pageMapper.getPageByKey(status,key);
    }

    @Override
    public Page getPageById(Integer id) {
        return pageMapper.getPageById(id);
    }

    @Override
    public void insertPage(Page page) {
        page.setPageCreateTime(new Date());
        page.setPageUpdateTime(new Date());
        page.setPageStatus(PageStatus.NORMAL.getValue());
        pageMapper.insert(page);
    }

    @Override
    public void deletePage(Integer id) {
        pageMapper.deleteById(id);

    }

    @Override
    public void updatePage(Page page) {
        page.setPageUpdateTime(new Date());
        pageMapper.update(page);

    }
}
