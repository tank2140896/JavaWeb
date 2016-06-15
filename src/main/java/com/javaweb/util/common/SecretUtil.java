package com.javaweb.util.common;

import java.io.ByteArrayOutputStream;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SecretUtil {
	
	private static final String DESEDE_ALG = "DESede/ECB/NOPADDING";

	//3DES加密
	public static byte[] encryptDESede(byte[] src, byte[] key) {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        try {
            Cipher c1 = Cipher.getInstance(DESEDE_ALG,"BC");
            c1.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, DESEDE_ALG));
            return c1.doFinal(src);
        } catch (Exception e) {
        	return null;
        }
    }
	
	//3DES解密
	public static byte[] decryptDESede(byte[] src, byte[] key) {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        try {
            Cipher c1 = Cipher.getInstance(DESEDE_ALG,"BC");
            c1.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, DESEDE_ALG));
            return c1.doFinal(src);
        } catch (Exception e) {
        	return null;
        }
    }
	
	//16进制字符串转byte数组
	public static byte[] hexStringToBytes(String hexString) {   
	    if (hexString == null || hexString.equals("")) {   
	        return null;   
	    }   
	    hexString = hexString.toUpperCase();   
	    int length = hexString.length() / 2;   
	    char[] hexChars = hexString.toCharArray();   
	    byte[] d = new byte[length];   
	    for (int i = 0; i < length; i++) {   
	        int pos = i * 2;   
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));   
	    }   
	    return d;   
	} 
	
	//字符转byte
	private static byte charToByte(char c) {   
		return (byte) "0123456789ABCDEF".indexOf(c);   
	} 
	
	//byte数组转16进制字符串
	public static String bytesToHexString(byte[] src){   
	    StringBuilder stringBuilder = new StringBuilder("");   
	    if (src == null || src.length <= 0) {   
	        return null;   
	    }   
	    for (int i = 0; i < src.length; i++) {   
	        int v = src[i] & 0xFF;   
	        String hv = Integer.toHexString(v);   
	        if (hv.length() < 2) {   
	            stringBuilder.append(0);   
	        }   
	        stringBuilder.append(hv);   
	    }   
	    return stringBuilder.toString();   
	}
	
	//字符串转16进制字符串
	public static String str2HexStr(String str) {    
        char[] chars = "0123456789ABCDEF".toCharArray();    
        StringBuilder sb = new StringBuilder("");  
        byte[] bs = str.getBytes();    
        int bit;    
        for (int i = 0; i < bs.length; i++) {    
            bit = (bs[i] & 0x0f0) >> 4;    
            sb.append(chars[bit]);    
            bit = bs[i] & 0x0f;    
            sb.append(chars[bit]);    
        }    
        return sb.toString();    
    }
	
	//16进制字符串转字符串
	public static String hexStr2Str(String hexStr) {  
	   ByteArrayOutputStream baos = new ByteArrayOutputStream(hexStr.length() / 2);  
	   //将每2位16进制整数组装成一个字节  
	   for (int i = 0; i < hexStr.length(); i += 2) {
		   baos.write(("0123456789ABCDEF".indexOf(hexStr.charAt(i)) << 4 | "0123456789ABCDEF".indexOf(hexStr.charAt(i + 1))));  
	   } 
	   return new String(baos.toByteArray());  
	} 
	
	//16进制字符串转10进制字符串
	public static String hexTo10(String str){
		Long lon = Long.parseLong(str,16);
		return Long.toString(lon);
	}
	
	//10进制字符串转16进制字符串
	public static String TenTo16(String str){
		return Long.toHexString(Long.parseLong(str)).toUpperCase();
	}
	
    //十进制转BCD
    public static byte[] str2Bcd(String asc) {  
        int len = asc.length();  
        int mod = len % 2;  
        if (mod != 0) {  
            asc = "0" + asc;  
            len = asc.length();  
        }  
        byte abt[] = new byte[len];  
        if (len >= 2) {  
            len = len / 2;  
        }  
        byte bbt[] = new byte[len];  
        abt = asc.getBytes();  
        int j, k;  
        for (int p = 0; p < asc.length() / 2; p++) {  
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {  
                j = abt[2 * p] - '0';  
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {  
                j = abt[2 * p] - 'a' + 0x0a;  
            } else {  
                j = abt[2 * p] - 'A' + 0x0a;  
            }  
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {  
                k = abt[2 * p + 1] - '0';  
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {  
                k = abt[2 * p + 1] - 'a' + 0x0a;  
            } else {  
                k = abt[2 * p + 1] - 'A' + 0x0a;  
            }  
            int a = (j << 4) + k;  
            byte b = (byte) a;  
            bbt[p] = b;  
        }  
        return bbt;  
    }
    
    //BCD转10进制字符串
    public static String bcd2Str(byte[] bytes) {  
        StringBuffer temp = new StringBuffer(bytes.length * 2);  
        for (int i = 0; i < bytes.length; i++) {  
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));  
            temp.append((byte) (bytes[i] & 0x0f));  
        }  
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp.toString().substring(1) : temp.toString();  
    }
    
}
