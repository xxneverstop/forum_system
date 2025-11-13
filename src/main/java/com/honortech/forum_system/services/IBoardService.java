package com.honortech.forum_system.services;

import com.honortech.forum_system.model.Board;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IBoardService {
    /**
     * 查询 num 条记录
     * @param num
     * @return
     */
    List<Board> selectByNum( Integer num);

    /**
     * 更新板块中的的发帖数
     * @param id 板块id
     */
    void addOneArticleCountById (Long id);
}
