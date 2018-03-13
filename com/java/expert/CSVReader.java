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

    private ArrayList<String> read(String filePath) {
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
                        + "(Question ?)) THEN " + conclusion;
                this.rulesList.add(rule);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rulesList;
    }

    public ArrayList<String> getRulesList() {
        String csvFile = "csv/pokemon_rule_lvl_";

        for (int i = 0; i < 6; i++) {
            String file = csvFile + i + ".csv";
            this.rulesList.addAll(read(file));
        }
        return rulesList;
    }
}