import React, { useState, useReducer, useEffect } from 'react';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import { Link, useParams, useNavigate } from 'react-router-dom';

import { APICallInit, APICallState } from "../services/ServiceUtil";
import { GetMedia, CreateMedia, UpdateMedia } from "../services/MediaService";

function MediaEdit() {
    const { id } = useParams();
    const isNew = isNaN(id);

    const navigate = useNavigate();

    const [ readState, readDispatch ] = useReducer(APICallState, APICallInit);
    const [ writeState, writeDispatch ] = useReducer(APICallState, APICallInit);
    const [ errors, setErrors ] = useState( [ ] );
    const [ path, setPath ] = useState('');
    const [ content, setContent ] = useState('');

    useEffect(() => {
        if (!isNew) {
            GetMedia(id, readDispatch);
        }
    }, [id, isNew]);

    useEffect(() => {
        console.log("State changed", readState.data);
        if (readState && readState.data) {
            setPath(readState.data.path);
            setContent(readState.data.content);
        }
    }, [readState]);

    useEffect(() => {
        if (writeState.data && !writeState.isError) {
            navigate("/media");
        }
    }, [writeState]);

    const updatePath = (event) => {
        setPath(event.target.value);
    }

    const updateContent = (event) => {
        setContent(event.target.value);
    }

    const handleSubmit = (event) => {
        event.preventDefault();

        var err = [];
        if (path.trim() === '') {
            // Required field
            err.push('Please provide a path to your file');
        } else {
            const cut = path.lastIndexOf('.');
            if (cut <= 0 || path.substring(cut + 1).indexOf('/') >= 0) {
                err.push('Your path must end with a file extension so we can identify the file type.');
            }
        }
        setErrors(err);
        if (err.length === 0) {
            const media = {
                path: path,
                content: content,
            };
            if (isNew) {
                CreateMedia(media, writeDispatch);
            } else {
                media.mediaId = id;
                UpdateMedia(media, writeDispatch);
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
                    <Label for="content">Content</Label>
                    <Input type="textarea" name="content" id="content"
                            onChange={updateContent} class="form-control" rows="20">{content}</Input>
                </FormGroup>
                <FormGroup>
                    <Button color="primary" type="submit">Save</Button>{' '}
                    <Button color="secondary" tag={Link} to="/media">Cancel</Button>
                </FormGroup>
            </Form>
        </Container>
    </div>
}

export default MediaEdit;