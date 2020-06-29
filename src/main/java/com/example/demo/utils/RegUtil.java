package com.example.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUtil {
        public static boolean email(String str){
            String pattern ="^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(str);
            Boolean result = m.matches();
            return  result;
        }
}
