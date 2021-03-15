package com.rr.blog.service.impl;

import com.rr.blog.entity.Link;
import com.rr.blog.mapper.LinkMapper;
import com.rr.blog.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class LinkServiceImpl implements LinkService {
    @Autowired
    private LinkMapper linkMapper;
    @Override
    public Integer countLink(Integer status) {
        return linkMapper.countLink(status);
    }

    @Override
    public List<Link> listLink(Integer status) {
        return linkMapper.listLink(status);
    }
    @Override
    public void insertLink(Link link)  {
        link.setLinkCreateTime(new Date());
        link.setLinkUpdateTime(new Date());
        linkMapper.insert(link);
    }

    @Override
    public void deleteLink(Integer id)  {
        linkMapper.deleteById(id);
    }

    @Override
    public void updateLink(Link link)  {
        link.setLinkUpdateTime(new Date());
        linkMapper.update(link);
    }

    @Override
    public Link getLinkById(Integer id)  {
        return linkMapper.getLinkById(id);
    }
}
