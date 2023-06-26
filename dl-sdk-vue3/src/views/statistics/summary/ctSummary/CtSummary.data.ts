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
  },
  {
    title: '名称',
    align: "center",
    dataIndex: 'name',
    fixed: 'left', // 固定列
    slots: { customRender: 'name' },
  },
  {
    title: '成本',
    align: "center",
    dataIndex: 'costMoney',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.costMoney > b.costMoney ? 1:-1;
    },
  },
  {
    title: '激活',
    align: "center",
    dataIndex: 'countActive',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.countActive > b.countActive ? 1:-1;
    },
  },
  {
    title: '激活设备数',
    align: "center",
    dataIndex: 'countActiveDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.countActiveDev > b.countActiveDev ? 1:-1;
    },
  },
  {
    title: '注册数',
    align: "center",
    dataIndex: 'countUser',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.countUser > b.countUser ? 1:-1;
    },
  },
  {
    title: '注册设备数',
    align: "center",
    dataIndex: 'countUserDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.countUserDev > b.countUserDev ? 1:-1;
    },
  },
  {
    title: '有效注册数',
    align: "center",
    dataIndex: 'countValidUser',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.countValidUser > b.countValidUser ? 1:-1;
    },
  },
  {
    title: '有效注册数-设备',
    align: "center",
    dataIndex: 'countValidUserDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.countValidUserDev > b.countValidUserDev ? 1:-1;
    },
  },
  {
    title: '激活注册率',
    align: "center",
    dataIndex: 'countActiveUserRate',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.countActiveUserRate > b.countActiveUserRate ? 1:-1;
    },
  },
  {
    title: '激活注册率-设备',
    align: "center",
    dataIndex: 'countActiveUserRateDev',
    defaultHidden: true,
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.countActiveUserRateDev > b.countActiveUserRateDev ? 1:-1;
    },
  },
  {
    title: '注册单价',
    align: "center",
    dataIndex: 'costCountUser',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.costCountUser > b.costCountUser ? 1:-1;
    },
  },
  {
    title: '注册单价-设备',
    align: "center",
    dataIndex: 'costCountUserDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.costCountUserDev > b.costCountUserDev ? 1:-1;
    },
  },
  {
    title: '首日付费额',
    align: "center",
    dataIndex: 'firstMoney',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.firstMoney > b.firstMoney ? 1:-1;
    },
  },
  {
    title: '首日付费额-设备',
    align: "center",
    dataIndex: 'firstMoneyDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.firstMoneyDev > b.firstMoneyDev ? 1:-1;
    },
  },
  {
    title: '首日回款率',
    align: "center",
    dataIndex: 'firstMoneyCostMoneyRate',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.firstMoneyCostMoneyRate > b.firstMoneyCostMoneyRate ? 1:-1;
    },
  },
  {
    title: '首日回款率-设备',
    align: "center",
    dataIndex: 'firstMoneyCostMoneyRateDev',
    defaultHidden: true,
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.firstMoneyCostMoneyRateDev > b.firstMoneyCostMoneyRateDev ? 1:-1;
    },
  },
  {
    title: '首日付费人数',
    align: "center",
    dataIndex: 'firstPayuser',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.firstPayuser > b.firstPayuser ? 1:-1;
    },
  },
  {
    title: '首日付费人数-设备',
    align: "center",
    dataIndex: 'firstPayuserDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.firstPayuserDev > b.firstPayuserDev ? 1:-1;
    },
  },
  {
    title: '首日付费单价',
    align: "center",
    dataIndex: 'firstPayuserCostMoney',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.firstPayuserCostMoney > b.firstPayuserCostMoney ? 1:-1;
    },
  },
  {
    title: '首日付费单价-设备',
    align: "center",
    dataIndex: 'firstPayuserCostMoneyDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.firstPayuserCostMoneyDev > b.firstPayuserCostMoneyDev ? 1:-1;
    },
  },
  {
    title: '首日arpu',
    align: "center",
    dataIndex: 'firstArpu',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.firstArpu > b.firstArpu ? 1:-1;
    },
  },
  {
    title: '首日arpu-设备',
    align: "center",
    dataIndex: 'firstArpuDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.firstArpuDev > b.firstArpuDev ? 1:-1;
    },
  },
  {
    title: '首日arppu',
    align: "center",
    dataIndex: 'firstArppu',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.firstArppu > b.firstArppu ? 1:-1;
    },
  },
  {
    title: '首日arppu-设备',
    align: "center",
    dataIndex: 'firstArppuDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.firstArppuDev > b.firstArppuDev ? 1:-1;
    },
  },
  {
    title: '首日ROI',
    align: "center",
    dataIndex: 'firstRoi',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.firstRoi > b.firstRoi ? 1:-1;
    },
  },
  {
    title: '首日ROI-设备',
    align: "center",
    dataIndex: 'firstRoiDev',
    defaultHidden: true,
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.firstRoiDev > b.firstRoiDev ? 1:-1;
    },
  },
  {
    title: '首日付费次数',
    align: "center",
    dataIndex: 'firstOrder',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.firstOrder > b.firstOrder ? 1:-1;
    },
  },
  {
    title: '首日付费次数-设备',
    align: "center",
    dataIndex: 'firstOrderDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.firstOrderDev > b.firstOrderDev ? 1:-1;
    },
  },
  {
    title: '首日订单均值',
    align: "center",
    dataIndex: 'firstMoneyFirstOrder',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.firstMoneyFirstOrder > b.firstMoneyFirstOrder ? 1:-1;
    },
  },
  {
    title: '首日订单均值-设备',
    align: "center",
    dataIndex: 'firstMoneyFirstOrderDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.firstMoneyFirstOrderDev > b.firstMoneyFirstOrderDev ? 1:-1;
    },
  },
  {
    title: '首日付费次数单价',
    align: "center",
    dataIndex: 'firstOrderCost',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.firstOrderCost > b.firstOrderCost? 1:-1;
    },
  },
  {
    title: '首日付费次数单价-设备',
    align: "center",
    dataIndex: 'firstOrderCostDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.firstOrderCostDev > b.firstOrderCostDev ? 1:-1;
    },
  },
  {
    title: '老用户付费率',
    align: "center",
    dataIndex: 'oldUserPayRate',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.oldUserPayRate > b.oldUserPayRate ? 1:-1;
    },
  },
  {
    title: '老用户付费率-设备',
    align: "center",
    dataIndex: 'oldUserPayRateDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.oldUserPayRateDev > b.oldUserPayRateDev ? 1:-1;
    },
  },
  {
    title: '老用户Arpu',
    align: "center",
    dataIndex: 'oldUserArpu',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.oldUserArpu > b.oldUserArpu ? 1:-1;
    },
  },
  {
    title: '老用户Arpu-设备',
    align: "center",
    dataIndex: 'oldUserArpuDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.oldUserArpuDev > b.oldUserArpuDev ? 1:-1;
    },
  },
  {
    title: '老用户Arppu',
    align: "center",
    dataIndex: 'oldUserArppu',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.oldUserArppu > b.oldUserArppu ? 1:-1;
    },
  },
  {
    title: '老用户Arppu-设备',
    align: "center",
    dataIndex: 'oldUserArppuDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.oldUserArppuDev > b.oldUserArppuDev ? 1:-1;
    },
  },
  {
    title: '周期付费额',
    align: "center",
    dataIndex: 'cycleMoney',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.cycleMoney > b.cycleMoney ? 1:-1;
    },
  },
  {
    title: '周期付费额-设备',
    align: "center",
    dataIndex: 'cycleMoneyDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.cycleMoneyDev > b.cycleMoneyDev ? 1:-1;
    },
  },
  {
    title: '周期回本率',
    align: "center",
    dataIndex: 'cycleMoneyCost',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.cycleMoneyCost > b.cycleMoneyCost ? 1:-1;
    },
  },
  {
    title: '周期回本率-设备',
    align: "center",
    dataIndex: 'cycleMoneyCostDev',
    customRender: ({ text }) => {
      return text + "%"
    },
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.cycleMoneyCostDev > b.cycleMoneyCostDev ? 1:-1;
    },
  },
  {
    title: '周期付费人数',
    align: "center",
    dataIndex: 'cycleUser',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.cycleUser > b.cycleUser ? 1:-1;
    },
  },
  {
    title: '周期付费人数-设备',
    align: "center",
    dataIndex: 'cycleUserDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.cycleUserDev > b.cycleUserDev ? 1:-1;
    },
  },
  {
    title: '周期付费率',
    align: "center",
    dataIndex: 'cycleUserCountUserRate',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.cycleUserCountUserRate > b.cycleUserCountUserRate ? 1:-1;
    },
  },
  {
    title: '周期付费率-设备',
    align: "center",
    dataIndex: 'cycleUserCountUserRateDev',
    defaultHidden: true,
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.cycleUserCountUserRateDev > b.cycleUserCountUserRateDev ? 1:-1;
    },
  },
  {
    title: 'ARPU(周期)',
    align: "center",
    dataIndex: 'cycleArpu',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.cycleArpu > b.cycleArpu ? 1:-1;
    },
  },
  {
    title: 'ARPU(周期)-设备',
    align: "center",
    dataIndex: 'cycleArpuDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.cycleArpuDev > b.cycleArpuDev ? 1:-1;
    },
  },
  {
    title: 'ARPPU(周期)',
    align: "center",
    dataIndex: 'cycleArppu',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.cycleArppu > b.cycleArppu ? 1:-1;
    },
  },
  {
    title: 'ARPPU(周期)-设备',
    align: "center",
    dataIndex: 'cycleArppuDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.cycleArppuDev > b.cycleArppuDev ? 1:-1;
    },
  },
  {
    title: '累积付费额',
    align: "center",
    dataIndex: 'totalMoney',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.totalMoney > b.totalMoney ? 1:-1;
    },
  },
  {
    title: '累积付费额-设备',
    align: "center",
    dataIndex: 'totalMoneyDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.totalMoneyDev > b.totalMoneyDev ? 1:-1;
    },
  },
  {
    title: '累积回本率',
    align: "center",
    dataIndex: 'totalMoneyCostMoneyRate',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.totalMoneyCostMoneyRate > b.totalMoneyCostMoneyRate ? 1:-1;
    },
  },
  {
    title: '累积回本率-设备',
    align: "center",
    dataIndex: 'totalMoneyCostMoneyRateDev',
    defaultHidden: true,
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.totalMoneyCostMoneyRateDev > b.totalMoneyCostMoneyRateDev ? 1:-1;
    },
  },
  {
    title: '累积付费人数',
    align: "center",
    dataIndex: 'totalPayuser',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.totalPayuser > b.totalPayuser ? 1:-1;
    },
  },
  {
    title: '累积付费人数-设备',
    align: "center",
    dataIndex: 'totalPayuserDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.totalPayuserDev > b.totalPayuserDev ? 1:-1;
    },
  },
  {
    title: 'ARPU(累积)',
    align: "center",
    dataIndex: 'totalArpu',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.totalArpu > b.totalArpu ? 1:-1;
    },
  },
  {
    title: 'ARPU(累积)-设备',
    align: "center",
    dataIndex: 'totalArpuDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.totalArpuDev > b.totalArpuDev ? 1:-1;
    },
  },
  {
    title: 'ARPPU(累积)',
    align: "center",
    dataIndex: 'totalArppu',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.totalArppu > b.totalArppu ? 1:-1;
    },
  },
  {
    title: 'ARPPU(累积)-设备',
    align: "center",
    dataIndex: 'totalArppuDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.totalArppuDev > b.totalArppuDev ? 1:-1;
    },
  },
  {
    title: 'DAU',
    align: "center",
    dataIndex: 'countDau',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.countDau > b.countDau ? 1:-1;
    },
  },
  {
    title: 'DAU-设备',
    align: "center",
    dataIndex: 'countDauDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.countDauDev > b.countDauDev ? 1:-1;
    },
  },
  {
    title: '活跃付费人数',
    align: "center",
    dataIndex: 'alivePayuser',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.alivePayuser > b.alivePayuser ? 1:-1;
    },
  },
  {
    title: '活跃付费人数-设备',
    align: "center",
    dataIndex: 'alivePayuserDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.alivePayuserDev > b.alivePayuserDev ? 1:-1;
    },
  },
  {
    title: '活跃付费金额',
    align: "center",
    dataIndex: 'aliveMoney',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.aliveMoney > b.aliveMoney ? 1:-1;
    },
  },
  {
    title: '活跃付费金额-设备',
    align: "center",
    dataIndex: 'aliveMoneyDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.aliveMoneyDev > b.aliveMoneyDev ? 1:-1;
    },
  },
  {
    title: '活跃付费次数',
    align: "center",
    dataIndex: 'aliveOrder',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.aliveOrder > b.aliveOrder ? 1:-1;
    },
  },
  {
    title: '活跃付费次数-设备',
    align: "center",
    dataIndex: 'aliveOrderDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.aliveOrderDev > b.aliveOrderDev ? 1:-1;
    },
  },
  {
    title: '活跃订单均值',
    align: "center",
    dataIndex: 'aliveMoneyAliveOrderAlive',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.aliveMoneyAliveOrderAlive > b.aliveMoneyAliveOrderAlive ? 1:-1;
    },
  },
  {
    title: '活跃订单均值-设备',
    align: "center",
    dataIndex: 'aliveMoneyAliveOrderAliveDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.aliveMoneyAliveOrderAliveDev > b.aliveMoneyAliveOrderAliveDev ? 1:-1;
    },
  },
  {
    title: '活跃订单均值',
    align: "center",
    dataIndex: 'aliveMoneyAliveOrderAlive',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.aliveMoneyAliveOrderAlive > b.aliveMoneyAliveOrderAlive ? 1:-1;
    },
  },
  {
    title: '活跃订单均值-设备',
    align: "center",
    dataIndex: 'aliveMoneyAliveOrderAliveDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.aliveMoneyAliveOrderAliveDev > b.aliveMoneyAliveOrderAliveDev ? 1:-1;
    },
  },
  {
    title: '次日留存',
    align: "center",
    dataIndex: 'retention2',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.retention2 > b.retention2 ? 1:-1;
    },
    customRender: ({ text }) => {
      return text + "%"
    },
  },
  {
    title: '3日留存',
    align: "center",
    dataIndex: 'retention3',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.retention3 > b.retention3 ? 1:-1;
    },
    customRender: ({ text }) => {
      return text + "%"
    },
  },
  {
    title: '4日留存',
    align: "center",
    dataIndex: 'retention4',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.retention4 > b.retention4 ? 1:-1;
    },
    customRender: ({ text }) => {
      return text + "%"
    },
  },
  {
    title: '5日留存',
    align: "center",
    dataIndex: 'retention5',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.retention5 > b.retention5 ? 1:-1;
    },
    customRender: ({ text }) => {
      return text + "%"
    },
  },
  {
    title: '6日留存',
    align: "center",
    dataIndex: 'retention6',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.retention6 > b.retention6 ? 1:-1;
    },
    customRender: ({ text }) => {
      return text + "%"
    },
  },
  {
    title: '7日留存',
    align: "center",
    dataIndex: 'retention7',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.retention7 > b.retention7 ? 1:-1;
    },
    customRender: ({ text }) => {
      return text + "%"
    },
  },
  {
    title: '次日留存-设备',
    align: "center",
    dataIndex: 'retention2Dev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.retention2Dev > b.retention2Dev ? 1:-1;
    },
    customRender: ({ text }) => {
      return text + "%"
    },
  },
  {
    title: '3日留存-设备',
    align: "center",
    dataIndex: 'retention3Dev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.retention3Dev > b.retention3Dev ? 1:-1;
    },
    customRender: ({ text }) => {
      return text + "%"
    },
  },
  {
    title: '4日留存-设备',
    align: "center",
    dataIndex: 'retention4Dev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.retention4Dev > b.retention4Dev ? 1:-1;
    },
    customRender: ({ text }) => {
      return text + "%"
    },
  },
  {
    title: '5日留存-设备',
    align: "center",
    dataIndex: 'retention5Dev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.retention5Dev > b.retention5Dev ? 1:-1;
    },
    customRender: ({ text }) => {
      return text + "%"
    },
  },
  {
    title: '6日留存-设备',
    align: "center",
    dataIndex: 'retention6Dev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.retention6Dev > b.retention6Dev ? 1:-1;
    },
    customRender: ({ text }) => {
      return text + "%"
    },
  },
  {
    title: '7日留存-设备',
    align: "center",
    dataIndex: 'retention7Dev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.retention7Dev > b.retention7Dev ? 1:-1;
    },
    customRender: ({ text }) => {
      return text + "%"
    },
  },
  {
    title: 'ltv1',
    align: "center",
    dataIndex: 'ltv1',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.ltv1 > b.ltv1 ? 1:-1;
    },
  },
  {
    title: 'ltv2',
    align: "center",
    dataIndex: 'ltv2',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.ltv2 > b.ltv2 ? 1:-1;
    },
  },
  {
    title: 'ltv3',
    align: "center",
    dataIndex: 'ltv3',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.ltv3 > b.ltv3 ? 1:-1;
    },
  },
  {
    title: 'ltv4',
    align: "center",
    dataIndex: 'ltv4',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.ltv4 > b.ltv4 ? 1:-1;
    },
  },
  {
    title: 'ltv5',
    align: "center",
    dataIndex: 'ltv5',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.ltv5 > b.ltv5 ? 1:-1;
    },
  },
  {
    title: 'ltv6',
    align: "center",
    dataIndex: 'ltv6',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.ltv6 > b.ltv6 ? 1:-1;
    },
  },
  {
    title: 'ltv7',
    align: "center",
    dataIndex: 'ltv7',
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.ltv7 > b.ltv7 ? 1:-1;
    },
  },
  {
    title: 'ltv1-设备',
    align: "center",
    dataIndex: 'ltv1Dev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.ltv1Dev > b.ltv1Dev ? 1:-1;
    },
  },
  {
    title: 'ltv2-设备',
    align: "center",
    dataIndex: 'ltv2Dev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.ltv2Dev > b.ltv2Dev ? 1:-1;
    },
  },
  {
    title: 'ltv3-设备',
    align: "center",
    dataIndex: 'ltv3Dev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.ltv3Dev > b.ltv3Dev ? 1:-1;
    },
  },
  {
    title: 'ltv4-设备',
    align: "center",
    dataIndex: 'ltv4Dev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.ltv4Dev > b.ltv4Dev ? 1:-1;
    },
  },
  {
    title: 'ltv5-设备',
    align: "center",
    dataIndex: 'ltv5Dev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.ltv5Dev > b.ltv5Dev ? 1:-1;
    },
  },
  {
    title: 'ltv6-设备',
    align: "center",
    dataIndex: 'ltv6Dev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.ltv6Dev > b.ltv6Dev ? 1:-1;
    },
  },
  {
    title: 'ltv7-设备',
    align: "center",
    dataIndex: 'ltv7Dev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.id == "合计" && c == "descend") return -1;
      if(a.id == "合计" && c == "ascend") return 1;
      return a.ltv7Dev > b.ltv7Dev ? 1:-1;
    },
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