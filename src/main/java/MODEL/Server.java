package MODEL;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings(value = {"unused", "StringConcatenationInsideStringBufferAppend", "FieldMayBeFinal", "SynchronizeOnNonFinalField", "StringConcatenationInLoop"})
public class Server implements Runnable {
    private int id;
    private Client current;
    private BlockingQueue<Client> clients;
    private AtomicInteger no_clients;
    private AtomicInteger service_time;
    private AtomicBoolean running = new AtomicBoolean(true);
    private Client lock;
    private FileWriter writer;

    public Server(int id, FileWriter writer) {
        this.writer = writer;
        this.id = id;
        running.set(true);
        clients = new LinkedBlockingQueue<>();
        service_time = new AtomicInteger(0);
        no_clients = new AtomicInteger(0);
        current = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getCurrent() {
        return current;
    }

    public void setCurrent(Client current) {
        this.current = current;
    }

    public BlockingQueue<Client> getClients() {
        return clients;
    }

    public void setClients(BlockingQueue<Client> clients) {
        this.clients = clients;
    }

    public AtomicInteger getService_time() {
        return service_time;
    }

    public void setService_time(AtomicInteger service_time) {
        this.service_time = service_time;
    }

    public AtomicBoolean getRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running.set(running);
    }

    public Client getLock() {
        return lock;
    }

    public void setLock(Client lock) {
        this.lock = lock;
    }

    public FileWriter getWriter() {
        return writer;
    }

    public void setWriter(FileWriter writer) {
        this.writer = writer;
    }

    public synchronized void addClient(Client c) {
        if (current == null)
            current = c;
        else
            clients.add(c);
        service_time.addAndGet(c.getService_time());
        no_clients.incrementAndGet();
    }

    public int getNo_clients() {
        return no_clients.get();
    }

    public void setNo_clients(AtomicInteger no_clients) {
        this.no_clients = no_clients;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (running.get()) {
            try {
                write_to_file();
                if (current == null && clients.peek() != null) {
                    current = clients.take();
                }
                if (current != null) {
                    current.setService_time(current.getService_time() - 1);
                    service_time.decrementAndGet();
                    if (current.getService_time() == 0) {
                        if (clients.peek() != null) {
                            current = clients.take();
                        } else
                            current = null;
                    }
                }
                synchronized (lock) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void print() {
        if (current == null && this.clients.isEmpty())
            System.out.println("Queue " + id + ":closed");
        else if (this.current != null)
            System.out.println("Queue " + id + ": " + current + " " + this.clients);
        else
            System.out.println("Queue " + id + ": " + this.clients);
    }

    public String to_Interface()
    {
        String aux="";
        String buffer;
        for(Client i:clients)
            aux=aux+i.toInterface();
        if (current == null && this.clients.isEmpty())
            buffer="Queue " + id + ":closed";
        else if (this.current != null)
            buffer="Queue " + id + ": " + "🚂" + " " + aux;
        else
            buffer="Queue " + id + ": " + aux;
        return buffer;
    }
    public synchronized void write_to_file() {
        try {
            if (current == null && this.clients.isEmpty())
                writer.append("Queue " + id + ":closed \n");
            else if (this.current != null)
                writer.append("Queue " + id + ": " + current + " " + this.clients + "\n");
            else
                writer.append("Queue " + id + ": " + this.clients + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}