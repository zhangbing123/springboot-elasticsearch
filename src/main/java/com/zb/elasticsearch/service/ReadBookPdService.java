package com.zb.elasticsearch.service;

import com.github.pagehelper.PageInfo;
import com.zb.elasticsearch.common.curd.BaseService;
import com.zb.elasticsearch.dto.ReadBookPdDTO;
import com.zb.elasticsearch.entity.ReadBookPd;

import java.util.List;

/**
 * Created by Zhangbing on 2020/1/9.
 */
public interface ReadBookPdService extends BaseService<ReadBookPd,Long> {

    /**
     * 查询全部
     * @return
     */
    List<ReadBookPdDTO> findAll();

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<ReadBookPdDTO> booksByPage(Integer pageNum,Integer pageSize);

}
