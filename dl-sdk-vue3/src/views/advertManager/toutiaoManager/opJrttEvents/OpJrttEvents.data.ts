import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '所属资产',
    align: "center",
    dataIndex: 'assetsId_dictText'
  },
  {
    title: '事件',
    align: "center",
    dataIndex: 'eventId_dictText'
  },
  {
    title: '回传方式',
    align: "center",
    dataIndex: 'trackType_dictText'
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
    label: "事件名称",
    field: 'eventName',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "回传方式",
    field: 'trackType',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "track_type"
    },
    colProps: {span: 6},
  },
  {
    label: "创建用户",
    field: 'createBy',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "创建时间",
    field: 'createTime',
    component: 'DatePicker',
    colProps: {span: 6},
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '所属资产',
    field: 'assetsId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "open_gateway.op_jrtt_assets,asset_name,id"
    },
    dynamicDisabled: true
  },
  {
    label: '事件ID',
    field: 'eventId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入事件ID!'},
             ];
    },
  },
  {
    label: '回传方式',
    field: 'trackType',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "track_type"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入回传方式!'},
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
