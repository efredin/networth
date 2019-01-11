package com.efredin.networth.balancesheets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * Repository for balance sheet documents.
 * I chose not to use a Spring Data repository here because I couldn't figure out how to
 * extend the functionality to allow for atomic queries to mongo.
 */
public class BalanceSheetRepository  {

    private static String ASSETS_KEY = "assets";
    private static String LIABILITIES_KEY = "liabilities";
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private MongoOperations operations;


    /**
     * Find a balance sheet by id
     * @param id Balance sheet id
     * @return Found balance sheet or null.
     */
    public BalanceSheet findById(String id) {
        logger.debug("findById {}", id);
        return this.operations.findById(id, BalanceSheet.class);
    }

    /**
     * Save an entire balance sheet object.
     */
    public BalanceSheet save(BalanceSheet sheet) {
        logger.debug("save {}", sheet.id);
        return this.operations.save(sheet);
    }

    /**
     * Atomic creation of an asset.
     * @param sheetId Balance sheet id
     * @param asset Asset to create
     * @return Created asset
     */
    public Asset createAsset(String sheetId, Asset asset) {
        logger.debug("createAsset for {}", sheetId);
        BalanceSheet sheet = this.findAndCreateEntry(sheetId, asset, ASSETS_KEY);
        if (sheet == null) {
            return null;
        }
        return sheet.assets.get(sheet.assets.size() - 1);
    }

    /**
     * Atomic creation of a liability
     * @param sheetId Balance sheet id
     * @param liability Liability to create
     * @return Created liability
     */
    public Liability createLiability(String sheetId, Liability liability) {
        logger.debug("createLiability for {}", sheetId);
        BalanceSheet sheet = this.findAndCreateEntry(sheetId, liability, LIABILITIES_KEY);
        if (sheet == null) {
            return null;
        }
        return sheet.liabilities.get(sheet.assets.size() - 1);
    }

    /**
     * Atomic creation of a balance sheet entry.
     * @param sheetId Balance sheet id
     * @param entry entry to create
     * @param key Key used to find the collection of entries on the balance sheet.  Valid values include 'assets', 'liabilities'
     * @return Created balance sheet
     */
    private BalanceSheet findAndCreateEntry(String sheetId, Entry entry, String key) {
        Query query = Query.query(Criteria.where("_id").is(sheetId));

        // only return the updated entry array since there doesn't appear to be a way to
        // request only new array elements back from mongo
        query.fields().include(key);

        Update update = new Update().push(key, entry);

        FindAndModifyOptions options = new FindAndModifyOptions()
            .returnNew(true);
        
        return this.operations.findAndModify(query, update, options, BalanceSheet.class);
    }

    /**
     * Atomic update of a balance sheet asset entry
     * @param sheetId Balance sheet id
     * @param assetId Asset id
     * @return Updated asset object
     */
    public Asset findAndUpdateAsset(String sheetId, Asset asset) {
        logger.debug("findAndUpdateAsset for sheet: {}, asset: {}", sheetId, asset.id);
        BalanceSheet sheet = this.findAndUpdateEntry(sheetId, asset, ASSETS_KEY);
        if (sheet == null) {
            return null;
        }
        return this.getAssetById(sheet, asset.id);
    }

    /**
     * Atomic update of a balance shset liability entry.
     * @param sheetId Balance sheet id
     * @param liability Liability id
     * @return Updated liability object
     */
    public Liability findAndUpdateLiabililty(String sheetId, Liability liability) {
        logger.debug("findAndUpdateLiabililty for sheet: {}, asset: {}", sheetId, liability.id);
        BalanceSheet sheet = this.findAndUpdateEntry(sheetId, liability, LIABILITIES_KEY);
        if (sheet == null) {
            return null;
        }
        return this.getLiabiiltyById(sheet, liability.id);
    }

    /**
     * Atomic find & update of a balance sheet entry.
     * @param sheetId Balance sheet id
     * @param entry entry to update
     * @param key Key used to find the collection of entries on the balance sheet.  Valid values include 'assets', 'liabilities'
     * @return Updated balance sheet
     */
    private BalanceSheet findAndUpdateEntry(String sheetId, Entry entry, String key) {
        Query query = Query.query(Criteria.where("_id").is(sheetId)
            .andOperator(Criteria.where(key + "._id").is(entry.id)));

        // only return the updated entry
        query.fields().include(key + ".$");

        Update update = new Update().set(key + ".$", entry);

        FindAndModifyOptions options = new FindAndModifyOptions()
            .returnNew(true);
        
        return this.operations.findAndModify(query, update, options, BalanceSheet.class);
    }

    /** Find an asset by id from a balance sheet assets collection */
    private Asset getAssetById(BalanceSheet sheet, String assetId) {
        return sheet.assets.stream()
            .filter(a -> a.id.equals(assetId))
            .findFirst()
            .get();
    }

    /** Find a liability by id from a balance sheet liabilities collection */
    private Liability getLiabiiltyById(BalanceSheet sheet, String liabilityId) {
        return sheet.liabilities.stream()
            .filter(a -> a.id.equals(liabilityId))
            .findFirst()
            .get();
    }
}
