package com.java.expert;

public class BoolFact implements IFact {
    protected String name;
    protected boolean value;
    protected int level;
    protected String question = null;

    @Override
    public String Name() {
        return name;
    }

    @Override
    public Object Value() {
        return value;
    }

    @Override
    public int Level() {
        return level;
    }

    @Override
    public String Question() {
        return question;
    }

    @Override
    public void SetLevel(int l) {
        level = l;
    }

    public BoolFact(String _name, boolean _value, String _question, int _level){
        name = _name;
        value = _value;
        question = _question;
        level = _level;
    }

    @Override
    public String toString() {
        String res = "";
        if(!value){
            res += "!";
        }
        res += name + " ("+level+")";
        return res;
    }
}
