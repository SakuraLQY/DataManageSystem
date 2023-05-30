import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '注册日期',
    align: "center",
    dataIndex: 'regTime'
  },
  {
    title: '注册数',
    align: "center",
    dataIndex: 'regCount'
  },
  {
    title: '新增付费人数',
    align: "center",
    dataIndex: 'firstPayUser'
  },
  {
    title: '新增付费',
    align: "center",
    dataIndex: 'firstPay'
  },
  {
    title: '期间付费人数',
    align: "center",
    dataIndex: 'periodUser'
  },
  {
    title: '期间付费',
    align: "center",
    dataIndex: 'periodMoney'
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "游戏项目",
    field: 'gameId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "游戏名称",
    field: 'subGameId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "子游戏包",
    field: 'pkgId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "渠道",
    field: 'channelId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "渠道类型",
    field: 'channelTypeId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "子渠道",
    field: 'channelSubAccountId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "注册时间",
    field: 'startTime',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "结束时间",
    field: 'endTime',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "付费日期",
    field: 'payStart',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "结束日期",
    field: 'payEnd',
    component: 'Input',
    colProps: {span: 6},
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '游戏项目',
    field: 'gameId',
    component: 'InputNumber',
  },
  {
    label: '游戏名称',
    field: 'subGameId',
    component: 'Input',
  },
  {
    label: '子游戏包',
    field: 'pkgId',
    component: 'Input',
  },
  {
    label: '渠道',
    field: 'channelId',
    component: 'Input',
  },
  {
    label: '渠道类型',
    field: 'channelTypeId',
    component: 'Input',
  },
  {
    label: '子渠道',
    field: 'channelSubAccountId',
    component: 'Input',
  },
  {
    label: '注册时间',
    field: 'startTime',
    component: 'Input',
  },
  {
    label: '结束时间',
    field: 'endTime',
    component: 'Input',
  },
  {
    label: '付费日期',
    field: 'payStart',
    component: 'Input',
  },
  {
    label: '结束日期',
    field: 'payEnd',
    component: 'Input',
  },
	// TODO 主键隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
];
