import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '渠道子账号ID',
    align: "center",
    dataIndex: 'id'
  },
  {
    title: '归属渠道',
    align: "center",
    dataIndex: 'channelId_dictText'
  },
  {
    title: '渠道类型',
    align: "center",
    dataIndex: 'channelTypeId_dictText'
  },
  {
    title: '渠道子账号名称',
    align: "center",
    dataIndex: 'subAccountName'
  },
  {
    title: '渠道子账号昵称',
    align: "center",
    dataIndex: 'subAccountNickname'
  },
  {
    title: '备注',
    align: "center",
    dataIndex: 'subAccountDesc'
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
    label: "归属渠道",
    field: 'channelId',
    component: 'JSelectMultiple',
    componentProps: {
      dictCode: "open_gateway.op_channel,channel_name,id"
    },
    colProps: {span: 6},
  },
  {
    label: "渠道类型",
    field: 'channelTypeId',
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
    label: '归属渠道',
    field: 'channelId',
    component: 'JSelectMultiple',
    componentProps:{
      dictCode: "open_gateway.op_channel,channel_name,id"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入归属渠道!'},
             ];
    },
  },
  {
    label: '渠道子账号名称',
    field: 'subAccountName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入渠道子账号名称!'},
             ];
    },
  },
  {
    label: '渠道子账号昵称',
    field: 'subAccountNickname',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入渠道子账号昵称!'},
             ];
    },
  },
  {
    label: '备注',
    field: 'subAccountDesc',
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
