import React, { useState, useContext, useReducer, useEffect } from 'react';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';

import { UserContext } from '../context/UserContext'

import { APICallInit, APICallState } from "../services/ServiceUtil";
import { LogIn } from "../services/AuthService";

function Login() {
    const [ userContext, userContextDispatch ] = useContext(UserContext);
    const [ userState, userDispatch ] = useReducer(APICallState, APICallInit);

    const [ userName, setUserName ] = useState('');
    const [ password, setPassword ] = useState('');

    useEffect(() => {
        console.log("Checking user state changes", userState);
        if (userState.data) {
          userContextDispatch( { type: "LOGGED_IN", payload: userState.data });
        }
        if (userState.isError) {
            userContextDispatch( { type: "ALERT_ERROR", payload: userState.errorMessage });
        }
    }, [userState, userContextDispatch]);

    const updateUserName = (event) => {
        setUserName(event.target.value);
    }

    const updatePassword = (event) => {
        setPassword(event.target.value);
    }

    const handleSubmit = (event) => {
        event.preventDefault();

        var err = [];
        if (userName.trim() === '') {
            err.push('Please provide a user name');
        }
        if (password.trim() === '') {
            err.push('Please enter your password');
        }
        if (err.length === 0) {
            userContextDispatch( { type: "ALERT_MESSAGE", payload: "Logging In" } );
            LogIn(userName, password, userDispatch);
        } else {
            userContextDispatch( { type: "ALERT_ERROR", payload: err.map((error, index) => 
                <div key={index}>{error}</div>
            ) } );
        }
    }

    return <div>
        <Container>
            <h2>Please Log In</h2>

            <Form onSubmit={handleSubmit}>
                <FormGroup>
                    <Label for="name">Username</Label>
                    <Input type="text" name="userName" id="userName" value={userName}
                        onChange={updateUserName}
                        autoComplete="userName"/>
                </FormGroup>
                <FormGroup>
                    <Label for="password">Password</Label>
                    <Input type="text" name="password" id="password" value={password}
                        onChange={updatePassword}
                        autoComplete="password"/>
                </FormGroup>
                <FormGroup>
                    <Button color="primary" type="submit">Log In</Button>
                </FormGroup>
            </Form>
        </Container>
    </div>
}

export default Login;