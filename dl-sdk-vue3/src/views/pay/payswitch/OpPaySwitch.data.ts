import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '游戏',
    align: "center",
    dataIndex: 'gameId_dictText'
  },
  {
    title: '子游戏',
    align: "center",
    dataIndex: 'subGameId_dictText'
  },
  {
    title: '渠道游戏包',
    align: "center",
    dataIndex: 'pkgId_dictText'
  },
  {
    title: '游戏版本',
    align: "center",
    dataIndex: 'gameVersion'
  },
  {
    title: '游戏构建',
    align: "center",
    dataIndex: 'gameBuild'
  },
  {
    title: '默认支付',
    align: "center",
    dataIndex: 'defaultPay',
    customRender: ({ text }) => {
      let chooses = "";
      let values = text.split(",");
      for(let val of values) {
        for(let option of options){
          if(option.value == val){
            chooses += option.label + ","
          }
        }
      }
      return chooses.substring(0, chooses.length -1);
    }
  },
  {
    title: '非默认支付',
    align: "center",
    dataIndex: 'noDefaultPay',
    customRender: ({ text }) => {
      let chooses = "";
      let values = text.split(",");
      for(let val of values) {
        for(let option of options){
          if(option.value == val){
            chooses += option.label + ","
          }
        }
      }
      return chooses.substring(0, chooses.length -1);
    }
  },
  {
    title: '单笔订单金额',
    align: "center",
    dataIndex: 'orderMoney'
  },
  {
    title: '充值次数',
    align: "center",
    dataIndex: 'rechargeTimes'
  },
  {
    title: '累计充值',
    align: "center",
    dataIndex: 'totalMoney'
  },
  {
    title: '时间范围',
    align: "center",
    dataIndex: 'rangeTime',
    slots:{
      customRender: 'rangeTime'
    }
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "游戏id",
    field: 'gameId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "op_sub_game,game_name,id"
    },
    colProps: {span: 6},
  },
  {
    label: "游戏版本",
    field: 'gameVersion',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "游戏构建",
    field: 'gameBuild',
    component: 'Input',
    colProps: {span: 6},
  },
];

export const options = [
  {
    label: '支付宝(sdk)',
    value: '1'
  },
  {
    label: '支付宝(网页)',
    value: '2'
  },
  {
    label: '微信(网页)',
    value: '3'
  },
  {
    label: '微信支付(现在支付-sdk)',
    value: '4'
  },
  {
    label: '微信支付(现在支付-wap)',
    value: '5'
  },
  {
    label: '平台币支付',
    value: '6'
  },
  {
    label: '应用宝支付',
    value: '7'
  },
  {
    label: 'ios正版支付(安卓勿选)',
    value: '8'
  },
]
