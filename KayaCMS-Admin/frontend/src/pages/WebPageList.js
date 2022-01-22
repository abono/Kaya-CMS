import React, { useState, useReducer, useEffect, useContext } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import { Link } from 'react-router-dom';

import { UserContext } from '../context/UserContext'

import { APICallInit, APICallState } from "../services/ServiceUtil";
import { SearchWebPage, DeleteWebPage } from "../services/WebPageService";

function WebPageList() {
    const [ , userContextDispatch ] = useContext(UserContext);

    const [ webPages, setWebPages ] = useState( [ ] );
    const [ searchState, searchDispatch ] = useReducer(APICallState, APICallInit);

    useEffect(() => {
        userContextDispatch( { type: "ALERT_MESSAGE", payload: "Loading pages" });
        SearchWebPage(1, 50, searchDispatch);
    }, []);

    useEffect(() => {
        if (searchState.isError) {
            userContextDispatch( { type: "ALERT_ERROR", payload: searchState.errorMessage });
        } else if (searchState.data) {
            userContextDispatch( { type: "ALERT_CLOSE" });
        }
        setWebPages(searchState.data && searchState.data.items ? searchState.data.items : [ ]);
    }, [searchState]);

    const deleteWebPage = (webPage) => {
        userContextDispatch( { type: "ALERT_CLOSE" });
        if (window.confirm("Are you sure you want to delete " + webPage.path)) {
            // DeleteWebPage(webPage.webPageId, (action) => {
            //     if (action.type === 'FETCH_SUCCESS') {
            //         SearchWebPage(1, 50, searchDispatch);
            //     }
            // });
        }
    };

    const webPageList = webPages.map(webPage => <tr key={webPage.webPageId}>
            <td>{webPage.type}{webPage.edited ? ' (' + webPage.typeEdits + ')' : ''}</td>
            <td>{webPage.path}{webPage.edited ? ' (' + webPage.pathEdits + ')' : ''}</td>
            <td>{webPage.title}{webPage.edited ? ' (' + webPage.titleEdits + ')' : ''}</td>
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