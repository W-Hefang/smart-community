// src/api/community.ts
import axios from '../utils/axios'
import type { Community, CommunityVO, CommunityPageLikeVO, ScaleStatisticsVO } from '../types'

// 分页查询（带模糊搜索）
export const pageLikeName = async (page: number, size: number, condition: string): Promise<CommunityPageLikeVO<CommunityVO>> => {
  const response = await axios.post('/community/pageLikeName', { page, size, condition })
  return response as unknown as CommunityPageLikeVO<CommunityVO>
}

// 根据ID查询
export const getById = async (id: number): Promise<CommunityVO> => {
  const response = await axios.get(`/community/getById?id=${id}`)
  return response as unknown as CommunityVO
}

// 根据名称模糊查询
export const getLikeName = async (name: string): Promise<CommunityVO[]> => {
  const response = await axios.get(`/community/getLikeName?name=${name}`)
  return response as unknown as CommunityVO[]
}

// 新增社区
export const addCommunity = async (data: Omit<Community, 'communityId' | 'createTime' | 'updateTime'>): Promise<Community> => {
  const response = await axios.post('/community/add', data)
  return response as unknown as Community
}

// 删除社区
export const deleteById = async (id: number): Promise<void> => {
  await axios.post(`/community/deleteById?id=${id}`)
}

// 更新社区
export const updateById = async (community: Community): Promise<void> => {
  await axios.post('/community/updateById', community)
}

// 统计社区规模分布
export const getStatistics = async (): Promise<ScaleStatisticsVO[]> => {
  const response = await axios.get('/community/statistics')
  return response as unknown as ScaleStatisticsVO[]
}


// 获取社区总数
export const getTotalCount = async (): Promise<{ totalCount: number }> => {
  const response = await axios.get('/community/getTotalCount')
  return response as unknown as { totalCount: number }
}

// 按规模范围查询
export const getByScaleRange = async (minScale?: number, maxScale?: number): Promise<CommunityVO[]> => {
  let url = '/community/getByScaleRange'
  const params = new URLSearchParams()
  if (minScale !== undefined) params.append('minScale', minScale.toString())
  if (maxScale !== undefined) params.append('maxScale', maxScale.toString())
  if (params.toString()) url += `?${params.toString()}`
  const response = await axios.get(url)
  return response as unknown as CommunityVO[]
}