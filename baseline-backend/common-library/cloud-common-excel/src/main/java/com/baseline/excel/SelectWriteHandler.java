package com.baseline.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.context.SheetWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.baseline.core.annotation.IEnumerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;

@Slf4j
public class SelectWriteHandler implements SheetWriteHandler {


    @Override
    public void afterSheetCreate(SheetWriteHandlerContext context) {
        SheetWriteHandler.super.afterSheetCreate(context);
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

        Sheet sheet = writeSheetHolder.getSheet();
        DataValidationHelper helper = sheet.getDataValidationHelper();

        Class<?> clazz = writeSheetHolder.getClazz();
        int index = 0;

        for (int i = 0; i < clazz.getDeclaredFields().length; i++) {
            Field field = clazz.getDeclaredFields()[i];
            if (!field.isAnnotationPresent(ExcelIgnore.class) && !"serialVersionUID".equals(field.getName())) {
                Type[] genericInterfaces = field.getType().getGenericInterfaces();
                if (genericInterfaces.length > 0 && Arrays.asList(genericInterfaces).contains(IEnumerator.class)) {
                    CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(1, 100, index, index);
                    String[] strings = Arrays.stream(field.getType().getEnumConstants()).map(o -> {
                        IEnumerator enumerator = (IEnumerator) o;
                        return enumerator.getDescription();
                    }).toArray(String[]::new);
                    DataValidationConstraint constraint = helper.createExplicitListConstraint(strings);
                    DataValidation validation = helper.createValidation(constraint, cellRangeAddressList);
                    sheet.addValidationData(validation);
                }
                index++;
            }
        }


    }
}
