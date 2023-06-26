import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: 'ID',
    align: "center",
    dataIndex: 'id'
  },
  {
    title: '支付类型',
    align: 'center',
    dataIndex: 'payType',
    slots: { customRender: 'payType_data' }
  },
  {
    title: '支付渠道名称',
    align: 'center',
    dataIndex: 'payVendorName',
  },
  {
    title: '支付渠道配置',
    align: 'center',
    dataIndex: 'payVendorConf',
    slots: { customRender: 'conf' },
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
      return !text ? '' : text;
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
      return !text ? '' : text;
    },
  },
  {
    title: '备注',
    align: 'center',
    dataIndex: 'note',
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: '支付类型',
    field: 'payType',
    component: 'JDictSelectTag',
    colProps: { span: 6 },
  },
  {
    label: '支付渠道名称',
    field: 'payVendorName',
    component: 'Input',
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
    label: '支付类型',
    field: 'payType',
    component: 'JDictSelectTag',
    componentProps: {
      showChooseOption: false,
    },
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入支付类型!' }];
    },
  },
  {
    label: '支付渠道名称',
    field: 'payVendorName',
    component: 'Input',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入支付渠道名称!' }];
    },
  },
  {
    label: '应用ID',
    field: 'app_id',
    component: 'Input',
  },
  {
    label: '合作者ID',
    field: 'seller_id',
    component: 'Input',
  },
  {
    label: '合作者账号',
    field: 'seller_email',
    component: 'Input',
  },
  {
    label: '合作者公钥',
    field: 'seller_pubkey',
    component: 'Input',
  },
  {
    label: '合作者私钥',
    field: 'seller_prikey',
    component: 'Input',
  },
  {
    label: '合作伙伴支付宝公钥',
    field: 'alipay_pubkey',
    component: 'Input',
  },
  {
    label: '开放平台支付宝公钥',
    field: 'alipay_pubkey2',
    component: 'Input',
  },
  {
    label: '商户密钥',
    field: 'seller_key',
    component: 'Input',
  },
  {
    label: '易宝RSA公钥',
    field: 'yeepay_pubkey',
    component: 'Input',
  },
  {
    label: '爱贝公钥',
    field: 'iapppay_pubkey',
    component: 'Input',
  },
  {
    label: '商户密钥',
    field: 'app_key',
    component: 'Input',
  },
  {
    label: '回调url',
    field: 'notify_url',
    component: 'Input',
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

export const options = [
  {
    label: '支付宝',
    value: 1
  },
  {
    label: '汇付宝',
    value: 2
  },
  {
    label: '易宝',
    value: 3
  },
  {
    label: '爱贝',
    value: 4
  },
  {
    label: '现在支付',
    value: 5
  },
  {
    label: '现在支付app',
    value: 6
  },
  {
    label: '微信',
    value: 7
  },
]