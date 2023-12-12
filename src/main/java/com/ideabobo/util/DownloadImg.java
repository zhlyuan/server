package com.ideabobo.util;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class DownloadImg {


    /**
     * 文件下载到指定路径
     *
     * @param urlString 链接
     * @param savePath  保存路径
     * @param filename  文件名
     * @throws Exception
     */
    public static void download(String urlString, String savePath, String filename) throws IOException {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为20s
        con.setConnectTimeout(20 * 1000);
        //文件路径不存在 则创建
        File sf = new File(savePath);
        if (!sf.exists()) {
            sf.mkdirs();
        }
        //jdk 1.7 新特性自动关闭
        try (InputStream in = con.getInputStream();
             OutputStream out = new FileOutputStream(sf.getPath() + "\\" + filename)) {
            //创建缓冲区
            byte[] buff = new byte[1024];
            int n;
            // 开始读取
            while ((n = in.read(buff)) >= 0) {
                out.write(buff, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) throws Exception {
        downloadImg2path("http://tmp/GSYt2XIwxUsP3452825e3acfd427fbc504f464be1a87.jpeg");
    }

    public static String downloadImg2path(String url){
        String fileName = UUID.randomUUID()+".png";
        try {
            String path = ResourceUtils.getURL("classpath:").getPath();
            String tagetPath = path+"static/upload/";
            String preplace = "target/classes";
            String targetSrcPath = tagetPath.replace(preplace,"src/main/resources");

            download(url,targetSrcPath,fileName);
            download(url,tagetPath,fileName);
            return fileName;
        } catch (Exception e){
            return null;
        }

    }

}
