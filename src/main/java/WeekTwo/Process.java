package WeekTwo;

import lombok.Data;

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

    private int need;

    private int consumption;

    @Override
    public void run() {

    }

    Process(){
        Random random = new Random();
        this.id = ++index;
        this.need = random.nextInt(Dispatch.getResources() + 1);
        this.consumption = 0;
    }
}
