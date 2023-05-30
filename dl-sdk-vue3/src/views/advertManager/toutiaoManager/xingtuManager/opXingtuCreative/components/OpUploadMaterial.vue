<template>
  <div class="material-div">
    <div style="text-align:center">{{ title }}</div>
    <a-upload :listType="listType" :multiple="multiple" :action="uploadUrl" :headers="headers" :data="{ biz: bizPath }"
      v-model:fileList="uploadFileList" :beforeUpload="beforeUpload" :disabled="disabled" @change="handleChange"
      @preview="handlePreview" :openFileDialogOnClick="false">
      <div class="ant-upload-div" v-if="uploadVisible" @click="onClickUpload">
        <div v-if="listType == 'picture-card'">
          <LoadingOutlined v-if="loading" />
          <UploadOutlined v-else />
          <div class="ant-upload-text">{{ text }}</div>
        </div>
        <a-button v-if="listType == 'picture'" :disabled="disabled">
          <UploadOutlined></UploadOutlined>
          {{ text }}
        </a-button>
      </div>
    </a-upload>
    <a-button class="change-video-button" type="primary" 
      @click="changeVideoCover">设置封面</a-button>
    <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel()">
      <video  controls alt="example" style="width: 100%" :src="previewImage" />
    </a-modal>
    <!-- 选择素材弹窗 -->
    <a-modal title="选择素材" :body-style="bodystyle" width="1000px" :visible="pictureListVisible" @cancel="handlePictureListCancel()" @ok="handleOk">
      <!--查询区域-->
      <div>
        <a-form @keyup.enter.native="searchMaterialQuery" :model="queryMaterialParam" :label-col="labelCol"
          :wrapper-col="wrapperCol">
          <a-row style="width: 900px; padding: 24px 24px 0px 24px">
            <a-col :lg="8">
              <a-form-item label="素材名">
                <a-input placeholder="请输入素材名" v-model:value="queryMaterialParam.materialName"
                   allowClear></a-input>
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="素材类型">
                <j-search-select placeholder="请选择素材类型" v-model:value="queryMaterialParam.type2" dict="material_video_type" allowClear/>
              </a-form-item>
            </a-col>
            <GameOptionForm ref="selectForm" @handler="getGameVal"></GameOptionForm>
            <a-col :lg="8">
              <a-button type="primary" preIcon="ant-design:search-outlined" @click="searchMaterialQuery">查询</a-button>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <div id="materialDiv">
        <a-row :gutter="16">
          <a-col style="padding-bottom: 30px" :span="6" v-for="item in imageList">
            <!-- 卡片 -->
            <a-card :title="item.materialName" :class="{ on_unit: choosePic == item.showUrl }"
              @click="choosePicData(item.showUrl, item.materialName, item.id)" :hoverable="true" :bordered="false"
              :bodyStyle="cardBodyStyle" :headStyle="choosePic == item.showUrl ? cardHeaderClickStyle : cardHeaderStyle">
              <!-- 卡片body限制区域 -->
              <div class="template-thumb">
                <video controls alt="example" :src="getFileAccessHttpUrl(item.showUrl)" />
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
            <a-tag color="#f50" style="font-size: 16px">{{ choosePicName }}</a-tag>
          </a-col>
          <a-col :span="8">
            <Pagination v-model:current="currentPage" :total="totalData" show-less-items @change="changePage"
              :pageSize="8" />
          </a-col>
        </a-row>
      </div>
    </a-modal>
    <!-- 更换视频封面弹窗 -->
    <a-modal title="选择视频封面" :body-style="bodystyle" width="1000px" :visible="screenshotListVisible" @ok="onClickScreenshotCard" @cancel="handlescreenshotCancel()">
      <a-spin :spinning="screenshotLoading">
        <div id="materialDiv">
          <a-row :gutter="16">
            <a-col style="padding-bottom: 30px" :span="6" v-for="item in screenshotImageList">
              <!-- 卡片 -->
              <a-card :title="'第'+item.frameRate+'帧'" :class="{ on_unit: chooseScreenshot == item.value }"
                @click="chooseScreenshotData(item)" :hoverable="true" :bordered="false" :bodyStyle="cardBodyStyle"
                :headStyle="chooseScreenshot == item.value ? cardHeaderClickStyle : cardHeaderStyle">
                <!-- 卡片body限制区域 -->
                <div class="template-thumb">
                  <img alt="example" :src="item.showUrl" />
                </div>
              </a-card>
            </a-col>
          </a-row>
        </div>
      </a-spin>
    </a-modal>
  </div>
</template>
<script lang="ts">
import { defHttp } from '/@/utils/http/axios';
import axios from 'axios'
import { defineComponent, PropType, ref, reactive, watchEffect, computed, unref, watch, onMounted } from 'vue';
import { LoadingOutlined, UploadOutlined } from '@ant-design/icons-vue';
import { useRuleFormItem } from '/@/hooks/component/useFormItem';
import { propTypes } from '/@/utils/propTypes';
import { useAttrs } from '/@/hooks/core/useAttrs';
import { useMessage } from '/@/hooks/web/useMessage';
import { getFileAccessHttpUrl, getScreenshotBase64Url, getRandom } from '/@/utils/common/compUtils';
import { uploadUrl } from '/@/api/common/api';
import { getToken } from '/@/utils/auth';
import { list, queryByIdList } from '/@/views/advertManager/materialManager/opMaterial/OpMaterial.api';
import Pagination from 'ant-design-vue/lib/pagination'; // 加载 JS
import 'ant-design-vue/lib/pagination/style/css'; // 加载 CSS
import GameOptionForm from '/@/views/common/gameOptionForm.vue';
import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';

const { createMessage, createErrorModal } = useMessage();
const selectForm= ref<any>();
export default defineComponent({
  name: 'JImageUpload',
  components: { LoadingOutlined, UploadOutlined, Pagination, JSearchSelect, GameOptionForm },
  inheritAttrs: false,
  props: {
    //绑定值
    value: propTypes.oneOfType([propTypes.object]),
    //按钮文本
    listType: {
      type: String,
      required: false,
      default: 'picture-card',
    },
    //按钮文本
    title: {
      type: String,
      required: false,
      default: '标题',
    },
    //按钮文本
    text: {
      type: String,
      required: false,
      default: '上传',
    },
    //这个属性用于控制文件上传的业务路径
    bizPath: {
      type: String,
      required: false,
      default: 'temp',
    },
    //是否禁用
    disabled: {
      type: Boolean,
      required: false,
      default: false,
    },
    //上传数量
    fileMax: {
      type: Number,
      required: false,
      default: 1,
    },
    // 子游戏id（查询条件）
    // subGameId: {
    //   type: Number,
    //   required: true,
    //   default: 0,
    // },
    // 头条账号id（查询条件）
    jrttAccountId: {
      type: Number,
      required: true,
      default: 0,
    },
    // materialManagerType: {
    //   type: Number,
    //   required: true,
    //   default: 2,
    // },
    materialImgType: {
      type: Number,
      required: true,
      default: 0,
    },
  },
  emits: ['options-change', 'change', 'update:value'],
  setup(props, { emit, refs }) {
    const emitData = ref<any[]>([]);
    const attrs = useAttrs();
    const labelCol = reactive({
      span: 5,
      offset: 1
    });
    const wrapperCol = reactive({
      span: 18
    });
    const listGrid = { gutter: 16, xs: 1, sm: 2, md: 4, lg: 4, xl: 6, xxl: 4, xxxl: 2 };
    const [state] = useRuleFormItem(props, 'value', 'change', emitData);
    //获取文件名
    const getFileName = (path) => {
      if (path.lastIndexOf('\\') >= 0) {
        let reg = new RegExp('\\\\', 'g');
        path = path.replace(reg, '/');
      }
      return path.substring(path.lastIndexOf('/') + 1);
    };
    //token
    const headers = ref<object>({
      'X-Access-Token': getToken(),
    });
    const bodystyle = {
      height: '700px',
      overflow: 'hidden',
      overflowY: 'scroll',
    }
    //上传状态
    const loading = ref<boolean>(false);
    //是否是初始化加载
    const initTag = ref<boolean>(true);
    //文件列表
    let uploadFileList = ref<any[]>([]);
    //预览图
    const previewImage = ref<string | undefined>('');
    //预览框状态
    const previewVisible = ref<boolean>(false);
    //图片列表截图弹窗状态
    const pictureListVisible = ref<boolean>(false);
    //视频截图列表弹窗状态
    const screenshotListVisible = ref<boolean>(false);
    //视频截图列表弹窗 loading
    const screenshotLoading = ref<boolean>(false);
    // 素材表的type1
    const materialManagerType = 2;
    // 素材表的type2
    const materialImgType = props['materialImgType'];
    //卡片标题样式
    const cardHeaderStyle = { 'text-align': 'center', 'font-size': '20px', 'font-weight': 'bold' };
    //卡片标题被选中样式
    const cardHeaderClickStyle = { 'text-align': 'center', 'font-size': '20px', 'font-weight': 'bold', 'background-color': '#f50' };
    //选择的图片
    const choosePic = ref<String>('');
    //选择图片名称
    const choosePicName = ref<String>('');
    //选择图片id
    const choosePicId = ref<Number>();
    //选择截图名称
    const chooseScreenshotRate = ref<String>('');
    //选择截图内容
    const chooseScreenshot = ref<String>('');
    // 卡片body样式
    const cardBodyStyle = { padding: '0' };
    // 当前页数
    const currentPage = ref<number>(1);
    // 总条数
    const totalData = ref<number>(1);
    //计算是否开启多图上传
    const multiple = computed(() => {
      return props['fileMax'] > 1;
    });

    //计算是否可以继续上传
    const uploadVisible = computed(() => {
      return uploadFileList.value.length < props['fileMax'];
    });

    //选择图片list
    const imageList = ref<any[]>([]);
    //查询素材
    const queryMaterialParam = ref<any>({ pageNo: 1, pageSize: 8 });
    let getGameVal = (e:any) => {
      queryMaterialParam.value.gameId = e.gameId;
      queryMaterialParam.value.subGameId = e.subGameId;
    }
    //视频截图list
    const screenshotImageList = ref<any[]>([]);

    /**
     * 监听value变化
     */
    watch(
      () => props.value,
      (val, prevCount) => {
        if (initTag.value == true) {
          console.log("changeFileList")
          initFileList(val);
        }
      },
      {immediate: true}
    );

    function getBase64(file: File) {
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
      });
    }

    /**
     * 初始化 只支持单个
     */
    function initFileList(material) {
      let materialId = material.id;
      let screenshot = "";
      if (material.screenshot) {
        screenshot = "data:image/png;base64," + material.screenshot;
      }
      if (!materialId || materialId.length == 0) {
        uploadFileList.value = [];
        return;
      }
      let files = [];
      queryByIdList({ ids: materialId }).then((res) => {
        res.forEach(async (value) => {
          let url = getFileAccessHttpUrl(value.showUrl);
          // TODO 回显视频封面这里还有点问题
          // if(!material.screenshot && material.id && material.frameRate){
          //   // 编辑赋值 视频封面
          //   let res = await axios.get(getScreenshotBase64Url(value.showUrl) + "?frameRate=" + material.frameRate)
          //   if (res.status == 200) {
          //     screenshot = "data:image/png;base64," + res.data[0];
          //   } else {
          //     console.log("获取视频封面失败");
          //   }
          // }
          files.push({
            showUrl: value.showUrl,
            uid: getRandom(10),
            name: getFileName(value.showUrl),
            status: 'done',
            url: url,
            thumbUrl: screenshot,
            response: {
              status: 'history',
              message: value,
            },
            preview: ""
          });
        })
        uploadFileList.value = files;
      });
    }

    /**
     * 上传前校验
     */
    function beforeUpload(file) {
      let fileType = file.type;
      if (fileType.indexOf('image') < 0) {
        createMessage.info('请上传图片');
        return false;
      }
    }
    /**
     * 文件上传结果回调
     */
    function handleChange({ file, fileList, event }) {
      // initTag.value = false;
      uploadFileList.value = fileList;
      if (file.status === 'error') {
        createMessage.error(`${file.name} 上传失败.`);
      }
      let fileUrls = [];
      //上传完成
      if (file.status != 'uploading') {
        fileList.forEach((file) => {
          if (file.status === 'done') {
            fileUrls.push(file.response.message);
          }
        });
        if (file.status === 'removed') {
          handleDelete(file);
        }
      }
      // emitData.value = fileUrls.join(',');
      state.value = fileUrls.join(',');
      emit('update:value', fileUrls.join(','));
    }

    /**
     * 删除图片
     */
    function handleDelete(file) {
      //如有需要新增 删除逻辑
      console.log(file);
    }

    /**
     * 预览图片
     */
    function handlePreview(file) {
      previewImage.value = file.url || file.thumbUrl;
      previewVisible.value = true;
    }

    function getAvatarView() {
      if (uploadFileList.length > 0) {
        let url = uploadFileList[0].url;
        return getFileAccessHttpUrl(url, null);
      }
    }

    function handleCancel() {
      previewVisible.value = false;
    }

    function handlePictureListCancel() {
      pictureListVisible.value = false;
    }
    function handlescreenshotCancel() {
      screenshotListVisible.value = false;
    }


    /**
     * 点击上传按钮
     */
    async function onClickUpload() {
      if (uploadFileList.value.length == 0) {
        // 设置子游戏id
        // queryMaterialParam.value.subGameId = props['subGameId'];
        queryMaterialParam.value.type1 = 2;
        // if (props['materialImgType'] != 0) {
        //   queryMaterialParam.value.type2 = props['materialImgType'];
        // }
        queryMaterialParam.value.jrttAccountId = props['jrttAccountId'];
        queryMaterialParam.value.jrttStatus = 0;
        let result = await list(queryMaterialParam.value);
        imageList.value = [];
        if (result.size != 0) {
          imageList.value = result.records;
        }
        pictureListVisible.value = true;
      }
    }

    /**
     * 点击视频封面卡片
     * @param item 
     */
    function onClickScreenshotCard() {
      emit('update:value', { 
        id: props.value?.id, 
        screenshot: chooseScreenshot.value,
        frameRate: chooseScreenshotRate.value,
        dealId: props.value?.dealId, 
        dealName: props.title 
      });
      uploadFileList.value[0].thumbUrl = "data:image/png;base64," + chooseScreenshot.value;
      screenshotListVisible.value = false;
    }

    /**
      * 点击图片操作
      */
    function choosePicData(showUrl, materialName, id) {
      choosePic.value = showUrl;
      choosePicName.value = materialName;
      choosePicId.value = id;
    }

    /**
     * 选择截屏操作
     */
    function chooseScreenshotData(item) {
      chooseScreenshot.value = item.value;
      chooseScreenshotRate.value = item.frameRate;
    }

    /**
     * 选择页数
     */
    function changePage() {
      queryMaterialParam.value.pageNo = currentPage.value;
      searchMaterialQuery();
    }

    /**
     * 确认选择素材
     */
    async function handleOk() {
      uploadFileList.value = [{ thumbUrl: getFileAccessHttpUrl(choosePic.value) }];
      // emit('update:value', choosePic.value);
      pictureListVisible.value = false;
      let thumbUrl = getFileAccessHttpUrl(choosePic.value);
      let showUrl = choosePic.value;
      let emitVal = { 
        id: choosePicId.value,
        dealId: props.value?.dealId,
        dealName: props.title,
        frameRate: 0,
      };
      // 如果是视频类型需要取第一帧作为默认封面
      let res = await axios.get(getScreenshotBase64Url(choosePic.value) + "?frameRate=1");
      if (res.status == 200) {
        thumbUrl = "data:image/png;base64," + res.data[0];
        showUrl = choosePic.value;
      } else {
        createMessage.error("获取视频封面失败");
      }
      emitVal['screenshot'] = res.data[0];
      uploadFileList.value = [{
        thumbUrl,
        showUrl
      }];
      emit('update:value', emitVal);
      pictureListVisible.value = false;
    }

    /**
     * 查看素材
     */
    function searchMaterialQuery() {
      list(queryMaterialParam.value).then((result: any) => {
        imageList.value = result.records;
        totalData.value = result.total;
        currentPage.value = result.current;
      });
    }

    /**
     * 更改视频封面
     */
    function changeVideoCover() {
      if (uploadFileList.value.length < 1) {
        createMessage.info('请先上传视频');
        return;
      }
      screenshotListVisible.value = true;
      let showUrl = uploadFileList.value[0].showUrl;
      screenshotImageList.value = [];
      screenshotLoading.value = true;
      // 接口请求时间较长 使用原生的axios 
      axios.get(getScreenshotBase64Url(showUrl)).then(res => {
        for (let i = 0; i < res.data.length; i++) {
          screenshotImageList.value.push({
            showUrl: "data:image/png;base64," + res.data[i],
            frameRate: i,
            value: res.data[i]
          });
        }
        screenshotLoading.value = false;
      }).catch((e => {
        createMessage.error("获取截图失败" + e);
        screenshotLoading.value = false;
      }));
    }

    return {
      state,
      attrs,
      listGrid,
      labelCol,
      wrapperCol,
      previewImage,
      previewVisible,
      pictureListVisible,
      screenshotListVisible,
      uploadFileList,
      screenshotImageList,
      imageList,
      multiple,
      headers,
      loading,
      uploadUrl,
      materialManagerType,
      materialImgType,
      beforeUpload,
      getFileAccessHttpUrl,
      uploadVisible,
      handlePreview,
      handleCancel,
      handlePictureListCancel,
      handleChange,
      handlescreenshotCancel,
      onClickUpload,
      onClickScreenshotCard,
      searchMaterialQuery,
      queryMaterialParam,
      changeVideoCover,
      screenshotLoading,
      cardBodyStyle,
      choosePic,
      cardHeaderStyle,
      cardHeaderClickStyle,
      choosePicData,
      choosePicName,
      changePage,
      currentPage,
      totalData,
      handleOk,
      choosePicId,
      chooseScreenshotData,
      chooseScreenshot,
      chooseScreenshotRate,
      JSearchSelect,
      GameOptionForm,
      selectForm,
      bodystyle,
      getGameVal
    };
  },
});
</script>
<style scoped>
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}

.ant-upload-select-picture-card .ant-upload-div {
  padding-top: 22px;
  height: 100%;
  width: 100%
}

.list-div {
  margin-left: 20px;
}

.material-div {
  margin-right: 10px;
  width: 105px;
  display: inline-block
}

.change-video-button {
  width: 105px;
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

#materialDiv .template-thumb img {
  width: 100%;
}
</style>
