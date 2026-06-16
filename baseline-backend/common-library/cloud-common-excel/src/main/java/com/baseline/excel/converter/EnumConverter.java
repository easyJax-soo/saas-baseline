package com.baseline.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.baseline.core.annotation.IEnumerator;

import java.util.Arrays;

public class EnumConverter implements Converter<IEnumerator> {

    @Override
    public WriteCellData<?> convertToExcelData(IEnumerator value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return new WriteCellData<>(value.getDescription());
    }

    @Override
    public IEnumerator convertToJavaData(ReadConverterContext<?> context) throws Exception {
        String stringValue = context.getReadCellData().getStringValue();
        Class<?> type = context.getContentProperty().getField().getType();
        return (IEnumerator) Arrays.stream(type.getEnumConstants())
                .filter(v -> {
                    IEnumerator e = (IEnumerator) v;
                    return e.getDescription().equals(stringValue);
                })
                .findFirst().orElse(null);
    }
}
