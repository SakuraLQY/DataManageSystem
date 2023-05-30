import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { faList } from './OpKsPutAccount.api';

function getSubName(subGameId, subGames){
  for(let subGame of subGames){
    if(subGame.id == subGameId){
      return subGame.gameName;
    }
  }
}

//列表数据
export const columns: BasicColumn[] = [
  {
    title: '账号等级',
    align: "center",
    dataIndex: 'levelId_dictText',
  },
  {
    title: '账号id',
    align: "center",
    dataIndex: 'id',
    slots: { customRender: 'id' },
  },
  {
    title: '账号昵称',
    align: "center",
    dataIndex: 'nickName'
  },
  {
    title: '账号',
    align: "center",
    dataIndex: 'account',
  },
  {
    title: '账号密码',
    align: "center",
    dataIndex: 'password',
    slots:{customRender:'password'}
  },  
  {
    title: '子游戏',
    align: "center",
    dataIndex: 'subGameIds',
    customRender:({text}) =>{
      let games = JSON.parse(text);
      let str = "";
      for(let game of games){
        str += "游戏：" + faList.value[game.gameId].gameName + "<=> 子游戏：" + getSubName(game.subGameId,faList.value[game.gameId].list)
      }
      return str;
    }
  },
  {
    title: '账号所属应用',
    align: "center",
    dataIndex: 'appId_dictText'
  },
  {
    title: '广告主ID',
    align: "center",
    dataIndex: 'advertiserId'
  },
  {
    title: '账号归属用户',
    align: "center",
    dataIndex: 'putUser_dictText'
  },
  {
    title: '账号状态',
    align: "center",
    dataIndex: 'state_dictText',
    slots: { customRender: 'state' },
  },
  {
    title: '账号备注',
    align: "center",
    dataIndex: 'accountDesc'
  },
  {
    title: '授权时间',
    align: "center",
    dataIndex: 'authorizeTime',
    customRender:({text}) =>{
      return !text?"":(text.length>10?text.substr(0,10):text);
    },
  },
];



//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "账号等级，1级为一级账号，2级为二级账号",
    field: 'levelId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "账号",
    field: 'account',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "账号昵称",
    field: 'nickName',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "账号密码",
    field: 'password',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "子游戏id",
    field: 'subGameIds',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
  {
    label: "账号状态",
    field: 'state',
    component: 'Input',
    colProps: {span: 6},
  },
];

