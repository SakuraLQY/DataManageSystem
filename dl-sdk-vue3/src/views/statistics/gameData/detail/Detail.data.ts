import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '日期',
    align: "center",
    dataIndex: 'day',
    fixed: 'left', // 固定列
    sorter: true,
  },
  {
    title: '游戏名',
    align: "center",
    dataIndex: 'gameName',
    sorter: true,
  },
  {
    title: '渠道名',
    align: "center",
    dataIndex: 'channelName',
    sorter: true,
  },
  {
    title: '广告名',
    align: "center",
    dataIndex: 'dealName',
    sorter: true,
  },
  {
    title: '激活数',
    align: "center",
    dataIndex: 'countActive',
    sorter: true,
  },
  {
    title: '注册数',
    align: "center",
    dataIndex: 'countUser',
    sorter: true,
  },
  {
    title: '有效注册数',
    align: "center",
    dataIndex: 'countValidUser',
    sorter: true,
  },
  {
    title: '新增付费人数',
    align: "center",
    dataIndex: 'firstPayuser',
    sorter: true,
  },
  {
    title: '新增付费金额',
    align: "center",
    dataIndex: 'firstMoney',
    sorter: true,
  },
  {
    title: '新增付费率',
    align: "center",
    dataIndex: 'firstMoneyRate',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '新增ARPU',
    align: "center",
    dataIndex: 'firstArpu',
    sorter: true,
  },
  {
    title: '新增ARPPU',
    align: "center",
    dataIndex: 'firstArppu',
    sorter: true,
  },
  {
    title: '老用户付费率',
    align: "center",
    dataIndex: 'oldUserPayRate',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '老用户ARPU',
    align: "center",
    dataIndex: 'oldUserArpu',
    sorter: true,
  },
  {
    title: '老用户ARPPU',
    align: "center",
    dataIndex: 'oldUserArppu',
    sorter: true,
  },
  {
    title: '累计付费金额',
    align: "center",
    dataIndex: 'totalMoney',
    sorter: true,
  },
  {
    title: 'DAU',
    align: "center",
    dataIndex: 'countDau',
    sorter: true,
  },
  {
    title: '活跃付费人数',
    align: "center",
    dataIndex: 'alivePayuser',
    sorter: true,
  },
  {
    title: '活跃付费金额',
    align: "center",
    dataIndex: 'aliveMoney',
    sorter: true,
  },
  {
    title: '活跃付费率',
    align: "center",
    dataIndex: 'aliveMoneyRate',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: 'ARPU',
    align: "center",
    dataIndex: 'arpu',
    sorter: true,
  },
  {
    title: 'ARPPU',
    align: "center",
    dataIndex: 'arppu',
    sorter: true,
  },
  {
    title: '推广费用',
    align: "center",
    dataIndex: 'costMoney',
    sorter: true,
  },
  {
    title: 'SDK分成所得',
    align: "center",
    dataIndex: 'sdkShare',
    sorter: true,
  },
];