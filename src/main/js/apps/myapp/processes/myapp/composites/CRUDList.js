import React from "react"
import { observer as fnObserver } from "mobx-react-lite";
import { ButtonToolbar } from "reactstrap";
import {
    Button,
    DomainActivityIndicator,
    FilterDSL,
    i18n,
    IQueryGrid as DataGrid,
    useDomainMonitor
} from "@quinscape/automaton-js"
import { Icon, Select } from "domainql-form";

// deconstruct FilterDSL methods
const { field, value } = FilterDSL;


const CRUDList = props => {

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
};

export default fnObserver(CRUDList);
