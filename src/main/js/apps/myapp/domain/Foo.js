import { computed, makeObservable, observable } from "mobx";

// TODO: Change/delete client side domain class

/**
 * Example implementation of a client-side domain class. These classes are automatically registered and converted when
 * receiving data for the domain type witht the same name. The class *must* provide @observable entries for all
 * observable fields.
 *
 * Note that you only need a client-side domain class if you want to attach custom methods and/or @actions or @computed
 * methods to a domain type. Otherwise the system will just use observable maps that work out-of-the-box.
 *
 */
export default class Foo {

    constructor()
    {
        makeObservable(this)
    }

    @observable id;

    @observable name;

    @observable created;

    @observable num;

    @observable description;

    @observable type;

    @observable flag;

    @observable owner;


    /**
     * Example of a @computed method for the domain type.
     * 
     * @returns {string}
     */
    @computed get code()
    {
        return this.name + ":" + this.num + ":" + this.type
    }
}
