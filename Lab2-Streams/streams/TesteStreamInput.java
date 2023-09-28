package streams;
import java.io.IOException;

import entidades.Pessoa;

public class TesteStreamInput {
    public static void main(String[] args) throws IOException {

		Pessoa[] pessoas = new Pessoa[1];
		PessoasInputStream pis = new PessoasInputStream(pessoas, System.in);
		pessoas = pis.readTCP();
		pis.close();
	}
}
