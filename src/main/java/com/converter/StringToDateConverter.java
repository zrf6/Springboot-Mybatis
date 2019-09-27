/**
 * @Title: StringToDateConverter.java
 * @Description: TODO
 * @author: bryant
 * @date: 2019年9月21日 上午11:07:25
 * @version: v1.0
 */
package com.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: StringToDateConverter
 * @Description: TODO
 * @author: bryant
 */
public class StringToDateConverter implements Converter<String, Date> {

    private static Set<String> patterns = null;

    static {
        patterns = new HashSet<String>();
        patterns.add("yyyy-MM-dd");
        patterns.add("yyyy/MM/dd");
        patterns.add("yyyy\\\\MM\\\\dd");
        patterns.add("yyyy年MM月dd日");
    }

    @Override
    public Date convert(String arg0) {
        Date date = null;
        for (String pattern : patterns) {
            try {
                date = new Date(new SimpleDateFormat(pattern).parse(arg0).getTime());
                return date;
            } catch (ParseException e) {
                continue;
            }
        }
        throw new RuntimeException("unsupported type String to Date");
    }

}
