import axios from 'axios';
import qs from 'qs';

var baseURL = 'http://localhost:8080/'

// axios配置
var instance = axios.create({
  baseURL: baseURL,
  timeout: 5000,
});

// 添加请求拦截器
instance.interceptors.request.use(
  config => {
    // 在发送请求之前做些什么
    config.data = qs.stringify(config.data);
    config.headers = {
      'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization':'eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOjEsImNyZWF0ZVRpbWUiOjE1MzMwNTI4MDAwMDAsInVwZGF0ZVRpbWUiOjE1MzMwNTI4MDAwMDAsInBlcm1pc3Npb25zIjoiUk9PVCxBRE1JTixVU0VSIiwic3ViIjoibXljbG91ZCIsImV4cCI6MTUzMzIxMTU2OX0.lCuDy1JqBaIfqFk39hBtd8b8m8mvNlFVZ7ut2zuO3wGYp8cBxXToNwU2yg8xRm4WTgXoxSuZCNG--q4iY7GsqQ'
    };
    return config
  },
  error => {
    // 对请求错误做些什么
    console.log(error);
    alert('网络异常:' + error);
    return Promise.reject(error);
  }
);

// 添加响应拦截器
instance.interceptors.response.use(
  response => {
    // 对响应数据做点什么
    return response;
  },
  error => {
    // 对响应错误做点什么
    console.log(error);
    alert('网络异常:' + error);
    return Promise.reject(error);
  }
);

export default {
  instance: instance,
  baseURL: baseURL,
};
