/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myclient;

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
public class JanelaClient extends JFrame{
        private JTextField texto;
	private JTextArea display;
        DatagramSocket asocket = null;
    
    
    public JanelaClient(){
        super("MyClient");
		
		texto = new JTextField();
		texto.setEditable(true);
		texto.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event){
						enviaDados(event.getActionCommand());
						texto.setText("");
					}
				});
		add(texto,BorderLayout.NORTH);
		
		display = new JTextArea();
		display.setEditable(false);
		add(display,BorderLayout.CENTER);
		
		setSize(400,300);
		setVisible(true);
    }
    
    private String getTime(){
                    return "["+Time.valueOf(LocalTime.now())+"] ";
    }
    
        private void displayMensagem(final String mensagem) {
		SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						display.append(getTime()+mensagem+"\n");
					}
				});
	}
    
    public void enviaDados(String dados){
                try {
                    byte [] m= dados.getBytes();
                    InetAddress server = InetAddress.getByName("127.0.0.0");
                    int port =6868;
                    displayMensagem("Conectando no server: "+server.getHostAddress());
                    displayMensagem("Conenctando na porta: "+port);
                    DatagramPacket request= new DatagramPacket(m,m.length,server,port);
                    displayMensagem("Conexão realizada com sucesso");
                    displayMensagem("Enviando Dados");
                    asocket.send(request);
                    displayMensagem("Dados Enviados com sucesso!");
                    
		}
		catch (IOException ioException) {
			displayMensagem("\nError escrevendo objeto");
		}
    }
    
    public void executaCliente(){
        displayMensagem("Inicializando Client");
         while(true){
        
            try {
                asocket = new DatagramSocket();                
                byte [] buffer = new byte[1000];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                asocket.receive(reply);
                displayMensagem("Resposta: " + 
                            reply.getLength() + " " +
                            new String(buffer,0,reply.getLength()));
                //System.out.println("Resposta: " +  new String(reply.getData(),0,reply.getLength()));
            } catch (SocketException ex) {
                Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownHostException ex) {
                Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
            } finally { 
                if (asocket != null)
                    asocket.close();
                }

        }
    }

}

