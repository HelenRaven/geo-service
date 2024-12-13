package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MessageSenderImplTests {
    public static Stream<Arguments> testSend() {
        return Stream.of(
                Arguments.of(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, GeoServiceImpl.LOCALHOST), "Добро пожаловать"),
                Arguments.of(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, GeoServiceImpl.MOSCOW_IP), "Добро пожаловать"),
                Arguments.of(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, GeoServiceImpl.NEW_YORK_IP), "Welcome"),
                Arguments.of(new HashMap<>(), "Welcome")
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testSend(Map<String, String> headers, String expected) {
        LocalizationService localizeService = Mockito.mock(LocalizationService.class);
        GeoService geoServer = Mockito.mock(GeoService.class);

        Mockito.when(localizeService.locale(Mockito.any(Country.class))).thenAnswer( invocation -> {
            Country argument = (Country) invocation.getArguments()[0];
            if (argument == Country.RUSSIA) {
               return "Добро пожаловать";
            } else {
                return "Welcome";
            }
        });

        Mockito.when(geoServer.byIp(Mockito.anyString())).thenAnswer( invocation -> {
            String argument = (String) invocation.getArguments()[0];
            if (argument.startsWith("172") || argument.equals("127.0.0.1")) {
                return new Location("Moscow", Country.RUSSIA, "Lenina", 15);
            } else {
                return new Location("New York", Country.USA, null,  0);
            }
        });

        MessageSenderImpl sender = new MessageSenderImpl(geoServer, localizeService);

        String actual = sender.send(headers);

        Assertions.assertEquals(expected, actual);
    }
}
