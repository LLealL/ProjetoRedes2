/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author keyalisth
 */
public class MyServer {

   /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       JanelaServer j= new JanelaServer();
       j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       j.executaServidor();
       
    }
}
