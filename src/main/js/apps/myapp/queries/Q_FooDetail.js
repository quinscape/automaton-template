import { query } from "@quinscape/automaton-js"

export default query(
    // language=GraphQL
        `query iQueryFooDetail($config: QueryConfigInput!)
    {
        iQueryFoo(config: $config)
        {
            type
            columnStates{
                name
                enabled
                sortable
            }
            queryConfig{
                id
                condition
                offset
                pageSize
                sortFields
            }
            rows{
                id
                name
                description
                flag
                num
                created
                type
                owner{
                    id
                    login
                }
            }
            rowCount
        }
    }`,
    {
        "config": {
            "pageSize": 20
        }
    }
)
