package com.baseline.oss.model;


import lombok.Data;

@Data
public class ChunkFileModel {
    // 序号
    int index;
    //最终完成之后的文件信息
    FileModel fileModel;
}
