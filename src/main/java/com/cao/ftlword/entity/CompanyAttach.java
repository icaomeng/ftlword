package com.cao.ftlword.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class CompanyAttach {
    @TableId
    private String uuid;   // 主键
    private String attachPath;  // 附件路径
    private String comId;  // 公司名称
}
