package com.zb.elasticsearch.common.dto;


import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author: zhangbing
 * @Descritioon: 分页结果类
 **/

public class PageRecordDTO<T> extends PageInfo<T> {

     public PageRecordDTO(List<T> list, int totalCount){
        super(list);
        super.total = totalCount;
        int pageSize = super.getPageSize();
        if(totalCount % pageSize == 0){
            setPages(totalCount / pageSize);
        }else{
            setPages(totalCount / pageSize + 1);
        }
    }
}

