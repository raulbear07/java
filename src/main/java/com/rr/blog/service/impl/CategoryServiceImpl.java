package com.rr.blog.service.impl;

import com.rr.blog.entity.Article;
import com.rr.blog.entity.Category;
import com.rr.blog.mapper.ArticleCategoryRefMapper;
import com.rr.blog.mapper.ArticleMapper;
import com.rr.blog.mapper.CategoryMapper;
import com.rr.blog.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleCategoryRefMapper articleCategoryRefMapper;
    @Override
    public Integer countCaterory() {
        return categoryMapper.countCategory();
    }

    @Override
    public List<Category> listCategory() {
        return categoryMapper.listCategory();
    }

    @Override
    public List<Category> listCategoryWithCount() {
        List<Category> list =categoryMapper.listCategory();
        try{
        for (Category category:list
             ) {
            Integer count =articleCategoryRefMapper.countArticleByCategoryId(category.getCategoryId());
            category.setArticleCount(count);
        }}
        catch (Exception e){
            e.printStackTrace();
            log.error("根据文章获得分类列表失败, cause:{}", e);
        }
        return list;
    }

    @Override
    public Category getCategoryById(Integer id) {
        Category category = null;
        try {
            category = categoryMapper.getCategoryById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据分类ID获得分类, id:{}, cause:{}", id, e);
        }
        return category;
    }

    @Override
    public void insertCategory(Category category) {
        try {
            categoryMapper.insert(category);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("创建分类失败, category:{}, cause:{}", category, e);
        }
    }

    @Override
    public void updateCategory(Category category) {
        try {
            categoryMapper.update(category);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新分类失败, category:{}, cause:{}", category, e);
        }
    }

    @Override
    public Integer countCategory() {
        return categoryMapper.countCategory();
    }

    @Override
    public void deleteCategory(Integer id) {
        try {
            categoryMapper.deleteCategory(id);
            articleCategoryRefMapper.deleteByCategoryId(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除分类失败, id:{}, cause:{}", id, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Override
    public Category getCategoryByName(String name) {
        Category category = null;
        try {
            category = categoryMapper.getCategoryByName(name);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新分类失败, category:{}, cause:{}", category, e);
        }
        return category;
    }
}
