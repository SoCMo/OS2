package WeekFour;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @program: UFDUnit
* @Description: User File Directory Unit
* @Author: SoCMo
* @Date: 2021/1/18
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UFDUnit {
    private String fileName;

    private String protectCode;

    private Integer fileLength;
}
