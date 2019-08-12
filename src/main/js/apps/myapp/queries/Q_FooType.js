import { query } from "@quinscape/automaton-js"

export default query(
    // language=GraphQL
        `query iQueryFooType($config: QueryConfigInput!)
    {
        iQueryFooType(config: $config)
        {
            rows{
                name
            }
            rowCount
        }
    }`,
    {
        "config": {
            // deactivate paging
            "pageSize": 0
        }
    }
)
