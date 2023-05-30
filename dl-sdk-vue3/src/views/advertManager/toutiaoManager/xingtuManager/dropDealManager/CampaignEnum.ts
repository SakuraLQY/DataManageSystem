// 广告组状态
enum operation {
  ENABLE = 'enable',
  DISABLE = 'disable',
}

// 推广目的
enum landingType {
  LIVE = 'LIVE',
  AWEME = 'AWEME',
}

// 预算类型
enum budgetMode {
  INFINITE = 'BUDGET_MODE_INFINITE',
  DAY = 'BUDGET_MODE_DAY',
}

// 预算择优分配
enum campaignBudgetOptimization {
  ON = 'ON',
  OFF = 'OFF',
}

export const OPERATION = operation;
export const BUDGET_MODE = budgetMode;
export const LANDING_TYPE = landingType;
export const CAMPAIGN_BUDGET_OPTIMIZATION = campaignBudgetOptimization;
