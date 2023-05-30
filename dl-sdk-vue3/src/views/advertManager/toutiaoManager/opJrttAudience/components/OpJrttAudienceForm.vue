<template>
  <a-row> 
    <a-col :span="10">
      <PageWrapper title="基本信息">
        <a-col :span="24">
          <a-card title="投放账号" style="font-weight:bolder" :bordered="false" size="small">
            <span style="color:red;float:left;margin-right:20px">*</span>
            <a-form-item  v-bind="validateInfos.accountId">
              <j-search-select v-model:value="formData.accountId"  dict="open_gateway.op_put_account where channel_Id = '4' and state = '1' and level_id = '2',nick_name,id" placeholder="请选择投放账号" :disabled="isAdd"/>
            </a-form-item>
          </a-card>
        </a-col>
        <a-col :span="24">
          <a-card title="定向包名称" style="font-weight:bolder" :bordered="false" size="small">
            <span style="color:red;float:left;margin-right:20px">*</span>
            <a-form-item  v-bind="validateInfos.name">
              <a-input v-model:value="formData.name" maxLength="30" placeholder="长度限制至30位" style="width:100%" maxlength="20" :disabled="disabled"></a-input>
            </a-form-item>
          </a-card>
        </a-col>
        <a-col :span="24">
          <a-card title="定向包描述" style="font-weight:bolder" :bordered="false" size="small">
            <span style="color:red;float:left;margin-right:20px">*</span>
            <a-form-item  v-bind="validateInfos.description">
              <a-textarea v-model:value="formData.description" showCount maxLength="255" rows="4" placeholder="长度限制至255位" :disabled="disabled"></a-textarea>
            </a-form-item>
          </a-card>
        </a-col>
        <a-col :span="24">
          <a-card title="广告类型" style="font-weight:bolder" :bordered="false" size="small">
            <a-form-item>
              <a-radio-group button-style="solid" v-model:value="advertType">
                <a-tooltip color="cyan" title="所有流量位投放，包括但不限于信息流广告、搜索广告等，寻找对您产品或服务感兴趣的用户。">
                  <a-radio-button style="margin-right:10px" value = '0'>所有广告</a-radio-button>
                </a-tooltip>
                <a-tooltip color="cyan" title="向对您产品或服务主动表达兴趣的用户展示广告，系统将根据您的投放目标，选择适合的搜索流量场景进行投放。">
                  <a-radio-button style="margin-right:10px" value = '1' disabled="false">搜索广告</a-radio-button>
                </a-tooltip>
              </a-radio-group>
            </a-form-item>
          </a-card>
        </a-col>
        <a-col :span="24">
          <a-card title="定向包类型"  style="font-weight:bolder" :bordered="false" size="small">
            <a-form-item  v-bind="validateInfos.landingType">
              <a-radio-group button-style="solid" v-model:value="formData.landingType" :disabled="isAdd">
                <a-radio-button style="margin-right:10px" value = 'EXTERNAL'>落地页</a-radio-button>
                <a-radio-button style="margin-right:10px" value = 'APP_ANDROID'>应用推广-Android</a-radio-button>
                <a-radio-button style="margin-right:10px" value = 'APP_IOS'>应用推广-iOS</a-radio-button>
                <a-radio-button style="margin-right:10px" value = 'AWEME'>抖音号</a-radio-button>
                <a-radio-button style="margin-right:10px" value = 'DPA'>商品</a-radio-button>
                <a-radio-button style="margin-right:10px" value = 'SHOP'>电商店铺推广</a-radio-button>
                <a-radio-button style="margin-right:10px" value = '0' disabled="false">快应用</a-radio-button>
                <a-radio-button style="margin-right:10px" value = 'LIVE'>直播间</a-radio-button>
                <a-radio-button style="margin-right:10px" value = 'MICRO_GAME'>小游戏</a-radio-button>
              </a-radio-group>
            </a-form-item>
          </a-card>
        </a-col>
        <a-col :span="24">
          <a-card title="投放范围" style="font-weight:bolder" :bordered="false" size="small">
            <a-form-item  v-bind="validateInfos.deliveryRange">
              <a-radio-group button-style="solid" v-model:value="formData.deliveryRange" :disabled="isAdd">
                <a-radio-button style="margin-right:10px" value = 'DEFAULT'>默认</a-radio-button>
                <a-tooltip color="cyan" title="基于4亿日活用户大数据积累，顶尖AI智能推荐技术，今日头条与最广泛的合作伙伴携手打造的全新移动生态联盟">
                  <a-radio-button v-if="formData.landingType === 'AWEME' || formData.landingType === 'LIVE'" disabled="false" style="margin-right:10px" value = 'UNION'>穿山甲</a-radio-button>
                  <a-radio-button v-if="formData.landingType !== 'AWEME' && formData.landingType !== 'LIVE'" style="margin-right:10px" value = 'UNION'>穿山甲</a-radio-button>
                </a-tooltip>
              </a-radio-group>
            </a-form-item>
          </a-card>
        </a-col>
      </PageWrapper>
    </a-col>
    <a-col :span="14">
      <PageWrapper :key="changeKey">
        <a-col :span="24">
        <a-form-item >
          <AudienceTarget :key="dateKey" ref="audientTarget" @receive="onReceive" :curValue="curValue" :extensions="extensions"/>
        </a-form-item>
        <a-col :span="24">
          <a-card title="过滤已转化用户" style="font-weight:bolder" :bordered="false" size="small">
            <a-form-item  v-bind="validateInfos.hideIfConverted">
              <a-radio-group button-style="solid" v-model:value="formData.hideIfConverted">
                <a-radio-button style="margin-right:10px" value = 'NO_EXCLUDE'>不过滤</a-radio-button>
                <a-radio-button style="margin-right:10px" value = 'AD'>广告计划</a-radio-button>
                <a-radio-button style="margin-right:10px" value = 'CAMPAIGN'>广告组</a-radio-button>
                <a-radio-button style="margin-right:10px" value = 'ADVERTISER'>广告账户</a-radio-button>
                <a-radio-button style="margin-right:10px" value = 'CUSTOMER'>公司账户</a-radio-button>
              </a-radio-group>
            </a-form-item>
          </a-card>
        </a-col>
        <a-col :span="24">
          <a-card title="过滤高活跃用户" style="font-weight:bolder" :bordered="false" size="small">
            <a-form-item  v-bind="validateInfos.filterAwemeAbnormalActive">
              <a-radio-group button-style="solid" v-model:value="formData.filterAwemeAbnormalActive">
                <a-radio-button style="margin-right:10px" value = '0'>不过滤</a-radio-button>
                <a-radio-button style="margin-right:10px" value = '1'>过滤</a-radio-button>
              </a-radio-group>
            </a-form-item>
          </a-card>
        </a-col>
        <a-col :span="24">
          <a-card title="过滤自己粉丝" style="font-weight:bolder" :bordered="false" size="small">
            <a-form-item  v-bind="validateInfos.filterOwnAwemeFans">
              <a-radio-group button-style="solid" v-model:value="formData.filterOwnAwemeFans" disabled="false">
                <a-radio-button style="margin-right:10px" value = '0'>不过滤</a-radio-button>
                <a-radio-button style="margin-right:10px" value = '1'>过滤</a-radio-button>
              </a-radio-group>
            </a-form-item>
          </a-card>
        </a-col>
        <a-col :span="24">
          <a-card title="过滤高关注数用户" style="font-weight:bolder" :bordered="false" size="small">
            <a-form-item  v-bind="validateInfos.filterAwemeFansCount">
              <a-radio-group button-style="solid" v-model:value="formData.filterAwemeFansCount">
                <a-radio-button style="margin-right:10px" value = '0'>不过滤</a-radio-button>
                <a-radio-button style="margin-right:10px" value = '1000' :disabled="ifFAFC">>1000</a-radio-button>
                <a-radio-button style="margin-right:10px" value = '500' :disabled="ifFAFC">>500</a-radio-button>
                <a-radio-button style="margin-right:10px" value = '200' :disabled="ifFAFC">>200</a-radio-button>
              </a-radio-group>
            </a-form-item>
          </a-card>
        </a-col>
      </a-col>
    </PageWrapper>
    </a-col>
  </a-row>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted, watch } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import { getValueType } from '/@/utils';
  import { saveOrUpdate } from '../OpJrttAudience.api';
  import { Form } from 'ant-design-vue';
  import { PageWrapper } from '/@/components/Page';
  import { CollapseContainer } from '/@/components/Container/index';
  import { setConfig, AudienceTarget, CustomCrowd } from '@bytedance-ad/mui-vue3';
  import { inject } from "vue";
  // 别忘记引入样式文件
  import '@bytedance-ad/mui-vue3/dist/style.css';
  import {getToken} from '/@/utils/auth';
  import func from '../../../../../../vue-temp/vue-editor-bridge';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: ()=>{} },
    formBpm: { type: Boolean, default: true }
  });
  const formRef = ref();
  const audientTarget = ref();
  const isAdd = ref();
  const dateKey = ref();
  const changeKey = ref(false);
  const ifFAFC = ref();
  function isCustomCrowdAction() {
    dateKey.value = false;
  }
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const advertType = ref('0');
  const formData2 = reactive<Record<string, any>>({
    id: '',
    audiencePackageId: undefined,
    name: '',   
    description: '',   
    landingType: 'EXTERNAL',   
    accountId: undefined,
    deliveryRange: 'DEFAULT',   
    district: undefined,   
    gender: undefined,   
    age: null,
    androidOsv: undefined,
    iosOsv: undefined,
    retargetingTagsConf: {},   
    superiorPopularityType: undefined,   
    interestActionMode: undefined,   
    awemeFanConf: {},   
    filterAwemeAbnormalActive: '0',
    filterOwnAwemeFans: '0',
    filterAwemeFansCount: '0',
    platform: null,   
    deviceType: null,   
    ac: null,   
    hideIfExists: undefined,
    hideIfConverted: 'AD',
    deviceBrand: null,   
    launchPrice: null,   
    autoExtendTargets: null,  
    carrier:null,
    articleCategory: null,
    interestActionConf: {},
    districtConf: {},
    superiorPopularityTypeConf: {},
    awemeFansNumbers: null,
  });
  const formData = reactive<Record<string, any>>({
    id: '',
    audiencePackageId: undefined,
    name: '',   
    description: '',   
    landingType: 'EXTERNAL',   
    accountId: undefined,
    deliveryRange: 'DEFAULT',   
    district: undefined,   
    gender: undefined,   
    age: null,
    androidOsv: undefined,
    iosOsv: undefined,
    retargetingTagsConf: {},   
    superiorPopularityType: undefined,   
    interestActionMode: undefined,   
    awemeFanConf: {},   
    filterAwemeAbnormalActive: '0',
    filterOwnAwemeFans: '0',
    filterAwemeFansCount: '0',
    platform: null,   
    deviceType: null,   
    ac: null,   
    hideIfExists: undefined,
    hideIfConverted: 'AD',
    deviceBrand: null,   
    launchPrice: null,   
    autoExtendTargets: null,  
    carrier:null,
    articleCategory: null,
    interestActionConf: {},
    districtConf: {},
    superiorPopularityTypeConf: {},
    awemeFansNumbers: null,
  });
 
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    name: [{ required: true, message: '请输入定向包名称!'},],
    description: [{ required: true, message: '请输入定向包描述!'},],
    landingType: [{ required: true, message: '请输入定向包类型!'},],
    accountId: [{ required: true, message: '请输入投放账号!'},],
  };
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: true });
  const getJrttAudienceDto = reactive<Record<string, any>>({
    accountId: formData.accountId,
  });
  const headers = reactive<Record<string, any>>({
    'X-Access-Token': getToken(),
  });
  watch(
      () => formData.accountId,
      val => {
        if(changeKey.value === false) {
          changeKey.value = true;
        }else {
          changeKey.value = false;
        }
        getJrttAudienceDto.accountId = val;
        setConfig({
          host: window._CONFIG['domianURL'], // 设置接口域名
          headers: headers,
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
          method: 'GET',
          params: getJrttAudienceDto,
        });
        curValue.value = {};
      }
  );
  
  const curValue = ref({})
  // 不需要商圈可不传
  const extensions = {
    securityJsCode: '您的高德code',
    securityKey: '您的高德key',
    delivery_range: formData.deliveryRange,
    landing_type: formData.landingType,
  };
 
  const onReceive = (value) => {
    // 不可缺少这一步
    curValue.value = value;
    
  };
//监听下拉框变化
  watch(
      () => curValue.value,
      val => {
        if(document.querySelector("div[data-slot-name='transform']") != null) {
          if(document.querySelector("div[data-slot-name='transform']").style.display !=="none") {
            document.querySelector("div[data-slot-name='transform']").style.display="none";
          }
        }
      });

  watch(
      () => extensions,
      val => {
        if(document.querySelector("div[data-slot-name='transform']") != null) {
          if(document.querySelector("div[data-slot-name='transform']").style.display !=="none") {
            document.querySelector("div[data-slot-name='transform']").style.display="none";
          }
        }
      });
  watch(formData, () => {
    if ((formData.landingType === 'APP_ANDROID' || formData.landingType === 'APP_IOS') && formData.deliveryRange === 'UNION') {
      ifFAFC.value = true;
    }else {
      ifFAFC.value = false;
    }
    
  });
//监听下拉框变化
  watch(
      () => formData.landingType,
      val => {
        extensions.landing_type = val; 
      },
    );

    watch(
      () => formData.deliveryRange,
      val => {
        extensions.delivery_range = val;
      }
    );
    
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

  
  /**
   * 新增
   */
  function add() {
    edit(formData2);
  }

  function editConcordanceVal(record) {
    if(record.retargetingTagsConf.hasOwnProperty('retargeting_tags')) {
      if(Array.isArray(record.retargetingTagsConf['retargeting_tags']) && record.retargetingTagsConf['retargeting_tags'].length !== 0) {
        curValue.value.retargeting_tags_include = record.retargetingTagsConf['retargeting_tags'];
      }
    }
    if(record.retargetingTagsConf.hasOwnProperty('retargeting_tags_exclude')) {
      if(Array.isArray(record.retargetingTagsConf['retargeting_tags_exclude']) && record.retargetingTagsConf['retargeting_tags_exclude'].length !== 0) {
        curValue.value.retargeting_tags_exclude = record.retargetingTagsConf['retargeting_tags_exclude'];
      }
    }
    if(record.hasOwnProperty('district')) {
      curValue.value.district = record.district;
    }
    if(record.hasOwnProperty('gender')) {
      curValue.value.gender = record.gender;
    }
    if(record.hasOwnProperty('age')) {
      curValue.value.age = record.age;
    }
    if(record.districtConf.hasOwnProperty('city')) {
      if(Array.isArray(record.districtConf['city']) && record.districtConf['city'].length !== 0) {
        curValue.value.city = record.districtConf['city'];
      }else if(Array.isArray(record.districtConf['city']) && record.districtConf['city'].length === 0) {
        curValue.value.district = 'NONE';
      }
    }
    if(record.districtConf.hasOwnProperty('region_version')) {
      curValue.value.region_version = record.districtConf['region_version'];
    }
    if(record.districtConf.hasOwnProperty('location_type')) {
      curValue.value.location_type = record.districtConf['location_type'];
    }
    if(record.hasOwnProperty('platform')) {
      curValue.value.platform = record.platform;
    }
    if(record.hasOwnProperty('deviceType')) {
      curValue.value.device_type = record.deviceType;
    }
    if(record.hasOwnProperty('ac')) {
      curValue.value.ac = record.ac;
    }
    if(record.hasOwnProperty('hideIfExists')) {
      if(record.hideIfExists === 0) {
        curValue.value.hide_if_exists = 'UNLIMITED';
      }else if(record.hideIfExists === 1) {
        curValue.value.hide_if_exists = 'FILTER';
      }else{
        curValue.value.hide_if_exists = 'TARGETING';
      }
    }
    //过滤已转化用户固定选择广告计划
    curValue.value.hide_if_converted = 'NO_EXCLUDE';
    if(record.hasOwnProperty('androidOsv')) {
      curValue.value.android_osv = record.androidOsv;
    }
    if(record.hasOwnProperty('iosOsv')) {
      curValue.value.ios_osv = record.iosOsv;
    }
    if(record.hasOwnProperty('carrier')) {
      curValue.value.carrier = record.carrier;
    }
    if(record.hasOwnProperty('deviceBrand')) {
      if(Array.isArray(record.deviceBrand) && record.deviceBrand.length !== 0) {
        curValue.value.device_brand = record.deviceBrand;
      }
    }
    if(record.hasOwnProperty('launchPrice')) {
      curValue.value.launch_price = record.launchPrice;
    }
    if(record.hasOwnProperty('superiorPopularityType')) {
      curValue.value.superior_popularity_type = record.superiorPopularityType;
    }
    if(record.superiorPopularityTypeConf.hasOwnProperty('flow_package')) {
      if(Array.isArray(record.superiorPopularityTypeConf['flow_package']) && record.superiorPopularityTypeConf['flow_package'].length !== 0) {
        curValue.value.flow_package = record.superiorPopularityTypeConf['flow_package'];
      }
    }
    if(record.superiorPopularityTypeConf.hasOwnProperty('exclude_flow_package')) {
      if(Array.isArray(record.superiorPopularityTypeConf['exclude_flow_package']) && record.superiorPopularityTypeConf['exclude_flow_package'].length !== 0) {
        curValue.value.exclude_flow_package = record.superiorPopularityTypeConf['exclude_flow_package'];
      }
    }
    if(record.hasOwnProperty('autoExtendTargets')) {
      if(Array.isArray(record.autoExtendTargets) && record.autoExtendTargets.length !== 0) {
        curValue.value.auto_extend_targets = record.autoExtendTargets;
      }
    }
    if(record.awemeFanConf.hasOwnProperty('aweme_fan_behaviors')) {
      if(Array.isArray(record.awemeFanConf['aweme_fan_behaviors']) && record.awemeFanConf['aweme_fan_behaviors'].length !== 0) {
        curValue.value.aweme_fan_behaviors = record.awemeFanConf['aweme_fan_behaviors']
      }
    }
    if(record.awemeFanConf.hasOwnProperty('aweme_fan_time_scope')) {
      curValue.value.aweme_fan_time_scope = record.awemeFanConf['aweme_fan_time_scope'];
    }
    if(record.awemeFanConf.hasOwnProperty('aweme_fan_categories')) {
      if(Array.isArray(record.awemeFanConf['aweme_fan_categories']) && record.awemeFanConf['aweme_fan_categories'].length !== 0) {
        curValue.value.aweme_fan_categories = record.awemeFanConf['aweme_fan_categories'];
      }
    }
    if(record.awemeFanConf.hasOwnProperty('aweme_fan_accounts')) {
      if(Array.isArray(record.awemeFanConf['aweme_fan_accounts']) && record.awemeFanConf['aweme_fan_accounts'].length !== 0) {
        curValue.value.aweme_fan_accounts = record.awemeFanConf['aweme_fan_accounts'];
      }
    }
    if(record.hasOwnProperty('interestActionMode')) {
      curValue.value.interest_action_mode = record.interestActionMode;
    }
    if(record.interestActionConf.hasOwnProperty('action_scene')) {
      if(Array.isArray(record.interestActionConf['action_scene']) && record.interestActionConf['action_scene'].length !== 0) {
        curValue.value.action_scene = record.interestActionConf['action_scene'];
      }
    }
    if(record.interestActionConf.hasOwnProperty('action_days')) {
      curValue.value.action_days = record.interestActionConf['action_days'];
    }
    if(record.interestActionConf.hasOwnProperty('action_categories')) {
      if(Array.isArray(record.interestActionConf['action_categories']) && record.interestActionConf['action_categories'].length !== 0) {
        curValue.value.action_categories = record.interestActionConf['action_categories'];
      }
    }
    if(record.interestActionConf.hasOwnProperty('action_words')) {
      if(Array.isArray(record.interestActionConf['action_words']) && record.interestActionConf['action_words'].length !== 0) {
        curValue.value.action_words = record.interestActionConf['action_words'];
      }
    }
    if(record.interestActionConf.hasOwnProperty('interest_categories')) {
      if(Array.isArray(record.interestActionConf['interest_categories']) && record.interestActionConf['interest_categories'].length !== 0) {
        curValue.value.interest_categories = record.interestActionConf['interest_categories'];
      }
    }
    if(record.interestActionConf.hasOwnProperty('interest_words')) {
      if(Array.isArray(record.interestActionConf['interest_words']) && record.interestActionConf['interest_words'].length !== 0) {
       curValue.value.interest_words = record.interestActionConf['interest_words'];
      }
    }
    
  }
  
  /**
   * 编辑
   */
  function edit(record) {
    dateKey.value = true;
    if(record.id !== undefined && record.id !== '') {
        isAdd.value = true;
        formData.accountId = record.accountId;
      }else {
        isAdd.value = false;
      }
    nextTick(() => {
      // resetFields();
      formData.id = record.id;
      formData.audiencePackageId = record.audiencePackageId;
      formData.name = record.name;
      formData.description = record.description;
      formData.landingType = record.landingType;
      formData.deliveryRange = record.deliveryRange;
      formData.filterAwemeAbnormalActive = record.filterAwemeAbnormalActive;
      formData.filterOwnAwemeFans = record.filterOwnAwemeFans;
      formData.filterAwemeFansCount = record.filterAwemeFansCount;
      formData.hideIfConverted = record.hideIfConverted;
      if(record.id !== undefined && record.id !== '') {
        editConcordanceVal(record);
      }else {
        //赋值
        Object.assign(formData, record);
      }
    });
  }

  function concordanceVal() {
    if(formData.landingType === 'APP_ANDROID') {
      formData.platform = ['ANDROID'];
    }else if(formData.landingType === 'APP_IOS'){
      formData.platform = ['IOS'];
    }
    if(curValue.value.hasOwnProperty('retargeting_tags_include')) {
      formData.retargetingTagsConf['retargeting_tags'] = curValue.value.retargeting_tags_include;
    }else {
      formData.retargetingTagsConf['retargeting_tags'] = null;
    }
    if(curValue.value.hasOwnProperty('retargeting_tags_exclude')) {
      formData.retargetingTagsConf['retargeting_tags_exclude'] = curValue.value.retargeting_tags_exclude;
    }else {
      formData.retargetingTagsConf['retargeting_tags_exclude'] = null;
    }
    if(curValue.value.hasOwnProperty('district') ) {
      formData.district = curValue.value.district;
    }
    if(curValue.value.hasOwnProperty('gender')) {
      formData.gender = curValue.value.gender;
    }else {
      formData.gender = null;
    }
    if(curValue.value.hasOwnProperty('age')) {
      formData.age = curValue.value.age;
    }else {
      formData.age = null;
    }
    if(curValue.value.hasOwnProperty('city')) {
      formData.districtConf['city'] = curValue.value.city;
      if(Array.isArray(curValue.value.city) && curValue.value.city.length === 0) {
        formData.district = 'NONE';
      }
    }else {
      formData.districtConf['city'] = null;
      formData.district = 'NONE';
    }
    if(curValue.value.hasOwnProperty('region_version') && curValue.value.district === 'REGION') {
      formData.districtConf['region_version'] = curValue.value.region_version;
    }else {
      formData.districtConf['region_version'] = null;
    }
    if(curValue.value.hasOwnProperty('location_type')) {
      formData.districtConf['location_type'] = curValue.value.location_type;
    }else {
      formData.districtConf['location_type'] = null;
    }
    if(curValue.value.hasOwnProperty('platform')) {
      formData.platform = curValue.value.platform;
    }else{
      formData.platform = null;
    }
    if(curValue.value.hasOwnProperty('device_type')) {
      formData.deviceType = curValue.value.device_type;
    }else {
      formData.deviceType = null;
    }
    if(curValue.value.hasOwnProperty('ac')) {
      formData.ac = curValue.value.ac;
    }else {
      formData.ac = null;
    }
    if(curValue.value.hasOwnProperty('hide_if_exists')) {
      if(curValue.value.hide_if_exists === 'UNLIMITED') {
        formData.hideIfExists = 0;
      }else if(curValue.value.hide_if_exists === 'FILTER') {
        formData.hideIfExists = 1;
      }else{
        formData.hideIfExists = 2;
      }
    }
    //过滤已转化用户固定选择广告计划
    // if(curValue.value.hasOwnProperty('hide_if_converted')) {
    //   formData.hideIfConverted = curValue.value.hide_if_converted;
    // }
    
    if(curValue.value.hasOwnProperty('android_osv')) {
      formData.androidOsv = curValue.value.android_osv;
    }else {
      formData.androidOsv = "0.0";
    }
    if(curValue.value.hasOwnProperty('ios_osv')) {
      formData.iosOsv = curValue.value.ios_osv;
    }else {
      formData.iosOsv = "0.0";
    }
    if(curValue.value.hasOwnProperty('carrier')) {
      formData.carrier = curValue.value.carrier;
    }else {
      formData.carrier = null;
    }
    if(curValue.value.hasOwnProperty('device_brand')) {
      formData.deviceBrand = curValue.value.device_brand;
    }else {
      formData.deviceBrand = null;
    }
    if(curValue.value.hasOwnProperty('launch_price')) {
      formData.launchPrice = curValue.value.launch_price;
    }else {
      formData.launchPrice = null;
    }
    if(curValue.value.hasOwnProperty('superior_popularity_type')) {
      formData.superiorPopularityType = curValue.value.superior_popularity_type;
    }else {
      formData.superiorPopularityType = null;
    }
    if(curValue.value.hasOwnProperty('flow_package')) {
      formData.superiorPopularityTypeConf['flow_package'] = curValue.value.flow_package;
    }else{
      formData.superiorPopularityTypeConf['flow_package'] = null;
    }
    if(curValue.value.hasOwnProperty('exclude_flow_package')) {
      formData.superiorPopularityTypeConf['exclude_flow_package'] = curValue.value.exclude_flow_package;
    }else {
      formData.superiorPopularityTypeConf['exclude_flow_package'] = null;
    }
    if(curValue.value.hasOwnProperty('auto_extend_targets')) {
      formData.autoExtendTargets = curValue.value.auto_extend_targets;
    }else{
      formData.autoExtendTargets = null;
    }
    if(curValue.value.hasOwnProperty('aweme_fan_behaviors')) {
      formData.awemeFanConf['aweme_fan_behaviors'] = curValue.value.aweme_fan_behaviors;
    }else {
      formData.awemeFanConf['aweme_fan_behaviors'] = null;
    }
    if(curValue.value.hasOwnProperty('aweme_fan_time_scope')) {
      formData.awemeFanConf['aweme_fan_time_scope'] = curValue.value.aweme_fan_time_scope;
    }else {
      formData.awemeFanConf['aweme_fan_time_scope'] = null;
    }
    if(curValue.value.hasOwnProperty('aweme_fan_categories')) {
      formData.awemeFanConf['aweme_fan_categories'] = curValue.value.aweme_fan_categories;
    }else {
      formData.awemeFanConf['aweme_fan_categories'] = null;
    }
    if(curValue.value.hasOwnProperty('aweme_fan_accounts')) {
      formData.awemeFanConf['aweme_fan_accounts'] = curValue.value.aweme_fan_accounts;
    }else {
      formData.awemeFanConf['aweme_fan_accounts'] = null;
    }
    if(curValue.value.hasOwnProperty('interest_action_mode')) {
      formData.interestActionMode = curValue.value.interest_action_mode;
    }else {
      formData.interestActionMode = null;
    }
    if(curValue.value.hasOwnProperty('action_scene')) {
      formData.interestActionConf['action_scene'] = curValue.value.action_scene;
    }else {
      formData.interestActionConf['action_scene'] = null;
    }
    if(curValue.value.hasOwnProperty('action_days')) {
      formData.interestActionConf['action_days'] = curValue.value.action_days;
    }else {
      formData.interestActionConf['action_days'] = null;
    }
    if(curValue.value.hasOwnProperty('action_categories')) {
      formData.interestActionConf['action_categories'] = curValue.value.action_categories;
    }else {
      formData.interestActionConf['action_categories'] = null;
    }
    if(curValue.value.hasOwnProperty('action_words')) {
      formData.interestActionConf['action_words'] = curValue.value.action_words;
    }else {
      formData.interestActionConf['action_words'] = null;
    }
    if(curValue.value.hasOwnProperty('interest_categories')) {
      formData.interestActionConf['interest_categories'] = curValue.value.interest_categories;
    }else {
      formData.interestActionConf['interest_categories'] = null;
    }
    if(curValue.value.hasOwnProperty('interest_words')) {
      formData.interestActionConf['interest_words'] = curValue.value.interest_words;
    }else {
      formData.interestActionConf['interest_words'] = null;
    }
  }

  /**
   * 提交数据
   */
  async function submitForm() {
    // 触发表单验证
    await validate();
    confirmLoading.value = true;
    const isUpdate = ref<boolean>(false);
    concordanceVal();
    //时间格式化
    let model = formData;
    if (model.id) {
      isUpdate.value = true;
    }
    //循环数据
    // for (let data in model) {
    //   //如果该数据是数组并且是字符串类型
      if (model['createTime'] instanceof Array) {
        let valueType = getValueType(formRef.value.getProps, 'createTime');
        //如果是字符串类型的需要变成以逗号分割的字符串
        if (valueType === 'string') {
          model['createTime'] = model['createTime'].join(',');
        }
      }
    // }
    await saveOrUpdate(model, isUpdate.value)
      .then((res) => {
        if (res.success) {
          createMessage.success(res.message);
          emit('ok');
        } else {
          createMessage.warning(res.message);
        }
      })
      .finally(() => {
        confirmLoading.value = false;
      });
  }


  defineExpose({
    add,
    edit,
    submitForm,
    advertType,
    curValue,
    isCustomCrowdAction,
    editConcordanceVal
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
  #kuang{position: absolute;background-color: blue;opacity: 0.3;border:1px}
</style>
