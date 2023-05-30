<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="24">
          <a-form-item label="子游戏名" v-bind="validateInfos.gameId">
            <j-search-select v-model:value="formData.gameId" dict="open_gateway.op_sub_game,game_name,id" :disabled="true"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-row v-for="(condition, index) in formData.goodsConf" :key="'item'+index">
            <a-col :span="8">
              <a-form-item 
              :label="'商品'+ (index+1)" 
              :prop="'goodsConf.' + index + '.goodsId'"
              :rules="{
                required: true, message: '不能为空', trigger: 'blur'
              }" >
                <a-input v-model:value="condition.goodsId"  maxLength="20"  placeholder="请输入商品ID" :disabled="disabled"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item
              :prop="'goodsConf.' + index + '.money'"
              :rules="{
                required: true, message: '不能为空', trigger: 'blur'
              }" >
                <a-input-number v-model:value="condition.money" placeholder="请输入金额" style="width: 100%" :disabled="disabled"/>
              </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item 
             :prop="'goodsConf.' + index + '.currencyType'"
              :rules="{
                required: true, message: '不能为空', trigger: 'blur'
              }">
              <j-dict-select-tag type='radio' v-model:value="condition.currencyType" dictCode="current_type" placeholder="请选择币种" :disabled="disabled"/>
            </a-form-item>
          </a-col>
          <a-col :span="3">
            <a-button style="float:right" @click.prevent="removeGoodsId(index)">{{'删除'}}</a-button>
          </a-col>
          </a-row>
        </a-col>
        <a-form-item>
          <a-button @click="addGoodsId">{{'添加' }}</a-button>
        </a-form-item>
        <!-- <a-col :span="24">
          <a-form-item label="游戏商品金额" v-bind="validateInfos.money">
	          <a-input-number v-model:value="formData.money" placeholder="请输入游戏商品金额" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="币种" v-bind="validateInfos.currencyType">
            <j-dict-select-tag type='radio' v-model:value="formData.currencyType" dictCode="current_type" placeholder="请选择币种" :disabled="disabled"/>
          </a-form-item>
        </a-col> -->
      </a-row>
    </a-form>
  </a-spin>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import { getValueType } from '/@/utils';
  import { saveOrUpdate } from '../OpCommodity.api';
  import { Form } from 'ant-design-vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import { message } from 'ant-design-vue';

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
    // goodsId: '',  
    goodsConf: [
          {
            goodsId: undefined,
            money: undefined,
            currencyType: undefined
          }
        ], 
    gameId: undefined,
    // money: undefined,
    // currencyType: '1',
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    goodsId: [{ required: true, message: '请输入商品ID!'},],
    // gameId: [{ required: true, message: '请输入子游戏ID!'},],
    money: [{ required: true, message: '请输入游戏商品金额!'},],
    currencyType: [{ required: true, message: '请输入币种!'},],
  };
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: true });

  const disabledGame = ref<boolean>(true)
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

  function removeGoodsId(index) {
      if (index !== -1) {
        formData.goodsConf.splice(index, 1)
      }
    }

  function addGoodsId() {
      formData.goodsConf.push({
        goodsId: undefined,
        money: undefined,
        currencyType: '0',
      })
    }
  
  /**
   * 新增
   */
  // function add(gameList) {
  //   edit({id:'',goodsId:'',gameId:gameList.id,gameName:gameList.gameName,money:undefined,currencyType:'0'});
  // }

  let isSelect = ref();

  /**
   * 编辑
   */
  function edit(record, count) {
    isSelect.value = count;
    nextTick(() => {
      resetFields();
      //赋值
      if(record.id !== null) {
        formData.id = record.id;
        formData.goodsConf = record.goodsConf;
      }
      formData.gameId = record.gameId;
    });
  }

  /**
   * 提交数据
   */
  async function submitForm() {
    // 触发表单验证
    await validate();
    for (let index = 0; index < formData.goodsConf.length; index++) {
      const element = formData.goodsConf[index];
      if(element.goodsId === undefined || element.money === undefined || element.currencyType === undefined) {
        message.warning('请填写完整');
        return;
      }
    }
    confirmLoading.value = true;
    const isUpdate = ref<boolean>(false);
    //时间格式化
    let model = formData;
    if (model.id) {
      isUpdate.value = true;
    }
    if(isSelect.value === 1) {
      model.subGameId = model.gameId;
    }else {
      model.pkgId = model.gameId;
    }
    
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
    edit,
    submitForm,
    removeGoodsId,
    addGoodsId
  });
</script>

<style lang="less" scoped>
  .antd-modal-form {
    min-height: 500px !important;
    overflow-y: auto;
    padding: 24px 24px 24px 24px;
  }
</style>
