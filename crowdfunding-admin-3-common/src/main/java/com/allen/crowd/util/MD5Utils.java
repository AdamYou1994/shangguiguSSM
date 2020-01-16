package com.allen.crowd.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class MD5Utils {
	public static boolean checkAsyncRequest(HttpServletRequest request) {
		String accept = request.getHeader("Accept");
		String xRequested = request.getHeader("X-Requested-With");
		if ((stringEffective(accept) && accept.contains("application/json") )|| 
				(stringEffective(xRequested) && xRequested.contains("XMLHttpRequest"))) {
			return true;
		}
		return false;
	}
	
	/**
     * 判断集合是否有效
     * @param collection
     * @param <E>
     * @return true表示有效 false表示无效
     */
    public static <E> boolean collectiveEffective(Collection<E> collection){
        return collection != null && collection.size() > 0;
    }
    /**
     * 判断集合是否有效
     * @param Map
     * @param <E>
     * @return true表示有效 false表示无效
     */
    public static <K,V> boolean mapEffective(Map<K,V> map){
        return map != null && map.size() > 0;   //&&短路   避免空指针
    }
    
	/**
     * 判断 source 是否有效
     * @param source 明文密码
     * @return  true 表示有效，false 表示无效
     */
    public static boolean stringEffective(String source) {
        return null != source && source.length() > 0;
    }

    /**
     * md5加密工具开发
     * @param source 被加密的明文字符串
     * @return 加密后的字符串
     */
	public static String md5(String source) {
		 //判断明文是否有效
        if(!stringEffective(source)){
            throw new RuntimeException(Constant.MESSAGE_CODE_INVALID);
        }

        //准备一个字符数组获取对应的值
        char[] characters = {'0', '1', '2', '3', '4', '5','6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        /*用于拼接密文---StringBuilder 相较于 StringBuffer 有速度优势，所以多数情况下建议使用 StringBuilder 类。
         * 然而在应用程序要求线程安全的情况下，则必须使用 StringBuffer 类。
         */
        StringBuilder builder = new StringBuilder();

        //指定加密算法
        String algroithm = "MD5";
        try {
            MessageDigest digest = MessageDigest.getInstance(algroithm);
            //将欲加密的明文转换为字节数组(一个字节8位  Byte  = 8bit)
            byte[] inputBytes = source.getBytes();
            byte[] outputBytes = digest.digest(inputBytes);
            //一个字节8位   四位对应一个16进制数   将其变成16进制
            for(int i = 0;i<outputBytes.length;i++){
                //获得当前字节
                byte b = outputBytes[i];
                //获得低四位
                int lowValue = b&15;
                //获得高四位
                int highValue = (b >> 4) & 15;
                //以高四位，低四位为下标获取字符数组中的值
                char highCharacter = characters[highValue];
                char lowCharacter = characters[lowValue];
                //拼接密文
                builder.append(highCharacter).append(lowCharacter);
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("加密工具类异常");
            e.printStackTrace();
        }
        return builder.toString();
	}
}
