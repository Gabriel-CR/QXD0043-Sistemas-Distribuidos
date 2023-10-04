package streams;

import java.io.IOException;

import entidades.Pessoa;

public class TestStreamsFile {
	public static void main(String[] args) throws IOException {

		Pessoa[] pessoas = new Pessoa[0];
		PessoasOutputStream pos = new PessoasOutputStream(pessoas, System.out);
		pos.writeFile();
		pos.close();
		
		PessoasInputStream pis = new PessoasInputStream(pessoas, System.in);
		pessoas = pis.readFile();
		pis.close();

		for (Pessoa pessoa : pessoas) {
			if (pessoa != null) {
				System.out.println(pessoa);
			}
		}
	}
}
