enum pricingType{
    PRICING_CPM = 'PRICING_CPM',
    PRICING_CPC = 'PRICING_CPC',
    PRICING_OCPM = 'PRICING_OCPM',
    PRICING_OCPC = 'PRICING_OCPC'
  }
  
  //优化目标枚举
enum optimizeGoalEnum {
    AD_CONVERT_TYPE_CLICK_NUM = 'AD_CONVERT_TYPE_CLICK_NUM',
    AD_CONVERT_TYPE_SHOW_OFF_NUM = 'AD_CONVERT_TYPE_SHOW_OFF_NUM'
  }
  //计划状态枚举
enum operationEnum {
    ENABLE = 'ENABLE',
    DISABLE = 'DISABLE'
  }

enum landingType {
    APP = 'APP'
}

enum appPromotionType{
    DOWNLOAD = 'DOWNLOAD'
}

enum marketingGoal {
    VIDEO_AND_IMAGE = 'VIDEO_AND_IMAGE'
}
enum adType {
    ALL = 'ALL'
}
enum deepBidType {
    DEEP_BID_DEFAULT = 'DEEP_BID_DEFAULT',
    BID_PER_ACTION = 'BID_PER_ACTION'
}
enum bidType {
    CUSTOM = 'CUSTOM',
    NO_BID = 'NO_BID',
    UPPER_CONTROL = 'UPPER_CONTROL'
}
enum audienceExtend {
    ON = 'ON',
    OFF = 'OFF'
}
enum downloadType {
    DOWNLOAD_URL = 'DOWNLOAD_URL',
    EXTERNAL_URL = 'EXTERNAL_URL'
}
enum downloadMode {
    APP_STORE_DELIVERY = 'APP_STORE_DELIVERY',
    DEFAULT = 'DEFAULT'
}
enum inventoryCatalog {
    MANUAL = 'MANUAL',
    UNIVERSAL_SMART = 'UNIVERSAL_SMART'
}
enum unionVideoType  {
    ORIGINAL_VIDEO  = 'ORIGINAL_VIDEO',
    REWARDED_VIDEO  = 'REWARDED_VIDEO',
    SPLASH_VIDEO = 'SPLASH_VIDEO'
}
enum scheduleType  {
    SCHEDULE_FROM_NOW = 'SCHEDULE_FROM_NOW',
    SCHEDULE_START_END = 'SCHEDULE_START_END'
}
enum bidSpeed  {
    BALANCE = 'BALANCE',
    FAST = 'FAST'
}
enum budgetMode   {
    BUDGET_MODE_INFINITE = 'BUDGET_MODE_INFINITE',
    BUDGET_MODE_DAY = 'BUDGET_MODE_DAY'
}

export const PRICINGTYPE = pricingType;
export const OPTIMIZEGOALENUM = optimizeGoalEnum;
export const OPERATIONENUM = operationEnum;
export const LANDINGTYPE = landingType;
export const APPPROMOTIONTYPE = appPromotionType;
export const MARKETINGGOAL = marketingGoal;
export const ADTYPE = adType;
export const DEEPBIDTYPE = deepBidType;
export const BIDTYPE = bidType;
export const AUDIENCEEXTEND = audienceExtend;
export const DOWNLOADTYPE = downloadType;
export const DOWNLOADMODE = downloadMode;
export const INVENTORYCATALOG = inventoryCatalog;
export const UNIONVIDEOTYPE = unionVideoType;
export const SCHEDULETYPE = scheduleType;
export const BIDSPEED = bidSpeed;
export const BUDGETMODE = budgetMode;
export const checkedList = ['INVENTORY_FEED', 'INVENTORY_VIDEO_FEED','INVENTORY_TOMATO_NOVEL','INVENTORY_AWEME_FEED','INVENTORY_UNION_SLOT','UNION_BOUTIQUE_GAME'];

