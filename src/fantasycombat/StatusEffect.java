/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fantasycombat;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Roxanne
 */
public abstract class StatusEffect {

    public StatusEffect(String name, Combatant owner, int duration, List<String> types) {
        this.name = name;
        this.owner = owner;
        this.duration = duration;
        this.types = types;
        
        
    }
    final String name;
    Combatant target;

    public void setTarget(Combatant target) {
        this.target = target;
        if (target.getTypes().contains("player")) targetname = "you";
        else targetname = target.getName();
        if (owner.getTypes().contains("player")) ownername = "Your";
        else ownername = owner.getName() + "'s";
        expiretext = ownername + " " + this.name + " fades from " + targetname;
    }
    String targetname;
    Combatant owner;
    String ownername;
    protected int duration;
    String ticktext;
    String expiretext;

    public Combatant getTarget() {
        return target;
    }

    public int getDuration() {
        return duration;
    }

    public String getText() {
        return text;
    }

    public List<String> getTypes() {
        return types;
    }
    protected String text;
    final List<String> types;
    
    public abstract void apply(); // what it does when first applied
    public abstract void tick(); // what it does each round
    public abstract void expire(); // what it does when it expires
    
    public boolean equals(StatusEffect status){
        if (owner.equals(status.owner) && name.equals(status.name)) return true;
        else return false;
    }
}
