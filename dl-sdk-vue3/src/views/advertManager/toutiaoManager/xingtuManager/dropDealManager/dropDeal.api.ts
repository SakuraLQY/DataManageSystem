import { defHttp } from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  list = '/advert/opXingtuDropDeal/list',
  save='/advert/opXingtuDropDeal/add',
  edit='/advert/opXingtuDropDeal/edit',
  deleteOne = '/advert/opXingtuDropDeal/delete',
  deleteBatch = '/advert/opXingtuDropDeal/deleteBatch',
  importExcel = '/advert/opXingtuDropDeal/importExcel',
  exportXls = '/advert/opXingtuDropDeal/exportXls',
  pgkList = '/game/opPkg/option',
  bind = '/advert/opAnchorPlan/bind',
  info = '/advert/opAnchorPlan/anchorDealInfo',
  makeUp = '/advert/opXingtuDropDeal/makeUp',
  assetList = '/advert/opJrttEvents/getExternalAction',
  audienceList = '/advert/opJrttAudience/getLocalAudienceList',
  batchCreatePlan = '/XingtuDealPlan/xingtuDealPlan/batchCreatePlan'
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
 * 主播信息接口
 */
 export const info = () => defHttp.get({ url: Api.info });

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
 * 补建广告组
 * @param params
 */
 export const makeUpDeal = (params) => {
  return defHttp.post({ url: Api.makeUp, params });
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

export const assetList = (params) => defHttp.get({url: Api.assetList, params});

export const audienceList = (params) => defHttp.get({url: Api.audienceList, params:{accountId: params}});

export const batchCreatePlanReq = (dealId,planInfo) => defHttp.post({url:Api.batchCreatePlan, data:{dealId:dealId, xingtuDealPlan: planInfo}})
