
cloud-common-oss 主要用于文件存储。

## 组件使用

1. 引入组件
    ```
    <dependency>
        <groupId>com.baseline.library</groupId>
        <artifactId>cloud-common-oss</artifactId>
        <version>lastest version</version>
    </dependency>
    ```
2. 配置application.yml
   - 使用minio的配置
       ```
       oss:
           enabled: true
           access-endpoint: http://192.168.2.42:9000
           type: minio
           access-key: XhKZsE2uHwXkLfxF
           secret-key: 6yqKHLSIUA4AFVX2M0I9hBH6dW0DFnXw
           endpoint: http://192.168.2.42:9000
       ```
   - 使用本地的配置
       ```
       oss:
          enabled: true
          access-endpoint: http://localhost:8081/sfile
          root-upload-path: /data/static/upload
          type: local
          temp-path: /data/static/temp
       ```

3. 业务中添加上传功能

    业务通过调用 `OssTemplate` 来完成对应功能

      ```
      @Api(tags = "文件服务")
      @RestController
      @RequestMapping("/system/file")
      public class SysFileController {
      
          @Resource
          OssTemplateFactory templateFactory;
      
          @ApiOperation("文件上传")
          @PostMapping("/put-file")
          public FileModel putFile(@RequestPart MultipartFile file) {
              return templateFactory.getTemplate().putFile(file);
          }
      
          @ApiOperation("访问文件的前缀域名部分")
          @GetMapping("/domain")
          public String getAccessDomain(){
              return templateFactory.getTemplate().getAccessDomain();
          }
      }
      ```

