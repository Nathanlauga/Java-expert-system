package com.java.expert;

import java.util.*;

public class Main implements HumanInterface {

    public static void main(String[] args) {

        Main p = new Main();
        p.Run();
    }

    private void Run(){
        System.out.println("** Création du moteur **");
        Motor m = new Motor(this);

        System.out.println("** Ajout des règles **");
        ArrayList<String> rulesList;
        CSVReader csvReader = new CSVReader();
        rulesList = csvReader.getRulesList();

        rulesList.forEach(m::AddRule);

        while (true){
            System.out.println("\n** Résolution **");
            m.Solve();
        }
    }

    @Override
    public int AskIntValue(String question) {
        Scanner sc = new Scanner(System.in);
        System.out.println(question);
        String str = sc.nextLine();
        try {
            return Integer.parseInt(str);
        }
        catch (Exception e){
            return 0;
        }
    }

    @Override
    public boolean AskBoolValue(String question) {
        Scanner sc = new Scanner(System.in);
        System.out.println(question + " (yes, no)");
        String str = sc.nextLine();
        return str.equals("yes");
    }

    @Override
    public String AskStringValue(String question) {
        Scanner sc = new Scanner(System.in);
        System.out.println(question);
        String str = sc.nextLine();
        try {
            return str;
        }
        catch (Exception e){
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    public void PrintFacts(ArrayList<IFact> facts) {
        StringBuilder res = new StringBuilder("Solution(s) trouvée(s) : \n");
        facts.sort(Comparator.comparingInt(IFact::Level));
        for (IFact f:facts) {
            res.append(f.toString()).append("\n");
        }
        System.out.println(res);
    }

    @Override
    public void PrintRules(ArrayList<Rule> rules) {
        StringBuilder res = new StringBuilder();
        for (Rule r:rules) {
            res.append(r.toString()).append("\n");
        }
        System.out.println(res);
    }
}
