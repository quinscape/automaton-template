import { action, makeObservable, observable } from "mobx";
import { injection } from "@quinscape/automaton-js";
import Q_Foo from "../../queries/Q_Foo";
import Q_FooType from "../../queries/Q_FooType";
import CRUDList from "./states/CRUDList";

// noinspection JSUnusedGlobalSymbols
export function initProcess(process, scope) {
    return CRUDList;
}

export default class CRUDTestScope {

    constructor()
    {
        makeObservable(this);
    }

    @observable
    currentFoo = null;

    /** Foo iQuery  */
    @observable
    foos = injection( Q_Foo );

    /**
     *  Example for an iQuery being used to drive a <Select/>
     *
     *  We could just as well define the values as constants in this case
     *
     * const FOO_TYPES = [
     *     "TYPE_A",
     *     "TYPE_B",
     *     "TYPE_C",
     *     "TYPE_D"
     * ];
     *  */
    @observable
    fooTypes = injection( Q_FooType );
    
    @action
    updateFoos(foos)
    {
        this.foos = foos;
    }

    @action
    removeFoo(id)
    {
        this.foos.rows = this.foos.rows.filter( foo => foo.id !== id);
    }

    @action
    updateCurrent(foo)
    {
        this.currentFoo = foo;
    }
}
