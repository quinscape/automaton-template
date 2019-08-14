import { observable } from "mobx";

export default class ChatHistory
{
    @observable
    data = [];

    constructor(data)
    {
        this.data.replace(data);
    }

    add(text, source = "SYSTEM")
    {
        this.data.push({
            message: {
                text,
                source
            },
            timestamp: new Date().toISOString()
        })
    }
}
