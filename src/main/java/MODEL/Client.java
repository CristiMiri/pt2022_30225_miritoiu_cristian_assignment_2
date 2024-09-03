package MODEL;

@SuppressWarnings("unused")
public class Client implements Comparable<Client>{
    private int Id;
    private int arrival_time;
    private int service_time;

    public Client(int id, int arrival_time, int service_time) {
        Id = id;
        this.arrival_time = arrival_time;
        this.service_time = service_time;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(int arrival_time) {
        this.arrival_time = arrival_time;
    }

    public int getService_time() {
        return service_time;
    }

    public void setService_time(int service_time) {
        this.service_time = service_time;
    }

    @Override
    public String toString() {
        return "(" +Id +","+arrival_time +","+ service_time+ ") ";
    }

    public String toInterface() {
        return "🚃";
    }
    @Override
    public int compareTo(Client c) {
        return this.arrival_time-c.arrival_time;
    }
}

