package com.ei10391048.project.controlador;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputValidatorTest {


    /**
     * Test de unidad que comprueba formato de mayúsculas
     */

    @ParameterizedTest
    @MethodSource("nameDataFormat")
    public void formatingInputNameTest(String input, String sol) {
        assertEquals(InputValidator.formatingInputName(input), sol);
    }

    static Stream<Arguments> nameDataFormat() {
        return Stream.of(
                Arguments.of("madrid", "Madrid"),
                Arguments.of("MaDRid", "Madrid"),
                Arguments.of("MADRID", "Madrid"),
                Arguments.of("Pekín", "Pekin"),
                Arguments.of("Castellón de la plana", "Castellon De La Plana")
        );
    }

    /**
     * Test de unidad que comprueba formato de coordenadas
     */
    @ParameterizedTest
    @MethodSource("coordsDataFormat")
    public void formatingInputCoordsTest(String input, double[] sol) {
        Assert.assertArrayEquals(InputValidator.formatingInputCoords(input), sol,0.1);
    }

    static Stream<Arguments> coordsDataFormat() {
        return Stream.of(
                Arguments.of("100.1 50.3", new double[]{100.1, 50.3}),
                Arguments.of("100.1, 50.3", new double[]{100.1, 50.3}),
                Arguments.of("100.1 50.3", new double[]{100.1, 50.3}),
                Arguments.of("36º59'32\"N 6º26'37\"W", new double[]{36.9921, -6.4435}),
                Arguments.of("36º59'32\"S, 6º26'37\"E", new double[]{-36.9921, 6.4435})
                );
    }

    /**
     * Test de unidad que comprueba formato de coordenadas
     */
    @ParameterizedTest
    @MethodSource("transformCoordsData")
    public void transformCoordsTest(String coord, double value){
        assertEquals(InputValidator.transformCoords(coord),value,0.1);
    }
    static Stream<Arguments> transformCoordsData() {
        return Stream.of(
                Arguments.of("36º59'32\"N",36.9921),
                Arguments.of("36º59'32\"S", -36.9921),
                Arguments.of("6º26'37\"E",  6.4435),
                Arguments.of("6º26'37\"O", -6.4435),
                Arguments.of("6º26'37\"W", -6.4435)
                );
    }


    /**
     * Test de unidad que comprueba formato de espacios en el nombre
     */
    @ParameterizedTest
    @MethodSource("urlLocationData")
    public void urlLocationName(String input, String sol){
        assertEquals(InputValidator.urlLocationName(input),sol);
    }

    static Stream<Arguments> urlLocationData() {
        return Stream.of(
                Arguments.of("Madrid", "Madrid"),
                Arguments.of("Los Ángeles", "Los_Ángeles"),
                Arguments.of("Castellon De La Plana", "Castellon_de_la_Plana")
        );}

    /**
     * Test de unidad que comprueba si el formato es correcto
     */
    @ParameterizedTest
    @MethodSource("NameOrCoordData")
    public void isCoordinatesTest(String input, boolean sol){
        assertEquals(InputValidator.isCoordinates(input),sol);
    }

    static Stream<Arguments> NameOrCoordData() {
        return Stream.of(
                Arguments.of("madrid", false),
                Arguments.of("MaDRid", false),
                Arguments.of("  MADRID", false),
                Arguments.of("Castellón de la Plana", false),
                Arguments.of("Washington DC", false),
                Arguments.of("Las Piedras", false),
                Arguments.of("20 2 3", false),
                Arguments.of("100.05, 50.02", true),
                Arguments.of(" 100.41, 64.01", true),
                Arguments.of("100º10'12\", 10º11'15\"", true),
                Arguments.of("100.05 50.02",true),
                Arguments.of("100º10'12\" 10º11'15\"", true),
                Arguments.of("100.05N, 10.11O",true),
                Arguments.of("100.05N, 10.11W",true),
                Arguments.of("100.05N, 10.11E",true),
                Arguments.of("100.05S, 10.11O",true),
                Arguments.of("N39°59'34.44\", O0°3'27.65\"",true)

                );
    }

}

