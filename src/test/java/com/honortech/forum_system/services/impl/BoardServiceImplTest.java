package com.honortech.forum_system.services.impl;

import com.honortech.forum_system.model.Board;
import com.honortech.forum_system.services.IBoardService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceImplTest {
    @Resource
    private IBoardService  boardService;

    @Test
    void selectByNum() {
        List<Board> borads = boardService.selectByNum(1);
        System.out.println(borads);
    }

    @Test
    @Transactional
    void addOneArticleCountById() {
        boardService.addOneArticleCountById(1L);
        System.out.println("update success");
    }
}