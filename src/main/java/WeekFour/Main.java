package WeekFour;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
* @program: Main
* @Description: 主函数
* @Author: SoCMo
* @Date: 2021/1/18
*/
public class Main {
    public static void main(String[] args) {
        MFD mfd = new MFD();
        mfd.setMfdUnitList(
                Arrays.asList(
                        new MFDUnit("zhangsan",
                                new UFD(
                                        Arrays.asList(
                                                new UFDUnit(
                                                        "zs_f1",
                                                        "111",
                                                        9999
                                                ),
                                                new UFDUnit(
                                                        "zs_f2",
                                                        "111",
                                                        0
                                                ),
                                                new UFDUnit(
                                                        "zs_f3",
                                                        "111",
                                                        100
                                                )
                                        )
                                )
                        ),
                        new MFDUnit("lisi",
                                new UFD(
                                        Arrays.asList(
                                                new UFDUnit(
                                                        "lisi_f1",
                                                        "101",
                                                        10
                                                ),
                                                new UFDUnit(
                                                        "lisi_f2",
                                                        "000",
                                                        9
                                                )
                                        )
                                )
                        )
                )
        );


        System.out.println("RUN");
        System.out.println("YOUR NAME?");
        Scanner scanner = new Scanner(System.in);

        String userTemp = scanner.nextLine();
        while (!mfd.getMfdUnitList().stream()
                .map(MFDUnit::getUserName).collect(Collectors.toList())
                .contains(userTemp.trim())){
            System.out.println("YOUR NAME IS NOT IN THE USER NAME TABLE,TRY AGAIN.");
            userTemp = scanner.nextLine();
        }

        String finalUser = userTemp;
        mfd.getMfdUnitList().stream().filter(mfdUnit -> mfdUnit.getUserName().equals(finalUser)).findFirst();
        OFUnit[] ofUnits = new OFUnit[5];
    }
}
