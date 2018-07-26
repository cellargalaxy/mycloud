//格式化时间戳
function formatTimestamp(timestamp, fmt) {
  return formatDate(new Date(timestamp), fmt);
}

//日期对象格式化
function formatDate(date, fmt) {
  var o = {
    "M+": date.getMonth() + 1, //月份
    "d+": date.getDate(), //日
    "h+": date.getHours(), //小时
    "m+": date.getMinutes(), //分
    "s+": date.getSeconds(), //秒
    "q+": Math.floor((date.getMonth() + 3) / 3), //季度
    "S": date.getMilliseconds() //毫秒
  };
  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
  }
  for (var k in o) {
    if (new RegExp("(" + k + ")").test(fmt)) {
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ?
        (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    }
  }
  return fmt;
}

//文件大小格式化
function formatFileSize(bytes) {
  var i = Math.floor(Math.log(bytes) / Math.log(1024)),
    sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
  return (bytes / Math.pow(1024, i)).toFixed(2) * 1 + ' ' + sizes[i];
}

// function simpleDealApi(axios) {
//   return axios
//     // .then(res => {
//     // })
//     .catch(err => {
//       alert('网络异常:' + err)
//     })
// }

export default {
  formatTimestamp: formatTimestamp,
  formatDate: formatDate,
  formatFileSize: formatFileSize,
  // simpleDealApi: simpleDealApi,
}
