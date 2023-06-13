package org.jeecg.modules.count.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.auth.In;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.count.bo.ReportAccountBillBo;
import org.jeecg.modules.count.bo.ReportAccountBo;
import org.jeecg.modules.count.bo.ReportAccountCostBo;
import org.jeecg.modules.count.bo.ReportBillCostBo;
import org.jeecg.modules.count.dto.ReportAccountDto;
import org.jeecg.modules.count.entity.OpReport;
import org.jeecg.modules.count.mapper.OpReportAccountMapper;
import org.jeecg.modules.count.modal.ReportAccountModal;
import org.jeecg.modules.count.service.IOpReportAccountService;
import org.jeecg.modules.count.vo.ReportAccountBillVo;
import org.jeecg.modules.count.vo.ReportAccountVo;
import org.jeecg.modules.count.vo.StatDealVo;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 数据报表
 * @Author: jeecg-boot
 * @Date:   2023-05-22
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpReportAccountServiceImpl extends ServiceImpl<OpReportAccountMapper, OpReport> implements
    IOpReportAccountService {

    private static final String ADMIN = "admin";
    @Resource
    private OpReportAccountMapper opReportAccountMapper;
    @Value("${jeecg.path.upload}")
    private String upLoadPath;
    @Override
    public List<ReportAccountVo> queryAccountList(ReportAccountDto reportAccountDto,String username) {
        QueryWrapper<ReportAccountDto> where = new QueryWrapper<>();
        QueryWrapper where2 = new QueryWrapper<>();
        List<ReportAccountVo>resList = new ArrayList<>();
        where.eq("a.state",1);
        if(ObjectUtils.isNotEmpty(reportAccountDto.getGameId())){
            where.in("d.game_id",reportAccountDto.getGameId());
        }
        if(ObjectUtils.isNotEmpty(reportAccountDto.getSubGameId())){
            where.in("d.sub_game_id",reportAccountDto.getSubGameId());
        }
        if(ObjectUtils.isNotEmpty(reportAccountDto.getPkgId())){
            where.in("d.pkg_id",reportAccountDto.getPkgId());
        }
        if(ObjectUtils.isNotEmpty(reportAccountDto.getChannelId())){
            where.in("a.channel_id",reportAccountDto.getChannelId());
        }
        if(ObjectUtils.isNotEmpty(reportAccountDto.getChannelSubAccountId())){
            where.in("d.channel_sub_account_id",reportAccountDto.getChannelSubAccountId());
        }
        if(ObjectUtils.isNotEmpty(reportAccountDto.getChannelTypeId())){
            where.in("d.channel_type_id",reportAccountDto.getChannelTypeId());
        }
        if(ObjectUtils.isNotEmpty(reportAccountDto.getAccountId())){
            where.in("a.id",reportAccountDto.getAccountId());
        }
        //进行权限校验或者别的
        if(username.equals(ADMIN)){
            if(ObjectUtils.isNotEmpty(reportAccountDto.getCreateUser())){
                //可能是put_user,但是此时这个表中没有user字段
                where.eq("d.create_by",reportAccountDto.getCreateUser());
            }
        }else{
            where.or().eq("a.put_user",username);
        }

        //1.获取账号充值资金与转出金额待定

        //展示账号列表
        List<ReportAccountBo>accountList = opReportAccountMapper.queryAccountList(where);
        //拿到对应的账号id
        List<Integer>accountListId = new ArrayList<>();
        for (ReportAccountBo  accountId: accountList) {
            accountListId.add(accountId.getId());
        }
        if(!accountListId.isEmpty()){
            where2.in("account_id",accountListId);
        }
        //获取投放成本
        List<ReportAccountCostBo>accountMoney = opReportAccountMapper.queryAccountCost(where2);
        //xinzeng
        Map<Integer, Map<String, Object>> costMoneyList = new HashMap<>();
        for (ReportAccountCostBo costMoney : accountMoney) {
            Map<String,Object>costInfo = new HashMap<>();
            costInfo.put("money",costMoney.getCostMoney());
            costInfo.put("exhibition",costMoney.getExhibition());
            costInfo.put("download",costMoney.getDownload());
            costInfo.put("updateTime",costMoney.getUpdateTime());
            Integer accountId = costMoney.getAccountId();
            if(accountId!=null && !accountId.equals(0)){
            costMoneyList.put(accountId,costInfo);
            }
        }
        //count_data,那些值默认都是0
        Map<String, BigDecimal>countData = new HashMap<>();
        countData.put("cost_money", BigDecimal.ZERO);
        countData.put("surplus_amount", BigDecimal.ZERO);
        countData.put("exhibition", BigDecimal.ZERO);
        countData.put("download", BigDecimal.ZERO);
        //获取当前账户的余额
        if(!accountList.isEmpty()){
            for (ReportAccountBo reportAccountBo : accountList) {
                ReportAccountVo reportAccountVo = new ReportAccountVo();
                reportAccountVo.setCreateTime(DateUtil.date(reportAccountBo.getCreateTime()));
                //账号
                reportAccountVo.setAccount(reportAccountBo.getAccount());
                reportAccountVo.setId(reportAccountBo.getId());
                //账号昵称
                reportAccountVo.setNickName(reportAccountBo.getNickName());
                //游戏
                reportAccountVo.setSubGameIds(reportAccountBo.getSubGameIds());
                //渠道
                reportAccountVo.setChannelName(reportAccountBo.getChannelName());
                //投放消耗
                Integer accountIds = reportAccountBo.getId();
                if(accountIds!=null && costMoneyList.containsKey(accountIds)){
                    reportAccountVo.setOutCostMoney(BigDecimal.valueOf(Double.parseDouble(costMoneyList.get(reportAccountBo.getId()).get("money").toString())));
                }else{
                    reportAccountVo.setOutCostMoney(BigDecimal.ZERO);
                }
                if(accountIds!=null && costMoneyList.containsKey(accountIds)){
                    reportAccountVo.setExhibition(BigDecimal.valueOf(Double.parseDouble(costMoneyList.get(reportAccountBo.getId()).get("exhibition").toString())));
                }else{
                    reportAccountVo.setExhibition(BigDecimal.ZERO);
                }
                if(accountIds!=null && costMoneyList.containsKey(accountIds)){
                    reportAccountVo.setDownload(BigDecimal.valueOf(Double.parseDouble(costMoneyList.get(reportAccountBo.getId()).get("download").toString())));
                }else{
                    reportAccountVo.setDownload(BigDecimal.ZERO);
                }
                reportAccountVo.setSurplusAmount(reportAccountVo.getOutCostMoney().negate());
                //负责人
                reportAccountVo.setPrincipalUser(reportAccountBo.getCreateUser());
                //创建时间、更新时间
                reportAccountVo.setCreateTime(reportAccountBo.getCreateTime());
                reportAccountVo.setUpdateTime(reportAccountBo.getCreateTime());
                resList.add(reportAccountVo);
                //计算合计
                countData.put("cost_money", countData.get("cost_money").add(reportAccountVo.getOutCostMoney()));
                countData.put("surplus_amount", countData.get("surplus_amount").add(reportAccountVo.getSurplusAmount()));
                countData.put("exhibition", countData.get("exhibition").add(reportAccountVo.getExhibition()));
                countData.put("download", countData.get("download").add(reportAccountVo.getDownload()));
            }
            ReportAccountVo total = new ReportAccountVo();
            total.setNickName("合计数据");
            total.setOutCostMoney(countData.get("cost_money"));
            total.setSurplusAmount(countData.get("surplus_amount"));
            total.setExhibition(countData.get("exhibition"));
            resList.add(total);
        }
        return resList;
    }

    @Override
    public List<ReportAccountBillVo> queryBillByAccountId(Integer id) {
        List<ReportAccountBillVo>list = new ArrayList<>();
        //根据op_put_account表查询-缺少返利点的表
        ReportAccountBillBo billBoList= opReportAccountMapper.queryBillByAccountId(id);
        //查询账号充值--表展示没有
        //获取投放成本
        List<ReportBillCostBo>costMoneyList = opReportAccountMapper.queryBillCostById(id);
        if(costMoneyList!=null){
            for (ReportBillCostBo reportBillCostBo : costMoneyList) {
                ReportAccountBillVo reportAccountBillVo = new ReportAccountBillVo();
                //时间转换成对应的
                reportAccountBillVo.setDateTime(DateUtil.format(reportBillCostBo.getDateTime(),"yyyy-MM-dd"));
                //类型
                if(id==null){
                    //account_id2为空，自定义字段的显示
                    reportAccountBillVo.setType("<span style=\"color: #0aad0a\">投放消耗</span>");
                }else{
                    reportAccountBillVo.setType("投放消耗");
                }
                reportAccountBillVo.setCost(reportBillCostBo.getMoney());
                String gameName = null;
                if(reportBillCostBo.getSubGameId()== null){
                    gameName = opReportAccountMapper.getNameByGameId(reportBillCostBo.getGameId());
                }else{
                    gameName = opReportAccountMapper.getNameBySubGameId(reportBillCostBo.getSubGameId());
                }
                reportAccountBillVo.setOtherData("游戏: "+gameName +" 展示: " +reportBillCostBo.getExhibition() + " 下载: " + reportBillCostBo.getDownload());
                //昵称
                reportAccountBillVo.setNickName(billBoList.getNickName());
                //账号
                reportAccountBillVo.setAccount(billBoList.getAccount());
                //创建人
                reportAccountBillVo.setCreateUser(billBoList.getCreateUser());
                reportAccountBillVo.setDesc(reportAccountBillVo.getDesc());
                list.add(reportAccountBillVo);
            }
        }
        if(list!=null && list.size()>0){
            BigDecimal temp = BigDecimal.ZERO;
            for (ReportAccountBillVo reportAccountBillVo : list) {
                temp.add(reportAccountBillVo.getCost());
                reportAccountBillVo.setSurplusAmount(temp.negate());
            }
            Collections.reverse(list);
        }
        return list;
    }

    @Override
    public ModelAndView exportExcel(HttpServletRequest request, ReportAccountDto reportAccountDto,
        Class<ReportAccountModal> reportAccountVoClass, String title) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String  username = JwtUtil.getUserNameByToken(request);
        // Step.2 获取导出数据
        List<ReportAccountVo> resultList = this.queryAccountList(reportAccountDto,username);
        List<ReportAccountModal>exportList = new ArrayList<>();
        for (ReportAccountVo reportAccountVo : resultList) {
            //拼接游戏和子游戏
            String gameSubGameIds = reportAccountVo.getSubGameIds();
            ObjectMapper objectMapper = new ObjectMapper();
            if(gameSubGameIds!=null){
                try {
                    List<Map<String, Object>> list = objectMapper.readValue(gameSubGameIds,
                        new TypeReference<List<Map<String, Object>>>() {
                        });
                    StringBuilder result = new StringBuilder();
                    for (Map<String, Object> item : list) {
                        String gameName = " ";
                        String subGameName =" ";
                        if(item.containsKey("gameId") && item.get("gameId")!=null){
                            int gameId = Integer.parseInt(String.valueOf(item.get("gameId"))) ;
                            gameName = opReportAccountMapper.getNameByGameId(gameId);
                        }
                        if(item.containsKey("subGameId") && item.get("subGameId")!=null){
                            int subGameId = Integer.parseInt(String.valueOf(item.get("subGameId")));
                            subGameName = opReportAccountMapper.getNameBySubGameId(subGameId);
                        }
                        result.append("游戏：").append(gameName).append("<=>子游戏：").append(subGameName).append(",");
                        String finalResult = result.substring(0,result.length()-1);
                        ReportAccountModal reportAccountModal = new ReportAccountModal();
                        BeanUtils.copyProperties(reportAccountVo,reportAccountModal);
                        reportAccountModal.setSubGameIds(finalResult);
                        exportList.add(reportAccountModal);
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            if(gameSubGameIds==null){
                ReportAccountModal reportAccountModal = new ReportAccountModal();
                BeanUtils.copyProperties(reportAccountVo,reportAccountModal);
                exportList.add(reportAccountModal);
            }
        }
        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.FILE_NAME, title);
        mv.addObject(NormalExcelConstants.CLASS, reportAccountVoClass);
        //update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
        ExportParams exportParams=new ExportParams(title + "报表", "导出人:" + sysUser.getRealname(), title);
        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
        mv.addObject(NormalExcelConstants.PARAMS,exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }
}
