import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '游戏',
    align: "center",
    dataIndex: 'gameName'
  },
  {
    title: '子游戏',
    align: "center",
    dataIndex: 'subGameName'
  },
  {
    title: '站点名',
    align: "center",
    dataIndex: 'siteName'
  },
  {
    title: '站点内容',
    align: "center",
    dataIndex: 'siteContent',
    slots:{customRender:'siteContent'},
    width: 250
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '游戏-id',
    field: 'gameId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入游戏-id!'},
             ];
    },
  },
  {
    label: '子游戏-id',
    field: 'subGameId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入子游戏-id!'},
             ];
    },
  },
  {
    label: '站点名',
    field: 'siteName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入站点名!'},
             ];
    },
  },
  {
    label: '站点内容',
    field: 'siteContent',
    component: 'InputTextArea',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入站点内容!'},
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
