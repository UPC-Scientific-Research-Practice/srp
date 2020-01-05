package com.upc.srp.controller;

import javax.servlet.http.HttpServletResponse;
import com.upc.srp.dto.Response;
import com.upc.srp.service.PreOperationService;
import com.upc.srp.utils.ExportExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author by anbang
 * @description 信息控制器
 * @date 2019/12/26 16:12
 */
@Controller
public class PreOperationController {

    @Autowired
    PreOperationService preService;

    @ResponseBody
    @PostMapping("/pre/selectbyno") //根据主键 住院号 查询所有信息
    public Response getpre(@RequestBody Map map){
        Map data = preService.selectbyno(map.get("no").toString());
        if(data!=null){
            return Response.getBuilder()
                    .setCode(200)
                    .setMessage("success")
                    .setData(data).build();
        }else{
            return Response.getBuilder()
                    .setCode(400)
                    .setMessage("查无此人").build();
        }
    }

    @ResponseBody
    @GetMapping("/pre/selectjiben") //返回基本信息的 List<Map>
    public Response getjiben(){
        List<Map> data = preService.selectjiben();
        if(data!=null){
            return Response.getBuilder()
                    .setCode(200)
                    .setMessage("success")
                    .setData(data).build();
        }else{
            return Response.getBuilder()
                    .setCode(400)
                    .setMessage("查无此人").build();
        }
    }

    @ResponseBody
    @PostMapping("/pre/insert")  //插入信息
    public Response insertpre(@RequestBody Map data){
        int sucess = preService.insertdata(data);
        System.out.println(sucess);
        if(sucess==1){
            return Response.getBuilder()
                    .setCode(200)
                    .setMessage("插入success").build();
        }else{
            return Response.getBuilder()
                    .setCode(400)
                    .setMessage("error").build();
        }
    }


    @ResponseBody
    @PostMapping("/pre/exportexcel") //导出excel， 传入 List<String>
    public Response excel(@RequestBody List<String> list, HttpServletResponse response){

        ExportExcel ee = new ExportExcel();
        int sucsess=0;
        List<Map> data = preService.selectbynolist(list);

        try {
            ee.exportExcel(data,"资料","导出信息.xls",response);
            sucsess=1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(sucsess==1){
            return Response.getBuilder()
                    .setCode(200)
                    .setMessage("导出成功")
                    .setData(data).build();
        }else{
            return Response.getBuilder()
                    .setCode(400)
                    .setMessage("导出失败").build();
        }
    }



}