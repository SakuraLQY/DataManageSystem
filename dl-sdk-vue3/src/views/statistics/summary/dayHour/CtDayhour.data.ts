import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '日期',
    align: "center",
    dataIndex: 'date',
    customRender:({text}) =>{
      return !text?"":(text.length>10?text.substr(0,10):text);
    },
  },
  {
    title: '汇总',
    align: "center",
    dataIndex: 'total'
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
    componentProps: {
      showTime: true,
    },
    colProps: {span: 6},
  },
  {
    label: "结束日期",
    field: 'endTime',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
    },
    colProps: {span: 6},
  },
  {
    label: "对比维度",
    field: 'level',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
];

export const compareLevelOption = [

  {
    label : '激活数',
    value : 'count_active'
  },
  
  {
    label : '注册数',
    value : 'count_user'
  },
  
  {
    label : '付费总数',
    value : 'total_money'
  },
  
  {
    label : '新用户付费总数',
    value : 'first_payuser'
  },
  
  {
    label : 'DAU',
    value : 'count_dau'
  },
  
  {
    label : '次日留存',
    value : 'loyal2'
  },
  
  {
    label : '3日留存',
    value : 'loyal3'
  },
  
  {
    label : '4日留存',
    value : 'loyal4'
  },
  {
    label : '5日留存',
    value : 'loyal5'
  },
  
  {
    label : '6日留存',
    value : 'loyal6'
  },
  {
    label : '7日留存',
    value : 'loyal7'
  },{
    label : '15日留存',
    value : 'loyal15'
  },
  {
    label : '30日留存',
    value : 'loyal30'
  },
  
  {
    label : '45日留存',
    value : 'loyal45'
  },
  
  {
    label : '60日留存',
    value : 'loyal60'
  }
]

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
