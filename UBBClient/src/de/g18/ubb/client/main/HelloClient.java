package de.g18.ubb.client.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.g18.ubb.client.communication.ServiceRepository;

public class HelloClient extends JFrame {

    private static final long serialVersionUID = 1L;


    public HelloClient() {
        super("Hello Client");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        JButton b1 = new JButton("Access EJB");

        getContentPane().add(b1, BorderLayout.PAGE_END);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String msg = "";
                try {
                    msg = "Message From EJB --> " + ServiceRepository.getTestService().sayHello();
                } catch(Exception ex){
                    msg = "Error --> " +  ex;
                    System.err.println(ex);
                }
                JOptionPane.showMessageDialog(HelloClient.this, msg, "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        setSize(200,200);
    }

    public static void main(String args[]) {
       new HelloClient().setVisible(true);
    }
}