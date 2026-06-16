package com.baseline.excel;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;


import java.util.ArrayList;
import java.util.List;

@Configuration
public class ExcelWebConfig implements InitializingBean {
    private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    public ExcelWebConfig(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
    }

    @Override
    public void afterPropertiesSet() {


        List<HandlerMethodReturnValueHandler> returnValueHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
        ExcelProcessor excelProcessor = new ExcelProcessor();
        List<HandlerMethodReturnValueHandler> customReturnValueHandlers = new ArrayList<>();
        customReturnValueHandlers.add(excelProcessor);
        assert returnValueHandlers != null;
        customReturnValueHandlers.addAll(returnValueHandlers);
        requestMappingHandlerAdapter.setReturnValueHandlers(customReturnValueHandlers);


        List<HandlerMethodArgumentResolver> argumentResolvers = requestMappingHandlerAdapter.getArgumentResolvers();
        ImportExcelResolver importExcelResolver = new ImportExcelResolver();
        List<HandlerMethodArgumentResolver> customerArgumentResolvers=new ArrayList<>();
        customerArgumentResolvers.add(importExcelResolver);
        assert argumentResolvers != null;
        customerArgumentResolvers.addAll(argumentResolvers);
        requestMappingHandlerAdapter.setArgumentResolvers(customerArgumentResolvers);
    }

}
