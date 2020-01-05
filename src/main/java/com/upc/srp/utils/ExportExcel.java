package com.upc.srp.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**

 * 用poi导出Excel文件的工具类

 *

 * @author anbang

 *

 */

public class ExportExcel {

    /**

     *   用poi导出Excel文件的静态方法

     * @param list                    数据：只能是List<Map<String, Object>>类型

     * @param sheetname        Excel的sheet名字

     * @param filepath             保存文件的地址

     * @throws IOException

     */

    public void exportExcel(List<Map> list,String sheetname,String fileName,HttpServletResponse response) throws IOException {

        //新建工作簿
        HSSFWorkbook workbook=new HSSFWorkbook();

        //创建Excel的sheet
        HSSFSheet sheet=workbook.createSheet(sheetname);

        //从list任意一个Map对象里获取标题（字段名或属性名）放到sheet的第一行上，若第一条记录某字段值没有，则会没有该字段
        Map<String, Object> map=list.get(0);


        int num=0;

        HSSFRow first=sheet.createRow(0);//创建sheet的第一行

        for(String key:map.keySet()) {

            first.createCell(num).setCellValue(key);//创建num+1行并在第num+1列上赋值（字段名）

            num++;

        }



        //从list取第一行到最后一行的内容并放到对应的Excel里，若记录里某字段值没有会有问题

        int rownum=1;//行数

        for(Map<String, Object> data:list) {

            HSSFRow row=sheet.createRow(rownum);//创建sheet的第rownum+1行

            int n=0;//列数

            for(String key:data.keySet()) {
                row.createCell(n).setCellValue(data.get(key).toString());//创建n+1行并在第n+1列上赋值
                n++;
            }

            rownum++;

        }



        //1.通过IO流把数据写到文件上：只能写到服务器端

//              FileOutputStream out=new FileOutputStream(fileName);
//
//              workbook.write(out);
//
//              out.close();



        //2.响应到客户端：可以下载到客户端（两个选一个）

        try {

            this.setResponseHeader(response, fileName);

            OutputStream os = response.getOutputStream();

            workbook.write(os);

            os.flush();

            os.close();

        } catch (Exception e) {

            e.printStackTrace();

        }



    }



    // 发送响应流方法

    public void setResponseHeader(HttpServletResponse response, String fileName) {

        try {

            try {

                fileName = new String(fileName.getBytes(), "ISO8859-1");

            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();

            }

            response.setContentType("application/octet-stream;charset=ISO8859-1");

            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            response.addHeader("Pargam", "no-cache");

            response.addHeader("Cache-Control", "no-cache");

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }



}