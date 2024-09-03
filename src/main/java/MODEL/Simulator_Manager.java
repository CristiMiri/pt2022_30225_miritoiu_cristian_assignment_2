package MODEL;

import VIEW.Process_frame;
import VIEW.Simulation_Frame;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@SuppressWarnings({"unused", "StringConcatenationInsideStringBufferAppend", "BusyWait"})
public class Simulator_Manager implements Runnable {
    private int nr_Clients;
    private int nr_Queues;
    private int Max_Simulator_time;
    private int min_arrival_time;
    private int max_arrival_time;
    private int min_service_time;
private int max_service_time;
    private BlockingQueue<Client> clients;
    private float average_waiting_time;
    private float average_service_time;
    private Scheduler schedule;
    private FileWriter writer;
    Simulation_Frame s;

    public Simulator_Manager(int nr_Clients, int nr_Queues,int Max_Simulator_time, int min_arrival_time, int max_arrival_time, int min_service_time, int max_service_time,Strategy strategy) {
        try {
            writer=new FileWriter("logs.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.nr_Clients = nr_Clients;
        this.nr_Queues = nr_Queues;
        this.Max_Simulator_time=Max_Simulator_time;
        this.min_arrival_time = min_arrival_time;
        this.max_arrival_time = max_arrival_time;
        this.min_service_time = min_service_time;
        this.max_service_time = max_service_time;
        schedule = new Scheduler(nr_Queues,writer,strategy);
        s=new Simulation_Frame(this);
        s.setVisible(true);
    }

    public void generate_clients() {
        average_waiting_time =0;
        average_service_time=0;
        Random r = new Random();
        clients = new LinkedBlockingQueue<>();
        ArrayList<Client> aux = new ArrayList<>();
        for (int i = 0; i < nr_Clients; i++) {
            int r_arrival = r.nextInt(max_arrival_time - min_arrival_time) + min_arrival_time;
            int r_service = r.nextInt(max_service_time - min_service_time) + min_service_time;
            average_service_time+=r_service;
            average_waiting_time =r_arrival;
            int Id = r.nextInt(100);
            Client c = new Client(Id, r_arrival, r_service);
            aux.add(c);
        }
        Collections.sort(aux);
        average_service_time/=this.nr_Clients;
        average_waiting_time /=this.nr_Clients;
        clients.addAll(aux);
    }
    public void print(int current_time)
    {
        System.out.println("Time: " + current_time);
        System.out.println("Waiting clients: "+clients);
        System.out.println();
    }
    public String to_Interface()
    {
        String aux="";
        String buffer;
        for(Client i:clients)
            aux=aux+i.toInterface();
        return "Waiting clients: "+aux+"\n";
    }
    public String state()
    {
        String aux="";
        for(Server i:schedule.getServers()) {
            aux=aux+i.to_Interface()+"\n";
        }
        return aux;
    }
    public void write_to_file(int current_time)
    {

        try {
            writer.append("Time: " + current_time +"\n");
            writer.append("Waiting clients: "+clients+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        generate_clients();
        int current_time = 0;
        int speed=1;
        while (current_time <= this.Max_Simulator_time) {
            try {
                Client c = clients.peek();
                while (c!=null &&  c.getArrival_time()== current_time) {
                    schedule.send_client(clients);
                    c = clients.peek();
                }
                Thread.sleep(1000);
                write_to_file(current_time);
                print(current_time);
                s.update(current_time);
                current_time++;
                schedule.wake_all();
                Thread.sleep(1000*speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        schedule.stop_all();
        schedule.wake_all();
        s.ending(average_waiting_time,average_service_time);
        try {
            writer.append("Average waiting time:"+this.average_waiting_time+"\n");
            writer.append("Average service time:"+this.average_service_time+"\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int getNr_Clients() {
        return nr_Clients;
    }

    public void setNr_Clients(int nr_Clients) {
        this.nr_Clients = nr_Clients;
    }

    public int getNr_Queues() {
        return nr_Queues;
    }

    public void setNr_Queues(int nr_Queues) {
        this.nr_Queues = nr_Queues;
    }

    public int getMax_Simulator_time() {
        return Max_Simulator_time;
    }

    public void setMax_Simulator_time(int max_Simulator_time) {
        Max_Simulator_time = max_Simulator_time;
    }

    public int getMin_arrival_time() {
        return min_arrival_time;
    }

    public void setMin_arrival_time(int min_arrival_time) {
        this.min_arrival_time = min_arrival_time;
    }

    public int getMax_arrival_time() {
        return max_arrival_time;
    }

    public void setMax_arrival_time(int max_arrival_time) {
        this.max_arrival_time = max_arrival_time;
    }

    public int getMin_service_time() {
        return min_service_time;
    }

    public void setMin_service_time(int min_service_time) {
        this.min_service_time = min_service_time;
    }

    public int getMax_service_time() {
        return max_service_time;
    }

    public void setMax_service_time(int max_service_time) {
        this.max_service_time = max_service_time;
    }

    public BlockingQueue<Client> getClients() {
        return clients;
    }

    public void setClients(BlockingQueue<Client> clients) {
        this.clients = clients;
    }

    public float getAverage_waiting_time() {
        return average_waiting_time;
    }

    public void setAverage_waiting_time(float average_waiting_time) {
        this.average_waiting_time = average_waiting_time;
    }

    public float getAverage_service_time() {
        return average_service_time;
    }

    public void setAverage_service_time(float average_service_time) {
        this.average_service_time = average_service_time;
    }

    public Scheduler getSchedule() {
        return schedule;
    }

    public void setSchedule(Scheduler schedule) {
        this.schedule = schedule;
    }

    public FileWriter getWriter() {
        return writer;
    }

    public void setWriter(FileWriter writer) {
        this.writer = writer;
    }
}
