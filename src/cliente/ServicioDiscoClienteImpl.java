package cliente;

import common.Fichero;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import servidor.ServicioGestorInterface;

public class ServicioDiscoClienteImpl extends UnicastRemoteObject implements ServicioDiscoClienteInterface{
	
	private static final long serialVersionUID = 9L;
	private static ServicioGestorInterface ServicioGestor;
	protected ServicioDiscoClienteImpl() throws RemoteException {
        super();
    }
	
	public boolean bajarFicheroPaso3(Fichero fichero) throws RemoteException{
		
		try {
			OutputStream os; 
			String nombreFichero = fichero.obtenerNombre();			
			os = new FileOutputStream(nombreFichero); 
			if (fichero.escribirEn(os)==false)
			{
				os.close();
			}
			os.close();
			System.out.println("Fichero " + fichero.obtenerNombre() + " recibido");
		} catch (FileNotFoundException e) {			
			System.err.println("Fichero no encontrado");
		} catch (RemoteException e) {			
			System.err.println("Error en el servidor. " + e.getMessage());
		} catch (IOException e) {			
			System.err.println("Error al guardar fichero");
		} catch (NullPointerException e) {			
			System.err.println("Fichero no encontrado");
		} 
		return true;
	};
	
	@Override
	public boolean listarFicherosCliente(int idRepos,String nombreCliente) throws RemoteException, MalformedURLException, NotBoundException
	{
		ServicioGestor=(ServicioGestorInterface) Naming.lookup("rmi://localhost:2000/gestor");
		if(!ServicioGestor.buscarFicherosClientes(idRepos, nombreCliente)) 
			return false;
		else
			return true;
	}
}
