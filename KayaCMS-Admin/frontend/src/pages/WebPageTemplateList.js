import React, { useState, useReducer, useEffect, useContext } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import { Link } from 'react-router-dom';

import { UserContext } from '../context/UserContext'

import { APICallInit, APICallState } from "../services/ServiceUtil";
import { SearchWebPageTemplate, DeleteWebPageTemplate } from "../services/WebPageTemplateService";

function WebPageTemplateList() {
    const [ , userContextDispatch ] = useContext(UserContext);

    const [ webPageTemplates, setWebPageTemplates ] = useState( [ ] );
    const [ searchState, searchDispatch ] = useReducer(APICallState, APICallInit);

    useEffect(() => {
        userContextDispatch( { type: "ALERT_MESSAGE", payload: "Loading templates" });
        SearchWebPageTemplate(1, 50, searchDispatch);
    }, []);

    useEffect(() => {
        if (searchState.isError) {
            userContextDispatch( { type: "ALERT_ERROR", payload: searchState.errorMessage });
        } else if (searchState.data) {
            userContextDispatch( { type: "ALERT_CLOSE" });
        }
        setWebPageTemplates(searchState.data && searchState.data.items ? searchState.data.items : [ ]);
    }, [searchState]);

    const deleteWebPageTemplate = (webPageTemplate) => {
        userContextDispatch( { type: "ALERT_CLOSE" });
        if (window.confirm("Are you sure you want to delete " + webPageTemplate.name)) {
            // DeleteWebPageTemplate(webPageTemplate.webPageTemplateId, (action) => {
            //     if (action.type === 'FETCH_SUCCESS') {
            //         SearchWebPageTemplate(1, 50, searchDispatch);
            //     }
            // });
        }
    };

    const webPageTemplateList = webPageTemplates.map(webPageTemplate => <tr key={webPageTemplate.webPageTemplateId}>
            <td>{webPageTemplate.name}{webPageTemplate.edited ? ' (' + webPageTemplate.nameEdits + ')' : ''}</td>
            <td>{webPageTemplate.edited ? "YES" : ""}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={`/webPageTemplate/${webPageTemplate.webPageTemplateId}`}>Edit</Button>
                    <Button size="sm" color="danger" onClick={() => deleteWebPageTemplate(webPageTemplate)}>Delete</Button>
                </ButtonGroup>
            </td>
        </tr>
    );

    return <div>
        <Container fluid>
            <div className="float-right">
                <Button color="success" tag={Link} to={`/webPageTemplate/new`}>Add Template</Button>
            </div>
            <h3>Templates</h3>
            <Table className="mt-4">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Contains Edits</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                    {webPageTemplateList}
                </tbody>
            </Table>
        </Container>
    </div>
}

export default WebPageTemplateList;