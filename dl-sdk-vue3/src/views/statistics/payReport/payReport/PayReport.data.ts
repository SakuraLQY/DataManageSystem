import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { values } from 'xe-utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '游戏',
    align: 'center',
    dataIndex: 'gameName',
  },
  {
    title: '广告id',
    align: 'center',
    dataIndex: 'dealId',
  },
  {
    title: '用户id',
    align: 'center',
    dataIndex: 'userId',
  },
  {
    title: '用户名',
    align: 'center',
    dataIndex: 'userName',
  },
  {
    title: '区服id',
    align: 'center',
    dataIndex: 'serverId',
  },
  {
    title: '银行发货状态',
    align: 'center',
    dataIndex: 'bankStatus',
    ifShow: false,
    slots: { customRender: 'bankStatus' },
  },
  {
    title: '游戏发货状态',
    align: 'center',
    dataIndex: 'gameStatus',
    ifShow: false,
    slots: { customRender: 'gameStatus' },
  },
  {
    title: '订单id',
    align: 'center',
    dataIndex: 'orderId',
    width: 300,
  },
  {
    title: 'cp订单号',
    align: 'center',
    dataIndex: 'gameOrderId',
  },
  {
    title: '渠道订单号',
    align: 'center',
    dataIndex: 'bankOrderId',
  },
  {
    title: '充值金额',
    align: 'center',
    dataIndex: 'money',
  },
  {
    title: '充值方式',
    align: 'center',
    dataIndex: 'bankType',
    slots: { customRender: 'bank_type' },
  },
  {
    title: '充值时间',
    align: 'center',
    dataIndex: 'openTime',
  },
  {
    title: '充值类型',
    align: 'center',
    dataIndex: 'orderType',
    slots: { customRender: 'order_type' },
  },
  {
    title: '发货重试次数',
    align: 'center',
    dataIndex: 'gameDeliverRetry',
    ifShow: false,
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '银行订单号',
    field: 'orderId',
    component: 'Input',
  },
  {
    label: 'CP订单号',
    field: 'gameOrderId',
    component: 'Input',
  },
  {
    label: 'CP透传参数',
    field: 'gameData',
    component: 'Input',
  },
  {
    label: '游戏发货次数',
    field: 'gameDeliverRetry',
    component: 'Input',
  },
  {
    label: '游戏发货时间',
    field: 'openTime',
    component: 'Input',
  },
  {
    label: '游戏发货状态',
    field: 'gameStatus',
    component: 'Input',
  },
];

export const type = [
  {
    label: '充值成功订单',
    value: 1,
  },
  {
    label: '未充值成功订单',
    value: 0,
  },
];

export const orderTypes = [
  {
    label: '游戏充值',
    value: 0,
  },
  {
    label: '平台币充值',
    value: 2,
  },
];

export const bankTypes = [
  {
    label: '支付宝（网页）',
    value: 2,
  },
  {
    label: '微信（网页）',
    value: 3,
  },
  {
    label: 'IOS正版支付',
    value: 8,
  },
];
export const phoneOs = [
  {
    label: '安卓',
    value: 0,
  },
  {
    label: 'IOS',
    value: 1,
  },
];

export const sendStatus = [
  {
    label: '游戏发货失败',
    value: 1,
  },
  {
    label: '未充值成功',
    value: 2,
  },
];
