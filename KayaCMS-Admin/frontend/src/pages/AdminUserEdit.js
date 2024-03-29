import React, { useState, useReducer, useEffect, useContext } from 'react';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import { Link, useParams, useNavigate } from 'react-router-dom';

import { UserContext } from '../context/UserContext'

import { APICallInit, APICallState } from "../services/ServiceUtil";
import { GetAdminUser, CreateAdminUser, UpdateAdminUser } from "../services/AdminUserService";

import { validateEmail, validatePassword, VALID_PASSWORD_DESCRIPTION } from "../util/ValidationUtils"

function AdminUserEdit() {
    const { id } = useParams();
    const isNew = isNaN(id);

    const navigate = useNavigate();

    const [ , userContextDispatch ] = useContext(UserContext);

    const [ readState, readDispatch ] = useReducer(APICallState, APICallInit);
    const [ writeState, writeDispatch ] = useReducer(APICallState, APICallInit);
    
    const [ firstName, setFirstName ] = useState('');
    const [ lastName, setLastName ] = useState('');
    const [ email, setEmail ] = useState('');
    const [ userName, setUserName ] = useState('');
    const [ password, setPassword ] = useState('');
    const [ confirmPassword, setConfirmPassword ] = useState('');

    useEffect(() => {
        if (!isNew) {
            userContextDispatch( { type: "ALERT_MESSAGE", payload: "Loading admin user" });
            GetAdminUser(id, readDispatch);
        }
    }, [id, isNew]);

    useEffect(() => {
        console.log("State changed", readState.data);
        if (readState && readState.data) {
            userContextDispatch( { type: "ALERT_CLOSE" });

            setFirstName(readState.data.firstName);
            setLastName(readState.data.lastName);
            setEmail(readState.data.email);
            setUserName(readState.data.userName);
        }
        if (readState.isError) {
            userContextDispatch( { type: "ALERT_ERROR", payload: readState.errorMessage });
        }
    }, [readState]);

    useEffect(() => {
        if (writeState.data && !writeState.isError) {
            navigate("/adminUser");
        }
        if (writeState.isError) {
            userContextDispatch( { type: "ALERT_ERROR", payload: writeState.errorMessage });
        }
    }, [writeState]);

    const updateFirstName = (event) => {
        setFirstName(event.target.value);
    }

    const updateLastName = (event) => {
        setLastName(event.target.value);
    }

    const updateEmail = (event) => {
        setEmail(event.target.value);
    }

    const updateUserName = (event) => {
        setUserName(event.target.value);
    }

    const updatePassword = (event) => {
        setPassword(event.target.value);
    }

    const updateConfirmPassword = (event) => {
        setConfirmPassword(event.target.value);
    }

    const handleSubmit = (event) => {
        event.preventDefault();

        var err = [];
        if (firstName.trim() === '') {
            // Required field
            err.push('Please enter a first name');
        }
        if (lastName.trim() === '') {
            // Required field
            err.push('Please enter the last name');
        }
        if (email.trim() === '') {
            // Required field
            err.push('Please enter a valid email');
        } else if (!validateEmail(email)) {
            // Make sure it is a valid looking email address.
            err.push('Email is not valid.  Please check and correct.');
        }
        if (userName.trim() === '') {
            // Required field
            err.push('You need to provide a user name');
        }
        if (password !== confirmPassword) {
            // Password and confirm password match
            err.push('Password and confirm password do not match.');
        } else if (isNew) {
            // if new, password provided and adheres to prerequisites
            if (password.trim() === '') {
                err.push('Please provide a password for this new user.');
            } else if (!validatePassword(password)) {
                err.push('Password must ' + VALID_PASSWORD_DESCRIPTION);
            }
        } else {
            // if existing, password can be empty but if not must adhere to prerequisites
            // empty password just means we are NOT changing it - we are leaving it as it is
            if (password.trim() !== '' && !validatePassword(password)) {
                err.push('Password must ' + VALID_PASSWORD_DESCRIPTION);
            }
        }
        if (err.length === 0) {
            const adminUser = {
                firstName: firstName,
                lastName: lastName,
                email: email,
                userName: userName,
            };
            if (isNew) {
                adminUser.password = password;
                userContextDispatch( { type: "ALERT_MESSAGE", payload: "Creating new user" } );
                CreateAdminUser(adminUser, writeDispatch);
            } else {
                adminUser.adminUserId = id;
                if (password.trim() !== '') {
                    adminUser.password = password;
                }
                userContextDispatch( { type: "ALERT_MESSAGE", payload: "Updating user" } );
                UpdateAdminUser(adminUser, writeDispatch);
            }
        } else {
            userContextDispatch( { type: "ALERT_ERROR", payload: err.map((error, index) => 
                <div key={index}>{error}</div>
            ) } );
        }
    }

    return (
        <Container>
            <Form onSubmit={handleSubmit}>
                <FormGroup>
                    <Label for="name">First Name</Label>
                    <Input type="text" name="firstName" id="firstName" value={firstName}
                            onChange={updateFirstName} autoComplete="firstName"/>
                </FormGroup>
                <FormGroup>
                    <Label for="name">Last Name</Label>
                    <Input type="text" name="lastName" id="lastName" value={lastName}
                            onChange={updateLastName} autoComplete="lastName"/>
                </FormGroup>
                <FormGroup>
                    <Label for="email">Email</Label>
                    <Input type="text" name="email" id="email" value={email}
                            onChange={updateEmail} autoComplete="email"/>
                </FormGroup>
                <FormGroup>
                    <Label for="name">User Name</Label>
                    <Input type="text" name="userName" id="userName" value={userName}
                            onChange={updateUserName} autoComplete="userName"/>
                </FormGroup>
                <FormGroup>
                    <Label for="password">Password</Label>
                    <Input type="text" name="password" id="password" value={password}
                            onChange={updatePassword} autoComplete="password"/>
                </FormGroup>
                <FormGroup>
                    <Label for="confirmPassword">Confirm Password</Label>
                    <Input type="text" name="confirmPassword" id="confirmPassword" value={confirmPassword}
                            onChange={updateConfirmPassword} autoComplete="confirmPassword"/>
                </FormGroup>
                <FormGroup>
                    <Button color="primary" type="submit">Save</Button>{' '}
                    <Button color="secondary" tag={Link} to="/adminUser">Cancel</Button>
                </FormGroup>
            </Form>
        </Container>
    );
}

export default AdminUserEdit;