import { defHttp } from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  list = '/advert/opXingtuDeal/list',
  save='/advert/opXingtuDeal/add',
  edit='/advert/opXingtuDeal/edit',
  deleteOne = '/advert/opXingtuDeal/delete',
  deleteBatch = '/advert/opXingtuDeal/deleteBatch',
  importExcel = '/advert/opXingtuDeal/importExcel',
  exportXls = '/advert/opXingtuDeal/exportXls',
  pgkList = '/game/opPkg/option',
  siteSave = '/advert/opXingtuDeal/postSite',
  bind = '/advert/opAnchorPlan/bind',
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
 * 绑定广告
 * @param params
 * @param handleSuccess
 */
 export const bind = (params, handleSuccess) => defHttp.get({ url: Api.bind, params }).then(() => {
    handleSuccess();
 });

/**
 * 站点列表接口
 * @param params
 */
export const siteList = (params) => defHttp.get({ url: Api.siteList, params });

/**
 * 创建站点
 * @param params
 */
 export const siteSave = (params) => {
  return defHttp.post({ url: Api.siteSave, params });
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

/**
 * 获取游戏包信息
 * @param params
 */
export const pkgInfoList = (params) => defHttp.get({ url: Api.pgkList, params });