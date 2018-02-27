package com.java.expert;

public class IntFact implements IFact {
    protected String name;
    protected int value;
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

    public IntFact(String _name, int _value, String _question, int _level){
        name = _name;
        value = _value;
        question = _question;
        level = _level;
    }

    @Override
    public String toString() {
        return name + "=" + value +" ("+ level+")";
    }
}
