<!-- src/views/community/CommunityList.vue -->
<template>
  <div class="community-list-container">
    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stats-row">
      <el-col :span="12">
        <el-card class="stats-card" @click="showStatisticsDialog = true">
          <div class="stats-content">
            <el-icon :size="40" color="#409EFF"><DataAnalysis /></el-icon>
            <div class="stats-info">
              <div class="stats-number">{{ totalCount }}</div>
              <div class="stats-label">社区总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="stats-card" @click="showScaleDialog = true">
          <div class="stats-content">
            <el-icon :size="40" color="#67C23A"><PieChart /></el-icon>
            <div class="stats-info">
              <div class="stats-number">{{ statisticsList.length }}</div>
              <div class="stats-label">规模分组</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 搜索卡片 -->
    <el-card class="search-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="社区名称">
          <el-input v-model="searchForm.condition" placeholder="请输入社区名称" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
          <el-button type="success" @click="showScaleRangeDialog = true">按规模查询</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 表格卡片 -->
    <el-card class="table-card">
      <template #header>
        <div class="card-header">
          <span>社区列表</span>
          <div class="header-buttons">
            <el-button 
              type="danger" 
              @click="showBatchDeleteDialog = true" 
              :disabled="selectedIds.length === 0"
            >
              批量删除 ({{ selectedIds.length }})
            </el-button>
            <el-button type="primary" @click="handleAdd">新增社区</el-button>
          </div>
        </div>
      </template>

      <el-table
        :data="tableData"
        style="width: 100%"
        v-loading="loading"
        stripe
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="communityId" label="ID" width="80" />
        <el-table-column prop="communityName" label="社区名称" width="150" />
        <el-table-column prop="address" label="地址" min-width="200" show-overflow-tooltip />
        <el-table-column prop="scale" label="规模(户)" width="120" />
        <el-table-column prop="leader" label="负责人" width="120" />
        <el-table-column prop="phone" label="联系电话" width="130" />
        <el-table-column prop="description" label="描述" min-width="150" show-overflow-tooltip />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row.communityId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="searchForm.page"
        v-model:page-size="searchForm.size"
        :page-sizes="[10, 20, 50, 100]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="fetchData"
        @current-change="fetchData"
        class="pagination"
      />
    </el-card>

    <!-- 新增/编辑弹窗 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogType === 'add' ? '新增社区' : '编辑社区'"
      width="600px"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
      >
        <el-form-item label="社区名称" prop="communityName">
          <el-input v-model="formData.communityName" placeholder="请输入社区名称" />
        </el-form-item>

        <el-form-item label="地址" prop="address">
          <el-input v-model="formData.address" placeholder="请输入地址" />
        </el-form-item>

        <el-form-item label="规模(户)" prop="scale">
          <el-input-number v-model="formData.scale" :min="1" :max="100000" style="width: 100%" />
        </el-form-item>

        <el-form-item label="负责人" prop="leader">
          <el-input v-model="formData.leader" placeholder="请输入负责人" />
        </el-form-item>

        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="formData.phone" placeholder="请输入联系电话" />
        </el-form-item>

        <el-form-item label="描述" prop="description">
          <el-input v-model="formData.description" type="textarea" :rows="3" placeholder="请输入描述" />
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

    <!-- 统计弹窗 -->
    <el-dialog v-model="showStatisticsDialog" title="社区规模统计" width="500px">
      <el-table :data="statisticsList" stripe>
        <el-table-column prop="scaleGroup" label="规模分组" />
        <el-table-column prop="count" label="社区数量" width="120">
          <template #default="{ row }">
            <el-tag type="primary">{{ row.count }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="avgScale" label="平均规模(户)" width="140">
          <template #default="{ row }">
            {{ Math.round(row.avgScale) }}
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <!-- 规模分组弹窗 -->
    <el-dialog v-model="showScaleDialog" title="按规模分组筛选" width="400px">
      <el-form label-width="100px">
        <el-form-item label="规模分组">
          <el-select v-model="selectedScaleGroup" placeholder="请选择规模分组" style="width: 100%">
            <el-option
              v-for="item in statisticsList"
              :key="item.scaleGroup"
              :label="`${item.scaleGroup} (${item.count}个社区)`"
              :value="item.scaleGroup"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showScaleDialog = false">取消</el-button>
        <el-button type="primary" @click="filterByScaleGroup">确定</el-button>
      </template>
    </el-dialog>

    <!-- 规模范围查询弹窗 -->
    <el-dialog v-model="showScaleRangeDialog" title="按规模范围查询" width="400px">
      <el-form label-width="100px">
        <el-form-item label="最小规模">
          <el-input-number v-model="scaleRange.min" :min="0" placeholder="最小规模" style="width: 100%" />
        </el-form-item>
        <el-form-item label="最大规模">
          <el-input-number v-model="scaleRange.max" :min="0" placeholder="最大规模" style="width: 100%" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showScaleRangeDialog = false">取消</el-button>
        <el-button type="primary" @click="handleScaleRangeSearch">确定查询</el-button>
      </template>
    </el-dialog>

    <!-- 批量删除确认弹窗 -->
    <el-dialog v-model="showBatchDeleteDialog" title="批量删除社区" width="400px">
      <el-alert
        title="警告"
        type="warning"
        :description="`确定要删除选中的 ${selectedIds.length} 个社区吗？此操作不可恢复！`"
        show-icon
        :closable="false"
      />
      <template #footer>
        <el-button @click="showBatchDeleteDialog = false">取消</el-button>
        <el-button type="danger" @click="handleBatchDelete">确定删除</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { DataAnalysis, PieChart } from '@element-plus/icons-vue'
import { pageLikeName, addCommunity, deleteById, updateById, getStatistics, getTotalCount, getByScaleRange } from '../../api/community'
import type { CommunityVO, ScaleStatisticsVO } from '../../types'
import type { FormInstance, FormRules } from 'element-plus'

const loading = ref(false)
const tableData = ref<CommunityVO[]>([])
const total = ref(0)
const dialogVisible = ref(false)
const dialogType = ref<'add' | 'edit'>('add')
const formRef = ref<FormInstance>()
const showStatisticsDialog = ref(false)
const showScaleDialog = ref(false)
const showScaleRangeDialog = ref(false)
const showBatchDeleteDialog = ref(false)
const statisticsList = ref<ScaleStatisticsVO[]>([])
const totalCount = ref(0)
const selectedScaleGroup = ref('')
const selectedIds = ref<number[]>([])

const searchForm = reactive({
  page: 1,
  size: 10,
  condition: ''
})

const scaleRange = reactive({
  min: 0,
  max: 0
})

const formData = reactive({
  communityId: undefined as number | undefined,
  communityName: '',
  address: '',
  scale: 100,
  leader: '',
  phone: '',
  description: ''
})

const formRules = reactive<FormRules>({
  communityName: [
    { required: true, message: '请输入社区名称', trigger: 'blur' }
  ],
  address: [
    { required: true, message: '请输入地址', trigger: 'blur' }
  ],
  scale: [
    { required: true, message: '请输入规模', trigger: 'blur' }
  ],
  leader: [
    { required: true, message: '请输入负责人', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入联系电话', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ]
})

const fetchData = async () => {
  loading.value = true
  try {
    const result = await pageLikeName(searchForm.page, searchForm.size, searchForm.condition)
    console.log('获取社区列表:', result)
    if (result && result.data) {
      tableData.value = result.data
      total.value = result.total
    } else {
      tableData.value = []
      total.value = 0
    }
  } catch (error) {
    console.error('获取社区列表失败:', error)
    ElMessage.error('获取社区列表失败，请检查后端是否运行')
    tableData.value = []
    total.value = 0
  } finally {
    loading.value = false
  }
}

const loadStatistics = async () => {
  try {
    const [stats, total] = await Promise.all([
      getStatistics(),
      getTotalCount()
    ])
    statisticsList.value = stats || []
    totalCount.value = total?.totalCount || 0
  } catch (error) {
    console.error('加载统计数据失败:', error)
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

const handleScaleRangeSearch = async () => {
  if (!scaleRange.min && !scaleRange.max) {
    ElMessage.warning('请至少输入一个规模范围')
    return
  }
  
  const min = scaleRange.min || undefined
  const max = scaleRange.max || undefined
  
  loading.value = true
  try {
    const result = await getByScaleRange(min, max)
    tableData.value = result
    total.value = result.length
    ElMessage.success(`查询到 ${result.length} 个社区`)
    showScaleRangeDialog.value = false
  } catch (error) {
    console.error('按规模查询失败:', error)
    ElMessage.error('查询失败')
  } finally {
    loading.value = false
  }
}

const filterByScaleGroup = () => {
  if (!selectedScaleGroup.value) {
    ElMessage.warning('请选择规模分组')
    return
  }
  
  const group = statisticsList.value.find(g => g.scaleGroup === selectedScaleGroup.value)
  if (!group) return
  
  let minScale, maxScale
  if (group.scaleGroup === '小型社区(<500户)') {
    minScale = 0
    maxScale = 499
  } else if (group.scaleGroup === '中型社区(500-1500户)') {
    minScale = 500
    maxScale = 1500
  } else {
    minScale = 1500
    maxScale = undefined
  }
  
  loading.value = true
  getByScaleRange(minScale, maxScale).then(result => {
    tableData.value = result
    total.value = result.length
    ElMessage.success(`查询到 ${result.length} 个社区`)
    showScaleDialog.value = false
  }).catch(error => {
    console.error('查询失败:', error)
    ElMessage.error('查询失败')
  }).finally(() => {
    loading.value = false
  })
}

const handleAdd = () => {
  dialogType.value = 'add'
  Object.assign(formData, {
    communityId: undefined,
    communityName: '',
    address: '',
    scale: 100,
    leader: '',
    phone: '',
    description: ''
  })
  dialogVisible.value = true
}

const handleEdit = (row: CommunityVO) => {
  dialogType.value = 'edit'
  Object.assign(formData, {
    communityId: row.communityId,
    communityName: row.communityName,
    address: row.address,
    scale: row.scale,
    leader: row.leader,
    phone: row.phone,
    description: row.description
  })
  dialogVisible.value = true
}

const handleDelete = async (id: number) => {
  try {
    await ElMessageBox.confirm('确定要删除该社区吗？', '提示', {
      type: 'warning'
    })
    await deleteById(id)
    ElMessage.success('删除成功')
    fetchData()
    loadStatistics()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  }
}

// 表格选择变化
const handleSelectionChange = (selection: CommunityVO[]) => {
  selectedIds.value = selection.map(item => item.communityId)
}

// 批量删除
const handleBatchDelete = async () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning('请至少选择一个社区')
    return
  }
  
  try {
    // 使用 Promise.all 并发删除
    await Promise.all(selectedIds.value.map(id => deleteById(id)))
    ElMessage.success(`成功删除 ${selectedIds.value.length} 个社区`)
    showBatchDeleteDialog.value = false
    selectedIds.value = []
    fetchData()
    loadStatistics()
  } catch (error) {
    console.error('批量删除失败:', error)
    ElMessage.error('批量删除失败，请重试')
  }
}

const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (dialogType.value === 'add') {
          await addCommunity({
            communityName: formData.communityName,
            address: formData.address,
            scale: formData.scale,
            leader: formData.leader,
            phone: formData.phone,
            description: formData.description
          })
          ElMessage.success('添加成功')
        } else {
          await updateById({
            communityId: formData.communityId,
            communityName: formData.communityName,
            address: formData.address,
            scale: formData.scale,
            leader: formData.leader,
            phone: formData.phone,
            description: formData.description
          })
          ElMessage.success('编辑成功')
        }
        dialogVisible.value = false
        fetchData()
        loadStatistics()
      } catch (error) {
        console.error('操作失败:', error)
        ElMessage.error(dialogType.value === 'add' ? '添加失败' : '编辑失败')
      }
    }
  })
}

onMounted(() => {
  fetchData()
  loadStatistics()
})
</script>

<style scoped>
.community-list-container {
  height: 100%;
}

.stats-row {
  margin-bottom: 20px;
}

.stats-card {
  cursor: pointer;
  transition: all 0.3s;
}

.stats-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.stats-content {
  display: flex;
  align-items: center;
  gap: 20px;
}

.stats-info {
  flex: 1;
}

.stats-number {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
}

.stats-label {
  font-size: 14px;
  color: #909399;
  margin-top: 5px;
}

.search-card {
  margin-bottom: 20px;
}

.table-card {
  height: calc(100% - 180px);
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