package inheritance.calc;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class CalculatorTest {

    @Test
    public void ex1() {
        PayCalculator calc = new PayCalculator();

        assertThat(calc.getWeeklyPayAfterTaxes(41))
                .isCloseTo(647.4, within(0.1));
    }

    @Test
    public void ex2() {
        TaxFreePayCalculator calc = new TaxFreePayCalculator();

        assertThat(calc.getWeeklyPayAfterTaxes(41))
                .isCloseTo(830, within(0.1));
    }

}
