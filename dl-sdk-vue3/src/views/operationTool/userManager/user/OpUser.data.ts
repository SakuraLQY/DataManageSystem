import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '用户ID',
    align:"center",
    dataIndex: 'id'
   },
   {
    title: '用户名',
    align:"center",
    dataIndex: 'userName'
   },
  //  {
  //   title: '用户类型',
  //   align:"center",
  //   dataIndex: 'userType'
  //  },
   {
    title: '手机号',
    align:"center",
    dataIndex: 'userPhone'
   },
   {
       title: '实名信息',
       align:"center",
       dataIndex: 'realNameInfo',
       slots: { customRender: 'realNameInfo' },
    },
   {
    title: '注册-时间',
    align:"center",
    dataIndex: 'signupTime',
    customRender:({text}) =>{
      return !text?"":text
    },
   },
   {
    title: '注册-IP',
    align:"center",
    dataIndex: 'signupIp'
   },
   {
    title: '注册-设备',
    align:"center",
    dataIndex: 'signupDevice'
   },
   {
    title: '登录-时间',
    align:"center",
    dataIndex: 'signinTime',
    customRender:({text}) =>{
      return !text?"":text
    },
   },
   {
    title: '登录-IP',
    align:"center",
    dataIndex: 'signinIp'
   },
   {
    title: '登录-设备',
    align:"center",
    dataIndex: 'signinDevice'
   },
   {
    title: '充值-时间',
    align:"center",
    dataIndex: 'chargeTime'
   },
   {
    title: '平台币',
    align:"center",
    dataIndex: 'platformCurrency',
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "用户ID",
      field: 'id',
      component: 'Input',
      colProps: {span: 6},
      dynamicRules: ({model,schema}) => {
        return [
               { required: false},
               { pattern: /^\d{0,10}$/, message: '只能输入十位以内数字!'},
        ];
      },
      
   },
   {
    label: "用户名",
    field: 'userName',
    component: 'Input',
    colProps: {span: 6},
    dynamicRules: ({model,schema}) => {
      return [
             { required: false},
             { pattern: /^(?=.*\w)(?=.*[\\u4e00-\\u9fa5]).{0,10}$/, message: '只能输入十位以内!'},
      ];
    },
    
  },
	{
      label: "手机号",
      field: 'userPhone',
      component: 'Input',
      dynamicRules: ({model,schema}) => {
        return [
               { required: false},
               { pattern: /^\d{0,11}$/, message: '只能输入十一位以内数字!'},
        ];
   },
      colProps: {span: 6},
 	},
];

//修改密码
export const editPassFormSchema: FormSchema[] = [
	{
      label: "新密码",
      field: 'userPassword',
      component: 'Input',
      dynamicRules: ({model,schema}) => {
       return [
              { required: true}, 
              { pattern: /^[\w]{6,16}$/, message: '长度最少6位,最多16位!只能有数字和字母组成'},
       ];
  },
 	},
];

//修改余额
export const editPlatformCurrencyFormSchema: FormSchema[] = [
	{
      label: "修改后的余额",
      field: 'platformCurrency',
      component: 'Input',
      dynamicRules: ({model,schema}) => {
       return [
              { required: true}, 
              { pattern: /^\d+$/, message: '请输入正确格式的金额'},
       ];
  },
 	},
];

//详情
export const detailFormSchema: FormSchema[] = [
   {
    label: '用户名',
    field: 'userName',
    component: 'Input',
   },
   {
    label: '用户信息-手机',
    field: 'userPhone',
    component: 'Input',
   },
   {
    label: '实名信息',
    component: 'Input',
    field: 'realName' + 'realNumber',
    },
   {
    label: '注册-时间',
    field: 'signupTime',
    component: 'DatePicker',
   },
   {
    label: '注册-IP',
    field: 'signupIp',
    component: 'Input',
   },
   {
    label: '注册-设备',
    field: 'signupDevice',
    component: 'Input',
   },
   {
    label: '注册-来源',
    field: 'signupSource',
    component: 'Input',
   },
   {
    label: '登录-时间',
    field: 'signinTime',
    component: 'DatePicker',
   },
   {
    label: '登录-IP',
    field: 'signinIp',
    component: 'Input',
   },
   {
    label: '登录-设备',
    field: 'signinDevice',
    component: 'Input',
   },
   {
    label: '登录-来源',
    field: 'signinSource',
    component: 'Input',
   },
   {
    label: '充值-时间',
    field: 'chargeTime',
    component: 'DatePicker',
   },
   {
    label: '平台币',
    field: 'platformCurrency',
    component: 'InputNumber',
   },
   {
    label: '微信用户ID',
    field: 'extendedField',
    component: 'Input',
  },
];



/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}