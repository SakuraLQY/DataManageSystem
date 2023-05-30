<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="24" v-if="formData.id == ''">
          <a-form-item label="生成广告条数">
            <a-input-number size="large" :min="1" :max="100000" v-model:value="formData.dealNumbers" />
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <div class="file-select"> 
            <GameOptionModal ref="selectForm" @handlerModal="getGameVal"></GameOptionModal>
          </div>
        </a-col>
        <a-col :span="24">
          <a-form-item label="游戏包ID" v-bind="validateInfos.pkgId">
            <a-tag color="error" v-if="formData.subGameId == undefined || formData.subGameId == '' || formData.gameId == undefined">请先选择游戏/子游戏</a-tag>
            <a-select v-model:value="formData.pkgId" placeholder="请选择游戏包ID" @change="selectPkg" :disabled="disabled" v-else>
            <a-select-option
                v-for="(item,index) in pkgList"
                :key="item.nick_name"
                :value="item.id"
            >
            {{item.nick_name}}
            </a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="投放账号ID" v-bind="validateInfos.accountIds">
            <a-tag color="error" v-if="formData.subGameId == undefined || formData.subGameId == '' || formData.gameId == undefined">请先选择游戏/子游戏</a-tag>
            <a-select v-model:value="formData.accountIds" placeholder="请选择投放账号ID" mode="multiple" :disabled="disabled"  v-else-if="formData.id == ''">
            <a-select-option
                v-for="item in accountList"
                :key="item.id"
                :value="item.id"
            >
            {{item.nickName}}
            </a-select-option>
            </a-select>
            <a-select v-model:value="formData.accountIds" placeholder="请选择投放账号ID" :disabled="disabled"  v-else>
            <a-select-option
                v-for="item in accountList"
                :key="item.id"
                :value="item.id"
            >
            {{item.nickName}}
            </a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="渠道子账号ID" v-bind="validateInfos.channelSubAccountId">
            <j-search-select
              v-model:value="formData.channelSubAccountId"
              dict="open_gateway.op_channel_sub_account where channel_id = 4,sub_account_name,id"
              :disabled="disabled"
              placeholder="请选择渠道子账号ID"
            />
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="广告名称" v-bind="validateInfos.dealName">
            <a-input v-model:value="formData.dealName" placeholder="默认填充游戏名" showCount :maxlength="100" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="监测链接">
            <a-textarea v-model:value="formData.dealArgs" placeholder="不填将自动生成" showCount :maxlength="512" auto-size :disabled="disabled"></a-textarea>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="下载链接">
            <a-textarea v-model:value="formData.pkgUrl" placeholder="不填自动生成" showCount :maxlength="128" auto-size :disabled="disabled"></a-textarea>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="广告描述">
            <a-textarea v-model:value="formData.dealDesc" placeholder="请输入广告描述" showCount :maxlength="128" auto-size :disabled="disabled" />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-spin>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted, watch } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import JCheckbox from "/@/components/Form/src/jeecg/components/JCheckbox.vue";
  import { getValueType } from '/@/utils';
  import { saveOrUpdate, pkgInfoList } from '../JrttDeal.api';
  import { Form } from 'ant-design-vue';
  import GameOptionModal from '/@/views/common/gameOptionModal.vue'
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import { list } from '../../opputaccount/OpPutAccount.api'
  import { jrttPkg } from '../JrttDeal.data';
  
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: ()=>{} },
    formBpm: { type: Boolean, default: true }
  });
  const formRef = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
    dealName: '',   
    gameId: undefined,
    subGameId: undefined,
    pkgId: undefined,
    accountIds: undefined,
    channelSubAccountId: undefined,
    dealArgs: '',   
    dealDesc: '',   
    pkgUrl: '',
    dealNumbers:1   
  });
  const selectForm= ref();
  const accountList = ref();
  let getGameVal = (e:any) => {
    formData.gameId = e.gameId;
    formData.subGameId = e.subGameId;
    formData.accountIds = defaultAccount;
    formData.pkgId = defaultpkg;
    defaultpkg = undefined;
    defaultAccount = undefined;
    changeAccountOptions(e.gameId, e.subGameId);
    getPkgList(e.gameId, e.subGameId);
  }
  // 查询投放账号
  const accountQueryParam = ref<any>({});
  accountQueryParam.value.pageNo = 1;
  accountQueryParam.value.pageSize = 9999;
  // 查询游戏包
  const pkgQueryParam = ref<any>({});
  
  let pkgList = ref([]);
  let defaultpkg = undefined;
  let defaultAccount = undefined;
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  
  //表单验证
  const validatorRules = {
    dealName: [{ required: true, message: '请输入广告名称!'},],
    gameId: [{ required: true, message: '请输入游戏ID!'},],
    subGameId: [{ required: true, message: '请输入子游戏ID!'},],
    pkgId: [{ required: true, message: '请选择游戏包!'},],
    accountIds: [{ required: true, message: '请选择投放账号!'},],
    channelSubAccountId: [{ required: true, message: '请选择渠道子账号!'},],
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
   * 游戏/子游戏变化
   */
  function changeAccountOptions(gameId, subGameId) {    
    let game = {
      gameId : gameId,
      subGameId : subGameId
    };
    accountQueryParam.value.subGameIds = JSON.stringify(game);
    list(accountQueryParam.value).then((res: any) => {
      accountList.value = res.records;
    })
  }

  /**
   * 选择游戏包
   */
  function getPkgList(gameId, subGameId){
    pkgQueryParam.value.gameId = gameId;
    pkgQueryParam.value.subGameId = subGameId;
    pkgQueryParam.value.channelId = 4;
    pkgInfoList(pkgQueryParam.value).then((res: any) => {
      pkgList.value = res;
    })
  }

  /**
   * 游戏/子游戏变化
   */
  function selectPkg(data, option) {
    formData.dealName = option.key;
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
      selectForm.value.edit({gameId:record.gameId, subGameId: record.subGameId});
      defaultAccount = record.accountId;
      defaultpkg = record.pkgId;
      formData.accountIds = defaultAccount;
      changeAccountOptions(record.gameId, record.subGameId);
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
    }
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
        selectForm.value.reload();
      });
  }


  defineExpose({
    add,
    edit,
    submitForm
  });
</script>

<style lang="less" scoped>
  .antd-modal-form {
    min-height: 500px !important;
    overflow-y: auto;
    padding: 24px 24px 24px 24px;
  }
  .file-select {
    width: 100%;
    display: flex;
    .ant-select {
      width: 33.3%;
    }
    .ant-select:not(:first-child) {
      margin-left: 10px;
    }
  }
</style>
