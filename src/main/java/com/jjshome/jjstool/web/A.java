package com.jjshome.jjstool.web;

import com.jjshome.jjstool.service.IGenerateClassService;
import com.jjshome.jjstool.util.GenerateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Created by qhn on 2018/6/27.
 */
@Controller
@RequestMapping("tool")
public class A {

    @Resource
    IGenerateClassService codeService;

    @RequestMapping("index")
    public ModelAndView index(){
        String dataBase = GenerateUtil.getDataBase();
        List<String> tableList = null;
        try {
            tableList = GenerateUtil.getTables();
        } catch (IOException e) {
            e.printStackTrace();
        }
        codeService.batchGenerate(tableList, dataBase);
        return new ModelAndView();
    }
}
