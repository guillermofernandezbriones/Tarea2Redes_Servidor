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
			DataOutputStream haciaCliente = new DataOutputStream(socketConexion.getOutputStream());
			
			recibidoDesdeCliente = desdeCliente.readLine();
			System.out.println("Recibido: " + recibidoDesdeCliente);
			
			// En este try catch se guarda todo lo que se envia desde el otro lado en un archivo de texto
			try{
				// Se crea el .txt con los datos recibidos
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
			
			/** DESDE AC� ES NUEVO **/
			
			// Tokenizamos el string recibido desde el cliente
			StringTokenizer stUserRecibido = new StringTokenizer(recibidoDesdeCliente);
			
			// Si es que se recibe un nick, entonces se ven en el archivo todos los mensajes con ese nick
			// Lo que mande el usuario es algo de tipo "USER juanito"
			if(stUserRecibido.nextToken().equals("USER")){
				String nick = "", contenido = "", mensaje = "", nickLeido = "";
				int i = 0;
				
				// Lee el nick que ingreso el usuario en "recibir_msjs.html"
				while(stUserRecibido.hasMoreElements()){
					nick = stUserRecibido.nextElement().toString();
				}
				
				// Ahora hay que buscar en el archivo datos_recibidos.txt todos los mensajes que tengan ese nick asociado
				// Formato de mensaje: "PARA,nick,mensaje"
				
				// Se crea un archivo para lectura
				File archivoDatos = new File("datos_recibidos.txt");
				BufferedReader lector = new BufferedReader(new FileReader(archivoDatos));
				
				// Leemos completamente el archivo de datos recibidos buscando los mensajes recibidos
				while((contenido = lector.readLine()) != null){
					StringTokenizer strtok = new StringTokenizer(contenido, ",");
					// "PARA" denota que es un mensaje...
					if(strtok.nextToken().equals("PARA")){
						// ...y por lo tanto lo que viene despues de PARA es un nick
						nickLeido = strtok.nextToken();
						
						// Si ese nick es igual al que se ingreso, entonces retornar un string que tiene los mensajes
						if(nickLeido.equals(nick)){
							while(strtok.hasMoreElements()){
								// Este if es solamente para que antes del primer mensaje leido no haya salto de linea 
								if(i == 0){
									mensaje = mensaje + "" + strtok.nextElement().toString();
									i++;
								}
								else
									mensaje = mensaje + "\n" + strtok.nextElement().toString();
							}
						}
					}
				}
				// En este print se ven los mensajes exclusivamente para el usuario solicitado
				System.out.println(mensaje);
				
				// Hay que enviar mensaje de vuelta al cliente TCP ahora
				haciaCliente.writeBytes(mensaje);
				
				lector.close();
			}
			
			/** HASTA AC� ES NUEVO**/
			
			if (flag){
				servidorTCPSocket.close();
				break;
			}
				//En alguna parte hay que leer lo que manda cliente para parar este while y cerrar el socket
		}	
	}
}