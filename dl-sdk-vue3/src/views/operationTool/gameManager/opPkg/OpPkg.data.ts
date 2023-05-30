import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '渠道游戏包id',
    align: "center",
    dataIndex: 'id'
  },
  {
    title: '游戏',
    align: "center",
    dataIndex: 'gameId_dictText'
  },
  {
    title: '子游戏',
    align: "center",
    dataIndex: 'subGameId_dictText'
  },
  {
    title: '渠道游戏名',
    align: "center",
    dataIndex: 'pkgName'
  },
  {
    title: '应用名',
    align: "center",
    dataIndex: 'nickName'
  },
  {
    title: '渠道',
    align: "center",
    dataIndex: 'channelId_dictText'
  },
  {
    title: 'icon图打包素材路径',
    align: "center",
    dataIndex: 'iconMaterialPackPath',
    slots: { customRender: 'iconMaterialPathContent' },
  },
  {
    title: '闪屏图打包素材路径',
    align: "center",
    dataIndex: 'screenMaterialPackPath',
    slots: { customRender: 'screenMaterialPackPathContent' },
  },
  {
    title: '加载图打包素材路径',
    align: "center",
    dataIndex: 'loadingMaterialPackPath',
    slots: { customRender: 'loadingMaterialPackPathContent' },
  },
  {
    title: '登录图打包素材路径',
    align: "center",
    dataIndex: 'loginMaterialPackPath',
    slots: { customRender: 'loginMaterialPackPathContent' },
  },
  {
    title: 'logo图打包素材路径',
    align: "center",
    dataIndex: 'logoMaterialPackPath',
    slots: { customRender: 'logoMaterialPackPathContent' },
  },
  {
    title: '资源',
    align: "center",
    dataIndex: 'apkPath',
    slots: { customRender: 'apkPathContent' },
  },
  {
    title: '打包状态',
    align: "center",
    dataIndex: 'packState_dictText'
  },
  {
    title: '备注',
    align: "center",
    dataIndex: 'descs'
  },
  {
    title: '创建人',
    align: "center",
    dataIndex: 'createBy'
  },
  {
    title: '创建日期',
    align: "center",
    dataIndex: 'createTime',
    customRender:({text}) =>{
      return !text?"":(text.length>10?text.substr(0,10):text);
    },
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "游戏",
    field: 'gameId',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
  {
    label: "子游戏",
    field: 'subGameId',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
  {
    label: "渠道id",
    field: 'channelId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "open_gateway.op_channel,channel_name,id"
    },
    colProps: {span: 6},
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '游戏',
    field: 'gameId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入游戏!'},
             ];
    },
  },
  {
    label: '子游戏',
    field: 'subGameId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入子游戏!'},
             ];
    },
  },
  {
    label: '应用名',
    field: 'nickName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入应用名!'},
             ];
    },
  },
  {
    label: '游戏版本',
    field: 'versionName',
    component: 'Input',
  },
  {
    label: '游戏构建版本',
    field: 'versionCode',
    component: 'Input',
  },
  {
    label: '下载链接',
    field: 'downloadUrl',
    component: 'Input',
  },
  {
    label: '备注',
    field: 'descs',
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
