package VIEW;

import MODEL.Simulator_Manager;
import MODEL.Strategy;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;

public class Simulation_Frame extends JFrame {

	private JPanel contentPane;
	private JTextArea SERVERE;
	private JTextArea COADA;
	private JTextField texttime;
	private JTextField textwaiting;
	private JTextField textservice;
	Simulator_Manager s;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Simulation_Frame frame = new Simulation_Frame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Simulation_Frame(Simulator_Manager s) {
		this.s=s;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 459, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel time = new JLabel("TIME");
		time.setFont(new Font("Tahoma", Font.PLAIN, 15));
		time.setBounds(0, 11, 49, 14);
		contentPane.add(time);
		
		texttime = new JTextField();
		texttime.setEditable(false);
		texttime.setFont(new Font("Tahoma", Font.PLAIN, 15));
		texttime.setBounds(59, 10, 49, 20);
		contentPane.add(texttime);
		texttime.setColumns(10);
		
		JLabel waiting = new JLabel("Average waiting time:");
		waiting.setFont(new Font("Tahoma", Font.PLAIN, 15));
		waiting.setBounds(0, 36, 147, 19);
		contentPane.add(waiting);
		
		JLabel service = new JLabel("Average service time:");
		service.setFont(new Font("Tahoma", Font.PLAIN, 15));
		service.setBounds(0, 66, 147, 19);
		contentPane.add(service);
		
		textwaiting = new JTextField();
		textwaiting.setEditable(false);
		textwaiting.setBounds(157, 37, 49, 20);
		contentPane.add(textwaiting);
		textwaiting.setColumns(10);
		
		textservice = new JTextField();
		textservice.setEditable(false);
		textservice.setBounds(157, 67, 49, 20);
		contentPane.add(textservice);
		textservice.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 190, 445, 173);
		contentPane.add(scrollPane);
		
		SERVERE = new JTextArea();
		SERVERE.setBackground(Color.LIGHT_GRAY);
		SERVERE.setEditable(false);
		SERVERE.setForeground(Color.RED);
		SERVERE.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPane.setViewportView(SERVERE);
		
		JLabel lblNewLabel_2 = new JLabel("Simulare Cozi");
		lblNewLabel_2.setBackground(Color.LIGHT_GRAY);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setColumnHeaderView(lblNewLabel_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(0, 104, 445, 63);
		contentPane.add(scrollPane_1);
		
		JLabel lblNewLabel = new JLabel("Waiting Clients");
		lblNewLabel.setBackground(Color.LIGHT_GRAY);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane_1.setColumnHeaderView(lblNewLabel);
		
		COADA = new JTextArea();
		COADA.setBackground(Color.LIGHT_GRAY);
		COADA.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPane_1.setViewportView(COADA);
		
		JButton btnRESTART = new JButton("Close");
		btnRESTART.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnRESTART.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRESTART.setBounds(300, 9, 135, 27);
		contentPane.add(btnRESTART);
	}


	public void update(int current_time)
	{
		this.COADA.setText(s.to_Interface());
		this.texttime.setText(String.valueOf(current_time));
		this.SERVERE.setText(s.state());
	}

	public void ending(float waiting,float service)
	{
		this.textservice.setText(String.valueOf(service));
		this.textwaiting.setText(String.valueOf(waiting));
	}
}
