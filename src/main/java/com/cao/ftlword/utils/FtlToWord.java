package com.cao.ftlword.utils;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 
* <p>Title: ftl模板文件转成word文件下载</p>
* <p>Description: </p>
* <p>Company: 常州培涵信息科技有限公司</p>
* <p>使用说明: 
* 		1.对于简单文件下载 只需要在map里面put
* 		2.对于附属图片的需要调用makeImagePathToBASE64Encoder进行编码
* 		3.对于循环需要使用List<Object> 此处Object为业务对象
* </p>
* @author zhanghb
* @date 2018年2月2日
 */
@Component
public class FtlToWord {

	private static Configuration configuration = null;
	private static Map<String, Template> allTemplates = null;

	/**
	 * 配置模板
	 * @param fileName
	 * @param filePath
	 */
	public static void configure(String fileName, String filePath) {
		configuration = new Configuration();  
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassForTemplateLoading(FtlToWord.class, filePath);
		allTemplates = new HashMap<String, Template>(); // Java 7 钻石语法
		try {
			allTemplates.put(fileName, configuration.getTemplate(fileName + ".ftl"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * 创建文档
	 * @param dataMap
	 * @param templateName
	 * @return
	 */
	public static File createDoc(Map<?, ?> dataMap, String templateName) {
		String name = "temp" + (int) (Math.random() * 100000) + ".doc";
		File f = new File(name);
		Template t = allTemplates.get(templateName);
		try {
			// 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开
			Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
			t.process(dataMap, w);
			w.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return f;
	}
	/**
	 * 下载文档
	 * @param response
	 * @param dataMap
	 * @param templateName
	 * @param downloadName
	 * @throws IOException
	 */
	public static void downloadDoc(HttpServletResponse response, Map<?, ?> dataMap, String templateName, String downloadName) throws IOException {
		 	File file = null;  
	        InputStream fin = null;  
	        ServletOutputStream out = null;
	        try {  
	            // 调用工具类WordGenerator的createDoc方法生成Word文档 
	        	file = FtlToWord.createDoc(dataMap,templateName);  
	            fin = new FileInputStream(file);  
	            response.setCharacterEncoding("utf-8");  
	            response.setContentType("application/msword");  
	            // 设置浏览器以下载的方式处理该文件默认名为resume.doc  
	            response.addHeader("Content-Disposition", "attachment;filename="+downloadName+".doc");
	            out = response.getOutputStream();  
	            byte[] buffer = new byte[512];  // 缓冲区  
	            int bytesToRead = -1;  
	            // 通过循环将读入的Word文件的内容输出到浏览器中  
	            while((bytesToRead = fin.read(buffer)) != -1) {  
	                out.write(buffer, 0, bytesToRead);  
	            }  
	        } finally {  
	            if(fin != null) fin.close();  
	            if(out != null) out.close();  
	            if(file != null) file.delete(); // 删除临时文件  
	        }  
	}
	public static String makeImagePathToBASE64Encoder(String imagePath) throws IOException {
		/** 将图片转化为**/
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(imagePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(in != null){
                in.close();
            }
        }
        /** 进行base64位编码  只支持Java8**/
        //Encoder encoder = Base64.getEncoder();
       // String encodeToString = encoder.encodeToString(data);
        String encodeToString = Base64.encodeBase64String(data);
		return encodeToString;
	}

	/**
	 * 下载文档存到本地
	 * @param dataMap
	 * @param templateName
	 * @param downloadName
	 * @throws IOException
	 */
	public static void downloadDocLocal(Map<?, ?> dataMap, String templateName, String downloadName,String path,String uuid) throws IOException {
		File file = null;
		InputStream fin = null;
		// 存到本地
		OutputStream os = null;
		try {
			// 调用工具类WordGenerator的createDoc方法生成Word文档
			file = FtlToWord.createDoc(dataMap,templateName);
			fin = new FileInputStream(file);
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流保存到本地文件
			File tempFile = new File(path);
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}
			downloadName = new String(downloadName.getBytes("ISO-8859-1"), "utf-8");
			os = new FileOutputStream(tempFile.getPath() + File.separator + downloadName+".doc");
			// 开始读取,存到服务器
			while ((len = fin.read(bs)) != -1) {
				os.write(bs, 0, len);
			}

		} finally {
			if(fin != null) fin.close();
			if(os != null) os.close();
//			if(out != null) out.close();
			if(file != null) file.delete(); // 删除临时文件
		}

	}

	public static void wordToPdf(String fileName,String fileNameToDown){
		Document doc = new Document(fileName);
		doc.saveToFile( fileNameToDown, FileFormat.PDF);
	}
}