function comment(e) {
    let content = $("#" + $(e).data("content-id")).val();
    if (!content) {
        alert("评论内容不能为空哦");
        return;
    }
    let parentType = $(e).data("parent-type");
    let parentId = $(e).data("parent-id");
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "content": content,
            "parentType": parentType,
            "parentId": parentId
        }),
        success: function (data) {
            switch (data.code) {
                case 2000:
                    window.location.reload();
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

function collapse(e) {
    let id = 'comment-' + $(e).data("id");
    let target = $('#' + id);
    let well = $("#well_" + $(e).data("id"));
    let length = well.children().length;

    if (length === 1) {
        $.getJSON("/comment/" + $(e).data("id"), function (response) {
            $.each(response.data.reverse(), function (index, comment) {
                let media = $("<div>", {
                    "class": "media"
                });

                let mediaLeft = $("<div>", {
                    "class": "media-left"
                }).append($("<img>", {
                    "src": comment.user.avatarUrl,
                    "class": "media-object avatar"
                }));

                let commentFooter = $("<div>", {
                    "class": "comment-footer"
                }).append($("<span>", {
                    "class": "glyphicon glyphicon-thumbs-up icon"
                })).append($("<span>", {
                    "class": "glyphicon glyphicon-comment icon"
                }));
                let mediaBody = $("<div>", {
                    "class": "media-body"
                }).append($("<h6>", {
                    "class": "commenter",
                    "html": comment.user.name + " · " + moment(comment.gmtCreate).format("YYYY-M-D H:m:s")
                })).append($("<p>", {
                    "html": comment.content
                })).append(commentFooter);
                media.append(mediaLeft).append(mediaBody).append($("<hr>", {
                    "class": "hr-comment"
                }));
                well.prepend(media);
            });
        });
    }
    target.toggleClass("in");
}

function showTagSelector() {
    $("#tag_selector").show();
}

function addTag(e) {
    let tag = $(e).data("tag");
    let input = $("#tag_input");
    let inputValue = input.val();
    let tags = inputValue.split(";");
    if (tags.includes(tag)) {
        $(e).hide();
        return;
    }
    inputValue += tag;
    input.val(inputValue + ";");
}

function toggleActive(e) {
    let list = $(e).parent().get();
    let children = $(list).children();
    children.removeClass("active");
    $(e).addClass("active");
}
