package org.jeecg.modules.game.common;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.game.entity.OpSubGame;

/**
 * @Author lili
 * @Description 返回前端对象
 * @Date 13:35 2022/12/16
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultGameAndSubGameList<T> {

    private List<T> gameList;

    private List<OpSubGame> subGameList;

}
