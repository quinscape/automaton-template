import React from "react"
import { observer as fnObserver } from "mobx-react-lite";
import { FormLayout } from "domainql-form";
import FooForm from "./FooForm";


const CRUDDetail = props => {

    const { env } = props;
    const { scope } = env;

    return (
        <React.Fragment>

            <div className="row">
                <div className="col">
                    <FooForm value={ scope.currentFoo } options={{ layout: FormLayout.HORIZONTAL }}/>
                </div>
            </div>

        </React.Fragment>
    )
};

export default fnObserver(CRUDDetail)
