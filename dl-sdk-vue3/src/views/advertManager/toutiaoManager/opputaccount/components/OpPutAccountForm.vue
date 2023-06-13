<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="12">
          <a-form-item label="账号昵称" v-bind="validateInfos.nickName">
            <a-input v-model:value="formData.nickName" maxLength="25" placeholder="长度限制25位"  :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="账号" v-bind="validateInfos.account">
            <a-input v-model:value="formData.account" maxLength="50" placeholder="长度限制50位" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="账号密码" v-bind="validateInfos.password">
            <a-input v-model:value="formData.password" maxLength="30" placeholder="长度限制30位"  :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="账号归属用户" v-bind="validateInfos.putUser">
            <j-search-select v-model:value="formData.putUser" dict="sys_user,realname,id" placeholder="请选择账号归属用户" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="账号所属应用" v-bind="validateInfos.appId">
	          <j-search-select v-model:value="formData.appId" dict="open_gateway.op_jrtt_put_account_app,app_name,id" placeholder="请选择账号所属应用" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="账号状态" v-bind="validateInfos.state">
	          <j-search-select v-model:value="formData.state" dict="account_state" placeholder="请选择账号状态" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="17" :pull="2" v-if="!props.formDisabled">
          <a-form-item label="游戏" v-bind="validateInfos.subGame">
            <a-space :size="15">
              <a-select v-model:value="gameId" show-search style="width:200px" placeholder="请选择游戏" allowClear  :disabled="disabled">
                <a-select-option
                  v-for="(gameItem,index) in faList"
                  :key="index"
                  :value="index"
                >
                {{gameItem.gameName + '(' + index + ')'}}
                </a-select-option>
              </a-select>
              <a-select  allowClear v-model:value="subGameId" show-search style="width:200px" placeholder="请选择子游戏名" v-if="gameId !=='' && gameId !==undefined"  :disabled="disabled">
                <a-select-option
                  v-for="(gameItem,index) in faList[gameId].list"
                  :key="index"
                  :value="gameItem.id"
                >
                {{gameItem.gameName + '(' + gameItem.id + ')'}}
                </a-select-option>
              </a-select>
              <a-select placeholder="请选择子游戏名" v-model:value="subGameId" style="width:200px" v-if="gameId ==='' || gameId ===undefined" required :disabled="disabled">
                <a-select-option
                  v-for="(gameItem,index) in []"
                  :key="index"
                  :value="gameItem.id"
                >
                {{gameItem.gameName}}
                </a-select-option>
              </a-select>
              <a-button type="primary" @click="addOption" :disabled="disabled">添加</a-button>
            </a-space>
          </a-form-item>
        </a-col>
        <a-col>
          <a-form-item label="已选择游戏" :span="12">
            <template v-for="(item,index) in selectedOptions">
            {{faList[item.gameId].gameName +"-" + getSubName(item.subGameId,faList[item.gameId].list)}} <a-button v-if="!props.formDisabled" type="link" @click="deleteOption(index)">删除</a-button>
            </template>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="账号备注" v-bind="validateInfos.accountDesc">
            <a-textarea v-model:value="formData.accountDesc" showCount maxLength="255" rows="4" placeholder="长度限制至255位" :disabled="disabled"></a-textarea>
          </a-form-item>
        </a-col>
        <a-col :span="12" v-if="props.formDisabled">
          <a-form-item label="access_token" v-bind="validateInfos.accessToken">
            <a-input v-model:value="formData.accessToken"  :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12" v-if="props.formDisabled">
          <a-form-item label="refresh_token" v-bind="validateInfos.refreshToken">
            <a-input v-model:value="formData.refreshToken"  :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12" v-if="props.formDisabled">
          <a-form-item label="token过期时间" v-bind="validateInfos.accessTokenTime">
            <a-input v-model:value="formData.accessTokenTime"  :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12" v-if="props.formDisabled">
          <a-form-item label="认证时间" v-bind="validateInfos.authorizeTime">
            <a-input v-model:value="formData.authorizeTime"  :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-spin>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, watch, onMounted } from 'vue';
  import { useMessage } from '/@/hooks/web/useMessage';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import { getValueType } from '/@/utils';
  import { saveOrUpdate,faList } from '../OpPutAccount.api';
  import { Form, message } from 'ant-design-vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';

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
    levelId: '',
    appId: undefined,
    pid:'',
    account: '',
    nickName: '',
    password: '',
    subGameIds: '',
    putUser: '',
    channelId:4,
    accountDesc: '',
    state: '1',
    accessToken: '',
    refreshToken: '',
    refreshTokenTime: '',
    authorizeTime: ''
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 12 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 12 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    appId: [{ required: true, message: '请输入账号所属应用!'},],
    account: [{ required: true, message: '请输入账号!'},],
    nickName: [{ required: true, message: '请输入账号昵称!'},],
    password: [{ required: true, message: '请输入账号密码!'},],
    putUser: [{ required: true, message: '请输入账号归属用户!'},],
    state: [{ required: true, message: '请输入账号状态!'},],
    subGame: [{required: true, message: '请选择子游戏'}]
  };
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: true });


  const gameId = ref();
  const subGameId = ref();
  // let selectedSubgame = ref();
  // let selectedGame = ref();
  let selectedOptions = ref([]);

  function deleteOption(index){
    let options = selectedOptions.value;
    options.splice(index,1)
  }

  function getSubName(subGameId, subGames){
    for(let subGame of subGames){
      if(subGame.id == subGameId){
        return subGame.gameName;
      }
    }
  }
  watch(
      () => gameId.value,
      val => {
          subGameId.value = undefined;
      },
    );
  // function chooseGame(value) {
  //   selectedGame = value;
  // }

  // function chooseSubgame(value) {
  //   selectedSubgame = value;
  // }

  function gameReload() {
    gameId.value = undefined;
    subGameId.value = undefined;
  }

  function addOption(){
    if(gameId.value == undefined || subGameId.value == undefined){
      message.warning('游戏/子游戏请选择完整');
      return false;
    }
    let option = {
      gameId : parseInt(gameId.value),
      subGameId : subGameId.value
    }
    let options = selectedOptions.value;
    let chooseSubgames = options.map(obj => {return obj.subGameId});
    if(chooseSubgames.includes(option.subGameId)) {
      message.warning('请勿重复添加');
      return false;
    }
    options.push(option)
  }


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
    selectedOptions.value =[];
    edit({});
  }

  /**
   * 编辑
   */
  function edit(record) {
    nextTick(() => {
      resetFields();
      Object.assign(formData, record);
    });
  }

  function fillOptions(record){
    nextTick(() =>{
      selectedOptions.value = JSON.parse(record.subGameIds);
    })
  }

  /**
   * 提交数据
   */
  async function submitForm() {
    // 触发表单验证
    await validate();
    confirmLoading.value = true;
    const isUpdate = ref<boolean>(false);
    formData.subGameIds = JSON.stringify(selectedOptions.value);
    if(formData.subGameIds === '[]') {
       message.warning('游戏/子游戏必选一个');
       confirmLoading.value = false;
       return false;
    }
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
      });
  }


  defineExpose({
    add,
    edit,
    submitForm,
    fillOptions,
    gameReload
  });
</script>

<style lang="less" scoped>
  .antd-modal-form {
    min-height: 500px !important;
    overflow-y: auto;
    padding: 12px 12px 12px 12px;
  }
</style>
