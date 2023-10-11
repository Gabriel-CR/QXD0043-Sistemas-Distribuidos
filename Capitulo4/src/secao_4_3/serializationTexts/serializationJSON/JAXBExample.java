package secao_4_3.serializationTexts.serializationJSON;

import java.io.File;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import secao_4_3.serializationBinary.entidades.Pessoa;

public class JAXBExample {
	public static void main(String[] args) {
		// Criação do objeto pessoa
		Pessoa pessoa = new Pessoa("Antonio", 123456789, 15);

		// Metodo que utiliza o jaxb para converter o objeto em xml
		jaxbObjectToXML(pessoa);

		String fileName = "pessoa.xml";

		// Metodo que faz a leitura do arquivo xml
		jaxbXmlFileToObject(fileName);
	}

	private static void jaxbObjectToXML(Pessoa pessoa) {
		try {
			// Criação do JABXContext
			JAXBContext jaxbContext = JAXBContext.newInstance(Pessoa.class);

			// Criação do Marshaller
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// Formatação do marshaller
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Conversão do objeto em XML e impressão do mesmo no console.
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(pessoa, sw);
			String xmlContent = sw.toString();
			System.out.println(xmlContent);

			// Criação do arquivo
			File file = new File("pessoa.xml");

			// Escreve o XML no arquivo
			jaxbMarshaller.marshal(pessoa, file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	private static void jaxbXmlFileToObject(String fileName) {

		File xmlFile = new File(fileName); 
		JAXBContext jaxbContext;

		try {
			// Criação do JAXBContext para a classe Pessoa
			jaxbContext = JAXBContext.newInstance(Pessoa.class);

			// Criação do Unmarshaller
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			
			// Desserializa o XML de um arquivo para um objeto Pessoa e imprime no console
			Pessoa pessoa = (Pessoa) jaxbUnmarshaller.unmarshal(xmlFile);
			System.out.println(pessoa);
			    
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}