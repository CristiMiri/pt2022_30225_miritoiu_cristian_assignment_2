package MODEL;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@SuppressWarnings({"unused", "SynchronizeOnNonFinalField"})
public class Scheduler {
    private List<Server> servers;
    private Strategy strategy;
    private int no_servers;
    protected Client lock;


    public Scheduler(int no_servers, FileWriter writer,Strategy strategy) {
        lock=new Client(0,0,0);
        this.no_servers=no_servers;
        servers=new ArrayList<>();
        this.strategy=strategy;
        for(int i=0;i<no_servers;i++)
        {
            Server s=new Server(i,writer);
            servers.add(s);
        }
        for(Server i:servers)
        {
            i.setLock(this.lock);
            Thread t = new Thread(i);
            t.start();
        }
    }

    public void wake_all()
    {
        synchronized (lock){
        lock.notifyAll();
        }
    }

    public void stop_all()
    {
        for(Server i:this.servers)
        {
            i.setRunning(false);
        }
    }

    public  void send_client(BlockingQueue<Client> q)
    {
        int min=Integer.MAX_VALUE;
        Server select = null;
        if(this.strategy == Strategy.SHORTEST_QUEUE)
        {
            for (Server i:servers)
            {
                if(i.getNo_clients()<min) {
                    min = i.getNo_clients();
                    select=i;
                }
            }
            try {
                if(select!=null)
                    select.addClient(q.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else
        {
            for (Server i:servers)
            {
                if(i.getService_time().get()<min) {
                    min = i.getService_time().get();
                    select=i;
                }
            }
            try {
                if(select!=null)
                    select.addClient(q.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public int getNo_servers() {
        return no_servers;
    }

    public void setNo_servers(int no_servers) {
        this.no_servers = no_servers;
    }

    public Client getLock() {
        return lock;
    }

    public void setLock(Client lock) {
        this.lock = lock;
    }

}
