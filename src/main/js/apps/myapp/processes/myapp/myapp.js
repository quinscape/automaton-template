import {
    observable,
    computed,
    action,
    toJS
} from "mobx";

import {
    injection,
    config,
    createDomainObject,
    storeDomainObject,
    deleteDomainObject,
    GraphQLQuery,
    backToParent,

    FilterDSL
} from "@quinscape/automaton-js";
import Q_Foo from "../../queries/Q_Foo";
import Q_FooType from "../../queries/Q_FooType";


// deconstruct FilterDSL methods
const { field, value } = FilterDSL;

// language=GraphQL
const FooDetailQuery = new GraphQLQuery(`query queryFooDetail($config: QueryConfigInput!)
{
    iQueryFoo(config: $config)
    {
        rows{
            id
            name
            num
            description
            created
            flag
            type
            owner{
                id
                login
            }
        }
    }
}`
);

// noinspection JSUnusedGlobalSymbols
export function initProcess(process, scope)
{

    // process config

    // return process states and transitions
    return (
        {
            startState: "CRUDList",
            states: {
                "CRUDList":
                    {
                        "new-foo": {
                            to: "CRUDDetail",
                            action: t => {
                                const newObj = createDomainObject("FooInput");

                                newObj.name = "Unnamed Foo";
                                newObj.desc = "";
                                newObj.num = 0;
                                newObj.flag = false;
                                newObj.created = new Date();
                                newObj.type = "TYPE_A";

                                return scope.updateCurrent(newObj);
                            }
                        },
                        "to-detail": {
                            to: "CRUDDetail",
                            action: t => {

                                console.log("to-detail, context = ", t.context);

                                return FooDetailQuery.execute({
                                    config: {
                                        condition:
                                            field("id")
                                                .eq(
                                                    value(
                                                        "String",
                                                        t.context
                                                    )
                                                )
                                    }
                                }).then(({iQueryFoo}) => {

                                    if (iQueryFoo.rows.length === 0)
                                    {
                                        alert("Could not load Foo with id '" + t.context)
                                    }
                                    return scope.updateCurrent(iQueryFoo.rows[0]);
                                });
                            }
                        }
                    }
                ,
                "CRUDDetail": {
                    "save": {
                        action: t =>
                            storeDomainObject({
                                ... t.context,
                                ownerId:  config.auth.id || "",
                            })
                                .then(() => scope.foos.update())
                                .then(() => t.back(backToParent(t)))
                    },
                    "delete": {
                        to: "CRUDList",
                        discard: true,
                        confirmation: context => `Delete ${context.name} ?`,

                        action: t => {
                            const { id } = t.context;

                            return deleteDomainObject("Foo", id)
                                .then(
                                    didDelete => didDelete && scope.foos.update()
                                )
                        }
                    },
                    "cancel": {
                        to: "CRUDList",
                        discard: true,
                        action: t => {

                            console.log("Transition 'cancel'")
                        }
                    }
                }
            }
        }
    );
}

export default class CRUDTestScope {

    @observable
    currentFoo = null;

    /** Foo iQuery  */
    @observable
    foos = injection( Q_Foo );

    /**
     *  Example for an iQuery being used to drive a <Select/>
     *
     *  We could just as well define the values as constants in this case
     *
     * const FOO_TYPES = [
     *     "TYPE_A",
     *     "TYPE_B",
     *     "TYPE_C",
     *     "TYPE_D"
     * ];
     *  */
    @observable
    fooTypes = injection( Q_FooType );
    
    @action
    updateFoos(foos)
    {
        this.foos = foos;
    }

    @action
    removeFoo(id)
    {
        this.foos.rows = this.foos.rows.filter( foo => foo.id !== id);
    }

    @action
    updateCurrent(foo)
    {
        this.currentFoo = foo;
    }
}
