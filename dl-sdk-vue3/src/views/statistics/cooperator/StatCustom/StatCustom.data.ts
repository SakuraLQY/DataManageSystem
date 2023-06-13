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
    title: '名称',
    align: "center",
    dataIndex: 'name'
  },
  {
    title: '激活数',
    align: "center",
    dataIndex: 'countActive'
  },
  {
    title: '注册数',
    align: "center",
    dataIndex: 'countUser'
  },
  {
    title: '有效注册数',
    align: "center",
    dataIndex: 'countValidUser'
  },
  {
    title: '新增付费人数',
    align: "center",
    dataIndex: 'firstPayuser'
  },
  {
    title: '新增付费金额',
    align: "center",
    dataIndex: 'firstMoney'
  },
  {
    title: '新增付费率',
    align: "center",
    dataIndex: 'addCostRate'
  },
  {
    title: '活跃人数',
    align: "center",
    dataIndex: 'countDau'
  },
  {
    title: '活跃付费人数',
    align: "center",
    dataIndex: 'alivePayuser'
  },
  {
    title: '付费总额',
    align: "center",
    dataIndex: 'aliveMoney'
  },
  {
    title: '活跃付费率',
    align: "center",
    dataIndex: 'aliveMoneyRate'
  },
  {
    title: '首日ARPU',
    align: "center",
    dataIndex: 'firstArpu'
  },
  {
    title: '总ARPU',
    align: "center",
    dataIndex: 'totalArpu'
  },
  {
    title: '次留',
    align: "center",
    dataIndex: 'day2'
  },
  {
    title: '3日留存',
    align: "center",
    dataIndex: 'day3'
  },
  {
    title: '7日留存',
    align: "center",
    dataIndex: 'day7'
  },
  {
    title: '15日留存',
    align: "center",
    dataIndex: 'day15'
  },
  {
    title: '30日留存',
    align: "center",
    dataIndex: 'day30'
  },
  {
    title: 'LTV1',
    align: "center",
    dataIndex: 'ltv1'
  },
  {
    title: 'LTV3',
    align: "center",
    dataIndex: 'ltv3'
  },
  {
    title: 'LTV7',
    align: "center",
    dataIndex: 'ltv7'
  },
  {
    title: 'LTV30',
    align: "center",
    dataIndex: 'ltv30'
  },
  {
    title: 'LTV60',
    align: "center",
    dataIndex: 'ltv60'
  },
  {
    title: 'LTV90',
    align: "center",
    dataIndex: 'ltv90'
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
    label: '所属渠道',
    field: 'channelId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入所属渠道!'},
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
    label: '角色ID',
    field: 'roleId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入角色ID!'},
             ];
    },
  },
  {
    label: '角色名',
    field: 'roleName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入角色名!'},
             ];
    },
  },
  {
    label: '角色等级',
    field: 'roleLevel',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入角色等级!'},
             ];
    },
  },
  {
    label: '服务器ID',
    field: 'serverId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入服务器ID!'},
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
    field: 'createTime',
    component: 'DatePicker',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入注册时间!'},
             ];
    },
  },
  {
    label: '最新登录时间',
    field: 'aliveTime',
    component: 'DatePicker',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入最新登录时间!'},
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
    label: '服务器名',
    field: 'serverName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入服务器名!'},
             ];
    },
  },
  {
    label: '注册-设备',
    field: 'createDevice',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入注册-设备!'},
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
