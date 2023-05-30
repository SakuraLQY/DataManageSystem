import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '应用名',
    align: "center",
    dataIndex: 'appName'
  },
  {
    title: '应用appid',
    align: "center",
    dataIndex: 'appId'
  },
  {
    title: '应用secret',
    align: "center",
    dataIndex: 'appSecret'
  },
  {
    title: '应用备注',
    align: "center",
    dataIndex: 'appDesc'
  },
  {
    title: '回调地址',
    align: "center",
    dataIndex: 'redirectUri'
  },
  {
    title: '授权地址',
    align: "center",
    dataIndex: 'authUrl'
  },
  {
    title: '创建用户',
    align: "center",
    dataIndex: 'createUser'
  },
  {
    title: '创建时间',
    align: "center",
    dataIndex: 'createTime'
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '应用名',
    field: 'appName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入应用名!'},
             ];
    },
  },
  {
    label: '应用appid',
    field: 'appId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入应用appid!'},
             ];
    },
  },
  {
    label: '应用secret',
    field: 'appSecret',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入应用secret!'},
             ];
    },
  },
  {
    label: '应用备注',
    field: 'appDesc',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入应用备注!'},
             ];
    },
  },
  {
    label: '回调地址',
    field: 'redirectUri',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入回调地址!'},
             ];
    },
  },
  {
    label: '授权地址',
    field: 'authUrl',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入授权地址!'},
             ];
    },
  },
	// TODO 主键隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
];
