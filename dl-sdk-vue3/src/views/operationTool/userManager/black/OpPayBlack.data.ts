import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '规则类型',
    align: "center",
    dataIndex: 'ruleType',
    customRender: ({ text }) => {
      return render.renderDict(text, 'pay_rule_type');
    },
  },
  {
    title: '游戏名称(游戏ID)',
    align: "center",
    dataIndex: "ruleNameAndId",
    slots: { customRender: 'ruleNameAndId' },
  },
  {
    title: '支付限制用户ID',
    align: "center",
    dataIndex: 'payLimitUserId'
  },
  {
    title: '支付限制IP',
    align: "center",
    dataIndex: 'payLimitIp'
  },
  {
    title: '支付限制设备',
    align: "center",
    dataIndex: 'payLimitDevice'
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
  {
    title: '更新用户',
    align: "center",
    dataIndex: 'updateBy'
  },
  {
    title: '更新时间',
    align: "center",
    dataIndex: 'updateTime',
    customRender:({text}) =>{
      return !text?"":text;
    },
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "规则类型",
    field: 'ruleType',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "pay_rule_type"
    },
    colProps: {span: 6},
  },
  {
    label: "规则ID",
    field: 'ruleId',
    component: 'Input',
    colProps: {span: 6},
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '规则类型',
    field: 'ruleType',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "pay_rule_type"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入规则类型!'},
             ];
    },
  },
  {
    label: '规则ID',
    field: 'ruleId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入规则ID!'},
             ];
    },
  },
  {
    label: '支付限制用户ID',
    field: 'payLimitUserId',
    component: 'InputTextArea',
  },
  {
    label: '支付限制IP',
    field: 'payLimitIp',
    component: 'InputTextArea',
  },
  {
    label: '支付限制设备',
    field: 'payLimitDevice',
    component: 'InputTextArea',
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
