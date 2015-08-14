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
public class DPTMod extends StatusEffect{
   final double mod;
    
    public DPTMod(String name, Combatant owner, int duration, List<String> types, double mod) {
        super(name, owner, duration, types);
        this.mod = mod;
    }
    
    @Override public void apply(){this.owner.dptmod += this.mod;}
    @Override public void tick(){}
    @Override public void expire(){this.owner.dptmod -= this.mod;}
    
}