package ru.otus.homework20210623.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.homework20210623.config.IntegrationConfig;
import ru.otus.homework20210623.domain.Butterfly;
import ru.otus.homework20210623.domain.Caterpillar;
import ru.otus.homework20210623.reference.Species;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Интеграция")
@SpringBootTest
@ContextConfiguration(classes = {IntegrationConfig.class})
class IntegrationTest {

    public static final List<Caterpillar> CATERPILLARS = Arrays.asList(
            Caterpillar.builder().id(1L).species(Species.HESPERIIDAE).build(),
            Caterpillar.builder().id(2L).species(Species.LYCAENIDAE).build(),
            Caterpillar.builder().id(3L).species(Species.NYMPHALIDAE).build());

    public static final List<Butterfly> BUTTERFLIES = Arrays.asList(
            Butterfly.builder().id(1L).species(Species.HESPERIIDAE).build(),
            Butterfly.builder().id(2L).species(Species.LYCAENIDAE).build(),
            Butterfly.builder().id(3L).species(Species.NYMPHALIDAE).build());

    @Autowired
    private QueueChannel hesperiidaeButterflyChannel;

    @Autowired
    private QueueChannel lycaenidaeButterflyChannel;

    @Autowired
    private QueueChannel nymphalidaeButterflyChannel;

    @Autowired
    private IntegrationConfig.CaterpillarTransformation caterpillarTransformation;

    @DisplayName("Трансформация с классификацией")
    @Test
    void classifyingTest() {
        caterpillarTransformation.classify(CATERPILLARS);

        final var actualHesperiidae = hesperiidaeButterflyChannel.receive();
        assertThat(actualHesperiidae)
                .isNotNull()
                .<Butterfly>extracting(p -> (Butterfly) p.getPayload())
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(BUTTERFLIES.get(0));

        final var actualLycaenidae = lycaenidaeButterflyChannel.receive();
        assertThat(actualLycaenidae)
                .isNotNull()
                .<Butterfly>extracting(p -> (Butterfly) p.getPayload())
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(BUTTERFLIES.get(1));

        final var actualNymphalidae = nymphalidaeButterflyChannel.receive();
        assertThat(actualNymphalidae)
                .isNotNull()
                .<Butterfly>extracting(p -> (Butterfly) p.getPayload())
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(BUTTERFLIES.get(2));
    }
}
