package com.example.serializationTexts.serializationXML;

import com.example.serializationBinary.entidades.Person;
import com.thoughtworks.xstream.XStream;

public class XStreamXML {

   public static void main(String args[]) {
        // criando um Aluno
        Person person = new Person("Antonio", "Rafael Braga", 70);
        // exibindo o resultado da serializacao com XStream
        XStream xstream = new XStream();

        /*
         * Para que o XStream consiga serializar uma classe, ela deve ter um
         * alias, que é um nome alternativo para a classe. O alias é definido
         * com o método alias da classe XStream.
         * uma permissão para que o XStream possa serializar qualquer tipo de
         * objeto. Essa permissão é definida com o método addPermission da
         * classe XStream.
         */
        xstream.alias("Person", Person.class);
        xstream.addPermission(com.thoughtworks.xstream.security.AnyTypePermission.ANY);
        String representacao = xstream.toXML(person);
        System.out.println(representacao);
        
        XStream xstream2 = new XStream();
        xstream2.alias("Person", Person.class);
        xstream2.addPermission(com.thoughtworks.xstream.security.AnyTypePermission.ANY);
        Person person2 = (Person) xstream2.fromXML(representacao);
        System.out.println(person2.getName());
     }
}