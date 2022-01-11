import React, { useState, useReducer, useEffect, useContext } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import { Link } from 'react-router-dom';

import { APICallInit, APICallState } from "../services/ServiceUtil";
import { SearchMedia, DeleteMedia } from "../services/MediaService";

function MediaList() {

    const [ errorMessage, setErrorMessage ] = useState('');
    const [ medias, setMedias ] = useState( [ ] );
    const [ searchState, searchDispatch ] = useReducer(APICallState, APICallInit);

    useEffect(() => {
        SearchMedia(1, 50, searchDispatch);
    }, []);

    useEffect(() => {
        setMedias(searchState.data && searchState.data.items ? searchState.data.items : [ ]);
    }, [searchState]);

    const deleteMedia = (media) => {
        if (window.confirm("Are you sure you want to delete " + media.path)) {
            // DeleteMedia(media.mediaId, (action) => {
            //     if (action.type === 'FETCH_SUCCESS') {
            //         SearchMedia(1, 50, searchDispatch);
            //     }
            // });
        }
    };

    const mediaList = medias.map(media => <tr key={media.mediaId}>
            <td>{media.type}</td>
            <td>{media.path}</td>
            <td>{media.createDate}</td>
            <td>{media.modifyDate}</td>
            <td>{media.pulishDate}</td>
            <td>{media.edited || ''}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={`/media/${media.mediaId}`}>Edit</Button>
                    <Button size="sm" color="danger" onClick={() => deleteMedia(media)}>Delete</Button>
                </ButtonGroup>
            </td>
        </tr>
    );

    return <div>
        <Container fluid>

            {errorMessage &&
                <div className="alert alert-danger" role="alert">
                    {errorMessage}
                </div>
            }

            {searchState.isLoading &&
                <div className="alert alert-warning" role="alert">
                    Loading...
                </div>
            }

            {searchState.isError &&
                <div className="alert alert-danger" role="alert">
                    {searchState.errorMessage}
                </div>
            }

            <div className="float-right">
                <Button color="success" tag={Link} to={`/media/new`}>Add File</Button>
            </div>
            <h3>Files</h3>
            <Table className="mt-4">
                <thead>
                <tr>
                    <th>Type</th>
                    <th>Path</th>
                    <th>Created On</th>
                    <th>Last Modified</th>
                    <th>Pulished</th>
                    <th>Has Unpulished Edits</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                    {mediaList}
                </tbody>
            </Table>
        </Container>
    </div>
}

export default MediaList;