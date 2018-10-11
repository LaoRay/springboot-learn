package com.zhengtoon.framework.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;

public class BigDecimalUtils {
    
    private final static DecimalFormat DF= new DecimalFormat("#,##0.00");
    
    public final static String DOLLAR_SIGN = "$";
    
    public static final RoundingMode ROUNDING_MODE_FOR_FORMAT_UTIL = RoundingMode.HALF_UP;
    
    public static BigDecimal accurateTwoValidDigits(BigDecimal value) {
        BigDecimal result = null;
        if (value != null) {
            if (value.compareTo(BigDecimal.ZERO) != 0) {
                result = BigDecimal.valueOf(0.01);
                BigDecimal twoDigit = value.setScale(2, BigDecimal.ROUND_HALF_UP);
                result = twoDigit.compareTo(result) < 0 ? result : twoDigit;
            } else {
                result = BigDecimal.ZERO;
            }
        }
        return result;
    }
    
    public static BigDecimal accurateTwoValidDigits(Double totalCost) {
        BigDecimal result = null;
        if (totalCost != null) {
            result = accurateTwoValidDigits(BigDecimal.valueOf(totalCost));
        }
        return result;
    }
    
    public static String formatTo2DecimalPlaces(BigDecimal num){
        if(num==null){
            num=BigDecimal.ZERO;
        }
        DF.setRoundingMode(ROUNDING_MODE_FOR_FORMAT_UTIL);
        return DF.format(num);
    }
}
