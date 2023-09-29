package streams;

import java.io.*;
import java.net.*;
import java.util.Scanner;

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
		/*
		 * Ler os dados de um conjunto (array) de Pessoas
		 * e escrever em um arquivo
		 */
		
		// ler a quantidade de pessoas do terminal
		Scanner sc = new Scanner(System.in);
		System.out.print("Informe a quantidade de pessoas: ");
		int quantidadePessoas = sc.nextInt();

		// criar um array de pessoas com a quantidade informada
		pessoas = new Pessoa[quantidadePessoas];

		// ler os dados de cada pessoa
		for (int i = 0; i < quantidadePessoas; i++) {
			System.out.print("Informe o nome da pessoa: ");
			String nome = sc.next();
			System.out.print("Informe o cpf da pessoa: ");
			double cpf = sc.nextDouble();
			System.out.print("Informe a idade da pessoa: ");
			int idade = sc.nextInt();
			pessoas[i] = new Pessoa(nome, cpf, idade);
		}

		// escrever os dados no arquivo
		try {
			File myObj = new File("./files/filename.txt");
			if (myObj.createNewFile()) {
				System.out.println("Arquivo criado: " + myObj.getName());
			} else {
				System.out.println("Usando arquivo já existente...");
			}
		} catch (IOException e) {
			System.out.println("Ocorreu um erro ao localizar o arquivo...");
			e.printStackTrace();
		}

		// escrever a quantidade de pessoas no arquivo
		try {
			FileWriter myWriter = new FileWriter("./files/filename.txt");
			myWriter.write(quantidadePessoas + "\n");
			myWriter.close();
			System.out.println("Sucesso ao escrever a quantidade de pessoas no arquivo...");
		} catch (IOException e) {
			System.out.println("Ocorreu ao escrever a quantidade de pessoas no arquivo.");
			e.printStackTrace();
		}

		// escrever os dados de cada pessoa no arquivo
		try {
			FileWriter myWriter = new FileWriter("./files/filename.txt", true);
			for (int i = 0; i < quantidadePessoas; i++) {
				myWriter.write(pessoas[i].getNome() + "\n");
				myWriter.write(pessoas[i].getCpf() + "\n");
				myWriter.write(pessoas[i].getIdade() + "\n");
			}
			myWriter.close();
			System.out.println("Sucesso ao escrever os dados das pessoas no arquivo.");
		} catch (IOException e) {
			System.out.println("Ocorreu um erro ao escrever os dados das pessoas no arquivo.");
			e.printStackTrace();
		}
	}
	
	public void writeTCP() {
		// envia os dados de um conjunto (array) de Pessoas
		try{
			System.out.println("Aguardando conexão...");
			ServerSocket listenSocket = new ServerSocket(7896);

			while(true){
				Socket clientSocket = listenSocket.accept();
				System.out.println("Conexão estabelecida com: "+clientSocket.getInetAddress().getHostAddress());
				Connection c = new Connection(clientSocket, this);		
			}
			
		} catch (IOException e) {
			System.out.println("readline:" + e.getMessage());
		}
	}
	
	// adiciona um objeto Pessoa ao final do array de Pessoas
	public void add(Pessoa p) {
		if(pessoas[0] == null){
			pessoas[0] = p;
		}else{
			Pessoa[] novoArrayPessoas = new Pessoa[pessoas.length + 1];

			// Copiar os objetos existentes para o novo array
			for (int i = 0; i < pessoas.length; i++) {
				novoArrayPessoas[i] = pessoas[i];
			}

			// Adicionar o novo objeto ao final do novo array
			novoArrayPessoas[pessoas.length] = p;

			// Atualizar a referência para o novo array
			pessoas = novoArrayPessoas;
		}
	}

	public void printArrayPessoas() {
		PrintStream opLocal = new PrintStream(op);

		opLocal.println("Quantidade de pessoas: "+ pessoas.length);

		for (Pessoa pessoa : pessoas) {
			if (pessoa != null) {
					opLocal.println(" nomePessoa: "+pessoa.getNome()+ "\n"+
									" cpf: "+pessoa.getCpf()+ "\n"+
									" idade: "+pessoa.getIdade() + "\n" +
									" ---------------------------------\n");
			}
		}
	}

	public int returnIdPessoa(Pessoa p) {
		int id = 0;
		for (int i = 0; i < pessoas.length; i++) {
			if (pessoas[i].getCpf() == p.getCpf()) {
				id = i;
			}
		}
		return id;
	}
	
	@Override
	public void write(int b) throws IOException {
		// TODO Auto-generated method stub
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
	PessoasOutputStream pessoas;

	public Connection(Socket aClientSocket, PessoasOutputStream apessoas) {

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

			// adicionar o objeto Pessoa ao array de Pessoas
			pessoas.add(pessoa);

			// imprimir o array de Pessoas
			pessoas.printArrayPessoas();

			// enviar o id da pessoa para o cliente
			out.writeUTF("id: " + pessoas.returnIdPessoa(pessoa));


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