import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { text } from 'stream/consumers';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '游戏',
    align: "center",
    dataIndex: 'gameId_dictText',
    // slots: { customRender: 'game' }
  },
  {
    title: '子游戏',
    align: "center",
    dataIndex: 'subGameId_dictText',
    // slots: { customRender: 'subGame' }
  },
  {
    title: 'apk文件名',
    align: "center",
    dataIndex: 'apkName'
  },
  {
    title: '游戏名',
    align: "center",
    dataIndex: 'gameName'
  },
  {
    title: 'apk包名',
    align: "center",
    dataIndex: 'packageName'
  },
  {
    title: '版本号',
    align: "center",
    dataIndex: 'version'
  },
  {
    title: '构建版本',
    align: "center",
    dataIndex: 'versionCode'
  },
  {
    title: '母包备注',
    align: "center",
    dataIndex: 'parentDesc'
  },
  {
    title: '创建用户',
    align: "center",
    dataIndex: 'creatUser'
  },
  {
    title: '包体更新时间',
    align: "center",
    dataIndex: 'pkgUpdateTime'
  },
  {
    title: '母包操作',
    align: "center",
    dataIndex: 'state',
    width: 320,
    slots: { customRender: 'state', }
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '游戏id',
    field: 'gameId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入游戏id!'},
             ];
    },
  },
  {
    label: '子游戏id',
    field: 'subGameId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入子游戏id!'},
             ];
    },
  },
  {
    label: 'apk文件名',
    field: 'apkName',
    component: 'Input',
  },
  {
    label: '游戏名',
    field: 'gameName',
    component: 'Input',
  },
  {
    label: 'apk包名',
    field: 'packageName',
    component: 'Input',
  },
  {
    label: '版本号',
    field: 'version',
    component: 'Input',
  },
  {
    label: '构建版本',
    field: 'versionCode',
    component: 'Input',
  },
  {
    label: '母包备注',
    field: 'parentDesc',
    component: 'Input',
  },
  {
    label: '创建用户',
    field: 'creatUser',
    component: 'Input',
  },
  {
    label: '包体更新时间',
    field: 'pkgUpdateTime',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss'
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
