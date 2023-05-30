import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '游戏ID',
    align: "center",
    dataIndex: 'id'
  },
  {
    title: '游戏名',
    align: "center",
    dataIndex: 'gameName'
  },
  {
    title: '备注',
    align: "center",
    dataIndex: 'descs'
  },
  {
    title: '创建时间',
    align: "center",
    dataIndex: 'createTime',
    customRender:({text}) =>{
      return !text?"":text;
    },
  },
  {
    title: '创建用户',
    align: "center",
    dataIndex: 'createBy'
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "游戏名",
    field: 'gameName',
    component: 'Input',
    colProps: {span: 6},
  },
];

//表单数据
//编辑
export const formSchema: FormSchema[] = [
  {
    label: '游戏类型',
    field: 'gameTypeId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "game_type"
    },
    
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入游戏类型!'},
             ];
    },
  },
  {
    label: '游戏名',
    field: 'gameName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入游戏名!'},
             ];
    },
  },
  {
    label: '备注',
    field: 'descs',
    component: 'InputTextArea',
  },
	// TODO 主键隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
];
