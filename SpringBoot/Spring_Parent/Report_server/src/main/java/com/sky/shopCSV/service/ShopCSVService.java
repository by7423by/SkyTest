package com.sky.shopCSV.service;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopCSVService {

    /**
     * 根据上传的CSV文件生成sql语句
     *
     * @param file 上传的文件
     */
    public void ShopCSV(MultipartFile file) {
        List<String> sqlData = new ArrayList();
        String fileName = null;

        //自定义一个异常检测统一异常处理
//        int x=1/0;
        

        try {
            //获取文件内容
            String str = new String(file.getBytes(), "GBK");
            //根据换行符号拆分行
            String[] csv = str.split("\n");
            for (String s : csv) {
                System.out.println("内容：" + s);
            }
            //获取文件名字，由于上传的文件固定为*.csv文件，所以获取文件名方式写死
            String originalFilename = file.getOriginalFilename();//123.csv
            fileName = originalFilename.substring(0, originalFilename.length() - 4);//123

            //这一部分是建表语句的生成 字符串拼接 动态sql语句生成
            StringBuilder sb = new StringBuilder("CREATE TABLE ");
            String[] title = csv[0].split(",");
            sb.append(fileName + " \n")
                    .append("  ( \n");
            for (String s : title) {
                if (s.contains("\r")) {
                    String[] split = s.split("\r");
                    sb.append("     ").append(split[0]).append("    nvarchar(32), \n");
                    continue;
                }
                sb.append("     ").append(s).append("    nvarchar(32), \n");
            }
            sb.append("  );\n\n ");
            System.out.println(sb.toString());
            sqlData.add(sb.toString());

//            String createTable ="CREATE TABLE student \n" +
//                    "  ( \n" +
//                    "     s#    INT, \n" +
//                    "     sname nvarchar(32), \n" +
//                    "     sage  INT, \n" +
//                    "     ssex  nvarchar(8) \n" +
//                    "  ) ";


            //这部分为插入语句的生成
            //INSERT INTO 表名称 VALUES (值1, 值2,....)
            for (int i = 1; i < csv.length; i++) {
                StringBuilder insert = new StringBuilder("insert into ");
                insert.append(fileName).append(" values (");
                String[] values = csv[i].split(",");
                for (int j = 0; j < values.length; j++) {
                    if (values[j].contains("\r")) {
                        String[] split = values[j].split("\r");
                        values[j] = split[0];
                    }
                    insert.append(values[j]);
                    if (j < values.length - 1) {
                        insert.append(",");
                    } else if (j == values.length - 1) {
                        insert.append(");\n\n");
                    }
                }
                System.out.println(insert);
                sqlData.add(insert.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //输出到目标文件中
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {                        //文件写入
            fw = new FileWriter("E:/"+fileName+".sql", true);
            bw = new BufferedWriter(fw, 100);
            for (String string : sqlData) {
                bw.write(string);
            }
        } catch (IOException e) {
            System.out.println("写入文件出错");
        } finally {
            if (bw != null) {
                try {
                    bw.flush();
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

}
}


