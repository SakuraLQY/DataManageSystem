import { defHttp } from '/@/utils/http/axios';
import { useMessage } from '/@/hooks/web/useMessage';

const { createConfirm } = useMessage();

enum Api {
  list = '/advert/opKuaishouDealPlan/list',
  save = '/advert/opKuaishouDealPlan/add',
  edit = '/advert/opKuaishouDealPlan/edit',
  deleteOne = '/advert/opKuaishouDealPlan/delete',
  deleteBatch = '/advert/opKuaishouDealPlan/deleteBatch',
  importExcel = '/advert/opKuaishouDealPlan/importExcel',
  exportXls = '/advert/opKuaishouDealPlan/exportXls',
  pgkList = '/game/opPkg/option',
  makeUp = '/advert/opKuaishouDealPlan/makeUp',
  budget = '/advert/opKuaishouDealPlan/modifyBudget',
  pkgApp = 'advert/opKuaishouDealPlan/packDeal',
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
 * 站点列表接口
 * @param params
 */
export const siteList = (params) => defHttp.get({ url: Api.siteList, params });

/**
 * 补建广告组
 * @param params
 */
export const makeUpDeal = (params) => {
  return defHttp.post({ url: Api.makeUp, params });
};

/**
 * 修改预算
 * @param params
 */
export const modifyBudget = (params) => {
  return defHttp.post({ url: Api.budget, params });
};

/**
 * 删除单个
 * @param params
 * @param handleSuccess
 */
export const deleteOne = (params, handleSuccess) => {
  return defHttp.delete({ url: Api.deleteOne, params }, { joinParamsToUrl: true }).then(() => {
    handleSuccess();
  });
};

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
      return defHttp.delete({ url: Api.deleteBatch, data: params }, { joinParamsToUrl: true }).then(() => {
        handleSuccess();
      });
    },
  });
};

export const createApp = (params, handleSuccess) => {
  createConfirm({
    iconType: 'info',
    title: '确认打包发布',
    content: '是否发布选择的数据',
    okText: '确定',
    cancelText: '取消',
    onOk: () => {
      return defHttp.get({ url: Api.pkgApp, params }).then(() => {
        handleSuccess();
      });
    },
  });
};

/**
 * 保存或者更新
 * @param params
 * @param isUpdate
 */
export const saveOrUpdate = (params, isUpdate) => {
  let url = isUpdate ? Api.edit : Api.save;
  return defHttp.post({ url: url, params }, { isTransformResponse: false });
};

/**
 * 获取游戏包信息
 * @param params
 */
export const pkgInfoList = (params) => defHttp.get({ url: Api.pgkList, params });
