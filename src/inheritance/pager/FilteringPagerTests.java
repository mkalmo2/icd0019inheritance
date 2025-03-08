package inheritance.pager;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FilteringPagerTests {

    @Test
    public void simplePagerPresentsDataInPages() {
        List<String> data = Arrays.asList(
                "1", null, null, "2", null, "3", "4");

        SimplePager simplePager = new SimplePager(data, 3);

        assertThat(simplePager.getPage(0)).containsExactly("1", null, null);
        assertThat(simplePager.getPage(1)).containsExactly("2", null, "3");
        assertThat(simplePager.getPage(2)).containsExactly("4");

        assertThat(simplePager.hasPage(-1)).isFalse();
        assertThat(simplePager.hasPage(3)).isFalse();
    }

    @Test
    public void filteringPagerPresentsFilteredDataInPages() {
        List<String> data = Arrays.asList(
                "1", null, null, "2",
                null, "3", "4", null, "5");

        SimplePager simplePager = new SimplePager(data, 4);
        FilteringPager pager = new FilteringPager(simplePager, 2);

        assertThat(pager.getCurrentPage()).containsExactly("1", "2");
        assertThat(pager.getNextPage()).containsExactly("3", "4");
        assertThat(pager.hasNextPage()).isTrue();
        assertThat(pager.getNextPage()).containsExactly("5");
        assertThat(pager.hasNextPage()).isFalse();

        assertThat(pager.getPreviousPage()).containsExactly("3", "4");
        assertThat(pager.getCurrentPage()).containsExactly("3", "4");

        assertThat(pager.hasPreviousPage()).isTrue();
        assertThat(pager.getPreviousPage()).containsExactly("1", "2");
        assertThat(pager.hasPreviousPage()).isFalse();
        assertThat(pager.getCurrentPage()).containsExactly("1", "2");
    }

    @Test
    public void knowsWhetherThereIsNextPage() {
        List<String> data = Arrays.asList(
                "1", null, null, "2", null, "3");

        SimplePager simplePager = new SimplePager(data, 4);
        FilteringPager pager = new FilteringPager(simplePager, 1);

        assertThat(pager.hasNextPage()).isTrue();
        pager.getNextPage();
        assertThat(pager.hasNextPage()).isTrue();
        pager.getNextPage();
        assertThat(pager.hasNextPage()).isFalse();
    }

    @Test
    public void knowsWhetherThereIsPreviousPage() {
        List<String> data = Arrays.asList(
                "1", null, null, "2",
                null, "3");

        SimplePager simplePager = new SimplePager(data, 4);
        FilteringPager pager = new FilteringPager(simplePager, 2);

        assertThat(pager.hasPreviousPage()).isFalse();
        pager.getNextPage();
        assertThat(pager.hasPreviousPage()).isTrue();
        pager.getPreviousPage();
        assertThat(pager.hasPreviousPage()).isFalse();
    }

    @Test
    public void throwsWhenNoNextPage() {
        SimplePager simplePager = new SimplePager(List.of(), 4);
        FilteringPager pager = new FilteringPager(simplePager, 2);

        assertThatThrownBy(pager::getNextPage)
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void throwsWhenNoPreviousPage() {
        SimplePager simplePager = new SimplePager(List.of(), 4);
        FilteringPager pager = new FilteringPager(simplePager, 2);

        assertThatThrownBy(pager::getPreviousPage)
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void knowsCurrentPageNumber() {
        List<String> data = Arrays.asList("1", null, "2", "3", null, "4", "5");
        SimplePager simplePager = new SimplePager(data, 4);
        FilteringPager pager = new FilteringPager(simplePager, 2);

        assertThat(pager.getCurrentPageNo()).isEqualTo(0);
        pager.getNextPage();
        assertThat(pager.getCurrentPageNo()).isEqualTo(1);
        pager.getNextPage();
        assertThat(pager.getCurrentPageNo()).isEqualTo(2);
        pager.getPreviousPage();
        assertThat(pager.getCurrentPageNo()).isEqualTo(1);
        pager.getPreviousPage();
        assertThat(pager.getCurrentPageNo()).isEqualTo(0);
    }

    @Test
    public void pagerHasTheSameBehaviourAsFilteringPagerWithMemory() {
        SimplePager simplePager = new SimplePager(getSampleInput(), 4);
        FilteringPagerWithMemory memoryPager = new FilteringPagerWithMemory(simplePager, 3);
        FilteringPager pager = new FilteringPager(simplePager, 3);

        while (memoryPager.hasNextPage()) {
            assertThat(pager.getNextPage()).isEqualTo(memoryPager.getNextPage());
            assertThat(pager.getCurrentPage()).isEqualTo(memoryPager.getCurrentPage());
        }

        while (memoryPager.hasPreviousPage()) {
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

        assertThat(simplePager.getPageRequestCount()).isLessThan(150);
    }

    @Test
    public void filteringPagerShouldHaveOnlyAllowedFields() {
        List<Field> fieldsNotAllowed = Arrays.stream(FilteringPager.class.getDeclaredFields())
                .filter(field -> !field.getType().equals(SimplePager.class))
                .filter(field -> !field.getType().equals(int.class))
                .toList();

        assertThat(fieldsNotAllowed).isEmpty();

        List<Field> intFields = Arrays.stream(FilteringPager.class.getDeclaredFields())
                .filter(field -> field.getType().equals(int.class))
                .toList();

        assertThat(intFields.size()).isLessThanOrEqualTo(4);
    }

    private List<String> getSampleInput() {
        List<String> strings = new ArrayList<>();
        Random random = new Random(0);
        for (int i = 0; i < 100; i++) {
            int randNum = random.nextInt(30);
            strings.add(randNum < 10 ? String.valueOf(randNum) : null);
        }

        return strings;
    }

}
