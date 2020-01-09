package com.zb.elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "read_book_pd")
public class ReadBookPd {
    @Id
    private Long id;

    private String name;

    @Column(name = "en_name")
    private String enName;

    private String author;

    private String imgurl;

    @Column(name = "create_time")
    private Date createTime;

    private Integer creator;

    @Column(name = "update_time")
    private Date updateTime;

    private Integer status;

    @Column(name = "comment_num")
    private Integer commentNum;

    private Integer price;

    private String category;

    private String discription;
}