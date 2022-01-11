import React, { useState, useReducer, useEffect, createRef } from 'react';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import { Link, useParams, useNavigate } from 'react-router-dom';

import { Editor } from '@tinymce/tinymce-react';

import { APICallInit, APICallState } from "../services/ServiceUtil";
import { GetWebPage, CreateWebPage, UpdateWebPage } from "../services/WebPageService";

function WebPageEdit() {
    const { id } = useParams();
    const isNew = isNaN(id);

    const navigate = useNavigate();

    const [ readState, readDispatch ] = useReducer(APICallState, APICallInit);
    const [ writeState, writeDispatch ] = useReducer(APICallState, APICallInit);
    const [ errors, setErrors ] = useState( [ ] );
    const [ path, setPath ] = useState('');
    const [ title, setTitle ] = useState('');
    const [ description, setDescription ] = useState('');
    const [ content, setContent ] = useState('');

    const editorRef = createRef(null);

    useEffect(() => {
        if (!isNew) {
            GetWebPage(id, readDispatch);
        }
    }, [id, isNew]);

    useEffect(() => {
        console.log("State changed", readState.data);
        if (readState && readState.data) {
            setPath(readState.data.path);
            setTitle(readState.data.title);
            setDescription(readState.data.description);
            setContent(readState.data.content);
        }
    }, [readState]);

    useEffect(() => {
        if (writeState.data && !writeState.isError) {
            navigate("/webPage");
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
        setErrors(err);
        if (err.length === 0) {
            const webPage = {
                path: path,
                title: title,
                description: description,
                content: content,
            };
            if (isNew) {
                CreateWebPage(webPage, writeDispatch);
            } else {
                webPage.webPageId = id;
                UpdateWebPage(webPage, writeDispatch);
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
                        initialValue={content}
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