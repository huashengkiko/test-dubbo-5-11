package com.deepexi.controller;

import com.deepexi.model.vo.DemoVO;
import com.deepexi.model.query.ValidDemoQuery;
import com.deepexi.util.ValidationUtils;
import com.deepexi.exception.common.DataExistException;
import com.deepexi.extension.web.Payload;
import com.deepexi.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import javax.validation.Valid;

import static com.deepexi.util.ConverterUtils.convert;
import com.deepexi.model.vo.OtherVO;

@RestController
@RequestMapping("demo")
@Payload
public class DemoController {

    @Autowired
    private DemoService service;
    
    
    @GetMapping("default-convert")
    public DemoVO defaultConvert() {
        return convert(service.get(),DemoVO.class);
    }

    @GetMapping("manual-convert")
    public OtherVO manualConvert() {
        return convert(service.get(),OtherVO.class);
    }
            
    @GetMapping("greeting")
    public String sayHello() {
        return service.sayHello();
    }

    @GetMapping("biz-error")
    public void bizerror() {
        throw new DataExistException("Demo数据已存在");
    }

    @GetMapping("valid")
    public String valid(@Valid ValidDemoQuery query, BindingResult result) {
        BindingResult errors = ValidationUtils.validate(query, ValidDemoQuery.Role.class);
        if (result.hasErrors() || errors.hasErrors()) {
            return "fail";
        }
        return "success";
    }

}
