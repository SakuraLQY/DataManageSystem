import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '渠道ID',
    align: "center",
    dataIndex: 'id',
  },
  {
    title: '渠道名称',
    align: "center",
    dataIndex: 'channelName',
  },
  {
    title: '渠道类型',
    align: "center",
    dataIndex: 'typeId_dictText'
  },
  {
    title: '渠道昵称',
    align: "center",
    dataIndex: 'channelNickname'
  },
  {
    title: '备注',
    align: "center",
    dataIndex: 'channelDesc'
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
    label: "渠道名称",
    field: 'channelName',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "渠道类型",
    field: 'typeId',
    component: 'JSearchSelect',
    componentProps:{
      dict: "open_gateway.op_channel_type,type_name,id"
    },
    colProps: {span: 6},
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '渠道名称',
    field: 'channelName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入渠道名称!'},
             ];
    },
  },
  {
    label: '渠道类型',
    field: 'typeId',
    component: 'JSearchSelect',
    componentProps:{
      dict: "open_gateway.op_channel_type,type_name,id"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入渠道类型!'},
             ];
    },
  },
  {
    label: '渠道昵称',
    field: 'channelNickname',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入渠道昵称!'},
             ];
    },
  },
  {
    label: '备注',
    field: 'channelDesc',
    component: 'Input',
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
