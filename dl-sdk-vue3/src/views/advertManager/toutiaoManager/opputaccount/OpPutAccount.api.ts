import { defHttp } from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";
import {optionList} from '/@/views/operationTool/gameManager/opSubGame/OpSubGame.api';
import { ref } from 'vue';
import { message } from 'ant-design-vue';

const { createConfirm } = useMessage();

enum Api {
  list = '/opputaccount/opJrttPutAccount/list',
  save='/opputaccount/opJrttPutAccount/add',
  edit='/opputaccount/opJrttPutAccount/edit',
  deleteOne = '/opputaccount/opJrttPutAccount/delete',
  deleteBatch = '/opputaccount/opJrttPutAccount/deleteBatch',
  importExcel = '/opputaccount/opJrttPutAccount/importExcel',
  exportXls = '/opputaccount/opJrttPutAccount/exportXls',
  auth = '/opputaccount/opJrttPutAccount/auth',
  syncAccount = '/opputaccount/opJrttPutAccount/synAccount'
}


export const faList = ref([]);

getOptionList();
// 下拉框取值
function getOptionList() {
  optionList({status:0}).then((res: any)=>{
    faList.value = res
  })
};


/**
 * 导出api
 * @param params
 */
export const getExportUrl = Api.exportXls;

/**
 * 导入api
 */
export const getImportUrl = Api.importExcel;

/**
 * 列表接口
 * @param params
 */
export const list = (params) => defHttp.get({ url: Api.list, params });

export const auth = (params) => {
  defHttp.get({ url: Api.auth, params }).then(res =>{
    window.open(res);
  })
}

export const syncAccount = (params) => {
  defHttp.get({url : Api.syncAccount, params}).then(res =>{
    let str = "";
    for (let single of res){
      str += "广告组：" + single.advertiserId;
      if(single.flag == 0){
        str += "添加成功"
      }else if (single.flag == 1){
        str += "添加失败"
      }else{
        str += "同步成功过了，请勿重复同步"
      }
      str += ";\n"
    }
    message.warning(str);
    // alert(str);
  })
}
 /**
 * 删除单个
 * @param params
 * @param handleSuccess
 */
export const deleteOne = (params,handleSuccess) => {
  return defHttp.delete({url: Api.deleteOne, params}, {joinParamsToUrl: true}).then(() => {
    handleSuccess();
  });
}

/**
 * 批量删除
 * @param params
 * @param handleSuccess
 */
export const batchDelete = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认删除',
    content: '是否删除选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.delete({url: Api.deleteBatch, data: params}, {joinParamsToUrl: true}).then(() => {
        handleSuccess();
      });
    }
  });
}

/**
 * 保存或者更新
 * @param params
 * @param isUpdate
 */
export const saveOrUpdate = (params, isUpdate) => {
  let url = isUpdate ? Api.edit : Api.save;
  return defHttp.post({ url: url, params }, { isTransformResponse: false });
}
