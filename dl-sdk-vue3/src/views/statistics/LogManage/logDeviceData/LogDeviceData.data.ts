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
    title: '所属渠道',
    align: 'center',
    dataIndex: 'channelName',
  },
  {
    title: '广告ID',
    align: 'center',
    dataIndex: 'dealId',
  },
  {
    title: '广告名',
    align: 'center',
    dataIndex: 'dealName',
  },
  {
    title: '唯一ID',
    align: 'center',
    dataIndex: 'uniqueId',
  },
  {
    title: '设备ID',
    align: 'center',
    dataIndex: 'deviceId',
  },
  {
    title: '序列号',
    align: 'center',
    dataIndex: 'serialId',
  },
  {
    title: 'android_id',
    align: 'center',
    dataIndex: 'android_id',
  },
  {
    title: 'IP',
    align: 'center',
    dataIndex: 'clientIp',
  },
  {
    title: '操作系统',
    align: 'center',
    dataIndex: 'devOs',
  },
  {
    title: '系统版本',
    align: 'center',
    dataIndex: 'devOsVer',
  },
  {
    title: '设备号',
    align: 'center',
    dataIndex: 'devModel',
  },
  {
    title: '安装包名',
    align: 'center',
    dataIndex: 'pkgName',
  },
  {
    title: '安装包版本',
    align: 'center',
    dataIndex: 'pkgVersionCode',
  },
  {
    title: '安装包版本名',
    align: 'center',
    dataIndex: 'pkgVersionName',
  },
  {
    title: 'SDK版本',
    align: 'center',
    dataIndex: 'sdkVersion',
  },
  {
    title: '注册时间',
    align: 'center',
    dataIndex: 'registerTime',
  },
  {
    title: '登录时间',
    align: 'center',
    dataIndex: 'loginTime',
  },
  {
    title: '支付时间',
    align: 'center',
    dataIndex: 'payTime',
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
  {
    label: '更新日期',
    field: 'updateTime',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss',
    },
  },
  {
    label: '所属部门',
    field: 'sysOrgCode',
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
