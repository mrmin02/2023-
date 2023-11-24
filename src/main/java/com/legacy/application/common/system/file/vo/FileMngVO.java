package com.legacy.application.common.system.file.vo;

import com.legacy.application.common.config.vo.DefaultVO;
import lombok.Data;

@Data
public class FileMngVO extends DefaultVO {

    private String file_seq; // 파일 순번
    private String table_nm; // 테이블 명
    private String table_seq; // 테이블 순번
    private String file_type; // 확장자
    private String inpt_date; // 등록일
    private String inpt_seq; // 등록자 순번
    private String file_flag;
    
    private String del_yn; // 삭제여부
    
    private String upd_seq; // 삭제한 회원 seq
    private String upd_date; // 삭제일자

    private boolean fileResult; // 파일 저장했는지.. true / flase

    public FileMngVO(){}

    public FileMngVO(String file_seq){
        this.file_seq = file_seq;
    }

    public FileMngVO(String table_nm, String table_seq){
        this.table_nm = table_nm;
        this.table_seq = table_seq;
    }
}
