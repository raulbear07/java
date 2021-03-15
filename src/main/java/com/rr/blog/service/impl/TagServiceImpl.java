package com.rr.blog.service.impl;

import com.rr.blog.entity.Tag;
import com.rr.blog.mapper.ArticleCategoryRefMapper;
import com.rr.blog.mapper.ArticleTagRefMapper;
import com.rr.blog.mapper.TagMapper;
import com.rr.blog.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
@Slf4j
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleTagRefMapper articleTagRefMapper;
    @Override
    public Integer countTag() {
        return tagMapper.countTag();
    }

    @Override
    public List<Tag> listTag() {
        return tagMapper.listTag();
    }

    @Override
    public List<Tag> listTagWithCount() {
        List<Tag> tagList=null;
        try{
            tagList=tagMapper.listTag();
            for (Tag tag:tagList
                 ) {
                Integer count =articleTagRefMapper.countArticleByTagId(tag.getTagId());
                tag.setArticleCount(count);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            log.error("查询标签及其文章数量,cause：{}",e);
        }
        return null;
    }

    @Override
    public Tag getTagById(Integer id) {
        Tag tag = null;
        try {
            tag = tagMapper.getTagById(id);
        } catch (Exception e) {            e.printStackTrace();
            log.error("根据ID获得标签失败, id:{}, cause:{}", id, e);
        }
        return tag;
    }

    @Override
    public Tag insertTag(Tag tag) {
        try {
            tagMapper.insert(tag);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加标签失败, tag:{}, cause:{}", tag, e);
        }
        return tag;
    }

    @Override
    public void updateTag(Tag tag) {
        try {
            tagMapper.update(tag);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新标签失败, tag:{}, cause:{}", tag, e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTag(Integer id) {
        try {
            tagMapper.deleteById(id);
            articleTagRefMapper.deleteByTagId(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除标签失败, id:{}, cause:{}", id, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Override
    public Tag getTagByName(String name) {
        Tag tag = null;
        try {
            tag = tagMapper.getTagByName(name);
        } catch (Exception e) {            e.printStackTrace();
            log.error("根据名称获得标签, name:{}, cause:{}", name, e);
        }
        return tag;
    }

    @Override
    public List<Tag> listTagByArticleId(Integer articleId) {
        List<Tag> tagList = null;
        try {
            tagList = articleTagRefMapper.listTagByArticleId(articleId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据文章ID获得标签失败，articleId:{}, cause:{}", articleId, e);
        }
        return tagList;
    }
}
