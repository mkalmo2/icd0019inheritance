package inheritance.pager;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FilteringPagerTests {

    @Test
    public void simplePagerPresentsDataInPages() {
        List<Integer> data = Arrays.asList(
                1, null, null, 2, null, 3, 4);

        SimplePager simplePager = new SimplePager(data, 3);

        assertThat(simplePager.getPage(0)).containsExactly(1, null, null);
        assertThat(simplePager.getPage(1)).containsExactly(2, null, 3);
        assertThat(simplePager.getPage(2)).containsExactly(4);

        assertThat(simplePager.hasPage(-1)).isFalse();
        assertThat(simplePager.hasPage(3)).isFalse();
    }

    @Test
    public void filteringPagerPresentsFilteredDataInPages() {
        List<Integer> data = Arrays.asList(
                1, null, null, 2,
                null, 3, 4);

        SimplePager simplePager = new SimplePager(data, 4);
        FilteringPager pager = new FilteringPager(simplePager, 2);

        assertThat(pager.getNextPage()).containsExactly(1, 2);
        assertThat(pager.getCurrentPage()).containsExactly(1, 2);

        assertThat(pager.getNextPage()).containsExactly(3, 4);
        assertThat(pager.getCurrentPage()).containsExactly(3, 4);

        assertThat(pager.getPreviousPage()).containsExactly(1, 2);
        assertThat(pager.getCurrentPage()).containsExactly(1, 2);
    }

    @Test
    public void throwsWhenNoNextPage() {
        SimplePager simplePager = new SimplePager(List.of(), 4);
        FilteringPager pager = new FilteringPager(simplePager, 2);

        assertThatThrownBy(() -> pager.getNextPage())
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void throwsWhenNoCurrentPage() {
        SimplePager simplePager = new SimplePager(List.of(), 4);
        FilteringPager pager = new FilteringPager(simplePager, 2);

        assertThatThrownBy(() -> pager.getCurrentPage())
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void throwsWhenNoPreviousPage() {
        SimplePager simplePager = new SimplePager(List.of(), 4);
        FilteringPager pager = new FilteringPager(simplePager, 2);

        assertThatThrownBy(() -> pager.getPreviousPage())
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void pagerHasTheSameBehaviourAsFilteringPagerWithMemory() {
        SimplePager simplePager = new SimplePager(getSampleInput(), 4);
        FilteringPagerWithMemory memoryPager = new FilteringPagerWithMemory(simplePager, 3);
        FilteringPager pager = new FilteringPager(simplePager, 3);

        while (memoryPager.hasNext()) {
            assertThat(pager.getNextPage()).isEqualTo(memoryPager.getNextPage());
            assertThat(pager.getCurrentPage()).isEqualTo(memoryPager.getCurrentPage());
        }

        while (memoryPager.hasPrevious()) {
            assertThat(pager.getPreviousPage()).isEqualTo(memoryPager.getPreviousPage());
            assertThat(pager.getCurrentPage()).isEqualTo(memoryPager.getCurrentPage());
        }
    }

    @Test
    public void shouldNotCallSimplePagerTooOften() {
        SimplePagerWithCounter simplePager = new SimplePagerWithCounter(getSampleInput(), 4);
        FilteringPager pager = new FilteringPager(simplePager, 3);

        for (int i = 0; i < 6; i++) {
            pager.getNextPage();
            pager.getCurrentPage();
        }

        for (int i = 0; i < 5; i++) {
            pager.getPreviousPage();
        }

        assertThat(simplePager.getPageRequestCount()).isLessThan(100);
    }

    @Test
    public void filteringPagerShouldHaveOnlyAllowedFields() {
        List<Field> fieldsNotAllowed = Arrays.stream(FilteringPager.class.getDeclaredFields())
                .filter(field -> !field.getType().equals(SimplePager.class))
                .filter(field -> !field.getType().equals(int.class))
                .filter(field -> !field.getType().equals(Integer.class))
                .collect(Collectors.toList());

        assertThat(fieldsNotAllowed).isEmpty();
    }

    private List<Integer> getSampleInput() {
        List<Integer> integers = new ArrayList<>();
        Random random = new Random(0);
        for (int i = 0; i < 100; i++) {
            int randNum = random.nextInt(30);
            integers.add(randNum < 10 ? randNum : null);
        }

        return integers;
    }

}
