<template>
  <PageWrapper title="营销目标与场景">
    <a-card>
      <CollapseContainer title="推广目的">
      <div class="div-style">
        <a-radio-group  v-model:value="formData.landingType">
          <a-radio-button :value="JrttEnum.LANDINGTYPE.APP" class = "radio-button">
            <div>
              <div style="font-size:14px;">应用推广</div>
              <div style="font-size:12px;margin-top: 4px; color: darkgrey;">推广您的线上APP</div>
            </div>    
          </a-radio-button>
          <a-radio-button value="" disabled class = "radio-button">
            <div style="font-size:14px;">销售线索</div>
            <div style="font-size:12px;margin-top: 4px; color: darkgrey;">为您的生意销售收集用户线索</div>
          </a-radio-button>
          <a-radio-button value="" disabled class = "radio-button">
            <div style="font-size:14px;">小游戏</div>
            <div style="font-size:12px;margin-top: 4px; color: darkgrey;">吸引更多用户进入小游戏</div>
          </a-radio-button>
        </a-radio-group>
      </div>
      <div class="div-style">
        <a-radio-group  v-model:value="formData.appPromotionType" button-style="solid" >
          <a-radio-button :value="JrttEnum.APPPROMOTIONTYPE.DOWNLOAD">应用下载</a-radio-button>
          <a-radio-button value="" disabled>应用调起</a-radio-button>
          <a-radio-button value="" disabled>预约下线</a-radio-button>
        </a-radio-group>
      </div>
      <div class="div-style">
        <BulbFilled />
        <span disabled style="margin-left: 4px;">{{appPromotionTypetip}}</span>
      </div>
    </CollapseContainer>
    <CollapseContainer title="营销场景">
      <div class = "div-style">
        <a-radio-group v-model:value="formData.marketingGoal" button-style="solid">
          <a-radio-button :value="JrttEnum.MARKETINGGOAL.VIDEO_AND_IMAGE">短视频+图文</a-radio-button>
          <a-radio-button value="" disabled>直播</a-radio-button>
        </a-radio-group>
      </div>
    </CollapseContainer>
    <CollapseContainer title="广告类型">
      <div class = "div-style">
        <a-radio-group v-model:value="formData.adType" button-style="solid">
          <a-radio-button :value="JrttEnum.ADTYPE.ALL" title = "所有流量位投放，包括但不限于信息流广告、搜索广告等，寻找对您产品或服务感兴趣的用户。"> 所有广告<QuestionCircleFilled /></a-radio-button>
          <a-radio-button value="" title = "搜索广告类型正逐步开放中" disabled>搜索广告<QuestionCircleFilled /></a-radio-button>
        </a-radio-group>
      </div>
    </CollapseContainer>
    </a-card> 
  </PageWrapper>
  <PageWrapper title = "投放内容与目标">
    <a-card>
    <CollapseContainer title="下载方式">
      <div class = "div-style">
        <a-radio-group v-model:value="formData.downloadType" button-style="solid" :disabled="formData.id != undefined">
          <a-radio-button :value="JrttEnum.DOWNLOADTYPE.DOWNLOAD_URL">直接下线</a-radio-button>
          <a-radio-button :value="JrttEnum.DOWNLOADTYPE.EXTERNAL_URL">落地页下载</a-radio-button>
        </a-radio-group>
      </div>
    </CollapseContainer>
    <CollapseContainer title="优先应用商店下载">
      <div class = "div-style">
        <a-radio-group v-model:value="formData.downloadMode" button-style="solid">
          <a-radio-button :value="JrttEnum.DOWNLOADMODE.DEFAULT">不启用</a-radio-button>
          <a-radio-button :value="JrttEnum.DOWNLOADMODE.APP_STORE_DELIVERY">启用</a-radio-button>
        </a-radio-group>
      </div>
    </CollapseContainer>
    <CollapseContainer title="事件管理">
      <a-select placeholder="请选择优化目标" style="margin-left: 10px;" v-model:value="formData.optimizeGoal.external_action" @change="onChangeEvent" :options="eventOptions" :disabled="formData.id != undefined"/>
    </CollapseContainer>
    </a-card>
  </PageWrapper>
  <PageWrapper title="版位与定向">
    <a-card>
      <CollapseContainer title = "投放位置">
        <div class = "div-style">
          <a-radio-group v-model:value="formData.deliveryRange.inventory_catalog" button-style="solid" :disabled="formData.id != undefined">
            <a-radio-button :value="JrttEnum.INVENTORYCATALOG.UNIVERSAL_SMART">通投智选</a-radio-button>
            <a-radio-button :value="JrttEnum.INVENTORYCATALOG.MANUAL">首选媒体</a-radio-button>
          </a-radio-group>
        </div>
      </CollapseContainer>
      <CollapseContainer title="媒体选择" v-if="formData.deliveryRange.inventory_catalog == JrttEnum.INVENTORYCATALOG.MANUAL" >
        <div style = "background-color：white; padding: 20px">
          <a-card>
            <div :style="{ borderBottom: '2px solid #E9E9E9' }">
              <a-checkbox
                  v-model:checked="state.checkAll"
                  :indeterminate="state.indeterminate"
                  @change="onCheckAllChange"
                  :disabled="formData.id != undefined"
                >
                  Check all
                </a-checkbox>
              </div>
              <br />
              <a-checkbox-group :disabled="formData.id != undefined" v-model:value="state.checkedList" :options="plainOptions" @change="onChangeVInventoryType"/>
          </a-card>
        </div>
      </CollapseContainer>
      <CollapseContainer title = "投放形式" v-if = "visibleUnionVideoType">
        <div class = "div-style">
          <a-radio-group v-model:value="formData.deliveryRange.union_video_type" button-style="solid">
            <a-radio-button :value="JrttEnum.UNIONVIDEOTYPE.REWARDED_VIDEO">激励视频</a-radio-button>
            <a-radio-button :value="JrttEnum.UNIONVIDEOTYPE.ORIGINAL_VIDEO">原生</a-radio-button>
            <a-radio-button :value="JrttEnum.UNIONVIDEOTYPE.SPLASH_VIDEO">开屏</a-radio-button>
          </a-radio-group>
        </div>
      </CollapseContainer>
      <CollapseContainer title="人群定向">
        <a-tabs>
          <a-tab-pane key="1" tab="新建定向"><AudienceTarget @receive="onReceive" :curValue="audience"/></a-tab-pane>
          <a-tab-pane key="2" tab="已有定向" force-render>
          <a-radio-group v-model:value="audiencePackageId">
            <a-radio-button v-for="item in audienceOptions" :value="item.audiencePackageId" class="audience-radio-button">{{item.name}}</a-radio-button>
          </a-radio-group>
        </a-tab-pane>
        </a-tabs>
      </CollapseContainer>
    </a-card>
  </PageWrapper>
  <PageWrapper title="排期与预算">
    <a-card>
      <CollapseContainer title = "投放时间">
        <div class = "div-style">
          <a-radio-group v-model:value = "formData.deliverySetting.schedule_type" button-style="solid"> 
            <a-radio-button :value="JrttEnum.SCHEDULETYPE.SCHEDULE_FROM_NOW">从今天起长期投放</a-radio-button>
            <a-radio-button :value="JrttEnum.SCHEDULETYPE.SCHEDULE_START_END">设开始时间和结束时间</a-radio-button>
          </a-radio-group>
        </div>
        <div class = "div-style" v-if = "formData.deliverySetting.schedule_type == JrttEnum.SCHEDULETYPE.SCHEDULE_START_END">
          <a-date-picker :disabled="formData.id != undefined" format="YYYY-MM-DD" value-format="YYYY-MM-DD" :disabled-date="disabledStartDate" v-model:value="formData.deliverySetting.start_time" placeholder="请选择开始时间"></a-date-picker> ~ <a-date-picker format="YYYY-MM-DD" value-format="YYYY-MM-DD" :disabled-date="disabledEndDate" v-model:value="formData.deliverySetting.end_time" placeholder="请选择结束时间"></a-date-picker>
        </div>
      </CollapseContainer>
      <CollapseContainer title = "投放时段">
        <a-radio-group v-model:value="noThing" button-style="solid" @change="onChangeTimeRange">
            <a-radio-button value="noLimit">不限</a-radio-button>
            <a-radio-button value="Limit">指定时间段</a-radio-button>
        </a-radio-group>
        <div class="byted-weektime" v-if="visibleTimeRange">
          <div class="calendar">
            <table class="calendar-table" style="width:610px">
              <thead class="calendar-head">
                <tr>
                  <th rowspan="6" class="week-td">星期/时间</th>
                  <th colspan="24">00:00 - 12:00</th>
                  <th colspan="24">12:00 - 24:00</th>
                </tr>
                <tr>
                  <td colspan="2" v-for="index in tableHeader">{{index}}</td>
                </tr>
              </thead>
              <tbody>
                <div id="kuang" :style="{width:kuangObj.width+'px',height:kuangObj.height+'px',top:kuangObj.top+'px',left:kuangObj.left+'px',bottom:kuangObj.bottom+'px',right:kuangObj.right+'px'}"></div>
                <tr>
                  <td>星期一</td>
                  <td @mousedown.prevent="handleMouseDown(i,0)" @mouseup.prevent="handleMouseUp(i,0)" class="calendar-atom-time" :class="item.class" v-for="(item,i) in rowUnit[0]"></td>
                </tr>
                <tr>
                  <td>星期二</td>
                  <td @mousedown.prevent="handleMouseDown(i,1)" @mouseup.prevent="handleMouseUp(i,1)" class="calendar-atom-time" :class="item.class" v-for="(item,i) in rowUnit[1]"></td>
              </tr>
              <tr>
                  <td>星期三</td>
                  <td @mousedown.prevent="handleMouseDown(i,2)" @mouseup.prevent="handleMouseUp(i,2)" class="calendar-atom-time" :class="item.class" v-for="(item,i) in rowUnit[2]"></td>
              </tr>
              <tr>
                  <td>星期四</td>
                  <td @mousedown.prevent="handleMouseDown(i,3)" @mouseup.prevent="handleMouseUp(i,3)" class="calendar-atom-time" :class="item.class" v-for="(item,i) in rowUnit[3]"></td>
              </tr>
              <tr>
                  <td>星期五</td>
                  <td @mousedown.prevent="handleMouseDown(i,4)" @mouseup.prevent="handleMouseUp(i,4)" class="calendar-atom-time" :class="item.class" v-for="(item,i) in rowUnit[4]"></td>
              </tr>
              <tr>
                  <td>星期六</td>
                  <td @mousedown.prevent="handleMouseDown(i,5)" @mouseup.prevent="handleMouseUp(i,5)" class="calendar-atom-time" :class="item.class" v-for="(item,i) in rowUnit[5]"></td>
              </tr>
              <tr>
                  <td>星期日</td>
                  <td @mousedown.prevent="handleMouseDown(i,6)" @mouseup.prevent="handleMouseUp(i,6)" class="calendar-atom-time" :class="item.class" v-for="(item,i) in rowUnit[6]"></td>
              </tr>
              <tr>
                  <td colspan="49" class="td-table-tip">
                      <div class="clearfix">
                          <span class="pull-left tip-text">请用鼠标点选时间段</span> <a @click="clear" class="pull-right"> 清空</a>
                      </div>
                  </td>
              </tr>
              <tr>
                <td colspan="49" class="timeContent">
                    <div v-for="(item,index) in timeStr" v-show="item.length">
                        <span>{{weekDate[index+1]}}: </span>
                        <strong><span>{{item}}</span></strong>
                    </div>
                </td>
              </tr>
              </tbody>
          </table>
          </div>
        </div>
        
      </CollapseContainer>
      <CollapseContainer title = "竞价策略">
        <div class = "div-style">
          <a-radio-group v-model:value="formData.deliverySetting.bid_type" @change="onChangeBidType" :disabled="formData.id != undefined">
              <a-radio-button :value="JrttEnum.BIDTYPE.CUSTOM" class = "radio-button">
                <div>
                  <div style="font-size:14px;">稳定成本</div>
                  <div style="font-size:12px;margin-top: 4px; color: darkgrey;">控制成本，尽量消耗完预算</div>
                </div>    
              </a-radio-button>
              <a-radio-button :value="JrttEnum.BIDTYPE.UPPER_CONTROL" class = "radio-button">
                <div>
                  <div style="font-size:14px;">控制成本上限</div>
                  <div style="font-size:12px;margin-top: 4px; color: darkgrey;">控制成本上限，尽量消耗预算</div>
                </div>    
              </a-radio-button>
              <a-radio-button :value="JrttEnum.BIDTYPE.NO_BID" class = "radio-button" disabled title="点击量/展示量场景不支持最大转化"> 
                <div>
                  <div style="font-size:14px;">最大转化</div>
                  <div style="font-size:12px;margin-top: 4px; color: darkgrey;">匀速花完预算，获取更多转化</div>
                </div>    
              </a-radio-button>
          </a-radio-group>
        </div>
        <div class="div-style" v-if = "formData.deliverySetting.bid_type == JrttEnum.BIDTYPE.CUSTOM">
          <div style = "color: #323335">
            投放速度
          </div>
          <a-radio-group button-style="solid" v-model:value = "formData.deliverySetting.bid_speed">
            <a-radio-button :value="JrttEnum.BIDSPEED.FAST">加速</a-radio-button>
            <a-radio-button :value="JrttEnum.BIDSPEED.BALANCE">匀速</a-radio-button>
          </a-radio-group>
        </div>
      </CollapseContainer>
      <CollapseContainer title = "项目预算">
        <div class="div-style">
          <a-radio-group button-style="solid" v-model:value="formData.deliverySetting.budget_mode"  @change="onChangeBudgetMode" :disabled="formData.id != undefined">
            <a-radio-button :value = 'JrttEnum.BUDGETMODE.BUDGET_MODE_INFINITE'>不限</a-radio-button>
            <a-radio-button :value = 'JrttEnum.BUDGETMODE.BUDGET_MODE_DAY'>设置预算</a-radio-button>
          </a-radio-group>
        </div>
        <div class="div-style" v-if = "visiableBudgetMode">
          <div style = "color: #323335">
            日预算
          </div>
          <div style="margin-top:4px">
            <a-input style="width:150px" placeholder="请输入" v-model:value="formData.deliverySetting.budget" @change="onChangePrice" :disabled = "formData.deliverySetting.budget_mode == JrttEnum.BUDGETMODE.BUDGET_MODE_INFINITE">
              <template #suffix>
                元
              </template>
            </a-input>
            <div style="color: crimson;">
              {{priceTip}}
            </div>
          </div>
        </div>
      </CollapseContainer>
      <CollapseContainer title = "付费方式">
        <a-radio-group button-style="solid" v-model:value="formData.deliverySetting.pricing" :disabled="formData.id != undefined">
          <a-radio-button :value = "JrttEnum.PRICINGTYPE.PRICING_CPC">按点击付费(CPC)</a-radio-button>
          <a-radio-button :value = "JrttEnum.PRICINGTYPE.PRICING_CPM">按展示付费(CPM)</a-radio-button>
          <a-radio-button :value = "JrttEnum.PRICINGTYPE.PRICING_OCPM">目标转化出价-按展示付费(oCPM)</a-radio-button>
          <a-radio-button :value = "JrttEnum.PRICINGTYPE.PRICING_OCPC">目标转化出价-按点击付费(PRICING_OCPC)</a-radio-button>
        </a-radio-group>
      </CollapseContainer>
    </a-card>
  </PageWrapper>
  <PageWrapper title = "搜索快投">
    <a-card>
      <div>
        出价系数
        <a-tooltip :title="radioTip" placement="right">
           <QuestionCircleFilled/>
        </a-tooltip>
      </div>
      <div>
        <a-input-number placeholder="请输入" v-model:value="formData.search_bid_ratio" :min="1" :max="2" :disabled = "bioRatioDisabled">
        </a-input-number>
      </div>
      <div class = "div-style">
        定向扩展
      </div>
      <div style = "margin-bottom: 6px;">
        <a-radio-group button-style="solid" v-model:value="formData.audience_extend">
          <a-radio-button :value="JrttEnum.AUDIENCEEXTEND.ON">启用</a-radio-button>
          <a-radio-button :value="JrttEnum.AUDIENCEEXTEND.OFF">不启用</a-radio-button>
        </a-radio-group>
      </div>
      <CollapseContainer title = "关键字">
      </CollapseContainer>
    </a-card>
  </PageWrapper>
  <PageWrapper title="基础信息">
    <a-card>
      <div>
        项目拼接名
      </div>
      <div style="margin-top:4px">
        <a-input placeholder="请输入要拼接的文字" style="width:350px" v-model:value="formData.name">
          <template #suffix>
            <a-tooltip title="项目名：广告名+项目拼接名" placement="right">
              <QuestionCircleFilled/>
            </a-tooltip>
          </template>
        </a-input>
      </div>
      <div class ="div-style">
        开启投放
      </div>
      <div>
        <a-switch :checkedValue="JrttEnum.OPERATIONENUM.ENABLE" :unCheckedValue="JrttEnum.OPERATIONENUM.DISABLE" v-model:checked="formData.operation"></a-switch>
      </div>
    </a-card>
  </PageWrapper>
  <template>
    <div>
      <a-modal v-model:visible="modelVisiable" @ok="handleOk" title = "创建信息">
        <div>
          <p v-for="(item, index) in returnMessage">
            {{"广告：" + item.dealId + ",创建结果：" + item.msg}}
          </p>
        </div>
      </a-modal>
    </div>
  </template>
  <template>
    <div>
      <a-modal v-model:visible="updateModelVisiable" @ok="updateHandleOk" title = "修改结果">
        <div>
          <p v-for="(item, index) in updateReturnMessage.errorList">
            {{"修改结果：" + item.errorMessage}}
          </p>
        </div>
      </a-modal>
    </div>
  </template>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted, watch, } from 'vue';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { BasicForm, FormSchema, useForm } from '/@/components/Form/index';
  import { getValueType } from '/@/utils';
  import { batchCreateProject,editProject, getExternalAction, localAudienceList} from '../JrttProject.api';
  import * as JrttEnum from '../JrttEnum';
  import { Form, message } from 'ant-design-vue';
  import { PageWrapper } from '/@/components/Page';
  import { CollapseContainer } from '/@/components/Container/index';
  import {BulbFilled, QuestionCircleFilled} from '@ant-design/icons-vue';
  import moment from "moment"
  import '@bytedance-ad/mui-vue3/dist/style.css';
  import { setConfig, AudienceTarget } from '@bytedance-ad/mui-vue3';
  import {getToken} from '/@/utils/auth';

  const audience:any = ref({});
  const audiencePackageId = ref();
  const onReceive = (value) => {
    // 不可缺少这一步
    audience.value = value
  };
  watch(audiencePackageId,(val) =>{
    audience.value.audience_package_id = val;
  })
  watch(audience,(val) =>{
    formData.audience = val;
    console.log(formData.audience);
  })
  const tableHeader = ['00','01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23'];
  const weekDate = {'1':'星期一','2':'星期二','3':'星期三','4':'星期四','5':'星期五','6':'星期六','7':'星期日'};

  const eventOptions = ref([])
  const state = reactive({
      indeterminate: true,
      checkAll: false,
      checkedList: JrttEnum.checkedList
  });
  const visibleInventoryType = ref<boolean>(false);
  const visibleUnionVideoType = ref<boolean>(false);
  const visibleSchedule = ref<boolean>(false);
  const visibleTimeRange = ref<boolean>(false);
  const visiableBudgetMode = ref<boolean>(true);
  const visiablePricing = ref<boolean>(false);
  const bioRatioDisabled = ref<boolean>(true);
  const modelVisiable = ref<boolean>(false);
  const updateModelVisiable = ref<boolean>(false);
  const noThing = ref<string>('noLimit');
  const plainOptions = [
    {
      label: '今日头条',
      value: 'INVENTORY_FEED',
    },
    {
      label: '西瓜视频',
      value: 'INVENTORY_VIDEO_FEED',
    },
    {
      label: '抖音短视频',
      value: 'INVENTORY_AWEME_FEED',
    },
    {
      label: '番茄小说',
      value: 'INVENTORY_TOMATO_NOVEL',
    },
    {
      label: '穿山甲',
      value: 'INVENTORY_UNION_SLOT',
    },
    {
      label: 'ohayoo精品游戏',
      value: 'UNION_BOUTIQUE_GAME',
    },
  ];
  let kuangObj = {
    width:0,
    height:0,
    top:0,
    left:0,
    bottom:0,
    right:0,
    oldLeft:0,
    oldTop:0,
    flag:false
  }
  const tip = "投放位置为站内平台（巨量引擎关联方流量网络平台，如抖音、今日头条、西瓜视频、番茄小说等）视频广告位的oCPM广告，下发“3秒视频播放”和“点击”；投放位置为穿山甲的视频广告位（如：激励视频）的oCPM广告，下发“10~20秒视频播放”和“点击”；其他非视频广告位的广告，仍仅下发“点击”"
  const radioTip = "设置出价系数后在搜索场景下会以目标转化出价*系数参与竞价，可以提高广告竞争胜出概获取更多转化。出价系数设置范围1-2"
  let priceTip = ref<string>('');
  const appPromotionTypetip = ref<string>('吸引用户下载并安装您的应用程序');
  let returnMessage:any = ref([]);
  let updateReturnMessage:any = ref([]);
  let deliverySetting = {
      schedule_type: 'SCHEDULE_FROM_NOW',
      start_time: undefined,
      end_time: undefined,
      schedule_time:undefined,
      bid_type:'CUSTOM',
      deep_bid_type:undefined,
      bid_speed:'FAST',
      budget_mode:'BUDGET_MODE_DAY',
      budget:undefined,
      pricing: JrttEnum.PRICINGTYPE.PRICING_OCPM,
      budget_optimize_switch:undefined
  };
  let optimizeGoal ={
    asset_ids: undefined,
    external_action: undefined,
    deep_external_action: undefined
  }

  let deliveryRange = {
    inventory_catalog: JrttEnum.INVENTORYCATALOG.UNIVERSAL_SMART,
    inventory_type: state.checkedList,
    union_video_type: JrttEnum.UNIONVIDEOTYPE.REWARDED_VIDEO
  }
  const formData = reactive<Record<string, any>>({
    id: undefined,
    dealId : undefined,
    operation: JrttEnum.OPERATIONENUM.ENABLE,
    advertiserId: undefined,
    projectId: '',
    landingType: JrttEnum.LANDINGTYPE.APP,
    appPromotionType: JrttEnum.APPPROMOTIONTYPE.DOWNLOAD,
    marketingGoal: JrttEnum.MARKETINGGOAL.VIDEO_AND_IMAGE,
    adType: JrttEnum.ADTYPE.ALL,
    name: undefined,
    searchBidRatio: undefined,
    audienceExtend: undefined,
    keywords: undefined,
    search_bid_ratio:undefined,
    audience_extend: JrttEnum.AUDIENCEEXTEND.ON,
    downloadType: JrttEnum.DOWNLOADTYPE.DOWNLOAD_URL,
    downloadMode: JrttEnum.DOWNLOADMODE.DEFAULT,
    optimizeGoal: optimizeGoal,
    deliveryRange: deliveryRange,
    audience: undefined,
    deliverySetting: deliverySetting,
  });
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: ()=>{} },
    formBpm: { type: Boolean, default: true }
  });
  const formRef = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  let rowUnit:any = [];
  let timeContent:any = [];
  let timeSection:any = [];
  let timeStr:any = ref([])
  let downEvent = ref(false);
  let beginDay = ref(0);
  let beginTime = ref(0);
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {};
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: true });
  let deals:any = [];
  const audienceOptions = ref([]);

  watch(() => formData.optimizeGoal.external_action,(val) =>{
    if(val == 'AD_CONVERT_TYPE_SHOW_OFF_NUM'){
      formData.deliverySetting.pricing = 'PRICING_CPM'
    }else if(val == 'AD_CONVERT_TYPE_CLICK_NUM'){
      formData.deliverySetting.pricing = 'PRICING_CPC'
    }else{
      formData.deliverySetting.pricing = 'PRICING_OCPM'
    }
  })

    // 表单禁用
  const disabled = computed(()=>{
    if(props.formBpm === true){
      if(props.formData.disabled === false){
        return false;
      }else{
        return true;
      }
    }
    return props.formDisabled;
  });

  function disabledStartDate(current){
    const time = moment().endOf('day').subtract(1, 'days')
    return current && current < time
  }
  function disabledEndDate(current){
    return current < formData.deliverySetting.start_time
  }
  function handleOk(){
    modelVisiable.value = false;
    returnMessage.value = []
  }
  function updateHandleOk() {
    updateReturnMessage.value = [];
    updateModelVisiable.value = false;
  }
  //出价系数是否可输入，修改bid_type或者deep_bid_type时调用
  function changeBioRatioDisabled(){
    let bidType = formData.deliverySetting.bid_type;
    let pricing = formData.deliverySetting.pricing;
    let deepBiyType = formData.deliverySetting.deep_bid_type;
    if(bidType != JrttEnum.BIDTYPE.NO_BID && pricing == JrttEnum.PRICINGTYPE.PRICING_OCPM 
    && deepBiyType == JrttEnum.DEEPBIDTYPE.DEEP_BID_DEFAULT || deepBiyType == JrttEnum.DEEPBIDTYPE.BID_PER_ACTION){
      bioRatioDisabled.value = true;
    }else{
      bioRatioDisabled.value = false;
    }
  }
  function onChangePrice(e){
    let value = e.target.value - 0;
    if(value < 100 || value > 9999999.99){
      priceTip.value = "预算范围，不少于100元，不超过9999999.99元"
    }else{
      priceTip.value = ""
    }
  }
  function onChangeEvent(value){
    if(value == JrttEnum.OPTIMIZEGOALENUM.AD_CONVERT_TYPE_CLICK_NUM){
      visiablePricing.value = true;
      formData.deliverySetting.pricing = JrttEnum.PRICINGTYPE.PRICING_CPC
    }else{
      visiablePricing.value = false;
      formData.deliverySetting.pricing = JrttEnum.PRICINGTYPE.PRICING_CPM
    }
  }
  function onChangeBidType(e:any){
    changeBioRatioDisabled();
  }
  function onChangeBudgetMode(e:any){
    if(e.target.value == 'BUDGET_MODE_DAY'){
      visiableBudgetMode.value = true;
    }else {
      visiableBudgetMode.value = false;
    }
  }
  function onChangeTimeRange(e:any){
    if(e.target.value == 'Limit'){
      visibleTimeRange.value = true;
    }else {
      visibleTimeRange.value = false;
    }
  }
  function clear() {
    rowUnit.forEach((item)=>{
        item.forEach((item1)=>{
            item1.class=null
        })
    })
    timeContent.forEach((item)=>{
        item.arr = []
    })
    timeSection.forEach((item)=>{
        //赋值成空数组[]出问题
        item.length = 0
    })
    timeStr.value.length = 0
    for (let i = 0; i < 7; i++) {
      timeStr.value.push('')
    }
  }
  function init(){
    for (let i = 0; i < 7; i++) {
        let arr:any = []
        for (let j = 0; j < 48; j++) {
            arr.push({class:null,timeData:j})
        }
        rowUnit.push(arr)
        timeContent.push({arr:[]})
        timeSection.push([])
        timeStr.value.push('')
    }
  }
  init();
  function handleMouseDown(i, day) {
    downEvent.value = true;
    beginDay.value = day
    beginTime.value = i;
  }
  function handleMouseUp(i,day) {
    if(downEvent.value){
        let begin = beginTime.value;
        let start = begin <= i ? begin : i;
        let length = Math.abs(begin - i);
        let end = start + length;
        let dayStart = beginDay.value <= day ? beginDay.value : day
        let dayLength = Math.abs(beginDay.value - day);
        let dayEnd = dayStart + dayLength;

        function isAdd(){
          for(let x = dayStart; x < dayEnd + 1; x++){
            for(let y = start; y < end + 1; y++){
              if(rowUnit[x][y].class == null) return true;
            }
          }
          return false;
        }
        if(isAdd()){
          for (let x = dayStart; x < dayEnd+1; x++) {
              for (let y = start; y < end+1; y++) {
                  if(rowUnit[x][y].class == null) {
                      rowUnit[x][y].class = 'ui-selected'
                      timeContent[x].arr.push(rowUnit[x][y].timeData)
                  }
              }
          }
        }else{
          for (let x = dayStart; x < dayEnd+1; x++) {
            for (let y = start; y < end+1; y++) {
                rowUnit[x][y].class = null
                timeContent[x].arr.splice(timeContent[x].arr.indexOf(timeContent[x].arr))
            }
        }
        }
        filterTime(dayStart,dayEnd);
    }
    downEvent.value = false;
  }
  function filterTime(start,end){
    function sortCut(arr) {  //提取连续的数字
        var result:any = []
        arr.forEach(function (v:any, i) {
            var temp = result[result.length - 1];
            if (!i) {
                result.push([v]);
            } else if (v % 1 === 0 && v - temp[temp.length - 1] == 1) {
                temp.push(v)
            } else {
                result.push([v])
            }
        });
        return result
    }
    function toStr(num) {
        if (Number.isInteger(num)) {
            let str = num<10 ? ('0'+num) : num.toString()
            return str+':00'
        }else{
            let str =Math.floor(num)<10 ? ('0'+Math.floor(num)) : Math.floor(num).toString()
            return str+':30'
        }
    }
    function timeToStr(arr) {  //把数组转成方便人看到字符串
        let str = ''
        arr.forEach((arr,index)=>{
            let str1 = ''
            if (index == 0) {
                str1 = toStr(arr[0]) + '~' + toStr(arr[1])
            }else{
                str1 = ' , ' + toStr(arr[0]) + '~' + toStr(arr[1])
            }
            str += str1
        })
        return str
    }
    let timeStr1 = timeStr.value;
    //排序,分割成
    for (let i = start; i < end+1; i++) {
      let arr1 = sortCut(timeContent[i].arr.sort((a, b) => a - b))
      let arr2:any = []
      arr1.forEach((arr)=>{   //转成带小数点的时间段,以及供前端显示的字符串
          let arr3:any = []
          arr3.push(arr[0]/2)
          arr3.push(arr[arr.length-1]/2+0.5)
          arr2.push(arr3)
      })
      //console.log(arr2)
      timeStr1[i] = timeToStr(arr2)
      timeSection[i] = arr2
    }
  }
  function onCheckAllChange(e: any){
    Object.assign(state, {
      checkedList: e.target.checked ? JrttEnum.checkedList : [],
      indeterminate: false,
    });
    formData.deliveryRange.inventory_type = state.checkedList
  }

  function onChangeVInventoryType(values){
    //有且只有穿山甲被选中
    if(values.length == 1 && values[0] == 'INVENTORY_UNION_SLOT'){
      visibleUnionVideoType.value = true
    }else{
      visibleUnionVideoType.value = false
    }
    formData.deliveryRange.inventory_type = values
  }
  /**
   * 新增
   */
  function batchCreateObject(params) {
    deals = params;
    //初始化优化目标选项
    getExternalAction(deals[0]).then(res =>{
      eventOptions.value = res;
      formData.optimizeGoal.asset_ids = res[0].assetIds
    })
    initAudience(deals[0].accountId);
    audience.value = {};
    //获取已有定向包
    localAudienceList(deals[0].accountId).then(res =>{
      audienceOptions.value = res;
    })
    edit(undefined,{});
  }

  function initAudience(accountId){
    setConfig({
      host: window._CONFIG['domianURL'],
      headers:{'X-Access-Token': getToken()},
      apis: {
            getCustomCrowdList: '/advert/opJrttAudience/getCustomAudienceList',
            getDistrictList: '/advert/opJrttAudience/getCountryInfoList',
            getAwemeFanCategories: '/advert/opJrttAudience/getAwemeFanCategories',
            getAwemeAuthorInfo: '/advert/opJrttAudience/getAwemeAuthorInfo',
            getAwemeSearchInfo: '/advert/opJrttAudience/getAwemeSearchInfo',
            getAwemeFanAccounts: '/advert/opJrttAudience/getAwemeFanAccounts',
            getAwemeSimilarAccounts: '/advert/opJrttAudience/getAwemeSimilarAccounts',
            getActionCategories: '/advert/opJrttAudience/category',
            getInterestActionInfoByIs: '/advert/opJrttAudience/interestActionInfoByIs',
            getActionKeywords: '/advert/opJrttAudience/keyword',
            getActionInterestKeywordSuggest: '/advert/opJrttAudience/ActionInterestKeywordSuggest',
            getInterestCategories: '/advert/opJrttAudience/category',
            getInterestKeywords: '/advert/opJrttAudience/keyword'
      },
      params:{accountId: accountId}
    })
  }

  /**
   * 编辑
   */
  function edit(accountId, record) {
    nextTick(() => {
      resetFields();
      if(accountId != undefined){
        initAudience(accountId)
        //获取已有定向包
        localAudienceList(accountId).then(res =>{
          audienceOptions.value = res;
        })
        audience.value = record.audience;
      }
      //赋值
      if(record.id == undefined){
        visibleSchedule.value = false;
        visibleInventoryType.value = false;
        visibleTimeRange.value = false;
        noThing.value = "noLimit"
        visiableBudgetMode.value = true;
      }
      Object.assign(formData, record);
    });
  }


  /**
   * 提交数据
   */
  async function submitForm() {
    // 触发表单验证
    // await validate();
    confirmLoading.value = true;
    const isUpdate = ref<boolean>(false);
    //时间格式化
    let model = formData;
    let action = model.optimizeGoal.external_action;
    if(action == undefined || action == ''){
      createMessage.warn("请选择优化目标");
      return;
    }
    let catalog = formData.deliveryRange.inventory_catalog;
    let inventoryType = model.deliveryRange.inventory_type;
    if(catalog == JrttEnum.INVENTORYCATALOG.MANUAL && state.checkedList.length == 0){
      createMessage.warn("请选择媒体")
      return;
    }
    if(!(catalog == JrttEnum.INVENTORYCATALOG.MANUAL && inventoryType.length == 1 && inventoryType[0] == 'INVENTORY_UNION_SLOT')){
      model.deliveryRange.union_video_type = undefined
    }
    if(catalog ==  JrttEnum.INVENTORYCATALOG.UNIVERSAL_SMART){
      model.deliveryRange.inventory_type = undefined
    }


    let deliverySetting = model.deliverySetting
    if(deliverySetting.schedule_type == JrttEnum.SCHEDULETYPE.SCHEDULE_START_END && (deliverySetting.start_time == undefined || deliverySetting.end_time == undefined)) {
      createMessage.warn("请输入投放开始时间和结束时间！");
      return;
    }
    let startTime = new Date(deliverySetting.start_time);
    let endTime = new Date(deliverySetting.end_time);
    if(startTime > endTime) {
      createMessage.warn("结束时间必须大于开始时间");
      return;
    }
    let timerange = visibleTimeRange.value;
    if(timerange && timeStr.value.length == 0){
      createMessage.warn("请选择投放时段");
      return;
    }
    let budget = model.deliverySetting.budget;
    let mode = model.deliverySetting.budget_mode;
    if(mode == 'BUDGET_MODE_DAY' && (budget == undefined || budget == '')){
      createMessage.warn("日预算不能为空");
      return;
    }else{
      budget = budget - 0;
      if(budget < 100 || budget > 9999999.99){
        createMessage.warn("日预算值不合法");
        return;
      }
    }
    if(model.name == undefined || model.name == ''){
      createMessage.warn("拼接名不能为空")
      return;
    }
    if(deliverySetting.schedule_type != 'SCHEDULE_FROM_NOW'){
      let schedule_time = '';
      rowUnit.forEach( item => {
        item.forEach(item1 =>{
          if(item1.class == 'ui-selected'){
            schedule_time += '1'
          }else{
            schedule_time += '0'
          }
        })
      });
      model.deliverySetting.schedule_time = schedule_time;
    }
    if (model.id) {
      isUpdate.value = true;
    }
    //循环数据
    for (let data in model) {
      //如果该数据是数组并且是字符串类型
      if (model[data] instanceof Array) {
        let valueType = getValueType(formRef.value.getProps, data);
        //如果是字符串类型的需要变成以逗号分割的字符串
        if (valueType === 'string') {
          model[data] = model[data].join(',');
        }
      }else if(model[data] instanceof Object){
        model[data] = JSON.stringify(model[data]);
      }
    }
    let dealIds:any = []
    deals.forEach(item =>{
      dealIds.push(item.id)
    })
    if(!isUpdate.value){
      await batchCreateProject(dealIds, model)
      .then((res) => {
        res.result.forEach(item =>{
          returnMessage.value.push({dealId: item.dealId, msg: item.message})
          modelVisiable.value = true;
        })
        emit('ok');
      })
      .finally(() => {
        confirmLoading.value = false;
      });
    }else{
      await editProject(model).then((res) =>{
        if(res.errorList == null){
          message.success("修改成功")
        }else{
          updateReturnMessage.value = res.errorList
          updateModelVisiable.value = true;
        }
        emit('ok');
      })
      .finally(() =>{
        confirmLoading.value = false;
      })
    }
  }

  defineExpose({
    batchCreateObject,
    edit,
    submitForm,
  });
</script>

<style lang="less" scoped>
  .antd-modal-form {
    min-height: 500px !important;
    overflow-y: auto;
    padding: 24px 24px 24px 24px;
  };
  .div-style {
    margin-top: 16px;
  }
  .radio-button {
    width: 208px;
    height: 70px;
    margin-left: 5px;
  }
  .week-td{width:75px;padding:20px 0}
  .byted-weektime.calendar{
    -webkit-user-select:none;
    position:relative;
    display:inline-block;
    margin-top: 6px;
  }
  .calendar-atom-time:hover{background:#ccc}
  .td-table-tip{line-height:2.4em;padding:0 12px 0 19px;background:#fff !important}
  .td-table-tip .clearfix{height:46px;line-height:46px}
  .td-table-tip .pull-left{font-size:14px;color:#333333}
  .ui-selected{background:#2F88FF}
  .ui-selected:hover{background:#2F88FF}
  .table,th,td{
    line-height: 1.15;
    -webkit-text-size-adjust: 100%;
    -webkit-font-smoothing: antialiased;
    --primary-color: #338aff;
    box-sizing: border-box;
    user-select: none;
    border: 1px solid #eeeef5;
    border-radius: 4px;
    overflow: hidden;
    background-color: #fff;
  }
  .audience-radio-button {
    width: auto;
    height: auto;
    margin-left: 5px;
    margin-top: 10px;
  }
  #kuang{position: absolute;background-color: blue;opacity: 0.3;border:1px}
</style>
