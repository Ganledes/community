function comment() {
    debugger
    let content = $("#comment").val();
    let questionId = $("#question_id").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "content": content,
            "parentType": 1,
            "parentId": questionId
        }),
        success: function (data) {
            switch (data.code) {
                case 2000:
                    break;
                case 1002:
                    if (confirm(data.message + "。去登录？")) {
                        window.open("https://github.com/login/oauth/authorize?client_id=e632a9c46b0fb6414e15&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", "true");
                    }
                    break;
                default:
                    alert(data.message);
            }
        },
        dataType: "json"
    });
}