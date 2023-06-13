import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '日期',
    align: "center",
    dataIndex: 'roiDate'
  },
  {
    title: '总消耗',
    align: "center",
    dataIndex: 'costMoney'
  },
  {
    title: '安卓消耗',
    align: "center",
    dataIndex: 'androidCostMoney'
  },
  {
    title: 'IOS消耗',
    align: "center",
    dataIndex: 'iosCostMoney'
  },
  {
    title: '曝光量',
    align: "center",
    dataIndex: 'exhibition'
  },
  {
    title: '点击量',
    align: "center",
    dataIndex: 'click'
  },
  {
    title: '点击率',
    align: "center",
    dataIndex: 'clickRate'
  },
  {
    title: '点击均价',
    align: "center",
    dataIndex: 'clickPrice'
  },
  {
    title: '下载',
    align: "center",
    dataIndex: 'download'
  },
  {
    title: '点击下载率',
    align: "center",
    dataIndex: 'downloadRate'
  },
  {
    title: '下载均价',
    align: "center",
    dataIndex: 'downloadPrice'
  },
  {
    title: '激活量',
    align: "center",
    dataIndex: 'countActive'
  },
  {
    title: '注册量',
    align: "center",
    dataIndex: 'countUser'
  },
  {
    title: '下载注册率',
    align: "center",
    dataIndex: 'downloadRegRate'
  },
  {
    title: '激活注册率',
    align: "center",
    dataIndex: 'activeRegRate'
  },
  {
    title: '注册单价',
    align: "center",
    dataIndex: 'regUnitPrice'
  },
  {
    title: '新增付费数',
    align: "center",
    dataIndex: 'costCount'
  },
  {
    title: '新增付费率',
    align: "center",
    dataIndex: 'costProbability'
  },
  {
    title: '新增付费',
    align: "center",
    dataIndex: 'addCostPrice'
  },
  {
    title: '总消费',
    align: "center",
    dataIndex: 'aliveMoney'
  },
  {
    title: '付费单价',
    align: "center",
    dataIndex: 'costUnitPrice'
  },
  {
    title: '新增ARPU',
    align: "center",
    dataIndex: 'firstArpu'
  },
  {
    title: '首日ROI',
    align: "center",
    dataIndex: 'firstROI'
  },
  {
    title: '回收率',
    align: "center",
    dataIndex: 'recoveryRate'
  },
  {
    title: '直播间观看人数',
    align: "center",
    dataIndex: 'lubanLiveEnterCnt'
  },
  {
    title: '超过1分钟观看数',
    align: "center",
    dataIndex: 'liveWatchOneMinuteCount'
  },
  {
    title: '直播间打赏次数',
    align: "center",
    dataIndex: 'lubanLiveGiftCnt'
  },
  {
    title: '直播间评论数',
    align: "center",
    dataIndex: 'lubanLiveCommentCnt'
  },
  {
    title: '直播间关注数',
    align: "center",
    dataIndex: 'lubanLiveFollowCnt'
  },
  {
    title: '直播间加入粉丝团',
    align: "center",
    dataIndex: 'liveFansClubJoinCnt'
  },
  {
    title: '直播间分享数',
    align: "center",
    dataIndex: 'lubanLiveShareCnt'
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "游戏",
    field: 'gameId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "子游戏",
    field: 'subGameId',
    component: 'Input',
    colProps: {span: 6},
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '游戏',
    field: 'gameId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入游戏!'},
             ];
    },
  },
  {
    label: '子游戏',
    field: 'subGameId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入子游戏!'},
             ];
    },
  },
  {
    label: '渠道游戏包',
    field: 'pkgId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入渠道游戏包!'},
             ];
    },
  },
  {
    label: '所属渠道',
    field: 'channelId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入所属渠道!'},
             ];
    },
  },
  {
    label: '广告ID',
    field: 'dealId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入广告ID!'},
             ];
    },
  },
  {
    label: '用户ID',
    field: 'userId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入用户ID!'},
             ];
    },
  },
  {
    label: '角色ID',
    field: 'roleId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入角色ID!'},
             ];
    },
  },
  {
    label: '角色名',
    field: 'roleName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入角色名!'},
             ];
    },
  },
  {
    label: '角色等级',
    field: 'roleLevel',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入角色等级!'},
             ];
    },
  },
  {
    label: '服务器ID',
    field: 'serverId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入服务器ID!'},
             ];
    },
  },
  {
    label: '在线时长',
    field: 'onlineTime',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入在线时长!'},
             ];
    },
  },
  {
    label: '注册时间',
    field: 'createTime',
    component: 'DatePicker',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入注册时间!'},
             ];
    },
  },
  {
    label: '最新登录时间',
    field: 'aliveTime',
    component: 'DatePicker',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入最新登录时间!'},
             ];
    },
  },
  {
    label: '渠道子账号id',
    field: 'channelSubAccountId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入渠道子账号id!'},
             ];
    },
  },
  {
    label: '服务器名',
    field: 'serverName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入服务器名!'},
             ];
    },
  },
  {
    label: '注册-设备',
    field: 'createDevice',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入注册-设备!'},
             ];
    },
  },
	// TODO 主键隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
];
