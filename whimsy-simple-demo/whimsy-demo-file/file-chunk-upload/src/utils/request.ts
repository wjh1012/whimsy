import axios from 'axios';

//1. 创建axios对象
const service = axios.create({
    baseURL: process.env["VITE_API_URL"]
});

//2. 请求拦截器
service.interceptors.request.use(config => {
    return config;
}, error => {
    return Promise.reject(error)
});

//3. 响应拦截器
service.interceptors.response.use(response => {
    //判断code码
    return response.data;
}, error => {
    return Promise.reject(error);
});

export default service;
