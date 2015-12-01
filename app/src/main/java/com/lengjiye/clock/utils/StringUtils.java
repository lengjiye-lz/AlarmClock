package com.lengjiye.clock.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isBlank(String str) {
        // int strLen;
        if (str == null || str.trim().length() == 0) {
            return true;
        }
        // for (int i = 0; i < strLen; i++) {
        // if ((Character.isWhitespace(str.trim().charAt(i)) == false)) {
        // return false;
        // }
        // }
        return false;
    }

    public static boolean isNotBlank(String s) {
        return !StringUtils.isBlank(s);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static boolean isNull(String obj) {
        return obj == null;
    }

    public static boolean isNotNull(String obj) {
        return obj != null;
    }

    public static boolean isNumericString(String str) {
        return str.matches("[0-9]+");
    }

    public static boolean isPhoneNumber(String mobileNo) {
        String regex = "(\\d{11})$";
        return Pattern.matches(regex, mobileNo);
    }

    public static boolean isValidEMail(String eMail) {
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return Pattern.matches(regex, eMail);
    }

    public static boolean isValidPwd(String pwd) {
        String regex = "^(?=.*?[a-zA-Z`~!@#$%^&*()_\\-+={}\\[\\]\\\\|:;\\\"'<>,.?/])[a-zA-Z\\d`~!@#$%^&*()_\\-+={}\\[\\]\\\\|:;\\\"'<>,.?/]{6,20}$";
        return Pattern.matches(regex, pwd);
    }

    public static boolean isValidVerifyCode(String code) {
        String regex = "[0-9]{6}";
        return Pattern.matches(regex, code);
    }

    public static boolean isValidStringDate(String strDate) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date inputBirthday = fmt.parse(strDate);
            if (new Date().after(inputBirthday)) {
                return true;
            } else {
                return false;

            }
        } catch (ParseException e) {
            return false;
        }
    }

    public static String getBirthday(String cardID) {
        String returnDate = null;
        StringBuffer tempStr = null;
        if (cardID != null && cardID.trim().length() > 0) {
            tempStr = new StringBuffer(cardID.substring(6, 14));
            tempStr.insert(6, '-');
            tempStr.insert(4, '-');
        }
        if (tempStr != null && tempStr.toString().trim().length() > 0) {
            returnDate = tempStr.toString();
        }
        return returnDate;
    }

    public static String getGender(String cardID) {
        String returnGender = null;
        if (cardID != null && cardID.trim().length() > 0) {
            returnGender = cardID.substring(16, 17);
            if (Integer.parseInt(returnGender) % 2 == 0) {
                returnGender = "2";
            } else {
                returnGender = "1";
            }
        }
        return returnGender;
    }

    public static boolean isValidZipCode(String zipCode) {
        String regex = "[0-9]\\d{5}(?!\\d)";
        return Pattern.matches(regex, zipCode);
    }

    public static String getCurrentDate() {
        java.util.Calendar c = java.util.Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(c.getTime());

    }

    public String SplitIt(String str, int length) {
        int loopCount;
        StringBuffer splitedStrBuf = new StringBuffer();
        loopCount = (str.length() % length == 0) ? (str.length() / length) : (str.length() / length + 1);
        System.out.println("Will Split into " + loopCount);
        for (int i = 1; i <= loopCount; i++) {
            if (i == loopCount) {
                splitedStrBuf.append(str.substring((i - 1) * length, str.length()));
            } else {
                splitedStrBuf.append(str.substring((i - 1) * length, (i * length)));
            }
        }

        return splitedStrBuf.toString();
    }

    /**
     * 手机号中间三位隐藏
     *
     * @param mobile 手机号
     * @return
     */
    public static String mobileFormat(String mobile) {
        if (isEmpty(mobile)) {
            return mobile;
        }
        String startString = mobile.substring(0, 4);
        String endString = mobile.substring(7);
        return startString + "***" + endString;
    }
}
