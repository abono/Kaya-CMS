import React, { useState, useReducer, useEffect, useContext } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import { Link } from 'react-router-dom';

import { APICallInit, APICallState } from "../services/ServiceUtil";
import { SearchRedirect, DeleteRedirect } from "../services/RedirectService";

function RedirectList() {

    const [ errorMessage, setErrorMessage ] = useState('');
    const [ redirects, setRedirects ] = useState( [ ] );
    const [ searchState, searchDispatch ] = useReducer(APICallState, APICallInit);

    useEffect(() => {
        SearchRedirect(1, 50, searchDispatch);
    }, []);

    useEffect(() => {
        setRedirects(searchState.data && searchState.data.items ? searchState.data.items : [ ]);
    }, [searchState]);

    const deleteRedirect = (redirect) => {
        if (window.confirm("Are you sure you want to delete\n    " + redirect.fromPath + "\n    -> " + redirect.toPath)) {
            // DeleteRedirect(redirect.redirectId, (action) => {
            //     if (action.type === 'FETCH_SUCCESS') {
            //         SearchRedirect(1, 50, searchDispatch);
            //     }
            // });
        }
    };

    const redirectList = redirects.map(redirect => <tr key={redirect.redirectId}>
            <td>{redirect.fromPath}</td>
            <td>{redirect.toPath}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={`/redirect/${redirect.redirectId}`}>Edit</Button>
                    <Button size="sm" color="danger" onClick={() => deleteRedirect(redirect)}>Delete</Button>
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
                <Button color="success" tag={Link} to={`/redirect/new`}>Add Redirect</Button>
            </div>
            <h3>Redirects</h3>
            <Table className="mt-4">
                <thead>
                <tr>
                    <th>From</th>
                    <th>To</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                    {redirectList}
                </tbody>
            </Table>
        </Container>
    </div>
}

export default RedirectList;