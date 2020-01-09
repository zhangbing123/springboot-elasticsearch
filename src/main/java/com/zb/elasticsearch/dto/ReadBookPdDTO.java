package com.zb.elasticsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by Zhangbing on 2020/1/9.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReadBookPdDTO {

    private Long id;

    private String name;

    @Column(name = "en_name")
    private String enName;

    private String author;

    private String imgurl;

    @Column(name = "create_time")
    private Date createTime;

    private Integer creator;

    @Column(name = "update_name")
    private Date updateTime;

    private Integer status;

    @Column(name = "comment_num")
    private Integer commentNum;

    private Integer price;

    private String category;

    private String discription;
}
