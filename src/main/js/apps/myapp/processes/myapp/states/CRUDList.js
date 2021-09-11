import React from "react";

import {
    ViewState,
    createDomainObject,
    config,
    useDomainMonitor,
    i18n,
    Button,
    DataGrid,
    DomainActivityIndicator,
    FilterDSL,
} from "@quinscape/automaton-js";

import { ButtonToolbar } from "reactstrap";
import { Icon, Select } from "domainql-form";
import CRUDDetail from "./CRUDDetail";
import Q_FooDetail from "../../../queries/Q_FooDetail";

const {
    field,
    value
} = FilterDSL;

const CRUDList = new ViewState("CRUDList", (process, scope) => ({
    "new-foo": {
        to: CRUDDetail,
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
        to: CRUDDetail,
        action: t => {

            console.log("to-detail, context = ", t.context);

            return Q_FooDetail.execute({
                config: {
                    condition:
                        field("id")
                            .eq(
                                value(
                                    t.context
                                )
                            )
                }
            }).then(({iQueryFoo}) => {

                if (iQueryFoo.rows.length === 0)
                {
                    alert("Could not load Foo with id '" + t.context)
                }
                return scope.updateCurrent(
                    config.inputSchema.clone(iQueryFoo.rows[0])
                );
            });
        }
    }
}), props => {

    const { env } = props;

    const { scope } = env;

    const monitor = useDomainMonitor(
        field("domainType")
            .eq(
                value("Foo")
            )
    );

    return (
        <React.Fragment>
            <h1>
                {
                    i18n("CRUD List")
                }
            </h1>

            <ButtonToolbar>
                <Button className="btn btn-primary mr-1" transition="new-foo">
                    <Icon className="fa-save mr-1" />
                    New
                </Button>
            </ButtonToolbar>

            <DataGrid
                id="iquery-test"
                value={ scope.foos }
            >
                <DataGrid.Column
                    heading={ i18n("Action") }
                >
                    {
                        foo => (
                            <Button
                                className="btn btn-secondary text-nowrap"
                                transition="to-detail"
                                context={ foo.id }>
                                <Icon className="fa-edit" />
                                Detail
                            </Button>
                        )
                    }
                </DataGrid.Column>
                <DataGrid.Column name="name" filter="containsIgnoreCase"/>
                <DataGrid.Column name="description" filter="containsIgnoreCase"/>
                <DataGrid.Column name="flag" filter="eq"/>
                <DataGrid.Column name="type" filter="eq" renderFilter={
                    (name, scalarType, label) => {
                        /**
                         * Use another iQuery (on FooType) as select values
                         */
                        return (
                            <Select
                                name={ name }
                                values={ scope.fooTypes.rows }
                                type={ scalarType }
                                label={ label }
                                labelClass="sr-only"
                                nameProperty={ "name" }
                                valueProperty={ "name" }
                            />
                        );
                    }
                }/>
                <DataGrid.Column name="owner.login" filter="containsIgnoreCase" heading={ i18n("owner") }/>
                <DataGrid.Column
                    heading={ i18n("Activity") }
                >
                    {
                        foo => (
                            <DomainActivityIndicator
                                key={ foo.id }
                                monitor={ monitor }
                                domainType="Foo"
                                id={ foo.id }
                            />
                        )
                    }
                </DataGrid.Column>
            </DataGrid>

        </React.Fragment>
    );
});

export default CRUDList;
