package com.baseline.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImportExcel {
    String name() default "file";
    Class<?> value();
    int sheetNo() default 0;
    int headRowNumber() default 1;
}
