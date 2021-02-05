package juddy.transport.impl.server;

import juddy.transport.api.*;
import juddy.transport.api.engine.ApiEngine;
import juddy.transport.config.server.ApiServerTestManualConfiguration;
import juddy.transport.utils.FileSourceHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(ApiServerTestManualConfiguration.class)
class ApiServerManualConfigTest {

    // Реализовать разные примеры CustomApiServerArgsConverter с преобразованием как аргуметров вызова, так и вычислением вызываемого метода от входящего ArgsWrapper

    @Autowired
    private ApiEngine apiEngineFromSource;
    @Autowired
    private TestApiSinkServer testApiSinkServer;
    @Autowired
    private FileSourceHelper fileSourceHelper;
    @Autowired
    private TestApi testApi;
    @Autowired
    private TestApiPhaseTwo testApiPhaseTwo;
    @Autowired
    private TestApiPhaseOne testApiPhaseOne;

    // Получаем строки из TestSource, преобразуем из в вызов единственного метода API, проверяем результат
    @Test
    void when_readFileSourceAndRunServerApi_then_ok() throws InterruptedException {
        testApiSinkServer.reset();
        apiEngineFromSource.run();
        Thread.sleep(1000);
        testApiSinkServer.check(fileSourceHelper.getValues().toArray(new String[fileSourceHelper.getValues().size()]));
    }

    // Вызываем сервер явно, получаем результат
    @Test
    void when_callApiServer_then_ok() throws ExecutionException, InterruptedException, TimeoutException {
        List<String> cities = testApi.split("Москва, Минск, Киев, Таллин, Рига, Кишинев").get(500, TimeUnit.MILLISECONDS);
        assertThat(cities).containsExactly("Москва", "Минск", "Киев", "Таллин", "Рига", "Кишинев");
    }

    // Вызываем сервер явно, получаем результат, далее еще один вызов
    @Test
    void when_callApiServerAndNextOne_then_ok() throws ExecutionException, InterruptedException, TimeoutException {
        testApiSinkServer.reset();
        List<String> cities = testApiPhaseOne.split("Москва, Минск, Киев, Таллин, Рига, Кишинев").get(500, TimeUnit.MILLISECONDS);
        Thread.sleep(100);
        testApiSinkServer.check(6);
    }
}