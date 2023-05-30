import { defHttp } from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  list = '/count/detail/list',
  save='/count/detail/add',
  edit='/count/detail/edit',
  deleteOne = '/count/detail/delete',
  deleteBatch = '/count/detail/deleteBatch',
  importExcel = '/count/detail/importExcel',
  exportXls = '/count/detail/exportXls',
  firstMoneyRate ='/count/detail/firstMoneyRate',
  aliveMoneyRate = '/count/detail/aliveMoneyRate'
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
 * 首次付费比例
 * @param params
 */
 export const firstMoneyRate = (params) => defHttp.get({ url: Api.firstMoneyRate, params });

 /**
 * 活跃付费比例
 * @param params
 */
  export const aliveMoneyRate = (params) => defHttp.get({ url: Api.aliveMoneyRate, params });