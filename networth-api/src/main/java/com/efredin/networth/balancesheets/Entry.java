package com.efredin.networth.balancesheets;

import javax.validation.constraints.NotNull;

public class Entry {

    public String id;

    public boolean longTerm;

    /** Friendly label for this entry */
    public String label;
    
    @NotNull
    public double value;

    public Entry() {
        this.value = 0d;
    }

    public Entry(String label, boolean longTerm) {
        this();
        this.label = label;
        this.longTerm = longTerm;
    }
}
