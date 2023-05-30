import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '用户名',
    align: 'center',
    dataIndex: 'userName',
  },
  {
    title: '用户id',
    align: 'center',
    dataIndex: 'userId',
  },
  {
    title: '游戏id',
    align: 'center',
    dataIndex: 'gameId',
  },
  {
    title: '广告id',
    align: 'center',
    dataIndex: 'dealId',
  },
  {
    title: '用户IP',
    align: 'center',
    dataIndex: 'clientIp',
  },
  {
    title: '订单id',
    align: 'center',
    dataIndex: 'orderId',
  },
  {
    title: '充值时间',
    align: 'center',
    dataIndex: 'openTime',
  },
  {
    title: '充值金额',
    align: 'center',
    dataIndex: 'money',
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
