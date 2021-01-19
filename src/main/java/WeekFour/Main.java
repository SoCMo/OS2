package WeekFour;

import java.util.*;
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

        String userIn = scanner.nextLine();
        String finalUser = userIn;
        while (mfd.getMfdUnitList().stream()
                .noneMatch(mfdUnit -> mfdUnit.getUserName()
                        .equals(finalUser.trim()))){
            System.out.println("YOUR NAME IS NOT IN THE USER NAME TABLE,TRY AGAIN.");
            userIn = scanner.nextLine();
        }

        Optional<MFDUnit> mfdUnitTemp =
                mfd.getMfdUnitList().stream()
                        .filter(mfdUnit -> mfdUnit.getUserName()
                                .equals(finalUser))
                        .findFirst();
        if(mfdUnitTemp.isEmpty()){
            System.out.println("YOUR NAME IS NOT IN THE USER NAME TABLE,TRY AGAIN.");
            return;
        }

        MFDUnit mfdUnit = mfdUnitTemp.get();
        OFUnit[] ofUnits = new OFUnit[5];

        System.out.println("COMMAND NAME?");
        String command = scanner.nextLine();
        command = command.toUpperCase();
        while(!command.equalsIgnoreCase("BYE")){
            switch (command){
                case "CREATE": create(mfdUnit, ofUnits, scanner);
                                break;
                case "DELETE": delete(mfdUnit, ofUnits, scanner);
                                break;
                case "OPEN":   open(mfdUnit, ofUnits, scanner);
                                break;
                case "CLOSE":  close(ofUnits, scanner);
                                break;
                case "READ":   read(mfdUnit, ofUnits, scanner);
                                break;
                case "WRITE":  write(mfdUnit, ofUnits, scanner);
            }

            System.out.println("COMMAND NAME?");
            command = scanner.nextLine();
        }
    }

    private static void write(MFDUnit mfdUnit, OFUnit[] ofUnits, Scanner scanner) {
        System.out.println("OPEN FILE NUMBER?");
        int number = scanner.nextInt();
        if (number > 5 || number <= 0 || ofUnits[number] == null){
            if(number > 5 || number <= 0 ){
                System.out.println("ERROR NUMBER");
            }else {
                System.out.println("NUMBER HAS NOT BEEN USED!");
            }
            return;
        }

        if(ofUnits[number].getOpenCode().matches("^0..")){
            System.out.println("ERROR MESSAGE:IT IS NOT ALLOWED TO WRITE THIS FILE!");
            return;
        }

        System.out.println("HOW MANY CHARACTERS TO BE WRITTEN INTO THAT FILE?");
        int input = scanner.nextInt();
        mfdUnit.getUfd().getUfdUnitList()
                .stream()
                .filter(ufdUnit -> ufdUnit.getId().equals(ofUnits[number].getFileId()))
                .forEach(ufdUnit -> ufdUnit.setFileLength(ufdUnit.getFileLength() + input));

        System.out.println("WRITE SUCCESS");
    }

    private static void read(MFDUnit mfdUnit, OFUnit[] ofUnits, Scanner scanner) {
        System.out.println("FILE OPEN NUMBER TO BE READ?");
        int number = scanner.nextInt();
        if (number > 5 || number <= 0 || ofUnits[number] == null){
            if(number > 5 || number <= 0 ){
                System.out.println("ERROR NUMBER");
            }else {
                System.out.println("NUMBER HAS NOT BEEN USED!");
            }
            return;
        }

        if(ofUnits[number].getOpenCode().matches("^.0.")){
            System.out.println("ERROR MESSAGE:IT IS NOT ALLOWED TO READ THIS FILE!");
            return;
        }

        System.out.println("READ!!");
    }

    private static void close(OFUnit[] ofUnits, Scanner scanner) {
        System.out.println("FILE OPEN NUMBER TO BE OPENED?");
        int number = scanner.nextInt();
        if (number > 5 || number <= 0 || ofUnits[number] == null){
            if(number > 5 || number <= 0 ){
                System.out.println("ERROR NUMBER");
            }else {
                System.out.println("NUMBER HAS NOT BEEN USED!");
            }
            return;
        }

        ofUnits[number] = null;
        System.out.println("CLOSE SUCCESS!");
    }

    private static void open(MFDUnit mfdUnit, OFUnit[] ofUnits, Scanner scanner) {
        System.out.println("FILE NAME TO BE OPENED?");
        String name = scanner.nextLine();

        if(mfdUnit.getUfd().getUfdUnitList().stream()
                .noneMatch(ufdUnit -> ufdUnit.getFileName()
                        .equals(name.trim()))){
            System.out.println("NONE MATCH!");
            return;
        }

        UFDUnit ufdUnit = mfdUnit.getUfd().getUfdUnitList().stream()
                .filter(ufdUnitTemp -> ufdUnitTemp.getFileName()
                        .equals(name.trim()))
                .findFirst().get();

        if(ufdUnit.getProtectCode().matches("^.0.")){
            System.out.println("ERROR MESSAGE:IT IS NOT ALLOWED TO READ THIS FILE!");
            return;
        }

        if(Arrays.stream(ofUnits).anyMatch(ofUnit -> ofUnit.getFileId().equals(ufdUnit.getId()))){
            System.out.println("ALREADY RUNNING!");
            return;
        }

        System.out.println("ENTER THE OPEN MODE?");
        String code = scanner.nextLine();

        if(!code.matches("^[01]{3}")){
            System.out.println("PROTECTION CODE FORMAT ERROR!");
            return;
        }

        if(!code.matches("^" +
                ufdUnit.getProtectCode()
                        .replace("1", "."))){
            System.out.println("REJECT CODE");
            return;
        }

        System.out.println("THIS FILE IS OPENED,ITS OPEN NUMBER IS");
        int number = scanner.nextInt();
        while (number > 5 || number <= 0 || ofUnits[number] != null){
            if(number > 5 || number <= 0 ){
                System.out.println("ERROR NUMBER");
            }else {
                System.out.println("NUMBER HAS BEEN USED!");
            }
            System.out.println("TYPE AGAIN: ");
            number = scanner.nextInt();
        }

        ofUnits[number] = new OFUnit(ufdUnit.getId(), code);
        System.out.println("OPEN SUCCESS!");
    }

    private static void delete(MFDUnit mfdUnit, OFUnit[] ofUnits, Scanner scanner) {
        System.out.println("THE FILE'S NAME?");
        String name = scanner.nextLine();

        if(mfdUnit.getUfd().getUfdUnitList().stream()
                .noneMatch(ufdUnit -> ufdUnit.getFileName()
                        .equals(name.trim()))){
            System.out.println("NONE MATCH!");
            return;
        }

        UFDUnit ufdUnit = mfdUnit.getUfd().getUfdUnitList().stream()
                .filter(ufdUnitTemp -> ufdUnitTemp.getFileName()
                        .equals(name.trim()))
                .findFirst().get();

        if(ufdUnit.getProtectCode().matches("^0..")){
            System.out.println("ERROR MESSAGE:IT IS NOT ALLOWED TO DELETE THIS FILE!");
            return;
        }

        if(Arrays.stream(ofUnits).anyMatch(ofUnit -> ofUnit.getFileId().equals(ufdUnit.getId()))){
            System.out.println("ERROR MESSAGE:IT IS NOT ALLOWED TO DELETE RUNNING FILE!");
            return;
        }

        mfdUnit.getUfd().getUfdUnitList().remove(ufdUnit);
        System.out.println("DELETE SUCCESS!");
    }

    private static void create(MFDUnit mfdUnit, OFUnit[] ofUnits, Scanner scanner) {
        if(mfdUnit.getUfd().getUfdUnitList().size() > 10){
            System.out.println("UFD FULL!");
            return;
        }

        System.out.println("THE NEW FILE'S NAME(LESS THAN 9 CHARS)?");
        String name = scanner.nextLine();
        if(name.length() >= 9){
            System.out.println("NAME LENGTH IS TOO LONG!");
            return;
        }

        if(mfdUnit.getUfd().getUfdUnitList().stream()
                .noneMatch(ufdUnit -> ufdUnit.getFileName()
                        .equals(name.trim()))){
            System.out.println("ALREADY EXISTS!");
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

            System.out.println("TYPE AGAIN: ");
            number = scanner.nextInt();
        }

        ofUnits[number] = new OFUnit(number, openMode);
    }
}
