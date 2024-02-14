package model;

import java.util.ArrayList;
import java.util.List;

// Represents a drug with a name
public class Drug {
    private String name;                  // name of the drug
    private List<String> sideEffects;     // a list of side effects

    // REQUIRES: drug name has a non-zero length
    // EFFECTS: constructs the drug with given name and empty side effects list
    public Drug(String name) {
        this.name = name;
        this.sideEffects = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given side effect to the side effects list
    //          with no duplicates
    //          if given side effect is already in list, does nothing
    public void addSideEffect(String sideEffect) {
        if (!sideEffects.contains(sideEffect)) {
            sideEffects.add(sideEffect);
        }
    }

    // EFFECTS: returns a string with all the side effects
    //          divided by a comma
    //          If no side effects listed, return null
    public String displaySideEffects() {
        String sideEffectList = "";
        for (String sideEffect : sideEffects) {
            sideEffectList = sideEffectList + ", " + sideEffect;
        }
        if (sideEffects.size() == 0) {
            return null;
        } else {
            return sideEffectList.substring(2);
        }
    }

    public String getName() {
        return name;
    }

    public List<String> getSideEffects() {
        return sideEffects;
    }
}
