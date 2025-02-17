package cachesimulator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Direto {

    public static void mapeamentoDireto(int powPal, int tamTag, int tamLinhaBits, int tamEnderecoBits, long qntLinhaCache,String arquivo) {
        ArrayList<String> cache = new ArrayList<>((int) qntLinhaCache);
        ArrayList<String> teste = FileManager.stringReader("/Users/usuario/Documents/Cache/CacheSimulator/src/cachesimulator/"+arquivo+".txt");
        int erro = 0, acerto = 0;
        int totalAcessos = 0;
        for (int i = 0; i < qntLinhaCache; i++) {
            cache.add("");
        }
        long startTime = System.nanoTime();
        for (String linha : teste) {
            totalAcessos++;

            int acesso = Integer.parseInt(linha);
            long bin[] = intToBinary(acesso, tamEnderecoBits);
            String stringBin = intToBinaryString(acesso, tamEnderecoBits);

            String parte = stringBin.substring(tamTag, (tamEnderecoBits - (powPal)));

            int posicaoCache = binaryToDecimal(parte);

            // verifica se a cache ta dentro do tamanho do array
            if (posicaoCache >= 0 && posicaoCache < qntLinhaCache) {
                if (cache.get(posicaoCache).equals(null) || !cache.get(posicaoCache).equals(stringBin.substring(0, tamTag))) {
                    cache.set(posicaoCache, stringBin.substring(0, tamTag));
                    erro++;

                } else if (cache.get(posicaoCache).equals(stringBin.substring(0, tamTag))) {
                    acerto++;

                }
            } else {
                // se o valor for maior que a cache,trata como erro
                erro++;

            }
        }
        
        long endTime = System.nanoTime(); // Finaliza o temporizador
        long duration = (endTime - startTime); // Calcula a duração em nanosegundos
        double taxaAcerto = (double) acerto / totalAcessos * 100;
        double taxaErro = (double) erro / totalAcessos * 100;
        
        String conj = "";
        Principal.alteraValor(totalAcessos,acerto,erro,taxaAcerto,taxaErro,conj,duration);
        
    }

    public static int binaryToDecimal(String binaryString) {
        int decimal = 0;
        int n = binaryString.length();
        for (int i = 0; i < n; i++) {
            if (binaryString.charAt(i) == '1') {
                decimal += Math.pow(2, n - i - 1);
            } else if (binaryString.charAt(i) != '0') {
                return -1;
            }
        }
        return decimal;
    }

    public static long[] intToBinary(int value, int size) {
        if (value > Math.pow(2, size) - 1) {
            return null;
        }
        long bin[] = new long[size];
        int i = 0;
        while (value > 0 && i < size) {
            int num = value % 2;
            value = value / 2;
            bin[i] = num;
            i++;
        }
        for (int j = 0; j <= size / 2; j++) {
            long temp = bin[j];
            bin[j] = bin[size - j - 1];
            bin[size - j - 1] = temp;
        }
        return bin;
    }

    public static String intToBinaryString(int value, int size) {
        if (value > Math.pow(2, size) - 1) {
            return null;
        }
        char bin[] = new char[size];
        for (int i = 0; i < size; i++) {
            bin[i] = '0';
        }
        int i = 0;
        while (value > 0 && i < size) {
            int num = value % 2;
            value = value / 2;
            bin[i] = (num + "").charAt(0);
            i++;
        }
        for (int j = 0; j <= size / 2; j++) {
            char temp = bin[j];
            bin[j] = bin[size - j - 1];
            bin[size - j - 1] = temp;
        }
        String nova = new String(bin);
        return nova;
    }


}
