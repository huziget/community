/**
 * 提交回复
 */
function reflexComment() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    $.ajax({
        type: "POST",
        contentType: 'application/json',
        url: "/comment",
        data: JSON.stringify({
            "parentId": questionId,
            "content": commentContent,
            "type": 1
        }),
        success: function (response) {
            if (response.code == 200) {
                //如果code 200 登录成功，隐藏回复的div
                $("#comment_section").hide();
            } else {
                if(response.code == 2003){
                    //如果code为2003,用户未登录，提示登录
                    var isAccepted = confirm(response.message);
                    if(isAccepted){
                        //打开新页面
                        window.open("https://github.com/login/oauth/authorize?client_id=1e15e2901e9c2ad86816&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        //存储localStorage，来控制新页面登录，并关闭新页面。
                        window.localStorage.setItem("closable",true);
                    }
                }else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

/**
 * 展开二级评论
 */
function collapseComments() {
    var data = $.data(this,"id");
    console.log(data);
}