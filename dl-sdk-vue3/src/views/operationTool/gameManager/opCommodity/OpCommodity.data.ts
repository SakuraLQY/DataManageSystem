import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '商品ID',
    align: "center",
    dataIndex: 'goodsId'
  },
  {
    title: '子游戏ID',
    align: "center",
    dataIndex: 'gameId'
  },
  {
    title: '游戏商品金额',
    align: "center",
    dataIndex: 'money'
  },
  {
    title: '币种',
    align: "center",
    dataIndex: 'currencyType_dictText'
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '商品ID',
    field: 'goodsId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入商品ID!'},
             ];
    },
  },
  {
    label: '子游戏ID',
    field: 'gameId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入子游戏ID!'},
             ];
    },
  },
  {
    label: '游戏商品金额',
    field: 'money',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入游戏商品金额!'},
             ];
    },
  },
  {
    label: '币种',
    field: 'currencyType',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "current_type"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入币种!'},
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
