package WeekFour;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        Optional<MFDUnit> mfdUnitTemp = mfd.getMfdUnitList().stream().filter(mfdUnit -> mfdUnit.getUserName().equals(finalUser)).findFirst();
        if(mfdUnitTemp.isEmpty()){
            System.out.println("YOUR NAME IS NOT IN THE USER NAME TABLE,TRY AGAIN.");
            return;
        }

        MFDUnit mfdUnit = mfdUnitTemp.get();
        OFUnit[] ofUnits = new OFUnit[5];

        System.out.println("COMMAND NAME?");
        String command = scanner.nextLine();
        while(!command.equalsIgnoreCase("BYE")){
            switch (command){
                case "CREATE": create(mfdUnit, ofUnits, scanner);
                                break;

            }

            System.out.println("COMMAND NAME?");
            command = scanner.nextLine();
        }
    }

    private static void create(MFDUnit mfdUnit, OFUnit[] ofUnits, Scanner scanner) {
        if(mfdUnit.getUfd().getUfdUnitList().size() > 10){
            System.out.println("UFD FULL!");
            return;
        }

        System.out.println("THE NEW FILE S NAME(LESS THAN 9 CHARS)?");
        String name = scanner.nextLine();
        if(name.length() >= 9){
            System.out.println("NAME LENGTH IS TOO LONG!");
            return;
        }

        System.out.println("THE NEW FILE’S PROTECTION CODE?");
        String protectCode = scanner.nextLine();
        if(!protectCode.matches("[01]{3}")){
            System.out.println("PROTECTION CODE FORMAT ERROR!");
            return;
        }

        UFDUnit ufdUnit = new UFDUnit(name, protectCode, 0);
        mfdUnit.getUfd().getUfdUnitList().add(ufdUnit);
        System.out.println("THE NEW FILE IS CREATED.");

        System.out.println("ENTER THE OPEN MODE?");
        String openMode = scanner.nextLine();
        if(!openMode.matches("[01]{3}")){
            System.out.println("OPEN MODE FORMAT ERROR!");
            return;
        }

        System.out.println("THIS FILE IS OPENED,ITS OPEN NUMBER IS");
        int number = scanner.nextInt();
        while(number > 5 || number <= 0 || ofUnits[number] != null){
            System.out.println("OPEN NUMBER ERROR!");
            number = scanner.nextInt();
        }

        ofUnits[number] = new OFUnit(number, openMode);
    }
}
