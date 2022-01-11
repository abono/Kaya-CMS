import React, { useState, useReducer, useEffect } from 'react';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import { Link, useParams, useNavigate } from 'react-router-dom';

import { APICallInit, APICallState } from "../services/ServiceUtil";
import { GetRedirect, CreateRedirect, UpdateRedirect } from "../services/RedirectService";

function RedirectEdit() {
    const { id } = useParams();
    const isNew = isNaN(id);

    const navigate = useNavigate();

    const [ readState, readDispatch ] = useReducer(APICallState, APICallInit);
    const [ writeState, writeDispatch ] = useReducer(APICallState, APICallInit);
    const [ errors, setErrors ] = useState( [ ] );
    const [ fromPath, setFromPath ] = useState('');
    const [ toPath, setToPath ] = useState('');

    useEffect(() => {
        if (!isNew) {
            GetRedirect(id, readDispatch);
        }
    }, [id, isNew]);

    useEffect(() => {
        console.log("State changed", readState.data);
        if (readState && readState.data) {
            setFromPath(readState.data.fromPath);
            setToPath(readState.data.toPath);
        }
    }, [readState]);

    useEffect(() => {
        if (writeState.data && !writeState.isError) {
            navigate("/redirect");
        }
    }, [writeState]);

    const updateFromPath = (event) => {
        setFromPath(event.target.value);
    }

    const updateToPath = (event) => {
        setToPath(event.target.value);
    }

    const handleSubmit = (event) => {
        event.preventDefault();

        var err = [];
        if (fromPath.trim() === '') {
            // Required field
            err.push('Please enter a from path');
        }
        if (toPath.trim() === '') {
            // Required field
            err.push('Please enter a to path');
        }
        setErrors(err);
        if (err.length === 0) {
            const redirect = {
                fromPath: fromPath,
                toPath: toPath,
            };
            if (isNew) {
                CreateRedirect(redirect, writeDispatch);
            } else {
                redirect.redirectId = id;
                UpdateRedirect(redirect, writeDispatch);
            }
        }
    }

    return <div>
        <Container>

            {readState.isLoading &&
                <div className="alert alert-warning" role="alert">
                    Loading...
                </div>
            }

            {readState.isError &&
                <div className="alert alert-danger" role="alert">
                    {readState.errorMessage}
                </div>
            }

            {writeState.isLoading &&
                <div className="alert alert-warning" role="alert">
                    Saving...
                </div>
            }

            {writeState.isError &&
                <div className="alert alert-danger" role="alert">
                    {writeState.errorMessage}
                </div>
            }

            {errors.map((error, index) =>
                <div key={index} className="alert alert-danger" role="alert">
                    {error}
                </div>
            )}

            <Form onSubmit={handleSubmit}>
                <FormGroup>
                    <Label for="fromPath">From</Label>
                    <Input type="text" name="fromPath" id="fromPath" value={fromPath}
                            onChange={updateFromPath} autoComplete="fromPath"/>
                </FormGroup>
                <FormGroup>
                    <Label for="toPath">To</Label>
                    <Input type="text" name="toPath" id="toPath" value={toPath}
                            onChange={updateToPath} autoComplete="toPath"/>
                </FormGroup>
                <FormGroup>
                    <Button color="primary" type="submit">Save</Button>{' '}
                    <Button color="secondary" tag={Link} to="/redirect">Cancel</Button>
                </FormGroup>
            </Form>
        </Container>
    </div>
}

export default RedirectEdit;