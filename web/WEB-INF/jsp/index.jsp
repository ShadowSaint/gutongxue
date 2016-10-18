<%--
  Created by IntelliJ IDEA.
  User: Shadow
  Date: 2016/10/8
  Time: 21:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <script src="http://cdn.staticfile.org/jquery/2.0.0/jquery.min.js" type="text/javascript"></script>
    <title>华北新闻</title>
    <style type="text/css">
        *{
            margin: 0;
            padding: 0;
        }
        .download_app{
            height:3rem;
            line-height: 3rem;
            width:3rem;
            margin: 0 0.5rem;
            padding: 0.5rem;
            float: right;
        }
        .header{
            height: 4rem;
            line-height: 4rem;
            background-color: #019687;
        }
        .header_span{
            float: left;
            font-family: 微软雅黑;
            font-size: 1.5rem;
            color: white;
            margin-left: 1.5rem;
        }
    </style>
</head>
<body onload="changeFontSize()">
<header class="header">
    <span class="header_span">
        华北石油局&nbsp;华北石油分公司&nbsp;华北石油工程公司
    </span>
    <img src="http://hbnewsimg.cyparty.com/hb1_web_image/ico_download.png" class="download_app" />
</header>
<nav></nav>
</body>
<script>
    //改变字体适应效果
    function changeFontSize() {
        var w = $(document.body).width();
        if (w >= 1080) {
            w = 1080;
            $('html').css('font-size', w / 78 + 'px');
        } else {
            $('html').css('font-size', w / 34 + 'px');
        }

    };
</script>
</html>
