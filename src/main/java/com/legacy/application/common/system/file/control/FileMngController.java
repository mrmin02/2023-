package com.legacy.application.common.system.file.control;

import com.legacy.application.common.system.file.service.FileMngService;
import com.legacy.application.common.system.file.vo.FileMngVO;
import com.legacy.application.common.util.FileUtil;
import com.legacy.application.common.util.GlobalsProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.legacy.application.common.util.FileDownload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

import static com.google.common.io.Files.getFileExtension;

@Controller
public class FileMngController {

    @Resource(name = "fileMngService")
    FileMngService fileMngService;

    public final String FILE_DEFAULT_PATH = GlobalsProperties.getProperty("file.DefaultPath");
    public final String FILE_SUB_PATH = GlobalsProperties.getProperty("file.DefaultSubPath");


    @RequestMapping("/cmmn/fileDownSimple")
    public void simpleFileDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String orignlFileNm = "";
        String table_seq = "";
        String systemFileNm = "";
        String fileFullPath = "";

        String fileSeq = request.getParameter("fileSeq");
        FileMngVO filevo = fileMngService.getFileDetail(new FileMngVO(fileSeq));

        if(filevo != null) {
            orignlFileNm = filevo.getFile_nm();
            table_seq = filevo.getTable_seq();
            systemFileNm = filevo.getFile_sys_nm();
        }
        String fileFlag = request.getParameter("flag");

        FileDownload.simpleFileDownload(systemFileNm,orignlFileNm,table_seq,fileFlag,request,response);
    }

    @RequestMapping(value = "/file/ckeditorFileupload")
    public void ckeditorFileUpload(	HttpServletRequest request,
                                       HttpServletResponse response,
                                       MultipartHttpServletRequest req) throws IOException {
        Iterator fileIter = req.getFileNames();

        InputStream in = null;
        OutputStream out = null;

        HashMap<String, String> map = new HashMap<String, String>();
        String ckeditorPath = GlobalsProperties.getProperty("file.CkeditorPath");
        String procPath = request.getParameter("path");
        String fileDir = GlobalsProperties.getProperty("file.DefaultPath") + ckeditorPath + "/" + procPath + "/";
        while (fileIter.hasNext()) {
            try {
                MultipartFile mFile = req.getFile((String) fileIter.next());

                String originalFileName = mFile.getOriginalFilename();

                // 파일 확장자 확인
                String originalExtension = getFileExtension(originalFileName);
                String regEx = "(gif|jpg|png|jpeg|bmp|GIF|JPG|PNG|JPEG|BMP)";

                if (!originalExtension.matches(regEx)) {
                    PrintWriter temp;
                    temp = response.getWriter();
                    temp.println("허용되지 않은 확장자 파일입니다.");
                    break;
                }
                //

                if (originalFileName.lastIndexOf("\\") >= 0) {
                    originalFileName = originalFileName.substring(originalFileName.lastIndexOf("\\") + 1);
                }

                String targetFileName = new StringBuffer().append(System.currentTimeMillis())
                        .append(".")
                        .append(originalFileName.substring(originalFileName.lastIndexOf(".") + 1, originalFileName.length())).toString();

                // 폴더 생성
                File targetPathDir = new File(fileDir);
                if (!targetPathDir.isDirectory()) {
                    targetPathDir.mkdirs();
                }

                String savedFilePath = fileDir + targetFileName;

                try {
                    in = mFile.getInputStream();
                    out = new FileOutputStream(savedFilePath);

                    int readBytes = 0;
                    byte[] buff = new byte[8192];

                    while ((readBytes = in.read(buff, 0, 8192)) != -1) {
                        out.write(buff, 0, readBytes);
                    }
                } finally {
                    if (in != null)
                        in.close();
                    if (out != null)
                        out.close();
                }

                map.put("orgFileName", originalFileName); // 실제 파일명
                map.put("sysFileName", targetFileName);   // 저장된 파일명
                map.put("defaultFilePath", fileDir);      // 기본경로
                map.put("subPath", FILE_SUB_PATH);      // 풀경로
                map.put("fileSize", String.valueOf(mFile.getSize()));
                map.put("fileType", mFile.getContentType());

                String funcNum = request.getParameter("CKEditorFuncNum");
                //String fileUrl = savedFilePath;

                // TODO 테스트서버 URL
                String tmpFileUrl = "/cmmn/fileView?path=" + ckeditorPath + "/" + procPath + "&physical=" + targetFileName + "&contentType=image";
                // TODO 실서버 URL
                /*String tmpFileUrl = "/data"+ckeditorPath+"/"+procPath+"/"+targetFileName;*/

                response.setContentType("text/html; charset=utf-8");
                PrintWriter print;

                try {
                    print = response.getWriter();
                    print.println("<script>window.parent.CKEDITOR.tools.callFunction(" + funcNum + ", '" + tmpFileUrl + "', '파일 업로드가 완료되었습니다.');</script>");
                } catch (IOException e) {
                    print = response.getWriter();
                    print.println("<script>window.parent.CKEDITOR.tools.callFunction('', '', '파일 업로드에 실패하였습니다.');</script>");
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @RequestMapping({"/cmmn/fileView"})
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String subPath = request.getParameter("path");
        String physical = request.getParameter("physical");
        String mimeType = request.getParameter("contentType");

        FileUtil.viewFile(response, FILE_DEFAULT_PATH, subPath, physical, mimeType);
    }
}
