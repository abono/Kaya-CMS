import React, { useState, useReducer, useEffect, createRef, useContext } from 'react';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import Select from 'react-select';
import { Link, useParams, useNavigate } from 'react-router-dom';

import { Editor } from '@tinymce/tinymce-react';

import { UserContext } from '../context/UserContext'

import { APICallInit, APICallState } from "../services/ServiceUtil";
import { SearchWebPageTemplate } from "../services/WebPageTemplateService";
import { GetWebPage, CreateWebPage, UpdateWebPage } from "../services/WebPageService";

function WebPageEdit() {
    const { id } = useParams();
    const isNew = isNaN(id);

    const navigate = useNavigate();

    const [ , userContextDispatch ] = useContext(UserContext);

    const [ readState, readDispatch ] = useReducer(APICallState, APICallInit);
    const [ writeState, writeDispatch ] = useReducer(APICallState, APICallInit);

    const [ readWebPageTemplatesState, readWebPageTemplatesDispatch ] = useReducer(APICallState, APICallInit);
    const [ webPageTemplates, setWebPageTemplates ] = useState( [ ] );
    
    const [ webPageTemplateId, setWebPageTemplateId ] = useState();
    const [ type, setType ] = useState('CONTENT');
    const [ path, setPath ] = useState('');
    const [ title, setTitle ] = useState('');
    const [ description, setDescription ] = useState('');
    const [ content, setContent ] = useState('');
    const [ parameters, setParameters ] = useState('{}');

    const editorRef = createRef(null);

    useEffect(() => {
        SearchWebPageTemplate(1, 100, readWebPageTemplatesDispatch);
        if (!isNew) {
            userContextDispatch( { type: "ALERT_MESSAGE", payload: "Loading template" });
            GetWebPage(id, readDispatch);
        }
    }, [id, isNew]);

    useEffect(() => {
        console.log("Processing web page templates", readWebPageTemplatesState);
        if (readWebPageTemplatesState.data && readWebPageTemplatesState.data.items) {
            console.log("Found items", readWebPageTemplatesState.data.items);
            setWebPageTemplates(readWebPageTemplatesState.data.items.map(item => {
                return {
                    value: item.webPageTemplateId,
                    label: item.name
                };
            }));
        }
    }, [readWebPageTemplatesState]);

    useEffect(() => {
        console.log("State changed", readState.data);
        if (readState && readState.data) {
            userContextDispatch( { type: "ALERT_CLOSE" });

            if (readState.data.edited) {
                setWebPageTemplateId({value: readState.data.webPageTemplateIdEdits});
                setType(readState.data.typeEdits);
                setPath(readState.data.pathEdits);
                setTitle(readState.data.titleEdits);
                setDescription(readState.data.descriptionEdits);
                setContent(readState.data.contentEdits);
                setParameters(readState.data.parametersEdits);
            } else {
                setWebPageTemplateId({value: readState.data.webPageTemplateId});
                setType(readState.data.type);
                setPath(readState.data.path);
                setTitle(readState.data.title);
                setDescription(readState.data.description);
                setContent(readState.data.content);
                setParameters(readState.data.parameters);
            }
        }
        if (readState.isError) {
            userContextDispatch( { type: "ALERT_ERROR", payload: readState.errorMessage });
        }
    }, [readState]);

    useEffect(() => {
        if (writeState.data && !writeState.isError) {
            navigate("/webPage");
        }
        if (writeState.isError) {
            userContextDispatch( { type: "ALERT_ERROR", payload: writeState.errorMessage });
        }
    }, [writeState]);

    const updatePath = (event) => {
        setPath(event.target.value);
    }

    const updateTitle = (event) => {
        setTitle(event.target.value);
    }

    const updateDescription = (event) => {
        setDescription(event.target.value);
    }

    const updateContent = (value) => {
        setContent(value);
    }

    const handleSubmit = (event) => {
        event.preventDefault();

        var err = [];
        if (path.trim() === '') {
            // Required field
            err.push('Please provide a path to your page');
        } else if (!path.endsWith('.html')) {
            err.push('Your path must end with a ".html" (case sensitive).');
        }
        if (title.trim() === '') {
            // Required field
            err.push('Please provide a title for the page');
        }
        if (description.trim() === '') {
            // Required field
            err.push('Please provide a description for your page');
        }
        if (err.length === 0) {
            const webPage = {
                webPageTemplateIdEdits: webPageTemplateId.value,
                typeEdits: type,
                pathEdits: path,
                titleEdits: title,
                descriptionEdits: description,
                contentEdits: content,
                parametersEdits: parameters,
            };
            if (isNew) {
                userContextDispatch( { type: "ALERT_MESSAGE", payload: "Creating new page" } );
                CreateWebPage(webPage, writeDispatch);
            } else {
                webPage.webPageId = id;
                userContextDispatch( { type: "ALERT_MESSAGE", payload: "Updating page" } );
                UpdateWebPage(webPage, writeDispatch);
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
                    <Label for="webPageTemplate">Template</Label>
                    <Select defaultValue={webPageTemplateId}
                            onChange={setWebPageTemplateId}
                            options={webPageTemplates} />
                </FormGroup>
                <FormGroup>
                    <Label for="path">Path</Label>
                    <Input type="text" name="path" id="path" value={path}
                            onChange={updatePath} autoComplete="path"/>
                </FormGroup>
                <FormGroup>
                    <Label for="title">Title</Label>
                    <Input type="text" name="title" id="title" value={title}
                            onChange={updateTitle} autoComplete="title"/>
                </FormGroup>
                <FormGroup>
                    <Label for="description">Description</Label>
                    <Input type="text" name="description" id="description" value={description}
                            onChange={updateDescription} autoComplete="description"/>
                </FormGroup>
                <FormGroup>
                    <Label for="content">Content</Label>
                    <Editor
                        onInit={(evt, editor) => editorRef.current = editor}
                        value={content}
                        init={{
                            height: 500,
                            menubar: true,
                            plugins: 
                                'advlist autolink lists link image charmap print preview anchor ' +
                                'searchreplace visualblocks code fullscreen ' +
                                'insertdatetime media table contextmenu paste code help wordcount'
                            ,
                            toolbar:
                                'insertfile undo redo | ' +
                                'styleselect | ' +
                                'bold italic backcolor | ' +
                                'alignleft aligncenter alignright alignjustify | ' +
                                'bullist numlist outdent indent | ' +
                                'link image | ' +
                                'removeformat | ' +
                                'help',
                            content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:14px }'
                        }}
                        onEditorChange={updateContent}
                    />
                </FormGroup>
                <FormGroup>
                    <Button color="primary" type="submit">Save</Button>{' '}
                    <Button color="secondary" tag={Link} to="/webPage">Cancel</Button>
                </FormGroup>
            </Form>
        </Container>
    </div>
}

export default WebPageEdit;