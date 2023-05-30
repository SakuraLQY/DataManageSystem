<template>
  <a-spin :spinning="confirmLoading">
    <a-row> 
    <a-col :span="10">
    <div style="margin-left:20px;margin-top:200px;font-size:20px;color:red">
      <div>判定规则(从上往下为判断顺序)</div>
      <div>1、游戏没有配置切支付->ios支付</div>
      <div>2、在黑马单内->ios支付</div>
      <div>3、没有配置非默认支付-> 默认支付</div>
      <div>4、在白名单内 -> 非默认支付</div>
      <div>5、游戏版本和构建版本都匹配 -> 默认支付</div>
      <div>6、不在时间范围内或者不在选中地区内 -> 默认支付</div>
      <div>7、单笔订单金额大于配置或者购买次数大于配置 -> 默认支付</div>
      <div>8、累充金额小于配置 -> 默认支付</div>
      <div>5-8都没有走向默认支付，使用非默认支付</div>
    </div>
    </a-col>
    <a-col :span="14">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <PkgOptionSingleSelect ref="selectForm" @handlerModal="getGameVal" :disabled="disabled"></PkgOptionSingleSelect>
        <a-col :span="24">
          <a-form-item label="默认支付" v-bind="validateInfos.defaultPay">
	          <j-select-multiple type="list_multi" v-model:value="formData.defaultPay" :options="options" placeholder="请选择默认支付" :disabled="disabled" :triggerChange="false"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="游戏版本" v-bind="validateInfos.gameVersion">
            <a-input v-model:value="formData.gameVersion" placeholder="请输入游戏版本" :disabled="disabled" style="width: 40%;"></a-input>
            <span style="color: darkgray;"> *游戏版本，示例：1.0</span>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="游戏构建" v-bind="validateInfos.gameBuild">
            <a-input v-model:value="formData.gameBuild" placeholder="请输入游戏构建" :disabled="disabled" style="width: 40%;"></a-input>
            <span style="color: darkgray;"> *游戏构建，示例：v1.0</span>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="非默认支付" v-bind="validateInfos.noDefaultPay">
	          <j-select-multiple type="list_multi" v-model:value="formData.noDefaultPay" :options="options" placeholder="请选择非默认支付" :disabled="disabled" :triggerChange="false" style="width: 30%;"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="时间范围" v-bind="validateInfos.rangeTime">
		        <TimeRangePicker value-format="HH:mm:ss" format="HH:mm:ss"  v-model:value="formData.rangeTime" style="width:50%" :disabled="disabled" />
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="地区" v-bind="validateInfos.paySwitch">
            <div>
            <a-checkbox
                v-model:checked="state.checkAll"
                :indeterminate="state.indeterminate"
                @change="onCheckAllChange"
                :disabled="disabled"
              >
                全选
              </a-checkbox>
            </div>
            <br />
            <a-checkbox-group v-model:value="state.checkedList" :options="plainOptions" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="单笔订单金额" v-bind="validateInfos.orderMoney">
	          <a-input v-model:value="formData.orderMoney" placeholder="请输入单笔订单金额" style="width: 40%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="充值次数" v-bind="validateInfos.rechargeTimes">
	          <a-input v-model:value="formData.rechargeTimes" placeholder="请输入充值次数" style="width: 40%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="累计充值金额" v-bind="validateInfos.totalMoney">
	          <a-input v-model:value="formData.totalMoney" placeholder="请输入累计充值金额" style="width: 40%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="黑名单">
	          <a-textarea v-model:value="formData.blackList" placeholder="示例：用户ID1;用户ID2;(注意为英文;)"  :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="白名单">
	          <a-textarea v-model:value="formData.whiteList" placeholder="请示例：用户ID1;用户ID2;(注意为英文;)"  :disabled="disabled"/>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
    </a-col>
    </a-row>
  </a-spin>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted, watch } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import PkgOptionSingleSelect from '/@/views/common/pkgOptionSingleSelect.vue'
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import JSwitch from '/@/components/Form/src/jeecg/components/JSwitch.vue';
  import JSelectMultiple from '/@/components/Form/src/jeecg/components/JSelectMultiple.vue';
  import { TimePicker } from 'ant-design-vue';
  import { getValueType } from '/@/utils';
  import { saveOrUpdate } from '../OpPaySwitch.api';
  import { Form, message } from 'ant-design-vue';
  import { options } from '../OpPaySwitch.data';
import { mode } from 'crypto-js';

  const time = ref();
  const TimeRangePicker= TimePicker.TimeRangePicker
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
    pkgIdList: undefined,
    gameVersion: '',   
    gameBuild: '',   
    defaultPay: '',   
    noDefaultPay: '',   
    orderMoney: undefined,
    rechargeTimes: '',
    totalMoney: '',
    rangeTime: '',  
    blackList: '',
    whiteList: '',
    regions: ''
  });
  const selectForm= ref();
  let getGameVal = (e:any) => {
    formData.pkgIdList = e.pkgIdList;
  }
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  const plainOptions = ['中国', '北京', '天津','上海', '重庆', '河北', '山西', '辽宁', '吉林', '黑龙江', '江苏', '浙江', '安徽', '福建',
  '江西', '山东', '河南', '湖北', '湖南', '广东', '海南', '四川', '贵州', '云南', '陕西', '甘肃', '青海', '台湾', '内蒙古', '广西', '西藏',
  '宁夏', '新疆', '香港', '澳门', '其他地区(包含海外)'];
  const state = reactive({
    indeterminate: true,
    checkAll: false,
    checkedList: ['Apple', 'Orange'],
  });

  const onCheckAllChange = (e: any) => {
    Object.assign(state, {
      checkedList: e.target.checked ? plainOptions : [],
      indeterminate: false,
    });
  };
  watch(
    () => state.checkedList,
    val => {
      state.indeterminate = !!val.length && val.length < plainOptions.length;
      state.checkAll = val.length === plainOptions.length;
      formData.regions = val;
    },
  );

  //表单验证
  const validatorRules = {
    // gameId: [{ required: true, message: '请输入游戏id!'},],
    defaultPay: [{ required: true, message: '请输入默认支付!'},],
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
   * 新增
   */
  function add() {
    edit({});
  }

  /**
   * 重置渠道游戏包
   */
  function pkgOptionSingleSelectReload() {
    selectForm.value.reload();
  }

  /**
   * 编辑
   */
  function edit(record) {
    selectForm.value.edit(record);
    nextTick(() => {
      resetFields();
      //赋值
      let paySwitchData = record;
      paySwitchData.gameId = paySwitchData.gameId + "";
      paySwitchData.rangeTime = paySwitchData.rangeTime instanceof Array?paySwitchData.rangeTime:paySwitchData.rangeTime.split(',')
      Object.assign(formData, paySwitchData);
    });
  }

  /**
   * 提交数据
   */
  async function submitForm() {
    // 触发表单验证
    await validate();
    if(formData.pkgIdList === undefined) {
      message.warning('请选择渠道游戏包');
      return;
    }
    confirmLoading.value = true;
    const isUpdate = ref<boolean>(false);
    //时间格式化
    let model = formData;
    if (model.id) {
      isUpdate.value = true;
    }
    //时间范围制空
    if(model.rangeTime == null) {
      model.rangeTime = ""
    }
    //循环数据
    for (let data in model) {
      if(data !== 'pkgIdList') {
        //如果该数据是数组并且是字符串类型
        if (model[data] instanceof Array) {
          let valueType = getValueType(formRef.value.getProps, data);
          //如果是字符串类型的需要变成以逗号分割的字符串
          if (valueType === 'string') {
            model[data] = model[data].join(',');
          }
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
    pkgOptionSingleSelectReload
  });
</script>

<style lang="less" scoped>
  .antd-modal-form {
    min-height: 500px !important;
    overflow-y: auto;
    padding: 24px 24px 24px 24px;
  }
</style>
