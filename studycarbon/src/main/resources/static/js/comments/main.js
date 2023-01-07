// comment main js
$(function() {
    var _pageSize;

    function getCommentList(pageIndex, pageSize) {
        $.ajax({
            url: "/comments/all",
            contentType: "application/json",
            data: {
                "async": true,
                "pageIndex": pageIndex,
                "pageSize": pageSize,
                "content": $("#searchName").val()
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
        getCommentList(pageIndex, pageSize);
        _pageSize = pageSize;
    });

    // 搜索
    $("#searchNameBtn").click(function() {
        getCommentList(0, _pageSize);
    });

    // 删除评论
    $("#rightContainer").on("click", ".delete-comment", function() {
        // 获取 CSRF Token 
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        // 组建url，设置type，发送之前进行处理
        $.ajax({
            url: "/comments/" + $(this).attr("commentId"),
            type: 'DELETE',
            beforeSend: function(request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token 
            },
            success: function(data) {
                if (data.success) {
                    // 从新刷新主界面
                    getCommentList(0, _pageSize);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function() {
                toastr.error("error!");
            }
        });
    });

    // 获取编辑评论页面
    $("#rightContainer").on("click",".edit-comment", function () {
        $.ajax({
            url: "/comments/edit/" + $(this).attr("commentId"),
            success: function(data){
                $("#userFormContainer").html(data);
            },
            error : function() {
                toastr.error("error!");
            }
        });
    });

    $("#submitEdit").click(function() {
        $.ajax({
            url: "/comments/update",
            type: 'POST',
            data:$('#commentForm').serialize(),
            success: function(data){
                $('#commentForm')[0].reset();

                if (data.success) {
                    // 从新刷新主界面
                    getCommentList(0, _pageSize);
                } else {
                    toastr.error(data.message);
                }

            },
            error : function() {
                toastr.error("error!");
            }
        });
    });


})