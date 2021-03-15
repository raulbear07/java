package com.rr.blog.service;

import com.rr.blog.entity.Tag;

import java.util.List;

public  interface  TagService {
    Integer countTag();
    List<Tag> listTag();
    List<Tag> listTagWithCount();
    /**
     * 根据id获得标签信息
     *
     * @param id 标签ID
     * @return 标签
     */
    Tag getTagById(Integer id) ;

    /**
     * 添加标签
     *
     * @param tag 标签
     * @return 标签
     */
    Tag insertTag(Tag tag) ;

    /**
     * 修改标签
     *
     * @param tag 标签
     */
    void updateTag(Tag tag) ;

    /**
     * 删除标签
     *
     * @param id 标签iD
     */
    void deleteTag(Integer id) ;

    /**
     * 根据标签名获取标签
     *
     * @param name 标签名称
     * @return 标签
     */
    Tag getTagByName(String name) ;

    /**
     * 根据文章ID获得标签
     *
     * @param articleId 文章ID
     * @return 标签列表
     */
    List<Tag> listTagByArticleId(Integer articleId);

}
