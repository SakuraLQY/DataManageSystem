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
    title: '游戏名',
    align: "center",
    dataIndex: 'gameName'
  },
  {
    title: '渠道名',
    align: "center",
    dataIndex: 'channelName'
  },
  {
    title: '广告名',
    align: "center",
    dataIndex: 'dealName'
  },
  {
    title: '注册数',
    align: "center",
    dataIndex: 'regCount'
  },
  {
    title: '推广费用',
    align: "center",
    dataIndex: 'costPay'
  },
  {
    title: '新增付费用户',
    align: "center",
    dataIndex: 'firstPayUser'
  }
  ,
  {
    title: '新增付费次留',
    align: "center",
    dataIndex: 'firstPayLoyal'
  },
  {
    title: '新增付费3留',
    align: "center",
    dataIndex: 'firstPayLoyal3'
  },
  {
    title: '新增付费7留',
    align: "center",
    dataIndex: 'firstPayLoyal7'
  },
  {
    title: '新增付费15留',
    align: "center",
    dataIndex: 'firstPayLoyal15'
  },
  {
    title: '新增付费30留',
    align: "center",
    dataIndex: 'firstPayLoyal30'
  },
  {
    title: '新增付费45留',
    align: "center",
    dataIndex: 'firstPayLoyal45'
  },
  {
    title: '新增付费60留',
    align: "center",
    dataIndex: 'firstPayLoyal60'
  },
  {
    title: '新增付费90留',
    align: "center",
    dataIndex: 'firstPayLoyal90'
  },
  {
    title: '新增付费次留单价',
    align: "center",
    dataIndex: 'firstPayPrice'
  },
  {
    title: '新增付费次留3单价',
    align: "center",
    dataIndex: 'firstPayPrice3'
  }
  ,
  {
    title: '新增付费次留7单价',
    align: "center",
    dataIndex: 'firstPayPrice7'
  }
  ,
  {
    title: '新增付费次留15单价',
    align: "center",
    dataIndex: 'firstPayPrice15'
  }
  ,
  {
    title: '新增付费次留30单价',
    align: "center",
    dataIndex: 'firstPayPrice30'
  }
  ,
  {
    title: '新增付费次留45单价',
    align: "center",
    dataIndex: 'firstPayPrice45'
  }
  ,
  {
    title: '新增付费次留60单价',
    align: "center",
    dataIndex: 'firstPayPrice60'
  }
  ,
  {
    title: '新增付费次留90单价',
    align: "center",
    dataIndex: 'firstPayPrice90'
  }
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "游戏名",
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
    label: "游戏包名",
    field: 'pkgId',
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
    label: "渠道名称",
    field: 'channelId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "二级渠道",
    field: 'channelSubAccountId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "广告列表",
    field: 'dealId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "留存类型",
    field: 'retainType',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "充值区间",
    field: 'begSection',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "区间结束",
    field: 'endSection',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "起始时间",
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
    label: "充值限期",
    field: 'costTime',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "账号人员",
    field: 'createUser',
    component: 'Input',
    colProps: {span: 6},
  },
];
//留存类型
export const retainOption = [
  {
    label : '留存',
    value : 'loyal'
  },
  
  {
    label : '新增付费留存',
    value : 'newLoyal'
  },
]




//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '游戏名',
    field: 'gameId',
    component: 'Input',
  },
  {
    label: '游戏名称',
    field: 'subGameId',
    component: 'Input',
  },
  {
    label: '游戏包名',
    field: 'pkgId',
    component: 'Input',
  },
  {
    label: '渠道类型',
    field: 'channelTypeId',
    component: 'Input',
  },
  {
    label: '渠道名称',
    field: 'channelId',
    component: 'Input',
  },
  {
    label: '二级渠道',
    field: 'subChannelAccountId',
    component: 'Input',
  },
  {
    label: '广告列表',
    field: 'dealId',
    component: 'Input',
  },
  {
    label: '留存类型',
    field: 'retainType',
    component: 'Input',
  },
  {
    label: '充值区间',
    field: 'begSection',
    component: 'Input',
  },
  {
    label: '区间结束',
    field: 'endSection',
    component: 'Input',
  },
  {
    label: '起始时间',
    field: 'startTime',
    component: 'Input',
  },
  {
    label: '结束时间',
    field: 'endTime',
    component: 'Input',
  },
  {
    label: '充值限期',
    field: 'costTime',
    component: 'Input',
  },
  {
    label: '账号人员',
    field: 'createUser',
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
