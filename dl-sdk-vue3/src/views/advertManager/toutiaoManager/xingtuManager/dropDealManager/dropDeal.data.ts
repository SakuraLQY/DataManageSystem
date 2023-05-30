import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '投放广告ID',
    align: "center",
    dataIndex: 'id'
  },
  {
    title: '头条广告ID',
    align: "center",
    dataIndex: 'campaignId',
    slots: { customRender: 'campaignId_data' }
  },
  {
    title: '广告组名称',
    align: "center",
    dataIndex: 'campaignName'
  },
  {
    title: '主播',
    align: "center",
    dataIndex: 'anchorPlanName',
    slots: { customRender: 'anchorPlanName_data' }
  },
  {
    title: '投放账号',
    align: "center",
    dataIndex: 'accountName'
  },
  {
    title: '子游戏名',
    align: "center",
    dataIndex: 'subGameName'
  },
  {
    title: '子游戏类型',
    align: 'center',
    dataIndex: 'subGameType',
    customRender:({text}) => {
      let subGameType = "";
      for(let option of subGameTypeOptions){
        if(option.value == text){
          subGameType = option.label
        }
      }
      return subGameType;
    }
  },
  {
    title: '游戏包名',
    align: "center",
    dataIndex: 'pkgName'
  },
  {
    title: '下载链接',
    align: "center",
    dataIndex: 'pkgUrl',
    slots: { customRender: 'pkgUrl_data' }
  },
  {
    title: '监测链接',
    align: "center",
    dataIndex: 'dealArgs',
    slots: { customRender: 'dealArgs_data' }
  },
  {
    title: '广告描述',
    align: "center",
    dataIndex: 'dealDesc'
  },
  {
    title: '创建人',
    align: "center",
    dataIndex: 'createBy_dictText'
  },
  {
    title: '创建日期',
    align: "center",
    dataIndex: 'createTime',
    customRender:({text}) =>{
      return !text?"":text;
    },
  },
  {
    title: '更新人',
    align: "center",
    dataIndex: 'updateBy_dictText'
  },
  {
    title: '更新日期',
    align: "center",
    dataIndex: 'updateTime',
    customRender:({text}) =>{
      return !text?"":text;
    },
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "广告名称",
    field: 'dealName',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "游戏ID",
    field: 'gameId',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
  {
    label: "子游戏ID",
    field: 'subGameId',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
  {
    label: "子游戏类型 0 安卓 1 IOS",
    field: 'subGameType',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
  {
    label: "游戏包ID",
    field: 'pkgId',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
  {
    label: "投放账号ID",
    field: 'accountId',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
  {
    label: "渠道子账号ID",
    field: 'channelSubAccountId',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
  {
    label: "打包状态，0为等待操作，1为待打包，2为打包中，3为打包成功，4为打包失败",
    field: 'packState',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "创建人",
    field: 'createBy',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "创建日期",
    field: 'createTime',
    component: 'DatePicker',
    colProps: {span: 6},
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '广告名称',
    field: 'dealName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入广告名称!'},
             ];
    },
  },
  {
    label: '游戏ID',
    field: 'gameId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入游戏ID!'},
             ];
    },
  },
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
    label: '子游戏类型 0 安卓 1 IOS',
    field: 'subGameType',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入子游戏类型 0 安卓 1 IOS!'},
             ];
    },
  },
  {
    label: '游戏包ID',
    field: 'pkgId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入游戏包ID!'},
             ];
    },
  },
  {
    label: '投放账号ID',
    field: 'accountId',
    component: 'JSelectMultiple',
    componentProps:{
      dictCode: ""
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入投放账号ID!'},
             ];
    },
  },
  {
    label: '渠道子账号ID',
    field: 'channelSubAccountId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入渠道子账号ID!'},
             ];
    },
  },
  {
    label: '广告参数',
    field: 'dealArgs',
    component: 'Input',
  },
  {
    label: '广告描述',
    field: 'dealDesc',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入广告描述!'},
             ];
    },
  },
  {
    label: '安装包下载链接',
    field: 'pkgUrl',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入安装包下载链接!'},
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

// 子游戏类型
export const subGameTypeOptions = [
  {
    label: '安卓',
    value: 0
  },
  {
    label: 'IOS',
    value: 1
  }
]

// 查询星图游戏包
export const jrttPkg = "open_gateway.op_pkg where channel_id = 5,nick_name,id"