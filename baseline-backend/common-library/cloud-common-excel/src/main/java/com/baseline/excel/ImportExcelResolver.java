package com.baseline.excel;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;


@Slf4j
public class ImportExcelResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(ImportExcel.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        ImportExcel importExcelAnnotation = methodParameter.getParameterAnnotation(ImportExcel.class);
        assert importExcelAnnotation != null;
        Class<?> clazz = importExcelAnnotation.value();
        String name = importExcelAnnotation.name();
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        assert request != null;
        Part part = request.getPart(name);
        if (part == null) {
            throw new IllegalArgumentException("导入文件未找到");
        }
        ImportExcelHelper<?> importExcelHelper = new ImportExcelHelper<>();
        return importExcelHelper.getListByStream(part.getInputStream(), clazz,
                importExcelAnnotation.sheetNo(), importExcelAnnotation.headRowNumber());

    }
}
