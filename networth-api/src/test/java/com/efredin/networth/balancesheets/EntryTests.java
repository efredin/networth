package com.efredin.networth.balancesheets;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class EntryTests {

    public void entry_withoutValues() {
        Entry entry = new Entry();
        
        assertThat(entry.longTerm).isFalse();
        assertThat(entry.label).isNull();
    }

    @Test
    public void entry_withValues() {
        String label = "crushing defeat";

        Entry entry = new Entry(label, true);

        assertThat(entry.longTerm).isTrue();
        assertThat(entry.label).isEqualTo(label);
    }
}