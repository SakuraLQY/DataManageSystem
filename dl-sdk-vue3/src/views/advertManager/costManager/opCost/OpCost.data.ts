import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '成本日期 ',
    align: "center",
    dataIndex: 'costDay',
    customRender:({text}) =>{
      return !text?"":(text.length>10?text.substr(0,10):text);
    },
  },
  {
    title: '游戏',
    align: "center",
    dataIndex: 'gameName'
  },
  {
    title: '子游戏',
    align: "center",
    dataIndex: 'subGameName'
  },
  {
    title: '渠道游戏包',
    align: "center",
    dataIndex: 'pkgName'
  },
  {
    title: '渠道',
    align: "center",
    dataIndex: 'channelName'
  },
  {
    title: '渠道子账号',
    align: "center",
    dataIndex: 'channelSubAccountName'
  },
  {
    title: '广告id',
    align: "center",
    dataIndex: 'dealId'
  },
  {
    title: '广告名',
    align: "center",
    dataIndex: 'dealName'
  },
  {
    title: '成本平台',
    align: "center",
    dataIndex: 'platform_dictText'
  },
  {
    title: '渠道广告id',
    align: "center",
    dataIndex: 'channelDealId'
    
  },
  {
    title: '投放账号',
    align: "center",
    dataIndex: 'accountName'
  },
  {
    title: '投放成本',
    align: "center",
    dataIndex: 'costMoney'
  },
  {
    title: '展示',
    align: "center",
    dataIndex: 'exhibition'
  },
  {
    title: '点击',
    align: "center",
    dataIndex: 'click'
  },
  {
    title: '下载',
    align: "center",
    dataIndex: 'download'
  },
  {
    title: '备注',
    align: "center",
    dataIndex: 'costDesc'
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
    label: "渠道类型id",
    field: 'channelTypeId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "渠道id",
    field: 'channelId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "渠道子账号id",
    field: 'channelSubAccountId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "广告id",
    field: 'dealId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "渠道广告id",
    field: 'channelDealId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "成本日期 ",
    field: 'costDay',
    component: 'DatePicker',
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
    label: '广告id',
    field: 'dealId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入广告id!'},
             ];
    },
  },
  {
    label: '渠道广告id',
    field: 'channelDealId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入渠道广告id!'},
             ];
    },
  },
  {
    label: '成本日期 ',
    field: 'costDay',
    component: 'DatePicker',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入成本日期 !'},
             ];
    },
  },
  {
    label: '成本金额',
    field: 'costMoney',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入成本金额!'},
             ];
    },
  },
  {
    label: '下载',
    field: 'download',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入下载!'},
             ];
    },
  },
  {
    label: '点击',
    field: 'click',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入点击!'},
             ];
    },
  },
  {
    label: '展示',
    field: 'exhibition',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入展示!'},
             ];
    },
  },
  {
    label: '投放账号id',
    field: 'accountId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入投放账号id!'},
             ];
    },
  },
  {
    label: '成本平台，0表示全部平台，1为安卓，2为ios',
    field: 'platform',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入成本平台，0表示全部平台，1为安卓，2为ios!'},
             ];
    },
  },
  {
    label: '成本-描述',
    field: 'costDesc',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入成本-描述!'},
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
