/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fantasycombat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Roxanne
 */
public abstract class Combatant { // base class for players and monsters

    public Combatant(String name, int basehp, int basemp, double basecritrate, double basecritmult, double basedefense, int basearmor, List<String> types) {
        this.statuses = new ArrayList<>();
        this.name = name;
        this.types = types;
        this.typemods = new HashMap();
        this.typemods.put("default", 1.0);
        
        this.basehp = basehp;
        this.basemp = basemp;
        this.basecritrate = basecritrate;
        this.basecritmult = basecritmult;
        this.basedefense = basedefense;
        this.basearmor = basearmor;
        
        this.maxhp = basehp;
        this.maxmp = basemp;
        
        this.currhp = basehp;
        this.currmp = basemp;
        this.currcritrate = basecritrate;
        this.currcritmult = basecritmult;
        this.currdefense = basedefense;
        this.currarmor = basearmor;
    }
    
    public Combatant(String name, int basehp, int basemp, double basecritrate, double basecritmult, double basedefense, int basearmor, List<String> types, Map<String, Double> typemods) {
        this.statuses = new ArrayList<>();
        this.name = name;
        this.types = types;
        this.typemods = new HashMap();
        this.typemods.putAll(typemods);
        if (!this.typemods.containsKey("default")) typemods.put("default", 1.0);
        
        this.basehp = basehp;
        this.basemp = basemp;
        this.basecritrate = basecritrate;
        this.basecritmult = basecritmult;
        this.basedefense = basedefense;
        this.basearmor = basearmor;
        
        this.maxhp = basehp;
        this.maxmp = basemp;
        
        this.currhp = basehp;
        this.currmp = basemp;
        this.currcritrate = basecritrate;
        this.currcritmult = basecritmult;
        this.currdefense = basedefense;
        this.currarmor = basearmor;
    }
    
    final String name;
    protected List<String> types; // 
    Map<String, Double> typemods; // Multipliers based on the type of incoming attacks, e.g. <"fire", 1.5>, <"physical", 0.5>
    Combatant currentOpponent;
    
    //Base stats
    final int basehp;
    final int basemp;
    final double basecritrate; // base critical strike rate from 0-100
    final double basecritmult; // damage is multiplied by this number on a critical strike
    final double basedefense; // % of incoming damage negated from 0-100
    final int basearmor; // flat amount of incoming damage negated each turn
    
    //Stat modifiers
    protected int maxhp;
    protected int maxmp;

    //modifiers to stats
    protected double critratemod = 1; //multiplier
    protected double critmultmod = 1; //multiplier
    protected double defensemod = 1; //multiplier
    protected int armormod = 0; //additive
    protected int dptmod = 0; //additive 
    protected double damagemult = 1; //multiplier
    
    //Current stats
    protected int currhp;
    protected int currmp;
    protected double currcritrate;
    protected double currcritmult;
    protected double currdefense;
    protected int currarmor;
    List<StatusEffect> statuses;
    
    public String getName(){
        return name;
    }
    
    public List<String> getTypes() {
        return types;
    }

    public int getMaxhp() {
        return maxhp;
    }

    public int getMaxmp() {
        return maxmp;
    }

    public int getCurrhp() {
        return currhp;
    }

    public int getCurrmp() {
        return currmp;
    }

    public double getCurrcritrate() {
        return currcritrate;
    }

    public double getCurrcritmult() {
        return currcritmult;
    }

    public double getCurrdefense() {
        return currdefense;
    }

    public int getCurrarmor() {
        return currarmor;
    }
    
    public void setNumbers(){ // set current stats
        currcritrate = basecritrate * critratemod;
        currcritmult = basecritmult * critmultmod;
        currdefense = basedefense * defensemod;
        currarmor = basearmor + armormod;
    }
    
    public void checkNumbers(){ // force any out-of-bounds numbers into bounds
        if (currhp > maxhp) currhp = maxhp;
        if (currhp < 0) currhp = 0;
        if (currmp > maxmp) currhp = maxmp;
        if (currmp < 0) currmp = 0;
        if (currcritrate > 100) currcritrate = 100;
        if (currcritrate < 0) currcritrate = 0;
        if (currcritmult < 1) currcritmult = 1;
        if (currarmor < 0) currarmor = 0;
        if (currdefense > 100) currdefense = 100;
        if (currdefense < 0) currdefense = 0;
    }
    
    public int takeDamage(Attack incoming){
        int netdmg = (int) ((incoming.rounddmg - this.currarmor) * ((100 - this.currdefense) / 100));
        
        Set<String> hastypes = new HashSet(this.typemods.keySet()); // copy list of types with modifiers
        hastypes.retainAll(incoming.types); // check for overlap with the incoming attack's types
        if (!hastypes.isEmpty()) for (String currtype : hastypes) netdmg *= this.typemods.get(currtype); /*
        apply multiplier for each relevant damage type */
        
        
        this.currhp -= netdmg; // apply damage
        if (incoming.haseffect){
            for (int i = 0; i < incoming.statuses.size(); i++)
                if (!this.statuses.contains(incoming.statuses.get(i))) this.statuses.add(incoming.statuses.get(i));
        }/*
        add status if any */
        return netdmg; // return damage done for printing
    };
    public void takeStatusDamage(int dmg){this.currhp -= dmg;}
    public void pitAgainst(Combatant opp){this.currentOpponent = opp;}
}
