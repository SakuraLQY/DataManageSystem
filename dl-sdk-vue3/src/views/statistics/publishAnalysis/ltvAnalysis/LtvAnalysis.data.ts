import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: 'ID',
    align: 'center',
    dataIndex: 'typeId',
  },
  {
    title: '名称',
    align: 'center',
    dataIndex: 'name',
  },
  {
    title: 'LTV1',
    align: 'center',
    dataIndex: 'ltv1',
  },
  {
    title: 'LTV2',
    align: 'center',
    dataIndex: 'ltv2',
  },
  {
    title: 'LTV3',
    align: 'center',
    dataIndex: 'ltv3',
  },
  {
    title: 'LTV4',
    align: 'center',
    dataIndex: 'ltv4',
  },
  {
    title: 'LTV5',
    align: 'center',
    dataIndex: 'ltv5',
  },
  {
    title: 'LTV6',
    align: 'center',
    dataIndex: 'ltv6',
  },
  {
    title: 'LTV7',
    align: 'center',
    dataIndex: 'ltv7',
  },
  {
    title: 'LTV15',
    align: 'center',
    dataIndex: 'ltv15',
  },
  {
    title: 'LTV30',
    align: 'center',
    dataIndex: 'ltv30',
  },
  {
    title: 'LTV45',
    align: 'center',
    dataIndex: 'ltv45',
  },
  {
    title: 'LTV60',
    align: 'center',
    dataIndex: 'ltv60',
  },
  {
    title: 'LTV90',
    align: 'center',
    dataIndex: 'ltv90',
  },
  {
    title: 'LTV120',
    align: 'center',
    dataIndex: 'ltv120',
  },
  {
    title: 'LTV150',
    align: 'center',
    dataIndex: 'ltv150',
  },
];

//自定义选项
export const typeOption = [
  {
    label: '全部',
    value: ' ',
  },
  {
    label: '按广告',
    value: 'deal_id',
  },
  {
    label: '按渠道',
    value: 'channel_id',
  },
  {
    label: '按游戏',
    value: 'game_id',
  },
  {
    label: '按游戏包',
    value: 'sub_game_id',
  },
  {
    label: '按渠道游戏包',
    value: 'pkg_id',
  },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: '游戏项目',
    field: 'gameId',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: '游戏名称',
    field: 'subGameId',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: '子游戏包',
    field: 'pkgId',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: '开始时间',
    field: 'startTime',
    component: 'DatePicker',
    colProps: { span: 6 },
  },
  {
    label: '结束时间',
    field: 'endTime',
    component: 'DatePicker',
    colProps: { span: 6 },
  },
  {
    label: '归类方式',
    field: 'type',
    component: 'Input',
    colProps: { span: 6 },
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '游戏',
    field: 'gameId',
    component: 'Input',
  },
  {
    label: '开始时间',
    field: 'startTime',
    component: 'DatePicker',
  },
  {
    label: '结束时间',
    field: 'endTime',
    component: 'DatePicker',
  },
  {
    label: '归类方式',
    field: 'type',
    component: 'Input',
  },
  {
    label: 'ID',
    field: 'typeId',
    component: 'Input',
  },
  {
    label: '名称',
    field: 'name',
    component: 'Input',
  },
  {
    label: 'LTV1',
    field: 'ltv1',
    component: 'Input',
  },
  {
    label: 'LTV2',
    field: 'ltv2',
    component: 'Input',
  },
  {
    label: 'LTV3',
    field: 'ltv3',
    component: 'Input',
  },
  {
    label: 'LTV4',
    field: 'ltv4',
    component: 'Input',
  },
  {
    label: 'LTV5',
    field: 'ltv5',
    component: 'Input',
  },
  {
    label: 'LTV6',
    field: 'ltv6',
    component: 'Input',
  },
  {
    label: 'LTV7',
    field: 'ltv7',
    component: 'Input',
  },
  {
    label: 'LTV15',
    field: 'ltv15',
    component: 'Input',
  },
  {
    label: 'LTV30',
    field: 'ltv30',
    component: 'Input',
  },
  {
    label: 'LTV45',
    field: 'ltv45',
    component: 'Input',
  },
  {
    label: 'LTV60',
    field: 'ltv60',
    component: 'Input',
  },
  {
    label: 'LTV90',
    field: 'ltv90',
    component: 'Input',
  },
  {
    label: 'LTV120',
    field: 'ltv120',
    component: 'Input',
  },
  {
    label: 'LTV150',
    field: 'ltv150',
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
