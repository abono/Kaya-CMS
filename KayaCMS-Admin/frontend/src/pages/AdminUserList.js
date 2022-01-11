import React, { useState, useReducer, useEffect, useContext } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import { Link } from 'react-router-dom';
import Modal from "react-bootstrap/Modal";

import { UserContext } from '../context/UserContext'

import { APICallInit, APICallState } from "../services/ServiceUtil";
import { SearchAdminUser, DeleteAdminUser } from "../services/AdminUserService";

function AdminUserList() {
    const [ userContext, ] = useContext(UserContext);

    const [ closeModal, setCloseModal ] = useState(true);
    const [ errorMessage, setErrorMessage ] = useState('');
    const [ adminUsers, setAdminUsers ] = useState( [ ] );
    const [ searchState, searchDispatch ] = useReducer(APICallState, APICallInit);

    useEffect(() => {
        setCloseModal(false);
        SearchAdminUser(1, 50, searchDispatch);
    }, []);

    useEffect(() => {
        if (!searchState.isError) {
            setCloseModal(false);
        }
        setAdminUsers(searchState.data && searchState.data.items ? searchState.data.items : [ ]);
    }, [searchState]);

    const deleteAdminUser = (adminUser) => {
        if (adminUser.adminUserId === userContext.user.adminUserId) {
            setCloseModal(false);
            setErrorMessage("You can not delete your own account.");
        } else {
            if (window.confirm("Are you sure you want to delete " + adminUser.firstName + " " + adminUser.lastName)) {
                setCloseModal(false);
                DeleteAdminUser(adminUser.adminUserId, (action) => {
                    if (action.type === 'FETCH_SUCCESS') {
                        SearchAdminUser(1, 50, searchDispatch);
                    }
                });
            }
        }
    };

    const adminUserList = adminUsers.map(adminUser => <tr key={adminUser.adminUserId}>
            <td style={{whiteSpace: 'nowrap'}}>{adminUser.firstName} {adminUser.lastName}</td>
            <td>{adminUser.email}</td>
            <td>{adminUser.userName}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={`/adminUser/${adminUser.adminUserId}`}>Edit</Button>
                    <Button size="sm" color="danger" onClick={() => deleteAdminUser(adminUser)}>Delete</Button>
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

            <Modal show={!closeModal && searchState.isLoading}>
                <Modal.Header>Loading</Modal.Header>
                <Modal.Body>Loading data ...</Modal.Body>
                <Modal.Footer><Button size="sm" color="secondary" onClick={() => setCloseModal(true)}>Close</Button></Modal.Footer>
            </Modal>

            {searchState.isError &&
                <div className="alert alert-danger" role="alert">
                    {searchState.errorMessage}
                </div>
            }

            <div className="float-right">
                <Button color="success" tag={Link} to={`/adminUser/new`}>Add Admin User</Button>
            </div>
            <h3>Admin Users</h3>
            <Table className="mt-4">
                <thead>
                <tr>
                    <th width="20%">Name</th>
                    <th width="40%">Email</th>
                    <th width="20%">User Name</th>
                    <th width="20%">Action</th>
                </tr>
                </thead>
                <tbody>
                    {adminUserList}
                </tbody>
            </Table>
        </Container>
    </div>
}

export default AdminUserList;