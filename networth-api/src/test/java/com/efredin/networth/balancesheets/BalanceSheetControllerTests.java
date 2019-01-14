package com.efredin.networth.balancesheets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.efredin.networth.currencies.CurrencyException;
import com.efredin.networth.currencies.CurrencyService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BalanceSheetControllerTests {

    @Autowired
    private BalanceSheetController controller;

    @MockBean
    private BalanceSheetRepository repository;

    @MockBean
    private CurrencyService currencyService;

    @Test
    public void getSheet() {
        BalanceSheet sheet = new BalanceSheet();
        sheet.id = "test-1234";

        when(this.repository.findById(sheet.id)).thenReturn(sheet);

        BalanceSheet result = this.controller.getSheet(sheet.id);

        assertThat(result).isInstanceOf(BalanceSheet.class);
        verify(this.repository).findById(sheet.id);
    }

    @Test
    public void getSheet_miss() {
        BalanceSheet sheet = new BalanceSheet();
        sheet.id = "test-1234";

        when(this.repository.findById(sheet.id)).thenReturn(null);

        try {
            this.controller.getSheet(sheet.id);
            fail("Expected ResponseStatusException to be thrown");
        } catch (ResponseStatusException e) {
            assertThat(e.getStatus() == HttpStatus.NOT_FOUND);
        }
        verify(this.repository).findById(sheet.id);
    }

    @Test
    public void createSheet() {
        BalanceSheet sheet = new BalanceSheet();

        when(this.repository.save(sheet)).thenReturn(sheet);

        this.controller.createSheet(sheet);
        verify(this.repository).save(sheet);
    }

    @Test
    public void createAsset() {
        String sheetId = "sheet-it";
        Asset asset = new Asset("a.label", true);
        asset.value = 92.55;

        when(this.repository.createAsset(sheetId, asset)).thenReturn(asset);

        Asset result = this.controller.createAsset(sheetId, asset);
        
        assertThat(result).isInstanceOf(Asset.class);
        verify(this.repository).createAsset(sheetId, asset);
    }

    @Test
    public void createAsset_miss() {
        String sheetId = "sheet-it";
        Asset asset = new Asset("a.label", true);
        asset.value = 92.55;

        when(this.repository.createAsset(sheetId, asset)).thenReturn(null);

        try {
            this.controller.createAsset(sheetId, asset);
            fail("Expected ResponseStatusException to be thrown");
        } catch (ResponseStatusException e) {
            assertThat(e.getStatus() == HttpStatus.NOT_FOUND);
        }
        verify(this.repository).createAsset(sheetId, asset);
    }

    @Test
    public void createLiability() {
        String sheetId = "sheet-it";
        Liability liability = new Liability("a.label", true);
        liability.value = 92.55;

        when(this.repository.createLiability(sheetId, liability)).thenReturn(liability);

        Liability result = this.controller.createLiability(sheetId, liability);
        
        assertThat(result).isInstanceOf(Liability.class);
        verify(this.repository).createLiability(sheetId, liability);
    }

    @Test
    public void createLiability_miss() {
        String sheetId = "sheet-it";
        Liability liability = new Liability("a.label", true);
        liability.value = 92.55;

        when(this.repository.createLiability(sheetId, liability)).thenReturn(null);

        try {
            this.controller.createLiability(sheetId, liability);
            fail("Expected ResponseStatusException to be thrown");
        } catch (ResponseStatusException e) {
            assertThat(e.getStatus() == HttpStatus.NOT_FOUND);
        }
        verify(this.repository).createLiability(sheetId, liability);
    }

    @Test
    public void updateAsset() {
        String sheetId = "sheet-it";
        Asset asset = new Asset("a.label", true);
        asset.id = "asset-1234";
        asset.value = 92.55;

        when(this.repository.findAndUpdateAsset(sheetId, asset)).thenReturn(asset);

        Asset result = this.controller.updateAsset(sheetId, asset.id, asset);
        
        assertThat(result).isInstanceOf(Asset.class);
        verify(this.repository).findAndUpdateAsset(sheetId, asset);
    }

    @Test
    public void updateAsset_miss() {
        String sheetId = "sheet-it";
        Asset asset = new Asset("a.label", true);
        asset.id = "asset-1234";
        asset.value = 92.55;

        when(this.repository.findAndUpdateAsset(sheetId, asset)).thenReturn(null);

        try {
            this.controller.updateAsset(sheetId, asset.id, asset);
            fail("Expected ResponseStatusException to be thrown");
        } catch (ResponseStatusException e) {
            assertThat(e.getStatus() == HttpStatus.NOT_FOUND);
        }
        verify(this.repository).findAndUpdateAsset(sheetId, asset);
    }

    @Test
    public void updateLiability() {
        String sheetId = "sheet-it";
        Liability liability = new Liability("a.label", true);
        liability.id = "liability-1234";
        liability.value = 92.55;

        when(this.repository.findAndUpdateLiability(sheetId, liability)).thenReturn(liability);

        Liability result = this.controller.updateLiability(sheetId, liability.id, liability);
        
        assertThat(result).isInstanceOf(Liability.class);
        verify(this.repository).findAndUpdateLiability(sheetId, liability);
    }

    @Test
    public void updateLiability_miss() {
        String sheetId = "sheet-it";
        Liability liability = new Liability("a.label", true);
        liability.id = "liability-1234";
        liability.value = 92.55;

        when(this.repository.findAndUpdateLiability(sheetId, liability)).thenReturn(null);

        try {
            this.controller.updateLiability(sheetId, liability.id, liability);
            fail("Expected ResponseStatusException to be thrown");
        } catch (ResponseStatusException e) {
            assertThat(e.getStatus() == HttpStatus.NOT_FOUND);
        }
        verify(this.repository).findAndUpdateLiability(sheetId, liability);
    }

    @Test
    public void deleteAsset() {
        String sheetId = "sheet-it";
        String assetId = "asset-73";

        when(this.repository.removeAsset(sheetId, assetId)).thenReturn(true);

        this.controller.deleteAsset(sheetId, assetId);
        
        verify(this.repository).removeAsset(sheetId, assetId);
    }

    @Test
    public void deleteAsset_miss() {
        String sheetId = "sheet-it";
        String assetId = "asset-73";
        
        when(this.repository.removeAsset(sheetId, assetId)).thenReturn(false);

        try {
            this.controller.deleteAsset(sheetId, assetId);
            fail("Expected ResponseStatusException to be thrown");
        } catch (ResponseStatusException e) {
            assertThat(e.getStatus() == HttpStatus.NOT_FOUND);
        }

        verify(this.repository).removeAsset(sheetId, assetId);
    }

    @Test
    public void deleteLiability() {
        String sheetId = "sheet-it";
        String liabilityId = "liability-51";

        when(this.repository.removeLiability(sheetId, liabilityId)).thenReturn(true);

        this.controller.deleteLiability(sheetId, liabilityId);
        
        verify(this.repository).removeLiability(sheetId, liabilityId);
    }

    @Test
    public void deleteLiability_miss() {
        String sheetId = "sheet-it";
        String liabilityId = "liability-51";
        
        when(this.repository.removeLiability(sheetId, liabilityId)).thenReturn(false);

        try {
            this.controller.deleteLiability(sheetId, liabilityId);
            fail("Expected ResponseStatusException to be thrown");
        } catch (ResponseStatusException e) {
            assertThat(e.getStatus() == HttpStatus.NOT_FOUND);
        }
        
        verify(this.repository).removeLiability(sheetId, liabilityId);
    }

    @Test
    public void convert() throws CurrencyException {
        String sheetId = "sheet-91";
        String currency = "USD";
        BalanceSheet sheet = new BalanceSheet();
        sheet.currency = "CAD";
        Asset asset = new Asset("cash", false);
        asset.value = 1d;
        sheet.assets.add(asset);
        Liability liability = new Liability("debt", false);
        liability.value = 1d;
        sheet.liabilities.add(liability);
        double rate = 0.5;

        when(this.repository.findById(sheetId)).thenReturn(sheet);
        when(this.currencyService.conversionRate(sheet.currency, currency)).thenReturn(rate);
        when(this.repository.save(sheet)).thenReturn(sheet);

        BalanceSheet result = this.controller.convert(sheetId, currency);
        
        assertThat(result.currency).isEqualTo(currency);
        assertThat(result.assets.get(0).value).isEqualTo(0.5);
        assertThat(result.liabilities.get(0).value).isEqualTo(0.5);
        verify(this.repository).findById(sheetId);
        verify(this.currencyService).conversionRate("CAD", currency);
        verify(this.repository).save(sheet);
    }

    @Test
    public void convert_miss() throws CurrencyException {
        String sheetId = "sheet-91";
        String currency = "USD";

        when(this.repository.findById(sheetId)).thenReturn(null);

        try {
            this.controller.convert(sheetId, currency);
            fail("Excepted ResponseStatusException to be thrown");
        } catch (ResponseStatusException e) {
            assertThat(e.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        }
        verify(this.repository).findById(sheetId);
        verify(this.currencyService, times(0)).conversionRate("CAD", currency);
    }

    @Test
    public void convert_error() throws CurrencyException {
        String sheetId = "sheet-91";
        String currency = "USD";
        BalanceSheet sheet = new BalanceSheet();
        sheet.currency = "CAD";

        when(this.repository.findById(sheetId)).thenReturn(sheet);
        when(this.currencyService.conversionRate(sheet.currency, currency))
            .thenThrow(new CurrencyException("bad currency"));

        try {
            this.controller.convert(sheetId, currency);
            fail("Excepted ResponseStatusException to be thrown");
        } catch (ResponseStatusException e) {
            assertThat(e.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        verify(this.repository).findById(sheetId);
        verify(this.currencyService).conversionRate("CAD", currency);
        verify(this.repository, times(0)).save(sheet);
    }
}