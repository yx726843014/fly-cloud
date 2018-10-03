package com.fly.controller;

import com.fly.service.GenerateService;
import com.fly.task.GenerateTask;
import com.fly.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;


/**
 * 代码生成控制器
 */
@RestController
@RequestMapping("/generator")
public class GeneratorController {

    @Autowired
    private GenerateService generateService;



    /**
     * 生成代码
     */
    @PostMapping("/generate")
    public Object generate(GenerateTask generateTask) throws SQLException, ClassNotFoundException {
        generateService.generate(generateTask);
        return Result.buildSuccess(null);
    }
}
