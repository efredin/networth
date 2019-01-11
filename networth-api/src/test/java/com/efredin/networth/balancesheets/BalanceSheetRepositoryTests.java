package com.efredin.networth.balancesheets;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
// @DataMongoTest
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
}