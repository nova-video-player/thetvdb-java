package com.uwetrottmann.thetvdb.services;

import static com.google.common.truth.Truth.assertThat;

import com.uwetrottmann.thetvdb.BaseTestCase;
import com.uwetrottmann.thetvdb.entities.SeriesUpdate;
import com.uwetrottmann.thetvdb.entities.SeriesUpdatesResponse;
import java.io.IOException;
import java.util.Calendar;
import org.junit.Test;


public class TheTvdbUpdatedTest extends BaseTestCase {

    @Test
    public void test_seriesUpdates() throws IOException {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -7);
        long timeWeekAgoSeconds = cal.getTimeInMillis() / 1000;

        SeriesUpdatesResponse response = executeCall(getTheTvdb().updated().seriesUpdates(timeWeekAgoSeconds, null));

        assertThat(response.data).isNotNull();
        assertThat(response.data).isNotEmpty(); // there have to be some updates over the last 7 days
        for (SeriesUpdate update : response.data) {
            assertThat(update.id).isAtLeast(1);
            assertThat(update.lastUpdated).isAtLeast(timeWeekAgoSeconds);
        }
    }

}
