<%--
  Created by IntelliJ IDEA.
  User: Shadow
  Date: 2016/10/8
  Time: 21:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <script src="/js/jquery-3.1.1.min.js" type="text/javascript"></script>
    <title>谷同学--每日更新笑话、囧图、视频</title>
    <style type="text/css">
        /*每个页面都需要的*/
        *{
            margin: 0;
            padding: 0;
        }
        body {
            background: #EAEDF1;
            -webkit-font-smoothing: antialiased;
            font-family: "\5FAE\8F6F\96C5\9ED1" !important;
            margin: 0 auto !important;
            position: relative;
            color: #52514f;
            font-size:14px;
        }
        a {
            text-decoration: none;
            color: #52514f;
        }

        a:link {
            color: #52514f;
            text-decoration: none;
        }

        a:visited {
            color: #52514f;
            text-decoration: none;
        }

        a:visited:hover {
            color: #000000;
            text-decoration: none;
        }

        a:hover {
            text-decoration: none;
            color: #52514f;
        }

        a:active {
            text-decoration: none;
            color: #000000;
        }

        a img {
            border: none;
        }

        li {
            list-style: none;
        }
        /*每个页面都需要的*/
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
        .nav{
            width: 108rem;
            margin: 0 auto;
            /*拉钩绿:00838a*/
            background-color: #086ED2;
        }
        li{
            list-style: none;
            float: left;
            width: 25%;
        }
        .clear{
            clear: both;
        }
        .nav li{
            text-align: center;
        }

    </style>
    <script type="text/javascript">
        var total;
        var page_list=1;
        var size=10;
        $(document).ready(function (){
            getPage(page_list);
            $(window).scroll(function () {

                        var Scroll = $(document).scrollTop();
                        var height = $(window).height();
                        var WD = $(document).height();
                console.log(Scroll);
                console.log(height);
                console.log(Scroll+height);
                        console.log(WD);
                        if (Scroll + height == WD) {
                            if (total < 10) {
                                page_list = 1;
                            } else {
                                if (page_list < Math.ceil(total / size)) {
                                    page_list++;
                                    if (page_list != Math.ceil(total / size)) {
                                        getPage(page_list);
                                    }
                                } else {
                                    $('.not_more').remove();
                                    $('.footer').prepend('<span class="not_more">没有更多数据</span>')
                                }
                            }

                        }
                    }
            );
        })
        function getPage(page_list){
            $.ajax({
                type:"POST",
                url:"/api/joke",
                dataType:'json',
                data:{page:page_list},
                success: function (data) {
                    console.log(data);
//                    $('.content').empty();
                    total = data.result.count;
                    for(var i = 0 ;i < data.result.list.length;i++){
                        var text = data.result.list[i];
                        $('.content').append('<span>'+text+'</span>');
                    }
                },
                error:function(){

                }
            });
        }
    </script>
</head>

<body onload="changeFontSize()">
<nav class="nav">
    <ul>
        <li>
            <a href="/">笑话</a>
        </li>
        <li>囧图</li>
        <li>视频</li>
        <div class="clear"></div>
    </ul>
</nav>
<div class="content">
    <span>我：老婆你骨折过吗？
老婆：有啊！
我：什么位置？
老婆：我掉过牙。
我。。。</span>
</div>
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
