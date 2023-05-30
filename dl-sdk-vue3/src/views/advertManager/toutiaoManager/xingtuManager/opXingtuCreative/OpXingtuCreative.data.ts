import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '广告主ID',
    align: "center",
    dataIndex: 'advertiserId_dictText'
  },
  {
    title: '广告计划ID',
    align: "center",
    dataIndex: 'adId_dictText'
  },
  {
    title: '广告创意ID',
    align: "center",
    dataIndex: 'creativeId'
  },
  {
    title: '素材类型',
    align: "center",
    dataIndex: 'imageMode'
  },
  {
    title: '创意素材标题',
    align: "center",
    dataIndex: 'title'
  },
  {
    title: '动态词包列表',
    align: "center",
    dataIndex: 'creativeWordIds'
  },
  {
    title: '创意素材状态',
    align: "center",
    dataIndex: 'status'
  },
  {
    title: '创意素材操作状态',
    align: "center",
    dataIndex: 'optStatus'
  },
  {
    title: '视频素材，封面图片ID',
    align: "center",
    dataIndex: 'imageId'
  },
  {
    title: '视频素材，视频ID',
    align: "center",
    dataIndex: 'videoId'
  },
  {
    title: '抖音视频ID',
    align: "center",
    dataIndex: 'awemeItemId'
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '广告主ID',
    field: 'advertiserId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入广告主ID!'},
             ];
    },
  },
  {
    label: '广告计划ID',
    field: 'adId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: ""
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入广告计划ID!'},
             ];
    },
  },
  {
    label: '广告创意ID',
    field: 'creativeId',
    component: 'InputNumber',
    dynamicDisabled: true
  },
  {
    label: '素材类型',
    field: 'imageMode',
    component: 'Input',
  },
  {
    label: '创意素材标题',
    field: 'title',
    component: 'Input',
  },
  {
    label: '动态词包列表',
    field: 'creativeWordIds',
    component: 'Input',
  },
  {
    label: '创意素材状态',
    field: 'status',
    component: 'Input',
  },
  {
    label: '创意素材操作状态',
    field: 'optStatus',
    component: 'Input',
  },
  {
    label: '视频素材，封面图片ID',
    field: 'imageId',
    component: 'Input',
  },
  {
    label: '视频素材，视频ID',
    field: 'videoId',
    component: 'Input',
  },
  {
    label: '抖音视频ID',
    field: 'awemeItemId',
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
