package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

public class GeoServiceImplTests {

    public static Stream<Arguments> testByIp() {
        return Stream.of(
                Arguments.of(GeoServiceImpl.LOCALHOST, new Location(null, null, null, 0)),
                Arguments.of(GeoServiceImpl.MOSCOW_IP, new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of(GeoServiceImpl.NEW_YORK_IP, new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.0.33.12", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.44.183.150", new Location("New York", Country.USA, null,  0)),
                Arguments.of("97.44.183.150", null)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testByIp(String ip, Location locationExpected) {
        GeoServiceImpl geoServer = new GeoServiceImpl();
        Location actual = geoServer.byIp(ip);

        if(locationExpected != null) {
            Assertions.assertEquals(locationExpected.getCity(), actual.getCity());
            Assertions.assertEquals(locationExpected.getCountry(), actual.getCountry());
            Assertions.assertEquals(locationExpected.getStreet(), actual.getStreet());
            Assertions.assertEquals(locationExpected.getBuiling(), actual.getBuiling());
        } else {
            Assertions.assertNull(locationExpected);
        }
    }

    @Test
    public void testByCoordinates(){
        GeoServiceImpl geoServer = new GeoServiceImpl();

        String message = "Not implemented";
        double latitude = 55.45, longitude = 37.37;
        Class<RuntimeException> expected = RuntimeException.class;
        Executable executable = () -> geoServer.byCoordinates(latitude, longitude);

        Assertions.assertThrowsExactly(expected, executable, message);
    }
}
