import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '日期',
    align: "center",
    dataIndex: 'roiDate'
  },
  {
    title: '游戏名',
    align: "center",
    dataIndex: 'gameName'
  },
  {
    title: '渠道名',
    align: "center",
    dataIndex: 'channelName'
  },
  {
    title: '广告名',
    align: "center",
    dataIndex: 'dealName'
  },
  {
    title: '推广费用',
    align: "center",
    dataIndex: 'promotionCost'
  },
  {
    title: '新增注册数',
    align: "center",
    dataIndex: 'regCount'
  },
  {
    title: '剩余DAU',
    align: "center",
    dataIndex: 'remainDAU'
  },
  {
    title: '付费DAU',
    align: "center",
    dataIndex: 'costDAU'
  },
  {
    title: '注册单价',
    align: "center",
    dataIndex: 'regUnitPrice'
  },
  {
    title: '新增付费人数',
    align: "center",
    dataIndex: 'costCount'
  },
  {
    title: '付费率',
    align: "center",
    dataIndex: 'costProbability'
  },
  {
    title: '付费单价',
    align: "center",
    dataIndex: 'costUnitPrice'
  },
  {
    title: '新增付费金额',
    align: "center",
    dataIndex: 'addCostPrice'
  },
  {
    title: '累计付费金额',
    align: "center",
    dataIndex: 'allCostPrice'
  },
  {
    title: 'sdk分成所得',
    align: "center",
    dataIndex: 'sdkShare'
  },
  {
    title: '利润',
    align: "center",
    dataIndex: 'profit'
  },
  // {
  //   title: '预估ROI',
  //   align: "center",
  //   dataIndex: 'estimatedROI'
  // },
  {
    title: '累计ROI',
    align: "center",
    dataIndex: 'allROI'
  },
  {
    title: '1日ROI(0)',
    align: "center",
    dataIndex: 'firstROI'
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
