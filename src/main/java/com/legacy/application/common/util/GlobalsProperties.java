package com.legacy.application.common.util;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * globalsProperties 값 가져오기 위한 클래스
 */
public class GlobalsProperties {

    // 파일 구분자
    public static final String SEPARATOR = File.separator;

    // 현재 이 파일 GlobalsProperties class 의 경로로 부터
    // 컴파일 완료 되었을 때, classes 폴더까지의 상대 경로
    public static final String NOW_RELATIVE_PATH = GlobalsProperties.class.getResource("").getPath().substring(0, GlobalsProperties.class.getResource("").getPath().lastIndexOf("classes")) + "classes" + SEPARATOR;

    // 컴파일 되었을 때, 현재 GlobalsProperties.class 파일의 경로를 가져와서 globals.properties 파일까지의 경로를 알아내어 지정
    public static final String GLOBALS_PROPERTIES_FILE = NOW_RELATIVE_PATH + "properties" + SEPARATOR + "globals.properties";


    // globals.properties 값 읽어오기
    public static String getProperty(String keyName){
        String rtnValue = "";
        FileInputStream inputStream = null;
        try {
            Properties props = new Properties();
            inputStream = new FileInputStream(GLOBALS_PROPERTIES_FILE);
            props.load(new BufferedInputStream(inputStream));
            if(props.getProperty(keyName) == null){
                return "";
            }
            rtnValue = props.getProperty(keyName).trim();

        }catch (Exception e){
            e.printStackTrace();;
        } finally {
            try{
                inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return rtnValue;
    }

    public static String filePathBlackList(String value) {
        String returnValue = value;
        if (returnValue == null || returnValue.trim().equals("")) {
            return "";
        }

        returnValue = returnValue.replaceAll("\\.\\./", ""); // ../
        returnValue = returnValue.replaceAll("\\.\\.\\\\", ""); // ..\

        return returnValue;
    }
}
