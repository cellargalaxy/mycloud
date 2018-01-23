/**
 * Created by cellargalaxy on 18-1-10.
 */

function backupAll(url) {
    if (confirm("确认备份全部文件?")) {
        $.ajax({
            url: url,
            type: 'post',
            data: {},
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",

            error: function () {
                alert("网络错误!");
            },
            success: function (data) {
                if (data.result) {
                    alert(data.data);
                    location.reload();
                } else {
                    alert(data.data);
                }
            }
        })
    }
}

function restoreAll(url) {
    if (confirm("确认恢复全部文件?")) {
        $.ajax({
            url: url,
            type: 'post',
            data: {},
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",

            error: function () {
                alert("网络错误!");
            },
            success: function (data) {
                if (data.result) {
                    alert(data.data);
                    location.reload();
                } else {
                    alert(data.data);
                }
            }
        })
    }
}

function backup(filename, date, url) {
    if (filename == null || filename == "") {
        alert("无效文件名: " + filename);
        return;
    }
    if (date == null || date == "") {
        alert("无效文件日期: " + date);
        return;
    }
    if (confirm("确认备份文件?: " + filename + "(" + date + ")")) {
        $.ajax({
            url: url,
            type: 'post',
            data: {"filename": filename, "date": date},
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",

            error: function () {
                alert("网络错误!");
            },
            success: function (data) {
                if (data.result) {
                    alert(data.data);
                    location.reload();
                } else {
                    alert(data.data);
                }
            }
        })
    }
}

function restore(filename, date, url) {
    if (filename == null || filename == "") {
        alert("无效文件名: " + filename);
        return;
    }
    if (date == null || date == "") {
        alert("无效文件日期: " + date);
        return;
    }
    if (confirm("确认恢复文件?: " + filename + "(" + date + ")")) {
        $.ajax({
            url: url,
            type: 'post',
            data: {"filename": filename, "date": date},
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",

            error: function () {
                alert("网络错误!");
            },
            success: function (data) {
                if (data.result) {
                    alert(data.data);
                    location.reload();
                } else {
                    alert(data.data);
                }
            }
        })
    }
}

function remove(filename, date, url) {
    if (filename == null || filename == "") {
        alert("无效文件名: " + filename);
        return;
    }
    if (date == null || date == "") {
        alert("无效文件日期: " + date);
        return;
    }
    if (confirm("确认删除文件?: " + filename + "(" + date + ")")) {
        $.ajax({
            url: url,
            type: 'post',
            data: {"filename": filename, "date": date},
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",

            error: function () {
                alert("网络错误!");
            },
            success: function (data) {
                if (data.result) {
                    alert(data.data);
                    location.reload();
                } else {
                    alert(data.data);
                }
            }
        })
    }
}

function uploadFile() {
    var uploadFileFormFile = $('#uploadFileFormFile');
    var uploadFileFormDate = $("#uploadFileFormDate");
    var uploadFileFormDescription = $("#uploadFileFormDescription");

    var files = uploadFileFormFile.prop('files');
    var date = uploadFileFormDate.val();
    var status = $("input[name='status']:checked").val();
    var description = uploadFileFormDescription.val();
    if (files == null || files == '' || files.length == 0 || files[0] == null || files[0] == '') {
        alert('请选择文件');
        return;
    }
    if (status != 0 && status != 1) {
        alert('是否备份值不合法:' + status);
        return;
    }
    if (description != null && description == '') {
        description = null;
    }
    var data = new FormData();
    data.append('file', files[0]);
    data.append('date', date);
    data.append('status', status);
    data.append('description', description);
    data.append('token', $("#token").attr("value"));
    $.ajax({
        url: $("#uploadFileForm").attr("action"),
        type: 'post',
        data: data,
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        cache: false,
        processData: false,
        contentType: false,

        error: function () {
            alert('网络异常');
        },
        success: function (data) {
            if (data.success == 0) {
                alert(data.message);
            } else {
                alert('信息:' + data.message + '\nurl:' + data.url);
                location.reload();
            }
        }
    });
}