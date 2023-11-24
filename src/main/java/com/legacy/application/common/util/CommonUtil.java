package com.legacy.application.common.util;

import org.apache.commons.beanutils.BeanUtilsBean;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

public class CommonUtil {

    /**
     * 클래스 복사 ( null 제외 )
     * @param dest
     * @param source
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void fn_copyClass(Object dest, Object source) throws IllegalAccessException, InvocationTargetException {
        new BeanUtilsBean() {
            @Override
            public void copyProperty(Object dest, String name, Object value)
                    throws IllegalAccessException, InvocationTargetException {
                if(value != null) {
                    super.copyProperty(dest, name, value);
                }
            }
        }.copyProperties(dest, source);
    }

    /**
     * get ip
     * @param request
     * @return
     */
    public static String getIP(HttpServletRequest request) {
        String clientIp = request.getHeader("X_FORWARDED_FOR");
        if (null == clientIp || clientIp.length() == 0
                || clientIp.toLowerCase().equals("unknown")) {
            clientIp = request.getHeader("REMOTE_ADDR");
        }
        if (null == clientIp || clientIp.length() == 0
                || clientIp.toLowerCase().equals("unknown")) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }

    public static boolean isNotEmpty(Object o) {
        if ((o instanceof String)) {
            return o != null && ((String) (o)).length() > 0;
        } else {
            return o != null;
        }
    }

    public static boolean isNumber(String str){
        try{
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e){
            return false;
        }

    }
}
