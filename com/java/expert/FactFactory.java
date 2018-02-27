package com.java.expert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FactFactory {

    public static IFact Fact(IFact f, Motor m){
        IFact newFact;
        if (f.getClass().equals(IntFact.class)){
            int value = m.AskIntValue(f.Question());
            newFact = new IntFact(f.Name(), value, null, 0);
        }
        else {
            boolean value = m.AskBoolValue(f.Question());
            newFact = new BoolFact(f.Name(), value, null, 0);
        }
        return newFact;
    }

    public static IFact Fact(String factStr){
        factStr = factStr.trim();
        if (factStr.contains("=")){
            String[] nameVal = factStr.split("=|\\(|\\)");
            ArrayList<String> nameValue = new ArrayList<String>(Arrays.asList(nameVal));
            nameValue.removeAll(Collections.singleton(" "));
            nameValue.removeAll(Collections.singleton(""));

            if (nameValue.size() >= 2){
                String question = null;
                if (nameValue.size() == 3) question = nameValue.get(2).trim();
                return new IntFact(nameValue.get(0).trim(), Integer.parseInt(nameValue.get(1).trim()), question, 0);
            }
            else return null;
        }
        else {
            boolean value = true;
            if (factStr.startsWith("!")){
                value = false;
                factStr = factStr.substring(1).trim();
            }
            String[] nameVal = factStr.split("=|\\(|\\)");
            ArrayList<String> nameQuestion = new ArrayList<String>(Arrays.asList(nameVal));
            nameQuestion.removeAll(Collections.singleton(" "));
            nameQuestion.removeAll(Collections.singleton(""));
            String question = null;
            if (nameQuestion.size() == 2){
                question = nameQuestion.get(1).trim();
            }
            return new BoolFact(nameQuestion.get(0).trim(), value, question, 0);
        }
    }
}
