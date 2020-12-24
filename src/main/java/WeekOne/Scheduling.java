                                                                                                             package WeekOne;

import java.lang.reflect.Field;
import java.util.*;

public class Scheduling {
    private static final int PCB_NUM = 5;

    public static void main(String[] args) {
        System.out.println("TYPE THE ALGORITHM:");
        Scanner scanner = new Scanner(System.in);
        String algorithm = scanner.nextLine().toUpperCase();
        if (algorithm.contains("PRIORITY")) {
            priority();
        } else if (algorithm.contains("ROUND")) {
            round();
        } else {
            System.out.println("Wrong Type!");
        }
        System.out.println("SYSTEM FINISHED");
    }

    private static void round() {
        PCB run = null;

        Queue<PCB> PCBQueue = new LinkedList<>();
        List<PCB> PCBList = new ArrayList<>();
        for (int i = 0; i < PCB_NUM; i++) {
            PCB temp = new PCB();
            temp.setWeight((temp.getWeight() - 60) / 3);
            PCBQueue.add(temp);
            PCBList.add(temp);
        }

        run = PCBQueue.poll();
        run.setStatus(Status.R);

        int now = 0;
        while(!PCBQueue.isEmpty() || run.getStatus() != Status.F){
            run.setCostTime(run.getCostTime() + 1);
            now++;

            if(run.getCostTime() >= run.getNeedTime()){
                run.setStatus(Status.F);
                if(!PCBQueue.isEmpty()){
                    run = PCBQueue.poll();
                    run.setStatus(Status.R);
                    now = 0;
                }
            }else if(now < run.getWeight()){}
            else {
                if(!PCBQueue.isEmpty()) {
                    run.setStatus(Status.W);
                    PCBQueue.add(run);
                    run = PCBQueue.poll();
                    run.setStatus(Status.R);
                }
                now = 0;
            }
            show(run, PCBQueue, PCBList);
        }
    }

    private static void show(PCB run, Queue<PCB> pcbQueue, List<PCB> PCBList) {
        System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
        System.out.println("OUTPUT OF ROUND");
        System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
        System.out.print("RUNNING PROC. " + run.getId());
        System.out.println();
        System.out.print("WAITING QUEUE: ");
        pcbQueue.forEach(pcb -> System.out.print(pcb.getId() + " "));
        System.out.println();
        System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");

        Class<PCB> pcbClass = PCB.class;
        Arrays.asList(new String[]{"id", "weight", "costTime", "needTime", "status"})
                .forEach((attribute) -> {
                    try {
                        System.out.printf("%10s\t\t", attribute);
                        Field field = pcbClass.getDeclaredField(attribute);
                        field.setAccessible(true);
                        for(int i = 0; i < PCB_NUM; i++){
                            System.out.print(field.get(PCBList.get(i)) + "\t");
                        }
                        System.out.println();
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                });
        System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = =\n");
    }

    private static void priority() {
        PCB run = null;

        List<PCB> PCBList = new ArrayList<PCB>();
        for (int i = 0; i < PCB_NUM; i++) {
            PCBList.add(new PCB());
        }

        PCBList.sort(PCB::compareTo);
        PCB head = PCBList.get(0);
        PCB temp = head;
        for (int i = 0; i < PCB_NUM - 1; i++) {
            temp.setNext(PCBList.get(i + 1));
            temp = temp.getNext();
        }

        run = head;
        head = head.getNext();
        run.setStatus(Status.R);
        run.setNext(null);
        while(run != null){
            run.setCostTime(run.getCostTime() + 1);
            run.setWeight(run.getWeight() - 3);
            if(run.getCostTime() >= run.getNeedTime()){
                run.setStatus(Status.F);
                run = head;
                if(head != null){
                    head = head.getNext();
                    run.setNext(null);
                    run.setStatus(Status.R);
                }
            }else if (head == null || run.getWeight() >= head.getWeight()){
            }else if(run.getWeight() < head.getWeight()){
                PCB p = head;
                for(;p.getNext() != null && p.getNext().getWeight() >= run.getWeight();p = p.getNext());
                run.setStatus(Status.W);
                if(p.getNext() != null){
                    run.setNext(p.getNext());
                }
                p.setNext(run);

                run = head;
                head = head.getNext();
                run.setStatus(Status.R);
                run.setNext(null);
            }
            show(PCBList, head);
        }
    }

    private static void show(List<PCB> PCBList, PCB head){
        System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
        System.out.println("OUTPUT OF PRIORITY");
        System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
        System.out.print("RUNNING PROC.");
        PCBList.stream().filter(pcb -> pcb.getStatus() == Status.R).forEach(pcb -> System.out.println(pcb.getId()));
        System.out.print("WAITING QUEUE: ");
        while (head != null){
            System.out.print(head.getId() + " ");
            head = head.getNext();
        }
        System.out.println();
        System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
        Class<PCB> pcbClass = PCB.class;
        Arrays.asList(new String[]{"id", "weight", "costTime", "needTime", "status"})
                .forEach((attribute) -> {
                    try {
                        System.out.printf("%10s\t\t", attribute);
                        Field field = pcbClass.getDeclaredField(attribute);
                        field.setAccessible(true);
                        for(int i = 0; i < PCB_NUM; i++){
                            System.out.print(field.get(PCBList.get(i)) + "\t");
                        }
                        System.out.println();
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                });
        System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = =\n");
    }
}
