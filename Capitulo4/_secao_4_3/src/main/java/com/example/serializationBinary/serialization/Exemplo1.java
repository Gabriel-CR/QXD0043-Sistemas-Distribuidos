package com.example.serializationBinary.serialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.example.serializationBinary.entidades.Pessoa;
import com.example.util.Desempacotamento;
import com.example.util.Empacotamento;

public class Exemplo1 {
    public Exemplo1() {super();}

	public static void main(String[] args) throws IOException {

		String nome;
		double pc, alt;
		Scanner ler = new Scanner(System.in);

		// 1) desserializacao: recuperando os objetos gravados no arquivo binario "dados.dat"
		ArrayList<Object> pessoa = Desempacotamento.lerArquivoBinario("dados.dat");

		// 2) entrada de dados
		while (true) {
			System.out.printf("Ficha numero: %d.\n", (pessoa.size() + 1));
			System.out.printf("Informe o nome da pessoa, FIM para encerrar:\n");
			nome = ler.nextLine();
			if (nome.equalsIgnoreCase("FIM"))
				break;

			System.out.printf("\nInforme o peso corporal (em kg)...............: ");
			pc = ler.nextDouble();

			System.out.printf("Informe a altura (em metros: 1,77 por exemplo): ");
			alt = ler.nextDouble();

			pessoa.add(new Pessoa(nome, pc, alt)); // adiciona um novo objeto a lista

			ler.nextLine(); // esvazia o buffer do teclado
			System.out.printf("\n");
		}
		ler.close();

		// 3) serializacao: gravando o objeto no arquivo binario "dados.dat"
		Empacotamento.gravarArquivoBinario(pessoa, "dados.dat");
	}
}
