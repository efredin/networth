package com.efredin.networth.balancesheets;

import javax.validation.constraints.NotNull;

public class Entry {

    public String id;

    public boolean longTerm;

    /** Friendly label for this entry */
    public String label;
    
    @NotNull
    public Double value;

    public Entry() {
    }

    public Entry(String label, boolean longTerm) {
        this();
        this.label = label;
        this.longTerm = longTerm;
    }
}
