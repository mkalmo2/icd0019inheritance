package inheritance.analyser;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class SalesAnalyserTest {

    List<SalesRecord> records = List.of(
            new SalesRecord("i1", 1, 12),
            new SalesRecord("i2", 2, 24),
            new SalesRecord("i1", 1, 6),
            new SalesRecord("i5", 12, 5),
            new SalesRecord("i5", 12, 5),
            new SalesRecord("i5", 12, 5),
            new SalesRecord("i5", 12, 5),
            new SalesRecord("i4", 24, 2, true));

    @Test
    public void calculatesTotalSalesWithFlatTaxRate() {
        FlatTaxSalesAnalyser analyser = new FlatTaxSalesAnalyser(records);

        assertThat(analyser.getTotalSales()).isCloseTo(295, within(0.1));
    }

    @Test
    public void calculatesTotalSalesByProductIdWithFlatTaxRate() {
        FlatTaxSalesAnalyser analyser = new FlatTaxSalesAnalyser(records);

        assertThat(analyser.getTotalSalesByProductId("i1")).isCloseTo(15, within(0.1));
    }

    @Test
    public void calculatesTotalSalesWithTaxFreeRate() {
        TaxFreeSalesAnalyser analyser = new TaxFreeSalesAnalyser(records);

        assertThat(analyser.getTotalSales()).isCloseTo(354, within(0.1));
    }

    @Test
    public void calculatesTotalSalesByProductIdWithTaxFreeRate() {
        TaxFreeSalesAnalyser analyser = new TaxFreeSalesAnalyser(records);

        assertThat(analyser.getTotalSalesByProductId("i1")).isCloseTo(18, within(0.1));
    }

    @Test
    public void calculatesTotalSalesWithDifferentiatedTaxRate() {
        DifferentiatedTaxSalesAnalyser analyser = new DifferentiatedTaxSalesAnalyser(records);

        assertThat(analyser.getTotalSales()).isCloseTo(298.6, within(0.1));
    }

    @Test
    public void findsTop3MostPopularSalesItems() {
        FlatTaxSalesAnalyser analyser = new FlatTaxSalesAnalyser(records);

        assertThat(analyser.getTop3PopularItems()).containsExactly("i2", "i5", "i1");
    }

    @Test
    public void findsTheItemWithTheLargestTotalSales() {
        FlatTaxSalesAnalyser analyser = new FlatTaxSalesAnalyser(records);

        assertThat(analyser.getIdOfItemWithLargestTotalSales()).isEqualTo("i5");
    }

    @Test
    public void allAnalysersHaveCommonAbstractSuperclass() {
        Class<?> s1 = FlatTaxSalesAnalyser.class.getSuperclass();
        Class<?> s2 = TaxFreeSalesAnalyser.class.getSuperclass();
        Class<?> s3 = DifferentiatedTaxSalesAnalyser.class.getSuperclass();

        assertThat(s1.getName()).isEqualTo(s2.getName());
        assertThat(s1.getName()).isEqualTo(s3.getName());

        assertThat(Modifier.isAbstract(s1.getModifiers()))
            .as("Superclass should be abstract")
            .isTrue();
    }

    @Test
    public void commonSuperclassIsSealed() {
        assertThat(FlatTaxSalesAnalyser.class.getSuperclass().isSealed())
            .as("Superclass should be sealed")
            .isTrue();
    }

    @Test
    public void specificAnalysersHaveOnlyMinimalCode() {
        assertThat(FlatTaxSalesAnalyser.class.getDeclaredMethods().length)
                .isLessThanOrEqualTo(2);
        assertThat(TaxFreeSalesAnalyser.class.getDeclaredMethods().length)
                .isLessThanOrEqualTo(2);
        assertThat(DifferentiatedTaxSalesAnalyser.class.getDeclaredMethods().length)
                .isLessThanOrEqualTo(2);
    }

    @Test
    public void specificAnalysersHaveOnlyProtectedMethods() {
        assertContainsOnlyProtectedMethods(FlatTaxSalesAnalyser.class);
        assertContainsOnlyProtectedMethods(TaxFreeSalesAnalyser.class);
        assertContainsOnlyProtectedMethods(DifferentiatedTaxSalesAnalyser.class);
    }

    @Test
    public void concreteClassesContainMinimalCode() {
        assertThat(getCodeSize(FlatTaxSalesAnalyser.class.getSimpleName())).isLessThan(30);
        assertThat(getCodeSize(TaxFreeSalesAnalyser.class.getSimpleName())).isLessThan(30);
        assertThat(getCodeSize(DifferentiatedTaxSalesAnalyser.class.getSimpleName())).isLessThan(30);
    }

    private int getCodeSize(String className) {
        String filePath = String.format("src/inheritance/analyser/%s.java", className);

        String contents;
        try {
            contents = Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return contents.replaceAll("\\s", "").split("\\W+").length;
    }

    private void assertContainsOnlyProtectedMethods(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            assertThat(Modifier.isProtected(method.getModifiers()))
                .as("Class %s contains non protected methods", clazz.getName())
                .isTrue();
        }
    }
}
