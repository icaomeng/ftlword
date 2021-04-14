package com.cao.ftlword.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cao.ftlword.entity.Company;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface CompanyMapper extends BaseMapper<Company> {

    // 新增公司
    void insertCompany(Map<String,Object> map);

    // 新增附件
    void insertCompanyAttach(Map<String,Object> map);

    // 根据公司id获取到详情
    Company selectCompany(@Param("uuid")String uuid);

    // 根据公司id获取到相关图片
    List<Map<String, Object>>  selectCompanyAttach(@Param("uuid")String uuid);


}
