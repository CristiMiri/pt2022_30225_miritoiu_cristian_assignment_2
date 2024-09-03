package VIEW;

import MODEL.Simulator_Manager;
import MODEL.Strategy;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Process_frame extends JFrame {

    private JPanel contentPane;
    private JTextField Queues;
    private JTextField Simulation;
    private JTextField Clients;
    private JTextField min_arrival;
    private JTextField min_service;
    private JTextField max_arrival;
    private JTextField max_service;
    private JLabel lblNewLabel_serviciu;
    private JLabel lblNewLabel_waiting;
    private JComboBox comboBox;
    Simulator_Manager s;
    /**
     * Launch the application.
     */
    
    /**
     * Create the frame.
     */
    public Process_frame() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 478, 401);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Numar Clienti");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel.setBounds(10, 20, 91, 19);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Numar Cozi");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1.setBounds(10, 60, 77, 19);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Timp de simulare");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1_1.setBounds(10, 100, 115, 19);
        contentPane.add(lblNewLabel_1_1);

        lblNewLabel_waiting = new JLabel("Timpul sosirii");
        lblNewLabel_waiting.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_waiting.setBounds(10, 152, 91, 19);
        contentPane.add(lblNewLabel_waiting);

        JLabel lblNewLabel_2 = new JLabel(":");
        lblNewLabel_2.setForeground(Color.RED);
        lblNewLabel_2.setBounds(260, 156, 10, 14);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_2_1 = new JLabel(":");
        lblNewLabel_2_1.setForeground(Color.RED);
        lblNewLabel_2_1.setBounds(260, 204, 8, 14);
        contentPane.add(lblNewLabel_2_1);

        JLabel lblStrategie = new JLabel("Strategie");
        lblStrategie.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblStrategie.setBounds(10, 264, 115, 19);
        contentPane.add(lblStrategie);

        lblNewLabel_serviciu= new JLabel("Timp de serviciu");
        lblNewLabel_serviciu.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_serviciu.setVisible(true);
        lblNewLabel_serviciu.setBackground(Color.RED);
        ///lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_serviciu.setBounds(10, 200, 115, 19);
        contentPane.add(lblNewLabel_serviciu);

        comboBox = new JComboBox();
        comboBox.setVisible(true);
        comboBox.setModel(new DefaultComboBoxModel(Strategy.values()));
        comboBox.setBounds(200, 264, 186, 27);
        contentPane.add(comboBox);

        Clients = new JTextField();
        Clients.setBounds(200, 20, 50, 20);
        contentPane.add(Clients);
        Clients.setColumns(10);

        Queues = new JTextField();
        Queues.setBounds(200, 60, 50, 20);
        contentPane.add(Queues);
        Queues.setColumns(10);

        Simulation = new JTextField();
        Simulation.setBounds(200, 100, 50, 20);
        contentPane.add(Simulation);
        Simulation.setColumns(10);

        min_arrival = new JTextField();
        min_arrival.setBounds(200, 152, 50, 20);
        contentPane.add(min_arrival);
        min_arrival.setColumns(10);

        min_service = new JTextField();
        min_service.setColumns(10);
        min_service.setBounds(200, 200, 50, 20);
        contentPane.add(min_service);

        max_arrival = new JTextField();
        max_arrival.setColumns(10);
        max_arrival.setBounds(278, 152, 50, 20);
        contentPane.add(max_arrival);

        max_service = new JTextField();
        max_service.setColumns(10);
        max_service.setBounds(278, 200, 50, 20);
        contentPane.add(max_service);

        JButton btnNewButton = new JButton("Procesare si simulare");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnNewButton.setBounds(100, 326, 186, 27);
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                go();
                Thread t=new Thread(s);
                t.start();
            }
        });
        contentPane.add(btnNewButton);
    }


    public int getQueues() {
        return Integer.valueOf(Queues.getText());
    }



    public int getSimulation() {
        return Integer.valueOf(Simulation.getText());
    }


    public int getClients() {
        return Integer.valueOf(Clients.getText());
    }


    public int getMin_arrival() {
        return Integer.valueOf(min_arrival.getText());
    }


    public  int getMin_service() {
        return Integer.valueOf(min_service.getText());
    }


    public  int getMax_arrival() {
        return Integer.valueOf(max_arrival.getText());
    }

    public int getMax_service() {
        return Integer.valueOf(max_service.getText());
    }
    public void go()
    {
        s=new Simulator_Manager(this.getClients(), this.getQueues(), this.getSimulation(), this.getMin_arrival(), this.getMax_arrival(), this.getMin_service(),this.getMax_service(),(Strategy) comboBox.getSelectedItem());
    }
}
