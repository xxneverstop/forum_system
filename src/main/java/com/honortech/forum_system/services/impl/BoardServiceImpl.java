package com.honortech.forum_system.services.impl;

import com.honortech.forum_system.common.AppResult;
import com.honortech.forum_system.common.ResultCode;
import com.honortech.forum_system.dao.BoardMapper;
import com.honortech.forum_system.exception.ApplicationException;
import com.honortech.forum_system.model.Board;
import com.honortech.forum_system.services.IBoardService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BoardServiceImpl implements IBoardService {
    @Resource
    private BoardMapper boardMapper;

    @Override
    public List<Board> selectByNum(Integer num) {
        if (num <= 0) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.fail((ResultCode.FAILED_PARAMS_VALIDATE)));
        }

        List<Board> boards = boardMapper.selectByNum(num);

        return boards;
    }

    @Override
    public void addOneArticleCountById(Long id) {
        if (id == null ||  id <= 0) {
            log.warn(ResultCode.FAILED_BOARD_ARTICLE_COUNT.toString());
            throw new ApplicationException(AppResult.fail((ResultCode.FAILED_BOARD_ARTICLE_COUNT)));
        }

        Board board = boardMapper.selectByPrimaryKey(id);
        if (board == null) {
            log.warn(ResultCode.ERROR_IS_NULL.toString() + ", board id = " + id);
            throw new ApplicationException(AppResult.fail((ResultCode.ERROR_IS_NULL)));
        }

        // 新建更新类，设置需要更新的属性，调用动态更新方法
        // update
        Board updateBoard = new Board();
        updateBoard.setId(board.getId());
        updateBoard.setArticleCount(board.getArticleCount() + 1);

        int row = boardMapper.updateByPrimaryKeySelective(updateBoard);
        if (row != 1) {
            log.warn(ResultCode.FAILED.toString() + ", will affect more than 1 row");
            throw new ApplicationException(AppResult.fail(ResultCode.FAILED));
        }
    }
}
