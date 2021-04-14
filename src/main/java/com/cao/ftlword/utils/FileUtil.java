package com.cao.ftlword.utils;



import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.UUID;


public class FileUtil {
    @Value("${uploadFilePath}")
    private String uploadFilePath;

    private FileUtil() {

    }

    /**
     * File转换为byte[]
     * @param file
     * @return
     */
    public static byte[] getBytesByFile(File file) {
        try {
            //获取输入流
            FileInputStream fis = new FileInputStream(file);

            //新的 byte 数组输出流，缓冲区容量1024byte
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            //缓存
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            //改变为byte[]
            byte[] data = bos.toByteArray();
            //
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void uploadFile(byte[] file, String filePath, String fileName) throws IOException {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            if (!targetFile.mkdirs()) {
                throw new IOException();
            }
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath + "/" + fileName);
            out.write(file);
            out.flush();
        } catch (IOException e) {
            throw new IOException();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        }
        return false;

    }

    public static String renameToUUID(String fileName) {
        return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 文件删除方法
     *
     * @param fileAddress
     * @return
     */
    public static boolean deleteQuietly(String fileAddress) {
        File file = new File(fileAddress);
        if (file == null) {
            return false;
        } else {
            try {
                if (file.isDirectory()) {
//                    cleanDirectory(file);
                }
            } catch (Exception var3) {
                ;
            }

            try {
                return file.delete();
            } catch (Exception var2) {
                return false;
            }
        }
    }

    /**
     * 下载本地文件
     * @param response
     * @param filePath
     * @param encode
     */
    public static void downloadLocal(HttpServletResponse response, String filePath,String encode) {
        response.setContentType("text/html;charset=" + encode);
        try {
            // 读到流中
            InputStream inStream = new FileInputStream(filePath); // 文件的存放路径
            // path是指欲下载的文件的路径
            File file = new File(filePath);
            // 取得文件名
            String fileName = file.getName();
            // 设置输出的格式
            response.reset();
            response.setContentType("bin");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes(encode), "ISO8859-1") + "\"");
            // 循环取出流中的数据
            byte[] b = new byte[100];
            int len;
            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 通过文件路径直接修改文件名
     *
     * @param filePath    需要修改的文件的完整路径
     * @param newFileName 需要修改的文件的名称
     * @return
     */
    public static String FixFileName(String filePath, String newFileName) {
        File f = new File(filePath);
        if (!f.exists()) { // 判断原文件是否存在（防止文件名冲突）
            return null;
        }
        newFileName = newFileName.trim();
        if ("".equals(newFileName) || newFileName == null) // 文件名不能为空
            return null;
        String newFilePath = null;
        if (f.isDirectory()) { // 判断是否为文件夹
            newFilePath = filePath.substring(0, filePath.lastIndexOf("/")) + "/" + newFileName;
        } else {
            newFilePath = filePath.substring(0, filePath.lastIndexOf("/")) + "/" + newFileName;
        }
        File nf = new File(newFilePath);
        try {
            f.renameTo(nf); // 修改文件名
        } catch (Exception err) {
            err.printStackTrace();
            return null;
        }
        return newFilePath;
    }

    /*
     *写文件
     */
    public static boolean write(String filePath, String fileName, String fileContent) throws IOException {
        boolean result = false;
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            if (!targetFile.mkdirs()) {
                throw new IOException();
            }
        }

        try {
            FileOutputStream fs = new FileOutputStream(filePath+"/"+fileName);
            //byte[] b = fileContent.getBytes();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fs, "UTF-8"));
          /*

            带bom的utf8

            fs.write( 0xef );

            fs.write( 0xbb);

            fs.write( 0xbf);*/
            writer.write(fileContent);
            writer.flush();
            writer.close();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    //需要注意的是当删除某一目录时，必须保证该目录下没有其他文件才能正确删除，否则将删除失败。
    public static boolean  deleteFolder(File folder) throws Exception {
        boolean flag = false;
        if (!folder.exists()) {
            throw new Exception("文件不存在");
        }
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    //递归直到目录下没有文件
                    deleteFolder(file);
//                    System.err.println("删除目录下所有文件");
                    flag=true;
                } else {
                    //删除
                    file.delete();
//                    System.err.println("删除文件夹里面的文件");
                    flag=true;
                }
            }
        }
        //删除
        folder.delete();
        return flag;
    }

    public static void downloadFile(HttpServletResponse resp, String downloadPath) throws IOException {
        String filePath = null;
        try {

            filePath= URLDecoder.decode(downloadPath,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //String realPath = "D:" + File.separator + "apache-tomcat-8.5.15" + File.separator + "files";
        String realPath=filePath;//项目路径下你存放文件的地方
        File file = new File(realPath);
        if(!file.exists()){
            throw new IOException("文件不存在");
        }
        String name = new String(file.getName().getBytes("GBK"), "ISO-8859-1");
        resp.reset();
        /*
         * 跨域配置
         * */
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");

        resp.setContentType("application/octet-stream");
        resp.setCharacterEncoding("utf-8");
        resp.setContentLength((int) file.length());
        resp.setHeader("Content-Disposition", "attachment;filename=" + name);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = resp.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
