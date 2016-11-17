package com.gutongxue.www.utilities;

import com.gif4j.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Shadow on 2016/11/16.
 */
public class GifUtil {



    public static void makeGif(File src, File dest, int width, int height)

            throws IOException {

        GifImage gifImage = GifDecoder.decode(src);// 创建一个GifImage对象.

        GifImage resizeIMG = GifTransformer.resize(gifImage, width, height,

                true);

        GifEncoder.encode(resizeIMG, dest);

    }





    public static void makeGif(String src, String dest, int width, int height)

            throws IOException {

        GifImage gifImage = GifDecoder.decode(new File(src));// 创建一个GifImage对象.

        makeGif(new File(src), new File(dest), gifImage.getScreenWidth() / 2,

                gifImage.getScreenHeight() / 2);

    }





    public static void makeGif(File src, File dest) throws IOException {

        GifImage gifImage = GifDecoder.decode(src);// 创建一个GifImage对象.

        makeGif(src, dest, gifImage.getScreenWidth() / 2, gifImage

                .getScreenHeight() / 2);

    }





    public static void makeGif(String src, String dest) throws IOException {

        makeGif(new File(src), new File(dest));

    }











    public static void addTextWatermarkToGif(File src, String watermarkText, File dest)throws IOException {





        //水印初始化、设置（字体、样式、大小、颜色）

//        TextPainter textPainter = new TextPainter(new Font("黑体", Font.ITALIC, 15));
        TextPainter textPainter = new TextPainter(new Font("黑体", Font.ITALIC, 15));

        textPainter.setOutlinePaint(Color.WHITE);

        BufferedImage renderedWatermarkText = textPainter.renderString(watermarkText, true);



        //图片对象

        GifImage gf = GifDecoder.decode(src);



        //获取图片大小

        int iw = gf.getScreenWidth();

        int ih = gf.getScreenHeight();



        //获取水印大小

        int tw = renderedWatermarkText.getWidth();

        int th = renderedWatermarkText.getHeight();



        //水印位置

        Point p = new Point();

        p.x = iw - tw - 5;

        p.y = ih - th - 4;



        //加水印

        Watermark watermark = new Watermark(renderedWatermarkText, p);

        gf = watermark.apply(GifDecoder.decode(src), true);



        //输出

        GifEncoder.encode(gf, dest);

    }



    public static void main(String[] arg) throws Exception{

//        String path = "D:\\";
//
//        File dir = new File(path);
//
//        File[] fs = dir.listFiles();
//
//        File f = null;
//
//        String fn = null;
//
//        for(int i = 0; i < fs.length; i ++){
//
//            f = fs[i];
//
//            fn = f.getName();
//
//            if(f.isDirectory() || fn.substring(fn.lastIndexOf( ".")).toLowerCase().equals(".gif") == false){
//
//                continue;
//
//            }
//
//            GifUtil.addTextWatermarkToGif(new File("D:\\1.gif"), "www.gutongxue.com", new File("D:\\1.gif"));
//
//        }
        GifUtil.addTextWatermarkToGif(new File("D:\\1.gif"), "www.gutongxue.com", new File("D:\\1.gif"));


    }

}
