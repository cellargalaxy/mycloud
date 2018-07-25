import axios from 'axios';
import qs from 'qs';

// axios配置
var instance = axios.create({
  baseURL: 'http://localhost:8080/',
  timeout: 5000,
});

// 添加请求拦截器
instance.interceptors.request.use(
  config => {
    // 在发送请求之前做些什么
    config.data = qs.stringify(config.data);
    config.headers = {
      'Content-Type': 'application/x-www-form-urlencoded'
    };
    return config
  },
  error => {
    // 对请求错误做些什么
    console.log(error);
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
    return Promise.reject(error);
  }
);

export default instance;
