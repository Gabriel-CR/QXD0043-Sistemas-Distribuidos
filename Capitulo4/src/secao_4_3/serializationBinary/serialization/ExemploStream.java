//https://www.devmedia.com.br/introducao-a-serializacao-de-objetos/3050
package secao_4_3.serializationBinary.serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import secao_4_3.serializationBinary.entidades.Cliente;

public class ExemploStream {
	public static void main(String[] args) {
		// Cria o objeto serializado
		Cliente cliente1 = new Cliente("Jose", 'M', "123.456.789-01");
		Cliente cliente2 = new Cliente("Maria", 'F', "123.456.789-02");
		try {
			// Gera o arquivo para armazenar o objeto
			FileOutputStream arquivoGrav = new FileOutputStream("saida.dat");
			// Classe responsavel por inserir os objetos
			ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);
			// Grava o objeto cliente no arquivo
			// writeObject faz a serializacao do objeto	
			objGravar.writeObject(cliente1);
			objGravar.writeObject(cliente2);
			// Fecha streams ObjectOutputStream
			// o metodo flush libera a memoria e forca a gravacao dos dados no arquivo
			// sem ele os dados ficam na memoria e nao sao gravados no arquivo
			// o metodo close fecha o stream e libera os recursos do sistema
			objGravar.flush();
			objGravar.close();
			// Fecha streams FileOutputStream	
			arquivoGrav.flush();
			arquivoGrav.close();
			System.out.println("Objetos gravados com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Recuperando objetos: ");
		try {
			// Carrega o arquivo
			FileInputStream arquivoLeitura = new FileInputStream("saida.dat"); 
			// Recupera os objetos do arquivo
			ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura);
			// Imprime os objetos
			// readObject faz a desserializacao dos objetos, ele retorna um Object
			// por isso e necessario fazer o cast
			System.out.println(objLeitura.readObject());
			
			Cliente cliente1_novo = (Cliente) objLeitura.readObject();
			System.out.println(cliente1_novo.getNome());
			
			objLeitura.close();
			arquivoLeitura.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}