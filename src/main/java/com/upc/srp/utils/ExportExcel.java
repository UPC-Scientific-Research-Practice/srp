package com.upc.srp.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
     * @param filename        保存文件的地址
     * @param list            数据：只能是List<Map<String, Object>>类型
     * @param sheetname[]       Excel的sheet名字数组
     * @throws IOException
     */
    public void exportExcel(List<Map<String, Object>> list, String sheetname[], String fileName, HttpServletResponse response) throws IOException {
        //新建工作簿
        HSSFWorkbook workbook=new HSSFWorkbook();
        String [][] headers = {{"﻿分中心","住院号","姓名","住院次数","性别","年龄","出生日期","国籍","病人籍贯","民族","身份证号","职业","住址","联系电话",
                "入院途径","住院天数","入院科别","转科科别","出院科别","主要诊断","主要诊断编码","其他诊断","其他诊断编码"},
                {"发病时间","主要症状","伴随症状","既往史","药物过敏","过敏药物","吸烟史","饮酒史","个人史","家族史"},
                {"血压","意识水平","肢体运动肌力","感觉障碍","语言表达","神经反射","病理征","NIHSS","GLASGOW","ADL","MRANKIN","MMSE","LOTCA"},
                {"颅内出血部位","颅内出血体积","CT值","心脏彩超","LVEF","颈动脉彩超","胸片"},
                {"白细胞","中性细胞数","淋巴细胞数","单核细胞","红细胞数","血红蛋白","血小板"},{"白细胞","红细胞","尿蛋白","脓细胞","OB"},
                {"谷丙转氨酶","谷草转氨酶","转肽酶","总蛋白","白蛋白","总胆红素","直接胆红素","血糖","总胆固醇","甘油三脂","HDL","LDL","糖化血红蛋白","HCY","肌钙蛋白T","肌钙蛋白I","BNP","钠","钾","氯","CO2结合力"},
                {"尿素","肌酐","尿酸","T3","T4","TSH","抗甲状腺过氧化物酶抗体","抗甲状腺球蛋白抗体"},
                {"乙肝","乙肝表面抗原","乙肝表面抗体","乙肝E抗原","乙肝E抗体","乙肝核心抗体","丙肝","梅毒","HIV","PCT","CRP","脑脊液","ABETA","TLR","NLR"},
                {"手术时间","手术方式","药物治疗","并发症","颅内感染","肺炎","泌尿系感染","NIHSS","GLASGOW","ADL","MRANKIN","血压"},
                {"血压","NIHSS","GLASGOW","ADL","MRANKIN","MMSE","LOTCA"},
                {"血压","意识水平","肢体运动肌力","感觉障碍","语言表达","神经反射","病理征","NIHSS","GLASGOW","ADL","MRANKIN"},
                {"血压","意识水平","肢体运动肌力","感觉障碍","语言表达","神经反射","病理征","NIHSS","GLASGOW","ADL","MRANKIN"},
                {"血压","意识水平","肢体运动肌力","感觉障碍","语言表达","神经反射","病理征","NIHSS","GLASGOW","ADL","MRANKIN","MMSE","LOTCA"},
                {"血压","意识水平","肢体运动肌力","感觉障碍","语言表达","神经反射","病理征","NIHSS","GLASGOW","ADL","MRANKIN"},
                {"血压","意识水平","肢体运动肌力","感觉障碍","语言表达","神经反射","病理征","NIHSS","GLASGOW","ADL","MRANKIN"},
                {"血压","意识水平","肢体运动肌力","感觉障碍","语言表达","神经反射","病理征","NIHSS","GLASGOW","ADL","MRANKIN"},
                {"血压","意识水平","肢体运动肌力","感觉障碍","语言表达","神经反射","病理征","NIHSS","GLASGOW","ADL","MRANKIN"},
                {"康复锻炼","理疗","康复时间"}};
        int [] shuliang = {23,10,13,7,7,5,21,8,15,12,7,11,11,13,11,11,11,11,3};
        int rownum=1;
        for(int i = 0; i<=18; i++){
            //创建Excel的sheet
            HSSFSheet sheet = workbook.createSheet(sheetname[i]);
            int num= 0;
            HSSFRow first=sheet.createRow(0);
            for(String head:headers[i])
            {
                first.createCell(num).setCellValue(head);
                num++;
            }
        }
        for(Map<String, Object> data:list) {
            int a = 0;
            Object[] keyset= data.keySet().toArray();
            System.out.println(keyset.length);

            for(int i = 0; i<=18; i++)
            {
                HSSFSheet sheet = workbook.getSheetAt(i);
                //创建sheet的第rownum+1行
                HSSFRow row=sheet.createRow(rownum);
                //列数
                int n=0;
                for(int j = 0;j <=shuliang[i]-1; j++) {
                    //创建n+1行并在第n+1列上赋值
                    if(data.get(keyset[a]) != null)
                        row.createCell(n).setCellValue(data.get(keyset[a]).toString());
                    n++;
                    a++;
                }
            }
            rownum++;
            System.out.println(a);
        }



        //创建Excel的sheet
//        HSSFSheet sheet=workbook.createSheet(sheetname[0]);
//        //从list任意一个Map对象里获取标题（字段名或属性名）放到sheet的第一行上，若第一条记录某字段值没有，则会没有该字段
//        Map<String, Object> map=list.get(0);
//        int num=0;
//        //创建sheet的第一行
//        HSSFRow first=sheet.createRow(0);
//        for(String key:map.keySet()) {
//            //创建num+1行并在第num+1列上赋值（字段名）
//            first.createCell(num).setCellValue(key);
//            num++;
//        }
//        //从list取第一行到最后一行的内容并放到对应的Excel里，若记录里某字段值没有会有问题
//        //行数
//        int rownum=1;
//        for(Map<String, Object> data:list) {
//            //创建sheet的第rownum+1行
//            HSSFRow row=sheet.createRow(rownum);
//            //列数
//            int n=0;
//            for(String key:data.keySet()) {
//                //创建n+1行并在第n+1列上赋值
//                if(data.get(key) != null)
//                    row.createCell(n).setCellValue(data.get(key).toString());
//                n++;
//            }
//            rownum++;
//        }
        //1.通过IO流把数据写到文件上：只能写到服务器端
//        FileOutputStream out=new FileOutputStream(fileName);
//        workbook.write(out);
//        out.close();
        //2.响应到客户端：可以下载到客户端（两个选一个）
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(workbook.getBytes());
//            int len;
//            byte[] buffer = new byte[1024];
//            while ((len = byteArrayInputStream.read(buffer)) != -1){
//                os.write(buffer, 0, len);
//            }
            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    // 发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            fileName = new String(fileName.getBytes(), "ISO8859-1");
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}