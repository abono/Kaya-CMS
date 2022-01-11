import React, { useReducer, useContext, useEffect } from 'react';
import { Button } from 'reactstrap';
import { HashRouter as Router, Route, Routes } from 'react-router-dom';
import Modal from "react-bootstrap/Modal";

import { UserContext } from '../context/UserContext'

import { APICallInit, APICallState } from "../services/ServiceUtil";
import { GetCurrentUser } from "../services/AuthService";

import AppNavbar from '../parts/AppNavbar';

import Home from './Home';
import AdminUserList from './AdminUserList';
import AdminUserEdit from './AdminUserEdit';
import MediaList from './MediaList';
import MediaEdit from './MediaEdit';
import RedirectList from './RedirectList';
import RedirectEdit from './RedirectEdit';
import WebPageList from './WebPageList';
import WebPageEdit from './WebPageEdit';
import WebPageTemplateList from './WebPageTemplateList';
import WebPageTemplateEdit from './WebPageTemplateEdit';
import Publish from './Publish'

function Template() {
  const [ userContext, userContextDispatch ] = useContext(UserContext);
  const [ userState, userDispatch ] = useReducer(APICallState, APICallInit);

  useEffect(() => {
    console.log("Checking user context changes", userContext, Date.now() - userContext.lastCheck);
    if (Date.now() - userContext.lastCheck > 5 * 60 * 1000) {
      userContextDispatch( { type: "CHECKING_LOGGED_IN" });
      GetCurrentUser(userDispatch);
    }
  }, [userContext]);

  useEffect(() => {
    console.log("Checking user state changes", userState);
    if (userState.data) {
      userContextDispatch( { type: "LOGGED_IN", payload: userState.data });
    } else {
      userContextDispatch( { type: "LOGGED_OUT" });
    }
    if (userState.isError) {
      userContextDispatch( { type: "ALERT_ERROR", payload: userState.errorMessage });
    } else if (userState.isLoading) {
      userContextDispatch( { type: "CHECKING_LOGGED_IN" });
    }
  }, [userState]);

  return (
    <div>
      <Router>
        <AppNavbar/>

        <Modal show={userContext.statusMessage}>
          <Modal.Header role="alert" className="alert alert-warning" variant="warning">Status</Modal.Header>
          <Modal.Body>{userContext.statusMessage}</Modal.Body>
          <Modal.Footer><Button size="sm" color="secondary"
              onClick={() => userContextDispatch( { type: "ALERT_CLOSE" })}>Close</Button></Modal.Footer>
        </Modal>

        <Modal show={userContext.errorMessage}>
          <Modal.Header role="alert" className="alert alert-danger">Error</Modal.Header>
          <Modal.Body>{userContext.errorMessage}</Modal.Body>
          <Modal.Footer><Button size="sm" color="secondary"
              onClick={() => userContextDispatch( { type: "ALERT_CLOSE" })}>Close</Button></Modal.Footer>
        </Modal>

        <Routes>
          <Route path={`/`} exact={true} element={<Home />}></Route>
          <Route path={`/adminUser`} exact={true} element={<AdminUserList />}></Route>
          <Route path={`/adminUser/:id`} element={<AdminUserEdit />}></Route>
          <Route path={`/media`} exact={true} element={<MediaList />}></Route>
          <Route path={`/media/:id`} element={<MediaEdit />}></Route>
          <Route path={`/redirect`} exact={true} element={<RedirectList />}></Route>
          <Route path={`/redirect/:id`} element={<RedirectEdit />}></Route>
          <Route path={`/webPage`} exact={true} element={<WebPageList />}></Route>
          <Route path={`/webPage/:id`} element={<WebPageEdit />}></Route>
          <Route path={`/webPageTemplate`} exact={true} element={<WebPageTemplateList />}></Route>
          <Route path={`/webPageTemplate/:id`} element={<WebPageTemplateEdit />}></Route>
          <Route path={`/publish`} exact={true} element={<Publish />}></Route>
        </Routes>
      </Router>
    </div>
  );
}

export default Template;