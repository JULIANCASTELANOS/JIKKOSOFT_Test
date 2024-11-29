package repositorio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.*;
import java.util.ArrayList;
import servidor.ServicioAutenticacionInterface;
import cliente.ServicioDiscoClienteInterface;
import common.Herramienta;
 
public class Repositorio {
 
    private static String nombre;
    public static ArrayList<String> lista;
    private static ServicioAutenticacionInterface autenticacion;
    private static ServicioDiscoClienteInterface SrDiscoCliente;
    
    private static BufferedReader lecturaConsola = new BufferedReader(new InputStreamReader(System.in));
    
    public static void main(String[] args) throws Exception {  	
    	autenticacion = (ServicioAutenticacionInterface) Naming.lookup("rmi://localhost:2000/autenticacion");   	
    	String recogeOpcion;
        
        int opcionMenu1 = 0;
		do{
			System.out.println("---------------------------");
			System.out.println("JIKKO MENU REPOSITORY");
			System.out.println("---------------------------");
			System.out.println("1-Register new repository");
			System.out.println("2-Authenticate in the JiKKo system");
			System.out.println("3-Exit");
			System.out.print("Choose option:");
		    recogeOpcion=lecturaConsola.readLine();
		    System.out.println();
		    if(!Herramienta.esNumero(recogeOpcion)){
		    	System.out.println("Introduce un valor de 1 a 3");
		    	System.out.println();
		    }else{
		    	opcionMenu1=Integer.parseInt(recogeOpcion);
		    
				switch(opcionMenu1){
				case 1:
					System.out.println();
					System.out.print("Introduce nombre del repositorio:");
					nombre=lecturaConsola.readLine();
					System.out.println();
		
					if(autenticacion.registraRepositorio(nombre)){
						System.out.println("Se ha registrado el repositorio: "+nombre);
						System.out.println();
					}
					else{
						System.out.println("No se ha podido registrar el repositorio: "+nombre);
						System.out.println();
					}
					break;
					
				case 2:
					
					System.out.println("Introduce el nombre del REPOSITORIO para proceder a la autenticaci�n de �ste:");
			        nombre = lecturaConsola.readLine();
			        System.out.println();
			        int idSesionRepositorio = autenticacion.autenticarRepositorio(nombre);
			       
			        
			        if (idSesionRepositorio==0){
			        	System.out.println("Fallo de autenticaci�n de repositorio. Causas: nombre repositorio inexistente. Repositorio ya autenticado.");
			        	System.out.println();
			        }else{
			        ServicioClOperadorImpl clienteOperador = new ServicioClOperadorImpl();
			        String URL_nombreClienteOperador = "rmi://localhost:2000/ServicioClOperador/"+ idSesionRepositorio;
			        Naming.rebind(URL_nombreClienteOperador, clienteOperador);
			        System.out.println();
			        System.out.println("Servicio Cliente Operador levantado.");
			        ServicioSrOperadorImpl servidorOperador = new ServicioSrOperadorImpl();
			        String URL_nombreServidorOperador = "rmi://localhost:2000/ServicioSrOperador/"+ idSesionRepositorio;
			        Naming.rebind(URL_nombreServidorOperador, servidorOperador);
			        System.out.println();
			        System.out.println("Servicio Servidor Operador levantado.");
			      
			        int opcionMenu2 = 0;
			        do {
			        	System.out.println("-------------------");
			        	System.out.println("JIKKO REPOSITORY MENU");
						System.out.println("-------------------");
			        	System.out.println("1.- List Jikko Clients.");
			            System.out.println("2.- List Jikko Files Client.");
			            System.out.println("3.- Exit.");
			            System.out.print("Choose option: ");
					    
					    recogeOpcion=lecturaConsola.readLine();
					    System.out.println();
					    
					    if(!Herramienta.esNumero(recogeOpcion)){
					    	System.out.println("Introduce un valor de 1 a 3");
					    	System.out.println();
					    }else{
					    	opcionMenu2=Integer.parseInt(recogeOpcion);
				            switch (opcionMenu2) {
				            case 1:
				            	lista=new ArrayList<String>();
				            	lista=autenticacion.listaClientesDeRepositorio(idSesionRepositorio);
				            	System.out.println("Clientes de este repositorio:");
				            	for(int x=0;x<lista.size();x++) {
				            		  System.out.println(lista.get(x));
				            		}
				                break;
				            case 2:
				            	System.out.println("Introduce el nombre del cliente: ");
				            	nombre=lecturaConsola.readLine();
				            	try{
				            		SrDiscoCliente=(ServicioDiscoClienteInterface) Naming.lookup("rmi://localhost:2000/DiscoCliente/"+idSesionRepositorio);
				            		if(!SrDiscoCliente.listarFicherosCliente(idSesionRepositorio, nombre.toUpperCase()))
				            		System.out.println("No existe el cliente en este repositorio");
				            	}catch (NotBoundException nbe){
				            		System.out.println("No hay ning�n cliente autenticado");
				            	}
				                break;
				            case 3:
				            	autenticacion.desAutenticarRepositorio(idSesionRepositorio);
				                break;
				            default:
				            	System.out.println("Opci�n no correcta");
								System.out.println();
				                break;
				            }
					    }
			        } while (opcionMenu2 != 3);
			        
			        }
			        
				case 3:
					System.out.println("Saliendo...");
					if (opcionMenu1==3)
						System.exit(3);
					break;
				default: 
					System.out.println("Opci�n no correcta");
					System.out.println();
				break;
				}
		    }
		} while (opcionMenu1!=3);
        
    }
} 