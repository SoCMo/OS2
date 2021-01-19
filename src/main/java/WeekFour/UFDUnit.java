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
@NoArgsConstructor
public class UFDUnit {
    private Integer id;

    private String fileName;

    private String protectCode;

    private Integer fileLength;

    private static int COUNT = 0;

    public UFDUnit(String fileName, String protectCode, Integer fileLength) {
        this.id = ++COUNT;
        this.fileName = fileName;
        this.protectCode = protectCode;
        this.fileLength = fileLength;
    }
}
