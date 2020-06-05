import React from "react"
import { observer as fnObserver } from "mobx-react-lite";
import { FormLayout } from "domainql-form";
import FooForm from "./FooForm";
import { useEntity } from "@quinscape/automaton-js";


const CRUDDetail = props => {

    const { env } = props;
    const { scope } = env;

    const entity = useEntity("Foo", scope.currentFoo.id);

    return (
        <React.Fragment>

            <div className="row">
                <div className="col">
                    <FooForm
                        key={ scope.currentFoo.id }
                        value={ scope.currentFoo }
                        options={{
                            layout: FormLayout.HORIZONTAL,
                            isolation: false
                        }}
                    />
                </div>
            </div>

        </React.Fragment>
    )
};

export default fnObserver(CRUDDetail)
