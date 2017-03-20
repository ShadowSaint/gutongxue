package com.gutongxue.www.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shadow on 2017/3/2.
 */
public class WebPageCrawler {
    public static void main(String[] args) {
        String url1 = "http://www.cnblogs.com/#p2";
//        String url1 = "http://www.jsph.org.cn/rencaizhaopin/zhaopingonggao/gaocengcirencaiyinjin/";
        String html1 = HtmlUtil.sendGetGzip(url1, "utf-8");
        String url2 = "http://www.cnblogs.com/#p3";
//        String url2 = "http://www.jsph.org.cn/rencaizhaopin/zhaopingonggao/gaocengcirencaiyinjin/index_2.html";
        String html2 = HtmlUtil.sendGetGzip(url2, "utf-8");
        Document doc1 = Jsoup.parse(html1);
        Elements elements1 = doc1.children();
        Document doc2 = Jsoup.parse(html2);
        Elements elements2 = doc2.children();

        System.out.println(returnListContent(elements1, elements2,true,new Elements()));
    }

//    private static String returnListContent(Elements content1,Elements content2){
//        List<Element> elementList1=new ArrayList<>();
//        List<Element> elementList2=new ArrayList<>();
//        for (Element element1:content1){
//            Elements elements1=element1.children();
//            for (Element element2:content2){
//                Elements elements2=element2.children();
//                for (Element element1Child:elements1){
//                    for (Element element2Child:elements2){
//                        if (element1Child.text().equals(element2Child.text())){
//                            elementList1.add(element1Child);
//                            elementList2.add(element2Child);
//                        }
//                    }
//                }
//            }
//        }
//        if (elementList1.size()==0&&elementList2.size()==0){
//            return content1.toString();
//        }
//        for (Element element:elementList1){
//
//        }
//    }

    private static Elements returnListContent(Elements content1, Elements content2, boolean success,Elements result) {
        List<Element> elementList1 = new ArrayList<>();
        List<Element> elementList2 = new ArrayList<>();
        //先遍历每个标签,看是不是想要的
        boolean end=true;
        if (content1.size()<2){
            end=false;
        }
        for (int i=1;i<content1.size();i++){
            String tag0=content1.get(i-1).tagName();
            String tag1=content1.get(i).tagName();
            if (!tag0.equals(tag1)){
                end=false;
            }
            if (content1.get(i).select("a").size()!=1){
                end=false;
            }
        }
        if (end){
            return content1;
        }
        for (int i = 0; i < content1.size(); i++) {
            if (content1.get(i).tagName().equals("head")){
                elementList1.add(content1.get(i));
                elementList2.add(content2.get(i));
                success = true;
                continue;
            }
            if (content1.get(i).text().equals(content2.get(i).text())) {
                elementList1.add(content1.get(i));
                elementList2.add(content2.get(i));
                success = true;
            }
        }
        for (Element element:elementList1){
            content1.remove(element);
        }
        for (Element element:elementList2){
            content2.remove(element);
        }
        if (success) {
            for (int i = 0 ; i<content1.size() ; i++){
                return returnListContent(content1.get(i).children(),content2.get(i).children(),false,content1.get(i).children());
            }
        }
        if (!success&&content1.size()==1){
            for (int i = 0 ; i<content1.size() ; i++){
                return returnListContent(content1.get(i).children(),content2.get(i).children(),false,result);
            }
        }
        return result;
    }
}
