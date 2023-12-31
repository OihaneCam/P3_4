package Practica_3_4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class Recursividad {
	
	private static int contFiltro = 1;
	private static int contManos = 1;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String frase1 = "Hola, mundo!";
        String frase2 = "Me gusta Java";
        String frase3 = "Estoy programando en Java";
        String frase4 = "Hola, mundo!";
        String frase5 = "Que tal   ?";
        String frase6 = "Lista: Sol, playa, arena...";
        
        System.out.println("String invertido letra a letra");
        System.out.println("Frase original 1: " + frase1);
        System.out.println("Frase invertida 1: " + invertirFrase(frase1));
        System.out.println();

        System.out.println("Frase original 2: " + frase2);
        System.out.println("Frase invertida 2: " + invertirFrase(frase2));
        System.out.println();

        System.out.println("Frase original 3: " + frase3);
        System.out.println("Frase invertida 3: " + invertirFrase(frase3));
        System.out.println();
        
        System.out.println("String invertido palabra a palabra");
        System.out.println("Palabra original 4: " + frase4);
        System.out.println("Palabra invertida 4: " + invertirPalabras(frase4));
        System.out.println();
        
        System.out.println("Palabra original 5: " + frase5);
        System.out.println("Palabra invertida 5: " + invertirPalabras(frase5));
        System.out.println();
        
        System.out.println("Palabra original 6: " + frase6);
        System.out.println("Palabra invertida 6: " + invertirPalabras(frase6));
        System.out.println();
        
        try {
			BaseDeDatos.inicializarBaseDeDatos();
			BaseDeDatos.crearTablas();
			
			
			ArrayList<Carta> baraja = new ArrayList<>();
			for (Carta.Palo palo : Carta.Palo.values()) {
	            for (Carta.Valor valor : Carta.Valor.values()) {
	                Carta carta = new Carta(palo.toString(), valor.toString());
	                baraja.add(carta);
	            }
	        }
			//		System.out.println("\nManos de n cartas de baraja");
			//		ArrayList<ArrayList<Carta>> manos = posiblesManos(2, baraja, 0);
			//		int cont = 1;
			//		for (ArrayList<Carta> mano : manos) {
			//            System.out.println(mano + " " + cont);
			//            cont++;
			//		}
					
					String desc = "Manos con al menos un AS";
					BaseDeDatos.nuevoFiltro(contFiltro, desc);
					System.out.println(desc);
					ArrayList<ArrayList<Carta>> manosFiltradas = filtraManos(2, baraja, 0);
					int contAS = 1;
					for (ArrayList<Carta> mano : manosFiltradas) {
			//            System.out.println(mano + " " + contAS);            
			            BaseDeDatos.aniadirCartasDeMano(contManos, contAS, mano, contFiltro);
						BaseDeDatos.manoFiltro(contFiltro, contManos);
			            contAS++;
			            contManos++;
			        }
					contFiltro++;
			
					
					String descPoker = "4 cartas con la misma figura en 5 cartas";
					BaseDeDatos.nuevoFiltro(contFiltro, descPoker);
					System.out.println(descPoker);
					ArrayList<ArrayList<Carta>> poker = manosPoker(5, baraja, 0);
					int contPoker = 1;
					for (ArrayList<Carta> mano : poker) {
			//            System.out.println(mano + " " + contPocker);
					    BaseDeDatos.aniadirCartasDeMano(contManos, contPoker, mano, contFiltro);
					    BaseDeDatos.manoFiltro(contFiltro, contManos);
						contManos++;
			            contPoker++;
			        }
					contFiltro++;
			
					
					String descFull = "3 cartas con la misma figura, 2 cartas con otra figura, en manos de 5 cartas";
					BaseDeDatos.nuevoFiltro(contFiltro, descFull);
					System.out.println(descFull);
					ArrayList<ArrayList<Carta>> full = manosFull(5, baraja, 0);
					int contFull = 1;
					for (ArrayList<Carta> mano : full) {
			//            System.out.println(mano + " " + contFull);
						BaseDeDatos.aniadirCartasDeMano(contManos, contFull, mano, contFiltro);
						BaseDeDatos.manoFiltro(contFiltro, contManos);
						contManos++;
			            contFull++;
			        }
					contFiltro++;
			
					String descEscalera = "5 cartas consecutivas del mismo palo";
					BaseDeDatos.nuevoFiltro(contFiltro, descEscalera);
					System.out.println(descEscalera);
					ArrayList<ArrayList<Carta>> escalera = manosEscalera(5, baraja, 0);
					int contEscalera = 1;
					for (ArrayList<Carta> mano : escalera) {
			//            System.out.println(mano + " " + contEscalera);
						BaseDeDatos.aniadirCartasDeMano(contManos, contEscalera, mano, contFiltro);
						BaseDeDatos.manoFiltro(contFiltro, contManos);
						contManos++;
						contEscalera++;
			        }
					contFiltro++;
					
				}catch (Exception e) {
					e.printStackTrace();
				}finally {
					BaseDeDatos.cerrarConexion();
				}
			}
	
	// 4.1 - Método invertirFrase
    public static String invertirFrase(String frase) {
        // Caso base: la cadena está vacía o tiene solo un carácter
        if (frase.isEmpty() || frase.length() == 1) {
            return frase;
        }

        // Paso recursivo: invertir la subcadena sin el primer carácter y concatenar el primer carácter al final
        String fraseInvertida = invertirFrase(frase.substring(0, frase.length()-1));
		return frase.charAt(frase.length()-1) + fraseInvertida;
    }
    
    // 4.2 - Método invertirPalabras
    public static String invertirPalabras(String frase) {
    	StringTokenizer tokenizer = new StringTokenizer(frase, " \t\n\r\f,.:;?!\"'"); // Separadores habituales
    	ArrayList<String> palabras = new ArrayList<String>() ;
        while(tokenizer.hasMoreTokens()) {
        	String palabra = tokenizer.nextToken();
        	palabras.add(palabra);
        }
		String fraseInvertida = "";
		for(int i=palabras.size()-1; i>=0; i--) {
			fraseInvertida += palabras.get(i) + " ";
		}
		return fraseInvertida;
	}
    
    public static ArrayList<Carta> manoActual = new ArrayList<>();
    public static ArrayList<ArrayList<Carta>> manos = new ArrayList<>();
    
    // 4.3 - Método para generar todas las posibles combinaciones de "n" cartas de la baraja
    public static ArrayList<ArrayList<Carta>> posiblesManos(int n, ArrayList<Carta> baraja,  int startIndex) {
    	if (manoActual.size() == n) {
			manos.add(new ArrayList<> (manoActual));
            return manos;
        }
		for (int i = startIndex; i < baraja.size(); i++) {
            Carta carta = baraja.get(i);
            if (!manoActual.contains(carta)) {
                manoActual.add(carta);
                posiblesManos(n, baraja, i+1);
                manoActual.remove(manoActual.size() - 1);
            }
        }
		return manos;
	}
    
    public static ArrayList<Carta> manoActualFiltrada = new ArrayList<>();
    public static ArrayList<ArrayList<Carta>> manosFiltradas = new ArrayList<>();
    
    // 4.4 - Método para generar y filtrar las posibles combinaciones de "n" cartas de la baraja
	private static ArrayList<ArrayList<Carta>> filtraManos(int n, ArrayList<Carta> baraja, int startIndex) {
		if (manoActualFiltrada.size() == n) {
			if(tieneAs(manoActualFiltrada)) {
				manosFiltradas.add(new ArrayList<> (manoActualFiltrada));
			}
            return manosFiltradas;
        }
		for (int i = startIndex; i < baraja.size(); i++) {
            Carta carta = baraja.get(i);
            if(!manoActualFiltrada.contains(carta)) {
            	manoActualFiltrada.add(carta);
            	filtraManos(n, baraja, i+1);
                manoActualFiltrada.remove(manoActualFiltrada.size() - 1);
            } 	
        }
        return manosFiltradas;
	}
	
	private static boolean tieneAs(ArrayList<Carta> mano) {
        for (Carta carta : mano) {
            if (carta.valor.equals("AS")) {
                return true;
            }
        }
        return false;
    }
	public static ArrayList<Carta> manoActPoker = new ArrayList<>();
    public static ArrayList<ArrayList<Carta>> manosPoker = new ArrayList<>();
    
	private static ArrayList<ArrayList<Carta>> manosPoker(int n, ArrayList<Carta> baraja, int startIndex) {
		if (manoActPoker.size() == n) {
			if(esPocker(manoActPoker)) {
				manosPoker.add(new ArrayList<> (manoActPoker));
			}
            return manosPoker;
        }
		for (int i = startIndex; i < baraja.size(); i++) {
            Carta carta = baraja.get(i);
            if(!manoActPoker.contains(carta)) {
            	manoActPoker.add(carta);
            	manosPoker(n, baraja, i+1);
            	manoActPoker.remove(manoActPoker.size() - 1);
            } 	
        }
        return manosPoker;
	}
	
	private static boolean esPocker(ArrayList<Carta> mano) {
        HashMap<String, Integer> conteoValores = new HashMap<>();

        for (Carta carta : mano) {
            String valor = carta.getValor();
            conteoValores.put(valor, conteoValores.getOrDefault(valor, 0) + 1);
        }
        for (int count : conteoValores.values()) {
            if (count >= 4) {
                return true;
            }
        }
        return false;
    }
	
	public static ArrayList<Carta> manoActFull = new ArrayList<>();
    public static ArrayList<ArrayList<Carta>> manosFull = new ArrayList<>();
    
	private static ArrayList<ArrayList<Carta>> manosFull(int n, ArrayList<Carta> baraja, int startIndex) {
		if (manoActFull.size() == n) {
			if(esFull(manoActFull)) {
				manosFull.add(new ArrayList<> (manoActFull));
			}
            return manosFull;
        }
		for (int i = startIndex; i < baraja.size(); i++) {
            Carta carta = baraja.get(i);
            if(!manoActFull.contains(carta)) {
            	manoActFull.add(carta);
            	manosFull(n, baraja, i+1);
            	manoActFull.remove(manoActFull.size() - 1);
            } 	
        }
        return manosFull;
	}

	private static boolean esFull(ArrayList<Carta> mano) {
        HashMap<String, Integer> conteoValores = new HashMap<>();

        for (Carta carta : mano) {
            String valor = carta.getValor();
            conteoValores.put(valor, conteoValores.getOrDefault(valor, 0) + 1);
        }
        boolean tieneTres = false;
        boolean tieneDos = false;

        for (int count : conteoValores.values()) {
            if (count == 3) {
                tieneTres = true;
            } else if (count == 2) {
                tieneDos = true;
            }
        }
        return tieneTres && tieneDos;
    }
	
	
	public static ArrayList<Carta> manoActEscalera = new ArrayList<>();
    public static ArrayList<ArrayList<Carta>> manosEscalera = new ArrayList<>();
    
	private static ArrayList<ArrayList<Carta>> manosEscalera(int n, ArrayList<Carta> baraja, int startIndex) {
		if (manoActEscalera.size() == n) {
			if(esFull(manoActEscalera)) {
				manosEscalera.add(new ArrayList<> (manoActEscalera));
			}
            return manosEscalera;
        }
		for (int i = startIndex; i < baraja.size(); i++) {
            Carta carta = baraja.get(i);
            if(!manoActEscalera.contains(carta)) {
            	manoActEscalera.add(carta);
            	manosEscalera(n, baraja, i+1);
            	manoActEscalera.remove(manoActEscalera.size() - 1);
            } 	
        }
        return manosEscalera;
	}

	private static boolean esEscalera(ArrayList<Carta> mano) {
        Collections.sort(mano, Comparator.comparingInt(carta -> carta.getValorNumerico()));
        int contadorConsecutivas = 1;

        for (int i = 1; i < mano.size(); i++) {
            if (mano.get(i).getValorNumerico() == mano.get(i - 1).getValorNumerico() + 1
                    && mano.get(i).getPalo() == mano.get(i - 1).getPalo()) {
                contadorConsecutivas++;
            } else {
                contadorConsecutivas = 1;
            }
            if (contadorConsecutivas == 5) {
                return true;
            }
        }
        return false;
    }
	
}
