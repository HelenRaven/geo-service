package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

public class LocalizationServiceImplTests {
    public static Stream<Arguments> testLocale() {
        return Stream.of(
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome"),
                Arguments.of(Country.RUSSIA, "Добро пожаловать")
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testLocale(Country country, String expected) {
        LocalizationServiceImpl localizeService = new LocalizationServiceImpl();
        String actual = localizeService.locale(country);

        Assertions.assertEquals(expected, actual);
    }
}
