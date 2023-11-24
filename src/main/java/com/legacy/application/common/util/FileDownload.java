package com.legacy.application.common.util;

import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class FileDownload {
    public static void simpleFileDownload(
            String systemFileNm,
            String orignlFileNm,
            String table_seq,
            String fileFlag,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {

        String filePath = "";

        // 서비스 추가되면 switch 문써서..
        filePath = GlobalsProperties.getProperty("file.ArticleFilePath");



        String fileStorePath = GlobalsProperties.getProperty("file.DefaultPath") + filePath;
        String fullPath = fileStorePath + table_seq + "/" + systemFileNm;


        File uFile = new File(fullPath);


        int fSize = (int) uFile.length();

        if (fSize > 0) {
            String mimetype = "application/x-msdownload";

            //response.setBufferSize(fSize);	// OutOfMemeory 발생
            response.setContentType(mimetype);
            //response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fvo.getOrignlFileNm(), "utf-8") + "\"");
            setDisposition(orignlFileNm, request, response);
            response.setContentLength(fSize);

            /*
             * FileCopyUtils.copy(in, response.getOutputStream());
             * in.close();
             * response.getOutputStream().flush();
             * response.getOutputStream().close();
             */
            BufferedInputStream in = null;
            BufferedOutputStream out = null;

            try {
                in = new BufferedInputStream(new FileInputStream(uFile));
                out = new BufferedOutputStream(response.getOutputStream());


                FileCopyUtils.copy(in, out);
                out.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
                //ex.printStackTrace();
                // 다음 Exception 무시 처리
                // Connection reset by peer: socket write error
                //log.debug("IGNORED: " + ex.getMessage());
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (Exception ignore) {
                        ignore.printStackTrace();
                        // no-op
                        // log.debug("IGNORED: " + ignore.getMessage());
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (Exception ignore) {
                        ignore.printStackTrace();
                        // no-op
                        //log.debug("IGNORED: " + ignore.getMessage());
                    }
                }
            }

        } else {
            response.setContentType("application/x-msdownload");
            response.setCharacterEncoding("utf-8");
            PrintWriter printwriter = response.getWriter();
            printwriter.println("<html>");
            printwriter.println("<br><br><br><h2>Could not get file name:<br>" + orignlFileNm + "</h2>");
            printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
            printwriter.println("<br><br><br>&copy; webAccess");
            printwriter.println("</html>");
            printwriter.flush();
            printwriter.close();
        }

    }

    public static void setDisposition(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String dispositionPrefix = "attachment; filename=";
        String encodedFilename = null;

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < filename.length(); i++) {
            char c = filename.charAt(i);
            if (c > '~') {
                sb.append(URLEncoder.encode("" + c, "UTF-8"));
            } else {
                sb.append(c);
            }
        }
        encodedFilename = sb.toString().replace(",", "");


        response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);
    }
}
