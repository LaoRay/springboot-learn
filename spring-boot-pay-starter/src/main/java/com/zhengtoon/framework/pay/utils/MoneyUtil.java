package com.zhengtoon.framework.pay.utils;

import java.math.BigDecimal;

public class MoneyUtil {

    /**
     * 元转分
     *
     * @param yuan
     * @return
     */
    public static Integer yuan2Fen(BigDecimal yuan) {
        return yuan.movePointRight(2).intValue();
    }

    /**
     * 分转元
     *
     * @param fen
     * @return
     */
    public static BigDecimal fen2Yuan(Integer fen) {
        return new BigDecimal(fen).movePointLeft(2);
    }
}
