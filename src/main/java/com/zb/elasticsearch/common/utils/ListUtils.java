package com.zb.elasticsearch.common.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 集合转集合
 * @author: zhangbing
 * @create: 2019-12-11 16:10
 **/
@Log4j2
public class ListUtils {

    private ListUtils() {
    }

    /**
     * list to list
     * @param target 目标list的类型
     * @param sourceList 源list
     * @param <T> 源list中数据类型
     * @param <E> 目标数据类型
     * @return
     */
    public static <T,E> List<E> listCopyOtherList(Class<E> target,List<T> sourceList){
        if (!CollectionUtils.isEmpty(sourceList)){

            List<E> list = new ArrayList<>(sourceList.size());
            for (T source : sourceList) {
                try {
                    E targetClass = target.newInstance();
                    BeanUtils.copyProperties(source,targetClass);
                    list.add(targetClass);
                } catch (InstantiationException e) {
                    log.info("InstantiationException ERROR",e);
                } catch (IllegalAccessException e) {
                    log.info("IllegalAccessException ERROR",e);
                }
            }
            return list;
        }else {
            return null;
        }

    }
}
