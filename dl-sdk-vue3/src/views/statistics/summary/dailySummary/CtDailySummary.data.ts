import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
];
export interface Item{
  level:string;
  zeroHour:string;
  oneHour:string;
  twoHour:string;
  threeHour:string;
  fourHour:string;
  fiveHour:string;
  sixHour:string;
  sevenHour:string;
  eightHour:string;
  nineHour:string;
  tenHour:string;
  elevenHour:string;
  twelveHour:string;
  thirteenHour:string;
  fourteenHour:string;
  fifteenHour:string;
  sixteenHour:string;
  seventeenHour:string;
  eighteenHour:string;
  nineteenHour:string;
  twentyHour:string;
  twentyoneHour:string;
  twentytwoHour:string;
  twentythreeHour:string;
}

export interface GrowCardItems{
  icon:string;
  title:string;
  value?:any;
  compare?:string;
  percent?:any
}


//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "广告列表",
    field: 'dealId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "游戏项目",
    field: 'gameId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "游戏名称",
    field: 'subGameId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "渠道游戏包",
    field: 'pkgId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "渠道类型",
    field: 'channelTypeId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "渠道名称",
    field: 'channelId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "二级渠道",
    field: 'channelSubAccountId',
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
