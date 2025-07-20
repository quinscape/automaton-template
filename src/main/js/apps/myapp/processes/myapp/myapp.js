import { action, makeObservable, observable } from "mobx";
import { injection } from "@quinscape/automaton-js";
import Home from "./states/Home";

// noinspection JSUnusedGlobalSymbols
export function initProcess(process, scope) {
    return Home;
}

export default class CRUDTestScope {

    constructor()
    {
        makeObservable(this);
    }

    @action
    bah()
    {
        
    }

}
