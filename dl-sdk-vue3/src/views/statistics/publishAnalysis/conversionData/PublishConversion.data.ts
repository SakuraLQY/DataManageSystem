import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: 'ID',
    align: "center",
    dataIndex: 'typeId'
  },
  {
    title: '名称',
    align: "center",
    dataIndex: 'name'
  },
  {
    title: '激活',
    align: "center",
    dataIndex: 'active'
  },
  {
    title: '激活设备数',
    align: "center",
    dataIndex: 'activeDev'
  },
  {
    title: '注册数',
    align: "center",
    dataIndex: 'regCount'
  },
  {
    title: '注册设备数',
    align: "center",
    dataIndex: 'regCountDev'
  },
  {
    title: '激活注册率',
    align: "center",
    dataIndex: 'activeRegRate'
  },
  {
    title: '新增付费人数',
    align: "center",
    dataIndex: 'firstPayUser'
  },
  {
    title: '有效注册数',
    align: "center",
    dataIndex: 'validUser'
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
    label: "归类方式",
    field: 'type',
    component: 'Input',
    colProps: {span: 6},
  },
];
//自定义下拉选项
export const typeOption = [
  {
    label: '全部',
    value: ' '
  },
  {
    label: '按广告',
    value: 'deal_id'
  },{
    label: '按渠道',
    value:  'channel_id'
  },{
    label: '按游戏',
    value:  'sub_game_id'
  },{
    label: '按游戏包',
    value:  'game_id'
  }
]

//表单数据
export const formSchema: FormSchema[] = [
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
    label: '归类方式',
    field: 'type',
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
