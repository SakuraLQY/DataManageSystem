import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { faList } from './OpReport.api';

function getSubName(subGameId, subGames) {
  for (let subGame of subGames) {
    if (subGame.id == subGameId) {
      return subGame.gameName;
    }
  }
}

//列表数据
export const columns: any = [
  {
    title: '账号昵称',
    align: "center",
    dataIndex: 'nickName'
  },
  {
    title: '账号',
    align: "center",
    dataIndex: 'account'
  },
  {
    title: '游戏',
    align: "center",
    dataIndex: 'subGameIds',
    customRender: ({ text }) => {
      let games = JSON.parse(text);
      let str = "";
      if(games==null){
        return str;
      }
      for (let game of games) {
        str += "游戏：" + faList.value[game.gameId].gameName + "<=> 子游戏：" + getSubName(game.subGameId, faList.value[game.gameId].list)
      }
      return str;
    }
  },
  {
    title: '渠道',
    align: "center",
    dataIndex: 'channelName'
  },
  // {
  //   title: '充值金额',
  //   align: "center",
  //   dataIndex: 'payMoney'
  // },
  // {
  //   title: '转出金额',
  //   align: "center",
  //   dataIndex: 'outMoney'
  // },
  {
    title: '投放消耗',
    align: "center",
    dataIndex: 'outCostMoney'
  },
  {
    title: '账号余额',
    align: "center",
    dataIndex: 'surplusAmount'
  },
  {
    title: '曝光',
    align: "center",
    dataIndex: 'exhibition'
  },
  {
    title: '下载',
    align: "center",
    dataIndex: 'download'
  },
  {
    title: '负责人',
    align: "center",
    dataIndex: 'principalUser'
  },
  {
    title: '创建时间',
    align: "center",
    dataIndex: 'createTime',
    customRender: ({ text }) => {
      return !text ? "" : (text.length > 10 ? text.substr(0, 10) : text);
    },
  },
  {
    title: '更新时间',
    align: "center",
    dataIndex: 'updateTime',
    customRender: ({ text }) => {
      return !text ? "" : (text.length > 10 ? text.substr(0, 10) : text);
    },
  },
  {
    title: '账单',
    align: "center",
    dataIndex: 'id',
    slots:{customRender:'bill_data'}
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "游戏项目",
    field: 'gameId',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: "游戏名称",
    field: 'subGameId',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: "渠道游戏包",
    field: 'pkgId',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: "渠道名称",
    field: 'channelId',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: "渠道类型",
    field: 'channelTypeId',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: "二级渠道",
    field: 'channelSubAccountId',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: "投放账号",
    field: 'putAccount',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: "账号人员",
    field: 'createUser',
    component: 'Input',
    colProps: { span: 6 },
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '游戏项目',
    field: 'gameId',
    component: 'InputNumber',
  },
  {
    label: '游戏名称',
    field: 'subGameId',
    component: 'InputNumber',
  },
  {
    label: '渠道游戏包',
    field: 'pkgId',
    component: 'InputNumber',
  },
  {
    label: '渠道名称',
    field: 'channelId',
    component: 'InputNumber',
  },
  {
    label: '渠道类型',
    field: 'channelTypeId',
    component: 'InputNumber',
  },
  {
    label: '二级渠道',
    field: 'subChannelAccountId',
    component: 'InputNumber',
  },
  {
    label: '投放账号',
    field: 'putAccount',
    component: 'InputNumber',
  },
  {
    label: '账号人员',
    field: 'createUser',
    component: 'Input',
  },
  {
    label: '账号昵称',
    field: 'nickName',
    component: 'Input',
  },
  {
    label: '账号',
    field: 'account',
    component: 'Input',
  },
  {
    label: '游戏',
    field: 'gameName',
    component: 'Input',
  },
  {
    label: '渠道',
    field: 'channelName',
    component: 'Input',
  },
  {
    label: '充值金额',
    field: 'payMoney',
    component: 'InputNumber',
  },
  {
    label: '转出金额',
    field: 'outMoney',
    component: 'InputNumber',
  },
  {
    label: '投放消耗',
    field: 'outCostMoney',
    component: 'InputNumber',
  },
  {
    label: '账号余额',
    field: 'surplusAmount',
    component: 'InputNumber',
  },
  {
    label: '曝光',
    field: 'exhibition',
    component: 'InputNumber',
  },
  {
    label: '下载',
    field: 'download',
    component: 'InputNumber',
  },
  {
    label: '负责人',
    field: 'principalUser',
    component: 'Input',
  },
  {
    label: '创建时间',
    field: 'createTime',
    component: 'DatePicker',
  },
  {
    label: '更新时间',
    field: 'updateTime',
    component: 'DatePicker',
  },
  {
    label: '账单',
    field: 'bill',
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
