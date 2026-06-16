package com.baseline.excel;

import com.alibaba.excel.EasyExcel;
import com.baseline.excel.converter.LocalDateConverter;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Collection;

public class ExcelProcessor implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return methodParameter.hasMethodAnnotation(ExportExcel.class);
    }


    @Override
    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        ExportExcel hcExcelAnnotation = methodParameter.getMethodAnnotation(ExportExcel.class);
        modelAndViewContainer.setRequestHandled(true);
        assert response != null;
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        assert hcExcelAnnotation != null;
        String fileName = URLEncoder.encode(hcExcelAnnotation.fileName(), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        EasyExcel.write(response.getOutputStream(), hcExcelAnnotation.clazz())
                .registerWriteHandler(new SelectWriteHandler())
                .registerConverter(new LocalDateConverter())
                .autoCloseStream(Boolean.FALSE).sheet(hcExcelAnnotation.sheetName()).doWrite((Collection<?>) o);
    }
}
