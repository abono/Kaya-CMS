import React, { useState, useReducer, useEffect, useContext } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import { Link } from 'react-router-dom';

import { APICallInit, APICallState } from "../services/ServiceUtil";
import { SearchWebPageTemplate, DeleteWebPageTemplate } from "../services/WebPageTemplateService";

function WebPageTemplateList() {

    const [ errorMessage, setErrorMessage ] = useState('');
    const [ webPageTemplates, setWebPageTemplates ] = useState( [ ] );
    const [ searchState, searchDispatch ] = useReducer(APICallState, APICallInit);

    useEffect(() => {
        SearchWebPageTemplate(1, 50, searchDispatch);
    }, []);

    useEffect(() => {
        setWebPageTemplates(searchState.data && searchState.data.items ? searchState.data.items : [ ]);
    }, [searchState]);

    const deleteWebPageTemplate = (webPageTemplate) => {
        if (window.confirm("Are you sure you want to delete " + webPageTemplate.name)) {
            // DeleteWebPageTemplate(webPageTemplate.webPageTemplateId, (action) => {
            //     if (action.type === 'FETCH_SUCCESS') {
            //         SearchWebPageTemplate(1, 50, searchDispatch);
            //     }
            // });
        }
    };

    const webPageTemplateList = webPageTemplates.map(webPageTemplate => <tr key={webPageTemplate.webPageTemplateId}>
            <td style={{whiteSpace: 'nowrap'}}>{webPageTemplate.name}</td>
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
                <Button color="success" tag={Link} to={`/webPageTemplate/new`}>Add Template</Button>
            </div>
            <h3>Templates</h3>
            <Table className="mt-4">
                <thead>
                <tr>
                    <th width="30%">Name</th>
                    <th width="40%">Actions</th>
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