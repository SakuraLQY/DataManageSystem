<template>
  <PageWrapper title="推广身份">
    <a-card>
      <CollapseContainer title="推广身份">
        <div class="div-style">
          <a-radio-group v-model:value="formData.promotionIdentity" button-style="solid"
            @change="changePromotionIdentity">
            <a-radio-button value="DOUYING">抖音号</a-radio-button>
            <a-radio-button value="ACCOUNTINFO">账户信息</a-radio-button>
          </a-radio-group>
          <div class="div-style" v-if="visiablePromotionIdentity">
            <div class="div-style">抖音号</div>
            <div class="div-style">
              <a-select placeholder="请选择抖音号" v-model:value="formData.awemeId" style="width:150px;margin-left: 10px;"
                :options="awemeOptions" />
              <a-button v-if="!props.formDisabled" type="link" @click="syncAweme()">同步</a-button>
            </div>
            <div class="div-style">
              <a-checkbox v-model:checked="formData.isFeedAndFavSee">主页作品列表隐藏广告视频</a-checkbox>
              <a-tooltip title="抖音主页视频及视频授权不可隐藏" placement="right">
                <QuestionCircleFilled />
              </a-tooltip>
            </div>
          </div>
        </div>
      </CollapseContainer>
    </a-card>
  </PageWrapper>
  <PageWrapper title="广告素材">
    <a-card>
      <CollapseContainer title=" 视频素材">
        <template class="div-style" v-for="(item, index) in videoMaterialList">
          <OpUploadMaterial :subGameId="subGameId" :title="item.dealName" :materialManagerType="2"
            v-model:value="videoMaterialList[index]"></OpUploadMaterial>
        </template>
      </CollapseContainer>
      <CollapseContainer title="文案素材">
        <div class="div-style">
          <div class="div-style">标题</div>
          <a-input placeholder="请输入标题" v-model:value="titleMaterial" style="width:350px"></a-input>
          <a-button @click="addTitleMaterial">添加</a-button>
          <a-popover placement="right">
            <template #content>
              <a-list :data-source="titleMaterialActionList">
                <template #renderItem="{ item }">
                  <a-list-item>
                    {{ item.actionContent }}
                    <template #actions>
                      <a @click="addActionTitleMaterial(item.actionContent)">选择</a>
                      <a @click="deleteActiontitleMaterialById(item.id)">删除</a>
                    </template>
                  </a-list-item>
                </template>
              </a-list>
            </template>
            <a-button>选择标题</a-button>
          </a-popover>
          <template v-for="(item, index) in formData.titleMaterialList">
            <a-col>
              <a-space style="width:300px;margin-top:5px;">{{ item }}</a-space><a-button v-if="!props.formDisabled"
                type="link" @click="deleteTitleMaterial(index)">删除</a-button>
            </a-col>
          </template>
        </div>
      </CollapseContainer>
      <CollapseContainer title="原生锚点">
        <div class="div-style">
          <a-radio-group v-model:value="formData.anchorRelatedType" button-style="solid">
            <a-radio-button value="OFF">默认值</a-radio-button>
            <a-radio-button value="AUTO">自动生成</a-radio-button>
            <a-radio-button disabled value="SELECT">手动选择</a-radio-button>
          </a-radio-group>
        </div>
      </CollapseContainer>
      <CollapseContainer title="产品信息">
        <div class="div-style">
          <div class="div-style">产品名称</div>
          <a-input v-model:value="formData.productTitle" placeholder="产品名称" maxLength="20" style="width:350px"></a-input>
        </div>
        <div class="div-style">
          <div class="div-style">产品主图</div>
          <div class="div-style">
            <OpUploadMaterial :subGameId="subGameId" :materialManagerType="1" :materialImgType="102"
              v-model:value="productImage"></OpUploadMaterial>
          </div>
        </div>
        <div class="div-style">
          <div class="div-style">产品卖点</div>
          <a-input placeholder="请输入产品卖点" v-model:value="sellingPoint" style="width:350px"></a-input>
          <a-button @click="addSellingPoint">添加</a-button>
          <a-popover placement="right">
            <template #content>
              <a-list :data-source="sellingPointActionList">
                <template #renderItem="{ item }">
                  <a-list-item>
                    {{ item.actionContent }}
                    <template #actions>
                      <a @click="addActionSellingPoint(item.actionContent)">选择</a>
                      <a @click="deleteActionSellingPointById(item.id)">删除</a>
                    </template>
                  </a-list-item>
                </template>
              </a-list>
            </template>
            <a-button>选择卖点</a-button>
          </a-popover>
          <template v-for="(item, index) in formData.sellingPoints">
            <a-col>
              <a-space style="width:300px;margin-top:5px;">{{ item }}</a-space><a-button v-if="!props.formDisabled"
                type="link" @click="deleteSellingPoint(index)">删除</a-button>
            </a-col>
          </template>
        </div>
      </CollapseContainer>
      <CollapseContainer title="创意组件">
        <a-col :span="12">
          <a-form-item label="行动号召" :labelCol="{ style: 'width: 73px' }">
            <j-select-multiple placeholder="请选择行动号召" dictCode="call_to_action" v-model:value="callToActionButton" />
          </a-form-item>
        </a-col>
      </CollapseContainer>
      <CollapseContainer title="创意设置">
        <div class="div-style">
          <div class="div-style">
            广告评论
            <a-tooltip title="开启之后则允许用户在客户端中评论您的广告，抖音暂时不支持关闭广告评论功能" placement="right">
              <QuestionCircleFilled />
            </a-tooltip>
          </div>
          <a-radio-group v-model:value="formData.isCommentDisable" button-style="solid">
            <a-radio-button value="OFF">不启用</a-radio-button>
            <a-radio-button value="ON">启用</a-radio-button>
          </a-radio-group>
        </div>
      </CollapseContainer>
    </a-card>
  </PageWrapper>
  <PageWrapper title="广告预算与出价">
    <a-card>
      <div class="div-style">
        <div style="color: #323335">
          预算
        </div>
        <div style="margin-top:4px">
          <a-input style="width:150px" placeholder="请输入" v-model:value="formData.budget" @change="onChangeBudget">
            <template #suffix>
              元
            </template>
          </a-input>
          <div style="color: crimson;">
            {{ budgetTip }}
          </div>
        </div>
      </div>
      <div class="div-style">
        <div style="color: #323335">
          点击出价/展示出价
        </div>
        <div style="margin-top:4px">
          <a-input style="width:150px" placeholder="请输入" v-model:value="formData.bid" @change="onChangeBidTip">
            <template #suffix>
              元
            </template>
          </a-input>
          <div style="color: crimson;">
            {{ bidTip }}
          </div>
        </div>
      </div>
      <div class="div-style">
        <div style="color: #323335">
          目标转化出价
        </div>
        <div style="margin-top:4px">
          <a-input style="width:150px" placeholder="请输入" v-model:value="formData.cpaBid" @change="onChangeCpaBidTip">
            <template #suffix>
              元
            </template>
          </a-input>
          <div style="color: crimson;">
            {{ cpaBidTip }}
          </div>
        </div>
      </div>
      <div class="div-style">
        <div style="color: #323335">
          深度优化出价
        </div>
        <div style="margin-top:4px">
          <a-input style="width:150px" placeholder="请输入" v-model:value="formData.deepCpabid"
            @change="onChangeDeepCpabidTip">
            <template #suffix>
              元
            </template>
          </a-input>
          <div style="color: crimson;">
            {{ deepCpabidTip }}
          </div>
        </div>
      </div>
      <div class="div-style">
        <div style="color: #323335">
          深度转化roi系数
        </div>
        <div style="margin-top:4px">
          <a-input style="width:150px" placeholder="请输入" v-model:value="formData.roiGoal" @change="onChangeRoiGoalTip">
            <template #suffix>
              元
            </template>
          </a-input>
          <div style="color: crimson;">
            {{ roiGoalTip }}
          </div>
        </div>
      </div>

    </a-card>
  </PageWrapper>
  <PageWrapper title="基础信息">
    <a-card>
      <div v-for="(item, index) in nameList">
        <div class="div-style">
          广告名称({{ item['dealId'] }})
        </div>
        <div style="margin-top:4px">
          <a-input placeholder="请输入广告名" style="width:350px" v-model:value="nameList[index]['name']" maxLength="50"></a-input>
        </div>
      </div>
      <div class="div-style">
        广告状态
      </div>
      <div>
        <a-radio-group v-model:value="formData.operation" button-style="solid">
          <a-radio-button value="ENABLE">启用</a-radio-button>
          <a-radio-button value="OFF">不启用</a-radio-button>
        </a-radio-group>
      </div>
    </a-card>
  </PageWrapper>
  <!-- 创建广告信息框 -->
  <a-modal v-model:visible="createVisible" title="创建广告进度" :maskClosable="false" :destroyOnClose="true">
    <div style="padding: 20px; font-size: 16px">
      <div>
        <span>开始为</span>
        <span class="span-font-normal">&nbsp;{{ videoMaterialList.length }}&nbsp;</span>
        <span>条广告创建头条广告</span>
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
            广告&nbsp;{{ time.dealId }} -- {{ time.status == 200 ? '创建成功' : '创建失败, ' + time.msg }}
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
import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted, watch, } from 'vue';
import { useMessage } from '/@/hooks/web/useMessage';
import { BasicForm, FormSchema, useForm } from '/@/components/Form/index';
import { getValueType } from '/@/utils';
import { awemeSync, awemeList, saveOrUpdate, addAction, queryByActionType, deleteAction } from '../JrttPromotion.api';
import { Form } from 'ant-design-vue';
import { PageWrapper } from '/@/components/Page';
import { CollapseContainer } from '/@/components/Container/index';
import { BulbFilled, QuestionCircleFilled } from '@ant-design/icons-vue';
import Timeline from 'ant-design-vue/lib/timeline'; // 加载 JS
import Progress from 'ant-design-vue/lib/progress'; // 加载 JS
import Pagination from 'ant-design-vue/lib/pagination'; // 加载 JS
import TimelineItem from 'ant-design-vue/lib/timeline/TimelineItem'; // 加载 JS
import 'ant-design-vue/lib/progress/style/css'; // 加载 CSS
import 'ant-design-vue/lib/timeline/style/css'; // 加载 CSS
import 'ant-design-vue/lib/pagination/style/css'; // 加载 CSS
import JSelectMultiple from '/@/components/Form/src/jeecg/components/JSelectMultiple.vue';
import OpUploadMaterial from './OpUploadMaterial.vue';
import { queryById as materialQueryById} from '/@/views/advertManager/materialManager/opMaterial/OpMaterial.api';
import { getFileAccessHttpUrl, getScreenshotFrameBase64Url, getRandom } from '/@/utils/common/compUtils';
import axios from 'axios'
const visiablePromotionIdentity = ref<boolean>(false);
const accountId = ref<number>();
const subGameId = ref<number>();
const titleMaterial = ref<string>(); // 文案素材标题
const sellingPoint = ref<String>(); // 产品卖点
const callToActionButton = ref<String>(''); // 行动号召
let budgetTip = ref<string>(''); // 预算提示
let bidTip = ref<string>(''); // 点击出价/展示出价提示
let cpaBidTip = ref<string>(''); // 目标转化出价提示
let deepCpabidTip = ref<string>(''); // 深度优化出价提示
let roiGoalTip = ref<string>(''); // 深度转化roi系数提示
const awemeOptions = ref<Array<object>>([]); //抖音号下拉框
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

const props = defineProps({
  formDisabled: { type: Boolean, default: false },
  formData: { type: Object, default: () => { } },
  formBpm: { type: Boolean, default: true }
});

const useForm = Form.useForm;
const emit = defineEmits(['register', 'ok']);
const videoMaterialList = ref<Array<object>>([]); // 视频素材列表
const nameList = ref<Array<object>>([]); // 名字列表
const productImage = ref({ id: undefined }); // 产品图
const titleMaterialActionList = ref<Array<object>>([]); // 标题历史
const sellingPointActionList = ref<Array<object>>([]); // 产品卖点历史
const formData = reactive<Record<string, any>>({
  promotionIdentity: "ACCOUNTINFO",
  isFeedAndFavSee: true,
  dealId: [], //广告ids
  videoMaterialId: undefined, // 视频素材id
  videoMaterialFrameRate:undefined, // 视频素材帧
  videoMaterialScreenShot: undefined, // 视频封面图(base64)
  titleMaterialList: [], // 标题素材
  anchorRelatedType: "AUTO", //原生锚点
  productTitle: undefined, // 产品名称
  productImageId: undefined, //产品主图
  sellingPoints: [], //产品卖点
  callToActionButtons: [], //行动号召
  isCommentDisable: "OFF", //广告评论 ON启动 OFF不启用
  budget: undefined, // 预算
  bid: undefined, // 点击出价/展示出价提示
  cpaBid: ref<string>(''), // 目标转化出价
  deepCpabid: ref<string>(''), // 深度优化出价
  roiGoal: ref<string>(''), // 深度转化roi系数
  name: undefined, //广告名称
  id: undefined,
  operation: "ENABLE", // 广告状态
});

const { createMessage } = useMessage();
const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
const confirmLoading = ref<boolean>(false);
//表单验证
const validatorRules = {

};
const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: true });

// 表单禁用
const disabled = computed(() => {
  if (props.formBpm === true) {
    if (props.formData.disabled === false) {
      return false;
    } else {
      return true;
    }
  }
  return props.formDisabled;
});


/**
 * 新增
 */
function add(records) {
  nextTick(() => {
    resetFields();
    //清空进度条
    handleCreateCancel();
    //赋值
    visiablePromotionIdentity.value = false;
    videoMaterialList.value.length = 0;
    nameList.value.length = 0;
    for (let i = 0; i < records.length; i++) {
      videoMaterialList.value.push({
        dealName: records[i].dealName,
        dealId: records[i].id,
        id: undefined,
        screenshot: undefined,
        frameRate:undefined
      });
      nameList.value.push({
        dealId: records[i].id,
        name: records[i].dealName
      })
    }
    subGameId.value = records[0].subGameId;
    accountId.value = records[0].accountId;
    initOption();
  });
}

/**
 * 编辑
 */
function edit(record) {
  nextTick(async() => {
    resetFields();
    //清空进度条
    handleCreateCancel();
    if(record.awemeId){
      formData.promotionIdentity = "DOUYIN";
      visiablePromotionIdentity.value = true;
    }else{
      formData.promotionIdentity = "ACCOUNTINFO";
      visiablePromotionIdentity.value = false;
    }
    //赋值广告名和视频素材
    nameList.value.length = 0;
    let res = await materialQueryById({id:record.videoMaterialId});
    let res2 = await axios.get(getScreenshotFrameBase64Url(res.showUrl) + "?frameNumber=" + record.videoMaterialFrameRate)
    let screenshot = "";
    if (res2.status == 200) {
      screenshot = res2.data;
    } else {
      console.log("获取视频封面失败");
    }
    videoMaterialList.value=[{
      dealName: record.dealName,
      dealId: record.dealId,
      id: record.videoMaterialId,
      screenshot: screenshot,
      frameRate:record.videoMaterialFrameRate
    }];
   
    productImage.value = {id: record.imageIds};
     //产品主题
    nameList.value.push({
      dealId: record.dealId,
      name: record.name
    });
    subGameId.value = record.subGameId;
    accountId.value = record.accountId;
    // 字段赋值
    Object.assign(formData, record);
    formData.sellingPoints = record.sellingPoints.split(",");
    formData.titleMaterialList = record.titleMaterialList.split(",");
    callToActionButton.value = record.callToActionButtons;
    initOption();
  });
}

/**
 * 初始化下拉框
 */
function initOption(){
    // 获取抖音授权账号列表
    awemeList({accountId : accountId.value}).then(res=>{
      formData.awemeId = "";
      awemeOptions.value = [];
      if (res.total > 0) {  
        for (let r of res.records) {
          awemeOptions.value.push({
            value: r.awemeId,
            label: r.awemeName
          });
        }
      }
    })
    titleMaterialActionList.value.length = 0;
    sellingPointActionList.value.length = 0;
    // 获取用户行为历史
    queryByActionType({}).then(res => {
      if (!res || res.length < 1) {
        return;
      }
      for (let i = 0; i < res.length; i++) {
        if (res[i].actionType == 1) {
          // 标题历史记录
          titleMaterialActionList.value.push(res[i]);
        } else if (res[i].actionType == 2) {
          // 产品卖点历史记录
          sellingPointActionList.value.push(res[i]);
        }
      }
    })
}


/**
 * 提交数据
 */
async function submitForm() {
  handleCreateCancel();
  let isUpdate = false;
  let model = formData;
  if (model.id) {
    isUpdate = true;
  }
  // 设置产品主图
  model.productImageId = productImage.value.id;
  // 校验推广身份
  if (model.promotionIdentity == "DOUYING" && !model.awemeId) {
    createMessage.warn("抖音号不能为空");
    return;
  }
  // 校验素材封面
  for (let videoMaterial of videoMaterialList.value) {
    const dealId = videoMaterial['dealId'];
    if (!videoMaterial['id']) {
      createMessage.warn(`广告[${dealId}]的视频素材不能为空`);
      return;
    }
    if (!videoMaterial['screenshot']) {
      createMessage.warn(`广告[${dealId}]的视频素材封面不能为空`);
      return;
    }
  }
  // 校验标题
  if (model.titleMaterialList.length == 0) {
    createMessage.warn("标题不能为空");
    return;
  }
  // 校验产品名称
  if (!model.productTitle || model.productTitle.length > 20) {
    createMessage.warn("产品名称，字数限制：[1-20]");
    return;
  }
  // 校验产品主图
  if (!model.productImageId) {
    createMessage.warn("产品主图不能为空");
    return;
  }
  // 校验产品卖点
  if (model.sellingPoints.length == 0) {
    createMessage.warn("产品卖点不能为空");
    return;
  }
  // 行动号召
  if (!callToActionButton) {
    createMessage.warn("行动号召文案不能为空");
    return;
  }
  // 校验预算出价
  if (model.budget && (model.budget < 100 || model.budget > 9999999.99)) {
    createMessage.warn("预算范围，不少于100元，不超过9999999.99元");
    return;
  } 
  if (model.bid && (model.bid > 100 || model.bid < 0.1)) {
    createMessage.warn("点击出价/展示出价，不少于0.1元，不超过100元");
    return;
  }
  if (model.cpaBid && (model.cpaBid > 10000 || model.cpaBid < 0.1)) {
    createMessage.warn("目标转化出价/预期成本，不少于0.1元，不超过10000元");
    return;
  }
  if (model.deepCpabid && (model.deepCpabid > 10000 || model.deepCpabid < 0.1)) {
    createMessage.warn("深度优化出价，不少于0.1元，不超过10000元");
  }
  if (model.roiGoal && (model.roiGoal > 5 || model.roiGoal < 0)) {
    createMessage.warn("深度转化ROI系数,范围(0,5]");
  }
  // 
  model.callToActionButtons = callToActionButton.value.split(",");
  createVisible.value = true;
  for (let i = 0; i < videoMaterialList.value.length; i++) {
    model.videoMaterialId = videoMaterialList.value[i]['id'];
    model.videoMaterialScreenShot = videoMaterialList.value[i]['screenshot'];
    model.videoMterialFrameRate = videoMaterialList.value[i]['frameRate'];
    model.dealId = videoMaterialList.value[i]['dealId'];
    for (let j = 0; j < nameList.value.length; j++) {
      // 获取广告名称
      if (nameList.value[j]['dealId'] == model.dealId) {
        model.name = nameList.value[j]['name'];
        break;
      }
    }
    await saveOrUpdate(model, isUpdate)
      .then((res) => {
        if (res.success) {
          createSuccess.value++;
          timelineDataList.value.push({ dealId: model.dealId, status: res.status, msg: res.message })
        } else {
          createFail.value++;
          timelineDataList.value.push({ dealId: model.dealId, status: res.status, msg: res.message })
        }
      })
      .finally(() => {

      });
    dealProcess.value++;
    var per = dealProcess.value / videoMaterialList.value.length;
    dealProcessPer.value = Math.floor(per * 100);
  }
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

/**
 * 推广身份更改事件
 */
function changePromotionIdentity() {
  if (formData.promotionIdentity == "DOUYING") {
    visiablePromotionIdentity.value = true;
  } else {
    visiablePromotionIdentity.value = false;
  }
}
/**
 * 添加标题素材
 */
function addTitleMaterial() {
  if (!titleMaterial.value || getStrLength(titleMaterial.value) > 30 || getStrLength(titleMaterial.value) < 5) {
    createMessage.warn("标题长度需要为5-30个字, 两个英文字符占1位。");
    return;
  }
  formData.titleMaterialList.push(titleMaterial.value);
  // 添加标题历史记录
  const action = {
    actionType: 1,
    actionContent: titleMaterial.value,
  }
  addAction(action);
  titleMaterial.value = "";
}

/**
 * 把行为填到标题列表中
 */
function addActionTitleMaterial(content) {
  formData.titleMaterialList.push(content);
}

/**
 * 删除标题素材
 */
function deleteTitleMaterial(index) {
  formData.titleMaterialList.splice(index, 1);
}

/**
 * 删除行为
 */
function deleteActiontitleMaterialById(id) {
  deleteAction({ id }).then(res => {
    for (let i = 0; i < titleMaterialActionList.value.length; i++) {
      if(titleMaterialActionList.value[i]['id'] == id){
        titleMaterialActionList.value.splice(i, 1);
      }
    }
  });
}

/**
 * 删除产品卖点
 */
function deleteActionSellingPointById(id){
  deleteAction({ id }).then(res => {
    for (let i = 0; i < sellingPointActionList.value.length; i++) {
      if(sellingPointActionList.value[i]['id'] == id){
        sellingPointActionList.value.splice(i, 1);
      }
    }
  });
}

/**
 * 添加产品卖点
 */
function addSellingPoint() {
  if (!sellingPoint.value || getStrLength(sellingPoint.value) > 9 || getStrLength(sellingPoint.value) < 6) {
    createMessage.warn("产品卖点，字数限制：[6-9]");
    return;
  }
  formData.sellingPoints.push(sellingPoint.value);
  // 添加标题历史记录
  const action = {
    actionType: 2,
    actionContent: sellingPoint.value,
  }
  addAction(action);
  sellingPoint.value = "";
}


/**
 * 把行为填到产品卖点列表中
 */
function addActionSellingPoint(content) {
  formData.sellingPoints.push(content);
}
/**
 * 删除产品卖点
 */
function deleteSellingPoint(index) {
  formData.sellingPoints.splice(index, 1);
}

/**
 * 更改预算
 */
function onChangeBudget(e) {
  let value = e.target.value - 0;
  if (value < 100 || value > 9999999.99) {
    budgetTip.value = "预算范围，不少于100元，不超过9999999.99元"
  } else {
    budgetTip.value = ""
  }
}

/**
 * 更改点击出价/展示出价
 */
function onChangeBidTip(e) {
  let value = e.target.value - 0;
  if (value > 100 || value < 0.1) {
    bidTip.value = "点击出价/展示出价，不少于0.1元，不超过100元"
  } else {
    bidTip.value = ""
  }
}

/**
 * 更改目标转化出价/预期成本
 */
function onChangeCpaBidTip(e) {
  let value = e.target.value - 0;
  if (value > 10000 || value < 0.1) {
    cpaBidTip.value = "目标转化出价/预期成本，不少于0.1元，不超过10000元"
  } else {
    cpaBidTip.value = ""
  }
}

/**
 * 更改深度优化出价
 */
function onChangeDeepCpabidTip(e) {
  let value = e.target.value - 0;
  if (value > 10000 || value < 0.1) {
    deepCpabidTip.value = "深度优化出价，不少于0.1元，不超过10000元"
  } else {
    deepCpabidTip.value = ""
  }
}

/**
 * 更改深度优化出价
 */
function onChangeRoiGoalTip(e) {
  let value = e.target.value - 0;
  if (value > 5 || value < 0) {
    roiGoalTip.value = "深度转化ROI系数,范围(0,5]"
  } else {
    roiGoalTip.value = ""
  }
}

/**
 * 计算字符串的长度 中文算一个 英文算半个
 */
function getStrLength(str) {
  let len = 0;
  if (!str) {
    return len;
  }
  for (var i = 0; i < str.length; i++) {
    if (str.charCodeAt(i) > 127 || str.charCodeAt(i) == 94) {
      len++;
    } else {
      len += 0.5;
    }
  }
  return len;
}

/**
 * 同步账户下抖音号授权关系以及授权视频
 */
function syncAweme() {
  awemeSync({ accountId: accountId.value }).then(res => {
    formData.awemeId = "";
    awemeOptions.value = [];
    if (res.length > 0) {  
      for (let r of res) {
        awemeOptions.value.push({
          value: r.awemeId,
          label: r.awemeName
        });
      }
      createMessage.success("同步成功");
    }else{
      createMessage.warn("该账号下没有抖音号");
    }
  });
}


defineExpose({
  add,
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

;

.div-style {
  margin-top: 16px;
}

.radio-button {
  width: 208px;
  height: 70px;
  margin-left: 5px;
}

.week-td {
  width: 75px;
  padding: 20px 0
}

.byted-weektime.calendar {
  -webkit-user-select: none;
  position: relative;
  display: inline-block;
  margin-top: 6px;
}

.calendar-atom-time:hover {
  background: #ccc
}

.td-table-tip {
  line-height: 2.4em;
  padding: 0 12px 0 19px;
  background: #fff !important
}

.td-table-tip .clearfix {
  height: 46px;
  line-height: 46px
}

.td-table-tip .pull-left {
  font-size: 14px;
  color: #333333
}

.ui-selected {
  background: #2F88FF
}

.ui-selected:hover {
  background: #2F88FF
}
</style>
