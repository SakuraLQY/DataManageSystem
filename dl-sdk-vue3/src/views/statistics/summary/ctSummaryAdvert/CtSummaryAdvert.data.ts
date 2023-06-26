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
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.costMoney > b.costMoney ? 1:-1;
    },
  },
  {
    title: '广告id',
    align: "center",
    dataIndex: 'dealId',
    slots: { customRender: 'dealId' },
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.dealId > b.dealId ? 1:-1;
    },
  },
  {
    title: '渠道游戏包名',
    align: "center",
    dataIndex: 'pkgName',
  },
  {
    title: '广告名',
    align: "center",
    dataIndex: 'dealName',
    defaultHidden: true,
  },
  {
    title: '渠道名',
    align: "center",
    dataIndex: 'channelName',
  },
  {
    title: '激活数',
    align: "center",
    dataIndex: 'countActive',
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.countActive > b.countActive ? 1:-1;
    },
  },
  {
    title: '激活设备数',
    align: "center",
    dataIndex: 'countActiveDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.countActiveDev > b.countActiveDev ? 1:-1;
    },
  },
  {
    title: '注册数',
    align: "center",
    dataIndex: 'countUser',
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.countUser > b.countUser ? 1:-1;
    },
  },
  {
    title: '注册设备数',
    align: "center",
    dataIndex: 'countUserDev',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.countUserDev > b.countUserDev ? 1:-1;
    },
  },
  {
    title: '激活设备数',
    align: "center",
    dataIndex: 'countActiveDev',
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.countActiveDev > b.countActiveDev ? 1:-1;
    },
  },
  {
    title: '激活注册率',
    align: "center",
    dataIndex: 'countActiveUserRate',
    defaultHidden: true,
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.countActiveUserRate > b.countActiveUserRate ? 1:-1;
    },
  },
  {
    title: '有效注册数',
    align: "center",
    dataIndex: 'countValidUser',
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.countValidUser > b.countValidUser ? 1:-1;
    },
  },
  {
    title: '有效注册率',
    align: "center",
    dataIndex: 'countValidUserCountUserRate',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.countValidUserCountUserRate > b.countValidUserCountUserRate ? 1:-1;
    },
  },
  {
    title: '新增付费人数',
    align: "center",
    dataIndex: 'firstPayuser',
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.firstPayuser > b.firstPayuser ? 1:-1;
    },
  },
  {
    title: '新增付费金额',
    align: "center",
    dataIndex: 'firstMoney',
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.firstMoney > b.firstMoney ? 1:-1;
    },
  },
  {
    title: '新增付费率',
    align: "center",
    dataIndex: 'firstMoneyFirstUserRate',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.firstMoneyFirstUserRate > b.firstMoneyFirstUserRate ? 1:-1;
    },
  },
  {
    title: '新增ARPU',
    align: "center",
    dataIndex: 'firstArpu',
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.firstArpu > b.firstArpu ? 1:-1;
    },
  },
  {
    title: '新增ARRPU',
    align: "center",
    dataIndex: 'firstArppu',
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.firstArppu > b.firstArppu ? 1:-1;
    },
  },
  {
    title: '老用户付费数',
    align: "center",
    dataIndex: 'oldUserPayuser',
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.oldUserPayuser > b.oldUserPayuser ? 1:-1;
    },
  },
  {
    title: '老用户付费金额',
    align: "center",
    dataIndex: 'oldUserMoney',
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.oldUserMoney > b.oldUserMoney ? 1:-1;
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
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.oldUserPayRate > b.oldUserPayRate ? 1:-1;
    },
  },
  {
    title: '老用户Arpu',
    align: "center",
    dataIndex: 'oldUserArpu',
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.oldUserArpu > b.oldUserArpu ? 1:-1;
    },
  },
  {
    title: '老用户Arppu',
    align: "center",
    dataIndex: 'oldUserArppu',
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.oldUserArppu > b.oldUserArppu ? 1:-1;
    },
  },
  {
    title: '累积付金费额',
    align: "center",
    dataIndex: 'totalMoney',
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.totalMoney > b.totalMoney ? 1:-1;
    },
  },
  {
    title: 'DAU',
    align: "center",
    dataIndex: 'countDau',
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.countDau > b.countDau ? 1:-1;
    },
  },
  {
    title: '活跃付费人数',
    align: "center",
    dataIndex: 'alivePayuser',
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.alivePayuser > b.alivePayuser ? 1:-1;
    },
  },
  {
    title: '活跃付费金额',
    align: "center",
    dataIndex: 'aliveMoney',
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.aliveMoney > b.aliveMoney ? 1:-1;
    },
  },
  {
    title: 'ARPU',
    align: "center",
    dataIndex: 'arpu',
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.arpu > b.arpu ? 1:-1;
    },
  },
  {
    title: 'ARPPU',
    align: "center",
    dataIndex: 'arppu',
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.arppu > b.arppu ? 1:-1;
    },
  },
  {
    title: '总付费率',
    align: "center",
    dataIndex: 'alivePayuserDauRate',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.alivePayuserDauRate > b.alivePayuserDauRate ? 1:-1;
    },
  },  
  {
    title: '次日留存',
    align: "center",
    dataIndex: 'retention2',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.retention2 > b.retention2 ? 1:-1;
    },
  },
  {
    title: '3日留存',
    align: "center",
    dataIndex: 'retention3',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.retention3 > b.retention3 ? 1:-1;
    },
  },
  {
    title: '4日留存',
    align: "center",
    dataIndex: 'retention4',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.retention4 > b.retention4 ? 1:-1;
    },
  },
  {
    title: '5日留存',
    align: "center",
    dataIndex: 'retention5',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.retention5 > b.retention5 ? 1:-1;
    },
  },
  {
    title: '6日留存',
    align: "center",
    dataIndex: 'retention6',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.retention6 > b.retention6 ? 1:-1;
    },
  },
  {
    title: '7日留存',
    align: "center",
    dataIndex: 'retention7',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.retention7 > b.retention7 ? 1:-1;
    },
  },
  {
    title: 'ltv1',
    align: "center",
    dataIndex: 'ltv1',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.ltv1 > b.ltv1 ? 1:-1;
    },
  },
  {
    title: 'ltv2',
    align: "center",
    dataIndex: 'ltv2',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.ltv2 > b.ltv2 ? 1:-1;
    },
  },
  {
    title: 'ltv3',
    align: "center",
    dataIndex: 'ltv3',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.ltv3 > b.ltv3 ? 1:-1;
    },
  },
  {
    title: 'ltv4',
    align: "center",
    dataIndex: 'ltv4',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.ltv4 > b.ltv4 ? 1:-1;
    },
  },
  {
    title: 'ltv5',
    align: "center",
    dataIndex: 'ltv5',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.ltv5 > b.ltv5 ? 1:-1;
    },
  },
  {
    title: 'ltv6',
    align: "center",
    dataIndex: 'ltv6',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.ltv6 > b.ltv6 ? 1:-1;
    },
  },
  {
    title: 'ltv7',
    align: "center",
    dataIndex: 'ltv7',
    defaultHidden: true,
    sorter: (a:any,b:any,c)=>{
      if(a.day == "合计" && c == "descend") return -1;
      if(a.day == "合计" && c == "ascend") return 1;
      return a.ltv7 > b.ltv7 ? 1:-1;
    },
  }
];