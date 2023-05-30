import { defHttp } from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  list = '/advert/opMaterial/list',
  setAccountWay = '/advert/opMaterial/setAccount',
  pushMaterialWay = '/advert/opMaterial/pushMaterial',
  save='/advert/opMaterial/add',
  edit='/advert/opMaterial/edit',
  deleteOne = '/advert/opMaterial/delete',
  deleteBatch = '/advert/opMaterial/deleteBatch',
  importExcel = '/advert/opMaterial/importExcel',
  exportXls = '/advert/opMaterial/exportXls',
  queryByIdList = '/advert/opMaterial/queryByIdList',
  queryById = '/advert/opMaterial/queryById',
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
 * 更新账号
 * @param params
 */
export const setAccountWay = (params) => defHttp.post({ url: Api.setAccountWay, params });

/**
 * 推送素材
 * @param params
 */
export const pushMaterialWay = (params) => defHttp.post({ url: Api.pushMaterialWay, params });

/**
 * 列表接口
 * @param params
 */
export const getAccountList = (params) => defHttp.get({ url: Api.getAccountList, params });

/**
 * 通过id批量查询
 * @param params
 */
 export const queryByIdList = (params) => defHttp.get({ url: Api.queryByIdList, params });

 /**
 * 通过id查询
 * @param params
 */
  export const queryById = (params) => defHttp.get({ url: Api.queryById, params });

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
