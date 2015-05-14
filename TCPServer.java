/*
	Daniel Ramos Miola - 438340

	Exercício da disciplina de Redes de computadores do curso de 
	Bacharelado em Ciencia da Comptaçao - UFSCar - Sorocaba

	Código do servidor TCP estudado em sala alterado para atender
	várias conexoes ao mesmo tempo
*/

import java.io.*; 
import java.net.*; 

/*
	A classe usa a interface Runnable para implementar o metodo run() que é disparado
	ao iniciar uma thread da classe. No simples exercicio não era preciso criar mais
	uma classe extendesse Thread.
*/
class TCPServer implements Runnable{

	// Atributo da classe que guarda o socket para a criaçao da thread
	Socket connectionSocket;

	// Construtor da classe que recebe o socket e seta no objeto da classe	
	public TCPServer(Socket connectionSocket){
		this.connectionSocket = connectionSocket;
	}

	public static void main(String argv[]) throws Exception { 

		ServerSocket welcomeSocket = new ServerSocket(6789); 

		while(true) { 

			Socket connectionSocket = welcomeSocket.accept(); 

			// Cria objeto da classe passando o socket para o construtor
			TCPServer s = new TCPServer(connectionSocket);

			// Cria nova thread passando como parametro o objeto da classe
			// que implementa o metodo run()
			Thread threads = new Thread(s);

			// Inicia thread que executa o metodo run(), liberando a conexao
    		threads.start();
	
		} 
	}

	// Método run() que executa a requisiçao
	public void run(){

		String clientSentence; 
		String capitalizedSentence; 

		try{
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(this.connectionSocket.getInputStream())); 
			DataOutputStream outToClient = new DataOutputStream(this.connectionSocket.getOutputStream()); 
			
			clientSentence = inFromClient.readLine(); 
			capitalizedSentence = clientSentence.toUpperCase() + '\n'; 
			
			outToClient.writeBytes(capitalizedSentence);

			// imprime o nome gerado para a thread no final da execuçao de cada requisiçao
			System.out.println(Thread.currentThread().getName());

		} catch (IOException io) {
			io.printStackTrace();
		}
	}

}