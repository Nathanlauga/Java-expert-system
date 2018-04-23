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
    private ArrayList<String> read(String filePath) {
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
                        + "(Question ? "+filePath+")) THEN " + conclusion;
                rules.add(rule);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rules;
    }

    public ArrayList<String> getRulesList() {
        String csvFile = "csv/pokemon_rule_lvl_";

        for (int i = 0; i < 7; i++) {
            String file = csvFile + i + ".csv";
            this.rulesList.addAll(read(file));
        }
        return rulesList;
    }
}