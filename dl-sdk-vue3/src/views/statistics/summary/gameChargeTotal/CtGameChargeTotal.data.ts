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
    title: '注册用户数',
    align: "center",
    dataIndex: 'registryNumber'
  },
  {
    title: '充值总额',
    align: "center",
    dataIndex: 'totalMoney'
  },
  {
    title: 'ARPU',
    align: "center",
    dataIndex: 'arpu'
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
];

//表单数据
export const formSchema: FormSchema[] = [
	// TODO 主键隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
];
