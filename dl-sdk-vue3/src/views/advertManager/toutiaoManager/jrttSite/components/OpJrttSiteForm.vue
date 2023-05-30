<template>
  <a-spin :spinning="confirmLoading">
    <PageWrapper title="基础信息">
      <a-card>
        <CollapseContainer title="所属游戏与名称">
          <a-form :label-col="labelCol" :wrapper-col="wrapperCol" >
            <a-row :gutter="24">
              <gameOptionForm ref="gameOptions" @handler="getGameVal"></gameOptionForm>
              <a-col :lg="4">
                <a-form-item label="站点名" v-bind="validateInfos.siteName">
                  <a-input placeholder="请输入站点名称" v-model:value="formData.siteName"></a-input>
                </a-form-item>
              </a-col>
            </a-row>
          </a-form>
        </CollapseContainer>
      </a-card>
    </PageWrapper>
    <PageWrapper title="制作落地页">
        <a-row :gutter="24">
        <a-col :span="2">
          <div class="col-md-2 modular assembly">
            <div class="title">组件模块</div>
              <div style="font-size:large;color: #rgb(10 10 10 / 85%);">单图</div>
              <a-card hoverable style="width: 240px;" @click="addModule">
                <template #cover>
                  <img alt="example" :src="CDNIMG + singleImgUrl"  />
                </template>
              </a-card>
              <div style="font-size:large;color: #rgb(10 10 10 / 85%)">组图</div>
              <a-card hoverable style="width: 240px" @click="addModule">
                <template #cover>
                  <img alt="example" :src="CDNIMG + groupImg1" />
                </template>
              </a-card>
              <a-card hoverable style="width: 240px" @click="addModule">
                <template #cover>
                  <img alt="example" :src="CDNIMG + groupImg2" />
                </template>
              </a-card>
              <a-card hoverable style="width: 240px" @click="addModule">
                <template #cover>
                  <img alt="example" :src="CDNIMG + groupImg3" />
                </template>
              </a-card>
              <div style="font-size:large;color: #rgb(10 10 10 / 85%)">按钮</div>
              <a-card hoverable style="width: 240px" @click="addModule">
                <template #cover>
                  <img alt="example" :src="CDNIMG + downloadImg" />
                </template>
              </a-card>
          </div>
        </a-col>
        <a-col :span="6" :offset="2">
          <div class="modular">
            <div class="title" style="width:480px;">展示模块</div>
          </div>
          <div id="htmlDiv">
            <a-card 
            style="width:100%"
            :bodyStyle="cardBodyStyle">
              <div class = "template-thumb">
                <div :class = "choosedModule.index == index?'group_div_on':''" v-for="(item,index) in chooseImgs">
                  <div class="download_group_div" v-if="item.name == 'XrButton'" @click="chooseModule(item,index)">{{choosedModule.text}}</div>
                  <div v-else-if="item.name == 'XrPicture'" @click="chooseModule(item,index)"><img :src="item.chooseImg!=undefined?item.chooseImg:item.url"></div>
                  <div v-else @click="chooseModule(item,index)"><img :src="item.chooseImgs.length > 0?item.chooseImgs[item.chooseImgs.length-1]:item.url"></div>
                </div>
              </div>
            </a-card>
          </div>
        </a-col>
        <a-col :span="6" :offset="2">
          <div class="modular">
            <div class="title" style="width:760px;margin-bottom: 0%;">设置模块</div>

          <table class="table table-bordered  table-striped" width="100%" border="0" cellspacing="0" cellpadding="3">
            <tr>
              <th>
                悬浮位置
              </th>
              <th>
                <a-radio-group :options="floatTypes" v-model:value="choosedModule.float" @change="changeFloat"></a-radio-group>
              </th>
              <th>
                <a-button danger @click="delModule">删除模块</a-button>
              </th>
            </tr>
            <tr v-if="choosedModule.name != 'XrButton'">
              <th width="15%" >选中图片</th>
              <td id="selectImg" colspan="2">
                  <div v-for="(item,index) in choosedModule.chooseImgs" v-if="choosedModule.name == 'XrPictureGroup'">
                    <div class="imgDiv">
                      <img :src="item">
                    </div>
                    <div class="imgDiv">
                      <img class="delete_select" :src="CDNIMG+ deleteImg" @click="groupRemoveImg(index)">
                    </div>
                  </div>
                  <div v-if="choosedModule.name == 'XrPicture' && choosedModule.chooseImgs != undefined">
                      <div class="imgDiv">
                        <img :src="choosedModule.chooseImgs" >
                      </div>
                      <div class="imgDiv">
                        <img class="delete_select" :src="CDNIMG + deleteImg" @click="singleRemoveImg">
                      </div>
                  </div>
                </td>
            </tr>
            <tr v-if="choosedModule.name == 'XrButton'">
              <th width="15%">
                下载配置
              </th>
              <th colspan="3">
                字体颜色：<a-input style="width:50px;margin-right: 5px;" v-model:value="choosedModule.color"></a-input>
                背景颜色：<a-input style="width:50px;margin-right: 5px;" v-model:value="choosedModule.bg_color"></a-input>
                按钮文本：<a-input placeholder="必须包含下载两个字" style="width:150px" v-model:value="choosedModule.text"></a-input>
              </th>
            </tr>
          </table>
        </div>
          <div class="modular">
            <div class="title" style="width:760px;margin-bottom: 0%;">素材模块</div>
          </div>
          <table  class="table">
            <tr>
              <th width="10%">
                素材游戏
              </th>
              <th width="40%">
                <gameOptionsUnit ref="gameOptions1" @handler="chooseGame"></gameOptionsUnit>
              </th>
              <th width="10%">
                创建人员
              </th>
              <th>
                <j-search-select v-model:value="materialQueryParams.createBy" placeholder="请选择创建用户" dict="sys_user,username,username" allowClear/>
              </th>
            </tr>
            <tr>
              <th>
                素材日期
              </th>
              <th>
                <a-date-picker format="YYYY-MM-DD" value-format="YYYY-MM-DD" v-model:value="materialQueryParams.createTime"></a-date-picker>
              </th>
              <th colspan="4" style="text-align: center">
                <a-button type="primary" @click="querySiteMaterial">查询</a-button>
              </th>
            </tr>
            <tr>
              <td colspan="16">
                <div id="materialDiv">
                  <a-row :gutter="16">
                    <a-col  :span="6" v-for="item in images">
                      <a-card hoverable 
                      style="width:170px;margin-top: 15px;"
                      :title="item.materialName"
                      @click="chooseImg(item)"
                      >
                          <template #cover>
                            <img :src="getFileAccessHttpUrl(item.showUrl)">
                          </template>
                      </a-card>
                    </a-col>
                  </a-row>
                </div>
              </td>
            </tr>
            <tr>
              <td colspan="16">
                <pagination  v-model:current="currentPage" :total="totalImgNum" show-less-items @change="changePage" defaultPageSize="8"/>
              </td>
            </tr>
          </table>
        </a-col>
      </a-row>
    </PageWrapper>
  </a-spin>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { getValueType } from '/@/utils';
  import { saveOrUpdate,listMaterial } from '../OpJrttSite.api';
  import { Form } from 'ant-design-vue';
  import gameOptionForm from '/@/views/common/gameOptionForm.vue'
  import gameOptionsUnit from '/@/views/common/gameOptions.vue'
  import { PageWrapper } from '/@/components/Page';
  import { CollapseContainer } from '/@/components/Container/index';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import { getFileAccessHttpUrl } from '/@/utils/common/compUtils';
  import Pagination from 'ant-design-vue/lib/pagination';
  const currentPage = ref(1);
  const totalImgNum = ref(0);
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: ()=>{} },
    formBpm: { type: Boolean, default: true }
  });
  const formRef = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: undefined,
    gameId: undefined,
    subGameId: undefined,
    siteName: '',   
    siteContent: '',   
  });
  const materialQueryParams = reactive<Record<string, any>>({
    gameId: undefined,
    subGameId: undefined,
    createBy: undefined,
    createTime: undefined
  });
  let choosedModule = reactive<Record<string, any>>({
    name: undefined,
    index: undefined,
    float: 'none',
    text: '立即下载',
    color: undefined,
    bg_color: undefined,
    url:undefined,
    chooseImgs:undefined
  })
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    siteName: [{ required: true, message: '请输入站点名!'},],
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

  const CDNIMG = '/resource/img/';
  const singleImgUrl = 'single_img.webp';
  const groupImg1 = 'many_img_1.webp';
  const groupImg2 = 'many_img_2.webp';
  const groupImg3 = 'many_img_3.webp';
  const downloadImg = 'download_img.jpg';
  const deleteImg = 'delete.png';
  let chooseImgs:any = ref([]);
  let images:any = ref([]);
  let getGameVal = (e:any) => {
    formData.gameId = e.gameId;
    formData.subGameId = e.subGameId;
  }
  const floatTypes = [
    {
    label: '不悬浮',
    value: 'none'
    },
    {
    label: '置顶',
    value: 'top'
    },
    {
    label: '置底',
    value: 'bottom'
    },
  ]
  const cardBodyStyle = { padding: '0' };
  const gameOptions = ref();
  const gameOptions1 = ref();
    
  /**
   * 单图移除图片
   */
  function singleRemoveImg(e) {
    choosedModule.chooseImgs = undefined;
    chooseImgs.value[choosedModule.index].chooseImg = undefined
  }
  /**
   * 组图移除模块中的图片
   */
  function groupRemoveImg(index){
    choosedModule.chooseImgs.splice(index,1);
    chooseImgs.value[choosedModule.index].materialIds.splice(index,1);
  }
  /**
   * 删除模块
   * @param e 
   */
  function delModule(e:any){
    if(choosedModule.index != undefined){
      chooseImgs.value.splice(choosedModule.index,1);
      choosedModule.index = undefined
    }
  }
  /**
   * 添加图片
   * @param e 
   */
  function chooseImg(item){
    if(choosedModule.index != undefined && choosedModule.name != 'XrButton'){
      if(choosedModule.name == 'XrPicture'){
        chooseImgs.value[choosedModule.index].chooseImg = getFileAccessHttpUrl(item.showUrl);
        chooseImgs.value[choosedModule.index].materialId = item.id
        choosedModule.chooseImgs = getFileAccessHttpUrl(item.showUrl)
      }
      if(choosedModule.name == 'XrPictureGroup'){
        let url = getFileAccessHttpUrl(item.showUrl)
        if(!choosedModule.chooseImgs.includes(url)){
          chooseImgs.value[choosedModule.index].materialIds.push(item.id)
          choosedModule.chooseImgs.push(url)
        }
      }
    }
  }
  /**
   * 修改悬浮位置
   * @param e 
   */
  function changeFloat(e:any) {
    if(choosedModule.index != undefined ){
      chooseImgs.value[choosedModule.index].float = e.target.value;
    }
  }
  /**
   * 选择游戏
   * @param e 
   */
  function chooseGame(e){
    materialQueryParams.gameId = e.gameId;
    materialQueryParams.subGameId = e.subGameId;
  }
  /**
   * 添加模块
   * @param e 
   */
  function addModule(e){
    let url = e.target.src
    let index = url.lastIndexOf("/");
    let imgName = url.substr(index + 1);
    let obj;
    if(imgName == singleImgUrl){
      obj = {
        name:'XrPicture',
        float: 'none',
        url:url,
        chooseImg:undefined,
        materialId:undefined
      }
    }else if(imgName == groupImg1 || imgName == groupImg2 || imgName == groupImg3){
      let groupType;
      if(url == groupImg1)
        groupType = 'normal';
      if(url == groupImg2)
        groupType = 'carousel'
      if(url == groupImg3)
        groupType = 'coverflow'

      obj = {
        name:'XrPictureGroup',
        float: 'none',
        url:url,
        chooseImgs:[],
        materialIds:[],
        groupType: groupType
      }
    }else{
      obj = {
        name: 'XrButton',
        float: 'none',
        text: '立即下载',
        color: 'rgba(255,255,255,1)',
        bg_color: undefined
      }
    }
    chooseImgs.value.push(obj)
  }
  /**
   * 选中模块
   * @param item 
   * @param index 
   */
  function chooseModule(item,index){
    choosedModule.name = item.name;
    choosedModule.float = item.float;
    choosedModule.index = index
    if(item.name == 'XrPicture'){
      choosedModule.chooseImgs = item.chooseImg
    }else if(item.name == 'XrPictureGroup'){
      choosedModule.chooseImgs = item.chooseImgs
    }
  }
  /**
   * 改变分页
   * @param current 
   * @param total 
   */
  function changePage(current, total){
    defHttp.get({url:listMaterial,params:{queryParam: materialQueryParams,pageNo: current}}).then(res =>{
      images.value = res.records
      totalImgNum.value = res.total;
      currentPage.value = res.current;
    })
  }
  /**
   * 查询落地页素材
   */
  function querySiteMaterial(){
    defHttp.get({url:listMaterial,params:materialQueryParams}).then(res =>{
      images.value = res.records
      totalImgNum.value = res.total;
      currentPage.value = res.current;
    })
  }

  /**
   * 新增
   */
  function add() {
    edit({});
  }

  /**
   * 编辑
   */
  function edit(record) {
    nextTick(() => {
      resetFields();
      gameOptions.value.reload();
      gameOptions1.value.reload();
      chooseImgs.value = [];
      images.value = [];
      //赋值
      Object.assign(materialQueryParams,{
        gameId: undefined,
        subGameId: undefined,
        createBy: undefined,
        createTime: undefined
      });
      Object.assign(choosedModule,{
        name: undefined,
        index: undefined,
        float: 'none',
        text: '立即下载',
        color: undefined,
        bg_color: undefined,
        url:undefined,
        chooseImgs:undefined
      })
      Object.assign(formData, record);
    });
  }

  /**
   * 提交数据
   */
  async function submitForm() {
    // 触发表单验证    
    await validate();
    //时间格式化
    let model = formData;
    const isUpdate = ref<boolean>(false);
    if (model.id) {
      isUpdate.value = true;
    }
    
    if(model.gameId == undefined || model.subGameId == undefined){
      createMessage.warning('请选择所属游戏')
      confirmLoading.value = false;
      return
    }
    if(chooseImgs.value.length <= 0){
      createMessage.warning('请创建落地页样式')
      return
    }
    confirmLoading.value = true;
    model.siteContent = JSON.stringify(chooseImgs.value);
    // //循环数据
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
  });
</script>

<style lang="less" scoped>
  .table { 
    width: 760px;
    border: 1px solid #1b1b1b; 
    margin: 0 auto;
    text-align: center; 
    th { border-bottom: 1px solid #1b1c1d; border-right: 1px solid #030303;}
  }
  .anth-modal-form {
    min-height: 500px !important;
    overflow-y: auto;
    padding: 24px 24px 24px 24px;
  }
  .modular .title{
    width: 240px;
    height: 30px;
    background-color: #0b93d5;
    color: #ffffff;
    font-size: 18px;
    margin-bottom: 20px;
  }
  .assembly .subtitle{
        width: 100%;
        height: 40px;
        line-height: 40px;
        margin: 5px 0;
        font-size: 18px;
    }
    .assembly .operate{
        width: 100%;
        border : 1px dashed #cdcdcd;
        border-radius:5px;
        padding: 5px;
        margin-bottom: 10px;
    }
    .assembly .download_img{
        border : 1px dashed #ff3c3c;
    }
    .assembly .operate img{
        width: 100%;
    }
    #materialDiv{
      margin-top: 0px;
      width: 100%;
      min-height: 10px;
      max-height: 500px;
      overflow-y:auto;
      border : 1px dashed #cdcdcd;
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
    .download_group_div{
        border : 1px dashed #ff3c3c;
        line-height: 50px;
        height: 50px;
        text-align: center;
        background-color: #f03f2a;
        color: #ffffff;
        font-size: 16px;
    }
    #htmlDiv{
        margin-top: 10px;
        width: 100%;
        max-height: 800px;
        overflow-y:auto;
        position:relative;
        border : 1px dashed #cdcdcd;
    }
    #htmlDiv img{
        width: 96%;
        margin: 5px 2%;
    }
    #htmlDiv .float_top{
        position:sticky;
        top: 0;
        left: 0;
    }
    #htmlDiv .float_bottom{
        position:sticky;
        bottom: 0;
        left: 0;
    }
    .group_div_on{
        box-shadow: 0px 0px 10px #386FCF inset;
    }
    #selectImg .imgDiv{
        float: left;
        height: 50px;
        margin-right: 10px;
        margin-bottom: 5px;
    }
    #selectImg img{
      height: 50px;
    }
    #selectImg img.delete_select{
        height: 30px;
        margin: 10px 10px 10px 0;
    }
</style>
