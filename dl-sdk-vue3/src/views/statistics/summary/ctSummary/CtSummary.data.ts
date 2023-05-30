import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: 'ID',
    align: "center",
    dataIndex: 'id',
    fixed: 'left', // 固定列
    sorter: true,
  },
  {
    title: '名称',
    align: "center",
    dataIndex: 'name',
    fixed: 'left', // 固定列
    slots: { customRender: 'name' },
    sorter: true,
  },
  {
    title: '成本',
    align: "center",
    dataIndex: 'costMoney',
    sorter: true,
  },
  {
    title: '激活',
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
    title: '有效注册数',
    align: "center",
    dataIndex: 'countValidUser',
    sorter: true,
  },
  {
    title: '有效注册数-设备',
    align: "center",
    dataIndex: 'countValidUserDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '激活注册率',
    align: "center",
    dataIndex: 'countActiveUserRate',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '激活注册率-设备',
    align: "center",
    dataIndex: 'countActiveUserRateDev',
    defaultHidden: true,
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '注册单价',
    align: "center",
    dataIndex: 'costCountUser',
    sorter: true,
  },
  {
    title: '注册单价-设备',
    align: "center",
    dataIndex: 'costCountUserDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '首日付费额',
    align: "center",
    dataIndex: 'firstMoney',
    sorter: true,
  },
  {
    title: '首日付费额-设备',
    align: "center",
    dataIndex: 'firstMoneyDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '首日回款率',
    align: "center",
    dataIndex: 'firstMoneyCostMoneyRate',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '首日回款率-设备',
    align: "center",
    dataIndex: 'firstMoneyCostMoneyRateDev',
    defaultHidden: true,
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '首日付费人数',
    align: "center",
    dataIndex: 'firstPayuser',
    sorter: true,
  },
  {
    title: '首日付费人数-设备',
    align: "center",
    dataIndex: 'firstPayuserDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '首日付费单价',
    align: "center",
    dataIndex: 'firstPayuserCostMoney',
    sorter: true,
  },
  {
    title: '首日付费单价-设备',
    align: "center",
    dataIndex: 'firstPayuserCostMoneyDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '首日arpu',
    align: "center",
    dataIndex: 'firstArpu',
    sorter: true,
  },
  {
    title: '首日arpu-设备',
    align: "center",
    dataIndex: 'firstArpuDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '首日arppu',
    align: "center",
    dataIndex: 'firstArppu',
    sorter: true,
  },
  {
    title: '首日arppu-设备',
    align: "center",
    dataIndex: 'firstArppuDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '首日ROI',
    align: "center",
    dataIndex: 'firstRoi',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '首日ROI-设备',
    align: "center",
    dataIndex: 'firstRoiDev',
    defaultHidden: true,
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '首日付费次数',
    align: "center",
    dataIndex: 'firstOrder',
    sorter: true,
  },
  {
    title: '首日付费次数-设备',
    align: "center",
    dataIndex: 'firstOrderDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '首日订单均值',
    align: "center",
    dataIndex: 'firstMoneyFirstOrder',
    sorter: true,
  },
  {
    title: '首日订单均值-设备',
    align: "center",
    dataIndex: 'firstMoneyFirstOrderDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '首日付费次数单价',
    align: "center",
    dataIndex: 'firstOrderCost',
    sorter: true,
  },
  {
    title: '首日付费次数单价-设备',
    align: "center",
    dataIndex: 'firstOrderCostDev',
    defaultHidden: true,
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
    title: '老用户付费率-设备',
    align: "center",
    dataIndex: 'oldUserPayRateDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '老用户Arpu',
    align: "center",
    dataIndex: 'oldUserArpu',
    sorter: true,
  },
  {
    title: '老用户Arpu-设备',
    align: "center",
    dataIndex: 'oldUserArpuDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '老用户Arppu',
    align: "center",
    dataIndex: 'oldUserArppu',
    sorter: true,
  },
  {
    title: '老用户Arppu-设备',
    align: "center",
    dataIndex: 'oldUserArppuDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '周期付费额',
    align: "center",
    dataIndex: 'cycleMoney',
    sorter: true,
  },
  {
    title: '周期付费额-设备',
    align: "center",
    dataIndex: 'cycleMoneyDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '周期回本率',
    align: "center",
    dataIndex: 'cycleMoneyCost',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '周期回本率-设备',
    align: "center",
    dataIndex: 'cycleMoneyCostDev',
    customRender: ({ text }) => {
      return text + "%"
    },
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '周期付费人数',
    align: "center",
    dataIndex: 'cycleUser',
    sorter: true,
  },
  {
    title: '周期付费人数-设备',
    align: "center",
    dataIndex: 'cycleUserDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '周期付费率',
    align: "center",
    dataIndex: 'cycleUserCountUserRate',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '周期付费率-设备',
    align: "center",
    dataIndex: 'cycleUserCountUserRateDev',
    defaultHidden: true,
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: 'ARPU(周期)',
    align: "center",
    dataIndex: 'cycleArpu',
    sorter: true,
  },
  {
    title: 'ARPU(周期)-设备',
    align: "center",
    dataIndex: 'cycleArpuDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: 'ARPPU(周期)',
    align: "center",
    dataIndex: 'cycleArppu',
    sorter: true,
  },
  {
    title: 'ARPPU(周期)-设备',
    align: "center",
    dataIndex: 'cycleArppuDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '累积付费额',
    align: "center",
    dataIndex: 'totalMoney',
    sorter: true,
  },
  {
    title: '累积付费额-设备',
    align: "center",
    dataIndex: 'totalMoneyDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '累积回本率',
    align: "center",
    dataIndex: 'totalMoneyCostMoneyRate',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '累积回本率-设备',
    align: "center",
    dataIndex: 'totalMoneyCostMoneyRateDev',
    defaultHidden: true,
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '累积付费人数',
    align: "center",
    dataIndex: 'totalPayuser',
    sorter: true,
  },
  {
    title: '累积付费人数-设备',
    align: "center",
    dataIndex: 'totalPayuserDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: 'ARPU(累积)',
    align: "center",
    dataIndex: 'totalArpu',
    sorter: true,
  },
  {
    title: 'ARPU(累积)-设备',
    align: "center",
    dataIndex: 'totalArpuDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: 'ARPPU(累积)',
    align: "center",
    dataIndex: 'totalArppu',
    sorter: true,
  },
  {
    title: 'ARPPU(累积)-设备',
    align: "center",
    dataIndex: 'totalArppuDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: 'DAU',
    align: "center",
    dataIndex: 'countDau',
    sorter: true,
  },
  {
    title: 'DAU-设备',
    align: "center",
    dataIndex: 'countDauDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '活跃付费人数',
    align: "center",
    dataIndex: 'alivePayuser',
    sorter: true,
  },
  {
    title: '活跃付费人数-设备',
    align: "center",
    dataIndex: 'alivePayuserDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '活跃付费金额',
    align: "center",
    dataIndex: 'aliveMoney',
    sorter: true,
  },
  {
    title: '活跃付费金额-设备',
    align: "center",
    dataIndex: 'aliveMoneyDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '活跃付费次数',
    align: "center",
    dataIndex: 'aliveOrder',
    sorter: true,
  },
  {
    title: '活跃付费次数-设备',
    align: "center",
    dataIndex: 'aliveOrderDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '活跃订单均值',
    align: "center",
    dataIndex: 'aliveMoneyAliveOrderAlive',
    sorter: true,
  },
  {
    title: '活跃订单均值-设备',
    align: "center",
    dataIndex: 'aliveMoneyAliveOrderAliveDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '活跃订单均值',
    align: "center",
    dataIndex: 'aliveMoneyAliveOrderAlive',
    sorter: true,
  },
  {
    title: '活跃订单均值-设备',
    align: "center",
    dataIndex: 'aliveMoneyAliveOrderAliveDev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '次日留存',
    align: "center",
    dataIndex: 'retention2',
    sorter: true,
  },
  {
    title: '3日留存',
    align: "center",
    dataIndex: 'retention3',
    sorter: true,
  },
  {
    title: '4日留存',
    align: "center",
    dataIndex: 'retention4',
    sorter: true,
  },
  {
    title: '5日留存',
    align: "center",
    dataIndex: 'retention5',
    sorter: true,
  },
  {
    title: '6日留存',
    align: "center",
    dataIndex: 'retention6',
    sorter: true,
  },
  {
    title: '7日留存',
    align: "center",
    dataIndex: 'retention7',
    sorter: true,
  },
  {
    title: '次日留存-设备',
    align: "center",
    dataIndex: 'retention2Dev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '3日留存-设备',
    align: "center",
    dataIndex: 'retention3Dev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '4日留存-设备',
    align: "center",
    dataIndex: 'retention4Dev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '5日留存-设备',
    align: "center",
    dataIndex: 'retention5Dev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '6日留存-设备',
    align: "center",
    dataIndex: 'retention6Dev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: '7日留存-设备',
    align: "center",
    dataIndex: 'retention7Dev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: 'ltv1',
    align: "center",
    dataIndex: 'ltv1',
    sorter: true,
  },
  {
    title: 'ltv2',
    align: "center",
    dataIndex: 'ltv2',
    sorter: true,
  },
  {
    title: 'ltv3',
    align: "center",
    dataIndex: 'ltv3',
    sorter: true,
  },
  {
    title: 'ltv4',
    align: "center",
    dataIndex: 'ltv4',
    sorter: true,
  },
  {
    title: 'ltv5',
    align: "center",
    dataIndex: 'ltv5',
    sorter: true,
  },
  {
    title: 'ltv6',
    align: "center",
    dataIndex: 'ltv6',
    sorter: true,
  },
  {
    title: 'ltv7',
    align: "center",
    dataIndex: 'ltv7',
    sorter: true,
  },
  {
    title: 'ltv1-设备',
    align: "center",
    dataIndex: 'ltv1Dev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: 'ltv2-设备',
    align: "center",
    dataIndex: 'ltv2Dev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: 'ltv3-设备',
    align: "center",
    dataIndex: 'ltv3Dev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: 'ltv4-设备',
    align: "center",
    dataIndex: 'ltv4Dev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: 'ltv5-设备',
    align: "center",
    dataIndex: 'ltv5Dev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: 'ltv6-设备',
    align: "center",
    dataIndex: 'ltv6Dev',
    defaultHidden: true,
    sorter: true,
  },
  {
    title: 'ltv7-设备',
    align: "center",
    dataIndex: 'ltv7Dev',
    defaultHidden: true,
    sorter: true,
  },

];

export const sameAccountColumn = [
  {
    title: '广告id',
    align: "center",
    dataIndex: 'dealId',
    width: 40
  },
  {
    title: '广告名',
    align: "center",
    dataIndex: 'dealName',
    width: 100
  },
  {
    title: '消耗',
    align: "center",
    dataIndex: 'costMoney',
    width: 40
  },
  {
    title: '注册数',
    align: "center",
    dataIndex: 'countUser',
    width: 40
  },
  {
    title: '注册单价',
    align: "center",
    dataIndex: 'costCountUser',
    width: 40
  },
  {
    title: '新增付费人数',
    align: "center",
    dataIndex: 'firstPayuser',
    width: 60
  },
  {
    title: '新增付费',
    align: "center",
    dataIndex: 'firstMoney',
    width: 40
  },
  {
    title: '新增付费单价',
    align: "center",
    dataIndex: 'firstPayuserCostMoney',
    width: 60
  },
  {
    title: '首日ARPU',
    align: "center",
    dataIndex: 'firstArpu',
    width: 40
  },
  {
    title: '首日ARRPU',
    align: "center",
    dataIndex: 'firstArppu',
    width: 40
  },
  {
    title: '首日ROI',
    align: "center",
    dataIndex: 'firstRoi',
    width: 40
  },
  {
    title: '累积付费额',
    align: "center",
    dataIndex: 'totalMoney',
    width: 40
  },
  {
    title: '活跃人数',
    align: "center",
    dataIndex: 'alivePayuser',
    width: 40
  },
  {
    title: '活跃金额',
    align: "center",
    dataIndex: 'aliveMoney',
    width: 40
  },
  
];

export const sevenDayColumn = [
  {
    title: '日期',
    align: "center",
    dataIndex: 'date',
  },
  {
    title: '消耗',
    align: "center",
    dataIndex: 'costMoney',
  },
  {
    title: '注册数',
    align: "center",
    dataIndex: 'countUser',
  },
  {
    title: '注册单价',
    align: "center",
    dataIndex: 'costCountUser',
  },
  {
    title: '新增付费人数',
    align: "center",
    dataIndex: 'firstPayuser',
  },
  {
    title: '新增付费',
    align: "center",
    dataIndex: 'firstMoney',
  },
  {
    title: '新增付费单价',
    align: "center",
    dataIndex: 'firstPayuserCostMoney',
  },
  {
    title: '首日ARPU',
    align: "center",
    dataIndex: 'firstArpu',
  },
  {
    title: '首日ARRPU',
    align: "center",
    dataIndex: 'firstArppu',
  },
  {
    title: '首日ROI',
    align: "center",
    dataIndex: 'firstRoi',
  },
  {
    title: '累积付费额',
    align: "center",
    dataIndex: 'totalMoney',
  },
  {
    title: '活跃付费人数',
    align: "center",
    dataIndex: 'alivePayuser',
  },
  {
    title: '活跃付费金额',
    align: "center",
    dataIndex: 'aliveMoney',
  },
  
];