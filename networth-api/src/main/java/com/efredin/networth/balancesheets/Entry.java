package com.efredin.networth.balancesheets;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

public class Entry {

    @Id
    public String id;

    /** 
     * Grouping of assets.
     * */
    public String group;

    /** Friendly label for this entry */
    public String label;
    
    @NotNull
    public Double value;

    public Entry() { }

    public Entry(String group, String label) {
        this();
        this.group = group;
        this.label = label;
    }
}
