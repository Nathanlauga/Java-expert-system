package com.nathan.expert;

import java.util.ArrayList;
import java.util.List;

public interface HumanInterface {
    int AskIntValue(String question);
    boolean AskBoolValue(String question);
    void PrintFacts(ArrayList<IFact> facts);
    void PrintRules(ArrayList<Rule> rules);
}
