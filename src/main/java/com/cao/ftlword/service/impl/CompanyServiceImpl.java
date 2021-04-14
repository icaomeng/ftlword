package com.cao.ftlword.service.impl;

import com.alibaba.fastjson.JSON;
import com.cao.ftlword.entity.Company;
import com.cao.ftlword.mapper.CompanyMapper;
import com.cao.ftlword.service.CompanyService;
import com.cao.ftlword.utils.CommUtils;
import com.cao.ftlword.utils.FileUtil;
import com.cao.ftlword.utils.FtlToWord;
import com.cao.ftlword.vo.WordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;
    @Value("${uploadFilePath}")
    private String uploadFilePath;

    @Override
    public void insertCompanyAndAttach(Map<String, Object> info, HttpServletRequest request) {
// 新增项目与文件
        info.put("uuid", CommUtils.getUUID());
        String projId = info.get("uuid") + "";

        // 项目基本信息入库
        companyMapper.insertCompany(info);

        // 处理文件的map
        Map<String, Object> map = new HashMap<>();
        //处理附件
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("files");
        //上传图片
        try {
            if (!files.isEmpty()) {
                for (int i = 0; i < files.size(); i++) {
                    MultipartFile file = files.get(i);
                    //获取文件全称 包含后缀
                    String fName = file.getOriginalFilename();
                    //截取文件后缀
                    String attachId = CommUtils.getUUID();
                    map.put("uuid", CommUtils.getUUID());
                    map.put("attach_path", "/word/" + projId + "/" + attachId + "/" + fName);
                    map.put("comId", projId);
                    // 存到数据库中
                    companyMapper.insertCompanyAttach(map);
                    // 上传文件到tomcat
                    FileUtil.uploadFile(file.getBytes(), uploadFilePath + "/word/" + projId + "/" + attachId,
                            fName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void wordDownload(String uuid, HttpServletResponse response) {
        // 1.根据公司id获取到详情,转化为map
        Company company = companyMapper.selectCompany(uuid);
        Map<String, Object> projectBaseInfo = JSON.parseObject(JSON.toJSONString(company), Map.class);
        // 2.根据公司id获取到相关图片
        List<Map<String, Object>> list = companyMapper.selectCompanyAttach(uuid);
        // 3.存储到图片的路径集合
        List<WordVo> image = new ArrayList<>();
        // 4.循环获取
        for (int i = 0; i < list.size(); i++) {
            WordVo doc=new WordVo();
            String attach_path = list.get(i).get("attach_path") + "";
            String replace = attach_path.replace("\\", "\\");
            try {
                doc.setImage(FtlToWord.makeImagePathToBASE64Encoder(uploadFilePath+ File.separator+list.get(i).get("attach_path")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            image.add(doc);
        }

        //----------------------------------------------------------------
        projectBaseInfo.put("image", image);
        //将信息添加到Map里,附件,配置模板,配置模板
        String fileName = "简介";
        String filePath = "/templates";
        FtlToWord.configure(fileName, filePath);
        //下载word
        String templateName = "简介";
        String downloadName = projectBaseInfo.get("name")+"简介";
        try {
            downloadName = new String(downloadName.getBytes("utf-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            FtlToWord.downloadDoc(response, projectBaseInfo, templateName, downloadName);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
