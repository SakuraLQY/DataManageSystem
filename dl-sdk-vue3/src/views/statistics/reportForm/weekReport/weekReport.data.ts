import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const dataColumns = [
  {
    title: '游戏名',
    dataIndex: 'firstGroup',
    colSpan: 1 
  },
  {
    title: '渠道名称',
    dataIndex: 'gameNickName',
    colSpan: 2 // 合并两列表头
  },
  {
    title: '渠道名称2',
    dataIndex: 'channelNickName',
    colSpan: 0 // 合并两列表头
  },
  {
    title: '现金消耗',
    dataIndex: 'costMoney',
    colSpan: 1
  },
  {
    title: '流水',
    dataIndex: 'aliveMoney',
    colSpan: 1
  },
  {
    title: '注册',
    dataIndex: 'countUser',
    colSpan: 1
  },
  {
    title: 'DAU',
    dataIndex: 'countDau',
    colSpan: 1  
  },
  {
    title: '单价',
    dataIndex: 'regPrice',
    colSpan: 1  
  },
  {
    title: '首日付费',
    dataIndex: 'firstMoney',
    colSpan: 1  
  },
  {
    title: '新增付费率',
    dataIndex: 'firstPayRate',
    colSpan: 1,
    customRender: ({ text }) => {
      return text + "%"
    },  
  },
  {
    title: '首日ROI',
    dataIndex: 'firstPayRate',
    colSpan: 1,
    customRender: ({ text }) => {
      return text + "%"
    },   
  },
  {
    title: '累计ROI',
    dataIndex: 'totalRoi',
    colSpan: 1,
    customRender: ({ text }) => {
      return text + "%"
    },   
  },
  {
    title: '活跃ROI',
    dataIndex: 'aliveRoi',
    colSpan: 1,
    customRender: ({ text }) => {
      return text + "%"
    },   
  },
  {
    title: '累亏',
    dataIndex: 'loss',
    colSpan: 1  
  },
  {
    title: '利润',
    dataIndex: 'profit',
    colSpan: 1  
  },
  
];


export const marketColumns = [
  {
    title: '日期',
    dataIndex: 'day',
    colSpan: 1 
  },
  {
    title: '消耗',
    dataIndex: 'costMoney',
    colSpan: 1 
  },
  {
    title: '新增',
    dataIndex: 'countUser',
    colSpan: 1 
  },
  {
    title: 'DAU',
    dataIndex: 'countDau',
    colSpan: 1 
  },
  {
    title: '新增付费',
    dataIndex: 'firstMoney',
    colSpan: 1 
  },
  {
    title: '活跃付费',
    dataIndex: 'aliveMoney',
    colSpan: 1 
  },
  {
    title: '分成所得',
    dataIndex: 'divideMoney',
    colSpan: 1 
  },
]


export const detailColumns = [
  {
    title: '游戏名',
    dataIndex: 'firstGroup',
    colSpan: 1,
    customCell: (_, index) => {
      if (index%4 == 0) { 
        return { rowSpan: 4 };
      }else{
        return { rowSpan: 0 };
      }
    }
  },
  {
    title: '渠道',
    dataIndex: 'secondGroup',
    colSpan: 1,
    customCell: (_, index) => {
      if (index%4 == 0) { 
        return { rowSpan: 4 };
      }else{
        return { rowSpan: 0 };
      }
    }
  },
  {
    title: '时间',
    dataIndex: 'day',
    colSpan: 1 
  },
  {
    title: '消耗',
    dataIndex: 'costMoney',
    colSpan: 1 
  },
  {
    title: '注册数',
    dataIndex: 'countUser',
    colSpan: 1 
  },
  {
    title: '首日roi',
    dataIndex: 'firstDayRoi',
    colSpan: 1,
    customRender: ({ text }) => {
      return text + "%"
    },   
  },
  {
    title: '首周roi',
    dataIndex: 'firstWeekRoi',
    colSpan: 1,
    customRender: ({ text }) => {
      return text + "%"
    },   
  },
  {
    title: '累计roi',
    dataIndex: 'totalRoi',
    colSpan: 1,
    customRender: ({ text }) => {
      return text + "%"
    },   
  },
  {
    title: '首日',
    dataIndex: 'ltv1',
    colSpan: 1 
  },
  {
    title: '2日',
    dataIndex: 'ltv2',
    colSpan: 1 
  },
  {
    title: '3日',
    dataIndex: 'ltv3',
    colSpan: 1 
  },
  {
    title: '4日',
    dataIndex: 'ltv4',
    colSpan: 1 
  },
  {
    title: '5日',
    dataIndex: 'ltv5',
    colSpan: 1 
  },
  {
    title: '6日',
    dataIndex: 'ltv6',
    colSpan: 1 
  },
  {
    title: '7日',
    dataIndex: 'ltv7',
    colSpan: 1 
  },
  {
    title: '15日',
    dataIndex: 'ltv15',
    colSpan: 1 
  },

]