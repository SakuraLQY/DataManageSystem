import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '计划ID',
    align: "center",
    dataIndex: 'id'
  },
  {
    title: '计划名(主播)',
    align: "center",
    dataIndex: 'planName'
  },
  {
    title: '抖音ID',
    align: "center",
    dataIndex: 'trillId'
  },
  {
    title: '绑定广告ID',
    align: "center",
    dataIndex: 'dealId_dictText',
    slots: { customRender: 'dealId_dictText' },
  },
  {
    title: '备注',
    align: "center",
    dataIndex: 'planDesc'
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
    label: "计划名(主播)",
    field: 'planName',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "抖音ID",
    field: 'trillId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "绑定广告ID",
    field: 'dealId',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '计划名(主播)',
    field: 'planName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入计划名(主播)!'},
             ];
    },
  },
  {
    label: '抖音ID',
    field: 'trillId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入抖音ID!'},
             ];
    },
  },
  {
    label: '绑定广告ID',
    field: 'dealId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
  },
  {
    label: '备注',
    field: 'planDesc',
    component: 'InputTextArea',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入备注!'},
             ];
    },
  },
	// TODO 主键隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
];
