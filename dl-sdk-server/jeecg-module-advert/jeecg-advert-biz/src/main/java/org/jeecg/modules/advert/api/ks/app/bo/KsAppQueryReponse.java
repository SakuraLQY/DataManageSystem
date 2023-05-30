package org.jeecg.modules.advert.api.ks.app.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

@Data
public class KsAppQueryReponse {

    @JSONField(name = "details")
    private List<KsAppQueryParamReponse> ksAppQueryParamReponseList;
}
