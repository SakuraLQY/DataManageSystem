import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '类型',
    align: "center",
    dataIndex: 'ruleType',
    customRender: ({ text }) => {
      return render.renderDict(text, 'rule_type');
    },
  },
  {
    title: '类型参数',
    align: "center",
    dataIndex: 'ruleName'
  },
  {
    title: '注册规则类型',
    align: "center",
    dataIndex: 'registerLimitSwitch',
    customRender: ({ text }) => {
      return render.renderDict(text, 'register_rule_type');
    },
  },
  {
    title: '注册关闭类型',
    align: "center",
    dataIndex: 'registerCloseType',
    customRender: ({ text }) => {
      return render.renderDict(text, 'close_type');
    },
  },
  {
    title: '登录关闭类型',
    align: "center",
    dataIndex: 'loginCloseType',
    customRender: ({ text }) => {
      return render.renderDict(text, 'close_type');
    },
  },
  // {
  //   title: '登录限制开关',
  //   align: "center",
  //   dataIndex: 'loginLimitSwitch_dictText'
  // },
  {
    title: '限制跨登录开关',
    align: "center",
    dataIndex: 'limitCrossLoginSwitch',
    customRender: ({ text }) => {
      return render.renderDict(text, 'is_open');
    },
  },
  {
    title: '备注',
    align: "center",
    dataIndex: 'descs'
  },
  {
    title: '创建用户',
    align: "center",
    dataIndex: 'createBy'
  },
  {
    title: '创建时间',
    align: "center",
    dataIndex: 'createTime',
    customRender:({text}) =>{
      return !text?"":text;
    },
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "类型",
    field: 'ruleType',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "rule_type"
    },
    colProps: {span: 6},
  },
  {
    label: "规则ID",
    field: 'ruleId',
    component: 'Input',
    colProps: {span: 6},
  },
  // {
  //   label: "注册限制开关",
  //   field: 'registerLimitSwitch',
  //   component: 'JDictSelectTag',
  //   componentProps:{
  //     dictCode: "is_open"
  //   },
  //   colProps: {span: 6},
  // },
  // {
  //   label: "登录限制开关",
  //   field: 'loginLimitSwitch',
  //   component: 'JDictSelectTag',
  //   componentProps:{
  //     dictCode: "is_open"
  //   },
  //   colProps: {span: 6},
  // },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '类型',
    field: 'ruleType',
    component: 'JDictSelectTag',
    defaultValue: '1',
    componentProps:{
      dictCode: "rule_type"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请选择类型!'},
             ];
    },
  },
  {
    label: '规则ID',
    field: 'ruleId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请选择规则ID!'},
             ];
    },
  },
  {
    label: '注册规则类型',
    field: 'registerLimitSwitch',
    defaultValue: '1',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "register_rule_type"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请选择注册规则类型!'},
             ];
    },
  },
  {
    label: '注册关闭类型',
    field: 'registerCloseType',
    defaultValue: '2',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "login_rule_type"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请选择注册限制开关!'},
             ];
    },
  },
  {
    label: '注册限制IP',
    field: 'registerLimitIp',
    component: 'InputTextArea',
  },
  {
    label: '注册限制设备',
    field: 'registerLimitDevice',
    component: 'InputTextArea',
  },
  {
    label: '登录关闭类型',
    field: 'loginCloseType',
    defaultValue: '2',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "login_rule_type"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请选择登录关闭类型!'},
             ];
    },
  },
  {
    label: '登录限制IP',
    field: 'loginLimitIp',
    component: 'InputTextArea',
  },
  {
    label: '登录限制用户ID',
    field: 'loginLimitUserId',
    component: 'InputTextArea',
  },
  {
    label: '登录限制设备',
    field: 'loginLimitDevice',
    component: 'InputTextArea',
  },
  // {
  //   label: '登录限制开关',
  //   field: 'loginLimitSwitch',
  //   component: 'JDictSelectTag',
  //   componentProps:{
  //     dictCode: "is_open"
  //   },
  //   dynamicRules: ({model,schema}) => {
  //     return [
  //             { required: true, message: '请输入登录限制开关!'},
  //            ];
  //   },
  // },
  // {
  //   label: '规则同步到注册',
  //   field: 'syncRegister',
  //   component: 'JDictSelectTag',
  //   componentProps:{
  //     dictCode: "is_open"
  //   },
  //   dynamicRules: ({model,schema}) => {
  //     return [
  //             { required: true, message: '请输入规则同步到注册!'},
  //            ];
  //   },
  // },
  {
    label: '限制跨登录开关',
    field: 'limitCrossLoginSwitch',
    defaultValue: '1',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "is_open"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请选择限制跨登录开关!'},
             ];
    },
  },
  {
    label: '备注',
    field: 'descs',
    component: 'Input',
  },
	// TODO 主键隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
];
