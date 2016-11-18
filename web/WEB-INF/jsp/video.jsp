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
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <script src="/js/jquery-3.1.1.min.js" type="text/javascript"></script>
    <title>谷同学--每日更新笑话、囧图、视频</title>
    <link href="/css/list_css.css" rel="stylesheet" type="text/css"/>
    <link href="/css/video-js.min.css" rel="stylesheet">
    <script src="/js/video.min.js"></script>
    <script type="text/javascript">
        var total;
        var page = 0;
        var size = 6;
        $(document).ready(function () {
            getPage(page);
            $(window).scroll(function () {

                        var Scroll = $(document).scrollTop();
                        var height = $(window).height();
                        var WD = $(document).height();
                        console.log(Scroll);
                        console.log(height);
                        console.log(Scroll + height);
                        console.log(WD);
                        if (Scroll + height == WD) {
                            if (total < 10) {
                                page = 0;
                            } else {
                                if (page < Math.ceil(total / size)) {
                                    page++;
                                    if (page != Math.ceil(total / size)) {
                                        getPage(page);
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
        function getPage(page) {
            $.ajax({
                type: "POST",
                url: "/api/video_list",
                dataType: 'json',
                data: {page: page, size: size},
                success: function (data) {
                    console.log(data);
//                    $('.content').empty();
                    total = data.result.count;
                    for (var i = 0; i < data.result.list.length; i++) {
                        var description = data.result.list[i].description;
                        var cover= data.result.list[i].cover;
                        var url=data.result.list[i].url;
                        $('.content').append('<div class="content_div">' + description + '</div>');
                        $($('.content_div')[page*size+i]).append('<video class="content_video" controls preload="none" width="100%"' +
                                'poster="'+cover+'" data-setup="{}">' +
                                '<source src="'+url+'" type="video/mp4"/>')
                    }
                },
                error: function () {

                }
            });
        }
    </script>
</head>

<body onload="changeFontSize()">
<div class="menu" style="position: fixed;bottom:0px;left:0px;width:100%;">
    <ul>
        <li>
            <a href="/joke"><img src="/images/joke_uncheck_small.png"></a>
        </li>
        <li>
            <a href="/image"><img src="/images/image_uncheck_small.png"></a>
        </li>
        <li>
        <a href="/video"><img src="/images/video_checked_small.png"></a>
        </li>
        <div class="clear"></div>
    </ul>
</div>
<div class="content">
</div>
<div class="menu" style="text-align: center">暂无更多</div>
<div class="menu"></div>
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

    }
    ;
</script>
</html>
