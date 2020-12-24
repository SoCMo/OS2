package WeekOne;

import lombok.Data;

import java.util.Random;

enum Status{W, R, F};

@Data
public class PCB implements Comparable<PCB>{
    private static int nowKey = 0;

    private int id;

    private PCB next;

    private int weight;

    private int costTime;

    private int needTime;

    private Status status;

    PCB() {
        this(++nowKey);
    }

    PCB(int id){
        Random random = new Random();
        this.id = id;
        this.next = null;
        this.weight = random.nextInt(30) + 60;
        this.costTime = 0;
        this.needTime = random.nextInt(20);
        this.status = Status.W;
    }

    @Override
    public int compareTo(PCB pcb) {
        int result = this.weight - pcb.weight;
        if(result == 0) return 0;
        return -1 * result / Math.abs(result);
    }

    public int insert(PCB p, PCB input){
        if(p.weight < input.weight){
            input.next = p;
            return 0;
        }

        while(p.next != null){
            if(p.next.weight < input.weight){
                input.next = p.next;
                p.next = input;
                return 1;
            }
        }

        p.next = input;
        return 2;
    }
}
