/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fantasycombat;

import java.util.List;

/**
 *
 * @author Roxanne
 */
public class DamageOverTime extends StatusEffect{
    final int dpt; // damage per turn, can be negative

    public DamageOverTime(String name, Combatant owner, int duration, List<String> types, int dpt) {
        super(name, owner, duration, types);
        this.dpt = dpt;
    }
    @Override public void setTarget(Combatant target){
        this.target = target;
        if (target.getTypes().contains("player")) targetname = "you";
        else targetname = target.getName();
        if (owner.getTypes().contains("player")) ownername = "Your";
        else ownername = owner.getName() + "'s";
        if (dpt < 0) ticktext = ownername + " " + this.name + " heals " + dpt * -1 + " damage from " + targetname;
        else ticktext = ownername + " " + this.name + " deals " + dpt + " damage to " + targetname;
        expiretext = ownername + " " + this.name + " fades from " + targetname;
    }
    @Override public void apply(){};
    @Override public void tick(){
        target.takeStatusDamage(dpt);
        this.duration--;
    };
    @Override public void expire(){
    };
}
