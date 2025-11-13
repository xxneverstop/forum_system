package com.honortech.forum_system.controller;

import com.honortech.forum_system.common.AppResult;
import com.honortech.forum_system.model.Board;
import com.honortech.forum_system.services.IBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@Tag(name = "板块接口", description = "用于测试板块功能")
@Slf4j
@RequestMapping("/board")
@RestController
public class BoardController {
    // 如果没配置，默认为9
    @Value("${honortech.index.board_num:9}")
    private Integer indexBoardNum;

    @Resource
    private IBoardService iBoardService;

    @Operation( summary = "获取板块",
            description = "获取指定数量的板块"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "获取成功"),
            @ApiResponse(responseCode = "400", description = "参数校验失败")
    })
    @GetMapping("/topList")
    public AppResult<List<Board>> topList() {
        log.info("board num: " +  indexBoardNum);

        List<Board> boards = iBoardService.selectByNum(indexBoardNum);
        if (boards == null) {
            boards = new ArrayList<>();
        }

        return AppResult.success(boards);
    }
}
