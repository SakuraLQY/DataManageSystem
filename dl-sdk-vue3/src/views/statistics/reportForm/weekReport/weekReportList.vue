<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
            <a-col :lg="8">
              <a-form-item label="统计日期">
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择开始时间" v-model:value="queryParam.startDate" allowClear/>至
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择结束时间" v-model:value="queryParam.endDate" allowClear/>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
                <a-col :lg="6">
                  <a-button type="primary" preIcon="ant-design:search-outlined" @click="searchQuery">查询</a-button>
                  <a @click="toggleSearchStatus = !toggleSearchStatus" style="margin-left: 8px">
                  {{ toggleSearchStatus ? '隐藏数据控制' : '展开数据控制' }}
                  <Icon :icon="toggleSearchStatus ? 'ant-design:up-outlined' : 'ant-design:down-outlined'" />
                </a>
                </a-col>
              </span>
            </a-col>
          </a-row>
      </a-form>
      <template v-if="toggleSearchStatus">
      <a-form  :model="configParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
            <a-col :lg="8">
              <a-form-item label="一级分组">
                <a-input placeholder="请输入一级分组" v-model:value="configParam.firstGroup"></a-input>
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="二级分组">
                <a-input placeholder="请输入二级分组" v-model:value="configParam.secondGroup"></a-input>
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="游戏名称">
                <a-input placeholder="请输入游戏名称" v-model:value="configParam.gameNickName"></a-input>
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="渠道名称">
                <a-input placeholder="请输入渠道名称" v-model:value="configParam.channelNickName"></a-input>
              </a-form-item>
            </a-col>
          
            <GameThirdMuchOptionForm ref="selectGameForm" @handler="getGameVal"></GameThirdMuchOptionForm>
            <ChannelThirdMuchOptionForm ref="selectChannelForm" @handler="getChannelVal"></ChannelThirdMuchOptionForm>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
                <a-col :lg="6">
                  <a-button type="primary" @click="addConfig">添加</a-button>
                </a-col>
              </span>
            </a-col>
          </a-row>
          <div  v-for="(item,index) in weekReportConfig" > 一级分组:{{ item.firstGroup }} &nbsp; 二级分组: {{ item.secondGroup }} &nbsp; 游戏名称:{{ item.gameNickName }} &nbsp; 渠道名称:{{ item.channelNickName }} &nbsp; 游戏名:{{ item.gameName }}&nbsp; 子游戏名:{{ item.subGameName }}&nbsp; 渠道游戏包名:{{ item.pkgName }}&nbsp; 渠道类型:{{ item.channelTypeName }}&nbsp; 渠道:{{ item.channelName }}&nbsp; 渠道子账号:{{ item.channelSubAccountName }}&nbsp;<a-button type="link" @click="deleteConfig(index)">删除</a-button></div>
      </a-form>
    </template>
      
    </div>
    <BasicTable v-if = false></BasicTable>
    <a-table :columns="dataColumns" :data-source="dataData" :pagination="false" bordered>
      <template #title>{{ dataTableTitle }}</template>
    </a-table>
    <br/>
    <a-table :columns="marketColumns" :data-source="marketData" :pagination="false" bordered>
      <template #title>{{ marketTableTitle }}</template>
    </a-table>
    <br/>
    <a-table :columns="detailColumns" :data-source="detailData" :pagination="false" bordered>
      <template #title>{{ detailTableTitle }}</template>
    </a-table>

  </div>
</template>

<script lang="ts" name="count-weekReport" setup>
  import { ref, reactive } from 'vue';
  import {formatToDate} from  '/@/utils/dateUtil'
  import { dataColumns,marketColumns,detailColumns } from './weekReport.data';
  import { list,queryByConfigName,saveOrUpdateConfig } from './weekReport.api';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import GameThirdMuchOptionForm from '/@/views/common/gameThirdMuchOptionForm.vue';
  import ChannelThirdMuchOptionForm from '/@/views/common/channelThirdMuchOptionForm.vue';
  import { message } from 'ant-design-vue';
  
  const selectGameForm= ref();
  const configName = "WEEK_REPORT";
  const selectChannelForm= ref();
  const labelCol = reactive({
    xs: { span: 24 },
    sm: { span: 7 },
  });
  const wrapperCol = reactive({
    xs: { span: 24 },
    sm: { span: 16 },
  });
  const weekReportConfig = ref<any>([]);
  const queryParam = ref<any>({
    startDate: formatToDate(new Date(new Date().getTime() - (6 * 24 * 60 * 60 * 1000))),
    endDate: formatToDate(new Date())
  });
  const toggleSearchStatus = ref(false)

  const configParam = ref<any>({

  });

  let getGameVal = (e: any) => {
    configParam.value.gameId = e.gameId;
    configParam.value.subGameId = e.subGameId;
    configParam.value.pkgId = e.pkgId;
  };
  let getChannelVal = (e: any) => {
    configParam.value.channelTypeId = e.channelTypeId;
    configParam.value.channelId = e.channelId;
    configParam.value.channelSubAccountId = e.channelSubAccountId;
  };

  const dataData = ref([]);
  const dataTableTitle = ref("数据报表");
  const marketData = ref([]);
  const marketTableTitle = ref("大盘同期环比数据");
  const detailData = ref([]);
  const detailTableTitle = ref("环比详细数据");
 
  searchQuery();
  /**
   * 查询
   */
  function searchQuery() {
    dataTableTitle.value = queryParam.value.startDate + "~" + queryParam.value.endDate + "数据报表";
    list(queryParam.value).then(res=>{
      //  数据报表
      dataData.value = res.data;
      //  大盘同期环比数据
      marketData.value  = res.market;
      // 环比详细数据
      detailData.value  = res.detail;
    });
    queryByConfigName({configName:configName}).then(res=>{
        weekReportConfig.value = res;
        console.log(res);
        
    })

  }
  
  // 添加配置
  function addConfig(){
    let isUpdate = false;
    if(weekReportConfig.value && weekReportConfig.value.length > 0){
      isUpdate = true;
    }else{
      weekReportConfig.value = [];
    }
    if(!configParam.value.gameId){
      message.warn("请选择游戏");
      return;
    }
    if(!configParam.value.channelTypeId){
      message.warn("请选择渠道类型");
      return;
    }
    weekReportConfig.value.push({
      firstGroup: configParam.value.firstGroup ,
      secondGroup: configParam.value.secondGroup,
      gameNickName: configParam.value.gameNickName,
      channelNickName: configParam.value.channelNickName,
      gameId : configParam.value.gameId ? configParam.value.gameId : [],
      subGameId : configParam.value.subGameId ? configParam.value.subGameId : [],
      pkgId : configParam.value.pkgId ? configParam.value.pkgId : [],
      channelTypeId : configParam.value.channelTypeId ? configParam.value.channelTypeId : [],
      channelId : configParam.value.channelId ? configParam.value.channelId : [],
      channelSubAccountId : configParam.value.channelSubAccountId ? configParam.value.channelSubAccountId : [],
    });
    const param = getConfigParam();
    saveOrUpdateConfig({configName:configName, config:JSON.stringify(param)}, isUpdate).then(res=>
      message.success("更新成功")
    );
  }
  
  // 删除配置
  function deleteConfig(index){
    weekReportConfig.value.splice(index, 1);
    const param = getConfigParam();
    saveOrUpdateConfig({configName:configName, config:JSON.stringify(param)}, true).then(res=>
      message.success("更新成功")
    );
  }

  /**
   * 获取配置信息
   */
  function getConfigParam(){
    let map = new Map();
    for(let config of weekReportConfig.value){
      let c = {
        secondGroup: config.secondGroup,
        gameNickName: config.gameNickName,
        channelNickName: config.channelNickName,
        channelId: config.channelId,
        channelTypeId: config.channelTypeId,
        channelSubAccountId: config.channelSubAccountId,
        gameId: config.gameId,
        subGameId: config.subGameId,
        pkgId: config.pkgId,
      }  
      if(map.get(config.firstGroup)){
        map.get(config.firstGroup).push(c);
      }else{
        map.set(config.firstGroup,[c]);
      }
    }
    let param:any = [];
    for (const [key, value] of map) {
      param.push({
        firstGroup: key,
        data: value
      });
    }
    return param;
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

<style>
  .ant-table-title{
    text-align: center;
  }
</style>
