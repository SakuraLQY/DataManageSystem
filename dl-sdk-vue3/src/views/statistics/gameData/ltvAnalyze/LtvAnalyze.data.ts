import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '日期',
    align: "center",
    dataIndex: 'ltvDate',
    customRender:({text}) =>{
      return !text?"":(text.length>10?text.substr(0,10):text);
    },
  },
  {
    title: '游戏名',
    align: "center",
    dataIndex: 'gameName'
  },
  {
    title: '渠道名',
    align: "center",
    dataIndex: 'channelName'
  },
  {
    title: '广告名',
    align: "center",
    dataIndex: 'dealName'
  },
  {
    title: '注册数',
    align: "center",
    dataIndex: 'regCount'
  },
  {
    title: 'LTV1',
    align: "center",
    dataIndex: 'ltv1'
  },
  {
    title: 'LTV2',
    align: "center",
    dataIndex: 'lyv2'
  },
  {
    title: 'LTV3',
    align: "center",
    dataIndex: 'ltv3'
  },
  {
    title: 'LTV4',
    align: "center",
    dataIndex: 'ltv4'
  },
  {
    title: 'LTV5',
    align: "center",
    dataIndex: 'ltv5'
  },
  {
    title: 'LTV6',
    align: "center",
    dataIndex: 'ltv6'
  },
  {
    title: 'LTV7',
    align: "center",
    dataIndex: 'ltv7'
  },
  {
    title: 'LTV8',
    align: "center",
    dataIndex: 'ltv8'
  },
  {
    title: 'LTV9',
    align: "center",
    dataIndex: 'ltv9'
  },
  {
    title: 'LTV10',
    align: "center",
    dataIndex: 'ltv10'
  },
  {
    title: 'LTV11',
    align: "center",
    dataIndex: 'ltv11'
  },
  {
    title: 'LTV12',
    align: "center",
    dataIndex: 'ltv12'
  },
  {
    title: 'LTV13',
    align: "center",
    dataIndex: 'ltv13'
  },
  {
    title: 'LTV14',
    align: "center",
    dataIndex: 'ltv14'
  },
  {
    title: 'LTV15',
    align: "center",
    dataIndex: 'ltv15'
  },
  {
    title: 'LTV16',
    align: "center",
    dataIndex: 'ltv16'
  },
  {
    title: 'LTV17',
    align: "center",
    dataIndex: 'ltv17'
  },
  {
    title: 'LTV18',
    align: "center",
    dataIndex: 'ltv18'
  },
  {
    title: 'LTV19',
    align: "center",
    dataIndex: 'ltv19'
  },
  {
    title: 'LTV20',
    align: "center",
    dataIndex: 'ltv20'
  },
  {
    title: 'LTV21',
    align: "center",
    dataIndex: 'ltv21'
  },
  {
    title: 'LTV22',
    align: "center",
    dataIndex: 'ltv22'
  },
  {
    title: 'LTV23',
    align: "center",
    dataIndex: 'ltv23'
  },
  {
    title: 'LTV24',
    align: "center",
    dataIndex: 'ltv24'
  },
  {
    title: 'LTV26',
    align: "center",
    dataIndex: 'ltv26'
  },
  {
    title: 'LTV27',
    align: "center",
    dataIndex: 'ltv27'
  },
  {
    title: 'LTV28',
    align: "center",
    dataIndex: 'ltv28'
  },
  {
    title: 'LTV29',
    align: "center",
    dataIndex: 'ltv29'
  },
  {
    title: 'LTV30',
    align: "center",
    dataIndex: 'ltv30'
  },
  {
    title: 'LTV31',
    align: "center",
    dataIndex: 'ltv31'
  },
  {
    title: 'LTV32',
    align: "center",
    dataIndex: 'ltv32'
  },
  {
    title: 'LTV33',
    align: "center",
    dataIndex: 'ltv33'
  },
  {
    title: 'LTV34',
    align: "center",
    dataIndex: 'ltv34'
  },
  {
    title: 'LTV35',
    align: "center",
    dataIndex: 'ltv35'
  },
  {
    title: 'LTV36',
    align: "center",
    dataIndex: 'ltv36'
  },
  {
    title: 'LTV37',
    align: "center",
    dataIndex: 'ltv37'
  },
  {
    title: 'LTV38',
    align: "center",
    dataIndex: 'ltv38'
  },
  {
    title: 'LTV39',
    align: "center",
    dataIndex: 'ltv39'
  },
  {
    title: 'LTV40',
    align: "center",
    dataIndex: 'ltv40'
  },
  {
    title: 'LTV41',
    align: "center",
    dataIndex: 'ltv41'
  },
  {
    title: 'LTV42',
    align: "center",
    dataIndex: 'ltv42'
  },
  {
    title: 'LTV43',
    align: "center",
    dataIndex: 'ltv43'
  },
  {
    title: 'LTV44',
    align: "center",
    dataIndex: 'ltv44'
  },
  {
    title: 'LTV45',
    align: "center",
    dataIndex: 'ltv45'
  },
  {
    title: 'LTV46',
    align: "center",
    dataIndex: 'ltv46'
  },
  {
    title: 'LTV47',
    align: "center",
    dataIndex: 'ltv47'
  },
  {
    title: 'LTV48',
    align: "center",
    dataIndex: 'ltv48'
  },
  {
    title: 'LTV49',
    align: "center",
    dataIndex: 'ltv49'
  },
  {
    title: 'LTV50',
    align: "center",
    dataIndex: 'ltv50'
  },
  {
    title: 'LTV51',
    align: "center",
    dataIndex: 'ltv51'
  },
  {
    title: 'LTV52',
    align: "center",
    dataIndex: 'ltv52'
  },
  {
    title: 'LTV53',
    align: "center",
    dataIndex: 'ltv53'
  },
  {
    title: 'LTV54',
    align: "center",
    dataIndex: 'ltv54'
  },
  {
    title: 'LTV55',
    align: "center",
    dataIndex: 'ltv55'
  },
  {
    title: 'LTV56',
    align: "center",
    dataIndex: 'ltv56'
  },
  {
    title: 'LTV57',
    align: "center",
    dataIndex: 'ltv57'
  },
  {
    title: 'LTV58',
    align: "center",
    dataIndex: 'ltv58'
  },
  {
    title: 'LTV59',
    align: "center",
    dataIndex: 'ltv59'
  },
  {
    title: 'LTV60',
    align: "center",
    dataIndex: 'ltv60'
  },
  {
    title: 'LTV61',
    align: "center",
    dataIndex: 'ltv61'
  },
  {
    title: 'LTV62',
    align: "center",
    dataIndex: 'ltv62'
  },
  {
    title: 'LTV63',
    align: "center",
    dataIndex: 'ltv63'
  },
  {
    title: 'LTV64',
    align: "center",
    dataIndex: 'ltv64'
  },
  {
    title: 'LTV65',
    align: "center",
    dataIndex: 'ltv65'
  },
  {
    title: 'LTV66',
    align: "center",
    dataIndex: 'ltv66'
  },
  {
    title: 'LTV67',
    align: "center",
    dataIndex: 'ltv67'
  },
  {
    title: 'LTV68',
    align: "center",
    dataIndex: 'ltv68'
  },
  {
    title: 'LTV69',
    align: "center",
    dataIndex: 'ltv69'
  },
  {
    title: 'LTV670',
    align: "center",
    dataIndex: 'ltv70'
  },
  {
    title: 'LTV71',
    align: "center",
    dataIndex: 'ltv71'
  },
  {
    title: 'LTV72',
    align: "center",
    dataIndex: 'ltv72'
  },
  {
    title: 'LTV73',
    align: "center",
    dataIndex: 'ltv73'
  },
  {
    title: 'LTV74',
    align: "center",
    dataIndex: 'ltv74'
  },
  {
    title: 'LTV75',
    align: "center",
    dataIndex: 'ltv75'
  },
  {
    title: 'LTV76',
    align: "center",
    dataIndex: 'ltv76'
  },
  {
    title: 'LTV77',
    align: "center",
    dataIndex: 'ltv77'
  },
  {
    title: 'LTV78',
    align: "center",
    dataIndex: 'ltv78'
  },
  {
    title: 'LTV79',
    align: "center",
    dataIndex: 'ltv79'
  },
  {
    title: 'LTV80',
    align: "center",
    dataIndex: 'ltv80'
  },
  {
    title: 'LTV81',
    align: "center",
    dataIndex: 'ltv81'
  },
  {
    title: 'LTV82',
    align: "center",
    dataIndex: 'ltv82'
  },
  {
    title: 'LTV83',
    align: "center",
    dataIndex: 'ltv83'
  },
  {
    title: 'LTV84',
    align: "center",
    dataIndex: 'ltv84'
  },
  {
    title: 'LTV85',
    align: "center",
    dataIndex: 'ltv85'
  },
  {
    title: 'LTV86',
    align: "center",
    dataIndex: 'ltv86'
  },
  {
    title: 'LTV87',
    align: "center",
    dataIndex: 'ltv87'
  },
  {
    title: 'LTV88',
    align: "center",
    dataIndex: 'ltv88'
  },
  {
    title: 'LTV89',
    align: "center",
    dataIndex: 'ltv89'
  },
  {
    title: 'LTV90',
    align: "center",
    dataIndex: 'ltv90'
  },
  {
    title: 'LTV91',
    align: "center",
    dataIndex: 'ltv91'
  },
  {
    title: 'LTV92',
    align: "center",
    dataIndex: 'ltv92'
  },
  {
    title: 'LTV93',
    align: "center",
    dataIndex: 'ltv93'
  },
  {
    title: 'LTV94',
    align: "center",
    dataIndex: 'ltv94'
  },
  {
    title: 'LTV95',
    align: "center",
    dataIndex: 'ltv95'
  },
  {
    title: 'LTV96',
    align: "center",
    dataIndex: 'ltv96'
  },
  {
    title: 'LTV97',
    align: "center",
    dataIndex: 'ltv97'
  },
  {
    title: 'LTV98',
    align: "center",
    dataIndex: 'ltv98'
  },
  {
    title: 'LTV99',
    align: "center",
    dataIndex: 'ltv99'
  },
  {
    title: 'LTV100',
    align: "center",
    dataIndex: 'ltv100'
  },
  {
    title: 'LTV101',
    align: "center",
    dataIndex: 'ltv101'
  },
  {
    title: 'LTV102',
    align: "center",
    dataIndex: 'ltv102'
  },
  {
    title: 'LTV103',
    align: "center",
    dataIndex: 'ltv103'
  },
  {
    title: 'LTV104',
    align: "center",
    dataIndex: 'ltv104'
  },
  {
    title: 'LTV105',
    align: "center",
    dataIndex: 'ltv105'
  },
  {
    title: 'LTV106',
    align: "center",
    dataIndex: 'ltv106'
  },
  {
    title: 'LTV107',
    align: "center",
    dataIndex: 'ltv107'
  },
  {
    title: 'LTV108',
    align: "center",
    dataIndex: 'ltv108'
  },
  {
    title: 'LTV109',
    align: "center",
    dataIndex: 'ltv109'
  },
  {
    title: 'LTV110',
    align: "center",
    dataIndex: 'ltv110'
  },
  {
    title: 'LTV111',
    align: "center",
    dataIndex: 'ltv111'
  },
  {
    title: 'LTV112',
    align: "center",
    dataIndex: 'ltv112'
  },
  {
    title: 'LTV113',
    align: "center",
    dataIndex: 'ltv113'
  },
  {
    title: 'LTV114',
    align: "center",
    dataIndex: 'ltv114'
  },
  {
    title: 'LTV115',
    align: "center",
    dataIndex: 'ltv115'
  },
  {
    title: 'LTV116',
    align: "center",
    dataIndex: 'ltv116'
  },
  {
    title: 'LTV117',
    align: "center",
    dataIndex: 'ltv117'
  },
  {
    title: 'LTV118',
    align: "center",
    dataIndex: 'ltv118'
  },
  {
    title: 'LTV119',
    align: "center",
    dataIndex: 'ltv119'
  },
  {
    title: 'LTV120',
    align: "center",
    dataIndex: 'ltv120'
  },
  {
    title: 'LTV121',
    align: "center",
    dataIndex: 'ltv121'
  },
  {
    title: 'LTV122',
    align: "center",
    dataIndex: 'ltv122'
  },
  {
    title: 'LTV123',
    align: "center",
    dataIndex: 'ltv123'
  },
  {
    title: 'LTV124',
    align: "center",
    dataIndex: 'ltv124'
  },
  {
    title: 'LTV125',
    align: "center",
    dataIndex: 'ltv125'
  },
  {
    title: 'LTV126',
    align: "center",
    dataIndex: 'ltv126'
  },
  {
    title: 'LTV127',
    align: "center",
    dataIndex: 'ltv127'
  },
  {
    title: 'LTV128',
    align: "center",
    dataIndex: 'ltv128'
  },
  {
    title: 'LTV129',
    align: "center",
    dataIndex: 'ltv129'
  },
  {
    title: 'LTV130',
    align: "center",
    dataIndex: 'ltv130'
  },
  {
    title: 'LTV131',
    align: "center",
    dataIndex: 'ltv131'
  },
  {
    title: 'LTV132',
    align: "center",
    dataIndex: 'ltv132'
  },
  {
    title: 'LTV133',
    align: "center",
    dataIndex: 'ltv133'
  },
  {
    title: 'LTV134',
    align: "center",
    dataIndex: 'ltv134'
  },
  {
    title: 'LTV135',
    align: "center",
    dataIndex: 'ltv135'
  },
  {
    title: 'LTV136',
    align: "center",
    dataIndex: 'ltv136'
  },
  {
    title: 'LTV137',
    align: "center",
    dataIndex: 'ltv137'
  },
  {
    title: 'LTV138',
    align: "center",
    dataIndex: 'ltv138'
  },
  {
    title: 'LTV139',
    align: "center",
    dataIndex: 'ltv139'
  },
  {
    title: 'LTV140',
    align: "center",
    dataIndex: 'ltv140'
  },
  {
    title: 'LTV141',
    align: "center",
    dataIndex: 'ltv141'
  },
  {
    title: 'LTV142',
    align: "center",
    dataIndex: 'ltv142'
  },
  {
    title: 'LTV143',
    align: "center",
    dataIndex: 'ltv143'
  },
  {
    title: 'LTV144',
    align: "center",
    dataIndex: 'ltv144'
  },
  {
    title: 'LTV145',
    align: "center",
    dataIndex: 'ltv145'
  },
  {
    title: 'LTV146',
    align: "center",
    dataIndex: 'ltv146'
  },
  {
    title: 'LTV147',
    align: "center",
    dataIndex: 'ltv147'
  },
  {
    title: 'LTV148',
    align: "center",
    dataIndex: 'ltv148'
  },
  {
    title: 'LTV149',
    align: "center",
    dataIndex: 'ltv149'
  },
  {
    title: 'TV150',
    align: "center",
    dataIndex: 'ltv150'
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
   {
    label: "游戏",
    field: 'gameId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "子游戏",
    field: 'subGameId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "渠道游戏包",
    field: 'pkgId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "渠道ID",
    field: 'channelId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "广告ID",
    field: 'dealId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "用户ID",
    field: 'userId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "注册时间",
    field: 'registerTime',
    component: 'DatePicker',
    colProps: {span: 6},
  },
  {
    label: "最近登录时间",
    field: 'loginTime',
    component: 'DatePicker',
    colProps: {span: 6},
  },
  {
    label: "渠道类型id",
    field: 'channelTypeId',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "渠道子账号id",
    field: 'channelSubAccountId',
    component: 'Input',
    colProps: {span: 6},
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '游戏项目',
    field: 'gameId',
    component: 'InputNumber',
  },
  {
    label: '游戏名称',
    field: 'subGameId',
    component: 'InputNumber',
  },
  {
    label: '游戏包名',
    field: 'pkgId',
    component: 'InputNumber',
  },
  {
    label: '渠道类型',
    field: 'channelTypeId',
    component: 'InputNumber',
  },
  {
    label: '渠道名称',
    field: 'channelId',
    component: 'InputNumber',
  },
  {
    label: '二级渠道',
    field: 'channelSubAccountId',
    component: 'InputNumber',
  },
  {
    label: '广告列表',
    field: 'dealId',
    component: 'InputNumber',
  },
  {
    label: '起始日期',
    field: 'startTime',
    component: 'DatePicker',
  },
  {
    label: '结束日期',
    field: 'endTime',
    component: 'DatePicker',
  },
  {
    label: '账号人员',
    field: 'userType',
    component: 'Input',
  },
	// TODO 主键隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
];
