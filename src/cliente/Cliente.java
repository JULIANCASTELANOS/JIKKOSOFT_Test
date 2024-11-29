package cliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.util.ArrayList;


import servidor.ServicioAutenticacionInterface;
import servidor.ServicioGestorInterface;
import repositorio.ServicioClOperadorInterface;
import common.Fichero;
import common.Herramienta;

public class Cliente {
    private static String nombre;
    private static String archivo;
    private static String rutaDirectorio;
    private static String URL_DiscoCliente;
    private static ServicioAutenticacionInterface ServicioAutenticacion;
    private static ServicioGestorInterface servicioGestor;
    private static ServicioClOperadorInterface accesoMetodosClOp;
    
    private static BufferedReader lecturaConsola = new BufferedReader(new InputStreamReader(System.in));
    
    public static void main(String[] args) throws Exception {
    	 	
    	ServicioAutenticacion = (ServicioAutenticacionInterface) Naming.lookup("rmi://localhost:2000/autenticacion");
    	servicioGestor=(ServicioGestorInterface) Naming.lookup("rmi://localhost:2000/gestor");
        String recogeOpcion;
        int opcionMenu1=0;
		do{
			System.out.println("------------------------");
			System.out.println("JIKKO CLIENTS MENU");
			System.out.println("------------------------");
			System.out.println("1-Registrar nuevo jikko client");
			System.out.println("2-Autenticarse en el sistema");
			System.out.println("3-Salir");
			System.out.print("Selecciona opci�n: ");
			recogeOpcion=lecturaConsola.readLine();
		    
		    if(!Herramienta.esNumero(recogeOpcion)){
		    	System.out.println("Introduce un valor de 1 a 3");
		    	System.out.println();
		    }else{
		    	opcionMenu1=Integer.parseInt(recogeOpcion);
			
				switch(opcionMenu1){
				case 1: 
					System.out.println("Introduce nombre del cliente:");
					nombre=lecturaConsola.readLine(); 
					
					if(ServicioAutenticacion.registraCliente(nombre)){
						System.out.println("Se ha registrado el cliente: "+nombre);
						System.out.println();
					}
					else{
						System.out.println("No se ha podido registrar el cliente, puede que ya exista: "+nombre+".");
						System.out.println();
					}
					break;
					
				case 2:
					System.out.println("Introduce tu nombre para proceder a la autenticaci�n:");
			        nombre = lecturaConsola.readLine();
			        
			        int idSesionCliente = ServicioAutenticacion.autenticarCliente(nombre);
			        
			        if (idSesionCliente==0){
			        	System.out.println("La operaci�n de autenticaci�n no ha sido posible. Causas: Repositorios no disponibles o cliente inexistente.");
			        }else{
			     		   	
						int idRepositorioCliente=ServicioAutenticacion.dameRepositorio(idSesionCliente);
						System.out.println("Repositorio del cliente: "+ idRepositorioCliente);
						ServicioDiscoClienteImpl discoCliente= new ServicioDiscoClienteImpl();
						URL_DiscoCliente = "rmi://localhost:2000/DiscoCliente/"+idRepositorioCliente;
	           	 		Naming.rebind(URL_DiscoCliente , discoCliente);
	           	 		System.out.println("Levantado el servicio Disco-Cliente.");	
	           	 		String direccionServicioClOp=servicioGestor.obtenerServicioClienteOperador(idSesionCliente);
	           	 		accesoMetodosClOp=(ServicioClOperadorInterface) Naming.lookup(direccionServicioClOp);
						
			        int opcionMenu2 = 0;
			        do {
						System.out.println("---------------");
			        	System.out.println("MEN� DE JIKKO CLIENTE");
						System.out.println("---------------");
			        	System.out.println("1.- Subir fichero.");
			            System.out.println("2.- Bajar fichero.");
			            System.out.println("3.- Borrar fichero.");
			            System.out.println("4.- Compartir fichero(Opcional).");
			            System.out.println("5.- Listar ficheros.");
			            System.out.println("6.- Listar clientes del sistema.");
			            System.out.println("7.- Salir.");
			            System.out.print("Selecciona opci�n: ");
			            
			            recogeOpcion=lecturaConsola.readLine();
					    
					    if(!Herramienta.esNumero(recogeOpcion)){
					    	System.out.println();
					    	System.out.println("Introduce un valor de 1 a 3");
					    	System.out.println();
					    }else{
					    	opcionMenu2=Integer.parseInt(recogeOpcion);
			   
				            switch (opcionMenu2) {
				            
				            case 1:
				            	try{
				            	System.out.println("Introduce la ruta donde se encuentra el archivo:");
				            	rutaDirectorio=lecturaConsola.readLine();
				            	System.out.println("Introduce el fichero a subir: ");
				            	archivo=lecturaConsola.readLine();
				            	
								if (rutaDirectorio.isEmpty()){
									Fichero fichero = new Fichero(archivo,nombre);
									if (!accesoMetodosClOp.subirFichero(fichero))
										System.out.println("No existe el fichero.");
										System.out.println();
									}
								else{
									Fichero fichero= new Fichero(rutaDirectorio,archivo, nombre); 
									if(!accesoMetodosClOp.subirFichero(fichero)) {
										System.out.println("No existe el fichero.");
										System.out.println();
									}
									else 
									{
										System.out.println("Fichero subido correctamente.");
										System.out.println();
									}
								}	
				            	}catch(NullPointerException npe){
				            		System.out.println("Error de creaci�n de objeto. No existe el fichero.");
				            		System.out.println();
				            	}
				                break;
				                
				            case 2:
				            		            	
				            	System.out.println("Introduce el nombre del fichero a descargar: ");
				            	archivo=lecturaConsola.readLine();
				            	
				            	try{
				            		servicioGestor.bajarFicheroPaso1(idSesionCliente,archivo,nombre);
				            	}catch(NullPointerException npe){
				            		System.out.println("No existe el fichero.");
				            	}
				            	
				                break;
				            case 3:
				            	
				            	System.out.println("Introduce el fichero a borrar: ");
				            	archivo=lecturaConsola.readLine();
				            	
								System.out.println("El fichero es: "+ archivo +", y el cliente es:"+nombre);
								if(accesoMetodosClOp.borrarFichero(archivo,nombre))
									System.out.println("Fichero borrado correctamente");
								else
									System.out.println();
									System.out.println("Fichero NO borrado. No encontrado o no existe.");
	
				                break;
				            case 4:
				            	System.out.println("Opci�n no implementada.");
				            	break;
				            	
				            case 5:
				            	System.out.println("Listando ficheros de la carpeta del cliente "+nombre+":");
				            	ArrayList<String> archivosExtraidos=new ArrayList<String>();
				            	archivosExtraidos=accesoMetodosClOp.listarFicherosCliente(nombre);

				            	for(int contador=0;contador<archivosExtraidos.size();contador++) {
				            			System.out.println(archivosExtraidos.get(contador));
				            	}
				            	break;
				            	
				            case 6:
				            	ArrayList<String> listadoClientes=new ArrayList<String>();
				            	listadoClientes=servicioGestor.listarClientesSistema();
				            	if (!listadoClientes.isEmpty()) {
				                     System.out.println();
				                     System.out.println("Clientes del repositorio:");
				                     for (String nombre : listadoClientes) {
				                          System.out.println(nombre);
				                     }
				                     System.out.println();
				                } else {
				                     System.out.println("==================================");
				                     System.out.println("No hay clientes registrados.");
				                     System.out.println("==================================");
				                }
				            	break;
				            case 7:
				            	ServicioAutenticacion.desAutenticarCliente(idSesionCliente);          
				            
				            	break;
				            default: 
				            	System.out.println("Opci�n no correcta");
				            	System.out.println();
				            }
					    }
			        } while (opcionMenu2 != 7);
			
			        }
			        
				case 3:
					System.out.println("Saliendo");
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