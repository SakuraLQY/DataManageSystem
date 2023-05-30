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
    slots: { customRender: 'subGameId_dictText' },
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
    dataIndex: 'type1_dictText'
  },
  {
    title: '素材归类',
    align: "center",
    dataIndex: 'type2',
    slots: { customRender: 'type2' },
  },
  {
    title: '头条素材账号',
    align: "center",
    dataIndex: 'jrttCreateAccountId',
    slots: { customRender: 'jrttCreateAccountId' },
  },
  {
    title: '头条素材ID',
    align: "center",
    dataIndex: 'jrttMaterialId',
    slots: { customRender: 'jrttMaterialId' },
  },
  {
    title: '头条文件ID',
    align: "center",
    dataIndex: 'jrttFileId',
    slots: { customRender: 'jrttFileId' },
  },
  {
    title: '广点通素材账号',
    align: "center",
    dataIndex: 'gdtCreateAccountId',
    slots: { customRender: 'gdtCreateAccountId' },
  },
  {
    title: '广点通素材ID',
    align: "center",
    dataIndex: 'gdtMaterialId',
    slots: { customRender: 'gdtMaterialId' },
  },
  {
    title: '广点通文件ID',
    align: "center",
    dataIndex: 'gdtFileId',
    slots: { customRender: 'gdtFileId' },
  },
  {
    title: '快手素材账号',
    align: "center",
    dataIndex: 'kuaishouCreateAccountId',
    slots: { customRender: 'kuaishouCreateAccountId' },
  },
  {
    title: '快手素材ID',
    align: "center",
    dataIndex: 'kuaishouMaterialId',
    slots: { customRender: 'kuaishouMaterialId' },
  },
  {
    title: '快手文件ID',
    align: "center",
    dataIndex: 'kuaishouFileId',
    slots: { customRender: 'kuaishouFileId' },
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
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "素材类型",
    field: 'type1',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "material_manager_type"
    },
    colProps: {span: 6},
  },
  {
    label: "素材归类",
    field: 'type2',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "material_img_type"
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
    component: 'InputNumber',
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
    field: 'type1',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "material_manager_type"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入素材类型!'},
             ];
    },
  },
  {
    label: '素材归类',
    field: 'type2',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "material_img_type"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入素材归类!'},
             ];
    },
  },
  {
    label: '头条素材账号',
    field: 'jrttCreateAccountId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "select_upload"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入头条素材账号!'},
             ];
    },
  },
  {
    label: '头条素材ID',
    field: 'jrttMaterialId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入头条素材ID!'},
             ];
    },
  },
  {
    label: '广点通素材账号',
    field: 'gdtCreateAccountId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "select_upload"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入广点通素材账号!'},
             ];
    },
  },
  {
    label: '广点通素材ID',
    field: 'gdtMaterialId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入广点通素材ID!'},
             ];
    },
  },
  {
    label: '快手素材账号',
    field: 'kuaishouCreateAccountId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "select_upload"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入快手素材账号!'},
             ];
    },
  },
  {
    label: '快手素材ID',
    field: 'kuaishouMaterialId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入快手素材ID!'},
             ];
    },
  },
  {
    label: '素材备注',
    field: 'materialDesc',
    component: 'InputTextArea',
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
