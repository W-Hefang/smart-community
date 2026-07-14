import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { Menu, UserLoginVO } from '../types'

// 修改 UserInfo 接口，字段与 UserLoginVO 保持一致
export interface UserInfo {
  userId: number
  userName: string
  userPhone: string
  userType: string
  userStatus: number
  createTime: string
  updateTime: string
  token: string
  menuList: Menu[]
  idCard?: string | null
}

export const useUserStore = defineStore('user', () => {
  const userInfo = ref<UserInfo | null>(null)
  const token = ref<string>('')

  const setUserInfo = (info: UserLoginVO) => {
    // 直接赋值，因为字段名已经匹配
    userInfo.value = info as UserInfo
    token.value = info.token
    localStorage.setItem('token', info.token)
    localStorage.setItem('userInfo', JSON.stringify(userInfo.value))
  }

  const clearUserInfo = () => {
    userInfo.value = null
    token.value = ''
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
  }

  const loadUserInfo = () => {
    const savedToken = localStorage.getItem('token')
    const savedUserInfo = localStorage.getItem('userInfo')
    if (savedToken && savedUserInfo) {
      token.value = savedToken
      try {
        userInfo.value = JSON.parse(savedUserInfo)
      } catch {
        clearUserInfo()
      }
    }
  }

  return {
    userInfo,
    token,
    setUserInfo,
    clearUserInfo,
    loadUserInfo
  }
})