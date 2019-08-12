import "whatwg-fetch"
import bootstrap from "jsview-bootstrap"
import { configure, runInAction, toJS } from "mobx"
import { config, getCurrentProcess, shutdown, startup } from "@quinscape/automaton-js"
import Layout from "../../components/Layout";

// noinspection ES6UnusedImports
import AUTOMATON_CSS from "./automatontemplate.css"

// set MobX configuration
configure({
    enforceActions: "observed"
});

bootstrap(
    initial => {

        return startup(
            require.context("./", true, /\.js$/),
            initial,
            config => {

                config.layout = Layout;
            }
        );
    },
    () => console.log("ready.")
);


export default {
    config,
    currentProcess: getCurrentProcess,
    runInAction,
    toJS
};
