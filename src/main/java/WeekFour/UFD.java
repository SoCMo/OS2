package WeekFour;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
* @program: UFD
* @Description: User File Directory
* @Author: SoCMo
* @Date: 2021/1/18
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UFD {
    private List<UFDUnit> ufdUnitList;
}
