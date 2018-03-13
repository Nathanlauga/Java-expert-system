package com.java.expert;

public class StringFact implements IFact {
    protected String name;
    protected String value;
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

    public StringFact(String _name, String _value, String _question, int _level){
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
