package com.ei10391048.project.controlador;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FindingLocationControllerTest {


    @ParameterizedTest
    @MethodSource("nameDataFormat")
    public void formatingInputNameTest(String input, String sol) {
        assertEquals(FindingLocationController.formatingInputName(input), sol);
    }

    static Stream<Arguments> nameDataFormat() {
        return Stream.of(
                Arguments.of("madrid", "Madrid"),
                Arguments.of("MaDRid", "Madrid"),
                Arguments.of("MADRID", "Madrid"),
                Arguments.of("Pekín", "Pekin")
        );
    }

    @ParameterizedTest
    @MethodSource("NameOrCoordData")
    public void isCoordinatesTest(String input, boolean sol){
        assertEquals(FindingLocationController.isCoordinates(input),sol);
    }

    static Stream<Arguments> NameOrCoordData() {
        return Stream.of(
                Arguments.of("madrid", false),
                Arguments.of("MaDRid", false),
                Arguments.of("  MADRID", false),
                Arguments.of("100.05, 50.02", true),
                Arguments.of(" 100.41, 64.01", true),
                Arguments.of("100º10'12\", 10º11'15\"", true),
                Arguments.of("100.05 50.02",true),
                Arguments.of("100º10'12\" 10º11'15\"", true),
                Arguments.of("100.05N, 10.11O",true),
                Arguments.of("100.05N, 10.11W",true),
                Arguments.of("100.05N, 10.11E",true),
                Arguments.of("100.05S, 10.11O",true)



                );
    }

}

