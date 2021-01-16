package WeekTwo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
* @program: Dispatch
* @Description: 调度程序
* @Author: SoCMo
* @Date: 2020/12/24
*/
public class Dispatch {
    public static final Integer PROCESS_NUM = 5;
    public static final Integer MAX_RESOURCE = 20;
    public static final Integer RESOURCE_TYPE = 5;
    public static List<Integer> resources = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("最大线程数为: " + PROCESS_NUM);
        System.out.println("最大资源数为: " + MAX_RESOURCE);
        Random random = new Random();
        List<Process> processList = new ArrayList<>();

        //生成资源数量
        for(int i = 0; i < RESOURCE_TYPE; i++)
            resources.add(random.nextInt(MAX_RESOURCE - 1) + 1);

        List<Integer> maxResources = new ArrayList<>(resources);

        //生成程序数量
        for(int i = 0; i < PROCESS_NUM; i++){
            processList.add(new Process());
        }

        show(processList);

        for(Process process : processList){
            for(int i = 0; i < RESOURCE_TYPE; i++){
                if(process.getMax().get(i) > maxResources.get(i)){
                    System.out.println("第" + (i + 1) + "个进程最大资源需求量超过系统资源数!");
                    return;
//                    processList.get(i).setIsEnd(true);
//                    for(int j = 0; j < RESOURCE_TYPE; j++){
//                        resources.set(j, resources.get(j) + processList.get(i).getConsumption().get(j));
//                    }
                }
            }
        }

        do{
            List<Integer> request = new ArrayList<>();
            System.out.println("请输入当前选择的进程编号:");
            int curProcess = scanner.nextInt() - 1;
            System.out.println("请输入每一种资源的请求数目：");
            for(int i = 0; i < RESOURCE_TYPE; i++){
                System.out.println("第" + (i+1) + "种资源(剩余" + resources.get(i) + ")：");
                request.add(scanner.nextInt());
            }

            Process nowProcess = processList.get(curProcess);
            if(Banker(request, processList, curProcess)){
                nowProcess.setIsEnd(true);
                for(int i = 0; i < RESOURCE_TYPE; i++){
                    nowProcess.getConsumption().set(i, nowProcess.getConsumption().get(i) + request.get(i));
                    resources.set(i, resources.get(i) - request.get(i));

                    if(nowProcess.getConsumption().get(i) < nowProcess.getMax().get(i)){
                        nowProcess.setIsEnd(false);
                    }
                }

                if(nowProcess.getIsEnd()){
                    for(int i = 0; i < RESOURCE_TYPE; i++){
                        resources.set(i, resources.get(i) + nowProcess.getConsumption().get(i));
                    }
                }
            }else{
                System.out.println("系统处于不安全状态");
            }
            show(processList);
        }while(!IsEnd(processList));
    }

    private static void show(List<Process> processList) {
        for(int i = 0; i < PROCESS_NUM; i++){
            System.out.println("第" + (i + 1) + "个进程" + (processList.get(i).getIsEnd() ? "已结束" : "还在运行"));
            for(int j = 0; j< RESOURCE_TYPE; j++){
                System.out.println("最大资源数:" + processList.get(i).getMax().get(j)
                        + " 已有资源数：" + processList.get(i).getConsumption().get(j)
                        + " 需要资源数：" + (processList.get(i).getMax().get(j) - processList.get(i).getConsumption().get(j)));
            }
            System.out.println("*************************************");
        }

        System.out.println("-------------------------------------------");
        for(int i = 0; i < RESOURCE_TYPE; i++){
            System.out.println("第" + (i+1) + "种资源剩余数目:" + resources.get(i));
        }
        System.out.println("==========================================");
    }

    private static Boolean IsEnd(List<Process> processList){
        boolean result = true;
        for(int i = 0; i < PROCESS_NUM; i++){
            if(!processList.get(i).getIsEnd()){
                result = false;
                break;
            }
        }
        return result;
    }

    private static Boolean Banker(List<Integer> request, List<Process> processList, Integer curProcess){
        boolean Legal = true;
        Process nowProcess = processList.get(curProcess);

        for(int i = 0 ;i < RESOURCE_TYPE; i++){
            if(request.get(i) > nowProcess.getMax().get(i) - nowProcess.getConsumption().get(i)){
                System.out.println("请求资源数大于该进程所需资源数");
                return false;
            }
        }

        for(int i = 0; i < RESOURCE_TYPE; i++){   //请求资源数大于剩余资源数
            if(request.get(i) > resources.get(i)){
                System.out.println("请求资源数大于剩余资源数");
                return false;
            }
        }

        List<Integer> tempResources = new ArrayList<>(resources);
        List<Process> tempProcessList = new ArrayList<>(processList);

        Process tempNowProcess = tempProcessList.get(curProcess);
        for(int i = 0; i < PROCESS_NUM; i++){
            tempNowProcess.getConsumption().set(i, tempNowProcess.getConsumption().get(i) + request.get(i));
            tempResources.set(i, tempResources.get(i) - request.get(i));
        }

        boolean IsFind;
        do{
            IsFind = false;
            int curRel = -1;
            for(int i = 0; i < PROCESS_NUM; i++){
                if(!tempProcessList.get(i).getIsEnd()){
                    boolean IsFF = true;
                    for(int j= 0; j < RESOURCE_TYPE;j++){
                        if(tempProcessList.get(i).getMax().get(j)
                                - tempProcessList.get(i).getConsumption().get(j) > tempResources.get(j)){
                            IsFF = false;
                            break;
                        }
                    }
                    IsFind = IsFF;
                    if(IsFind){
                        curRel = i;
                        break;
                    }
                }
            }
            if(IsFind){
                for(int i = 0; i < PROCESS_NUM; i++){
                    tempResources.set(i, tempResources.get(i) + tempProcessList.get(curRel).getConsumption().get(i));
                    tempProcessList.get(curRel).setIsEnd(true);
                    tempProcessList.get(curRel).getConsumption().set(i, 0);
                    tempProcessList.get(curRel).setIsEnd(true);
                }
            }
        }while(!IsEnd(tempProcessList) && IsFind);
        for(int i = 0; i < PROCESS_NUM; i++){
            if(!tempProcessList.get(i).getIsEnd()){
                Legal = false;
                break;
            }
        }
        return Legal;
    }
}

