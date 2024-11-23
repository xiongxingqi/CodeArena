package com.celest.backend.utils;

import jakarta.servlet.http.HttpServletRequest;

public class IpUtil {

    public  final static String LOCALHOST_IPV4 = "127.0.0.1";
    public final static String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
    public final static String UNKNOWN = "unknown";

    public final static String  SQUID_PROXY = "X-Forwarded_For";
    public final static String APACHE_PROXY = "Proxy-Client-IP";
    public final static String WEBLOGIC_PROXY = "WL-Proxy-Client-IP";
    public final static String OTHER_PROXY = "HTTP_CLIENT_IP";
    public final static String NGINX_PROXY = "X-Real-IP";


    public static String getClientIpAddress(HttpServletRequest request){
        if (request == null) {
            return UNKNOWN;
        }
        String ip = request.getHeader(SQUID_PROXY);
        if(ip==null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(APACHE_PROXY);
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(WEBLOGIC_PROXY);
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(OTHER_PROXY);
        }
        if(ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)){
            ip = request.getHeader(NGINX_PROXY);
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            //获得网卡中的IPv4地址
//            if (LOCALHOST_IPV4.equalsIgnoreCase(ip) || LOCALHOST_IPV6.equalsIgnoreCase(ip)) {
//                InetAddress iNet = null;
//                try {
//                    iNet = InetAddress.getLocalHost();
//                } catch (UnknownHostException e) {
//                    e.printStackTrace();
//                }
//                if (iNet != null) {
//                    ip = iNet.getHostAddress();
//                }
//            }
        }
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(',') > 0) {
                ip = ip.substring(0,ip.indexOf(','));
            }
        }
        return LOCALHOST_IPV6.equalsIgnoreCase(ip)? LOCALHOST_IPV4 : ip;
    }

}
