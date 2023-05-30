import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: 'ID',
    align: "center",
    dataIndex: 'lanuchId'
  },
  {
    title: '名称',
    align: "center",
    dataIndex: 'name'
  },
  {
    title: '成本',
    align: "center",
    dataIndex: 'cost'
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
    title: 'DAU',
    align: "center",
    dataIndex: 'countDau'
  },
  {
    title: '有效注册数',
    align: "center",
    dataIndex: 'countValidUser'
  },
  {
    title: '激活注册率',
    align: "center",
    dataIndex: 'registrationRate'
  },
  {
    title: '注册单价',
    align: "center",
    dataIndex: 'registryPrice'
  },
  {
    title: '首日付费额',
    align: "center",
    dataIndex: 'firstMoney'
  },
  {
    title: '首日付费人数',
    align: "center",
    dataIndex: 'firstPayuser'
  },
  {
    title: '首日付费率',
    align: "center",
    dataIndex: 'firstPayrate'
  },
  {
    title: '首日付费单价',
    align: "center",
    dataIndex: 'firstPayprice'
  },
  {
    title: '首日arpu',
    align: "center",
    dataIndex: 'firstArpu'
  },
  {
    title: '首日arppu',
    align: "center",
    dataIndex: 'firstArppu'
  },
  {
    title: '首日ROI',
    align: "center",
    dataIndex: 'firstRoi'
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "游戏选择",
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
    label: "游戏包",
    field: 'pkgId',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
  {
    label: "渠道类型",
    field: 'channelTypeId',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
  {
    label: "渠道",
    field: 'channelId',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
  {
    label: "渠道子账号",
    field: 'channelSubAccountId',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
  {
    label: "广告编号",
    field: 'dealId',
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
    },
    colProps: {span: 6},
  },
  {
    label: "注册时间",
    field: 'registryTime',
    component: 'DatePicker',
    colProps: {span: 6},
  },
  // {
  //   label: "付费时间",
  //   field: 'payTime',
  //   component: 'DatePicker',
  //   colProps: {span: 6},
  // },
  {
    label: "归类方式",
    field: 'type',
    component: 'Input',
    colProps: {span: 6},
  },
];

//归类方式
export const compareTypeOption = [
  {
    label: '全部',
    value: 'cost_day'
  },
  {
    label: '按广告',
    value: 'deal_id'
  },
  {
    label: '按渠道',
    value: 'channel_id'
  },
  {
    label: '按游戏',
    value: 'game_id'
  },
  {
    label: '按子游戏',
    value: 'sub_game_id'
  },
  {
    label: '按游戏包',
    value: 'pkg_id'
  },
  {
    label: '按渠道子账号分类',
    value: 'channel_sub_account_id'
  },
  {
    label: '按投放账号',
    value: 'account_id'
  },

]

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '游戏选择',
    field: 'gameId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
  },
  {
    label: '子游戏',
    field: 'subGameId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
  },
  {
    label: '游戏包',
    field: 'pkgId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
  },
  {
    label: '渠道类型',
    field: 'channelTypeId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
  },
  {
    label: '渠道',
    field: 'channelId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
  },
  {
    label: '渠道子账号',
    field: 'channelSubAccountId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
  },
  {
    label: '广告编号',
    field: 'dealId',
    component: 'JSelectMultiple',
    componentProps:{
      dictCode: ""
    },
  },
  {
    label: '投放账号',
    field: 'account',
    component: 'JSelectMultiple',
    componentProps:{
      dictCode: ""
    },
  },
  {
    label: '注册时间',
    field: 'registryTime',
    component: 'DatePicker',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入注册时间!'},
             ];
    },
  },
  // {
  //   label: '付费时间',
  //   field: 'payTime',
  //   component: 'DatePicker',
  //   dynamicRules: ({model,schema}) => {
  //     return [
  //             { required: true, message: '请输入付费时间!'},
  //            ];
  //   },
  // },
  {
    label: '归类方式',
    field: 'type',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入归类方式!'},
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
