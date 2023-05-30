<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <GameThirdMuchOptionForm ref="selectGameForm" @handler="getGameVal"></GameThirdMuchOptionForm>
          <ChannelThirdMuchOptionForm ref="selectChannelForm" @handler="getChannelVal"></ChannelThirdMuchOptionForm>
          <a-col :lg="8">
            <a-form-item label="广告列表">
              <j-select-multiple dictCode="open_gateway.op_deal,deal_name,id" v-model:value="queryParam.dealId" allowClear />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="注册日期">
              <a-range-picker v-model:value="userTime" />
            </a-form-item>
          </a-col>
          <a-col :lg="7">
            <span style="float: right; overflow: hidden" class="table-page-search-submitButtons">
              <a-col :lg="6">
                <a-button type="primary" preIcon="ant-design:search-outlined" @click="getUserAccumulateLevelData">查询</a-button>
                <a-button type="primary" preIcon="ant-design:reload-outlined" @click="searchReset" style="margin-left: 8px">重置</a-button>
              </a-col>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <a-row :gutter="24">
      <!-- 用户首充等级分布数据 -->
      <a-col :lg="12">
        <a-row><div style="display: inline-block; margin: 0 auto">用户首充等级分布数据</div></a-row>
        <pie height="300px" weight="300px" :chart-data="levelGraphical" />
        <a-table :columns="levelColumns" :dataSource="levelDataSource" :pagination="false" bordered />
      </a-col>
      <!-- 用户首充时长分布数据 -->
      <a-col :lg="12">
        <a-row><div style="display: inline-block; margin: 0 auto">用户首充时长分布数据</div></a-row>
        <pie height="300px" weight="300px" :chart-data="timeGraphical" />
        <a-table :columns="timeColumns" :dataSource="timeDataSource" :pagination="false" bordered />
      </a-col>
    </a-row>

    <!-- 表单区域 -->
    <UserAccumulateLevelModal ref="registerModal" @success="handleSuccess"></UserAccumulateLevelModal>
  </div>
</template>

<script lang="ts" name="UserAccumulateLevel-userAccumulateLevel" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './UserAccumulateLevel.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl, getUserAccumulateLevel } from './UserAccumulateLevel.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import UserAccumulateLevelModal from './components/UserAccumulateLevelModal.vue';
  import GameThirdMuchOptionForm from '/@/views/common/gameThirdMuchOptionForm.vue';
  import ChannelThirdMuchOptionForm from '/@/views/common/channelThirdMuchOptionForm.vue';
  import Pie from '/@/components/chart/Pie.vue';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import JSelectMultiple from '/@/components/Form/src/jeecg/components/JSelectMultiple.vue';

  const queryParam = ref<any>({});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  const selectGameForm = ref();
  const selectChannelForm = ref();
  const userTime = ref<Moment[]>([]);

  let getGameVal = (e: any) => {
    queryParam.value.gameId = e.gameId;
    queryParam.value.subGameId = e.subGameId;
    queryParam.value.pkgId = e.pkgId;
  };
  let getChannelVal = (e: any) => {
    queryParam.value.channelTypeId = e.channelTypeId;
    queryParam.value.channelId = e.channelId;
    queryParam.value.channelSubAccountId = e.channelSubAccountId;
  };

  getUserAccumulateLevelData();
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'user_accumulate_level',
      api: list,
      columns,
      canResize: false,
      useSearchForm: false,
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        return Object.assign(params, queryParam.value);
      },
    },
    exportConfig: {
      name: 'user_accumulate_level',
      url: getExportUrl,
    },
    importConfig: {
      url: getImportUrl,
      success: handleSuccess,
    },
  });
  const [registerTable, { reload, collapseAll, updateTableDataRecord, findTableDataRecord, getDataSource }, { rowSelection, selectedRowKeys }] =
    tableContext;
  const labelCol = reactive({
    xs: { span: 24 },
    sm: { span: 7 },
  });
  const wrapperCol = reactive({
    xs: { span: 24 },
    sm: { span: 16 },
  });

  //用户首充等级分布数据
  const levelDataSource = ref();
  const levelGraphical = ref();
  const levelColumns = [
    {
      title: '游戏等级',
      dataIndex: 'level',
      width: 100,
    },
    {
      title: '人数',
      dataIndex: 'num',
      width: 100,
    },
    {
      title: '占比',
      dataIndex: 'ratio',
      width: 100,
    },
  ];

  //用户首充时长分布数据
  const timeDataSource = ref();
  const timeGraphical = ref();
  const timeColumns = [
    {
      title: '游戏时长',
      dataIndex: 'time',
    },
    {
      title: '人数',
      dataIndex: 'num',
    },
    {
      title: '占比',
      dataIndex: 'ratio',
    },
  ];

  function getUserAccumulateLevelData() {
    if (userTime.value != null && userTime.value.length > 0) {
      queryParam.value.userStartTime = userTime.value[0].format('YYYY-MM-DD') + ' 00:00:00';
      queryParam.value.userEndTime = userTime.value[1].format('YYYY-MM-DD') + ' 23:59:59';
    } else {
      queryParam.value.userStartTime = null;
      queryParam.value.userEndTime = null;
    }

    getUserAccumulateLevel(queryParam.value).then((res: any) => {
      levelDataSource.value = res.levelMaps;
      timeDataSource.value = res.timeMaps;
      levelGraphical.value = res.ratioLevelMaps;
      timeGraphical.value = res.ratioTimeMaps;
    });
  }
  /**
   * 新增事件
   */
  function handleAdd() {
    registerModal.value.disableSubmit = false;
    registerModal.value.add();
  }

  /**
   * 编辑事件
   */
  function handleEdit(record: Recordable) {
    registerModal.value.disableSubmit = false;
    registerModal.value.edit(record);
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
      },
      {
        label: '删除',
        popConfirm: {
          title: '是否确认删除',
          confirm: handleDelete.bind(null, record),
        },
      },
    ];
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
    userTime.value = [];
    //刷新数据
    selectGameForm.value.reload();
    selectChannelForm.value.reload();
    getUserAccumulateLevelData();
  }
</script>

<style lang="less" scoped>
  .jeecg-basic-table-form-container {
    .table-page-search-submitButtons {
      display: block;
      margin-bottom: 24px;
      white-space: nowrap;
    }
    .query-group-cust {
      width: calc(50% - 15px);
      min-width: 100px !important;
    }
    .query-group-split-cust {
      width: 30px;
      display: inline-block;
      text-align: center;
    }
  }
</style>
