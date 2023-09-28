package streams;

import java.io.IOException;

import entidades.Pessoa;

public class TestStreams {
	public static void main(String[] args) throws IOException {

		/*
		 * O código abaixo ler os dados de uma pessoa
		 * direto do terminal do sistema operacional
		 */
		Pessoa[] pessoas = new Pessoa[1];
		PessoasInputStream pis = new PessoasInputStream(pessoas, System.in);
		pessoas = pis.readTCP();
		pis.close();        
		
		/*
		 * O código a seguir excreve os dados do objeto pessoa
		 * no terminal usando a classe PessoaOutputStream
		 */
		PessoasOutputStream pos = new PessoasOutputStream(pessoas, System.out);
		pos.writeTCP();
		pos.close();
	}
}
