<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <GameThirdMuchOptionForm ref="selectGameForm" @handler="getGameVal"></GameThirdMuchOptionForm>
            <ChannelThirdMuchOptionForm ref="selectChannelForm" @handler="getChannelVal"></ChannelThirdMuchOptionForm>
            <a-col :lg="8">
              <a-form-item label="日期">
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择起始日期" v-model:value="queryParam.startTime" />
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
    <BasicTable @register="registerTable" v-if="false">
    </BasicTable>
    <p style="text-align:center;fontSize:30px">{{title}}</p>
    <div v-for="(item, index) in countDataList" :key="index">
      <a-table :columns="columns" :pagination="false" :data-source="getDataList(index)" >
      </a-table>
    </div>
    <div>
      <a-table :columns="columns" :pagination="false" :data-source="countAllVo" bordered>
      </a-table>
    </div>
  </div>
</template>

<script lang="ts" name="count-ctUser" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import {  deleteOne, batchDelete, getImportUrl, getExportUrl } from '/@/views/statistics/userData/ctUser/CtUser.api';
  import { queryDayReportList } from '/@/views/statistics/financeData/RoiData/CtDaily.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import {BasicModal, useModalInner} from '/@/components/Modal';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import GameThirdMuchOptionForm from '/@/views/common/gameThirdMuchOptionForm.vue';
  import ChannelThirdMuchOptionForm from '/@/views/common/channelThirdMuchOptionForm.vue';
  import { Popconfirm } from 'ant-design-vue';
  import { useMethods } from '/@/hooks/system/useMethods';
  import { message } from 'ant-design-vue';
  import func from '../../../../../vue-temp/vue-editor-bridge';
  import LineMulti from '/@/components/chart/LineMulti.vue';
  import HeadInfo from '/@/components/chart/HeadInfo.vue';
  import DealOptionSelect from '/@/views/common/dealOptionSelect.vue';

  let date = new Date(Date.now() - 24 * 60 * 60 * 1000);
  let year = date.getFullYear();
  let month = date.getMonth() + 1;
  let day = date.getDate();
  // 格式化年月日信息，回填到时间控件上
  let dateString = year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
  const queryParam = ref<any>({startTime:dateString});
  let title = ref(year + '年' + (month < 10 ? '0' + month : month) + '月' + (day < 10 ? '0' + day : day) + '日数据报表')
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
  const toggleSearchStatus = ref<boolean>(false);

  const labelCol = reactive({
    xs: { span: 24 },
    sm: { span: 7 },
  });
  const wrapperCol = reactive({
    xs: { span: 24 },
    sm: { span: 16 },
  });

  let dataList = ref([])
  let countDataList = ref()
  let countAllVo = ref([])
  let resList = ref([])
  getList()
  /**
   * 查询列表
   */
  function getList() {
    queryDayReportList(queryParam.value).then((res: any)=>{
      dataList.value = res.dataList
      countDataList.value = res.countDataList
      if(dataList.value.length !== 0) {
        countAllVo.value = res.countAllVo
        countAllVo.value[0].roiDate = '大盘汇总'
        countAllVo.value[0].downloadRate = countAllVo.value[0].downloadRate + '%'
        countAllVo.value[0].regProbability = countAllVo.value[0].regProbability + '%'
        countAllVo.value[0].addCostProbability = countAllVo.value[0].addCostProbability + '%'
        countAllVo.value[0].firstROI = countAllVo.value[0].firstROI + '%'
        countAllVo.value[0].productionRatio = countAllVo.value[0].productionRatio + '%'
      }
    })
    let dateList = queryParam.value.startTime.split('-')
    title.value = dateList[0] + '年' + dateList[1] + '月' + dateList[2] + '日数据报表'
  }

  function getDataList(index) {
    resList.value = []
    dataList.value.forEach(element => {
      if(element.pkgId + '' === index) {
        if (!element.downloadRate.toString().includes('%')) {
          element.downloadRate = element.downloadRate + '%'
          element.regProbability = element.regProbability + '%'
          element.addCostProbability = element.addCostProbability + '%'
          element.firstROI = element.firstROI + '%'
          element.productionRatio = element.productionRatio + '%'
        }
        resList.value.push(element)
      }
    });
    if (resList.value.length > 1) {
      countDataList.value[index].roiDate = '合计汇总'
      if (!countDataList.value[index].downloadRate.toString().includes('%')) {
          countDataList.value[index].downloadRate = countDataList.value[index].downloadRate + '%'
          countDataList.value[index].regProbability = countDataList.value[index].regProbability + '%'
          countDataList.value[index].addCostProbability = countDataList.value[index].addCostProbability + '%'
          countDataList.value[index].firstROI = countDataList.value[index].firstROI + '%'
          countDataList.value[index].productionRatio = countDataList.value[index].productionRatio + '%'
        }
      resList.value.push(countDataList.value[index])
    }
    return resList.value
  }

  const columns = ref([{
    title: '日期',
    align: "center",
    dataIndex: 'roiDate',
    slots: { customRender: 'roiDate' },
    customRender: ({ text, index }) => {
          const obj = {
            children: text,
            props: {} as any,
          };
          // if (index === resList.value.length - 1) {
          //   obj.props.colSpan = 3;
          // }
          if (text === '大盘汇总' || text === '合计汇总') {
            obj.props.colSpan = 3;
          }
          return obj;
        },
    },
    {
    title: '产品',
    align: "center",
    dataIndex: 'gameName',
    customRender: ({ text, index }) => {
          const obj = {
            children: text,
            props: {} as any,
          };
          if (index === 0 && resList.value.length !== 1) {
            obj.props.rowSpan = resList.value.length - 1;
          }
          if (index > 0 && index < resList.value.length - 1) {
            obj.props.rowSpan = 0;
          }
          // if (index === resList.value.length - 1) {
          //   obj.props.colSpan = 0;
          // }
          if (text === null) {
            obj.props.colSpan = 0;
          }
          return obj;
        },
    },
    {
    title: '渠道',
    align: "center",
    dataIndex: 'channelName',
    customRender: ({ text, index }) => {
          const obj = {
            children: text,
            props: {} as any,
          };
          if (text === null) {
            obj.props.colSpan = 0;
          }
          return obj;
        },
    },
    {
    title: '曝光',
    align: "center",
    dataIndex: 'exhibition',
    },
    {
    title: '下载数',
    align: "center",
    dataIndex: 'download',
    },
    {
    title: '下载率',
    align: "center",
    dataIndex: 'downloadRate',
    },
    {
    title: '下载单价',
    align: "center",
    dataIndex: 'downloadUnitPrice',
    },
    {
    title: '注册数',
    align: "center",
    dataIndex: 'countUser',
    },
    {
    title: '注册率',
    align: "center",
    dataIndex: 'regProbability',
    },
    {
    title: '注册单价',
    align: "center",
    dataIndex: 'regUnitPrice',
    },
    {
    title: '付费单价',
    align: "center",
    dataIndex: 'costUnitPrice',
    },
    {
    title: '消耗',
    align: "center",
    dataIndex: 'costMoney',
    },
    {
    title: '现金消耗',
    align: "center",
    dataIndex: 'realCostMoney',
    },
    {
    title: '新增付费人数',
    align: "center",
    dataIndex: 'firstUser',
    },
    {
    title: '新增付费率',
    align: "center",
    dataIndex: 'addCostProbability',
    },
    {
    title: '新增付费金额',
    align: "center",
    dataIndex: 'firstMoney',
    },
    {
    title: '新增ARPU',
    align: "center",
    dataIndex: 'firstArpu',
    },
    {
    title: '活跃付费金额',
    align: "center",
    dataIndex: 'aliveMoney',
    },
    {
    title: '首日ROI',
    align: "center",
    dataIndex: 'firstROI',
    },
    {
    title: '投产比',
    align: "center",
    dataIndex: 'productionRatio',
    },])

  /**
   * 查询
   */
  function searchQuery() {
    countAllVo.value = []
    getList()
  }

  
  /**
   * 重置
   */
  function searchReset() {
    countAllVo.value = []
    queryParam.value = {startTime:dateString};
    selectGameForm.value.reload();
    selectChannelForm.value.reload();
    selectDealForm.value.reload();
    //刷新数据
    getList()
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
  .button-container {
      display: flex;
      justify-content: center;
      gap: 20px;
      margin-bottom: 20px;
    }

</style>
