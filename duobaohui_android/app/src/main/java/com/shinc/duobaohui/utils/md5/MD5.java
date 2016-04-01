package com.shinc.duobaohui.utils.md5;

/**
 * Created by liugaopo on 15/10/15.
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SuppressWarnings("ALL")
public class MD5 {
    // È«¾ÖÊý×é
    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    // È«¾ÖÊý×é
    /*private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
        "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };*/


    // ·µ»ØÐÎÊ½ÎªÊý×Ö¸ú×Ö·û´®
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // ·µ»ØÐÎÊ½Ö»ÎªÊý×Ö
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    // ×ª»»×Ö½ÚÊý×éÎª16½øÖÆ×Ö´®
    private static String byteToString(byte[] bByte) {
        StringBuilder sBuffer = new StringBuilder();
        //noinspection ForLoopReplaceableByForEach
        for (byte aBByte : bByte) {
            sBuffer.append(byteToArrayString(aBByte));
        }
        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() ¸Ãº¯Êý·µ»ØÖµÎª´æ·Å¹þÏ£Öµ½á¹ûµÄbyteÊý×é
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

}


