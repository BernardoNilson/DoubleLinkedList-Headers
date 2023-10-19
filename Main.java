import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Esta é a classe principal do T2 de ALESTI. Leia o README.md para mais informações.
 * @author Bernardo Nilson - PUCRS
 * Orientação:
 * @author Prof. Marcio Pinho
 */

public class Main {

    public static void LeArquivo(String nome, DoubleLinkedListOfHeaders L)
    {
        Path path1 = Paths.get(nome);

        //try (BufferedReader reader = Files.newBufferedReader(path1, Charset.defaultCharset())) {
        try (BufferedReader reader = Files.newBufferedReader(path1, StandardCharsets.UTF_8)) {
            String line = null;

            int cont = 0;
            while ((line = reader.readLine()) != null) {
                //System.out.println("Linha: "+ line);
                L.addIncreasingOrder(line);
                cont++;
            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }  
    }
    public static void main(String[] args) {


        DoubleLinkedListOfHeaders L = new DoubleLinkedListOfHeaders();

        LeArquivo("ListaDePalavrasSmall.txt", L);

        // Ao inves de ler um arquivo, voce pode testar seu métodos 
        // usando chamadas do método addIncreasingOrder
        
        L.addIncreasingOrder("Pedra");
        L.addIncreasingOrder("Casa");     
        L.addIncreasingOrder("Dado");
        L.addIncreasingOrder("Abacate");
        L.addIncreasingOrder("Telha");
        
        
        L.ImprimeLista();

        L.GeraDOT();

        
    }
}
