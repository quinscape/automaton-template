import React from "react"
import bootstrap from "jsview-bootstrap"
import { Icon } from "@quinscape/automaton-js";

bootstrap(
    function (initial) {

        const {contextPath, csrfToken} = initial;

        // TODO: Change login-title
        return (
            <div className="container">
                <div className="row">
                    <div className="col">
                        <h1>
                            <Icon className="fa-flask mr-1 text-success mt-3"/>
                            automatontemplate Login
                        </h1>
                        <hr/>
                        {
                            location.search.indexOf("error") >= 0 && (
                                <React.Fragment>
                                    <p className="text-danger">Login failed</p>
                                    <hr/>
                                </React.Fragment>
                            )
                        }

                        <form method="POST" action={contextPath + "/login_check"}>
                            <div className="form-group">
                                <label htmlFor="loginField">Login</label>
                                <input type="text" className="form-control" name="username" id="loginField"
                                       placeholder="Login Name"/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="passwordField">Password</label>
                                <input type="password" className="form-control" id="passwordField"
                                       placeholder="Password" name="password"/>
                            </div>
                            <div className="form-check">
                                <input type="checkbox" className="form-check-input" id="rememberMeCheckbox"
                                       name="remember-me"/>
                                <label className="form-check-label" htmlFor="rememberMeCheckbox">Remember the login on
                                    this computer</label>
                            </div>

                            <input type="hidden" name={csrfToken.param} value={csrfToken.value}/>

                            <button type="submit" className="btn btn-primary">Submit</button>
                        </form>
                    </div>
                </div>
            </div>
        );
    })
    .then(() => console.info("ready!")
);

