package com.java.expert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {
    private ArrayList<String> rulesList;

    CSVReader() {
        this.rulesList = new ArrayList<>();
    }

    // 0 : generation - 1,2,...
    // 1 : evolution - 0,1,2 ...
    // 2 : type1 - grass, fire, ...
    // 3 : type2 - '', grass, fire, ...
    // 4 : isTall - True/False
    // 5 : is_legendary - True/False
    // 6 : classification - seed
    private ArrayList<String> read(String filePath, String question) {

        ArrayList<String> rules = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
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
                        + "( "+question+" )) THEN " + conclusion;
                rules.add(rule);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rules;
    }

    public ArrayList<String> getRulesList() {
        String csvFile = "csv/pokemon_rule_lvl_";
        String[] questions = {
                "Quel est la génération du pokémon ? entre 1 et 7 inclu",
                "Quel est son stade d'évolution ? 1, 2 ou 3",
                "Quel est son premier type ?",
                "Quel est son second type ? Si pas de type répondre Aucun",
                "Est-il plus grand qu'un mètre ? Oui ou Non",
                "Est-il légendaire ? Oui ou Non",
                "Quelle est sa classification ?"
        };

        for (int i = 0; i < 7; i++) {
            String file = csvFile + i + ".csv";
            this.rulesList.addAll(read(file, questions[i]));
        }
        return rulesList;
    }
}