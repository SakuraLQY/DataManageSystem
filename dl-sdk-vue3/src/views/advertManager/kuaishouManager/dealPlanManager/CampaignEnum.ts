// 计划类型
enum type {
  APP = 2,
}

// 出价类型
enum bidType {
  DEFAULT = 0,
  MAX = 1,
}

// 预算类型
enum budgetMode {
  INFINITE = 0,
  DAY = 1,
  WEEK = 2
}

export const TYPE = type;
export const BID_TYPE = bidType;
export const BUDGET_MODE = budgetMode;
