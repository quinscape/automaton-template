package de.quinscape.automatontemplate.runtime.config;

import de.quinscape.automaton.model.js.StaticFunctionReferences;
import de.quinscape.automaton.runtime.attachment.AttachmentRepository;
import de.quinscape.automaton.runtime.attachment.DatabaseAttachmentContentRepository;
import de.quinscape.automaton.runtime.attachment.DefaultAttachmentRepository;
import de.quinscape.automaton.runtime.data.FilterContextConfiguration;
import de.quinscape.automaton.runtime.data.FilterContextRegistry;
import de.quinscape.automaton.runtime.domain.DomainMonitorService;
import de.quinscape.automaton.runtime.domain.IdGenerator;
import de.quinscape.automaton.runtime.domain.UUIDGenerator;
import de.quinscape.automaton.runtime.domain.op.BatchStoreOperation;
import de.quinscape.automaton.runtime.domain.op.DefaultBatchStoreOperation;
import de.quinscape.automaton.runtime.domain.op.DefaultStoreOperation;
import de.quinscape.automaton.runtime.domain.op.StoreOperation;
import de.quinscape.automaton.runtime.filter.JavaFilterTransformer;
import de.quinscape.automaton.runtime.i18n.DefaultTranslationService;
import de.quinscape.automaton.runtime.i18n.TranslationService;
import de.quinscape.automaton.runtime.pubsub.DefaultPubSubService;
import de.quinscape.automaton.runtime.pubsub.PubSubMessageHandler;
import de.quinscape.automaton.runtime.pubsub.PubSubService;
import de.quinscape.automaton.runtime.userinfo.IQueryUserInfoProvider;
import de.quinscape.automaton.runtime.userinfo.UserInfoService;
import de.quinscape.automaton.runtime.ws.DefaultAutomatonWebSocketHandler;
import de.quinscape.automatontemplate.domain.tables.pojos.AppTranslation;
import de.quinscape.domainql.DomainQL;
import de.quinscape.spring.jsview.loader.FileResourceLoader;
import de.quinscape.spring.jsview.loader.ResourceHandle;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.Collections;

import static de.quinscape.automatontemplate.domain.Tables.*;

@Configuration
public class ServiceConfiguration
{
    private final static Logger log = LoggerFactory.getLogger(ServiceConfiguration.class);


    private FileResourceLoader externalResourceLoader;


    @Bean
    public IdGenerator uuidGenerator()
    {
        return new UUIDGenerator();
    }

    @Bean
    public StoreOperation defaultStoreOperation(
        DSLContext dslContext,
        @Lazy DomainQL domainQL
    )
    {
        return new DefaultStoreOperation(
            dslContext,
            domainQL
        );
    }

    @Bean
    public BatchStoreOperation defaultBatchStoreOperation(
        DSLContext dslContext,
        @Lazy DomainQL domainQL
    )
    {
        return new DefaultBatchStoreOperation(
            dslContext,
            domainQL
        );
    }


    @Bean
    public DefaultAutomatonWebSocketHandler automatonWebSocketHandler(
        PubSubService pubSubService,
        JavaFilterTransformer javaFilterTransformer,
        @Lazy DomainQL domainQL
    )
    {
        return new DefaultAutomatonWebSocketHandler(
            Collections.singletonList(
                new PubSubMessageHandler(domainQL, pubSubService, javaFilterTransformer)
            )
        );
    }

    @Bean
    public PubSubService pubSubService(FilterContextRegistry registry)
    {
        return new DefaultPubSubService(registry);
    }

    @Bean
    public DomainMonitorService domainMonitorService(PubSubService pubSubService)
    {
        return new DomainMonitorService(
            pubSubService
        );
    }

    @Bean
    public TranslationService translationService(
        DSLContext dslContext,

        @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") @Qualifier("jsFunctionReferences")
            ResourceHandle<StaticFunctionReferences> jsFunctionReferencesHandle
    )
    {
        return new DefaultTranslationService(
            dslContext,
            jsFunctionReferencesHandle,
            APP_TRANSLATION,
            AppTranslation.class
        );
    }

    @Bean
    public FilterContextConfiguration filterContextConfiguration(DSLContext dslContext)
    {
        return registry -> {
            registry.register("param","https://quinscape.de");
            registry.register("userCount", ctx -> dslContext.selectCount().from(APP_USER).fetchOne().value1());
        };
    }

    @Bean
    public UserInfoService userInfoService(
        @Lazy DomainQL domainQL
    )
    {
        return new UserInfoService(
            new IQueryUserInfoProvider(
                domainQL,
                // language=GraphQL
                "query getUserInfo($config: QueryConfigInput!)\n" +
                "{\n" +
                "    iQueryAppUser(config: $config)\n" +
                "    {\n" +
                "        type" +
                    "        \n" +
                    "        rows{\n" +
                "            id\n" +
                "            created\n" +
                "            lastLogin\n" +
                "        }\n"    +
                "    }\n" +
                "}"
            )
        );
    }

// Alternative: File based attachment repository                                                    A
//    @Bean
//    public AttachmentRepository attachmentRepository(
//        DSLContext dslContext,
//        @Lazy DomainQL domainQL,
//        @Value("${automatontest.attachments}") String attachmentDirectory
//    )
//    {
//        return new DefaultAttachmentRepository(
//            dslContext,
//            domainQL,
//            new FileBasedAttachmentContentRepository(
//                new File(attachmentDirectory)
//            )
//        );
//    }

    @Bean
    public AttachmentRepository attachmentRepository(
        DSLContext dslContext,
        @Lazy DomainQL domainQL
    )
    {
        return new DefaultAttachmentRepository(
            dslContext,
            domainQL,
            new DatabaseAttachmentContentRepository(
                dslContext,
                domainQL
            )
        );
    }
}
