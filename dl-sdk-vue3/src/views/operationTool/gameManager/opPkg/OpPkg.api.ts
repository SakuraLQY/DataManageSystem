import { defHttp } from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  list = '/game/opPkg/list',
  getOptionList = '/game/opPkg/getOptionList',
  queryList = '/game/opPkg/queryList',
  save='/game/opPkg/add',
  edit='/game/opPkg/edit',
  updatePackState = '/game/opPkg/updatePackState',
  deleteOne = '/game/opPkg/delete',
  deleteBatch = '/game/opPkg/deleteBatch',
  importExcel = '/game/opPkg/importExcel',
  exportXls = '/game/opPkg/exportXls',
}

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

/**
 * 列表接口
 * @param params
 */
export const getOptionList = (params) => defHttp.get({ url: Api.getOptionList, params });

/**
 * 列表接口
 * @param params
 */
export const queryList = () => defHttp.get({ url: Api.queryList });

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

/**
 * 更新状态
 * @param params
 * @param handleSuccess
 */
 export const updatePackState = (params,handleSuccess) => {
  return defHttp.post({url: Api.updatePackState, params}, {joinParamsToUrl: true}).then(() => {
    handleSuccess();
  });
}