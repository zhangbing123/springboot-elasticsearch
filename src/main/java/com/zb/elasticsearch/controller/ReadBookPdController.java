package com.zb.elasticsearch.controller;

import com.github.pagehelper.PageInfo;
import com.zb.elasticsearch.common.dto.PageRecordDTO;
import com.zb.elasticsearch.common.result.Result;
import com.zb.elasticsearch.dto.ReadBookPdDTO;
import com.zb.elasticsearch.service.ReadBookPdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Zhangbing on 2020/1/9.
 */
@Slf4j
@RequestMapping(value = "/book")
@RestController
public class ReadBookPdController {

    @Autowired
    private ReadBookPdService readBookPdService;

    @GetMapping(value = "/books")
    public Result<List<ReadBookPdDTO>> findAll() {
        List<ReadBookPdDTO> allBooks = readBookPdService.findAll();
        return Result.ok(allBooks, "查询成功");
    }

    @GetMapping(value = "/books/page")
    public Result<PageInfo<ReadBookPdDTO>> booksByPage(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return Result.ok(readBookPdService.booksByPage(pageNum,pageSize),"查询成功");
    }
}
