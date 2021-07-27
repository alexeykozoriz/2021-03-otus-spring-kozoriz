package ru.otus.homework20210623.config;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import ru.otus.homework20210623.domain.Butterfly;
import ru.otus.homework20210623.domain.Caterpillar;
import ru.otus.homework20210623.reference.Species;

import java.util.Collection;

/**
 * Конфигурация интеграции
 */
@EnableIntegration
@IntegrationComponentScan
public class IntegrationConfig {

    /**
     * Канал для бабочек вида HESPERIIDAE
     *
     * @return очередь
     */
    @Bean
    QueueChannel hesperiidaeButterflyChannel() {
        return new QueueChannel();
    }

    /**
     * Канал для бабочек вида LYCAENIDAE
     *
     * @return очередь
     */
    @Bean
    QueueChannel lycaenidaeButterflyChannel() {
        return new QueueChannel();
    }

    /**
     * Канал для бабочек вида NYMPHALIDAE
     *
     * @return очередь
     */
    @Bean
    QueueChannel nymphalidaeButterflyChannel() {
        return new QueueChannel();
    }

    /**
     * Принадлежность к виду HESPERIIDAE
     *
     * @param butterfly бабочка
     * @return true если да
     */
    boolean isHesperiidae(Butterfly butterfly) {
        return butterfly.getSpecies() == Species.HESPERIIDAE;
    }

    /**
     * Принадлежность к виду LYCAENIDAE
     *
     * @param butterfly бабочка
     * @return true если да
     */
    boolean isLycaenidae(Butterfly butterfly) {
        return butterfly.getSpecies() == Species.LYCAENIDAE;
    }

    /**
     * Принадлежность к виду NYMPHALIDAE
     *
     * @param butterfly бабочка
     * @return true если да
     */
    boolean isNymphalidae(Butterfly butterfly) {
        return butterfly.getSpecies() == Species.NYMPHALIDAE;
    }

    /**
     * Поток для трансформации гусеницы в бабочку с классификацией по видам
     *
     * @return поток
     */
    @Bean
    public IntegrationFlow classify() {
        return flow -> flow.split()
                .transform(Caterpillar.class, p -> Butterfly.builder().id(p.getId()).species(p.getSpecies()).build())
                .routeToRecipients(route -> route
                        .recipientFlow(subflow -> subflow.<Butterfly>filter(this::isHesperiidae).channel("hesperiidaeButterflyChannel"))
                        .recipientFlow(subflow -> subflow.<Butterfly>filter(this::isLycaenidae).channel("lycaenidaeButterflyChannel"))
                        .recipientFlow(subflow -> subflow.<Butterfly>filter(this::isNymphalidae).channel("nymphalidaeButterflyChannel")));
    }

    /**
     * Шлюз потоков трансформации гусеницы
     */
    @MessagingGateway
    public interface CaterpillarTransformation {

        /**
         * Поток трансформации из гусеницы в бабочку с классификацией по видам
         *
         * @param caterpillars гусеницы
         */
        @Gateway(requestChannel = "classify.input")
        void classify(Collection<Caterpillar> caterpillars);
    }
}
