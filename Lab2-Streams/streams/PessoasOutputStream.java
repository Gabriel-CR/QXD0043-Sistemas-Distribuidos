package streams;

import java.io.*;
import java.net.*;

import entidades.Pessoa;

public class PessoasOutputStream extends OutputStream {
	
	private OutputStream op;
	private Pessoa[] pessoas;
	
	public PessoasOutputStream() {}
	
	public PessoasOutputStream(Pessoa[] p, OutputStream os) {
		this.pessoas = p;
		this.op = os;
	}

	public void writeSystem() {
		
		PrintStream opLocal = new PrintStream(op);
		
		//envia quantidade de pessoas;
		int qtdpessoa = pessoas.length;
		opLocal.println("Quantidade de pessoas: "+qtdpessoa);
		
		//envia os dados de um conjunto (array) de Pessoas
		for (Pessoa pessoa : pessoas) {
			if (pessoa != null) {
				int tamanhoNomePessoa = pessoa.getNome().getBytes().length;
				String nome = pessoa.getNome();
				double cpf = pessoa.getCpf();
				int idade = pessoa.getIdade();
							
				opLocal.println(" tamanhoNomePessoa: "+tamanhoNomePessoa+ "\n"+
								" nomePessoa: "+nome+ "\n"+
								" cpf: "+cpf+ "\n"+
								" idade: "+idade);
			}
		}
	}

	public void writeFile() {
		// envia os dados de um conjunto (array) de Pessoas
		System.out.println("writeFile");
	}
	
	public void writeTCP() {
		// envia os dados de um conjunto (array) de Pessoas

		try{
			System.out.println("Aguardando conexão...");
			ServerSocket listenSocket = new ServerSocket(7896);

			while(true){
				Socket clientSocket = listenSocket.accept();
				System.out.println("Conexão estabelecida com: "+clientSocket.getInetAddress().getHostAddress());
				Connection c = new Connection(clientSocket, pessoas);
			}
			
		} catch (IOException e) {
			System.out.println("readline:" + e.getMessage());
		}
	}		
	
	@Override
	public void write(int b) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("write");
	}

	@Override
	public String toString() {
		return "Ola, mundo! Estamos sobrescrevendo o método toString()!\n"
				+ " PessoasOutputStream [ \n"
				+ " getClass()=" + getClass() +",\n"
				+ " hashCode()=" + hashCode() +",\n"
				+ " toString()="+ super.toString() + "]";
	}
}

class Connection extends Thread {
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;
	Pessoa[] pessoas;

	public Connection(Socket aClientSocket, Pessoa[] apessoas) {

		try {
			clientSocket = aClientSocket;
			pessoas = apessoas;
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
			this.start();
		} catch (IOException e) {
			System.out.println("Connection:" + e.getMessage());
		}
	}

	public void run() {
		try { // an echo server
			System.out.println("Aguardando dados...");
			
			String data = in.readUTF(); // read a line of data from the stream

			//separar os dados
			String[] dados = data.split("\n");
			String nome = dados[0];
			double cpf = Double.parseDouble(dados[1]);
			int idade = Integer.parseInt(dados[2]);	

			// armazenar os dados em um objeto Pessoa
			Pessoa pessoa = new Pessoa(nome, cpf, idade);

			if(pessoas[0] == null){
				pessoas[0] = pessoa;
			}else{
				int tamanhoAtual = pessoas.length;
				Pessoa[] pessoasAtualizado = new Pessoa[tamanhoAtual + 1];
				for (int i = 0; i < tamanhoAtual; i++) {
					pessoasAtualizado[i] = pessoas[i];
				}

				pessoasAtualizado[tamanhoAtual] = pessoa;
				pessoas = pessoasAtualizado;
			}
			
			System.out.println("Quantidade de pessoas: "+pessoas.length);
			for (int i = 0; i < pessoas.length; i++) {
				System.out.println("Pessoa "+(i)+": "+pessoas[i].getNome());
			}


		} catch (EOFException e) {
			System.out.println("EOF:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("readline:" + e.getMessage());
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				/* close failed */}
		}

	}
}
