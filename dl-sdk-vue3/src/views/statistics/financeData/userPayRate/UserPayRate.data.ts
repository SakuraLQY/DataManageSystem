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
  },
  {
    title: '渠道名',
    align: "center",
    dataIndex: 'channelName',
  },
  {
    title: '广告名',
    align: "center",
    dataIndex: 'dealName',
  },
  {
    title: '注册数',
    align: "center",
    dataIndex: 'countUser',
  },
  {
    title: '首日付费率',
    align: "center",
    dataIndex: 'userPayRate1',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '首日arppu',
    align: "center",
    dataIndex: 'arppu1',
    sorter: true,
  },
  {
    title: '次日付费率',
    align: "center",
    dataIndex: 'userPayRate2',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '次日arppu',
    align: "center",
    dataIndex: 'arppu2',
    sorter: true,
  },
  {
    title: '3日付费率',
    align: "center",
    dataIndex: 'userPayRate3',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '3日arppu',
    align: "center",
    dataIndex: 'arppu3',
    sorter: true,
  },
  {
    title: '4日付费率',
    align: "center",
    dataIndex: 'userPayRate4',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '4日arppu',
    align: "center",
    dataIndex: 'arppu4',
    sorter: true,
  },
  {
    title: '5日付费率',
    align: "center",
    dataIndex: 'userPayRate5',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '5日arppu',
    align: "center",
    dataIndex: 'arppu5',
    sorter: true,
  },
  {
    title: '6日付费率',
    align: "center",
    dataIndex: 'userPayRate6',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '6日arppu',
    align: "center",
    dataIndex: 'arppu6',
    sorter: true,
  },
  {
    title: '7日付费率',
    align: "center",
    dataIndex: 'userPayRate7',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '7日arppu',
    align: "center",
    dataIndex: 'arppu7',
    sorter: true,
  },
  {
    title: '15日付费率',
    align: "center",
    dataIndex: 'userPayRate15',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '15日arppu',
    align: "center",
    dataIndex: 'arppu15',
    sorter: true,
  },
  {
    title: '30日付费率',
    align: "center",
    dataIndex: 'userPayRate30',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '30日arppu',
    align: "center",
    dataIndex: 'arppu30',
    sorter: true,
  },
  {
    title: '45日付费率',
    align: "center",
    dataIndex: 'userPayRate45',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '45日arppu',
    align: "center",
    dataIndex: 'arppu45',
    sorter: true,
  },
  {
    title: '60日付费率',
    align: "center",
    dataIndex: 'userPayRate60',
    customRender: ({ text }) => {
      return text + "%"
    },
    sorter: true,
  },
  {
    title: '60日arppu',
    align: "center",
    dataIndex: 'arppu60',
    sorter: true,
  },
];