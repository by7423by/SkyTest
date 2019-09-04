package com.sky;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Junit {
    @Test
    public void test1(){
        String str = "001001003";
        String[] split = str.split("");
        List num = new ArrayList();
        StringBuilder sb = new StringBuilder();
        for (String s : split) {

            sb.append(s);
            if (sb.length()==3){
                num.add(sb.toString());
                sb.delete(0,4);
            }
        }
        System.out.println(num);
    }

    @Test
    public void test2(){
        List<String> sqlData = new ArrayList<>();
        List<String> shopIds = new ArrayList<>();
        try {
            File csv = new File("C:/Users/97916/Desktop/123.csv");
            BufferedReader br = new BufferedReader(new FileReader(csv));


            String line = "";
            String itemUpdate ="";
            while ((line = br.readLine()) != null) {    //判断文件是否结束
//                String item[] = line.split(",");      //csv 逗号分隔
                String item[] = line.split("\t");
                for (String s : item) {
                    System.out.println(s);
                }


                itemUpdate =
                        "update DPPresales.PS_LeadShop  set CityId = "+item[1]+" , DistrictId = "+item[2]+" , ShopTypeId = "+item[3]+" where ShopId = "+item[0]+";\t";


                System.out.println(itemUpdate);
                sqlData.add(itemUpdate);
            }


            FileWriter fw = null;
            BufferedWriter bw = null;
            try {                        //文件写入
                fw = new FileWriter("C:/Users/97916/Desktop/Result.txt", true);
                bw = new BufferedWriter(fw, 100);
                for (String string:sqlData){
                    bw.write(string);


                }
            }catch (IOException e){


                System.out.println("写入文件出错");
            }finally {
                if (bw != null){
                    bw.flush();
                    bw.close();
                }
                if (fw!=null)
                    fw.close();
            }

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
