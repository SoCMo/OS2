package WeekThree;

import java.util.*;

public class pageManagement {
    private static final int ORDER_NUM = 256;
    private static final int VIRTUAL_SIZE = 32 * 1024;
    private static final int END_SIZE = 8 * 1024;
    private static final int MIN_ACTUALPAGE = 4;
    private static final int MAX_ACTUALPAGE = 32;

    public static void main(String[] args) {
        List<Integer> orderList = new ArrayList<>();

        Random random = new Random();
        int now = random.nextInt(VIRTUAL_SIZE / 2);
        orderList.add(now);

        for(int i = 0; i < ORDER_NUM; i++){
            double dice = random.nextDouble();
            if(dice < 0.5){
                orderList.add(++now);
            }else if(dice < 0.75){
                now = random.nextInt(VIRTUAL_SIZE / 2);
                orderList.add(now);
            }else {
                now = random.nextInt(VIRTUAL_SIZE / 2) + VIRTUAL_SIZE / 2;
                orderList.add(now);
            }
        }

        System.out.println("THE VIRTUAL ADDRESS STREAM AS FOLLOWS:");
        int i = 0;
        for(; i < orderList.size() - 4; i += 4){
            System.out.println("a[" + i + "] = " + orderList.get(i) + " \t"
                                + "a[" + (i + 1) + "] = " + orderList.get(i + 1) + " \t"
                                + "a[" + (i + 2) + "] = " + orderList.get(i + 2) + " \t"
                                + "a[" + (i + 3) + "] = " + orderList.get(i + 3) + " \t");
        }
        for(;i<orderList.size(); i++){
            System.out.print("a[" + i + "] = " + orderList.get(i) + "\t");
        }
        System.out.println();
        System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");

        System.out.println("选择算法: 1.OPT; 2.LRU");
        Scanner scanner = new Scanner(System.in);
        int algorithm = scanner.nextInt();
        switch (algorithm) {
            case 0:
                OPT(orderList);
                break;
            case 1:
                LRU(orderList);
            default:
                return;
        }
    }

    private static void LRU(List<Integer> orderList) {
        System.out.println("The algorithm is: LRU");
        int nowSize = 1024;
        while(nowSize <= END_SIZE){
            show(orderList, nowSize);
            for(int actualPage = MIN_ACTUALPAGE; actualPage <= MAX_ACTUALPAGE; actualPage += 2){
                int target = 0;
                System.out.print(actualPage + " \t\t\t\t\t\t ");
                Map<Integer, Integer> actualMap = new HashMap<>();
                for(int order = 0; order < orderList.size(); order++){
                    int page = orderList.get(order) / nowSize + 1;
                    if(actualMap.containsKey(page)){
                        actualMap.replace(page, actualMap.get(page) + 1);
                        target++;
                    }else if(actualMap.size() < actualPage){
                        actualMap.put(page, 0);
                    }else{
                        int min = 9999, minKey = -1;
                        for(Map.Entry<Integer, Integer> entry :actualMap.entrySet()){
                            if(entry.getValue() < min){
                                min = entry.getValue();
                                minKey = entry.getKey();
                            }
                        }
                        actualMap.remove(minKey);
                        actualMap.put(page, 0);
                    }
                }

                Double result = (double)target / ORDER_NUM;
                System.out.println(result);
            }
            nowSize *= 2;
        }
    }

    private static void OPT(List<Integer> orderList) {
        System.out.println("The algorithm is: OPT");
        int nowSize = 1024;
        while(nowSize <= END_SIZE){
            show(orderList, nowSize);
            int i;
            for(int actualPage = MIN_ACTUALPAGE; actualPage <= MAX_ACTUALPAGE; actualPage += 2){
                int target = 0;
                System.out.print(actualPage + " \t\t\t\t\t\t ");
                List<Integer> actualList = new ArrayList<>();
                for(int order = 0; order < orderList.size(); order++){
                    int page = orderList.get(order) / nowSize + 1;
                    if(actualList.contains(page)){
                        target++;
                    }else if(actualList.size() < actualPage){
                        actualList.add(page);
                    }else{
                        int[] flag = new int[actualPage];
                        for(int p = order + 1; p < orderList.size(); p++){
                            int pPage = orderList.get(p) / nowSize + 1;
                            int find = actualList.indexOf(pPage);
                            if(find == actualPage){
                                System.out.println("!!!");
                            }
                            if(find != -1 && flag[find] != 0){
                                flag[find] = p;
                            }
                        }

                        int max = 0, maxIndex = -1;
                        for(i = 0; i < actualPage; i++){
                            if(flag[i] == 0){
                                maxIndex = i;
                                break;
                            }
                            if(flag[i] >= max){
                                max = flag[i];
                                maxIndex = i;
                            }
                        }

                        actualList.set(maxIndex, page);
                    }
                }

                Double result = (double)target / ORDER_NUM;
                System.out.println(result);
            }
            nowSize *= 2;
        }
    }

    private static void show(List<Integer> orderList, int nowSize) {
        System.out.println("PAGE NUMBER WITH SIZE " + (nowSize / 1024) + "k FOR EACH ADDRESS IS:");
        int i = 0;
        for(; i < orderList.size() - 4; i += 4){
            System.out.println("pageno[" + i + "] = " + (orderList.get(i) / nowSize + 1) + " \t"
                    + "pageno[" + (i + 1) + "] = " + (orderList.get(i + 1) / nowSize + 1) + " \t"
                    + "pageno[" + (i + 2) + "] = " + (orderList.get(i + 2) / nowSize + 1) + " \t"
                    + "pageno[" + (i + 3) + "] = " + (orderList.get(i + 3) / nowSize + 1) + " \t");
        }
        for(;i<orderList.size(); i++){
            System.out.print("pageno[" + i + "] = " + (orderList.get(i) / nowSize + 1) + "\t");
        }
        System.out.println();
        System.out.println();

        System.out.println("vmsize = " + (VIRTUAL_SIZE / 1024) + "k pagesize = " + (nowSize / 1024) + "k");
        System.out.println("------------- --------------");
        System.out.println("page assigned \t\t\t pages_in/total references");
    }
}
