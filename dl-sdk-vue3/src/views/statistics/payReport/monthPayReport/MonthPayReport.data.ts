import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '日期',
    align: 'center',
    dataIndex: 'createTime',
  },
  {
    title: '充值方式',
    align: 'center',
    dataIndex: 'payType',
    slots: { customRender: 'pay_type' },
  },
  {
    title: '游戏',
    align: 'center',
    dataIndex: 'gameName',
  },
  {
    title: '充值总额',
    align: 'center',
    dataIndex: 'money',
  },
  {
    title: '充值用户数',
    align: 'center',
    dataIndex: 'countUser',
  },
  {
    title: '充值次数',
    align: 'center',
    dataIndex: 'countNum',
  },
  {
    title: 'ARPU值',
    align: 'center',
    dataIndex: 'arpuNum',
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
