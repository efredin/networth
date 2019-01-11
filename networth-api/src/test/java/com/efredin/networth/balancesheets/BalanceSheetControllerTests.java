package com.efredin.networth.balancesheets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void createAsset() {
        String sheetId = "sheet-it";
        Asset asset = new Asset("group", "a.label");
        asset.value = 92.55;

        when(this.repository.createAsset(sheetId, asset)).thenReturn(asset);

        Asset result = this.controller.createAsset(sheetId, asset);
        
        assertThat(result).isInstanceOf(Asset.class);
        verify(this.repository).createAsset(sheetId, asset);
    }

    @Test
    public void createAsset_miss() {
        String sheetId = "sheet-it";
        Asset asset = new Asset("group", "a.label");
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
        Liability liability = new Liability("group", "a.label");
        liability.value = 92.55;

        when(this.repository.createLiability(sheetId, liability)).thenReturn(liability);

        Liability result = this.controller.createLiability(sheetId, liability);
        
        assertThat(result).isInstanceOf(Liability.class);
        verify(this.repository).createLiability(sheetId, liability);
    }

    @Test
    public void createLiability_miss() {
        String sheetId = "sheet-it";
        Liability liability = new Liability("group", "a.label");
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
        Asset asset = new Asset("group", "a.label");
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
        Asset asset = new Asset("group", "a.label");
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
        Liability liability = new Liability("group", "a.label");
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
        Liability liability = new Liability("group", "a.label");
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
}