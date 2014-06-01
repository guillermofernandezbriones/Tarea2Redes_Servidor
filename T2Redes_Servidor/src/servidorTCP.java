import java.io.*;
import java.net.*;
import java.util.*;

public class servidorTCP {
	public static void main(String argv[]) throws Exception{
		int puerto = 7777;
		boolean flag = false;
		String recibidoDesdeCliente;
		ServerSocket servidorTCPSocket = new ServerSocket(puerto);
		while(true){
			Socket socketConexion = servidorTCPSocket.accept();
			BufferedReader desdeCliente = new BufferedReader(new InputStreamReader(socketConexion.getInputStream()));
			recibidoDesdeCliente = desdeCliente.readLine();
			System.out.println("Recibido: " + recibidoDesdeCliente);
			if (flag){
				servidorTCPSocket.close();
				break;
			}
				//En alguna parte hay que leer lo que manda cliente para parar este while y cerrar el socket
		}	
	}
}