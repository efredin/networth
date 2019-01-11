// package com.efredin.networth.balancesheets;

// import java.util.ArrayList;

// import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;

// public class BalanceSheetRepositoryEventListener extends AbstractRepositoryEventListener<BalanceSheet> {

//     /** 
//      * Intercept balance sheet creation.
//      * Adds default entries to prevent the user from being presented with a blank sheet.
//      */
//     public void onBeforeCreate(BalanceSheet sheet) {
        
//         if (sheet.assets == null) {
//             sheet.assets = new ArrayList<EntryGroup>();
//         }
//         if (sheet.liabilities == null) {
//             sheet.liabilities = new ArrayList<EntryGroup>();
//         }

//         // todo: use resources instead of hard-coded strings
//         if (sheet.assets.isEmpty()) {
//             EntryGroup cashAndInvestments = new EntryGroup("Cash & Investments");
//             cashAndInvestments.entries.add(new Entry("Chequing"));;
//             cashAndInvestments.entries.add(new Entry("Savings for Taxes"));
//             cashAndInvestments.entries.add(new Entry("Rainy Day Fund"));
//             cashAndInvestments.entries.add(new Entry("Savings for Fun"));
//             cashAndInvestments.entries.add(new Entry("Savings for Travel"));
//             cashAndInvestments.entries.add(new Entry("Savings for Personal Development"));
//             cashAndInvestments.entries.add(new Entry("Investment 1"));
//             cashAndInvestments.entries.add(new Entry("Investment 2"));
//             cashAndInvestments.entries.add(new Entry("Investment 3"));
//             cashAndInvestments.entries.add(new Entry("Investment 4"));
//             cashAndInvestments.entries.add(new Entry("Investment 5"));
//             sheet.assets.add(cashAndInvestments);
            
//             EntryGroup longTermAssets = new EntryGroup("Long Term Assets");
//             longTermAssets.entries.add(new Entry("Primary Home"));
//             longTermAssets.entries.add(new Entry("Second Home"));
//             longTermAssets.entries.add(new Entry("Other"));
//             sheet.assets.add(longTermAssets);
//         }

//         if (sheet.liabilities.isEmpty()) {
//             EntryGroup shortTermLiabilities = new EntryGroup("Short Term Liabilities");
//             shortTermLiabilities.entries.add(new Entry("Credit Card 1"));
//             shortTermLiabilities.entries.add(new Entry("Credit Card 2"));
//             shortTermLiabilities.entries.add(new Entry("Others"));
//             sheet.assets.add(shortTermLiabilities);
            
//             EntryGroup longTermLiabilities = new EntryGroup("Long Term Liabilities");
//             longTermLiabilities.entries.add(new Entry("Mortgage 1"));
//             longTermLiabilities.entries.add(new Entry("Mortgage 2"));
//             longTermLiabilities.entries.add(new Entry("Line of Credit"));
//             longTermLiabilities.entries.add(new Entry("Investment Loan"));
//             longTermLiabilities.entries.add(new Entry("Student Loan"));
//             longTermLiabilities.entries.add(new Entry("Car Loan"));
//             sheet.liabilities.add(longTermLiabilities);
//         }
//     }
// }