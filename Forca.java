import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Francisco Navarrete
 */
public class Forca {
    
    private ArrayList<String> log = new ArrayList<String>();
    
    /* public List<Integer> analisaLetraEExibeDescobertos(List<Integer> vet, String palavra, Integer tam, String letra, Integer tent){
        List <Integer> check = new ArrayList<>();
        check.clear();
        check.addAll(vet);
        for (int i = 0; i < tam; i++){
            if (palavra.charAt(i) == letra.charAt(0))
                vet.set(i, 1);
        }

        for (int i = 0; i < tam; i++){
            if (vet.get(i) > 0){
                String aux = palavra.charAt(i);
                System.out.print(aux.getBytes());
            }
            else
                outputStream.write("_".getBytes());    
        }
        if (check.equals(vet))
            vet.set(tam, 0);
        else
            vet.set(tam, 1);

        return vet;
    } */
    
    public ArrayList<String> getLog() {
        return log;
    }
    
    public void writeLog() {
        System.out.println("O log foi:");
        for(String s : log) {
            System.out.println("\t"+s);
        }
    }
    
    
    public void clientHandler(Socket cs) throws IOException {
        
        String userLine = "";
        
        System.out.println("clienthandler chamado.");
        
        OutputStream outputStream = cs.getOutputStream();
        InputStream inputStream = cs.getInputStream();
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
        
        //Criando coleção de Strings
        List<String> colecao = new ArrayList<String>();
        colecao.add("audi");
        colecao.add("lamborghini");
        colecao.add("general motors");
        colecao.add("volkswagen");
        colecao.add("skoda");
        colecao.add("volvo");
        colecao.add("mitsubishi");
        colecao.add("lada");
        colecao.add("gurgel");
        colecao.add("pontiac");
        
        //Sorteando uma dessas strings e atribuindo o valor da escolha a variavel "chosen"
        Random gerador = new Random();
        Integer chosen;
        chosen = gerador.nextInt(colecao.size());

        //Variáveis do jogo
        String palavra = colecao.get(chosen);
        Integer tamPalavra = palavra.length();
        Integer tentrealizadas = 0, marcador = 0, rodada = 0;
        List<Integer> descobertos = new ArrayList<Integer>(tamPalavra + 1);
        for (int i = 0; i <= tamPalavra; i++)
        descobertos.add(0);
        
        //Lógica do jogo
        outputStream.write("\n\nBEM VINDO AO JOGO DA FORCA DAS MARCAS DE CARRO\nVocê tem uma palavra e 5 chances, será que consegue vencer ?\n".getBytes());
        outputStream.write("\nSua Palavra foi escolhida e tem o número de caracteres igual a: ".getBytes());
        outputStream.write(tamPalavra.toString().getBytes());
        while(!userLine.equals("fim")) {
            rodada++;
            outputStream.write("\n\nRodada ".getBytes());
            outputStream.write(rodada.toString().getBytes());
            outputStream.write("\nEscreva uma letra entre a-z: ".getBytes());
            userLine = bf.readLine();
            String carac = userLine.toLowerCase();
            List <Integer> check = new ArrayList<>();
            check.clear();
            check.addAll(descobertos);
            for (int i = 0; i < tamPalavra; i++){
                if (palavra.charAt(i) == carac.charAt(0)){
                    descobertos.set(i, 1);
                    marcador++;
                }
            }
    
            for (int i = 0; i < tamPalavra; i++){
                if (descobertos.get(i) > 0){
                    String aux = String.valueOf(palavra.charAt(i));
                    outputStream.write(aux.getBytes());
                }
                else
                    outputStream.write("_".getBytes());    
            }
            if (marcador == tamPalavra){
                outputStream.write("\n\nParabéns mito, você venceu!!!!\n\n\n\n".getBytes());
                break;
            }

            if (check.equals(descobertos))
                descobertos.set(tamPalavra, 0);
            else
                descobertos.set(tamPalavra, 1);

            if (tentrealizadas >= 5){
                outputStream.write("\n\nVocê excedeu o limite de tentativas!! Comece novamente \n".getBytes());
                break;
            }

            if (descobertos.get(tamPalavra) == 0){
                tentrealizadas++;
                outputStream.write("\n\nLetra errada! Sua quantidade de erros: ".getBytes());
                outputStream.write(tentrealizadas.toString().getBytes());
                
            }
            else{
            outputStream.write("\n\nLetra Certa! Sua quantidade de erros: ".getBytes());
            outputStream.write(tentrealizadas.toString().getBytes());
            outputStream.write("\n\n".getBytes());
            }


/*             for (int i = 0; i < tamPalavra; i++){
                if ()
            } */

        }
        if (marcador != tamPalavra){
            outputStream.write("A palavra correta era -> ".getBytes());
            outputStream.write(palavra.getBytes());
            outputStream.write("\n\n".getBytes());
        }
        
        cs.close();
        
        System.out.println("Comunicação encerrada");
    }

    public static void main(String[] args) {
        Forca forca = new Forca();
        int port = 60000;
        try {
            
            ServerSocket chatServer = new ServerSocket(port);
            while(true) {
                System.out.println("esperando cliente");
                Socket clientSocket = chatServer.accept();
                System.out.println("cliente conectou");
                forca.getLog().clear();
                forca.clientHandler(clientSocket);
                forca.writeLog();
            }
        } catch(Exception e) {
            System.out.println("Seu código tem um erro, favor revisá-lo !!");
            e.toString();
        }
    }
    

}