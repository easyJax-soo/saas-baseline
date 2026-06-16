package com.baseline.common.service;

import com.baseline.common.dto.SysMessageInfoSaveTypeBizDTO;

/**
 * @author bryant
 * @date 2025/11/25
 **/
public interface ISysMessageInfoBizService {
    boolean saveByType(SysMessageInfoSaveTypeBizDTO dto, String source);
}
