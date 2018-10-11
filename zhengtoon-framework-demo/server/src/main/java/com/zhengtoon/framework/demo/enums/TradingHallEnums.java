package com.zhengtoon.framework.demo.enums;

/**
 * @author: qindaorong
 * @Date: 2018/9/25 14:49
 * @Description:
 */
public enum TradingHallEnums {

    ENGINEERING_CONSTRUCTION ("http://cd.ggzyjyw.com/deallist/1.shtml", "1"),
    GOVERNMENT_PROCUREMENT("http://cd.ggzyjyw.com/deallist/2.shtml", "2"),
    LAND_MINING("http://cd.ggzyjyw.com/deallist/3.shtml", "3"),
    STATE_OWNED("http://cd.ggzyjyw.com/deallist/4.shtml", "4");


    private String url;
    private String type;

    TradingHallEnums(String url,String type){
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }
}
