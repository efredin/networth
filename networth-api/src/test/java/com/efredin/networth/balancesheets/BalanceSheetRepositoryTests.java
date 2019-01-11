package com.efredin.networth.balancesheets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BalanceSheetRepositoryTests {

    @Autowired
    private BalanceSheetRepository repository;

    @MockBean
    private MongoOperations operations;

    @Test
    public void findById() {
        BalanceSheet sheet = new BalanceSheet();
        sheet.id = "test-1234";

        when(this.operations.findById(sheet.id, BalanceSheet.class)).thenReturn(sheet);

        BalanceSheet result = this.repository.findById(sheet.id);

        assertThat(result).isInstanceOf(BalanceSheet.class);
        verify(this.operations).findById(sheet.id, BalanceSheet.class);
    }

    @Test
    public void save() {
        BalanceSheet sheet = new BalanceSheet();
        sheet.id = "test-999";
        sheet.currency = "CAD";

        when(this.operations.save(sheet)).thenReturn(sheet);

        BalanceSheet result = this.repository.save(sheet);

        assertThat(result).isInstanceOf(BalanceSheet.class);
        verify(this.operations).save(sheet);
    }

    @Test
    public void createAsset() {
        String sheetId = "real-sheet-123";
        String group = "i'm a real boy";
        String label = "gumdrop buttons";
        Asset asset = new Asset(group, label);
        asset.value = 13.37;
        BalanceSheet sheet = new BalanceSheet();
        sheet.id = sheetId;
        sheet.currency = "CAD";
        sheet.assets.add(asset);

        when(this.operations.findAndModify(
            any(Query.class), 
            any(Update.class), 
            any(FindAndModifyOptions.class), 
            any())
        ).thenReturn(sheet);

        Asset result = this.repository.createAsset(sheetId, asset);

        assertThat(result).isInstanceOf(Asset.class);
        assertThat(result).isEqualToComparingOnlyGivenFields(asset, "group", "label", "value");
        verify(this.operations).findAndModify(
            any(Query.class), 
            any(Update.class), 
            any(FindAndModifyOptions.class), 
            any()
        );
    }

    @Test
    public void createAsset_miss() {
        String sheetId = "real-sheet-123";
        String group = "i'm a real boy";
        String label = "gumdrop buttons";
        Asset asset = new Asset(group, label);
        asset.value = 13.37;

        when(this.operations.findAndModify(
            any(Query.class), 
            any(Update.class), 
            any(FindAndModifyOptions.class), 
            any())
        ).thenReturn(null);

        Asset result = this.repository.createAsset(sheetId, asset);

        assertThat(result).isNull();
        verify(this.operations).findAndModify(
            any(Query.class), 
            any(Update.class), 
            any(FindAndModifyOptions.class), 
            any()
        );
    }

    @Test
    public void createLiability() {
        String sheetId = "real-sheet-123";
        String group = "i'm a real boy";
        String label = "gumdrop buttons";
        Liability liability = new Liability(group, label);
        liability.value = 13.37;
        BalanceSheet sheet = new BalanceSheet();
        sheet.id = sheetId;
        sheet.currency = "CAD";
        sheet.liabilities.add(liability);

        when(this.operations.findAndModify(
            any(Query.class), 
            any(Update.class), 
            any(FindAndModifyOptions.class), 
            any())
        ).thenReturn(sheet);

        Liability result = this.repository.createLiability(sheetId, liability);

        assertThat(result).isInstanceOf(Liability.class);
        assertThat(result).isEqualToComparingOnlyGivenFields(liability, "group", "label", "value");
        verify(this.operations).findAndModify(
            any(Query.class), 
            any(Update.class), 
            any(FindAndModifyOptions.class), 
            any()
        );
    }

    @Test
    public void createLiability_miss() {
        String sheetId = "real-sheet-123";
        String group = "i'm a real boy";
        String label = "gumdrop buttons";
        Liability liability = new Liability(group, label);
        liability.value = 13.37;

        when(this.operations.findAndModify(
            any(Query.class), 
            any(Update.class), 
            any(FindAndModifyOptions.class), 
            any())
        ).thenReturn(null);

        Liability result = this.repository.createLiability(sheetId, liability);

        assertThat(result).isNull();
        verify(this.operations).findAndModify(
            any(Query.class), 
            any(Update.class), 
            any(FindAndModifyOptions.class), 
            any()
        );
    }

    @Test
    public void findAndUpdateAsset() {
        String sheetId = "real-sheet-123";
        String group = "i'm a real boy";
        String label = "gumdrop buttons";
        Asset asset = new Asset(group, label);
        asset.id = "asset-999";
        asset.value = 13.37;
        BalanceSheet sheet = new BalanceSheet();
        sheet.id = sheetId;
        sheet.currency = "CAD";
        sheet.assets.add(asset);

        when(this.operations.findAndModify(
            any(Query.class), 
            any(Update.class), 
            any(FindAndModifyOptions.class), 
            any())
        ).thenReturn(sheet);

        Asset result = this.repository.findAndUpdateAsset(sheetId, asset);

        assertThat(result).isInstanceOf(Asset.class);
        assertThat(result).isEqualToComparingOnlyGivenFields(asset, "group", "label", "value");
        verify(this.operations).findAndModify(
            any(Query.class), 
            any(Update.class), 
            any(FindAndModifyOptions.class), 
            any()
        );
    }

    @Test
    public void findAndUpdateAsset_miss() {
        String sheetId = "real-sheet-123";
        String group = "i'm a real boy";
        String label = "gumdrop buttons";
        Asset asset = new Asset(group, label);
        asset.id = "asset-999";
        asset.value = 13.37;

        when(this.operations.findAndModify(
            any(Query.class), 
            any(Update.class), 
            any(FindAndModifyOptions.class), 
            any())
        ).thenReturn(null);

        Asset result = this.repository.findAndUpdateAsset(sheetId, asset);

        assertThat(result).isNull();
        verify(this.operations).findAndModify(
            any(Query.class), 
            any(Update.class), 
            any(FindAndModifyOptions.class), 
            any()
        );
    }

    @Test
    public void findAndUpdateLiability() {
        String sheetId = "real-sheet-123";
        String group = "i'm a real boy";
        String label = "gumdrop buttons";
        Liability liability = new Liability(group, label);
        liability.id = "liability-333";
        liability.value = 13.37;
        BalanceSheet sheet = new BalanceSheet();
        sheet.id = sheetId;
        sheet.currency = "CAD";
        sheet.liabilities.add(liability);

        when(this.operations.findAndModify(
            any(Query.class), 
            any(Update.class), 
            any(FindAndModifyOptions.class), 
            any())
        ).thenReturn(sheet);

        Liability result = this.repository.findAndUpdateLiability(sheetId, liability);

        assertThat(result).isInstanceOf(Liability.class);
        assertThat(result).isEqualToComparingOnlyGivenFields(liability, "group", "label", "value");
        verify(this.operations).findAndModify(
            any(Query.class), 
            any(Update.class), 
            any(FindAndModifyOptions.class), 
            any()
        );
    }

    @Test
    public void findAndUpdateLiability_miss() {
        String sheetId = "real-sheet-123";
        String group = "i'm a real boy";
        String label = "gumdrop buttons";
        Liability liability = new Liability(group, label);
        liability.id = "liability-333";
        liability.value = 13.37;

        when(this.operations.findAndModify(
            any(Query.class), 
            any(Update.class), 
            any(FindAndModifyOptions.class), 
            any())
        ).thenReturn(null);

        Liability result = this.repository.findAndUpdateLiability(sheetId, liability);

        assertThat(result).isNull();
        verify(this.operations).findAndModify(
            any(Query.class), 
            any(Update.class), 
            any(FindAndModifyOptions.class), 
            any()
        );
    }
}