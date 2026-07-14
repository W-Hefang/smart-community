<!-- src/views/Home.vue -->
<template>
  <div class="home-container">
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <span class="logo">智慧社区管理系统</span>
        </div>

        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-icon><User /></el-icon>
              {{ userStore.userInfo?.userName }}
              <el-icon><ArrowDown /></el-icon>
            </span>

            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="logout">
                  <el-icon><SwitchButton /></el-icon>
                  退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <el-container>
        <el-aside class="sidebar" width="200px">
          <el-menu
            :default-active="activeMenu"
            class="sidebar-menu"
            mode="vertical"
            router
            background-color="#fff"
            text-color="#303133"
            active-text-color="#409EFF"
            :default-openeds="['1', '3']"
          >
            <template v-for="menu in menuList" :key="menu.id">
              <el-sub-menu v-if="menu.children && menu.children.length > 0" :index="String(menu.id)">
                <template #title>
                  <span>{{ menu.remark || menu.name }}</span>
                </template>

                <el-menu-item
                  v-for="child in menu.children"
                  :key="child.id"
                  :index="child.url"
                >
                  <span>{{ child.remark || child.name }}</span>
                </el-menu-item>
              </el-sub-menu>

              <el-menu-item v-else :index="menu.url">
                <span>{{ menu.remark || menu.name }}</span>
              </el-menu-item>
            </template>
          </el-menu>
        </el-aside>

        <el-main class="main-content">
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useUserStore } from '../stores/user'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, ArrowDown, SwitchButton } from '@element-plus/icons-vue'
import { getMenusByRoleId } from '../api/menu'
import type { Menu } from '../types'

const userStore = useUserStore()
const router = useRouter()
const route = useRoute()
const activeMenu = ref('')

interface MenuItem extends Menu {
  children?: MenuItem[]
}

const defaultMenus: MenuItem[] = [
  {
    id: 1,
    name: '用户管理',
    remark: '用户管理',
    type: 'menu',
    url: '',
    icon: 'User',
    pid: 0,
    children: [
      { id: 11, name: '住户列表', remark: '住户列表', type: 'menu', url: '/user/list', icon: 'List', pid: 1 },
      { id: 12, name: '用户列表', remark: '用户列表', type: 'menu', url: '/user/list', icon: 'UserFilled', pid: 1 }
    ]
  },
  {
    id: 2,
    name: '设备管理',
    remark: '设备管理',
    type: 'menu',
    url: '/device/list',
    icon: 'Monitor',
    pid: 0
  },
  {
    id: 3,
    name: '社区管理',
    remark: '社区管理',
    type: 'menu',
    url: '',
    icon: 'Home',
    pid: 0,
    children: [
      { id: 31, name: '小区列表', remark: '小区列表', type: 'menu', url: '/community/list', icon: 'Building', pid: 3 },
      { id: 32, name: '统计分析', remark: '统计分析', type: 'menu', url: '/community/statistics', icon: 'BarChart', pid: 3 }
    ]
  },
  {
    id: 4,
    name: '房屋管理',
    remark: '房屋管理',
    type: 'menu',
    url: '/house/list',
    icon: 'HomeFilled',
    pid: 0
  },
  {
    id: 5,
    name: '维修管理',
    remark: '维修管理',
    type: 'menu',
    url: '/repair/list',
    icon: 'Wrench',
    pid: 0
  },
  {
    id: 6,
    name: '公告管理',
    remark: '公告管理',
    type: 'menu',
    url: '/announcement/list',
    icon: 'Bell',
    pid: 0
  }
]

const menuList = computed<MenuItem[]>(() => {
  if (!userStore.userInfo?.menuList || userStore.userInfo.menuList.length === 0) {
    return defaultMenus
  }
  
  const menus = userStore.userInfo.menuList.filter((m: Menu) => {
    return m.url && m.url !== '' && m.pid === 0
  })
  
  return menus.map((menu: Menu): MenuItem => ({
    ...menu,
    children: userStore.userInfo?.menuList.filter((m: Menu) => {
      return m.pid === menu.id && m.url && m.url !== ''
    }) || []
  }))
})

const handleCommand = (command: string) => {
  if (command === 'logout') {
    userStore.clearUserInfo()
    ElMessage.success('退出成功')
    router.push('/')
  }
}

watch(() => route.path, (newPath: string) => {
  activeMenu.value = newPath
}, { immediate: true })

const loadMenus = async () => {
  if (userStore.userInfo?.userId) {
    try {
      const menus = await getMenusByRoleId(userStore.userInfo.userId)
      if (menus && menus.length > 0) {
        if (userStore.userInfo) {
          userStore.userInfo.menuList = menus
        }
      }
    } catch (error) {
      console.error('加载菜单失败:', error)
    }
  }
}

onMounted(() => {
  activeMenu.value = route.path
  loadMenus()
})
</script>

<style scoped>
.home-container {
  height: 100vh;
}

.header {
  background-color: #409EFF;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.header-left .logo {
  color: white;
  font-size: 18px;
  font-weight: bold;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-info {
  color: white;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
}

.sidebar {
  background-color: #fff;
  border-right: 1px solid #eee;
}

.sidebar-menu {
  height: 100%;
  border-right: none;
}

.sidebar-menu :deep(.el-menu-item.is-active) {
  background-color: #ecf5ff;
  color: #409EFF;
}

.sidebar-menu :deep(.el-menu-item:hover) {
  background-color: #f5f7fa;
}

.sidebar-menu :deep(.el-sub-menu__title:hover) {
  background-color: #f5f7fa;
}

.sidebar-menu :deep(.el-sub-menu.is-active .el-sub-menu__title) {
  background-color: #ecf5ff;
  color: #409EFF;
}

.main-content {
  background-color: #F5F7FA;
  padding: 20px;
}
</style>