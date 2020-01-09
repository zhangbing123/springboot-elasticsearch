package com.zb.elasticsearch.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zb.elasticsearch.common.curd.BaseMapper;
import com.zb.elasticsearch.common.curd.BaseServiceImpl;
import com.zb.elasticsearch.common.dto.PageRecordDTO;
import com.zb.elasticsearch.common.utils.ListUtils;
import com.zb.elasticsearch.dto.ReadBookPdDTO;
import com.zb.elasticsearch.entity.ReadBookPd;
import com.zb.elasticsearch.mapper.ReadBookPdMapper;
import com.zb.elasticsearch.service.ReadBookPdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Zhangbing on 2020/1/9.
 */
@Transactional
@Slf4j
@Service
public class ReadBookPdServiceImpl extends BaseServiceImpl<ReadBookPd, Long > implements ReadBookPdService {

    @Autowired
    private ReadBookPdMapper readBookPdMapper;

    @Override
    public BaseMapper<ReadBookPd, Long> getMapper() {
        return readBookPdMapper;
    }

    @Override
    public List<ReadBookPdDTO> findAll() {
        List<ReadBookPd> readBookPds = super.selectAll();

        return ListUtils.listCopyOtherList(ReadBookPdDTO.class,readBookPds);
    }

    @Override
    public PageInfo<ReadBookPdDTO> booksByPage(Integer pageNum, Integer pageSize) {

        //设置分页查询的参数 第几页  每页大小
        PageHelper.startPage(pageNum,pageSize);

        List<ReadBookPdDTO> bookPdDTOS = this.findAll();

        //查询数量
        int count = super.selectCount(null);

        return new PageRecordDTO<>(bookPdDTOS,count);
    }
}
