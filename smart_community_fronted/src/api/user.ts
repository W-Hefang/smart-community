// src/api/user.ts
import axios from '../utils/axios'
import type { UserLoginDTO, UserLoginVO, User, UserPageLikeVO } from '../types'

export const login = async (data: UserLoginDTO): Promise<UserLoginVO> => {
  const response = await axios.post('/user/login', data)
  return response as unknown as UserLoginVO
}

export const pageLikePhone = async (page: number, size: number, condition: string): Promise<UserPageLikeVO<User>> => {
  const response = await axios.post('/user/pageLikePhone', { page, size, condition })
  return response as unknown as UserPageLikeVO<User>
}

export const addUser = async (data: Omit<User, 'userId' | 'createTime' | 'updateTime'>): Promise<User> => {
  const response = await axios.post('/user/add', data)
  return response as unknown as User
}

export const deleteById = async (id: number): Promise<void> => {
  await axios.post(`/user/deleteById?id=${id}`)
}

export const updateById = async (user: User): Promise<void> => {
  await axios.post('/user/updateById', user)
}

export const getById = async (id: number): Promise<User> => {
  const response = await axios.get(`/user/getById?id=${id}`)
  return response as unknown as User
}

export const getLikePhone = async (phone: string): Promise<User[]> => {
  const response = await axios.get(`/user/getLikePhone?phone=${phone}`)
  return response as unknown as User[]
}
export const deleteUserBatch = async (ids: number[]): Promise<void> => {
  await axios.post('/user/deleteBatch', { ids })
}