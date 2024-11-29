package common;

import java.io.*;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;

public class Fichero implements Serializable{

	private static final long serialVersionUID = -701591859841871541L;
	private String propietario;		
	private String nombre;			
	private long peso;				
	private long checksum;			
	private byte[] data;			
	
	public Fichero (String nombre, String propietario)
	{
		this.nombre=nombre;
		this.propietario=propietario;
		
		CheckedInputStream c = null;
		peso = 0;
		
		try{
			c = new CheckedInputStream(new FileInputStream(nombre), new CRC32());
			peso = new File(nombre).length();
			data=new byte[(int) this.peso];
			while(c.read(data) > 0) {
		    }
			c.close();
			 
		}catch (FileNotFoundException ef)
		{
			System.err.println("Fichero no encontrado");
		} catch (IOException e) {

			System.err.println("Error leyendo fichero" + e.toString());
		}
		checksum = c.getChecksum().getValue();
		
	}
	public Fichero (String ruta, String nombre, String propietario)	//constructor
	{
		this.nombre=nombre;
		this.propietario=propietario;
		
		CheckedInputStream c = null;
		peso = 0;
		
		try{
			c = new CheckedInputStream(new FileInputStream(ruta+File.separator+nombre), new CRC32());
			peso = new File(ruta+"//"+nombre).length();
			data=new byte[(int) this.peso];
			while(c.read(data) > 0) {
		    }
			c.close();
			 
		}catch (FileNotFoundException ef)
		{
			System.err.println("Fichero no encontrado");
		} catch (IOException e) {

			System.err.println("Error leyendo fichero" + e.toString());
		}
		checksum = c.getChecksum().getValue();
		
	}
	public boolean escribirEn (OutputStream os)
	{
		long CheckSum;
		CheckedOutputStream cs= new CheckedOutputStream(os,new CRC32());
		try{
			cs.write(data);
			CheckSum=cs.getChecksum().getValue();
			cs.close();
			if (CheckSum != this.checksum)
			{
				return (false);
			}			
		}catch(Exception e){
			System.err.println("Error escribiendo fichero" + e.toString());
		}
		return (true);
	}
	public String obtenerPropietario()
	{
		return (propietario);
	}
	public String obtenerNombre()
	{
		return (nombre);
	}
	public long obtenerPeso()
	{
		return (peso);
	}
	public long obtenerChecksum()
	{
		return (checksum);
	}
	
}

