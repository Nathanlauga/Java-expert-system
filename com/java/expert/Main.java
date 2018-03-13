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

        /*m.AddRule("R1 : IF (Ordre=3(Quel est l'ordre ?)) THEN Triangle");
        m.AddRule("R2 : IF (Triangle AND Angle Droit(La figure a-t-elle au moins un angle droit ?)) THEN Triangle Rectangle");
        m.AddRule("R3 : IF (Triangle AND Cotes Egaux=2 (Combien la figure a-t-elle de côtés égaux ?)) THEN Triangle Isocèle");
        m.AddRule("R4 : IF (Triangle Rectangle AND Triangle Isocèle) THEN Triangle Rectangle Isocèle");
        m.AddRule("RS : IF (Triangle AND Cotes Egaux=3 (Combien la figure a-t-elle de côtés égaux ?)) THEN Triangle Equilateral");
        m.AddRule("R6 : IF (Ordre=4 (Quel est l'ordre ?)) THEN Quadrilatère");
        m.AddRule("R7 : IF (Quadrilatère AND Cotes Paralleles=2 (Combien y a-t-il de côtés parallèles entre eux - 0, 2 ou 4)) THEN Trapeze");
        m.AddRule("R8 : IF (Quadrilatère AND Cotes Paralleles=4 (Combien y a-t-il de côtés parallèles entre eux - 0, 2 ou 4)) THEN Parallélogramme");
        m.AddRule("R9 : IF (Parallélogramme AND Angle Droit(La figure a-t-elle au moins un angle droit ?)) THEN Rectangle");
        m.AddRule("RlO : IF (Parallélogramme AND Cotes Egaux=4 (Combien la figure a-t-elle de côtés égaux ?)) THEN Losange");
        m.AddRule("R11 : IF (Rectangle AND Losange THEN Carré");
        m.AddRule("R12 : IF (Ordre=5 (Quel est l'ordre ?)) THEN Pentagone");
        m.AddRule("R13 : IF (Ordre=6 (Quel est l'ordre ?)) THEN Hexagone");
        m.AddRule("R14 : IF (Ordre=8 (Quel est l'ordre ?)) THEN Octogone");
        m.AddRule("R15 : IF (Ordre=10 (Quel est l'ordre ?)) THEN Décagone");
        m.AddRule("R16 : IF (Ordre=12 (Quel est l'ordre ?)) THEN Dodécagone");
        m.AddRule("R17 : IF (Ordre=20 (Quel est l'ordre ?)) THEN Icosagone");*/

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
