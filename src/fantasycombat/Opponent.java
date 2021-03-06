/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fantasycombat;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Roxanne
 */
public class Opponent extends Combatant{ // class for non-player combatants

    public Opponent(String name, int basehp, int basemp, double basecritrate, double basecritmult, double basedefense, int basearmor, List<String> types) {
        super(name, basehp, basemp, basecritrate, basecritmult, basedefense, basearmor, types);
        
    }
    public Opponent(String name, int basehp, int basemp, double basecritrate, double basecritmult, double basedefense, int basearmor, List<String> types, Map<String, Double> typemods) {
        super(name, basehp, basemp, basecritrate, basecritmult, basedefense, basearmor, types, typemods);
        
    }
    Attack[] attacks;
    public void setAttacks(Attack[] attacks){this.attacks = attacks;}
    public Attack attack(){
        int totalweight = 0; // accumulator
        int roll = (int)(Math.random() * 100);
        for (int i = 0; i < attacks.length; i++)
        {
            if (roll - totalweight <= attacks[i].weight)
            {
                attacks[i].gendmg();
                return attacks[i];
            }
            totalweight += attacks[i].weight;
        }
        // default
        attacks[0].gendmg();
        return attacks[0];
    }
    
}
