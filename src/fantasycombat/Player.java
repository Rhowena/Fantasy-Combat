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
public class Player extends Combatant{

    public Player(String name, int basehp, int basemp, double basecritrate, double basecritmult, double basedefense, int basearmor, List<String> types) {
        super(name, basehp, basemp, basecritrate, basecritmult, basedefense, basearmor, types);
    }
    
}
