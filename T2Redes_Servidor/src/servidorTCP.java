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
			
			try{
				// Se crea el .txt con la lista de contactos
				File datosRecibidos = new File("datos_recibidos.txt");
				
				// El (..., true) es para que se agregue lo leido al final del archivo -append-)
				BufferedWriter escritor = new BufferedWriter(new FileWriter(datosRecibidos, true));
				
				// Escribo en el archivo nuevo dado el siguiente formato
				escritor.write(recibidoDesdeCliente);
				escritor.newLine();
				
				// Cierro escritor
				escritor.close();
			}
			catch(Exception ioe){
				System.out.println(ioe.toString());
			}
			
			if (flag){
				servidorTCPSocket.close();
				break;
			}
				//En alguna parte hay que leer lo que manda cliente para parar este while y cerrar el socket
		}	
	}
}