import React, { useCallback, useReducer, useState } from "react"

import { LogoutForm, runProcessURI } from "@quinscape/automaton-js"

import { Collapse, Container, Navbar, NavbarBrand, NavbarToggler } from "reactstrap"

import Nav from "reactstrap/lib/Nav";
import Dropdown from "reactstrap/lib/Dropdown";
import DropdownToggle from "reactstrap/lib/DropdownToggle";
import DropdownMenu from "reactstrap/lib/DropdownMenu";
import DropdownItem from "reactstrap/lib/DropdownItem";


const Layout = props => {

    const [ isNavExpanded, toggleNav ] = useReducer(isNavExpanded => !isNavExpanded, false);

    const [ dropdownOpen, setDropdownOpen ] = useState(false);

    const toggleDropdown = useCallback(() => setDropdownOpen(!dropdownOpen), [dropdownOpen]);

    const { env, children } = props;

    const { contextPath } = env.config;

    // TODO: replace "myprocess" in menu

    return (
        <Container
            fluid={ false }
        >
            <Navbar
                id="app-navbar"
                color="primary"
                dark
                expand="md"
            >
                <NavbarBrand
                    href={contextPath + "/"}
                >
                    <i className="brand-icon fab fa-quinscape mr-1"/>
                    Automaton-template
                </NavbarBrand>
                <NavbarToggler
                    onClick={ toggleNav }
                />
                <Collapse
                    isOpen={ isNavExpanded }
                    navbar
                >
                    <Nav className="ml-auto" navbar>

                        <Dropdown nav isOpen={ dropdownOpen } toggle={ toggleDropdown }>
                            <DropdownToggle nav caret>
                                Tests
                            </DropdownToggle>
                            <DropdownMenu>
                                <DropdownItem
                                    onClick={
                                        () => runProcessURI("/myapp/myapp")
                                    }
                                >
                                    MyApp Process
                                </DropdownItem>
                            </DropdownMenu>
                        </Dropdown>
                    </Nav>
                </Collapse>
            </Navbar>
            {
                children
            }
            <div className="footer">
                <LogoutForm/>
            </div>
        </Container>
    );
};

export default Layout
