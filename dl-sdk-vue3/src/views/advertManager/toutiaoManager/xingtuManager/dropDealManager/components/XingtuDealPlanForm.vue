<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <PageWrapper title="基础信息">
        <div class="pdiv" >
          <div v-if="formData.id == undefined">
            <div class = "subtitle">创建数量</div>
            <div>
              <a-form-item>
                <a-input-number placeholder="请输入创建数量" v-model:value="createNum" :min="1"></a-input-number>
              </a-form-item>
            </div>
          </div>
          <div class="last-div">
            <div class = "subtitle">{{!formData.id?'计划拼接名':'计划名'}}</div>
            <div>
                <a-input :placeholder="disabled?'请输入计划拼接名':'请输入计划名'" v-model:value="formData.name" class="input"> </a-input>
            </div>
          </div>
        </div>
    </PageWrapper>
    <PageWrapper title="优化目标">
      <div class="pdiv">
        <div>
          <div class="subtitle">投放类型</div>
          <div>
            <a-radio-group v-model:value="deliverType" :disabled="formData.id != undefined">
              <a-radio-button value = "LIVE_CONVERSION" disabled>直播间转化</a-radio-button>
              <a-radio-button value = "APPLICATION_EXTENSION">应用推广</a-radio-button>
              <a-radio-button value = "" disabled>线索搜集</a-radio-button>
            </a-radio-group>
          </div>
        </div>
        <div>
          <div class="subtitle">优化目标</div>
          <div>
              <a-select placeholder="请选择优化目标" v-model:value="optimizeGoal.external_action" :options="assetsOptions" style="width:350px" :disabled="formData.id != undefined"></a-select>
          </div>
          <div v-if="assetsOptions == null" style="color:red">*请先为该账号创建事件资产</div>
        </div>
        <div class="last-div" v-if="optimizeGoal.external_action == 'AD_CONVERT_TYPE_PAY'">
          <div class="subtitle">深度优化目标</div>
          <div>
              <a-select placeholder="请选择优化目标" v-model:value="optimizeGoal.deep_external_action" style="width:350px" :disabled="formData.id != undefined">
                <a-select-option value="AD_CONVERT_TYPE_PURCHASE_ROI">付费ROI</a-select-option>
              </a-select>
          </div>
        </div>
      </div>
    </PageWrapper>
    <PageWrapper title="投放位置">
      <div class="pdiv">
        <div>
          <div class="subtitle">投放大类</div>
          <div>
            <a-radio-group v-model:value="deliveryRange.inventory_catalog" :disabled="formData.id != undefined">
              <a-radio-button value = "MANUAL">首选媒体</a-radio-button>
            </a-radio-group>
          </div>
        </div>
        <div class="last-div">
          <div class="subtitle">投放位置</div>
          <div>
            <a-checkbox-group v-model:value="deliveryRange.inventory_type" :options="options" :disabled="formData.id != undefined"/>
          </div>
        </div>
      </div>
    </PageWrapper>
    <PageWrapper title="用户定向">
      <a-tabs>
        <a-tab-pane key="1" tab="新建定向"><AudienceTarget @receive="onReceiveAudience" :curValue="audience"/></a-tab-pane>
        <a-tab-pane key="2" tab="选择已有定向">
          <a-radio-group v-model:value="audienceInfo.audience_package_id">
            <a-radio-button class="audience-radio-button" v-for="(item,index) in audienceOptions" :value="item.audiencePackageId">
              <div>
                <div>
                  {{item.name}}
                </div>
                <div>
                  {{item.description}}
                </div>
              </div>
            </a-radio-button>
          </a-radio-group>
        </a-tab-pane>
      </a-tabs>
    </PageWrapper>
    <PageWrapper title="预算与出价">
      <div class="pdiv">
        <div class="subtitle">投放场景</div>
        <div>
          <a-radio-group v-model:value="deliverySetting.smart_bid_type" :disabled="formData.id != undefined">
            <a-radio-button value="SMART_BID_CUSTOM" class = "radio-button">
              <div>
                  <div style="font-size:14px;">常规投放</div>
                  <div style="font-size:12px;margin-top: 4px; color: darkgrey;">控制成本，尽量消耗预算</div>
                </div> 
            </a-radio-button>
            <a-radio-button value="SMART_BID_CONSERVATIVE" class = "radio-button">
              <div>
                  <div style="font-size:14px;">放量投放</div>
                  <div style="font-size:12px;margin-top: 4px; color: darkgrey;">接受成本上浮，消耗更多预算</div>
                </div> 
            </a-radio-button>
            <a-radio-button value="SMART_BID_NO_BID" class = "radio-button">
              <div>
                  <div style="font-size:14px;">最大转化投放</div>
                  <div style="font-size:12px;margin-top: 4px; color: darkgrey;">匀速花完预算，获取更多转化</div>
                </div> 
            </a-radio-button>
          </a-radio-group>
        </div>
        <div v-if="deliverySetting.smart_bid_type == 'SMART_BID_CUSTOM'">
          <div class="subtitle">竞价策略</div>
          <div>
            <a-radio-group v-model:value="deliverySetting.flow_control_mode">
              <a-radio-button value="FLOW_CONTROL_MODE_FAST">优先跑量</a-radio-button>
              <a-radio-button value="FLOW_CONTROL_MODE_SMOOTH">优先低成本</a-radio-button>
              <a-radio-button value="FLOW_CONTROL_MODE_BALANCE" :disabled = "optimizeGoal.deep_external_action != ''" :title="optimizeGoal.deep_external_action != ''?'':tip3">均衡投放</a-radio-button>
            </a-radio-group>
          </div>
        </div>
        <div>
          <div class="subtitle">投放时间</div>
          <div>
            <a-radio-group v-model:value="deliverySetting.schedule_type">
              <a-radio-button value = 'SCHEDULE_FROM_NOW'>从今日起长期投放</a-radio-button>
              <a-radio-button value = 'SCHEDULE_START_END'>设置开始时间和结束时间</a-radio-button>
            </a-radio-group>
          </div>
          <div style="margin-top:10px" v-if="deliverySetting.schedule_type == 'SCHEDULE_START_END'">
            <a-date-picker format="YYYY-MM-DD" value-format="YYYY-MM-DD" :disabled-date="disabledStartDate" v-model:value="deliverySetting.start_time" placeholder="请选择开始时间" :disabled="formData.id != undefined"></a-date-picker> ~ <a-date-picker format="YYYY-MM-DD" value-format="YYYY-MM-DD" :disabled-date="disabledEndDate" v-model:value="deliverySetting.end_time" placeholder="请选择结束时间"></a-date-picker>
          </div>
        </div>
        <div>
          <div class="subtitle">投放时段</div>
          <div>
            <a-radio-group v-model:value="scheduleTimeType">
              <a-radio-button :value="false">不限</a-radio-button>
              <a-radio-button :value="true">指定时间</a-radio-button>
            </a-radio-group>
          </div>
          <div style="margin-top:10px" v-if="scheduleTimeType">
            <MSchedule @receive="onReceiveSchedule" :curValue="scheduleTime" />
          </div>
        </div>
        <div>
          <div class="subtitle">预算类型</div>
          <div>
            <a-radio-group v-model:value="deliverySetting.budget_mode" :disabled="formData.id != undefined">
              <a-radio-button value="BUDGET_MODE_INFINITE">不限</a-radio-button>
              <a-radio-button value="BUDGET_MODE_DAY">日预算</a-radio-button>
              <a-radio-button value="BUDGET_MODE_TOTAL">总预算</a-radio-button>
            </a-radio-group>
          </div>
        </div>
        <div v-if="deliverySetting.budget_mode != 'BUDGET_MODE_INFINITE'">
          <div class="subtitle">预算</div>
          <div>
            <a-input style="width:150px" placeholder="请输入" v-model:value="deliverySetting.budget">
              <template #suffix>
                元
              </template>
            </a-input>
          </div>
          <div style="margin-top=10px; color: red;">
            {{tip2}}
          </div>
        </div>
        <div v-if="deliverySetting.flow_control_mode == 'FLOW_CONTROL_MODE_FAST' && deliverySetting.smart_bid_type == 'SMART_BID_CUSTOM'">
          <div class="subtitle">付费方式</div>
          <div>
            <a-radio-group v-model:value="deliverySetting.pricing">
              <a-radio-button value="PRICING_OCPM">按展示付费（oCPM）</a-radio-button>
            </a-radio-group>
          </div>
        </div>
        <div  v-if="deliverySetting.smart_bid_type != 'SMART_BID_NO_BID'">
          <div class="subtitle">目标转化出价<a-tooltip :title="tip" placement="topLeft"><QuestionCircleFilled/></a-tooltip></div>
          <div>
            <a-input style="width:150px" placeholder="请输入" v-model:value="deliverySetting.cpa_bid">
              <template #suffix>
                元
              </template>
            </a-input>
          </div>
        </div>
        <div class="last-div" v-if="optimizeGoal.external_action == 'AD_CONVERT_TYPE_PAY' && optimizeGoal.deep_external_action == 'AD_CONVERT_TYPE_PURCHASE_ROI'">
          <div class="subtitle">深度优化方式</div>
          <div>
            <a-radio-group v-model:value="deliverySetting.deep_bid_type">
              <a-radio-button value="ROI_PACING">自动优化</a-radio-button>
            </a-radio-group>
          </div>
        </div>
      </div>
    </PageWrapper>
    <PageWrapper title="搜索快投">
      <div class="pdiv">
        <div>
          <div class="subtitle">关键字</div>
          <div><!--todo-->
            
          </div>
        </div>
        <div v-if="optimizeGoal.deep_external_action == undefined || optimizeGoal.deep_external_action == ''">
          <div class="subtitle">出价系数</div>
          <div>
            <a-input-number v-model:value="deliverySearch.search_bid_ratio" style="width:125px" :min="1" :max="2"></a-input-number>
          </div>
        </div>
        <div class="last-div">
          <div class="subtitle">定向扩展</div>
          <div>
            <a-radio-group v-model:value = "deliverySearch.audience_extend">
              <a-radio-button value="OFF">不启用</a-radio-button>
              <a-radio-button value="ON">启用</a-radio-button>
            </a-radio-group>
          </div>
        </div>
      </div>
    </PageWrapper>
    </a-form>
        <!-- 创建站点信息框 -->
        <a-modal v-model:visible="createVisible" title="创建站点进度" :maskClosable="false" :destroyOnClose="true">
      <div style="padding: 20px; font-size: 16px">
        <div>
          <span>开始为</span>
          <span class="span-font-normal">&nbsp;{{ createDealList.length }}&nbsp;</span>
          <span>条广告组创建广告计划</span>
          <span class="span-font-normal">&nbsp;{{ chooseSiteName }}&nbsp;</span>
        </div>
        <a-row :gutter="16">
          <a-col :span="8">
            <span>当前进度</span>
            <span class="span-font-normal">&nbsp;{{ dealProcess }} / {{ createDealList.length }}</span>
          </a-col>
          <a-col :span="8">
            <span>成功</span>
            <span class="span-font-success">&nbsp;{{ createSuccess }}</span>
          </a-col>
          <a-col :span="8">
            <span>失败</span>
            <span class="span-font-error">&nbsp;{{ createFail }}</span>
          </a-col>
        </a-row>
        <!-- 进度条 -->
        <Progress :percent="dealProcessPer" />
        <!-- 时间线 -->
        <div class="time-line">
          <Timeline>
            <TimelineItem :color="rsp.code == 0 ? 'green' : 'red'" v-for="rsp in timelineDataList">
              广告&nbsp;{{ rsp.dealId }} &nbsp;{{rsp.dealName}} -- {{ rsp.code == 0 ? '创建成功' : '创建失败, ' + rsp.message}}
            </TimelineItem>
          </Timeline>
        </div>
      </div>
      <template #footer>
        <a-button key="back" @click="handleCreateCancel">关闭</a-button>
      </template>
    </a-modal>
  </a-spin>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted, watch } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { getValueType } from '/@/utils';
  import {  assetList, audienceList, batchCreatePlanReq } from '../dropDeal.api';
  import { editPlan } from '../XingtuDealPlan.api';
  import { Form, message } from 'ant-design-vue';
  import { PageWrapper } from '/@/components/Page';
  import { AudienceTarget, setConfig } from '@bytedance-ad/mui-vue3';
  import '@bytedance-ad/mui-vue3/dist/style.css';
  import {getToken} from '/@/utils/auth';
  import moment from "moment"
  import { QuestionCircleFilled} from '@ant-design/icons-vue';
  import { MSchedule } from '@bytedance-ad/mui-vue3'
  import Timeline from 'ant-design-vue/lib/timeline'; // 加载 JS
  import Progress from 'ant-design-vue/lib/progress'; // 加载 JS
  import TimelineItem from 'ant-design-vue/lib/timeline/TimelineItem'; // 加载 JS


  const options = [
    {
      label:'抖音',
      value: 'INVENTORY_AWEME_FEED'
    },
    {
      label:'穿山甲',
      value: 'INVENTORY_UNION_SLOT'
    }
  ]
  //已有定向包选项
  let audienceOptions:any = ref([]);
  //事件资产选项
  let assetsOptions:any = ref([]);
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: ()=>{} },
    formBpm: { type: Boolean, default: true }
  });
  const formRef = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const deliverType = ref('APPLICATION_EXTENSION')
  const scheduleTimeType = ref<Boolean>(false);
  const tip = "目标转化价格是指您愿意为每次转化支付的目标价格。价格的高低会影响您获得的转化次数。例如，如果设置的目标价格过低，您可能会错失一部分转化。目标转化价格仅作为智能优化投放的成本参考，实际仍按照展示付费"
  const tip2 = ref("");
  const tip3 = ref("您所选的转化目标包括深度转化，暂不支持控制成本上限竞价策略，若有控制成本上限诉求，请更换转化目标。");
  let dealIds:any = [];
  const createNum = ref(1);
  const createVisible = ref(false);
  const createDealList = ref<Array>([]);
  const dealProcess = ref<number>(0);
  const dealProcessPer = ref<number>(0);
  const createSuccess = ref<number>(0);
  const createFail = ref<number>(0);
  const accountIdParam = ref<number>(0);
  const chooseSiteName = ref<string>('');
  // 时间线
  const timelineDataList = ref<Array>([]);
  //搜索快投
  const deliverySearch = reactive<Record<string, any>>({
    feed_delivery_search:undefined,
    search_bid_ratio: undefined,
    audience_extend: 'ON'
  })
  //用户定向
  const audienceInfo = reactive<Record<string, any>>({
    audience_package_id:undefined,
    audience: undefined
  })
  //优化目标
  const optimizeGoal = reactive<Record<string, any>>({
    asset_ids: undefined,
    external_action: undefined,
    deep_external_action: undefined
  })
  //投放范围
  const deliveryRange = reactive<Record<string, any>>({
    inventory_catalog: 'MANUAL',
    inventory_type: [],
  })
  //排期与预算
  const deliverySetting = reactive<Record<string, any>>({
    smart_bid_type: 'SMART_BID_CUSTOM',
    flow_control_mode : 'FLOW_CONTROL_MODE_FAST',
    budget_mode : 'BUDGET_MODE_INFINITE',
    budget: undefined,
    schedule_type: 'SCHEDULE_FROM_NOW',
    start_time: undefined,
    end_time: undefined,
    schedule_time: undefined,
    pricing: 'PRICING_OCPM',
    deep_bid_type: undefined,
    cpa_bid: undefined,
  })
  const formData = reactive<Record<string, any>>({
    id: undefined,
    dealId: undefined,
    advertiserId: undefined,
    campaignId: undefined,   
    name: '计划',   
    operation: undefined,   
    awemeAccount: undefined,   
    deliveryRange: deliveryRange,   
    audience: audienceInfo,   
    optimizeGoal: optimizeGoal,   
    deliverySearch: deliverySearch,   
    deliverySetting: deliverySetting,  
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  const audience = ref({})
  //表单验证
  const validatorRules = {
  };
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: true });

  const scheduleTime = ref({
    schedule_time: ''
  })
  const onReceiveSchedule = (value) => {
    // 不能缺少这一步
    scheduleTime.value = value
  };
  watch(() => deliverySetting.smart_bid_type, (val) => {
    if (val == 'SMART_BID_CUSTOM') {
      deliverySetting.pricing = 'PRICING_OCPM'
    }else{
      deliverySetting.pricing = undefined;
    }
  })
  watch(deliveryRange,(val) =>{
    formData.deliverRange = val;
  })
  watch(deliverySearch,
  (val) =>{
    formData.deliverySearch = val
  })
  watch(optimizeGoal,
  (val) =>{
    if(val.external_action == 'AD_CONVERT_TYPE_PAY' && val.deep_external_action == 'AD_CONVERT_TYPE_PURCHASE_ROI'){
      deliverySetting.deep_bid_type = 'ROI_PACING'
    }else{
      deliverySetting.deep_bid_type = undefined
    }
    formData.optimizeGoal = val;
  })
  watch(() => deliverySetting.budget,
      (val) =>{
        let num = Number(val);
        if(isNaN(num)){
          tip2.value = '请输入数字'
        }else if(num < 300 || num > 9999999.99){
          tip2.value = '请输入预算，不少于300元，不超过9999999.99元'
        }else{
          tip2.value = ''
        }
      } 
  )
  watch(() => scheduleTime.value,
    (val) =>{
      deliverySetting.schedule_time = val.schedule_time;
    }
  )
  watch(deliverySetting,
    (val) =>{
      formData.deliverySetting = val
    })
  watch(() => audience.value,
    (val) =>{
      audienceInfo.audience = val
  })
  watch(audienceInfo,
    (val) =>{
      formData.audience = val
    }
  )
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

  const onReceiveAudience = (value) => {
    // 不可缺少这一步
    audience.value = value
  };
  
  function handleCreateCancel() {
    createVisible.value = false;
  }
  function disabledStartDate(current){
    const time = moment().endOf('day').subtract(1, 'days')
    return current && current < time
  }
  function disabledEndDate(current){
    return current && current <= moment(new Date(deliverySetting.start_time))
  }

  /**
   * 批量新增计划
   */
  function batchCreatePlan(records) {
    //平台不选择，根据游戏包决定
    if(document.querySelector("div[data-slot-name='platform']") != null) {
      if(document.querySelector("div[data-slot-name='platform']").style.display !=="none") {
        document.querySelector("div[data-slot-name='platform']").style.display="none";
      }
    }
    let accountId = records[0].accountId;
    records.forEach(item =>{
      dealIds.push(item.id);
    })
    let assetQueryParam = {
      id: records[0].id,
      accountId: accountId,
      pkgId: records[0].pkgId,
      subGameType: records[0].subGameType
    }
    //初始化事件选项
    assetList(assetQueryParam).then(res =>{
      assetsOptions.value = []
      res.forEach(item => {
        assetsOptions.value.push({
          label: item.label,
          value: item.value
        })
      })
      optimizeGoal.asset_ids = res[0].assetIds
      // assetsOptions.value  = res.options;
      //  = res.assetIds;
    })
    //初始化已有定向包选项
    audienceList(accountId).then(res =>{
      audienceOptions.value = res;
     })

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
      params:{accountId: records[0].accountId}
    })
    edit(undefined,{});
  }

  /**
   * 编辑
   */
  function edit(accountId, record) {
    nextTick(() => {
      resetFields();
      accountIdParam.value = accountId;
      //定向包
      Object.assign(audienceInfo, JSON.parse(record.audience));
      //投放范围
      Object.assign(deliveryRange, JSON.parse(record.deliverRange));
      //预算与出价
      let deliverySetting1 = JSON.parse(record.deliverySetting);
      Object.assign(deliverySetting, deliverySetting1);
      if(deliverySetting1.schedule_time != undefined){
        scheduleTime.value.schedule_time = deliverySetting.schedule_time;
        scheduleTimeType.value = true;
      }else{
        scheduleTime.value.schedule_time = '';
        scheduleTimeType.value = false;
      }
      //优化目标
      Object.assign(optimizeGoal, JSON.parse(record.optimizeGoal));
      //搜索快投
      Object.assign(deliverySearch, JSON.parse(record.deliverySearch));
      //赋值
      Object.assign(formData, record);
    });
  }

  /**
   * 表单校验
   */
  function checkForm() {
    if(formData.name == undefined || formData.name == ''){
      createMessage.info("请填入计划拼接名");
      return false;
    }
    if(formData.optimizeGoal == undefined || formData.optimizeGoal.external_action == undefined || formData.optimizeGoal.external_action == ''){
      createMessage.info("请选择优化目标");
      return false;
    }
    if(formData.deliverRange == undefined || formData.deliverRange.inventory_type.length <= 0){
      createMessage.info("请选择媒体");
      return false;
    }
    if(formData.deliverySetting.schedule_type == 'SCHEDULE_START_END' && (formData.deliverySetting.start_time == '' || formData.deliverySetting.end_time == '')){
      createMessage.info("请选择开始日期和结束日期");
      return false;
    }
    if(scheduleTimeType.value == false){
      formData.deliverySetting.schedule_time = undefined
    }
    if(formData.deliverySetting.budget_mode != 'BUDGET_MODE_INFINITE'){
      if(formData.deliverySetting.budget == undefined || formData.deliverySetting.budget < 300 || formData.deliverySetting.budget > 9999999.99) {
        createMessage.info("请输入预算，不少于300元，不超过999999.99元");
        return false;
      }
    }
    if(formData.deliverySetting.cpa_bid == undefined){
      createMessage.info("目标转化出价值为空");
      return false;
    }else{
      let cpaBid = Number(formData.deliverySetting.cpa_bid);
      if(isNaN(cpaBid)){
        createMessage.info("目标转化出价值非法");
        return false;
      }
      if(cpaBid < 0.1 || cpaBid > 10000){
        createMessage.info("请输入目标转化出价，不少于0.1元，不超过10000元")
        return false;
      } 
    }
    if(formData.optimizeGoal.deep_external_action != undefined){
      if(formData.deliverySearch.search_bid_ratio < 1 || formData.deliverySearch.search_bid_ratio > 2){
        createMessage.info("出价系数不小于1，不超过2");
        return false;
      }
    }
    // else{
    //   createMessage.info("出价系数不能为空");
    //   return;
    // }
    return true;
  }

  /**
   * 提交数据
   */
  async function submitForm() {
    // 触发表单验证
    await validate();
    if(!await checkForm()){
      return;
    }
    confirmLoading.value = true;
    const isUpdate = ref<boolean>(false);
    //时间格式化
    let model = formData;
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
      }
      if(model[data] instanceof Object) {
        model[data] = JSON.stringify(model[data]);
      }
    }
    if(!isUpdate.value){
      createDealList.value.length = dealIds.length * createNum.value;
      emit("ok");
      createVisible.value = true
      let handledNum = 0;

      for(let dealId of dealIds){
        for(let i = 0; i < createNum.value; i++){
          await batchCreatePlanReq(dealId, model).then((res) =>{
            dealProcess.value += 1;
            handledNum = handledNum +1;
            let per = handledNum/createDealList.value.length
            dealProcessPer.value = Math.floor(per * 100);
            if(res.code != 0){
              createFail.value += 1;
            }else{
              createSuccess.value += 1;
            }
            timelineDataList.value.push(res);
          })
        }
      }
    }else{
      editPlan(accountIdParam.value, model).then(res =>{
        console.log(res);
      }).
      finally(() =>{
        confirmLoading.value = false;
        emit("ok");
      })
    }
    
      
    // await saveOrUpdate(model, isUpdate.value)
    //   .then((res) => {
    //     if (res.success) {
    //       createMessage.success(res.message);
    //       emit('ok');
    //     } else {
    //       createMessage.warning(res.message);
    //     }
    //   })
    //   .finally(() => {
    //     confirmLoading.value = false;
    //   });
  }


  defineExpose({
    batchCreatePlan,
    edit,
    submitForm,
  });
</script>

<style lang="less" scoped>
  .antd-modal-form {
    min-height: 500px !important;
    overflow-y: auto;
    padding: 24px 24px 24px 24px;
  }
  .input {
    width:150px
  }
  .pdiv{
    border-top:1px solid #ebebfa;
    border-bottom:1px solid #ebebfa;
    // margin-bottom: 30px;
  }
  .subtitle{
    font-weight: bolder;
    margin-top: 20px;
    margin-bottom: 5px;
    color: #333
  }
  .last-div{
    margin-bottom: 25px;
  }
  .placement-site-item.active {
    background: #ebf3ff;
    border: 1px solid #338aff;
  }
  .placement-site-info {
    display: flex;
    align-items: center;
  }
  .placement-site-name {
    text-align: left;
    flex: 1;
  }
  .radio-button {
    width: 208px;
    height: 70px;
    margin-left: 5px;
  }
  .audience-radio-button {
    width: auto;
    height: auto;
    margin-left: 5px;
    margin-top: 10px;
  }
</style>
