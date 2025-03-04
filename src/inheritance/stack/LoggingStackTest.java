package inheritance.stack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoggingStackTest {

    @Test
    public void loggingStackLogsMethodCalls() {
        LoggingStack stack = new LoggingStack();

        stack.push(1);
        stack.push(2);

        assertThat(stack.size()).isEqualTo(2);
        assertThat(stack.pop()).isEqualTo(2);
        assertThat(stack.pop()).isEqualTo(1);
        assertThat(stack.size()).isEqualTo(0);
    }


    @Test
    public void canAddMultipleElementsAtOnce() {
        LoggingStack stack = new LoggingStack();

        // stack.pushAll(1, 2, 3);

        assertThat(stack.size()).isEqualTo(3);
        assertThat(stack.pop()).isEqualTo(3);
        assertThat(stack.pop()).isEqualTo(2);
    }



}
