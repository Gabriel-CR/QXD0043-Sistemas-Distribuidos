package streams;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

import entidades.Pessoa;

public class PessoasInputStream extends InputStream {

	private InputStream is;
	private Pessoa[] pessoas;
	
	public PessoasInputStream(Pessoa[] p, InputStream is) {
		this.pessoas = p;
		this.is = is;
	}

	public Pessoa[] readSystem() {
       
        Scanner sc = new Scanner(is);
        
		System.out.println("Informa o nome da pessoa:"); 
		String nome = sc.nextLine();
		System.out.println("Informe o cpf da pessoa:"); 
		double cpf = sc.nextDouble();
		System.out.println("Informe a idade do pessoa:"); 
		int idade = sc.nextInt();
		
		pessoas[0] = new Pessoa(nome, cpf, idade);
		
		sc.close();
		
		return pessoas;
	}
	
	/*
	 * Leitura dos dados de uma pessoa
	 * usando a leitura de um arquivo
	 */
	public Pessoa[] readFile() {
		try {
			String path = "./files/filename.txt";
			File myObj = new File(path);
			Scanner myReader = new Scanner(myObj);
			int quantidadePessoas = myReader.nextInt();
			pessoas = new Pessoa[quantidadePessoas];
			
			for (int i = 0; i < quantidadePessoas; i++) {
				String nome = myReader.next();
				double cpf = Double.parseDouble(myReader.next());	
				int idade = myReader.nextInt();
				pessoas[i] = new Pessoa(nome, cpf, idade);
			}

			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Erro ao ler arquivo" + e.getMessage());
		}		

		return pessoas;
	}
	
	/*
	 * Leitura dos dados de uma pessoa
	 * usando a leitura via rede usando o protocolo TCP
	 */
	public Pessoa[] readTCP() {
		// cria um socket para ler e escrever dado
		Socket socketRead = null;

		// cria um scanner para ler os dados do terminal
		Scanner inScanner = new Scanner(is);

		try{
			// passa o endereço do servidor e a porta
			socketRead = new Socket("localhost", 7896);

			// cria um canal de saída para enviar os dados para o servidor
			DataOutputStream out = new DataOutputStream(socketRead.getOutputStream());
			DataInputStream in = new DataInputStream(socketRead.getInputStream());
			
			// recebe os dados do terminal
			System.out.println("Informe seus dados...");
			System.out.println("Nome: ");
			String nome = inScanner.nextLine();
			System.out.println("CPF: ");
			double cpf = inScanner.nextDouble();
			System.out.println("Idade: ");
			int idade = inScanner.nextInt();

			inScanner.close();

			// envia os dados para o servidor
			pessoas = new Pessoa[1];
			pessoas[0] = new Pessoa(nome, cpf, idade);
			out.writeUTF(pessoas[0].getNome() + "\n" + pessoas[0].getCpf() + "\n" + pessoas[0].getIdade());

			// recebe o id da pessoa cadastrada
			String data = in.readUTF();

			System.out.println("Pessoa cadastrada com sucesso!");
			System.out.println("Seu id é: " + data);
			
		}catch(IOException e){
			System.out.println("Socket:" + e.getMessage());
		
		}finally{
			if(socketRead != null){
				try{
					socketRead.close();
				}catch(IOException e){
					System.out.println("close:" + e.getMessage());
				}
			}
		}

		return pessoas;
	}

	@Override
	public int read() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

}
