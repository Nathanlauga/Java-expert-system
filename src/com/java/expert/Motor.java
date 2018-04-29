package com.java.expert;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Motor {
    private FactsBase factsBase;
    private RulesBase rulesBase;
    private HumanInterface humanInterface;

    Motor(HumanInterface humanInterface) {
        this.humanInterface = humanInterface;
        this.factsBase = new FactsBase();
        this.rulesBase = new RulesBase();
    }

    public int AskIntValue(String p){
        return humanInterface.AskIntValue(p);
    }

    public boolean AskBoolValue(String p){
        return humanInterface.AskBoolValue(p);
    }

    public String AskStringValue(String p) {
        return humanInterface.AskStringValue(p);
    }

    private int CanApply(Rule r){
        int maxlevel = -1;

        for (IFact f : r.getPremises()) {
            IFact foundFact = factsBase.search(f.Name());
            if (foundFact == null){
                if (f.Question() != null){
                    foundFact = FactFactory.Fact(f, this);
                    factsBase.AddFact(foundFact);
                    maxlevel = Math.max(maxlevel, 0);
                }
                else {
                    return -1;
                }
            }
            if (!foundFact.Value().equals(f.Value())) {
                return -1;
            }
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
                return new Pair<>(r, level);
            }
        }
        return null;
    }

    public void Solve(){
        RulesBase usableRules = new RulesBase();
        usableRules.setRules(new ArrayList<>(rulesBase.getRules()));
        Pair<Rule, Integer> t;
        factsBase.clear();

        while ((t = FindUsableRule(usableRules)) != null){
            IFact newFact = t.getKey().getConclusion();
            newFact.SetLevel(t.getValue() + 1);
            factsBase.AddFact(newFact);

            String conclusion = t.getKey().getConclusion().Name();

            //TODO  : garder uniquement les règles qui nous intéresse
            RulesBase tmpBase = new RulesBase();
            for (Rule r : usableRules.getRules()){
                if (r.getConclusion().toString().contains(conclusion)) tmpBase.getRules().add(r);
                else if (r.getPremises().get(0).toString().contains(conclusion))tmpBase.getRules().add(r);
            }
//            generation-6/evolution-1/type1-flying/type2-dragon/is_tall-Non/is_legendary-Non,Sound Wave,Noibat
            usableRules = tmpBase;
            usableRules.Remove(t.getKey());

            if(usableRules.getRules().size()+newFact.Level() == 7 && newFact.Level() != 7){
                newFact = usableRules.getRules().get(usableRules.getRules().size()-1).getConclusion();
                newFact.SetLevel(t.getValue() + 2);
                factsBase.AddFact(newFact);
                break;
            }
        }
        humanInterface.PrintFacts(factsBase.getFacts());
    }

    public void AddRule(String ruleStr){
        String[] line = ruleStr.split(" : ");
        String name = line[0].trim();

        if (line.length == 2){
            List<String> premisesAndConclusion = new ArrayList<>(Arrays.asList(line[1].trim().split("IF|THEN")));
            premisesAndConclusion.remove(0);
            if (premisesAndConclusion.size() == 2){
                String[] premises = premisesAndConclusion.get(0).trim().split("AND");
                ArrayList<IFact> premiseList = new ArrayList<>();

                for (String prem: premises) {
                    IFact premise = FactFactory.Fact(prem);
                    premiseList.add(premise);
                }
                IFact conclusion = FactFactory.Fact(premisesAndConclusion.get(1).trim());

                rulesBase.AddRule(new Rule(premiseList, conclusion, name));
            }
        }
    }
}
