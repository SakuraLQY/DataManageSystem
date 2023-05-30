import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: 'ID',
    align: "center",
    dataIndex: 'id'
  },
  {
    title: '渠道游戏包',
    align: "center",
    dataIndex: 'pkgName'
  },
  {
    title: '所属渠道',
    align: "center",
    dataIndex: 'channelName'
  },
  {
    title: '回调渠道',
    align: "center",
    dataIndex: 'callbackChannel'
  },
  {
    title: '回调事件类型',
    align: "center",
    dataIndex: 'eventType_dictText'
  },
  {
    title: '广告-id',
    align: "center",
    dataIndex: 'dealId'
  },
  {
    title: '回调状态',
    align: "center",
    dataIndex: 'callbackState_dictText'
  },
  {
    title: '回调次数',
    align: "center",
    dataIndex: 'callbackNum'
  },
  {
    title: '更新时间',
    align: "center",
    dataIndex: 'updateTime'
  },
  {
    title: '回调参数',
    align: "center",
    dataIndex: 'callbackData'
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "游戏id",
    field: 'gameId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "子游戏id",
    field: 'subGameId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "渠道游戏包id",
    field: 'pkgId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "渠道id",
    field: 'channelId',
    component: 'Input',
    colProps: {span: 6},
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '游戏id',
    field: 'gameId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入游戏id!'},
             ];
    },
  },
  {
    label: '子游戏id',
    field: 'subGameId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入子游戏id!'},
             ];
    },
  },
  {
    label: '渠道游戏包id',
    field: 'pkgId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入渠道游戏包id!'},
             ];
    },
  },
  {
    label: '渠道id',
    field: 'channelId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入渠道id!'},
             ];
    },
  },
  {
    label: '渠道子账号id',
    field: 'channelSubAccountId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入渠道子账号id!'},
             ];
    },
  },
  {
    label: '回调渠道',
    field: 'callbackChannel',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入回调渠道!'},
             ];
    },
  },
  {
    label: '广告-id',
    field: 'dealId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入广告-id!'},
             ];
    },
  },
  {
    label: '回调事件类型',
    field: 'eventType',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入回调事件类型!'},
             ];
    },
  },
  {
    label: '回调数据',
    field: 'callbackData',
    component: 'InputTextArea',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入回调数据!'},
             ];
    },
  },
  {
    label: '回调次数',
    field: 'callbackNum',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入回调次数!'},
             ];
    },
  },
  {
    label: '回调状态：1为待回调，2为回调成功，3为回调失败',
    field: 'callbackState',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入回调状态：1为待回调，2为回调成功，3为回调失败!'},
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
