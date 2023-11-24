package com.legacy.application.common.util;

import com.legacy.application.common.config.vo.DefaultVO;
import com.legacy.application.common.system.file.vo.FileMngVO;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.tika.Tika;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.UUID.randomUUID;

public class FileUtil {

    public static final int BUFFER_SIZE = 8192;

    public static final String SEPARATOR = File.separator;

    /**
     * DefaultVO 를 상속받은 객체 대상으로 MIME Type 검사
     * @param obj
     * @param mime_type
     * @return
     * @throws Exception
     */
    public static boolean fn_checkFileMime(Object obj, String mime_type) throws Exception{

        DefaultVO vo = new DefaultVO();
        BeanUtils.copyProperties(vo,obj);

        boolean result = false;
        if(vo.getFile() != null){
            for(MultipartFile file : vo.getFile()){
                if(file.getOriginalFilename() != null && file.getOriginalFilename() != ""){
                    String mime = new Tika().detect(file.getInputStream());
                    String[] check_types = mime_type.split("\\\\\\\\"); // 백슬러시 2개로 구분

                    for(String type : check_types) {
                        if(mime.equals(type)){
                            result = true;
                        }
                    }
                }
            }
        }
        return result;
    }

    public static List<FileMngVO> fn_getFileInfo(Object obj, String table_name, String table_seq, String fileDirPath, String subDirPath, String mime_type, String inpt_seq) throws Exception{
        List<FileMngVO> rtnList = new ArrayList<>();
        DefaultVO vo = new DefaultVO();
        BeanUtils.copyProperties(vo, obj);

        boolean fileResult = false;
        if(StringUtils.isEmpty(mime_type)){
            // mime_type = "jpg\\png";
            fileResult = true;
        }else{
            fileResult = fn_checkFileMime(obj, mime_type);
        }
//        fileResult = fn_checkFileMime(obj, mime_type);

        if(fileResult){
            if(vo.getFile() != null){
                for(MultipartFile file : vo.getFile()){
                    if(CommonUtil.isNotEmpty(file.getOriginalFilename())){
                        String mime = new Tika().detect(file.getInputStream());
                        // FIXME 전자정부 프레임워크와 비슷하게 바꾸기..
                        // 호
                        FileMngVO fileVO = new FileMngVO();

                        // 파일 확장자
                        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") +1);

                        fileVO.setTable_nm(table_name); // 테이블 명
                        fileVO.setTable_seq(table_seq); // 테이블의 순번
                        fileVO.setFile_nm(file.getOriginalFilename()); // 사용자가 업로드한 파일 이름

                        String file_sys_nm = "";

                        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

                        // 저장될 파일 명 : 업로드 날짜_랜덤문자_등록자seq.확장자
                        file_sys_nm = format.format(new Date())+"_"+randomUUID().toString().replace("-","")+"_"+ inpt_seq + "." + fileExt;

                        fileVO.setFile_sys_nm(file_sys_nm); //

                        fileVO.setFile_type(mime); // MIME 타입

                        fileVO.setFile_path(subDirPath + table_seq + SEPARATOR);
                        fileVO.setInpt_seq(inpt_seq);
                        // 실제 업로드 경로
                         String real_upload_path = fileDirPath + subDirPath + table_seq + SEPARATOR ;

                         File dir = new File(real_upload_path);
                         if(!dir.exists()){
                             dir.mkdirs(); // 경로 없으면 경로 생성
                         }

                        // 업로드
                        file.transferTo(new File(real_upload_path + file_sys_nm));

                        fileVO.setFileResult(true);
                        rtnList.add(fileVO);
                    }
                }
            }
        }

        return rtnList;
    }

    public static void viewFile(HttpServletResponse response, String where, String serverSubPath, String physicalName, String mimeTypeParam) throws Exception {
        String mimeType = mimeTypeParam;
        String downFileName = GlobalsProperties.getProperty("file.DefaultPath") + SEPARATOR + serverSubPath + SEPARATOR + physicalName;

        File file = new File(GlobalsProperties.filePathBlackList(downFileName));


        byte[] b = new byte[BUFFER_SIZE];

        if (mimeType == null) {
            mimeType = "application/octet-stream;";
        }

        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", "filename=image;");

        BufferedInputStream fin = null;
        BufferedOutputStream outs = null;

        try {
            fin = new BufferedInputStream(new FileInputStream(file));
            outs = new BufferedOutputStream(response.getOutputStream());

            int read = 0;

            while ((read = fin.read(b)) != -1) {
                outs.write(b, 0, read);
            }

        } catch (Exception e){

        } finally {
            if (outs != null) {
                try {
                    outs.close();
                } catch (Exception ignore) {
                    // //System.out.println("IGNORE: " + ignore);
                }
            }
            if (fin != null) {
                try {
                    fin.close();
                } catch (Exception ignore) {
                    // //System.out.println("IGNORE: " + ignore);
                }
            }
        }
    }
}
