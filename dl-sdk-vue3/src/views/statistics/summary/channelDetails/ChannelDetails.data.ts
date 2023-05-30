import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '日期',
    align: "center",
    dataIndex: 'timeDaily'
  },
  {
    title: '游戏名',
    align: "center",
    dataIndex: 'gameName'
  },
  {
    title: '子游戏',
    align: "center",
    dataIndex: 'subGameName'
  },
  {
    title: '渠道',
    align: "center",
    dataIndex: 'channel'
  },
  {
    title: '激活',
    align: "center",
    dataIndex: 'countActive'
  },
  {
    title: '激活设备数',
    align: "center",
    dataIndex: 'countActiveDev'
  },
  {
    title: '注册数',
    align: "center",
    dataIndex: 'countUser'
  },
  {
    title: '注册设备数',
    align: "center",
    dataIndex: 'countUserDev'
  },
  {
    title: '激活注册率',
    align: "center",
    dataIndex: 'activeRate'
  },
  {
    title: '有效注册数',
    align: "center",
    dataIndex: 'countValidUser'
  },
  {
    title: '新增付费人数',
    align: "center",
    dataIndex: 'firstPayUser'
  },
  {
    title: '新增付费金额',
    align: "center",
    dataIndex: 'firstMoney'
  },
  {
    title: '新增付费率',
    align: "center",
    dataIndex: 'firstPayRate'
  },
  {
    title: '新增ARPU',
    align: "center",
    dataIndex: 'firstArpu'
  },
  {
    title: '新增ARPPU',
    align: "center",
    dataIndex: 'firstArppu'
  },
  {
    title: '老用户付费数',
    align: "center",
    dataIndex: 'oldPayUser'
  },
  {
    title: '老用户付费金额',
    align: "center",
    dataIndex: 'oldMoney'
  },
  {
    title: '老用户付费率',
    align: "center",
    dataIndex: 'oldPayRate'
  },
  {
    title: '老用户ARPU',
    align: "center",
    dataIndex: 'oldArpu'
  },
  {
    title: '老用户ARPPU',
    align: "center",
    dataIndex: 'oldArppu'
  },
  {
    title: '累计付费金额',
    align: "center",
    dataIndex: 'totalMoney'
  },
  {
    title: 'DAU',
    align: "center",
    dataIndex: 'dau'
  },
  {
    title: '活跃付费人数',
    align: "center",
    dataIndex: 'alivePayUser'
  },
  {
    title: '活跃付费金额',
    align: "center",
    dataIndex: 'aliveMoney'
  },
  {
    title: 'ARPU',
    align: "center",
    dataIndex: 'arpu'
  },
  {
    title: 'ARPPU',
    align: "center",
    dataIndex: 'arppu'
  },
  {
    title: '总付费率',
    align: "center",
    dataIndex: 'totalPayRate'
  },
  {
    title: '次留',
    align: "center",
    dataIndex: 'loyal'
  },
  {
    title: '3日留存',
    align: "center",
    dataIndex: 'loyal3'
  },
  {
    title: '4日留存',
    align: "center",
    dataIndex: 'loyal4'
  },
  {
    title: '5日留存',
    align: "center",
    dataIndex: 'loyal5'
  },
  {
    title: '6日留存',
    align: "center",
    dataIndex: 'loyal6'
  },
  {
    title: '7日留存',
    align: "center",
    dataIndex: 'loyal7'
  },
  {
    title: 'LTV1',
    align: "center",
    dataIndex: 'ltv1'
  },
  {
    title: 'LTV2',
    align: "center",
    dataIndex: 'ltv2'
  },
  {
    title: 'LTV3',
    align: "center",
    dataIndex: 'ltv3'
  },
  {
    title: 'LTV4',
    align: "center",
    dataIndex: 'ltv4'
  },
  {
    title: 'LTV5',
    align: "center",
    dataIndex: 'ltv5'
  },
  {
    title: 'LTV6',
    align: "center",
    dataIndex: 'ltv6'
  },
  {
    title: 'LTV7',
    align: "center",
    dataIndex: 'ltv7'
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "游戏项目",
    field: 'gameId',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
  {
    label: "游戏名称",
    field: 'subGameId',
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
    label: "渠道名称",
    field: 'channelId',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
  {
    label: "起始日期",
    field: 'startTime',
    component: 'DatePicker',
    colProps: {span: 6},
  },
  {
    label: "结束日期",
    field: 'endTime',
    component: 'DatePicker',
    colProps: {span: 6},
  },
  {
    label: "展示方式",
    field: 'showType',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
  {
    label: "日期",
    field: 'dateTime',
    component: 'Input',
    colProps: {span: 6},
  },
];

//展示方式
export const showTypeOption = [
  {
    label:'详细数据',
    value: 'detail'
  },
  {
    label: '完整数据',
    value: 'total'
  }
]

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '游戏项目',
    field: 'gameId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
  },
  {
    label: '游戏名称',
    field: 'subGameId',
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
    label: '渠道名称',
    field: 'channelId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
  },
  {
    label: '起始日期',
    field: 'startTime',
    component: 'DatePicker',
  },
  {
    label: '结束日期',
    field: 'endTime',
    component: 'DatePicker',
  },
  {
    label: '展示方式',
    field: 'showType',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
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
