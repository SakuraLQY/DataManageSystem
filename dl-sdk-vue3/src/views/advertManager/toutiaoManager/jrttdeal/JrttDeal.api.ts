import { defHttp } from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  list = '/advert/opJrttDeal/list',
  optionList = '/advert/opDeal/optionList',
  selectVagueList = '/advert/opDeal/selectVagueList',
  save='/advert/opJrttDeal/add',
  edit='/advert/opJrttDeal/edit',
  deleteOne = '/advert/opJrttDeal/delete',
  deleteBatch = '/advert/opJrttDeal/deleteBatch',
  importExcel = '/advert/opDeal/importExcel',
  exportXls = '/advert/opDeal/exportXls',
  pgkList = '/game/opPkg/option',
  siteList = '/advert/opJrttSite/list',
  siteSave = '/advert/opJrttDeal/postSite',
  getAwemeAuthListByDealId = '/advert/opJrttDeal/getAwemeAuthListByDealId',
  extendPackage = '/advert/opJrttDeal/extendPackage',
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
 * 分页下拉框列表查询
 * @param params
 */
export const optionList = (params) => defHttp.get({ url: Api.optionList, params });

/**
 * 模糊下拉框列表查询
 * @param params
 */
export const selectVagueList = (params) => defHttp.get({ url: Api.selectVagueList, params });

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

/**
 * 根据广告id获取抖音授权关系
 * @param params
 */
 export const getAwemeAuthListByDealId = (params) => defHttp.get({ url: Api.getAwemeAuthListByDealId, params });

 /**
 * 重新打包
 * @param params
 */
export const extendPackage = (params) => {return defHttp.post({url: Api.extendPackage, params}, {joinParamsToUrl: true})};


