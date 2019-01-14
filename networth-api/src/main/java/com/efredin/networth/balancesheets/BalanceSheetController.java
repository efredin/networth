package com.efredin.networth.balancesheets;

import com.efredin.networth.currencies.CurrencyException;
import com.efredin.networth.currencies.CurrencyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/balancesheets")
@CrossOrigin(origins = "*")
public class BalanceSheetController {

    @Autowired
    BalanceSheetRepository repository;

    @Autowired
    CurrencyService currencyService;

    /** Get an existing balance sheet */
    @GetMapping("/{sheetId}")
    public BalanceSheet getSheet(@PathVariable("sheetId") String sheetId) {
        BalanceSheet sheet = this.repository.findById(sheetId);

        if (sheet == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return sheet;
    }

    /** Create a new balance sheet. */
    @PostMapping
    public BalanceSheet createSheet(@RequestBody BalanceSheet sheet) {
        return this.repository.save(sheet);
    }

    /** Create a new asset on a balance sheet */
    @PostMapping("/{sheetId}/assets")
    public Asset createAsset(
        @PathVariable("sheetId") String sheetId, 
        @RequestBody Asset asset
    ) {
        asset = this.repository.createAsset(sheetId, asset);
        if (asset == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return asset;
    }

    /** Create a new liability on a balance sheet */
    @PostMapping("/{sheetId}/liabilities")
    public Liability createLiability(
        @PathVariable("sheetId") String sheetId, 
        @RequestBody Liability liability
    ) {
        liability = this.repository.createLiability(sheetId, liability);
        if (liability == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return liability;
    }

    /** Update an existing asset on a balance sheet */
    @PutMapping("/{sheetId}/assets/{assetId}")
    public Asset updateAsset(
        @PathVariable("sheetId") String sheetId, 
        @PathVariable("assetId") String assetId,
        @RequestBody Asset asset
    ) {
        asset = this.repository.findAndUpdateAsset(sheetId, asset);
        if (asset == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return asset;
    }

    /** Update an existing liability on a balance sheet */
    @PutMapping("/{sheetId}/liabilities/{liabilityId}")
    public Liability updateLiability(
        @PathVariable("sheetId") String sheetId,
        @PathVariable("liabilityId") String assetId, 
        @RequestBody Liability liability
    ) {
        liability = this.repository.findAndUpdateLiability(sheetId, liability);
        if (liability == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return liability;
    }

    @DeleteMapping("/{sheetId}/assets/{assetId}")
    public void deleteAsset(
        @PathVariable("sheetId") String sheetId, 
        @PathVariable("assetId") String assetId
    ) {
        boolean deleted = this.repository.removeAsset(sheetId, assetId);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{sheetId}/liabilities/{liabilityId}")
    public void deleteLiability(@PathVariable("sheetId") String sheetId,
            @PathVariable("liabilityId") String liabilityId) {
        boolean deleted = this.repository.removeLiability(sheetId, liabilityId);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{sheetId}/convert/{currency}")
    public BalanceSheet convert(
        @PathVariable("sheetId") String sheetId, 
        @PathVariable("currency") String currency
    ) {
        double rate;

        // get currency currency
        BalanceSheet sheet = this.repository.findById(sheetId);
        if (sheet == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // lookup conversion rate
        try {
            rate = this.currencyService.conversionRate(sheet.currency, currency);
        } catch (CurrencyException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // convert each entry
        sheet.currency = currency;
        for (Asset asset : sheet.assets) {
            asset.value = (double) Math.round(asset.value * rate * 100d) / 100d;
        }
        for (Liability liability : sheet.liabilities) {
            liability.value = (double) Math.round(liability.value * rate * 100d) / 100d;
        }
        return this.repository.save(sheet);
    }
}