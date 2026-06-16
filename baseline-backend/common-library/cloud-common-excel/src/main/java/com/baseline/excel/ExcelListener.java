package com.baseline.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellExtra;
import com.baseline.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Excel模板的读取监听类
 * @param <T>
 */
@Slf4j
public class ExcelListener<T> extends AnalysisEventListener<T> {

    /**
     * 解析的数据
     */
    private final List<T> list = new ArrayList<>();

    /**
     * 正文起始行
     */
    private final Integer headRowNumber;
    /**
     * 合并单元格
     */
    private final List<CellExtra> extraMergeInfoList = new ArrayList<>();

    public ExcelListener(Integer headRowNumber) {
        this.headRowNumber = headRowNumber;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        list.add(data);
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
    }

    /**
     * 返回解析出来的List
     */
    public List<T> getData() {
        return list;
    }

    /**
     * 读取额外信息：合并单元格
     */
    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        if (extra.getType() == CellExtraTypeEnum.MERGE) {
            if (extra.getRowIndex() >= headRowNumber) {
                extraMergeInfoList.add(extra);
            }
        }
    }

    /**
     * 返回解析出来的合并单元格List
     */
    public List<CellExtra> getExtraMergeInfoList() {
        return extraMergeInfoList;
    }

    /**
     * 返回具体的异常行列及数据信息
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        // 如果要获取头的信息 配合invokeHeadMap使用
        String errorMessage = "";
        try {
            if (exception instanceof ExcelDataConvertException) {
                ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
                
                // 获取行列信息
                int rowIndex = excelDataConvertException.getRowIndex();
                int columnIndex = excelDataConvertException.getColumnIndex();
                Object cellData = excelDataConvertException.getCellData();
                
                // 尝试获取字段名称
                String fieldName = getFieldNameByColumnIndex(context, columnIndex);
                String columnInfo = fieldName != null ? 
                    String.format("第%d列(%s)", columnIndex, fieldName) : 
                    String.format("第%d列", columnIndex);
                
                log.error("第{}行，{}解析异常，数据为:{}", rowIndex, columnInfo, cellData);
                
                // 获取更详细的异常信息
                String detailMessage = "";
                if (excelDataConvertException.getCause() != null) {
                    detailMessage = excelDataConvertException.getCause().getMessage();
                } else if (excelDataConvertException.getMessage() != null) {
                    detailMessage = excelDataConvertException.getMessage();
                } else {
                    detailMessage = "数据转换失败";
                }
                
                // 记录完整的异常堆栈信息用于调试
                log.error("完整异常信息:", excelDataConvertException);
                
                errorMessage = String.format("第%d行，%s解析异常，异常信息:%s", 
                    rowIndex, columnInfo, detailMessage);
            } else if (exception instanceof ExcelAnalysisException) {
                ExcelAnalysisException excelAnalysisException = (ExcelAnalysisException) exception;
                log.error("Excel解析过程中出现异常: {}", excelAnalysisException.getMessage());
                errorMessage = "Excel 解析异常: " + excelAnalysisException.getMessage();
            } else {
                log.error("发生其他异常: {}", exception.getMessage());
                errorMessage = "发生其他异常: " + exception.getMessage();
            }
        } catch (Exception e) {
            log.error("处理异常时发生错误", e);
            errorMessage = "处理异常时发生错误";
        }
        throw new BusinessException(errorMessage);
    }

    /**
     * 根据列索引获取字段名称
     */
    private String getFieldNameByColumnIndex(AnalysisContext context, int columnIndex) {
        try {
            // 尝试从配置中获取字段信息
            if (context.currentReadHolder() != null && 
                context.currentReadHolder().excelReadHeadProperty() != null &&
                context.currentReadHolder().excelReadHeadProperty().getHeadMap() != null) {
                
                Map<Integer, com.alibaba.excel.metadata.Head> headMap = 
                    context.currentReadHolder().excelReadHeadProperty().getHeadMap();
                if (headMap.containsKey(columnIndex)) {
                    com.alibaba.excel.metadata.Head head = headMap.get(columnIndex);
                    if (head != null && head.getHeadNameList() != null && !head.getHeadNameList().isEmpty()) {
                        return head.getHeadNameList().get(head.getHeadNameList().size() - 1);
                    }
                }
            }
        } catch (Exception e) {
            // 获取字段名称失败，忽略异常
            log.debug("获取字段名称失败: {}", e.getMessage());
        }
        return null;
    }
}