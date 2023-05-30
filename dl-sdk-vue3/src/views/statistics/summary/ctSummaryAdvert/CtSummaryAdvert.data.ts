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
    title: '广告id',
    align: "center",
    dataIndex: 'dealId',
    slots: { customRender: 'dealId' },
    sorter: true,
  },
  {
    title: '渠道游戏包名',
    align: "center",
    dataIndex: 'pkgName',
    sorter: true,
  },
  {
    title: '广告名',
    align: "center",
    dataIndex: 'dealName',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '渠道名',
    align: "center",
    dataIndex: 'channelName',
    sorter: true,
  },
  {
    title: '激活数',
    align: "center",
    dataIndex: 'countActive',
    sorter: true,
  },
  {
    title: '激活设备数',
    align: "center",
    dataIndex: 'countActiveDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '注册数',
    align: "center",
    dataIndex: 'countUser',
    sorter: true,
  },
  {
    title: '注册设备数',
    align: "center",
    dataIndex: 'countUserDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '激活设备数',
    align: "center",
    dataIndex: 'countActiveDev',
    sorter: true,
  },
  {
    title: '激活注册率',
    align: "center",
    dataIndex: 'countActiveUserRate',
    defaultHidden: true,
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '有效注册数',
    align: "center",
    dataIndex: 'countValidUser',
    sorter: true,
  },
  {
    title: '有效注册率',
    align: "center",
    dataIndex: 'countValidUserCountUserRate',
    customRender: ({ text }) => {
      return text + "%"
    },
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
    dataIndex: 'firstMoneyFirstUserRate',
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
    title: '新增ARRPU',
    align: "center",
    dataIndex: 'firstArppu',
    sorter: true,
  },
  {
    title: '老用户付费数',
    align: "center",
    dataIndex: 'oldUserPayuser',
    sorter: true,
  },
  {
    title: '老用户付费金额',
    align: "center",
    dataIndex: 'oldUserMoney',
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
    title: '老用户Arpu',
    align: "center",
    dataIndex: 'oldUserArpu',
    sorter: true,
  },
  {
    title: '老用户Arppu',
    align: "center",
    dataIndex: 'oldUserArppu',
    sorter: true,
  },
  {
    title: '累积付金费额',
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
    title: '总付费率',
    align: "center",
    dataIndex: 'alivePayuserDauRate',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },  
  {
    title: '次日留存',
    align: "center",
    dataIndex: 'retention2',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '3日留存',
    align: "center",
    dataIndex: 'retention3',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '4日留存',
    align: "center",
    dataIndex: 'retention4',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '5日留存',
    align: "center",
    dataIndex: 'retention5',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '6日留存',
    align: "center",
    dataIndex: 'retention6',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '7日留存',
    align: "center",
    dataIndex: 'retention7',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: 'ltv1',
    align: "center",
    dataIndex: 'ltv1',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: 'ltv2',
    align: "center",
    dataIndex: 'ltv2',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: 'ltv3',
    align: "center",
    dataIndex: 'ltv3',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: 'ltv4',
    align: "center",
    dataIndex: 'ltv4',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: 'ltv5',
    align: "center",
    dataIndex: 'ltv5',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: 'ltv6',
    align: "center",
    dataIndex: 'ltv6',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: 'ltv7',
    align: "center",
    dataIndex: 'ltv7',
    defaultHidden: true,
    sorter: true,
  }
];