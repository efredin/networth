package com.efredin.networth.balancesheets;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class BalanceSheetTests {

    @Test
    public void balanceSheet() {
        BalanceSheet sheet = new BalanceSheet();
        
        assertThat(sheet.assets).isNotNull();
        assertThat(sheet.assets).isEmpty();
        assertThat(sheet.liabilities).isNotNull();
        assertThat(sheet.liabilities).isEmpty();
    }
}