import React, { useState, useReducer, useEffect, useContext } from 'react';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import { Link, useParams, useNavigate } from 'react-router-dom';

import { UserContext } from '../context/UserContext'

import { APICallInit, APICallState } from "../services/ServiceUtil";
import { GetWebPageTemplate, CreateWebPageTemplate, UpdateWebPageTemplate } from "../services/WebPageTemplateService";

const DEFAULT_NEW_TEMPLATE = "<html>\n\n"
    + "<head>\n"
    + "    <title>${webPage.title}</title>\n"
    + "</head>\n\n"
    + "<body>\n"
    + "    ${webPage.content}\n"
    + "</body>\n\n"
    + "</html>\n";

function WebPageTemplateEdit() {
    const { id } = useParams();
    const isNew = isNaN(id);

    const navigate = useNavigate();

    const [ , userContextDispatch ] = useContext(UserContext);

    const [ readState, readDispatch ] = useReducer(APICallState, APICallInit);
    const [ writeState, writeDispatch ] = useReducer(APICallState, APICallInit);

    const [ name, setName ] = useState('');
    const [ content, setContent ] = useState(isNew ? DEFAULT_NEW_TEMPLATE : '');

    useEffect(() => {
        if (!isNew) {
            userContextDispatch( { type: "ALERT_MESSAGE", payload: "Loading template" });
            GetWebPageTemplate(id, readDispatch);
        }
    }, [id, isNew]);

    useEffect(() => {
        console.log("State changed", readState.data);
        if (readState && readState.data) {
            userContextDispatch( { type: "ALERT_CLOSE" });

            if (readState.data.edited) {
                setName(readState.data.nameEdits);
                setContent(readState.data.contentEdits);
            } else {
                setName(readState.data.name);
                setContent(readState.data.content);
            }
        }
        if (readState.isError) {
            userContextDispatch( { type: "ALERT_ERROR", payload: readState.errorMessage });
        }
    }, [readState]);

    useEffect(() => {
        if (writeState.data && !writeState.isError) {
            navigate("/webPageTemplate");
        }
        if (writeState.isError) {
            userContextDispatch( { type: "ALERT_ERROR", payload: writeState.errorMessage });
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
        if (err.length === 0) {
            const webPageTemplate = {
                nameEdits: name,
                contentEdits: content,
            };
            if (isNew) {
                userContextDispatch( { type: "ALERT_MESSAGE", payload: "Creating new template" } );
                CreateWebPageTemplate(webPageTemplate, writeDispatch);
            } else {
                webPageTemplate.webPageTemplateId = id;
                userContextDispatch( { type: "ALERT_MESSAGE", payload: "Updating template" } );
                UpdateWebPageTemplate(webPageTemplate, writeDispatch);
            }
        } else {
            userContextDispatch( { type: "ALERT_ERROR", payload: err.map((error, index) => 
                <div key={index}>{error}</div>
            ) } );
        }
    }

    return <div>
        <Container>
            <Form onSubmit={handleSubmit}>
                <FormGroup>
                    <Label for="name">Name</Label>
                    <Input type="text" name="name" id="name" value={name}
                            onChange={updateName} autoComplete="name"/>
                </FormGroup>
                <FormGroup>
                    <Label for="email">Template</Label>
                    <Input type="textarea" name="content" id="content" value={content}
                            onChange={updateContent} className="form-control" rows="20" />
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