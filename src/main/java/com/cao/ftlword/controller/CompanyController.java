package com.cao.ftlword.controller;

import com.cao.ftlword.service.CompanyService;
import com.cao.ftlword.utils.AjaxObj;
import com.cao.ftlword.utils.JsonUtil;
import com.cao.ftlword.utils.ReturnValCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    // 新增方案与附件，修改方案与附件，根据uuid判断
    @PostMapping("/insertCompany")
    public AjaxObj insertProject(String info,  HttpServletRequest request) {
        Map<String, Object> infoMap = JsonUtil.convertJsonStrToMap(info);
        companyService.insertCompanyAndAttach(infoMap,request);
        return new AjaxObj(ReturnValCode.RTN_VAL_CODE_SUCCESS, "请求成功");
    }

    @GetMapping("/wordDownload")
    public void wordDownload(String uuid,
                             HttpServletResponse response) throws Exception {
        companyService.wordDownload(uuid,response);
    }
}
