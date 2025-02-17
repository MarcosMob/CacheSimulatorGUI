package cachesimulator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {
    public static ArrayList<String> stringReader (String path){
        BufferedReader buffRead = null;
        try {
            buffRead = new BufferedReader(new FileReader(path));
            ArrayList<String> text = new ArrayList<>();
            String line = buffRead.readLine();
            while (line != null) {
                text.add(line);
                line = buffRead.readLine();
            }   buffRead.close();
            return text;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                buffRead.close();
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void readConfigTxt(String config,String arquivo, String metodo, Long conjuntos) {

        String memTam,cacheTam,rowTam,wordTam;
        ArrayList<String> dadosConfig = FileManager.stringReader("/Users/usuario/Documents/Cache/CacheSimulator/src/cachesimulator/"+config+".txt");


        //Acessando a primeira linha do arquivo
        String dadosMemoriaCompleto = dadosConfig.get(0);

        //Dividindo a linha em duas partes
        String partesMem[] = dadosMemoriaCompleto.split("=");

        //Exibe o texto mem


        memTam = partesMem[1].replaceAll("\\D+", ""); // Remove todos os caracteres que não são dígitos

        long memTamLong = Long.parseLong(memTam);

        //Exibindo o valor do tamanho da memoria

        String unidadeMedida = partesMem[1].replaceAll("\\d+", "").trim(); // Remove todos os dígitos e espaços em branco extras
        if (unidadeMedida.equals("KB;")){
            memTamLong = memTamLong * 1024;
        }else if(unidadeMedida.equals("MB;")){
            memTamLong = memTamLong * 1024 * 1024;
        }else if (unidadeMedida.equals("GB;")){
            memTamLong = memTamLong * 1024 * 1024 * 1024;
        }else if (unidadeMedida.equals("B;")){
            memTamLong = memTamLong ;
        }


        //LENDO A SEGUNDA LINHA DO ARQUIVO (PALAVRA)

        dadosMemoriaCompleto = dadosConfig.get(1);
        //Dividindo a linha em duas partes
        partesMem = dadosMemoriaCompleto.split("=");

        //Exibe o texto cache


        wordTam = partesMem[1].replaceAll("\\D+", ""); // Remove todos os caracteres que não são dígitos

        long wordTamLong = Long.parseLong(wordTam);

        //Exibindo o valor do tamanho da memoria cache
        unidadeMedida = partesMem[1].replaceAll("\\d+", "").trim(); // Remove todos os dígitos e espaços em branco extras
        

        if (unidadeMedida.equals("KB;")){
            wordTamLong = wordTamLong * 1024;
        }else if(unidadeMedida.equals("MB;")){
            wordTamLong = wordTamLong * 1024 * 1024;
        }else if (unidadeMedida.equals("GB;")){
            wordTamLong = wordTamLong * 1024 * 1024 * 1024;
        }else if (unidadeMedida.equals("B;")){
            wordTamLong = wordTamLong ;
        }
        

        //LENDO A TERCEIRA LINHA DO ARQUIVO (CACHE)

         dadosMemoriaCompleto = dadosConfig.get(2);
        //Dividindo a linha em duas partes
        partesMem = dadosMemoriaCompleto.split("=");

        //Exibe o texto cache
       


        cacheTam = partesMem[1].replaceAll("\\D+", ""); // Remove todos os caracteres que não são dígitos

        long cacheTamLong = Long.parseLong(cacheTam);

        //Exibindo o valor do tamanho da memoria cache
        
        unidadeMedida = partesMem[1].replaceAll("\\d+", "").trim(); // Remove todos os dígitos e espaços em branco extras
      

        if (unidadeMedida.equals("KB;")){
            cacheTamLong = cacheTamLong * 1024;
        }else if(unidadeMedida.equals("MB;")){
            cacheTamLong = cacheTamLong * 1024 * 1024;
        }else if (unidadeMedida.equals("GB;")){
            cacheTamLong = cacheTamLong * 1024 * 1024 * 1024;
        }else if (unidadeMedida.equals("B;")){
            cacheTamLong = cacheTamLong  ;
        }
      
        //LENDO A QUARTA LINHA DO ARQUIVO (LINHA)

        dadosMemoriaCompleto = dadosConfig.get(3);
        //Dividindo a linha em duas partes
        partesMem = dadosMemoriaCompleto.split("=");

        //Exibe o texto cache
        


        rowTam = partesMem[1].replaceAll("\\D+", ""); // Remove todos os caracteres que não são dígitos

        long rowTamLong = Long.parseLong(rowTam);

        //Exibindo o valor do tamanho da memoria cache
      
        unidadeMedida = partesMem[1].replaceAll("\\d+", "").trim(); // Remove todos os dígitos e espaços em branco extras
       

        if (unidadeMedida.equals("KB;")){
            rowTamLong = rowTamLong * 1024;
        }else if(unidadeMedida.equals("MB;")){
            rowTamLong = rowTamLong * 1024 * 1024;
        }else if (unidadeMedida.equals("GB;")){
            rowTamLong = rowTamLong * 1024 * 1024 * 1024;
        }else if (unidadeMedida.equals("B;")){
            rowTamLong = rowTamLong;
        }else if (unidadeMedida.equals("pal;")){
            rowTamLong = rowTamLong ;
        }
       

        //calculando os bits (rowtamlong,cachetamlong,wordtamlong,memtamlong) --> variaveis com os valores em long
        //pegando o expoente dos valores Long e colocando em Int
        int powCache = (int) (Math.log(cacheTamLong)/Math.log(2));
        int powPal = (int) (Math.log(wordTamLong)/Math.log(2));
        int powMem = (int)  (Math.log(memTamLong)/Math.log(2));
        int powLinha = (int) (Math.log(rowTamLong)/Math.log(2));

        //calculando quantidade de linhas e espaco total da memoria

        long qntLinhaCache = (int) (Math.pow(2,powCache) / (Math.pow(2,powLinha)*Math.pow(2,powPal)));
        long calcEndereco = (int) (Math.pow(2,powMem)/Math.pow(2,powPal));
        //pegando o tamanho da linha e do endereco total em bits, descobrindo tamanho de tag, linha e palavra
        int tamLinhaBits= (int) (Math.log(qntLinhaCache)/Math.log(2));
        int tamEnderecoBits= (int) (Math.log(calcEndereco)/Math.log(2));
        int tamTag = tamEnderecoBits - (tamLinhaBits + powPal);
        
        Long qntConj = conjuntos;
        Long qntLinhaConj = qntLinhaCache/qntConj;
        
        
        switch(metodo){
            case "Mapeamento Direto":
                Direto.mapeamentoDireto(powPal,tamTag,tamLinhaBits,tamEnderecoBits,qntLinhaCache,arquivo);
                break;
            case "Mapeamento Associativo FIFO":
                Associativo.associativoFIFO( qntLinhaCache,powPal,tamEnderecoBits,arquivo);
                break;
            case "Mapeamento Associativo Random":
                Associativo.associativoRandom(qntLinhaCache,powPal,tamEnderecoBits,arquivo);
                break;
            case "Mapeamento Associativo LRU":
                Associativo.associativoLRU(qntLinhaCache,powPal,tamEnderecoBits,arquivo);
                break;
            case "Mapeamento Associativo LFU":
                Associativo.associativoLFU(qntLinhaCache,powPal,tamEnderecoBits,arquivo);
                break;
            case "Conjunto Associativo FIFO":
                Conjunto.conjuntoFIFO(qntLinhaConj, powPal, tamEnderecoBits, arquivo, qntConj);
                break;
            case "Conjunto Associativo LRU":
                Conjunto.conjuntoLRU(qntLinhaConj, powPal, tamEnderecoBits, arquivo, qntConj);
                break;
            case "Conjunto Associativo LFU":
                Conjunto.conjuntoLFU(qntLinhaConj, powPal, tamEnderecoBits, arquivo, qntConj);
                break;
            case "Conjunto Associativo Random":
                Conjunto.conjuntoRandom(qntLinhaConj, powPal, tamEnderecoBits, arquivo, qntConj);
                break;
            default:
                System.out.println(metodo);
                break;
        
        }
    }

}
