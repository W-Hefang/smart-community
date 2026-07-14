import axios from 'axios'
import type { Response } from '../types'
import { ElMessage, ElMessageBox } from 'element-plus'

const instance = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 10000
})

instance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')
    console.log('===== 请求拦截器 =====')
    console.log('请求URL:', config.url)
    console.log('获取到的token:', token)
    
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
      console.log('已设置 Authorization:', config.headers.Authorization)
    } else {
      console.log('token为空，未设置Authorization')
    }
    return config
  },
  (error) => Promise.reject(error)
)

instance.interceptors.response.use(
  (response) => {
    const data = response.data as Response
    if (data.code !== 200) {
      ElMessage.error(data.message || '请求失败')
      return Promise.reject(data)
    }
    return data.data
  },
  (error) => {
    if (error.response?.status === 401) {
      ElMessageBox.confirm(
        '登录已过期，请重新登录',
        '提示',
        {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).then(() => {
        localStorage.removeItem('token')
        localStorage.removeItem('userInfo')
        window.location.href = '#/'
      })
    } else {
      ElMessage.error(error.message || '网络错误')
    }
    return Promise.reject(error)
  }
)

export default instance