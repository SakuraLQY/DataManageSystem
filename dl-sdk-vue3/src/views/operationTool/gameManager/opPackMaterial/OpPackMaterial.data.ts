import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '游戏',
    align: "center",
    dataIndex: 'gameId_dictText'
  },
  {
    title: '子游戏',
    align: "center",
    dataIndex: 'subGameId_dictText',
    slots: { customRender: 'subGameId_dictText' }
  },
  {
    title: '素材名',
    align: "center",
    dataIndex: 'materialName'
  },
  {
    title: '素材内容',
    align: "center",
    dataIndex: 'materialContent',
    slots: { customRender: 'materialContent' },
  },
  {
    title: '素材类型',
    align: "center",
    dataIndex: 'type_dictText'
  },
  {
    title: '素材格式',
    align: "center",
    dataIndex: 'format'
  },
  {
    title: '规格',
    align: "center",
    dataIndex: 'specs'
  },
  {
    title: '素材备注',
    align: "center",
    dataIndex: 'materialDesc'
  },
  {
    title: '创建用户',
    align: "center",
    dataIndex: 'createBy'
  },
  {
    title: '创建时间',
    align: "center",
    dataIndex: 'createTime',
    customRender:({text}) =>{
      return !text?"":text;
    },
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "子游戏ID",
    field: 'subGameId',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
  {
    label: "素材类型",
    field: 'type',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "material_type"
    },
    colProps: {span: 6},
  },
  {
    label: "创建用户",
    field: 'createBy',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "创建时间",
    field: 'createTime',
    component: 'DatePicker',
    colProps: {span: 6},
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '子游戏ID',
    field: 'subGameId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入子游戏ID!'},
             ];
    },
  },
  {
    label: '素材名',
    field: 'materialName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入素材名!'},
             ];
    },
  },
  {
    label: '素材类型',
    field: 'type',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "material_type"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入素材类型!'},
             ];
    },
  },
  {
    label: '素材格式',
    field: 'format',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入素材格式!'},
             ];
    },
  },
  {
    label: '素材大小',
    field: 'size',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入素材大小!'},
             ];
    },
  },
  {
    label: '规格',
    field: 'specs',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入规格!'},
             ];
    },
  },
  {
    label: '文件目录',
    field: 'path',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入文件目录!'},
             ];
    },
  },
  {
    label: '素材链接',
    field: 'showUrl',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入素材链接!'},
             ];
    },
  },
  {
    label: '素材md5',
    field: 'fileMd5',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入素材md5!'},
             ];
    },
  },
  {
    label: '素材备注',
    field: 'materialDesc',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入素材备注!'},
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
