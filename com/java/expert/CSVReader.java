package com.java.expert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {

    public static void main(String[] args) {
        String line;
        String csvFile = "csv/pokemon_rule_lvl_";
        String file;

        for (int i = 0; i < 3; i++) {
            file = csvFile.concat(String.valueOf(i).concat(".csv"));

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String headers[] = br.readLine().split(",");
                String factName = headers[2];

                while ((line = br.readLine()) != null) {
                    String[] result = line.split(",");
                    String ruleName = result[0];
                    String premises = result[1];
                    String ruleValue = result[2];
                    String conclusion = result[3];
                    String rule = ruleName + " : IF ("
                            + (!premises.equals("") ? premises + " AND " : premises)
                            + factName + "=" + ruleValue
                            + "(Question ?)) THEN " + conclusion;
                    System.out.println(rule);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}