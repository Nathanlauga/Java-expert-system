package com.java.expert;

import java.util.ArrayList;

public class RulesBase {
    protected ArrayList<Rule> rules;

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public void setRules(ArrayList<Rule> rules) {
        this.rules = rules;
    }

    public  RulesBase(){
        rules = new ArrayList<>();
    }

    public void ClearBase(){
        rules.clear();
    }

    public void AddRule(Rule r){
        rules.add(r);
    }

    public void Remove(Rule r){
        rules.remove(r);
    }
}
