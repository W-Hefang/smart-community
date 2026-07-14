<template>
  <div class="announcement-list-container">
    <el-card class="search-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="关键词">
          <el-input v-model="searchForm.keyword" placeholder="请输入标题或内容关键词" clearable />
        </el-form-item>

        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="全部" clearable>
            <el-option label="全部" value="" />
            <el-option label="草稿" value="draft" />
            <el-option label="已发布" value="published" />
            <el-option label="已归档" value="archived" />
          </el-select>
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
          <span>公告列表</span>
          <div class="header-buttons">
            <el-button type="danger" @click="handleBatchDelete" :disabled="selectedIds.length === 0">
              批量删除
            </el-button>
            <el-button type="primary" @click="handleAdd">
              新增公告
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
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" min-width="200" />
        <el-table-column prop="announcementType" label="类型" width="100">
          <template #default="{ row }">
            <el-tag :type="getTypeTagType(row.announcementType)">
              {{ row.announcementType }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="priority" label="优先级" width="100">
          <template #default="{ row }">
            <el-tag :type="getPriorityTagType(row.priority)">
              {{ getPriorityLabel(row.priority) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="publisherName" label="发布人" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusTagType(row.status)">
              {{ getStatusLabel(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="viewCount" label="浏览次数" width="100" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="220" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleView(row)">查看</el-button>
            <el-button type="success" size="small" v-if="row.status === 'draft'" @click="handlePublish(row.id)">发布</el-button>
            <el-button type="warning" size="small" v-if="row.status === 'published'" @click="handleArchive(row.id)">归档</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row.id)">删除</el-button>
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

    <el-dialog
      v-model="viewDialogVisible"
      title="公告详情"
      width="600px"
    >
      <el-form :model="viewFormData" label-width="100px">
        <el-form-item label="标题">
          <span>{{ viewFormData.title }}</span>
        </el-form-item>
        <el-form-item label="类型">
          <el-tag :type="getTypeTagType(viewFormData.announcementType)">
            {{ viewFormData.announcementType }}
          </el-tag>
        </el-form-item>
        <el-form-item label="优先级">
          <el-tag :type="getPriorityTagType(viewFormData.priority)">
            {{ getPriorityLabel(viewFormData.priority) }}
          </el-tag>
        </el-form-item>
        <el-form-item label="发布人">
          <span>{{ viewFormData.publisherName }}</span>
        </el-form-item>
        <el-form-item label="状态">
          <el-tag :type="getStatusTagType(viewFormData.status)">
            {{ getStatusLabel(viewFormData.status) }}
          </el-tag>
        </el-form-item>
        <el-form-item label="发布时间">
          <span>{{ viewFormData.publishTime || '-' }}</span>
        </el-form-item>
        <el-form-item label="截止时间">
          <span>{{ viewFormData.endTime || '-' }}</span>
        </el-form-item>
        <el-form-item label="浏览次数">
          <span>{{ viewFormData.viewCount }}</span>
        </el-form-item>
        <el-form-item label="正文内容">
          <div class="content-box">{{ viewFormData.content }}</div>
        </el-form-item>
        <el-form-item label="备注">
          <span>{{ viewFormData.remark || '-' }}</span>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="viewDialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>

    <el-dialog
      v-model="dialogVisible"
      :title="dialogType === 'add' ? '新增公告' : '编辑公告'"
      width="600px"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
      >
        <el-form-item label="公告标题" prop="title">
          <el-input v-model="formData.title" placeholder="请输入公告标题" />
        </el-form-item>

        <el-form-item label="公告类型" prop="announcementType">
          <el-select v-model="formData.announcementType" placeholder="请选择公告类型" style="width: 100%">
            <el-option label="通知" value="通知" />
            <el-option label="活动" value="活动" />
            <el-option label="维保" value="维保" />
          </el-select>
        </el-form-item>

        <el-form-item label="优先级" prop="priority">
          <el-select v-model="formData.priority" placeholder="请选择优先级" style="width: 100%">
            <el-option label="低" value="low" />
            <el-option label="中" value="medium" />
            <el-option label="高" value="high" />
          </el-select>
        </el-form-item>

        <el-form-item label="发布人ID" prop="publisherId">
          <el-input v-model.number="formData.publisherId" placeholder="请输入发布人ID" type="number" />
        </el-form-item>

        <el-form-item label="发布人姓名" prop="publisherName">
          <el-input v-model="formData.publisherName" placeholder="请输入发布人姓名" />
        </el-form-item>

        <el-form-item label="截止时间">
          <el-date-picker
            v-model="formData.endTime"
            type="datetime"
            placeholder="选择截止时间"
            format="YYYY-MM-DD HH:mm:ss"
            value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-form-item>

        <el-form-item label="附件链接">
          <el-input v-model="formData.attachmentUrl" placeholder="请输入附件链接" />
        </el-form-item>

        <el-form-item label="正文内容" prop="content">
          <el-input
            v-model="formData.content"
            type="textarea"
            :rows="4"
            placeholder="请输入公告内容"
          />
        </el-form-item>

        <el-form-item label="备注">
          <el-input
            v-model="formData.remark"
            type="textarea"
            :rows="2"
            placeholder="请输入备注信息"
          />
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
import { addAnnouncement, updateAnnouncement, deleteById, getById, publish, archive, pageSearch } from '../../api/announcement'
import type { AnnouncementVO } from '../../types'
import type { FormInstance, FormRules } from 'element-plus'

const loading = ref(false)
const tableData = ref<AnnouncementVO[]>([])
const total = ref(0)
const selectedIds = ref<number[]>([])
const dialogVisible = ref(false)
const viewDialogVisible = ref(false)
const dialogType = ref<'add' | 'edit'>('add')
const formRef = ref<FormInstance>()

const searchForm = reactive({
  page: 1,
  size: 10,
  keyword: '',
  status: ''
})

const formData = reactive({
  id: undefined as number | undefined,
  title: '',
  content: '',
  announcementType: '通知',
  priority: 'medium',
  publisherId: 1,
  publisherName: '',
  endTime: '',
  attachmentUrl: '',
  remark: ''
})

const viewFormData = reactive<AnnouncementVO>({
  id: 0,
  title: '',
  content: '',
  announcementType: '',
  priority: '',
  publisherId: 0,
  publisherName: '',
  status: '',
  publishTime: null,
  endTime: null,
  viewCount: 0,
  attachmentUrl: null,
  remark: null,
  createTime: '',
  updateTime: ''
})

const formRules = reactive<FormRules>({
  title: [
    { required: true, message: '请输入公告标题', trigger: 'blur' }
  ],
  announcementType: [
    { required: true, message: '请选择公告类型', trigger: 'change' }
  ],
  priority: [
    { required: true, message: '请选择优先级', trigger: 'change' }
  ],
  publisherId: [
    { required: true, message: '请输入发布人ID', trigger: 'blur' }
  ],
  publisherName: [
    { required: true, message: '请输入发布人姓名', trigger: 'blur' }
  ],
  content: [
    { required: true, message: '请输入公告内容', trigger: 'blur' }
  ]
})

const getTypeTagType = (type: string) => {
  const typeMap: Record<string, any> = {
    '通知': 'info',
    '活动': 'success',
    '维保': 'warning'
  }
  return typeMap[type] || 'info'
}

const getPriorityTagType = (priority: string) => {
  const priorityMap: Record<string, any> = {
    'low': 'info',
    'medium': 'warning',
    'high': 'danger'
  }
  return priorityMap[priority] || 'info'
}

const getPriorityLabel = (priority: string) => {
  const priorityMap: Record<string, string> = {
    'low': '低',
    'medium': '中',
    'high': '高'
  }
  return priorityMap[priority] || priority
}

const getStatusTagType = (status: string) => {
  const statusMap: Record<string, any> = {
    'draft': 'info',
    'published': 'success',
    'archived': 'warning'
  }
  return statusMap[status] || 'info'
}

const getStatusLabel = (status: string) => {
  const statusMap: Record<string, string> = {
    'draft': '草稿',
    'published': '已发布',
    'archived': '已归档'
  }
  return statusMap[status] || status
}

const fetchData = async () => {
  loading.value = true
  try {
    const result = await pageSearch(searchForm.page, searchForm.size, searchForm.keyword, searchForm.status)
    
    if (result && result.records && Array.isArray(result.records)) {
      tableData.value = result.records
      total.value = result.total || 0
    } else {
      tableData.value = []
      total.value = 0
    }
  } catch (error) {
    console.error('获取公告列表失败:', error)
    ElMessage.error('获取公告列表失败')
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
  searchForm.keyword = ''
  searchForm.status = ''
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

const handleSelectionChange = (selection: AnnouncementVO[]) => {
  selectedIds.value = selection.map(item => item.id)
}

const handleAdd = () => {
  dialogType.value = 'add'
  Object.assign(formData, {
    id: undefined,
    title: '',
    content: '',
    announcementType: '通知',
    priority: 'medium',
    publisherId: 1,
    publisherName: '',
    endTime: '',
    attachmentUrl: '',
    remark: ''
  })
  dialogVisible.value = true
}

const handleView = async (row: AnnouncementVO) => {
  try {
    const result = await getById(row.id)
    Object.assign(viewFormData, result)
    viewDialogVisible.value = true
  } catch (error) {
    console.error('获取公告详情失败:', error)
    ElMessage.error('获取公告详情失败')
  }
}

const handlePublish = async (id: number) => {
  try {
    await ElMessageBox.confirm('确定要发布此公告吗？', '提示', {
      type: 'warning'
    })
    await publish(id)
    ElMessage.success('发布成功')
    fetchData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('发布失败:', error)
      ElMessage.error('发布失败')
    }
  }
}

const handleArchive = async (id: number) => {
  try {
    await ElMessageBox.confirm('确定要归档此公告吗？', '提示', {
      type: 'warning'
    })
    await archive(id)
    ElMessage.success('归档成功')
    fetchData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('归档失败:', error)
      ElMessage.error('归档失败')
    }
  }
}

const handleDelete = async (id: number) => {
  try {
    await ElMessageBox.confirm('确定要删除该公告吗？', '提示', {
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
    await ElMessageBox.confirm(`确定要删除选中的 ${selectedIds.value.length} 个公告吗？`, '提示', {
      type: 'warning'
    })
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
          await addAnnouncement({
            title: formData.title,
            content: formData.content,
            announcementType: formData.announcementType,
            priority: formData.priority,
            publisherId: formData.publisherId,
            publisherName: formData.publisherName,
            endTime: formData.endTime || undefined,
            attachmentUrl: formData.attachmentUrl || undefined,
            remark: formData.remark || undefined
          })
          ElMessage.success('添加成功')
        } else {
          await updateAnnouncement({
            id: formData.id,
            title: formData.title,
            content: formData.content,
            announcementType: formData.announcementType,
            priority: formData.priority,
            publisherId: formData.publisherId,
            publisherName: formData.publisherName,
            status: 'draft',
            endTime: formData.endTime || undefined,
            attachmentUrl: formData.attachmentUrl || undefined,
            remark: formData.remark || undefined
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
.announcement-list-container {
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

.content-box {
  padding: 10px;
  background-color: #f5f5f5;
  border-radius: 4px;
  min-height: 100px;
  white-space: pre-wrap;
}
</style>