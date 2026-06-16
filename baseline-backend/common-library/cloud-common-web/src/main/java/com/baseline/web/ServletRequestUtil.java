package com.baseline.web;

import jakarta.servlet.http.HttpServletRequest;

public class ServletRequestUtil {

    public final static String getIpAddress(HttpServletRequest request) {
        String ip = "";
        ip = request.getHeader("X - Forwarded - For");
        if (ip != null && ip.length() > 0 && !"unKnown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (ip != null && ip.length() > 0 && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getRemoteAddr();
        return ip;
    }
}