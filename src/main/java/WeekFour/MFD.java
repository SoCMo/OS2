package WeekFour;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
* @program: MFD
* @Description: Master File Directory
* @Author: SoCMo
* @Date: 2021/1/18
*/
@Data
@NoArgsConstructor
public class MFD {
    private List<MFDUnit> mfdUnitList;
}
