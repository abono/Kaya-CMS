import React, { useState, useReducer, useEffect, useContext } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import { Link } from 'react-router-dom';

import { APICallInit, APICallState } from "../services/ServiceUtil";
import { SearchWebPage, DeleteWebPage } from "../services/WebPageService";

function WebPageList() {

    const [ errorMessage, setErrorMessage ] = useState('');
    const [ webPages, setWebPages ] = useState( [ ] );
    const [ searchState, searchDispatch ] = useReducer(APICallState, APICallInit);

    useEffect(() => {
        SearchWebPage(1, 50, searchDispatch);
    }, []);

    useEffect(() => {
        setWebPages(searchState.data && searchState.data.items ? searchState.data.items : [ ]);
    }, [searchState]);

    const deleteWebPage = (webPage) => {
        if (window.confirm("Are you sure you want to delete " + webPage.path)) {
            // DeleteWebPage(webPage.webPageId, (action) => {
            //     if (action.type === 'FETCH_SUCCESS') {
            //         SearchWebPage(1, 50, searchDispatch);
            //     }
            // });
        }
    };

    const webPageList = webPages.map(webPage => <tr key={webPage.webPageId}>
            <td>{webPage.type}</td>
            <td>{webPage.path}</td>
            <td>{webPage.title}</td>
            <td>{webPage.createDate}</td>
            <td>{webPage.modifyDate}</td>
            <td>{webPage.pulishDate}</td>
            <td>{webPage.edited || ''}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={`/webPage/${webPage.webPageId}`}>Edit</Button>
                    <Button size="sm" color="danger" onClick={() => deleteWebPage(webPage)}>Delete</Button>
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
                <Button color="success" tag={Link} to={`/webPage/new`}>Add Web Page</Button>
            </div>
            <h3>Web Pages</h3>
            <Table className="mt-4">
                <thead>
                <tr>
                    <th>Type</th>
                    <th>Path</th>
                    <th>Title</th>
                    <th>Created On</th>
                    <th>Last Modified</th>
                    <th>Pulished</th>
                    <th>Has Unpulished Edits</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                    {webPageList}
                </tbody>
            </Table>
        </Container>
    </div>
}

export default WebPageList;