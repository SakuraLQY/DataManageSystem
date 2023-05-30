import { defHttp } from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  list = '/count/summaryAdvert/list',
  save='/count/summaryAdvert/add',
  edit='/count/summaryAdvert/edit',
  deleteOne = '/count/summaryAdvert/delete',
  deleteBatch = '/count/summaryAdvert/deleteBatch',
  importExcel = '/count/summaryAdvert/importExcel',
  exportXls = '/count/summaryAdvert/exportXls',
  getSummaryAdvertEChart = '/count/summaryAdvert/getSummaryAdvertEChart',
  getSummaryAdvertLine = '/count/summaryAdvert/getSummaryAdvertLine'
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
 * 获取饼图
 * @param params
 */
 export const getSummaryAdvertEChart = (params) => defHttp.get({ url: Api.getSummaryAdvertEChart, params });

 /**
  * 获取折线图
  * @param params 
  * @returns 
  */
 export const getSummaryAdvertLine = (params) => defHttp.get({ url: Api.getSummaryAdvertLine, params });
 