package inheritance.calc;

public class PayCalculator {

    public static final Double OVERTIME_RATE = 1.5;
    public static final Double HOUR_RATE = 20.0;
    public static final Double TAX_RATE = 22.0;

    protected Double straightPay(Integer hoursWorked) {
        hoursWorked = Math.min(40, hoursWorked);
        return HOUR_RATE * hoursWorked;
    }

    protected Double overtimePay(Integer hoursWorked) {
        Integer straightTime = Math.min(40, hoursWorked);
        Integer overTimeHoursWorked =
                Math.max(0, hoursWorked - straightTime);
        return OVERTIME_RATE * HOUR_RATE * overTimeHoursWorked;
    }

    public Double getWeeklyPayAfterTaxes(Integer hoursWorked) {
        double multiplier = (1 - TAX_RATE / 100);
        return (straightPay(hoursWorked) +
                overtimePay(hoursWorked)) * multiplier;
    }

}
