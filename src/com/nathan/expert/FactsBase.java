package com.nathan.expert;

import java.util.ArrayList;
import java.util.List;

public class FactsBase {
    protected ArrayList<IFact> facts;

    public ArrayList<IFact> getFacts() {
        return facts;
    }

    public FactsBase() {
        this.facts = new ArrayList<IFact>();
    }

    public void clear(){
        facts.clear();
    }

    public void AddFact(IFact f){
        facts.add(f);
    }

    public IFact search(String _name){
        for (IFact f:facts) {
            if (f.Name().equals(_name))
                return f;
        }
        return null;
    }

    public Object Value(String _name){
        IFact fact = null;
        for (IFact f:facts) {
            if (f.Name().equals(_name))
               fact = f;
        }
        if (fact != null) return fact.Value();
        else return null;
    }
}
