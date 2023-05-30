import { defHttp } from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  queryROIList = '/count/ctDaily/queryROIList',
  queryOverViewList = '/count/ctDaily/queryOverViewList',
  queryRecoveryList = '/count/ctDaily/queryRecoveryList',
  queryFinanceUserList = '/count/ctDaily/queryFinanceUserList',
  deleteOne = '/count/ctUser/delete',
  deleteBatch = '/count/ctUser/deleteBatch',
  importExcel = '/count/ctUser/importExcel',
  exportXls = '/count/ctUser/exportXls',
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
export const queryROIList = (params) => defHttp.get({ url: Api.queryROIList, params });

/**
 * 列表接口
 * @param params
 */
export const queryOverViewList = (params) => defHttp.get({ url: Api.queryOverViewList, params });

/**
 * 列表接口
 * @param params
 */
export const queryRecoveryList = (params) => defHttp.get({ url: Api.queryRecoveryList, params });

/**
 * 列表接口
 * @param params
 */
export const queryFinanceUserList = (params) => defHttp.get({ url: Api.queryFinanceUserList, params });

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


