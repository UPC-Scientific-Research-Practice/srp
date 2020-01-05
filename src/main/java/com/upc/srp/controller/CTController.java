package com.upc.srp.controller;

import com.upc.srp.dto.Response;
import com.upc.srp.service.CTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author by zhoutao
 * @description TODO
 * @date 2020/1/5 13:41
 */
@Controller
public class CTController {

    @Autowired
    CTService ctService;

    @ResponseBody
    @GetMapping("/view")
    public Response view(@RequestParam Map<String, Object> params){
        try{
            List<Map<String, Object>> result = ctService.getCT(params);
            return Response.getBuilder().setCode(200).setMessage("Success").setData(result).build();
        }catch(Exception e){
            return Response.getBuilder().setCode(400).setMessage("Error").setData("文件下载失败").build();
        }
    }
}
