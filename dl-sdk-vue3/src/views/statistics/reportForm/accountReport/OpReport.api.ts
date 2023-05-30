import { defHttp } from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";
import {optionList} from '/@/views/operationTool/gameManager/opSubGame/OpSubGame.api';
import { ref } from 'vue';
import { message } from 'ant-design-vue';
const { createConfirm } = useMessage();

enum Api {
  list = '/count/opReport/list',
  querylist = '/count/opReport/queryList',
  queryBill = '/count/opReport/queryBill',
  save='/count/opReport/add',
  edit='/count/opReport/edit',
  deleteOne = '/count/opReport/delete',
  deleteBatch = '/count/opReport/deleteBatch',
  importExcel = '/count/opReport/importExcel',
  exportXls = '/count/opReport/exportXls',
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



export const queryList = (params) => defHttp.get({ url: Api.querylist, params });
export const queryBill = (params) => defHttp.get({ url: Api.queryBill,params });
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
