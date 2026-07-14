<!-- src/views/user/UserList.vue -->
<template>
  <div class="user-list-container">
    <el-card class="search-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="手机号">
          <el-input v-model="searchForm.condition" placeholder="请输入手机号" clearable />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="table-card">
      <template #header>
        <div class="card-header">
          <span>用户列表</span>
          <div class="header-buttons">
            <el-button type="danger" @click="handleBatchDelete" :disabled="selectedIds.length === 0">
              批量删除
            </el-button>
            <el-button type="primary" @click="handleAdd">
              新增用户
            </el-button>
          </div>
        </div>
      </template>

      <el-table
        :data="tableData"
        style="width: 100%"
        @selection-change="handleSelectionChange"
        v-loading="loading"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="userId" label="ID" width="80" />
        <el-table-column prop="userName" label="用户名" width="120" />
        <el-table-column prop="userPhone" label="手机号" width="130" />
        <el-table-column prop="userType" label="用户类型" width="120">
          <template #default="{ row }">
            <el-tag :type="getTypeTagType(row.userType)">
              {{ row.userType }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="userStatus" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.userStatus === 1 ? 'success' : 'danger'">
              {{ row.userStatus === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row.userId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="searchForm.page"
        v-model:page-size="searchForm.size"
        :page-sizes="[10, 20, 50, 100]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        class="pagination"
      />
    </el-card>

    <!-- 新增/编辑弹窗 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogType === 'add' ? '新增用户' : '编辑用户'"
      width="500px"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="80px"
      >
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="formData.userName" placeholder="请输入用户名" />
        </el-form-item>

        <el-form-item label="手机号" prop="userPhone">
          <el-input v-model="formData.userPhone" placeholder="请输入手机号" />
        </el-form-item>

        <el-form-item label="密码" prop="userPassword" v-if="dialogType === 'add'">
          <el-input v-model="formData.userPassword" type="password" placeholder="请输入密码" show-password />
        </el-form-item>

        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="formData.idCard" placeholder="请输入身份证号" />
        </el-form-item>

        <el-form-item label="用户类型" prop="userType">
          <el-select v-model="formData.userType" placeholder="请选择用户类型" style="width: 100%">
            <el-option label="住户" value="住户" />
            <el-option label="物业工作人员" value="物业工作人员" />
            <el-option label="系统管理员" value="系统管理员" />
          </el-select>
        </el-form-item>

        <el-form-item label="状态" prop="userStatus">
          <el-radio-group v-model="formData.userStatus">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { pageLikePhone, addUser, deleteById, updateById } from '../../api/user'
import type { User } from '../../types'
import type { FormInstance, FormRules } from 'element-plus'

const loading = ref(false)
const tableData = ref<User[]>([])
const total = ref(0)
const selectedIds = ref<number[]>([])
const dialogVisible = ref(false)
const dialogType = ref<'add' | 'edit'>('add')
const formRef = ref<FormInstance>()

const searchForm = reactive({
  page: 1,
  size: 10,
  condition: ''
})

const formData = reactive({
  userId: undefined as number | undefined,
  userName: '',
  userPhone: '',
  userPassword: '',
  idCard: '',
  userType: '住户',
  userStatus: 1
})

const formRules = reactive<FormRules>({
  userName: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  userPhone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  userPassword: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ],
  userType: [
    { required: true, message: '请选择用户类型', trigger: 'change' }
  ]
})

const getTypeTagType = (type: string) => {
  const typeMap: Record<string, any> = {
    '住户': 'info',
    '物业工作人员': 'warning',
    '系统管理员': 'danger'
  }
  return typeMap[type] || 'info'
}

const fetchData = async () => {
  loading.value = true
  try {
    const result = await pageLikePhone(searchForm.page, searchForm.size, searchForm.condition)
    console.log('=== 调试信息 ===')
    console.log('result:', result)
    
    // 根据实际返回结构赋值
    if (result && result.data && Array.isArray(result.data)) {
      tableData.value = result.data
      total.value = result.total || 0
    } else if (result && Array.isArray(result)) {
      tableData.value = result
      total.value = result.length
    } else {
      tableData.value = []
      total.value = 0
    }
    
    console.log('tableData:', tableData.value)
    console.log('total:', total.value)
    console.log('tableData.value 长度:', tableData.value.length)  // 添加这行
    console.log('tableData.value[0]:', tableData.value[0]) 
  } catch (error) {
    console.error('获取用户列表失败:', error)
    ElMessage.error('获取用户列表失败')
    tableData.value = []
    total.value = 0
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  searchForm.page = 1
  fetchData()
}

const handleReset = () => {
  searchForm.condition = ''
  searchForm.page = 1
  fetchData()
}

const handleSizeChange = (size: number) => {
  searchForm.size = size
  searchForm.page = 1
  fetchData()
}

const handleCurrentChange = (page: number) => {
  searchForm.page = page
  fetchData()
}

const handleSelectionChange = (selection: User[]) => {
  selectedIds.value = selection.map(item => item.userId!)
}

const handleAdd = () => {
  dialogType.value = 'add'
  Object.assign(formData, {
    userId: undefined,
    userName: '',
    userPhone: '',
    userPassword: '',
    idCard: '',
    userType: '住户',
    userStatus: 1
  })
  dialogVisible.value = true
}

const handleEdit = (row: User) => {
  dialogType.value = 'edit'
  Object.assign(formData, {
    userId: row.userId,
    userName: row.userName,
    userPhone: row.userPhone,
    userPassword: '',
    idCard: row.idCard || '',
    userType: row.userType,
    userStatus: row.userStatus
  })
  dialogVisible.value = true
}

const handleDelete = async (id: number) => {
  try {
    await ElMessageBox.confirm('确定要删除该用户吗？', '提示', {
      type: 'warning'
    })
    await deleteById(id)
    ElMessage.success('删除成功')
    fetchData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  }
}

const handleBatchDelete = async () => {
  try {
    await ElMessageBox.confirm(`确定要删除选中的 ${selectedIds.value.length} 个用户吗？`, '提示', {
      type: 'warning'
    })
    // 批量删除需要逐个调用或后端提供批量接口，这里先逐个删除
    for (const id of selectedIds.value) {
      await deleteById(id)
    }
    ElMessage.success('批量删除成功')
    fetchData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('批量删除失败:', error)
      ElMessage.error('批量删除失败')
    }
  }
}

const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (dialogType.value === 'add') {
          await addUser({
            userName: formData.userName,
            userPhone: formData.userPhone,
            userPassword: formData.userPassword,
            idCard: formData.idCard,
            userType: formData.userType,
            userStatus: formData.userStatus
          })
          ElMessage.success('添加成功')
        } else {
          await updateById({
            userId: formData.userId,
            userName: formData.userName,
            userPhone: formData.userPhone,
            idCard: formData.idCard,
            userType: formData.userType,
            userStatus: formData.userStatus
          })
          ElMessage.success('编辑成功')
        }
        dialogVisible.value = false
        fetchData()
      } catch (error) {
        console.error('操作失败:', error)
        ElMessage.error(dialogType.value === 'add' ? '添加失败' : '编辑失败')
      }
    }
  })
}

onMounted(() => {
  fetchData()
})
</script>

<style scoped>
.user-list-container {
  height: 100%;
}

.search-card {
  margin-bottom: 20px;
}

.table-card {
  height: calc(100% - 100px);
  display: flex;
  flex-direction: column;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 16px;
  font-weight: bold;
}

.header-buttons {
  display: flex;
  gap: 10px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>