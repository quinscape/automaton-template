 package de.quinscape.automatontemplate.runtime.service;

 import de.quinscape.automaton.model.data.InteractiveQuery;
 import de.quinscape.automaton.model.data.QueryConfig;
 import de.quinscape.automaton.runtime.data.InteractiveQueryService;
 import de.quinscape.automatontemplate.domain.tables.pojos.AppUser;
 import de.quinscape.automatontemplate.domain.tables.pojos.DomainField;
 import de.quinscape.automatontemplate.domain.tables.pojos.DomainType;
 import de.quinscape.automatontemplate.domain.tables.pojos.App;
 import de.quinscape.domainql.annotation.GraphQLLogic;
 import de.quinscape.domainql.annotation.GraphQLQuery;
 import de.quinscape.domainql.annotation.GraphQLTypeParam;
 import graphql.schema.DataFetchingEnvironment;
 import org.jooq.DSLContext;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;

 import javax.validation.constraints.NotNull;

 @GraphQLLogic
public class AutomatontemplateLogic
{
    private final static Logger log = LoggerFactory.getLogger(AutomatontemplateLogic.class);

    private final DSLContext dslContext;
    private final InteractiveQueryService interactiveQueryService;

    @Autowired
    public AutomatontemplateLogic(
        DSLContext dslContext,
        InteractiveQueryService interactiveQueryService
    )
    {
        this.dslContext = dslContext;
        this.interactiveQueryService = interactiveQueryService;
    }


    /**
     * Default implementation of an InteractiveQuery based query for type [T].
     *
     * @param type
     * @param env
     * @param config    configuration for the Interactive query.
     * @param <T>
     * @return
     */
    @GraphQLQuery
    public <T> InteractiveQuery<T> iQuery(

        @GraphQLTypeParam(
            types = {
                App.class,
                AppUser.class,
                DomainType.class,
                DomainField.class
            }
        )
        Class<T> type,
        DataFetchingEnvironment env,
        @NotNull QueryConfig config
    )
    {

        log.info("iQuery<{}>, config = {}", type, config);

        return interactiveQueryService.buildInteractiveQuery( type, env, config)
            .execute();

    }
}
