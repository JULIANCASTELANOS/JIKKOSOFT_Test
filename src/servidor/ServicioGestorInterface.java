package servidor;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
 
public interface ServicioGestorInterface extends Remote{
 
     public String obtenerServicioClienteOperador(int id) throws RemoteException, NotBoundException, MalformedURLException;
     public String obtenerServicioServidorOperador(int id) throws RemoteException, NotBoundException, MalformedURLException;
     public void bajarFicheroPaso1(int sesionCliente, String archivo, String nombreCliente) throws RemoteException, NotBoundException,MalformedURLException;
     public ArrayList<String> listarClientesSistema() throws RemoteException,NotBoundException,MalformedURLException;
     public boolean buscarFicherosClientes(int idSesionRepositorio,String nombreCliente) throws RemoteException, MalformedURLException, NotBoundException;
}