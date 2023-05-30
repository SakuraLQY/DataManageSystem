import { defHttp } from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

enum Api {
  list = '/count/weekReport/list',
  saveConfig='/count/ctReportConfig/add',
  editConfig='/count/ctReportConfig/edit',
  queryByConfigName = '/count/ctReportConfig/queryByConfigName'
}

/**
 * 列表接口
 * @param params
 */
export const list = (params) => defHttp.get({ url: Api.list, params });


 /**
 * 通过id查询 报表配置
 * @param params
 */
  export const queryByConfigName = (params) => defHttp.get({ url: Api.queryByConfigName, params });

  /**
 * 保存或者更新 报表配置
 * @param params
 * @param isUpdate
 */
export const saveOrUpdateConfig = (params, isUpdate) => {
  let url = isUpdate ? Api.editConfig : Api.saveConfig;
  return defHttp.post({ url: url, params }, { isTransformResponse: false });
}