package com.cao.ftlword.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Company {
    @TableId
    private String uuid;   // 主键
    private String name;  // 公司名称
    private String webUrl;  // 网址
    private String createUser;  // 创始人
    private String createTime;  // 成立时间
    private String dist;  // 公司地址
    private String range;  // 经营范围
    private String about;  // 简介
}
