/**
 * Created by cellargalaxy on 17-11-11.
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

