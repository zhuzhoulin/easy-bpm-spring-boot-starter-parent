package com.pig.easy.bpm.auth.service;

import com.pig.easy.bpm.auth.entity.FileDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.auth.dto.request.*;
import com.pig.easy.bpm.auth.dto.response.*;
import java.util.List;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
public interface FileService extends BaseService<FileDO> {

    Result<PageInfo<FileDTO>> getListPageByCondition(FileQueryDTO param);

    Result<Integer> insertFile(FileSaveOrUpdateDTO param);

    Result<Integer> updateFile(FileSaveOrUpdateDTO param);

    Result<Integer> deleteFile(FileSaveOrUpdateDTO param);

    Result<FileDTO> getFileById(Integer fileId);

    Result<List<FileDTO>> getListByCondition(FileQueryDTO param);
}
