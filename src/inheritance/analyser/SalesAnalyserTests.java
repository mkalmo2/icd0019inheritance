package inheritance.analyser;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SalesAnalyserTests {

    List<SalesRecord> records = List.of(
            new SalesRecord("i1", getDate("2009-08-03"), 1, 2),
            new SalesRecord("i2", getDate("2010-09-04"), 2, 1),
            new SalesRecord("i2", getDate("2011-01-07"), 2, 3),
            new SalesRecord("i3", getDate("2012-07-15"), 3, 7),
            new SalesRecord("i1", getDate("2013-04-08"), 1, 2),
            new SalesRecord("i3", getDate("2014-09-11"), 3, 6),
            new SalesRecord("i5", getDate("2024-01-01"), 6, 5),
            new SalesRecord("i4", getDate("2024-11-05"), 3, 1),
            new SalesRecord("i5", getDate("2025-12-03"), 6, 1),
            new SalesRecord("i5", getDate("2025-10-22"), 7, 5));

    @Test
    public void calculatesTotalSalesForEstonia() {
        EstonianTaxSalesAnalyser analyser = new EstonianTaxSalesAnalyser(records);

        assertThat(analyser.getTotalSales()).is(closeTo(102.6));
    }

    @Test
    public void calculatesTotalSalesByProductIdWithForEstonia() {
        EstonianTaxSalesAnalyser analyser = new EstonianTaxSalesAnalyser(records);

        assertThat(analyser.getTotalSalesByProductId("i5")).is(closeTo(57.7));
    }

    @Test
    public void calculatesTotalSalesWithTaxFreeRate() {
        TaxFreeSalesAnalyser analyser = new TaxFreeSalesAnalyser(records);

        assertThat(analyser.getTotalSales()).is(closeTo(125));
    }

    @Test
    public void calculatesTotalSalesByProductIdWithTaxFreeRate() {
        TaxFreeSalesAnalyser analyser = new TaxFreeSalesAnalyser(records);

        assertThat(analyser.getTotalSalesByProductId("i1")).is(closeTo(4));
    }

    @Test
    public void calculatesTotalSalesForFinland() {
        FinnishSalesAnalyser analyser = new FinnishSalesAnalyser(records);

        assertThat(analyser.getTotalSales()).is(closeTo(100.6));
    }

    @Test
    public void findsTop3MostPopularSalesItems() {
        FinnishSalesAnalyser analyser = new FinnishSalesAnalyser(records);

        assertThat(analyser.getTop3PopularItems())
                .containsExactlyInAnyOrder("i1", "i3", "i5");
    }

    @Test
    public void findsLargestTotalSalesAmountForSingleItem() {
        FinnishSalesAnalyser analyser = new FinnishSalesAnalyser(records);

        assertThat(analyser.getLargestTotalSalesAmountForSingleItem()).is(closeTo(56.9));
    }

    private static LocalDate getDate(String date) {
        return LocalDate.parse(date);
    }

    private Condition<Double> closeTo(double expected) {
        double precision = 0.1;

        return new Condition<>(
                actual -> Math.abs(actual - expected) <= precision,
                "value close to %s (within +/- %s)", expected, precision
        );
    }
}
