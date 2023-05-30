import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '渠道类型ID',
    align: "center",
    dataIndex: 'id',
  },
  {
    title: '渠道类型名称',
    align: "center",
    dataIndex: 'typeName',
  },
  {
    title: '备注',
    align: "center",
    dataIndex: 'typeDesc'
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
    label: "渠道类型名称",
    field: 'typeName',
    component: 'Input',
    colProps: {span: 6},
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '渠道类型名称',
    field: 'typeName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入渠道类型名称!'},
             ];
    },
  },
  {
    label: '备注',
    field: 'typeDesc',
    component: 'InputTextArea',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入备注!'},
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
