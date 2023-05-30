import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '定向包ID',
    align: "center",
    dataIndex: 'audiencePackageId'
  },
  {
    title: '定向包名称',
    align: "center",
    dataIndex: 'name'
  },
  {
    title: '定向包描述',
    align: "center",
    dataIndex: 'description'
  },
  {
    title: '定向包类型',
    align: "center",
    dataIndex: 'landingType_dictText'
  },
  {
    title: '投放账号',
    align: "center",
    dataIndex: 'accountId_dictText'
  },
  {
    title: '广告投放范围',
    align: "center",
    dataIndex: 'deliveryRange',
    slots: { customRender: 'deliveryRange' },
  },
  {
    title: '创建用户',
    align: "center",
    dataIndex: 'createBy'
  },
  {
    title: '创建时间',
    align: "center",
    dataIndex: 'createTime',
    customRender:({text}) =>{
      return !text?"":text;
    },
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "定向包名称",
    field: 'name',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "定向包描述",
    field: 'description',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "定向包类型",
    field: 'landingType',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "投放账号",
    field: 'accountId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "open_gateway.op_put_account where channel_Id = '4' and state = '1' and level_id = '2',nick_name,id"
    },
    colProps: {span: 6},
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '定向包ID',
    field: 'audiencePackageId',
    component: 'InputNumber',
  },
  {
    label: '定向包名称',
    field: 'name',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入定向包名称!'},
             ];
    },
  },
  {
    label: '定向包描述',
    field: 'description',
    component: 'InputTextArea',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入定向包描述!'},
             ];
    },
  },
  {
    label: '定向包类型',
    field: 'landingType',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入定向包类型!'},
             ];
    },
  },
  {
    label: '投放账号',
    field: 'accountId',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "open_gateway.op_put_account where channel_Id = '4' and state = '1' and level_id = '2',nick_name,id"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入投放账号!'},
             ];
    },
  },
  {
    label: '广告投放范围',
    field: 'deliveryRange',
    component: 'Input',
  },
  {
    label: '地域',
    field: 'district',
    component: 'Input',
  },
  {
    label: '性别',
    field: 'gender',
    component: 'Input',
  },
  {
    label: '年龄',
    field: 'age',
    component: 'InputTextArea',
  },
  {
    label: '自定义人群',
    field: 'retargetingTagsConf',
    component: 'InputTextArea',
  },
  {
    label: '穿山甲媒体定向',
    field: 'superiorPopularityType',
    component: 'Input',
  },
  {
    label: '行为兴趣',
    field: 'interestActionMode',
    component: 'Input',
  },
  {
    label: '抖音达人',
    field: 'awemeFanConf',
    component: 'InputTextArea',
  },
  {
    label: '过滤高活跃用户',
    field: 'filterAwemeAbnormalActive',
    component: 'InputNumber',
  },
  {
    label: '过滤自己的粉丝',
    field: 'filterOwnAwemeFans',
    component: 'InputNumber',
  },
  {
    label: '过滤高关注数用户',
    field: 'filterAwemeFansCount',
    component: 'InputNumber',
  },
  {
    label: '平台',
    field: 'platform',
    component: 'InputTextArea',
  },
  {
    label: '设备类型',
    field: 'deviceType',
    component: 'Input',
  },
  {
    label: '网络',
    field: 'ac',
    component: 'InputTextArea',
  },
  {
    label: '已安装用户',
    field: 'hideIfExists',
    component: 'InputNumber',
  },
  {
    label: '手机品牌',
    field: 'deviceBrand',
    component: 'InputTextArea',
  },
  {
    label: '手机价格',
    field: 'launchPrice',
    component: 'Input',
  },
  {
    label: '智能放量',
    field: 'autoExtendTargets',
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
