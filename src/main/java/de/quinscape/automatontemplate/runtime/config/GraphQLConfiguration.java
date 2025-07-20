package de.quinscape.automatontemplate.runtime.config;

import de.quinscape.automaton.runtime.scalar.ConditionScalar;
import de.quinscape.automaton.runtime.scalar.ConditionType;
import de.quinscape.automaton.runtime.scalar.FieldExpressionScalar;
import de.quinscape.automaton.runtime.scalar.FieldExpressionType;
import de.quinscape.automatontemplate.domain.Public;
import de.quinscape.automatontemplate.domain.tables.pojos.DomainField;
import de.quinscape.automatontemplate.domain.tables.pojos.DomainType;
import de.quinscape.automatontemplate.domain.tables.pojos.App;
import de.quinscape.domainql.DomainQL;
import de.quinscape.domainql.annotation.GraphQLLogic;
import de.quinscape.domainql.config.SourceField;
import de.quinscape.domainql.config.TargetField;
import de.quinscape.domainql.generic.DomainObject;
import de.quinscape.domainql.generic.DomainObjectScalar;
import de.quinscape.domainql.generic.GenericScalar;
import de.quinscape.domainql.generic.GenericScalarType;
import de.quinscape.domainql.jsonb.JSONB;
import de.quinscape.domainql.jsonb.JSONBScalar;
import de.quinscape.domainql.meta.MetadataProvider;
import graphql.GraphQL;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Collection;

import static de.quinscape.automatontemplate.domain.Tables.*;

/**
 * Exemplary configuration of GraphQL in a project.
 */
@Configuration
public class GraphQLConfiguration
{
    private final static Logger log = LoggerFactory.getLogger(GraphQLConfiguration.class);


    private final ApplicationContext applicationContext;
    private final DSLContext dslContext;


    @Autowired
    public GraphQLConfiguration(
        ApplicationContext applicationContext,
        DSLContext dslContext
    )
    {
        this.applicationContext = applicationContext;
        this.dslContext = dslContext;
    }

    
    @Bean
    public DomainQL domainQL() throws IOException
    {
        final Collection<Object> logicBeans = applicationContext.getBeansWithAnnotation(GraphQLLogic.class).values();
        final Collection<MetadataProvider> metadataProviders = applicationContext.getBeansOfType(MetadataProvider.class).values();

        final DomainQL domainQL = DomainQL.newDomainQL(dslContext)
            //.parameterProvider(new AutomatonConnectionProviderFactory(applicationContext))

            .logicBeans(logicBeans)

            .objectTypes(Public.PUBLIC)

            .withAdditionalScalar(DomainObject.class, DomainObjectScalar.newDomainObjectScalar())
            .withAdditionalScalar(JSONB.class, new JSONBScalar())
            .withAdditionalScalar(GenericScalar.class, GenericScalarType.newGenericScalar())
            .withAdditionalScalar(ConditionScalar.class, ConditionType.newConditionType())
            .withAdditionalScalar(FieldExpressionScalar.class, FieldExpressionType.newFieldExpressionType())

            .withAdditionalInputTypes(
                App.class,
                DomainType.class,
                DomainField.class
            )


            // configure object creation for schema relationships

            //.configureRelation(DB_TABLE.FOREIGN_KEY_FIELD, SourceField.*, TargetField.*[, "sourceFieldName", "targetFieldName"])
            .configureRelation(DOMAIN_TYPE.APP_ID, SourceField.NONE, TargetField.MANY)
            .configureRelation(DOMAIN_FIELD.DOMAIN_TYPE_ID, SourceField.NONE, TargetField.MANY)
            .configureRelation(DOMAIN_TYPE_META.DOMAIN_TYPE_ID, SourceField.NONE, TargetField.MANY, "", "meta")
            .configureRelation(DOMAIN_FIELD_META.DOMAIN_FIELD_ID, SourceField.NONE, TargetField.MANY, "", "meta")
            /*
            /*
                documentation for the types defined in the automaton library
             */
            .withTypeDocsFrom(
                new ClassPathResource("automaton-typedocs.json").getInputStream()
            )
            /*
                hand-written JSON docs for example
             */
            .withTypeDocsFrom(
                new ClassPathResource("domain-typedocs.json").getInputStream()
            )
            /*
                local source docs
             */
            .withTypeDocsFrom(
                new ClassPathResource("source-typedocs.json").getInputStream()
            )

            .withMetadataProviders(
                metadataProviders.toArray(new MetadataProvider[0])
            )

            .build();
            
        return domainQL;
    }

    @Bean
    public GraphQL graphQL(DomainQL domainQL)
    {
        return GraphQL.newGraphQL(domainQL.getGraphQLSchema()).build();
    }
}
