package com.example.serializationTexts.serializationXML;

import com.example.serializationBinary.entidades.Person;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class XStreamJSONExample {

	public static void main(String[] args) {

        Person pessoa = new Person("Antonio", "Rafael", 15);
        String xml = new XStreamJSONExample().object2JSON(pessoa);
        System.out.println(xml);
        JSON2Object(xml);
	}

	private String object2JSON(Person pessoa) {
		XStream xstream = new XStream(new JettisonMappedXmlDriver());
        //XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
        xstream.setMode(XStream.NO_REFERENCES);
        xstream.alias("Person", Person.class);
		String xml = xstream.toXML(pessoa);
        System.out.println(xml);
		return xml;
	}
	
	private static void JSON2Object(String xml) {
		//String json = "{\"pessoa\":{\"nome\":\"Banana\",\"cpf\":123456789,\"idade\":23}}";
		XStream xstream = new XStream(new JettisonMappedXmlDriver());
		xstream.alias("Person", Person.class);
		xstream.addPermission(AnyTypePermission.ANY);
		Person pessoa = (Person) xstream.fromXML(xml);
		System.out.println(pessoa.getName());		
	}
}