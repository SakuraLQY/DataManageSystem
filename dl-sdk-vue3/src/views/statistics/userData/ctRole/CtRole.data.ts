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
    title: '所属渠道',
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
    title: '角色ID',
    align: "center",
    dataIndex: 'roleId'
  },
  {
    title: '角色名',
    align: "center",
    dataIndex: 'roleName'
  },
  {
    title: '角色等级',
    align: "center",
    dataIndex: 'roleLevel'
  },
  {
    title: '服务器ID',
    align: "center",
    dataIndex: 'serverId'
  },
  {
    title: '在线时长',
    align: "center",
    dataIndex: 'onlineTimeStr'
  },
  {
    title: '注册时间',
    align: "center",
    dataIndex: 'createTime',
    customRender:({text}) =>{
      return !text?"":text;
    },
  },
  {
    title: '最新登录时间',
    align: "center",
    dataIndex: 'aliveTime',
    customRender:({text}) =>{
      return !text?"":text;
    },
  },
  {
    title: '封号操作',
    align: "center",
    dataIndex: 'blockadeUserId',
    slots: { customRender: 'blockadeUserId' },
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
