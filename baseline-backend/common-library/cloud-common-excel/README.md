
`cloud-common-excel` 组件是对[easyexcel](https://github.com/alibaba/easyexcel) 的二次封装，实现了

- 基于注解的来完成导入导出功能
- 添加了基于spring的参数和返回值解析器
- 解决了合并单元各数据读取问题


## 使用说明

1. 引入组件
    ```
    <dependency>
        <groupId>com.baseline.library</groupId>
        <artifactId>cloud-common-excel</artifactId>
        <version>lastest version</version>
    </dependency>
    ```
2. 数据导出

    - 定义导出数据模型,ExcelProperty() 文字则为表头字段
        ```
        @ApiModel(value = "SysDictVO",description = "字典数据")
        @Data
        public class SysDictVO {
            @ExcelIgnore
            Long id;
            @ExcelProperty("标签")
            String label;
            @ExcelProperty("值")
            String value;
        }
        ```
    - 定义接口， `@ExportExcel(fileName = "文档",clazz = SysDictVO.class,sheetName = "123")` 注解在最终返回的时候解析为excel文件
        ```
        @ApiOperation("根据字典code获取字典数据")
        @GetMapping("/data/export/{code}")
        @ApiImplicitParam(name = "code",value = "类型code")
        @ExportExcel(fileName = "文档",clazz = SysDictVO.class,sheetName = "123")
        public List<SysDictVO> exportDataByCode(@PathVariable String code){
            return  sysDictDataService.getDataByCode(code);
        }
        ```
3. 数据导入
    
    - 定义导入数据模型, `@ExcelProperty(index = 3)` 这里也可以使用表头文字,比如 `@ExcelProperty("字典类型编码")`,但是如果表格有合并单元格则必须使用单元格序号 
        ```
        @ApiModel(value = "SysDictDataDTO",description = "数据保存")
        @Data
        public class SysDictDataDTO {
        
        @ApiModelProperty(value = "字典ID")
        private Long id;
        
        @ApiModelProperty(value = "字典排序")
        @ExcelProperty(index = 3)
        private Integer sortNo;
        
        @ApiModelProperty(value = "字典标签")
        @ExcelProperty(index = 1)
        private String label;
        
        @ApiModelProperty(value = "字典键值")
        @ExcelProperty(index = 2)
        private String value;
        
        @ApiModelProperty(value = "字典类型编码")
        @ExcelProperty(index = 0)
        private String code;
        
        @ApiModelProperty(value = "是否默认")
        @ExcelProperty(index = 5)
        private Boolean isDefault;
        
        @ApiModelProperty(value = "状态")
        private SysDictDataEnum.Status status;
        
        @ApiModelProperty(value = "备注")
        @ExcelProperty(index = 4)
        private String remark;
        }
        ```
    - 定义导出接口 ，使用注解 `@ImportExcel(value = SysDictDataDTO.class)` 系统会自动解析excel文件,post过来的数据文件表单名称默认为file,可以通过name属性修改
        ```
        @ApiOperation("导入字典数据")
        @PostMapping("/data/import")
        public boolean importData(@ImportExcel(value = SysDictDataDTO.class) List<SysDictDataDTO> data){
            ......
        }
        ```


## 自定义类型处理器

1. 枚举类型
   ```
    @ExcelProperty(value = "处理方式",converter = EnumConverter.class)
    private WarningManageEnum.HandleType handleType;
   ```

