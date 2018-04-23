package com.java.expert;

import java.util.ArrayList;

public interface HumanInterface {
    int AskIntValue(String question);
    boolean AskBoolValue(String question);
    String AskStringValue(String question);
    void PrintFacts(ArrayList<IFact> facts);
    void PrintRules(ArrayList<Rule> rules);
}
