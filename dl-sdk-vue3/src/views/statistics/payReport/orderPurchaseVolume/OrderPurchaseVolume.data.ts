import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '游戏',
    align: 'center',
    dataIndex: 'gameName',
  },
  {
    title: '媒体名称',
    align: 'center',
    dataIndex: 'channelName',
  },
  {
    title: '广告名称',
    align: 'center',
    dataIndex: 'dealName',
  },
  {
    title: '广告id',
    align: 'center',
    dataIndex: 'dealId',
  },
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
    title: '充值金额',
    align: 'center',
    dataIndex: 'money',
  },
  {
    title: '注册时间',
    align: 'center',
    dataIndex: 'userCreateTime',
  },
  {
    title: '充值方式',
    align: 'center',
    dataIndex: 'payType',
    slots: { customRender: 'payType' },
  },
  {
    title: '充值时间',
    align: 'center',
    dataIndex: 'openTime',
  },
  {
    title: '地域',
    align: 'center',
    dataIndex: 'area',
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
