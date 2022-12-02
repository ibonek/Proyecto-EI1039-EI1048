package com.ei10391048.project.controlador;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FindingLocationControllerTest {


    @ParameterizedTest
    @MethodSource("dataFormat")
    public void formatingInputTest(String input, String sol) {
        assertEquals(FindingLocationController.formatingInput(input), sol);
    }

    static Stream<Arguments> dataFormat() {
        return Stream.of(
                Arguments.of("madrid", "Madrid"),
                Arguments.of("MaDRid", "Madrid"),
                Arguments.of("MADRID", "Madrid"),
                Arguments.of(" Madrid", "Madrid"),
                Arguments.of("Madrid ", "Madrid"),
                Arguments.of("Pekín", "Pekin")
        );
    }
}

