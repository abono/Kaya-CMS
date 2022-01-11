import React, { useReducer, useContext, useEffect } from 'react';
import {Navbar, NavbarBrand} from 'reactstrap';
import {Link} from 'react-router-dom';

import { UserContext } from '../context/UserContext'

import { APICallInit, APICallState } from "../services/ServiceUtil";
import { LogOut } from "../services/AuthService";

function AppNavbar() {
    const [ userContext, userContextDispatch ] = useContext(UserContext);
    const [ userState, userDispatch ] = useReducer(APICallState, APICallInit);

    const logOut = () => {
        LogOut(userDispatch);
    }

    useEffect(() => {
        !userState.data && userContextDispatch( { type: "LOGGED_OUT" });
    }, [userState, userContextDispatch]);

    return (
        <Navbar color="dark" dark expand="md">
            <NavbarBrand tag={Link} to={`/`}>Home</NavbarBrand>
            {userContext.user && (
                <div style={{width: "100%"}}>
                    <NavbarBrand style={{float: "left"}} tag={Link} to={`/adminUser`}>Admin Users</NavbarBrand>
                    <NavbarBrand style={{float: "left"}} tag={Link} to={`/webPageTemplate`}>Templates</NavbarBrand>
                    <NavbarBrand style={{float: "left"}} tag={Link} to={`/webPage`}>Pages</NavbarBrand>
                    {/* <NavbarBrand style={{float: "left"}} tag={Link} to={`/media`}>Files</NavbarBrand> */}
                    {/* <NavbarBrand style={{float: "left"}} tag={Link} to={`/redirect`}>Redirects</NavbarBrand> */}
                    <NavbarBrand style={{float: "left"}} tag={Link} to={`/publish`}>Publish Changes</NavbarBrand>
                    <NavbarBrand style={{float: "right"}} onClick={logOut}>Log Out</NavbarBrand>
                    <NavbarBrand style={{float: "right"}}>Welcome {userContext.user.firstName}</NavbarBrand>
                </div>
            )}
        </Navbar>
    );
}

export default AppNavbar;
