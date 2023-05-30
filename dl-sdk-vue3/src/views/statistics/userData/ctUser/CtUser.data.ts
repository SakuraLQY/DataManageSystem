import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
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
    title: '渠道ID',
    align: "center",
    dataIndex: 'channelId_dictText'
  },
  {
    title: '广告ID',
    align: "center",
    dataIndex: 'dealId'
  },
  {
    title: '广告名',
    align: "center",
    dataIndex: 'dealId_dictText'
  },
  {
    title: '设备ID',
    align: "center",
    dataIndex: 'uniqueId'
  },
  {
    title: '用户ID',
    align: "center",
    dataIndex: 'userId'
  },
  {
    title: '账号',
    align: "center",
    dataIndex: 'userName'
  },
  {
    title: '首服ID',
    align: "center",
    dataIndex: 'serverInit'
  },
  {
    title: '新服ID',
    align: "center",
    dataIndex: 'serverLast'
  },
  {
    title: '在线时长',
    align: "center",
    dataIndex: 'onlineTimeStr'
  },
  {
    title: '注册时间',
    align: "center",
    dataIndex: 'registerTime',
    customRender:({text}) =>{
      return !text?"":text;
    },
  },
  {
    title: '最近登录时间',
    align: "center",
    dataIndex: 'loginTime',
    customRender:({text}) =>{
      return !text?"":text;
    },
  },
  {
    title: '最新登录IP',
    align: "center",
    dataIndex: 'clientIp'
  },
  {
    title: 'IP归属地',
    align: "center",
    dataIndex: 'ipPlace'
  },
  {
    title: '封号操作',
    align: "center",
    dataIndex: 'blockadeUserId',
    slots: { customRender: 'blockadeUserId' },
  },
  {
    title: '封IP操作',
    align: "center",
    dataIndex: 'blockadeIp',
    slots: { customRender: 'blockadeIp' },
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "游戏",
    field: 'gameId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "子游戏",
    field: 'subGameId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "渠道游戏包",
    field: 'pkgId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "渠道ID",
    field: 'channelId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "广告ID",
    field: 'dealId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "用户ID",
    field: 'userId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "注册时间",
    field: 'registerTime',
    component: 'DatePicker',
    colProps: {span: 6},
  },
  {
    label: "最近登录时间",
    field: 'loginTime',
    component: 'DatePicker',
    colProps: {span: 6},
  },
  {
    label: "渠道类型id",
    field: 'channelTypeId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "渠道子账号id",
    field: 'channelSubAccountId',
    component: 'Input',
    colProps: {span: 6},
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '游戏',
    field: 'gameId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入游戏!'},
             ];
    },
  },
  {
    label: '子游戏',
    field: 'subGameId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入子游戏!'},
             ];
    },
  },
  {
    label: '渠道游戏包',
    field: 'pkgId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入渠道游戏包!'},
             ];
    },
  },
  {
    label: '渠道ID',
    field: 'channelId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入渠道ID!'},
             ];
    },
  },
  {
    label: '广告ID',
    field: 'dealId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入广告ID!'},
             ];
    },
  },
  {
    label: '设备ID',
    field: 'uniqueId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入设备ID!'},
             ];
    },
  },
  {
    label: '用户ID',
    field: 'userId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入用户ID!'},
             ];
    },
  },
  {
    label: '首服ID',
    field: 'serverInit',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入首服ID!'},
             ];
    },
  },
  {
    label: '新服ID',
    field: 'serverLast',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入新服ID!'},
             ];
    },
  },
  {
    label: '在线时长',
    field: 'onlineTime',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入在线时长!'},
             ];
    },
  },
  {
    label: '注册时间',
    field: 'registerTime',
    component: 'DatePicker',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入注册时间!'},
             ];
    },
  },
  {
    label: '最近登录时间',
    field: 'loginTime',
    component: 'DatePicker',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入最近登录时间!'},
             ];
    },
  },
  {
    label: '最新登录IP',
    field: 'clientIp',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入最新登录IP!'},
             ];
    },
  },
  {
    label: '渠道类型id',
    field: 'channelTypeId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入渠道类型id!'},
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
    label: '当天在线时长',
    field: 'dayOnlineTime',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入当天在线时长!'},
             ];
    },
  },
  {
    label: '登陆标记',
    field: 'loginMask',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入登陆标记!'},
             ];
    },
  },
  {
    label: '充值时间',
    field: 'payTime',
    component: 'DatePicker',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入充值时间!'},
             ];
    },
  },
  {
    label: '上报时间',
    field: 'aliveTime',
    component: 'DatePicker',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入上报时间!'},
             ];
    },
  },
  {
    label: '首次支付时间',
    field: 'timeFirstPay',
    component: 'DatePicker',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入首次支付时间!'},
             ];
    },
  },
  {
    label: '用户支付留存',
    field: 'payUserLoginMask',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入用户支付留存!'},
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
