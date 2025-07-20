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

const {
    field,
    value
} = FilterDSL;

const Home = new ViewState("CRUDList", (process, scope) => ({
    "new-foo": {
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
                    i18n("Home")
                }
            </h1>

        </React.Fragment>
    );
});

export default Home;
