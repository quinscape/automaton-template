import React, { useEffect, useState, useRef } from "react"
import cx from "classnames"

import { config, Hub } from "@quinscape/automaton-js"
import { action, toJS } from "mobx"
import { observer as fnObserver } from "mobx-react-lite"
import { observer } from "mobx-react"
import { Popover, PopoverBody, PopoverHeader } from "reactstrap"

const addChatMessage = action("addChatMessage", (text, source) => {
    const { chatHistory } = config;
    chatHistory.add(text, source)
});

// noinspection ES6UnusedImports
import STYLE from "./chatbar.css"

const ChatEntry = ({entry}) => {

    if (!entry)
    {
        return false;
    }

    const { timestamp, message: { text, source }  } = entry;

    return (
        <span
            title={ timestamp }
        >
            {
                "<" + source + "> " + text + "\n"
            }
        </span>
    );
};


/**
 * Oldschool klass because we need snapshot values
 */
@observer
class ChatWindow extends React.Component {

    wrapperRef = React.createRef();


    getSnapshotBeforeUpdate(prevProps, prevState) {
        const list = this.wrapperRef.current;
        return list.scrollHeight - list.scrollTop;
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        // If we have a snapshot value, we've just added new items.
        // Adjust scroll so these new items don't push the old ones out of view.
        // (snapshot here is the value returned from getSnapshotBeforeUpdate)
        if (snapshot !== null) {
            const wrapper = this.wrapperRef.current;
            wrapper.scrollTop = wrapper.scrollHeight - snapshot;
        }
    }

    render() {

        const { chatHistory } = this.props;

        return (
            <div
                ref={ this.wrapperRef }
                className="wrapper pl-3"
            >
            <pre>
            {
                chatHistory.data.map((e, idx) => (
                    <ChatEntry
                        key={ idx }
                        entry={ e }
                    />
                ))
            }
            </pre>
            </div>
        );
    }
}



const Chatbar = fnObserver(({}) => {

    const [ text, setText ] = useState("");

    const [ open, setOpen ] = useState(false);
    const [ mention, setMention ] = useState(null);

    const inputRef = useRef(null);

    const { chatHistory } = config;

    useEffect(
        () => Hub.register("SYSTEM", msg => addChatMessage(msg.text)),
        []
    );

    useEffect(
        () => Hub.register("CHAT", msg => addChatMessage(msg.text, msg.source)),
        []
    );

    useEffect(
        () => {

            const { data } = chatHistory;

            let newMention;
            for (let i = data.length - 1; i >= 0; i--)
            {
                const entry = data[i];
                if (entry.message.text.indexOf("@" + config.auth.login) >= 0)
                {
                    newMention = entry;
                    break;
                }
            }

            if (newMention)
            {
                setMention(newMention);
            }
        },
        [chatHistory.data.length]
    );


    const toggleOpen = () => {
        if (open)
        {
            setOpen(false);
        }
        else
        {
            setOpen(true);
            inputRef.current.focus();
            if (mention)
            {
                setMention("");
            }
        }
    };

    return (
        <div
            className={
                cx(
                    "automatontemplate-chatbar",
                    open ? "open" : "closed"
                )
            }
        >
            <button
                id="chat-button"
                type="button"
                className="btn rounded rounded-pill"
                onClick={ toggleOpen }
            >
                <i className="fa-2x fa fa-users"/>
            </button>
            <Popover isOpen={ !open && !!mention } trigger="legacy" placement="left" target="chat-button">
                <PopoverHeader>Message</PopoverHeader>
                <PopoverBody>
                    <ChatEntry
                        entry={ mention }
                    />
                </PopoverBody>
            </Popover>

            <ChatWindow
                text={ text }
                setText={ setText }
                chatHistory={ chatHistory }
            />
            <form className="form-inline" onSubmit={ ev => {
                ev.preventDefault();
                const source = config.auth.login;

                Hub.send("CHAT", {
                    message: text,
                    source
                });

                addChatMessage(text, source);
                setText("")
            }}>
                <label
                    className="sr-only"
                    htmlFor="chatField">
                    Chat Message Field
                </label>
                <input
                    id="chatField"
                    ref={ inputRef }
                    type="text"
                    className="form-control form-control-sm m-3"
                    value={ text }
                    size={ 30 }
                    onChange={ ev => setText(ev.target.value) }
                    placeholder="Enter Message"
                />
            </form>

        </div>
    );
});

Chatbar.displayName = "Sidebar";

export default Chatbar;
