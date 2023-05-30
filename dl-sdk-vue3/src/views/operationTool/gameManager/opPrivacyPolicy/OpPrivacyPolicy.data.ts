import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  // {
  //   title: '子游戏(子游戏ID)',
  //   align: "center",
  //   dataIndex: 'game',
  //   slots: { customRender: 'game' },
  // },
  {
    title: '名称',
    align: "center",
    dataIndex: 'name'
  },
  {
    title: 'cdn地址',
    align: "center",
    dataIndex: 'url'
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
      return !text?"":text;
    },
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "子游戏ID",
    field: 'gameId',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '子游戏ID',
    field: 'gameId',
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
    label: '内容',
    field: 'content',
    component: 'InputTextArea',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入内容!'},
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
