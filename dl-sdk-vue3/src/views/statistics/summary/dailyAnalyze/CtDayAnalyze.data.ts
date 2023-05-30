import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export interface Item{
  level:string;
  zeroHour:string;
  oneHour:string;
  twoHour:string;
  threeHour:string;
  fourHour:string;
  fiveHour:string;
  sixHour:string;
  sevenHour:string;
  eightHour:string;
  nineHour:string;
  tenHour:string;
  elevenHour:string;
  twelveHour:string;
  thirteenHour:string;
  fourteenHour:string;
  fifteenHour:string;
  sixteenHour:string;
  seventeenHour:string;
  eighteenHour:string;
  nineteenHour:string;
  twentyHour:string;
  twentyoneHour:string;
  twentytwoHour:string;
  twentythreeHour:string;
}

export const columns: BasicColumn[] = [
  {
    title: '维度',
    align: "center",
    dataIndex: 'level'
  },
  {
    title: '0时',
    align: "center",
    dataIndex: 'zeroHour'
  },
  {
    title: '1时',
    align: "center",
    dataIndex: 'oneHour'
  },
  {
    title: '2时',
    align: "center",
    dataIndex: 'twoHour'
  },
  {
    title: '3时',
    align: "center",
    dataIndex: 'threeHour'
  },
  {
    title: '4时',
    align: "center",
    dataIndex: 'fourHour'
  },
  {
    title: '5时',
    align: "center",
    dataIndex: 'fiveHour'
  },
  {
    title: '6时',
    align: "center",
    dataIndex: 'sixHour'
  },
  {
    title: '7时',
    align: "center",
    dataIndex: 'sevenHour'
  },
  {
    title: '8时',
    align: "center",
    dataIndex: 'eightHour'
  },
  {
    title: '9时',
    align: "center",
    dataIndex: 'nineHour'
  },
  {
    title: '10时',
    align: "center",
    dataIndex: 'tenHour'
  },
  {
    title: '11时',
    align: "center",
    dataIndex: 'elevenHour'
  },
  {
    title: '12时',
    align: "center",
    dataIndex: 'twelveHour'
  },
  {
    title: '13时',
    align: "center",
    dataIndex: 'thirteenHour'
  },
  {
    title: '14时',
    align: "center",
    dataIndex: 'fourteenHour'
  },
  {
    title: '15时',
    align: "center",
    dataIndex: 'fifteenHour'
  },
  {
    title: '16时',
    align: "center",
    dataIndex: 'sixteenHour'
  },
  {
    title: '17时',
    align: "center",
    dataIndex: 'seventeenHour'
  },
  {
    title: '18时',
    align: "center",
    dataIndex: 'eighteenHour'
  },
  {
    title: '19时',
    align: "center",
    dataIndex: 'nineteenHour'
  },
  {
    title: '20时',
    align: "center",
    dataIndex: 'twentyHour'
  },
  {
    title: '21时',
    align: "center",
    dataIndex: 'twentyoneHour'
  },
  {
    title: '22时',
    align: "center",
    dataIndex: 'twentytwoHour'
  },
  {
    title: '23时',
    align: "center",
    dataIndex: 'twentythreeHour'
  },
  {
    title: '汇总',
    align: "center",
    dataIndex: 'total'
  }
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "广告列表",
    field: 'dealId',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
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
    label: "渠道游戏包",
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
    label: "渠道名称",
    field: 'channelId',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
  {
    label: "二级渠道",
    field: 'channelSubAccountId',
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
];

//表单数据
export const formSchema: FormSchema[] = [
	// TODO 主键隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
];
