import React from "react";

import {
    ViewState,
    storeDomainObject,
    extractTypeData,
    config,
    backToParent,
    deleteDomainObject,
    useEntity, i18n, CalendarField, Button,
} from "@quinscape/automaton-js";

import { Field, Form, GlobalErrors, Icon, TextArea } from "domainql-form";
import CRUDList from "./CRUDList";
import { ButtonToolbar } from "reactstrap";

const CRUDDetail = new ViewState("CRUDDetail", (process, scope) => ({
    "save": {
        action: t =>
            storeDomainObject({
                ... extractTypeData("FooInput", t.context),
                ownerId:  config.auth.id || "",
            })
                .then(() => scope.foos.update())
                .then(() => t.back(backToParent(t)))
    },

    "delete": {
        to: CRUDList,
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
        to: CRUDList,
        discard: true,
        action: t => {

            console.log("Transition 'cancel'")
        }
    }
}), props => {

    const { env } = props;
    const { scope } = env;

    const entity = useEntity("Foo", scope.currentFoo.id);

    return (
        <React.Fragment>

            <div className="row">
                <div className="col">



                    <Form
                        key={ scope.currentFoo.id }
                        type="Foo"
                        value={ scope.currentFoo }
                    >
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
                    </Form>
                </div>
            </div>

        </React.Fragment>
    )
});

export default CRUDDetail;
