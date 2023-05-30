import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '头条资产ID',
    align: "center",
    dataIndex: 'assetId'
  },
  {
    title: '资产名',
    align: "center",
    dataIndex: 'assetName'
  },
  {
    title: '游戏',
    align: "center",
    dataIndex: 'gameId_dictText'
  },
  {
    title: '子游戏',
    align: "center",
    dataIndex: 'subGameId_dictText'
  },
  {
    title: '渠道游戏包',
    align: "center",
    dataIndex: 'pkgId_dictText'
  },
  {
    title: '投放账号',
    align: "center",
    dataIndex: 'accountId_dictText'
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
    label: "资产名",
    field: 'assetName',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "游戏",
    field: 'gameId',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
  {
    label: "子游戏",
    field: 'subGameId',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
  {
    label: "渠道游戏包",
    field: 'pkgId',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
  {
    label: "投放账号",
    field: 'accountId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "op_put_account where channel_Id = 4 and state = 1 and level_id = 2 ,nick_name,id"
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
    label: '头条资产ID',
    field: 'assetId',
    component: 'Input',
  },
  {
    label: '资产名',
    field: 'assetName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入资产名!'},
             ];
    },
  },
  {
    label: '游戏',
    field: 'gameId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入游戏!'},
             ];
    },
  },
  {
    label: '子游戏',
    field: 'subGameId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入子游戏!'},
             ];
    },
  },
  {
    label: '渠道游戏包',
    field: 'pkgId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入渠道游戏包!'},
             ];
    },
  },
  {
    label: '投放账号',
    field: 'accountId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "op_put_account where channel_Id = 4 and state = 1 and level_id = 2 ,nick_name,id"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入投放账号!'},
             ];
    },
  },
  {
    label: '渠道ID （4/5  头条/星图）',
    field: 'channelId',
    component: 'InputNumber',
  },
	// TODO 主键隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
];
