package com.efredin.networth.balancesheets;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

public class BalanceSheet {

    @Id
    public String id;

    /** Currency for the balance sheet.  Ex: CAD, USD */
    @NotNull
    public String currency;

    public List<Asset> assets;

    public List<Liability> liabilities;

    public BalanceSheet() {
        this.assets = new ArrayList<Asset>();
        this.liabilities = new ArrayList<Liability>();
    }
}
