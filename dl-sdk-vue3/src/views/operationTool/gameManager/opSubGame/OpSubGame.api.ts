import { defHttp } from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  list = '/game/opSubGame/list',
  optionList = '/game/opSubGame/optionList',
  gameAndSubGameAndPkgList = '/game/opSubGame/gameAndSubGameAndPkgList',
  queryAll = '/game/opSubGame/queryAll',
  getConf = '/game/opSubGame/getConf',
  recover = '/game/opSubGame/recover',
  save='/game/opSubGame/add',
  edit='/game/opSubGame/edit',
  deleteOne = '/game/opSubGame/delete',
  deleteBatch = '/game/opSubGame/deleteBatch',
  importExcel = '/game/opSubGame/importExcel',
  exportXls = '/game/opSubGame/exportXls',
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
 * 下拉框列表接口
 * @param params
 */
export const optionList = (params) => defHttp.get({ url: Api.optionList, params });

/**
 * 下拉框列表接口
 * @param params
 */
export const gameAndSubGameAndPkgList = () => defHttp.get({ url: Api.gameAndSubGameAndPkgList});

/**
 * 不分页查询列表接口
 * @param params
 */
export const queryAll = (params) => defHttp.get({ url: Api.queryAll, params });

/**
 * 参数
 * @param params
 * @param isUpdate
 */
export const getConf = (params) => {
  return defHttp.post({ url: Api.getConf, params }, { isTransformResponse: false });
}

/**
 * 恢复
 * @param params
 */
export const recover = (params) => defHttp.post({ url: Api.recover, params });

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
