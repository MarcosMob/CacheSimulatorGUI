    package cachesimulator;
    import java.util.Random;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.LinkedList;
    import java.util.Queue;
    import java.util.LinkedHashSet;
    import java.util.*;
    
    public class Associativo {
        public static void associativoFIFO(long qntLinhaCache,long powPal,long tamEnderecoBits,String arquivo){
            HashMap<String,Long> cache =  new HashMap((int) qntLinhaCache);
            //criando uma fila
            Queue <String> fila = new LinkedList<>();
            ArrayList<String> teste = FileManager.stringReader("/Users/usuario/Documents/Cache/CacheSimulator/src/cachesimulator/"+arquivo+".txt");
            //criacao de variaveis de erros e acertos
            int erro = 0,acerto = 0;
            int totalAcessos = 0;
            long startTime = System.nanoTime();
            
            for (String linha : teste) {
                totalAcessos++;

                int acesso = Integer.parseInt(linha);
                long bin[] = intToBinary(acesso, (int) tamEnderecoBits);
                String stringBin = intToBinaryString(acesso, (int) tamEnderecoBits);

                String chave = stringBin.substring(0, (int) (tamEnderecoBits - (powPal)));
                long  valor = binaryToDecimal(stringBin);

                //criando o sistema associativo
                if (cache.containsKey(chave)){
                    acerto++;
                }else {
                    if (cache.size() >= qntLinhaCache){
                        cache.remove(fila.poll());
                        fila.offer(chave);
                        cache.put(chave,valor);
                        erro++;
                    }else {
                        fila.offer(chave);
                        cache.put(chave, valor);
                        erro++;
                    }
                }

            }
            long endTime = System.nanoTime(); // Finaliza o temporizador
            long duration = (endTime - startTime); // Calcula a duração em nanosegundos
            double taxaAcerto = (double) acerto / totalAcessos * 100;
            double taxaErro = (double) erro / totalAcessos * 100;
         
            
            String conj = "";
            Principal.alteraValor(totalAcessos,acerto,erro,taxaAcerto,taxaErro,conj,duration);
        }

        public static void associativoRandom(long qntLinhaCache, long powPal, long tamEnderecoBits, String arquivo) {
        ArrayList<String> teste = FileManager.stringReader("/Users/usuario/Documents/Cache/CacheSimulator/src/cachesimulator/" + arquivo + ".txt");
            HashSet<String> cache = new HashSet<>((int) qntLinhaCache);
            Random random = new Random();
            HashMap<Integer, String> randomMap = new HashMap<>();

            int erro = 0, acerto = 0;
            int totalAcessos = 0;
            long startTime = System.nanoTime();
            for (String linha : teste) {
                totalAcessos++;

                int acesso = Integer.parseInt(linha);
                long[] bin = intToBinary(acesso, (int) tamEnderecoBits);
                String stringBin = intToBinaryString(acesso, (int) tamEnderecoBits);

                String chave = stringBin.substring(0, (int) (tamEnderecoBits - (powPal)));
                long valor = binaryToDecimal(stringBin);

                if (cache.contains(chave)) {
                    acerto++;
                } else {
                    if (cache.size() >= qntLinhaCache) {
                        // Remover chave aleatória usando randomMap
                        int randomIndex = random.nextInt(randomMap.size());
                        String chaveRemover = randomMap.remove(randomIndex);
                        cache.remove(chaveRemover);

                        // Adicionar nova chave ao randomMap
                        randomMap.put(randomIndex, chave);
                        cache.add(chave);
                        erro++;
                    } else {
                        // Adicionar nova chave ao cache e ao randomMap
                        randomMap.put(randomMap.size(), chave);
                        cache.add(chave);
                        erro++;
                    }
                }
            }

        long endTime = System.nanoTime(); // Finaliza o temporizador
        long duration = (endTime - startTime); // Calcula a duração em nanosegundos
        double taxaAcerto = (double) acerto / totalAcessos * 100;
        double taxaErro = (double) erro / totalAcessos * 100;

        
        String conj = "";
        Principal.alteraValor(totalAcessos, acerto, erro, taxaAcerto, taxaErro, conj, duration);
    }


        public static void associativoLRU(long qntLinhaCache, long powPal, long tamEnderecoBits,String arquivo){
            ArrayList<String> teste = FileManager.stringReader("/Users/usuario/Documents/Cache/CacheSimulator/src/cachesimulator/"+arquivo+".txt");
            HashMap<String, Long> cache = new HashMap<>((int) qntLinhaCache);
            LinkedHashSet<String> fila = new LinkedHashSet<>();


            int erro = 0, acerto = 0;
            int totalAcessos = 0;
            long startTime = System.nanoTime();
            
            for (String linha : teste) {
                totalAcessos++;

                int acesso = Integer.parseInt(linha);
                String stringBin = Integer.toBinaryString(acesso);

                // Certifique-se de que a string binária tenha o comprimento correto
                while (stringBin.length() < tamEnderecoBits) {
                    stringBin = "0" + stringBin;
                }

                String chave = stringBin.substring(0, (int) (tamEnderecoBits - powPal));
                long valor = Long.parseLong(stringBin, 2);

                if (cache.containsKey(chave)) {
                    acerto++;
                    // Atualiza a ordem de acesso na LinkedHashSet
                    fila.remove(chave);
                    fila.add(chave);
                } else {
                    if (cache.size() >= qntLinhaCache) {
                        // Remove a chave mais antiga
                        String eldestKey = fila.iterator().next();
                        fila.remove(eldestKey);
                        cache.remove(eldestKey);
                    }
                    fila.add(chave);
                    cache.put(chave, valor);
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

        public static void associativoLFU(long qntLinhaCache, long powPal, long tamEnderecoBits, String arquivo) {
            //Leitura dos dados do arquivo
            ArrayList<String> teste = FileManager.stringReader("/Users/usuario/Documents/Cache/CacheSimulator/src/cachesimulator/" + arquivo + ".txt");
            //Inicialização do Cache
            LinkedHashMap<String, Long> cache = new LinkedHashMap<>((int) qntLinhaCache);

            //Variáveis de Controle
            Map.Entry<String,Long> menorValor;
            int erro = 0, acerto = 0;
            int totalAcessos = 0;
            long startTime = System.nanoTime();
            
            //Processamento das linhas de entrada
            for (String linha : teste) {
                totalAcessos++;

                int acesso = Integer.parseInt(linha);
                long[] bin = intToBinary(acesso, (int) tamEnderecoBits);
                String stringBin = intToBinaryString(acesso, (int) tamEnderecoBits);

                String chave = stringBin.substring(0, (int) (tamEnderecoBits - (powPal)));
                long valor = binaryToDecimal(stringBin);

                //Verificação de Presença e Atualização de Contagem
                if (cache.containsKey(chave)) {
                    // Incrementa o contador de acessos da chave
                    cache.replace(chave, cache.get(chave) + 1);
                    acerto++;
                } else {//Gerenciamento de Substituição
                    if (cache.size() >= qntLinhaCache) {
                        menorValor = cache.entrySet().iterator().next();
                        for (Map.Entry<String, Long> busca : cache.entrySet()){
                            if (busca.getValue()< menorValor.getValue()){
                                menorValor = busca;
                                if (menorValor.getValue()== 0L){
                                    break;
                                }
                            }
                        }

                        // Remove a chave com o menor número de acessos
                        cache.remove(menorValor.getKey());
                        cache.put(chave, 0L);
                        erro++;
                    }else {
                        // Adiciona a nova chave com contagem de acessos inicializada
                        cache.put(chave, 0L);
                        erro++;
                    }
                }
            }
           
            long endTime = System.nanoTime(); // Finaliza o temporizador
            long duration = (endTime - startTime); // Calcula a duração em nanosegundos
            //Cálculo das Taxas de Acerto e Erro
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
