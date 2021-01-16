package WeekTwo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
* @program: Process
* @Description: 进程信息
* @Author: SoCMo
* @Date: 2020/12/24
*/
@Data
public class Process implements Runnable{
    private static int index = 0;

    private int id;

    private List<Integer> max;

    private List<Integer> consumption;

    private Boolean isEnd;

    @Override
    public void run() {

    }

    Process(){
        max = new ArrayList<>();
        consumption = new ArrayList<>();
        Random random = new Random();
        this.id = ++index;
        for(int i = 0; i < Dispatch.RESOURCE_TYPE; i++){
            this.max.add(random.nextInt(Dispatch.resources.get(i)));
            int min = Math.min(this.max.get(i), Dispatch.resources.get(i) / 2);
            this.consumption.add(random.nextInt(Math.max(min, 1)));
            Dispatch.resources.set(i, Dispatch.resources.get(i) - this.consumption.get(i));
        }
        this.isEnd = false;
    }
}
