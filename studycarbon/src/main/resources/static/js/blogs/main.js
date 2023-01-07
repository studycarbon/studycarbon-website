// comment main js
$(function() {
    var _pageSize;

    function getBlogList(pageIndex, pageSize) {
        $.ajax({
            url: "/blogs/all",
            contentType: "application/json",
            data: {
                "async": true,
                "pageIndex": pageIndex,
                "pageSize": pageSize,
                "keyword": $("#searchName").val()
            },
            success: function(data) {
                $("#mainContainer").html(data);
            },
            error: function() {
                toastr.error("error!");
            }
        });
    }

    // 分页
    $.tbpage("#mainContainer", function(pageIndex, pageSize) {
        getBlogList(pageIndex, pageSize);
        _pageSize = pageSize;
    });

    // 搜索
    $("#searchNameBtn").click(function() {
        getBlogList(0, _pageSize);
    });


})