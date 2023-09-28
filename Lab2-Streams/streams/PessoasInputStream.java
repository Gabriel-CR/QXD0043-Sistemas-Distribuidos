package streams;

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
			File myObj = new File("./files/filename.txt");
			Scanner myReader = new Scanner(myObj);
			int quantidadePessoas = myReader.nextInt();
			pessoas = new Pessoa[quantidadePessoas];
			
			for (int i = 0; i < quantidadePessoas; i++) {
				String nome = myReader.next();
				double cpf = myReader.nextDouble();
				int idade = myReader.nextInt();
				pessoas[i] = new Pessoa(nome, cpf, idade);
			}

			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		/*
		 * Printa todas as pessoas
		 */
		

		return pessoas;
	}
	
	/*
	 * Leitura dos dados de uma pessoa
	 * usando a leitura via rede usando o protocolo TCP
	 */
	public Pessoa[] readTCP() {
		Socket socketRead = null;
		Scanner in = new Scanner(is);

		try{
			socketRead = new Socket("172.25.227.154", 7896);
			
			DataOutputStream out = new DataOutputStream(socketRead.getOutputStream());
			
			System.out.println("Informe seus dados...");
			System.out.println("Nome: ");
			String nome = in.nextLine();
			System.out.println("CPF: ");
			double cpf = in.nextDouble();
			System.out.println("Idade: ");
			int idade = in.nextInt();

			pessoas = new Pessoa[1];
			pessoas[0] = new Pessoa(nome, cpf, idade);
			out.writeUTF(pessoas[0].getNome() + "\n" + pessoas[0].getCpf() + "\n" + pessoas[0].getIdade());
			
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
