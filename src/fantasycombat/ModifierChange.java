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
public class ModifierChange extends StatusEffect {
    final double change;
    final String type;
    
    public ModifierChange(String name, Combatant owner, int duration, List<String> types, double change, String type){
        super(name, owner, duration, types);
        this.change = change;
        this.type = type;
    }
    
    @Override public void apply(){
        if (owner.typemods.containsKey(type)) owner.typemods.put(type, owner.typemods.get(type) * change);
        else owner.typemods.put(type, change);
    }
    @Override public void tick(){this.duration--;}
    @Override public void expire(){ owner.typemods.put(type, owner.typemods.get(type) / change); }
    
}
