package com.example.serializationTexts.serializationXML;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.example.serializationBinary.entidades.Person;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class XStreamXMLExample {
	public static void main(String[] args) throws IOException {
		Person pessoa = new Person("Gabriel", "Castro", 20);
		Object2XML(pessoa);
		System.out.println();
		XML2Object();
	}

	private static void XML2Object() throws IOException {
		
		XStream instream = new XStream();

		BufferedReader br = new BufferedReader(new FileReader("pessoa.xml"));
		StringBuffer buff = new StringBuffer();
		String line;
		while((line = br.readLine()) != null){
		   buff.append(line);
		}
		
		instream.alias("Person", Person.class);
		instream.addPermission(AnyTypePermission.ANY);
		
		Person p = (Person) instream.fromXML(buff.toString());
		System.out.println("Nome: " + p.getName());
		System.out.println("Sobrenome: " + p.getLastName());
		System.out.println("idade: " + p.getAge());
	}
	
	
	private static void Object2XML(Person pessoa) throws IOException {
		XStream xstream = new XStream();
		xstream.alias("Person", Person.class);
		String xml = xstream.toXML(pessoa);
		System.out.println(xml);
		
		FileWriter gravar = new FileWriter("pessoa.xml");
		try {
		    BufferedWriter s = new BufferedWriter(gravar);
		    s.write(xml);
		    s.flush();
		    s.close();		
		} catch (IOException e) {
			System.out.println("Olá");
			e.printStackTrace();
		} finally {
		    gravar.close();
		}
	}
}
