import { defHttp } from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  list = '/opJrttProject/opJrttProject/list',
  save='/opJrttProject/opJrttProject/add',
  edit='/opJrttProject/opJrttProject/edit',
  deleteOne = '/opJrttProject/opJrttProject/delete',
  deleteBatch = '/opJrttProject/opJrttProject/deleteBatch',
  importExcel = '/opJrttProject/opJrttProject/importExcel',
  exportXls = '/opJrttProject/opJrttProject/exportXls',
  batchCreateProject = '/opJrttProject/opJrttProject/batchCreateProject',
  getEditInfo = '/opJrttProject/opJrttProject/getEditInfo',
  eventList = '/advert/opJrttEvents/getExternalAction',
  localAudienceList = '/advert/opJrttAudience/getLocalAudienceList'
}

/**
 * 导出api
 * @param params
 */
export const getExportUrl = Api.exportXls;

export const getEditInfoUrl = Api.getEditInfo

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
 * 获取项目编辑信息
 */
export const getProjectInfo = (dealId) => {
  defHttp.get({url:  Api.getEditInfo, params:{dealId : dealId}});
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
 * 批量创建项目
 * @param params
 */
export const batchCreateProject = (dealIds, opJrttProject) => {
  return defHttp.post({ url: Api.batchCreateProject, data: {dealIds: dealIds, opJrttProject: opJrttProject}}, { isTransformResponse: false });
}

/**
 * 编辑项目
 */

export const editProject = (opJrttProject) =>{
  return defHttp.post({url:Api.edit, data: opJrttProject})
}

/**
 * 获取优化目标
 */
export const getExternalAction = (deal) => {
  return defHttp.get({url: Api.eventList, params:deal})
}

/**
 * 获取本地定向包
 * @param params 
 * @returns 
 */
export const localAudienceList = (params) => {return defHttp.get({url: Api.localAudienceList, params:{accountId:params}})};