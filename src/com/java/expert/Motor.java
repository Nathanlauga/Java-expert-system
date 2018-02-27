package com.java.expert;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Motor {
    private FactsBase fDB;
    private RulesBase rDB;
    private HumanInterface ihm;

    public Motor(HumanInterface ihm) {
        this.ihm = ihm;
        this.fDB = new FactsBase();
        this.rDB = new RulesBase();
    }

    public int AskIntValue(String p){
        return ihm.AskIntValue(p);
    }

    public boolean AskBoolValue(String p){
        return ihm.AskBoolValue(p);
    }

    private int CanApply(Rule r){
        int maxlevel = -1;

        for (IFact f:r.getPremises()) {
            IFact foundFact = fDB.search(f.Name());
            if (foundFact == null){
                if (f.Question() != null){
                    foundFact = FactFactory.Fact(f, this);
                    fDB.AddFact(foundFact);
                    maxlevel = Math.max(maxlevel, 0);
                }
                else return -1;
            }
            if (!foundFact.Value().equals(f.Value()))return -1;
            else {
                maxlevel = Math.max(maxlevel, foundFact.Level());
            }
        }
        return maxlevel;
    }

    private Pair<Rule, Integer> FindUsableRule(RulesBase rBase){
        for (Rule r:rBase.getRules()) {
            int level = CanApply(r);
            if (level != -1){
                Pair pair = new Pair<>(r, level);
                return pair;
            }
        }
        return null;
    }

    public void Solve(){
        boolean moreRules = true;
        RulesBase usableRules = new RulesBase();
        usableRules.setRules(new ArrayList<>(rDB.getRules()));

        fDB.clear();

        while (moreRules){
            Pair<Rule, Integer> t = FindUsableRule(usableRules);
            if (t != null){
                IFact newFact = t.getKey().getConclusion();
                newFact.SetLevel(t.getValue() + 1);
                fDB.AddFact(newFact);

                usableRules.Remove(t.getKey());
            }
            else {
                moreRules = false;
            }
        }
        ihm.PrintFacts(fDB.getFacts());
    }

    public void AddRule(String ruleStr){
        String[] nameTmp = ruleStr.split(" : ");
        ArrayList<String> splitName = new ArrayList<String>(Arrays.asList(nameTmp));
        splitName.removeAll(Collections.singleton(" "));
        splitName.removeAll(Collections.singleton(""));

        if (splitName.size() == 2){
            String name = splitName.get(0);
            String[] splitPremConclTmp = splitName.get(1).split("IF|THEN");
            ArrayList<String> splitPremConcl = new ArrayList<String>(Arrays.asList(splitPremConclTmp));
            splitPremConcl.removeAll(Collections.singleton(" "));
            splitPremConcl.removeAll(Collections.singleton(""));
            if (splitPremConcl.size() == 2){
                ArrayList<IFact> premises = new ArrayList<>();

                String[] premisesStrTmp = splitPremConcl.get(0).split("AND");
                ArrayList<String> premisesStr = new ArrayList<String>(Arrays.asList(premisesStrTmp));
                premisesStr.removeAll(Collections.singleton(" "));
                premisesStr.removeAll(Collections.singleton(""));

                for (String prem: premisesStr) {
                    IFact premise = FactFactory.Fact(prem);
                    premises.add(premise);
                }
                String conclusionStr = splitPremConcl.get(1).trim();
                IFact conclusion =FactFactory.Fact(conclusionStr);

                rDB.AddRule(new Rule(premises, conclusion, name));
            }
        }
    }
}
