import "whatwg-fetch"
import bootstrap from "jsview-bootstrap"
import { configure, runInAction, toJS, observable } from "mobx"
import { config, getCurrentProcess, addConfig, shutdown, startup, Hub } from "@quinscape/automaton-js"
import Layout from "../../components/Layout";

// noinspection ES6UnusedImports
import AUTOMATON_CSS from "./automatontemplate.css"

// set MobX configuration
configure({
    enforceActions: "observed"
});

bootstrap(
    initial => {

        // we wait for the websocket handshake to finish
        return Hub.init(initial.connectionId)
            .then(() => {

            // and then start. If you can prevent websocket interactions early the wait might not be necessary.
            
            return startup(
                require.context("./", true, /\.js$/),
                initial,
                config => {

                    config.layout = Layout;
                }
            );
        })

    })
    .then(() => console.log("ready."));

export default {
    config,
    currentProcess: getCurrentProcess,
    runInAction,
    toJS
};
