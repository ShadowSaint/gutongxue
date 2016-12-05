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
    <%--神马搜索验证网站--%>
    <meta name="shenma-site-verification" content="dad65031d7f2b7bfa2c391dd04befa4c_1480918795">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <script src="/js/jquery-3.1.1.min.js" type="text/javascript"></script>
    <title>谷同学--每日更新笑话、囧图、视频</title>
    <link rel="shortcut icon" href="/images/logo.ico"/>
    <link href="/css/list_css.css" rel="stylesheet" type="text/css"/>
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
                url: "/api/joke_list",
                dataType: 'json',
                data: {page: page, size: size},
                success: function (data) {
                    console.log(data);
//                    $('.content').empty();
                    total = data.result.count;
                    for (var i = 0; i < data.result.list.length; i++) {
                        var text = data.result.list[i].content;
                        $('.content').append('<div class="content_div">' + text + '</div>');
                    }
                },
                error: function () {

                }
            });
        }
    </script>
</head>

<body onload="changeFontSize()">
<div class="menu" style="position: fixed;bottom:0px;left:0px;width:100%;z-index:10000;">
    <ul>
        <li>
            <a href="/joke"><img src="/images/joke_checked_small.png" height="40px"></a>
        </li>
        <li>
            <a href="/image"><img src="/images/image_uncheck_small.png" height="40px"></a>
        </li>
        <li>
            <a href="/video"><img src="/images/video_uncheck_small.png" height="40px"></a>
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
