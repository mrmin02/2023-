package com.legacy.application.common.system.file.service;

import com.legacy.application.common.system.file.mapper.FileMngMapper;
import com.legacy.application.common.system.file.vo.FileMngVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("fileMngService")
public class FileMngService {

    @Resource(name = "fileMngMapper")
    FileMngMapper fileMngMapper;

    public int setFile(FileMngVO fileMngVO) throws Exception{
        return fileMngMapper.setFile(fileMngVO);
    }

    public FileMngVO getFileDetail(FileMngVO fileMngVO) throws Exception{
        return fileMngMapper.getFileDetail(fileMngVO);
    }

    public List<FileMngVO> getFiles(FileMngVO fileMngVO) throws Exception{
        return fileMngMapper.getFiles(fileMngVO);
    }

    public int delFile(FileMngVO fileMngVO) throws Exception{
        return fileMngMapper.delFile(fileMngVO);
    }
}
