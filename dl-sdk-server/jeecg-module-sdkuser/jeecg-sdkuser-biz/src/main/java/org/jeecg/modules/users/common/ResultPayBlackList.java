package org.jeecg.modules.users.common;


import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.users.vo.OpPayBlackVo;

/**
 * @Author lili
 * @Description 返回前端对象
 * @Date 13:35 2022/12/16
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultPayBlackList<T> {

    private IPage<OpPayBlackVo> pageList;

    private List<OpPayBlackVo> list;

}
