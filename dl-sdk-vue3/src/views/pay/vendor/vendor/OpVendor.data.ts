import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '厂商名',
    align: 'center',
    dataIndex: 'vendorName',
  },
  {
    title: '支付宝',
    align: 'center',
    dataIndex: 'aliPayVendor_dictText',
    slots: { customRender: 'ali_data' },
  },
  {
    title: '汇付宝',
    align: 'center',
    dataIndex: 'heePayVendor_dictText',
    slots: { customRender: 'hee_data' },
  },
  {
    title: '易宝',
    align: 'center',
    dataIndex: 'yeePayVendor_dictText',
    slots: { customRender: 'yee_data' },
  },
  {
    title: '爱贝',
    align: 'center',
    dataIndex: 'iappPayVendor_dictText',
    slots: { customRender: 'iapp_data' },
  },
  {
    title: '现在支付',
    align: 'center',
    dataIndex: 'ipaynowPayVendor_dictText',
    slots: { customRender: 'ipaynow_data' },
  },
  {
    title: '现在支付app',
    align: 'center',
    dataIndex: 'ipaynowappPayVendor_dictText',
    slots: { customRender: 'ipaynowapp_data' },
  },
  {
    title: '微信支付',
    align: 'center',
    dataIndex: 'wxPayVendor_dictText',
    slots: { customRender: 'wx_data' },
  },
  {
    title: '备注',
    align: 'center',
    dataIndex: 'note',
  },
  {
    title: '创建人',
    align: 'center',
    dataIndex: 'createBy_dictText',
  },
  {
    title: '创建日期',
    align: 'center',
    dataIndex: 'createTime',
    customRender: ({ text }) => {
      return !text ? '' : text.length > 10 ? text.substr(0, 10) : text;
    },
  },
  {
    title: '更新人',
    align: 'center',
    dataIndex: 'updateBy_dictText',
  },
  {
    title: '更新日期',
    align: 'center',
    dataIndex: 'updateTime',
    customRender: ({ text }) => {
      return !text ? '' : text.length > 10 ? text.substr(0, 10) : text;
    },
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: '厂商名',
    field: 'vendorName',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: '支付宝支付渠道ID',
    field: 'aliPayVendor',
    component: 'JSearchSelect',
    componentProps: {
      dict: 'open_gateway.op_pay_vendor where pay_type = 1,pay_vendor_name,id',
    },
    colProps: { span: 6 },
  },
  {
    label: '汇付宝支付渠道ID',
    field: 'heePayVendor',
    component: 'JSearchSelect',
    componentProps: {
      dict: 'open_gateway.op_pay_vendor where pay_type = 2,pay_vendor_name,id',
    },
    colProps: { span: 6 },
  },
  {
    label: '易宝支付渠道ID',
    field: 'yeePayVendor',
    component: 'JSearchSelect',
    componentProps: {
      dict: 'open_gateway.op_pay_vendor where pay_type = 3,pay_vendor_name,id',
    },
    colProps: { span: 6 },
  },
  {
    label: '爱贝支付渠道ID',
    field: 'iappPayVendor',
    component: 'JSearchSelect',
    componentProps: {
      dict: 'open_gateway.op_pay_vendor where pay_type = 4,pay_vendor_name,id',
    },
    colProps: { span: 6 },
  },
  {
    label: '现在支付支付渠道ID',
    field: 'ipaynowPayVendor',
    component: 'JSearchSelect',
    componentProps: {
      dict: 'open_gateway.op_pay_vendor where pay_type = 5,pay_vendor_name,id',
    },
    colProps: { span: 6 },
  },
  {
    label: '现在支付app支付渠道ID',
    field: 'ipaynowappPayVendor',
    component: 'JSearchSelect',
    componentProps: {
      dict: 'open_gateway.op_pay_vendor where pay_type = 6,pay_vendor_name,id',
    },
    colProps: { span: 6 },
  },
  {
    label: '微信支付支付渠道ID',
    field: 'wxPayVendor',
    component: 'JSearchSelect',
    componentProps: {
      dict: 'open_gateway.op_pay_vendor where pay_type = 7,pay_vendor_name,id',
    },
    colProps: { span: 6 },
  },
  {
    label: '创建人',
    field: 'createBy',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: '创建日期',
    field: 'createTime',
    component: 'DatePicker',
    colProps: { span: 6 },
  },
  {
    label: '更新人',
    field: 'updateBy',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: '更新日期',
    field: 'updateTime',
    component: 'DatePicker',
    colProps: { span: 6 },
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '厂商名',
    field: 'vendorName',
    component: 'Input',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入厂商名!' }];
    },
  },
  {
    label: '支付宝支付渠道ID',
    field: 'aliPayVendor',
    component: 'JSearchSelect',
    componentProps: {
      dict: 'open_gateway.op_pay_vendor where pay_type = 1,pay_vendor_name,id',
    },
  },
  {
    label: '汇付宝支付渠道ID',
    field: 'heePayVendor',
    component: 'JSearchSelect',
    componentProps: {
      dict: 'open_gateway.op_pay_vendor where pay_type = 2,pay_vendor_name,id',
    },
  },
  {
    label: '易宝支付渠道ID',
    field: 'yeePayVendor',
    component: 'JSearchSelect',
    componentProps: {
      dict: 'open_gateway.op_pay_vendor where pay_type = 3,pay_vendor_name,id',
    },
  },
  {
    label: '爱贝支付渠道ID',
    field: 'iappPayVendor',
    component: 'JSearchSelect',
    componentProps: {
      dict: 'open_gateway.op_pay_vendor where pay_type = 4,pay_vendor_name,id',
    },
  },
  {
    label: '现在支付支付渠道ID',
    field: 'ipaynowPayVendor',
    component: 'JSearchSelect',
    componentProps: {
      dict: 'open_gateway.op_pay_vendor where pay_type = 5,pay_vendor_name,id',
    },
  },
  {
    label: '现在支付app支付渠道ID',
    field: 'ipaynowappPayVendor',
    component: 'JSearchSelect',
    componentProps: {
      dict: 'open_gateway.op_pay_vendor where pay_type = 6,pay_vendor_name,id',
    },
  },
  {
    label: '微信支付支付渠道ID',
    field: 'wxPayVendor',
    component: 'JSearchSelect',
    componentProps: {
      dict: 'open_gateway.op_pay_vendor where pay_type = 7,pay_vendor_name,id',
    },
  },
  {
    label: '备注',
    field: 'note',
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
