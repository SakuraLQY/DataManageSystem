import { BasicColumn } from '/@/components/Table';
//列表数据
export const columns: BasicColumn[] = [
    {
        title: '广告ID',
        align: "center",
        dataIndex: 'dealId'
    },
    {
        title: '头条广告ID',
        align: "center",
        dataIndex: 'jrttPromotionId'
    },
    {
        title: '头条广告名称',
        align: "center",
        dataIndex: 'name'
    },
    {
        title: '创建人',
        align: "center",
        dataIndex: 'createBy'
    },
    {
        title: '创建日期',
        align: "center",
        dataIndex: 'createTime',
        customRender: ({ text }) => {
            return !text ? "" : text;
        },
    },
    {
        title: '更新人',
        align: "center",
        dataIndex: 'updateBy'
    },
    {
        title: '更新日期',
        align: "center",
        dataIndex: 'updateTime',
        customRender: ({ text }) => {
            return !text ? "" : text;
        },
    },
];