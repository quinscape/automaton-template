import React from "react"
import { observer as fnObserver } from "mobx-react-lite";
import { Button, config, i18n, ScrollTracker, CalendarField } from "@quinscape/automaton-js"

import { Field, GlobalErrors, TextArea, withForm, FieldMode } from "domainql-form"
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
                <Button
                    className="btn btn-primary mr-1"
                    transition="save"
                    icon="fa-save mr-1"
                    text="Save"
                />
                <Button
                    className="btn btn-danger mr-1"
                    transition="delete"
                    icon="fa-times mr-1"
                    text="Delete"
                />
                <Button
                    className="btn btn-secondary mr-1"
                    transition="cancel"
                    icon="fa-times mr-1"
                    text="Cancel"
                />

            </ButtonToolbar>

        </React.Fragment>
    )
};

export default withForm(
    fnObserver(
        FooForm
    ),
    {
        type: "FooInput"
    }
)

