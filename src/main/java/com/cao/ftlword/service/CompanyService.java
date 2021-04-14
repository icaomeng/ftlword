package com.cao.ftlword.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface CompanyService {

    // 新增工程与附件
    void insertCompanyAndAttach(Map<String,Object> info,  HttpServletRequest request);

    // 导出word
    void wordDownload(String uuid, HttpServletResponse response);
}
