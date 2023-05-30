<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <a-col :lg="8">
            <a-form-item label="广告列表">
              <j-search-select placeholder="请输入广告列表" v-model:value="queryParam.dealId" dict="open_gateway.op_deal,deal_name,id" allowClear/>
            </a-form-item>
          </a-col>
          <GameThirdSingleOptionForm ref="selectGameForm" @handler="getGameVal"></GameThirdSingleOptionForm>
          <template v-if="toggleSearchStatus">
            <ChannelThirdOptionForm ref="selectChannelForm" @handler="getChannelVal"></ChannelThirdOptionForm>
            <a-col :lg="8">
              <a-form-item label="起始日期">
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择起始日期" v-model:value="queryParam.startTime" />
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="结束日期">
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择结束日期" v-model:value="queryParam.endTime" />
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
    <!-- <Card>
    <DailyAnalyzeChat />
    </Card> -->
    <div>
      <LineMulti :chartData="lineMultiData" height="50vh" type="line" :option="barOption" ></LineMulti>
    </div>
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection">
      <!--插槽:table标题-->
      <template #tableTitle>
      
        <!-- <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>
        <a-button  type="primary" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
        <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button> -->
        <a-dropdown v-if="selectedRowKeys.length > 0">
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
        </a-dropdown>
      </template>
      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)"/>
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
    <!-- 表单区域 -->
    <CtDayAnalyzeModal ref="registerModal" @success="handleSuccess"></CtDayAnalyzeModal>
  </div>
</template>

<script lang="ts" name="count-ctDayAnalyze" setup>
  import LineMulti from '/@/components/chart/LineMulti.vue';
  import { ref, reactive,provide} from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './CtDayAnalyze.data';
  import { queryList, deleteOne, batchDelete, getImportUrl, getExportUrl } from './CtDayAnalyze.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import CtDayAnalyzeModal from './components/CtDayAnalyzeModal.vue'
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import JSelectMultiple from '/@/components/Form/src/jeecg/components/JSelectMultiple.vue';
  import {formatToDate } from '/@/utils/dateUtil';
  import DailyAnalyzeChat from './components/DailyAnalyzeChat.vue';
  import { Card } from 'ant-design-vue';
  import GameThirdSingleOptionForm from '/@/views/common/gameThirdSingleOptionForm.vue';
  import ChannelThirdOptionForm from '/@/views/common/channelThirdOptionForm.vue';
  const lineMultiData = ref([]);
  const queryParam = ref<any>({startTime:formatToDate(new Date()),endTime:formatToDate(new Date())});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  const result = ref();
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

    //设置x,y轴对应的属性
    const barOption = ref({
    title: { text: '分时数据分析', left: 'center'},
    xAxis:{
      // inverse: true //反向坐标
    },
      legend: { top: 'bottom' },
      yAxis: {
      type: 'value',
      name: '数值（个）',
      nameLocation: 'middle',
      nameGap: 45
    },
  })
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'ct_dayanalyze',
      api: queryList,
      columns,
      pagination:false,
      canResize:false,
      useSearchForm: false,
      showActionColumn:false,
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        return Object.assign(params, queryParam.value);
      },
      afterFetch:(res)=>{
        lineMultiData.value = [];
        // result.value = res;
          if(res[0].level==='激活数'){
          lineMultiData.value.push({name:'0时',type:'激活数',value:res[0].zeroHour})
          lineMultiData.value.push({name:'1时',type:'激活数',value:res[0].oneHour})
          lineMultiData.value.push({name:'2时',type:'激活数',value:res[0].twoHour})
          lineMultiData.value.push({name:'3时',type:'激活数',value:res[0].threeHour})
          lineMultiData.value.push({name:'4时',type:'激活数',value:res[0].fourHour})
          lineMultiData.value.push({name:'5时',type:'激活数',value:res[0].fiveHour})
          lineMultiData.value.push({name:'6时',type:'激活数',value:res[0].sixHour})
          lineMultiData.value.push({name:'7时',type:'激活数',value:res[0].sevenHour})
          lineMultiData.value.push({name:'8时',type:'激活数',value:res[0].eightHour})
          lineMultiData.value.push({name:'9时',type:'激活数',value:res[0].nineHour})
          lineMultiData.value.push({name:'10时',type:'激活数',value:res[0].tenHour})
          lineMultiData.value.push({name:'11时',type:'激活数',value:res[0].elevenHour})
          lineMultiData.value.push({name:'12时',type:'激活数',value:res[0].twelveHour})
          lineMultiData.value.push({name:'13时',type:'激活数',value:res[0].thirteenHour})
          lineMultiData.value.push({name:'14时',type:'激活数',value:res[0].fourteenHour})
          lineMultiData.value.push({name:'15时',type:'激活数',value:res[0].fifteenHour})
          lineMultiData.value.push({name:'16时',type:'激活数',value:res[0].sixteenHour})
          lineMultiData.value.push({name:'17时',type:'激活数',value:res[0].seventeenHour})
          lineMultiData.value.push({name:'18时',type:'激活数',value:res[0].eighteenHour})
          lineMultiData.value.push({name:'19时',type:'激活数',value:res[0].nineteenHour})
          lineMultiData.value.push({name:'20时',type:'激活数',value:res[0].twentyHour})
          lineMultiData.value.push({name:'21时',type:'激活数',value:res[0].twentyoneHour})
          lineMultiData.value.push({name:'22时',type:'激活数',value:res[0].twentytwoHour})
          lineMultiData.value.push({name:'23时',type:'激活数',value:res[0].twentythreeHour})
        }
        if(res[1].level==='激活设备数'){
          lineMultiData.value.push({name:'0时',type:'激活设备数',value:res[1].zeroHour})
          lineMultiData.value.push({name:'1时',type:'激活设备数',value:res[1].oneHour})
          lineMultiData.value.push({name:'2时',type:'激活设备数',value:res[1].twoHour})
          lineMultiData.value.push({name:'3时',type:'激活设备数',value:res[1].threeHour})
          lineMultiData.value.push({name:'4时',type:'激活设备数',value:res[1].fourHour})
          lineMultiData.value.push({name:'5时',type:'激活设备数',value:res[1].fiveHour})
          lineMultiData.value.push({name:'6时',type:'激活设备数',value:res[1].sixHour})
          lineMultiData.value.push({name:'7时',type:'激活设备数',value:res[1].sevenHour})
          lineMultiData.value.push({name:'8时',type:'激活设备数',value:res[1].eightHour})
          lineMultiData.value.push({name:'9时',type:'激活设备数',value:res[1].nineHour})
          lineMultiData.value.push({name:'10时',type:'激活设备数',value:res[1].tenHour})
          lineMultiData.value.push({name:'11时',type:'激活设备数',value:res[1].elevenHour})
          lineMultiData.value.push({name:'12时',type:'激活设备数',value:res[1].twelveHour})
          lineMultiData.value.push({name:'13时',type:'激活设备数',value:res[1].thirteenHour})
          lineMultiData.value.push({name:'14时',type:'激活设备数',value:res[1].fourteenHour})
          lineMultiData.value.push({name:'15时',type:'激活设备数',value:res[1].fifteenHour})
          lineMultiData.value.push({name:'16时',type:'激活设备数',value:res[1].sixteenHour})
          lineMultiData.value.push({name:'17时',type:'激活设备数',value:res[1].seventeenHour})
          lineMultiData.value.push({name:'18时',type:'激活设备数',value:res[1].eighteenHour})
          lineMultiData.value.push({name:'19时',type:'激活设备数',value:res[1].nineteenHour})
          lineMultiData.value.push({name:'20时',type:'激活设备数',value:res[1].twentyHour})
          lineMultiData.value.push({name:'21时',type:'激活设备数',value:res[1].twentyoneHour})
          lineMultiData.value.push({name:'22时',type:'激活设备数',value:res[1].twentytwoHour})
          lineMultiData.value.push({name:'23时',type:'激活设备数',value:res[1].twentythreeHour})
        }
        if(res[2].level==='注册数'){
          lineMultiData.value.push({name:'0时',type:'注册数',value:res[2].zeroHour})
          lineMultiData.value.push({name:'1时',type:'注册数',value:res[2].oneHour})
          lineMultiData.value.push({name:'2时',type:'注册数',value:res[2].twoHour})
          lineMultiData.value.push({name:'3时',type:'注册数',value:res[2].threeHour})
          lineMultiData.value.push({name:'4时',type:'注册数',value:res[2].fourHour})
          lineMultiData.value.push({name:'5时',type:'注册数',value:res[2].fiveHour})
          lineMultiData.value.push({name:'6时',type:'注册数',value:res[2].sixHour})
          lineMultiData.value.push({name:'7时',type:'注册数',value:res[2].sevenHour})
          lineMultiData.value.push({name:'8时',type:'注册数',value:res[2].eightHour})
          lineMultiData.value.push({name:'9时',type:'注册数',value:res[2].nineHour})
          lineMultiData.value.push({name:'10时',type:'注册数',value:res[2].tenHour})
          lineMultiData.value.push({name:'11时',type:'注册数',value:res[2].elevenHour})
          lineMultiData.value.push({name:'12时',type:'注册数',value:res[2].twelveHour})
          lineMultiData.value.push({name:'13时',type:'注册数',value:res[2].thirteenHour})
          lineMultiData.value.push({name:'14时',type:'注册数',value:res[2].fourteenHour})
          lineMultiData.value.push({name:'15时',type:'注册数',value:res[2].fifteenHour})
          lineMultiData.value.push({name:'16时',type:'注册数',value:res[2].sixteenHour})
          lineMultiData.value.push({name:'17时',type:'注册数',value:res[2].seventeenHour})
          lineMultiData.value.push({name:'18时',type:'注册数',value:res[2].eighteenHour})
          lineMultiData.value.push({name:'19时',type:'注册数',value:res[2].nineteenHour})
          lineMultiData.value.push({name:'20时',type:'注册数',value:res[2].twentyHour})
          lineMultiData.value.push({name:'21时',type:'注册数',value:res[2].twentyoneHour})
          lineMultiData.value.push({name:'22时',type:'注册数',value:res[2].twentytwoHour})
          lineMultiData.value.push({name:'23时',type:'注册数',value:res[2].twentythreeHour})
        }
        if(res[3].level==='注册设备数'){
          lineMultiData.value.push({name:'0时',type:'注册设备数',value:res[3].zeroHour})
          lineMultiData.value.push({name:'1时',type:'注册设备数',value:res[3].oneHour})
          lineMultiData.value.push({name:'2时',type:'注册设备数',value:res[3].twoHour})
          lineMultiData.value.push({name:'3时',type:'注册设备数',value:res[3].threeHour})
          lineMultiData.value.push({name:'4时',type:'注册设备数',value:res[3].fourHour})
          lineMultiData.value.push({name:'5时',type:'注册设备数',value:res[3].fiveHour})
          lineMultiData.value.push({name:'6时',type:'注册设备数',value:res[3].sixHour})
          lineMultiData.value.push({name:'7时',type:'注册设备数',value:res[3].sevenHour})
          lineMultiData.value.push({name:'8时',type:'注册设备数',value:res[3].eightHour})
          lineMultiData.value.push({name:'9时',type:'注册设备数',value:res[3].nineHour})
          lineMultiData.value.push({name:'10时',type:'注册设备数',value:res[3].tenHour})
          lineMultiData.value.push({name:'11时',type:'注册设备数',value:res[3].elevenHour})
          lineMultiData.value.push({name:'12时',type:'注册设备数',value:res[3].twelveHour})
          lineMultiData.value.push({name:'13时',type:'注册设备数',value:res[3].thirteenHour})
          lineMultiData.value.push({name:'14时',type:'注册设备数',value:res[3].fourteenHour})
          lineMultiData.value.push({name:'15时',type:'注册设备数',value:res[3].fifteenHour})
          lineMultiData.value.push({name:'16时',type:'注册设备数',value:res[3].sixteenHour})
          lineMultiData.value.push({name:'17时',type:'注册设备数',value:res[3].seventeenHour})
          lineMultiData.value.push({name:'18时',type:'注册设备数',value:res[3].eighteenHour})
          lineMultiData.value.push({name:'19时',type:'注册设备数',value:res[3].nineteenHour})
          lineMultiData.value.push({name:'20时',type:'注册设备数',value:res[3].twentyHour})
          lineMultiData.value.push({name:'21时',type:'注册设备数',value:res[3].twentyoneHour})
          lineMultiData.value.push({name:'22时',type:'注册设备数',value:res[3].twentytwoHour})
          lineMultiData.value.push({name:'23时',type:'注册设备数',value:res[3].twentythreeHour})
        }
        if(res[4].level==='新增付费人数'){
          lineMultiData.value.push({name:'0时',type:'新增付费人数',value:res[4].zeroHour})
          lineMultiData.value.push({name:'1时',type:'新增付费人数',value:res[4].oneHour})
          lineMultiData.value.push({name:'2时',type:'新增付费人数',value:res[4].twoHour})
          lineMultiData.value.push({name:'4时',type:'新增付费人数',value:res[4].threeHour})
          lineMultiData.value.push({name:'4时',type:'新增付费人数',value:res[4].fourHour})
          lineMultiData.value.push({name:'5时',type:'新增付费人数',value:res[4].fiveHour})
          lineMultiData.value.push({name:'6时',type:'新增付费人数',value:res[4].sixHour})
          lineMultiData.value.push({name:'7时',type:'新增付费人数',value:res[4].sevenHour})
          lineMultiData.value.push({name:'8时',type:'新增付费人数',value:res[4].eightHour})
          lineMultiData.value.push({name:'9时',type:'新增付费人数',value:res[4].nineHour})
          lineMultiData.value.push({name:'10时',type:'新增付费人数',value:res[4].tenHour})
          lineMultiData.value.push({name:'11时',type:'新增付费人数',value:res[4].elevenHour})
          lineMultiData.value.push({name:'12时',type:'新增付费人数',value:res[4].twelveHour})
          lineMultiData.value.push({name:'14时',type:'新增付费人数',value:res[4].thirteenHour})
          lineMultiData.value.push({name:'14时',type:'新增付费人数',value:res[4].fourteenHour})
          lineMultiData.value.push({name:'15时',type:'新增付费人数',value:res[4].fifteenHour})
          lineMultiData.value.push({name:'16时',type:'新增付费人数',value:res[4].sixteenHour})
          lineMultiData.value.push({name:'17时',type:'新增付费人数',value:res[4].seventeenHour})
          lineMultiData.value.push({name:'18时',type:'新增付费人数',value:res[4].eighteenHour})
          lineMultiData.value.push({name:'19时',type:'新增付费人数',value:res[4].nineteenHour})
          lineMultiData.value.push({name:'20时',type:'新增付费人数',value:res[4].twentyHour})
          lineMultiData.value.push({name:'21时',type:'新增付费人数',value:res[4].twentyoneHour})
          lineMultiData.value.push({name:'22时',type:'新增付费人数',value:res[4].twentytwoHour})
          lineMultiData.value.push({name:'23时',type:'新增付费人数',value:res[4].twentythreeHour})
        }
        if(res[5].level==='新增付费金额'){
          lineMultiData.value.push({name:'0时',type:'新增付费金额',value:res[5].zeroHour})
          lineMultiData.value.push({name:'1时',type:'新增付费金额',value:res[5].oneHour})
          lineMultiData.value.push({name:'2时',type:'新增付费金额',value:res[5].twoHour})
          lineMultiData.value.push({name:'5时',type:'新增付费金额',value:res[5].threeHour})
          lineMultiData.value.push({name:'5时',type:'新增付费金额',value:res[5].fourHour})
          lineMultiData.value.push({name:'5时',type:'新增付费金额',value:res[5].fiveHour})
          lineMultiData.value.push({name:'6时',type:'新增付费金额',value:res[5].sixHour})
          lineMultiData.value.push({name:'7时',type:'新增付费金额',value:res[5].sevenHour})
          lineMultiData.value.push({name:'8时',type:'新增付费金额',value:res[5].eightHour})
          lineMultiData.value.push({name:'9时',type:'新增付费金额',value:res[5].nineHour})
          lineMultiData.value.push({name:'10时',type:'新增付费金额',value:res[5].tenHour})
          lineMultiData.value.push({name:'11时',type:'新增付费金额',value:res[5].elevenHour})
          lineMultiData.value.push({name:'12时',type:'新增付费金额',value:res[5].twelveHour})
          lineMultiData.value.push({name:'15时',type:'新增付费金额',value:res[5].thirteenHour})
          lineMultiData.value.push({name:'15时',type:'新增付费金额',value:res[5].fourteenHour})
          lineMultiData.value.push({name:'15时',type:'新增付费金额',value:res[5].fifteenHour})
          lineMultiData.value.push({name:'16时',type:'新增付费金额',value:res[5].sixteenHour})
          lineMultiData.value.push({name:'17时',type:'新增付费金额',value:res[5].seventeenHour})
          lineMultiData.value.push({name:'18时',type:'新增付费金额',value:res[5].eighteenHour})
          lineMultiData.value.push({name:'19时',type:'新增付费金额',value:res[5].nineteenHour})
          lineMultiData.value.push({name:'20时',type:'新增付费金额',value:res[5].twentyHour})
          lineMultiData.value.push({name:'21时',type:'新增付费金额',value:res[5].twentyoneHour})
          lineMultiData.value.push({name:'22时',type:'新增付费金额',value:res[5].twentytwoHour})
          lineMultiData.value.push({name:'23时',type:'新增付费金额',value:res[5].twentythreeHour})
        }
        if(res[6].level==='累计付费金额'){
          lineMultiData.value.push({name:'0时',type:'累计付费金额',value:res[6].zeroHour})
          lineMultiData.value.push({name:'1时',type:'累计付费金额',value:res[6].oneHour})
          lineMultiData.value.push({name:'2时',type:'累计付费金额',value:res[6].twoHour})
          lineMultiData.value.push({name:'6时',type:'累计付费金额',value:res[6].threeHour})
          lineMultiData.value.push({name:'6时',type:'累计付费金额',value:res[6].fourHour})
          lineMultiData.value.push({name:'6时',type:'累计付费金额',value:res[6].fiveHour})
          lineMultiData.value.push({name:'6时',type:'累计付费金额',value:res[6].sixHour})
          lineMultiData.value.push({name:'7时',type:'累计付费金额',value:res[6].sevenHour})
          lineMultiData.value.push({name:'8时',type:'累计付费金额',value:res[6].eightHour})
          lineMultiData.value.push({name:'9时',type:'累计付费金额',value:res[6].nineHour})
          lineMultiData.value.push({name:'10时',type:'累计付费金额',value:res[6].tenHour})
          lineMultiData.value.push({name:'11时',type:'累计付费金额',value:res[6].elevenHour})
          lineMultiData.value.push({name:'12时',type:'累计付费金额',value:res[6].twelveHour})
          lineMultiData.value.push({name:'16时',type:'累计付费金额',value:res[6].thirteenHour})
          lineMultiData.value.push({name:'16时',type:'累计付费金额',value:res[6].fourteenHour})
          lineMultiData.value.push({name:'16时',type:'累计付费金额',value:res[6].fifteenHour})
          lineMultiData.value.push({name:'16时',type:'累计付费金额',value:res[6].sixteenHour})
          lineMultiData.value.push({name:'17时',type:'累计付费金额',value:res[6].seventeenHour})
          lineMultiData.value.push({name:'18时',type:'累计付费金额',value:res[6].eighteenHour})
          lineMultiData.value.push({name:'19时',type:'累计付费金额',value:res[6].nineteenHour})
          lineMultiData.value.push({name:'20时',type:'累计付费金额',value:res[6].twentyHour})
          lineMultiData.value.push({name:'21时',type:'累计付费金额',value:res[6].twentyoneHour})
          lineMultiData.value.push({name:'22时',type:'累计付费金额',value:res[6].twentytwoHour})
          lineMultiData.value.push({name:'23时',type:'累计付费金额',value:res[6].twentythreeHour})
        }
        if(res[7].level==='活跃付费人数'){
          lineMultiData.value.push({name:'0时',type:'活跃付费人数',value:res[7].zeroHour})
          lineMultiData.value.push({name:'1时',type:'活跃付费人数',value:res[7].oneHour})
          lineMultiData.value.push({name:'2时',type:'活跃付费人数',value:res[7].twoHour})
          lineMultiData.value.push({name:'7时',type:'活跃付费人数',value:res[7].threeHour})
          lineMultiData.value.push({name:'7时',type:'活跃付费人数',value:res[7].fourHour})
          lineMultiData.value.push({name:'7时',type:'活跃付费人数',value:res[7].fiveHour})
          lineMultiData.value.push({name:'7时',type:'活跃付费人数',value:res[7].sixHour})
          lineMultiData.value.push({name:'7时',type:'活跃付费人数',value:res[7].sevenHour})
          lineMultiData.value.push({name:'8时',type:'活跃付费人数',value:res[7].eightHour})
          lineMultiData.value.push({name:'9时',type:'活跃付费人数',value:res[7].nineHour})
          lineMultiData.value.push({name:'10时',type:'活跃付费人数',value:res[7].tenHour})
          lineMultiData.value.push({name:'11时',type:'活跃付费人数',value:res[7].elevenHour})
          lineMultiData.value.push({name:'12时',type:'活跃付费人数',value:res[7].twelveHour})
          lineMultiData.value.push({name:'17时',type:'活跃付费人数',value:res[7].thirteenHour})
          lineMultiData.value.push({name:'17时',type:'活跃付费人数',value:res[7].fourteenHour})
          lineMultiData.value.push({name:'17时',type:'活跃付费人数',value:res[7].fifteenHour})
          lineMultiData.value.push({name:'17时',type:'活跃付费人数',value:res[7].sixteenHour})
          lineMultiData.value.push({name:'17时',type:'活跃付费人数',value:res[7].seventeenHour})
          lineMultiData.value.push({name:'18时',type:'活跃付费人数',value:res[7].eighteenHour})
          lineMultiData.value.push({name:'19时',type:'活跃付费人数',value:res[7].nineteenHour})
          lineMultiData.value.push({name:'20时',type:'活跃付费人数',value:res[7].twentyHour})
          lineMultiData.value.push({name:'21时',type:'活跃付费人数',value:res[7].twentyoneHour})
          lineMultiData.value.push({name:'22时',type:'活跃付费人数',value:res[7].twentytwoHour})
          lineMultiData.value.push({name:'23时',type:'活跃付费人数',value:res[7].twentythreeHour})
        }
        if(res[8].level==='活跃付费金额'){
          lineMultiData.value.push({name:'0时',type:'活跃付费金额',value:res[8].zeroHour})
          lineMultiData.value.push({name:'1时',type:'活跃付费金额',value:res[8].oneHour})
          lineMultiData.value.push({name:'2时',type:'活跃付费金额',value:res[8].twoHour})
          lineMultiData.value.push({name:'8时',type:'活跃付费金额',value:res[8].threeHour})
          lineMultiData.value.push({name:'8时',type:'活跃付费金额',value:res[8].fourHour})
          lineMultiData.value.push({name:'8时',type:'活跃付费金额',value:res[8].fiveHour})
          lineMultiData.value.push({name:'8时',type:'活跃付费金额',value:res[8].sixHour})
          lineMultiData.value.push({name:'8时',type:'活跃付费金额',value:res[8].sevenHour})
          lineMultiData.value.push({name:'8时',type:'活跃付费金额',value:res[8].eightHour})
          lineMultiData.value.push({name:'9时',type:'活跃付费金额',value:res[8].nineHour})
          lineMultiData.value.push({name:'10时',type:'活跃付费金额',value:res[8].tenHour})
          lineMultiData.value.push({name:'11时',type:'活跃付费金额',value:res[8].elevenHour})
          lineMultiData.value.push({name:'12时',type:'活跃付费金额',value:res[8].twelveHour})
          lineMultiData.value.push({name:'18时',type:'活跃付费金额',value:res[8].thirteenHour})
          lineMultiData.value.push({name:'18时',type:'活跃付费金额',value:res[8].fourteenHour})
          lineMultiData.value.push({name:'18时',type:'活跃付费金额',value:res[8].fifteenHour})
          lineMultiData.value.push({name:'18时',type:'活跃付费金额',value:res[8].sixteenHour})
          lineMultiData.value.push({name:'18时',type:'活跃付费金额',value:res[8].seventeenHour})
          lineMultiData.value.push({name:'18时',type:'活跃付费金额',value:res[8].eighteenHour})
          lineMultiData.value.push({name:'19时',type:'活跃付费金额',value:res[8].nineteenHour})
          lineMultiData.value.push({name:'20时',type:'活跃付费金额',value:res[8].twentyHour})
          lineMultiData.value.push({name:'21时',type:'活跃付费金额',value:res[8].twentyoneHour})
          lineMultiData.value.push({name:'22时',type:'活跃付费金额',value:res[8].twentytwoHour})
          lineMultiData.value.push({name:'23时',type:'活跃付费金额',value:res[8].twentythreeHour})
        }
        if(res[9].level==='DAU'){
          lineMultiData.value.push({name:'0时',type:'DAU',value:res[9].zeroHour})
          lineMultiData.value.push({name:'1时',type:'DAU',value:res[9].oneHour})
          lineMultiData.value.push({name:'2时',type:'DAU',value:res[9].twoHour})
          lineMultiData.value.push({name:'9时',type:'DAU',value:res[9].threeHour})
          lineMultiData.value.push({name:'9时',type:'DAU',value:res[9].fourHour})
          lineMultiData.value.push({name:'9时',type:'DAU',value:res[9].fiveHour})
          lineMultiData.value.push({name:'9时',type:'DAU',value:res[9].sixHour})
          lineMultiData.value.push({name:'9时',type:'DAU',value:res[9].sevenHour})
          lineMultiData.value.push({name:'9时',type:'DAU',value:res[9].eightHour})
          lineMultiData.value.push({name:'9时',type:'DAU',value:res[9].nineHour})
          lineMultiData.value.push({name:'10时',type:'DAU',value:res[9].tenHour})
          lineMultiData.value.push({name:'11时',type:'DAU',value:res[9].elevenHour})
          lineMultiData.value.push({name:'12时',type:'DAU',value:res[9].twelveHour})
          lineMultiData.value.push({name:'19时',type:'DAU',value:res[9].thirteenHour})
          lineMultiData.value.push({name:'19时',type:'DAU',value:res[9].fourteenHour})
          lineMultiData.value.push({name:'19时',type:'DAU',value:res[9].fifteenHour})
          lineMultiData.value.push({name:'19时',type:'DAU',value:res[9].sixteenHour})
          lineMultiData.value.push({name:'19时',type:'DAU',value:res[9].seventeenHour})
          lineMultiData.value.push({name:'19时',type:'DAU',value:res[9].eighteenHour})
          lineMultiData.value.push({name:'19时',type:'DAU',value:res[9].nineteenHour})
          lineMultiData.value.push({name:'20时',type:'DAU',value:res[9].twentyHour})
          lineMultiData.value.push({name:'21时',type:'DAU',value:res[9].twentyoneHour})
          lineMultiData.value.push({name:'22时',type:'DAU',value:res[9].twentytwoHour})
          lineMultiData.value.push({name:'23时',type:'DAU',value:res[8].twentythreeHour})
        }
      }
    },
    
 
    //导入导出excel
    exportConfig: {
      name: "ct_dayanalyze",
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

//Echarts数据处理
 
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
  provide('params',queryParam.value);

  provide('data',result)
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
  // .extra-wrapper {
  //   line-height: 55px;
  //   padding-right: 24px;

  //   .extra-item {
  //     display: inline-block;
  //     margin-right: 24px;

  //     a {
  //       margin-left: 24px;
  //     }
  //   }
  // }
</style>
