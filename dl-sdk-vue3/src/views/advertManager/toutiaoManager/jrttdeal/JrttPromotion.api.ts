import { defHttp } from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";
const { createConfirm } = useMessage();

enum Api {
  list = '/advert/opJrttPromotion/list',
  save='/advert/opJrttPromotion/add',
  edit='/advert/opJrttPromotion/edit',
  deleteOne = '/advert/opJrttPromotion/delete',
  deleteBatch = '/advert/opJrttPromotion/deleteBatch',
  importExcel = '/advert/opJrttPromotion/importExcel',
  exportXls = '/advert/opJrttPromotion/exportXls',
  awemeSync = '/aweme/opJrttAweme/syncAwemeByAccountId', // 根据accountId同步抖音号
  awemeList = '/aweme/opJrttAweme/list', // 获取抖音号列表
  addAction = '/advert/opJrttAction/add', // 新增行为记录
  deleteAction ='/advert/opJrttAction/delete', // 删除行为记录
  queryByActionType ='/advert/opJrttAction/queryByActionType', // 根据类型搜索行为记录
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
 * 同步抖音授权账号
 * @param params 
 * @returns 
 */
export const awemeSync = (params) => defHttp.post({ url: Api.awemeSync, params }, {joinParamsToUrl: true});

/**
 * 列表接口
 * @param params
 */
 export const awemeList = (params) => defHttp.get({ url: Api.awemeList, params });


 /**
 * 新增记录
 * @param params 
 * @returns 
 */
export const addAction = (params) => defHttp.post({ url: Api.addAction, params });

/**
 * 根据类型查询列表
 * @param params
 */
 export const queryByActionType = (params) => defHttp.get({ url: Api.queryByActionType, params });

/**
 * 删除单个
 * @param params
 */
export const deleteAction = (params) => defHttp.delete({url: Api.deleteAction, params}, {joinParamsToUrl: true});