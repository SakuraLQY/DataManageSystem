<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <GameThirdMuchOptionForm ref="selectGameForm" @handler="getGameVal"></GameThirdMuchOptionForm>
          <template v-if="toggleSearchStatus">
            <ChannelThirdMuchOptionForm ref="selectChannelForm" @handler="getChannelVal"></ChannelThirdMuchOptionForm>
            <a-col :lg="8">
              <a-form-item label="广告">
                <j-select-multiple placeholder="请选择广告" v-model:value="queryParam.dealId"  dictCode="open_gateway.op_deal,deal_name,id" allowClear />
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="注册日期">
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择开始时间" v-model:value="queryParam.regStartTime" allowClear/>至
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择结束时间" v-model:value="queryParam.regEndTime" allowClear/>
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
    <div>
          <a-row>
            <a-col :offset="18" :span="6">
              <a-radio-group v-model:value="barType" @change="changeBar">
                <a-radio-button value="normal">常规数据</a-radio-button>
                <a-radio-button value="firstMoneyRate">新增充值比例</a-radio-button>
                <a-radio-button value="aliveMoneyRate">活跃充值比例</a-radio-button>
              </a-radio-group>
            </a-col>
          </a-row>
          <div v-if="normalBar">
            <LineMulti  :chartData="lineMultiData" height="50vh" type="line" :option="barOption"></LineMulti>
          </div>
          <div v-if="firstMoneyRateBar">
            <pie :chartData="firstMoneyRateData" height="50vh" :option="firstMoneyRateOption" />
          </div>
          <div v-if="aliveMoneyRateBar">
            <pie :chartData="aliveMoneyRateData" height="50vh" :option="aliveMoneyRateOption" />
          </div>
        </div>
    <!--引用表格-->
    <BasicTable @register="registerTable">
      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)"/>
      </template>
      <!--字段回显插槽-->
      <template #htmlSlot="{text}">
        <div v-html="text"></div>
      </template>
      <template #name="{record}">
        {{record.name}}
        <span v-if="groupByDealFlag && record.name">
          <profile-two-tone @click="dealInfoData(record)"/>
        </span>
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

<script lang="ts" name="count-detail" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './Detail.data';
  import { list,firstMoneyRate,aliveMoneyRate} from './Detail.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import GameThirdMuchOptionForm from '/@/views/common/gameThirdMuchOptionForm.vue';
  import ChannelThirdMuchOptionForm from '/@/views/common/channelThirdMuchOptionForm.vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import JSelectMultiple from '/@/components/Form/src/jeecg/components/JSelectMultiple.vue';
  import {formatToDate} from  '/@/utils/dateUtil'
  import {ProfileTwoTone} from '@ant-design/icons-vue'
  import LineMulti from '/@/components/chart/LineMulti.vue';
  import Pie from '/@/components/chart/Pie.vue';
  const selectGameForm= ref();
  const selectChannelForm= ref();
  const lineMultiData = ref([]);
  const firstMoneyRateData = ref([]);
  const aliveMoneyRateData = ref([]);
  const queryParam = ref<any>({
    regStartTime: formatToDate(new Date(new Date().getTime() - (6 * 24 * 60 * 60 * 1000))),
    regEndTime: formatToDate(new Date())
  });
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  const groupByDealFlag = ref<boolean>(false); // 根据广告查询flag 用于小图标展示
  const ctDealInfoDataModal = ref();
  const barOption = ref({
    title: { text: '', left: 'center'},
     yAxis: {
      type: 'value',
      name: '人数（人）/ 付费（元）',
      nameLocation: 'middle',
      nameGap: 45
    },
    xAxis:{
      inverse: true //反向坐标
    },
    legend: { top: 'bottom' }
  })
  const firstMoneyRateOption = ref({ 
    title: { text: '新增充值面额占比图', left: 'center' } 
  })
  const aliveMoneyRateOption = ref({ 
    title: { text: '活跃充值面额占比图', left: 'center' } 
  })
  const barType = ref("");
  const normalBar = ref<boolean>(true);
  const firstMoneyRateBar = ref<boolean>(false);
  const aliveMoneyRateBar = ref<boolean>(false);
  
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: '',
      api: list,
      columns,
      canResize:false,
      useSearchForm: false,
      pagination: false,
      showActionColumn: false,
      clickToRowSelect:true,
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        return Object.assign(params, queryParam.value);
      },afterFetch:(res) =>{
        // 设置首次付费和活跃付费
        firstMoneyRateData.value = [];
        aliveMoneyRateData.value = [];
        firstMoneyRate(queryParam.value).then(res=>{
          if(res && res.length >0){
            res.forEach((item) => {
              firstMoneyRateData.value.push({name:`金额:${item.money}元，数量:${item.count}`, value:item.rate});
            })
          }
        });
        aliveMoneyRate(queryParam.value).then(res=>{
         
          if(res && res.length >0){
            res.forEach((item) => {
              aliveMoneyRateData.value.push({name:`金额:${item.money}元，数量:${item.count}`, value:item.rate});
            })
          }
        });
        lineMultiData.value = [];
        if(!res || res.length == 1){
          barOption.value.title.text = "无数据";
        }else{
          barOption.value.title.text = res[1]?.gameName + "数据";
        }
        res.forEach((item) => {
          if(item.day != "合计"){
            lineMultiData.value.push({ name: item.day, type: '注册', value: item.countUser });
            lineMultiData.value.push({ name: item.day, type: '活跃', value: item.aliveUser });
            lineMultiData.value.push({ name: item.day, type: '新增付费', value: item.firstMoney });
            lineMultiData.value.push({ name: item.day, type: '活跃付费', value: item.aliveMoney });
          }
        });

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
    if(queryParam.value.groupType == 8) {
      groupByDealFlag.value = true;
    }else{
      groupByDealFlag.value = false;
    }
    reload();
  }
  
  /**
   * 重置
   */
  function searchReset() {
    queryParam.value = {};
    // selectedRowKeys.value = [];
    if(queryParam.value.groupType == 8) {
      groupByDealFlag.value = true;
    }else{
      groupByDealFlag.value = false;
    }
    selectGameForm.value.reload();
    selectChannelForm.value.reload();
    //刷新数据
    reload();
  }

   /**
   * 打开新列表
   */
  function dealInfoData(record){
    record.regStartTime = queryParam.value.regStartTime
    record.regEndTime = queryParam.value.regEndTime
    ctDealInfoDataModal.value.edit(record);
  }

  /**
   * 更改展示的图表
   */
  function changeBar(type){
    if(type.target.value == "normal"){
      normalBar.value = true;
      firstMoneyRateBar.value = false;
      aliveMoneyRateBar.value = false;
    }else if(type.target.value == "firstMoneyRate"){
      normalBar.value = false;
      firstMoneyRateBar.value = true;
      aliveMoneyRateBar.value = false;
    }else{
      normalBar.value = false;
      firstMoneyRateBar.value = false;
      aliveMoneyRateBar.value = true;
    }
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
