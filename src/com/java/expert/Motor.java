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
        Pair<Rule, Integer> ruleWithDepthLevel;
        factsBase.clear();
        int maxLevel = 7;
        int currentLevel = 0;

        while ((ruleWithDepthLevel = FindUsableRule(usableRules)) != null){
            IFact newFact;
            ArrayList<Rule> usableRulesList;
            // CHANGE THE CURRENT LEVEL VALUE
            currentLevel = ruleWithDepthLevel.getValue() + 1;

            // ADD THE NEW FACT TO THE FACT BASE
            newFact = ruleWithDepthLevel.getKey().getConclusion();
            newFact.SetLevel(currentLevel);
            factsBase.AddFact(newFact);

            String conclusion = ruleWithDepthLevel.getKey().getConclusion().Name();

            // DROP USELESS RULES AND THE RULE THAT WAS ADD INTO THE FACT BASE
            usableRules = dropUselessRules(usableRules, conclusion);
            usableRules.Remove(ruleWithDepthLevel.getKey());
            usableRulesList = usableRules.getRules();

            // CHECK IF THE NEXT QUESTIONS ARE USELESS TO ASK
            Boolean questionIsUseless;
            for (int i=currentLevel ; i<=maxLevel ; i++) {
                if (usableRulesList.size() > 1) {
                    String premiseOneName = usableRulesList.get(0).getPremises().get(1).Name();
                    String premiseTwoName = usableRulesList.get(1).getPremises().get(1).Name();
                    questionIsUseless = !premiseOneName.equals(premiseTwoName);

                    // IF WE GET ONLY ONE POSSIBLE WAY THEN ADD IT DROP ALL THE OTHER RULES --> IT'S THE ANSWER
                    if (currentLevel+usableRulesList.size() == maxLevel && currentLevel != maxLevel){
                        currentLevel+=1;
                        maxLevel = currentLevel;
                        newFact = usableRulesList.get(usableRulesList.size()-1).getConclusion();
                    }
                    // IF THE NEXT QUESTION IS USELESS THEN ADD IT TO THE FACT BASE
                    // AND DROP ALL USELESS RULES
                    else if (ruleWithDepthLevel.getValue() != 0 && questionIsUseless) {
                        currentLevel+=1;
                        newFact = usableRulesList.get(0).getConclusion();
                    }
                    // ELSE BREAK THE FOR LOOP
                    else break;

                    newFact.SetLevel(currentLevel);
                    factsBase.AddFact(newFact);
                    usableRules = dropUselessRules(usableRules, newFact.Name());
                    usableRules.Remove(usableRulesList.get(0));
                    usableRulesList = usableRules.getRules();
                }
            }

            // PRINT ALL POSSIBILITES FOR CLASSFICATIONS
            if (newFact.Level()==maxLevel-1){
                System.out.println("Les classifications possibles sont : ");
                for(Rule r : usableRulesList){
                    System.out.println("- "+r.getPremises().get(1).Value());
                }
            }
        }
        humanInterface.PrintFacts(factsBase.getFacts());
    }

    private RulesBase dropUselessRules(RulesBase usableRules, String conclusion){
        RulesBase tmpBase = new RulesBase();
        for (Rule r : usableRules.getRules()){
            if (r.getConclusion().toString().contains(conclusion)) tmpBase.getRules().add(r);
            else if (r.getPremises().get(0).toString().contains(conclusion))tmpBase.getRules().add(r);
        }
        return tmpBase;
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
