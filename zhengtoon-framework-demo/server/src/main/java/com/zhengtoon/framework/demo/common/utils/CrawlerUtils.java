package com.zhengtoon.framework.demo.common.utils;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 描述:
 * 爬虫辅助类
 *
 * @author sy
 * @date 2018-09-12 16:10
 */
public class CrawlerUtils {

    /**
     * 公共资源爬取数据
     *
     * @param url         网页地址
     * @param currentPage 当前页
     * @param pageSize    当前页面展示数量
     * @return json串
     */
    public static String getOKHttpResponse(String url, int currentPage, int pageSize) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build();

            FormBody formBody = new FormBody.Builder()
                    .add("page", String.valueOf(currentPage))
                    .add("rows", String.valueOf(pageSize))
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            Response response = client.newCall(request).execute();

            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 根据页面条数 总条数 取得 总页面数
     *
     * @param pageSize 页面条数
     * @param total    总条数
     * @return 总页数
     */
    public static Integer getPageCount(int pageSize, int total) {
        if (total == 0) {
            return 0;
        }
        if (pageSize == 0) {
            return 0;
        }
        int pages = total / pageSize;
        if (total % pageSize != 0) {
            pages++;
        }
        return pages;
    }


    /**
     * @description: 获得当前日期前 month 月份 的第一天日期
     * @author: dongchao
     * @date: 2018-09-13
     * @param month 向前月份数
     * @return java.util.Date
     */
    public static Date monthMinus(Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     * @return java.util.Date
     *
     */
    public static Date timeStamp2Date(String seconds) {
        return  new Date(Long.valueOf(seconds + "000")) ;
    }

    public static String processImgSrc(String content, String baseUrl,String classId) {
        Document document = Jsoup.parse(content);
        document.setBaseUri(baseUrl);
        Elements imgElements = document.select("img");
        for (Element el : imgElements) {
            String imgUrl = el.attr("src");
            if (imgUrl.trim().startsWith("/")) {
                el.attr("src", el.absUrl("src"));
            }
        }

        Elements hrefElements = document.select("a[href]");

        for (Element ele : hrefElements) {
            String docUrl = ele.attr("href");
            if (docUrl.trim().startsWith("/")) {
                ele.attr("href", ele.absUrl("abs:href"));
            }
        }
        return document.select(classId).toString();
    }

    public static void main(String[] args) {
        String response = CrawlerUtils.getOKHttpResponse("http://cd.ggzyjyw.com/deallist/1.shtml",1,20);

        System.out.print(" String [response] : " + response);
    }

}
