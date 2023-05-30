import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '子游戏ID',
    align: "center",
    dataIndex: 'id',
  },
  {
    title: '子游戏名',
    align: "center",
    dataIndex: 'gameName',
  },
  {
    title: '游戏名(游戏ID)',
    align: "center",
    dataIndex: 'gameId',
    slots: { customRender: 'gameId' },
  },
  {
    title: '子游戏类型',
    align: "center",
    dataIndex: 'gameType',
    customRender: ({ text }) => {
      return render.renderDict(text, 'sub_game_type');
    },
  },
  {
    title: '充值发货url',
    align: "center",
    dataIndex: 'deliverUrl'
  },
  {
    title: '安装包-下载链接(IOS)',
    align: "center",
    dataIndex: 'game2PkgUrl'
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "游戏名",
    field: 'faGameName',
    component: 'JDictSelectTag',
    colProps: {span: 6},
  },
  {
    label: "子游戏名",
    field: 'gameName',
    component: 'JDictSelectTag',
    componentProps:{
    },
    colProps: {span: 6},
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '游戏名',
    field: 'faGameName',
    component: 'JDictSelectTag',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请选择游戏名!'},
             ];
    },
  },
  {
    label: '厂商',
    field: 'vendorId',
    component: 'JSearchSelect',
    componentProps:{
      dict: "open_gateway.op_vendor,vendor_name,id"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入渠道类型!'},
             ];
    },
  },
  {
    label: '子游戏名',
    field: 'gameName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入子游戏名!'},
             ];
    },
  },
  {
    label: '子游戏类型',
    field: 'gameType',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "sub_game_type"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请选择子游戏类型!'},
             ];
    },
  },
  {
    label: '充值发货url',
    field: 'deliverUrl',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入充值发货url!'},
             ];
    },
  },
  {
    label: '安装包-下载链接(IOS)',
    field: 'game2PkgUrl',
    component: 'Input',
  },
  {
    label: '是否开启平台币',
    field: 'platformCurrencySwitch',
    component: 'JSwitch',
    componentProps:{
    },
  },
  {
    label: '折扣配置',
    field: 'platformCurrencyDiscount',
    component: 'InputNumber',
  },
  {
    label: '包id配置',
    field: 'platformCurrencyPkgConfig',
    component: 'Input',
  },
  {
    label: '包名称',
    field: 'packName',
    component: 'Input',
  },
  {
    label: '绑定手机开关',
    field: 'phoneSwitch',
    component: 'JSwitch',
    componentProps:{
    },
  },
  {
    label: '隐私政策开关',
    field: 'privacySwitch',
    component: 'JSwitch',
    componentProps:{
    },
  },
  {
    label: '实名验证窗口',
    field: 'idAuthSwitch',
    component: 'JSwitch',
    componentProps:{
    },
  },
  {
    label: '是否调用接口',
    field: 'idAuthApi',
    component: 'JSwitch',
    componentProps:{
    },
  },
  {
    label: '接入中宣部',
    field: 'officialAntiIndulgeSwitch',
    component: 'JSwitch',
    componentProps:{
    },
  },
  {
    label: '备案识别码',
    field: 'officialBizId',
    component: 'Input',
  },
  {
    label: '防沉迷开关',
    field: 'antiIndulgeSwitch',
    component: 'JSwitch',
    componentProps:{
    },
  },
  {
    label: '游戏key',
    field: 'gameKey',
    component: 'InputTextArea',
  },
  {
    label: '备注',
    field: 'descs',
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
