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
    title: '广告',
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
  },  {
    title: '全部激活数',
    align: "center",
    dataIndex: 'countAllActive'
  },  {
    title: '注册数',
    align: "center",
    dataIndex: 'regCount'
  }, 
  //  {
  //   title: '全部注册数',
  //   align: "center",
  //   dataIndex: 'regAllCount'
  // },  
  {
    title: '有效注册数',
    align: "center",
    dataIndex: 'validReg'
  },  
  // {
  //   title: '全部有效注册数',
  //   align: "center",
  //   dataIndex: 'AllValidReg'
  // },  
  {
    title: '新增付费数',
    align: "center",
    dataIndex: 'firstPayUser'
  },  
  // {
  //   title: '全部新增付费人数',
  //   align: "center",
  //   dataIndex: 'AllFirstPayUser'
  // },
  {
    title: '新增付费金额',
    align: "center",
    dataIndex: 'firstPayMoney'
  },
  // {
  //   title: '全部新增付费金额',
  //   align: "center",
  //   dataIndex: 'AllFirstPayMoney'
  // },
  {
    title: '新增付费率',
    align: "center",
    dataIndex: 'firstPayRate'
  },
  // {
  //   title: '全部新增付费率',
  //   align: "center",
  //   dataIndex: 'AllFirstPayRate'
  // },
  {
    title: '活跃人数',
    align: "center",
    dataIndex: 'countDau'
  },
  // {
  //   title: '全部活跃人数',
  //   align: "center",
  //   dataIndex: 'AllCountDau'
  // },
  {
    title: '活跃付费人数',
    align: "center",
    dataIndex: 'alivePayUser'
  },
  // {
  //   title: '全部活跃付费人数',
  //   align: "center",
  //   dataIndex: 'AllAlivePayUser'
  // },
  {
    title: '付费总额',
    align: "center",
    dataIndex: 'totalMoney'
  },
  // {
  //   title: '全部付费总额',
  //   align: "center",
  //   dataIndex: 'AllTotalMoney'
  // },
  {
    title: '活跃付费率',
    align: "center",
    dataIndex: 'alivePayRate'
  },
  // {
  //   title: '全部活跃付费率',
  //   align: "center",
  //   dataIndex: 'AllAlivePayRate'
  // },
  {
    title: '首日ARPU',
    align: "center",
    dataIndex: 'firstArpu'
  },
  // {
  //   title: '全部首日ARPU',
  //   align: "center",
  //   dataIndex: 'allFirstArpu'
  // },
  {
    title: '总ARPU',
    align: "center",
    dataIndex: 'totalArpu'
  },
  // {
  //   title: '全部总ARPU',
  //   align: "center",
  //   dataIndex: 'allTotalArpu'
  // },
  {
    title: '次日留存',
    align: "center",
    dataIndex: 'loyal2'
  },
  {
    title: '3日留存',
    align: "center",
    dataIndex: 'loyal3'
  },
  {
    title: '7日留存',
    align: "center",
    dataIndex: 'loyal7'
  },
  {
    title: '15日留存',
    align: "center",
    dataIndex: 'loyal15'
  },
  {
    title: '30日留存',
    align: "center",
    dataIndex: 'loyal30'
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
    label: "广告类型",
    field: 'dealId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "起始日期",
    field: 'startTime',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "结束日期",
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

  {
    label: "渠道子账号",
    field: 'channelSubAccountId',
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
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '广告类型',
    field: 'dealId',
    component: 'Input',
  },
  {
    label: '起始日期',
    field: 'startTime',
    component: 'Input',
  },
  {
    label: '结束日期',
    field: 'endTime',
    component: 'Input',
  },
  {
    label: '归类方式',
    field: 'type',
    component: 'Input',
  },
  {
    label: '游戏',
    field: 'gameId',
    component: 'Input',
  },
  {
    label: '子游戏',
    field: 'subGameId',
    component: 'Input',
  },
  {
    label: '渠道子账号',
    field: 'channelSubAccountId',
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
    label: '渠道游戏包',
    field: 'pkgId',
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
