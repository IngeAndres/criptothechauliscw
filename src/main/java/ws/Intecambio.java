package ws;

import java.util.Random;

public class Intecambio {
    
    public static int p;
    public static int g;
   public static  int privado;

    public static int encontrarGeneradorPrimitivo(int p) {
        for (int g = 2; g < p; g++) {
            boolean esGenerador = true;
            for (int i = 1; i < p - 1; i++) {
                if (Math.pow(g, i) % p == 1) {
                    esGenerador = false;
                    break;
                }
            }
            if (esGenerador) {
                return g;
            }
        }
        return -1;
    }

    private static boolean esPrimoMayor100(int num) {
       
        int sqrtNum = (int) Math.sqrt(num) + 1;
        for (int i = 2; i <= sqrtNum; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public  static int generarPrimoAleatorioMayor100() {
        Random random = new Random();
        int candidatoPrimo;
        do {
            candidatoPrimo = random.nextInt(100) + 11; 
        } while (!esPrimoMayor100(candidatoPrimo));
        return candidatoPrimo;
    }
/*-----------------------------------*/
    public int GenerarPrivado() {
        Random random = new Random();
        int clavePrivada = random.nextInt(10) + 1;
        return clavePrivada;
    }

    public int ClaveGenerada(int p, int g) {
        int ClaveGenerada = (int) (Math.pow(g, privado) % p);
        return ClaveGenerada;
    }

    public int calcularClaveCompartida(int publicoCliente, int p) {
        int claveCompartida = (int) (Math.pow(publicoCliente, privado) % p);
        return claveCompartida;
    }



}
