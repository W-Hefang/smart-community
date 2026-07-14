import axios from '../utils/axios'
import type { Menu } from '../types'

export const getMenusByRoleId = async (roleId: number): Promise<Menu[]> => {
  const response = await axios.get(`/menu/getMenusByRoleId?roleId=${roleId}`)
  return response as unknown as Menu[]
}

export const getAllMenus = async (): Promise<Menu[]> => {
  const response = await axios.get('/menu/getAll')
  return response as unknown as Menu[]
}