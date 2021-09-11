import "whatwg-fetch"
import bootstrap from "jsview-bootstrap"
import { configure, runInAction, toJS, observable } from "mobx"
import {
    config,
    getCurrentProcess,
    StartupRegistry,
    shutdown,
    startup,
    Hub,
    printSchema,
    pickSchemaTypes
} from "@quinscape/automaton-js"
import Layout from "../../components/Layout";

// noinspection ES6UnusedImports
import AUTOMATON_CSS from "./automatontemplate.css"
import { FormContext } from "domainql-form";

// set MobX configuration
configure({
    enforceActions: "observed"
});

bootstrap(
    initial => {

            // and then start. If you can prevent websocket interactions early the wait might not be necessary.

        return startup(
            require.context("./", true, /^.\/(scopes|domain\/.*)\.js$/),

            name => import(
                /* webpackChunkName: "process-" */
                /* webpackExclude: /(states|queries|composites)/ */
                `./processes/${name}/${name}.js`
                ),
            initial,
            config => {

                new FormContext(config.inputSchema).useAsDefault();

                config.layout = Layout;

                // register things with StartupRegistry

                return Hub.init(initial.connectionId)
            }
        );
    })
    .then(() => console.log("ready."));

export default {
    config,
    currentProcess: getCurrentProcess,
    pickSchemaTypes : array => pickSchemaTypes(config.inputSchema, array),
    runInAction,
    toJS,
    printSchema
};
