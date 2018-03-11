package otus;

import info.debatty.java.stringsimilarity.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("*Небольшая программка по определению расстояний между двумя строками различными методами*\n\n" +
                "Введите две строки, расстояния между которыми вы хотите рассчитать :)");

        try(Scanner scanner = new Scanner(System.in)) {

            System.out.println("s1 = ");
            String s1 = scanner.nextLine();
            System.out.println("s2 = ");
            String s2 = scanner.nextLine();

            Cosine cosine = new Cosine();
            Jaccard jaccard = new Jaccard();
            Levenshtein levenshtein = new Levenshtein();
            NormalizedLevenshtein normalizedLevenshtein = new NormalizedLevenshtein();
            NGram nGram = new NGram();
            QGram qGram = new QGram();

            StringBuilder outputString = new StringBuilder();
            outputString.append("\nCosine: ").append(cosine.distance(s1, s2))
                    .append("\nJaccard: ").append(jaccard.distance(s1, s2))
                    .append("\nLevenshtein: ").append(levenshtein.distance(s1, s2))
                    .append("\nNormalizedLevenshtein: ").append(normalizedLevenshtein.distance(s1, s2))
                    .append("\nN-Gram: ").append(nGram.distance(s1, s2))
                    .append("\nQ-Gram: ").append(qGram.distance(s1, s2));

            System.out.println(outputString);
        }
    }
}
