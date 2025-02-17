package cachesimulator;
import java.util.*;

public class Conjunto {

    public static void conjuntoFIFO(long qntLinhaConj, long powPal, long tamEnderecoBits, String arquivo, long qntConj) {
        ArrayList<String> teste = FileManager.stringReader("/Users/usuario/Documents/Cache/CacheSimulator/src/cachesimulator/" + arquivo + ".txt");
        ArrayList<HashMap<String, Long>> cache = new ArrayList<>();
        ArrayList<Queue<String>> fila = new ArrayList<>();

        // Inicializa o ArrayList cache com HashMaps vazias para cada conjunto
        for (int i = 0; i < qntConj; i++) {
            cache.add(new HashMap<>((int) qntLinhaConj));
        }
        for (int i = 0; i < qntConj;i++){
            fila.add(new LinkedList<>());
        }

        int conjLog = (int) (Math.log(qntConj)/Math.log(2));
        int erro = 0, acerto = 0;
        int totalAcessos = 0;
        long startTime = System.nanoTime();
            
        for (String linha : teste) {
            totalAcessos++;

            int acesso = Integer.parseInt(linha);
            String stringBin = Integer.toBinaryString(acesso);

            // Garante que a string binária tenha o comprimento correto
            while (stringBin.length() < tamEnderecoBits) {
                stringBin = "0" + stringBin;
            }

            // Calcula o índice do conjunto
            String tag = stringBin.substring(0, (int) (tamEnderecoBits - (powPal + conjLog)));
            long valor = Long.parseLong(stringBin, 2);
            int conj = (int) Long.parseLong(stringBin.substring((int) (tamEnderecoBits - (powPal + conjLog)), (int) (tamEnderecoBits - powPal)),2);


            if (cache.get(conj).containsKey(tag)) {
                acerto++;
            } else {
                if (cache.get(conj).size() >= qntLinhaConj) {
                    cache.get(conj).remove(fila.get(conj).poll());
                    fila.get(conj).offer(tag);
                    cache.get(conj).put(tag, valor);
                    erro++;
                } else {
                    fila.get(conj).offer(tag);
                    cache.get(conj).put(tag, valor);
                    erro++;
                }
            }
        }
        
        long endTime = System.nanoTime(); // Finaliza o temporizador
        long duration = (endTime - startTime); // Calcula a duração em nanosegundos
        double taxaAcerto = (double) acerto / totalAcessos * 100;
        double taxaErro = (double) erro / totalAcessos * 100;

        String conj = String.valueOf(qntConj);
        Principal.alteraValor(totalAcessos,acerto,erro,taxaAcerto,taxaErro,conj,duration);
    }

    public static void conjuntoLRU(long qntLinhaConj, long powPal, long tamEnderecoBits, String arquivo, long qntConj) {
        ArrayList<String> teste = FileManager.stringReader("/Users/usuario/Documents/Cache/CacheSimulator/src/cachesimulator/" + arquivo + ".txt");
        ArrayList<HashMap<String, Long>> cache = new ArrayList<>();
        ArrayList<LinkedHashSet<String>> lruQueue = new ArrayList<>();

        // Inicializa o ArrayList cache com HashMaps vazias para cada conjunto
        for (int i = 0; i < qntConj; i++) {
            cache.add(new HashMap<>((int) qntLinhaConj));
        }
        for (int i = 0; i < qntConj; i++) {
            lruQueue.add(new LinkedHashSet<>((int) qntLinhaConj));
        }

        int conjLog = (int) (Math.log(qntConj)/Math.log(2));
        int erro = 0, acerto = 0;
        int totalAcessos = 0;
        long startTime = System.nanoTime();
           
        for (String linha : teste) {
            totalAcessos++;

            int acesso = Integer.parseInt(linha);
            String stringBin = Integer.toBinaryString(acesso);

            // Garante que a string binária tenha o comprimento correto
            while (stringBin.length() < tamEnderecoBits) {
                stringBin = "0" + stringBin;
            }

            // Calcula o índice do conjunto
            String tag = stringBin.substring(0, (int) (tamEnderecoBits - (powPal + conjLog)));
            long valor = Long.parseLong(stringBin, 2);
            int conj = (int) Long.parseLong(stringBin.substring((int) (tamEnderecoBits - (powPal + conjLog)), (int) (tamEnderecoBits - powPal)),2);


            if (cache.get(conj).containsKey(tag)) {
                acerto++;
                lruQueue.get(conj).remove(tag);
                lruQueue.get(conj).add(tag);
            } else {
                if (cache.get(conj).size() >= qntLinhaConj) {
                    String eldestKey = lruQueue.get(conj).iterator().next();
                    lruQueue.get(conj).remove(eldestKey);
                    cache.get(conj).remove(eldestKey);
                }
                    lruQueue.get(conj).add(tag);
                    cache.get(conj).put(tag,valor);
                    erro++;

            }
        }
        
        long endTime = System.nanoTime(); // Finaliza o temporizador
        long duration = (endTime - startTime); // Calcula a duração em nanosegundos
        double taxaAcerto = (double) acerto / totalAcessos * 100;
        double taxaErro = (double) erro / totalAcessos * 100;

        
        String conj = String.valueOf(qntConj);
        Principal.alteraValor(totalAcessos,acerto,erro,taxaAcerto,taxaErro,conj,duration);
    }

    public static void conjuntoLFU(long qntLinhaConj, long powPal, long tamEnderecoBits, String arquivo, long qntConj) {
        ArrayList<String> teste = FileManager.stringReader("/Users/usuario/Documents/Cache/CacheSimulator/src/cachesimulator/" + arquivo + ".txt");
        ArrayList<LinkedHashMap<String, Long>> cache = new ArrayList<>();
        Map.Entry<String,Long> menorValor;

        // Inicializa o ArrayList cache com HashMaps vazias para cada conjunto
        for (int i = 0; i < qntConj; i++) {
            cache.add(new LinkedHashMap<>((int) qntLinhaConj));
        }

        int conjLog = (int) (Math.log(qntConj)/Math.log(2));
        int erro = 0, acerto = 0;
        int totalAcessos = 0;
        
        long startTime = System.nanoTime();
        
        for (String linha : teste) {
            totalAcessos++;

            int acesso = Integer.parseInt(linha);
            String stringBin = Integer.toBinaryString(acesso);

            // Garante que a string binária tenha o comprimento correto
            while (stringBin.length() < tamEnderecoBits) {
                stringBin = "0" + stringBin;
            }

            // Calcula o índice do conjunto
            String tag = stringBin.substring(0, (int) (tamEnderecoBits - (powPal + conjLog)));
            long valor = Long.parseLong(stringBin, 2);
            int conj = (int) Long.parseLong(stringBin.substring((int) (tamEnderecoBits - (powPal + conjLog)), (int) (tamEnderecoBits - powPal)),2);


            if (cache.get(conj).containsKey(tag)) {
                cache.get(conj).replace(tag,cache.get(conj).get(tag)+1);
                acerto++;
            } else {
                if (cache.get(conj).size() >= qntLinhaConj) {
                    menorValor = cache.get(conj).entrySet().iterator().next();
                    for (Map.Entry<String, Long> busca : cache.get(conj).entrySet()){
                        if (busca.getValue()<menorValor.getValue()){
                            menorValor = busca;
                            if (menorValor.getValue() == 0L){
                                break;
                            }
                        }
                    }
                    cache.get(conj).remove(menorValor.getKey());
                    cache.get(conj).put(tag, 0L);
                    erro++;
                }else {
                    cache.get(conj).put(tag, 0L);
                    erro++;
                }
            }
        }
        long endTime = System.nanoTime(); // Finaliza o temporizador
        long duration = (endTime - startTime); // Calcula a duração em nanosegundos
        double taxaAcerto = (double) acerto / totalAcessos * 100;
        double taxaErro = (double) erro / totalAcessos * 100;

        
        String conj = String.valueOf(qntConj);
        Principal.alteraValor(totalAcessos,acerto,erro,taxaAcerto,taxaErro,conj,duration);
    }

    public static void conjuntoRandom(long qntLinhaConj, long powPal, long tamEnderecoBits, String arquivo, long qntConj) { 
        ArrayList<String> teste = FileManager.stringReader("/Users/usuario/Documents/Cache/CacheSimulator/src/cachesimulator/" + arquivo + ".txt");
        ArrayList<HashSet<String>> cache = new ArrayList<>();
        Random random = new Random();
        ArrayList<HashMap<Integer, String>> randomMap = new ArrayList<>();

        // Inicializa o ArrayList cache com HashSets vazias para cada conjunto
        for (int i = 0; i < qntConj; i++) {
            cache.add(new HashSet<>((int) qntLinhaConj));
            randomMap.add(new HashMap<>((int) qntLinhaConj));
        }

        int conjLog = (int) (Math.log(qntConj) / Math.log(2));
        int erro = 0, acerto = 0;
        int totalAcessos = 0;
        long startTime = System.nanoTime();

        for (String linha : teste) {
            totalAcessos++;

            int acesso = Integer.parseInt(linha);
            String stringBin = Integer.toBinaryString(acesso);

            // Garante que a string binária tenha o comprimento correto
            while (stringBin.length() < tamEnderecoBits) {
                stringBin = "0" + stringBin;
            }

            // Calcula o índice do conjunto
            String tag = stringBin.substring(0, (int) (tamEnderecoBits - (powPal + conjLog)));
            long valor = Long.parseLong(stringBin, 2);
            int conj = (int) Long.parseLong(stringBin.substring((int) (tamEnderecoBits - (powPal + conjLog)), (int) (tamEnderecoBits - powPal)), 2);

            if (cache.get(conj).contains(tag)) {
                acerto++;
            } else {
                if (cache.get(conj).size() >= qntLinhaConj) {
                    int randomIndex = random.nextInt(randomMap.get(conj).size());
                    String chaveRemover = randomMap.get(conj).remove(randomIndex);
                    cache.get(conj).remove(chaveRemover);
                    randomMap.get(conj).put(randomIndex, tag);
                    cache.get(conj).add(tag);
                    erro++;
                } else {
                    randomMap.get(conj).put(randomMap.get(conj).size(), tag);
                    cache.get(conj).add(tag);
                    erro++;
                }
            }
        }
        
        long endTime = System.nanoTime(); // Finaliza o temporizador
        long duration = (endTime - startTime); // Calcula a duração em nanosegundos
        double taxaAcerto = (double) acerto / totalAcessos * 100;
        double taxaErro = (double) erro / totalAcessos * 100;

        
        String conj = String.valueOf(qntConj);
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
