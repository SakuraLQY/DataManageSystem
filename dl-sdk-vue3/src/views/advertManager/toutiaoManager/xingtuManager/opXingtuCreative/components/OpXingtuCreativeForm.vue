<template>
  <PageWrapper title="基本信息">
    <a-col :span="24">
      <a-card title="所属投放账号" style="font-weight:bolder" :bordered="false" size="small">
        <span style="color:red;float:left;margin-right:20px">*</span>
        <a-form-item  v-bind="validateInfos.accountId">
          <j-search-select v-model:value="formData.accountId"  dict="open_gateway.op_put_account where channel_Id = '4' and state = '1' and level_id = '2',nick_name,id" placeholder="请选择投放账号" disabled="false"/>
        </a-form-item>
      </a-card>
    </a-col>
    <a-col :span="24">
      <a-card title="所属广告计划" style="font-weight:bolder" :bordered="false" size="small">
        <span style="color:red;float:left;margin-right:20px">*</span>
        <a-form-item  v-bind="validateInfos.adId">
          <j-search-select v-model:value="formData.adId"  dict="open_gateway.op_xingtu_deal_plan,name,id" placeholder="请选择所属广告计划" disabled="false"/>
        </a-form-item>
      </a-card>
    </a-col>
    <a-col :span="24">
      <a-card title="选择抖音号" style="font-weight:bolder" :bordered="false" size="small">
        <span style="color:red;float:left;margin-right:20px">*</span>
        <a-form-item  >
          <j-search-select v-model:value="formData.trillId"  dict="open_gateway.op_anchor_plan,plan_name,trill_id" placeholder="请选择抖音号" disabled="false"/>
          <div>
            <a-checkbox v-model:checked="formData.isFeedAndFavSee">
              主页作品列表隐藏广告视频
              <a-tooltip placement="topLeft" title="来源为“抖音主页”的视频不可隐藏，若使用“自动生成创意”，生成的创意将默认被隐藏。">
                <a-button preIcon="ant-design:info-circle-twotone"></a-button>
              </a-tooltip>
            </a-checkbox>
          </div>
        </a-form-item>
      </a-card>
    </a-col>
  </PageWrapper>
  <PageWrapper title="制作创意">
    <a-col :span="24">
      <a-card title="创意形式" style="font-weight:bolder" :bordered="false" size="small">
        <span style="color:red;float:left;margin-right:20px">*</span>
        <a-form-item  v-bind="validateInfos.imageMode">
          <a-radio-group button-style="solid" v-model:value="formData.imageMode" :disabled="isAdd">
            <a-radio-button style="margin-right:10px" value = ''>广告创意</a-radio-button>
            <a-radio-button style="margin-right:10px" value = 'CREATIVE_IMAGE_MODE_AWEME_LIVE' disabled="false">直播创意</a-radio-button>
          </a-radio-group>
        </a-form-item>
      </a-card>
    </a-col>
    <a-col :span="24">
      <a-card title="组合方式" v-show="formData.imageMode !== 'CREATIVE_IMAGE_MODE_AWEME_LIVE' " style="font-weight:bolder" :bordered="false" size="small">
        <span style="color:red;float:left;margin-right:20px">*</span>
        <a-form-item  v-bind="validateInfos.creativeMaterialMode" >
          <a-radio-group button-style="solid" v-model:value="formData.creativeMaterialMode" :disabled="isAdd">
            <a-radio-button style="margin-right:10px" value = 'STATIC_ASSEMBLE'>程序化创意</a-radio-button>
            <a-radio-button style="margin-right:10px" value = 'STATIC_BY_MYSELF'>自定义创意</a-radio-button>
          </a-radio-group>
        </a-form-item>
      </a-card>
    </a-col>
    <a-col :span="24">
      <a-card title="添加素材" v-show="formData.imageMode !== 'CREATIVE_IMAGE_MODE_AWEME_LIVE' " style="font-weight:bolder" :bordered="false" size="small">
        <span style="color:red;float:left;margin-right:20px">*</span>
        <!-- <a-row> -->
          <a-row>
            <template class="div-style" v-for="(item, index) in videoMaterialList">
              <a-col :span="4" >
              <OpUploadMaterial  :title="item.dealName" :jrttAccountId="formData.accountId"
                v-model:value="videoMaterialList[index]"></OpUploadMaterial>
              </a-col>
                <a-col :span="20" style="margin-top:30px">
                  <a-form-item label="创意标题" v-show="formData.creativeMaterialMode !== 'STATIC_ASSEMBLE' " style="text-align: left" v-bind="validateInfos.titleList" >
                    <a-input v-model:value="formData.titleList[index].title" placeholder="标题5-30个字，请正确输入" showCount :maxlength="30" :disabled="disabled"></a-input>
                    <!-- <a-row :gutter="24">插入动态词包: <a-col :lg="3" @click.prevent="addLabelOne(index)"><a>+地点</a></a-col> <a-col :lg="3" @click.prevent="addLabelTwo(index)"><a>+日期</a></a-col> <a-col :lg="3" @click.prevent="addLabelThird(index)"><a>+星期</a></a-col> <a-col @click.prevent="addLabelFour(index)" :lg="3"><a>+年龄</a></a-col></a-row> -->
                  </a-form-item>
                </a-col>
            </template>
          <!-- </a-col> -->
          
        </a-row>
      </a-card>
    </a-col>
    <a-col :span="24">
      <a-card title="创意文案" v-show="formData.creativeMaterialMode === 'STATIC_ASSEMBLE' " style="font-weight:bolder" :bordered="false" size="small">
        <a-form-item style="color:red" >* 已添加{{formData.titleList.length}}/10</a-form-item>
          <a-form-item  v-bind="validateInfos.titleList" v-for="(titleOne, index) in formData.titleList" :key="'titleOne'+index">
            <a-input v-model:value="titleOne.title" placeholder="标题5-30个字，请正确输入" showCount :maxlength="30" :disabled="disabled"></a-input>
            <a-button style="float:right" @click.prevent="removeTitle(index)">{{'删除'}}</a-button>
            <!-- <a-row :gutter="24">插入动态词包: <a-col :lg="3" @click.prevent="addLabelOne(index)"><a>+地点</a></a-col> <a-col :lg="3" @click.prevent="addLabelTwo(index)"><a>+日期</a></a-col> <a-col :lg="3" @click.prevent="addLabelThird(index)"><a>+星期</a></a-col> <a-col @click.prevent="addLabelFour(index)" :lg="3"><a>+年龄</a></a-col></a-row> -->
          </a-form-item>
      </a-card>
      <a-form-item >
          <a-button v-show="formData.creativeMaterialMode === 'STATIC_ASSEMBLE' " @click="addTitle">{{'添加' }}</a-button>
      </a-form-item>
    </a-col>
  </PageWrapper>
  <PageWrapper title="创意设置">
    <a-col :span="24">
      <a-card title="来源" v-show="formData.imageMode !== 'CREATIVE_IMAGE_MODE_AWEME_LIVE' " style="font-weight:bolder" :bordered="false" size="small">
        <span style="color:red;float:left;margin-right:20px">*</span>
        <a-form-item  v-bind="validateInfos.source">
          <a-input v-model:value="formData.source" placeholder="请输入来源，来源2-10个字" showCount :maxlength="10" :disabled="disabled"></a-input>
        </a-form-item>
        <div>
            <a-checkbox v-model:checked="formData.enableSmartSource">
              智能优选
            </a-checkbox>
            <a-form-item style="color:grey">勾选后，系统将基于实时投放数据，展示更有利于转化的文案，帮助提升转化。</a-form-item>
          </div>
      </a-card>
    </a-col>
    <a-col :span="24">
      <a-card title="创意分类" style="font-weight:bolder" :bordered="false" size="small">
        <span style="color:red;float:left;margin-right:20px">*</span>
        <a-form-item  v-bind="validateInfos.thirdIndustryId">
           <a-cascader
              v-model:value="formData.thirdIndustryId"
              :options="treeData"
              show-all-levels="false"
              placeholder="请选择"
              :disabled="disabled"
            />
        </a-form-item>
      </a-card>
    </a-col>
    <a-col :span="24">
      <a-card title="创意标签"  style="font-weight:bolder" :bordered="false" size="small">
        <span style="color:red;float:left;margin-right:20px">*</span>
        <a-form-item  v-bind="validateInfos.adKeywords" >
          <a-input-search
            v-model:value="keywords"
            placeholder="最多20个，每个10个字以内，空格分隔"
            size="large"
            showCount :maxlength="10"
            @search="onSearch"
            @keyup.enter="show()"
          >
            <template #enterButton>
              <a-button @click="show()">添加回车键</a-button>
            </template>
          </a-input-search>
        </a-form-item>
        <a-card :title="adKeywordsTitle">
        <a-row  style="font-weight:bolder" :bordered="false" size="small">
          <a-col :span="8" v-for="(item, index) in formData.adKeywords" :key="'item'+index">
            {{item}}<a-button @click.prevent="removeKeywords(index)">{{'删除'}}</a-button>
          </a-col>
        </a-row>
        </a-card>
      </a-card>
    </a-col>
  </PageWrapper>
  <!-- 创建广告创意信息框 -->
  <a-modal v-model:visible="createVisible" title="创建广告创意进度" :maskClosable="false" :destroyOnClose="true">
    <div style="padding: 20px; font-size: 16px">
      <div>
        <span>开始为</span>
        <span class="span-font-normal">&nbsp;{{ videoMaterialList.length }}&nbsp;</span>
        <span>条广告计划创建星图广告创意</span>
      </div>
      <a-row :gutter="16">
        <a-col :span="8">
          <span>当前进度</span>
          <span class="span-font-normal">&nbsp;{{ dealProcess }} / {{ videoMaterialList.length }}</span>
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
          <TimelineItem :color="time.status == 200 ? 'green' : 'red'" v-for="time in timelineDataList">
            广告计划&nbsp;{{ time.adId }} -- {{ time.status == 200 ? '创建成功' : '创建失败, ' + time.msg }}<br>
          </TimelineItem>
        </Timeline>
      </div>
    </div>
    <template #footer>
      <a-button key="back" @click="handleCreateCancel">关闭</a-button>
    </template>
  </a-modal>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import { getValueType } from '/@/utils';
  import { saveOrUpdate, getIndustryList } from '../OpXingtuCreative.api';
  import { Form } from 'ant-design-vue';
  import { PageWrapper } from '/@/components/Page';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import OpUploadMaterial from './OpUploadMaterial.vue';
  import axios from 'axios'
  import { message } from 'ant-design-vue';
  import { queryById as materialQueryById} from '/@/views/advertManager/materialManager/opMaterial/OpMaterial.api';
  import { getFileAccessHttpUrl, getScreenshotFrameBase64Url, getRandom } from '/@/utils/common/compUtils';
  import func from '../../../../../../../vue-temp/vue-editor-bridge';

  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: ()=>{} },
    formBpm: { type: Boolean, default: true }
  });
  const formRef = ref();
  let trillId = ref();
  let isAdd = ref(false);
  const treeData = ref([]);
  const useForm = Form.useForm;
  // 广告进度
  const createVisible = ref<boolean>(false); //  广告进度弹窗
  // 创建进度
  const dealProcess = ref<number>(0);
  const dealProcessPer = ref<number>(0);
  // 成功
  const createSuccess = ref<number>(0);
  // 失败
  const createFail = ref<number>(0);
  // 时间线
  const timelineDataList = ref<Array>([]);
  const emit = defineEmits(['register', 'ok']);
  const videoMaterialList = ref<Array<object>>([]);// 视频素材列表
  let planName = ref(); 
  const formData = reactive<Record<string, any>>({
    id: undefined,
    accountId: undefined,
    isFeedAndFavSee: true,
    adId: undefined,
    adIdLong: undefined,
    creativeId: undefined,
    imageMode: '',
    trillId: undefined,
    creativeMaterialMode: 'STATIC_ASSEMBLE',
    videoMaterials :[],
    // creativeWordIds: '',   
    // status: '',   
    // optStatus: '',   
    // imageId: '',   
    // videoId: '',   
    // awemeItemId: '', 
    titleList: [
      {
        title: undefined
      }
    ], 
    thirdIndustryId: undefined,
    source: undefined,
    enableSmartSource: true,
    adKeywords: [],

  });
  getList();
  function getList() {
    getIndustryList({}).then((res: any)=>{
        treeData.value = res
      })
  }
  function removeKeywords(index) {
      if (index !== -1) {
        formData.adKeywords.splice(index, 1)
      }
      adKeywordsTitle.value =  '已选择( '+ formData.adKeywords.length +'/20)';
    }
  let keywords = ref();
  let adKeywordsTitle = ref('已选择( '+ formData.adKeywords.length +'/20)');
  function show() {
      if(keywords.value === undefined || keywords.value === null || keywords.value === '') {
        message.warning('创意标签不能为空');
        return false;
      }
     formData.adKeywords.push(keywords.value);
     keywords.value = undefined;
     adKeywordsTitle.value =  '已选择( '+ formData.adKeywords.length +'/20)';
    }
  function removeTitle(index) {
      if (index !== -1) {
        formData.titleList.splice(index, 1)
      }
    }

  function addTitle() {
      formData.titleList.push({
        title: undefined,
      })
    }
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    accountId: [{ required: true, message: '请输入广告主ID!'},],
    source: [{ required: true, message: '来源不能为空!'}, { pattern: /^[\u4E00-\u9FA5A-Za-z0-9]{2,10}$/, message: '请输入正确的来源!'}],
    thirdIndustryId: [{ required: true, message: '创意分类不能为空!'}]
    // adId: [{ required: true, message: '请输入广告计划ID!'},],
  };
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: true });

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
   * 插入动态词包
   */
  function addLabelOne(index) {
    if(formData.titleList[index].title !== undefined && formData.titleList[index].title !== '') {
      formData.titleList[index].title = formData.titleList[index].title + '{地点}';
    }else {
      formData.titleList[index].title = '{地点}';
    }
  } 

  /**
   * 插入动态词包
   */
  function addLabelTwo(index) {
    if(formData.titleList[index].title !== undefined && formData.titleList[index].title !== '') {
      formData.titleList[index].title = formData.titleList[index].title + '{日期}';
    }else {
      formData.titleList[index].title = '{日期}';
    }
  } 

  /**
   * 插入动态词包
   */
  function addLabelThird(index) {
    if(formData.titleList[index].title !== undefined && formData.titleList[index].title !== '') {
      formData.titleList[index].title = formData.titleList[index].title + '{星期}';
    }else {
      formData.titleList[index].title = '{星期}';
    }
  } 

  /**
   * 插入动态词包
   */
  function addLabelFour(index) {
    if(formData.titleList[index].title !== undefined && formData.titleList[index].title !== '') {
      formData.titleList[index].title = formData.titleList[index].title + '{年龄}';
    }else {
      formData.titleList[index].title = '{年龄}';
    }
  } 
  
  /**
   * 新增
   */
  function add() {
    videoMaterialList.value.length = 0;
    videoMaterialList.value.push({
        dealName: undefined,
        dealId: undefined,
        id: undefined,
        screenshot: undefined,
        frameRate:undefined
      });
    
    // edit({});
  }

  let adIds = ref([]);
  let adLongs = ref([]);

  /**
   * 编辑
   */
  function edit(record,count) {
    nextTick(async() => {
      resetFields();
      //清空进度条
      handleCreateCancel();
      adIds.value = [];
      adLongs.value = [];
      if(count === 0) {
      isAdd.value = false;
      videoMaterialList.value.length = 0;
      videoMaterialList.value.push({
        dealName: undefined,
        dealId: undefined,
        id: undefined,
        screenshot: undefined,
        frameRate:undefined
      });

      adIds.value.push(record.adId);
      adLongs.value.push(record.adIdLong);
    }else if (count === 1){
      isAdd.value = true;
      videoMaterialList.value = [];
      for(let i = 0;i < record.videoMaterials.length; i++) {
        let res = await materialQueryById({id:record.videoMaterials[i].videoMaterialId});
        let res2 = await axios.get(getScreenshotFrameBase64Url(res.showUrl) + "?frameNumber=" + record.videoMaterials[i].videoMaterialFrameRate)
        let screenshot = "";
        if (res2.status == 200) {
          screenshot = res2.data;
        } else {
          console.log("获取视频封面失败");
        }
        const videoMaterialOne = reactive<Record<string, any>>({
          dealName: record.dealName,
          dealId: record.dealId,
          id: record.videoMaterials[i].videoMaterialId,
          screenshot: screenshot,
          frameRate:record.videoMaterials[i].videoMaterialFrameRate
        })
        videoMaterialList.value.push(videoMaterialOne);
        record.videoMaterials[i].videoMaterialScreenShot = screenshot;
      }
      adIds.value.push(record.adId);
      adLongs.value.push(record.adIdLong);
      if(record.adKeywords === null) {
        record.adKeywords = [];
      }
    }else {
      isAdd.value = false;
      videoMaterialList.value = [];
      record.titleList = [];
      // record.accountId = record.accountId;
      record.trillId = record[0].awemeAccount;
      for(let i=0;i < record.length;i++) {
        videoMaterialList.value.push({
          dealName: undefined,
          dealId: undefined,
          id: undefined,
          screenshot: undefined,
          frameRate:undefined
        });
        adIds.value.push(record[i].id);
        adLongs.value.push(record[i].adId);
        record.titleList.push({
          title: undefined
        })
      }
      
    }
    resetFields();
      //赋值
      Object.assign(formData, record);
      
    });
  }

  /**
   * 提交数据
   */
  async function submitForm() {
    // 触发表单验证
    await validate();
    for(let i = 0; i < formData.titleList.length; i++) {
      let regu = /^[\u4E00-\u9FA5A-Za-z0-9]{5,30}$/;
      if(formData.titleList[i].title === null || formData.titleList[i].title === undefined || formData.titleList[i].title === '') {
        message.warning('标题不能为空');
        return false;
      } 
      if(!regu.test(formData.titleList[i].title)) {
        message.warning('标题不符合规则');
        return false;
      } 
    }
    
    if(formData.adKeywords.length > 20) {
      message.warning('创意标签最多20个');
      return false;
    }

    //清空进度条
    handleCreateCancel();
    // 校验素材封面
    for (let videoMaterial of videoMaterialList.value) {
      if (!videoMaterial['id']) {
        createMessage.warn(`视频素材不能为空`);
        return;
      }
      if (!videoMaterial['screenshot']) {
        createMessage.warn(`视频素材封面不能为空`);
        return;
      }
    }
    confirmLoading.value = true;
    // const isUpdate = ref<boolean>(false);
    if(formData.isFeedAndFavSee === true) {
      formData.isFeedAndFavSee = 1;
    }else {
      formData.isFeedAndFavSee = 0;
    }
    if(formData.enableSmartSource === true) {
      formData.enableSmartSource = 'ON';
    }else {
      formData.enableSmartSource = 'OFF';
    }
    //时间格式化
    let model = formData;
    let titleMaterials = model.titleList;
    
    createVisible.value = true;
    for (let i = 0; i < videoMaterialList.value.length; i++) {
      if(model.creativeMaterialMode === 'STATIC_BY_MYSELF') {
        model.titleList = [];
        model.titleList.push(titleMaterials[i]);
      }
      model.videoMaterials = [];
      model.adId = adIds.value[i];
      model.adIdLong = adLongs.value[i];
      
      const videoMaterialOne = reactive<Record<string, any>>({
        imageId: undefined,
        videoId: undefined,
        videoMaterialId: undefined,
        videoMaterialFrameRate: undefined,
        videoMaterialScreenShot: undefined
      })
      videoMaterialOne.videoMaterialId = videoMaterialList.value[i]['id'];
      videoMaterialOne.videoMaterialScreenShot = videoMaterialList.value[i]['screenshot'];
      videoMaterialOne.videoMaterialFrameRate = videoMaterialList.value[i]['frameRate'];
      model.videoMaterials.push(videoMaterialOne);
      await saveOrUpdate(model)
      .then((res) => {
        if (res.success) {
          // createMessage.success(res.message);
          createSuccess.value++;
          timelineDataList.value.push({ adId: model.adId, status: res.code, msg: res.message })
          emit('ok');
        } else {
          // createMessage.warning(res.message);
          createFail.value++;
          timelineDataList.value.push({ adId: model.adId, status: res.code, msg: res.message })
        }
      })
      .finally(() => {
        confirmLoading.value = false;
        // emit('ok');
      });
      dealProcess.value++;
      var per = dealProcess.value / videoMaterialList.value.length;
      dealProcessPer.value = Math.floor(per * 100);
    }
    // if (model.id) {
    //   isUpdate.value = true;
    // }
    //循环数据
    // for (let data in model) {
    //   //如果该数据是数组并且是字符串类型
    //   if (model[data] instanceof Array) {
    //     let valueType = getValueType(formRef.value.getProps, data);
    //     //如果是字符串类型的需要变成以逗号分割的字符串
    //     if (valueType === 'string') {
    //       model[data] = model[data].join(',');
    //     }
    //   }
    // }
    
  }

  /**
   * 关闭创建站点信息
   */
  function handleCreateCancel() {
    createFail.value = 0;
    createSuccess.value = 0;
    dealProcess.value = 0;
    dealProcessPer.value = 0;
    timelineDataList.value = [];
    createVisible.value = false;
  }



  defineExpose({
    add,
    edit,
    submitForm,
    addLabelOne,
    addLabelTwo,
    addLabelThird,
    addLabelFour,
    removeTitle,
    addTitle,
    removeKeywords,
    show,
    handleCreateCancel
  });
</script>

<style lang="less" scoped>
  .antd-modal-form {
    min-height: 500px !important;
    overflow-y: auto;
    padding: 24px 24px 24px 24px;
  }
  .div-style {
  margin-top: 16px;
}
</style>
