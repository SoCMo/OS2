package WeekTwo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
* @program: Dispatch
* @Description: 调度程序
* @Author: SoCMo
* @Date: 2020/12/24
*/
public class Dispatch {
    private static final int MAX_PROCESS = 5;
    private static final int MAX_RESOURCE = 20;
    private static int resources;

    public static void main(String[] args) {
        Random random = new Random();
        List<Process> processList = new ArrayList<>();

        //生成资源数量
        resources = random.nextInt(MAX_RESOURCE);

        //生成程序数量
        int processNum = random.nextInt(MAX_PROCESS);
        for(int i = 0; i < processNum; i++){
            processList.add(new Process());
        }

        for(Process process : processList){
            if(process.getNeed() - process.getConsumption() > resources){
                System.out.println("最大资源需求量超过系统资源数!");
            }
        }


    }

    public static int getResources() {
        return resources;
    }
}
