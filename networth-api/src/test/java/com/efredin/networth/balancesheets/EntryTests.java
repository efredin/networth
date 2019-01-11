package com.efredin.networth.balancesheets;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class EntryTests {

    public void entry_withoutValues() {
        Entry entry = new Entry();
        
        assertThat(entry.group).isNull();
        assertThat(entry.label).isNull();
    }

    @Test
    public void entry_withValues() {
        String group = "awesome group";
        String label = "crushing defeat";

        Entry entry = new Entry(group, label);

        assertThat(entry.group).isEqualTo(group);
        assertThat(entry.label).isEqualTo(label);
    }
}