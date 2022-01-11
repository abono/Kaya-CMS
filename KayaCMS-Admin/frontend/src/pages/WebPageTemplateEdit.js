import React, { useState, useReducer, useEffect } from 'react';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import { Link, useParams, useNavigate } from 'react-router-dom';

import { APICallInit, APICallState } from "../services/ServiceUtil";
import { GetWebPageTemplate, CreateWebPageTemplate, UpdateWebPageTemplate } from "../services/WebPageTemplateService";

const DEFAULT_NEW_TEMPLATE = <html>
    <head>
        <title>&#123;webPage.title&#125;</title>
    </head>
    <body>
        &#123;webPage.content&#125;
    </body>
</html>

function WebPageTemplateEdit() {
    const { id } = useParams();
    const isNew = isNaN(id);

    const navigate = useNavigate();

    const [ readState, readDispatch ] = useReducer(APICallState, APICallInit);
    const [ writeState, writeDispatch ] = useReducer(APICallState, APICallInit);
    const [ errors, setErrors ] = useState( [ ] );
    const [ name, setName ] = useState('');
    const [ content, setContent ] = useState(isNew ? DEFAULT_NEW_TEMPLATE : '');

    useEffect(() => {
        if (!isNew) {
            GetWebPageTemplate(id, readDispatch);
        }
    }, [id, isNew]);

    useEffect(() => {
        console.log("State changed", readState.data);
        if (readState && readState.data) {
            setName(readState.data.name);
            setContent(readState.data.content);
        }
    }, [readState]);

    useEffect(() => {
        if (writeState.data && !writeState.isError) {
            navigate("/webPageTemplate");
        }
    }, [writeState]);

    const updateName = (event) => {
        setName(event.target.value);
    }

    const updateContent = (event) => {
        setContent(event.target.value);
    }

    const handleSubmit = (event) => {
        event.preventDefault();

        var err = [];
        if (name.trim() === '') {
            // Required field
            err.push('Please enter a name');
        }
        setErrors(err);
        if (err.length === 0) {
            const webPageTemplate = {
                name: name,
                content: content,
            };
            if (isNew) {
                CreateWebPageTemplate(webPageTemplate, writeDispatch);
            } else {
                webPageTemplate.webPageTemplateId = id;
                UpdateWebPageTemplate(webPageTemplate, writeDispatch);
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
                    <Label for="name">Name</Label>
                    <Input type="text" name="name" id="name" value={name}
                            onChange={updateName} autoComplete="name"/>
                </FormGroup>
                <FormGroup>
                    <Label for="email">Template</Label>
                    <Input type="textarea" name="content" id="content"
                            onChange={updateContent} class="form-control" rows="20">{content}</Input>
                </FormGroup>
                <FormGroup>
                    <Button color="primary" type="submit">Save</Button>{' '}
                    <Button color="secondary" tag={Link} to="/webPageTemplate">Cancel</Button>
                </FormGroup>
            </Form>
        </Container>
    </div>
}

export default WebPageTemplateEdit;