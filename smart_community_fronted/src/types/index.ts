export interface UserLoginDTO {
  phone: string
  password: string
}

export interface Menu {
  id: number
  name: string
  remark: string
  type: string
  url: string
  icon: string
  pid: number
}

export interface UserLoginVO {
  userId: number
  userName: string
  userPhone: string
  userType: string
  userStatus: number
  createTime: string
  updateTime: string
  token: string
  menuList: Menu[] | null
  idCard: string | null
}

// 修正：User 接口字段与后端匹配
export interface User {
  userId?: number
  userName: string
  userPhone: string
  userPassword?: string
  idCard?: string
  userType: string
  userStatus?: number
  createTime?: string
  updateTime?: string
}

// 修正：分页响应类型
export interface UserPageLikeVO<T = any> {
  data: T[]
  total: number
}

export interface Response<T = any> {
  code: number
  message: string
  data: T
}
export interface Community {
  communityId?: number
  communityName: string
  address: string
  scale: number
  leader: string
  phone: string
  description: string
  createTime?: string
  updateTime?: string
}

export interface CommunityVO {
  communityId: number
  communityName: string
  address: string
  scale: number
  leader: string
  phone: string
  description: string
  createTime: string
  updateTime: string
}

export interface CommunityPageLikeVO<T = any> {
  data: T[]
  total: number
}

export interface ScaleStatisticsVO {
  scaleGroup: string
  count: number
  avgScale: number
}

export interface Announcement {
  id?: number
  title: string
  content: string
  announcementType: string
  priority: string
  publisherId: number
  publisherName: string
  status: string
  publishTime?: string
  endTime?: string
  viewCount?: number
  attachmentUrl?: string
  remark?: string
  createTime?: string
  updateTime?: string
}

export interface AnnouncementVO {
  id: number
  title: string
  content: string
  announcementType: string
  priority: string
  publisherId: number
  publisherName: string
  status: string
  publishTime: string | null
  endTime: string | null
  viewCount: number
  attachmentUrl: string | null
  remark: string | null
  createTime: string
  updateTime: string
}