import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
];

//查询数据
export const searchFormSchema: FormSchema[] = [
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
    componentProps: {
      showTime: true,
    },
    colProps: {span: 6},
  },
  {
    label: "更新人",
    field: 'updateBy',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "更新日期",
    field: 'updateTime',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
    },
    colProps: {span: 6},
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '创建人',
    field: 'createBy',
    component: 'Input',
  },
  {
    label: '创建日期',
    field: 'createTime',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss'
    },
  },
  {
    label: '更新人',
    field: 'updateBy',
    component: 'Input',
  },
  {
    label: '更新日期',
    field: 'updateTime',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss'
    },
  },
  {
    label: '所属部门',
    field: 'sysOrgCode',
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
