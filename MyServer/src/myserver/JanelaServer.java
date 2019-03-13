/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myserver;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author keyalisth
 */ 
public class JanelaServer extends JFrame{
    	private JTextField texto;
	private JTextArea display;
        private DatagramSocket asocket = null;
        private int porta=6868;
                  
        public JanelaServer()
	{
		super("MyServer");
		
		texto = new JTextField();
		texto.setEditable(true);
		texto.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event){
						//enviaDados(event.getActionCommand());
						texto.setText("");
					}
				});
		add(texto,BorderLayout.NORTH);
		
		display = new JTextArea();
		display.setEditable(false);
		add(display,BorderLayout.CENTER);
		
		setSize(800,600);
		setVisible(true);
	}
        
        private String getTime(){
            return "["+Time.valueOf(LocalTime.now())+"] ";
        }
        
        
        public void executaServidor()
	{
            try {
                displayMensagem("Conectando servidor...\n");
                asocket = new DatagramSocket(porta);
                displayMensagem("Servidor Conectado na porta: "+porta+"\n");
                displayMensagem("Servidor Conectado em: "+InetAddress.getByName("127.0.0.0").getHostAddress()+"\n");
                while (true){
                    byte [] buffer = new byte[1000];
                    DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                    displayMensagem("Esperando Mensagem....\n");
                    asocket.receive(request);
                    displayMensagem("Recebendo dados de "+request.getAddress()+"\n");
                    displayMensagem("Mensagem Recebida: " + 
                            request.getLength() + " " +
                            new String(buffer,0,request.getLength())+"\n");
                    DatagramPacket reply = new DatagramPacket(request.getData(), 
                            request.getLength(),request.getAddress(),
                            request.getPort());
                    asocket.send(reply);
                }
            } catch (SocketException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownHostException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            } finally { 
                if (asocket != null)
                    asocket.close();
            }

        }
        
        private void displayMensagem(final String mensagem) {
		SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						display.append(getTime()+mensagem);
					}
				});
	}

}
