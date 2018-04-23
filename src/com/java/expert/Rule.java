package com.java.expert;

import java.util.ArrayList;
import java.util.Arrays;

public class Rule {
    public ArrayList<IFact> Premises;
    public IFact Conclusion;
    public String Name;

    public ArrayList<IFact> getPremises() {
        return Premises;
    }

    public void setPremises(ArrayList<IFact> premises) {
        Premises = premises;
    }

    public IFact getConclusion() {
        return Conclusion;
    }

    public void setConclusion(IFact conclusion) {
        Conclusion = conclusion;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Rule(ArrayList<IFact> premises, IFact conclusion, String name) {
        Premises = premises;
        Conclusion = conclusion;
        Name = name;
    }

    @Override
    public String toString() {
        return Name + " : IF (" +String.join(" AND ", Arrays.asList(Premises) + ") THEN "+ Conclusion.toString());
    }
}
