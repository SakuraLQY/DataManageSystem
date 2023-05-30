<template>
  <div>
    <a-modal
      :title="title"
      :width="width"
      :visible="visible"
      style="top: 20px"
      :maskClosable="false"
      :okButtonProps="{ class: { 'jee-hidden': disableSubmit } }"
      @cancel="handleCancel"
    >
      <template #footer>
        <a-button @click="handleCancel">关闭</a-button>
        <a-popconfirm placement="top" ok-text="确定" cancel-text="取消" @confirm="confirm" title="确定开始创建站点吗?">
          <a-button type="primary">创建站点</a-button>
        </a-popconfirm>
      </template>
      <div>
        <!-- 查询区域 -->
        <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-row style="width: 900px; padding: 24px 24px 0px 24px">
            <GameOptionForm ref="selectForm" @handler="getGameVal"></GameOptionForm>
            <a-col :lg="6">
              <a-form-item label="创建人">
                <j-search-select placeholder="请选择创建人" v-model:value="queryParam.createBy" dict="sys_user,username,username" allowClear />
              </a-form-item>
            </a-col>
            <a-col :lg="2">
              <a-form-item>
                <a-button type="primary" preIcon="ant-design:search-outlined" @click="searchQuery">查询</a-button>
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
        <!-- 显示区域 -->
        <div id="materialDiv">
          <a-row :gutter="16">
            <a-col style="padding-bottom: 30px" :span="6" v-for="site in siteDataList">
              <!-- 卡片 -->
              <a-card
                :title="site.siteName"
                :class="{ on_unit: chooseSite == site.id }"
                @click="chooseSiteData(site.id, site.siteName)"
                :hoverable="true"
                :bordered="false"
                :bodyStyle="cardBodyStyle"
                :headStyle="chooseSite == site.id ? cardHeaderClickStyle : cardHeaderStyle"
              >
                <!-- 卡片body限制区域 -->
                <div class="template-thumb">
                    <!-- 是否浮动 -->
                    <div :class="{ float_top: siteContent.float == 'top', float_bottom: siteContent.float == 'bottom' }" 
                      v-for="siteContent in JSON.parse(site.siteContent)">
                      <!-- 下载按钮 -->
                      <div
                        :style="{ 'text-align': 'center', 'background-color': siteContent.bg_color, color: siteContent.color }"
                        v-if="siteContent.name == 'XrButton' && siteContent.image_url == undefined"
                      >
                        {{ siteContent.text }}
                      </div>
                      <!-- 图片 -->
                      <img :src="siteContent.image_url" v-else-if="siteContent.image_url != undefined" />
                      <img :src="siteContent.group_content[0]['image_url']" v-else />
                    </div>
                </div>
              </a-card>
            </a-col>
          </a-row>
        </div>
        <!-- 分页 -->
        <div style="padding: 10px">
          <a-row :gutter="16" type="flex" justify="space-around" align="middle">
            <a-col :span="8">
              <span style="font-size: 16px">当前选择：</span>
              <a-tag color="#f50" style="font-size: 16px">{{ chooseSiteName }}</a-tag>
            </a-col>
            <a-col :span="8">
              <Pagination v-model:current="currentPage" :total="totalData" show-less-items @change="changePage"  defaultPageSize="8"/>
            </a-col>
          </a-row>
        </div>
      </div>
    </a-modal>
    <!-- 创建站点信息框 -->
    <a-modal v-model:visible="createVisible" title="创建站点进度" :maskClosable="false" :destroyOnClose="true">
      <div style="padding: 20px; font-size: 16px">
        <div>
          <span>开始为</span>
          <span class="span-font-normal">&nbsp;{{ createDealList.length }}&nbsp;</span>
          <span>条广告创建站点</span>
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
            <TimelineItem :color="time.status == 200 ? 'green' : 'red'" v-for="time in timelineDataList">
              广告&nbsp;{{ time.dealId }} -- {{ time.status == 200 ? '创建成功' : '创建失败, ' + time.msg}}
            </TimelineItem>
          </Timeline>
        </div>
      </div>
      <template #footer>
        <a-button key="back" @click="handleCreateCancel">关闭</a-button>
      </template>
    </a-modal>
  </div>
</template>

<script lang="ts" setup>
  import { ref, nextTick, defineExpose, reactive, defineProps } from 'vue';
  import { siteList, siteSave } from '../JrttDeal.api';
  import { message } from 'ant-design-vue';
  import Timeline from 'ant-design-vue/lib/timeline'; // 加载 JS
  import Progress from 'ant-design-vue/lib/progress'; // 加载 JS
  import Pagination from 'ant-design-vue/lib/pagination'; // 加载 JS
  import TimelineItem from 'ant-design-vue/lib/timeline/TimelineItem'; // 加载 JS
  import 'ant-design-vue/lib/progress/style/css'; // 加载 CSS
  import 'ant-design-vue/lib/timeline/style/css'; // 加载 CSS
  import 'ant-design-vue/lib/pagination/style/css'; // 加载 CSS
  import GameOptionForm from '/@/views/common/gameOptionForm.vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';

  const props = defineProps({
    successCallback: { type: Function, default: null },
    siteSave: { type: Function, default: null },
  });

  const labelCol = reactive({
    xs: { span: 24 },
    sm: { span: 7 },
  });
  const wrapperCol = reactive({
    xs: { span: 24 },
    sm: { span: 16 },
  });

  const selectForm = ref();
  const title = ref<string>('');
  const width = ref<number>(1000);
  const visible = ref<boolean>(false);
  const createVisible = ref<boolean>(false);
  const disableSubmit = ref<boolean>(false);
  const emit = defineEmits(['register', 'success']);
  // 选择的站点
  const chooseSite = ref<number>(0);
  const chooseSiteName = ref<string>('');
  // 当前页数
  const currentPage = ref<number>(1);
  // 总条数
  const totalData = ref<number>(1);
  // 站点数据列表
  const siteDataList = ref<Array>([]);
  // 查询参数
  const queryParam = ref<any>({});
  const handleSuccess = ref<any>({});
  // 选择的广告ids
  const dealDataList = ref<Array>([]);
  // 创建站点的广告ID
  const createDealList = ref<Array>([]);
  // 创建进度
  const dealProcess = ref<number>(0);
  const dealProcessPer = ref<number>(0);
  // 成功
  const createSuccess = ref<number>(0);
  // 失败
  const createFail = ref<number>(0);
  // 时间线
  const timelineDataList = ref<Array>([]);
  // 卡片标题样式
  const cardHeaderStyle = { 'text-align': 'center', 'font-size': '20px', 'font-weight': 'bold' };
  // 卡片标题被选中样式
  const cardHeaderClickStyle = { 'text-align': 'center', 'font-size': '20px', 'font-weight': 'bold', 'background-color': '#f50' };
  // 卡片body样式
  const cardBodyStyle = { padding: '0' };
  // 选择游戏/子游戏
  let getGameVal = (e: any) => {
    queryParam.value.gameId = e.gameId;
    queryParam.value.subGameId = e.subGameId;
  };

  init();
  /**
   * 数据初始化
   */
  function init() {
    siteList().then((res: any) => {
      siteDataList.value = res.records;
      totalData.value = res.total;
      currentPage.value = res.current;
    });
  }

  /**
   * 选择页数
   */
  function changePage(current, total) {
    queryParam.value.pageNo = current;
    siteList(queryParam.value).then((res: any) => {
      siteDataList.value = res.records;
      totalData.value = res.total;
      currentPage.value = res.current;
    });
  }

  /**
   * 选择页数
   */
  function searchQuery() {
    changePage(1, 8);
  }
  
  /**
   * 选择站点
   */
  function chooseSiteData(id, name) {
    chooseSite.value = id;
    chooseSiteName.value = name;
  }

  /**
   * 创建站点
   */
  const confirm  = (e: MouseEvent) => {
      createDealList.value = dealDataList.value;
      if(createDealList.value.length <= 0) {
        message.error('未选择广告');
        return;
      }
      if(chooseSite.value == 0) {
        message.error('未选择站点');
        return;
      }
      createVisible.value = true;
      createSite()
    };

  /**
   * 取消按钮回调事件
   */
  function handleCancel() {
    visible.value = false;
    props.successCallback()
  }

  /**
   * 批量创建站点
   */
  function createSite() {
    for(let i = 0; i < createDealList.value.length; i++) {
      props.siteSave({dealId: createDealList.value[i], siteId: chooseSite.value}).then((res: any) => {
        if(res.status == 200) {
          createSuccess.value ++;
          timelineDataList.value.push({dealId: createDealList.value[i], status: res.status, msg: res.message})
        } else {
          createFail.value ++;
          timelineDataList.value.push({dealId: createDealList.value[i], status: res.status, msg: res.message})
        }
        dealProcess.value ++;
        var per = dealProcess.value / createDealList.value.length;
        dealProcessPer.value = Math.floor(per * 100);
      });
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
    createDealList.value = [];
    timelineDataList.value = [];
    createVisible.value = false;
  }

  defineExpose({
    disableSubmit,
    visible,
    dealDataList
  });
</script>

<style>
/**隐藏样式-modal确定按钮 */
.jee-hidden {
  display: none !important;
}
.span-font-normal {
  color: #108ee9;
  font-weight: bold
}
.span-font-error {
  color: #ff4d4f;
  font-weight: bold
}
.span-font-success {
  color: #87d068;
  font-weight: bold
}
#materialDiv {
  margin-top: 10px;
  width: 100%;
  height: 710px;
  overflow-y: auto;
  border: 1px dashed #cdcdcd;
  background-color: #ececec;
  padding: 20px 20px 0px 20px;
}
#materialDiv .ant-card {
  border-radius: 10px;
  transition: all 0.2s linear;
}
#materialDiv .ant-card:hover {
  transform: scale(1.01, 1.01);
}
#materialDiv .material_unit {
  height: 300px;
}
#materialDiv .on_unit {
  box-shadow: 0px 0px 15px #f50;
}
#materialDiv .template-thumb {
  width: 100%;
  height: 250px;
  overflow-y: auto;
  position: relative;
}
.time-line {
  padding: 20px;
  height: 300px;
  overflow-y: auto;
}
#materialDiv .template-thumb img {
  width: 100%;
}
#materialDiv .template-thumb .float_top {
  position: sticky;
  top: 0;
  left: 0;
}
#materialDiv .template-thumb .float_bottom {
  position: sticky;
  bottom: 0;
  left: 0;
}
</style>
