import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '游戏',
    align: 'center',
    dataIndex: 'pkgName',
  },
  {
    title: '广告ID',
    align: 'center',
    dataIndex: 'dealId',
  },
  {
    title: '设备类型',
    align: 'center',
    dataIndex: 'uniqueType',
  },
  {
    title: '设备ID',
    align: 'center',
    dataIndex: 'uniqueId',
  },
  {
    title: '渠道名',
    align: 'center',
    dataIndex: 'channelName',
  },
  {
    title: '创建时间',
    align: 'center',
    dataIndex: 'createTime',
  },
  {
    title: '更新时间',
    align: 'center',
    dataIndex: 'updateTime',
  },
  {
    title: '回调参数',
    align: 'center',
    dataIndex: 'visitData',
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: '创建日期',
    field: 'createTime',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
    },
    colProps: { span: 6 },
  },
  {
    label: '更新人',
    field: 'updateBy',
    component: 'Input',
    colProps: { span: 6 },
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '创建日期',
    field: 'createTime',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss',
    },
  },
  {
    label: '更新人',
    field: 'updateBy',
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

export const uniqueType = [
  {
    label: 'androidId',
    value: 'androidId',
  },
  {
    label: 'idfa',
    value: 'idfa',
  },
  {
    label: 'imei',
    value: 'imei',
  },
  {
    label: 'ip',
    value: 'ip',
  },
  {
    label: 'oaid',
    value: 'oaid',
  },
];

export const tableName = [
  {
    label: '01表',
    value: 0,
  },
  {
    label: '02表',
    value: 1,
  },
  {
    label: '03表',
    value: 2,
  },
  {
    label: '04表',
    value: 3,
  },
  {
    label: '05表',
    value: 4,
  },
  {
    label: '06表',
    value: 5,
  },
  {
    label: '07表',
    value: 6,
  },
  {
    label: '08表',
    value: 7,
  },
  {
    label: '09表',
    value: 8,
  },
  {
    label: '10表',
    value: 9,
  },
  {
    label: '11表',
    value: 10,
  },
  {
    label: '12表',
    value: 11,
  },
  {
    label: '13表',
    value: 12,
  },
  {
    label: '14表',
    value: 13,
  },
  {
    label: '15表',
    value: 14,
  },
  {
    label: '16表',
    value: 15,
  },
  {
    label: '17表',
    value: 16,
  },
  {
    label: '18表',
    value: 17,
  },
  {
    label: '19表',
    value: 18,
  },
  {
    label: '20表',
    value: 19,
  },
  {
    label: '21表',
    value: 20,
  },
  {
    label: '22表',
    value: 21,
  },
  {
    label: '23表',
    value: 22,
  },
  {
    label: '24表',
    value: 23,
  },
  {
    label: '25表',
    value: 24,
  },
  {
    label: '26表',
    value: 25,
  },
  {
    label: '27表',
    value: 26,
  },
  {
    label: '28表',
    value: 27,
  },
  {
    label: '29表',
    value: 28,
  },
  {
    label: '30表',
    value: 29,
  },
  {
    label: '31表',
    value: 30,
  },
  {
    label: '32表',
    value: 31,
  },
];
