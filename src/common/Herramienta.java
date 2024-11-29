package common;

import java.lang.StringBuffer;

public class Herramienta {
	
	public static boolean esNumero(String cadena){
		try{
			Integer.parseInt(cadena);
			return true;
		}catch (NumberFormatException nfe){
			return false;
	}
	}

	public static boolean comprobarSufijoCadena(String []array) {
		StringBuffer cadena = new StringBuffer();
		
		for (int x=0;x<array.length;x++){
			   cadena.append(array[x]);
			}
		if (cadena.toString().endsWith(".jar")) {
			return true;
		}
		else {
			return false;
		}
	}
}