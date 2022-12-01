package com.ei10391048.project.controlador;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FindingByNameControllerTest {


    @ParameterizedTest
    @MethodSource("dataFormat")
    public void formatingInputTest(String input, String sol) {
        assertEquals(FindingByNameController.formatingInput(input), sol);
    }

    static Stream<Arguments> dataFormat() {
        return Stream.of(
                Arguments.of("madrid", "Madrid"),
                Arguments.of("MaDRid", "Madrid"),
                Arguments.of("MADRID", "Madrid"),
                Arguments.of(" Madrid", "Madrid"),
                Arguments.of("Madrid ", "Madrid")
        );
    }
}

