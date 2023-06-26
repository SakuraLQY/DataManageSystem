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
    title: '新增付费人数',
    align: 'center',
    dataIndex: 'firstPayUser',
  },
  {
    title: '老用户付费人数',
    align: 'center',
    dataIndex: 'oldPayUser',
  },
  {
    title: '总付费人数',
    align: 'center',
    dataIndex: 'totalPayUser',
  },
  {
    title: '新增付费金额',
    align: 'center',
    dataIndex: 'firstPayMoney',
  },
  {
    title: '老用户付费金额',
    align: 'center',
    dataIndex: 'oldPayMoney',
  },
  {
    title: '总付费金额',
    align: 'center',
    dataIndex: 'totalPayMoney',
  },
  {
    title: '老用户ARPU',
    align: 'center',
    dataIndex: 'oldArpu',
  },
  {
    title: '老用户ARPPU',
    align: 'center',
    dataIndex: 'oldArppu',
  },
  {
    title: 'ARPU',
    align: 'center',
    dataIndex: 'arpu',
  },
  {
    title: 'ARPPU',
    align: 'center',
    dataIndex: 'arppu',
  },
  {
    title: '新增付费率',
    align: 'center',
    dataIndex: 'firstPayRate',
  },
  {
    title: '总付费率',
    align: 'center',
    dataIndex: 'totalRate',
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
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: '结束时间',
    field: 'endTime',
    component: 'Input',
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
    label: '新增付费人数',
    field: 'firstPayUser',
    component: 'Input',
  },
  {
    label: '老用户付费人数',
    field: 'oldPayUser',
    component: 'Input',
  },
  {
    label: '总付费人数',
    field: 'totalPayUser',
    component: 'Input',
  },
  {
    label: '新增付费金额',
    field: 'firstPayMoney',
    component: 'Input',
  },
  {
    label: '老用户付费金额',
    field: 'oldPayMoney',
    component: 'Input',
  },
  {
    label: '总付费金额',
    field: 'totalPayMoney',
    component: 'Input',
  },
  {
    label: '累计付费金额',
    field: 'aliveTotalMoney',
    component: 'Input',
  },
  {
    label: '老用户ARPU',
    field: 'oldArpu',
    component: 'Input',
  },
  {
    label: '老用户ARPPU',
    field: 'oldArppu',
    component: 'Input',
  },
  {
    label: 'ARPU',
    field: 'arpu',
    component: 'Input',
  },
  {
    label: 'ARPPU',
    field: 'arppu',
    component: 'Input',
  },
  {
    label: '新增付费率',
    field: 'firstPayRate',
    component: 'Input',
  },
  {
    label: '总付费率',
    field: 'totalRate',
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
