<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <DealOptionSelect ref="selectDealForm" @handler="getDealVal"></DealOptionSelect>
          <GameThirdMuchOptionForm ref="selectGameForm" @handler="getGameVal"></GameThirdMuchOptionForm>
          <ChannelThirdMuchOptionForm ref="selectChannelForm" @handler="getChannelVal"></ChannelThirdMuchOptionForm>
          <a-col :lg="8">
            <a-form-item label="日期">
              <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择开始日期" v-model:value="queryParam.startTime" />
              <span>至</span>
              <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择结束日期" v-model:value="queryParam.endTime" />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
              <a-form-item label="常用日期">
                <a href="#" @click="handleClick(1)">今天</a> <a href="#" @click="handleClick(2)">昨天</a> <a href="#" @click="handleClick(3)">最近三天</a> <a href="#" @click="handleClick(4)">最近一周</a> <a href="#" @click="handleClick(5)">最近两周</a>
              </a-form-item>
            </a-col>
          <a-col :lg="8">
            <a-form-item label="归类方式">
              <j-search-select placeholder="请选择归类方式" v-model:value="queryParam.type"  dict="stat_custom_type"  />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-col :lg="6">
                <a-button type="primary" preIcon="ant-design:search-outlined" @click="searchQuery">查询</a-button>
                <a-button type="primary" preIcon="ant-design:reload-outlined" @click="searchReset" style="margin-left: 8px">重置</a-button>
                <!-- <a @click="toggleSearchStatus = !toggleSearchStatus" style="margin-left: 8px">
                  {{ toggleSearchStatus ? '收起' : '展开' }}
                  <Icon :icon="toggleSearchStatus ? 'ant-design:up-outlined' : 'ant-design:down-outlined'" />
                </a> -->
              </a-col>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!--引用表格-->
    <BasicTable @register="registerTable" >
      <!--插槽:table标题-->
      <template #tableTitle>
      </template>
      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" />
      </template>
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
  </div>
</template>

<script lang="ts" name="count-ctRole" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './StatCustom.data';
  import GameThirdMuchOptionForm from '/@/views/common/gameThirdMuchOptionForm.vue';
  import { queryStatCustomList } from '/@/views/statistics/financeData/RoiData/CtDaily.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import { Popconfirm } from 'ant-design-vue';
  import { useMethods } from '/@/hooks/system/useMethods';
  import { blockade } from '/@/views/statistics/userData/ctUser/CtUser.api';
  import { message } from 'ant-design-vue';
  import {BasicModal, useModalInner} from '/@/components/Modal';
  import ChannelThirdMuchOptionForm from '/@/views/common/channelThirdMuchOptionForm.vue';
  import DealOptionSelect from '/@/views/common/dealOptionSelect.vue';

  let date = new Date();
  let year = date.getFullYear();
  let month = date.getMonth() + 1;
  let day = date.getDate();
  // 格式化年月日信息，回填到时间控件上
  let dateString = year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
  const queryParam = ref<any>({type: 0, startTime: dateString, endTime: dateString});
  // const toggleSearchStatus = ref<boolean>(false);
  const selectGameForm= ref();
  const selectDealForm= ref();
  const selectChannelForm= ref();
  let getGameVal = (e: any) => {
    queryParam.value.gameId = e.gameId;
    queryParam.value.subGameId = e.subGameId;
    queryParam.value.pkgId = e.pkgId;
  };
  let getDealVal = (e: any) => {
    queryParam.value.dealId = e.dealId;
  };
  let getChannelVal = (e: any) => {
    queryParam.value.channelTypeId = e.channelTypeId;
    queryParam.value.channelId = e.channelId;
    queryParam.value.channelSubAccountId = e.channelSubAccountId;
  };
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'retention',
      api: queryStatCustomList,
      columns,
      canResize:false,
      useSearchForm: false,
      showActionColumn:false,
      pagination: false,
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        return Object.assign(params, queryParam.value);
      },
      afterFetch: (res) => {
        res.forEach(element => {
          element.addCostRate = element.addCostRate + '%'
          element.aliveMoneyRate = element.aliveMoneyRate + '%'
          element.day2 = element.day2 + '%'
          element.day3 = element.day3 + '%'
          element.day7 = element.day7 + '%'
          element.day15 = element.day15 + '%'
          element.day30 = element.day30 + '%'
          element.day45 = element.day45 + '%'
          element.day60 = element.day60 + '%'
        });
        return res
      }
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

  function handleClick(type) {
    let date = new Date();
    //今天
    if(type === 1) {
      // 创建Date对象，获取今天的年、月、日等信息
      date = new Date();
      let year = date.getFullYear();
      let month = date.getMonth() + 1;
      let day = date.getDate();
      // 格式化年月日信息，回填到时间控件上
      let dateString = year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
      queryParam.value.startTime = dateString;
      queryParam.value.endTime = dateString;
    }else if(type === 2) {
      //昨天
      date = new Date(Date.now() - 24 * 60 * 60 * 1000);
      let year = date.getFullYear();
      let month = date.getMonth() + 1;
      let day = date.getDate();
      // 格式化年月日信息，回填到时间控件上
      let dateString = year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
      queryParam.value.startTime = dateString;
      queryParam.value.endTime = dateString;
    }else if(type === 3) {
      //最近三天
      date = new Date(Date.now() - 24 * 60 * 60 * 1000);
      let date2 = new Date(Date.now() - 24 * 60 * 60 * 1000 * 3);
      let year = date.getFullYear();
      let month = date.getMonth() + 1;
      let day = date.getDate();
      let year2 = date2.getFullYear();
      let month2 = date2.getMonth() + 1;
      let day2 = date2.getDate();
      // 格式化年月日信息，回填到时间控件上
      let dateString = year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
      let dateString2 = year2 + '-' + (month2 < 10 ? '0' + month2 : month2) + '-' + (day2 < 10 ? '0' + day2 : day2);
      queryParam.value.startTime = dateString2;
      queryParam.value.endTime = dateString;
    }else if(type === 4) {
      //最近一周
      date = new Date(Date.now() - 24 * 60 * 60 * 1000);
      let date2 = new Date(Date.now() - 24 * 60 * 60 * 1000 * 7);
      let year = date.getFullYear();
      let month = date.getMonth() + 1;
      let day = date.getDate();
      let year2 = date2.getFullYear();
      let month2 = date2.getMonth() + 1;
      let day2 = date2.getDate();
      // 格式化年月日信息，回填到时间控件上
      let dateString = year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
      let dateString2 = year2 + '-' + (month2 < 10 ? '0' + month2 : month2) + '-' + (day2 < 10 ? '0' + day2 : day2);
      queryParam.value.startTime = dateString2;
      queryParam.value.endTime = dateString;
    }else if(type === 5) {
      //最近两周
      date = new Date(Date.now() - 24 * 60 * 60 * 1000);
      let date2 = new Date(Date.now() - 24 * 60 * 60 * 1000 * 14);
      let year = date.getFullYear();
      let month = date.getMonth() + 1;
      let day = date.getDate();
      let year2 = date2.getFullYear();
      let month2 = date2.getMonth() + 1;
      let day2 = date2.getDate();
      // 格式化年月日信息，回填到时间控件上
      let dateString = year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
      let dateString2 = year2 + '-' + (month2 < 10 ? '0' + month2 : month2) + '-' + (day2 < 10 ? '0' + day2 : day2);
      queryParam.value.startTime = dateString2;
      queryParam.value.endTime = dateString;
    }
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
    ];
  }

  /**
   * 下拉操作栏
   */
  function getDropDownAction(record) {
    return [
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
    queryParam.value = {type: 0, startTime: dateString, endTime: dateString};
    //刷新数据
    reload();
    selectGameForm.value.reload();
    selectChannelForm.value.reload();
    selectDealForm.value.reload();
  }




</script>

<style lang="less" scoped>
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
