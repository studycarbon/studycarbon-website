/**
 * Bolg main JS.
 * Created by waylau.com on 2017/3/9.
 */
"use strict";
//# sourceURL=main.js

// DOM 加载完再执行
$(function() {

    // 菜单事件
    $(".blog-menu .list-group-item").click(function() {
        // 获取到链接
        var url = $(this).attr("url");

        // 先移除其他的点击样式，再添加当前的点击样式
        $(".blog-menu .list-group-item").removeClass("active");
        $(this).addClass("active");

        // 加载其他模块的页面到右侧工作区
        // 根据选择不同加载不同的页面：如选择用户管理，展示用户管理页面
        // 选择
        $.ajax({
            url: url,
            success: function(data) {
                $("#rightContainer").html(data);
            },
            error: function() {
                alert("error");
            }
        });
    });


    // 选中菜单第一项
    $(".blog-menu .list-group-item:first").trigger("click");
});