import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '日期',
    align: "center",
    dataIndex: 'dateTime'
  },
  {
    title: '广告名称',
    align: "center",
    dataIndex: 'dealName'
  },
  {
    title: '游戏名称',
    align: "center",
    dataIndex: 'gameName'
  },
  {
    title: '激活数',
    align: "center",
    dataIndex: 'countActive'
  },
  {
    title: '注册数',
    align: "center",
    dataIndex: 'regCount'
  },
  {
    title: '有效注册数',
    align: "center",
    dataIndex: 'validReg'
  },
  {
    title: '新增付费人数',
    align: "center",
    dataIndex: 'firstPayUser'
  },
  {
    title: '新增付费金额',
    align: "center",
    dataIndex: 'firstPayMoney'
  },
  {
    title: '活跃人数',
    align: "center",
    dataIndex: 'countDau'
  },
  {
    title: '活跃付费人数',
    align: "center",
    dataIndex: 'alivePayUser'
  },
  {
    title: '付费总额',
    align: "center",
    dataIndex: 'totalMoney'
  },
];

export const typeOption = [
  {
    label: '按广告',
    value: 'deal_id'
  },{
    label: '按日期',
    value:  'time_daily'
  }
]
//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "广告id",
    field: 'dealId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "开始时间",
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
    label: "归类方式",
    field: 'type',
    component: 'Input',
    colProps: {span: 6},
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '广告id',
    field: 'dealId',
    component: 'Input',
  },
  {
    label: '开始时间',
    field: 'startTime',
    component: 'Input',
  },
  {
    label: '结束时间',
    field: 'endTime',
    component: 'Input',
  },
  {
    label: '归类方式',
    field: 'type',
    component: 'Input',
  },
  {
    label: '日期',
    field: 'dateTime',
    component: 'Input',
  },
  {
    label: '广告名称',
    field: 'dealName',
    component: 'Input',
  },
  {
    label: '游戏名称',
    field: 'gameName',
    component: 'Input',
  },
  {
    label: '激活数',
    field: 'countActive',
    component: 'Input',
  },
  {
    label: '注册数',
    field: 'regCount',
    component: 'Input',
  },
  {
    label: '有效注册数',
    field: 'validReg',
    component: 'Input',
  },
  {
    label: '新增付费人数',
    field: 'firstPayUser',
    component: 'Input',
  },
  {
    label: '新增付费金额',
    field: 'firstPayMoney',
    component: 'Input',
  },
  {
    label: '活跃人数',
    field: 'countDau',
    component: 'Input',
  },
  {
    label: '活跃付费人数',
    field: 'alivePayUser',
    component: 'Input',
  },
  {
    label: '付费总额',
    field: 'totalMoney',
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
