/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fantasycombat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 *
 * @author Roxanne
 */
public class Attack {

    // multiple constructors so that statuseffect and typemods can be omitted if they don't apply
    public Attack(Combatant owner, int weight, int base, int random, String text, boolean targetself, StatusEffect status, List<String> types) {
        this.owner = owner;
        this.weight = weight;
        this.base = base;
        this.random = random;
        this.text = text;
        this.targetself = targetself;
        this.haseffect = true;
        this.status = status;
        this.types = types;
        this.typemods = new HashMap<>();
        this.typemods.put("default", 1.0);
    }
    public Attack(Combatant owner, int weight, int base, int random, String text, boolean targetself, List<String> types) {
        this.owner = owner;
        this.weight = weight;
        this.base = base;
        this.random = random;
        this.text = text;
        this.targetself = targetself;
        this.haseffect = false;
        this.status = null;
        this.types = types;
        this.typemods = new HashMap<>();
        this.typemods.put("default", 1.0);
    }
    public Attack(Combatant owner, int weight, int base, int random, String text, boolean targetself, StatusEffect status, List<String> types, Map<String, Double> typemods) {
        this.owner = owner;
        this.weight = weight;
        this.base = base;
        this.random = random;
        this.text = text;
        this.targetself = targetself;
        this.haseffect = true;
        this.status = status;
        this.types = types;
        this.typemods = new HashMap<>();
        this.typemods.putAll(typemods);
        if (!this.typemods.containsKey("default")) typemods.put("default", 1.0);
    }
    public Attack(Combatant owner, int weight, int base, int random, String text, boolean targetself, List<String> types, Map<String, Double> typemods) {
        this.owner = owner;
        this.weight = weight;
        this.base = base;
        this.random = random;
        this.text = text;
        this.targetself = targetself;
        this.haseffect = false;
        this.status = null;
        this.types = types;
        this.typemods = new HashMap<>();
        this.typemods.putAll(typemods);
        if (!this.typemods.containsKey("default")) typemods.put("default", 1.0);
    }
    Combatant owner;
    int weight; // % chance of the attack being used 
    int base; // minimum damage
    int random; // damage range
    int rounddmg = 0; // damage roll for the round
    int truedmg = 0; // actual damage done to the target after modifiers; used for printouts
    public final boolean targetself; // Does it target the user? Used for heals and self-buffs
    public final boolean haseffect; // Does it inflict a status?
    StatusEffect status; // Status inflicted, if any
    List<String> types; // Physical, magic, ice, fire, etc.
    Map<String, Double> typemods; // Multipliers based on the type of the target e.g. <"dragon", 1.1>

    public void setTruedmg(int truedmg) {
        this.truedmg = truedmg;
    }
    String text; // verb to use in the printout, e.g. "hits", "slashes", "breathes fire on"
    String roundtext = ""; // assembled print string

    public String getRoundtext() {
        return roundtext;
    }
    boolean iscrit;
    public void gendmg(){
        rounddmg = base + (int)(Math.random() * (random+ 1)); // roll for damage
        
        //check multipliers
        Set<String> hastypes = new HashSet(typemods.keySet());
        if (targetself) hastypes.retainAll(owner.getTypes());
        else hastypes.retainAll(owner.currentOpponent.getTypes());
        if (hastypes.isEmpty()) rounddmg *= this.typemods.get("default");
        else {
            for (String currtype : hastypes) rounddmg *= this.typemods.get(currtype);
        }
        
        //roll for crit
        double critroll = (Math.random() * 100);
        if (critroll <= owner.getCurrcritrate()){
            rounddmg *= owner.getCurrcritmult();
            iscrit = true;   
        }
        else iscrit = false;
        if (haseffect)
        {
            if (targetself) this.status.setTarget(owner);
            else this.status.setTarget(owner.currentOpponent);
        }
    }
    public void gentext(String targetname){
        roundtext = owner.getName() + " " + text + " " + targetname + " for " + truedmg + " damage";
        if (iscrit) roundtext += " *CRITICAL*";
    }
    
}
