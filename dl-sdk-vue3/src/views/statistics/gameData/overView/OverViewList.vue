<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <DealOptionSelect ref="selectDealForm" @handler="getDealVal"></DealOptionSelect>
          <GameThirdMuchOptionForm ref="selectGameForm" @handler="getGameVal"></GameThirdMuchOptionForm>
          <template v-if="toggleSearchStatus">
            <ChannelThirdMuchOptionForm ref="selectChannelForm" @handler="getChannelVal"></ChannelThirdMuchOptionForm>
            <a-col :lg="8">
              <a-form-item label="日期">
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择起始日期" v-model:value="queryParam.startTime" />
                <span>至</span>
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择结束日期" v-model:value="queryParam.endTime" />
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="常用日期">
                <a href="#" @click="handleClick(1)">今天</a> <a href="#" @click="handleClick(2)">昨天</a> <a href="#" @click="handleClick(3)">最近三天</a> <a href="#" @click="handleClick(4)">最近一周</a> <a href="#" @click="handleClick(5)">最近两周</a>
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
        <a-col :span="24">
          <a-card :loading="loading" :bordered="false" :title="title"  :style="{ marginTop: '24px' }">
            <a-row>
              <a-col :span="6">
                <HeadInfo title="老用户充值金额" :content="overViewInfo.oldUserPay" icon="environment"></HeadInfo>
              </a-col>
              <a-col :span="6">
                <HeadInfo title="新增充值金额" :content="overViewInfo.addCostPrice" icon="team"></HeadInfo>
              </a-col>
              <a-col :span="6">
                <HeadInfo title="总充值金额" :content="overViewInfo.activeCostPrice" icon="rise"></HeadInfo>
              </a-col>
            </a-row>
            <LineMulti :chartData="lineMultiData" height="50vh" type="line" :option="option"></LineMulti>
          </a-card>
        </a-col>
      </a-row>
    </div>
    <div>
      <a-row>
        <a-col :span="24">
          <a-card :loading="loading" :bordered="false" :title="title2" :style="{ marginTop: '24px' }">
            <a-row>
              <a-col :span="6">
                <HeadInfo title="付费率" :content="overViewInfo.activeProbability" icon="environment"></HeadInfo>
              </a-col>
              <a-col :span="6">
                <HeadInfo title="新角色付费率" :content="overViewInfo.addCostProbability" icon="team"></HeadInfo>
              </a-col>
              <a-col :span="6">
                <HeadInfo title="老角色付费率" :content="overViewInfo.oldUserPayProbability" icon="rise"></HeadInfo>
              </a-col>
            </a-row>
            <LineMulti :chartData="lineMultiData2" height="50vh" type="line" :option="option2"></LineMulti>
          </a-card>
        </a-col>
      </a-row>
    </div>
    <div>
      <a-row>
        <a-col :span="24">
          <a-card :loading="loading" :bordered="false" :title="title3" :style="{ marginTop: '24px' }">
            <a-row>
              <a-col :span="6">
                <HeadInfo title="ARPPU" :content="overViewInfo.arppu" icon="environment"></HeadInfo>
              </a-col>
              <a-col :span="6">
                <HeadInfo title="新角色ARPPU" :content="overViewInfo.addArppu" icon="team"></HeadInfo>
              </a-col>
              <a-col :span="6">
                <HeadInfo title="老角色APRRU" :content="overViewInfo.oldUserPayArppu" icon="rise"></HeadInfo>
              </a-col>
            </a-row>
            <LineMulti :chartData="lineMultiData3" height="50vh" type="line" :option="option3"></LineMulti>
          </a-card>
        </a-col>
      </a-row>
    </div>
    <div>
      <a-row>
        <a-col :span="24">
          <a-card :loading="loading" :bordered="false" :title="title4" :style="{ marginTop: '24px' }">
            <a-row>
              <a-col :span="6">
                <HeadInfo title="活跃账号" :content="overViewInfo.dau" icon="environment"></HeadInfo>
              </a-col>
              <a-col :span="6">
                <HeadInfo title="新增账号" :content="overViewInfo.regCount" icon="team"></HeadInfo>
              </a-col>
            </a-row>
            <LineMulti :chartData="lineMultiData4" height="50vh" type="line" :option="option4"></LineMulti>
          </a-card>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script lang="ts" name="count-ctUser" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import {  deleteOne, batchDelete, getImportUrl, getExportUrl } from '/@/views/statistics/userData/ctUser/CtUser.api';
  import { queryOverViewList } from '/@/views/statistics/financeData/RoiData/CtDaily.api';
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

  let date3 = new Date();
  let date4 = new Date(Date.now() - 24 * 60 * 60 * 1000 * 6);
  let year3 = date3.getFullYear();
  let month3 = date3.getMonth() + 1;
  let day3 = date3.getDate();
  let year4 = date4.getFullYear();
  let month4 = date4.getMonth() + 1;
  let day4 = date4.getDate();
  // 格式化年月日信息，回填到时间控件上
  let dateString3 = year3 + '-' + (month3 < 10 ? '0' + month3 : month3) + '-' + (day3 < 10 ? '0' + day3 : day3);
  let dateString4 = year4 + '-' + (month4 < 10 ? '0' + month4 : month4) + '-' + (day4 < 10 ? '0' + day4 : day4);
  const queryParam = ref<any>({startTime:dateString4, endTime: dateString3});

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

  /**
   * 查询
   */
  function searchQuery() {
    getList();
  }

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

  getList()
  let option= ref();
  let option2= ref();
  let option3= ref();
  let option4= ref();
  let overViewInfo = ref({});
  let resList= ref([]);
  let lineMultiData= ref([]);
  let lineMultiData2= ref([]);
  let lineMultiData3= ref([]);
  let lineMultiData4= ref([]);
  let data= ref([]);
  let data2= ref([]);
  let data3= ref([]);
  let data4= ref([]);
  let data5= ref([]);
  let data6= ref([]);
  let data7= ref([]);
  let data8= ref([]);
  let data9= ref([]);
  let data10= ref([]);
  let data11= ref([]);
  let title= ref('每日充值金额');
  let title2= ref('付费率');
  let title3= ref('ARPPU');
  let title4= ref('账号统计');
  /**
   * 查询列表
   */
  function getList() {
    queryOverViewList(queryParam.value).then((res: any)=>{
      resList.value = res
      lineMultiData.value = []
      lineMultiData2.value = []
      lineMultiData3.value = []
      lineMultiData4.value = []
      data.value = []
      data2.value = []
      data3.value = []
      data4.value = []
      data5.value = []
      data6.value = []
      data7.value = []
      data8.value = []
      data9.value = []
      data10.value = []
      data11.value = []
      resList.value.forEach(element => {
        lineMultiData.value.push({ name: element.roiDate, type: '老用户充值金额', value: element.oldUserPay })
        lineMultiData.value.push({ name: element.roiDate, type: '新增充值金额', value: element.addCostPrice })
        lineMultiData.value.push({ name: element.roiDate, type: '总充值金额', value: element.activeCostPrice })
        lineMultiData2.value.push({ name: element.roiDate, type: '付费率', value: element.activeProbability })
        lineMultiData2.value.push({ name: element.roiDate, type: '新角色付费率', value: element.addCostProbability })
        lineMultiData2.value.push({ name: element.roiDate, type: '老角色付费率', value: element.oldUserPayProbability })
        lineMultiData3.value.push({ name: element.roiDate, type: 'ARPPU', value: element.arppu })
        lineMultiData3.value.push({ name: element.roiDate, type: '新角色ARPPU', value: element.addArppu })
        lineMultiData3.value.push({ name: element.roiDate, type: '老角色APRRU', value: element.oldUserPayArppu })
        lineMultiData4.value.push({ name: element.roiDate, type: '活跃账号', value: element.dau })
        lineMultiData4.value.push({ name: element.roiDate, type: '新增账号', value: element.regCount })
        data.value.push(element.oldUserPay)
        data2.value.push(element.addCostPrice)
        data3.value.push(element.activeCostPrice)
        data4.value.push(element.activeProbability)
        data5.value.push(element.addCostProbability)
        data6.value.push(element.oldUserPayProbability)
        data7.value.push(element.arppu)
        data8.value.push(element.addArppu)
        data9.value.push(element.oldUserPayArppu)
        data10.value.push(element.dau)
        data11.value.push(element.regCount)
      });
      option.value = {
        // 图例
        legend: {
          top: 'bottom',
        },
        xAxis: {
          type: 'category',
          axisLabel: {
            interval: 0, // 每隔 0 个单位显示一个标签
            rotate: 45
          }
        },
        yAxis: {
          type: 'value',
          name: '充值金额'
        },
        // 悬浮提示框
        // tooltip: {
        //   trigger: 'axis',
        //   triggerOn: 'mousemove',
        //   formatter: function(params) {
        //     // 获取第一个数据点的数值和X轴标签
        //     // var value = params[0].value;
        //     // var label = params[0].axisValue;
        //     overViewInfo.value.oldUserPay = params[0].value
        //     overViewInfo.value.addCostPrice = params[1].value
        //     overViewInfo.value.activeCostPrice = params[2].value
        //     title.value = '每日充值金额' + '(' + params[0].axisValue + ')'
        //     // 返回提示框的标题和内容
        //     return '日期：' + params[0].axisValue + '<br/>老用户充值金额：' + params[0].value + '<br/>新增充值金额：' + params[1].value + '<br/>总充值金额：' + params[2].value;
        //   }
        // },
        grid: {
            bottom: 80  // 调整底部留白区域的大小
          },
      };
      option2.value = {
        // 图例
        legend: {
          top: 'bottom',
        },
        xAxis: {
          type: 'category',
          axisLabel: {
            interval: 0, // 每隔 0 个单位显示一个标签
            rotate: 45
          }
        },
        yAxis: {
          type: 'value',
          name: '付费率(%)'
        },
        grid: {
            bottom: 80  // 调整底部留白区域的大小
        },
        // 悬浮提示框
        tooltip: {
          trigger: 'axis',
          triggerOn: 'mousemove',
          formatter: function(params) {
            overViewInfo.value.activeProbability = params[0].value + '%'
            overViewInfo.value.addCostProbability = params[1].value + '%'
            overViewInfo.value.oldUserPayProbability = params[2].value + '%'
            title2.value = '付费率' + '(' + params[0].axisValue + ')'
            return '日期：' + params[0].axisValue + '<br/>付费率：' + params[0].value + '<br/>新角色付费率：' + params[1].value + '<br/>老角色付费率：' + params[2].value;
          }
        },
      };
      option3.value = {
        // 图例
        legend: {
          top: 'bottom',
        },
        xAxis: {
          type: 'category',
          axisLabel: {
            interval: 0, // 每隔 0 个单位显示一个标签
            rotate: 45
          }
        },
        yAxis: {
          type: 'value',
          name: 'ARPPU'
        },
        grid: {
            bottom: 80  // 调整底部留白区域的大小
        },
        // 悬浮提示框
        tooltip: {
          trigger: 'axis',
          triggerOn: 'mousemove',
          formatter: function(params) {
            overViewInfo.value.arppu = params[0].value
            overViewInfo.value.addArppu = params[1].value
            overViewInfo.value.oldUserPayArppu = params[2].value
            title3.value = 'ARPPU' + '(' + params[0].axisValue + ')'
            return '日期：' + params[0].axisValue + '<br/>ARPPU：' + params[0].value + '<br/>新角色ARPPU：' + params[1].value + '<br/>老角色APRRU：' + params[2].value;
          }
        },
      };
      option4.value = {
        // 图例
        legend: {
          top: 'bottom',
        },
        xAxis: {
          type: 'category',
          axisLabel: {
            interval: 0, // 每隔 0 个单位显示一个标签
            rotate: 45
          }
        },
        yAxis: {
          type: 'value',
          name: '账号个数'
        },
        grid: {
            bottom: 80  // 调整底部留白区域的大小
        },
        // 悬浮提示框
        tooltip: {
          trigger: 'axis',
          triggerOn: 'mousemove',
          formatter: function(params) {
            overViewInfo.value.dau = params[0].value
            overViewInfo.value.regCount = params[1].value
            title4.value = '账号统计' + '(' + params[0].axisValue + ')'
            return '日期：' + params[0].axisValue + '<br/>活跃账号：' + params[0].value + '<br/>新增账号：' + params[1].value;
          }
        },
      };
    })
  }

  /**
   * 重置
   */
  function searchReset() {
    queryParam.value = {startTime:dateString4, endTime: dateString3};
    selectGameForm.value.reload();
    selectChannelForm.value.reload();
    selectDealForm.value.reload();
    //刷新数据
    getList();
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
