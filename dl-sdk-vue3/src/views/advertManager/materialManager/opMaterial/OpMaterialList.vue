<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <GameOptionForm ref="selectForm" @handler="getGameVal"></GameOptionForm>
          <a-col :lg="8">
            <a-form-item label="素材类型">
              <j-search-select placeholder="请选择素材类型" v-model:value="queryParam.type1" dict="material_manager_type" allowClear/>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :lg="8">
              <a-form-item label="素材归类">
                <j-search-select v-if="queryParam.type1 === '' || queryParam.type1 === undefined " placeholder="请先选择素材类型" v-model:value="undefined" />
                <j-search-select v-if="queryParam.type1 === '1'" placeholder="请选择素材归类" v-model:value="queryParam.type2" dict="material_img_type" allowClear/>
                <j-search-select v-if="queryParam.type1 === '2'" placeholder="请选择素材归类" v-model:value="queryParam.type2" dict="material_video_type" allowClear/>
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="创建用户">
                <j-search-select placeholder="请选择创建用户" v-model:value="queryParam.createBy" dict="sys_user,realname,username" allowClear/>
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="创建时间">
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择开始时间" v-model:value="queryParam.startTime" allowClear/>至
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择结束时间" v-model:value="queryParam.endTime" allowClear/>
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-col :lg="6">
                <a-button type="primary" preIcon="ant-design:search-outlined" @click="searchQuery">查询</a-button>
                <a-button type="primary" preIcon="ant-design:reload-outlined" @click="searchReset" style="margin-left: 8px">重置</a-button>
                <a @click="toggleSearchStatus = !toggleSearchStatus" style="margin-left: 8px">
                  {{ toggleSearchStatus ? '收起' : '展开' }}
                  <Icon :icon="toggleSearchStatus ? 'ant-design:up-outlined' : 'ant-design:down-outlined'" />
                </a>
              </a-col>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection">
      <template #materialContent="{ record }">
        <Tag color="green">
          <img v-if="record.type1 === 1" :src="getFileAccessHttpUrl(record.showUrl)" min-width="70" height="70" />
          <video v-if="record.type1 === 2" :src="getFileAccessHttpUrl(record.showUrl)" controls = "true" 
          preload="auto" 
          webkit-playsinline="true"
          playsinline="true" 
          x-webkit-airplay="allow" 
          x5-video-player-type="h5"
          x5-video-player-fullscreen="true" 
          x5-video-orientation="portraint"
          style="object-fit:fill"
            min-width="70" height="70" />
        </Tag>
      </template>
      <template #subGameId_dictText="{ record }">
        <Tag color="green" v-if="record.subGameId !== 0">
          {{ record.subGameId_dictText}}
        </Tag>
        <Tag color="green" v-if="record.subGameId === 0">
          {{'全部'}}
        </Tag>
      </template>
      <template #type2="{ record }">
        <Tag color="green" v-if="record.type1 === 1 ">
          {{ record.type2_dictText}}
        </Tag>
        <Tag color="green" v-if="record.type2 === 201">
          {{'横版视频'}}
        </Tag>
        <Tag color="green" v-if="record.type2 === 202">
          {{'竖版视频'}}
        </Tag>
      </template>
      <template #jrttCreateAccountId="{ record }">
        <Tag color="green" v-if="record.jrttCreateAccountId !== null  && record.jrttMaterialId !== null">
          {{ record.jrttCreateAccountId_dictText}}
        </Tag>
        <Tag color="green" v-if="record.jrttCreateAccountId !== null  && record.jrttMaterialId === null && record.jrttStatus === 1">
          {{ record.jrttCreateAccountId_dictText}}
        </Tag>
        <Tag color="green" v-if="record.jrttCreateAccountId !== null && record.jrttMaterialId === null && record.jrttStatus !== 1">
          {{ record.jrttCreateAccountId_dictText}}
          <a-button  size="small" preIcon="ant-design:plus" type="primary" @click="setAccount(record.id,1)">修改</a-button>
        </Tag>
        <Tag color="green" v-if="record.jrttCreateAccountId === null">
          <h1 v-if="record.jrttStatus === 1" style="color:red">正在上传</h1>
          <h1 v-if="record.jrttStatus === 2" style="color:red">上传失败</h1>
          <a-button v-if="record.jrttStatus === 3 || record.jrttStatus === 2" size="small" preIcon="ant-design:plus" type="primary" @click="setAccount(record.id,1)">设置账号</a-button>
        </Tag>
      </template>
      <template #jrttMaterialId="{ record }">
        <Tag color="green" v-if="record.jrttMaterialId !== null">
          <a-tooltip>
            <template #title>{{ record.jrttMaterialId }}</template>
            <a-button type="primary" shape="round">查看</a-button>
          </a-tooltip>
        </Tag>
        <Tag color="green" v-if="record.jrttMaterialId === null || record.jrttMaterialId === ''">
          <h1 v-if="record.jrttStatus === 1" style="color:red">正在上传</h1>
          <h1 v-if="record.jrttStatus === 2" style="color:red">上传失败</h1>
          <a-button v-if="record.jrttStatus === 3 || record.jrttStatus === 2" size="small" preIcon="ant-design:export-outlined" type="error" @click="pushMaterial(record.id,1)">补推</a-button>
        </Tag>
      </template>
      <template #jrttFileId="{ record }">
        <Tag color="green" v-if="record.jrttFileId !== null">
          <a-tooltip>
            <template #title>{{ record.jrttFileId }}</template>
            <a-button type="primary" shape="round">查看</a-button>
          </a-tooltip>
        </Tag>
      </template>

      <template #gdtCreateAccountId="{ record }">
        <Tag color="green" v-if="record.gdtCreateAccountId !== null  && record.gdtMaterialId !== null">
          {{ record.gdtCreateAccountId_dictText}}
        </Tag>
        <Tag color="green" v-if="record.gdtCreateAccountId !== null  && record.gdtMaterialId === null && record.gdtStatus === 1">
          {{ record.gdtCreateAccountId_dictText}}
        </Tag>
        <Tag color="green" v-if="record.gdtCreateAccountId !== null && record.gdtMaterialId === null && record.gdtStatus !== 1">
          {{ record.gdtCreateAccountId_dictText}}
          <a-button  size="small" preIcon="ant-design:plus" type="primary" @click="setAccount(record.id,2)">修改</a-button>
        </Tag>
        <Tag color="green" v-if="record.gdtCreateAccountId === null">
          <h1 v-if="record.gdtStatus === 1" style="color:red">正在上传</h1>
          <h1 v-if="record.gdtStatus === 2" style="color:red">上传失败</h1>
          <a-button v-if="record.gdtStatus === 3 || record.gdtStatus === 2" size="small" preIcon="ant-design:plus" type="primary" @click="setAccount(record.id,2)">设置账号</a-button>
        </Tag>
      </template>
      <template #gdtMaterialId="{ record }">
        <Tag color="green" v-if="record.gdtMaterialId !== null">
          <a-tooltip>
            <template #title>{{ record.gdtMaterialId }}</template>
            <a-button type="primary" shape="round">查看</a-button>
          </a-tooltip>
        </Tag>
        <Tag color="green" v-if="record.gdtMaterialId === null || record.gdtMaterialId === ''">
          <h1 v-if="record.gdtStatus === 1" style="color:red">正在上传</h1>
          <h1 v-if="record.gdtStatus === 2" style="color:red">上传失败</h1>
          <a-button v-if="record.gdtStatus === 3 || record.gdtStatus === 2" size="small" preIcon="ant-design:export-outlined" type="error" @click="pushMaterial(record.id,2)">补推</a-button>
        </Tag>
      </template>

      <template #kuaishouCreateAccountId="{ record }">
        <Tag color="green" v-if="record.kuaishouCreateAccountId !== null  && record.kuaishouMaterialId !== null">
          {{ record.kuaishouCreateAccountId_dictText}}
        </Tag>
        <Tag color="green" v-if="record.kuaishouCreateAccountId !== null  && record.kuaishouMaterialId === null && record.ksStatus === 1">
          {{ record.kuaishouCreateAccountId_dictText}}
        </Tag>
        <Tag color="green" v-if="record.kuaishouCreateAccountId !== null && record.kuaishouMaterialId === null && record.ksStatus !== 1">
          {{ record.kuaishouCreateAccountId_dictText}}
          <a-button  size="small" preIcon="ant-design:plus" type="primary" @click="setAccount(record.id,3)">修改</a-button>
        </Tag>
        <Tag color="green" v-if="record.kuaishouCreateAccountId === null">
          <h1 v-if="record.ksStatus === 1" style="color:red">正在上传</h1>
          <h1 v-if="record.ksStatus === 2" style="color:red">上传失败</h1>
          <a-button v-if="record.ksStatus === 3 || record.ksStatus === 2" size="small" preIcon="ant-design:plus" type="primary" @click="setAccount(record.id,3)">设置账号</a-button>
        </Tag>
      </template>
      <template #kuaishouMaterialId="{ record }">
        <Tag color="green" v-if="record.kuaishouMaterialId !== null">
          <a-tooltip>
            <template #title>{{ record.kuaishouMaterialId }}</template>
            <a-button type="primary" shape="round">查看</a-button>
          </a-tooltip>
        </Tag>
        <Tag color="green" v-if="record.kuaishouMaterialId === null || record.kuaishouMaterialId === ''">
          <h1 v-if="record.ksStatus === 1" style="color:red">正在上传</h1>
          <h1 v-if="record.ksStatus === 2" style="color:red">上传失败</h1>
          <a-button v-if="record.ksStatus === 3 || record.ksStatus === 2" size="small" preIcon="ant-design:export-outlined" type="error" @click="pushMaterial(record.id,3)">补推</a-button>
        </Tag>
      </template>
      <template #kuaishouFileId="{ record }">
        <Tag color="green" v-if="record.kuaishouFileId !== null">
          <a-tooltip>
            <template #title>{{ record.kuaishouFileId }}</template>
            <a-button type="primary" shape="round">查看</a-button>
          </a-tooltip>
        </Tag>
      </template>

      <!--插槽:table标题-->
      <template #tableTitle>
        <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>
        <a-button  type="primary" preIcon="ant-design:export-outlined" @click="onExportXls" v-show="false"> 导出</a-button>
        <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls" v-show="false">导入</j-upload-button>
        <!-- <a-dropdown v-if="selectedRowKeys.length > 0" v-show="false">
          <template #overlay>
            <a-menu>
              <a-menu-item key="1" @click="batchHandleDelete">
                <Icon icon="ant-design:delete-outlined"></Icon>
                删除
              </a-menu-item>
            </a-menu>
          </template>
          <a-button>批量操作
            <Icon icon="mdi:chevron-down"></Icon>
          </a-button>
        </a-dropdown> -->
      </template>
      <!--操作栏-->
      <!-- <template #action="{ record }" v-show="false">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)"/>
      </template> -->
      <!--字段回显插槽-->
      <template #htmlSlot="{text}">
        <div v-html="text"></div>
      </template>
      <!--省市区字段回显插槽-->
      <template #pcaSlot="{text}">
        {{ getAreaTextByCode(text) }}
      </template>
      <template #fileSlot="{text}">
        <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
        <a-button v-else :ghost="true" type="primary" preIcon="ant-design:download-outlined" size="small" @click="downloadFile(text)">下载</a-button>
      </template>
    </BasicTable>
    <!-- 表单区域 -->
    <OpMaterialModal ref="registerModal" @success="handleSuccess"></OpMaterialModal>
    <div>
      <a-modal :title="title" :width="width" :visible="visible" @ok="handleOk"  @cancel="handleCancel" cancelText="关闭">
        <a-spin :spinning="confirmLoading">
            <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol2" :wrapperCol="wrapperCol2" >
              <a-row>
                <a-col :span="15">
                  <a-form-item :label="label" >
                    <j-search-select v-if="showForm===1" placeholder="请选择头条账号" v-model:value="accountId" dict="open_gateway.op_put_account where channel_Id = '4' or channel_Id = '5' and state = '1' and level_id = '2' ,nick_name,id"/>
                    <j-search-select v-if="showForm===3" placeholder="请选择快手账号" v-model:value="accountId" dict="open_gateway.op_put_account where channel_Id = '9' and state = '1' and level_id = '2' ,nick_name,id"/>
                  </a-form-item>
                </a-col>
              </a-row>
            </a-form>
        </a-spin>  
        </a-modal>
    </div>
  </div>
</template>

<script lang="ts" name="advert-opMaterial" setup>
  import { ref, reactive, watch, defineExpose, onMounted, onUnmounted } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './OpMaterial.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl } from './OpMaterial.api';
  import { downloadFile } from '/@/utils/common/renderUtils';					
  import OpMaterialModal from './components/OpMaterialModal.vue'
  import GameOptionForm from '/@/views/common/gameOptionForm.vue'
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import { getAccountList, setAccountWay, pushMaterialWay } from './OpMaterial.api';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { getFileAccessHttpUrl } from '/@/utils/common/compUtils';
  import { message } from 'ant-design-vue';

  const queryParam = ref<any>({});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  let typeList = ref([]);
  const selectForm= ref();
  const confirmLoading = ref<boolean>(false);
  const formRef = ref();
  const title = ref<string>('');
  const label = ref<string>('');
  const { createMessage } = useMessage();
  let showForm = ref(1);
  let formId = ref();
  let formType = ref();
  let accountId = ref();
  const width = ref<number>(800);
  const visible = ref<boolean>(false);
  const labelCol2 = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol2 = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  let getGameVal = (e:any) => {
    queryParam.value.gameId = e.gameId;
    queryParam.value.subGameId = e.subGameId;
  }
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'op_material',
      api: list,
      columns,
      canResize:false,
      useSearchForm: false,
      showActionColumn: false,
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        return Object.assign(params, queryParam.value);
      },
    },
    exportConfig: {
      name: "op_material",
      url: getExportUrl,
    },
	  importConfig: {
	    url: getImportUrl,
	    success: handleSuccess
	  },
  });
  const [registerTable, { reload, collapseAll, updateTableDataRecord, findTableDataRecord, getDataSource }, { rowSelection, selectedRowKeys }] = tableContext;
  const labelCol = reactive({
    xs: { span: 24 },
    sm: { span: 7 },
  });
  const wrapperCol = reactive({
    xs: { span: 24 },
    sm: { span: 16 },
  });

  /**
   * 提交表单
   */
  function handleOk() {
    if(accountId.value === '' || accountId.value === null || accountId.value === undefined) {
      message.warning('账号不能为空');
      return false;
    }
    confirmLoading.value = true;
    setAccountWay({id:formId.value,type:formType.value,accountId:accountId.value}).then((res: any)=>{
      visible.value = false;
    })
    .finally(() => {
        confirmLoading.value = false;
        accountId.value = undefined;
        reload();
      });
  }

  function pushMaterial(id,type) {
    pushMaterialWay({id:id,type:type}).then((res: any)=>{
      reload();
    })
  }

  /**
   * 新增事件
   */
  function handleAdd() {
    registerModal.value.disableSubmit = false;
    registerModal.value.add();
  }

  /**
   * 取消按钮回调事件
   */
  function handleCancel() {
    visible.value = false;
    accountId.value = undefined;
  }
  
  /**
   * 编辑事件
   */
  function handleEdit(record: Recordable) {
    registerModal.value.disableSubmit = false;
    registerModal.value.edit(record);
  }

  function setAccount(id,type) {
    formId.value = id;
    formType.value = type;
    if(type === 1) {
      showForm.value = 1;
      title.value = '设置id为《'+id+'》的素材的头条账号';
      label.value = '头条账号';
    }else if(type === 2) {
      showForm.value = 2;
      title.value = '设置id为《'+id+'》的素材的广点通账号';
      label.value = '广点通账号';
    }else {
      showForm.value = 3;
      title.value = '设置id为《'+id+'》的素材的快手账号';
      label.value = '快手账号';
    }
    visible.value = true;
  }
   
  /**
   * 详情
   */
  function handleDetail(record: Recordable) {
    registerModal.value.disableSubmit = true;
    registerModal.value.edit(record);
  }
   
  /**
   * 删除事件
   */
  async function handleDelete(record) {
    await deleteOne({ id: record.id }, handleSuccess);
  }
   
  /**
   * 批量删除事件
   */
  async function batchHandleDelete() {
    await batchDelete({ ids: selectedRowKeys.value }, handleSuccess);
  }
   
  /**
   * 成功回调
   */
  function handleSuccess() {
    (selectedRowKeys.value = []) && reload();
  }
   
  /**
   * 操作栏
   */
  function getTableAction(record) {
    return [
      {
        label: '编辑',
        onClick: handleEdit.bind(null, record),
      },
    ];
  }
   
  /**
   * 下拉操作栏
   */
  function getDropDownAction(record) {
    return [
      {
        label: '详情',
        onClick: handleDetail.bind(null, record),
      }, {
        label: '删除',
        popConfirm: {
          title: '是否确认删除',
          confirm: handleDelete.bind(null, record),
        }
      }
    ]
  }

  /**
   * 查询
   */
  function searchQuery() {
    reload();
  }
  
  /**
   * 重置
   */
  function searchReset() {
    queryParam.value = {};
    selectedRowKeys.value = [];
    //刷新数据
    reload();
  }
  
  // 获取静态资源
  function getStatic(item) {
    if (item) {
      return getFileAccessHttpUrl(item)
    }
    return '';
  }

</script>

<style lang="less" scoped>
.antd-modal-form {
    min-height: 500px !important;
    overflow-y: auto;
    padding: 24px 24px 24px 24px;
  }
  .jeecg-basic-table-form-container {
    .table-page-search-submitButtons {
      display: block;
      margin-bottom: 24px;
      white-space: nowrap;
    }
    .query-group-cust{
      width: calc(50% - 15px);
      min-width: 100px !important;
    }
    .query-group-split-cust{
      width: 30px;
      display: inline-block;
      text-align: center
    }
  }
</style>
