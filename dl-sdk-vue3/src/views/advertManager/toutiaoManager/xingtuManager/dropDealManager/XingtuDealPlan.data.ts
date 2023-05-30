import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: 'ID',
    align: "center",
    dataIndex: 'id'
  },
  {
    title: '广告ID',
    align: "center",
    dataIndex: 'dealId'
  },
  {
    title: '广告主ID',
    align: "center",
    dataIndex: 'advertiserId'
  },
  {
    title: '广告组ID',
    align: "center",
    dataIndex: 'campaignId'
  },
  {
    title: '广告计划名称',
    align: "center",
    dataIndex: 'name'
  },
  {
    title: '广告计划ID',
    align: "center",
    dataIndex: 'adId'
  },
  {
    title: '广告创意ID',
    align: "center",
    dataIndex: 'creativeId'
  },
  {
    title: '计划状态',
    align: "center",
    dataIndex: 'operation'
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
];


