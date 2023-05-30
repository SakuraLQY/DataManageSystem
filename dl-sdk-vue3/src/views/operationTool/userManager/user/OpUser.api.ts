import {defHttp} from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  list = '/hello/opUser/list',
  editPass = '/hello/opUser/editPass',
  editPlatformCurrency = '/hello/opUser/editPlatformCurrency',
  removePhone = '/hello/opUser/removePhone',
}

/**
 * 列表接口
 * @param params
 */
export const list = (params) =>
  defHttp.get({url: Api.list, params});


/**
 * 解绑手机号
 */
export const removePhoneOne = (params) => {
  return defHttp.post({url: Api.removePhone, params});

}


/**
 * 下拉选项事件
 * @param params
 */
export const updateAll = (params, isUpdate) => {
  let url = isUpdate ? Api.editPass : Api.editPlatformCurrency;
  return defHttp.post({url: url, params});
}
