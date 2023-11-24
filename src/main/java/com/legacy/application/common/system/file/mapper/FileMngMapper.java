package com.legacy.application.common.system.file.mapper;

import com.legacy.application.common.system.file.vo.FileMngVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("fileMngMapper")
public interface FileMngMapper {

    int setFile(FileMngVO fileMngVO) throws Exception;

    FileMngVO getFileDetail(FileMngVO fileMngVO) throws Exception;

    List<FileMngVO> getFiles(FileMngVO fileMngVO) throws Exception;

    int delFile(FileMngVO fileMngVO) throws Exception;
}
