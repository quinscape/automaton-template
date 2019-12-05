import React from "react"
import { observer as fnObserver } from "mobx-react-lite";
import { Button, CalendarField, config, i18n } from "@quinscape/automaton-js"

import { Field, GlobalErrors, Icon, TextArea, withForm } from "domainql-form"
import { ButtonToolbar } from "reactstrap";


const FooForm = props => {

    const { formConfig } = props;

    const { root } = formConfig;

    const auth = config.auth;

    return (
        <React.Fragment>
            <h1>
                {
                    i18n("Foo Detail")
                }
            </h1>


            <GlobalErrors/>
            <Field name="name"/>
            <TextArea name="description"/>
            <CalendarField name="created"/>
            <Field name="flag"/>
            <Field name="num"/>
            <Field name="type"/>

            <ButtonToolbar>
                <Button className="btn btn-primary mr-1" transition="save">
                    <Icon className="fa-save mr-1" />
                    Save
                </Button>
                <Button className="btn btn-danger mr-1" transition="delete">
                    <Icon className="fa-times mr-1" />
                    Delete
                </Button>
                <Button className="btn btn-secondary mr-1" transition="cancel">
                    <Icon className="fa-times mr-1" />
                    Cancel
                </Button>

            </ButtonToolbar>

        </React.Fragment>
    );
};

export default withForm(
    fnObserver(
        FooForm
    ),
    {
        type: "FooInput"
    }
)

