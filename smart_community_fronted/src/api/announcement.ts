import axios from '../utils/axios'
import type { Announcement, AnnouncementVO } from '../types'

export const addAnnouncement = async (data: Omit<Announcement, 'id' | 'viewCount' | 'status' | 'createTime' | 'updateTime'>): Promise<AnnouncementVO> => {
  const response = await axios.post('/announcement/add', data)
  return response as unknown as AnnouncementVO
}

export const updateAnnouncement = async (data: Announcement): Promise<AnnouncementVO> => {
  const response = await axios.post('/announcement/update', data)
  return response as unknown as AnnouncementVO
}

export const deleteById = async (id: number): Promise<void> => {
  await axios.post(`/announcement/deleteById?id=${id}`)
}

export const getById = async (id: number): Promise<AnnouncementVO> => {
  const response = await axios.get(`/announcement/getById?id=${id}`)
  return response as unknown as AnnouncementVO
}

export const listAll = async (): Promise<AnnouncementVO[]> => {
  const response = await axios.get('/announcement/listAll')
  return response as unknown as AnnouncementVO[]
}

export const search = async (keyword?: string, status?: string): Promise<AnnouncementVO[]> => {
  const params: Record<string, string> = {}
  if (keyword) params.keyword = keyword
  if (status) params.status = status
  const response = await axios.get('/announcement/search', { params })
  return response as unknown as AnnouncementVO[]
}

export interface PageResult<T> {
  records: T[]
  total: number
  current: number
  size: number
}

export const pageSearch = async (page: number, size: number, keyword?: string, status?: string): Promise<PageResult<AnnouncementVO>> => {
  const params: Record<string, string | number> = { page, size }
  if (keyword) params.keyword = keyword
  if (status) params.status = status
  const response = await axios.get('/announcement/page', { params })
  return response as unknown as PageResult<AnnouncementVO>
}

export const listByStatus = async (status: string): Promise<AnnouncementVO[]> => {
  const response = await axios.get(`/announcement/listByStatus?status=${status}`)
  return response as unknown as AnnouncementVO[]
}

export const publish = async (id: number): Promise<void> => {
  await axios.post(`/announcement/publish?id=${id}`)
}

export const archive = async (id: number): Promise<void> => {
  await axios.post(`/announcement/archive?id=${id}`)
}