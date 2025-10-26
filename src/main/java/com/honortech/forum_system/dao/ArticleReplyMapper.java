package com.honortech.forum_system.dao;

import com.honortech.forum_system.model.ArticleReply;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleReplyMapper {
    int insert(ArticleReply row);

    int insertSelective(ArticleReply row);

    ArticleReply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleReply row);

    int updateByPrimaryKey(ArticleReply row);
}